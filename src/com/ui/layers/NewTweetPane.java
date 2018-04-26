package com.ui.layers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.log.Logger;

import net.miginfocom.swing.MigLayout;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class NewTweetPane extends JLayeredPane implements ActionListener, KeyListener, DocumentListener{
	private JLabel Tweet_head = new JLabel("Compose a New Tweet : - ");
	public JLabel charLengthLabel;
	private JTextArea txt_tweet = new JTextArea();
	private JButton button_post = new JButton("Post a Tweet");
	private JButton Add_photo = new JButton("Add a Photo");
	private String Acctok,Acckey;
	private TitleBarPane titleBar;
	
	public NewTweetPane(TitleBarPane titleBar, String AccessToken,String AccessKey){
		this.titleBar = titleBar;
		this.Acctok = AccessToken;
		this.Acckey = AccessKey;
		this.setLayout(new MigLayout("insets 8", "[][]"));
		
		charLengthLabel = new JLabel("140");
		txt_tweet.addKeyListener(this);
		txt_tweet.setLineWrap(true);
		
		this.add(new JScrollPane(txt_tweet), "w 100%,h 100px!,span, wrap");
		this.add(charLengthLabel, "h 32px!, align right");
		this.repaint();

	}
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setPaint(Color.decode("#aaaaaa"));
		g2d.setStroke(new BasicStroke(2f));
		g2d.drawRect(1, 1, this.getWidth()-2, this.getHeight()-2);
	
	}
	
	public void postTweet() {
		TwitterFactory factory = new TwitterFactory();
	    AccessToken accessToken = (new AccessToken(Acctok, Acckey));
	    Twitter twitter = factory.getInstance();
	    twitter.setOAuthConsumer("1AesGANYZnGrAVl44QBIQA","xHZhqRjUU2w6TZzuamC1QXrZYvMPs058azegHi484");
	    twitter.setOAuthAccessToken(accessToken);
	    Status status;
		try {
			
			status = twitter.updateStatus(txt_tweet.getText());
			Logger.printInfo("Successfully Updated Status to "+txt_tweet.getText());
			JOptionPane.showMessageDialog(null, "<html><h4><center>Tweet Successfully Posted !!</center></h4><html>","Tweet Posted",JOptionPane.INFORMATION_MESSAGE);
			
		} catch (TwitterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	
		if(e.getSource() == button_post)
		{
			TwitterFactory factory = new TwitterFactory();
		    AccessToken accessToken = (new AccessToken(Acctok, Acckey));
		    Twitter twitter = factory.getInstance();
		    twitter.setOAuthConsumer("1AesGANYZnGrAVl44QBIQA","xHZhqRjUU2w6TZzuamC1QXrZYvMPs058azegHi484");
		    twitter.setOAuthAccessToken(accessToken);
		    Status status;
			try {
				
				status = twitter.updateStatus(txt_tweet.getText());
				Logger.printInfo("Successfully Updated Status to "+txt_tweet.getText());
				JOptionPane.showMessageDialog(null, "<html><h4><center>Tweet Successfully Posted !!</center></h4><html>","Tweet Posted",JOptionPane.INFORMATION_MESSAGE);
				this.remove(1);
				TwitterMenuPane MenuPane = new TwitterMenuPane(Acctok,Acckey);
				java.awt.Container parent = this.getParent();
				parent.remove(1);
				parent.add(MenuPane, "w 100%, h 100%, growy", 1);
				parent.repaint();
				parent.revalidate();
				
			} catch (TwitterException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    
		}
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getSource() == txt_tweet) {
			System.out.println("key typed!");
			Integer count = 140 - txt_tweet.getText().length();
			charLengthLabel.setText(count.toString());
			if(count < 0) {
				charLengthLabel.setFont(charLengthLabel.getFont().deriveFont(Font.BOLD));
				charLengthLabel.setForeground(Color.RED);
				titleBar.disableTweetButton();
			}
			else {
				charLengthLabel.setFont(charLengthLabel.getFont().deriveFont(Font.PLAIN));
				charLengthLabel.setForeground(Color.BLACK);
				titleBar.enableTweetButton();
			}
			
		}
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}
}
