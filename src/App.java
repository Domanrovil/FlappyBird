import java.awt.Image;

import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 360;
        int boardheight = 640;

        JFrame frame = new JFrame("Flappy Bird");
        frame.setSize(boardWidth,boardheight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Image backgroundImg;
        Image birdImg;
        Image topPipeImage;
        Image bottomPipeImage;

        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack();
        //although the object already has focus it may be possible that the window is not focused initially thus no keys
        //being received requestFocusInWindow ensures the opened window of the game is the focus and the jPanel flappybird is the priority
        flappyBird.requestFocusInWindow();
        //hides the window is false shows the window ie game screen if true
        frame.setVisible(true);
    }
}
