package mystery_maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
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
    private final int ROWS = 31; // Needs to be odd
    private final int COLUMNS = 31; // Needs to be odd
    private final int EXIT_X = 29;
    private final int EXIT_Y = 29;
    private final int Player_X = 1;
    private final int Player_Y = 1;
    private final int HIDDEN_TREASURE_COUNT = 5;
    private final int ENEMY_COUNT = 5;
    private final int WALL_CODE = 1;
    private final int WALL_2_CODE = 2;
    private final int SPIKE_CODE = 3;
    private final int TREASURE_CODE = 4;
    private final int TREASURE_OPEN_CODE = 5;
    private final int KEY_CODE = 6;
    private final int GRASS_CODE = 0;

    private boolean GameStarted = false;
    private boolean inGame = true;

    private int[][] grid;
    private final List<int[]> coins = new ArrayList<>();
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Enemy> removeEnemies = new ArrayList<>();
    private final Date startTime = new Date();
    private Timer timer;
    private Timer gameTimer;
    private Timer enemyMove;
    private String time;

    private Image wall_1;
    private Image wall_2;
    private Image grass;
    private Image exit;
    private Image spike;
    private Image bomb_spark;
    private Image bomb_flash;
    private Image treasure;
    private Image treasure_open;
    private Image key;
    private Image coin;
    private Image shadow;

    private Player player;

    public Board() {
        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initPlayer();
        initGame();
    }

    private void loadImages() {

        wall_1 = new ImageIcon("Assets/V01_Tile1.png").getImage();
        wall_1 = wall_1.getScaledInstance(SPRITE_SIZE, SPRITE_SIZE, Image.SCALE_DEFAULT);

        wall_2 = new ImageIcon("Assets/V01_Tile2.png").getImage();
        wall_2 = wall_2.getScaledInstance(SPRITE_SIZE, SPRITE_SIZE, Image.SCALE_DEFAULT);

        grass = new ImageIcon("Assets/V01_Tile3.png").getImage();
        grass = grass.getScaledInstance(SPRITE_SIZE, SPRITE_SIZE, Image.SCALE_DEFAULT);

        exit = new ImageIcon("Assets/V01_Door.png").getImage();
        exit = exit.getScaledInstance(SPRITE_SIZE, SPRITE_SIZE, Image.SCALE_DEFAULT);

        spike = new ImageIcon("Assets/V01_Obstacle.png").getImage();
        spike = spike.getScaledInstance(SPRITE_SIZE, SPRITE_SIZE, Image.SCALE_DEFAULT);

        bomb_spark = new ImageIcon("Assets/V01_Bomb_Spark.png").getImage();
        bomb_spark = bomb_spark.getScaledInstance(SPRITE_SIZE, SPRITE_SIZE, Image.SCALE_DEFAULT);

        bomb_flash = new ImageIcon("Assets/V01_Bomb_Flash.png").getImage();
        bomb_flash = bomb_flash.getScaledInstance(SPRITE_SIZE, SPRITE_SIZE, Image.SCALE_DEFAULT);

        treasure = new ImageIcon("Assets/V01_Chest.png").getImage();
        treasure = treasure.getScaledInstance(SPRITE_SIZE, SPRITE_SIZE, Image.SCALE_DEFAULT);

        treasure_open = new ImageIcon("Assets/V01_Chest_Open.png").getImage();
        treasure_open = treasure_open.getScaledInstance(SPRITE_SIZE, SPRITE_SIZE, Image.SCALE_DEFAULT);

        key = new ImageIcon("Assets/V01_Key.png").getImage();
        key = key.getScaledInstance(SPRITE_SIZE, SPRITE_SIZE, Image.SCALE_DEFAULT);

        coin = new ImageIcon("Assets/V01_Coin.png").getImage();
        coin = coin.getScaledInstance(SPRITE_SIZE, SPRITE_SIZE, Image.SCALE_DEFAULT);

        shadow = new ImageIcon("Assets/V01_Shadow.png").getImage();
        shadow = shadow.getScaledInstance(SPRITE_SIZE, SPRITE_SIZE, Image.SCALE_DEFAULT);

    }

    private void initPlayer() {
        player = new Player(Player_X,Player_Y, SPRITE_SIZE);
        player.setX((player.getX() * SPRITE_SIZE) + MAZE_X);
        player.setY((player.getY() * SPRITE_SIZE) + MAZE_Y);
    }

    private void initGame() {

        Maze maze = new Maze(ROWS, COLUMNS);
        if (!maze.isMazeExists()) {
            maze.createMaze();
        }
        grid = maze.getMaze();
        createMaze();
        System.out.println("The Maze has been generated");
        //repaint();
        timer = new Timer(DELAY, this);
        timer.start();

    }

    private boolean treasurePossible(int i, int j, int[][] grid){
        if(grid[i][j] == 1 || i == 0 || j == 0 || i == ROWS - 1 || j == COLUMNS - 1){
            return false;
        }
        boolean north = grid[i-1][j] != 1;
        boolean south = grid[i+1][j] != 1;
        boolean east = grid[i][j+1] != 1;
        boolean west = grid[i][j-1] != 1;
        if(north == true && south == false && east == false && west == false){
            return true;
        }else if(north == false && south == true && east == false && west == false){
           return true;
        }else if(north == false && south == false && east == true && west == false){
            return true;
        }else if(north == false && south == false && east == false && west == true){
            return true;
        }
        return false;
    }

    private void removeFrom(int i, int j, List<int[]> from){
        for(int[] coord: from){
            if(coord[0] == i && coord[1] == j){
                coins.remove(coord);
                break;
            }
        }
    }

    private void createMaze(){
        //g.drawImage(player.getImage(), (player.x * SPRITE_SIZE) + MAZE_X, (player.y * SPRITE_SIZE) + MAZE_Y, this);
        double r;
        List<int[]> t_possible = new ArrayList<>();
        int[][] temp_grid = new int[ROWS][COLUMNS];
        for(int i=0; i < ROWS; i++){
            for(int j=0; j < COLUMNS; j++){
                r = Math.random();
                if((i!=1 && j!=1) && this.treasurePossible(i, j, grid)){
                    t_possible.add(new int[]{(j * SPRITE_SIZE) + MAZE_X, (i * SPRITE_SIZE) + MAZE_Y});
                }
            
                if(r > 0.93 && i!=0 && j!=0 && j!=COLUMNS-1 && grid[i][j] == 1 && grid[i-1][j] != 1){
                    // Randomly decide if a wall is converted to spike or not
                    temp_grid[i][j] = SPIKE_CODE;
                }else if(grid[i][j] == 1 && i != ROWS-1 && grid[i+1][j] == 1){
                    // Decide which wall to use
                    temp_grid[i][j] = WALL_2_CODE;
                }else if (grid[i][j] == 1){
                    temp_grid[i][j] = WALL_CODE;
                }else{
                    // Rest all will be grass
                    temp_grid[i][j] = GRASS_CODE;
                    coins.add(new int[]{(j * SPRITE_SIZE) + MAZE_X, (i * SPRITE_SIZE) + MAZE_Y});
                }
            }
        }
        for(int i=0; i<ROWS; i++){
            System.arraycopy(temp_grid[i], 0, grid[i], 0, COLUMNS);
        }
        // Remove Player position from coins
        coins.remove(0);

        for(int i=0; i<ENEMY_COUNT; i++){
            int[] coord = coins.get((int)(Math.random() * coins.size()));
            enemies.add(new Enemy(coord[0], coord[1], SPRITE_SIZE));
            coins.remove(coord);
        }
        // Add hidden treasures
        int[] coord;
        for(int i = 0; i < HIDDEN_TREASURE_COUNT; i++){
            coord = t_possible.get((int)(Math.random() * t_possible.size()));
            grid[(coord[1] - MAZE_Y)/SPRITE_SIZE][(coord[0] - MAZE_X)/SPRITE_SIZE] = TREASURE_CODE;
            t_possible.remove(coord);
            this.removeFrom(coord[0], coord[1], coins);
            coord = coins.get((int)(Math.random() * coins.size()));
            grid[(coord[1] - MAZE_Y)/SPRITE_SIZE][(coord[0] - MAZE_X)/SPRITE_SIZE] = KEY_CODE;
            coins.remove(coord);
            this.removeFrom(coord[0], coord[1], t_possible);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //createMaze(g);
        if(GameStarted == false){
            drawStartScreen(g);
        }else if(inGame){
            doDrawing(g);
        }else{
            drawGameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawStartScreen(Graphics g) {
        String msg = "Press Enter to Start";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    private void doDrawing(Graphics g) {
        // x coordinate in drawImage goes from left to right
        // y coordinate in drawImage goes from top to bottom

        if(inGame){
            // Make the entire background as grass
            for (int i = 0; i < B_WIDTH/SPRITE_SIZE + 1; i++) {
                for(int j = 0; j < B_HEIGHT/SPRITE_SIZE + 1; j++){
                    g.drawImage(grass, (i * SPRITE_SIZE), (j * SPRITE_SIZE), this);
                }
            }

            Font large = new Font("Helvetica", Font.BOLD, 20);
            FontMetrics fm = getFontMetrics(large);
            int offset = 50;
            int width = 0;
            g.setColor(Color.white);
            g.setFont(large);
            g.drawString(time, 10, 20);
            width += fm.stringWidth(time);
            g.drawString("Score: " + player.getScore(), offset + width, 20);
            width += fm.stringWidth("Score: " + player.getScore()) + offset;
            g.drawString("Keys: " + player.getKey(), offset + width, 20);
            width += fm.stringWidth("Keys: " + player.getKey()) + offset;
            g.drawString("Bombs: " + player.BOMB_COUNT, offset + width, 20);

            // Draw the maze
            for(int i = 0; i < ROWS; i++){
                for(int j = 0; j < COLUMNS; j++){
                    switch (grid[i][j]) {
                        case WALL_CODE -> g.drawImage(wall_2, (j * SPRITE_SIZE) + MAZE_X, (i * SPRITE_SIZE) + MAZE_Y, this);
                        case WALL_2_CODE -> g.drawImage(wall_1, (j * SPRITE_SIZE) + MAZE_X, (i * SPRITE_SIZE) + MAZE_Y, this);
                        case SPIKE_CODE -> g.drawImage(spike, (j * SPRITE_SIZE) + MAZE_X, (i * SPRITE_SIZE) + MAZE_Y, this);
                        case TREASURE_CODE -> g.drawImage(treasure, (j * SPRITE_SIZE) + MAZE_X, (i * SPRITE_SIZE) + MAZE_Y, this);
                        case TREASURE_OPEN_CODE -> g.drawImage(treasure_open, (j * SPRITE_SIZE) + MAZE_X, (i * SPRITE_SIZE) + MAZE_Y, this);
                        case KEY_CODE -> g.drawImage(key, (j * SPRITE_SIZE) + MAZE_X, (i * SPRITE_SIZE) + MAZE_Y, this);
                        default -> g.drawImage(grass, (j * SPRITE_SIZE) + MAZE_X, (i * SPRITE_SIZE) + MAZE_Y, this);
                    }
                }
            }
            for(int[] coord: coins){
                g.drawImage(coin, coord[0], coord[1], this);
            }

            g.drawImage(exit, (EXIT_X * SPRITE_SIZE) + MAZE_X, (EXIT_Y * SPRITE_SIZE) + MAZE_Y, this);
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
            for(Enemy enemy: enemies){
                g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
            }

            // Different things to draw based on whether bomb is flashing or not
            for(Bomb bomb : player.getBombs()){
                if(!bomb.isBlown()){
                    g.drawImage(bomb.getImage(), bomb.getX(), bomb.getY(), this);
                }else if(bomb.blow == 1){
                    bomb.blow = 0;
                    bomb.flash();
                }else if(bomb.isBlown() && bomb.isFlashing()){
                    // Bomb is blown in a 2 range radius
                    g.drawImage(bomb.getImage(), bomb.getX(), bomb.getY(), this);
                    int i = (bomb.getX() - MAZE_X)/SPRITE_SIZE;
                    int j = (bomb.getY() - MAZE_Y)/SPRITE_SIZE - 1;
                    int range = bomb.RANGE;
                    while(range-- > 0 && grid[j][i] == GRASS_CODE){
                        g.drawImage(bomb_flash, (i*SPRITE_SIZE) + MAZE_X, (j*SPRITE_SIZE) + MAZE_Y, this);
                        j -= 1;
                    }
                    j = (bomb.getY() - MAZE_Y)/SPRITE_SIZE + 1;
                    range = 2;
                    while(range-- > 0 && grid[j][i] == GRASS_CODE){
                        g.drawImage(bomb_flash, (i*SPRITE_SIZE) + MAZE_X, (j*SPRITE_SIZE) + MAZE_Y, this);
                        j += 1;
                    }
                    range = 2;
                    j = (bomb.getY() - MAZE_Y)/SPRITE_SIZE;
                    i -= 1;
                    while(range-- > 0 && grid[j][i] == GRASS_CODE){
                        g.drawImage(bomb_flash, (i*SPRITE_SIZE) + MAZE_X, (j*SPRITE_SIZE) + MAZE_Y, this);
                        i -= 1;
                    }
                    range = 2;
                    i = (bomb.getX() - MAZE_X)/SPRITE_SIZE + 1;
                    while(range-- > 0 && grid[j][i] == GRASS_CODE){
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
        int score = (10 * player.getCoin()) + (100 * player.getChest());
        String scoreMsg = "Score: " + score;
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, (B_HEIGHT / 2) - 10);
        g.drawString(scoreMsg, (B_WIDTH - fm.stringWidth(msg)) / 2, (B_HEIGHT / 2) + 10);
    }

    private boolean willCollide(int x,int y, boolean isEnemy){
        int i = (y - MAZE_Y)/SPRITE_SIZE;
        int j = (x - MAZE_X)/SPRITE_SIZE;
        if(i <= 0 || j <= 0 || i >= ROWS - 1 || j >= COLUMNS - 1){
            return true;
        }
        if(isEnemy && grid[i][j] == SPIKE_CODE){
            return true;
        }
        if(!isEnemy && grid[i][j] == TREASURE_CODE && player.getKey() > 0){
            grid[i][j] = TREASURE_OPEN_CODE;
            player.setChest(player.getChest() + 1);
            player.setKey(player.getKey() - 1);
        }
        return (grid[i][j] == WALL_CODE || grid[i][j] == WALL_2_CODE || grid[i][j] == TREASURE_CODE || grid[i][j] == TREASURE_OPEN_CODE);
    }

    private void checkCell(){
        int i = (player.getY() - MAZE_Y)/SPRITE_SIZE;
        int j = (player.getX() - MAZE_X)/SPRITE_SIZE;
        switch (grid[i][j]) {
            case SPIKE_CODE -> {
                inGame = false;
            }
            case KEY_CODE -> {
                grid[i][j] = GRASS_CODE;
                player.setKey(player.getKey() + 1);
            }
        }
        for(int[] coord: coins){
            if(coord[0] == player.getX() && coord[1] == player.getY()){
                coins.remove(coord);
                player.setCoin(player.getCoin() + 1);
                break;
            }
        }
        for(Enemy enemy: enemies){
            if(enemy.getX() == player.getX() && enemy.getY() == player.getY()){
                inGame = false;
            }
        }
    }

    private int[] playerSeen(Enemy enemy){
        int playerX = player.getX();
        int playerY = player.getY();
        int enemyX = enemy.getX();
        int enemyY = enemy.getY();
        int i = (enemyY - MAZE_Y)/SPRITE_SIZE;
        int j = (enemyX - MAZE_X)/SPRITE_SIZE;
        int direction = 0;
        int range = enemy.DETECT_RADIUS;
        int result[] = {0,0,0};
        if(playerX != enemyX && playerY != enemyY){
            return result;
        }
        if(playerX == enemyX){
            while(grid[i][j] == GRASS_CODE && range-- > 0){
                if(playerY == enemyY){
                    result[0] = 1;
                    result[1] = enemyX;
                    result[2] = enemy.getY() + (direction * enemy.SPEED); 
                    return result;
                }
                if(playerY > enemyY){
                    enemyY += enemy.SPEED;
                    i += 1;
                    direction = 1;
                }else{
                    enemyY -= enemy.SPEED;
                    i -= 1;
                    direction = -1;
                }
            }
        }else{
            while(grid[i][j] == GRASS_CODE && range-- > 0){
                if(playerX == enemyX){
                    enemy.setX(enemy.getX() + (direction * enemy.SPEED));
                    result[0] = 1;
                    result[1] = enemy.getX() + (direction * enemy.SPEED);
                    result[2] = enemyY; 
                    return result;
                }
                if(playerX > enemyX){
                    enemyX += enemy.SPEED;
                    j += 1;
                    direction = 1;
                }else{
                    enemyX -= enemy.SPEED;
                    j -= 1;
                    direction = -1;
                }
            }
        }
        return result;
    }

    private void checkEnemyCollision(Enemy enemy, int x, int y){
        for(Enemy enemy1: enemies){
            if(enemy1 != enemy){
                if(enemy1.getX() == x && enemy1.getY() == y){
                    return;
                }else{
                    enemy.setX(x);
                    enemy.setY(y);
                }
            }
        }
    }

    private void checkBombCollision(){
        for(Bomb bomb: player.getBombs()){
            if(bomb.isBlown() && bomb.isFlashing()){
                int bombX = bomb.getX();
                int bombY = bomb.getY();
                int i = (bomb.getY() - MAZE_Y)/SPRITE_SIZE;
                int j = (bomb.getX() - MAZE_X)/SPRITE_SIZE;
                int range = bomb.RANGE;
                for(Enemy enemy: enemies){
                    if(enemy.getX() == bombX && enemy.getY() == bombY){
                        removeEnemies.add(enemy);
                    }
                }
                if(player.getX() == bombX && player.getY() == bombY){
                    inGame = false;
                }
                bombX -= SPRITE_SIZE;
                j -= 1;
                while(range-- > 0 && grid[j][i] == GRASS_CODE){
                    for(Enemy enemy: enemies){
                        if(enemy.getX() == bombX && enemy.getY() == bombY){
                            removeEnemies.add(enemy);
                        }
                    }
                    if(player.getX() == bombX && player.getY() == bombY){
                        inGame = false;
                    }
                    j -= 1;
                    bombX -= SPRITE_SIZE;
                }
                bombX = bomb.getX() + SPRITE_SIZE;
                j = (bomb.getY() - MAZE_Y)/SPRITE_SIZE + 1;
                range = bomb.RANGE;
                while(range-- > 0 && grid[j][i] == GRASS_CODE){
                    for(Enemy enemy: enemies){
                        if(enemy.getX() == bombX && enemy.getY() == bombY){
                            removeEnemies.add(enemy);
                        }
                    }
                    if(player.getX() == bombX && player.getY() == bombY){
                        inGame = false;
                    }
                    j += 1;
                    bombX += SPRITE_SIZE;
                }
                bombX = bomb.getX();
                bombY -= SPRITE_SIZE;
                range = bomb.RANGE;
                j = (bomb.getY() - MAZE_Y)/SPRITE_SIZE;
                i -= 1;
                while(range-- > 0 && grid[j][i] == GRASS_CODE){
                    for(Enemy enemy: enemies){
                        if(enemy.getX() == bombX && enemy.getY() == bombY){
                            removeEnemies.add(enemy);
                        }
                    }
                    if(player.getX() == bombX && player.getY() == bombY){
                        inGame = false;
                    }
                    i -= 1;
                    bombY -= SPRITE_SIZE;
                }
                bombY = bomb.getY() + SPRITE_SIZE;
                range = bomb.RANGE;
                i = (bomb.getX() - MAZE_X)/SPRITE_SIZE + 1;
                while(range-- > 0 && grid[j][i] == GRASS_CODE){
                    for(Enemy enemy: enemies){
                        if(enemy.getX() == bombX && enemy.getY() == bombY){
                            removeEnemies.add(enemy);
                        }
                    }
                    if(player.getX() == bombX && player.getY() == bombY){
                        inGame = false;
                    }
                    i += 1;
                    bombY += SPRITE_SIZE;
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println("Pass " + counter);
        //counter++;
        if (inGame) {
            //checkCollision();
            if(!willCollide(player.x + player.dx, player.y + player.dy, false)){
                player.move();
                checkCell();
            }
            checkBombCollision();
            //player.move();
        }else{
            timer.stop();
        }
        repaint();
    }

    ActionListener moveEnemies = (ActionEvent e) -> {
        for(Enemy enemy: removeEnemies){
            enemies.remove(enemy);
        }
        removeEnemies.clear();
        for(Enemy enemy: enemies){
            int[] coord = playerSeen(enemy);
            if(coord[0] == 1){
                checkEnemyCollision(enemy, coord[1], coord[2]);
                return;
            }
            int[] coord1 = enemy.move();
            if(!willCollide(coord1[0], coord1[1], true)){
                checkEnemyCollision(enemy, coord1[0], coord1[1]);
            }
        }
    };

    ActionListener incrementTime = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Date currentTime = new Date();
            currentTime.setTime(System.currentTimeMillis());
            long diff = currentTime.getTime() - startTime.getTime();
            long hours = diff/1000/60/60;
            long minutes = diff/1000/60;
            long seconds = diff/1000;
            if(hours < 10){
                time = "0" + hours + ":";
            }else{
                time = hours + ":";
            }
            if(minutes < 10){
                time = time + "0" + minutes + ":";
            }else{
                time = time + minutes + ":";
            }
            if(seconds < 10){
                time = time + "0" + seconds;
            }else{
                time = time + seconds;
            }
        }
    };

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_ENTER){
                GameStarted = true;
                startTime.setTime(System.currentTimeMillis());
                time = "00:00:00";
                gameTimer = new Timer(1000, incrementTime);
                gameTimer.start();
                enemyMove = new Timer(1000, moveEnemies);
                enemyMove.start();
                return;
            }
            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }
    }
}
