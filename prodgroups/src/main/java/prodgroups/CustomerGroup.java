package prodgroups;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CustomerGroup
{
	String name;
	List<Customer> customers = new ArrayList<>();
	private List<String> cachedAllCustomerNames = null;

	public CustomerGroup(String groupName) {
		name = groupName;
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
}
