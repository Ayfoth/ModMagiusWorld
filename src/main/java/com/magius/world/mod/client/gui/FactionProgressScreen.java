package com.magius.world.mod.client.gui;

import com.magius.world.mod.client.ClientFactionProgressCache;
import com.magius.world.mod.faction.FactionObjectiveDefinition;
import com.magius.world.mod.faction.FactionObjectiveRegistry;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class FactionProgressScreen extends Screen {
    private enum ObjectiveFilter {
        ALL,
        COMPLETED,
        IN_PROGRESS,
        LOCKED
    }

    private ObjectiveFilter selectedFilter = ObjectiveFilter.ALL;

    private int leftPos;
    private int topPos;
    private static final int PANEL_WIDTH = 460;
    private static final int PANEL_HEIGHT = 245;



    private static final int LIST_WIDTH = 255;

    private static final int LIST_HEIGHT = 145;
    private static final int ENTRY_HEIGHT = 28;


    private static final int DETAILS_HEIGHT = 145;
    private static final int DETAILS_WIDTH = 175;

    private int listX() {
        return leftPos + 10;
    }

    private int listY() {
        return topPos + 82;
    }

    private int detailsX() {
        return leftPos + 275;
    }

    private int detailsY() {
        return topPos + 82;
    }


    private String selectedCategory = "Exploration";
    private int scrollOffset = 0;
    private final List<FactionObjectiveDefinition> filteredObjectives = new ArrayList<>();
    private FactionObjectiveDefinition selectedObjective = null;

    public FactionProgressScreen() {
        super(Component.literal("Progression de faction"));
    }

    @Override
    protected void init() {
        super.init();
        leftPos = (this.width - PANEL_WIDTH) / 2;
        topPos = (this.height - PANEL_HEIGHT) / 2;

        int tabY = topPos + 36;

        addRenderableWidget(Button.builder(Component.literal("Exploration"), button -> {
            selectedCategory = "Exploration";
            scrollOffset = 0;
            refreshFilteredObjectives();
        }).bounds(leftPos + 10, tabY, 80, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Combat"), button -> {
            selectedCategory = "Combat";
            scrollOffset = 0;
            refreshFilteredObjectives();
        }).bounds(leftPos + 95, tabY, 70, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Récolte"), button -> {
            selectedCategory = "Récolte";
            scrollOffset = 0;
            refreshFilteredObjectives();
        }).bounds(leftPos + 170, tabY, 75, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Artisanat"), button -> {
            selectedCategory = "Artisanat";
            scrollOffset = 0;
            refreshFilteredObjectives();
        }).bounds(leftPos + 250, tabY, 80, 20).build());

        addRenderableWidget(Button.builder(Component.literal("Commerce"), button -> {
            selectedCategory = "Commerce";
            scrollOffset = 0;
            refreshFilteredObjectives();
        }).bounds(leftPos + 335, tabY, 80, 20).build());



        // int filterY = topPos + 58;

        addRenderableWidget(
                CycleButton.builder(this::getFilterDisplayText)
                        .withValues(ObjectiveFilter.ALL, ObjectiveFilter.COMPLETED, ObjectiveFilter.IN_PROGRESS, ObjectiveFilter.LOCKED)
                        .withInitialValue(selectedFilter)
                        .create(leftPos + 10, topPos + 58, 140, 20,
                                Component.literal("Filtre"),
                                (button, value) -> {
                                    selectedFilter = value;
                                    scrollOffset = 0;
                                    refreshFilteredObjectives();
                                })
        );
        addRenderableWidget(Button.builder(Component.literal("Récompenses"), button -> {
            selectedCategory = "Récompenses";
            scrollOffset = 0;
            refreshFilteredObjectives();
        }).bounds(leftPos + 335, topPos + 58, 100, 20).build());



        refreshFilteredObjectives();
    }
    private int getMasteryAnimatedColor() {
        double time = (System.currentTimeMillis() % 1400L) / 1400.0;
        double wave = (Math.sin(time * Math.PI * 2.0) + 1.0) / 2.0;

        int red = 180 + (int) (wave * 60);
        int green = 140 + (int) (wave * 80);
        int blue = 40 + (int) (wave * 30);

        return 0xFF000000 | (red << 16) | (green << 8) | blue;
    }
    private boolean isMasteryObjective(FactionObjectiveDefinition def) {
        return def != null && FactionObjectiveRegistry.RUBY_MASTERY.equals(def.getId());
    }
    private Component getFilterDisplayText(ObjectiveFilter filter) {
        return switch (filter) {
            case ALL -> Component.literal("Tous");
            case COMPLETED -> Component.literal("Terminés");
            case IN_PROGRESS -> Component.literal("En cours");
            case LOCKED -> Component.literal("Verrouillés");
        };
    }

    private void refreshFilteredObjectives() {
        filteredObjectives.clear();

        for (FactionObjectiveDefinition def : FactionObjectiveRegistry.getAll().values()) {
            if (!selectedCategory.equals(def.getCategory())) continue;

            boolean completed = isCompleted(def);
            boolean unlocked = isUnlocked(def);

            boolean matches = switch (selectedFilter) {
                case ALL -> true;
                case COMPLETED -> completed;
                case IN_PROGRESS -> unlocked && !completed;
                case LOCKED -> !unlocked;
            };

            if (matches) {
                filteredObjectives.add(def);
            }
        }

        if (!filteredObjectives.isEmpty()) {
            if (selectedObjective == null || !filteredObjectives.contains(selectedObjective)) {
                selectedObjective = filteredObjectives.get(0);
            }
        } else {
            selectedObjective = null;
        }

        int maxScroll = Math.max(0, filteredObjectives.size() - visibleEntries());
        if (scrollOffset > maxScroll) {
            scrollOffset = maxScroll;
        }
    }
    private int getAnimatedCompletedColor() {
        double time = (System.currentTimeMillis() % 1200L) / 1200.0;
        double wave = (Math.sin(time * Math.PI * 2.0) + 1.0) / 2.0;

        int base = 80;
        int bonus = (int) (wave * 100);

        int green = Math.min(255, base + bonus);

        return 0xFF000000 | (0x44 << 16) | (green << 8) | 0x44;
    }

    private int visibleEntries() {
        return LIST_HEIGHT / ENTRY_HEIGHT;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        int maxScroll = Math.max(0, filteredObjectives.size() - visibleEntries());

        if (delta < 0) {
            scrollOffset = Math.min(scrollOffset + 1, maxScroll);
        } else if (delta > 0) {
            scrollOffset = Math.max(scrollOffset - 1, 0);
        }

        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (int i = 0; i < visibleEntries(); i++) {
            int index = scrollOffset + i;
            if (index >= filteredObjectives.size()) {
                break;
            }

            int entryY = listY() + (i * ENTRY_HEIGHT);

            if (mouseX >= listX() && mouseX <= listX() + LIST_WIDTH
                    && mouseY >= entryY && mouseY <= entryY + ENTRY_HEIGHT - 2) {
                selectedObjective = filteredObjectives.get(index);
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);

        drawPanelBackground(graphics);
        drawHeader(graphics);
        drawObjectiveList(graphics);
        drawDetailsPanel(graphics);

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    private void drawPanelBackground(GuiGraphics graphics) {
        graphics.fill(leftPos, topPos, leftPos + PANEL_WIDTH, topPos + PANEL_HEIGHT, 0xD0101010);
        graphics.fill(leftPos, topPos, leftPos + PANEL_WIDTH, topPos + 1, 0xFFFFFFFF);
        graphics.fill(leftPos, topPos + PANEL_HEIGHT - 1, leftPos + PANEL_WIDTH, topPos + PANEL_HEIGHT, 0xFFFFFFFF);
        graphics.fill(leftPos, topPos, leftPos + 1, topPos + PANEL_HEIGHT, 0xFFFFFFFF);
        graphics.fill(leftPos + PANEL_WIDTH - 1, topPos, leftPos + PANEL_WIDTH, topPos + PANEL_HEIGHT, 0xFFFFFFFF);
    }




    private void drawObjectiveList(GuiGraphics graphics) {
        graphics.fill(listX(), listY(), listX() + LIST_WIDTH, listY() + LIST_HEIGHT, 0x66000000);

        int visible = visibleEntries();

        for (int i = 0; i < visible; i++) {
            int index = scrollOffset + i;
            if (index >= filteredObjectives.size()) {
                break;
            }

            FactionObjectiveDefinition def = filteredObjectives.get(index);
            int entryY = listY() + (i * ENTRY_HEIGHT);

            boolean selected = def.equals(selectedObjective);
            boolean completed = isCompleted(def);
            boolean unlocked = isUnlocked(def);

            int bgColor;
            if (selected) {
                bgColor = 0x88444488;
            } else if (!unlocked) {
                bgColor = 0x55333333;
            } else if (completed) {
                int pulse = isMasteryObjective(def) ? getMasteryAnimatedColor() : getAnimatedCompletedColor();
                bgColor = 0x55000000 | (pulse & 0x00FFFFFF);
            } else {
                bgColor = 0x55222222;
            }

            graphics.fill(listX() + 2, entryY + 1, listX() + LIST_WIDTH - 2, entryY + ENTRY_HEIGHT - 2, bgColor);

            ItemStack iconStack = getIconStack(def);
            graphics.renderItem(iconStack, listX() + 6, entryY + 6);

            int textColor;
            if (!unlocked) {
                textColor = 0x777777;
            } else if (completed) {
                textColor = 0x55FF55;
            } else {
                textColor = 0xFFFFFF;
            }

            graphics.drawString(this.font, trim(def.getDisplayName(), 24), listX() + 28, entryY + 5, textColor);

            int progress = ClientFactionProgressCache.progress.getOrDefault(def.getId(), 0);
            int target = Math.max(1, def.getTargetValue());

            drawProgressBar(
                    graphics,
                    listX() + 28,
                    entryY + 16,
                    LIST_WIDTH - 36,
                    8,
                    Math.min(progress, target),
                    target,
                    unlocked,
                    completed
            );
        }

        drawScrollBar(graphics);
    }

    private void drawDetailsPanel(GuiGraphics graphics) {
        graphics.fill(detailsX(), detailsY(), detailsX() + DETAILS_WIDTH, detailsY() + DETAILS_HEIGHT, 0x66000000);
        graphics.drawString(this.font, "Détails", detailsX() + 6, detailsY() + 6, 0xFFD700);

        if (selectedObjective == null) {
            graphics.drawString(this.font, "Aucun objectif", detailsX() + 6, detailsY() + 24, 0xAAAAAA);
            return;
        }

        boolean isRewardObjective = "Récompenses".equals(selectedObjective.getCategory());
        boolean isMastery = FactionObjectiveRegistry.RUBY_MASTERY.equals(selectedObjective.getId());

        boolean completed = isCompleted(selectedObjective);
        boolean unlocked = isUnlocked(selectedObjective);
        int progress = ClientFactionProgressCache.progress.getOrDefault(selectedObjective.getId(), 0);
        int target = Math.max(1, selectedObjective.getTargetValue());

        int y = detailsY() + 22;

        ItemStack icon = getIconStack(selectedObjective);
        graphics.renderItem(icon, detailsX() + 6, y);

        int titleColor = isMastery ? getMasteryAnimatedColor() : 0xFFFFFF;
        graphics.drawString(this.font, trim(selectedObjective.getDisplayName(), 18), detailsX() + 28, y + 4, titleColor);
        y += 22;

        y += drawWrappedText(
                graphics,
                "Desc : " + selectedObjective.getDescription(),
                detailsX() + 6,
                y,
                DETAILS_WIDTH - 12,
                0xCCCCCC,
                3
        ) + 2;

        graphics.drawString(this.font, "Catégorie : " + selectedObjective.getCategory(), detailsX() + 6, y, 0xAAAAFF);
        y += 12;

        String status;
        int statusColor;
        if (!unlocked) {
            status = "Statut : verrouillé";
            statusColor = 0x999999;
        } else if (completed) {
            status = "Statut : terminé";
            statusColor = 0x55FF55;
        } else {
            status = "Statut : en cours";
            statusColor = 0xFFFFFF;
        }

        graphics.drawString(this.font, status, detailsX() + 6, y, statusColor);
        y += 12;

        graphics.drawString(this.font, "Progression : " + Math.min(progress, target) + "/" + target, detailsX() + 6, y, 0xFFFFFF);
        y += 12;

        drawProgressBar(
                graphics,
                detailsX() + 6,
                y,
                DETAILS_WIDTH - 12,
                10,
                Math.min(progress, target),
                target,
                unlocked,
                completed
        );
        y += 14;

        if (isRewardObjective) {
            y += drawWrappedText(
                    graphics,
                    "Récompense : " + selectedObjective.getRewardText(),
                    detailsX() + 6,
                    y,
                    DETAILS_WIDTH - 12,
                    0xFFD700,
                    3
            ) + 2;

            y += drawWrappedText(
                    graphics,
                    isMastery ? "Récompense finale Rubis" : "Récompense de catégorie",
                    detailsX() + 6,
                    y,
                    DETAILS_WIDTH - 12,
                    isMastery ? 0xFFAA00 : 0xFFD700,
                    1
            );

            return;
        }

        y += drawWrappedText(
                graphics,
                "Récompense : " + selectedObjective.getRewardText(),
                detailsX() + 6,
                y,
                DETAILS_WIDTH - 12,
                0xFFD700,
                2
        ) + 2;

        String parentId = selectedObjective.getParentObjectiveId();
        if (parentId != null) {
            FactionObjectiveDefinition parent = FactionObjectiveRegistry.get(parentId);
            String parentName = parent != null ? parent.getDisplayName() : parentId;

            drawWrappedText(
                    graphics,
                    "Prérequis : " + trim(parentName, 14),
                    detailsX() + 6,
                    y,
                    DETAILS_WIDTH - 12,
                    0xAAAAAA,
                    1
            );
        } else {
            graphics.drawString(this.font, "Prérequis : aucun", detailsX() + 6, y, 0xAAAAAA);
        }
    }


    private ItemStack getIconStack(FactionObjectiveDefinition def) {
        ResourceLocation itemId = ResourceLocation.parse(def.getIconItemId());
        Item item = ForgeRegistries.ITEMS.getValue(itemId);
        if (item == null) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(item);
    }

    private void drawHeader(GuiGraphics graphics) {
        int completed = ClientFactionProgressCache.getCompletedCount();
        int total = Math.max(1, FactionObjectiveRegistry.getTotalObjectives());
        int percent = (int) ((completed / (float) total) * 100.0f);
        int level = 1 + (completed / 2);

        graphics.drawString(this.font, this.title, leftPos + 10, topPos + 10, 0xFFFFFF);
        graphics.drawString(this.font, "Faction : " + ClientFactionProgressCache.factionId, leftPos + 10, topPos + 22, 0xAAAAFF);

        graphics.drawString(this.font, "Niveau de faction : " + level, leftPos + 250, topPos + 10, 0x55FF55);
        graphics.drawString(this.font, "Progression globale : " + completed + "/" + total + " (" + percent + "%)", leftPos + 250, topPos + 22, 0xFFD700);

        drawProgressBar(graphics, leftPos + 250, topPos + 34, 180, 10, completed, total, true, completed >= total);
    }

    private void drawProgressBar(GuiGraphics graphics, int x, int y, int width, int height,
                                 int progress, int target, boolean unlocked, boolean completed) {

        graphics.fill(x, y, x + width, y + height, 0xFF222222);

        int fillWidth = (int) ((width - 2) * (progress / (float) target));
        int fillColor;


        if (!unlocked) {
            fillColor = 0xFF555555;
        } else if (completed) {
            fillColor = (selectedObjective != null && FactionObjectiveRegistry.RUBY_MASTERY.equals(selectedObjective.getId()))
                    ? getMasteryAnimatedColor()
                    : getAnimatedCompletedColor();
        } else {
            fillColor = 0xFFFF5555;
        }

        graphics.fill(x + 1, y + 1, x + 1 + fillWidth, y + height - 1, fillColor);
    }

    private void drawScrollBar(GuiGraphics graphics) {
        int total = filteredObjectives.size();
        int visible = visibleEntries();

        if (total <= visible) {
            return;
        }

        int barX = listX() + LIST_WIDTH - 4;
        int barY = listY();
        int barHeight = LIST_HEIGHT;

        graphics.fill(barX, barY, barX + 3, barY + barHeight, 0xFF333333);

        int thumbHeight = Math.max(20, (int) (barHeight * (visible / (float) total)));
        int maxScroll = total - visible;
        int thumbY = barY + (int) ((barHeight - thumbHeight) * (scrollOffset / (float) maxScroll));

        graphics.fill(barX, thumbY, barX + 3, thumbY + thumbHeight, 0xFFAAAAAA);
    }

    private boolean isCompleted(FactionObjectiveDefinition def) {
        return ClientFactionProgressCache.completed.getOrDefault(def.getId(), false);
    }

    private boolean isUnlocked(FactionObjectiveDefinition def) {
        String parentId = def.getParentObjectiveId();
        if (parentId == null) {
            return true;
        }
        return ClientFactionProgressCache.completed.getOrDefault(parentId, false);
    }

    private String trim(String text, int maxLength) {
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, Math.max(0, maxLength - 3)) + "...";
    }

    private int drawWrappedText(GuiGraphics graphics, String text, int x, int y, int maxWidth, int color, int maxLines) {
        List<net.minecraft.util.FormattedCharSequence> lines = this.font.split(Component.literal(text), maxWidth);
        int drawnLines = Math.min(lines.size(), maxLines);

        for (int i = 0; i < drawnLines; i++) {
            graphics.drawString(this.font, lines.get(i), x, y + (i * 8), color);
        }

        return drawnLines * 8;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
