package ulutashus.androidmvc;

import java.util.ArrayList;
import java.util.List;

public class Property<T>
{
    private T value;
    private List<IPropertyListener<T>> listeners;

    public Property()
    {
        listeners = new ArrayList<>();
    }

    public Property(T value)
    {
        this();
        this.value = value;
    }

    public T get()
    {
        return value;
    }

    public void set(T value)
    {
        this.value = value;
        notifyChange();
    }

    public void addListener(IPropertyListener<T> listener)
    {
        listeners.add(listener);
    }

    public void notifyChange()
    {
        // Notify to Listeners
        for (IPropertyListener<T> listener : listeners)
        {
            listener.onUpdated(value);
        }
    }
}
