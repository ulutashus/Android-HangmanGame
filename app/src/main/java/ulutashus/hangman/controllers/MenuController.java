package ulutashus.hangman.controllers;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ulutashus.androidmvc.Controller;
import ulutashus.androidmvc.Property;
import ulutashus.hangman.models.GameCategory;
import ulutashus.hangman.models.enums.Category;
import ulutashus.hangman.views.GameScreenView;
import ulutashus.hangman.views.HighScoresView;

public class MenuController extends Controller
{
    public final Property<GameCategory> SelectedCategory;
    public final Property<List<GameCategory>> AllCategories;

    public MenuController(Resources resources, Context context)
    {
        super(resources, context);
        SelectedCategory = new Property<>();
        AllCategories = new Property<>();
    }

    @Override
    public void onStart()
    {
        super.onStart();

        // Initialize Category
        List<GameCategory> allCategories = new ArrayList<>();
        for(Category category : Category.values())
        {
            GameCategory gameCategory = new GameCategory(category);
            // Load questions
            int repoId = category.getRepositoryId();
            String[] questions = getResources().getStringArray(repoId);
            gameCategory.setQuestions(Arrays.asList(questions));
            // Add type
            allCategories.add(gameCategory);
        }
        AllCategories.set(allCategories);
    }

    // region Commands
    public void cmdNavigatePlayScreen()
    {
        GameController playController =
                new GameController(getResources(), getContext());
        playController.GameCategory.set(SelectedCategory.get());
        navigateToView(GameScreenView.class, playController);
    }

    public void cmdNavigateHighscoresScreen()
    {
        navigateToView(HighScoresView.class, null);
    }
    // endregion
}
