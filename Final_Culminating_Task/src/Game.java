import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;

public class Game extends JPanel implements Runnable, KeyListener, MouseListener{
    // Useless Methods
    public void keyTyped(KeyEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}


    public Game() throws IOException{
        setPreferredSize(new Dimension(800, 450));
        // Add KeyListener
        this.setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
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



    // Assets
    private final BufferedImage main_screen;
    private final BufferedImage rules_screen;
    private final BufferedImage controls_screen;
    private final BufferedImage credits_screen;
    private final BufferedImage in_game_placeholder;
    private final BufferedImage game_over_screen;
    private final BufferedImage special_ending_screen;
    private final BufferedImage records_screen;

    // System Stats
    private int game_state = 0;
    private final boolean[] pressed_keys = new boolean['z' + 1];


    // Input Handling
    public void keyPressed(KeyEvent e) { if(e.getKeyChar() <= 'z') pressed_keys[e.getKeyChar()] = true; }
    public void keyReleased(KeyEvent e) { if(e.getKeyChar() <= 'z') pressed_keys[e.getKeyChar()] = false; }
    public void mouseClicked(MouseEvent e) {
        if(game_state == 0){
            if(on_button(e, 371, 174, 58, 29)){ game_state = 4; } // start button
            else if(on_button(e, 368, 233, 64, 29)){ game_state = 1;} // rules button
            else if(on_button(e, 350, 292, 100, 29)){ game_state = 2;} // controls button
            else if(on_button(e, 358, 351, 85, 29)){ game_state = 3;} // credits button
        }
        else if(game_state == 1 || game_state == 2 || game_state == 3 || game_state == 7){
            game_state = 0;
        }
        else if(game_state == 5 || game_state == 6){
            game_state = 7;
        }
    }
    private boolean on_button(MouseEvent e, int x, int y, int width, int height){
        return e.getButton() == 1 &&
                e.getX() >= x && e.getX() <= x + width &&
                e.getY() >= y && e.getY() <= y + height;
    }

    public void run() {
        while(true){
            try { Thread.sleep(20); }
            catch(Exception e){}

            repaint();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        if(game_state == 0){
            g.drawImage(main_screen, 0, 0, null);
        }
        else if(game_state == 1){
            g.drawImage(rules_screen, 0, 0, null);
        }
        else if(game_state == 2){
            g.drawImage(controls_screen, 0, 0, null);
        }
        else if(game_state == 3){
            g.drawImage(credits_screen, 0, 0, null);
        }
        else if(game_state == 4){
            g.drawImage(in_game_placeholder, 0, 0, null);
            in_game_event_handler(g);
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

    private void in_game_event_handler(Graphics g){
        if(pressed_keys['j']) game_state = 5;
        else if(pressed_keys['k']) game_state = 6;
    }

    public static void main(String[] args) throws IOException{
        JFrame frame = new JFrame("Lesson 9 - Gravity");
        Game panel = new Game();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
