package ehi2vsa.tjoonerapp.singletons;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ehi2vsa.tjoonerapp.objects.Category;


/**
 * Created by joost on 18/10/2016.
 */

public class Categories {
    private static Categories instance;
    private ArrayList<Category>categories;

    public static Categories getInstance(){
        if (instance == null){
            instance = new Categories();
        }
        return instance;
    }

    private Categories(){
        categories = new ArrayList<>();
    }

    public ArrayList<Category> getCategories(){
        return categories;
    }
}
