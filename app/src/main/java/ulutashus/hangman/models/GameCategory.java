package ulutashus.hangman.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ulutashus.hangman.models.enums.Category;

public class GameCategory
{
    private Category category;
    private List<String> questions;

    public GameCategory(Category category)
    {
        this.category = category;
    }

    public Category getCategory()
    {
        return category;
    }

    public String getRandomQuestion()
    {
        Random random = new Random();
        int i = random.nextInt(questions.size());
        return questions.remove(i);
    }

    public void setQuestions(List<String> questions)
    {
        this.questions = new ArrayList<>(questions);
    }
}
