package ehi2vsa.tjoonerapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.async.GetAllLocalImagesAsync;
import ehi2vsa.tjoonerapp.async.GlobalAsync;
import ehi2vsa.tjoonerapp.fragments.CameraFragment;
import ehi2vsa.tjoonerapp.fragments.GalleryFragment;
import ehi2vsa.tjoonerapp.fragments.MediaFragment;
import ehi2vsa.tjoonerapp.fragments.SettingFragment;
import ehi2vsa.tjoonerapp.fragments.VideoFragment;
import ehi2vsa.tjoonerapp.singletons.LoginToken;

public class LoggedIn extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    CameraFragment camera;
    MediaFragment media;
    VideoFragment video;
    GalleryFragment gallery;
    SettingFragment settings;
    LoginToken token = LoginToken.getInstance();
    Toolbar toolbar;
    GetAllLocalImagesAsync getAllLocalImagesAsync;
    boolean firsttimeloadinglocal = true;
    GlobalAsync globalAsync;

    SharedPreferences sharedPref;
    String ACCESS_TOKEN = "ACCESS_TOKEN";
    String USER = "USER";
    final String PREFS_NAME = "MyPrefsFile";

    //test Stan
    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Media");
        camera = new CameraFragment();
        video = new VideoFragment();
        gallery = new GalleryFragment();
        media = new MediaFragment();
        settings = new SettingFragment();


        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }


            media.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.fragment_container, media).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logged_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        try {
            switch (id) {
                case R.id.nav_media:
                    toolbar.setTitle(R.string.media);
                    media.setArguments(getIntent().getExtras());
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, media).commit();
                    break;
                case R.id.nav_gallery:
                    if (firsttimeloadinglocal) {
                        getAllLocalImagesAsync = new GetAllLocalImagesAsync();
                        getAllLocalImagesAsync.execute(this);
                        firsttimeloadinglocal = false;
                    }
                    toolbar.setTitle(R.string.gallery);
                    gallery.setArguments(getIntent().getExtras());
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, gallery).commit();
                    break;
                case R.id.nav_camera:
                    toolbar.setTitle(R.string.camera);
                    dispatchTakePictureIntent();
                    camera.setArguments(getIntent().getExtras());
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, camera).commit();
                    break;
                case R.id.nav_video:
                    toolbar.setTitle(R.string.video);
                    video.setArguments(getIntent().getExtras());
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, video).commit();
                    break;
                case R.id.nav_logout:
                    sharedPref = this.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.clear();
                    editor.remove(ACCESS_TOKEN);
                    editor.apply();
                    token.logout();
                    finish();
                    break;
                case R.id.nav_manage:
                    toolbar.setTitle(R.string.settings);
                    settings.setArguments(getIntent().getExtras());
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, settings).commit();
                    break;
            }
        } catch (Exception e) {
            Log.d("Switch", e.getMessage());
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.currentpage), Toast.LENGTH_LONG).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    //test Stan
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    //test Stan
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                camera.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
}
