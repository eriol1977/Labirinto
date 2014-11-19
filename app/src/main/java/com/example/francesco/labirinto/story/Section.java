package com.example.francesco.labirinto.story;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Francesco on 18/11/2014.
 */
public class Section {

    private final String id;

    private List<String> text;

    private boolean starting = false;

    private boolean ending = false;

    private boolean oneOutcome = false;

    Section(final String id) {
        this.id = id;
    }

    List<String> getText() {
        return text;
    }

    String getId() {
        return id;
    }

    void setText(List<String> text) {
        this.text = text;
    }

    boolean isEnding() {
        return ending;
    }

    void setEnding(boolean ending) {
        this.ending = ending;
    }

    boolean isStarting() {
        return starting;
    }

    void setStarting(boolean starting) {
        this.starting = starting;
    }

    public boolean hasOneOutcome() {
        return oneOutcome;
    }

    public void setOneOutcome(boolean oneOutcome) {
        this.oneOutcome = oneOutcome;
    }
}
