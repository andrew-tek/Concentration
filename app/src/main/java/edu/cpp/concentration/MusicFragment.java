package edu.cpp.concentration;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment {

    MediaPlayer mediaPlayer;
    private View thisFragmentView;
    Button musicToggle;

    //runs once, when the fragment is created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    //runs every time the fragment is reinitialized on state-change (including the very first time)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View thisFragmentView = inflater.inflate(R.layout.fragment_music, container, false);
        playMusic();
        if(musicToggle == null) {
            musicToggle = (Button) thisFragmentView.findViewById(R.id.toggleMusicButton);
        }
        return thisFragmentView;
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseMusic();
        Log.i("PAUSE", "Hello from onPause!");
    }

    //runs once when the game is actually finished and the fragment is permanently destroyed
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopMusic();
        Log.i("DESTROY", "Hello from onDestroy!");
    }

    private void playMusic(){
        if(mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getActivity(), R.raw.mario_song);
        }
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    private void stopMusic(){
        if(mediaPlayer!= null){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void pauseMusic(){
        if(mediaPlayer!= null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }


    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

}
