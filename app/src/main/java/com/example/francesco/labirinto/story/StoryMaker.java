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

    /**
     * ex: <item name="s_3_items" type="string">i_torch,i_key,i_rope</item>
     *
     * @param id
     * @return
     */
    private List<Item> getSectionItems(String id) {
        final String itemsString = sl.s("s_" + id + "_items");
        if(itemsString != null) {
            String[] itemIds = itemsString.split(",");
            return story.getItems().getItems(itemIds);
        }
        return new ArrayList<Item>();
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
        final Section section = new Section(id, story);
        section.setText(getSectionText(id));
        section.setItems(getSectionItems(id));
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

    public Section createInventorySection(final Inventory inventory, final Section current) {
        Section inventorySection = new Section("temp", current.getStory());
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
        current.getStory().addSection(inventorySection);
        current.getStory().addDirectOutcome("temp", current.getId());
        return inventorySection;
    }

    public Section createItemsSection(final Section current) {
        Section itemsSection = new Section("temp", current.getStory());
        final List<Item> items = current.getItems();
        List<String> text = new ArrayList<String>(items.size() + 1);
        if (items.isEmpty())
            text.add(sl.EMPTY_SECTION_ITEMS);
        else {
            text.add(sl.SECTION_ITEMS);
            for (Item item : items) {
                text.add(item.getName());
            }
        }
        itemsSection.setText(text);
        current.getStory().addSection(itemsSection);
        current.getStory().addDirectOutcome("temp", current.getId());
        return itemsSection;
    }

    public Section createGetSection(final Section current, final String what, final Inventory inventory) {
        final List<Item> items = current.getItems();
        Item item = null;
        for(Item i: items) {
            if(what.contains(i.getName())) {
                item = i;
                break;
            }
        }

        List<String> text = new ArrayList<String>();
        if(item != null) {
            text.add(sl.YOU_GOT);
            text.add(item.getName());
            inventory.addItem(item);
            current.removeItem(item);
        }else{
            text.add(sl.NO_OBJECT);
        }

        Section getSection = new Section("temp", current.getStory());
        getSection.setText(text);
        current.getStory().addSection(getSection);
        current.getStory().addDirectOutcome("temp", current.getId());
        return getSection;
    }

    public void link(final String from, final String to, final String outcome) {
        this.story.addOutcome(from, to, outcome);
    }

    public void linkDirectly(final String from, final String to) {
        this.story.addDirectOutcome(from, to);
    }
}
