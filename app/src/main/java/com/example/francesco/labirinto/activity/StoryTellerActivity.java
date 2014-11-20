package com.example.francesco.labirinto.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.francesco.labirinto.story.Story;
import com.example.francesco.labirinto.story.StoryMaker;
import com.example.francesco.labirinto.story.StoryTeller;
import com.example.francesco.labirinto.story.StringLoader;

import java.util.List;

/**
 * TODOS:
 * - immagazzinare i dati quando si interrompe la partita per un po' (tipo home screen, o telefonata ricevuta...)
 *  [dopo un back, ricomincia da capo; dopo un home, resta senza voce]
 * - implementare salva e carica partita
 * - commentare codice
 * - implementare comandi inventario, prendi, usa
 * - testare storiella in inglese/portoghese
 */

public abstract class StoryTellerActivity extends Activity implements View.OnClickListener {

    private String title;

    protected StringLoader sl;

    protected StoryTeller teller;

    protected StoryMaker maker;

    //private static final String STORY_DATA = "STORY_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sl = new StringLoader(this);

        maker = new StoryMaker(sl);

        //if (savedInstanceState != null) {
        //    teller = (StoryTeller) savedInstanceState.getSerializable(STORY_DATA);
        //} else {
        teller = new StoryTeller(buildStory(), sl);
        teller.introduce();
        //}
    }

    protected abstract Story buildStory();

    protected abstract void displayText(List<String> text);

    protected abstract void processInput();

    @Override
    public void onClick(View v) {

        if (teller.hasToQuit())
            finish();

        if (!teller.storyHasEnded()) {
            if (teller.hasOneOutcome()) {
                teller.proceed();
                displayText(teller.getCurrentText());
            } else {
                processInput();
            }
        } else {
            if (teller.isEndPhase()) {
                processInput();
            } else {
                teller.proceedToEnd();
                displayText(teller.getCurrentText());
            }
        }
    }

    //@Override
    //protected void onSaveInstanceState(Bundle outState) {
    //    outState.putSerializable(STORY_DATA, teller);
    //    super.onSaveInstanceState(outState);
    //}

    protected void setTitle(String title) {
        this.title = title;
    }

    public String s(final String key) {
        try {
            int resId = getResources().getIdentifier(key, "string", getPackageName());
            return getResources().getString(resId);
        } catch (Exception e) {
            return null;
        }
    }
}