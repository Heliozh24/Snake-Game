import java.awt.Color;
import java.awt.Rectangle;

class SnakeFood
{
    private Color foodColor;
    private Rectangle rectangle;
    public SnakeFood(Color foodColor, Rectangle rectangle)
    {
        this.foodColor = foodColor;
        this.rectangle = rectangle;
    }  
    public Color getColor()
    {
        return foodColor;
    } 
    public Rectangle getRectangle()
    {
        return rectangle;
    }
}