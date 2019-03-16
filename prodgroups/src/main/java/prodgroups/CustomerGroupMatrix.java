package prodgroups;

public class CustomerGroupMatrix {
    CustomerGroup group1;
    CustomerGroup group2;
    CustomerGroup intersection;

    public CustomerGroupMatrix(CustomerGroup group1, CustomerGroup group2) {
        this.group1 = group1;
        this.group2 = group2;
    }

    public CustomerGroup getGroup1() {
        return group1;
    }

    public void setGroup1(CustomerGroup group1) {
        this.group1 = group1;
    }

    public CustomerGroup getGroup2() {
        return group2;
    }

    public void setGroup2(CustomerGroup group2) {
        this.group2 = group2;
    }

    public CustomerGroup getIntersection() {
        return intersection;
    }

    public void setIntersection(CustomerGroup intersection) {
        this.intersection = intersection;
    }
}
