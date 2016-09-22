package texus.kavi.tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import texus.kavi.KaviApplication;
import texus.kavi.datamodel.GalleryData;
import texus.kavi.datamodel.IndexData;
import texus.kavi.db.Databases;
import texus.kavi.dialogs.ProgressDialog;
import texus.kavi.network.NetworkService;
import texus.kavi.preference.SavedPreferance;
import texus.kavi.utility.LOG;

/**
 * Created by sandeep on 21/09/16.
 */
public class FetchNewImagesTask  extends AsyncTask<Void, Void, Void> {

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
            LOG.log("XXXXXXXX","Network Task:" + xmlData);
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
//        populateList(galleryDatas);

    }
}
