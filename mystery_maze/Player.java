package mystery_maze;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Player extends Sprite{
    
    private final int SPEED = 20;
    protected int dx;
    protected int dy;
    protected int BOMB_COUNT = 5;
    private List<Bomb> bombs;
    private int COINS = 0;
    private int KEYS = 0;
    private int CHESTS = 0;
    
    public Player(int x, int y, int SPRITE_SIZE) {
        super(x, y, SPRITE_SIZE);
        
        initMan();
    }

    private void initMan() {
        bombs = new ArrayList<>();
        loadImage("Assets/V01_MainCharacter.png");
        getImageDimensions();
    }

    public void move() {
        
        x += dx;
        y += dy;
        
        if (x < 1) {
            x = 1;
        }
        
        if (y < 1) {
            y = 1;
        }
    }

    public List<Bomb> getBombs() {
        return bombs;
    }

    public int getCoin() {
        return COINS;
    }

    public int getKey() {
        return KEYS;
    }

    public int getChest() {
        return CHESTS;
    }

    public String getScore(){
        int score = 10*COINS + 100*CHESTS;
        return "" + score;
    }

    public void setCoin(int coins) {
        COINS = coins;
    }

    public void setKey(int keys) {
        KEYS = keys;
    }

    public void setChest(int chests) {
        CHESTS = chests;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        if(key == KeyEvent.VK_SPACE) {
            drop();
        }
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            dx = -SPEED;
            dy = 0;
        }

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            dx = SPEED;
            dy = 0;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            dy = -SPEED;
            dx = 0;
        }

        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            dy = SPEED;
            dx = 0;
        }
    }

    public void drop() {
        if(BOMB_COUNT > 0) {
            bombs.add(new Bomb(x, y, SPRITE_SIZE));
            BOMB_COUNT -= 1;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            dy = 0;
        }
    }
}
