package com.example.francesco.labirinto.story;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Francesco on 18/11/2014.
 */
public class StoryTeller implements Serializable {

    private final Story story;

    private boolean endPhase = false;

    public StoryTeller(final Story story) {
        this.story = story;
    }

    public void introduce() {
        this.story.introduce();
    }

    public void start() throws StoryException {
        this.endPhase = false;
        this.story.start();
    }

    public List<String> getCurrentText() {
        return this.story.getCurrent().getText();
    }

    public void proceed() {
        this.story.proceed();
    }

    public void proceed(final String outcome) throws StoryException {

        if(outcome.equalsIgnoreCase("ripeti"))
            return;
        else if(outcome.contains("iniziare") || outcome.contains("nuova") || outcome.contains("partita")){
            start();
            return;
        }

        String[] outcomeKeywords = outcome.split("\\s+");
        String[] candidateKeywords;
        List<String> availableOutcomes = getCurrentOutcomes();
        String selectedOutcome = null;
        for (String candidate : availableOutcomes) {
            candidateKeywords = candidate.split("\\s+");
            for (String outcomeKeyword : outcomeKeywords) {
                for (String candidateKeyword : candidateKeywords) {
                    if (outcomeKeyword.equalsIgnoreCase(candidateKeyword)) {
                        selectedOutcome = candidate;
                        break;
                    }
                }
                if (selectedOutcome != null) {
                    break;
                }
            }
            if (selectedOutcome != null) {
                break;
            }
        }
        if (selectedOutcome != null) {
            this.story.proceed(selectedOutcome);
        } else {
            throw new StoryException();
        }
    }

    public void proceedToEnd() {
        this.endPhase = true;
        this.story.proceedToEnd();
    }

    public boolean hasOneOutcome() {
        return this.story.hasOneOutcome();
    }

    private List<String> getCurrentOutcomes() {
        return this.story.getOutcomes(this.story.getCurrent().getId());
    }

    public boolean storyHasEnded() {
        return this.story.isEnded();
    }

    public boolean isEndPhase() {
        return endPhase;
    }
}
