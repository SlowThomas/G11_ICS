import java.awt.*;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

import java.awt.event.*;

public class Lesson4 extends JPanel implements KeyListener
{	
	// Game Stats
	public static int posx = 0;
	public static int posy = 0;
	
	// Game Images
	public static BufferedImage background;
	public static BufferedImage image;
	
	// JPanel Constructor
	public Lesson4()
	{
		setPreferredSize(new Dimension(640,480));
		setBackground(new Color(250,250,250));
		// setFocusable allows the key detection to happen
		// within the JPanel
		this.setFocusable(true);
		addKeyListener(this);
		try
		{
			background = ImageIO.read(new File("bg.jpg"));
			image = ImageIO.read(new File("ball.png"));
		}
		catch (Exception e)
		{
			System.out.println("File cannot be found");
		}
	}
	
	// KeyListener Methods
	public void keyPressed(KeyEvent e)
	{
		//Right Key
		if(e.getKeyCode()==39 || e.getKeyChar() == 'd')
		{
			System.out.println("Right");
			posx += 5;
			if(posx > 540)
			{
				posx = 540;
			}
			paintComponent(this.getGraphics());
		}
		//Left Key
		else if (e.getKeyCode()==37 || e.getKeyChar() == 'a')
		{
			System.out.println("Left");
			posx -= 5;
			if(posx < 0)
			{
				posx = 0;
			}
			paintComponent(this.getGraphics());
		}
		//Up Key
		else if (e.getKeyCode()==38 || e.getKeyChar() == 'w')
		{
			System.out.println("Up");
			posy -= 5;
			if(posy < 0)
			{
				posy = 0;
			}
			paintComponent(this.getGraphics());
		}
		//Down Key
		else if (e.getKeyCode()==40 || e.getKeyChar() == 's')
		{
			System.out.println("Down");
			posy += 5;
			if(posy > 380)
			{
				posy = 380;
			}
			paintComponent(this.getGraphics());
		}
		
	}
	public void keyReleased(KeyEvent e)
	{

	}
	public void keyTyped(KeyEvent e)
	{
	}
	
	// PaintComponent
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);				// clear the screen
		g.drawImage(background, 0, 0, this);	// draws the background
		g.drawImage(image, posx, posy, this);	// draws the red ball on top of the background
	}
	
	public static void main(String[] args) {
		// Create a frame
		JFrame myFrame = new JFrame("Lesson 4");
	
		// Create a panel to put inside the frame
		Lesson4 myPanel = new Lesson4(); 
		myFrame.add(myPanel);
		
		// Maximize your frame to the size of the panel
		myFrame.pack();
		
		// Set the visibility of the frame to visible
		myFrame.setVisible(true);		
	}
}