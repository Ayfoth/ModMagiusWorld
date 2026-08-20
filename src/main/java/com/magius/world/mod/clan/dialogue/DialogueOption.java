package com.magius.world.mod.clan.dialogue;

import com.magius.world.mod.clan.dialogue.action.DialogueAction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class DialogueOption {

    private final Component text;
    private final DialogueData nextDialogue;
    private final DialogueAction action;

    public DialogueOption(
            Component text,
            DialogueData nextDialogue,
            DialogueAction action
    ) {
        this.text = text;
        this.nextDialogue = nextDialogue;
        this.action = action;
    }

    public Component getText() {
        return text;
    }

    public DialogueData getNextDialogue() {
        return nextDialogue;
    }

    public DialogueAction getAction() {
        return action;
    }

    public void execute(Player player) {
        if (action != null && player != null) {
            action.execute(player);
        }
    }
}
