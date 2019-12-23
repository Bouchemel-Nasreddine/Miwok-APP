package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseplayer();
        }
    };

    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseplayer();
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //creating english numbers array
        final ArrayList<english> words = new ArrayList<>();
        words.add(new english("father","әpә", R.drawable.family_father, R.raw.family_father));
        words.add(new english("mother","әṭa", R.drawable.family_mother, R.raw.family_mother));
        words.add(new english("son","angsi", R.drawable.family_son, R.raw.family_son));
        words.add(new english("daughter","’tune", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new english("bother","taachi", R.drawable.family_younger_brother, R.raw.family_older_brother));
        words.add(new english("sister","teṭe", R.drawable.family_younger_sister, R.raw.family_older_sister));
        words.add(new english("grandfather","paapa", R.drawable.family_grandfather, R.raw.family_grandfather));
        words.add(new english("grandmother","ama", R.drawable.family_grandmother, R.raw.family_grandmother));

        WordsAdapter wordsAdapter = new WordsAdapter(this, words, R.color.category_family);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(wordsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                releaseplayer();
                if (mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT) ==
                        AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this, words.get(position).getAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }
    private void releaseplayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseplayer();
    }
}
