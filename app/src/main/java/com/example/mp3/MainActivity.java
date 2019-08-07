package com.example.mp3;


import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    TextView txtTitle, txtTimeSong, txtTimeTotal;
    SeekBar skSong;
    ImageView imageView;
    ImageButton btnPrev, btnPlay, btnNext, btnReturn, btnShuffle;

    ArrayList<Song> arraySong;
    int position = 0;
    MediaPlayer mediaPlayer;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        AddSong();


        animation = AnimationUtils.loadAnimation(this, R.anim.disc_rotate);

        KhoiTaoMediaPlayer();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if (position > arraySong.size() - 1) {
                    position = 0;
                }

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }

                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                SetTimeTotal();
                UpdateTimeSong();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if (position < 0) {
                    position = arraySong.size() - 1;
                }
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                SetTimeTotal();
                UpdateTimeSong();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {

                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                }else {

                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                }
                SetTimeTotal();
                UpdateTimeSong();
                imageView.startAnimation(animation);

            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (v.isSelected()) {
                    mediaPlayer.setLooping(true);
                    btnShuffle.setEnabled(false);
                }else {
                    mediaPlayer.setLooping(false);
                    btnShuffle.setEnabled(true);
                }
            }
        });

        btnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (v.isSelected()) {
                    mediaPlayer.setLooping(false);
                }else {
                    mediaPlayer.setLooping(true);

                }
            }
        });

        skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(skSong.getProgress());
            }
        });

    }

    private void UpdateTimeSong() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat time = new SimpleDateFormat("mm:ss");
                txtTimeSong.setText(time.format(mediaPlayer.getCurrentPosition()));

                skSong.setProgress(mediaPlayer.getCurrentPosition());

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {

                        position++;
                        if (position > arraySong.size() - 1) {
                            position = 0;
                        }
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        if (btnShuffle.isClickable() == true) {
                            Collections.shuffle(arraySong);
                        }
                        KhoiTaoMediaPlayer();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                        SetTimeTotal();
                        UpdateTimeSong();
                    }
                });

                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    private void SetTimeTotal() {
        SimpleDateFormat time = new SimpleDateFormat("mm:ss");
        txtTimeTotal.setText(time.format(mediaPlayer.getDuration()));
        skSong.setMax(mediaPlayer.getDuration());
    }

    private void KhoiTaoMediaPlayer() {
        mediaPlayer = MediaPlayer.create(MainActivity.this, arraySong.get(position).getFile());
        txtTitle.setText(arraySong.get(position).getTitle());
    }

    private void AddSong() {
        arraySong = new ArrayList<>();
        arraySong.add(new Song("A World Without Danger ", R.raw.a_world_without_danger));
        arraySong.add(new Song("My Life Is A Party (R.I.O. Video Edit) - Italobrothers ", R.raw.my_life_is_a_party));
        arraySong.add(new Song("Mz Hyde - Halestorm ", R.raw.mz_hyde));
        arraySong.add(new Song("Angel With A Shotgun - Nightcore ", R.raw.angel_with_a_shotgun));
        arraySong.add(new Song("Switch On! (Fourze) - Anna Tsuchiya ", R.raw.switch_on));
    }

    private void AnhXa() {
        txtTimeSong = (TextView) findViewById(R.id.textViewTimeSong);
        txtTimeTotal = (TextView) findViewById(R.id.textViewTimeTotal);
        txtTitle = (TextView) findViewById(R.id.textviewTitle);
        skSong = (SeekBar) findViewById(R.id.seekBarSong);
        btnNext = (ImageButton) findViewById(R.id.imageButtonNext);
        btnPlay = (ImageButton) findViewById(R.id.imageButtonPlay);
        btnPrev = (ImageButton) findViewById(R.id.imageButtonPrev);
        btnReturn = (ImageButton) findViewById(R.id.imageButtonReturn);
        btnShuffle = (ImageButton) findViewById(R.id.imageButtonShuffle);
        imageView = (ImageView) findViewById(R.id.image);
    }
}
