package com.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JRootPane;

import net.miginfocom.swing.MigLayout;

import com.ui.layers.NewTweetPane;
import com.ui.layers.TitleBarPane;

public class NewTweetWindow extends JFrame implements ActionListener{
	
	private NewTweetPane newTweetPane;
	private TitleBarPane titleBar;
	
	public NewTweetWindow(Container owner, String publicToken, String privateToken) {
		titleBar = new TitleBarPane(this);
		newTweetPane = new NewTweetPane(titleBar, publicToken, privateToken);
		
		
		this.setUndecorated(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		this.setLayout(new MigLayout("insets 0","push[grow, fill]push","[]0[]"));
		this.add(titleBar, "w 100%, h 48px!, wrap");
		this.add(newTweetPane, "w 100%, h 100%");
		this.setSize(350, 201);
		this.setLocationRelativeTo(owner);
		//this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		newTweetPane.postTweet();		
	}
}
