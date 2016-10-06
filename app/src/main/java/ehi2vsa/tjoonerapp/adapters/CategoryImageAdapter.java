package ehi2vsa.tjoonerapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.async.PreviewIdToImage;
import ehi2vsa.tjoonerapp.objects.Media;

/**
 * Created by joost on 06/10/2016.
 */
public class CategoryImageAdapter extends BaseAdapter {
    private ArrayList<Media> media;
    private Activity context;

    static class ViewHolder {
        public ViewHolder() {
        }

        TextView title;
        ImageView preview;
        RelativeLayout frame;
    }

    public CategoryImageAdapter(ArrayList<Media> media, Activity activity) {
        this.media = media;
        this.context = activity;

    }

    @Override
    public int getCount() {
        return media.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            Log.d("Convertview inflater", "Convertview");
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gridview_object, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.tv_category_title);
            holder.preview = (ImageView) convertView.findViewById(R.id.iv_category_preview);
            holder.frame = (RelativeLayout) convertView.findViewById(R.id.rl_category_frame);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Media picture = media.get(i);
        if (picture.getPreviewId() != null) {
            PreviewIdToImage image = new PreviewIdToImage(picture.getPreviewId());
            image.execute();
            try {
                holder.preview.setImageBitmap(image.get());
            } catch (InterruptedException | ExecutionException e) {
                Log.d("playlistadapter", e.getMessage());
            }
        }

        holder.title.setText(picture.getDescription());
        return convertView;
    }
}