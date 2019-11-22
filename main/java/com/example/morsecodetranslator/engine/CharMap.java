package com.example.morsecodetranslator.engine;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class CharMap {
    private final String fileMorseMap="morseCodeCharMap.txt";
    private Context C;

    private Map charMapFromRaw;
    private Map charMapFromMorse;


    public CharMap(Context context){
        C=context;
        loadCharMapFromFile(context);
        Log.d("info","CharMap was create and fill!");
    }

    private void loadCharMapFromFile(Context c){
        charMapFromRaw=FileLoader.GetCharMapFromFileCS(c,fileMorseMap);
        charMapFromMorse=FileLoader.GetCharMapFromFileSC(c,fileMorseMap);
    }


    public String getMorseByChar(Character c){
        if(charMapFromRaw.containsKey(c))
            return (String) charMapFromRaw.get(c);
        else
            return null;
    }

    public boolean IsExistSomeMorse(String s){
        s=s.toLowerCase();
        return charMapFromMorse.containsKey(s);
    }
    public Character getRawByMorse(String s){
        s=s.toLowerCase();
        if(charMapFromMorse.containsKey(s))
            return (Character) charMapFromMorse.get(s);
        else
            return null;
    }
}
