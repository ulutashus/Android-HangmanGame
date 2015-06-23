package ulutashus.hangman.models;

import ulutashus.hangman.R;

public enum Categories
{
    Adage,
    Famous,
    City,
    Country;

    public int getId()
    {
        switch (this)
        {
            case Adage:
                return R.string.adage;
            case Famous:
                return R.string.famous;
            case City:
                return R.string.city;
            case Country:
                return R.string.country;
        }
        return 0;
    }
}
