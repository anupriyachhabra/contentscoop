package com.provida.search.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DataBaseSetup implements ServletContextListener {

	public void contextInitialized(ServletContextEvent event) {
		try {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();		
		Connection con = DriverManager.getConnection(
				"jdbc:derby:scoop;create=true");
		con.setAutoCommit(false);
		Statement s = con.createStatement();
		s.execute("CREATE TABLE store(" +
				"id int generated always as identity constraint top_pk primary key, " +
				"text varchar2(2000)");
		con.commit();
		con.close();
		} catch (SQLException ignore) {} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Database has been setup");

	}    

    public void contextDestroyed(ServletContextEvent event) {
//    	Connection con;
//		try {
//			con = DriverManager.getConnection("jdbc:derby:scoop");
//			Statement s = con.createStatement();
//			s.execute("Drop database scoop");
//			con.commit();
//			con.close();
//			DriverManager.getConnection("jdbc:derby:;shutdown=true");
//			
//		} catch (SQLException e) {
//			System.out.println("Database teardown failed");
//		}
//    	System.out.println("Database has been torn down");
    }
}
