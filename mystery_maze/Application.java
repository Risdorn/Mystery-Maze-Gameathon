package mystery_maze;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Application extends JFrame {

    public Application() {
        
        initUI();
    }
    
    private void initUI() {
        
        Board board = new Board();
        add(board);
        
        setSize(board.B_WIDTH, board.B_HEIGHT);
        
        setTitle("Mystery Maze");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            Application ex = new Application();
            ex.setVisible(true);
        });
    }
}
