package texus.kavi;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import texus.kavi.adapters.FullImagePagerAdapter;
import texus.kavi.datamodel.GalleryData;

public class GalleryFullVIewActivity extends AppCompatActivity {

    public static final String PARAM_IMAGE = "param_image";
    public static final String PARAM_IMAGES = "param_images";
    public static final String PARAM_POSITION = "param_position";

    GalleryData object = null;

    ArrayList<GalleryData> objects = null;

    ViewPager pager;

    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view);

        objects = getIntent().getParcelableArrayListExtra(PARAM_IMAGES);

        position = getIntent().getIntExtra(PARAM_POSITION, 0);

        object = getIntent().getParcelableExtra(PARAM_IMAGE);
        if(objects == null) {
            Log.d("GalleryFullVIewActivity","objects is NULL");
            if( object != null) {
                objects = new ArrayList<GalleryData>();
                objects.add(object);
            }
        }

        init();
    }

    public void init() {
        pager = (ViewPager) this.findViewById(R.id.pager);
        if(objects !=  null) {
            Log.d("GalleryFullVIewActivity","objects size:" + objects.size());
        } else {
            return;
        }
        if(objects.size() == 0) return;
        FullImagePagerAdapter adapter = new FullImagePagerAdapter(this, objects);
        pager.setAdapter(adapter);
        pager.setCurrentItem(position);
    }
}
