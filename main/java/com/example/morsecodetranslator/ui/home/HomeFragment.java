package com.example.morsecodetranslator.ui.home;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.morsecodetranslator.R;
import com.example.morsecodetranslator.engine.Translator;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private EditText rawInputText;
    private EditText morseInputText;

    private Translator T;

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
        T=new Translator();

        rawInputText=root.findViewById(R.id.rawInput);
        morseInputText=root.findViewById(R.id.morseInput);

        rawInputText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                morseInputText.setText(T.TranslateToMorse(rawInputText.getText().toString()));
                return false;
            }
        });

        morseInputText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                rawInputText.setText(T.TranslateFromMorse(morseInputText.getText().toString()));
                return false;
            }
        });

        return root;
    }
}