package com.example.xchen.deafphoneapp;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xchen.deafphoneapp.speech2Text.Speech2Text;
import com.example.xchen.deafphoneapp.text2Speech.Text2Speech;

import java.util.ArrayList;


public class PhoneMain extends AppCompatActivity {

    public final int REQUEST_CODE_SPEECH_INPUT = 100;

    private TextView outputText;
    private Speech2Text speech2Text;

    private EditText inputText;
    private Text2Speech text2Speech;

//    private TelephonyManager tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_main);

        outputText = (TextView) findViewById(R.id.outputText);
        speech2Text = new Speech2Text(this);

        inputText = (EditText) findViewById(R.id.inputText);
        text2Speech = new Text2Speech(this);

//        tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
//        tm.listen(speech2Text, PhoneStateListener.LISTEN_CALL_STATE);
    }


    //==========Text To Speech===============================
    /**
     * Quelle
     * https://www.tutorialspoint.com/android/android_text_to_speech.htm
     * */
    public void onClickSpeechButton(View v){
        String toSpeak = inputText.getText().toString();
        Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_LONG).show();
        text2Speech.speak(toSpeak);
    }

//    public void onPause(){
//        text2Speech.shutdown();
//        super.onPause();
//    }
    //====================================================

    //============Speech To Text =========================
    /**
     * Quelle:
     * http://www.androidhive.info/2014/07/android-speech-to-text-tutorial/
     */
    public void onClickRecognizeButton(View v){
        if(v.getId() == R.id.recognizeButton){
            speech2Text.startVoiceRecognitionActivity();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQUEST_CODE_SPEECH_INPUT: // = 100
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    outputText.setText(result.get(0));
                }
                break;
            default: System.out.println("Speech Input Failure");
        }
    }
    //====================================================


}
