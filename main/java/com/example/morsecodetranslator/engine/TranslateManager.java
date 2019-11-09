package com.example.morsecodetranslator.engine;

import android.widget.TextView;

public class TranslateManager {
    private Translator T;
    private Player P;

    public TranslateManager(){
        T=new Translator();
        P=new Player(T);
    }

    public String TranslateRaw(String raw){
        return T.TranslateToMorse(raw);
    }
    public String TranslateMorse(String mc){
        return T.TranslateFromMorse(mc);
    }

    public Translator getTranslator(){
        return T;
    }
    public Player getPlayer(){
        return P;
    }

}
