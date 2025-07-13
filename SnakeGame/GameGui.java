import javax.swing.JFrame;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.ButtonModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.FlowLayout;
import java.awt.Component;

class GameGui extends JFrame
{

    private static Color snakeColor = Color.green; //colors to give in GameLogicPanel
    private static Color levelColor = Color.black;
    private int snakeColorChoice = 0;
    private int levelColorChoice = 0;
    private JLayeredPane layeredPane;

    public  Color getSnakeColor()
    {
        return snakeColor;
    }                                   //getters

    public  Color getLevelColor()
    {
        return levelColor;
    }
    public JLayeredPane getLayeredPane()
    {
        return layeredPane;
    }
    public  void initLabel(JLabel label, int x, int y, int width, int height,Font font, Color color)    //initilaztion methods
    {
        label.setBounds(x,y,width,height);
        label.setFont(font);
        label.setForeground(color);
    }

    public  void initButton(JButton button, Dimension dimension, float allignment,Font font, Color foregroundColor, Color backgroundColor)
    {
        button.setPreferredSize(dimension);
        button.setMaximumSize(dimension);
        button.setAlignmentX(allignment);
        button.setFont(font);
        button.setForeground(foregroundColor);
        button.setBackground(backgroundColor);
    }
    public  void initPanel(JPanel panel, boolean isOpaque,LayoutManager layoutManager,int x, int y, int width, int height)
    {
        panel.setOpaque(isOpaque);
        panel.setLayout(layoutManager);
        panel.setBounds(x,y,width,height);
    }
    public GameGui()
    {
        super("Snake Game");

        JLabel background = new JLabel(new ImageIcon("background.gif")); 
        JLabel  gameTitle = new JLabel("Snake Game");                           // creating the UI components
        JLabel  copyrightLabel = new JLabel(" © 2025 Helio Zhuleku.");
        JLabel settings = new JLabel("SETTINGS");
        JLabel settingsBackground = new JLabel((new ImageIcon("background.gif")));
        JLabel scoreLabel = new JLabel("SCORE: 0");
        JLabel effectLabel = new JLabel("NO CURRENT EFFECT");

        JButton playButton = new JButton("PLAY");
        JButton settingsButton = new JButton("SETTINGS");
        JButton exitButton = new JButton("EXIT");
        JButton changeSnakeColorButton = new JButton("SNAKE COLOR: GREEN");
        JButton changeLevelColorButton = new JButton("LEVEL COLOR: BLACK");
        JButton returnToMainButton = new JButton("← BACK TO MAIN MENU");

        layeredPane = new JLayeredPane();
        JLayeredPane settingsPane = new JLayeredPane();

        JPanel buttonPanel = new JPanel();
        JPanel settingsButtonPanel = new JPanel();
        JPanel secondSettingsPanel = new JPanel();

        background.setBounds(-7, -7, 800, 600);
        settingsBackground.setBounds(-7, -7, 800, 600);

        initPanel(buttonPanel,false,new BoxLayout(buttonPanel, BoxLayout.Y_AXIS),-80, 150, 1000, 300);
        buttonPanel.add(playButton);
        buttonPanel.add(Box.createVerticalStrut(50)); 
        buttonPanel.add(settingsButton);
        buttonPanel.add(Box.createVerticalStrut(50)); 
        buttonPanel.add(exitButton);

        Dimension buttonSize = new Dimension(280, 100);
        Dimension settingsButtonSize = new Dimension(350, 70);

        initButton(playButton,buttonSize,Component.CENTER_ALIGNMENT,new Font("Arial", Font.BOLD, 45),new Color(255, 255, 255),new Color(0, 255, 0));
        initButton(settingsButton,buttonSize,Component.CENTER_ALIGNMENT,new Font("Arial", Font.BOLD, 45),new Color(255, 255, 255),new Color(0, 255, 0));
        initButton(exitButton,buttonSize,Component.CENTER_ALIGNMENT,new Font("Arial", Font.BOLD, 45),new Color(255, 255, 255),new Color(0, 255, 0));
        initButton(changeSnakeColorButton,settingsButtonSize,Component.LEFT_ALIGNMENT,new Font("Arial", Font.BOLD, 25),new Color(255, 255, 255),new Color(0, 255, 0));
        initButton(changeLevelColorButton,settingsButtonSize,Component.RIGHT_ALIGNMENT,new Font("Arial", Font.BOLD, 25),new Color(255, 255, 255),new Color(0, 255, 0));
        initButton(returnToMainButton, settingsButtonSize,Component.CENTER_ALIGNMENT , new Font("Arial", Font.BOLD, 25), new Color(255, 255, 255),new Color(0, 255, 0));

        initLabel(gameTitle,100,20,600,100, new Font("Arial", Font.BOLD, 80),new Color(0, 255, 0));
        gameTitle.setHorizontalAlignment(SwingConstants.CENTER);
        initLabel(copyrightLabel,7,538,222,20,new Font("Arial", Font.BOLD, 20),(Color.WHITE));
        initLabel(settings,100,20,600,100,new Font("Arial", Font.BOLD, 80),new Color(0, 255, 0));
        settings.setHorizontalAlignment(SwingConstants.CENTER);
        initLabel(scoreLabel,700,600,300,80,new Font("Arial", Font.BOLD, 50),new Color(0, 255, 0));
        initLabel(effectLabel, 800,700, 300, 80, new Font("Arial", Font.BOLD, 50),snakeColor);

        initPanel(secondSettingsPanel, false,new FlowLayout(),-115, 350, 1000, 100);
        secondSettingsPanel.add(returnToMainButton);
        initPanel(settingsButtonPanel,false,new BoxLayout(settingsButtonPanel, BoxLayout.X_AXIS),20, 70, 1000, 300);
        settingsButtonPanel.add(changeSnakeColorButton);
        settingsButtonPanel.add(Box.createHorizontalStrut(45));
        settingsButtonPanel.add(changeLevelColorButton);


        layeredPane.setPreferredSize(new Dimension(800, 600));
        layeredPane.add(background, Integer.valueOf(0));
        layeredPane.add(gameTitle, Integer.valueOf(1)); 
        layeredPane.add(buttonPanel, Integer.valueOf(2));
        layeredPane.add(copyrightLabel, Integer.valueOf(3));
        settingsPane.setPreferredSize(new Dimension(800, 600));
        settingsPane.add(settingsBackground, Integer.valueOf(0));
        settingsPane.add(settings,Integer.valueOf(1));
        settingsPane.add(settingsButtonPanel,Integer.valueOf(2));
        settingsPane.add(Box.createVerticalStrut(500));
        settingsPane.add(secondSettingsPanel, Integer.valueOf(3));

        setContentPane(layeredPane);

        playButton.addActionListener(e -> {GameLogicPanel gamePanel = new GameLogicPanel(this); setContentPane(gamePanel); setSize(new Dimension(800,720)); revalidate(); gamePanel.requestFocusInWindow();});
        // UI Button functionalities
        playButton.getModel().addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent event)
            {
                ButtonModel model = (ButtonModel) event.getSource();
               
                if(model.isPressed())
                {
                    playButton.setBackground(new Color(20,200,55));
                }
                else if(model.isRollover())
                {
                    playButton.setBackground(new Color(20, 200, 55));
                }
                else
                {
                    playButton.setBackground(new Color(0,255,0));
                }
            }
        });

        settingsButton.getModel().addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent event)
            {
                ButtonModel model = (ButtonModel) event.getSource();
               
                if(model.isPressed())
                {
                    settingsButton.setBackground(new Color(20,200,55));
                }
                else if(model.isRollover())
                {
                    settingsButton.setBackground(new Color(20, 200, 55));
                }
                else
                {
                    settingsButton.setBackground(new Color(0,255,0));
                }
            }
        });

        settingsButton.addActionListener(e -> { layeredPane.setVisible(false);
            settingsPane.setVisible(true);  //changing the UI Screen
            setContentPane(settingsPane);});

        exitButton.getModel().addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent event)
            {
                ButtonModel model = (ButtonModel) event.getSource();
               
                if(model.isPressed())
                {
                    exitButton.setBackground(new Color(255,0,0));
                    System.exit(0);
                }
                else if(model.isRollover())
                {
                    exitButton.setBackground(new Color(255, 0, 0));
                }
                else
                {
                    exitButton.setBackground(new Color(0,255,0));
                }
            }
        });
        returnToMainButton.addActionListener(e -> {layeredPane.setVisible(true);
            settingsPane.setVisible(false);
            setContentPane(layeredPane);});

        returnToMainButton.getModel().addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent event)
            {
                ButtonModel model = (ButtonModel) event.getSource();
               
                if(model.isPressed())
                {
                    returnToMainButton.setBackground(new Color(20,200,55));
                }
                else if(model.isRollover())
                {
                    returnToMainButton.setBackground(new Color(20, 200, 55));
                }
                else
                {
                    returnToMainButton.setBackground(new Color(0,255,0));
                }
            }
        });

        changeSnakeColorButton.getModel().addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent event)
            {
                ButtonModel model = (ButtonModel) event.getSource();
                Color snakeColors[] = {Color.green, Color.red, Color.blue};
                String snakeColorNames[] = {"Green", "Red", "Blue"};
                if(model.isPressed())
                {
                    changeSnakeColorButton.setBackground(new Color(20,200,55));
                    snakeColorChoice = (snakeColorChoice + 1) % snakeColors.length;
                    snakeColor = snakeColors[snakeColorChoice];     // changing the snake color 
                    changeSnakeColorButton.setText("SNAKE COLOR: "+snakeColorNames[snakeColorChoice]);
                }
                else if(model.isRollover())
                {
                    changeSnakeColorButton.setBackground(new Color(20, 200, 55));
                }
                else
                {
                    changeSnakeColorButton.setBackground(new Color(0,255,0));
                }
            }
        });

        changeLevelColorButton.getModel().addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent event)
            {
                ButtonModel model = (ButtonModel) event.getSource();
                Color levelColors[] = {Color.black, Color.white};
                String levelColorNames[] = {"Black", "White"};
                if(model.isPressed())
                {
                    changeLevelColorButton.setBackground(new Color(20,200,55));
                    levelColorChoice = (levelColorChoice + 1) % levelColors.length;
                    levelColor = levelColors[levelColorChoice];
                    changeLevelColorButton.setText("LEVEL COLOR: "+levelColorNames[levelColorChoice]);
                }
                else if(model.isRollover())
                {
                    changeLevelColorButton.setBackground(new Color(20, 200, 55));
                }
                else
                {
                    changeLevelColorButton.setBackground(new Color(0,255,0));
                }
            }
        });
    }
}
