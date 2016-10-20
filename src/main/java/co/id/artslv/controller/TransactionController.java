package co.id.artslv.controller;

import co.id.artslv.lib.inventory.Inventory;
import co.id.artslv.lib.responses.MessageWrapper;
import co.id.artslv.lib.schedule.PropertySchedule;
import co.id.artslv.lib.transactions.Bookingdata;
import co.id.artslv.lib.transactions.Transaction;
import co.id.artslv.lib.users.User;
import co.id.artslv.lib.utility.CustomErrorResponse;
import co.id.artslv.lib.utility.CustomException;
import co.id.artslv.repository.UserRepository;
import co.id.artslv.service.DefaultSeatService;
import co.id.artslv.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by gemuruhgeo on 06/09/16.
 */
@RestController
@RequestMapping(value = "/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private DefaultSeatService defaultseatService;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/arts_details", method = RequestMethod.POST)
    public ResponseEntity<?> getTransactionDetails(@RequestBody Transaction transaction) {
        try {
            MessageWrapper<Object> resultWrapper = transactionService.getTransactionDetails(transaction);
            return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
        } catch (CustomException e) {
            CustomErrorResponse customErrorResponse = (CustomErrorResponse) e.getCause();
            MessageWrapper<?> transactionError = new MessageWrapper<>(customErrorResponse);
            return new ResponseEntity<Object>(transactionError, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/arts_getbookinfo/{rqid}/{paramcode}", method = RequestMethod.GET)
    public ResponseEntity<?> getBookInfo(@PathVariable String rqid, @PathVariable String paramcode) throws IOException {
        try {
            MessageWrapper<Object> resultWrapper = transactionService.getBookInfo(rqid, paramcode);
            return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
        } catch (CustomException e) {
            CustomErrorResponse customErrorResponse = (CustomErrorResponse) e.getCause();
            MessageWrapper<?> transactionError = new MessageWrapper<>(customErrorResponse);
            return new ResponseEntity<Object>(transactionError, HttpStatus.OK);
        }
    }

    //Booking Tanpa setschedule
    @RequestMapping(value = "/arts_booking/{rqid}", method = RequestMethod.POST)
    public ResponseEntity<?> setBooking(@RequestBody Bookingdata bookingdata, @PathVariable String rqid) {
        try {
            MessageWrapper<Bookingdata> resultWrapper = transactionService.setBooking(bookingdata, rqid);
            return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
        } catch (CustomException e) {
            CustomErrorResponse customErrorResponse = (CustomErrorResponse) e.getCause();
            MessageWrapper<?> transactionError = new MessageWrapper<>(customErrorResponse);
            return new ResponseEntity<Object>(transactionError, HttpStatus.OK);
        }
    }

    //Booking dengan setschedule
    @RequestMapping(value = "/arts_bookingv2/{rqid}", method = RequestMethod.POST)
    public ResponseEntity<?> setBookingv2(@RequestBody Bookingdata bookingdata, @PathVariable String rqid) {
        try {
            MessageWrapper<Bookingdata> resultWrapper = transactionService.setBookingv2(bookingdata, rqid);
            return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
        } catch (CustomException e) {
            CustomErrorResponse customErrorResponse = (CustomErrorResponse) e.getCause();
            MessageWrapper<?> transactionError = new MessageWrapper<>(customErrorResponse);
            return new ResponseEntity<Object>(transactionError, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/arts_setschedule/{rqid}", method = RequestMethod.POST)
    public ResponseEntity<?> setschedule(@RequestBody Bookingdata bookingdata, @PathVariable String rqid) throws CustomException {
        MessageWrapper<List<Inventory>> resultWrapper;
        User user = userRepository.findByRqid(rqid);
        if (user == null) {
            resultWrapper = new MessageWrapper<>("01", "RQID is not valid");
        } else {
            List<Inventory> inventorylist = defaultseatService.getDefaultSeat(bookingdata.getDepartdate(), bookingdata.getOrg(), bookingdata.getDest(), bookingdata.getNoka(), bookingdata.getTotpsgadult() + bookingdata.getTotpsgchild() + bookingdata.getTotpsginfant());
            if (inventorylist == null || inventorylist.isEmpty()) {
                resultWrapper = new MessageWrapper<>("10", "Seat Not Available");
            } else {
                resultWrapper = new MessageWrapper<>("00", "SUCCESS", inventorylist);
            }
        }
        return new ResponseEntity<>(resultWrapper, HttpStatus.OK);
    }
}
