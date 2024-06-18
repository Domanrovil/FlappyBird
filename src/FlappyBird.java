
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
    Image bottomPipeImage;

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
    int velocityX = -4; 
    int gravity = 1;
    //ArrayList is a resizable array that can change size dynamically as well as remove and add elements whenever needed.
    ArrayList<Pipe> pipes;
    //make pipes appear at random sizes
    Random random = new Random();

    //variable for game loop;
    Timer gameLoop;
    Timer placePipesTimer;
    boolean gameOver = false;
    double score =0;

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
        bottomPipeImage = new ImageIcon(getClass().getResource("/Images/bottompipe.png")).getImage();

        //bird
        bird = new Bird(birdImg);
        //pipe
        pipes = new ArrayList<Pipe>();

        //pipes timer
        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        //starts placement of pipes timer
        placePipesTimer.start();

        //game timer
        gameLoop = new Timer(1000/60,this); //1000 miliseconds / 60 actions = an action every 16.6 miliseconds or 60 actions per second.
        gameLoop.start();
    }

    public void placePipes(){
        // math random gives us number between 0-1  we then multiply by pipeHeight divided by 2 | 512/2 = 256 | 
        //math.Random() * Pipeheight/2 will give us a random number between 0-256
        // REMEMBER top left of screen is 0,0 as such you want negative numbers 
        int randomPipeY = (int)(pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));

        int openingSpace = boardHeight /4; 
        //create a toppipe object of type Pipe with parameter topPipeImage
        Pipe topPipe = new Pipe(topPipeImage);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImage);
        /* toppipe.y uses the value (negative) and then adds the total pipe height in order to start it on the positive y end of the screen
         * although enough space may be able to be created with making the value start at 600 it is often better to add an individual variable
         * that handles open spaces in case of future updates.
         */
        //bottomPipe.y = topPipe.y + 600 can also work but as mentioned before having variable openSpace will be better longterm since it can be more easily manipulated if need be later for example
        // adding difficulties etc. 
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
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

        //pipes
        for (int i = 0; i< pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height,null);
        }
    }
    // handles all the movement logic
    public void move(){
        //bird
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);
        bird.y = Math.min(bird.y,575-bird.height);

        //Pipes
        for(int i =0;i<pipes.size();i++){
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            //if bird has not passed pipe and the bird x position is greater than pipe x and its width it will add score. X position will almost be left most part 
            // as such when adding width it gives us to the end x position or rightmost side of the object. 
            if(!pipe.passed && bird.x > pipe.x + pipe.width){
                pipe.passed = true;
            }

            // if bird collides with pipe object end game
            if (collision(bird, pipe)){
                gameOver = true; 
            }
        }

        if(bird.y > 543){
            gameOver = true;
        }

    }


 
    public boolean collision(Bird b, Pipe p){
        //uses axis alligned bounding box collision method (AABB)
        return  
        b.x < p.x + p.width && //Checks that bird left corner doesnt reach pipes top right corner
        b.x + b.width > p.x && // // Bird's right edge is to the right of the pipe's left edge
        b.y < p.y +p.height && // Bird's top edge is above the pipe's bottom edge
        b.y + b.height > p.y; // Bird's bottom left edge is below the pipe's top left edge
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            placePipesTimer.stop();
            gameLoop.stop();
        }
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
