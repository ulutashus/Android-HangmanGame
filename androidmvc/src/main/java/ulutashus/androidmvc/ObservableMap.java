package ulutashus.androidmvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObservableMap
{
    private Map<String, Object> map;
    private Map<String, List<IPropertyListener>> propertyListeners;

    public ObservableMap()
    {
        map = new HashMap<String, Object>();
        propertyListeners = new HashMap<String, List<IPropertyListener>>();
    }

    public Object getValue(String key)
    {
        return map.get(key);
    }

    public void setValue(String key, Object value)
    {
        Object oldValue = map.get(key);
        map.put(key, value);

        // Notify Listeners
        List<IPropertyListener> listeners = propertyListeners.get(key);
        if (listeners != null)
        {
            for (IPropertyListener listener : listeners)
            {
                listener.onUpdated(oldValue, value);
            }
        }
    }

    public void addListener(String key, IPropertyListener listener)
    {
        List<IPropertyListener> repo = propertyListeners.get(key);
        if (repo == null)
        {
            repo = new ArrayList<IPropertyListener>();
        }
        repo.add(listener);
        propertyListeners.put(key, repo);
    }
}
