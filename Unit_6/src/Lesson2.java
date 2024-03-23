import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;


public class Lesson2 extends JPanel {

	public Lesson2()
	{
		// JPanel Default Settings
		setPreferredSize(new Dimension(1000,500));
		setBackground(new Color(111,245,66));
	}
	
	public void paintComponent(Graphics g)
	{
		// Local Variables
		BufferedImage image;
		
		// Method Body
		
		// Clear Screen
		super.paintComponent(g);
				
		// Importing img1.jpg
		try
		{
			image = ImageIO.read(new File("img1.jpg"));
			// Draw the image in the centre of the screen
			// To draw an image, you use the command
			// g.drawImage(img, x, y, ImageObserver)
			// This will place the top left corner
			// of the image at coodinates (x,y)
			g.drawImage(image, 150, 70, null);
		}
		catch (Exception e)
		{
			System.out.println("Image DOES NOT EXIST D:");
		}
	}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("WHY YOU SHOULD LEARN CS");
		Lesson2 panel = new Lesson2();
		
		frame.add(panel);
		
		frame.pack();
		frame.setVisible(true);
	}
	
}

