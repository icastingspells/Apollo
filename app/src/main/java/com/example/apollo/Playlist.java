package com.example.apollo;

import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.logging.ConsoleHandler;


public class Playlist extends Fragment {

    private static final int REQUEST_PERMISSION = 99;
    Song song;
    public static int currentSongPos = 0;

    static ArrayList<Song> songArrayList;
    static Bundle bundle = new Bundle();
    ListView lvSongs;
    SongsAdapter songsAdapter;
    SongFragment songFragment = new SongFragment();
    public static int nextSongPos;
    public static int prevSongPos;

    public FragmentTransaction fTrans;

    public Playlist() {
        // Required empty public constructor
    }

    public static void currentSong(){
        if (currentSongPos>=songArrayList.size()){
            currentSongPos = 0;
        }
        Song cursong = songArrayList.get(currentSongPos);
        bundle.putString("curpath", cursong.getPath());
        bundle.putString("curtitle", cursong.getTitle());
        bundle.putString("curartist", cursong.getArtist());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        lvSongs = view.findViewById(R.id.playlistList);
        SongFragment songFragment = new SongFragment();
        songArrayList = new ArrayList<>();
        songsAdapter = new SongsAdapter(view.getContext(), songArrayList);
        lvSongs.setAdapter(songsAdapter);
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);

        } else {
            getSongs();
        }
        lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                currentSongPos = position;
                song = songArrayList.get(position);
                fTrans = getFragmentManager().beginTransaction();

                bundle.putString("path", song.getPath());
                bundle.putString("title", song.getTitle());
                bundle.putString("artist", song.getArtist());
                songFragment.setArguments(bundle);
                getFragmentManager().findFragmentByTag("SongFragment");
                if (getFragmentManager().findFragmentByTag("PlaylistFragment") != null) {
                    MediaPlayer.mediaPlayer.stop();
                    fTrans = getFragmentManager().beginTransaction();
                    fTrans.hide(getFragmentManager().findFragmentByTag("PlaylistFragment"));
                    fTrans.replace(R.id.fragmentCount, songFragment, "SongFragment").commit();
                }
            }
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getSongs();
            }
        }

    }

    public void getSongs() {

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {

                    int indexTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                    int indexArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                    int indexData = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                    do {
                        String title = cursor.getString(indexTitle);
                        String artist = cursor.getString(indexArtist);
                        String path = cursor.getString(indexData);
                        songArrayList.add(new Song(title, artist, path));
                    } while (cursor.moveToNext());
                }
                songsAdapter.notifyDataSetChanged();

            }
        }
    }
}