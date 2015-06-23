package ulutashus.androidmvc;

import android.content.res.Resources;

public abstract class Controller extends ObservableMap
{
    public final static String PrpNavigation = "PrpNavigation";
    private Resources resources;

    public Controller(Resources resources)
    {
        this.resources = resources;
    }

    public void onStart()
    {
        // nothing
    }

    protected <T> void navigateToView(Class<?> viewClass, T controller)
    {
        Navigation navigation = new Navigation(viewClass, controller);
        setNavigation(navigation);
    }

    protected Resources getResources()
    {
        return resources;
    }

    // region PrpNavigation
    private Navigation getNavigation()
    {
        return (Navigation) getValue(PrpNavigation);
    }

    private void setNavigation(Navigation navigation)
    {
        setValue(PrpNavigation, navigation);
    }
    // endregion
}
