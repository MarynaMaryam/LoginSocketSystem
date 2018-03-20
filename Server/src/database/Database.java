package database;

import java.sql.*;



public class Database
{
	// JDBC URL, username and password of MySQL server
	private static final String url = "jdbc:mysql://localhost:3306/login_system";
	private static final String user = "root";
	private static final String password = "238954312";

	// JDBC variables for opening and managing connection
	private static Connection con;
	public static Statement  stmt;
	public static ResultSet  rs;

	public static ResultSet SendSelectQuery(String Query)
	{
		try {
			// opening database connection to MySQL server
			con = DriverManager.getConnection(url, user, password);

			// getting Statement object to execute query
			stmt = con.createStatement();

			// executing SELECT query
			rs = stmt.executeQuery(Query);

			return rs;

		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}
		return null;
	}

	public static int SendUpdateQuery(String Query)
	{
		try {
			// opening database connection to MySQL server
			con = DriverManager.getConnection(url, user, password);

			// getting Statement object to execute query
			stmt = con.createStatement();

			return stmt.executeUpdate(Query);

		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}
		return 0;
	}

	public static void Closed()
	{
		//close connection ,stmt and resultset here
		try
		{
			con.close();
		}
		catch(SQLException se)
		{ /*can't do anything */ }
		try
		{
			stmt.close();
		}
		catch(SQLException se)
		{ /*can't do anything */ }
		try
		{
			rs.close();
		}
		catch(SQLException se)
		{ /*can't do anything */ }
	}
}
