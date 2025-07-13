import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Point;
import java.util.Iterator;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JFrame;

class GameLogicPanel extends JPanel implements KeyListener
{
    //initilizations
    private Color borderColor;
    private GameGui frame;
    private Color snakeColor;
    private Color levelColor;
    private ArrayList<Rectangle> snake = new ArrayList<>();
    private ArrayList<Rectangle> border = new ArrayList<>();
    private ArrayList<Rectangle> obstacles = new ArrayList<>();
    private ArrayList<SnakeFood> snakeFood = new ArrayList<>();
    private Color snakeFoodColorChoices[] = {Color.yellow,Color.cyan,Color.gray,Color.pink};
    private JLabel scoreLabel = new JLabel("SCORE: 0");
    private JLabel effectLabel = new JLabel("NO CURRENT EFFECTS"); 
    private JPanel statsPanel = new JPanel();
    private Timer mainTimer;
    private Timer speedEffectTimer;
    private Timer ghostEffectTimer;
    private Timer playerLostTimer;
    private int foodEaten = 0;
    private int ghostFoodEaten = 0;
    private int speedFoodEaten = 0;
    public final static int moveTime = 70;
    public final static int speedTime = 5000;
    public final static int ghostTime = 5000;
    public final static int loseTime = 1000;
    boolean speedIsActive = false;
    boolean ghostIsActive = false;
    int obstacleNumber = ThreadLocalRandom.current().nextInt(8,13);
    int delta_x = 20;
    int delta_y = 0;
    int score = 0;

