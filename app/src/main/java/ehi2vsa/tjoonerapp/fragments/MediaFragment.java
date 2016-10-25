package ehi2vsa.tjoonerapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import ehi2vsa.tjoonerapp.intents.CategoryInfoActivity;
import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.adapters.CategoryAdapter;
import ehi2vsa.tjoonerapp.async.GetCategories;
import ehi2vsa.tjoonerapp.objects.Category;
import ehi2vsa.tjoonerapp.singletons.Categories;

public class MediaFragment extends Fragment {
    //ArrayList<Category>categories = Categories.getInstance().getCategories();
    private GridView gridview;
    private CategoryAdapter adapter;
    Intent intent;
    boolean firsttime = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media, container, false);
        gridview = (GridView) view.findViewById(R.id.gv_media_fragment);
        if (firsttime) {
            loadItems();
            firsttime = false;
        }
        adapter = new CategoryAdapter(Categories.getInstance().getCategories(), getActivity());
        gridview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        intent = new Intent(getActivity(), CategoryInfoActivity.class);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent.putExtra("Category", Categories.getInstance().getCategories().get(i));
                startActivity(intent);
            }
        });
        return view;
    }

//    @Override
//    public void onResume() {
//        adapter.notifyDataSetChanged();
//    }

    private void loadItems() {
        Categories.getInstance().getCategories().clear();
        GetCategories getCategories = new GetCategories();
        getCategories.execute();
        //String toast = "Retrieved " + Categories.getInstance().getCategories().size() + " categories";
        //Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
