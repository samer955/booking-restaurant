package booking.restaurant.service.repository;

import booking.restaurant.domain.model.Table;

import java.util.List;

public interface TableRepository {

    List<Table> findAllByMinOrMaxCapacity(int min, int max);

}
