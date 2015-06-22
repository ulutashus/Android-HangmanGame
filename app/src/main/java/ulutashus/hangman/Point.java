package ulutashus.hangman;

import java.util.TreeSet;

public class Point
{
    public static final double MAX_ROUND_POINT = 100;
    public static final int MAX_SECONDS = 100;
    public static final int WRONG_ANSWER_POINT = -10;
    public static final String VOWEL_LETTERS = "AEIÝOÖUÜ";
    private int total = 0;
    private int round = 0;
    private String question;
    private double letterPoint;
    private TreeSet<Character> vowelLetters;
    private TreeSet<Character> consonantLetters;

    public Point()
    {
        vowelLetters = new TreeSet<Character>();
        consonantLetters = new TreeSet<Character>();
    }

    public void newRound(String question)
    {
        this.question = question;
        letterPoint = MAX_ROUND_POINT / Question.getNumOfLetter(question);

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
    }

    public Integer endRound(int seconds)
    {
        this.round = 0;
        if (seconds <= MAX_SECONDS)
        {
            this.total += MAX_SECONDS - seconds;
            return MAX_SECONDS - seconds;
        }
        return 0;
    }

    public Integer getTotal()
    {
        return total;
    }

    public Integer getRound()
    {
        return round;
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
        int pts = (int) (letterPoint * Question.searchLetter(question, ch) * factor);
        this.total += pts;
        this.round += pts;
    }

    public void decrease()
    {
        if (this.total > 0)
        {
            this.total += WRONG_ANSWER_POINT;
            this.round += WRONG_ANSWER_POINT;
        }
    }
} 