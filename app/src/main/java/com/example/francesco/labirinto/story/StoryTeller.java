package com.example.francesco.labirinto.story;

import java.util.List;

/**
 * Created by Francesco on 18/11/2014.
 */
public class StoryTeller {

    private final Story story;

    public StoryTeller(final Story story) {
        this.story = story;
    }

    public void start() throws StoryException {
        this.story.start();
    }

    public List<String> getCurrentText() {
        return this.story.getCurrent().getText();
    }

    public void proceed() {
        this.story.proceed();
    }

    public void proceed(final String outcome) throws StoryException {

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

    public boolean hasOneOutcome() {
        return this.story.hasOneOutcome();
    }

    private List<String> getCurrentOutcomes() {
        return this.story.getOutcomes(this.story.getCurrent().getId());
    }

    public boolean storyHasEnded() {
        return this.story.isEnded();
    }
}
