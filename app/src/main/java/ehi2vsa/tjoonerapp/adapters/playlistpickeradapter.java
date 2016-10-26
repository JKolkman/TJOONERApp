package ehi2vsa.tjoonerapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.objects.Playlist;
import ehi2vsa.tjoonerapp.singletons.PlaylistSingleton;

/**
 * Created by joost on 26/10/2016.
 */

public class PlaylistPickerAdapter extends BaseAdapter {
    private Activity context;

    static class ViewHolder{
        public ViewHolder(){
        }
        TextView name;
    }

    public PlaylistPickerAdapter(Activity activity) {
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
        PlaylistPickerAdapter.ViewHolder holder;
        if (convertView == null) {
            Log.d("Convertview inflater", "Convertview");
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.plain_textview, null);
            holder = new PlaylistPickerAdapter.ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.tv_playlist_name_adapter);

            convertView.setTag(holder);
        } else {
            holder = (PlaylistPickerAdapter.ViewHolder) convertView.getTag();
        }
        holder.name.setText(PlaylistSingleton.getInstance().getList().get(position).getTitle());
        return convertView;
    }
}
