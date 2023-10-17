package com.mindgate.repository;

import java.util.*;

import com.mindgate.domain.Employee;
import com.mindgate.domain.Login;

public interface EmployeeRepositoryInterface {
	boolean addNewEmployee(Employee employee);
	Employee getEmployeeByEmployeeId(int employeeId);
	List<Employee> getAllEmployee();
	Employee updateEmployeeSalary(int employeeId,double newSalary);
	boolean deleteEmployeeById(int employeeId);
	Employee updateEmployeeName(int employeeId,String name);
	Login authenticateLogin(String userName,String password);
}
