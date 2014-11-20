package com.example.francesco.labirinto;

import android.os.Bundle;
import android.widget.Button;

import com.example.francesco.labirinto.activity.VoiceStoryTellerActivity;
import com.example.francesco.labirinto.story.Story;


public class Labirinto extends VoiceStoryTellerActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(getResources().getString(R.string.l_title));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labirinto);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    protected Story buildStory() {

        maker.createStartingSection("1");
        maker.createSections(2,10);
        maker.createEndingSection("11");
        maker.createEndingSection("12");

        maker.link("1", "2", sl.GO_BACK);
        maker.link("1", "3", sl.ENTER);
        maker.linkDirectly("2", "11");
        maker.link("3", "4", sl.RIGHT);
        maker.link("3", "8", sl.LEFT);
        maker.link("4", "5", sl.GO_STRAIGHT_ON);
        maker.link("4", "10", sl.LEFT);
        maker.link("5", "7", sl.RIGHT);
        maker.link("5", "6", sl.LEFT);
        maker.linkDirectly("6", "11");
        maker.linkDirectly("7", "12");
        maker.link("8", "4", sl.GO_BACK);
        maker.link("8", "9", sl.GO_ON);
        maker.linkDirectly("9", "11");
        maker.linkDirectly("10", "11");

        return maker.getStory();
    }
}
