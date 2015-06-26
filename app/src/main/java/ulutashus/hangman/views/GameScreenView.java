package ulutashus.hangman.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import ulutashus.hangman.R;
import ulutashus.hangman.controllers.GameController;
import ulutashus.hangman.models.GameCategory;
import ulutashus.hangman.models.enums.GameStatus;
import ulutashus.hangman.views.adapters.KeyboardAdapter;

public class GameScreenView extends ulutashus.androidmvc.View<GameController>
{
    // region Fields
    private RelativeLayout all_Table;
    private GridView keyboard_Layout;
    private LinearLayout score_Table;
    private TextView question_Text;
    private TextView letterCount_Text;
    private TextView category_Text;
    private TextView score_Text;
    private TextView guessScore_Text;
    private TextView timeScore_Text;
    private Chronometer chronometer;
    private ImageView gameMessage_Image;
    private ImageView narrowTree_Image;
    private ImageView high_Image;
    private Button nextGame_Button;
    // endregion

    public GameScreenView()
    {
        super(GameController.class);
    }

    @Override
    public void onCreate(Bundle data)
    {
        super.onCreate(data);
        initializeFields();
        chronometer.start();
    }

    @Override
    protected void initializeBindings()
    {
        super.initializeBindings();
        getController().GameCategory.addListener(this::onCategoryChanged);
        getController().MoveCount.addListener(this::onMoveCountChanged);
        getController().Question.addListener(this::onQuestionChanged);
        getController().GameStatus.addListener(this::onGameStatusChanged);
        getController().Score.addListener(this::onScoreChanged);
    }

    // region UI Initialize
    private void initializeFields()
    {
        setContentView(R.layout.activity_gamescreen);

        all_Table = (RelativeLayout) findViewById(R.id.allTable);
        keyboard_Layout = (GridView) findViewById(R.id.keyboard);
        score_Table = (LinearLayout) findViewById(R.id.scoreTable);
        question_Text = (TextView) findViewById(R.id.word);
        letterCount_Text = (TextView) findViewById(R.id.wordLength);
        category_Text = (TextView) findViewById(R.id.typeName);
        score_Text = (TextView) findViewById(R.id.score);
        guessScore_Text = (TextView) findViewById(R.id.tahminPuani);
        timeScore_Text = (TextView) findViewById(R.id.surePuani);
        gameMessage_Image = (ImageView) findViewById(R.id.gameMessage);
        narrowTree_Image = (ImageView) findViewById(R.id.cinali);
        high_Image = (ImageView) findViewById(R.id.rekor);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        nextGame_Button = (Button) findViewById(R.id.nextButton);

        populateButtons();
    }

    private void populateButtons()
    {
        String alphabet = getString(R.string.alphabet);
        keyboard_Layout.setAdapter(new KeyboardAdapter(alphabet));
        keyboard_Layout.setOnItemClickListener((adapterView, view, i, l) -> {
            onLetterClick((Button) view);
        });
    }
    // endregion

    // region UI Events
    public void onLetterClick(Button button)
    {
        if(button.isEnabled())
        {
            button.setClickable(false);
            button.setEnabled(false);
            char ch = button.getText().charAt(0);
            getController().cmdMakeGuess(ch);
        }
    }

    public void onNextGameClick(View v)
    {
        initializeFields();
        getController().cmdNextGame();
        chronometer.start();
    }
    // endregion

    // region Bindings
    private void onCategoryChanged(GameCategory category)
    {
        int id = category.getCategory().getId();
        String categoryStr = getResources().getString(id);
        category_Text.setText(categoryStr);
    }

    private void onQuestionChanged(String question)
    {
        question_Text.setText(question);
        int letterCount = getController().getQuestionManager().getLetterCount();
        letterCount_Text.setText(String.valueOf(letterCount) + " Harf");
    }

    private void onMoveCountChanged(Integer moveCount)
    {
        narrowTree_Image.setImageResource(R.drawable.adama + moveCount);
    }

    private void onScoreChanged(Integer score)
    {
        score_Text.setText(score.toString());
    }

    private void onGameStatusChanged(GameStatus gameStatus)
    {
        if(gameStatus == GameStatus.Win)
        {
            all_Table.setBackgroundDrawable(getResources().getDrawable(R.drawable.green));

            // show scores
            guessScore_Text.setText(getController().getScoreManager().getRoundGuessScore() + "");
            timeScore_Text.setText(getController().getScoreManager().getRoundTimeScore() + "");
            score_Text.setText(getController().getScoreManager().getTotalScore() + "");
            score_Table.setVisibility(TableLayout.VISIBLE);
            // hide hangman
            narrowTree_Image.setVisibility(Button.GONE);
            // show congratulation message
            gameMessage_Image.setImageResource(R.drawable.win);
            gameMessage_Image.setVisibility(ImageView.VISIBLE);
            // display continue button
            nextGame_Button.setVisibility(ImageView.VISIBLE);
        }
        else
        {
            all_Table.setBackgroundDrawable(getResources().getDrawable(R.drawable.red));
            // display answer of Question
            question_Text.setText(getController().Question.get());
            // display game over message
            gameMessage_Image.setImageResource(R.drawable.gameover);
            gameMessage_Image.setVisibility(ImageView.VISIBLE);
            if (gameStatus == GameStatus.NewRecord)
            {
                high_Image.setVisibility(ImageView.VISIBLE);
                narrowTree_Image.setVisibility(ImageView.GONE);
            }
        }
        // hide keyboard
        keyboard_Layout.setVisibility(TableLayout.GONE);
        // stop chronometer
        chronometer.stop();
    }
    // endregion
}