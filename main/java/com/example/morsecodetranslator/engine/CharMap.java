package com.example.morsecodetranslator.engine;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class CharMap {
    private final Map charMapFromRaw = new HashMap<Character, String>();
    private final Map charMapFromMorse = new HashMap<String, Character>();

    /*private final Character[] keys={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o',
            'p','q','r','s','t','u','v','w','x','y','z'
    };*/
    private final String[] values={".-","-...","-.-.","-..",".","..-.","--.","....","..",
            ".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-",
            "...-",".--","-..-","-.--","--.."};

    public CharMap(){
        fillCharMaps();
        Log.d("info","CharMap was create and fill!");
    }

    private Character getCharByInt(int i){
        return (char)(97+i);
    }

    private void fillCharMaps(){

        for (int i=0;i<values.length;i++){
            charMapFromRaw.put(getCharByInt(i),values[i]);
            charMapFromMorse.put(values[i],getCharByInt(i));
        }
    }

    public String getMorseByChar(Character c){
        if(charMapFromRaw.containsKey(c))
            return (String) charMapFromRaw.get(c);
        else
            return "";
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
