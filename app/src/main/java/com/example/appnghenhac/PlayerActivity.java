package com.example.appnghenhac;

import static com.example.appnghenhac.MainActivity.listBaiHat;
import static com.example.appnghenhac.MainActivity.repeatBoolean;
import static com.example.appnghenhac.MainActivity.shuffleBoolean;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class PlayerActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener{
    TextView song_name, song_artist,duration_played, duration_total;
    ImageView back_btn, menu_btn, cover_art, shuffle_btn, skip_pre_btn, skip_next_btn, repeat_btn;
    FloatingActionButton playPause_btn;
    SeekBar seek_bar;
    int position = -1;
    static ArrayList<FileBaiHat> listSongs = new ArrayList<>();
    static Uri uri;
    static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private Thread playThread, prevThread, nextThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        initView();
        getIntentMethod();
        mediaPlayer.setOnCompletionListener(this);
        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null) {
                    // get seconds
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seek_bar.setProgress(mCurrentPosition);
                    duration_played.setText(formattedTime(mCurrentPosition));
                }
                handler.postDelayed(this,1000);
            }
        });
        shuffle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shuffleBoolean) {
                    shuffleBoolean = false;
                    shuffle_btn.setImageResource(R.drawable.shuffle_off);
                }
                else {
                    shuffleBoolean = true;
                    shuffle_btn.setImageResource(R.drawable.shuffle_on);
                }
            }
        });

        repeat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(repeatBoolean) {
                    repeatBoolean = false;
                    repeat_btn.setImageResource(R.drawable.repeat_off);
                }
                else {
                    repeatBoolean = true;
                    repeat_btn.setImageResource(R.drawable.repeat_on);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        playThreadBtn();
        prevThreadBtn();
        nextThreadBtn();
        super.onResume();


    }

    private void nextThreadBtn() {
        nextThread = new Thread(){
            @Override
            public void run() {
                super.run();
                skip_next_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nextBtnClicked();
                    }
                });
            }
        };
        nextThread.start();
    }

    private void nextBtnClicked() {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            if(shuffleBoolean && !repeatBoolean){
                position = getRandom(listSongs.size() - 1);
            }
            else if (!shuffleBoolean && !repeatBoolean){
                position = (((position + 1)) % listSongs.size());
            }

            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            seek_bar.setMax(mediaPlayer.getDuration()/1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null) {
                        // get seconds
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seek_bar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this,1000);
                }
            });
            mediaPlayer.setOnCompletionListener(this);
            playPause_btn.setBackgroundResource(R.drawable.pause);
            mediaPlayer.start();
        }
        else {
            mediaPlayer.stop();
            mediaPlayer.release();
            if(shuffleBoolean && !repeatBoolean){
                position = getRandom(listSongs.size() - 1);
            }
            else if (!shuffleBoolean && !repeatBoolean){
                position = (((position + 1)) % listSongs.size());
            }

            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            seek_bar.setMax(mediaPlayer.getDuration()/1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null) {
                        // get seconds
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seek_bar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this,1000);
                }
            });
            mediaPlayer.setOnCompletionListener(this);
            playPause_btn.setBackgroundResource(R.drawable.play_arrow);
        }
    }

    private int getRandom(int i) {

        Random random = new Random();
        return random.nextInt(i+1);
    }

    private void prevThreadBtn() {
        prevThread = new Thread(){
            @Override
            public void run() {
                super.run();
                skip_pre_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        preBtnClicked();
                    }
                });
            }
        };
        prevThread.start();
    }

    private void preBtnClicked() {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            if(shuffleBoolean && !repeatBoolean){
                position = getRandom(listSongs.size() - 1);
            }
            else if (!shuffleBoolean && !repeatBoolean){
                position = ((position - 1) < 0 ? (listSongs.size() - 1) : (position - 1));
            }

            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            seek_bar.setMax(mediaPlayer.getDuration()/1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null) {
                        // get seconds
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seek_bar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this,1000);
                }
            });
            mediaPlayer.setOnCompletionListener(this);
            playPause_btn.setBackgroundResource(R.drawable.pause);
            mediaPlayer.start();
        }
        else {
            mediaPlayer.stop();
            mediaPlayer.release();
            if(shuffleBoolean && !repeatBoolean){
                position = getRandom(listSongs.size() - 1);
            }
            else if (!shuffleBoolean && !repeatBoolean){
                position = ((position - 1) < 0 ? (listSongs.size() - 1) : (position - 1));
            }

            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            seek_bar.setMax(mediaPlayer.getDuration()/1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null) {
                        // get seconds
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seek_bar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this,1000);
                }
            });
            mediaPlayer.setOnCompletionListener(this);
            playPause_btn.setBackgroundResource(R.drawable.play_arrow);
        }
    }

    private void playThreadBtn() {
        playThread = new Thread(){
            @Override
            public void run() {
                super.run();
                playPause_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playPauseBtnClicked();
                    }
                });
            }
        };
        playThread.start();
    }

    private void playPauseBtnClicked() {
        if(mediaPlayer.isPlaying()) {
            playPause_btn.setImageResource(R.drawable.play_arrow);
            mediaPlayer.pause();
            seek_bar.setMax(mediaPlayer.getDuration()/1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null) {
                        // get seconds
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seek_bar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this,1000);
                }
            });
        }
        else {
            playPause_btn.setImageResource(R.drawable.pause);
            mediaPlayer.start();
            seek_bar.setMax(mediaPlayer.getDuration() /1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null) {
                        // get seconds
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seek_bar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this,1000);
                }
            });
        }
    }

    private String formattedTime(int mCurrentPosition) {
        String totalOut = "";
        String totalNew = "";
        String seconds = String.valueOf(mCurrentPosition % 60);
        String minute = String.valueOf(mCurrentPosition / 60);
        totalOut = minute + ":" + seconds;
        totalNew = minute + ":" + "0" + seconds;
        if(seconds.length() == 1) {
            return totalNew;
        } else {
            return totalOut;
        }

    }

    private void getIntentMethod() {
        position = getIntent().getIntExtra("position", -1);
        listSongs = listBaiHat;

        if(listSongs != null) {
            playPause_btn.setImageResource(R.drawable.pause);
            uri = Uri.parse(listSongs.get(position).getPath());
        }
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        } else {
            mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
            mediaPlayer.start();
        }

        seek_bar.setMax(mediaPlayer.getDuration() / 1000);
        metaData(uri);

    }

    private void initView() {
        song_name = findViewById(R.id.song_name);
        song_artist = findViewById(R.id.song_artist);
        duration_played = findViewById(R.id.duration_played);
        duration_total = findViewById(R.id.duration_total);
        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getBaseContext();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
        menu_btn = findViewById(R.id.menu_btn);
        cover_art = findViewById(R.id.cover_art);
        shuffle_btn = findViewById(R.id.shuffle_btn);
        skip_pre_btn = findViewById(R.id.skip_pre_btn);
        playPause_btn = findViewById(R.id.playPause_btn);
        skip_next_btn = findViewById(R.id.skip_next_btn);
        repeat_btn = findViewById(R.id.repeat_btn);
        seek_bar = findViewById(R.id.seek_bar);

    }

    private void metaData(@NonNull Uri uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        int durationTotal = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) / 1000;
        duration_total.setText(formattedTime(durationTotal));
        song_name.setText(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        song_artist.setText(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));

        byte[] art = retriever.getEmbeddedPicture();
        if(art != null)
        {
            Glide.with(this)
                    .asBitmap()
                    .load(art)
                    .into(cover_art);
        }
        else {
            Glide.with(this)
                    .asBitmap()
                    .load(R.drawable.item_img)
                    .into(cover_art);
        }
        try {
            retriever.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        nextBtnClicked();
        if(mediaPlayer!=null) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(this);
        }
    }
}