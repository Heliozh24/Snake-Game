import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.Action;
import javax.swing.ButtonModel;
import java.awt.event.ActionListener;

class GameOverScreen extends JLayeredPane
{
    private GameGui frame;
    private DataLoader dataLoader = new DataLoader();
    public GameOverScreen(GameGui frame, GameLogicPanel gamePanel)
    {
        this.frame = frame;
        int currentHighScore = dataLoader.loadHighScore();
        int playerScore = gamePanel.getScore();
        JLabel background = new JLabel(new ImageIcon("background2.gif"));
        JLabel gameOverTitle = new JLabel("GAME OVER");
        JLabel gameAnalysis = new JLabel("GAME ANALYSIS");
        JLabel score = new JLabel();
        JLabel totalFoodEaten = new JLabel();
        JLabel totalGhostFoodEaten = new JLabel();
        JLabel totalSpeedFoodEaten = new JLabel();
        JLabel highScore = new JLabel();
        JLabel newHighScore = new JLabel();
        JButton backToMainMenuButton = new JButton("← BACK TO MAIN MENU");
        JPanel gameAnalysisPanel = new JPanel();
        background.setBounds(-7, -10, 800,800);
        frame.initLabel(gameOverTitle,100,0,600,100, new Font("Arial", Font.BOLD, 80),new Color(255, 0, 0));
        gameOverTitle.setHorizontalAlignment(SwingConstants.CENTER);
        frame.initLabel(gameAnalysis,100,120,600,100, new Font("Arial", Font.BOLD, 50),Color.green);
        gameAnalysis.setHorizontalAlignment(SwingConstants.CENTER);
        frame.initLabel(score,100,20,400,100, new Font("Arial", Font.BOLD, 30),Color.green);
        score.setHorizontalAlignment(SwingConstants.CENTER);
        score.setText("TOTAL SCORE EARNED: "+playerScore);
        frame.initLabel(totalFoodEaten,100,20,400,100, new Font("Arial", Font.BOLD, 30),Color.green);
        totalFoodEaten.setHorizontalAlignment(SwingConstants.CENTER);
        totalFoodEaten.setText("TOTAL AMOUNT OF FOOD EATEN: "+ gamePanel.getFoodEaten());
        frame.initLabel(totalSpeedFoodEaten,100,20,400,100, new Font("Arial", Font.BOLD, 30),new Color(100,220,200));
        totalSpeedFoodEaten.setHorizontalAlignment(SwingConstants.CENTER);
        totalSpeedFoodEaten.setText("TOTAL AMOUNT OF SPEED FOOD EATEN: "+ gamePanel.getSpeedFoodEaten());
        frame.initLabel(totalGhostFoodEaten,100,20,400,100, new Font("Arial", Font.BOLD, 30),new Color(150,150,150,120));
        totalGhostFoodEaten.setHorizontalAlignment(SwingConstants.CENTER);
        totalGhostFoodEaten.setText("TOTAL AMOUNT OF SPEED FOOD EATEN: "+ gamePanel.getGhostFoodEaten());
        frame.initLabel(highScore,100,20,400,100, new Font("Arial", Font.BOLD, 30),Color.green);
        highScore.setHorizontalAlignment(SwingConstants.CENTER);
        frame.initLabel(newHighScore,100,20,400,100, new Font("Arial", Font.BOLD, 30),Color.green);
        newHighScore.setHorizontalAlignment(SwingConstants.CENTER);
        if(playerScore > currentHighScore)
        {
            highScore.setText("PREVIOUS HIGHEST SCORE OF ALL TIME: "+ currentHighScore);
            newHighScore.setText("NEW HIGHEST SCORE OF ALL TIME: "+ playerScore);
            dataLoader.updateHighScore(playerScore);
        }
        else
        {
            highScore.setText("HIGHEST SCORE OF ALL TIME: "+ currentHighScore);
        }
        frame.initButton(backToMainMenuButton,new Dimension(350, 100),Component.CENTER_ALIGNMENT,new Font("Arial", Font.BOLD, 25),new Color(255, 255, 255),new Color(0, 255, 0));
        backToMainMenuButton.setBounds(230, 640, 350, 80);
        backToMainMenuButton.setAlignmentX(CENTER_ALIGNMENT);
        frame.initPanel(gameAnalysisPanel,false,new BoxLayout(gameAnalysisPanel, BoxLayout.Y_AXIS),0, 200, 783, 400);
        gameAnalysisPanel.setBorder(BorderFactory.createLineBorder(Color.white, 4));
        add(background,Integer.valueOf(0));
        add(gameOverTitle,Integer.valueOf(1));
        add(gameAnalysis, Integer.valueOf(2));
        add(gameAnalysisPanel,Integer.valueOf(3));
        add(backToMainMenuButton,Integer.valueOf(4));
        gameAnalysisPanel.add(score);
        gameAnalysisPanel.add(Box.createVerticalStrut(30));
        gameAnalysisPanel.add(totalFoodEaten);
        gameAnalysisPanel.add(Box.createVerticalStrut(30));
        gameAnalysisPanel.add(totalGhostFoodEaten);
        gameAnalysisPanel.add(Box.createVerticalStrut(30));
        gameAnalysisPanel.add(totalSpeedFoodEaten);
        gameAnalysisPanel.add(Box.createVerticalStrut(30));
        gameAnalysisPanel.add(highScore);
        gameAnalysisPanel.add(Box.createVerticalStrut(30));
        gameAnalysisPanel.add(newHighScore);

        backToMainMenuButton.addActionListener(e -> {frame.getLayeredPane().setVisible(true);
        GameOverScreen.this.setVisible(false);
        frame.setContentPane(frame.getLayeredPane());
        frame.setSize(new Dimension(800,600)); revalidate();});
        
        backToMainMenuButton.getModel().addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent event)
            {
                ButtonModel model = (ButtonModel) event.getSource();
               
                if(model.isPressed())
                {
                    backToMainMenuButton.setBackground(new Color(20,200,55));
                }
                else if(model.isRollover())
                {
                    backToMainMenuButton.setBackground(new Color(20, 200, 55));
                }
                else
                {
                    backToMainMenuButton.setBackground(new Color(0,255,0));
                }
            }
        });
    }
}