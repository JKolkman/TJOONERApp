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
import ehi2vsa.tjoonerapp.objects.ImageInfo;
import ehi2vsa.tjoonerapp.singletons.ImagesOnPhone;

public class CustomImageAdapter extends BaseAdapter {
    private Context mContext;

    private ImagesOnPhone imagesOnPhone = ImagesOnPhone.getInstance();
    private ArrayList<ImageInfo> images = imagesOnPhone.getImageInfos();
    private ArrayList<ImageInfo> tempImages;

    public CustomImageAdapter(Activity activity, ArrayList<ImageInfo> images) {
        mContext = activity;

        Log.d("Images", "CustomImageAdapter: images is " + this.images.size());
    }

    public CustomImageAdapter(Activity activity) {
        mContext = activity;
    }

    public int getCount() {
        return imagesOnPhone.getImageInfos().size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {
        public ViewHolder() {
        }

        TextView tvPath;
        ImageView ivImage;

    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_gridview_component, null);
            holder = new ViewHolder();
            holder.tvPath = (TextView) convertView.findViewById(R.id.gridview_textview);
            holder.ivImage = (ImageView) convertView.findViewById(R.id.gridview_imageview);
            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageInfo imageInfo = ImagesOnPhone.getInstance().getImageInfo(position);
//        notifyDataSetChanged();
//        ImageInfo imageInfo = images.get(position);
        holder.ivImage.setImageBitmap(imageInfo.getThumbnail());
        holder.tvPath.setText(imageInfo.getTitle());
        Log.d("Position", "getView: Position is " + position);
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
