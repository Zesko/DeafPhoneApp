package com.example.xchen.deafphoneapp;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xchen.deafphoneapp.speech2Text.Speech2Text;

import java.util.ArrayList;
import java.util.Locale;


public class PhoneMain extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private TextView outputText;
    private Speech2Text speech2Text;

    private TextToSpeech text2Speech;
    private EditText inputText;
    private Button speak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_main);

        outputText = (TextView) findViewById(R.id.outputText);
        speech2Text = new Speech2Text(this);

        inputText = (EditText) findViewById(R.id.inputText);
        speak = (Button) findViewById(R.id.speakButton);
//        text2Speech = new Text2Speech();
        speak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
//                speakOutNow();
            }
        });
    }


    //======================
    public void onClickSpeechButton(View v){
        speakOutNow();
    }


    private void speakOutNow(){
        String text = inputText.getText().toString();
        text2Speech.speak(text,TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            int language = text2Speech.setLanguage(Locale.getDefault());
            if(language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED){
                speak.setEnabled(true);
                speakOutNow();
            } else {

            }
        } else {

        }
    }

    //===========================

    public void onClickRecognizeButton(View v){
        if(v.getId() == R.id.recognizeButton){
            speech2Text.promptSpeechInput();
        }
    }

    public void onActivityResult(int request_code, int result_code, Intent intent){
        super.onActivityResult(request_code, result_code, intent);
        switch(request_code){
            case 100: if(result_code == RESULT_OK && intent != null){
                ArrayList<String> result = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                outputText.setText(result.get(0));
            }
                break;
        }
    }

}
