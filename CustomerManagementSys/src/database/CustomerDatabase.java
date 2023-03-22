package database;

import java.util.ArrayList;
import java.util.List;

import model.Customer;

public class CustomerDatabase {
	private List<Customer> customers = new ArrayList<>();

	public CustomerDatabase() {
		customers.add(new Customer("a@a.com", "John", "Goa", "1244353222", "abc"));
		customers.add(new Customer("b@a.com", "Lisa", "Banglore", "6362666377", "mks"));
	}

	public List<Customer> getAllCustomers() {
		return this.customers;
	}

	public boolean insertCustomer(Customer customer) throws Exception {
		for (Customer cust : customers) {
			if (cust.getEmail().equals(customer.getEmail()))
				throw new Exception("Customer cannot be registered as email already exists");
		}

		customers.add(customer);
		return true;
	}

	public Customer getCustomerByEmail(String email) {
		Customer customer = null;

		for (Customer cust : customers) {
			if (cust.getEmail().equals(email)) {
				customer = cust;
				break;
			}
		}
		return customer;
	}

	public boolean login(String email, String password) {
		for (Customer c : customers) {
			if (c.getEmail().equals(email)) {
				if (c.getPassword().equals(password))
					return true;
			}
		}
		return false;
	}

}
