package PremierLeague;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataConnection implements Serializable{

	public static Connection connection = null;
	private Statement statement = null;

	public DataConnection() {
		try {

			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String userName = "giri";
			String password = "1234";

			// load the JDBC driver class
			Class.forName("oracle.jdbc.OracleDriver");

			// create the connection
			connection = DriverManager.getConnection(url, userName, password);

			if (connection != null) {
				//System.out.println("Connection is successful ! ");
			}

		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet executeSelectQuery(String sql) {

		ResultSet result = null;

		try {
			statement = connection.createStatement();
			result = statement.executeQuery(sql);
			// statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public ArrayList<String[]> executeSelectQueryEnhanced(String sql) {

		ArrayList<String[]> returnable = new ArrayList<String[]>();

		try {
			statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			if (result != null) {

				ResultSetMetaData meta = result.getMetaData();
				final int columnCount = meta.getColumnCount();

				while (result.next()) {

					String[] row = new String[columnCount];

					for (int cnt = 1; cnt <= columnCount; cnt++) {
						Object o = result.getObject(cnt);
						if (o != null) {
							row[cnt - 1] = o.toString();
						}
					}

					returnable.add(row);
				}
			}

			// statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return returnable;
	}

	public boolean executeQuery(String sql) {
		// For Insert, Update, Delete
		try {

			Statement statement = connection.createStatement();
			statement.executeQuery(sql);
			statement.close();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}
	
	public Connection getOracleConnection() throws Exception {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String username = "giri";
		String password = "1234";

		// load Oracle driver
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, username, password);
		return conn;
	}

	public void closeConnection() {

		try {
			if (connection != null)
				connection.close();
		} catch (Exception e) {
			System.out.println("Close connection error!");
		}
	}
	
}
