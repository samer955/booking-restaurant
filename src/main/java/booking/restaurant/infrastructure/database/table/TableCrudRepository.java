package booking.restaurant.infrastructure.database.table;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableCrudRepository extends CrudRepository<TableDTO, Long> {

    List<TableDTO> findAllByMinCapacityIsOrMaxCapacityIs(int min, int max);
}
