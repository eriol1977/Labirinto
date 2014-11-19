package com.example.francesco.labirinto.story;

/**
 * Created by Francesco on 18/11/2014.
 */
public class Outcome {

    private final String outcome;

    private final Section nextSection;

    public Outcome(final String outcome, final Section nextSection) {
        this.outcome = outcome;
        this.nextSection = nextSection;
    }

    public String getOutcome() {
        return outcome;
    }

    public Section getNextSection() {
        return nextSection;
    }
}
