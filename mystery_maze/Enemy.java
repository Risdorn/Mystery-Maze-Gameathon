package mystery_maze;

public class Enemy extends Sprite{
    protected final int SPEED = 20;
    protected final int DETECT_RADIUS = 100;
    
    public Enemy(int x, int y, int SPRITE_SIZE) {
        super(x, y, SPRITE_SIZE);
        initEnemy();
    }

    private void initEnemy() {
        loadImage("Assets/V01_Enemy.png");
        getImageDimensions();
    }

    public int[] move(){
        int direction = (int) (Math.random() * 4);
        int newX = x;
        int newY = y;
        switch (direction) {
            case 0 -> newX += SPEED;
            case 1 -> newX -= SPEED;
            case 2 -> newY += SPEED;
            case 3 -> newY -= SPEED;
        }
        int[] coord = {newX, newY};
        return coord;
    }
}
