package ulutashus.hangman.models;

import ulutashus.hangman.models.enums.Category;

public class HighScore
{
    private final Category category;
    private final Integer score;

    public HighScore(Category category, Integer score)
    {
        this.category = category;
        this.score = score;
    }

    public Category getCategory()
    {
        return category;
    }

    public Integer getScore()
    {
        return score;
    }
}
