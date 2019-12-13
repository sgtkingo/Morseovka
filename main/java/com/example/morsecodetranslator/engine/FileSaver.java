package com.example.morsecodetranslator.engine;

import android.content.Context;

import java.io.FileOutputStream;

public class FileSaver {
    public static boolean SaveFile(Context context,String fn, String data){
        try {
            FileOutputStream fos = context.openFileOutput(fn, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
