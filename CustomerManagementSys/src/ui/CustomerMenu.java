package ui;

import java.util.Scanner;
import database.CustomerDB;
import database.DBConnection;
import model.Customer;
import service.CustomerService;

public class CustomerMenu {

	public static void adminMenu() {
		System.out.println(
				"Select operations \n" + "1. See all customers \n" + "2. Fetch customer by email \n" + "3. LogOff \n");
	}

	public static void customerMenu() {
		System.out.println("Select Operations \n" + "1. See profile \n" + "2. Change password \n" + "3. Edit profile \n"
				+ "4. Delete customer \n" + "5. LogOff \n");
	}

	public static void main(String[] args) {
		DBConnection.getConnection();
		CustomerDB custDB = new CustomerDB();
		CustomerService custserv = new CustomerService(custDB);

		Scanner input = new Scanner(System.in);
		String email, password, city, phone, name;
		int choice;
		boolean flag = true;
		do {
			System.out.println("Select Operation \n" + "1. Login \n" + "2. Register Customer \n" + "3. Exit \n");
			choice = input.nextInt();
			input.nextLine();	
			switch (choice) {
			case 1:
				System.out.println("Please Enter email");
				email = input.next();
				System.out.println("Please Enter password");
				password = input.next();
				if (email.equals("admin@a.com") && password.equals("admin")) {
					boolean flag1 = true;
					do {
						System.out.println("Admin DashBoard");
						adminMenu();
						choice = input.nextInt();
						switch (choice) {
						case 1:
							try {
								for (Customer cust : custserv.getAllCustomer()) {
									System.out.println(cust);
								}
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
							break;
						case 2:
							System.out.println("Enter an email ID");
							String emailID = input.next();
							try {
								Customer cust = custserv.getCustomerByEmail(emailID);
								System.out.println(cust);
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
							break;
						case 3:
							System.out.println("Thank you !");
							flag1 = false;
							break;
						default:
							System.out.println("Select valid option");
							break;
						}
					} while (flag1);
				} else {
					try {
						if (custserv.validateCredentials(email, password)) {
							boolean flag2 = true;
							System.out.println("Welcome");
							do {
							customerMenu();
							choice = input.nextInt();
							switch (choice) {
							case 1:
								try {
									Customer cust = custserv.getCustomerByEmail(email);
									System.out.println(cust);
								} catch (Exception e) {
									System.out.println(e.getMessage());
								}
								break;
							case 2:
								System.out.println("Enter new password :");
								String currPwd = input.next();
								try {
									custserv.changePassword(email, currPwd);
									System.out.println("Password changed successfully !");
								}catch(Exception e) {
									System.out.println(e.getMessage());
								}
								break;
							case 3:
								System.out.println("Edit Profile \n");
								System.out.println("Select a field which you want to edit:\n" + "1. name \n"
										 + "2. phone \n" + "3. city \n");

								choice = input.nextInt();
								switch (choice) {
								case 1:
								
									System.out.println("Please enter new name :");
									String newName = input.next();
									try {
										Customer cust = custserv.getCustomerByEmail(email);
										cust.setCustname(newName);
									} catch (Exception e) {
										System.out.println(e.getMessage());
									}
									break;
								case 2:
									System.out.println("Please enter new phone no:");
									String newPhone = input.next();
									try {
										Customer cust = custserv.getCustomerByEmail(email);
										cust.setPhone(newPhone);
									} catch (Exception e) {
										System.out.println(e.getMessage());
									}
									break;
								case 3:
									System.out.println("Please enter new city");
									String newCity = input.next();
									try {
										Customer cust = custserv.getCustomerByEmail(email);
										cust.setCity(newCity);
									} catch (Exception e) {
										System.out.println(e.getMessage());
									}
									break;
								default:
									System.out.println("please select valid option");
									break;
								}
								break;
							case 4:
								System.out.println("Delete Customer");
								System.out.println("Are you sure you want to delete your acount ?");
								System.out.println("Enter Yes/yes to delete account or cancel");
								String shouldDelete = input.next();
								if(shouldDelete.equalsIgnoreCase("Yes")) {
									try {
										custserv.deleteCustomer(email);
										flag2 = false;
									}catch(Exception e) {
										System.out.println(e.getMessage());
									}
								}
								break;
							case 5:
								System.out.println("Thank you.");
								flag2 = false;
								break;
							default:
								System.out.println("Please select valid option");
								break;
							}
						} while (flag2);
						}

					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
				break;

			case 2:
				System.out.println("Please enter name");
				String newName = input.next();
				System.out.println("Please enter city");
				String newCity = input.next();
				System.out.println("Please enter email");
				String newEmail = input.next();
				System.out.println("Please enter password");
				String newPass = input.next();
				System.out.println("Please enter phone");
				String newPhone = input.next();
				Customer newCust = new Customer(newEmail, newName, newCity, newPhone, newPass);
				try {
					custserv.registerCustomer(newCust);
					System.out.println("Customer registered successfully :");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case 3:
				System.out.println("Thank you.");
				flag = false;
				break;
			default:
				System.out.println("Please select correct option");
				break;
			}
		} while (flag);
	}
}
