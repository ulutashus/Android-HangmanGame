package ulutashus.hangman.models.enums;

import ulutashus.hangman.R;

public enum Category
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

    public int getRepositoryId()
    {
        switch (this)
        {
            case Adage:
                return R.array.adage;
            case Famous:
                return R.array.famouses;
            case City:
                return R.array.cities;
            case Country:
                return R.array.countries;
        }
        return 0;
    }
}
