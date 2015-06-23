package ulutashus.hangman.views.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class KeyboardAdapter extends BaseAdapter
{
    private String alphabet = null;

    public KeyboardAdapter(String alphabet)
    {
        this.alphabet = alphabet;
    }

    @Override
    public int getCount()
    {
        return alphabet.length();
    }

    @Override
    public Object getItem(int i)
    {
        return (Object) alphabet.charAt(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent)
    {
        Button button = null;
        if (convertView == null)
        {
            button = new Button(parent.getContext());
            button.setText(alphabet.charAt(i) + "");
            button.setPadding(0, 0, 0, 0);
            button.setTextSize(25);
            button.setFocusable(false);
            button.setFocusableInTouchMode(false);
            button.setClickable(false);
        } else
        {
            button = (Button) convertView;
        }
        return button;
    }
}
