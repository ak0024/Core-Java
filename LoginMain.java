package com.mindgate.main;

import java.util.Scanner;

import com.mindgate.domain.Login;
import com.mindgate.service.EmployeeService;
import com.mindgate.service.EmployeeServiceInterafce;

public class LoginMain {

	public static void main(String[] args) {
		EmployeeServiceInterafce employeeServiceInterafce = new EmployeeService();
		Scanner scanner= new Scanner(System.in);
		System.out.println("Enter login Details\n"
				+ "Enter the username");
		String username=scanner.next();
		System.out.println("Enter password");
		String password =scanner.next();
		Login login=employeeServiceInterafce.authenticateLogin(username, password);
		if(login==null)
			System.out.println("Invalid username password");
		else if(!login.isExcessCount() && !login.isInvalidPassword()){
			System.out.println("Login in successfully");
			System.out.println("Welcome "+login.getName());
		}
		else if(login.isExcessCount()){
			System.out.println("Attempt is 3 times more \nContact admin");
		}
		else if(login.isInvalidPassword()){
			System.out.println("Invalid password");
		}	
		
	}

}
