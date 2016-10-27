package co.id.artslv.service;

import co.id.artslv.lib.inventory.Inventory;
import co.id.artslv.lib.schedule.PropertySchedule;
import co.id.artslv.lib.schedule.Stop;
import co.id.artslv.lib.users.User;
import co.id.artslv.lib.utility.CustomErrorResponse;
import co.id.artslv.lib.utility.CustomException;
import co.id.artslv.repository.InventoryRepository;
import co.id.artslv.repository.PropertyScheduleRepository;
import co.id.artslv.repository.StopRepository;
import co.id.artslv.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by root on 19/10/16.
 */
@Service
public class DefaultSeatService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private PropertyScheduleRepository propertyScheduleRepository;

    private static final ExecutorService ex = Executors.newFixedThreadPool(2);
    private static final Logger logger = LoggerFactory.getLogger(DefaultSeatService.class);

    @Transactional(rollbackFor = CustomException.class,propagation = Propagation.REQUIRED)
    public List<Inventory> getDefaultSeat(LocalDate tripdate, String orgstasiun, String deststasiun, String noka, int noOfPassanger,String propertyid) throws CustomException {

        logger.info("Go To getDefaultSeat");

        PropertySchedule propertySchedule =  propertyScheduleRepository.findById(propertyid);
        int searemaining = propertySchedule.getSeatavailable();

        if(searemaining<noOfPassanger){
            throw new CustomException(new CustomErrorResponse("10","No Seat Available"));
        }

        List<Stop> stopOrigin = stopRepository.findByStatsiuncode(orgstasiun);
        List<Stop> stopDestination = stopRepository.findByStatsiuncode(deststasiun);
        int orgOrder = stopOrigin.get(0).getStoporder();
        int destOrder = stopDestination.get(0).getStoporder();

        Map<String,Integer> stoporderMap = seatStopOrder(orgOrder,destOrder);

        int orderBenchmark = orgOrder<destOrder?orgOrder:destOrder;
        if(orgOrder>destOrder){
            int temp = orgOrder;
            orgOrder = destOrder;
            destOrder = temp;
        }


        List<Inventory> benchmarkOriginInventory = inventoryRepository.findByBookstatAndTripdateAndStoporder("0",tripdate,orderBenchmark);
        List<Inventory> occupiedSeatBetweenOrgNDest = inventoryRepository.findByBookstatAndTripdateAndStoporderBetween("1",tripdate,orgOrder,destOrder);
        //Remove data occupied seat from benchmark
        occupiedSeatBetweenOrgNDest.forEach(inventory -> {
            benchmarkOriginInventory.removeIf(p->p.getWagondetid().equals(inventory.getWagondetid()) && p.getStamformdetcode().equals(inventory.getStamformdetcode()));
        });

        List<Inventory> availableSeats = new ArrayList<>();
        benchmarkOriginInventory.forEach(inventory -> {

            Inventory availableSeat = new Inventory();
            availableSeat.setStatus(inventory.getStatus());
            availableSeat.setBookstat(inventory.getBookstat());
            availableSeat.setSchedulenoka(inventory.getSchedulenoka());
            availableSeat.setTripdate(inventory.getTripdate());
            availableSeat.setWagondetcol(inventory.getWagondetcol());
            availableSeat.setWagondetid(inventory.getWagondetid());
            availableSeat.setWagondetrow(inventory.getWagondetrow());
            availableSeat.setStamformdetcode(inventory.getStamformdetcode());
            availableSeat.setStamformdetid(inventory.getStamformdetid());
            availableSeat.setId(inventory.getId());
            availableSeats.add(availableSeat);
        });


        if(availableSeats==null || availableSeats.isEmpty()){
            throw new CustomException(new CustomErrorResponse("10","No Seat Available"));
        }

        //Filter based on noka
        List<Inventory> availableBasedOnNoka = availableSeats.stream().filter(avail->avail.getSchedulenoka().equals(noka)).collect(Collectors.toList());

        availableBasedOnNoka.sort((o1, o2) -> {
            String comb1 = o1.getStamformdetcode()+"-"+o1.getWagondetrow()+"-"+o1.getWagondetcolnum();
            String comb2 = o2.getStamformdetcode()+"-"+o2.getWagondetrow()+"-"+o2.getWagondetcolnum();

            return comb1.compareTo(comb2);
        });

        List<Inventory> nSeat = availableBasedOnNoka.stream().limit(noOfPassanger).collect(Collectors.toList());

        List<Inventory> updatedInventoryOrgtoDes = new ArrayList<>();

        for(Inventory inv:nSeat){
            updatedInventoryOrgtoDes.addAll(inventoryRepository.findByStamformdetidAndWagondetidAndStoporderBetween(inv.getStamformdetid(),inv.getWagondetid(),stoporderMap.get("origin"),stoporderMap.get("dest")));
        }

        updatedInventoryOrgtoDes.forEach(i->i.setBookstat("1"));
        inventoryRepository.save(updatedInventoryOrgtoDes);

        updateAvailableSeat(propertyid,noka,noOfPassanger);

        logger.info(writeSeatLogs(nSeat));

        return nSeat;
    }


    private Map<String,Integer> seatStopOrder(int orginOrder,int destOrder){
        int stopOrderOrg = 0;
        int stopOrderDest = 0;
        if(orginOrder>destOrder){
            int temp = orginOrder;
            orginOrder = destOrder;
            destOrder = temp;

            stopOrderOrg = orginOrder+1;
            stopOrderDest = destOrder;
        }else{
            stopOrderOrg = orginOrder;
            stopOrderDest = destOrder-1;
        }
        Map<String,Integer> map = new HashMap<>();
        map.put("origin",stopOrderOrg);
        map.put("dest",stopOrderDest);
        return map;
    }

    public String writeSeatLogs(List<Inventory> seats){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String value = objectMapper.writeValueAsString(seats);
            return value;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PropertySchedule> updateAvailableSeat(String propertyid, String noka, int noOfPassanger){
        logger.info("updateAvailableSeat");
        PropertySchedule propertySchedule =  propertyScheduleRepository.findById(propertyid);
        int stoporg = propertySchedule.getStoporderorg();
        int stopdest = propertySchedule.getStoporderdest();
        String subclass = propertySchedule.getSubclasscode();
        int count = 0;
        Map<Integer,List<Integer>> map = new HashMap<>();
        for(int i=stoporg;i<stopdest;i++){
            for(int j=i+1;j<=stopdest;j++){
                count++;
                map.put(count, Arrays.asList(i,j));
            }
        }
        List<PropertySchedule> updatedPropertySchedule = new ArrayList<>();
        Set<Map.Entry<Integer,List<Integer>>> entrys = map.entrySet();
        for(Map.Entry<Integer,List<Integer>> entry:entrys){
            List<Integer> stopedge = entry.getValue();
            PropertySchedule pro = propertyScheduleRepository.findByStoporderorgAndStoporderdestAndSubclasscodeAndNoka(stopedge.get(0),stopedge.get(1),subclass,noka);
            int avaseat = pro.getSeatavailable();
            int remainingseat = (avaseat-noOfPassanger)>0?(avaseat-noOfPassanger):0;
            pro.setSeatavailable(remainingseat);
            updatedPropertySchedule.add(pro);
        }
        List<PropertySchedule> updatedResult = propertyScheduleRepository.save(updatedPropertySchedule);
        return updatedResult;
    }
}
