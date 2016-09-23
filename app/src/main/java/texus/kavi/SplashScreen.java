package texus.kavi;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.ArrayList;

import texus.kavi.controls.AVLoadingIndicatorView;
import texus.kavi.datamodel.GalleryData;
import texus.kavi.db.Databases;
import texus.kavi.preference.SavedPreferance;
import texus.kavi.utility.LOG;
import texus.kavi.utility.Utility;

/**
 * Created by sandeep on 21/09/16.
 */
public class SplashScreen extends BaseActivity {

    private AVLoadingIndicatorView aviProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();

    }

    @Override
    public void init() {

//        aviProgress = (AVLoadingIndicatorView) this.findViewById(R.id.aviProgress);

        LoadDataTask loadDataTask = new LoadDataTask(this);
        loadDataTask.execute();

//        FetchNewImagesTask fetchNewImagesTask = new FetchNewImagesTask(
//                KaviApplication.getInstance().getApplicationContext());
//        fetchNewImagesTask.execute();
    }


    public class LoadDataTask extends AsyncTask<Void, Void, Void> {


        Context context;
        ArrayList<GalleryData> galleryDatas = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        public LoadDataTask(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {

            Databases db = new Databases(context);
            if(!SavedPreferance.getReadFromAsset(context)) {
                //Means have to read from asset and insert to db, its a first time running
                String xmlData = Utility.readFromAssets("Gallery.xml", context);
                galleryDatas = GalleryData.getParsed(xmlData);
                for( GalleryData galleryData: galleryDatas) {
                    LOG.log("XXXXXXXX","Gallery inserting....");
                    GalleryData.insertObject(db, galleryData);
                }
                SavedPreferance.setReadFromAsset(context, true);
                SavedPreferance.setVersion(context,1);
            } else {
                //Read from db
                galleryDatas = GalleryData.getAllNonViewedObjects(db);
                galleryDatas.addAll(GalleryData.getAllViewedObjects(db));
            }
            db.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Intent intent = new  Intent(context, HomeActivity.class);
            startActivity(intent);

//            populateList(galleryDatas);

        }
    }



}
