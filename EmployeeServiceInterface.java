package com.mindgate.service;


import java.util.*;

import com.mindgate.domain.Employee;
import com.mindgate.domain.Login;

public interface EmployeeServiceInterafce {
	
	Login authenticateLogin(String userName,String password);
}
