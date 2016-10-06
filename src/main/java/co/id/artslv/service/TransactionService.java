package co.id.artslv.service;

import co.id.artslv.lib.response.MessageWrapper;
import co.id.artslv.lib.transactions.Transaction;
import co.id.artslv.lib.transactions.Transactiondet;
import co.id.artslv.lib.utility.CustomErrorResponse;
import co.id.artslv.lib.utility.CustomException;
import co.id.artslv.repository.TransactionRepository;
import co.id.artslv.repository.TransactiondetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by root on 26/09/16.
 */
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactiondetRepository transactiondetRepository;

    @Transactional(rollbackFor = CustomException.class)
    public MessageWrapper<List<Transaction>> getAllTransaction() throws CustomException {
        List<Transaction> transactions = transactionRepository.findAll();
        if(transactions==null){
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
        MessageWrapper<Object> resultWrapper = new MessageWrapper<>("00","SUCCESS",availableTransaction,avaTransactiondets);
        return resultWrapper;
    }

}
