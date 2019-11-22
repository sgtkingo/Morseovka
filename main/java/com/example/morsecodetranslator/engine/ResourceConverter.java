package com.example.morsecodetranslator.engine;
import java.lang.reflect.Field;
import java.text.NumberFormat;

public class ResourceConverter {

    public static int getResId(Class<?> c, String resName) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public static int convertStringToInt(String s){
        try {
            return Integer.parseInt(s);
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    public static double convertStringToDouble(String s){
        try {
            return Double.parseDouble(s);
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    public static double convertStringToDouble(String s, NumberFormat nf){
        try {
            return (double)nf.parse(s);
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
