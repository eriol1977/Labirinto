package com.example.francesco.labirinto.character;

/**
 * Created by Francesco on 20/11/2014.
 */
public class Item {

    final private String id;

    final private String name;

    final private String description;

    private int paragraphToRemove;

    private String paragraphToAdd;

    public Item(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getParagraphToRemove() {
        return paragraphToRemove;
    }

    public void setParagraphToRemove(int paragraphToRemove) {
        this.paragraphToRemove = paragraphToRemove;
    }

    public String getParagraphToAdd() {
        return paragraphToAdd;
    }

    public void setParagraphToAdd(String paragraphToAdd) {
        this.paragraphToAdd = paragraphToAdd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (!id.equals(item.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
