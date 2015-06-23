package ulutashus.hangman.views;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.List;

import ulutashus.androidmvc.IPropertyListener;
import ulutashus.hangman.R;
import ulutashus.hangman.controllers.MenuScreenController;
import ulutashus.hangman.models.GameCategory;
import ulutashus.hangman.views.adapters.CategoriesAdapter;

public class MenuScreenView extends ulutashus.androidmvc.View<MenuScreenController>
{
    private Spinner spinner;

    public MenuScreenView()
    {
        super(MenuScreenController.class);
    }

    @Override
    protected void onCreate(Bundle data)
    {
        super.onCreate(data);

        setContentView(R.layout.activity_menu);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                GameCategory selectedType = (GameCategory) view.getTag();
                getController().setSelectedCategory(selectedType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }

    // region UI Listeners
    public void onStartGameClick(View v)
    {
        // Bir veri objesi olusturuyoruz
        Bundle data = new Bundle();
        data.putInt("questionType", spinner.getSelectedItemPosition());

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

    // region View
    @Override
    protected void initializeBindings()
    {
        super.initializeBindings();

        final Context view = this;
        getController().addListener(MenuScreenController.PrpAllCategories, new IPropertyListener()
        {
            @Override
            public void onUpdated(Object oldValue, Object newValue)
            {
                List<GameCategory> allCategories = (List<GameCategory>)newValue;
                CategoriesAdapter adapter = new CategoriesAdapter
                        (view, android.R.layout.simple_spinner_item, allCategories);
                spinner.setAdapter(adapter);
            }
        });
    }
    // endregion
}
