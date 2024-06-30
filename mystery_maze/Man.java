package mystery_maze;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Man extends Sprite{
    
    private final int SPEED = 20;
    protected int dx;
    protected int dy;
    private int BOMB_COUNT = 5;
    private List<Bomb> bombs;
    
    public Man(int x, int y) {
        super(x, y);
        
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

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        if(key == KeyEvent.VK_SPACE) {
            drop();
        }
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            dx = -SPEED;
        }

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            dx = SPEED;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            dy = -SPEED;
        }

        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            dy = SPEED;
        }
    }

    public void drop() {
        if(BOMB_COUNT > 0) {
            bombs.add(new Bomb(x, y));
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
