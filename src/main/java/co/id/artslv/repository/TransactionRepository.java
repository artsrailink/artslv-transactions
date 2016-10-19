package co.id.artslv.repository;

import co.id.artslv.lib.transactions.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by root on 26/09/16.
 */
public interface TransactionRepository extends JpaRepository<Transaction,String>{
    Transaction findByBookcodeIgnoreCase(String bookcode);
    Transaction findByPaycodeIgnoreCase(String paycode);
    Transaction findByBookcodeIgnoreCaseAndPaycodeIgnoreCase(String bookcode, String paycode);

    @Query(value = "select arts_f_createbookcode() ", nativeQuery = true)
    String getBookCode();

}
