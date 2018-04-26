package com.ui.layers;

import java.awt.Container;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import com.log.Logger;

public class OAuthPane extends JLayeredPane implements ActionListener{
	private JTextField pinField;
	private JLabel info, lastStepText; 
	private JButton proceedButton;  
	private Twitter twitterInstance;
	private AccessToken accessToken;
	private RequestToken requestToken;
	private boolean isReady = false;
	private Statement statement;
	
	private final static String CONSUMER_KEY = "1AesGANYZnGrAVl44QBIQA";
    private final static String CONSUMER_KEY_SECRET = "xHZhqRjUU2w6TZzuamC1QXrZYvMPs058azegHi484";
	
	public OAuthPane() {
		this.setLayout(new MigLayout("", "push[center][center]push", "push[]20px[][]20px[]100px"));
		
		lastStepText = new JLabel("One last step!");
		lastStepText.setFont(lastStepText.getFont().deriveFont(28f));
		
		info = new JLabel("Enter the authorization pin below:");
		info.setFont(info.getFont().deriveFont(Font.ITALIC));
		
		pinField = new JTextField();
		
		proceedButton = new JButton("Proceed");
		proceedButton.addActionListener(this);
		
		this.add(lastStepText, "h 48px!, wrap, span 2");
		this.add(info, "h 16px!, w 300px!, span 2, wrap");
		this.add(pinField, "h 32px!, w 300px!, span 2, wrap");
		this.add(proceedButton, "h 32px!, w 250px!, span 2, wrap");
		
		this.repaint();
	}
	
	public void authorize() throws TwitterException, IOException, URISyntaxException {
		twitterInstance = new TwitterFactory().getInstance();
	    twitterInstance.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
	    requestToken = twitterInstance.getOAuthRequestToken();
	    if(Desktop.isDesktopSupported())
	    {
	      Desktop.getDesktop().browse(new URI(requestToken.getAuthenticationURL()));
	    }	
	    
	    isReady = true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(isReady)
		{
			try {
				accessToken = twitterInstance.getOAuthAccessToken(requestToken, pinField.getText());
				String query = "insert into users values ('"+accessToken.getScreenName()+"','"+accessToken.getToken()+"','"+accessToken.getTokenSecret()+"')";
				Class.forName("org.sqlite.JDBC");
				Connection connection = DriverManager.getConnection("jdbc:sqlite:"+System.getProperty("user.home")+"/.chirp/users.db");
				statement = connection.createStatement();
				statement.setQueryTimeout(30);
				statement.executeUpdate(query);
				connection.close();
				TwitterMenuPane MenuPane = new TwitterMenuPane(accessToken.getToken(),accessToken.getTokenSecret());
				Logger.printInfo("App authorized successfully!");
				JOptionPane.showMessageDialog(null, "<html><p>The app has been authorized to access your twitter account.</p><br><p><b><center>Enjoy!</center></b></p></html>","Success", JOptionPane.INFORMATION_MESSAGE);
				
				Container parent = this.getParent().getParent();
				parent.remove(1);
				parent.add(MenuPane, "w 100%, h 100%, growy", 1);
				parent.repaint();
				parent.revalidate();
				
				
				
			} catch (TwitterException | SQLException | ClassNotFoundException e1) {
				Logger.printErr("Token error. Check token.");
				JOptionPane.showMessageDialog(this, "The token did not match. Please try again", "Incorrect Token", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		}
	}
	
	
}
