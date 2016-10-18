package co.id.artslv.service;

import co.id.artslv.lib.PageImplBean;
import co.id.artslv.lib.responses.MessageWrapper;
import co.id.artslv.lib.transactions.Transaction;
import co.id.artslv.lib.transactions.Transactiondet;
import co.id.artslv.lib.utility.CustomErrorResponse;
import co.id.artslv.lib.utility.CustomException;
import co.id.artslv.repository.TransactiondetRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * Created by root on 04/10/16.
 */
@Service
public class TransactiondetService {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransactiondetRepository transactiondetRepository;

    @Transactional(rollbackFor = {CustomException.class,IOException.class})
    public MessageWrapper<PageImplBean<Transactiondet>> getAllTransactiondet(Pageable pageable) throws IOException, CustomException {
        Page<Transactiondet> pageTransactiondet = transactiondetRepository.findAll(pageable);
        String pageStringResult = objectMapper.writeValueAsString(pageTransactiondet);
        PageImplBean<Transactiondet> pageImplBeanTransdet = objectMapper.readValue(pageStringResult, new TypeReference<PageImplBean<Transaction>>() {});
        
        if(pageImplBeanTransdet==null || pageImplBeanTransdet.getContent().isEmpty()){
            throw new CustomException(new CustomErrorResponse("10","Cannot Find Transaction detail"));
        }
        return new MessageWrapper<>("00","SUCCESS",pageImplBeanTransdet);
    }
}
