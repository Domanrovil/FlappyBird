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
        frame.setVisible(true);
    }
}
