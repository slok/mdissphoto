package org.mdissjava.commonutils.sql.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class represents a connection to a database. It's a singleton class so 
 * just one connection can be created. It allows to connect to the database,
 * disconnect from it and create statements to it. 
 * 
 * An example of how this class can be used:
 * 
 * <blockquote>
 * 
 * <pre>
 * ...
 * DatabaseConnection dbc = DatabaseConnection.getInstance();
 * try
 * {
 * 		dbc.connect(&quot;DRIVER&quot;, &quot;CONNECTION&quot;);
 * 
 * 		Statement stmt = dbc.createStatement();
 * 		String sqlStmt = &quot;...&quot;;
 * 		stmt.execute???(sqlStmt);
 * 		stmt.close();
 * 
 * 		dbc.disconnect();
 * }
 * catch(Exception e)
 * {
 * 		e.printStackTrace();
 * }
 * ...
 * </pre>
 * 
 * </blockquote>
 * 
 * @author JudaX
 * @author MDISS Java team 2011-2012 University of Deusto
 */

public final class SqlDBConnection
{
	private static SqlDBConnection instance = null;

	private Connection connection = null;
	
	/**
	 * Checks if there is an active connection between the client and MongoDB server instance
	 */
	private void checkConnection()
	{
		if (connection == null)
			throw new IllegalStateException(
					"The connection to the database hasn't been created yet.");
	}

	/**
	 * Private constructor so instances of this class can only be created from inside the class
	 */
	private SqlDBConnection()
	{
	}

	/**
	 * Returns the unique instance of the singleton pattern.
	 * 
	 * @return The unique instance of this class.
	 */
	public static SqlDBConnection getInstance()
	{
		if (instance == null)
		{
			instance = new SqlDBConnection();
		}
		return instance;
	}

	/**
	 * Releases the unique instance of the singleton pattern.
	 */
	public static void releaseInstance()
	{
		instance = null;
	}

	/**
	 * Connects with the database connection.
	 * 
	 * @throws ClassNotFoundException if the given driver cannot be found.
	 * @throws SQLException if there is an error while connecting to the database.
	 */
	public void connect(String driverClasspath, String connectionString)
			throws ClassNotFoundException, SQLException
	{
		if (connection != null)
		{
			disconnect();
		}
		Class.forName(driverClasspath);
		connection = java.sql.DriverManager.getConnection(connectionString);
	}

	/**
	 * Disconnects the database connection.
	 * 
	 * @throws SQLException if there is any error while disconnecting.
	 */
	public void disconnect() throws SQLException
	{
		checkConnection();
		connection.close();
		connection = null;
	}

	/**
	 * Method that creates an sql statement that can be executed.
	 * @return The created statement.
	 * @throws SQLException if any problem arise while creating the statement.
	 */
	public Statement createStatement() throws SQLException
	{
		checkConnection();
		return connection.createStatement();
	}
	
	public PreparedStatement createPreparedStatement(String sentence) throws SQLException
	{
		checkConnection();
		return connection.prepareStatement(sentence);
	}

	/**
	 * Method that returns the interface to get all the meta information
	 * about the database that this connection is connected to.
	 * @return The meta information interface structure.
	 * @throws SQLException if any problem arise while getting the meta information structure.
	 */
	public DatabaseMetaData getMetaData() throws SQLException
	{
		checkConnection();
		return connection.getMetaData();
	}
}
