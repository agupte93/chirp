package com.ui.layers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import net.miginfocom.swing.MigLayout;
import twitter4j.TwitterException;

import com.log.Logger;
import com.ui.Chirp;

@SuppressWarnings("serial")
public class WelcomePane extends JLayeredPane implements ActionListener{
	private JLabel welcomeText[];
	private JButton authButton;
	
	private ImageIcon authIcon;
	
	public WelcomePane(Chirp currentInstance) {
		welcomeText = new JLabel[7];
		welcomeText[0] = new JLabel("Chirp!");
		welcomeText[0].setFont(welcomeText[0].getFont().deriveFont(28f));
		
		welcomeText[1] = new JLabel("Before you start.");
		welcomeText[1].setFont(welcomeText[1].getFont().deriveFont(Font.BOLD));
		
		welcomeText[2] = new JLabel("You need to authorize this app to access");
		welcomeText[2].setFont(welcomeText[2].getFont().deriveFont(Font.ITALIC));
		
		welcomeText[3] = new JLabel("your Twitter account.");
		welcomeText[3].setFont(welcomeText[3].getFont().deriveFont(Font.ITALIC));
		
		welcomeText[4] = new JLabel("While authorizing you will be provided with");
		welcomeText[4].setFont(welcomeText[4].getFont().deriveFont(Font.ITALIC));
		
		welcomeText[5] = new JLabel("a pin. Copy that pin and enter it");
		welcomeText[5].setFont(welcomeText[5].getFont().deriveFont(Font.ITALIC));
		
		welcomeText[6] = new JLabel("in the text box provided here.");
		welcomeText[6].setFont(welcomeText[6].getFont().deriveFont(Font.ITALIC));
		 
		authIcon = new ImageIcon(this.getClass().getResource("../../icons/auth.png"));
		
		authButton = new JButton("Authorize", authIcon);
		authButton.addActionListener(this);
				
		this.addComponents();
		
	}	
	
	public void addComponents() {
		this.removeAll();
		this.setLayout(new MigLayout("", "push[center][center]push", "push[][][][]20px[][][]20px[]push"));
		this.add(welcomeText[0], "h 48px!, wrap, span 2");
		this.add(welcomeText[1], "h 16px!, wrap, span 2");
		this.add(welcomeText[2], "h 16px!, wrap, span 2");
		this.add(welcomeText[3], "h 16px!, wrap, span 2");
		this.add(welcomeText[4], "h 16px!, wrap, span 2");
		this.add(welcomeText[5], "h 16px!, wrap, span 2");
		this.add(welcomeText[6], "h 16px!, wrap, span 2");
		this.add(authButton, "h 32px!, w 150px!, span 2");
		this.repaint(1000);
		this.revalidate();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setPaint(Color.decode("#aaaaaa"));
		g2d.setStroke(new BasicStroke(2f));
		g2d.drawRect(1, 0, this.getWidth()-2, this.getHeight()-1);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		OAuthPane oAuth = new OAuthPane();  
		
		this.removeAll();
		this.setLayout(new MigLayout());
		this.add(oAuth, "w 100%, h 100%, growy");
		this.repaint(1000);
		this.revalidate();
		
		try {
			Logger.printInfo("Starting authorization process.");
			oAuth.authorize();
		} catch (TwitterException | IOException | URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}	
}
