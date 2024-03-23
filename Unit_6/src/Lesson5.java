import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;


// Runnable is the interface which allows you to control
// the time until some processes get executed
public class Lesson5 extends JPanel implements KeyListener, Runnable
{	
	// Game States
	public static boolean intro = false;
	public static boolean play = true;
	
	// Game Stats
	public static boolean jump = false; //False if mario is on the ground
	public static int gravity = 0;      //This is the gravity for mario
	public static int posxMario = 15;
	public static int posyMario = 426;
	public static int posxBullet = 640;
	public static int posyBullet = 386;
	
	// Game Images
	public static BufferedImage mario;
	public static BufferedImage bullet;
	
	// JPanel Constructor
	public Lesson5()
	{
		setPreferredSize(new Dimension(640,480));
		setBackground(new Color(255,255,255));
		this.setFocusable(true);
		addKeyListener(this);
		try
		{
			mario = ImageIO.read(new File("mario.png"));
			bullet = ImageIO.read(new File("bullet.png"));
		}
		catch (Exception e)
		{
			System.out.println("File cannot be found");
		}
		// When using Runnable, you have to
		// create a new Thread
		Thread thread = new Thread(this);
		thread.start();
	}
	
	// Threading Method
	public void run()
	{
		while(true)
		{
			repaint();
			try
			{	
				Thread.sleep(80); // The commands after while(true) but before try will execute once every 80 milliseconds
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	
	// Bullet Position Update
	public static void bulletUpdate()
	{
		posxBullet -= 35;
		if (posxBullet < -150)
		{
			posxBullet = 640;
		}
	}
	
	public static void marioUpdate()
	{
		if(jump)
		{
			gravity += 2;
			posyMario += gravity;
			if(gravity == 24) //When gravity is 24 pixels per frame, Mario will be back on the floor
			{
				jump = false; //Reset the jump back to false because Mario is on the ground now
				gravity = 0;  // Reset the gravity back to 0
			}
		}
	}
	
	// KeyListener Methods
	public void keyPressed(KeyEvent e)
	{	
		if (e.getKeyChar() == ' ')
		{
			// If Mario was on the ground to begin with
			if(!jump)
			{
				jump = true;
				posyMario = 270;
			}
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
		if(play)
		{
			super.paintComponent(g);
			bulletUpdate();
			marioUpdate();
			g.drawImage(bullet, posxBullet, posyBullet, this);
			g.drawImage(mario, posxMario, posyMario, this);
		}
	}
	
	public static void main(String[] args) {
		// Create a frame
		JFrame myFrame = new JFrame("Lesson 5");
	
		// Create a panel to put inside the frame
		Lesson5 myPanel = new Lesson5(); 
		myFrame.add(myPanel);
		
		// Maximize your frame to the size of the panel
		myFrame.pack();
		
		// Set the visibility of the frame to visible
		myFrame.setVisible(true);	
	}
}