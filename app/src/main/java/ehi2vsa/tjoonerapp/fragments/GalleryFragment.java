package ehi2vsa.tjoonerapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.concurrent.ExecutionException;

import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.adapters.CustomImageAdapter;
import ehi2vsa.tjoonerapp.async.GetAllLocalImages;

public class GalleryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        GetAllLocalImages getAllLocalImages = new GetAllLocalImages();
        getAllLocalImages.execute(this.getActivity());

        try {
            gridview.setAdapter(new CustomImageAdapter(this.getActivity(),getAllLocalImages.get()));
        } catch (ExecutionException|InterruptedException e) {
            e.printStackTrace();
        }

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

    }
}
