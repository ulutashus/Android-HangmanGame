package ulutashus.hangman.models;

public class QuestionManager
{
    private String question; // Sorulan kelime
    private int numOfLetter; // Kelimedeki harf sayisi (bosluksuz)
    private char[] letterOfKnown; // Bilinen harfler
    private int sizeLetterOfKnown; // letterOfKnown array uzunlugu
    private int numOfKnown; // Bilinen harf sayisi

    public static int getLetterCount(String question)
    {
        int counter = 0;

        for (int i = 0; i < question.length(); ++i)
            if (question.charAt(i) != ' ') ++counter;

        return counter;
    }

    public static int searchLetter(String question, char ch)
    {
        int counter = 0;

        for (int i = 0; i < question.length(); ++i)
            if (question.charAt(i) == ch) ++counter;

        return counter;
    }

    public boolean makeGuess(char button)
    {
        int numFound = searchLetter(question, button);

        if (numFound != 0)
        {
            letterOfKnown[sizeLetterOfKnown++] = button;
            numOfKnown += numFound;
            return true;
        }

        return false;
    }

    public String getQuestionToDisplay()
    {
        String result = "";
        char ch;
        int lastNewLine = 0;

        // Sorunun her bir harfi icin dongu
        for (int i = 0; i < question.length(); ++i)
        {
            if (question.charAt(i) == ' ')
            {
                // Yeni satira gecme ihtiyacini kontrol ediyor
                if (question.indexOf(' ', i + 1) - lastNewLine > 20)
                {
                    result += '\n';
                    lastNewLine = i;
                } else if (i + 1 != question.length()) // Sondaki boslugu
                    // koymamak icin
                    result += " ";
            } else
            {
                ch = '-';
                // Sorudaki bilinen harfleri tespit ediyor
                for (int j = 0; j < sizeLetterOfKnown; ++j)
                {
                    if (question.charAt(i) == letterOfKnown[j])
                    {
                        ch = letterOfKnown[j];
                        break;
                    }
                }

                result += ch;
            }
        }
        return result;
    }

    public boolean isComplete()
    {
        if (numOfLetter == numOfKnown) return true;
        else return false;
    }

    public String getQuestion()
    {
        return question;
    }

    public void setQuestion(String question)
    {
        this.question = question + " ";
        numOfLetter = getLetterCount(question);
        letterOfKnown = new char[numOfLetter];
        sizeLetterOfKnown = 0;
        numOfKnown = 0;
    }

    public int getLetterCount()
    {
        return numOfLetter;
    }
}
