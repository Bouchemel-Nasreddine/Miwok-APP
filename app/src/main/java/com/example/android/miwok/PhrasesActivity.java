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

public class PhrasesActivity extends AppCompatActivity {

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
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
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
        words.add(new english("Where are you going?","minto wuksus", R.raw.phrase_where_are_you_going));
        words.add(new english("What is your name?","tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        words.add(new english("My name is","oyaaset", R.raw.phrase_my_name_is));
        words.add(new english("How are you feeling?","’michәksәs?", R.raw.phrase_how_are_you_feeling));
        words.add(new english("I'm feeling good.","kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new english("Are you coming?","әәnәs'aa?", R.raw.phrase_are_you_coming));
        words.add(new english("Yes, i'm coming.","hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        words.add(new english("I'm coming","әәnәm", R.raw.phrase_im_coming));
        words.add(new english("Let's go.","yoowutis", R.raw.phrase_lets_go));
        words.add(new english("Come here.","әnni'nem", R.raw.phrase_come_here));

        WordsAdapter wordsAdapter = new WordsAdapter(this, words, R.color.category_phrases);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(wordsAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    releaseplayer();
                    if (mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT) ==
                            AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, words.get(position).getAudioResourceId());
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
