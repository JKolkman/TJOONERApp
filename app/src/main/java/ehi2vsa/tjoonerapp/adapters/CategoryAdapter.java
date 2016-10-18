package ehi2vsa.tjoonerapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
import ehi2vsa.tjoonerapp.objects.Category;

public class CategoryAdapter extends BaseAdapter {
    private ArrayList<Category> categories;
    private Activity context;

    private static class ViewHolder{
        ViewHolder(){}
        TextView title;
        ImageView preview;
        RelativeLayout frame;
    }

    public CategoryAdapter(ArrayList<Category> categories, Activity activity) {
        this.categories = categories;
        this.context = activity;

    }

    @Override
    public int getCount() {
        return categories.size();
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

        Category category = categories.get(i);
        if (category.getThumbnail() != null) {
            PreviewIdToImage image = new PreviewIdToImage(category.getThumbnail());
            image.execute();
            try {
                holder.preview.setImageBitmap(image.get());
            } catch (InterruptedException | ExecutionException e) {
                Log.d("playlistadapter", e.getMessage());
            }
        }

        holder.title.setText(category.getDescription());
        holder.title.setTextColor(Color.parseColor(category.getBackgroundcolor()));
        holder.title.setTextSize(25);
        return convertView;
    }
}
