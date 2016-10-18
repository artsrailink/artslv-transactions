package co.id.artslv.repository;

import co.id.artslv.lib.inventory.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by root on 26/09/16.
 */
public interface InventoryRepository extends JpaRepository<Inventory,String>{
}
