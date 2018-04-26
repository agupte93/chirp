package com.ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JRootPane;

import net.miginfocom.swing.MigLayout;

import com.ui.layers.TitleBarPane;
import com.ui.layers.NewTweetPane;
import com.ui.layers.UserListPane;
import com.ui.layers.WelcomePane;

public class Chirp extends JFrame {
	private String title;
	private boolean doUsersExist = false;
	private Statement statement;
	public Chirp() {
		this.setTitle("Chirp");
		this.setSize(400, 610);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setLayout(new MigLayout("insets 0, wrap 1", "[grow, fill]", "[]0[]"));
		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		
	}
	
	public void setupUI() {
		TitleBarPane titleBar = new TitleBarPane(this);
		WelcomePane welcomeScreen = new WelcomePane(this);
		//FeedPane feedPane = new FeedPane();
		UserListPane userListPane = new UserListPane(this, welcomeScreen);
		
		this.add(titleBar, "w 100%, h 48px!", 0);
		if(!doUsersExist)
			this.add(welcomeScreen, "w 100%, h 100%, growy", 1);
		else
			this.add(userListPane, "w 100%, h 100%, growy", 1);
		//this.add(feedPane, "w 100%, h 100%, growy");
		
	}
	
	public void boot() {
		
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:"+System.getProperty("user.home")+"/.chirp/users.db");
			statement = connection.createStatement();
			statement.setQueryTimeout(30);
			
			ResultSet result = statement.executeQuery("select count(*) from users");
			result.next();
			if(result.getInt(1) > 0) {
				doUsersExist = true;
			}
			else {
				
			}
			connection.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}