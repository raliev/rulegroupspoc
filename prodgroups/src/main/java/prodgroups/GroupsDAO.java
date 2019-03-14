package prodgroups;

import java.util.*;
import java.util.stream.Collectors;

public class GroupsDAO {

    Map<String, List<CustomerGroup>> productToGroup = new HashMap<>();
    Set<CustomerGroup> allGroups = new HashSet<>();
    Map<String, List<String>> productsForGroupForCleanupPurposes = new HashMap<>();
    int maxProductNameLength = 0;
    int groupId = 0; // counter for the group names

    List<CustomerGroup>  findAllGroupsForProduct (Product product) {
        return productToGroup.get(product.getName());
    }

    public void associateProductWithGroup(Product product, CustomerGroup group) {
        List<CustomerGroup> customerGroups = productToGroup.get(product.getName());
        if (customerGroups == null) { customerGroups = new ArrayList<>(); }
        if (!isPresent(group, customerGroups)) {
            customerGroups.add(group);
            allGroups.add(group);
        }

        adjustMaxProductNameLength(product);
        addProductsForGroupForCleaningUpPurposes(product, group);
        productToGroup.put(product.getName(), customerGroups);
    }

    public void unlinkGroupFromProduct(Product product, CustomerGroup group) {
        List<CustomerGroup> customerGroups = productToGroup.get(product.getName());
        customerGroups.remove(group);
        removeFromProductsForGroupForCleaningUpPurposes(product, group);
        productToGroup.put(product.getName(), customerGroups);
    }

    private void addProductsForGroupForCleaningUpPurposes(Product product, CustomerGroup group) {
        List g = productsForGroupForCleanupPurposes.get(group.getName());
        if (g == null) {g = new ArrayList<>(); }
        g.add(product.getName());
        productsForGroupForCleanupPurposes.put(group.getName(), g);
    }

    private void removeFromProductsForGroupForCleaningUpPurposes(Product product, CustomerGroup group) {
        List pr = productsForGroupForCleanupPurposes.get(group.getName());
        if (pr != null) {
            pr.remove(product.getName());
            if (pr.size() == 0) {
                // no products for a group. Clean up a group
                Iterator<CustomerGroup> iter = allGroups.iterator();
                while (iter.hasNext()) {
                    CustomerGroup c = iter.next();
                    if (c.getName().equals(group.getName())) {
                        allGroups.remove(c);
                        return;
                    }
                }
            }
        }
    }

    private void adjustMaxProductNameLength(Product product) {
        int len = product.getName().length();
        if (len > maxProductNameLength) { maxProductNameLength = len; }
    }

    private boolean isPresent(CustomerGroup group, List<CustomerGroup> customerGroups) {
        Iterator<CustomerGroup> iter = customerGroups.iterator();
        while (iter.hasNext()) {
            CustomerGroup fgroup = iter.next();
            if (fgroup.getName().equals(group)) { return true; }
        }
        return false;
    }

    public String getNextGroupId() {
        groupId ++;
        return "gr"+String.format("%05d", groupId);
    }

