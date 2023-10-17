package com.mindgate.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.mindgate.domain.Employee;
import com.mindgate.domain.Login;

public class EmployeeRepository implements EmployeeRepositoryInterface {
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	private String url = "jdbc:oracle:thin:@localhost:1521/xe";
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String user = "ashok";
	private String password = "ak";
	private Login login;
	private String insertEmployeeSQL = "insert into employee_details values(?,?,?)";
	private String selectEmployeeSQL = "select * from employee_details";
	private String selectOneEmployeeSQL = "select * from employee_details where employee_id=?";
	private String updateEmployeeSalary = "Update employee_details set salary=? where employee_id=?";
	private String deleteEmployeeSQL = "Delete from employee_details where employee_id =?";
	private String authenticateSQL = "select * from login_details where login_id=?";
	private String updateLoginCount = "update login_details set login_count=? where login_id=? ";

	@Override
	public boolean addNewEmployee(Employee employee) {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
			preparedStatement = connection.prepareStatement(insertEmployeeSQL);
			preparedStatement.setInt(1, employee.getEmployeeId());
			preparedStatement.setString(2, employee.getName());
			preparedStatement.setDouble(3, employee.getSal());

			int rowCount = preparedStatement.executeUpdate();
			if (rowCount > 0) {
				return true;
			} else
				return false;
		} catch (ClassNotFoundException e) {
			System.out.println("Failed to load driver");
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println("Connection Failed!!!");
			System.out.println(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public Employee getEmployeeByEmployeeId(int employeeId) {

		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
			preparedStatement = connection.prepareStatement(selectOneEmployeeSQL);
			preparedStatement.setInt(1, employeeId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int employeeId1 = resultSet.getInt("employee_id");
				String name = resultSet.getString("name");
				double salary = resultSet.getDouble("salary");
				Employee e = new Employee(employeeId1, name, salary);
				return e;
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Failed to lead driver");
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println("Connection failed");
			System.out.println(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;
	}

	@Override
	public List<Employee> getAllEmployee() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
			preparedStatement = connection.prepareStatement(selectEmployeeSQL);
			resultSet = preparedStatement.executeQuery();
			List<Employee> employeeSet = new LinkedList<Employee>();
			while (resultSet.next()) {
				int employeeId = resultSet.getInt("employee_id");
				String name = resultSet.getString("name");
				double salary = resultSet.getDouble("salary");
				Employee e = new Employee(employeeId, name, salary);
				employeeSet.add(e);

			}
			return employeeSet;
		} catch (ClassNotFoundException e) {
			System.out.println("Failed to lead driver");
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println("Connection failed");
			System.out.println(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;
	}

	@Override
	public Employee updateEmployeeSalary(int employeeId, double newSalary) {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
			preparedStatement = connection.prepareStatement(updateEmployeeSalary);
			preparedStatement.setInt(2, employeeId);
			preparedStatement.setDouble(1, newSalary);
			int rowCount = preparedStatement.executeUpdate();
			if (rowCount > 0) {
				return getEmployeeByEmployeeId(employeeId);
			} else
				return null;
		} catch (ClassNotFoundException e) {
			System.out.println("Failed to lead driver");
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println("Connection failed");
			System.out.println(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;
	}

	@Override
	public boolean deleteEmployeeById(int employeeId) {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
			preparedStatement = connection.prepareStatement(deleteEmployeeSQL);
			preparedStatement.setInt(1, employeeId);
			int rowCount = preparedStatement.executeUpdate();
			if (rowCount > 0) {
				return true;
			} else
				return false;
		} catch (ClassNotFoundException e) {
			System.out.println("Failed to lead driver");
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println("Connection failed");
			System.out.println(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return false;
	}

	@Override
	public Login authenticateLogin(String userName, String password1) {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
			preparedStatement = connection.prepareStatement(authenticateSQL);
			preparedStatement.setString(1, userName);
			resultSet = preparedStatement.executeQuery();
			//System.out.println(resultSet);
			if (resultSet.next()) {
				String tableUser = resultSet.getString("login_id");
				String tablePassword = resultSet.getString("password");
				String tableName = resultSet.getString("name");
				int tableLoginCount = resultSet.getInt("login_count");
//				System.out.println(tableUser);
//				System.out.println(tableUser.equals(user));
//				System.out.println(tablePassword.equals(password1));
				if (tableUser.equals(userName) && tablePassword.equals(password1) && tableLoginCount < 3) {
					login = new Login(tableUser, tablePassword, tableName, tableLoginCount, false, false);
					return login;
				} else if (tableUser.equals(userName) && tablePassword.equals(password1) && tableLoginCount >= 3) {
					login = new Login("", "", "", tableLoginCount, true, false);
					return login;
				} else if (tableUser.equals(userName) && !tablePassword.equals(password1)) {
					login = new Login("", "", "", tableLoginCount, false, true);
					//System.out.println("Hi");
					preparedStatement = connection.prepareStatement(updateLoginCount);
					
					tableLoginCount = tableLoginCount+1;
					//System.out.println(tableLoginCount);
					
					preparedStatement.setInt(1, tableLoginCount);
					preparedStatement.setString(2, tableUser);
					int bool = preparedStatement.executeUpdate();
					//System.out.println(bool);
					return login;

				}
				else{
					return null;
				}

			}

		} catch (ClassNotFoundException e) {
			System.out.println("Failed to lead driver");
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println("Connection failed");
			System.out.println(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;
	}

	@Override
	public Employee updateEmployeeName(int employeeId, String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
