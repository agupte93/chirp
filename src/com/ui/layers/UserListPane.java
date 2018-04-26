package com.ui.layers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import net.miginfocom.swing.MigLayout;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import com.log.Logger;
import com.ui.Chirp;

public class UserListPane extends JLayeredPane implements ActionListener, ItemListener {
	
	private Statement statement;
	private Vector<String> userList;
	private JComboBox<String> userComboBox;
	
	private JLabel welcomeText[];
	private JLabel profilePicture;
	
	private JButton loginButton;
	private JButton addAccButton;
	
	private ImageIcon addAccIcon;
	private ImageIcon profileImage;
	
	private String Accesstoken;
	private String Accesskey;
	
	private Chirp currentInstance;
	private WelcomePane welcomePane;
	
	public UserListPane(Chirp currentInstance, WelcomePane welcomePane) {
		this.welcomePane = welcomePane;
		
		this.currentInstance = currentInstance;
		
		userList = new Vector<String>();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:"+System.getProperty("user.home")+"/.chirp/users.db");
			statement = connection.createStatement();
			statement.setQueryTimeout(30);
			
			ResultSet result = statement.executeQuery("select screen_name from users");
			Logger.printInfo("Reading user list from database.");
			while(result.next()) {
				Logger.printInfo("-> Found user \""+result.getString(1)+"\"");
				userList.add(result.getString(1));
			}
			
			userComboBox = new JComboBox<String>(userList);
			userComboBox.addItemListener(this);
			
			/******************** Getting user account profile pic *******************/
			if(userComboBox.getItemCount() > 0) {
				Integer scrname_index = userComboBox.getSelectedIndex();
				String scrname = userComboBox.getItemAt(scrname_index);
				Logger.printInfo("Selected User is  "+scrname);
				try {
					result = statement.executeQuery("select token,token_secret from users where screen_name = '"+scrname+"'");
					Accesstoken = result.getString(1);
				    Accesskey = result.getString(2);
				    profilePicture = new JLabel(this.getProfilePic(Accesstoken, Accesskey));
				    profilePicture.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.decode("#88ADE0"), Color.decode("#688DB3")));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}					
			}
			else {
				profilePicture = new JLabel();
				/*4564546546*/
			}
			/**************************************************************************/
			
			welcomeText = new JLabel[3];
			welcomeText[0] = new JLabel("Chirp!");
			welcomeText[0].setFont(welcomeText[0].getFont().deriveFont(48f));
			
			welcomeText[1] = new JLabel("Please choose your account and continue.");
			welcomeText[1].setFont(welcomeText[1].getFont().deriveFont(Font.ITALIC));
			
			welcomeText[2] = new JLabel("Or add another account.");
			welcomeText[2].setFont(welcomeText[2].getFont().deriveFont(Font.ITALIC));
			
			loginButton = new JButton("Continue");
			loginButton.addActionListener(this);
			
			addAccIcon = new ImageIcon(this.getClass().getResource("../../icons/add.png"));
			addAccButton = new JButton("Add Account", addAccIcon);
			addAccButton.addActionListener(this);
			addAccButton.setIconTextGap(10);
			
			
			
			this.setLayout(new MigLayout("","push[center][center]push","push[]20px[]20px[]10px[]20px[]60px[]20px[]45px"));
			
			this.add(welcomeText[0], "h 48px!, wrap, span 2");
			this.add(profilePicture, "h 73px!, w 73px!, span 2, wrap");
			this.add(welcomeText[1], "h 16px!, wrap, span 2");
			this.add(userComboBox, "w 300px!, h 32px!, wrap, span 2");
			this.add(loginButton, "w 150px!, h 32px!, wrap, span 2");
			this.add(welcomeText[2], "h 16px!, wrap, span 2");
			this.add(addAccButton, "w 150px!, h 42px!, wrap, span 2");
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setPaint(Color.decode("#aaaaaa"));
		g2d.setStroke(new BasicStroke(2f));
		g2d.drawRect(1, 0, this.getWidth()-2, this.getHeight()-1);
	}

	private ImageIcon getProfilePic(String Accesstoken, String Accesskey) {
		TwitterFactory factory = new TwitterFactory();
	    AccessToken accessToken = (new AccessToken(Accesstoken, Accesskey));
	    Twitter twitter = factory.getInstance();
	    twitter.setOAuthConsumer("1AesGANYZnGrAVl44QBIQA","xHZhqRjUU2w6TZzuamC1QXrZYvMPs058azegHi484");
	    twitter.setOAuthAccessToken(accessToken);
	    	    
	    try {
	    	Logger.printInfo("Getting current profile pic: "+twitter.showUser(twitter.getId()).getBiggerProfileImageURLHttps());
			profileImage = new ImageIcon(new URL(twitter.showUser(twitter.getId()).getBiggerProfileImageURLHttps()));
		} catch (IllegalStateException | TwitterException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return profileImage;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addAccButton) {
			java.awt.Container parent = this.getParent();
			parent.remove(1);
			parent.add(welcomePane, "w 100%, h 100%, growy", 1);
			parent.repaint();
			parent.revalidate();
			Logger.printInfo("Opening Add Acc pane");
		}
		
		if(e.getSource() == loginButton)
		{
			TwitterMenuPane MenuPane = new TwitterMenuPane(Accesstoken,Accesskey);
			java.awt.Container parent = this.getParent();
			parent.remove(1);
			parent.add(MenuPane, "w 100%, h 100%, growy", 1);
			parent.repaint();
			parent.revalidate();
			
		}
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Integer scrname_index = userComboBox.getSelectedIndex();
		String scrname = userComboBox.getItemAt(scrname_index);
		Logger.printInfo("Selected User is  "+scrname);
		try {
			ResultSet result = statement.executeQuery("select token,token_secret from users where screen_name = '"+scrname+"'");
			Accesstoken = result.getString(1);
		    Accesskey = result.getString(2);
		    profilePicture.setIcon(this.getProfilePic(Accesstoken, Accesskey));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}		
	}
}