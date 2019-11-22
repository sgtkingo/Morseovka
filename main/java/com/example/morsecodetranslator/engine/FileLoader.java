package com.example.morsecodetranslator.engine;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class FileLoader {
    private static String LoadFile(Context context, String fn){
        String result="";

        try {
            InputStream is = context.getAssets().open(fn);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = reader.readLine())!=null){
                result += line;
            }
            reader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            return result;
        }
    }

    private static Map<Character, String> ParseFileCS(String data){
        if(data=="")return null;
        Log.d("debug",data);

        Map<Character, String> result=new HashMap<>();

        String[] lines=data.split(",");
        for (String l:lines) {
            String[] elements=l.split("\t");
            if(elements.length >= 2){
                result.put(elements[0].charAt(0), elements[1]);
            }
        }
        return result;
    }
    private static Map<String, Character> ParseFileSC(String data){
        if(data=="")return null;
        Log.d("debug",data);

        Map<String, Character> result=new HashMap<>();

        String[] lines=data.split(",");
        for (String l:lines) {
            String[] elements=l.split("\t");
            if(elements.length >= 2){
                result.put(elements[1],elements[0].charAt(0));
            }
        }
        return result;
    }

    public static Map<Character, String> GetCharMapFromFileCS(Context context, String fn){
        return ParseFileCS(LoadFile(context,fn));
    }

    public static Map<String, Character> GetCharMapFromFileSC(Context context, String fn){
        return ParseFileSC(LoadFile(context,fn));
    }
}
