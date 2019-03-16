package prodgroups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class RuleProcessor
{
	GroupsService groupsService = new GroupsService();

	int minLength = 3;

	public void printProductGroupTable() {
		groupsService.printGroupsForDebugPurposes();
	}

	public void printGroupDetailsForDebugPurposes() {
		groupsService.printGroupDetailsForDebugPurposes();
	}
	public void printCustomerProductMatrix() {
		groupsService.printCustomerProductMatrixForDebugPurposes();
	}


	public void process(String customerStr, String productStr, String opStr) {
		try {
			Customer customer = new Customer(customerStr);
			Product product = new Product(productStr);
			Op op = opStr.equals("include") ? Op.INCLUDE : opStr.equals("exclude") ? Op.EXCLUDE : null;
			if (op == null) {
				throw new UndefinedOp();
			}
			process(customer, product, op);
		} catch (UndefinedOp e) {
			System.err.println(e.getMessage());
			System.err.println(e.getStackTrace());
		}
	}

	private void process(Customer customer, Product product, Op op) {
		log(customer, product, op);
		List<CustomerGroup> groups = groupsService.findGroups(product);
		if (groups == null || (groups != null && groups.size() == 0)) {
			CustomerGroup groupWithCustomers = groupsService.findGroupWithCustomers(Arrays.asList(customer));
			CustomerGroup group;
			if (groupWithCustomers == null) {
				 if (op.equals(Op.EXCLUDE)) {
					 // nothing
				 }
				 if (op.equals(Op.INCLUDE)) {
					 group = groupsService.createGroup();
					 groupsService.addCustomerToGroup(group, customer);
					 groupsService.associateProductWithGroup(product, group);
				 }
			} else
			{
				group = groupWithCustomers;
				if (op.equals(Op.EXCLUDE)) {
					  groupsService.removeCustomerFromGroup(group, customer);
				}
				if (op.equals(Op.INCLUDE)) {
					if (!group.getAllCustomerNames().contains(customer.getName())) {
						groupsService.addCustomerToGroup(group, customer);
					}
					groupsService.associateProductWithGroup(product, group);
				}
			}
		}
		else // groups is not null
		{
			if (groups.size() > 0) {
				CustomerGroup group = groups.get(0);
				if (op.equals(Op.EXCLUDE) && !group.getAllCustomerNames().contains(customer.getName())) { return; }
				//List<Product> products = groupsService.findAllProductsWithGroup(group);
				List<Customer> customers = group.getCustomers();
				groupsService.unlinkGroupFromProduct(group, product);
				//if (products.size() == 1 && products.get(0).getName().equals(product.getName())) {
				// cleanup - findAllProductsWithGroup ?
				if (op.equals(Op.INCLUDE)) {

					CustomerGroup checkNewGroup = new CustomerGroup("forCheck");
					checkNewGroup.getCustomers().addAll(customers);
					if (!group.getAllCustomerNames().contains(customer.getName())) {
						checkNewGroup.getCustomers().add(customer);
					}
					CustomerGroup foundGroup = groupsService.findGroup(checkNewGroup);
					if (foundGroup != null) {
						groupsService.associateProductWithGroup(product, foundGroup);
					} else {
						CustomerGroup newGroup = groupsService.createGroup();
						groupsService.addCustomersToGroup(newGroup, customers);
						groupsService.addCustomerToGroup(newGroup, customer);
						groupsService.associateProductWithGroup(product, newGroup);
					}

				}
			}
		}
	}

	private void log(Customer customer, Product product, Op op) {
		System.out.println("RULE>> "+customer.getName() + " <"+op.name()+"> "+product.getName());
	}

	public void parseAndProcess(String st) {
		if (st.indexOf("-!>") > 0) {
			String customerId = st.substring(0, st.indexOf("-!>"));
			String productId  = st.substring(st.indexOf("-!>") + 3, st.length());
			process(customerId, productId, "include");
		}
		if (st.indexOf("-x>") > 0) {
			String customerId = st.substring(0, st.indexOf("-x>"));
			String productId  = st.substring(st.indexOf("-x>") + 3, st.length());
			process(customerId, productId, "exclude");
		}
		if (st.indexOf("=") > 0 && st.indexOf(">") >= 0 && st.indexOf(">") < st.indexOf("=")) {
			String var = st.substring(0, st.indexOf("="));
			String value  = st.substring(st.indexOf("=") + 1, st.length());
			if (var.toLowerCase().equals(">minlength")) {
				minLength = Integer.parseInt(value);
			}
		}

		if (st.indexOf(">printGroups") >= 0) {
			printProductGroupTable();
		}
		if (st.indexOf(">printGroupDetails") >= 0) {
			printGroupDetailsForDebugPurposes();
		}
		if (st.indexOf(">printCustomerMatrix") >= 0) {
			printCustomerProductMatrix();
		}
		if (st.indexOf(">optimize") >= 0) {
			GroupProcessor groupProcessor = new GroupProcessor();
			groupProcessor.setGroups(groupsService.getGroups());
			groupProcessor.setMinLength(minLength);
			groupProcessor.optimize();
			groupProcessor.printGroupsForDebug();
		}

	}
}