import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.Scanner;

public class Game extends JPanel implements Runnable, KeyListener, MouseListener{
    // Useless Methods
    public void keyTyped(KeyEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    private static class Record_Board {
        public int[] scores = new int[3];
        public boolean[] survived = new boolean[3];

        public Record_Board() throws IOException {
            Scanner fin = new Scanner(new File("data/records.txt"));
            for(int i = 0; i < 3; i++){
                if(!fin.hasNextInt()) {
                    scores[i] = -1;
                    continue;
                }
                scores[i] = fin.nextInt();
                survived[i] = fin.nextBoolean();
            }
            fin.close();
        }

        public String get_score(int idx){
            if(scores[idx] == -1) {
                return "-";
            }
            return "" + scores[idx];
        }

        public boolean record(int score, boolean survived){ // returns whether new record is achieved
            for(int i = 0; i < 3; i++){
                if(scores[i] < score || scores[i] == score && !this.survived[i] && survived){
                    for(int j = 2; j > i; j--){
                        scores[j] = scores[j - 1];
                        this.survived[j] = this.survived[j - 1];
                    }
                    scores[i] = score;
                    this.survived[i] = survived;
                    return i == 0;
                }
            }
            return false;
        }

        public void save() throws IOException{
            PrintWriter fout = new PrintWriter(new FileWriter("data/record.txt"));
            for(int i = 0; i < 3 && scores[i] != -1; i++){
                fout.println(scores[i] + " " + survived[i]);
            }
            fout.close();
        }
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

    private final Record_Board record_board;
    private int score;
    private boolean new_high;


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

        record_board = new Record_Board();
    }



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
            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("Courier New", Font.PLAIN, 28));
            g.drawString(record_board.get_score(0), 351, 234);
            g.drawString(record_board.get_score(1), 351, 276);
            g.drawString(record_board.get_score(2), 351, 318);

            g.setColor(new Color(255, 0, 0));
            g.setFont(new Font("Courier New", Font.PLAIN, 21));
            if(record_board.survived[0]) g.drawString("survived!", 400, 230);
            if(record_board.survived[1]) g.drawString("survived!", 400, 272);
            if(record_board.survived[2]) g.drawString("survived!", 400, 314);

            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("Courier New", Font.PLAIN, 28));
            g.drawString("" + score, 405, 370);

            g.setColor(new Color(255, 0, 0));
            g.setFont(new Font("Courier New", Font.BOLD, 21));
            if(new_high) g.drawString("new record!", 333, 150);
        }

    }

    private void in_game_event_handler(Graphics g){
        score = 0;

        if(pressed_keys['j']){
            game_state = 5;
            new_high = record_board.record(score, false);
        }
        else if(pressed_keys['k']){
            game_state = 6;
            new_high = record_board.record(score, true);
        }
    }

    public static void main(String[] args) throws IOException{
        JFrame frame = new JFrame("Lesson 9 - Gravity");
        Game panel = new Game();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
