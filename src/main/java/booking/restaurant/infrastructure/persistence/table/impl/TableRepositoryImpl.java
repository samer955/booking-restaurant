package booking.restaurant.infrastructure.persistence.table.impl;

import booking.restaurant.domain.model.Table;
import booking.restaurant.infrastructure.persistence.table.TableCrudRepository;
import booking.restaurant.infrastructure.persistence.table.TableDTO;
import booking.restaurant.service.repository.TableRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TableRepositoryImpl implements TableRepository {

    private final TableCrudRepository tableCrudRepository;

    public TableRepositoryImpl(TableCrudRepository tableCrudRepository) {
        this.tableCrudRepository = tableCrudRepository;
    }

    @Override
    public List<Table> findAllByMinOrMaxCapacity(int min, int max) {
        List<TableDTO> tables = tableCrudRepository.findAllByMinCapacityIsOrMaxCapacityIs(min, max);
        return tables.stream().map(TableDTO::toTable).collect(Collectors.toList());
    }
}
