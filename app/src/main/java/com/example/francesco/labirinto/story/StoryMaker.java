package com.example.francesco.labirinto.story;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francesco on 18/11/2014.
 */
public class StoryMaker {

    private final Story story;

    public StoryMaker() {
        this.story = new Story();
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
