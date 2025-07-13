import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

class DataLoader
{
    String csvFile = "HighScores.csv";
    String line;
    int highScore;

    public int loadHighScore()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); //skipping title of csv file
            while ((line = br.readLine()) != null) {
                highScore = Integer.parseInt(line); //loading the current highest score
            }
        } catch (IOException e) {
            System.out.println("Could not read highscore");
        }
        return highScore;
    }
    public void updateHighScore(int currentHighScore)
    {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
                writer.write("HighScore\n");
                writer.write(String.valueOf(currentHighScore)); // writing the newest highest score in the file
            } catch (IOException e) {
                System.out.println("Could not write highscore");
            }
    }
}
