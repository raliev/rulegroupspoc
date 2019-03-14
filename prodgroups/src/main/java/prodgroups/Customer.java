package prodgroups;

public class Customer {
    String name;

    public Customer(String customerStr) {
        name = customerStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
