	package com;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.log.Logger;
import com.seaglasslookandfeel.SeaGlassLookAndFeel;
import com.ui.Chirp;

public class StartChirp {

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		try {
			UIManager.setLookAndFeel(new SeaGlassLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.setProperty("awt.useSystemAAFontSettings","on");
		System.setProperty("swing.aatext", "true");
		
		
		Chirp newChirpInstance = new Chirp();
		
		// Checking if the database file exists.
		File configDir = new File(System.getProperty("user.home")+"/.chirp");
		File dbFile = new File(System.getProperty("user.home")+"/.chirp/users.db");
		if(configDir.isDirectory()) {
			Logger.printInfo("Found configuration directory at "+configDir.getAbsolutePath());
			if(dbFile.exists())
				Logger.printInfo("Found users database at "+dbFile.getAbsolutePath());
			else {
				Logger.printInfo("Database file not found. Creating one.");
				dbFile.createNewFile();
				Class.forName("org.sqlite.JDBC");
				Connection connection = DriverManager.getConnection("jdbc:sqlite:"+System.getProperty("user.home")+"/.chirp/users.db");
				Statement statement = connection.createStatement();
				statement.setQueryTimeout(30);
				Logger.printInfo("--> Creating tables.");
				statement.executeUpdate("create table users(screen_name string, token string, token_secret string)");
				connection.close();
			}
		}
		else {
			Logger.printInfo("Creating configuration directory at "+configDir.getAbsolutePath());
			configDir.mkdir();
			Logger.printInfo("Creating a database file");
			dbFile.createNewFile();
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:"+System.getProperty("user.home")+"/.chirp/users.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			Logger.printInfo("-> Creating tables.");
			statement.executeUpdate("create table users(screen_name string, token string, token_secret string)");
			connection.close();
		}
		
		
		// Rise my app!
		newChirpInstance.boot();
		// It's alive!!! 
		newChirpInstance.setupUI();
		newChirpInstance.setVisible(true);
	}

}
