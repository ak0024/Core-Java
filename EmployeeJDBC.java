/**
 * 
 */
package com.mindgate.main;


import java.util.*;

import com.mindgate.domain.Employee;
import com.mindgate.service.EmployeeService;
import com.mindgate.service.EmployeeServiceInterafce;

/**
 * @author MGSCHN-ASHOK KUMAR
 *
 */
public class EmployeeJDBC {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EmployeeServiceInterafce employeeServiceInterafce = new EmployeeService();
		Employee employee;
		String choice;
		int employeeId;
		double sal;
		
		Scanner scanner = new Scanner(System.in);
		do {
			System.out.println("Menu\n1.Add new Employee\n2.Print one employee\n3.Print all employee"
					+ "\n4.Update salary of existing salary" + "\n5.Delete existing employee" + "\nEnter your choice::");
			int option = scanner.nextInt();
			switch (option) {
			case 1:
				System.out.println("Enter Employee Id::");
				employeeId = scanner.nextInt();
				System.out.println("Enter employee name::");
				String name = scanner.next();
				System.out.println("Enter Employee Salary");
				sal = scanner.nextDouble();
				employee = new Employee(employeeId, name, sal);
				if (employeeServiceInterafce.addNewEmployee(employee))
					System.out.println("Employee Added Successfully");
				else
					System.out.println("Employee not added");
				break;

			case 2:
				// System.out.println("Enter employee id to print");
				System.out.println("Enter Employee Id::");
				employeeId = scanner.nextInt();
				System.out.println(employeeServiceInterafce.getEmployeeByEmployeeId(employeeId));
				break;

			case 3:
				System.out.println("All Employees List");
				List<Employee> employeeList = employeeServiceInterafce.getAllEmployee();
				for (Employee e : employeeList) {
					System.out.println(e);
				}
				break;

			case 4:
				System.out.println("Enter Employee Id::");
				employeeId = scanner.nextInt();
				System.out.println("Enter Employee Salary");
				sal = scanner.nextDouble();
				Employee e1 = employeeServiceInterafce.updateEmployeeSalary(employeeId, sal);
				if (e1 == null)
					System.out.println("Employee id not found");
				else
					System.out.println(e1 + "\nEmployee salary updated successfully");
				break;

			case 5:
				System.out.println("Enter employee id to delete");
				employeeId = scanner.nextInt();
				if (employeeServiceInterafce.deleteEmployeeById(employeeId))
					System.out.println("Deleted Successfully");
				else
					System.out.println("Deletion unsuccessful");
				break;

			default:
				System.out.println("Enter valid option");
				break;

			}
			System.out.println("Do you want to continue?\nYes or No");
			choice = scanner.next();
		} while (choice.equalsIgnoreCase("Yes"));
		scanner.close();
	}

}
