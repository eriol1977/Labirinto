package com.example.francesco.labirinto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.francesco.labirinto.story.Story;
import com.example.francesco.labirinto.story.StoryException;
import com.example.francesco.labirinto.story.StoryMaker;
import com.example.francesco.labirinto.story.StoryTeller;

import java.util.List;
import java.util.Locale;


public class Labirinto extends Activity {

    private StoryTeller teller;

    private TextToSpeech tts;

    private static final int GET_SPEECH = 1111;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labirinto);

        teller = new StoryTeller(buildStory());
        try {
            teller.start();
        } catch (StoryException e) {
            speak("Si è verificato un errore grave! Il gioco verrà terminato.");
            exit();
        }

        // TODO disattivarlo mentre la voce parla
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!teller.storyHasEnded()) {
                    if (teller.hasOneOutcome()) {
                        teller.proceed();
                        displayText(teller.getCurrentText());
                    } else {
                        getSpeech();
                    }
                } else {
                    speak("La partita è terminata");
                    exit();
                }
            }
        });

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

    private Story buildStory() {

        StoryMaker maker = new StoryMaker();

        maker.createStartingSection("1", "Ti trovi dinanzi all'ingresso del labirinto, fauci oscure intagliate nel fianco della montagna.", "Una ventata gelida proveniente dall'interno ti fa venire la pelle d'oca.", "Entri, o torni indietro?");
        maker.createSection("2", "Maledicendo la tua vigliaccheria, volti le spalle all'entrata e torni sui tuoi passi.", "Non saprai mai quali splendidi tesori si celino nel labirinto.");
        maker.createSection("3", "Trattenendo il respiro, fai un passo ed entri nell'oscurità del labirinto.", "Il tunnel di pietra prosegue dritto per una cinquantina di passi, prima di dividersi.", "Vai a destra o a sinistra?");
        maker.createSection("4", "Svolti a destra e prosegui nella luce sempre più fioca, sino a trovarti di fronte a una nuova diramazione.", "Davanti a te si ode uno strano brontolio.", "Alla tua sinistra tutto è silenzioso.", "Prosegui dritto o vai a sinistra?");
        maker.createSection("5", "Il tunnel si apre in un'ampia sala circolare, dal cui soffitto una cascatella d'acqua cade a formare una pozza.", "Il brontolio che avevi udito era quello dell'acqua corrente!", "Davanti al minuscolo specchio d'acqua, vedi uno scrigno con due serrature, e una chiave in ciascuna serratura.", "Devi scegliere quale delle due utilizzare: destra o sinistra?");
        maker.createSection("6", "La serratura di sinistra si apre con uno scatto secco e in quell'istante provi una forte fitta di dolore.", "Con orrore, vedi una spina acuminata conficcata nel dorso della tua mano.", "Nel giro di un istante il veleno si diffonde nel tuo corpo, regalandoti una morte lenta e dolorosa.");
        maker.createSection("7", "La serratura di destra si apre con uno scatto lieve.", "Sollevi con cautela il coperchio dello scrigno, ed ecco che il bagliore di innumerevoli gemme e monete preziose ti invade gli occhi.", "Felice come non mai per la tua scoperta, ti riempi le tasche di preziosi prima di incamminarti verso l'uscita del labirinto.");
        maker.createSection("8", "Svolti a sinistra e prosegui nell'oscurità quasi completa.", "Dopo alcuni passi non sei più in grado di scorgere nulla.", "Torni indietro o prosegui?");
        maker.createSection("9", "Avanzi fiducioso nel buio, certo che prima o poi un raggio di luce ti illuminerà il cammino.", "Invece piombi tra le fauci spalancate di una fossa scavata nel mezzo del cammino.", "Mentre precipiti urlando, rimpiangi di non essere tornato indietro quando potevi.");
        maker.createSection("10", "Svolti a sinistra, preferendo di gran lunga il silenzio a quel sinistro brontolio.", "Peccato che il silenzio celi la presenza di un formidabile predatore: il ragno, più grande di un uomo, si cala dal soffitto alle tue spalle e ti avvolge nell'abbraccio delle sue otto zampe pelose.", "Ti dibatti inutilmente per lunghi e terribili istanti, prima che il morso del pungiglione ti privi misericordiosamente dei sensi.");
        maker.createEndingSection("11", "Peccato, hai perso.", "Non arrenderti, riprova!");
        maker.createEndingSection("12", "Bravo, hai vinto!", "Hai sconfitto le insidie del labirinto, uscendone ricco di tesori e di storie da raccontare.");

        maker.link("1", "2", "torno indietro");
        maker.link("1", "3", "entro");
        maker.linkDirectly("2", "11");
        maker.link("3", "4", "destra");
        maker.link("3", "8", "sinistra");
        maker.link("4", "5", "proseguo dritto");
        maker.link("4", "10", "sinistra");
        maker.link("5", "7", "destra");
        maker.link("5", "6", "sinistra");
        maker.linkDirectly("6", "11");
        maker.linkDirectly("7", "12");
        maker.link("8", "4", "torno indietro");
        maker.link("8", "9", "proseguo");
        maker.linkDirectly("9", "11");
        maker.linkDirectly("10", "11");

        return maker.getStory();
    }

    public void displayText(List<String> text) {
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
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Parla!");
        startActivityForResult(i, GET_SPEECH);
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
        super.onPause();
    }

    private void exit() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_labirinto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
