package ehi2vsa.tjoonerapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.async.PreviewIdToImage;
import ehi2vsa.tjoonerapp.objects.Media;

public class SinglePlaylistAdapter extends BaseAdapter {
    ArrayList<Media> items;
    private Activity context;

    static class ViewHolder {
        public ViewHolder() {
        }

        ImageView preview;
    }

    public SinglePlaylistAdapter(ArrayList<Media> items, Activity activity) {
        this.items = items;
        this.context = activity;
    }


    @Override
    public int getCount() {
        return items.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            Log.d("Convertview inflater", "Convertview");
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.playlist_detail_object, null);
            holder = new ViewHolder();
            holder.preview = (ImageView) convertView.findViewById(R.id.iv_playlist_media_object);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PreviewIdToImage image = new PreviewIdToImage(items.get(position).getPreviewId());
        image.execute();
        try {
            holder.preview.setImageBitmap(image.get());
        } catch (InterruptedException | ExecutionException e) {
            Log.d("playlistadapter", e.getMessage());
        }
        return convertView;
    }
}
