package ulutashus.androidmvc;

public class Navigation<T>
{
    private final Class<T> viewType;
    private final T controller;

    public Navigation(Class<T> viewType)
    {
        this.viewType = viewType;
        this.controller = null;
    }
    
    public Navigation(Class<T> viewType, T controller)
    {
        this.viewType = viewType;
        this.controller = controller;
    }

    public Class<T> getViewType()
    {
        return viewType;
    }

    public T getController()
    {
        return controller;
    }
}
