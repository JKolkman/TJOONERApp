package ehi2vsa.tjoonerapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.objects.Category;
import ehi2vsa.tjoonerapp.singletons.Categories;

/**
 * Created by Thijs on 21-10-2016.
 */

public class CheckedTextViewItemAdapter extends BaseAdapter {
        private Context context;
        private Categories categories;
    public CheckedTextViewItemAdapter(Context context){
        this.context = context;
        categories = categories.getInstance();
    }
    static class ViewHolder{
        public ViewHolder(){};
        CheckedTextView checkedTextView;
    }

    @Override
    public int getCount() {
        Log.d("getCount", "getCount: categories.getCategories().size()");
        return categories.getCategories().size();
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_categories, null);
            holder = new ViewHolder();
            holder.checkedTextView = (CheckedTextView) view.findViewById(R.id.ctv_item);
            view.setTag(holder);


        } else {
            holder = (ViewHolder) view.getTag();
        }

        Category category = categories.getCategoryFromPosition(i);
        Log.d("checkedTextView", "getView: position is "+i +" description is "+category.getDescription());
        holder.checkedTextView.setText(category.getDescription());
        if (holder.checkedTextView.isChecked()){
            holder.checkedTextView.setChecked(false);
        } else {
            holder.checkedTextView.setChecked(true);

        }
//        holder.checkedTextView.setBackgroundColor(Color.parseColor(category.getBackgroundcolor()));
        holder.checkedTextView.setVisibility(View.VISIBLE);
        return view;
    }
}
