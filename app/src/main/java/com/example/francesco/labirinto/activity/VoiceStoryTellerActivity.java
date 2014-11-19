package com.example.francesco.labirinto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;

import com.example.francesco.labirinto.story.StoryException;

import java.util.List;
import java.util.Locale;

/**
 * TODOS:
 * - immagazzinare i dati quando si interrompe la partita per un po' (tipo home screen, o telefonata ricevuta...)
 * - fare il tutorial per capire meglio TTS
 * - usare file di strings piuttosto che hardcoded
 * - implementare salva e carica partita
 */

public abstract class VoiceStoryTellerActivity extends StoryTellerActivity {

    private TextToSpeech tts;

    private static final int GET_SPEECH = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.ITALIAN);
                    displayText(teller.getCurrentText());
                }
            }
        });

    }

    protected void displayText(List<String> text) {
        for (String paragraph : text) {
            speak(paragraph);
            tts.playSilence(750, TextToSpeech.QUEUE_ADD, null);
        }
    }

    private void speak(final String text) {
        tts.speak(text, TextToSpeech.QUEUE_ADD, null);
    }

    protected void processInput() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Comando?");
        startActivityForResult(i, GET_SPEECH);
    }

    @Override
    public void onClick(View v) {
        tts.stop(); // interrompe la voce
        super.onClick(v);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_SPEECH && resultCode == RESULT_OK) {
            String speech = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
            try {
                teller.proceed(speech);
                displayText(teller.getCurrentText());
            } catch (StoryException e) {
                speak("Azione indisponibile. Riprova.");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        tts.stop();
        tts.shutdown();
        super.onStop();
    }
}