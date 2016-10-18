package ehi2vsa.tjoonerapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.adapters.CategoryAdapter;
import ehi2vsa.tjoonerapp.intents.CategoryInfoActivity;
import ehi2vsa.tjoonerapp.singletons.Categories;

/**
 * Created by joost on 18/10/2016.
 */

public class SettingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        return view;
    }
}
