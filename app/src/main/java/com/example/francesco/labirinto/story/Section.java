package com.example.francesco.labirinto.story;

import com.example.francesco.labirinto.character.Item;

import java.util.List;

/**
 * Created by Francesco on 18/11/2014.
 */
public class Section {

    private final String id;

    private final Story story;

    private List<String> text;

    private boolean starting = false;

    private boolean ending = false;

    private boolean oneOutcome = false;

    private List<Item> items;

    Section(final String id, Story story) {
        this.id = id;
        this.story = story;
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

    boolean hasOneOutcome() {
        return oneOutcome;
    }

    void setOneOutcome(boolean oneOutcome) {
        this.oneOutcome = oneOutcome;
    }

    List<Item> getItems() {
        return items;
    }

    void setItems(List<Item> items) {
        this.items = items;
    }

    Item getItem(final String name) throws StoryException {
        for (Item item : items) {
            if (item.getName().contains(name))
                return item;
        }
        throw new StoryException();
    }

    void removeItem(final Item item) {
        this.items.remove(item);
    }

    public Story getStory() {
        return story;
    }

    void switchParagraph(Item item) {
        final int index = item.getParagraphToRemove() - 1;
        final String newParagraph = item.getParagraphToAdd();
        if (index >= 0) {
            this.text.remove(index);
            if (!newParagraph.isEmpty())
                this.text.add(index, newParagraph);
        }
    }
}
