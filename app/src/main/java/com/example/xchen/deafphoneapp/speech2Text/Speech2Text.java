package com.example.xchen.deafphoneapp.speech2Text;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import com.example.xchen.deafphoneapp.PhoneMain;
import java.util.Locale;

/**
 * Created by xchen on 02.02.2017.
 */

public class Speech2Text  {

    private final PhoneMain phoneMain;

    public Speech2Text(PhoneMain phoneMain){
        this.phoneMain = phoneMain;
    }

    /**
     * Input of microfon
     */
    public void promptSpeechInput(){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something!");
        try{
            phoneMain.startActivityForResult(i, 100);
        }catch(ActivityNotFoundException a){
            Toast.makeText(phoneMain, "Sorry, Your device doesn't support speech language!", Toast.LENGTH_LONG).show();
        }
    }

}
