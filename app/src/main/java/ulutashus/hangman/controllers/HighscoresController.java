package ulutashus.hangman.controllers;

import android.content.Context;
import android.content.res.Resources;

import ulutashus.androidmvc.Controller;
import ulutashus.hangman.db.HighScoresDb;

public class HighScoresController extends Controller
{
    // Fields
    private final HighScoresDb db;

    public HighScoresController(Resources resources, Context context)
    {
        super(resources, context);
        db = new HighScoresDb(context);
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }
}
