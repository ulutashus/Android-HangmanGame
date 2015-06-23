package ulutashus.hangman.controllers;

import android.content.res.Resources;

import ulutashus.androidmvc.Controller;
import ulutashus.hangman.models.GameCategory;

public class PlayScreenController extends Controller
{
    public static final String PrpGameCategory = "PrpGameCategory";

    public PlayScreenController(Resources resources)
    {
        super(resources);
    }

    // region PrpGameType
    public GameCategory getGameCategory()
    {
        return (GameCategory) getValue(PrpGameCategory);
    }

    public void setGameCategory(GameCategory gameCategory)
    {
        setValue(PrpGameCategory, gameCategory);
    }
    // endregion
}
