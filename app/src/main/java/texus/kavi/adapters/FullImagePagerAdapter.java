package texus.kavi.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import texus.kavi.R;
import texus.kavi.datamodel.GalleryData;

/**
 * Created by sandeep on 7/6/16.
 */
public class FullImagePagerAdapter extends PagerAdapter {

    private Context mContext;
    ArrayList<GalleryData> galleryDatas;

    public FullImagePagerAdapter(Context context, ArrayList<GalleryData> galleryDatas) {
        mContext = context;
        this.galleryDatas = galleryDatas;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.pager_item_image, collection, false);

        ImageView imageView = (ImageView) layout.findViewById(R.id.imImage);

        GalleryData data = getData(position);
        Log.d("ImagePagerAdapter","GalleryData:" + data.url);

        if( data != null ) {
            Glide.with(imageView.getContext())
                    .load(data.url)
                    .into(imageView);
        }
        collection.addView(layout);
        return layout;
    }
    private GalleryData getData( int podstion) {
        try {
            return galleryDatas.get(podstion);
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return  null;

    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return galleryDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
//        return mContext.getString(customPagerEnum.getTitleResId());
//    }

}