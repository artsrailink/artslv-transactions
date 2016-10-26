package co.id.artslv.repository;

import co.id.artslv.lib.schedule.PropertySchedule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by root on 26/09/16.
 */
public interface PropertyScheduleRepository extends JpaRepository<PropertySchedule,String>{
    PropertySchedule findById(String id);
}
