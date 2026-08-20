package com.magius.world.mod.clan.client.screen.tab;

import com.magius.world.mod.clan.chronicle.data.ChronicleDefinition;
import com.magius.world.mod.clan.chronicle.data.ChronicleRegistry;
import com.magius.world.mod.clan.chronicle.unlock.ChronicleUnlockManager;
import com.magius.world.mod.clan.client.screen.widget.ClanCardRenderer;
import com.magius.world.mod.clan.theme.ClanTheme;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class ChronicleTab implements ClanTab {

    private static final int GAP = 5;
    private static final int ENTRY_HEIGHT = 29;
    private static final int ENTRY_GAP = 4;

    private static final ItemStack LOCKED_ICON =
            new ItemStack(Items.IRON_BARS);

    private int selectedChronicle = -1;
    private int scrollOffset = 0;

    private boolean reading = false;
    private int currentPage = 0;

    private record ReaderPage(
            String title,
            List<FormattedCharSequence> lines
    ) {
    }

    private List<ReaderPage> buildReaderPages(
            ChronicleDefinition chronicle,
            int textWidth,
            int availableTextHeight
    ) {

        var font = Minecraft.getInstance().font;

        List<ReaderPage> result =
                new ArrayList<>();

        int maxLines =
                Math.max(
                        1,
                        availableTextHeight / 11
                );

        for (ChronicleDefinition.Page sourcePage :
                chronicle.getPages()) {

            List<FormattedCharSequence> allLines =
                    new ArrayList<>();

            for (String paragraph : sourcePage.getText()) {

                if (paragraph == null || paragraph.isBlank()) {

                    allLines.add(
                            FormattedCharSequence.EMPTY
                    );

                    continue;
                }

                allLines.addAll(
                        font.split(
                                Component.literal(paragraph),
                                textWidth
                        )
                );

                // Espace entre les paragraphes
                allLines.add(
                        FormattedCharSequence.EMPTY
                );
            }

            // Évite une ligne vide inutile à la fin
            if (
                    !allLines.isEmpty()
                            && allLines.get(allLines.size() - 1)
                            == FormattedCharSequence.EMPTY
            ) {
                allLines.remove(
                        allLines.size() - 1
                );
            }

            if (allLines.isEmpty()) {

                result.add(
                        new ReaderPage(
                                sourcePage.getTitle(),
                                List.of()
                        )
                );

                continue;
            }

            for (
                    int start = 0;
                    start < allLines.size();
                    start += maxLines
            ) {

                int end =
                        Math.min(
                                start + maxLines,
                                allLines.size()
                        );

                result.add(
                        new ReaderPage(
                                sourcePage.getTitle(),
                                new ArrayList<>(
                                        allLines.subList(
                                                start,
                                                end
                                        )
                                )
                        )
                );
            }
        }

        return result;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Chroniques");
    }

    @Override
    public void render(
            GuiGraphics guiGraphics,
            ClanTheme theme,
            ResourceLocation clanId,
            int x,
            int y,
            int width,
            int height,
            int mouseX,
            int mouseY,
            float partialTick
    ) {

        List<ChronicleDefinition> chronicles =
                ChronicleRegistry.getForClan(clanId);

        Player player =
                Minecraft.getInstance().player;

        int totalChronicles =
                chronicles.size();

        int unlockedChronicles =
                countUnlocked(player, chronicles);

        validateSelection(
                player,
                chronicles
        );

        boolean compact =
                height < 190;

        int listWidth =
                compact
                        ? (int) (width * 0.44f)
                        : (int) (width * 0.38f);

        int detailWidth =
                width - listWidth - GAP;

        int listX = x;
        int detailX =
                x + listWidth + GAP;

        /*
         * =====================================================
         * LISTE
         * =====================================================
         */

        ClanCardRenderer.render(
                guiGraphics,
                theme,
                listX,
                y,
                listWidth,
                height,
                Component.literal(
                        "Chroniques  "
                                + unlockedChronicles
                                + " / "
                                + totalChronicles
                )
        );

        int entryX =
                listX + 7;

        int entryY =
                y + 25;

        int entryWidth =
                listWidth - 20;

        int entriesBottom =
                y + height - 7;

        int availableHeight =
                entriesBottom - entryY;

        int visibleEntries =
                Math.max(
                        1,
                        (availableHeight + ENTRY_GAP)
                                / (ENTRY_HEIGHT + ENTRY_GAP)
                );

        int maxScroll =
                Math.max(
                        0,
                        totalChronicles - visibleEntries
                );

        scrollOffset =
                Math.max(
                        0,
                        Math.min(
                                scrollOffset,
                                maxScroll
                        )
                );

        if (chronicles.isEmpty()) {

            renderEmptyList(
                    guiGraphics,
                    theme,
                    listX,
                    y,
                    listWidth
            );

        } else {

            for (
                    int visibleIndex = 0;
                    visibleIndex < visibleEntries;
                    visibleIndex++
            ) {

                int chronicleIndex =
                        scrollOffset
                                + visibleIndex;

                if (chronicleIndex >= totalChronicles) {
                    break;
                }

                ChronicleDefinition chronicle =
                        chronicles.get(
                                chronicleIndex
                        );

                boolean unlocked =
                        ChronicleUnlockManager.isUnlocked(
                                player,
                                chronicle
                        );

                boolean selected =
                        unlocked
                                && chronicleIndex
                                == selectedChronicle;

                int currentY =
                        entryY
                                + visibleIndex
                                * (
                                ENTRY_HEIGHT
                                        + ENTRY_GAP
                        );

                renderChronicleEntry(
                        guiGraphics,
                        theme,
                        entryX,
                        currentY,
                        entryWidth,
                        ENTRY_HEIGHT,
                        unlocked
                                ? getChronicleIcon(chronicle)
                                : LOCKED_ICON,
                        unlocked
                                ? chronicle.getShortTitle()
                                : "???",
                        selected,
                        unlocked,
                        mouseX,
                        mouseY
                );
            }

            renderScrollbar(
                    guiGraphics,
                    theme,
                    listX,
                    y,
                    listWidth,
                    height,
                    visibleEntries,
                    totalChronicles
            );
        }

        /*
         * =====================================================
         * DÉTAIL
         * =====================================================
         */

        if (chronicles.isEmpty()) {

            reading = false;

            renderEmptyDetailPanel(
                    guiGraphics,
                    theme,
                    detailX,
                    y,
                    detailWidth,
                    height
            );

        } else if (selectedChronicle < 0) {

            reading = false;

            renderLockedDetailPanel(
                    guiGraphics,
                    theme,
                    detailX,
                    y,
                    detailWidth,
                    height
            );

        } else {

            ChronicleDefinition selected =
                    chronicles.get(
                            selectedChronicle
                    );

            if (reading) {

                renderReader(
                        guiGraphics,
                        theme,
                        selected,
                        detailX,
                        y,
                        detailWidth,
                        height
                );

            } else {

                renderDetailPanel(
                        guiGraphics,
                        theme,
                        selected,
                        detailX,
                        y,
                        detailWidth,
                        height,
                        compact
                );
            }
        }
    }

    private void validateSelection(
            Player player,
            List<ChronicleDefinition> chronicles
    ) {

        if (chronicles.isEmpty()) {

            selectedChronicle = -1;
            scrollOffset = 0;

            return;
        }

        if (
                selectedChronicle >= 0
                        && selectedChronicle < chronicles.size()
                        && ChronicleUnlockManager.isUnlocked(
                        player,
                        chronicles.get(selectedChronicle)
                )
        ) {
            return;
        }

        selectedChronicle = -1;

        for (
                int i = 0;
                i < chronicles.size();
                i++
        ) {

            if (
                    ChronicleUnlockManager.isUnlocked(
                            player,
                            chronicles.get(i)
                    )
            ) {
                selectedChronicle = i;
                return;
            }
        }
    }

    private int countUnlocked(
            Player player,
            List<ChronicleDefinition> chronicles
    ) {

        int count = 0;

        for (
                ChronicleDefinition chronicle :
                chronicles
        ) {

            if (
                    ChronicleUnlockManager.isUnlocked(
                            player,
                            chronicle
                    )
            ) {
                count++;
            }
        }

        return count;
    }

    private void renderEmptyList(
            GuiGraphics guiGraphics,
            ClanTheme theme,
            int x,
            int y,
            int width
    ) {

        var font =
                Minecraft.getInstance().font;

        String text =
                "Aucune chronique";

        int textWidth =
                font.width(text);

        guiGraphics.drawString(
                font,
                Component.literal(text),
                x + (width - textWidth) / 2,
                y + 35,
                theme.getTextColor(),
                false
        );
    }

    private void renderEmptyDetailPanel(
            GuiGraphics guiGraphics,
            ClanTheme theme,
            int x,
            int y,
            int width,
            int height
    ) {

        var font =
                Minecraft.getInstance().font;

        ClanCardRenderer.render(
                guiGraphics,
                theme,
                x,
                y,
                width,
                height,
                Component.literal("Chronique")
        );

        String text =
                "Aucune chronique disponible.";

        int textWidth =
                font.width(text);

        guiGraphics.drawString(
                font,
                Component.literal(text),
                x + (width - textWidth) / 2,
                y + 35,
                theme.getTextColor(),
                false
        );
    }

    private void renderLockedDetailPanel(
            GuiGraphics guiGraphics,
            ClanTheme theme,
            int x,
            int y,
            int width,
            int height
    ) {

        var font =
                Minecraft.getInstance().font;

        ClanCardRenderer.render(
                guiGraphics,
                theme,
                x,
                y,
                width,
                height,
                Component.literal(
                        "Chronique verrouillée"
                )
        );

        int iconX =
                x + (width - 16) / 2;

        int iconY =
                y + 40;

        guiGraphics.renderItem(
                LOCKED_ICON,
                iconX,
                iconY
        );

        String unknown =
                "???";

        guiGraphics.drawCenteredString(
                font,
                Component.literal(unknown),
                x + width / 2,
                iconY + 24,
                theme.getTitleColor()
        );

        List<net.minecraft.util.FormattedCharSequence> lines =
                font.split(
                        Component.literal(
                                "Cette chronique n'a pas encore été découverte."
                        ),
                        Math.max(
                                20,
                                width - 30
                        )
                );

        int textY =
                iconY + 45;

        for (
                int i = 0;
                i < lines.size();
                i++
        ) {

            int lineWidth =
                    font.width(lines.get(i));

            guiGraphics.drawString(
                    font,
                    lines.get(i),
                    x + (width - lineWidth) / 2,
                    textY + i * 12,
                    theme.getTextColor(),
                    false
            );
        }
    }

    private void renderDetailPanel(
            GuiGraphics guiGraphics,
            ClanTheme theme,
            ChronicleDefinition chronicle,
            int x,
            int y,
            int width,
            int height,
            boolean compact
    ) {

        var font =
                Minecraft.getInstance().font;

        ClanCardRenderer.render(
                guiGraphics,
                theme,
                x,
                y,
                width,
                height,
                Component.literal(
                        chronicle.getTitle()
                )
        );

        int bookX =
                x + 10;

        int bookY =
                y + 29;

        guiGraphics.renderItem(
                getChronicleIcon(chronicle),
                bookX,
                bookY
        );

        guiGraphics.drawString(
                font,
                Component.literal(
                        chronicle.getShortTitle()
                ),
                bookX + 23,
                bookY + 1,
                theme.getTitleColor(),
                false
        );

        guiGraphics.drawString(
                font,
                Component.literal(
                        "Chronique découverte"
                ),
                bookX + 23,
                bookY + 13,
                theme.getAccentColor(),
                false
        );

        int descriptionX =
                x + 10;

        int descriptionY =
                bookY + 34;

        List<net.minecraft.util.FormattedCharSequence> lines =
                font.split(
                        Component.literal(
                                chronicle.getDescription()
                        ),
                        Math.max(
                                20,
                                width - 20
                        )
                );

        int maxLines =
                compact ? 2 : 4;

        int lineCount =
                Math.min(
                        lines.size(),
                        maxLines
                );

        for (
                int i = 0;
                i < lineCount;
                i++
        ) {

            guiGraphics.drawString(
                    font,
                    lines.get(i),
                    descriptionX,
                    descriptionY + i * 12,
                    theme.getTextColor(),
                    false
            );
        }

        if (!compact) {

            int pageCount =
                    chronicle
                            .getPages()
                            .size();

            guiGraphics.drawString(
                    font,
                    Component.literal(
                            pageCount
                                    + (
                                    pageCount == 1
                                            ? " page"
                                            : " pages"
                            )
                    ),
                    descriptionX,
                    descriptionY
                            + lineCount * 12
                            + 10,
                    theme.getAccentColor(),
                    false
            );
        }

        String readText =
                "Cliquer pour lire";

        int readWidth =
                font.width(readText);

        guiGraphics.drawString(
                font,
                Component.literal(readText),
                x
                        + (width - readWidth) / 2,
                y + height - 17,
                theme.getTitleColor(),
                false
        );
    }

    private void renderReader(
            GuiGraphics guiGraphics,
            ClanTheme theme,
            ChronicleDefinition chronicle,
            int x,
            int y,
            int width,
            int height
    ) {

        var font =
                Minecraft.getInstance().font;

        int textX =
                x + 14;

        int textY =
                y + 54;

        int textWidth =
                Math.max(
                        20,
                        width - 28
                );

        /*
         * On réserve le bas du panneau pour :
         *
         * <     Page X / X     >
         *          Retour
         */
        int navigationY =
                y + height - 34;

        int textBottom =
                navigationY - 12;

        int availableTextHeight =
                Math.max(
                        11,
                        textBottom - textY
                );

        List<ReaderPage> pages =
                buildReaderPages(
                        chronicle,
                        textWidth,
                        availableTextHeight
                );

        if (pages.isEmpty()) {

            reading = false;
            currentPage = 0;

            return;
        }

        currentPage =
                Math.max(
                        0,
                        Math.min(
                                currentPage,
                                pages.size() - 1
                        )
                );

        ReaderPage page =
                pages.get(currentPage);

        // =====================================================
        // CADRE
        // =====================================================

        ClanCardRenderer.render(
                guiGraphics,
                theme,
                x,
                y,
                width,
                height,
                Component.literal(
                        chronicle.getShortTitle()
                )
        );

        // =====================================================
        // TITRE
        // =====================================================

        guiGraphics.drawCenteredString(
                font,
                Component.literal(
                        page.title()
                ),
                x + width / 2,
                y + 29,
                theme.getTitleColor()
        );

        // Séparateur
        guiGraphics.fill(
                x + 12,
                y + 44,
                x + width - 12,
                y + 45,
                theme.getAccentColor()
        );

        // =====================================================
        // TEXTE
        // =====================================================

        int currentY =
                textY;

        for (FormattedCharSequence line :
                page.lines()) {

            /*
             * Ligne vide = séparation de paragraphe.
             */
            if (line == FormattedCharSequence.EMPTY) {

                currentY += 7;
                continue;
            }

            guiGraphics.drawString(
                    font,
                    line,
                    textX,
                    currentY,
                    theme.getTextColor(),
                    false
            );

            currentY += 11;
        }

        // =====================================================
        // NAVIGATION
        // =====================================================

        String counter =
                "Page "
                        + (currentPage + 1)
                        + " / "
                        + pages.size();

        /*
         * Flèche précédente
         */
        if (currentPage > 0) {

            guiGraphics.drawString(
                    font,
                    Component.literal("<"),
                    x + 16,
                    navigationY,
                    theme.getTitleColor(),
                    false
            );
        }

        /*
         * Compteur centré
         */
        guiGraphics.drawCenteredString(
                font,
                Component.literal(counter),
                x + width / 2,
                navigationY,
                theme.getAccentColor()
        );

        /*
         * Flèche suivante
         */
        if (currentPage < pages.size() - 1) {

            String next =
                    ">";

            guiGraphics.drawString(
                    font,
                    Component.literal(next),
                    x + width
                            - 16
                            - font.width(next),
                    navigationY,
                    theme.getTitleColor(),
                    false
            );
        }

        // =====================================================
        // RETOUR
        // =====================================================

        guiGraphics.drawCenteredString(
                font,
                Component.literal("Retour"),
                x + width / 2,
                y + height - 18,
                theme.getTitleColor()
        );
    }

    private void renderChronicleEntry(
            GuiGraphics guiGraphics,
            ClanTheme theme,
            int x,
            int y,
            int width,
            int height,
            ItemStack icon,
            String name,
            boolean selected,
            boolean unlocked,
            int mouseX,
            int mouseY
    ) {

        var font =
                Minecraft.getInstance().font;

        boolean hovered =
                mouseX >= x
                        && mouseX < x + width
                        && mouseY >= y
                        && mouseY < y + height;

        int borderColor =
                selected
                        ? theme.getTitleColor()
                        : theme.getAccentColor();

        guiGraphics.fill(
                x,
                y,
                x + width,
                y + height,
                borderColor
        );

        int backgroundColor;

        if (selected) {

            backgroundColor =
                    theme.getAccentColor();

        } else if (hovered && unlocked) {

            backgroundColor =
                    0xFF3A1117;

        } else {

            backgroundColor =
                    theme.getButtonColor();
        }

        guiGraphics.fill(
                x + 2,
                y + 2,
                x + width - 2,
                y + height - 2,
                backgroundColor
        );

        if (selected) {

            guiGraphics.fill(
                    x + 2,
                    y + 2,
                    x + 5,
                    y + height - 2,
                    theme.getTitleColor()
            );
        }

        int iconX =
                x + 8;

        int iconY =
                y + (height - 16) / 2;

        guiGraphics.renderItem(
                icon,
                iconX,
                iconY
        );

        int textColor;

        if (selected) {

            textColor =
                    theme.getTitleColor();

        } else if (unlocked) {

            textColor =
                    theme.getTextColor();

        } else {

            textColor =
                    0xFF777777;
        }

        guiGraphics.drawString(
                font,
                Component.literal(name),
                iconX + 22,
                y + (height - 8) / 2,
                textColor,
                false
        );
    }

    private void renderScrollbar(
            GuiGraphics guiGraphics,
            ClanTheme theme,
            int listX,
            int y,
            int listWidth,
            int height,
            int visibleEntries,
            int totalChronicles
    ) {

        if (
                totalChronicles <= 0
                        || visibleEntries >= totalChronicles
        ) {
            return;
        }

        int trackX =
                listX + listWidth - 10;

        int trackY =
                y + 25;

        int trackHeight =
                height - 32;

        guiGraphics.fill(
                trackX,
                trackY,
                trackX + 3,
                trackY + trackHeight,
                0xFF160708
        );

        int thumbHeight =
                Math.max(
                        12,
                        trackHeight
                                * visibleEntries
                                / totalChronicles
                );

        int maxScroll =
                Math.max(
                        1,
                        totalChronicles
                                - visibleEntries
                );

        int thumbTravel =
                trackHeight
                        - thumbHeight;

        int thumbY =
                trackY
                        + thumbTravel
                        * scrollOffset
                        / maxScroll;

        guiGraphics.fill(
                trackX,
                thumbY,
                trackX + 3,
                thumbY + thumbHeight,
                theme.getTitleColor()
        );
    }

    @Override
    public boolean mouseClicked(
            double mouseX,
            double mouseY,
            int button,
            ResourceLocation clanId,
            int x,
            int y,
            int width,
            int height
    ) {

        if (button != 0) {
            return false;
        }

        List<ChronicleDefinition> chronicles =
                ChronicleRegistry.getForClan(
                        clanId
                );

        Player player =
                Minecraft.getInstance().player;

        int totalChronicles =
                chronicles.size();

        if (totalChronicles == 0) {
            return false;
        }


        boolean compact =
                height < 190;

        int listWidth =
                compact
                        ? (int) (width * 0.44f)
                        : (int) (width * 0.38f);

        /*
         * =========================================================
         * CLICS DANS LE PANNEAU DE DROITE
         * =========================================================
         */

        if (
                selectedChronicle >= 0
                        && selectedChronicle < totalChronicles
        ) {

            ChronicleDefinition selected =
                    chronicles.get(
                            selectedChronicle
                    );

            boolean unlocked =
                    ChronicleUnlockManager.isUnlocked(
                            player,
                            selected
                    );

            if (unlocked) {

                int detailX =
                        x + listWidth + GAP;

                int detailWidth =
                        width - listWidth - GAP;

                boolean insideDetail =
                        mouseX >= detailX
                                && mouseX < detailX + detailWidth
                                && mouseY >= y
                                && mouseY < y + height;

                if (insideDetail) {

                    /*
                     * =============================================
                     * MODE LECTURE
                     * =============================================
                     */

                    if (reading) {

                        int navigationY =
                                y + height - 34;

                        int textY =
                                y + 54;

                        int textBottom =
                                navigationY - 12;

                        int availableTextHeight =
                                Math.max(
                                        11,
                                        textBottom - textY
                                );

                        List<ReaderPage> pages =
                                buildReaderPages(
                                        selected,
                                        Math.max(
                                                20,
                                                detailWidth - 28
                                        ),
                                        availableTextHeight
                                );

                        // RETOUR
                        if (
                                mouseY >= y + height - 25
                                        && mouseY < y + height
                        ) {

                            reading = false;
                            currentPage = 0;

                            return true;
                        }

                        // NAVIGATION
                        if (
                                mouseY >= y + height - 43
                                        && mouseY < y + height - 25
                        ) {

                            /*
                             * Flèche gauche
                             */
                            if (
                                    mouseX >= detailX
                                            && mouseX < detailX + 45
                            ) {

                                if (currentPage > 0) {
                                    currentPage--;
                                }

                                return true;
                            }

                            /*
                             * Flèche droite
                             */
                            if (
                                    mouseX >= detailX
                                            + detailWidth
                                            - 45
                                            && mouseX
                                            < detailX
                                            + detailWidth
                            ) {

                                if (
                                        currentPage
                                                < pages.size() - 1
                                ) {
                                    currentPage++;
                                }

                                return true;
                            }
                        }

                    } else {

                        /*
                         * =============================================
                         * OUVERTURE DU LECTEUR
                         * =============================================
                         */

                        if (
                                !selected.getPages().isEmpty()
                                        && mouseY
                                        >= y + height - 27
                                        && mouseY
                                        < y + height
                        ) {

                            reading = true;
                            currentPage = 0;

                            return true;
                        }
                    }
                }
            }
        }

        int entryX =
                x + 7;

        int entryY =
                y + 25;

        int entryWidth =
                listWidth - 20;

        int entriesBottom =
                y + height - 7;

        int availableHeight =
                entriesBottom - entryY;

        int visibleEntries =
                Math.max(
                        1,
                        (availableHeight + ENTRY_GAP)
                                / (ENTRY_HEIGHT + ENTRY_GAP)
                );

        for (
                int visibleIndex = 0;
                visibleIndex < visibleEntries;
                visibleIndex++
        ) {

            int chronicleIndex =
                    scrollOffset
                            + visibleIndex;

            if (chronicleIndex >= totalChronicles) {
                break;
            }

            int currentY =
                    entryY
                            + visibleIndex
                            * (
                            ENTRY_HEIGHT
                                    + ENTRY_GAP
                    );

            boolean inside =
                    mouseX >= entryX
                            && mouseX < entryX + entryWidth
                            && mouseY >= currentY
                            && mouseY < currentY + ENTRY_HEIGHT;

            if (!inside) {
                continue;
            }

            ChronicleDefinition chronicle =
                    chronicles.get(
                            chronicleIndex
                    );

            if (
                    !ChronicleUnlockManager.isUnlocked(
                            player,
                            chronicle
                    )
            ) {
                return true;
            }

            selectedChronicle =
                    chronicleIndex;

            reading = false;
            currentPage = 0;

            return true;
        }

        return false;
    }

    @Override
    public boolean mouseScrolled(
            double mouseX,
            double mouseY,
            double delta,
            ResourceLocation clanId,
            int x,
            int y,
            int width,
            int height
    ) {

        List<ChronicleDefinition> chronicles =
                ChronicleRegistry.getForClan(
                        clanId
                );

        int totalChronicles =
                chronicles.size();

        if (totalChronicles == 0) {
            return false;
        }

        boolean compact =
                height < 190;

        int listWidth =
                compact
                        ? (int) (width * 0.44f)
                        : (int) (width * 0.38f);

        boolean insideList =
                mouseX >= x
                        && mouseX < x + listWidth
                        && mouseY >= y
                        && mouseY < y + height;

        if (!insideList) {
            return false;
        }

        int entryY =
                y + 25;

        int entriesBottom =
                y + height - 7;

        int availableHeight =
                entriesBottom - entryY;

        int visibleEntries =
                Math.max(
                        1,
                        (availableHeight + ENTRY_GAP)
                                / (ENTRY_HEIGHT + ENTRY_GAP)
                );

        int maxScroll =
                Math.max(
                        0,
                        totalChronicles
                                - visibleEntries
                );

        if (delta < 0) {

            scrollOffset =
                    Math.min(
                            maxScroll,
                            scrollOffset + 1
                    );

        } else if (delta > 0) {

            scrollOffset =
                    Math.max(
                            0,
                            scrollOffset - 1
                    );
        }

        return true;
    }

    private ItemStack getChronicleIcon(
            ChronicleDefinition chronicle
    ) {

        Item item =
                BuiltInRegistries.ITEM
                        .get(
                                chronicle.getIcon()
                        );

        if (item == Items.AIR) {

            return new ItemStack(
                    Items.ENCHANTED_BOOK
            );
        }

        return new ItemStack(item);
    }
}