import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class AsteroidEvader extends JPanel implements ActionListener, KeyListener {
    int canvasWidth = 360;
    int canvasHeight = 640;

    // Asset images 
    Image bgImage;
    Image flyerImage;
    Image upperObstacleImage;
    Image lowerObstacleImage;

    // Flyer properties
    int flyerXPos = canvasWidth / 8;
    int flyerYPos = canvasWidth / 2;
    int flyerWidth = 34;
    int flyerHeight = 24;

    class Flyer {
        int x = flyerXPos;
        int y = flyerYPos;
        int width = flyerWidth;
        int height = flyerHeight;
        Image sprite;

        Flyer(Image sprite) {
            this.sprite = sprite;
        }
    }

    // Obstacle properties
    int obstacleXStart = canvasWidth;
    int obstacleYStart = 0;
    int obstacleWidth = 64;  
    int obstacleHeight = 512;
    
    class Obstacle {
        int x = obstacleXStart;
        int y = obstacleYStart;
        int width = obstacleWidth;
        int height = obstacleHeight;
        Image sprite;
        boolean isPassed = false;

        Obstacle(Image sprite) {
            this.sprite = sprite;
        }
    }

    // Game mechanics
    Flyer flyer;
    int pipeSpeed = -4; // Speed at which obstacles move left
    int verticalVelocity = 0; // Vertical speed of the flyer
    int fallSpeed = 1;

    ArrayList<Obstacle> obstacles;
    Random rng = new Random();

    Timer mainLoop;
    Timer obstacleSpawner;
    boolean hasGameEnded = false;
    double gameScore = 0;

    AsteroidEvader() {
        setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        setFocusable(true);
        addKeyListener(this);

        // Load assets
        bgImage = new ImageIcon(getClass().getResource("./backgroundimg.png")).getImage();
        flyerImage = new ImageIcon(getClass().getResource("./flyer.png")).getImage();
        upperObstacleImage = new ImageIcon(getClass().getResource("./topasteroids.png")).getImage();
        lowerObstacleImage = new ImageIcon(getClass().getResource("./bottomasteroids.png")).getImage();

        // Initialize flyer and obstacles
        flyer = new Flyer(flyerImage);
        obstacles = new ArrayList<Obstacle>();

        // Timer to generate new obstacles
        obstacleSpawner = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateObstacles();
            }
        });
        obstacleSpawner.start();
        
        // Main game loop
        mainLoop = new Timer(1000 / 60, this); // Executes approximately 60 times per second
        mainLoop.start();
    }
    
    void generateObstacles() {
        int randomY = (int) (obstacleYStart - obstacleHeight / 4 - Math.random() * (obstacleHeight / 2));
        int gapSize = canvasHeight / 4;
    
        Obstacle upperObstacle = new Obstacle(upperObstacleImage);
        upperObstacle.y = randomY;
        obstacles.add(upperObstacle);
    
        Obstacle lowerObstacle = new Obstacle(lowerObstacleImage);
        lowerObstacle.y = upperObstacle.y + obstacleHeight + gapSize;
        obstacles.add(lowerObstacle);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderGame(g);
    }

    public void renderGame(Graphics g) {
        // Draw background
        g.drawImage(bgImage, 0, 0, this.canvasWidth, this.canvasHeight, null);

        // Draw flyer
        g.drawImage(flyerImage, flyer.x, flyer.y, flyer.width, flyer.height, null);

        // Draw obstacles
        for (Obstacle obstacle : obstacles) {
            g.drawImage(obstacle.sprite, obstacle.x, obstacle.y, obstacle.width, obstacle.height, null);
        }

        // Display score
        g.setColor(Color.white);
        g.setFont(new Font("Montserrat", Font.PLAIN, 32));
        if (hasGameEnded) {
            g.drawString("Game Over: " + (int) gameScore, 10, 35);
        } else {
            g.drawString(String.valueOf((int) gameScore), 10, 35);
        }
    }

    public void updateGame() {
        // Update flyer position
        verticalVelocity += fallSpeed;
        flyer.y += verticalVelocity;
        flyer.y = Math.max(flyer.y, 0); // Prevent flyer from moving off the top

        // Update obstacle positions
        for (Obstacle obstacle : obstacles) {
            obstacle.x += pipeSpeed;

            if (!obstacle.isPassed && flyer.x > obstacle.x + obstacle.width) {
                gameScore += 0.5; // Increment score by 0.5 for each obstacle pair passed
                obstacle.isPassed = true;
            }

            if (checkCollision(flyer, obstacle)) {
                hasGameEnded = true;
            }
        }

        if (flyer.y > canvasHeight) {
            hasGameEnded = true;
        }
    }

    boolean checkCollision(Flyer flyer, Obstacle obstacle) {
        return flyer.x < obstacle.x + obstacle.width &&
               flyer.x + flyer.width > obstacle.x &&
               flyer.y < obstacle.y + obstacle.height &&
               flyer.y + flyer.height > obstacle.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateGame();
        repaint();
        if (hasGameEnded) {
            obstacleSpawner.stop();
            mainLoop.stop();
        }
    }  

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            verticalVelocity = -9;

            if (hasGameEnded) {
                resetGame();
            }
        }
    }

    void resetGame() {
        flyer.y = flyerYPos;
        verticalVelocity = 0;
        obstacles.clear();
        hasGameEnded = false;
        gameScore = 0;
        mainLoop.start();
        obstacleSpawner.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
