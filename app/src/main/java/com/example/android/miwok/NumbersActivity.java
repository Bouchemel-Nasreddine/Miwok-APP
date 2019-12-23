
package com.example.android.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Context;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

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
        words.add(new english("one","lutte", R.drawable.number_one, R.raw.number_one));
        words.add(new english("two","otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new english("three","tolookoshu", R.drawable.number_three, R.raw.number_three));
        words.add(new english("four","’oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new english("five","massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new english("six","temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new english("seven","kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new english("eight","kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new english("nine","wo’e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new english("ten", "na’aacha", R.drawable.number_ten, R.raw.number_ten));

        WordsAdapter wordsAdapter = new WordsAdapter(this, words, R.color.category_numbers);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(wordsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                releaseplayer();
                if (mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT) ==
                        AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, words.get(position).getAudioResourceId());
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
