package com.example.francesco.labirinto.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.francesco.labirinto.R;
import com.example.francesco.labirinto.story.Story;
import com.example.francesco.labirinto.story.StoryException;
import com.example.francesco.labirinto.story.StoryMaker;
import com.example.francesco.labirinto.story.StoryTeller;

import java.util.List;
import java.util.Locale;

/**
 * TODOS:
 * - deve salvare i dati quando si interrompe (tipo home screen)
 * - fare il tutorial per capire meglio TTS
 * - uso di file di strings piuttosto che hardcoded
 */

public abstract class VoiceStoryTellerActivity extends Activity implements View.OnClickListener {

    private final String title;

    private StoryTeller teller;

    protected StoryMaker maker;

    private TextToSpeech tts;

    private static final int GET_SPEECH = 1111;

    private static final String STORY_DATA = "STORY_DATA";

    protected VoiceStoryTellerActivity(final String title) {
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        maker = new StoryMaker(title);

        if (savedInstanceState != null) {
            teller = (StoryTeller) savedInstanceState.getSerializable(STORY_DATA);
        } else {
            teller = new StoryTeller(buildStory());
            teller.introduce();
        }

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

    protected abstract Story buildStory();

    private void displayText(List<String> text) {
        for (String paragraph : text) {
            speak(paragraph);
            tts.playSilence(750, TextToSpeech.QUEUE_ADD, null);
        }
    }

    private void speak(final String text) {
        tts.speak(text, TextToSpeech.QUEUE_ADD, null);
    }

    private void getSpeech() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Comando?");
        startActivityForResult(i, GET_SPEECH);
    }

    @Override
    public void onClick(View v) {
        tts.stop(); // interrompe la voce
        if (!teller.storyHasEnded()) {
            if (teller.hasOneOutcome()) {
                teller.proceed();
                displayText(teller.getCurrentText());
            } else {
                getSpeech();
            }
        } else {
            if(teller.isEndPhase()){
                getSpeech();
            }else {
                teller.proceedToEnd();
                displayText(teller.getCurrentText());
            }
        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(STORY_DATA, teller);
        super.onSaveInstanceState(outState);
    }

    private void exit() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}