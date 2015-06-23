package ulutashus.hangman.views.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ulutashus.hangman.models.GameCategory;

public class CategoriesAdapter extends ArrayAdapter<String>
{
    private final List<GameCategory> categories;

    public CategoriesAdapter(Context context, int textViewResourceId, List<GameCategory> categories)
    {
        super(context, textViewResourceId);
        this.categories = categories;

        for(GameCategory category : categories)
        {
            int id = category.getCategory().getId();
            String categoryStr = context.getResources().getString(id);
            add(categoryStr);
        }

        // Specify the layout to use when the list of choices appears
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = super.getView(position, convertView, parent);
        view.setTag(categories.get(position));
        return view;
    }
}
