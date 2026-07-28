package com.magius.world.mod.quest;

public enum QuestState {
    NOT_STARTED,
    STARTED,
    COMPLETED;

    public static QuestState fromName(String name) {
        if (name == null || name.isBlank()) {
            return NOT_STARTED;
        }

        try {
            return valueOf(name);
        } catch (IllegalArgumentException exception) {
            return NOT_STARTED;
        }
    }
}