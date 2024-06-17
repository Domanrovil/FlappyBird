
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

//This class will inherit from JPanel class
//Will implement two interfaces ActionListener and KeyListener
//ActionListener - receives actions
//KeyListener - receives keystroke/presses;
public class FlappyBird extends JPanel implements ActionListener, KeyListener{
    // Instance variables belonging to the class but not any method.
    int boardWidth = 360;
    int boardHeight = 640;
    // Instance variables for image objects
    Image backgroundImg;
    Image birdImg;
    Image topPipeImage;
    Image bottomPipImage;

    // the bird is the word
    int birdX = boardWidth / 8;
    int birdY = boardHeight / 2;
    int birdWidth = 34;
    int birdHeight = 24;

    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    //Pipes Logic
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        //checks whether flappybird passed the pipe (used for scoring)
        boolean passed = false;

        Pipe(Image img){
            this.img = img;
        }
    }

    //game logic 
    Bird bird;
    int velocityY = 0;
    int gravity = 1;

    //variable for game loop;
    Timer gameLoop;


    // create the constructor
    FlappyBird() {
        // uses in-line instantiation to create an object of dimension class.
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.blue);
        //makes flappybird class the jpanel the one taking in key events
        setFocusable(true);
        //makes sures that it checks the three functions for keys
        addKeyListener(this);

        // loading images
        backgroundImg = new ImageIcon(getClass().getResource("/Images/flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("/Images/flappybird.png")).getImage();
        topPipeImage = new ImageIcon(getClass().getResource("/Images/toppipe.png")).getImage();
        bottomPipImage = new ImageIcon(getClass().getResource("/Images/bottompipe.png")).getImage();

        //bird
        bird = new Bird(birdImg);

        //game timer
        gameLoop = new Timer(1000/60,this); //1000 miliseconds / 60 actions = an action every 16.6 miliseconds or 60 actions per second.
        gameLoop.start();
    }

    

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g) {
        // System.out.println("draw"); debug statement to check gameTimer loop
        // background
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

        //bird 
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
    }
    // handles all the movement logic
    public void move(){
        //bird
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);

        bird.y = Math.min(bird.y,575-bird.height);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

    }


    //is any key a character key, arrow keys, ie all keys
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            velocityY = -9;
        }
    }


    //when you type on key with character ie 'A'. f9 or esc for example would not work.
    @Override
    public void keyTyped(KeyEvent e) {
    }   


    // when you press on a key and let go and the key goes back up
    @Override
    public void keyReleased(KeyEvent e) {
    }

}
