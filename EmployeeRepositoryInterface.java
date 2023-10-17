package com.mindgate.repository;

import java.util.*;

import com.mindgate.domain.Employee;
import com.mindgate.domain.Login;

public interface EmployeeRepositoryInterface {
	
	Login authenticateLogin(String userName,String password);
}
