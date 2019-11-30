package com.example.morsecodetranslator.engine;

import android.content.res.Resources;
import android.media.MediaPlayer;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.morsecodetranslator.R;

import java.util.HashMap;
import java.util.Map;

public class Player {
    private Map playMap;

    static Translator T;

    public Player(Translator t){
        T=t;
        playMap=new HashMap<Character,String>();
    }

    public void createPlayMap(){
        Character c=null;
        String txtBuffer=T.resultRaw;
        for (int i=0;i<txtBuffer.length();i++){
            c=txtBuffer.charAt(i);
            playMap.put(c,T.LocalCharMap.getMorseByChar(c));
        }
    }
    public Map getPlayMap(){
        createPlayMap();
        return playMap;
    }

    public void playSoundDot(){
        final MediaPlayer mp=MediaPlayer.create(T.getContext, R.raw.beepdot);
        mp.start();
    }

    public void playSoundLine(){
        final MediaPlayer mp=MediaPlayer.create(T.getContext, R.raw.beepline);
        mp.start();
    }

    public void playFragment(FragmentMorse f){
        char[] data=(f.morseCode).toCharArray();
        for (int i=0;i<data.length;i++){
            if(data[i]=='.')playSoundDot();
            if(data[i]=='-')playSoundLine();
            try {
                Thread.sleep(100);
            }
             catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
