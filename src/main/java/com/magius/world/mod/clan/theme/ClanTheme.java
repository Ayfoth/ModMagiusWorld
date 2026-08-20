package com.magius.world.mod.clan.theme;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ClanTheme {

    private final ResourceLocation backgroundTexture;
    private final ResourceLocation frameTexture;
    private final ResourceLocation icon;

    private final Component displayName;
    private final Component reputationName;

    private final int accentColor;
    private final int titleColor;
    private final int textColor;
    private final int buttonColor;

    private final ResourceLocation openSound;
    private final ResourceLocation clickSound;

    public ClanTheme(
            ResourceLocation backgroundTexture,
            ResourceLocation frameTexture,
            ResourceLocation icon,
            Component displayName,
            Component reputationName,
            int accentColor,
            int titleColor,
            int textColor,
            int buttonColor,
            ResourceLocation openSound,
            ResourceLocation clickSound
    ) {
        this.backgroundTexture = backgroundTexture;
        this.frameTexture = frameTexture;
        this.icon = icon;
        this.displayName = displayName;
        this.reputationName = reputationName;

        this.accentColor = accentColor;
        this.titleColor = titleColor;
        this.textColor = textColor;
        this.buttonColor = buttonColor;

        this.openSound = openSound;
        this.clickSound = clickSound;
    }

    public ResourceLocation getBackgroundTexture() {
        return backgroundTexture;
    }

    public ResourceLocation getFrameTexture() {
        return frameTexture;
    }

    public ResourceLocation getIcon() {
        return icon;
    }

    public Component getDisplayName() {
        return displayName;
    }

    public Component getReputationName() {
        return reputationName;
    }

    public int getAccentColor() {
        return accentColor;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getButtonColor() {
        return buttonColor;
    }

    public ResourceLocation getOpenSound() {
        return openSound;
    }

    public ResourceLocation getClickSound() {
        return clickSound;
    }
}
