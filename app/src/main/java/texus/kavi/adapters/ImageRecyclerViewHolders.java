package texus.kavi.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import texus.kavi.GalleryFullVIewActivity;
import texus.kavi.R;
import texus.kavi.datamodel.GalleryData;

/**
 * Created by sandeep on 8/4/16.
 */
public class ImageRecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    private ArrayList<GalleryData> objects = null;
    GalleryData object;
    int position = 0;

    public ImageView    imImage;


    public ImageRecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        imImage         = (ImageView)    itemView.findViewById(R.id.imImage);
    }

    public void setValue(GalleryData object) {

        this.object = object;

        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams)imImage.getLayoutParams();
        rlp.height = object.cardViewHeight;
        imImage.setLayoutParams(rlp);

        Glide.with(imImage.getContext())
                .load(object.url)
                .into(imImage);
    }

    public void setValue(ArrayList<GalleryData> objects) {
        this.objects = objects;
    }

    public void setPosition( int position ) {
        this.position = position;
    }

    @Override
    public void onClick(View view) {
//        Toast.makeText(view.getContext(), "Clicked Country Position = "
// + getPosition(), Toast.LENGTH_SHORT).show();

        // Creating an intent to open the activity StudentViewActivity
        Intent intent = new Intent(view.getContext(), GalleryFullVIewActivity.class);

        // Passing data as a parecelable object to StudentViewActivity
        if(objects != null) {
            intent.putExtra(GalleryFullVIewActivity.PARAM_IMAGES,objects);
        } else {
            intent.putExtra(GalleryFullVIewActivity.PARAM_IMAGE,object);
        }
        intent.putExtra(GalleryFullVIewActivity.PARAM_POSITION,position);


        // Opening the activity
        view.getContext().startActivity(intent);

    }

}
