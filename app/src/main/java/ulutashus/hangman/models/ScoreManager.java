package ulutashus.hangman.models;

import java.util.TreeSet;

public class ScoreManager
{
    public static final double MAX_ROUND_POINT = 100;
    public static final int MAX_SECONDS = 100;
    public static final int WRONG_ANSWER_POINT = -10;
    public static final String VOWEL_LETTERS = "AEIÝOÖUÜ";
    // region Fields
    private int totalScore = 0;
    private int roundGuessScore = 0;
    private int roundTimeScore = 0;
    private String question;
    private double letterPoint;
    private TreeSet<Character> vowelLetters;
    private TreeSet<Character> consonantLetters;
    private long roundStartTime;
    // endregion

    public ScoreManager()
    {
        vowelLetters = new TreeSet<>();
        consonantLetters = new TreeSet<>();
    }

    public void newRound(String question)
    {
        this.question = question;
        this.roundGuessScore = 0;

        letterPoint = MAX_ROUND_POINT / QuestionManager.getLetterCount(question);

        for (int i = 0; i < question.length(); ++i)
        {
            char ch = question.charAt(i);
            if (VOWEL_LETTERS.indexOf(ch) != -1)
            {
                vowelLetters.add(ch);
            } else if (ch != ' ')
            {
                consonantLetters.add(ch);
            }
        }
        roundStartTime = System.currentTimeMillis();
    }

    public void endRound()
    {
        int elapsed = (int)(System.currentTimeMillis() - roundStartTime) / 1000;
        if (elapsed <= MAX_SECONDS)
        {
            roundTimeScore = MAX_SECONDS - elapsed;
            totalScore += roundTimeScore;
        }
    }

    public void increase(char ch)
    {
        double factor = 1.0;
        if (VOWEL_LETTERS.indexOf(ch) != -1)
        {
            factor = (consonantLetters.isEmpty()) ? 2.0 : 0.5;
            vowelLetters.remove(ch);
        } else
        {
            consonantLetters.remove(ch);
        }
        int pts = (int) (letterPoint * QuestionManager.searchLetter(question, ch) * factor);
        this.totalScore += pts;
        this.roundGuessScore += pts;
    }

    public void decrease()
    {
        if (this.totalScore > 0)
        {
            this.totalScore += WRONG_ANSWER_POINT;
            this.roundGuessScore += WRONG_ANSWER_POINT;
        }
    }

    public int getTotalScore()
    {
        return totalScore;
    }

    public int getRoundGuessScore()
    {
        return roundGuessScore;
    }

    public int getRoundTimeScore()
    {
        return roundTimeScore;
    }
} 