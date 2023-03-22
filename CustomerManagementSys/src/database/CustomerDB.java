package database;

import java.util.*;

import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDB {

	Connection conn = DBConnection.getConnection();

	public List<Customer> getAllCustomer() throws Exception {

		String sql = "Select * from customer;";

		List<Customer> customers = new ArrayList<>();

		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				Customer c1 = new Customer();
				c1.setEmail(rs.getString(1));
				c1.setCustname(rs.getString(2));
				c1.setCity(rs.getString(3));
				c1.setPhone(rs.getString(4));
				c1.setPassword(rs.getString(5));
				customers.add(c1);
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return customers;
	}

	public boolean insertCustomer(Customer customer) throws Exception {
		String sql = "insert into customer values(?,?,?,?,?);";
		System.out.println(sql);

		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, customer.getEmail());
			statement.setString(2, customer.getCustname());
			statement.setString(3, customer.getCity());
			statement.setString(4, customer.getPhone());
			statement.setString(5, customer.getPassword());
			statement.execute();

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return true;
	}

	public Customer getCustomerByEmail(String email) throws Exception {
		Customer c1 = null;
		String sql = "Select custName, city, phone from customer where email = ?;";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, email);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				c1 = new Customer();
				c1.setEmail(email);
				c1.setCustname(rs.getString(1));
				c1.setCity(rs.getString(2));
				c1.setPhone(rs.getString(3));
			} else {
				throw new Exception("No customer with " + email + " found");
			}
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}

		return c1;

	}

	public boolean updateCustomer(Customer customer) throws Exception {
		String sql = "update customer set city=?, phone =?, custName=? where email=?";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, customer.getCity());
			statement.setString(2, customer.getPhone());
			statement.setString(3, customer.getCustname());
			statement.setString(4, customer.getEmail());
			statement.execute();
		} catch (SQLException ex) {
			throw new Exception(ex.getMessage());
		}

		return true;
	}

	public boolean updatePassword(String newPassword, String email) throws Exception {
		String sql = "update customer set password=? where email=?";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, newPassword);
			statement.setString(2, email);
			statement.execute();
		} catch (SQLException ex) {
			throw new Exception(ex.getMessage());
		}

		return true;
	}

	public boolean deleteCustomer(String email) throws Exception {
		String sql = "delete from customer where email=?";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, email);
			statement.execute();
		} catch (SQLException ex) {
			throw new Exception(ex.getMessage());
		}
		return true;
	}

	public boolean login(String email, String password) throws Exception {
		Customer customer = null;
		String sql = "select password from customer where email=?";
		// select password from customer where email='sh@g.c'
		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, email);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				if (password.equals(rs.getString(1)))
					return true;
			}
			throw new Exception("Invalid credentials");
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

}
