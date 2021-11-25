package com.example.cosmonotes.Notes;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

import com.example.cosmonotes.NewNoteFragment;

import java.util.HashMap;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class TtsManager extends UtteranceProgressListener implements TextToSpeech.OnInitListener{
    private TextToSpeech mTts = null;
    private boolean isLoaded = false;

    Context context;

    // Inicializa el atributo mTts
    public TtsManager(NewNoteFragment context)  {
        try {
             mTts = new TextToSpeech(context.getContext(), this);
            this.context = context.getContext();
            mTts.setOnUtteranceProgressListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Carga el Idioma (castellano) y reproduce a voz un string

    @Override
    public void onInit(int status) {
        Locale spanish = new Locale("es", "ES");
        if (status == TextToSpeech.SUCCESS) {
            int result = mTts.setLanguage(spanish);
            isLoaded = true;

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toasty.error(context, "Este Lenguaje no esta permitido", Toast.LENGTH_SHORT, true).show();
            }
        } else {
            Toasty.error(context, "Fallo al Inicilizar", Toast.LENGTH_SHORT, true).show();
        }
    }

    // Carga el string que se va reproducir a voz
    public void initQueue(String text) {
        if (isLoaded)
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // Toasty.info(context, "Reproducción en marcha", Toast.LENGTH_SHORT, true).show();
                String myUtteranceID = "myUtteranceID";
                mTts.speak(text, TextToSpeech.QUEUE_FLUSH, null, myUtteranceID);
            }
            else {
                // Toasty.info(context, "Reproducción en marcha", Toast.LENGTH_SHORT, true).show();
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "myUtteranceID");
                mTts.speak(text, TextToSpeech.QUEUE_FLUSH, hashMap);
            }
        }
        else
            Toasty.error(context, "Fallo al Inicilizar", Toast.LENGTH_SHORT, true).show();

    }


    //Apaga el objeto para que no consuma recursos del sistema
    public void shutDown() {
        mTts.shutdown();
    }

    // Detiene la reproducción
    public void stop() {
        mTts.stop();
        Toasty.info(context, "Reproducción detenida", Toast.LENGTH_SHORT, true).show();
    }

    public boolean isSpeaking() {
        return mTts.isSpeaking();
    }


    @Override
    public void onStart(String utteranceId) {

    }

    @Override
    public void onDone(String utteranceId) {
        Toasty.error(context, "Fallo al done", Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void onError(String utteranceId) {

    }
}
