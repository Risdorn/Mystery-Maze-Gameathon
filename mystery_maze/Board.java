package mystery_maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
    
    private final int SPRITE_SIZE = 20;
    public final int B_WIDTH = 900;
    public final int B_HEIGHT = 900;
    private final int DELAY = 140;
    private final int MAZE_X = 140;
    private final int MAZE_Y = 200;
    private final int EXIT_X = 29;
    private final int EXIT_Y = 29;
    private final int HIDDEN_TREASURE_COUNT = 2;
    private Timer timer;
    private int counter = 0;

    private boolean inGame = true;
    private boolean mazeGenerated = false;

    private int[][] grid;
    private List<Rectangle> walls = new ArrayList<Rectangle>();;
    private List<int[]> open = new ArrayList<int[]>();;

    private Image wall_1;
    private Image wall_2;
    private Image grass;
    private Image exit;
    private Image spike;
    private Image bomb_spark;
    private Image bomb_flash;

    private Man mc = new Man(1,1);

    public Board() {
        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        mc.x = (mc.x * SPRITE_SIZE) + MAZE_X;
        mc.y = (mc.y * SPRITE_SIZE) + MAZE_Y;
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

        ImageIcon iib = new ImageIcon("Assets/V01_Bomb_Spark.png");
        bomb_spark = iib.getImage();
        bomb_spark = bomb_spark.getScaledInstance(SPRITE_SIZE, SPRITE_SIZE, Image.SCALE_DEFAULT);

        ImageIcon iif = new ImageIcon("Assets/V01_Bomb_Flash.png");
        bomb_flash = iif.getImage();
        bomb_flash = bomb_flash.getScaledInstance(SPRITE_SIZE, SPRITE_SIZE, Image.SCALE_DEFAULT);
    }

    private void initGame() {

        Maze maze = new Maze(31, 31);
        if (maze.isMazeExists()) {
            grid = maze.getMaze();
        }
        createMaze();
        System.out.println("The Maze has been generated");
        //repaint();
        timer = new Timer(DELAY, this);
        timer.start();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //createMaze(g);
        if(inGame){
            doDrawing(g);
        }else{
            drawGameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void createMaze(){
        
        //g.drawImage(mc.getImage(), (mc.x * SPRITE_SIZE) + MAZE_X, (mc.y * SPRITE_SIZE) + MAZE_Y, this);
        double r;
        Rectangle bound;
        for(int i=0; i < 31; i++){
            for(int j=0; j < 31; j++){
                r = Math.random();
                if(r > 0.93 && grid[i][j] == 1 && i!=30 && i!=0 && grid[i+1][j] != 1){
                    grid[i][j] = 3;
                    bound = new Rectangle((j * SPRITE_SIZE) + MAZE_X, (i * SPRITE_SIZE) + MAZE_Y, SPRITE_SIZE, SPRITE_SIZE);
                    walls.add(bound);
                }else if(grid[i][j] == 1 && i != 30 && grid[i+1][j] == 1){
                    grid[i][j] = 2;
                    bound = new Rectangle((j * SPRITE_SIZE) + MAZE_X, (i * SPRITE_SIZE) + MAZE_Y, SPRITE_SIZE, SPRITE_SIZE);
                    walls.add(bound);
                }else if (grid[i][j] == 1){
                    grid[i][j] = 1;
                    bound = new Rectangle((j * SPRITE_SIZE) + MAZE_X, (i * SPRITE_SIZE) + MAZE_Y, SPRITE_SIZE, SPRITE_SIZE);
                    walls.add(bound);
                }else{
                    grid[i][j] = 4;
                    open.add(new int[]{(j * SPRITE_SIZE) + MAZE_X, (i * SPRITE_SIZE) + MAZE_Y});
                }
            }
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {
        if(inGame){
            for (int i = 0; i < B_WIDTH/SPRITE_SIZE + 1; i++) {
                for(int j = 0; j < B_HEIGHT/SPRITE_SIZE + 1; j++){
                    g.drawImage(grass, (i * SPRITE_SIZE), (j * SPRITE_SIZE), this);
                }
            }
            for(int i = 0; i < 31; i++){
                for(int j = 0; j < 31; j++){
                    if(grid[i][j] == 3){
                        g.drawImage(spike, (j * SPRITE_SIZE) + MAZE_X, (i * SPRITE_SIZE) + MAZE_Y, this);
                    }else if(grid[i][j] == 2){
                        g.drawImage(wall_1, (j * SPRITE_SIZE) + MAZE_X, (i * SPRITE_SIZE) + MAZE_Y, this);
                    }else if (grid[i][j] == 1){
                        g.drawImage(wall_2, (j * SPRITE_SIZE) + MAZE_X, (i * SPRITE_SIZE) + MAZE_Y, this);
                    }else{
                        g.drawImage(grass, (j * SPRITE_SIZE) + MAZE_X, (i * SPRITE_SIZE) + MAZE_Y, this);
                    }
                }
            }
            g.drawImage(exit, (EXIT_X * SPRITE_SIZE) + MAZE_X, (EXIT_Y * SPRITE_SIZE) + MAZE_Y, this);
            g.drawImage(mc.getImage(), mc.getX(), mc.getY(), this);

            for(Bomb bomb : mc.getBombs()){
                if(!bomb.isBlown()){
                    g.drawImage(bomb.getImage(), bomb.getX(), bomb.getY(), this);
                }else if(bomb.blow == 1){
                    bomb.blow = 0;
                    bomb.flash();
                }else if(bomb.isBlown() && bomb.isFlashing()){
                    g.drawImage(bomb.getImage(), bomb.getX(), bomb.getY(), this);
                    int i = (bomb.getX() - MAZE_X)/SPRITE_SIZE;
                    int j = (bomb.getY() - MAZE_Y)/SPRITE_SIZE - 1;
                    while(grid[j][i] == 4){
                        g.drawImage(bomb_flash, (i*SPRITE_SIZE) + MAZE_X, (j*SPRITE_SIZE) + MAZE_Y, this);
                        j -= 1;
                    }
                    j = (bomb.getY() - MAZE_Y)/SPRITE_SIZE + 1;
                    while(grid[j][i] == 4){
                        g.drawImage(bomb_flash, (i*SPRITE_SIZE) + MAZE_X, (j*SPRITE_SIZE) + MAZE_Y, this);
                        j += 1;
                    }
                    j = (bomb.getY() - MAZE_Y)/SPRITE_SIZE;
                    i -= 1;
                    while(grid[j][i] == 4){
                        g.drawImage(bomb_flash, (i*SPRITE_SIZE) + MAZE_X, (j*SPRITE_SIZE) + MAZE_Y, this);
                        i -= 1;
                    }
                    i = (bomb.getX() - MAZE_X)/SPRITE_SIZE + 1;
                    while(grid[j][i] == 4){
                        g.drawImage(bomb_flash, (i*SPRITE_SIZE) + MAZE_X, (j*SPRITE_SIZE) + MAZE_Y, this);
                        i += 1;
                    }
                }
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
                B_HEIGHT / 2);
    }

    private boolean willCollide(int x,int y){
        Rectangle mc_bound = new Rectangle(x, y, SPRITE_SIZE, SPRITE_SIZE);
        for(Rectangle wall : walls){
            if(mc_bound.intersects(wall)){
                int checkSpike = grid[(int)(wall.y - MAZE_Y)/SPRITE_SIZE][(int)(wall.x - MAZE_X)/SPRITE_SIZE];
                if(checkSpike == 3){
                    inGame = false;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println("Pass " + counter);
        //counter++;
        if (inGame) {
            //checkCollision();
            if(!willCollide(mc.x + mc.dx, mc.y + mc.dy)){
                mc.move();
            }
            //mc.move();
        }else{
            timer.stop();
        }
        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            mc.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            mc.keyPressed(e);
        }
    }
}
