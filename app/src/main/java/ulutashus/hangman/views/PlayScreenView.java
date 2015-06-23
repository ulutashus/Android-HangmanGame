package ulutashus.hangman.views;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import ulutashus.hangman.Point;
import ulutashus.hangman.Question;
import ulutashus.hangman.R;
import ulutashus.hangman.controllers.PlayScreenController;
import ulutashus.hangman.db.HighScoresDb;
import ulutashus.hangman.views.adapters.KeyboardAdapter;

public class PlayScreenView extends ulutashus.androidmvc.View<PlayScreenController>
{
    private RelativeLayout all_Table;
    private GridView keyboard_Layout;
    private TableLayout score_Table;
    private TextView word_Text;
    private TextView wordLength_Text;
    private TextView typeName_Text;
    private TextView totalPoints_Text;
    private TextView answerPoint_Text;
    private TextView timePoint_Text;
    private Chronometer chronometer;
    private ImageView gameMessage_Image;
    private ImageView narrowTree_Image;
    private ImageView high_Image;
    private FrameLayout continueGame_Button;

    private Question game; // Soru
    private int narrowTreeID; // Gosterilen dar agaci resim idsi
    private int gameType;
    private Point points;
    private HighScoresDb database;
    private int[] highscores = new int[4];
    private boolean modified = false;

    public PlayScreenView()
    {
        super(PlayScreenController.class);
    }

    @Override
    public void onCreate(Bundle data)
    {
        super.onCreate(data);

        getHighScores();
        // Veriden oyun tipi ve zorlugu okunuyor
//        data = getIntent().getExtras();
//        gameType = data.getInt("questionType");

        // strings.xml dosyasindan sorular okunuyor
        Question.loadQuestions(getResources());

        // Yeni oyun baslatiliyor
        newGame();
    }

    private void getHighScores()
    {
        database = new HighScoresDb(this);
        SQLiteDatabase db = database.getReadableDatabase();
        String[] SELECT = {"puan"};
        Cursor cursor = db.query("activity_highscores", SELECT, null, null, null, null, null);
        startManagingCursor(cursor);
        for (int i = 0; cursor.moveToNext(); ++i)
        {
            highscores[i] = cursor.getInt(cursor.getColumnIndex("puan"));
        }
    }

    /**
     * Yeni Oyun
     */
    private void newGame()
    {
        relateXML();
        populateButtons();

        game = new Question(gameType);
        points = new Point();
        points.newRound(game.getQuestion());
        narrowTreeID = R.drawable.adama;

        // Display
        chronometer.start();
        narrowTree_Image.setImageResource(narrowTreeID);
        word_Text.setText(game.createStringToDisplay());
        wordLength_Text.setText(String.valueOf(game.getNumOfLetter()) + " Harf");
        typeName_Text.setText(Question.typeNames[gameType]);
    }

    /**
     * Sirdaki Oyun
     */
    private void nextGame()
    {
        relateXML();

        game = new Question(gameType);
        points.newRound(game.getQuestion());

        // Display
        chronometer.start();
        totalPoints_Text.setText(points.getTotal().toString());
        narrowTree_Image.setImageResource(narrowTreeID);
        word_Text.setText(game.createStringToDisplay());
        wordLength_Text.setText(String.valueOf(game.getNumOfLetter()) + " Harf");
        typeName_Text.setText(Question.typeNames[gameType]);
    }

    /**
     * PlayScreenView.java'yi activity_playscreen.xmlcreen.xml ile iliskilendiriyor.
     */
    private void relateXML()
    {
        setContentView(R.layout.activity_playscreen);

        all_Table = (RelativeLayout) findViewById(R.id.allTable);
        keyboard_Layout = (GridView) findViewById(R.id.keyboard);
        score_Table = (TableLayout) findViewById(R.id.pointTable);
        word_Text = (TextView) findViewById(R.id.word);
        wordLength_Text = (TextView) findViewById(R.id.wordLength);
        typeName_Text = (TextView) findViewById(R.id.typeName);
        totalPoints_Text = (TextView) findViewById(R.id.points);
        answerPoint_Text = (TextView) findViewById(R.id.tahminPuani);
        timePoint_Text = (TextView) findViewById(R.id.surePuani);
        gameMessage_Image = (ImageView) findViewById(R.id.gameMessage);
        narrowTree_Image = (ImageView) findViewById(R.id.cinali);
        high_Image = (ImageView) findViewById(R.id.rekor);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        continueGame_Button = (FrameLayout) findViewById(R.id.continueLayout);
    }

