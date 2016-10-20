package co.id.artslv.service;

import co.id.artslv.lib.availability.AvailabilityData;
import co.id.artslv.lib.availability.ScheduleData;
import co.id.artslv.lib.inventory.Inventory;
import co.id.artslv.lib.payments.Fare;
import co.id.artslv.lib.responses.MessageWrapper;
import co.id.artslv.lib.schedule.PropertySchedule;
import co.id.artslv.lib.transactions.Bookingdata;
import co.id.artslv.lib.transactions.Pax;
import co.id.artslv.lib.transactions.Transaction;
import co.id.artslv.lib.transactions.Transactiondet;
import co.id.artslv.lib.users.User;
import co.id.artslv.lib.utility.CustomErrorResponse;
import co.id.artslv.lib.utility.CustomException;
import co.id.artslv.repository.*;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by root on 26/09/16.
 */
@Service
public class TransactionService {

    private static final String scheduleBaseUrl = "https://arts-schedule.herokuapp.com/schedule/arts_getschedule/";

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactiondetRepository transactiondetRepository;
    @Autowired
    private StasiunRepository stasiunRepository;
    @Autowired
    private PropertyScheduleRepository propertyscheduleRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private FareRepository fareRepository;
    @Autowired
    private DefaultSeatService defaultseatservice;

    @Autowired
    private UserRepository userRepository;

    @Transactional(rollbackFor = CustomException.class)
    public MessageWrapper<List<Transaction>> getAllTransaction() throws CustomException {
        List<Transaction> transactions = transactionRepository.findAll();
        if (transactions == null || transactions.isEmpty()) {
            throw new CustomException(new CustomErrorResponse("10", "No Transaction Available"));
        }
        MessageWrapper<List<Transaction>> messageWrapper = new MessageWrapper<>("00", "SUCCESS", transactions);
        return messageWrapper;
    }

    @Transactional(rollbackFor = CustomException.class)
    public MessageWrapper<Object> getTransactionDetails(Transaction transaction) throws CustomException {
        String transactionid = transaction.getId();
        Transaction availableTransaction = transactionRepository.findOne(transaction.getId());
        if (availableTransaction == null) {
            throw new CustomException(new CustomErrorResponse("10", "No Transaction Available"));
        }
        List<Transactiondet> avaTransactiondets = transactiondetRepository.findByTransactionid(transactionid);
        MessageWrapper<Object> resultWrapper = null;
        if (avaTransactiondets != null && !avaTransactiondets.isEmpty()) {
            resultWrapper = new MessageWrapper<>("00", "SUCCESS", availableTransaction, avaTransactiondets);
        } else {
            resultWrapper = new MessageWrapper<>("00", "SUCCESS", availableTransaction);
        }
        return resultWrapper;
    }

