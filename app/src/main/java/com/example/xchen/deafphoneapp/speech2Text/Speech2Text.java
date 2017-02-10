package com.example.xchen.deafphoneapp.speech2Text;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.xchen.deafphoneapp.PhoneMain;
import java.util.Locale;

/**
 * Created by xchen on 02.02.2017.
 */

public class Speech2Text extends PhoneStateListener {

    private final PhoneMain phoneMain;

    public Speech2Text(PhoneMain phoneMain){
        this.phoneMain = phoneMain;
    }

    /**
     * Input of microfon
     */
    public void startVoiceRecognitionActivity(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now");
        try{
            phoneMain.startActivityForResult(intent, phoneMain.REQUEST_CODE_SPEECH_INPUT /* = 100*/);
        }catch(ActivityNotFoundException a){
            Toast.makeText(phoneMain, "Sorry, your device doesn't support speech language!", Toast.LENGTH_LONG).show();
        }
    }


//    @Override
//    public void onCallStateChanged(int state, String incomingNumber) {
//        switch (state) {
//            case TelephonyManager.CALL_STATE_RINGING:
//                // called when someone is ringing to this phone
//                Toast.makeText(phoneMain, "Incoming: "+incomingNumber, Toast.LENGTH_LONG).show();
//                break;
//        }
//    }

}
