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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        logger.info("Go To getDefaultSeat ======> 1");
        if(searemaining<noOfPassanger){
            throw new CustomException(new CustomErrorResponse("10","No Seat Available"));
        }
        logger.info("Go To getDefaultSeat ======> 2");

        List<Stop> stopOrigin = stopRepository.findByStatsiuncode(orgstasiun);
        List<Stop> stopDestination = stopRepository.findByStatsiuncode(deststasiun);
        int orgOrder = stopOrigin.get(0).getStoporder();
        int destOrder = stopDestination.get(0).getStoporder();

        logger.info("Go To getDefaultSeat ======> 3");

        Map<String,Integer> stoporderMap = seatStopOrder(orgOrder,destOrder);

        int orderBenchmark = orgOrder<destOrder?orgOrder:destOrder;
        if(orgOrder>destOrder){
            int temp = orgOrder;
            orgOrder = destOrder;
            destOrder = temp;
        }

        logger.info("Go To getDefaultSeat ======> 4");

        List<Inventory> benchmarkOriginInventory = inventoryRepository.findByBookstatAndTripdateAndStoporder("0",tripdate,orderBenchmark);
        List<Inventory> occupiedSeatBetweenOrgNDest = inventoryRepository.findByBookstatAndTripdateAndStoporderBetween("1",tripdate,orgOrder,destOrder);
        //Remove data occupied seat from benchmark
        occupiedSeatBetweenOrgNDest.forEach(inventory -> {
            benchmarkOriginInventory.removeIf(p->p.getWagondetid().equals(inventory.getWagondetid()) && p.getStamformdetcode().equals(inventory.getStamformdetcode()));
        });

        logger.info("Go To getDefaultSeat ======> 5");

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

        logger.info("Go To getDefaultSeat ======> 6");



        if(availableSeats==null || availableSeats.isEmpty()){
            throw new CustomException(new CustomErrorResponse("10","No Seat Available"));
        }

        logger.info("Go To getDefaultSeat ======> 7");

        //Filter based on noka
        List<Inventory> availableBasedOnNoka = availableSeats.stream().filter(avail->avail.getSchedulenoka().equals(noka)).collect(Collectors.toList());

        availableBasedOnNoka.sort((o1, o2) -> {
            String comb1 = o1.getStamformdetcode()+"-"+o1.getWagondetrow()+"-"+o1.getWagondetcolnum();
            String comb2 = o2.getStamformdetcode()+"-"+o2.getWagondetrow()+"-"+o2.getWagondetcolnum();

            return comb1.compareTo(comb2);
        });

        logger.info("Go To getDefaultSeat ======> 8");

        List<Inventory> nSeat = availableBasedOnNoka.stream().limit(noOfPassanger).collect(Collectors.toList());

        List<Inventory> updatedInventoryOrgtoDes = new ArrayList<>();

        for(Inventory inv:nSeat){
            updatedInventoryOrgtoDes.addAll(inventoryRepository.findByStamformdetidAndWagondetidAndStoporderBetween(inv.getStamformdetid(),inv.getWagondetid(),stoporderMap.get("origin"),stoporderMap.get("dest")));
        }

        logger.info("Go To getDefaultSeat ======> 9");
        updatedInventoryOrgtoDes.forEach(i->i.setBookstat("1"));
        inventoryRepository.save(updatedInventoryOrgtoDes);

        logger.info("Go To getDefaultSeat ======> 10");

        int avaseat = propertySchedule.getSeatavailable();
        int remainingseat = (avaseat-noOfPassanger)>0?(avaseat-noOfPassanger):0;
        propertySchedule.setSeatavailable(remainingseat);

        propertyScheduleRepository.save(propertySchedule);

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
}
