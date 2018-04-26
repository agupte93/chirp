package com.ui.layers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import net.miginfocom.swing.MigLayout;

import com.ui.Chirp;
import com.ui.NewTweetWindow;

public class TitleBarPane extends JLayeredPane implements ActionListener, MouseMotionListener{
	private JButton closeButton;
	private JButton minimizeButton;
	private JButton aboutButton;
	private JButton tweetButton;
	private JButton cancelButton;
	private JLabel chirpTitle;
	private JFrame currentWindow;
	private NewTweetWindow newTweetWindow;
	protected int posX;
	protected int posY;
	
	private ImageIcon closeIcon;
	private ImageIcon minimizeIcon;
	
	public TitleBarPane(final NewTweetWindow newTweetWindow) {
		this.newTweetWindow = newTweetWindow;
		this.currentWindow = newTweetWindow;
		this.setLayout(new MigLayout("insets 8", "[]push[center]push[]"));
		tweetButton = new JButton("Tweet");
		tweetButton.addActionListener(newTweetWindow);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		this.add(cancelButton, "w 78px!, h 32px!");
		this.add(new JLabel("Post a Tweet"), "h 32px");
		this.add(tweetButton, "w 78px!, h 32px!");
		
		this.addMouseListener(new MouseAdapter()
		{
		   public void mousePressed(MouseEvent e) {
		      posX=e.getX();
		      posY=e.getY();
		   }
		});
		this.addMouseMotionListener(this);
	}
	
	public TitleBarPane(final Chirp currentWindow) {
		this.currentWindow = currentWindow;
		this.setLayout(new MigLayout("insets 8", "[]push[center]push[][]"));
		
		closeIcon = new ImageIcon(this.getClass().getResource("../../icons/close.png"));
		minimizeIcon = new ImageIcon(this.getClass().getResource("../../icons/minimize.png"));		
		
		closeButton = new JButton(closeIcon);
		closeButton.setContentAreaFilled(false);
		closeButton.setFocusPainted(false);
		closeButton.addActionListener(this);
		closeButton.setFocusPainted(false);
		closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		//closeButton.setBorder(BorderFactory.createLineBorder(Color.decode("#999999"), 1, true));
		
		minimizeButton = new JButton(minimizeIcon);
		minimizeButton.setContentAreaFilled(false);
		minimizeButton.setFocusPainted(false);
		minimizeButton.addActionListener(this);
		minimizeButton.setFocusPainted(false);
		minimizeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		//minimizeButton.setBorder(BorderFactory.createLineBorder(Color.decode("#999999"), 1, true));
		
		aboutButton = new JButton("About");
		aboutButton.addActionListener(this);
		aboutButton.setFocusPainted(false);
		
		chirpTitle = new JLabel("Chirp - The Twitter Client");
		chirpTitle.setFont(chirpTitle.getFont().deriveFont(Font.BOLD));
		
		this.setupUI();
		
		this.addMouseListener(new MouseAdapter()
		{
		   public void mousePressed(MouseEvent e) {
		      posX=e.getX();
		      posY=e.getY();
		   }
		});
		this.addMouseMotionListener(this);
	}
	
	public void disableTweetButton() {
		tweetButton.setEnabled(false);
	}
	
	public void enableTweetButton() {
		tweetButton.setEnabled(true);
	}
	
	private void setupUI() {
		this.add(aboutButton, "w 78px! , h 32px!");
		this.add(chirpTitle, "h 32px");
		this.add(minimizeButton, "w 22px!, gapright 3px!, h 22px!");
		this.add(closeButton, "w 22px! , h 22px!");
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setPaint(Color.decode("#9AE4E8"));
		g2d.setPaint(new GradientPaint(new Point(this.getWidth()/2, 0), Color.decode("#9AE4E8"), new Point(this.getWidth()/2, this.getHeight()), Color.decode("#88CACD")));
		g2d.fillRect(1, 1, this.getWidth(), this.getHeight());
		g2d.setPaint(Color.decode("#88CACD"));
		g2d.setStroke(new BasicStroke(2f));
		g2d.drawRect(1, 0, this.getWidth()-2, this.getHeight()-1);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == closeButton) {
			int value = JOptionPane.showConfirmDialog(this.getParent(), new String("Are you sure you want to quit?"), "Quit?", JOptionPane.YES_NO_OPTION);
			if(value == JOptionPane.YES_OPTION)
				System.exit(0);
		}
		else if(e.getSource() == minimizeButton) {
			currentWindow.setState(JFrame.ICONIFIED);
		}
		else if(e.getSource() == cancelButton) {
			newTweetWindow.setVisible(false);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {		
		currentWindow.setLocation(e.getXOnScreen()-posX, e.getYOnScreen()-posY);		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
