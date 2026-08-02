package com.magius.world.mod.clan.dialogue;

import net.minecraft.network.chat.Component;

public class DialogueOption {

    private final Component text;
    private final DialogueData nextDialogue;
    private final Runnable action;

    public DialogueOption(
            Component text,
            DialogueData nextDialogue,
            Runnable action
    ) {
        this.text = text;
        this.nextDialogue = nextDialogue;
        this.action = action;
    }

    public DialogueData getNextDialogue() {
        return nextDialogue;
    }

    public Runnable getAction() {
        return action;
    }

    public Component getText() {
        return text;
    }



    public void execute() {
        if (action != null) {
            action.run();
        }
    }
}
