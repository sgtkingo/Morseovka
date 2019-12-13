package com.example.morsecodetranslator.ui.notifications;

import android.content.Intent;
import android.net.Uri;
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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private View primalRoot;
    private final int RESULT_OK=-1;
    private TranslateManager TM;

    private Uri inFileUri=null;
    private String selecFilePath="";
    private String selecFileName="";
    private String outFileName="";

    TextView txtSelectFile;
    EditText txtOutView;
    ImageButton btnSelectFile;
    EditText txtOutFile;
    Button btnProcessFile;
    ProgressBar progressBar;


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
                if(resultCode==RESULT_OK){
                    selecFilePath=data.getData().getPath();
                    selecFileName=data.getData().toString();
                    txtSelectFile.setText(selecFileName);
                    inFileUri=Uri.parse(data.getData().getPath());
                }
                break;
        }
    }

    private void CreateContent(){
         txtSelectFile=primalRoot.findViewById(R.id.txtFilepath);
         btnSelectFile=primalRoot.findViewById(R.id.btnPickFile);
         txtOutFile=primalRoot.findViewById(R.id.txtOutFileName);
         txtOutView=primalRoot.findViewById(R.id.txtOutView);
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
                if(outFileName.compareTo("")==0 || selecFilePath.compareTo("")==0){
                    Toast.makeText(getContext(), "Please, enter valid file name...", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(!TM.translateFile(inFileUri,outFileName+".mrc")){
                    Toast.makeText(getContext(),"File transcrypt errror...",Toast.LENGTH_SHORT).show();
                    return false;
                }
                else Toast.makeText(getContext(),"File transcrypt success!",Toast.LENGTH_SHORT).show();
                udpadeTxtOut(TM.getTranslator().resultMorse);
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

    private void udpadeTxtOut(String data){
        final int splitterValue=50;
        String result="";

        for(int i=0;i<(data.length()-splitterValue);i+=splitterValue){
            result+=data.substring(i,i+splitterValue);
            result+="\n";
        }
        txtOutView.setText(result);
    }
}