package co.id.artslv.repository;

import co.id.artslv.lib.transactions.Transaction;
import co.id.artslv.lib.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by root on 18/10/16.
 */
public interface UserRepository extends JpaRepository<User,String> {
    User findByRqid(String rqid);
}
