package ehi2vsa.tjoonerapp.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.async.GetPlaylist;
import ehi2vsa.tjoonerapp.async.PreviewIdToImage;
import ehi2vsa.tjoonerapp.objects.Playlist;
import ehi2vsa.tjoonerapp.singletons.PlaylistSingleton;

public class PlaylistAdapter extends BaseAdapter {
    ArrayList<Playlist> items;
    private Activity context;
    static class ViewHolder{
        public ViewHolder(){
        }
        ImageView preview;
        TextView playlist_name, playlist_amount;
    }

    public PlaylistAdapter(ArrayList<Playlist> items, Activity activity) {
        this.items = items;
        this.context = activity;
    }


    @Override
    public int getCount() {
        return PlaylistSingleton.getInstance().getSize();
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
            convertView = inflater.inflate(R.layout.playlist_object, null);
            holder = new ViewHolder();
            holder.playlist_name = (TextView) convertView.findViewById(R.id.tv_playlist_name);
            holder.playlist_amount = (TextView) convertView.findViewById(R.id.tv_image_amount);
            holder.preview = (ImageView) convertView.findViewById(R.id.iv_preview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Playlist list = PlaylistSingleton.getInstance().getList().get(position);
        if (list.getPreview() != null){
            holder.preview.setImageBitmap(list.getPreview());
        } else {
            try{
                Bitmap bm = BitmapFactory.decodeResource(convertView.getResources(), R.drawable.image_not_found);
                holder.preview.setImageBitmap(bm);
            }catch (Exception e){

            }
        }

        holder.playlist_name.setText(list.getTitle());
        holder.playlist_amount.setText(list.getMedia().size() + " Images");
        Log.d("Position", "getView: position" + position);
        return convertView;
    }
}
