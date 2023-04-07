package com.example.apollo;

import static android.content.Context.MODE_PRIVATE;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

public class SongFragment extends Fragment{

    TextView tvTime, tvDuration, tvArtistPlay, tvTitlePlay;
    SeekBar seekBar;

    Button playButton, stop, nextButton, previousButton;
    Song song;
    public FragmentTransaction fTrans;
    public static String title;
    public static String artist;
    public static String path;
    public String duration;

    public SongFragment() {

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_song, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvDuration = view.findViewById(R.id.tvDuration);
        tvTime = view.findViewById(R.id.tvTime);
        tvArtistPlay = view.findViewById(R.id.tvArtistPLay);
        tvTitlePlay = view.findViewById(R.id.tvTitlePlay);
        seekBar = view.findViewById(R.id.seekBar);
        playButton = view.findViewById(R.id.playButton);
        nextButton = view.findViewById(R.id.nextButton);
        previousButton = view.findViewById(R.id.previousButton);
        MediaPlayer.mediaPlayer.setLooping(true);
        Bundle bundle = new Bundle();
        bundle = this.getArguments();
        if(bundle !=null) {
            path = bundle.getString("path");
            title = bundle.getString("title");
            artist = bundle.getString("artist");
        }else
        {
            title = " ";
            artist = " ";
        }
        System.out.println(title);
        tvTitlePlay.setText(title);
        tvArtistPlay.setText(artist);
        System.out.println(path);

        if(path != null) {
            try {
                MediaPlayer.mediaPlayer.reset();
                MediaPlayer.mediaPlayer.setDataSource(path);
                MediaPlayer.mediaPlayer.prepare();
                MediaPlayer.mediaPlayer.start();
                playButton.setBackgroundResource(R.drawable.baseline_pause_24);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        timechange();
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MediaPlayer.mediaPlayer.isPlaying()) {
                    MediaPlayer.mediaPlayer.pause();
                    playButton.setBackgroundResource(R.drawable.baseline_play_arrow_24);
                } else {
                    MediaPlayer.mediaPlayer.start();
                    playButton.setBackgroundResource(R.drawable.baseline_pause_24);
                }
            }

        });

        Bundle curBundle = bundle;
        previousButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MediaPlayer.mediaPlayer.pause();
                if(Playlist.currentSongPos == 0 ) {
                    Playlist.currentSongPos = Playlist.songArrayList.size() - 1;
                }else {
                    Playlist.currentSongPos -= 1;
                }
                Playlist.currentSong();
                if(curBundle !=null) {
                    path = curBundle.getString("curpath");
                    title = curBundle.getString("curtitle");
                    artist = curBundle.getString("curartist");
                    tvTitlePlay.setText(title);
                    tvArtistPlay.setText(artist);
                    if(path != null) {
                        try {
                            MediaPlayer.mediaPlayer.reset();
                            MediaPlayer.mediaPlayer.setDataSource(path);
                            MediaPlayer.mediaPlayer.prepare();
                            MediaPlayer.mediaPlayer.start();
                            playButton.setBackgroundResource(R.drawable.baseline_pause_24);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                timechange();

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MediaPlayer.mediaPlayer.pause();
                Playlist.currentSongPos += 1;
                Playlist.currentSong();

                if(curBundle !=null) {
                    path = curBundle.getString("curpath");
                    title = curBundle.getString("curtitle");
                    artist = curBundle.getString("curartist");

                    tvTitlePlay.setText(title);
                    tvArtistPlay.setText(artist);
                    if(path != null) {
                        try {
                            MediaPlayer.mediaPlayer.reset();
                            MediaPlayer.mediaPlayer.setDataSource(path);
                            MediaPlayer.mediaPlayer.prepare();
                            MediaPlayer.mediaPlayer.start();
                            playButton.setBackgroundResource(R.drawable.baseline_pause_24);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                timechange();
            }
        });
    }

    public void timechange(){
        duration = TimeToString(MediaPlayer.mediaPlayer.getDuration());
        tvDuration.setText(duration);
        seekBar.setMax(MediaPlayer.mediaPlayer.getDuration());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean isFromUser) {
                if(isFromUser){
                    MediaPlayer.mediaPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (MediaPlayer.mediaPlayer != null){
                    if(MediaPlayer.mediaPlayer.isPlaying()){
                        try {
                            final double current = MediaPlayer.mediaPlayer.getCurrentPosition();
                            final  String elapsedTime = TimeToString((int) current);
                            if(getActivity() == null)
                                return;
                            getActivity().runOnUiThread(new Runnable(){
                                public void run() {
                                    tvTime.setText(elapsedTime);
                                    seekBar.setProgress((int) current);
                                }
                            });
                            Thread.sleep(100);
                        } catch (InterruptedException e) {

                        }
                    }
                }
            }
        }).start();

    }

    public String TimeToString(int time){
        String elapsedTime = "";
        int minutes = time / 1000 / 60;
        int seconds = time / 1000 % 60;
        elapsedTime = minutes+":";
        if(seconds < 10){
            elapsedTime += "0";
        }
        elapsedTime += seconds;

        return elapsedTime;
    }
}


