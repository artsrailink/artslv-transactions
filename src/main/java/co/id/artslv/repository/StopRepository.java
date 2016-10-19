package co.id.artslv.repository;

import co.id.artslv.lib.schedule.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by root on 11/10/16.
 */
public interface StopRepository extends JpaRepository<Stop,String>{
    List<Stop> findByStatsiuncode(String stasiuncode);
    Stop findByStatsiuncodeAndSchedulenoka(String stasiuncode, String noka);
}
