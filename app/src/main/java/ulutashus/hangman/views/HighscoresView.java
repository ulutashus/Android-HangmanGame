package ulutashus.hangman.views;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ulutashus.hangman.R;
import ulutashus.hangman.controllers.HighScoresController;
import ulutashus.hangman.db.HighScoresDb;
import ulutashus.hangman.models.HighScore;
import ulutashus.hangman.views.adapters.HighScoresAdapter;

public class HighScoresView  extends ulutashus.androidmvc.View<HighScoresController>
{
    private ListView highScores_list;

    public HighScoresView()
    {
        super(HighScoresController.class);
    }

    @Override
    protected void onCreate(Bundle data)
    {
        super.onCreate(data);
        initializeFields();
    }

    @Override
    protected void initializeBindings()
    {
        super.initializeBindings();
        getController().HighScores.addListener(this::onHighScoresChanged);
    }

    private void initializeFields()
    {
        setContentView(R.layout.activity_highscores);
        highScores_list = (ListView) findViewById(R.id.highScoresList);
    }

    private void onHighScoresChanged(List<HighScore> highScores)
    {
        HighScoresAdapter adapter = new HighScoresAdapter(highScores);
        highScores_list.setAdapter(adapter);
    }
}
