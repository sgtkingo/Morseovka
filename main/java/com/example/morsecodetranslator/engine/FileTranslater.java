package com.example.morsecodetranslator.engine;

import android.content.Context;
import android.net.Uri;
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

    public boolean TranslateFile(Uri inFilePath, String outFileName){
        String data=FileLoader.LoadFile(inFilePath);
        //String data=FileLoader.LoadFileFromAsset(context,"test.txt");
        if(data=="" || data==null)return false;

        String[] parseData=data.split("\n");

        String resultData="";

        for(String line:parseData){
            resultData+=T.TranslateToMorse(line);
            resultData+="\n";
            Progress(100/(parseData.length+1));
        }

        return FileSaver.SaveFile(context,outFileName,resultData);
    }
}
