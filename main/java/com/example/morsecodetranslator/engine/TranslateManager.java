package com.example.morsecodetranslator.engine;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

public class TranslateManager {
    private Translator T;
    private Player P;
    private Fragmenter F;
    private FileTranslater FT;
    private Context C;

    public TranslateManager(Context context){
        C=context;
        T=new Translator(C);
        P=new Player(T);
        F=new Fragmenter(T);
        FT=new FileTranslater(C,T);
    }

    public String TranslateRaw(String raw){
        return T.TranslateToMorse(raw);
    }
    public String TranslateMorse(String mc){
        return T.TranslateFromMorse(mc);
    }
    public List<FragmentMorse> getFragmentsList() { return F.getFragmentMorsesList();}

    public Translator getTranslator(){
        return T;
    }
    public Player getPlayer(){
        return P;
    }
    public Fragmenter getFragmenter(){ return F; }
    public FileTranslater getFileTranslator(){ return FT; }

    public void clearTranslator(){
        T.clearBuffer();
    }

    public void playSound(boolean mode){
        if(mode)P.playSoundDot();
        else P.playSoundLine();
    }

    public void playFragment(FragmentMorse f){
        P.playFragment(f);
    }

    public boolean translateFile(String in, String out){
        return FT.TranslateFile(in,out);
    }

}
