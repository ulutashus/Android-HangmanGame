package ulutashus.hangman.models;

public class GameCategory
{
    private Categories category;
    private int highScore;

    public GameCategory(Categories category)
    {
        this.category = category;
    }

    public int getHighScore()
    {
        return highScore;
    }

    public void setHighScore(int highScore)
    {
        this.highScore = highScore;
    }

    public Categories getCategory()
    {
        return category;
    }
}
