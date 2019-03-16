package prodgroups;

import java.util.*;
import java.util.stream.Collectors;

public class GroupProcessor {

    int minLength = 3;
    int minReplacement = 2;

    List<CustomerGroup> groups = new ArrayList<>();

    GroupsService groupsService = new GroupsService();

    public List<CustomerGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<CustomerGroup> groups) {
        this.groups = groups;
    }
    public int optimize() {
        int cnt = 0;
        while (optimize_step()) {
            cnt ++;
            printGroupsForDebug();
            System.out.println("a");
        }
        return cnt;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMinReplacement() {
        return minReplacement;
    }

    public void setMinReplacement(int minReplacement) {
        this.minReplacement = minReplacement;
    }

    public boolean optimize_step() {
         boolean result = false;
         List<CustomerGroupMatrix> pairs = new ArrayList<>();
         pairs = generatePairs(groups);
         calculateIntersections(pairs);

         Collections.sort(pairs, (o1, o2) -> {
             return
                o1.getIntersection().getCustomers().size() <
                o2.getIntersection().getCustomers().size() ? 1 :
                        o1.getIntersection().getCustomers().size() ==
                        o2.getIntersection().getCustomers().size() ?
                        0 : -1;
         });

        int maxEl = pairs.get(0).getIntersection().getCustomers().size();
        if (maxEl == 0) { return false; }
        Collections.sort(pairs, (o1, o2) -> o1.getGroup1().getCustomers().size() + o1.getGroup2().getCustomers().size() >
        o2.getGroup1().getCustomers().size() + o2.getGroup2().getCustomers().size() ? 1 : o1.getGroup1().getCustomers().size() + o1.getGroup2().getCustomers().size() ==
                o2.getGroup1().getCustomers().size() + o2.getGroup2().getCustomers().size() ? 0 : -1);

         CustomerGroupMatrix selectedPair = null;
         for (int i=0; i<pairs.size(); i++) {
            CustomerGroupMatrix pair = pairs.get(i);
            if (pair.getIntersection().getCustomers().size() == maxEl) {
                selectedPair = pair;
                break;
            }
         }
         if (selectedPair != null) {
             if (selectedPair.getGroup1().getCustomers().size() <= getMinLength()) { return false; }
             CustomerGroup foundGroup = groupsService.findGroup(selectedPair.getIntersection(), groups, selectedPair.getGroup1().getName(), true);
             System.out.println(">> "+groups.get(0).getAllCustomerNames());
             if (foundGroup == null) {
                 //adding a group
                 if (selectedPair.getIntersection().getCustomers().size() < getMinReplacement()) { return false; }
                 result = true;

                 CustomerGroup group = groupsService.createGroup();
                 if (group != null) {
                     group.setCustomers(selectedPair.getIntersection().getCustomers());
                     if (groups!=null) groups.add(group);

                     selectedPair.setGroup1(
                       groupsService.subtractCustomerGroupFromCustomers(
                             groupsService.findGroup(selectedPair.getGroup1(), groups, "", false),
                             selectedPair.getIntersection() )
                     );
                 }
                 groupsService.addSubgroup(selectedPair.getGroup1(), group);
             } else {
                 if (foundGroup.getCustomers().size() <= 1) { return false; }
                 result = true;
                 selectedPair.setGroup1(
                                 groupsService.subtractCustomerGroupFromCustomers(
                                         groupsService.findGroup(selectedPair.getGroup1(), groups, "", false),
                                                                                  selectedPair.getIntersection())

                                 );
                 groupsService.addSubgroup(selectedPair.getGroup1(), foundGroup);
             }
         }
         return result;
    }

    private void calculateIntersections(List<CustomerGroupMatrix> pairs) {
        for (CustomerGroupMatrix pair : pairs) {
            calculateIntersection(pair);

        }
    }

    private List<Customer> calculateIntersection(CustomerGroupMatrix pair) {
        List<String> result = new ArrayList<>();
        result.addAll(pair.getGroup1().getAllCustomerNames());
        result.retainAll(pair.getGroup2().getAllCustomerNames());
        List<Customer> intersection = new ArrayList<>();
        for (String el : result) {
            intersection.add(new Customer(el));
        }
        pair.setIntersection(new CustomerGroup("intersectionOf"+pair.getGroup1().getName()+" and "+pair.getGroup2().getName(), intersection));
        return intersection;
    }

    private List<CustomerGroupMatrix> generatePairs(List<CustomerGroup> groups) {
        List<CustomerGroupMatrix> pairs = new ArrayList<>();
        for (int i = 0; i < groups.size()-1; i++)
            for (int j = i+1; j < groups.size(); j++)
            {
                pairs.add(new CustomerGroupMatrix(groups.get(i), groups.get(j)));
            }
        return pairs;
    }

    public void printGroupsForDebug() {
        System.out.println("\n========= Groups and their customers  =====");
        List<CustomerGroup> sortedListOfGroups = new ArrayList<>(groups);
        Collections.sort(sortedListOfGroups, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        Iterator<CustomerGroup> iterCustomerGroups = sortedListOfGroups.iterator();
        while (iterCustomerGroups.hasNext()) {
            CustomerGroup key = iterCustomerGroups.next();
            String subGroups = CommonUtils.toCommaSep(key.getSubgroups().stream().map(CustomerGroup::getName).collect( Collectors.toList()));
            if (!subGroups.equals("")) { subGroups = "(" + subGroups + ")"; }
            System.out.printf("* %s: %s %s\n", key.getName(), CommonUtils.toCommaSep(key.getAllCustomerNames()), subGroups);
        }

        System.out.println();
    }
}
