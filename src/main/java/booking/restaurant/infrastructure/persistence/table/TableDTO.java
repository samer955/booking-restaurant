package booking.restaurant.infrastructure.persistence.table;

import booking.restaurant.domain.model.Table;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

public class TableDTO {

    @Id
    private Long id;
    private int number;
    private int minCapacity;
    private int maxCapacity;

    @PersistenceConstructor
    public TableDTO(Long id, int number, int minCapacity, int maxCapacity) {
        this.id = id;
        this.number = number;
        this.minCapacity = minCapacity;
        this.maxCapacity = maxCapacity;
    }

    public TableDTO(Table table) {
        this.id = table.getId();
        this.number = table.getNumber();
        this.minCapacity = table.getMinCapacity();
        this.maxCapacity = table.getMaxCapacity();
    }

    public Table toTable() {
        return new Table(id, number, minCapacity, maxCapacity);
    }

}
