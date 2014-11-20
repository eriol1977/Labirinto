package com.example.francesco.labirinto.story;

import com.example.francesco.labirinto.activity.StoryTellerActivity;

import java.io.Serializable;

/**
 * Created by Francesco on 20/11/2014.
 */
public class StringLoader {

    private final StoryTellerActivity activity;

    public final String GO_BACK;
    public final String ENTER;
    public final String RIGHT;
    public final String LEFT;
    public final String GO_STRAIGHT_ON;
    public final String GO_ON;
    public final String REPEAT;
    public final String INSTRUCTIONS;
    public final String HELP;
    public final String BACK_TO_GAME;
    public final String START;
    public final String NEW;
    public final String GAME;
    public final String SAVE_GAME;
    public final String LOAD_GAME;
    public final String QUIT;
    public final String UNAVAILABLE;

    public StringLoader(final StoryTellerActivity activity) {

        this.activity = activity;

        REPEAT = activity.s(CommandIds.REPEAT);
        INSTRUCTIONS = activity.s(CommandIds.INSTRUCTIONS);
        HELP = activity.s(CommandIds.HELP);
        BACK_TO_GAME = activity.s(CommandIds.BACK_TO_GAME);
        START = activity.s(CommandIds.START);
        NEW = activity.s(CommandIds.NEW);
        GAME = activity.s(CommandIds.GAME);
        SAVE_GAME = activity.s(CommandIds.SAVE_GAME);
        LOAD_GAME = activity.s(CommandIds.LOAD_GAME);
        QUIT = activity.s(CommandIds.QUIT);
        GO_BACK = activity.s(CommandIds.GO_BACK);
        ENTER = activity.s(CommandIds.ENTER);
        RIGHT = activity.s(CommandIds.RIGHT);
        LEFT = activity.s(CommandIds.LEFT);
        GO_STRAIGHT_ON = activity.s(CommandIds.GO_STRAIGHT_ON);
        GO_ON = activity.s(CommandIds.GO_ON);

        UNAVAILABLE = activity.s(MessageIds.UNAVAILABLE);
    }

    public String s(final String key)
    {
        return this.activity.s(key);
    }
}
