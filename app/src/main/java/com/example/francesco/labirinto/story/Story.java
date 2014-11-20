package com.example.francesco.labirinto.story;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Francesco on 18/11/2014.
 */
public class Story {

    private Section starting;

    private Section current;

    private final Map<String, Section> sections = new HashMap<String, Section>();

    private final Map<String, List<Outcome>> outcomes = new HashMap<String, List<Outcome>>();

    private boolean ended = false;

    public static final String HOME = "home";
    public static final String HELP = "help";
    public static final String END = "end";
    public static final String QUIT = "quit";

    void addSection(final Section section) {
        this.sections.put(section.getId(), section);
        this.outcomes.put(section.getId(), new ArrayList<Outcome>());
    }

    void addOutcome(final String from, final String to, final String outcome) {
        final List<Outcome> sectionOutcomes = this.outcomes.get(from);
        sectionOutcomes.add(new Outcome(outcome, this.sections.get(to)));
    }

    void addDirectOutcome(final String from, final String to) {
        this.sections.get(from).setOneOutcome(true);
        final List<Outcome> sectionOutcomes = this.outcomes.get(from);
        sectionOutcomes.clear();
        sectionOutcomes.add(new Outcome(null, this.sections.get(to)));
    }

    void introduce() {
        setCurrent(this.sections.get(HOME));
    }

    void start() throws StoryException {
        if(starting == null)
            throw new StoryException();
        setCurrent(starting);
        ended = false;
    }

    void proceed() {
        Outcome outcome = this.outcomes.get(this.current.getId()).get(0);
        setCurrent(outcome.getNextSection());
    }

    void proceed(final String outcome) throws StoryException {
        final List<Outcome> sectionOutcomes = this.outcomes.get(this.current.getId());
        boolean found = false;
        for (final Outcome o : sectionOutcomes) {
            if (o.getOutcome().equals(outcome)) {
                setCurrent(o.getNextSection());
                found = true;
                break;
            }
        }
        if (!found)
            throw new StoryException();
    }

    public void proceedToHelp() {
        setCurrent(this.sections.get(HELP));
    }

    void proceedToEnd() {
        setCurrent(this.sections.get(END));
    }

    void proceedToQuit() {
        setCurrent(this.sections.get(QUIT));
    }

    List<String> getOutcomes(final String id) {
        final List<Outcome> sectionOutcomes = this.outcomes.get(id);
        final List<String> result =  new ArrayList<String>(sectionOutcomes.size());
        for(final Outcome o: sectionOutcomes) {
            result.add(o.getOutcome());
        }
        return result;
    }

    boolean hasOneOutcome() {
        return getCurrent().hasOneOutcome();
    }

    void setStarting(Section starting) {
        this.starting = starting;
    }

    Section getCurrent() {
        return current;
    }

    void setCurrent(Section current) {
        this.current = current;
        if (current.isEnding()) {
            this.ended = true;
        }
    }

    boolean isEnded() {
        return ended;
    }


}