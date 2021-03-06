package prodgroups;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CustomerGroup
{
	String name;
	List<Customer> customers = new ArrayList<>();
	List<CustomerGroup> subgroups = new ArrayList<>();
	private List<String> cachedAllCustomerNames = null;

	public CustomerGroup(String groupName) {
		name = groupName;
	}
	public CustomerGroup(String groupName, List<Customer> customers) {
		name = groupName;
		this.customers = customers;
	}
	public CustomerGroup(String groupName, List<String> customers, boolean t) {
		name = groupName;
		this.customers = new ArrayList<>();
		for (String customer: customers)
			this.customers.add(new Customer(customer));
	}
	public CustomerGroup(String groupName, List<Integer> customers, String t) {
		name = groupName;
		this.customers = new ArrayList<>();
		for (Integer customer: customers)
			this.customers.add(new Customer(customer.toString()));
	}

	public List<Customer> getCustomers()
	{
		return customers;
	}

	public void setCustomers(List<Customer> customers)
	{
		this.customers = customers;
		cachedAllCustomerNames = null;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<String> getAllCustomerNames() {
		if (cachedAllCustomerNames != null) { return cachedAllCustomerNames; }
		cachedAllCustomerNames = new ArrayList<>();
		if (customers == null) { return cachedAllCustomerNames; }
		Iterator<Customer> customerIterator = customers.iterator();
		while (customerIterator.hasNext()) {
			Customer cust = customerIterator.next();
			cachedAllCustomerNames.add(cust.getName());
		}
		return cachedAllCustomerNames;
	}

   public void addSubgroup(CustomerGroup subgroup) {
		subgroups.add(subgroup);
   }
	public void addSubgroups(List<CustomerGroup> subgroups) {
		subgroups.addAll(subgroups);
	}

	public List<CustomerGroup> getSubgroups() {
		return subgroups;
	}

	public void setSubgroups(List<CustomerGroup> subgroups) {
		this.subgroups = subgroups;
	}
}
