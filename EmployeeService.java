package com.mindgate.service;


import java.util.*;

import com.mindgate.domain.Employee;
import com.mindgate.domain.Login;
import com.mindgate.repository.EmployeeRepository;
import com.mindgate.repository.EmployeeRepositoryInterface;

public class EmployeeService implements EmployeeServiceInterafce {
	private EmployeeRepositoryInterface employeeRepositoryInterface=new EmployeeRepository();
	
	@Override
	public Login authenticateLogin(String userName, String password) {
		return employeeRepositoryInterface.authenticateLogin(userName, password);
	}

}
