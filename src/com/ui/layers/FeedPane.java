package com.ui.layers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;

import net.miginfocom.swing.MigLayout;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import com.log.Logger;
import com.ui.components.FeedElement;

public class FeedPane extends JLayeredPane {
	public FeedPane(String AccessToken,String AccessKey) {
		this.setLayout(new MigLayout("align center"));
		this.setBorder(BorderFactory.createEtchedBorder(1));
		this.setOpaque(true);
		
		
		initTweets(AccessToken,AccessKey);
		
	}
	private Twitter twitter;
		public void initTweets(String AccessToken,String AccessKey){
		    TwitterFactory factory = new TwitterFactory();
			AccessToken accessToken = (new AccessToken(AccessToken, AccessKey));
		    twitter = factory.getInstance();
		    twitter.setOAuthConsumer("1AesGANYZnGrAVl44QBIQA","xHZhqRjUU2w6TZzuamC1QXrZYvMPs058azegHi484");
		    twitter.setOAuthAccessToken(accessToken);

		    List<Status> statuses = null;
			try {
				statuses = twitter.getHomeTimeline();
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Logger.printInfo("Showing home timeline.");
		    FeedElement[] feed = new FeedElement[20];
		    Integer i=0;
		    for (Status status : statuses) {
		       
		    	try {
					feed[i] =  new FeedElement(twitter,status.getUser().getName(),status.getText(),new ImageIcon(new URL(status.getUser().getMiniProfileImageURL())),status.getId());
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	this.add(feed[i],"w 360px!,wrap");
		    	i++;
		    }
		
	}
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setPaint(Color.decode("#aaaaaa"));
		g2d.setStroke(new BasicStroke(2f));
		g2d.drawRect(1, 0, this.getWidth()-2, this.getHeight()-1);
	}
}
