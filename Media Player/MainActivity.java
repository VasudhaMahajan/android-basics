package com.s1.media;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button b1;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=(Button)findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer=new MediaPlayer();
                String media_path="sdcard/TuHai.mp3";
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                Uri uri=Uri.parse(media_path);
                try {
                    mediaPlayer.setDataSource(getApplicationContext(),uri);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(getApplicationContext(),"PlayBack Started",Toast.LENGTH_LONG).show();
                    b1.setEnabled(false);
                } catch (IOException e) {e.printStackTrace();

                }
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                            b1.setEnabled(true);
                        mediaPlayer.release();
                        mediaPlayer=null;
                    }
                });
            }
        });
    }
}
