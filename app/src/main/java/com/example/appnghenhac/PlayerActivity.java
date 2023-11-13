package com.example.appnghenhac;

import static com.example.appnghenhac.MainActivity.listBaiHat;

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

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {
    TextView song_name, song_artist,duration_played, duration_total;
    ImageView back_btn, menu_btn, cover_art, shuffle_btn, skip_pre_btn, play_btn, skip_next_btn, repeat_btn;
    SeekBar seek_bar;
    int position = -1;
    static ArrayList<FileBaiHat> listSongs = new ArrayList<>();
    static Uri uri;
    static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        initView();
        getIntentMethod();
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
            play_btn.setImageResource(R.drawable.pause);
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
        play_btn = findViewById(R.id.play_btn);
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
    }
}