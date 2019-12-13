package com.example.morsecodetranslator.engine;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

import androidx.core.app.ActivityCompat;

import com.example.morsecodetranslator.MainActivity;

public class Flasher {
    Context context;

    public Flasher(Context c){
        context=c;
    }

    public void Flash(boolean mode){
        int time=0;
        if(mode)time=100;
        else time=250;

        try {
            flashLightOn();
            try {
                Thread.sleep(time);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            flashLightOff();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void flashFragment(FragmentMorse f){
        char[] data=(f.morseCode).toCharArray();
        for (int i=0;i<data.length;i++){
            if(data[i]=='.')Flash(false);
            if(data[i]=='-')Flash(true);
            try {
                Thread.sleep(100);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void flashLightOn() {
        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, true);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void flashLightOff() {
        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}
