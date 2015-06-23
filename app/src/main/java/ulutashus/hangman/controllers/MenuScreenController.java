package ulutashus.hangman.controllers;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import ulutashus.androidmvc.Controller;
import ulutashus.hangman.HighscoresScreen;
import ulutashus.hangman.models.Categories;
import ulutashus.hangman.models.GameCategory;
import ulutashus.hangman.views.PlayScreenView;

public class MenuScreenController extends Controller
{
    public final static String PrpSelectedCategory = "PrpSelectedCategory";
    public final static String PrpAllCategories = "PrpAllCategories";

    public MenuScreenController(Resources resources)
    {
        super(resources);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        // Initialize Categories
        List<GameCategory> allCategories = new ArrayList<GameCategory>();
        for(Categories category : Categories.values())
        {
            GameCategory type = new GameCategory(category);
            allCategories.add(type);
        }
        setAllCategories(allCategories);
    }

    // region Commands
    public void cmdNavigatePlayScreen()
    {
        PlayScreenController playController = new PlayScreenController(getResources());
        playController.setGameCategory(getSelectedCategory());
        navigateToView(PlayScreenView.class, playController);
    }

    public void cmdNavigateHighscoresScreen()
    {
        navigateToView(HighscoresScreen.class, null);
    }
    // endregion

    // region PrpSelectedCategory
    public GameCategory getSelectedCategory()
    {
        return (GameCategory) getValue(PrpSelectedCategory);
    }

    public void setSelectedCategory(GameCategory selectedCategory)
    {
        setValue(PrpSelectedCategory, selectedCategory);
    }
    // endregion

    // region PrpAllCategories
    public List<GameCategory> getAllCategories()
    {
        return (List<GameCategory>) getValue(PrpAllCategories);
    }

    private void setAllCategories(List<GameCategory> allCategories)
    {
        setValue(PrpAllCategories, allCategories);
    }
    // endregion
}
