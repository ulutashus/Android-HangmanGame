package ulutashus.hangman;

import android.content.res.Resources;

import java.util.Random;
import java.util.TreeSet;

public class Question
{
    public static String[] typeNames = {"Atasözleri", "Ýsim", "Þehir", "Ülke"};
    private static String[][] QUESTIONS;// Tum sorularin tutuldugu array
    private static TreeSet<Integer>[] asked = new TreeSet[]{new TreeSet<Integer>(), new TreeSet<Integer>(), new TreeSet<Integer>(), new TreeSet<Integer>()};
    private String question; // Sorulan kelime
    private int numOfLetter; // Kelimedeki harf sayisi (bosluksuz)
    private char[] letterOfKnown; // Bilinen harfler
    private int sizeLetterOfKnown; // letterOfKnown array uzunlugu
    private int numOfKnown; // Bilinen harf sayisi

    public Question(int qqType)
    {
        Random random = new Random();
        Integer rand;
        boolean isAsked;

        do
        {
            rand = random.nextInt(QUESTIONS[qqType].length);
            isAsked = !asked[qqType].add(rand);
            if (asked[qqType].size() == QUESTIONS[qqType].length) asked[qqType].clear();
        } while (isAsked);

        question = QUESTIONS[qqType][rand] + " ";
        // Ekrana sigmama durumlarinda dogru sekilde satir atlanmasi icin
        // sorunun sonuna
        // bosluk karakteri konuluyor.
        numOfLetter = getNumOfLetter(question);
        letterOfKnown = new char[numOfLetter];
        sizeLetterOfKnown = 0;
        numOfKnown = 0;
    }

    /*
     * strings.xml dosyasindaki string array'leri yukler.
     */
    static public void loadQuestions(Resources res)
    {
        QUESTIONS = new String[4][];
        QUESTIONS[0] = res.getStringArray(R.array.adage);
        QUESTIONS[1] = res.getStringArray(R.array.famouses);
        QUESTIONS[2] = res.getStringArray(R.array.cities);
        QUESTIONS[3] = res.getStringArray(R.array.countries);
    }

    /*
     * Sorudaki harf sayisini hesaplar ve return eder.
     */
    public static int getNumOfLetter(String question)
    {
        int counter = 0;

        for (int i = 0; i < question.length(); ++i)
            if (question.charAt(i) != ' ') ++counter;

        return counter;
    }

    /*
     * Fonksiyona yollanan harfi soru icerisinde arar, ve kac adet bulundugunu
     * return eder.
     */
    public static int searchLetter(String question, char ch)
    {
        int counter = 0;

        for (int i = 0; i < question.length(); ++i)
            if (question.charAt(i) == ch) ++counter;

        return counter;
    }

    /*
     * Kullanicinin verdigi cevabin dogrulunu kontrol eder.
     */
    public boolean checkAnswer(char button)
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

    /*
     * Sorunun, ekranda gosterilecek halinin String'i olusturulur ve return
     * edilir.
     */
    public String createStringToDisplay()
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

    /*
     * Oyunun kazanilip kazanilmadigini return eder.
     */
    public boolean win()
    {
        if (numOfLetter == numOfKnown) return true;
        else return false;
    }

    // STATIC HELPER FUNCTIONS

    // GETTERS
    public String getQuestion()
    {
        return question;
    }

    public int getNumOfLetter()
    {
        return numOfLetter;
    }
}
