package texus.kavi;

import android.app.Application;

/**
 * Created by sandeep on 11/06/16.
 */
public class KaviApplication  extends Application {


    private static KaviApplication ourInstance = new KaviApplication();

    public static KaviApplication getInstance() {
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = this;
        loadConfiguration();
    }

    private void loadConfiguration() {

    }

}
