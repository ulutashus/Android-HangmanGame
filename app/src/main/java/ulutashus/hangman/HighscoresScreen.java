package ulutashus.hangman;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ulutashus.hangman.db.HighScoresDb;

public class HighscoresScreen extends Activity
{
    private TextView[] categories_Text = new TextView[4];
    private HighScoresDb database;

    @Override
    protected void onCreate(Bundle data)
    {
        super.onCreate(data);

        relateXML();

/** DATABASE */
        database = new HighScoresDb(this);
        SQLiteDatabase db = database.getReadableDatabase();
        String[] SELECT = {"puan"};
        Cursor cursor = db.query("activity_highscores", SELECT, null, null, null, null, null);
        startManagingCursor(cursor);
        for (int i = 0; cursor.moveToNext(); ++i)
        {
            Integer score = cursor.getInt(cursor.getColumnIndex("puan"));
            categories_Text[i].setText(score.toString());
        }
    }

    private void relateXML()
    {
        setContentView(R.layout.activity_highscores);

        categories_Text[0] = (TextView) findViewById(R.id.kategori1);
        categories_Text[1] = (TextView) findViewById(R.id.kategori2);
        categories_Text[2] = (TextView) findViewById(R.id.kategori3);
        categories_Text[3] = (TextView) findViewById(R.id.kategori4);
    }

    public void menuListener(View v)
    {
        finish();
    }
}
