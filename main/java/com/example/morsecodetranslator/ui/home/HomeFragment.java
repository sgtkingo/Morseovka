package com.example.morsecodetranslator.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


/*import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;*/

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.morsecodetranslator.MainActivity;
import com.example.morsecodetranslator.R;
import com.example.morsecodetranslator.engine.FragmentMorse;
import com.example.morsecodetranslator.engine.TranslateManager;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private EditText rawInputText;
    private EditText morseInputText;
    private TextView fragmentRaw;
    private TextView fragmentMorse;

    private ImageButton PlayBtn;
    public static Switch swAudio;
    public static Switch swFlash;

    public static TranslateManager TM;
    private View primalRoot;

    private final int CAMERA_REQUEST = 101;
    public static boolean hasCameraFlash = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        /*final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        textView.setText("Morsecode Translator ");*/
        primalRoot=root;

        //Camera permission request
        try {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
            hasCameraFlash = getContext().getPackageManager().
                    hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
            if(!hasCameraFlash)
            {
                swFlash.setEnabled(false);
                Toast.makeText(getContext(),"No flash on camera detected!",Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //Modules setting
        rawInputText=root.findViewById(R.id.rawInput);
        fragmentRaw=root.findViewById(R.id.textFragmentRaw);

        morseInputText=root.findViewById(R.id.morseInput);
        fragmentMorse=root.findViewById(R.id.textFragmentMorse);

        PlayBtn=root.findViewById(R.id.imagePlay);
        swAudio=root.findViewById(R.id.audioSwitch);
        swFlash=root.findViewById(R.id.flashSwitch);
        swAudio.setChecked(true);

        TM=new TranslateManager(this.getContext());

        rawInputText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                morseInputText.setText(TM.TranslateRaw(rawInputText.getText().toString()));
                textAutoSize(morseInputText);
                return false;
            }
        });

        morseInputText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                rawInputText.setText(TM.TranslateMorse(morseInputText.getText().toString()));
                textAutoSize(rawInputText);
                return false;
            }
        });

        PlayBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    for (FragmentMorse f : TM.getFragmentsList()) {
                        fragmentRaw.setText(f.rawChar.toString());
                        fragmentMorse.setText(f.morseCode);

                        if(swAudio.isChecked()) TM.playFragment(f);
                        if(swFlash.isChecked() && hasCameraFlash) TM.flashFragment(f);
                        try {
                            primalRoot.invalidate();
                            Thread.sleep(150);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                return false;
            }
        });

        //morseInputText.addTextChangedListener();

        return root;
    }

    private void textAutoSize(EditText ET){
        int textSize=48-(ET.length()/2);
        if(textSize  < 20)textSize=20;
        ET.setTextSize(textSize);
    }


    /*private void flashLightOn() {
        CameraManager cameraManager = (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, true);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void flashLightOff() {
        CameraManager cameraManager = (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    hasCameraFlash = getContext().getPackageManager().
                            hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
                    if(!hasCameraFlash)
                    {
                        swFlash.setEnabled(false);
                        Toast.makeText(getContext(),"No flash on camera detected!",Toast.LENGTH_SHORT).show();
                    }
                    else swFlash.setEnabled(true);
                } else {
                    swFlash.setEnabled(false);
                    Toast.makeText(getContext(), "Permission Denied for the Camera", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}