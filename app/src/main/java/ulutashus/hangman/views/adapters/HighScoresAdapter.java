package ulutashus.hangman.views.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ulutashus.hangman.R;
import ulutashus.hangman.models.HighScore;

public class HighScoresAdapter extends BaseAdapter
{
    private final List<HighScore> highScores;

    public HighScoresAdapter(List<HighScore> highScores)
    {
        this.highScores = highScores;
    }

    @Override
    public int getCount()
    {
        return highScores.size();
    }

    @Override
    public Object getItem(int i)
    {
        return highScores.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View rowView = viewGroup.inflate(viewGroup.getContext(), R.layout.list_item_two_column, null);
        TextView text1 = (TextView)rowView.findViewById(R.id.text1);
        TextView text2 = (TextView)rowView.findViewById(R.id.text2);
        HighScore highScore = (HighScore) getItem(i);

        int id = highScore.getCategory().getId();
        String categoryStr = viewGroup.getContext().getResources().getString(id);
        text1.setText(categoryStr);
        text2.setText(highScore.getScore().toString());
        return rowView;
    }
}
