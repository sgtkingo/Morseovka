package com.example.morsecodetranslator.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.morsecodetranslator.R;
import com.example.morsecodetranslator.engine.TranslateManager;
import com.example.morsecodetranslator.ui.home.HomeFragment;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private View primalRoot;
    private final int RESULT_OK=200;
    private TranslateManager TM;

    private String selecFilePath;
    private String outFileName;

    TextView txtSelectFile;
    ImageButton btnSelectFile;
    EditText txtOutFile;
    Button btnProcessFile;
    ProgressBar progressBar;

    private boolean lock=false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        primalRoot=root;
        CreateContent();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 100:
                if(resultCode==RESULT_OK || resultCode==-1){
                    selecFilePath=data.getData().getPath();
                    txtSelectFile.setText(selecFilePath);
                }
                break;
        }
    }

    private void CreateContent(){
         txtSelectFile=primalRoot.findViewById(R.id.txtFilepath);
         btnSelectFile=primalRoot.findViewById(R.id.btnPickFile);
         txtOutFile=primalRoot.findViewById(R.id.txtOutFileName);
         btnProcessFile=primalRoot.findViewById(R.id.btnProcess);
         progressBar=primalRoot.findViewById(R.id.progressBar);

        TM=HomeFragment.TM;
        TM.clearTranslator();
        TM.getFileTranslator().setProgressBar(progressBar);

        btnSelectFile.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent myFileIntent=new Intent(Intent.ACTION_GET_CONTENT);
                myFileIntent.setType("*/*");
                startActivityForResult(myFileIntent,100);
                return true;
            }
        });

        btnProcessFile.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                outFileName=txtOutFile.getText().toString();
                if(outFileName=="" || outFileName=="Enter file name..."){
                    Toast.makeText(getContext(), "Please, enter valid file name...", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(!TM.translateFile(selecFilePath,outFileName+".mrc")){
                    Toast.makeText(getContext(),"File transcrypt errror...",Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getContext(),"File transcrypt success!",Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        txtOutFile.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                txtOutFile.setText("");
                return false;
            }
        });
    }
}