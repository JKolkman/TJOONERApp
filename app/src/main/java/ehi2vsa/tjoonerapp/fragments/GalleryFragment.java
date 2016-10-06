package ehi2vsa.tjoonerapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.adapters.CustomImageAdapter;
import ehi2vsa.tjoonerapp.async.GetAllLocalImagesAsync;

public class GalleryFragment extends Fragment {
    GetAllLocalImagesAsync getAllLocalImagesAsync;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        CustomImageAdapter customImageAdapter =new CustomImageAdapter(this.getActivity());
        gridview.setAdapter(customImageAdapter);
//        try {
//            gridview.setAdapter(new CustomImageAdapter(this.getActivity(), getAllLocalImagesAsync.get()));
////
//        } catch (ExecutionException|InterruptedException e) {
//            e.printStackTrace();
//        }
        customImageAdapter.notifyDataSetChanged();
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
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
