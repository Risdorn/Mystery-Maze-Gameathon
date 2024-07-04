package mystery_maze;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Bomb extends Sprite implements ActionListener{

    private boolean blown = false;
    private boolean flashing = false;
    public int blow = 1;
    protected final int RANGE = 2;
    private final int DELAY = 2000;// milliseconds
    private final int FLASH_DELAY = 1000;// milliseconds
    private Timer timer;
    
    public Bomb(int x, int y, int SPRITE_SIZE) {
        super(x, y, SPRITE_SIZE);
        
        initBomb();
    }
    
    private void initBomb() {
        
        loadImage("Assets/V01_Bomb.png");
        getImageDimensions();     
        timer = new Timer(DELAY, this);
        timer.setRepeats(false);
        timer.start();   
    }

    public boolean isBlown() {
        return blown;
    }
    public boolean isFlashing() {
        return flashing;
    }

    public void flash(){
        flashing = true;
        loadImage("Assets/V01_Bomb_Spark.png");
        timer = new Timer(FLASH_DELAY, this);
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!blown){
            blown = true;
        }
        if(flashing){
            flashing = false;
        }
    }
}
