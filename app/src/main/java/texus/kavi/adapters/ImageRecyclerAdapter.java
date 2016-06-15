package texus.kavi.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import orange.sbl.com.edinette.R;
import orange.sbl.com.edinette.datamodels.GalleryData;

/**
 * Created by sandeep on 8/4/16.
 */
public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerViewHolders> {

    private ArrayList<GalleryData> objects;
    private Context context;

    public ImageRecyclerAdapter(Context context, ArrayList<GalleryData> objects) {
        this.objects = objects;
        this.context = context;
    }

    @Override
    public ImageRecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, null);
        ImageRecyclerViewHolders rcv = new ImageRecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final ImageRecyclerViewHolders holder, int position) {

        GalleryData object = objects.get(position);

        holder.setValue( object );
        holder.setValue( objects );
        holder.setPosition( position );

    }



    @Override
    public int getItemCount() {
        return this.objects.size();
    }
}