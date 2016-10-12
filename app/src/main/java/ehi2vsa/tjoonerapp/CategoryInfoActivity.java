package ehi2vsa.tjoonerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import ehi2vsa.tjoonerapp.adapters.CategoryImageAdapter;
import ehi2vsa.tjoonerapp.objects.Category;

/**
 * Created by joost on 06/10/2016.
 */
public class CategoryInfoActivity extends AppCompatActivity {
    private GridView gridview;
    Intent gottenIntent, newIntent;
    Category category;
    CategoryImageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_info);
        gottenIntent = getIntent();
        category = (Category) gottenIntent.getSerializableExtra("Category");

        gridview = (GridView) findViewById(R.id.gv_category_info);
        adapter = new CategoryImageAdapter(category.getMedia(), this);
        gridview.setAdapter(adapter);

        newIntent = new Intent(this, MediaInfoActivity.class);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                newIntent.putExtra("Media", category.getMedia().get(i));
                startActivity(newIntent);
            }
        });
    }
}
