package ulutashus.hangman.controllers;

import android.content.Context;
import android.content.res.Resources;

import java.util.List;

import ulutashus.androidmvc.Controller;
import ulutashus.androidmvc.Property;
import ulutashus.hangman.db.HighScoresDb;
import ulutashus.hangman.models.HighScore;

public class HighScoresController extends Controller
{
    // Properties
    public final Property<List<HighScore>> HighScores;
    // Fields
    private final HighScoresDb db;

    public HighScoresController(Resources resources, Context context)
    {
        super(resources, context);
        db = new HighScoresDb(context);
        HighScores = new Property<>();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        HighScores.set(db.getAllHighScores());
    }
}
