package com.example.morsecodetranslator.ui.notifications;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.morsecodetranslator.R;
import com.example.morsecodetranslator.engine.FileTranslater;
import com.example.morsecodetranslator.engine.TranslateManager;
import com.example.morsecodetranslator.ui.home.HomeFragment;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private View primalRoot;
    private final int RESULT_OK=-1;
    private final int RAWFILE=R.drawable.ic_fileraw;
    private final int MORSEFILE=R.mipmap.ic_icon_morse_foreground;

    private TranslateManager TM;

    private Uri inFileUri=null;
    private Uri outFileUri=null;

    private boolean fileType=false;

    private static final int REQUEST_EXTERNAL_STORAGE = 900;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    TextView txtSelectFile;
    EditText txtOutView;
    ImageButton btnSelectFile;
    EditText txtOutFile;
    Button btnProcessFile;
    ProgressBar progressBar;
    ImageView imgFrom;
    ImageView imgTo;
    ImageView imgDataDirection;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        primalRoot=root;
        //Verify SD file read permision
        verifyStoragePermissions(getActivity());
        CreateContent();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 200:
                if(resultCode==RESULT_OK){
                    inFileUri=Uri.parse(data.getData().getPath());
                    txtSelectFile.setText(inFileUri.toString());
                    fileType=inFileUri.toString().contains(".mrc");
                    fileSwichMethod(fileType);
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
         imgFrom=primalRoot.findViewById(R.id.imgFrom);
         imgTo=primalRoot.findViewById(R.id.imgTo);
         imgDataDirection=primalRoot.findViewById(R.id.imgProcessDirection);

        TM=HomeFragment.TM;
        TM.clearTranslator();
        TM.getFileTranslator().setProgressBar(progressBar);

        btnSelectFile.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    Intent myFileIntent=new Intent(Intent.ACTION_GET_CONTENT);
                    myFileIntent.setType("*/*");
                    startActivityForResult(myFileIntent,200);
                    return true;
                }
                return false;
            }
        });

        btnProcessFile.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                if(inFileUri==null)return false;
                imgDataDirection.setColorFilter(Color.TRANSPARENT);

                String outFileName=txtOutFile.getText().toString();
                if(fileType)outFileUri=createOutFileUri(outFileName+".txt",inFileUri);
                else outFileUri=createOutFileUri(outFileName+".mrc",inFileUri);

                if(outFileUri==null || inFileUri==null){
                    Toast.makeText(getContext(), "Please, enter valid file name...", Toast.LENGTH_SHORT).show();
                    return false;
                }

                if(!TM.translateFile(inFileUri,outFileUri,fileType)){
                    imgDataDirection.setColorFilter(Color.RED);
                    Toast.makeText(getContext(),"File transcrypt errror...",Toast.LENGTH_SHORT).show();
                    return false;
                }
                else {
                    imgDataDirection.setColorFilter(Color.GREEN);
                    Toast.makeText(getContext(),"File transcrypt success!",Toast.LENGTH_SHORT).show();
                }
                udpadeTxtOut(FileTranslater.globalResultData);
                return true;
                }
                return false;
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

    private Uri createOutFileUri(String fn, Uri ifu){
        fn.trim();
        if(fn.compareTo(".txt")==0 || fn.compareTo(".mrc")==0)return null;
        String path=ifu.toString();
        int cutIndex=path.lastIndexOf("/");
        String clearPath=path.substring(0,cutIndex);
        clearPath+="/"+fn;

        Uri uri=Uri.parse(clearPath);
        return uri;
    }

    private void fileSwichMethod(boolean type){
        imgDataDirection.setColorFilter(Color.TRANSPARENT);
        if(type){
            Toast.makeText(getContext(),"Detect INPUT .mrc (Morse code) file!",Toast.LENGTH_SHORT).show();
            imgTo.setImageResource(RAWFILE);
            imgFrom.setImageResource(MORSEFILE);
        }
        else {
            Toast.makeText(getContext(),"Detect INPUT text(raw) file!",Toast.LENGTH_SHORT).show();
            imgTo.setImageResource(MORSEFILE);
            imgFrom.setImageResource(RAWFILE);
        }
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

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        // Check if we have write permission
        permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}