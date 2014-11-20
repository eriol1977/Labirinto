package com.example.francesco.labirinto.story;

import com.example.francesco.labirinto.character.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francesco on 18/11/2014.
 */
public class Section {

    private final String id;

    private List<String> text;

    private boolean starting = false;

    private boolean ending = false;

    private boolean oneOutcome = false;

    private final List<Item> items;

    Section(final String id, final List<Item> items) {
        this.id = id;
        this.items = items;
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

    public Item getItem(final String name) throws StoryException {
        for(Item item: items) {
            if(item.getName().contains(name))
                return item;
        }
        throw new StoryException();
    }
}
