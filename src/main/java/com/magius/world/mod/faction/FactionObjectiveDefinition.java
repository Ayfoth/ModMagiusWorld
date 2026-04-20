package com.magius.world.mod.faction;

public class FactionObjectiveDefinition {

    private final String id;
    private final String displayName;
    private final String description;
    private final String category;
    private final int targetValue;
    private final String parentObjectiveId;
    private final String iconItemId;
    private final String rewardText;

    public FactionObjectiveDefinition(
            String id,
            String displayName,
            String description,
            String category,
            int targetValue,
            String parentObjectiveId,
            String iconItemId,
            String rewardText
    ) {
        this.id = id;
        this.displayName = displayName;
        this.description = description;
        this.category = category;
        this.targetValue = targetValue;
        this.parentObjectiveId = parentObjectiveId;
        this.iconItemId = iconItemId;
        this.rewardText = rewardText;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public int getTargetValue() {
        return targetValue;
    }

    public String getParentObjectiveId() {
        return parentObjectiveId;
    }

    public String getIconItemId() {
        return iconItemId;
    }

    public String getRewardText() {
        return rewardText;
    }
}
