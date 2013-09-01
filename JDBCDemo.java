package com.saki.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Restaurants {

	private static String dBURL = "jdbc:sapdb://mxg_pc/CE1?user=SAPCE1DB&password=XXXXXXXX";	
	
	private static String TableName = "restaurants";

	private static Connection conn = null;
	private static Statement stmt = null;

	public static void main(String[] args) {
		createConnectin();
		insertRestaurant(5, "LaVals", "Berkeley");
		selectRestaurants();
		shutdown();

	}

	private static void shutdown() {
		
	     try
	        {
	            if (stmt != null)
	            {
	                stmt.close();
	            }
	            if (conn != null)
	            {
	                DriverManager.getConnection(dBURL + ";shutdown=true");
	                conn.close();
	            }           
	        }
	        catch (SQLException sqlExcept)
	        {
	            
	        }		

	}

	private static void selectRestaurants() {
		
        try
        {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("select * from " + TableName);
            ResultSetMetaData rsmd = results.getMetaData();
            int numberCols = rsmd.getColumnCount();
            for (int i=1; i<=numberCols; i++)
            {
                //print Column Names
                System.out.print(rsmd.getColumnLabel(i)+"\t\t");  
            }

            System.out.println("\n-------------------------------------------------");

            while(results.next())
            {
                int id = results.getInt(1);
                String restName = results.getString(2);
                String cityName = results.getString(3);	
                System.out.println(id + "\t\t" + restName + "\t\t" + cityName);
            }
            results.close();
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
		
		

	}

	private static void insertRestaurant(int id, String restName,
			String cityName) {

		try {
			stmt = conn.createStatement();
			String stmtSQL = "insert into " + TableName + " values (" +
                    id + ",'" + restName + "','" + cityName +"')";
			
			String uppercaseSQL = stmtSQL.toUpperCase();
			
            stmt.execute(uppercaseSQL);		
            
            stmt.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	private static void createConnectin() {

		try {
//			Class.forName("org.Apache.derby.Clientdriver").newInstance();

			Class.forName("com.sap.dbtech.jdbc.DriverSapDB").newInstance();			
			conn = DriverManager.getConnection(dBURL);

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}