    public void printGroupDetailsForDebugPurposes() {
        System.out.println("\n========= Groups and their customers  =====");
        List<CustomerGroup> sortedListOfGroups = new ArrayList<>(allGroups);
        Collections.sort(sortedListOfGroups, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        Iterator<CustomerGroup> iterCustomerGroups = sortedListOfGroups.iterator();
            while (iterCustomerGroups.hasNext()) {
            CustomerGroup key = iterCustomerGroups.next();
            System.out.printf("* %s: %s\n", key.getName(), toCommaSep(key.getAllCustomerNames()));
        }
        System.out.println();

    }

    private Object toCommaSep(List<String> allCustomerNames) {
        return allCustomerNames.stream()
                .collect( Collectors.joining( "," ) );
    }

    public void printGroupsForDebugPurposes() {
        System.out.println("\n========= Product x Groups =====");
        List<CustomerGroup> sortedListOfGroups = new ArrayList<>(allGroups);
        Collections.sort(sortedListOfGroups, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        Iterator<CustomerGroup> iterCustomerGroups = sortedListOfGroups.iterator();
        System.out.printf(String.format("%"+maxProductNameLength+"s", " "));
        while (iterCustomerGroups.hasNext()) {
            CustomerGroup key = iterCustomerGroups.next();
            System.out.printf("|%s", key.getName());
        }
        System.out.println();

        Set<String> products = productToGroup.keySet();
        List<String> sortedListOfProducts = new ArrayList<>(products);
        Collections.sort(sortedListOfProducts, (o1, o2) -> o1.compareTo(o2));
        Iterator < String > iterProducts = sortedListOfProducts.iterator();

        while (iterProducts.hasNext()) {
            String product = iterProducts.next();
            System.out.printf(String.format("%"+maxProductNameLength+"s", product));
            Iterator<CustomerGroup> iterProductGroups = sortedListOfGroups.iterator();
            while (iterProductGroups.hasNext()) {
                CustomerGroup key = iterProductGroups.next();
                System.out.printf("|%"+key.getName().length()+"s", productToGroup.get(product).contains(key) ? "true" :  "");
            }
            System.out.println();
        }
        System.out.println();
    }

    public CustomerGroup findGroupWithCustomers(List<Customer> customers) {
        Iterator<CustomerGroup> groupsIterator = allGroups.iterator();
        int cnt;
        while (groupsIterator.hasNext()) {
            Iterator<Customer> customersIterator = customers.iterator();
            CustomerGroup nextGroup = groupsIterator.next();
            cnt = 0;
            while (customersIterator.hasNext()) {
                Customer customerNext = customersIterator.next();
                if (nextGroup.getAllCustomerNames().contains(customerNext.getName())) { cnt ++; }
            }
            if (cnt == nextGroup.getCustomers().size()) { return nextGroup; }
        }
        return null;
    }


    public List<Product> findAllProductsWithGroup(CustomerGroup group) {
        Iterator<String> iter = productToGroup.keySet().iterator();
        List<Product> res = new ArrayList<>();
        while (iter.hasNext()) {
            String productKey = iter.next();
            List <CustomerGroup> listGroups = productToGroup.get(productKey);
            Iterator<CustomerGroup> iterCust = listGroups.iterator();
            while (iterCust.hasNext()) {
                CustomerGroup cg = iterCust.next();
                if (cg.getName().equals(group.getName())) {
                    res.add(getProductForProductKey(productKey));
                }
            }
        }
        return res;
    }

    private Product getProductForProductKey(String productKey) {
        // quick and dirty  - TODO: use ProductKey-ProductObj map
        return new Product(productKey);
    }

    public void printCustomerProductMatrixForDebugPurposes() {
        System.out.println("\n========= Product x Customers =====");
        List<String> listOfCustomers = new ArrayList<>();
        Iterator<CustomerGroup> iter = allGroups.iterator();
        int maxLen = 0;
        while (iter.hasNext()) {
            CustomerGroup c = iter.next();
            for (String cn : c.getAllCustomerNames()) {
                if (cn.length() > maxLen) { maxLen = cn.length(); }
                listOfCustomers.add(cn);
            }
        }
        Collections.sort(listOfCustomers, (Comparator<String>) (o1, o2) -> o1.compareTo(o2));
        HashSet<String> sortedListOfCustomers = new HashSet<>(listOfCustomers);
        System.out.printf(String.format("%"+maxProductNameLength+"s", " "));
        for (String cn : sortedListOfCustomers) {
            System.out.printf("|%"+maxLen+"s", cn);
        }
        System.out.println();
        Set<String> products = productToGroup.keySet();
        List<String> sortedListOfProducts = new ArrayList<>(products);
        Collections.sort(sortedListOfProducts, (o1, o2) -> o1.compareTo(o2));
        Iterator < String > iterProducts = sortedListOfProducts.iterator();

        while (iterProducts.hasNext()) {
            String product = iterProducts.next();
            System.out.printf(String.format("%"+maxProductNameLength+"s", product));
            HashMap<String, Boolean> access = new HashMap<>();
                List<CustomerGroup> customerGroups = productToGroup.get(product);
                for (CustomerGroup cg : customerGroups) {
                    List<String> names = cg.getAllCustomerNames();
                    for (String name : names) {
                        access.put(name, true);
                    }
                }
                for (String cust : sortedListOfCustomers) {
                    System.out.printf("|%" + maxLen + "s", access.get(cust) != null ? "true" : "");
                }

            System.out.println();
        }
        System.out.println();
    }

    public CustomerGroup findGroup(CustomerGroup group) {
        if (group == null) { return null; }
        if (group.getCustomers() == null) { return null; }
        List<String> customer = group.getAllCustomerNames();
        Collections.sort(customer, (o1, o2) -> o1.toString().compareTo(o2.toString()));
        int cnt = 0;
        for (CustomerGroup g : allGroups) {
            CustomerGroup savedGroup = new CustomerGroup("");
            for (String c : g.getAllCustomerNames()) {
                if (group.getAllCustomerNames().contains(c))
                {
                    cnt++;
                    savedGroup = g;
                }
            }
            if (cnt == group.getCustomers().size()) { return savedGroup; }
        }
        return null;
    }
}
