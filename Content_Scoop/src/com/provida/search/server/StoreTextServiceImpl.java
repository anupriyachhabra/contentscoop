package com.provida.search.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.StringEscapeUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.provida.search.client.StoreTextService;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class StoreTextServiceImpl extends RemoteServiceServlet implements StoreTextService {

	public String storeText(String text) throws IllegalArgumentException {

		Connection con;
		try {
			con = DriverManager.getConnection("jdbc:derby:scoop");
			Statement s = con.createStatement();
			s.execute("insert into store values (DEFAULT, '" +StringEscapeUtils.escapeSql(text) +"')");
			con.commit();
			con.close();
	    	System.out.println("Text has been stored in database");
		} catch (SQLException e) {
			System.out.println("Database store opertaion failed");
		}
    	return null;
	}

	
}
