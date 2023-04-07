package com.example.apollo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public SongFragment songFragment;
    public Playlist playlistFragment;
    public FragmentTransaction fTrans;
    Song song;
    BottomNavigationView bottomNavigationView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        songFragment =  new SongFragment();
        playlistFragment = new Playlist();
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.playlist);
        fTrans = getFragmentManager().beginTransaction();
        fTrans.replace(R.id.fragmentCount, playlistFragment, "PlaylistFragment");
        fTrans.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                switch (id) {
                    case R.id.song:
                        switchToSong();
                        return true;
                    case R.id.playlist:
                        switchToPlaylist();
                        return true;
                    case R.id.equalizer:
                        switchToEqualizer();
                        return true;
                    case R.id.settings:
                        switchToSettings();
                        return true;

                }
                return false;
            }
        });
    }


    private void switchToSettings() {
        fTrans = getFragmentManager().beginTransaction();
        if(getFragmentManager().findFragmentByTag("Settings") != null) {
            //if the fragment exists, show it.
            fTrans.show(getFragmentManager().findFragmentByTag("Settings")).commit();
        } else {
            fTrans = getFragmentManager().beginTransaction();
            //if the fragment does not exist, add it to fragment manager.
            fTrans.add(R.id.fragmentCount, playlistFragment, "Settings").commit();
        }
        if(getFragmentManager().findFragmentByTag("PlaylistFragment") != null){
            fTrans = getFragmentManager().beginTransaction();
            //if the other fragment is visible, hide it.
            fTrans.hide(getFragmentManager().findFragmentByTag("PlaylistFragment")).commit();
        }
        if(getFragmentManager().findFragmentByTag("SongFragment") != null){
            fTrans = getFragmentManager().beginTransaction();
            //if the other fragment is visible, hide it.
            fTrans.hide(getFragmentManager().findFragmentByTag("SongFragment")).commit();
        }
        if(getFragmentManager().findFragmentByTag("Equalizer") != null){
            fTrans = getFragmentManager().beginTransaction();
            //if the other fragment is visible, hide it.
            fTrans.hide(getFragmentManager().findFragmentByTag("Equalizer")).commit();
        }
    }

    private void switchToEqualizer() {
        fTrans = getFragmentManager().beginTransaction();
        if(getFragmentManager().findFragmentByTag("Equalizer") != null) {
            //if the fragment exists, show it.
            fTrans.show(getFragmentManager().findFragmentByTag("Equalizer")).commit();
        } else {
            fTrans = getFragmentManager().beginTransaction();
            //if the fragment does not exist, add it to fragment manager.
            fTrans.add(R.id.fragmentCount, playlistFragment, "Equalizer").commit();
        }
        if(getFragmentManager().findFragmentByTag("PlaylistFragment") != null){
            fTrans = getFragmentManager().beginTransaction();
            //if the other fragment is visible, hide it.
            fTrans.hide(getFragmentManager().findFragmentByTag("PlaylistFragment")).commit();
        }
        if(getFragmentManager().findFragmentByTag("Setting") != null){
            fTrans = getFragmentManager().beginTransaction();
            //if the other fragment is visible, hide it.
            fTrans.hide(getFragmentManager().findFragmentByTag("Settings")).commit();
        }
        if(getFragmentManager().findFragmentByTag("SongFragment") != null){
            fTrans = getFragmentManager().beginTransaction();
            //if the other fragment is visible, hide it.
            fTrans.hide(getFragmentManager().findFragmentByTag("SongFragment")).commit();
        }
    }

    private void switchToPlaylist() {
        fTrans = getFragmentManager().beginTransaction();
        if(getFragmentManager().findFragmentByTag("PlaylistFragment") != null) {
            //if the fragment exists, show it.
            fTrans.show(getFragmentManager().findFragmentByTag("PlaylistFragment")).commit();
        } else {
            fTrans = getFragmentManager().beginTransaction();
            //if the fragment does not exist, add it to fragment manager.
            fTrans.add(R.id.fragmentCount, playlistFragment, "PlaylistFragment").commit();
        }
        if(getFragmentManager().findFragmentByTag("SongFragment") != null){
            fTrans = getFragmentManager().beginTransaction();
            //if the other fragment is visible, hide it.
            fTrans.hide(getFragmentManager().findFragmentByTag("SongFragment")).commit();
        }
        if(getFragmentManager().findFragmentByTag("Setting") != null){
            fTrans = getFragmentManager().beginTransaction();
            //if the other fragment is visible, hide it.
            fTrans.hide(getFragmentManager().findFragmentByTag("Settings")).commit();
        }
        if(getFragmentManager().findFragmentByTag("Equalizer") != null){
            fTrans = getFragmentManager().beginTransaction();
            //if the other fragment is visible, hide it.
            fTrans.hide(getFragmentManager().findFragmentByTag("Equalizer")).commit();
        }
    }

    public void switchToSong() {

        fTrans = getFragmentManager().beginTransaction();
        if(getFragmentManager().findFragmentByTag("SongFragment") != null) {
            //if the fragment exists, show it.
            fTrans.show(getFragmentManager().findFragmentByTag("SongFragment")).commit();
        } else {
            fTrans = getFragmentManager().beginTransaction();
            //if the fragment does not exist, add it to fragment manager.
            fTrans.add(R.id.fragmentCount, songFragment, "SongFragment").commit();
        }
        if(getFragmentManager().findFragmentByTag("PlaylistFragment") != null){
            fTrans = getFragmentManager().beginTransaction();
            //if the other fragment is visible, hide it.
            fTrans.hide(getFragmentManager().findFragmentByTag("PlaylistFragment")).commit();
        }
        if(getFragmentManager().findFragmentByTag("Setting") != null){
            fTrans = getFragmentManager().beginTransaction();
            //if the other fragment is visible, hide it.
            fTrans.hide(getFragmentManager().findFragmentByTag("Settings")).commit();
        }
        if(getFragmentManager().findFragmentByTag("Equalizer") != null){
            fTrans = getFragmentManager().beginTransaction();
            //if the other fragment is visible, hide it.
            fTrans.hide(getFragmentManager().findFragmentByTag("Equalizer")).commit();
        }
    }

}