    @Transactional(rollbackFor = CustomException.class)
    public MessageWrapper<Object> getBookInfo(Transaction transaction, String rqid) throws CustomException, IOException {

        String bookcode = transaction.getBookcode();
        String paycode = transaction.getPaycode();

        User user = userRepository.findByRqid(rqid);
        if (user == null) {
            throw new CustomException(new CustomErrorResponse("01", "RQID is not valid"));
        }

        Transaction availableTransaction;
        if ((bookcode != null) && ((paycode == null) || (paycode.isEmpty()))) {
            availableTransaction = transactionRepository.findByBookcodeIgnoreCase(bookcode);
        } else if (((bookcode == null) || (bookcode.isEmpty())) && (paycode != null)) {
            availableTransaction = transactionRepository.findByPaycodeIgnoreCase(paycode);
        } else if ((bookcode != null) && (paycode != null)) {
            availableTransaction = transactionRepository.findByBookcodeIgnoreCaseAndPaycodeIgnoreCase(bookcode, paycode);
        } else {
            throw new CustomException(new CustomErrorResponse("10", "No Transaction Available"));
        }

        if (availableTransaction == null) {
            throw new CustomException(new CustomErrorResponse("10", "No Transaction Available"));
        }

        List<Transactiondet> avaTransactiondets = transactiondetRepository.findByTransactionid(availableTransaction.getId());
        if (avaTransactiondets == null || avaTransactiondets.isEmpty()) {
            throw new CustomException(new CustomErrorResponse("10", "No Transactiondet Available"));
        }
        Bookingdata bookingdata = new Bookingdata();
        bookingdata.setBookcode(availableTransaction.getBookcode());
        bookingdata.setPaycode(availableTransaction.getPaycode());
        bookingdata.setCustname(availableTransaction.getCustname());
        bookingdata.setPhone(availableTransaction.getPhonenum());
        bookingdata.setNoka(availableTransaction.getSchedulenoka());
        bookingdata.setTrainname(availableTransaction.getScheduleTrainname());
        bookingdata.setOrg(availableTransaction.getStasiuncodeorg());
        bookingdata.setDest(availableTransaction.getStasiuncodedes());
        bookingdata.setDepartdate(availableTransaction.getDepartdate());

        //USERPOS123/MRI/BPR/2017-01-01
        DateTimeFormatter formattter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String departdateS = availableTransaction.getDepartdate().format(formattter);
        RestTemplate restTemplate = new RestTemplate();
        String orgstatsiuncode = availableTransaction.getStasiuncodeorg();
        String deststasindest = availableTransaction.getStasiuncodedes();
        String url = new StringBuffer(scheduleBaseUrl).append(rqid)
                .append(String.format("/%s", orgstatsiuncode))
                .append(String.format("/%s", deststasindest))
                .append(String.format("/%s", departdateS)).toString();

        String result = restTemplate.getForObject(url, String.class);

        MessageWrapper<Object> messageWrapper = new MessageWrapper<>();
        List<AvailabilityData> availabilityDatas = messageWrapper.getValue(result, "availabilitydatalist", new TypeReference<List<AvailabilityData>>() {
        });

        AvailabilityData avadata = availabilityDatas.get(0);
        ScheduleData scheduleData = avadata.getScheduleDatas().get(0);
        String departtime = scheduleData.getStopdeparture();
        String arrivaltime = scheduleData.getStoparrival();

        bookingdata.setDeparttime(departtime);
        //proto temporarily, arrivedate = departdate (one day trip)
        bookingdata.setArrivedate(availableTransaction.getDepartdate());
        bookingdata.setArrivetime(arrivaltime);
        bookingdata.setSubclass(availableTransaction.getSubclasscode());
        bookingdata.setTotamount(availableTransaction.getTotamount());
        bookingdata.setExtrafee(availableTransaction.getExtrafee());
        bookingdata.setNetamount(availableTransaction.getNetamount());
        bookingdata.setBookbalance(availableTransaction.getPaidamount().subtract(availableTransaction.getNetamount()));

        List<Pax> paxs = avaTransactiondets.stream().map(ava -> {
            Pax pax = new Pax();
            pax.setName(ava.getTransactioncustname());
            pax.setIdnum(ava.getPsgid());
            pax.setTicketnum(ava.getTicketnum());
            pax.setStamformdetcode(ava.getStamformdetcode());
            pax.setWagondetrow(ava.getWagondetrow());
            pax.setWagondetcol(ava.getWagondetcol());
            pax.setStatus(ava.getStatus());
            return pax;
        }).collect(Collectors.toList());
        bookingdata.setPaxlist(paxs);

        MessageWrapper<Object> resultWrapper = null;
        resultWrapper = new MessageWrapper<>("00", "SUCCESS", bookingdata);

        return resultWrapper;
    }

