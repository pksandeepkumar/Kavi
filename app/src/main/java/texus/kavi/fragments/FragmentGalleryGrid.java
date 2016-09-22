package texus.kavi.fragments;


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

/**
 * Created by sandeep on 22/09/16.
 */
public class FragmentGalleryGrid extends Fragment {

    public static final String PARAM_GALLERY_DATA = "ParamGalleryData";

    ArrayList<GalleryData> galleryDatas = null;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_gallery, container, false);

        galleryDatas = ( ArrayList<GalleryData> ) getArguments().get(PARAM_GALLERY_DATA);

        return  rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {

//        recyclerView.addItemDecoration(new MarginDecoratio(getActivity()));
//        recyclerView.setHasFixedSize(true);

        if( galleryDatas != null) {
            ImageRecyclerAdapter adapter = new ImageRecyclerAdapter(getActivity(),
                    galleryDatas);
            recyclerView.setAdapter(adapter);
        }
    }
}
