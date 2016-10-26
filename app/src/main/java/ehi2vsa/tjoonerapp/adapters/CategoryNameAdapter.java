package ehi2vsa.tjoonerapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ehi2vsa.tjoonerapp.R;

public class CategoryNameAdapter extends BaseAdapter {
    private ArrayList<String> strings;
    private Activity context;

    private static class ViewHolder {
        ViewHolder() {
        }

        TextView title;
    }

    public CategoryNameAdapter(ArrayList<String> strings, Activity activity) {
        this.strings = strings;
        this.context = activity;

    }

    @Override
    public int getCount() {
        return strings.size();
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
            convertView = inflater.inflate(R.layout.textview_object, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.tv_textview_object);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String[]strings2 = strings.get(i).split(";");
        holder.title.setText(strings2[1]);
        holder.title.setTextColor(Color.parseColor(strings2[2]));
        return convertView;
    }

}
