package prodgroups;

public class Product {
    String name;

    public Product(String productStr) {
        name = productStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
