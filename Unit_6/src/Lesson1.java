import java.awt.*;
import javax.swing.*;

public class Lesson1 extends JPanel{

	// Creating the JPanel
	public Lesson1()
	{
		// JPanel Default Settings
		setPreferredSize(new Dimension(600,480));
		setBackground(new Color(0,0,0));
	}
	
	// Drawing in JPanel
	// You will method override the paintComponent method in JPanel
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		// Set a colour to draw
		g.setColor(Color.white);
		
		// Drawing Lines
		// .drawLine(x1, y1, x2, y2)
		g.drawLine(0, 0, 250, 300);
		g.drawLine(50, 240, 549, 240);
		
		// .drawRect(x1, y1, width, height)
		g.drawRect(50, 200, 100, 50);
		
		// .fillRect(x1, y1, width, height)
		g.setColor(new Color(100,100,100));
		g.fillRect (400, 20, 500, 100);
			
		// g.drawOval (x1, y1, width, height)
		// g.fillOval (x1, y1, width, height)
		g.setColor(new Color(252, 211, 3));
		g.fillOval(70,70, 130, 130);
				
		// g.drawArc (x1, y1, width, height,startAngle, amountofAngle)
		// g.fillArc (x1, y1, width, height startAngle, amountofAngle)
		g.setColor(new Color(0, 0, 0));
		g.fillArc(70, 70, 130, 130, 30, -60);
		
		// g.drawPolygon (xArr, yArr, #ofPoints)
		// g.fillPolygon (xArr, yArr, #ofPoints)
		g.setColor(new Color(51, 102, 255));
		int[] xPos = {320, 400, 360, 280, 240};
		int[] yPos = {20, 70, 120, 120, 70};
		g.fillPolygon(xPos, yPos, 5);
		
		
		// g.drawString
		g.setColor(new Color(252,144,3));
		g.setFont(new Font("Arial", Font.PLAIN, 50));
		g.drawString("abc",300, 240);
	}
	
	public static void main(String[] args)
	{
		// Create a frame
		JFrame myFrame = new JFrame("Lesson 1");
				
		// Create a panel to put inside the frame
		Lesson1 myPanel = new Lesson1();
		myFrame.add(myPanel);
				
		// This will open up your software/program based on the preferred panel size
		myFrame.pack();
				
		// Set the visibility of the frame to visible
		myFrame.setVisible(true);
	}
}
