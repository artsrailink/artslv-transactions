package co.id.artslv.service;

import co.id.artslv.lib.availability.AvailabilityData;
import co.id.artslv.lib.availability.ScheduleData;
import co.id.artslv.lib.responses.MessageWrapper;
import co.id.artslv.lib.transactions.Bookingdata;
import co.id.artslv.lib.transactions.Pax;
import co.id.artslv.lib.transactions.Transaction;
import co.id.artslv.lib.transactions.Transactiondet;
import co.id.artslv.lib.users.User;
import co.id.artslv.lib.utility.CustomErrorResponse;
import co.id.artslv.lib.utility.CustomException;
import co.id.artslv.repository.TransactionRepository;
import co.id.artslv.repository.TransactiondetRepository;
import co.id.artslv.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by root on 26/09/16.
 */
@Service
public class TransactionService {

    private static final String scheduleBaseUrl = "https://arts-schedule.herokuapp.com/schedule/getschedule/";

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactiondetRepository transactiondetRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(rollbackFor = CustomException.class)
    public MessageWrapper<List<Transaction>> getAllTransaction() throws CustomException {
        List<Transaction> transactions = transactionRepository.findAll();
        if(transactions==null || transactions.isEmpty()){
            throw new CustomException(new CustomErrorResponse("10","No Transaction Available"));
        }
        MessageWrapper<List<Transaction>> messageWrapper = new MessageWrapper<>("00","SUCCESS",transactions);
        return messageWrapper;
    }

    @Transactional(rollbackFor = CustomException.class)
    public MessageWrapper<Object> getTransactionDetails(Transaction transaction) throws CustomException {
        String transactionid = transaction.getId();
        Transaction availableTransaction = transactionRepository.findOne(transaction.getId());
        if(availableTransaction==null){
            throw new CustomException(new CustomErrorResponse("10","No Transaction Available"));
        }
        List<Transactiondet> avaTransactiondets = transactiondetRepository.findByTransactionid(transactionid);
        MessageWrapper<Object> resultWrapper = null;
        if(avaTransactiondets!=null && !avaTransactiondets.isEmpty()){
            resultWrapper = new MessageWrapper<>("00","SUCCESS",availableTransaction,avaTransactiondets);
        }else{
            resultWrapper = new MessageWrapper<>("00","SUCCESS",availableTransaction);
        }
        return resultWrapper;
    }

    @Transactional(rollbackFor = CustomException.class)
    public MessageWrapper<Object> getBookInfo(Transaction transaction,String rqid) throws CustomException, IOException {

        String bookcode = transaction.getBookcode();
        String paycode = transaction.getPaycode();

        User user = userRepository.findByRqid(rqid);
        if (user== null) {
            throw new CustomException(new CustomErrorResponse("01","RQID is not valid"));
        }

        Transaction availableTransaction = transactionRepository.findByBookcodeContainingIgnoreCaseAndPaycodeContainingIgnoreCase(
                bookcode, paycode);

        if(availableTransaction==null){
            throw new CustomException(new CustomErrorResponse("10","No Transaction Available"));
        }

        List<Transactiondet> avaTransactiondets = transactiondetRepository.findByTransactionid(availableTransaction.getId());
        if(avaTransactiondets==null || avaTransactiondets.isEmpty()){
            throw new CustomException(new CustomErrorResponse("10","No Transactiondet Available"));
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
                .append(String.format("/%s",orgstatsiuncode))
                .append(String.format("/%s",deststasindest))
                .append(String.format("/%s",departdateS)).toString();


        String result = restTemplate.getForObject(url,String.class);

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

        List<Pax> paxs = avaTransactiondets.stream().map(ava->{
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
        resultWrapper = new MessageWrapper<>("00","SUCCESS", bookingdata);

        return resultWrapper;
    }

}
