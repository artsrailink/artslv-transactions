package co.id.artslv.repository;

import co.id.artslv.lib.transactions.Transaction;
import co.id.artslv.lib.transactions.Transactiondet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by root on 26/09/16.
 */
public interface TransactionRepository extends JpaRepository<Transaction,String>{
    Transaction findByBookcodeContainingIgnoreCaseAndPaycodeContainingIgnoreCase(String bookcode, String paycode);
}
