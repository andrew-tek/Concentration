package edu.cpp.concentration;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    Button musicToggle;

    public MusicFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        if(mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getActivity(), R.raw.mario_song);
            mediaPlayer.start();
        }
        musicToggle = (Button) view.findViewById(R.id.toggleMusicButton);

        return view;
    }
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

}
