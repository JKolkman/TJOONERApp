package ehi2vsa.tjoonerapp.singletons;

import java.util.ArrayList;

import ehi2vsa.tjoonerapp.objects.Playlist;

/**
 * Created by joost on 21/10/2016.
 */

public class PlaylistSingleton {
    private static PlaylistSingleton instance;
    private ArrayList<Playlist>list;

    public static PlaylistSingleton getInstance(){
        if (instance == null){
            instance = new PlaylistSingleton();
        }
        return instance;
    }

    private PlaylistSingleton(){
        list = new ArrayList<Playlist>();
    }

    public ArrayList<Playlist> getList() {
        return list;
    }

    public void setPlaylist(ArrayList<Playlist>list){
        this.list.clear();
        this.list = list;
    }

    public int getSize(){
        return list.size();
    }
}
