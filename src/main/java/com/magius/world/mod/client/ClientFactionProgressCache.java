package com.magius.world.mod.client;

import java.util.HashMap;
import java.util.Map;

public class ClientFactionProgressCache {

    public static String factionId = "Aucune";
    public static final Map<String, Integer> progress = new HashMap<>();
    public static final Map<String, Boolean> completed = new HashMap<>();

    private ClientFactionProgressCache() {}

    public static void clear() {
        factionId = "Aucune";
        progress.clear();
        completed.clear();
    }

    public static int getCompletedCount() {
        int count = 0;
        for (boolean value : completed.values()) {
            if (value) {
                count++;
            }
        }
        return count;
    }
}
