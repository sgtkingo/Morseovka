package com.example.morsecodetranslator.engine;

import android.content.Context;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class FileTranslater {
    Context context;
    Translator T;
    ProgressBar progressBar;

    private int proggressStatus;


    public FileTranslater(Context c, Translator t){
        context=c;
        T=t;
    }
    public void setProgressBar(ProgressBar pb){
        progressBar=pb;
        proggressStatus=0;
        progressBar.setProgress(proggressStatus);
    }

    public void Progress(int progress){
        if(progressBar==null)return;
        proggressStatus+=progress;
        progressBar.setProgress(proggressStatus);
    }

    public boolean TranslateFile(String inFilePath, String outFileName){
        //String data=FileLoader.LoadFileAny(context,inFilePath);
        String data=FileLoader.LoadFileFromAsset(context,"test.txt");
        if(data=="")return false;

        String[] parseData=data.split("\n");

        String resultData="";

        for(String line:parseData){
            resultData+=T.TranslateToMorse(line);
            resultData+="\n";
            Progress(parseData.length);
        }

        FileLoader.SaveFileToAsset(context,outFileName,resultData);
        return true;
    }
}
