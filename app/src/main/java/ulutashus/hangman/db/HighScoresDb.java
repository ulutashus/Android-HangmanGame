package ulutashus.hangman.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ulutashus.hangman.models.HighScore;
import ulutashus.hangman.models.enums.Category;

public class HighScoresDb extends SQLiteOpenHelper
{
    private static final int DbVersion = 1;
    private static final String TableName = "highscores";
    private static final String CategoryCol = "category";
    private static final String ScoreCol = "Score";
    private SQLiteDatabase db;
    private final Context context;

    public HighScoresDb(Context context)
    {
        super(context, TableName, null, DbVersion);
        this.context = context;
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        this.db = db;
        String query = String.format("CREATE TABLE %s (%s INTEGER, %s INTEGER)",
                TableName, CategoryCol, ScoreCol);
        db.execSQL(query);

        for(Category category : Category.values())
        {
            setHighScore(category, 0);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        this.db =db;
        String query = String.format("DROP TABLE IF EXIST %s", TableName);
        db.execSQL(query);
        onCreate(db);
    }

    public List<HighScore> getAllHighScores()
    {
        String query = String.format("SELECT * FROM %s", TableName, CategoryCol);
        Cursor cursor = db.rawQuery(query, null);
        List<HighScore> highScores = new ArrayList<>();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            Integer category = cursor.getInt(cursor.getColumnIndex(CategoryCol));
            Integer score = cursor.getInt(cursor.getColumnIndex(ScoreCol));
            highScores.add(new HighScore(Category.createById(category), score));
        }
        return highScores;
    }

    public int getHighScore(Category category)
    {
        String query = String.format("SELECT * FROM %s WHERE %s = '%d'",
                TableName, CategoryCol, category.getId());
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int colIndex = cursor.getColumnIndex(ScoreCol);
        if(cursor.getCount() > 0)
            return cursor.getInt(colIndex);
        return 0;
    }

    public void setHighScore(Category category, int score)
    {
        String query = String.format("SELECT * FROM %s WHERE %s = '%d'",
                TableName, CategoryCol, category.getId());
        Cursor cursor = db.rawQuery(query, null);
        ContentValues values = new ContentValues();
        values.put(CategoryCol, category.getId());
        values.put(ScoreCol, score);
        if(cursor.getCount() == 0)
        {
            db.insert(TableName, null, values);
        }
        else
        {
            String where = String.format("%s=?", CategoryCol);
            db.update(TableName, values, where, new String[] {category.getId()+""});
        }
    }
}
