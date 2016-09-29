package ehi2vsa.tjoonerapp.adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.async.PreviewIdToImage;
import ehi2vsa.tjoonerapp.objects.Playlist;

public class PlaylistAdapter extends ArrayAdapter<Playlist> {
    private Playlist[] objects;

    public PlaylistAdapter(Context context, Playlist[] objects) {
        super(context, 0);
        System.out.println("" + objects.length);
        this.objects = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.playlist_object, null);
        }
        ImageView preview = (ImageView) convertView.findViewById(R.id.iv_preview);
        TextView playlist_name = (TextView) convertView.findViewById(R.id.tv_playlist_name);
        TextView playlist_amount = (TextView) convertView.findViewById(R.id.tv_image_amount);

        PreviewIdToImage getImage = new PreviewIdToImage(objects[position].getThumbnail());
        getImage.execute();
        try {
            preview.setImageBitmap(getImage.get());
        } catch (InterruptedException | ExecutionException e) {
            Log.d("Adapter", e.getMessage());
        }
        playlist_name.setText(objects[position].getTitle());
        playlist_amount.setText(objects[position].getMedia().length + " Images");

        return convertView;
    }
}
