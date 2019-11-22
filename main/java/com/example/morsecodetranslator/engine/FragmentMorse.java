package com.example.morsecodetranslator.engine;

import android.media.midi.MidiOutputPort;
import android.util.Log;

public class FragmentMorse {
    public String morseCode;
    public Character rawChar;

    public FragmentMorse(String mc, Character c){
        morseCode=mc;
        rawChar=c;
    }

    @Override
    public String toString(){
        return (rawChar+"|"+morseCode);
    }

    public void printFragment(){
        Log.d("fragment-info",this.toString());
    }

}
