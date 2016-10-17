package co.id.artslv.repository;

import co.id.artslv.lib.transactions.Transactiondet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by root on 03/10/16.
 */
public interface TransactiondetRepository extends JpaRepository<Transactiondet,String>{
    List<Transactiondet> findByTransactionid(String transactionid);
    Page<Transactiondet> findAll(Pageable pageable);
}
