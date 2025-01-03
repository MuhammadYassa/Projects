import javax.swing.*;

public class App {
    public static void main(String[] args){
        int boardWidth = 360;
        int boardHeight = 640;

        JFrame frame = new JFrame("Flappy Bird");
		frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null); 
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        AsteroidEvader asteroidEvader = new AsteroidEvader();
        frame.add(asteroidEvader);
        frame.pack();
        asteroidEvader.requestFocus();
        frame.setVisible(true);
    }
}