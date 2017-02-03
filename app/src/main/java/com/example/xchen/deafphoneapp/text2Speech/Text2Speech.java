package com.example.xchen.deafphoneapp.text2Speech;

import android.speech.tts.TextToSpeech;

import com.example.xchen.deafphoneapp.PhoneMain;

import java.util.Locale;

/**
 * Created by xchen on 02.02.2017.
 */

public class Text2Speech {

    private final TextToSpeech textToSpeech;
    private final PhoneMain phoneMain;
    public Text2Speech(PhoneMain phoneMain){
        this.phoneMain = phoneMain;
        this.textToSpeech = new TextToSpeech(phoneMain.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.getDefault());
                }
            }
        });
    }

    public void speak(String toSpeak){
        textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void shutdown(){
        if(textToSpeech !=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

}
