package ulutashus.hangman.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ulutashus.hangman.models.enums.Category;

public class HighScoresDb extends SQLiteOpenHelper
{
    private static final int DbVersion = 1;
    private static final String TableName = "highscores";
    private static final String CategoryCol = "category";
    private static final String ScoreCol = "Score";
    private SQLiteDatabase db;

    public HighScoresDb(Context context)
    {
        super(context, TableName, null, DbVersion);
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query = String.format("CREATE TABLE %s (%s NVARCHAR(255), %s INTEGER)",
                TableName, CategoryCol, ScoreCol);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String query = String.format("DROP TABLE IF EXIST %s", TableName);
        db.execSQL(query);
        onCreate(db);
    }

    public int getHighScore(Category category)
    {
        String query = String.format("SELECT * FROM %s WHERE %s = '%s'",
                TableName, CategoryCol, category.toString());
        Cursor resultSet = db.rawQuery(query, null);
        resultSet.moveToFirst();
        int colIndex = resultSet.getColumnIndex(ScoreCol);
        if(resultSet.getCount() > 0)
            return resultSet.getInt(colIndex);
        return 0;
    }

    public void setHighScore(Category category, int score)
    {
        ContentValues values = new ContentValues();
        values.put(CategoryCol, category.toString());
        values.put(ScoreCol, score);
        db.insert(TableName, null, values);
    }
}
