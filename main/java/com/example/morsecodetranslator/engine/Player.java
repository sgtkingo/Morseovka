package com.example.morsecodetranslator.engine;

import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class Player {
    private Map playMap;

    Translator T;

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
}
