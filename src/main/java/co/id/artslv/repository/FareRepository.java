package co.id.artslv.repository;

import co.id.artslv.lib.payments.Fare;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by root on 26/09/16.
 */
public interface FareRepository extends JpaRepository<Fare,String>{
}