    public MessageWrapper<Bookingdata> setBooking(Bookingdata bookingdata, String rqid) throws CustomException {
        User user = userRepository.findByRqid(rqid);
        if (user == null) {
            throw new CustomException(new CustomErrorResponse("01", "RQID is not valid"));
        }
        List<Pax> paxlist = bookingdata.getPaxlist();
        if (paxlist == null) {
            throw new CustomException(new CustomErrorResponse("10", "No Pax"));
        }
        if (bookingdata.getPropscheduleid() == null || bookingdata.getPropscheduleid().isEmpty()) {
            throw new CustomException(new CustomErrorResponse("10", "PropSchedule cannot be Empty"));
        }
        List<PropertySchedule> propSchedulelist = propertyscheduleRepository.findAll();
        Map<String, PropertySchedule> propscheduleMap = propSchedulelist.stream().collect(Collectors.toMap(PropertySchedule::getId, p -> p));
        List<Fare> farelist = fareRepository.findAll();
        Map<String, Fare> fareMap = farelist.stream().collect(Collectors.toMap(Fare::getId, p -> p));

        //Insert Transaction
        Transaction newtrans = new Transaction();
        newtrans.setBookcode(transactionRepository.getBookCode());
        newtrans.setPaycode(transactionRepository.getPayCode());
        newtrans.setTransdate(bookingdata.getTransdatetime());
        newtrans.setTotamount(bookingdata.getTotamount());
        newtrans.setExtrafee(bookingdata.getExtrafee());
        newtrans.setNetamount(bookingdata.getNetamount());
        newtrans.setPaidamount(bookingdata.getPaidamoount());
        newtrans.setCustname(bookingdata.getCustname());
        newtrans.setPhonenum(bookingdata.getPhone());
        newtrans.setEmail(bookingdata.getEmail());
        newtrans.setTotpsgadult(bookingdata.getTotpsgadult());
        newtrans.setTotpsgchild(bookingdata.getTotpsgchild());
        newtrans.setTotpsginfant(bookingdata.getTotpsginfant());
        newtrans.setDepartdate(propscheduleMap.get(bookingdata.getPropscheduleid()).getTripdate());
        newtrans.setStasiunidorg(propscheduleMap.get(bookingdata.getPropscheduleid()).getStasiunidorg());
        newtrans.setStasiuncodeorg(propscheduleMap.get(bookingdata.getPropscheduleid()).getStasiuncodeorg());
        newtrans.setStasiuniddes(propscheduleMap.get(bookingdata.getPropscheduleid()).getStasiuniddes());
        newtrans.setStasiuncodedes(propscheduleMap.get(bookingdata.getPropscheduleid()).getStasiuncodedes());
        newtrans.setScheduleid(bookingdata.getPropscheduleid());
        newtrans.setSchedulenoka(propscheduleMap.get(bookingdata.getPropscheduleid()).getNoka());
        newtrans.setSchedulelocalstat("1");
        newtrans.setScheduleTrainname(propscheduleMap.get(bookingdata.getPropscheduleid()).getTrainname());
        newtrans.setTripid(propscheduleMap.get(bookingdata.getPropscheduleid()).getTripid());
        newtrans.setTripdate(propscheduleMap.get(bookingdata.getPropscheduleid()).getTripdate());
        newtrans.setSubclassid(propscheduleMap.get(bookingdata.getPropscheduleid()).getSubclassid());
        newtrans.setSubclasscode(propscheduleMap.get(bookingdata.getPropscheduleid()).getSubclasscode());
        newtrans.setPaytypeid("PYT-TUNAI");    //ini dpet id dari mana??sementara 
        newtrans.setPaytypecode("TUNAI");   //Sama kayak paytypeid di hardcode
        newtrans.setUseridbook("userpos");
        newtrans.setUserfullnamebook("USER POS");
        newtrans.setUnitidbook("POS_MRI_01");
        newtrans.setUnitcodebook("POS_MRI_01");
        newtrans.setChannelidbook("CNL-POS");
        newtrans.setChannelcodebook("POS");
        newtrans.setUseridpay("userpos");
        newtrans.setUserfullnamepay("USER POS");
        newtrans.setUnitidpay("POS_MRI_01");
        newtrans.setUnitcodepay("POS_MRI_01");
        newtrans.setChannelidpay("CNL-POS");
        newtrans.setChannelcodepay("POS");
        newtrans.setBookedon(bookingdata.getTransdatetime());
        newtrans.setSmsbooksent("0");
        newtrans.setEmailbooksent("0");
        newtrans.setSmspaysent("0");
        newtrans.setEmailpaysent("0");
        newtrans.setReroutestat("0");
        newtrans.setFlexiredeemstat("0");
        newtrans.setStatus("1");
        newtrans.setDomain("4c112a65-e6f2-4b0d-bfef-0912748bdb76");
        newtrans.setCreatedby("userpos");
        newtrans.setCreatedon(LocalDateTime.now());
        newtrans.setModifiedby("userpos");
        newtrans.setModifiedon(LocalDateTime.now());

        Transaction savetrans = transactionRepository.save(newtrans);
        if (savetrans == null) {
            throw new CustomException(new CustomErrorResponse("10", "Booking (Insert Transaction) Failed"));
        }
        List<Inventory> inventorylist = defaultseatservice.getDefaultSeat(savetrans.getTripdate(), savetrans.getStasiuncodeorg(), savetrans.getStasiuncodedes(), savetrans.getSchedulenoka(), paxlist.size());
        //insert transactiondet
        int order = 1;
        List<Pax> newpaxlist = new ArrayList<>();
        for (Pax pax : paxlist) {
            Transactiondet newtransdet = new Transactiondet();
            newtransdet.setTransactionid(savetrans.getId());
            newtransdet.setTransactionbookcode(savetrans.getBookcode());
            newtransdet.setTransactionpaycode(savetrans.getPaycode());
            newtransdet.setTransactiontransdate(bookingdata.getTransdatetime());
            newtransdet.setTransactioncustname(bookingdata.getCustname());
            newtransdet.setTransactionphonenum(bookingdata.getPhone());
            newtransdet.setTransactionemail(bookingdata.getEmail());
            newtransdet.setStasiunidorg(propscheduleMap.get(bookingdata.getPropscheduleid()).getStasiunidorg());
            newtransdet.setStasiuncodeorg(propscheduleMap.get(bookingdata.getPropscheduleid()).getStasiuncodeorg());
            newtransdet.setStasiuniddes(propscheduleMap.get(bookingdata.getPropscheduleid()).getStasiuniddes());
            newtransdet.setStasiuncodedes(propscheduleMap.get(bookingdata.getPropscheduleid()).getStasiuncodedes());
            newtransdet.setScheduleid(bookingdata.getPropscheduleid());
            newtransdet.setSchedulenoka(propscheduleMap.get(bookingdata.getPropscheduleid()).getNoka());
            newtransdet.setSchedulelocalstat("1");
            newtransdet.setScheduletrainname(propscheduleMap.get(bookingdata.getPropscheduleid()).getTrainname());
            newtransdet.setTripid(propscheduleMap.get(bookingdata.getPropscheduleid()).getTripid());
            newtransdet.setTripdate(propscheduleMap.get(bookingdata.getPropscheduleid()).getTripdate());
            newtransdet.setSubclassid(propscheduleMap.get(bookingdata.getPropscheduleid()).getSubclassid());
            newtransdet.setSubclasscode(propscheduleMap.get(bookingdata.getPropscheduleid()).getSubclasscode());
            newtransdet.setUseridbook("userpos");
            newtransdet.setUserfullnamebook("USER POS");
            newtransdet.setUnitidbook("POS_MRI_01");
            newtransdet.setUnitcodebook("POS_MRI_01");
            newtransdet.setChannelidbook("CNL-POS");
            newtransdet.setChannelcodebook("POS");
            newtransdet.setUseridpay("userpos");
            newtransdet.setUserfullnamepay("USER POS");
            newtransdet.setUnitidpay("POS_MRI_01");
            newtransdet.setUnitcodepay("POS_MRI_01");
            newtransdet.setChannelidpay("CNL-POS");
            newtransdet.setChannelcodepay("POS");
            newtransdet.setTransactionbookedon(bookingdata.getTransdatetime());
            newtransdet.setTransactionreroutestat("0");
            newtransdet.setTransactionflexiredeemstat("0");
            newtransdet.setPsgname(pax.getName());
            newtransdet.setPsgid(pax.getIdnum());
            newtransdet.setStamformdetid(inventorylist.get(order - 1).getStamformdetid());
            newtransdet.setStamformdetcode(inventorylist.get(order - 1).getStamformdetcode());
            newtransdet.setWagondetid(inventorylist.get(order - 1).getWagondetid());
            newtransdet.setWagondetrow(inventorylist.get(order - 1).getWagondetrow());
            newtransdet.setWagondetcol(inventorylist.get(order - 1).getWagondetcol());
            newtransdet.setAmount(pax.getAmount());
            newtransdet.setTicketnum("DUMMYTICKETNUM-" + order);
            newtransdet.setFareid(propscheduleMap.get(bookingdata.getPropscheduleid()).getFareid());
            newtransdet.setFarebasicfare(fareMap.get(newtransdet.getFareid()).getBasicfare());
            newtransdet.setFaretuslahfee(fareMap.get(newtransdet.getFareid()).getTuslahfee());
            newtransdet.setFarersvfee(fareMap.get(newtransdet.getFareid()).getRsvfee());
            newtransdet.setFarestfee(fareMap.get(newtransdet.getFareid()).getStfee());
            newtransdet.setFarersvfee(fareMap.get(newtransdet.getFareid()).getRsvfee());
            newtransdet.setFareaddfee(fareMap.get(newtransdet.getFareid()).getAddfee());
            newtransdet.setFarecompinsurance(fareMap.get(newtransdet.getFareid()).getCompinsurance());
            newtransdet.setFareaddinsurance(fareMap.get(newtransdet.getFareid()).getAddinsurance());
            newtransdet.setFarersvfee(fareMap.get(newtransdet.getFareid()).getRsvfee());
            newtransdet.setFarepsofare(fareMap.get(newtransdet.getFareid()).getPsofare());
            newtransdet.setFaretotamount(fareMap.get(newtransdet.getFareid()).getTotamount());
            newtransdet.setMaxprint(99);
            newtransdet.setStatus("1");
            newtransdet.setDomain("4c112a65-e6f2-4b0d-bfef-0912748bdb76");
            newtransdet.setCreatedby("userpos");
            newtransdet.setCreatedon(LocalDateTime.now());
            newtransdet.setModifiedby("userpos");
            newtransdet.setModifiedon(LocalDateTime.now());
            newtransdet.setOrder(order);
            newtransdet.setReleasestat("0");
            Transactiondet savetransdet = transactiondetRepository.save(newtransdet);
            if (savetransdet == null) {
                throw new CustomException(new CustomErrorResponse("10", "Booking (Insert PSG: " + newtransdet.getPsgname() + ") Failed"));
            }
            pax.setSeat(newtransdet.getStamformdetcode() + "/" + newtransdet.getWagondetrow() + newtransdet.getWagondetcol());
            pax.setWagondetrow(newtransdet.getWagondetrow());
            pax.setWagondetcol(newtransdet.getWagondetcol());
            newpaxlist.add(pax);

            Inventory inventory = inventoryRepository.findOne(inventorylist.get(order - 1).getId());
            if (inventory == null) {
                throw new CustomException(new CustomErrorResponse("10", "Seat Not Found"));
            }
            inventory.setBookstat("1");
            inventory.setBookcode(savetrans.getBookcode());
            inventory.setTransactiondetorder(order);
            inventoryRepository.save(inventory);
            order++;
        }

        Bookingdata result = new Bookingdata();
        result.setCustname(savetrans.getCustname());
        result.setPhone(savetrans.getPhonenum());
        result.setEmail(savetrans.getEmail());
        result.setDepartdate(propscheduleMap.get(bookingdata.getPropscheduleid()).getTripdate());
        result.setDeparttime(propscheduleMap.get(bookingdata.getPropscheduleid()).getStopdeparture());
        result.setArrivedate(propscheduleMap.get(bookingdata.getPropscheduleid()).getTripdate());
        result.setArrivetime(propscheduleMap.get(bookingdata.getPropscheduleid()).getStoparrival());
        result.setOrg(savetrans.getStasiuncodeorg());
        result.setDest(savetrans.getStasiuncodedes());
        result.setBookcode(savetrans.getBookcode());
        result.setNoka(savetrans.getSchedulenoka());
        result.setTrainname(savetrans.getScheduleTrainname());
        result.setPaycode(savetrans.getPaycode());
        result.setTotpsgadult(savetrans.getTotpsgadult());
        result.setTotpsgchild(savetrans.getTotpsgchild());
        result.setTotpsginfant(savetrans.getTotpsginfant());
        result.setTotamount(savetrans.getTotamount());
        result.setNetamount(savetrans.getNetamount());
        result.setExtrafee(savetrans.getExtrafee());
        result.setPaxlist(newpaxlist);

        MessageWrapper<Bookingdata> messageWrapper = new MessageWrapper<>("00", "SUCCESS", result);
        return messageWrapper;
    }

