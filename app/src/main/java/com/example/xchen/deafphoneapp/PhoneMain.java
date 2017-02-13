package com.example.xchen.deafphoneapp;

import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xchen.deafphoneapp.speech2Text.Speech2Text;
import com.example.xchen.deafphoneapp.text2Speech.Text2Speech;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;


public class PhoneMain extends AppCompatActivity {

    public final int REQUEST_CODE_SPEECH_INPUT = 100;

    private TextView outputText;
    private Speech2Text speech2Text;
    private Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    private boolean isListining = false;

    private EditText inputText;
    private Text2Speech text2Speech;

    private String LOG_TAG = "PhoneMain";
    SpeechRecognizer sr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_main);

        boolean available = SpeechRecognizer.isRecognitionAvailable(this);
        Log.i(LOG_TAG, ""+ available);
        if (available){
            sr = SpeechRecognizer.createSpeechRecognizer(this);

            sr.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {
                    Log.i(LOG_TAG, "onReadyForSpeech, Bundle params = " + params.toString());
                }

                @Override
                public void onBeginningOfSpeech() {
                    Log.i(LOG_TAG, "onBeginningOfSpeech");

                }

                @Override
                public void onRmsChanged(float rmsdB) {
//                    Log.i(LOG_TAG, "onRmsChanged, float rmsdB = " + rmsdB);

                }

                @Override
                public void onBufferReceived(byte[] buffer) {
                    Log.i(LOG_TAG, "onBufferReceived, byte[] buffer = " + Arrays.toString(buffer));
                }

                @Override
                public void onEndOfSpeech() {
                    Log.i(LOG_TAG, "onEndOfSpeech");
                }

                @Override
                public void onError(int error) {
                    Log.i(LOG_TAG, "onError, int error = " + getErrorText(error));
                    if(error == SpeechRecognizer.ERROR_RECOGNIZER_BUSY ){
                        sr.cancel();
                        isListining = false;
                        repeatListening();
                    } else if( error == SpeechRecognizer.ERROR_NO_MATCH){
                        isListining = false;
                        repeatListening();
                    } else if( error == SpeechRecognizer.ERROR_SPEECH_TIMEOUT){
                        isListining = false;
                        repeatListening();
                    }
                }


                @Override
                public void onResults(Bundle results) {
                    isListining = false;
                    repeatListening();
                    //Result
                    Log.i(LOG_TAG, "onResults, Bundle results = "+results.toString());
                    String str = new String();
                    ArrayList<String> result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    for (int i = 0; i < 1 /*result.size()*/; i++)
                    {
                        str += result.get(i) + ". ";
                    }
                    outputText.setText(str);
                }

                @Override
                public void onPartialResults(Bundle partialResults) {
                    isListining = false;

                    Log.i(LOG_TAG, "onPartialResults, Bundle partialResults = "+ partialResults.toString());
                    String str = new String();
                    ArrayList<String> result = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    for (int i = 0; i < result.size(); i++)
                    {
                        str += result.get(i) + ". ";
                    }
                    outputText.setText(str);
                }

                @Override
                public void onEvent(int eventType, Bundle params) {
                    Log.i(LOG_TAG, "onEvent, int eventType = "+ eventType + " , Bundle params = "+ params.toString());
                }
            });
        }

        outputText = (TextView) findViewById(R.id.outputText);
        speech2Text = new Speech2Text(this);

        inputText = (EditText) findViewById(R.id.inputText);
        text2Speech = new Text2Speech(this);
    }



    public String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    //==========Text To Speech===============================
    /**
     * Quelle
     * https://www.tutorialspoint.com/android/android_text_to_speech.htm
     * */
    public void onClickSpeechButton(View v){
        String toSpeak = inputText.getText().toString();
        Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_LONG).show();
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
            if(isListining){
                sr.cancel();
                isListining = false;
            }
            if(!isListining) {
                createIntent();
                repeatListening();
            }
        }
    }


    public void createIntent(){
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 3000L);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
    }

    public void repeatListening(){
        //            speech2Text.startVoiceRecognitionActivity();
        if(!isListining){
            isListining = true;
            sr.startListening(intent);
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
