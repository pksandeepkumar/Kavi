package texus.kavi;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import texus.kavi.controls.MarginDecoration;
import texus.kavi.datamodel.GalleryData;
import texus.kavi.datamodel.IndexData;
import texus.kavi.db.Databases;
import texus.kavi.dialogs.ProgressDialog;
import texus.kavi.network.NetworkService;
import texus.kavi.preference.SavedPreferance;
import texus.kavi.utility.Utility;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycler_view = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        recycler_view = (RecyclerView) this.findViewById(R.id.recycler_view);
        recycler_view.addItemDecoration(new MarginDecoration(this));
        recycler_view.setHasFixedSize(true);
    }

    private void populateList(ArrayList<GalleryData> galleryDatas) {
        if( galleryDatas == null) return;

    }

    public class LoadDataTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog = null;
        Context context;
        ArrayList<GalleryData> galleryDatas = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.show();
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
            dialog.dismiss();
            populateList(galleryDatas);

        }
    }

    public class FetchNewImagesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog = null;
        Context context;
        ArrayList<GalleryData> galleryDatas = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
        }

        public FetchNewImagesTask(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {

            Databases db = new Databases(context);

            String indexXML = NetworkService.getResponse(KaviApplication.INDEX_URL);
            int verson = SavedPreferance.getVersion(context);

            ArrayList<IndexData> indexFiles = IndexData.getParsed(indexXML);
            indexXML =  null;
            for( IndexData indexFile : indexFiles) {
                if( verson <=  indexFile.version) continue;

                String xmlData = NetworkService.getResponse(KaviApplication.BASE_URL + "/"
                                + indexFile.name);
                ArrayList<GalleryData> galleryDatas = GalleryData.getParsed(xmlData);
                if(indexFile.need_delete) {
                    GalleryData.deleteItems(db,galleryDatas);
                    continue;
                }
                for(GalleryData galleryData : galleryDatas) {
                    GalleryData.inseartOrUpdateOperationLocal(db,galleryData);
                }

            }
            db.close();

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dialog.dismiss();
            populateList(galleryDatas);

        }
    }
}
