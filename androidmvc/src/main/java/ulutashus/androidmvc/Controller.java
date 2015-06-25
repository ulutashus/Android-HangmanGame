package ulutashus.androidmvc;

import android.content.Context;
import android.content.res.Resources;

public abstract class Controller
{
    public final Property<Navigation> Navigation;
    private Resources resources;
    private Context context;

    public Controller(Resources resources, Context context)
    {
        this.resources = resources;
        this.context = context;
        this.Navigation = new Property<>();
    }

    public void onStart()
    {
        // nothing
    }

    protected <T> void navigateToView(Class<?> viewClass, T controller)
    {
        Navigation navigation = new Navigation(viewClass, controller);
        Navigation.set(navigation);
    }

    protected Resources getResources()
    {
        return resources;
    }

    protected Context getContext()
    {
        return context;
    }
}
