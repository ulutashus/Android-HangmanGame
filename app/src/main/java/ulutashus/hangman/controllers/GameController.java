package ulutashus.hangman.controllers;

import android.content.Context;
import android.content.res.Resources;

import ulutashus.androidmvc.Controller;
import ulutashus.androidmvc.Property;
import ulutashus.hangman.db.HighScoresDb;
import ulutashus.hangman.models.GameCategory;
import ulutashus.hangman.models.QuestionManager;
import ulutashus.hangman.models.ScoreManager;
import ulutashus.hangman.models.enums.GameStatus;

public class GameController extends Controller
{
    // Properties
    public final Property<GameCategory> GameCategory;
    public final Property<String> Question;
    public final Property<Integer> MoveCount;
    public final Property<GameStatus> GameStatus;
    public final Property<Integer> Score;
    // Fields
    private final int maxMoveCount = 9;
    private final HighScoresDb db;
    private final QuestionManager questionManager;
    private final ScoreManager scoreManager;
    private int highScore;

    public GameController(Resources resources, Context context)
    {
        super(resources, context);
        db = new HighScoresDb(context);
        questionManager = new QuestionManager();
        scoreManager = new ScoreManager();
        GameCategory = new Property<>();
        Question = new Property<>();
        MoveCount = new Property<>(0);
        GameStatus = new Property<>();
        Score = new Property<>(0);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        highScore = db.getHighScore(GameCategory.get().getCategory());
        GameCategory.notifyChange();
        cmdNextGame();
    }

    public void cmdNextGame()
    {
        String questionStr = GameCategory.get().getRandomQuestion();
        questionManager.setQuestion(questionStr);
        scoreManager.newRound(questionStr);
        // Notify
        Question.set(questionManager.getQuestionToDisplay());
        Score.set(scoreManager.getTotalScore());
        MoveCount.notifyChange();
    }

    public void cmdMakeGuess(char ch)
    {
        if (questionManager.makeGuess(ch))
        {
            scoreManager.increase(ch);
            if (questionManager.isComplete()) // Win
            {
                scoreManager.endRound();
                GameStatus.set(ulutashus.hangman.models.enums.GameStatus.Win);
            }
            Question.set(questionManager.getQuestionToDisplay());
        }
        else
        {
            MoveCount.set(MoveCount.get() + 1);
            if (MoveCount.get() == maxMoveCount) // Game Over
            {
                Question.set(questionManager.getQuestion());
                if (scoreManager.getTotalScore() > highScore)
                {
                    GameStatus.set(ulutashus.hangman.models.enums.GameStatus.NewRecord);
                    db.setHighScore(GameCategory.get().getCategory(), scoreManager.getTotalScore());
                }
                else
                {
                    GameStatus.set(ulutashus.hangman.models.enums.GameStatus.Lost);
                }
            }
        }
        Score.set(scoreManager.getTotalScore());
    }

    public QuestionManager getQuestionManager()
    {
        return questionManager;
    }

    public ScoreManager getScoreManager()
    {
        return scoreManager;
    }
}