    // getters and setters
    public int getPosX(int i)
    {
        return snake.get(i).x; 
    }
    public int getPosY(int i)
    {
        return snake.get(i).y; 
    }
    public void setPosX(int dx, int i)
    {
        snake.get(i).x = (snake.get(i).x + dx + 800) % 800;  
    }
    public void setReadyPosX(int pos, int i)
    {
        snake.get(i).x = pos;
    }
    public void setReadyPosY(int pos, int i)
    {
        snake.get(i).y = pos;
    }
    public void setPosY(int dy, int i)
    {
        snake.get(i).y = (snake.get(i).y + dy + 600) % 600;  
    }
    public int getFoodEaten()
    {
        return foodEaten;
    }
    public int getGhostFoodEaten()
    {
        return ghostFoodEaten;
    }
    public int getSpeedFoodEaten()
    {
        return speedFoodEaten;
    }
    public int getScore()
    {
        return score;
    }
    public void applyFoodEffects(SnakeFood food)    // apply the food effects that the snake ate
    {
        Color foodColor = food.getColor();
        if(foodColor == Color.yellow) //Classic Food (Only points)
            score += 30;
        else if(foodColor == Color.cyan)    // Speed food (grants speed + points)
        {
            speedFoodEaten++;
            score += 50;
            mainTimer.setDelay(31);
            speedIsActive = true;
            ghostEffectTimer.stop();
            ghostIsActive = false;
            speedEffectTimer.restart();
            snakeColor = new Color(100,220,200);
            borderColor = Color.orange;
            speedEffectTimer.start();
        }
        else if(foodColor == Color.gray )   // ghost food (invisibility)
        {
            ghostFoodEaten++;
            score += 30;
            ghostIsActive = true;
            speedIsActive = false;
            speedEffectTimer.stop();
            mainTimer.setDelay(moveTime);
            ghostEffectTimer.restart();
            if(levelColor == Color.white)
                snakeColor = new Color(150,150,150,120);
            else
                snakeColor = new Color(255, 255, 255, 100);
            borderColor = new Color(160,32,240);
            ghostEffectTimer.start();
        }
        else if (foodColor == Color.pink)   // changes position and number of obstacles (rng)
        {
            score += 40;
            obstacles.clear();
            spawnObstacles();
        }
        scoreLabel.setText("SCORE: "+ score);
        effectLabel.setForeground(snakeColor);
        foodEaten++;
        if(ghostIsActive)
            effectLabel.setText("GHOST EFFECT");
        else if(speedIsActive)
            effectLabel.setText("SPEED EFFECT");
 
    }
    public void resetSnakeAndBorderColors()
    {
        snakeColor = frame.getSnakeColor();
        borderColor = (levelColor == Color.black) ? Color.white : Color.black;
        effectLabel.setForeground(snakeColor);
        effectLabel.setText("NO CURRENT EFFECTS");
    }
    public void spawnFood() //spawning food mechanism (rng)
    {
        for(int i = 0; i< 7; i++)
        {
            int posX;
            int posY;
            int choice;
            Color foodColor;
            Rectangle foodRectangle;
            do
            {
                posX = ThreadLocalRandom.current().nextInt(50,700);
                posY = ThreadLocalRandom.current().nextInt(50, 500);
                choice = ThreadLocalRandom.current().nextInt(0,4);
                foodColor = snakeFoodColorChoices[choice];
                foodRectangle = new Rectangle(posX,posY,18,18);
            }while( isIntersectingFood(snakeFood,foodRectangle) || isIntersecting(obstacles, foodRectangle) ); // food and obstacles should not interfere
            snakeFood.add(new SnakeFood(foodColor, foodRectangle));
        }
    }
    public void spawnObstacles()
    {
        for(int i = 0; i< obstacleNumber; i++)
        {
            int posX;
            int posY;
            Rectangle obstacle;
            do
            {
                posX = ThreadLocalRandom.current().nextInt(50,700);
                posY = ThreadLocalRandom.current().nextInt(50, 500);
                obstacle= new Rectangle(posX,posY,18,18);
            }while( (posX >= 70 && posX <= 83) || (posY >=300 && posY <= 317) || isIntersecting(obstacles, obstacle) || isIntersectingFood(snakeFood, obstacle) || snake.get(snake.size()-1).intersects(obstacle) );
            obstacles.add(obstacle);
        }
    }
    public void checkObstacleCollision(Rectangle head) //checks if snake hits and obstacle
    {
        for(Rectangle obs: obstacles)
        {
            if(obs.intersects(head))
            {
                mainTimer.stop();
                speedEffectTimer.stop();
                ghostEffectTimer.stop();
                resetSnakeAndBorderColors();
                playerLostTimer.start();
                break;
            } 
        }
    }
    public void checkSelfCollision(Rectangle head)  // checking if snake head touches the rest of its body
    {
        HashSet<Point> snakeBody = new HashSet<>();
        for (int i = 0; i <= snake.size() - 2; i++) 
            snakeBody.add(new Point(getPosX(i), getPosY(i)));
        Point headPos = new Point(getPosX(snake.size()-1),getPosY(snake.size()-1));
        if(snakeBody.contains(headPos))
        {
            mainTimer.stop();
            speedEffectTimer.stop();
            ghostEffectTimer.stop();
            resetSnakeAndBorderColors();
            playerLostTimer.start();
        }
    }
    public void checkFoodCollision(Rectangle head)  //checks if snake ate food
    {
        Iterator<SnakeFood> foodIterator = snakeFood.iterator();
        while (foodIterator.hasNext()) {
            SnakeFood food = foodIterator.next();
            if (food.getRectangle().intersects(head)) {
                foodIterator.remove(); 
                snake.add(new Rectangle(getPosX(snake.size()-1), getPosY(snake.size()-1), 18, 18));
                applyFoodEffects(food);
                break; 
            }
        }
        if(snakeFood.size() == 0) spawnFood();
    }
    public void checkCollision()    //checks ALL collisions
    {
        Rectangle head = snake.get(snake.size()-1);
        if(!ghostIsActive)
        {
            checkObstacleCollision(head);
            checkSelfCollision(head);
        }
        checkFoodCollision(head);
    }   
    public boolean isIntersecting(ArrayList<Rectangle> obstacles, Rectangle obstacle) // checks if 1 obstacle intersects with other obstacles
    {
        for(Rectangle obs : obstacles)
        {
            if(obs.intersects(obstacle)) return true;
        }
        return false;
    }
    public boolean isIntersectingFood(ArrayList<SnakeFood> snakeFood, Rectangle food) // check if 1 food/obstacle intersects with other foods
    {
        for(SnakeFood sFood : snakeFood)
        {
            if(sFood.getRectangle().intersects(food)) return true;
        }
        return false;
    }
    public GameLogicPanel(GameGui frame)    //constructor
    { 
        this.frame = frame;
        snakeColor = frame.getSnakeColor();
        levelColor = frame.getLevelColor();
        addKeyListener(this);
        setFocusable(true); 
        setBackground(levelColor);
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());  //setting the statistics UI (score and status effect)
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));  
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 40));
        scoreLabel.setForeground(Color.GREEN);
        scoreLabel.setAlignmentX(CENTER_ALIGNMENT);
        scoreLabel.setAlignmentY(Box.TOP_ALIGNMENT);
        scoreLabel.setBackground(Color.black);
        effectLabel.setFont(new Font("Arial", Font.BOLD, 40));
        effectLabel.setForeground(snakeColor);
        effectLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        effectLabel.setBackground(Color.black);
        statsPanel.setBackground(Color.DARK_GRAY);
        statsPanel.add(scoreLabel, Integer.valueOf(0));
        statsPanel.add(Box.createVerticalStrut(30));
        statsPanel.add(effectLabel, Integer.valueOf(1));
        add(statsPanel, BorderLayout.SOUTH);
        borderColor = (levelColor == Color.black) ? Color.white : Color.black;
        for(int x = 0; x<=800-30; x+=14)
        {
            border.add(new Rectangle(x,0,12,12));
            border.add(new Rectangle(x,600-53,12,12));
        }
        for(int y = 0; y<=600-53; y+=14)
        {
            border.add(new Rectangle(0,y,12,12));
            border.add(new Rectangle(800-30,y,12,12));
        }
        snake.add(new Rectangle(50,300,18,18));
        spawnObstacles();
        spawnFood();
        mainTimer = new Timer(moveTime, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                moveSnake();
                checkCollision();
                repaint(); 
            }
        });
        //timers

        mainTimer.start();
        speedEffectTimer = new Timer(speedTime, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                mainTimer.setDelay(moveTime);
                speedIsActive = false;
                resetSnakeAndBorderColors();
            }  
        });

        ghostEffectTimer = new Timer(ghostTime, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ghostIsActive = false;
                resetSnakeAndBorderColors();
            }  
        });

        playerLostTimer = new Timer(loseTime, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                frame.setContentPane(new GameOverScreen(frame,GameLogicPanel.this));
                frame.setSize(new Dimension(800,800));
                frame.revalidate();
                frame.repaint();
            }  
        });
        playerLostTimer.setRepeats(false);
    }
    
    public void keyPressed(KeyEvent e) {    //keyboard usage detection

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
           delta_x = -20;
           delta_y = 0; 
        }

        if (key == KeyEvent.VK_RIGHT) {
            delta_x = 20;
            delta_y = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            delta_x = 0;
            delta_y = 20;
        }

        if (key == KeyEvent.VK_UP) {
            delta_x = 0;
            delta_y = -20;
        }
    }
    public void moveSnake() //snake movement
    {
        int prevPosX = getPosX(snake.size()-1);
        int prevPosY = getPosY(snake.size()-1);
        setPosX(delta_x,snake.size()-1);
        setPosY(delta_y,snake.size()-1);
        for(int i = snake.size()-2; i> -1; i--)
        {
            int tempPosX = getPosX(i);
            int tempPosY = getPosY(i);
            setReadyPosX(prevPosX, i);
            setReadyPosY(prevPosY,i);
            prevPosX = tempPosX;
            prevPosY = tempPosY;
        }
    }
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public void paintComponent(Graphics gfx) //painting level,snake etc
    {
        super.paintComponent(gfx);
        gfx.setColor(snakeColor);
        for(Rectangle snakePart: snake)
        {
            gfx.fillRect(snakePart.x,snakePart.y,snakePart.width,snakePart.height);   
        }
        gfx.setColor(borderColor);
        for(Rectangle borderPart: border)
        {
            gfx.fillRect(borderPart.x,borderPart.y,borderPart.width,borderPart.height);
        }
        gfx.setColor(new Color(128, 80, 128));
        for(Rectangle obs: obstacles)
        {
            gfx.fillRect(obs.x,obs.y,obs.width,obs.height);
        }
        for(SnakeFood food: snakeFood)
        {
            gfx.setColor(food.getColor());
            gfx.fillRect(food.getRectangle().x,food.getRectangle().y,food.getRectangle().width,food.getRectangle().height);
        }
    }
}