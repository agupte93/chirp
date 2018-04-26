package com.ui.layers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import com.log.Logger;
import com.ui.NewTweetWindow;

public class TwitterMenuPane extends JLayeredPane implements ActionListener{
	private String publicToken,privateToken;
	private JButton feedButton;
	private JButton postTweetButon;
	public TwitterMenuPane(String Accesstok,String Secret) {
		// TODO Auto-generated constructor stub
		this.publicToken = Accesstok;
		this.privateToken = Secret;
		postTweetButon = new JButton("Post a Tweet");
		feedButton = new JButton("See Latest Feed");
		
	this.setLayout(new MigLayout("insets 2","push[][]push"));
	
		postTweetButon.setFont(postTweetButon.getFont().deriveFont(14f));
		postTweetButon.addActionListener(this);
		feedButton.setFont(feedButton.getFont().deriveFont(14f));
		feedButton.addActionListener(this);
		this.add(postTweetButon);
		this.add(feedButton);
	this.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
		if(e.getSource() == postTweetButon )
		{
			NewTweetWindow tweet = new NewTweetWindow(this.getParent(), publicToken,privateToken);
			tweet.setVisible(true);
			Logger.printInfo("Tweet Option Selected");
			/*java.awt.Container parent = this.getParent();
			parent.remove(1);
			parent.add(tweet, "w 100%, h 100%, growy", 1);
			parent.repaint();
			parent.revalidate();*/
			
		}
		
		if(e.getSource() == feedButton )
		{
			Logger.printInfo("Get Feed Option Selected");
			FeedPane homeFeedPane =  new FeedPane(publicToken,privateToken);
			java.awt.Container parent = this.getParent();
			parent.remove(1);
			parent.add(new JScrollPane(homeFeedPane), "w 100%, h 100%, growy", 1);
			
			parent.repaint();
			parent.revalidate();
			
		}
		
	}
}
