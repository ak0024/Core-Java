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
	
	private String authenticateSQL = "select * from login_details where login_id=?";
	private String updateLoginCount = "update login_details set login_count=? where login_id=? ";

	

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

	

}
