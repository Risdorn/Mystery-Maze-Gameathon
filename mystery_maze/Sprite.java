package mystery_maze;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Sprite {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;
    protected int SPRITE_SIZE;

    public Sprite(int x, int y, int SPRITE_SIZE) {

        this.x = x;
        this.y = y;
        visible = true;
        this.SPRITE_SIZE = SPRITE_SIZE;
    }

    protected void getImageDimensions() {

        width = SPRITE_SIZE;
        height = SPRITE_SIZE;
    }

    protected void loadImage(String imageName) {

        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
        image = image.getScaledInstance(SPRITE_SIZE, SPRITE_SIZE, Image.SCALE_DEFAULT);
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}