package co.id.artslv.controller;

import co.id.artslv.lib.PageImplBean;
import co.id.artslv.lib.response.MessageWrapper;
import co.id.artslv.lib.transactions.Transactiondet;
import co.id.artslv.lib.utility.CustomErrorResponse;
import co.id.artslv.service.TransactiondetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by root on 04/10/16.
 */
@RestController
@RequestMapping(value = "/transaction")
public class TransactiondetController {

    @Autowired
    private TransactiondetService transactiondetService;

    @RequestMapping(value = "/getalltransactiondet")
    public ResponseEntity<?> getAllTransactiondet(@PageableDefault(page = 0, value = Integer.MAX_VALUE) Pageable page){
        try {
            MessageWrapper<PageImplBean<Transactiondet>> messageWrapperResult = transactiondetService.getAllTransactiondet(page);
            return new ResponseEntity<>(messageWrapperResult, HttpStatus.OK);
        } catch (Exception e) {
            CustomErrorResponse customErrorResponse = (CustomErrorResponse)e.getCause();
            MessageWrapper<Object> error = new MessageWrapper<>(customErrorResponse);
            return new ResponseEntity<>(error,HttpStatus.OK);
        }
    }
}
