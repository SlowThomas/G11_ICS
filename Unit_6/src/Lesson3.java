import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Lesson3 extends JPanel implements MouseListener
{
	// Class Variables
	int xPos;
	int yPos;
	boolean startProgram = true;
	boolean drawCircle = false;
	
	// Object Constructor
	public Lesson3()
	{
		// Panels Default Settings 		
		setPreferredSize(new Dimension(700,360));
		setBackground(new Color(250, 250, 250));
		addMouseListener(this);
	}
	
	// Object Method
	public void paintComponent(Graphics g)
	{
		int red = (int) Math.round(255*Math.random());
		int green = (int) Math.round(255*Math.random());
		int blue = (int) Math.round(255*Math.random());
		
		if (startProgram)
		{
			super.paintComponent(g);
			startProgram = false;
		}
		if(drawCircle)
		{
			// super.paintComponent(g); // This means clear the draw screen
			g.setColor(new Color(red,green,blue));
			g.fillOval(xPos-10, yPos-10, 20, 20);
			drawCircle = false;
		}
	}
	
	// mouseClick will execute the command inside
	// of the mouseClick method if it detects a mouseClick
	// A mouse click corresponds to a pressed and released motion
    public void mouseClicked(MouseEvent e)
    {
    	//System.out.println("Pressed and Released");
    }  
    // mouseEntered will execute the command inside
    // when the mouse is inside a particular set boundary
    // by default, the set boundary is the JPanel
    public void mouseEntered(MouseEvent e)
    {
    	
    }  
    // mouseExited will execute the command inside
    // when the mouse leaves a particular set boundary
    // by default, the set boundary is the JPanel
    public void mouseExited(MouseEvent e)
    {
    	//System.out.println("Mouse exits the screen");
    }  
    // mousePressed will execute the command inside
    // when the mouse is pressed down
    public void mousePressed(MouseEvent e)
    {
    	xPos = e.getX();
    	yPos = e.getY();
    	drawCircle = true;
    	paintComponent(this.getGraphics());
    }  
    // mouseReleased will execute the command inside
    // when the mouse is released
    public void mouseReleased(MouseEvent e)
    {
    	//System.out.println("Released");
    }  
	
	public static void main(String[] args)
	{
		// Create a frame
		JFrame myFrame = new JFrame("Lesson 3");
		
		// Create a panel to put inside the frame
		Lesson3 myPanel = new Lesson3();
		myFrame.add(myPanel);
		
		// Maximize your frame to the size of the panel
		myFrame.pack();
		
		// Set the visibility of the frame to visible
		myFrame.setVisible(true);
	}
}
