package ulutashus.androidmvc;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

public abstract class View<T extends Controller> extends Activity
{
    private T controller;
    private Class<T> controllerType;
    private NavigationManager navigationManager;

    public View(Class<T> controllerType)
    {
        this.controllerType = controllerType;
        this.navigationManager = new NavigationManager(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        try
        {
            controller = navigationManager.getNavigationData(getIntent());
            if(controller == null)
            {
                controller = controllerType.getDeclaredConstructor(Resources.class).newInstance(getResources());
            }
            initializeBindings();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        getController().onStart();
    }

    protected void initializeBindings()
    {
        final View view = this;
        getController().addListener(Controller.PrpNavigation, new IPropertyListener()
        {
            @Override
            public void onUpdated(Object oldValue, Object newValue)
            {
                Navigation navigation = (Navigation) newValue;
                navigationManager.navigate(navigation);
            }
        });
    }

    // region Getter & Setter
    public T getController()
    {
        return controller;
    }

    public void setController(T controller)
    {
        this.controller = controller;
    }
    // endregion
}
