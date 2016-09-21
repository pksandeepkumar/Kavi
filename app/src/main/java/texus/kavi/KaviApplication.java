package texus.kavi;

import android.app.Application;

import java.util.Random;

/**
 * Created by sandeep on 11/06/16.
 */
public class KaviApplication  extends Application {

    public static final String BASE_URL  = "http://texusapps.com/AppXmls/Kavi";
    public static final String INDEX_URL  = BASE_URL + "/index.xml";

    int minHeight;
    int maxHeight;


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

        minHeight = getResources()
                .getDimensionPixelSize(R.dimen.item_card_image_min_height);
        maxHeight = getResources()
                .getDimensionPixelSize(R.dimen.item_card_image_max_height);
    }

    public int getHeight() {

        Random random = new Random();
        int height = minHeight + random.nextInt(maxHeight - minHeight);
        return height;
    }

}
