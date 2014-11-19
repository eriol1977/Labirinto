package com.example.francesco.labirinto.story;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francesco on 18/11/2014.
 */
public class StoryMaker {

    private final Story story;

    public StoryMaker(final String title) {
        this.story = new Story();

        createSection("HOME","Benvenuto nell'audiogioco " + title +", sarò la tua guida lungo il percorso.","Per interagire, premi una qualsiasi parte dello schermo e attendi il bip sonoro, quindi pronuncia il tuo comando in base alle possibilità elencate.","Puoi interrompermi in qualsiasi momento premendo lo schermo per impartire un comando.","Vuoi iniziare una nuova partita, caricare una partita salvata, o ascoltare le istruzioni complete?");
        createSection("HELP","Oltre alle diverse azioni da me suggerite al termine di ogni parte della narrazione, puoi sempre usare i seguenti comandi:","Aiuto, o istruzioni, per tornare qui.","Ripeti, per farti ripetere l'ultima parte della narrazione","Salva partita, per salvare la tua posizione nel gioco.","Carica partita, per riprendere il gioco dalla posizione precedentemente salvata.","Esci dal gioco: beh, questa è facile da capire.","Torna al gioco, per tornare alla narrazione a partire da una sezione alternativa come questa.");
        createSection("END","La partita è giunta al termine.", "Vuoi iniziare una nuova partita, caricare una partita salvata, o uscire dal gioco?");
        createSection("QUIT","Grazie per aver giocato " + title, "Premi lo schermo per abbandonare il gioco.");

    }

    public Story getStory() {
        return story;
    }

    public Section createSection(final String id, final String... text)
    {
        final Section section = new Section(id);
        List<String> paragraphs = new ArrayList<String>(text.length);
        for(final String paragraph: text)
        {
            paragraphs.add(paragraph);
        }
        section.setText(paragraphs);
        this.story.addSection(section);
        return section;
    }

    public void createStartingSection(final String id, final String... text)
    {
        final Section section = createSection(id, text);
        section.setStarting(true);
        this.story.setStarting(section);
    }

    public void createEndingSection(final String id, final String... text)
    {
        final Section section = createSection(id, text);
        section.setEnding(true);
    }

    public void link(final String from, final String to, final String outcome) {
        this.story.addOutcome(from, to, outcome);
    }

    public void linkDirectly(final String from, final String to) {
        this.story.addDirectOutcome(from, to);
    }
}