    /**
     * Dogru Cevap
     */
    private void rightAnswer(char ch)
    {
        // soru guncelleniyor
        word_Text.setText(game.createStringToDisplay());
        // puan guncelleniyor
        points.increase(ch);
        totalPoints_Text.setText(points.getTotal().toString());

        // Win
        if (game.win())
        {
            win();
        }
    }

    /**
     * Yanlis Cevap
     */
    private void wrongAnswer()
    {
        // dar agaci resmi guncelleniyor
        ++narrowTreeID;
        narrowTree_Image.setImageResource(narrowTreeID);

        // Game Over
        if (narrowTreeID == R.drawable.adamj)
        {
            lost();
        }
    }

    /**
     * Kazanma Durumu
     */
    private void win()
    {
        all_Table.setBackgroundDrawable(getResources().getDrawable(R.drawable.green));
        // puanlar ayarlaniyor
        long elapsedSecond = (SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000;
        answerPoint_Text.setText(points.getRound().toString());
        timePoint_Text.setText(points.endRound((int) elapsedSecond).toString());
        totalPoints_Text.setText(points.getTotal().toString());

        score_Table.setVisibility(TableLayout.VISIBLE);
        narrowTree_Image.setVisibility(Button.GONE);
        // klavye kaldiriliyor
        keyboard_Layout.setVisibility(TableLayout.GONE);
        // tebrik mesaji veriliyor
        gameMessage_Image.setImageResource(R.drawable.win);
        gameMessage_Image.setVisibility(ImageView.VISIBLE);
        // kronometre durduruluyor
        chronometer.stop();
        // devam et butonu gosteriliyor
        continueGame_Button.setVisibility(ImageView.VISIBLE);
    }

    /**
     * Kaybetme Durumu
     */
    private void lost()
    {
        if (points.getTotal() > highscores[gameType])
        {
            highscores[gameType] = points.getTotal();
            modified = true;
            high_Image.setVisibility(ImageView.VISIBLE);
            narrowTree_Image.setVisibility(ImageView.GONE);
        }
        all_Table.setBackgroundDrawable(getResources().getDrawable(R.drawable.red));
        // klavye kaldiriliyor
        keyboard_Layout.setVisibility(TableLayout.GONE);
        // sorunun cevabi gosteriliyor
        word_Text.setText(game.getQuestion());
        // oyunu kaybetme mesaji veriliyor
        gameMessage_Image.setImageResource(R.drawable.gameover);
        gameMessage_Image.setVisibility(ImageView.VISIBLE);
        // kronometre durduruluyor
        chronometer.stop();
    }

    /**
     * Devam Et Tusu
     */
    public void continueListener(View v)
    {
        nextGame();
    }

    /**
     * Menu Tusu
     */
    public void menuListener(View v)
    {
        finish();
    }

    public void finish()
    {
        if (modified)
        {
            SQLiteDatabase db = database.getReadableDatabase();
            db.delete("activity_highscores", null, null);
            for (int i = 0; i < 4; ++i)
            {
                ContentValues data = new ContentValues();
                data.put("puan", highscores[i]);
                db.replaceOrThrow("activity_highscores", null, data);
            }
        }
        super.finish();
    }

    private void populateButtons()
    {
        String alphabet = getString(R.string.alphabet);
        keyboard_Layout.setAdapter(new KeyboardAdapter(alphabet));
        keyboard_Layout.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Button button = (Button) view;
                onLetterClick(button);
            }
        });
    }

    public void onLetterClick(View view)
    {
        view.setClickable(false);
        view.setEnabled(false);

        Button button = (Button) view;

        char ch = button.getText().charAt(0);
        if (game.checkAnswer(ch)) rightAnswer(ch);
        else wrongAnswer();
    }

    @Override
    protected void initializeBindings()
    {
        super.initializeBindings();
    }
}