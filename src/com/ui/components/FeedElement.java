package com.ui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.log.Logger;

import net.miginfocom.swing.MigLayout;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class FeedElement extends JPanel implements ActionListener{
	private JTextArea tweetText;
	private JLabel from;
	private JLabel pic;
	private JButton retweet;
	private JButton reply;
	private JButton fav;
	private long statid;
	private Twitter twitter;
	public FeedElement(Twitter twitter,String from, String tweetText,ImageIcon img,Long id) {
		this.twitter = twitter;
		pic = new JLabel(img);
		this.statid = id;
		this.from = new JLabel(from);
		this.tweetText = new JTextArea(tweetText);
		this.tweetText.setWrapStyleWord(true);
		this.tweetText.setLineWrap(true);
		reply = new JButton(new ImageIcon(this.getClass().getResource("../../icons/reply.png")));
		retweet = new JButton(new ImageIcon(this.getClass().getResource("../../icons/retweet.png")));
		fav = new JButton(new ImageIcon(this.getClass().getResource("../../icons/fav.png")));
		this.setLayout(new MigLayout("insets 5"));
		this.add(this.from, "gap 10px, w 340px!, h 16px!,span 3, wrap");
		this.add(pic,"split 2,h 48px!,w 48px!");
		this.add(this.tweetText, "gap 10px, w 290px!, h 64px!, wrap, span 2");
		reply.addActionListener(this);
		retweet.addActionListener(this);
		fav.addActionListener(this);
		
		this.add(reply,"split 3, left");
		this.add(retweet,"center");
		this.add(fav,"right");
		
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setPaint(Color.decode("#cccccc"));
		g2d.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == retweet)
		{
			try {
				twitter.retweetStatus(statid);
				retweet.setText("RETWEETED");
				retweet.setIcon(null);
				
				retweet.setEnabled(false);
			} catch (TwitterException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            Logger.printInfo("Successfully retweeted status [" + statid + "].");
            		}
		if(e.getSource() == fav)
		{
			try {
				twitter.createFavorite(statid);
				Logger.printInfo("Successfully Fav status [" + statid + "].");
				fav.setText("FAVORITE");
				fav.setIcon(null);
				
				fav.setEnabled(false);
			} catch (TwitterException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if(e.getSource() == reply)
		{
			
		}
	}
}
