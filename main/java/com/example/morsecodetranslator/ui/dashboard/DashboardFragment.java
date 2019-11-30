package com.example.morsecodetranslator.ui.dashboard;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.morsecodetranslator.R;
import com.example.morsecodetranslator.engine.TranslateManager;
import com.example.morsecodetranslator.ui.home.HomeFragment;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    private Button btnDot;
    private Button btnLine;
    private ImageButton btnSpace;
    private ImageButton btnClear;

    private TextView txtRaw;
    private TextView txtMorse;

    private View primalRoot;
    private final int bufferSize=75;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        primalRoot=root;

        createContent();
        return root;


    }

    private void createContent(){

        btnDot=(Button)primalRoot.findViewById(R.id.btn_Dot);
        btnLine=(Button)primalRoot.findViewById(R.id.btn_Line);
        btnSpace=(ImageButton)primalRoot.findViewById(R.id.btnSpace);
        btnClear=(ImageButton)primalRoot.findViewById(R.id.btnClear);

        txtRaw=(TextView)primalRoot.findViewById(R.id.txt_raw_mem);
        txtMorse=(TextView)primalRoot.findViewById(R.id.txt_morse_mem);

        clearAll();

        HomeFragment.TM.clearTranslator();

        btnDot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    txtMorse.setText(txtMorse.getText() + ".");
                    txtRaw.setText(HomeFragment.TM.TranslateMorse(txtMorse.getText().toString()));
                    HomeFragment.TM.playSound(true);
                    checkTextLenght();
                }
                return false;
            }
        });

        btnLine.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    txtMorse.setText(txtMorse.getText() + "-");
                    txtRaw.setText(HomeFragment.TM.TranslateMorse(txtMorse.getText().toString()));
                    HomeFragment.TM.playSound(false);
                    checkTextLenght();
                }
                return false;
            }
        });

        btnSpace.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    txtMorse.setText(txtMorse.getText() + HomeFragment.TM.getTranslator().getSpace());
                    txtRaw.setText(HomeFragment.TM.TranslateMorse(txtMorse.getText().toString()));
                    checkTextLenght();
                }
                return false;
            }
        });

        btnClear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    clearAll();
                    Toast.makeText(getContext(), "Clearing...", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    private void checkTextLenght(){
        if(txtMorse.getText().length() > bufferSize){
            clearAll();
        }
    }

    private void clearAll(){
        txtRaw.setText("");
        txtMorse.setText("");
    }
}