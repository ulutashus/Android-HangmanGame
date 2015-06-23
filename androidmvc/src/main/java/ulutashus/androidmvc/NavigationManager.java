package ulutashus.androidmvc;

import android.app.Activity;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class NavigationManager
{
    private static Map<String, Controller> NavigationDataMap = new HashMap<String, Controller>();
    private final Activity activity;

    public NavigationManager(Activity activity)
    {
        this.activity = activity;
    }

    public <T extends Controller> void navigate(Navigation<T> navigation)
    {
        // Saving navigation data
        String navigationUuid = UUID.randomUUID().toString();
        NavigationDataMap.put(navigationUuid, navigation.getController());
        // Creating target intent
        Intent intent = new Intent(activity, navigation.getViewType());
        // Putting navigation data into Intent
//        Bundle data = new Bundle();
//        data.putString("id", navigationUuid);
        intent.putExtra("id", navigationUuid);
        // Start target activity
        activity.startActivity(intent);
    }

    public <T extends Controller> T getNavigationData(Intent intent)
    {
        if(intent != null)
        {
            String navigationId = intent.getStringExtra("id");
            return (T) NavigationDataMap.get(navigationId);
        }
        return null;
    }
}
