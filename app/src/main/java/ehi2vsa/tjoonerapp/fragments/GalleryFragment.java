package ehi2vsa.tjoonerapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.ViewImageActivity;
import ehi2vsa.tjoonerapp.adapters.CustomImageAdapter;
import ehi2vsa.tjoonerapp.async.GetAllLocalImagesAsync;
import ehi2vsa.tjoonerapp.singletons.ImagesOnPhone;

public class GalleryFragment extends Fragment {
    GetAllLocalImagesAsync getAllLocalImagesAsync;
    Intent intentOpenImage;
    CustomImageAdapter customImageAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        customImageAdapter = new CustomImageAdapter(this.getActivity());
        gridview.setAdapter(customImageAdapter);
//        try {
//            gridview.setAdapter(new CustomImageAdapter(this.getActivity(), getAllLocalImagesAsync.get()));
////
//        } catch (ExecutionException|InterruptedException e) {
//            e.printStackTrace();
//        }
        while (ImagesOnPhone.getInstance().getImageInfoSize() < 20){
            customImageAdapter.notifyDataSetChanged();
        }
        gridview.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {

                customImageAdapter.notifyDataSetChanged();
                return false;
            }
        });

        intentOpenImage= new Intent(getActivity(), ViewImageActivity.class);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Log.d("GalleryFragment", "onItemClick: position is "+position +"item is "+ ImagesOnPhone.getInstance().getImageInfo(position).getTitle());
                        intentOpenImage.putExtra("Position", position);
                        startActivity(intentOpenImage);
                    }
                });

        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getAllLocalImagesAsync=new GetAllLocalImagesAsync();
//        getAllLocalImagesAsync.execute(this.getActivity());

    }
}
