package com.example.morsecodetranslator.ui.home;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.morsecodetranslator.R;
import com.example.morsecodetranslator.engine.TranslateManager;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private EditText rawInputText;
    private EditText morseInputText;
    private TextView fragmentRaw;
    private TextView fragmentMorse;

    private ImageButton PlayBtn;

    private TranslateManager TM;

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
        rawInputText=root.findViewById(R.id.rawInput);
        fragmentRaw=root.findViewById(R.id.textFragmentRaw);

        morseInputText=root.findViewById(R.id.morseInput);
        fragmentMorse=root.findViewById(R.id.textFragmentMorse);

        PlayBtn=root.findViewById(R.id.imagePlay);

        TM=new TranslateManager();

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
                Map M=TM.getPlayer().getPlayMap();
                String mf="";
                Character c=null;

                for (Object key:M.keySet()) {
                    c=(Character)key;
                    mf=(String)M.get(c);

                    fragmentRaw.setText(c);
                    fragmentMorse.setText(mf);
                    try {
                        Thread.sleep(250);
                    }
                    catch (Exception e){
                        e.printStackTrace();
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

}