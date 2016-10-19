package co.id.artslv.service;

import co.id.artslv.lib.inventory.Inventory;
import co.id.artslv.lib.schedule.Stop;
import co.id.artslv.repository.InventoryRepository;
import co.id.artslv.repository.StopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    public List<Inventory> getDefaultSeat(LocalDate tripdate, String orgstasiun, String deststasiun, String noka, int noOfPassanger){

        List<Stop> stopOrigin = stopRepository.findByStatsiuncode(orgstasiun);
        List<Stop> stopDestination = stopRepository.findByStatsiuncode(deststasiun);
        int orgOrder = stopOrigin.get(0).getStoporder();
        int destOrder = stopDestination.get(0).getStoporder();

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

        //Filter based on noka
        List<Inventory> availableBasedOnNoka = availableSeats.stream().filter(avail->avail.getSchedulenoka().equals(noka)).collect(Collectors.toList());

        availableBasedOnNoka.sort((o1, o2) -> {
            String comb1 = o1.getStamformdetcode()+"-"+o1.getWagondetrow()+"-"+o1.getWagondetcolnum();
            String comb2 = o2.getStamformdetcode()+"-"+o2.getWagondetrow()+"-"+o2.getWagondetcolnum();

            return comb1.compareTo(comb2);
        });

        List<Inventory> nSeat = availableBasedOnNoka.stream().limit(noOfPassanger).collect(Collectors.toList());

        return nSeat;
    }
}