    public MessageWrapper<Bookingdata> setBookingv2(Bookingdata bookingdata,String rqid) throws CustomException {
        User user = userRepository.findByRqid(rqid);
        if (user == null) {
            throw new CustomException(new CustomErrorResponse("01", "RQID is not valid"));
        }
        List<Pax> paxlist = bookingdata.getPaxlist();
        if (paxlist == null) {
            throw new CustomException(new CustomErrorResponse("10", "No Pax"));
        }
        if (bookingdata.getPropscheduleid() == null || bookingdata.getPropscheduleid().isEmpty()) {
            throw new CustomException(new CustomErrorResponse("10", "PropSchedule cannot be Empty"));
        }
        List<PropertySchedule> propSchedulelist = propertyscheduleRepository.findAll();
        Map<String, PropertySchedule> propscheduleMap = propSchedulelist.stream().collect(Collectors.toMap(PropertySchedule::getId, p -> p));
        List<Inventory> inventorylist = inventoryRepository.findAll();
        Map<String, Inventory> inventoryMap = inventorylist.stream().collect(Collectors.toMap(Inventory::getId, p -> p));
        List<Fare> farelist = fareRepository.findAll();
        Map<String, Fare> fareMap = farelist.stream().collect(Collectors.toMap(Fare::getId, p -> p));

        //Insert Transaction
        Transaction newtrans = new Transaction();
        newtrans.setBookcode(transactionRepository.getBookCode());
        newtrans.setPaycode(transactionRepository.getPayCode());
        newtrans.setTransdate(bookingdata.getTransdatetime());
        newtrans.setTotamount(bookingdata.getTotamount());
        newtrans.setExtrafee(bookingdata.getExtrafee());
        newtrans.setNetamount(bookingdata.getNetamount());
        newtrans.setPaidamount(bookingdata.getPaidamoount());
        newtrans.setCustname(bookingdata.getCustname());
        newtrans.setPhonenum(bookingdata.getPhone());
        newtrans.setEmail(bookingdata.getEmail());
        newtrans.setTotpsgadult(bookingdata.getTotpsgadult());
        newtrans.setTotpsgchild(bookingdata.getTotpsgchild());
        newtrans.setTotpsginfant(bookingdata.getTotpsginfant());
        newtrans.setDepartdate(propscheduleMap.get(bookingdata.getPropscheduleid()).getTripdate());
        newtrans.setStasiunidorg(propscheduleMap.get(bookingdata.getPropscheduleid()).getStasiunidorg());
        newtrans.setStasiuncodeorg(propscheduleMap.get(bookingdata.getPropscheduleid()).getStasiuncodeorg());
        newtrans.setStasiuniddes(propscheduleMap.get(bookingdata.getPropscheduleid()).getStasiuniddes());
        newtrans.setStasiuncodedes(propscheduleMap.get(bookingdata.getPropscheduleid()).getStasiuncodedes());
        newtrans.setScheduleid(bookingdata.getPropscheduleid());
        newtrans.setSchedulenoka(propscheduleMap.get(bookingdata.getPropscheduleid()).getNoka());
        newtrans.setSchedulelocalstat("1");
        newtrans.setScheduleTrainname(propscheduleMap.get(bookingdata.getPropscheduleid()).getTrainname());
        newtrans.setTripid(propscheduleMap.get(bookingdata.getPropscheduleid()).getTripid());
        newtrans.setTripdate(propscheduleMap.get(bookingdata.getPropscheduleid()).getTripdate());
        newtrans.setSubclassid(propscheduleMap.get(bookingdata.getPropscheduleid()).getSubclassid());
        newtrans.setSubclasscode(propscheduleMap.get(bookingdata.getPropscheduleid()).getSubclasscode());
        newtrans.setPaytypeid("PYT-TUNAI");    //ini dpet id dari mana??sementara 
        newtrans.setPaytypecode("TUNAI");   //Sama kayak paytypeid di hardcode
        newtrans.setUseridbook("userpos");
        newtrans.setUserfullnamebook("USER POS");
        newtrans.setUnitidbook("POS_MRI_01");
        newtrans.setUnitcodebook("POS_MRI_01");
        newtrans.setChannelidbook("CNL-POS");
        newtrans.setChannelcodebook("POS");
        newtrans.setUseridpay("userpos");
        newtrans.setUserfullnamepay("USER POS");
        newtrans.setUnitidpay("POS_MRI_01");
        newtrans.setUnitcodepay("POS_MRI_01");
        newtrans.setChannelidpay("CNL-POS");
        newtrans.setChannelcodepay("POS");
        newtrans.setBookedon(bookingdata.getTransdatetime());
        newtrans.setSmsbooksent("0");
        newtrans.setEmailbooksent("0");
        newtrans.setSmspaysent("0");
        newtrans.setEmailpaysent("0");
        newtrans.setReroutestat("0");
        newtrans.setFlexiredeemstat("0");
        newtrans.setStatus("1");
        newtrans.setDomain("4c112a65-e6f2-4b0d-bfef-0912748bdb76");
        newtrans.setCreatedby("userpos");
        newtrans.setCreatedon(LocalDateTime.now());
        newtrans.setModifiedby("userpos");
        newtrans.setModifiedon(LocalDateTime.now());

        Transaction savetrans = transactionRepository.save(newtrans);
        if (savetrans == null) {
            throw new CustomException(new CustomErrorResponse("10", "Booking (Insert Transaction) Failed"));
        }
        //insert transactiondet
        int order = 1;
        List<Pax> newpaxlist = new ArrayList<>();
        for (Pax pax : paxlist) {
            Transactiondet newtransdet = new Transactiondet();
            newtransdet.setTransactionid(savetrans.getId());
            newtransdet.setTransactionbookcode(savetrans.getBookcode());
            newtransdet.setTransactionpaycode(savetrans.getPaycode());
            newtransdet.setTransactiontransdate(bookingdata.getTransdatetime());
            newtransdet.setTransactioncustname(bookingdata.getCustname());
            newtransdet.setTransactionphonenum(bookingdata.getPhone());
            newtransdet.setTransactionemail(bookingdata.getEmail());
            newtransdet.setStasiunidorg(propscheduleMap.get(bookingdata.getPropscheduleid()).getStasiunidorg());
            newtransdet.setStasiuncodeorg(propscheduleMap.get(bookingdata.getPropscheduleid()).getStasiuncodeorg());
            newtransdet.setStasiuniddes(propscheduleMap.get(bookingdata.getPropscheduleid()).getStasiuniddes());
            newtransdet.setStasiuncodedes(propscheduleMap.get(bookingdata.getPropscheduleid()).getStasiuncodedes());
            newtransdet.setScheduleid(bookingdata.getPropscheduleid());
            newtransdet.setSchedulenoka(propscheduleMap.get(bookingdata.getPropscheduleid()).getNoka());
            newtransdet.setSchedulelocalstat("1");
            newtransdet.setScheduletrainname(propscheduleMap.get(bookingdata.getPropscheduleid()).getTrainname());
            newtransdet.setTripid(propscheduleMap.get(bookingdata.getPropscheduleid()).getTripid());
            newtransdet.setTripdate(propscheduleMap.get(bookingdata.getPropscheduleid()).getTripdate());
            newtransdet.setSubclassid(propscheduleMap.get(bookingdata.getPropscheduleid()).getSubclassid());
            newtransdet.setSubclasscode(propscheduleMap.get(bookingdata.getPropscheduleid()).getSubclasscode());
            newtransdet.setUseridbook("userpos");
            newtransdet.setUserfullnamebook("USER POS");
            newtransdet.setUnitidbook("POS_MRI_01");
            newtransdet.setUnitcodebook("POS_MRI_01");
            newtransdet.setChannelidbook("CNL-POS");
            newtransdet.setChannelcodebook("POS");
            newtransdet.setUseridpay("userpos");
            newtransdet.setUserfullnamepay("USER POS");
            newtransdet.setUnitidpay("POS_MRI_01");
            newtransdet.setUnitcodepay("POS_MRI_01");
            newtransdet.setChannelidpay("CNL-POS");
            newtransdet.setChannelcodepay("POS");
            newtransdet.setTransactionbookedon(bookingdata.getTransdatetime());
            newtransdet.setTransactionreroutestat("0");
            newtransdet.setTransactionflexiredeemstat("0");
            newtransdet.setPsgname(pax.getName());
            newtransdet.setPsgid(pax.getIdnum());
            newtransdet.setStamformdetid(inventoryMap.get(pax.getInventoryid()).getStamformdetid());
            newtransdet.setStamformdetcode(inventoryMap.get(pax.getInventoryid()).getStamformdetcode());
            newtransdet.setWagondetid(inventoryMap.get(pax.getInventoryid()).getWagondetid());
            newtransdet.setWagondetrow(inventoryMap.get(pax.getInventoryid()).getWagondetrow());
            newtransdet.setWagondetcol(inventoryMap.get(pax.getInventoryid()).getWagondetcol());
            newtransdet.setAmount(pax.getAmount());
            newtransdet.setTicketnum("DUMMYTICKETNUM-" + order);
            newtransdet.setFareid(propscheduleMap.get(bookingdata.getPropscheduleid()).getFareid());
            newtransdet.setFarebasicfare(fareMap.get(newtransdet.getFareid()).getBasicfare());
            newtransdet.setFaretuslahfee(fareMap.get(newtransdet.getFareid()).getTuslahfee());
            newtransdet.setFarersvfee(fareMap.get(newtransdet.getFareid()).getRsvfee());
            newtransdet.setFarestfee(fareMap.get(newtransdet.getFareid()).getStfee());
            newtransdet.setFarersvfee(fareMap.get(newtransdet.getFareid()).getRsvfee());
            newtransdet.setFareaddfee(fareMap.get(newtransdet.getFareid()).getAddfee());
            newtransdet.setFarecompinsurance(fareMap.get(newtransdet.getFareid()).getCompinsurance());
            newtransdet.setFareaddinsurance(fareMap.get(newtransdet.getFareid()).getAddinsurance());
            newtransdet.setFarersvfee(fareMap.get(newtransdet.getFareid()).getRsvfee());
            newtransdet.setFarepsofare(fareMap.get(newtransdet.getFareid()).getPsofare());
            newtransdet.setFaretotamount(fareMap.get(newtransdet.getFareid()).getTotamount());
            newtransdet.setMaxprint(99);
            newtransdet.setStatus("1");
            newtransdet.setDomain("4c112a65-e6f2-4b0d-bfef-0912748bdb76");
            newtransdet.setCreatedby("userpos");
            newtransdet.setCreatedon(LocalDateTime.now());
            newtransdet.setModifiedby("userpos");
            newtransdet.setModifiedon(LocalDateTime.now());
            newtransdet.setOrder(order);
            newtransdet.setReleasestat("0");
            Transactiondet savetransdet = transactiondetRepository.save(newtransdet);
            if (savetransdet == null) {
                throw new CustomException(new CustomErrorResponse("10", "Booking (Insert PSG: " + newtransdet.getPsgname() + ") Failed"));
            }
            pax.setSeat(newtransdet.getStamformdetcode() + "/" + newtransdet.getWagondetrow() + newtransdet.getWagondetcol());
            pax.setWagondetrow(newtransdet.getWagondetrow());
            pax.setWagondetcol(newtransdet.getWagondetcol());
            newpaxlist.add(pax);

            Inventory inventory = inventoryRepository.findOne(inventoryMap.get(pax.getInventoryid()).getId());
            if (inventory == null) {
                throw new CustomException(new CustomErrorResponse("10", "Seat Not Found"));
            }
            inventory.setBookstat("1");
            inventory.setBookcode(savetrans.getBookcode());
            inventory.setTransactiondetorder(order);
            inventoryRepository.save(inventory);
            order++;
        }

        Bookingdata result = new Bookingdata();
        result.setCustname(savetrans.getCustname());
        result.setPhone(savetrans.getPhonenum());
        result.setEmail(savetrans.getEmail());
        result.setDepartdate(propscheduleMap.get(bookingdata.getPropscheduleid()).getTripdate());
        result.setDeparttime(propscheduleMap.get(bookingdata.getPropscheduleid()).getStopdeparture());
        result.setArrivedate(propscheduleMap.get(bookingdata.getPropscheduleid()).getTripdate());
        result.setArrivetime(propscheduleMap.get(bookingdata.getPropscheduleid()).getStoparrival());
        result.setOrg(savetrans.getStasiuncodeorg());
        result.setDest(savetrans.getStasiuncodedes());
        result.setBookcode(savetrans.getBookcode());
        result.setNoka(savetrans.getSchedulenoka());
        result.setTrainname(savetrans.getScheduleTrainname());
        result.setPaycode(savetrans.getPaycode());
        result.setTotpsgadult(savetrans.getTotpsgadult());
        result.setTotpsgchild(savetrans.getTotpsgchild());
        result.setTotpsginfant(savetrans.getTotpsginfant());
        result.setTotamount(savetrans.getTotamount());
        result.setNetamount(savetrans.getNetamount());
        result.setExtrafee(savetrans.getExtrafee());
        result.setPaxlist(newpaxlist);

        MessageWrapper<Bookingdata> messageWrapper = new MessageWrapper<>("00", "SUCCESS", result);
        return messageWrapper;
    }
}
