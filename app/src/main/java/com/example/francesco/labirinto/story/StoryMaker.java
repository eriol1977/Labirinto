package com.example.francesco.labirinto.story;

import com.example.francesco.labirinto.character.Inventory;
import com.example.francesco.labirinto.character.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francesco on 18/11/2014.
 */
public class StoryMaker {

    private final Story story;

    private final StringLoader sl;

    public StoryMaker(final StringLoader sl) {
        this.sl = sl;
        this.story = new Story(new StoryItems(sl));

        createSections(Story.HOME, Story.HELP, Story.END, Story.QUIT);
    }

    public Story getStory() {
        return story;
    }

    private List<String> getSectionText(final String id) {
        boolean finish = false;
        int i = 1;
        String paragraph;
        List<String> text = new ArrayList<String>();
        while (!finish) {
            paragraph = sl.s("s_" + id + "_" + i); // es: s_home_1, s_2_3
            if (paragraph == null)
                finish = true;
            else {
                text.add(paragraph);
                i++;
            }
        }
        return text;
    }

    public void createSections(final int from, final int to) {
        for (int i = from; i <= to; i++) {
            createSection(String.valueOf(i));
        }
    }

    public void createSections(final String... ids) {
        for (String id : ids) {
            createSection(id);
        }
    }

    public Section createSection(final String id) {
        final Section section = new Section(id, null); // FIXME load items
        section.setText(getSectionText(id));
        this.story.addSection(section);
        return section;
    }

    public void createStartingSection(final String id) {
        final Section section = createSection(id);
        section.setStarting(true);
        this.story.setStarting(section);
    }

    public void createEndingSection(final String id) {
        final Section section = createSection(id);
        section.setEnding(true);
    }

    public Section createInventorySection(final Inventory inventory) {
        Section inventorySection = new Section(null, null);
        final List<Item> items = inventory.getItems();
        List<String> text = new ArrayList<String>(items.size() + 1);
        if (items.isEmpty())
            text.add(sl.EMPTY_INVENTORY);
        else {
            text.add(sl.INVENTORY_CONTENT);
            for (Item item : items) {
                text.add(item.getName());
            }
        }
        inventorySection.setText(text);
        return inventorySection;
    }

    public void link(final String from, final String to, final String outcome) {
        this.story.addOutcome(from, to, outcome);
    }

    public void linkDirectly(final String from, final String to) {
        this.story.addDirectOutcome(from, to);
    }
}
