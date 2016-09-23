package texus.kavi.fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import texus.kavi.R;
import texus.kavi.adapters.ImageRecyclerAdapter;
import texus.kavi.datamodel.GalleryData;
import texus.kavi.db.Databases;
import texus.kavi.preference.SavedPreferance;
import texus.kavi.utility.LOG;
import texus.kavi.utility.Utility;

/**
 * Created by sandeep on 22/09/16.
 */
public class FragmentGalleryGrid extends Fragment {

    public static final String PARAM_GALLERY_DATA = "ParamGalleryData";

    ArrayList<GalleryData> galleryDatas = null;

    RecyclerView rv;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_gallery, container, false);

//        galleryDatas = ( ArrayList<GalleryData> ) getArguments().get(PARAM_GALLERY_DATA);
//        setupRecyclerView(rv);
        LoadDataTask task = new LoadDataTask(getActivity());
        task.execute();

        return  rv;
    }

    private void setupRecyclerView() {

//        recyclerView.addItemDecoration(new MarginDecoratio(getActivity()));
//        recyclerView.setHasFixedSize(true);

        if( galleryDatas != null && rv != null) {
            ImageRecyclerAdapter adapter = new ImageRecyclerAdapter(getActivity(),
                    galleryDatas);
            rv.setAdapter(adapter);
        }
    }


    public class LoadDataTask extends AsyncTask<Void, Void, Void> {


        Context context;


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

            setupRecyclerView();
//            Intent intent = new  Intent(context, HomeActivity.class);
//            startActivity(intent);

//            populateList(galleryDatas);

        }
    }
}
