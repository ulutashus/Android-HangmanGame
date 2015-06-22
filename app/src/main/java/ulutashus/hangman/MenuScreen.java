package ulutashus.hangman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

public class MenuScreen extends Activity
{
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle data)
    {
        super.onCreate(data);

        setContentView(R.layout.activity_menu);
        spinner = (Spinner) findViewById(R.id.spinner);
    }

    /**
     * Yeni Oyun tusunun listener'i
     */
    public void startGameListener(View v)
    {
        // Bir veri objesi olusturuyoruz
        Bundle data = new Bundle();

        // Soru kategorisini veriye yerlestiriyoruz
        data.putInt("questionType", spinner.getSelectedItemPosition());

        Intent newGame = new Intent(this, PlayScreen.class);
        newGame.putExtras(data);

        startActivity(newGame);
    }

    /**
     * Rekor tusunun listener'i
     */
    public void highscoresListener(View v)
    {
        Intent highscores = new Intent(this, HighscoresScreen.class);
        startActivity(highscores);
    }

    /**
     * Cikis tusunun listener'i
     */
    public void exitListener(View v)
    {
        finish();
        System.exit(0);
    }
}
