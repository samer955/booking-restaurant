package booking.restaurant.domain.model;

public class Table {

    private Long id;
    private int number;
    private int minCapacity;
    private int maxCapacity;

    public Table(Long id, int number, int minCapacity, int maxCapacity) {
        this.id = id;
        this.number = number;
        this.minCapacity = minCapacity;
        this.maxCapacity = maxCapacity;
    }

    public Long getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public int getMinCapacity() {
        return minCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }


}
