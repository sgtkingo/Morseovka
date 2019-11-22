package com.example.morsecodetranslator.engine;

import java.util.ArrayList;
import java.util.List;

public class Fragmenter {
    private List<FragmentMorse> fragmentMorses;
    private Translator bound;

    public Fragmenter(Translator b){
        bound=b;
        fragmentMorses=new ArrayList<>();
    }

    private void FragmentData(){
        fragmentMorses.clear();
        String[] dataMorse=bound.resultMorse.split(bound.US.toString());
        Character c;

        for (String s: dataMorse) {
            if(bound.LocalCharMap.IsExistSomeMorse(s)){
                c=bound.LocalCharMap.getRawByMorse(s);
                fragmentMorses.add(new FragmentMorse(s,c));
            }
        }
        printFragments();
    }

    public List<FragmentMorse> getFragmentMorsesList(){
        FragmentData();
        return fragmentMorses;
    }

    public void printFragments(){
        for ( FragmentMorse f: fragmentMorses) {
            f.printFragment();
        }
    }
}
