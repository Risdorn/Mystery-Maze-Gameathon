package mystery_maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel implements ActionListener {
    
    private final int SPRITE_SIZE = 20;
    public final int B_WIDTH = 900;
    public final int B_HEIGHT = 900;
    private final int DELAY = 140;
    private final int MAZE_X = 140;
    private final int MAZE_Y = 245;
    private final int EXIT_X = 29;
    private final int EXIT_Y = 29;
    private final int HIDDEN_TREASURE_COUNT = 2;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;

    private int[][] grid;

    private Image wall_1;
    private Image wall_2;
    private Image grass;
    private Image exit;
    private Image spike;

    public Board() {
        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    private void loadImages() {

        ImageIcon iid = new ImageIcon("Assets/V01_Tile1.png");
        wall_1 = iid.getImage();
        wall_1 = wall_1.getScaledInstance(SPRITE_SIZE, SPRITE_SIZE, Image.SCALE_DEFAULT);

        ImageIcon iia = new ImageIcon("Assets/V01_Tile2.png");
        wall_2 = iia.getImage();
        wall_2 = wall_2.getScaledInstance(SPRITE_SIZE, SPRITE_SIZE, Image.SCALE_DEFAULT);

        ImageIcon iih = new ImageIcon("Assets/V01_Tile3.png");
        grass = iih.getImage();
        grass = grass.getScaledInstance(SPRITE_SIZE, SPRITE_SIZE, Image.SCALE_DEFAULT);

        ImageIcon iie = new ImageIcon("Assets/V01_Door.png");
        exit = iie.getImage();
        exit = exit.getScaledInstance(SPRITE_SIZE, SPRITE_SIZE, Image.SCALE_DEFAULT);

        ImageIcon iis = new ImageIcon("Assets/V01_Obstacle.png");
        spike = iis.getImage();
        spike = spike.getScaledInstance(SPRITE_SIZE, SPRITE_SIZE, Image.SCALE_DEFAULT);
    }

    private void initGame() {

        Maze maze = new Maze(31, 31);
        if (maze.isMazeExists()) {
            grid = maze.getMaze();
        }
        System.out.println("The Maze has been generated");
        repaint();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        for (int i = 0; i < B_WIDTH/SPRITE_SIZE + 1; i++) {
            for(int j = 0; j < B_HEIGHT/SPRITE_SIZE + 1; j++){
                g.drawImage(grass, (i * SPRITE_SIZE), (j * SPRITE_SIZE), this);
            }
        }
        double r;
        for(int i = 0; i < 31; i++){
            for(int j = 0; j < 31; j++){
                r = Math.random();
                if(r > 0.90 && grid[i][j] == 1 && i!=0 && grid[i-1][j] != 1){
                    g.drawImage(spike, (j * SPRITE_SIZE) + MAZE_X, (i * SPRITE_SIZE) + MAZE_Y, this);
                }else if(grid[i][j] == 1 && i != 30 && grid[i+1][j] == 1){
                    g.drawImage(wall_1, (j * SPRITE_SIZE) + MAZE_X, (i * SPRITE_SIZE) + MAZE_Y, this);
                }else if (grid[i][j] == 1){
                    g.drawImage(wall_2, (j * SPRITE_SIZE) + MAZE_X, (i * SPRITE_SIZE) + MAZE_Y, this);
                }else{
                    g.drawImage(grass, (j * SPRITE_SIZE) + MAZE_X, (i * SPRITE_SIZE) + MAZE_Y, this);
                }
            }
        }
        g.drawImage(exit, (EXIT_X * SPRITE_SIZE) + MAZE_X, (EXIT_Y * SPRITE_SIZE) + MAZE_Y, this);

        Toolkit.getDefaultToolkit().sync();
   
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
}
