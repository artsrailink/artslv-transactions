package co.id.artslv.controller;

import co.id.artslv.lib.responses.MessageWrapper;
import co.id.artslv.lib.transactions.Bookingdata;
import co.id.artslv.lib.transactions.Transaction;
import co.id.artslv.lib.utility.CustomErrorResponse;
import co.id.artslv.lib.utility.CustomException;
import co.id.artslv.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by gemuruhgeo on 06/09/16.
 */
@RestController
@RequestMapping(value = "/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/details",method = RequestMethod.POST)
    public ResponseEntity<?> getTransactionDetails(@RequestBody Transaction transaction){
        try {
            MessageWrapper<Object> resultWrapper = transactionService.getTransactionDetails(transaction);
            return new ResponseEntity<>(resultWrapper,HttpStatus.OK);
        } catch (CustomException e) {
            CustomErrorResponse customErrorResponse = (CustomErrorResponse)e.getCause();
            MessageWrapper<?> transactionError = new MessageWrapper<>(customErrorResponse);
            return new ResponseEntity<Object>(transactionError,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/bookinfo/{rqid}",method = RequestMethod.POST)
    public ResponseEntity<?> getBookInfo(@RequestBody Transaction transaction, @PathVariable String rqid) throws IOException {
        try {
            MessageWrapper<Object> resultWrapper = transactionService.getBookInfo(transaction, rqid);
            return new ResponseEntity<>(resultWrapper,HttpStatus.OK);
        } catch (CustomException e) {
            CustomErrorResponse customErrorResponse = (CustomErrorResponse)e.getCause();
            MessageWrapper<?> transactionError = new MessageWrapper<>(customErrorResponse);
            return new ResponseEntity<Object>(transactionError,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/booking",method = RequestMethod.POST)
    public ResponseEntity<?> setBooking(@RequestBody Bookingdata bookingdata){
        try {
            MessageWrapper<Bookingdata> resultWrapper = transactionService.setBooking(bookingdata);
            return new ResponseEntity<>(resultWrapper,HttpStatus.OK);
        } catch (CustomException e) {
            CustomErrorResponse customErrorResponse = (CustomErrorResponse)e.getCause();
            MessageWrapper<?> transactionError = new MessageWrapper<>(customErrorResponse);
            return new ResponseEntity<Object>(transactionError,HttpStatus.OK);
        }
    }
}