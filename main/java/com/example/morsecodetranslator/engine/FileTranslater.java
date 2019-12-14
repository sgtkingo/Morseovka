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
    public static String globalResultData="";


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

    public boolean TranslateFile(Uri inFileUri, Uri outFileUri, boolean fileType){
        String data=FileLoader.LoadFile(inFileUri);
        //String data=FileLoader.LoadFileFromAsset(context,"test.txt");
        if(data=="" || data==null)return false;

        String[] parseData=data.split("\n");

        String resultData="";
        proggressStatus=0;
        for(String line:parseData){
            if(!fileType)resultData+=T.TranslateToMorse(line);
            if(fileType)resultData+=T.TranslateFromMorse(line);
            resultData+="\n";
            Progress(100/(parseData.length));
        }
        Progress(100/(parseData.length));

        globalResultData=resultData;
        return FileSaver.SaveFile(outFileUri,resultData);
    }
}
