package ulutashus.hangman.views;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.List;

import ulutashus.androidmvc.IPropertyListener;
import ulutashus.hangman.R;
import ulutashus.hangman.controllers.MenuController;
import ulutashus.hangman.models.GameCategory;
import ulutashus.hangman.views.adapters.CategoriesAdapter;

public class MenuScreenView extends ulutashus.androidmvc.View<MenuController>
{
    private Spinner spinner;

    public MenuScreenView()
    {
        super(MenuController.class);
    }

    @Override
    protected void onCreate(Bundle data)
    {
        super.onCreate(data);

        setContentView(R.layout.activity_menuscreen);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                GameCategory selectedType = (GameCategory) view.getTag();
                getController().SelectedCategory.set(selectedType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }

    @Override
    protected void initializeBindings()
    {
        super.initializeBindings();

        final Context view = this;
        getController().AllCategories.addListener(this::onAllCategoriesChanged);
    }

    // region UI Events
    public void onStartGameClick(View v)
    {
        getController().cmdNavigatePlayScreen();
    }

    public void onHighscoresClick(View v)
    {
        getController().cmdNavigateHighscoresScreen();
    }

    public void onExitClick(View v)
    {
        finish();
        System.exit(0);
    }
    // endregion

    // region Bindings
    private void onAllCategoriesChanged(List<GameCategory> newValue)
    {
        CategoriesAdapter adapter = new CategoriesAdapter(this, android.R.layout.simple_spinner_item, newValue);
        spinner.setAdapter(adapter);
    }
    // endregion
}
