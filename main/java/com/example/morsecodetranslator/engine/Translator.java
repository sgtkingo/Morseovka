package com.example.morsecodetranslator.engine;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Translator {
    final CharMap LocalCharMap;
    final Character US=(char)(0);

    public String resultRaw;
    public String resultMorse;
    public Context getContext;

    public Translator(Context context){
        resultMorse="";
        resultRaw="";
        LocalCharMap=new CharMap(context);
        getContext=context;
    }

    public String getSpace(){
        return (US+" ");
    }

    public void clearBuffer(){
        resultRaw="";
        resultMorse="";
    }

    private void saveResults(String r, String m){
        resultMorse=m;
        resultRaw=r;
    }

    public String TranslateToMorse(String r){

        String result="";
        String codeFragment="";

        for (int i=0;i<r.length();i++){
            codeFragment=LocalCharMap.getMorseByChar(r.charAt(i));
            if(codeFragment !=null){
                result+=codeFragment;
                result+=US; //US UnitSeparator
            }
        }
        saveResults(r,result);

        return result;
    }

    public String TranslateFromMorse(String m){
        String result="";
        String codeFragment="";
        Character lastResult=null;
        int index=0;

        for (int i=0;i<m.length();i++){
            codeFragment = "";
            lastResult=null;

            for(int j=i;j<m.length();j++){
                codeFragment+=m.charAt(j);
                if(LocalCharMap.IsExistSomeMorse(codeFragment) ) {
                    index=j;
                    lastResult=LocalCharMap.getRawByMorse(codeFragment);
                }
            }
            if(lastResult!=null){
                result+=lastResult;
                i=index;
            }
        }
        saveResults(result,m);
        return result;
    }
}
