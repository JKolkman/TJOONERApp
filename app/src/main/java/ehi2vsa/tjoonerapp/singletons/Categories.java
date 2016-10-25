package ehi2vsa.tjoonerapp.singletons;

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
    public Category getCategoryFromPosition(int position){
        return categories.get(position);
    }

    public void addCategory(Category category){
        categories.add(category);
    }
}
