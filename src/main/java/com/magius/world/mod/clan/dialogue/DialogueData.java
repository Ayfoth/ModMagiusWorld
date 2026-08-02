package com.magius.world.mod.clan.dialogue;

import net.minecraft.network.chat.Component;

import java.util.List;

public class DialogueData {

    private final Component title;
    private final Component speaker;
    private final Component text;
    private final List<DialogueOption> options;

    public List<DialogueOption> getOptions() {
        return options;
    }

    public DialogueData(
            Component title,
            Component speaker,
            Component text, List<DialogueOption> options
    ) {
        this.title = title;
        this.speaker = speaker;
        this.text = text;
        this.options = options;
    }

    public Component getTitle() {
        return title;
    }

    public Component getSpeaker() {
        return speaker;
    }

    public Component getText() {
        return text;
    }
}
