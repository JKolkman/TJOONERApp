package ehi2vsa.tjoonerapp.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ehi2vsa.tjoonerapp.R;

/**
 * Created by joost on 28/09/2016.
 */
public class MainMenuFragment extends Fragment {
    private View container;
    Button button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        button = (Button) view.findViewById(R.id.button);


        return view;
    }
}
