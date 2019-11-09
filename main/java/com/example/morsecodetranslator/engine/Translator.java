package com.example.morsecodetranslator.engine;

import android.util.Log;
import android.widget.Toast;

public class Translator {
    final CharMap LocalCharMap=new CharMap();
    final Character US=(char)(0);

    public String resultRaw;
    public String resultMorse;

    public Translator(){
        resultMorse="";
        resultRaw="";
    }

    public String TranslateToMorse(String r){
        resultRaw=r;

        String result="";
        String codeFragment="";

        for (int i=0;i<r.length();i++){
            codeFragment=LocalCharMap.getMorseByChar(r.charAt(i));
            if(codeFragment !=null){
                result+=codeFragment;
                result+=US; //US UnitSeparator
            }
        }
        resultMorse=result;
        return result;
    }

    public String TranslateFromMorse(String m){
        resultMorse=m;

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
                /*Log.d("info",result);
                Log.d("info",lastResult.toString());
                Log.d("info",Integer.toString(index));
                Log.d("info",Integer.toString(i));*/
            }
        }
        resultRaw=result;
        return result;
    }
}
