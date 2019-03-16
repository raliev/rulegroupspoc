package prodgroups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class GroupsService
{
	 GroupsDAO groupsDAO = new GroupsDAO();

	 public CustomerGroup removeCustomerFromGroup(CustomerGroup group, Customer customer) {
	 	List<Customer> customers = group.getCustomers();
	 	int i = customers.indexOf(customer);
		if (i != -1) {
			customers.remove(i);
		}
		return group;
	 }
	public CustomerGroup addCustomerToGroup(CustomerGroup group, Customer customer) {
		return addCustomersToGroup(group, Arrays.asList(customer));
	 }
	public CustomerGroup addCustomersToGroup(CustomerGroup group, List<Customer> customers) {
		List<Customer> groupCustomers = group.getCustomers();
		if (groupCustomers == null) { groupCustomers = new ArrayList<>(); }
		if (customers == null) { customers = new ArrayList<>(); }
		Iterator<Customer> iter = customers.iterator();
		while (iter.hasNext()) {
			Customer c = iter.next();
			if (!group.getAllCustomerNames().contains(c.getName())) {
				groupCustomers.add(c);
			}
		}
		group.setCustomers(groupCustomers);
		return group;
	}
	public List<CustomerGroup> findGroups(Product product) {
			List<CustomerGroup> groups = groupsDAO.findAllGroupsForProduct(product);
			return groups;
	 }

	public void associateProductWithGroup(Product product, CustomerGroup group) {
	 	groupsDAO.associateProductWithGroup(product, group);
	}
	public void unlinkGroupFromProduct(CustomerGroup group, Product product) {
	 	groupsDAO.unlinkGroupFromProduct(product, group);
	}
	public List <CustomerGroup> getGroups () {
	 	return groupsDAO.getGroups();
	}
	public CustomerGroup createGroup() {
	 	String groupName = groupsDAO.getNextGroupId();
	 	CustomerGroup group = new CustomerGroup(groupName);
	 	return group;
	}

	public void printGroupsForDebugPurposes() {
	 	groupsDAO.printGroupsForDebugPurposes();
	}

	public CustomerGroup findGroupWithCustomers(List<Customer> customers) {
	 	return groupsDAO.findGroupWithCustomers(customers);
	}

	public void printGroupDetailsForDebugPurposes() {
	 	groupsDAO.printGroupDetailsForDebugPurposes();
	}


	public List<Product> findAllProductsWithGroup(CustomerGroup group) {
	 	return groupsDAO.findAllProductsWithGroup(group);
	}

	public void printCustomerProductMatrixForDebugPurposes() {
		groupsDAO.printCustomerProductMatrixForDebugPurposes();
	}

	public CustomerGroup findGroup(CustomerGroup newGroup) {
	 	return groupsDAO.findGroup(newGroup);
	}
	public CustomerGroup findGroup(CustomerGroup newGroup, List<CustomerGroup> customerGroups, String except, boolean exactMatch) {
		return groupsDAO.findGroup(newGroup, customerGroups, except, exactMatch);
	}


	public CustomerGroup subtractCustomerGroupFromCustomers(CustomerGroup group, CustomerGroup beingSubtracted) {
	 	CustomerGroup result = group;
	 	List<Customer> customers = new ArrayList<>();
	 	for (Customer g : group.getCustomers()) {
	 		if (!beingSubtracted.getAllCustomerNames().contains(g.getName())) {
	 			customers.add(g);
			}
		}
	 	result.setCustomers(customers);
	 	//result.setSubgroups(group.getSubgroups());
	 	return result;
	}

	public void addSubgroup(CustomerGroup group, CustomerGroup subgroup) {
	 	group.addSubgroup(subgroup);
	}
}
