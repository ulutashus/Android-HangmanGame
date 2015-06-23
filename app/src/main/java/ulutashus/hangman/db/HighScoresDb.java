package ulutashus.hangman.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HighScoresDb extends SQLiteOpenHelper
{

    private static final int VERSION = 1;
    private static final String NAME = "activity_highscores";

    public HighScoresDb(Context context)
    {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE activity_highscores (score INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXIST activity_highscores");
        onCreate(db);
    }
}
