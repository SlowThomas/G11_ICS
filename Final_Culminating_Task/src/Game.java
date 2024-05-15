import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;

public class Game extends JPanel implements Runnable, KeyListener, MouseListener{
    // Useless Methods
    public void keyTyped(KeyEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

    private boolean[] pressed_keys = new boolean[91]; // 90 is the keycode for 'Z'
    private boolean mouse_down = false;
    private boolean click_triggered = false;
    private int mouse_x;
    private int mouse_y;
    public void keyPressed(KeyEvent e) { if(e.getKeyCode() < 91) pressed_keys[e.getKeyCode()] = true; }
    public void keyReleased(KeyEvent e) { if(e.getKeyCode() < 91) pressed_keys[e.getKeyCode()] = false; }
    public void mousePressed(MouseEvent e) { if(e.getButton() == 1) mouse_down = true; mouse_x = e.getX(); mouse_y = e.getY(); }
    public void mouseReleased(MouseEvent e) { if(e.getButton() == 1) if(mouse_down) click_triggered = true; mouse_down = false; }
    private boolean mouse_triggered() {if(click_triggered){click_triggered = false; return true;} return false;}

    // Assets
    private BufferedImage main_screen;
    private BufferedImage rules_screen;
    private BufferedImage controls_screen;
    private BufferedImage credits_screen;
    private BufferedImage in_game_placeholder;
    private BufferedImage game_over_screen;
    private BufferedImage special_ending_screen;
    private BufferedImage records_screen;

    // System Stats
    private int game_state = 0;


    public void run() {
        while(true){
            try { Thread.sleep(20); }
            catch(Exception e){}

            repaint();
        }
    }

    public Game() throws IOException{
        setPreferredSize(new Dimension(800, 450));
        // Add KeyListener
        this.setFocusable(true);
        addKeyListener(this);
        // Add Thread
        Thread thread = new Thread(this);
        thread.start();

        // Load image
        main_screen = ImageIO.read(new File("img/Main.png"));
        rules_screen = ImageIO.read(new File("img/Rules.png"));
        controls_screen = ImageIO.read(new File("img/Controls.png"));
        credits_screen = ImageIO.read(new File("img/Credits.png"));
        in_game_placeholder = ImageIO.read(new File("img/In Game Sample.png"));
        game_over_screen = ImageIO.read(new File("img/Game_Over.png"));
        special_ending_screen = ImageIO.read(new File("img/Survival.png"));
        records_screen = ImageIO.read(new File("img/Records.png"));
    }

    public static void main(String[] args) throws IOException{
        JFrame frame = new JFrame("Lesson 9 - Gravity");
        Game panel = new Game();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private boolean clicked_on_button(int x, int y, int width, int height){
        return mouse_triggered() &&
                mouse_x >= x && mouse_x <= x + width &&
                mouse_y >= y && mouse_y <= y + height;

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        if(game_state == 0){
            g.drawImage(main_screen, 0, 0, null);
            if(clicked_on_button(371, 174, 58, 29)){ game_state = 4; } // start button
            else if(clicked_on_button(368, 233, 64, 29)){ game_state = 1;} // rules button
            else if(clicked_on_button(350, 292, 100, 29)){ game_state = 2;} // controls button
            else if(clicked_on_button(358, 351, 85, 29)){ game_state = 3;} // credits button
        }
        else if(game_state == 1){
            g.drawImage(rules_screen, 0, 0, null);
            if(mouse_triggered()){}
        }
        else if(game_state == 2){
            g.drawImage(controls_screen, 0, 0, null);
        }
        else if(game_state == 3){
            g.drawImage(credits_screen, 0, 0, null);
        }
        else if(game_state == 4){
            g.drawImage(in_game_placeholder, 0, 0, null);
        }
        else if(game_state == 5){
            g.drawImage(game_over_screen, 0, 0, null);
        }
        else if(game_state == 6){
            g.drawImage(special_ending_screen, 0, 0, null);
        }
        else if(game_state == 7){
            g.drawImage(records_screen, 0, 0, null);
        }

    }

}
