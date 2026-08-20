package com.magius.world.mod.clan.client.screen;

import com.magius.world.mod.clan.client.layout.ClanLayout;
import com.magius.world.mod.clan.client.screen.tab.*;
import com.magius.world.mod.clan.client.screen.widget.ClanHeaderRenderer;
import com.magius.world.mod.clan.client.screen.widget.ClanSidebarButton;
import com.magius.world.mod.clan.client.screen.widget.ClanSidebarRenderer;
import com.magius.world.mod.clan.client.theme.ClanThemeRenderer;
import com.magius.world.mod.clan.theme.ClanTheme;
import com.magius.world.mod.clan.theme.ClanThemeRegistry;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ClanMainScreen extends Screen {

    private static final int NORMAL_WIDTH = 620;
    private static final int NORMAL_HEIGHT = 360;

    private static final int COMPACT_WIDTH = 470;
    private static final int COMPACT_HEIGHT = 280;

    private static final int SCREEN_MARGIN = 8;

    private final ResourceLocation clanId;
    private final ClanTheme theme;

    private final HomeTab homeTab =
            new HomeTab();

    private final ChronicleTab chronicleTab =
            new ChronicleTab();

    private final QuestTab questTab =
            new QuestTab();

    private final PrestigeTab prestigeTab =
            new PrestigeTab();

    private final RewardTab rewardTab =
            new RewardTab();

    private final MemberTab memberTab =
            new MemberTab();

    private ClanTab currentTab =
            homeTab;

    public ClanMainScreen(
            ResourceLocation clanId
    ) {

        super(
                Component.literal("Clan")
        );

        this.clanId =
                clanId;

        this.theme =
                ClanThemeRegistry
                        .get(clanId)
                        .orElseThrow(
                                () ->
                                        new IllegalStateException(
                                                "Aucun thème enregistré pour le clan : "
                                                        + clanId
                                        )
                        );
    }

    /*
     * =========================================================
     * MODE COMPACT
     * =========================================================
     */

    private boolean isCompactMode() {

        return this.width
                < NORMAL_WIDTH
                + SCREEN_MARGIN * 2

                || this.height
                < NORMAL_HEIGHT
                + SCREEN_MARGIN * 2;
    }

    /*
     * =========================================================
     * DIMENSIONS
     * =========================================================
     */

    private int getPanelWidth() {

        if (!isCompactMode()) {
            return NORMAL_WIDTH;
        }

        return Math.min(
                COMPACT_WIDTH,
                Math.max(
                        320,
                        this.width
                                - SCREEN_MARGIN * 2
                )
        );
    }

    private int getPanelHeight() {

        if (!isCompactMode()) {
            return NORMAL_HEIGHT;
        }

        return Math.min(
                COMPACT_HEIGHT,
                Math.max(
                        220,
                        this.height
                                - SCREEN_MARGIN * 2
                )
        );
    }

    private int getPanelX() {

        return (
                this.width
                        - getPanelWidth()
        ) / 2;
    }

    private int getPanelY() {

        return (
                this.height
                        - getPanelHeight()
        ) / 2;
    }

    /*
     * =========================================================
     * LAYOUT
     * =========================================================
     */

    private ClanLayout createLayout() {

        return new ClanLayout(
                getPanelX(),
                getPanelY(),
                getPanelWidth(),
                getPanelHeight(),
                isCompactMode()
        );
    }

    /*
     * =========================================================
     * INIT
     * =========================================================
     */

    @Override
    protected void init() {

        super.init();

        ClanLayout layout =
                createLayout();

        ScreenRectangle sidebar =
                layout.sidebar();

        boolean compact =
                layout.isCompact();

        int spacing =
                compact ? 2 : 3;

        int buttonX =
                sidebar.left() + 5;

        int buttonWidth =
                Math.max(
                        20,
                        sidebar.width() - 10
                );

        int availableButtonHeight =
                sidebar.height()
                        - 10
                        - spacing * 5;

        int buttonHeight =
                Math.max(
                        18,
                        Math.min(
                                compact ? 20 : 24,
                                availableButtonHeight / 6
                        )
                );

        int buttonY =
                sidebar.top() + 5;

        addTabButton(
                Component.literal("Accueil"),
                Items.OAK_DOOR,
                buttonX,
                buttonY,
                buttonWidth,
                buttonHeight,
                homeTab
        );

        addTabButton(
                Component.literal("Chroniques"),
                Items.WRITABLE_BOOK,
                buttonX,
                buttonY
                        + buttonHeight
                        + spacing,
                buttonWidth,
                buttonHeight,
                chronicleTab
        );

        addTabButton(
                Component.literal("Quêtes"),
                Items.PAPER,
                buttonX,
                buttonY
                        + 2
                        * (
                        buttonHeight
                                + spacing
                ),
                buttonWidth,
                buttonHeight,
                questTab
        );

        addTabButton(
                Component.literal("Prestige"),
                Items.NETHER_STAR,
                buttonX,
                buttonY
                        + 3
                        * (
                        buttonHeight
                                + spacing
                ),
                buttonWidth,
                buttonHeight,
                prestigeTab
        );

        addTabButton(
                Component.literal("Récompenses"),
                Items.CHEST,
                buttonX,
                buttonY
                        + 4
                        * (
                        buttonHeight
                                + spacing
                ),
                buttonWidth,
                buttonHeight,
                rewardTab
        );

        addTabButton(
                Component.literal("Membres"),
                Items.PLAYER_HEAD,
                buttonX,
                buttonY
                        + 5
                        * (
                        buttonHeight
                                + spacing
                ),
                buttonWidth,
                buttonHeight,
                memberTab
        );
    }

    /*
     * =========================================================
     * RENDU
     * =========================================================
     */

    @Override
    public void render(
            GuiGraphics guiGraphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {

        this.renderBackground(
                guiGraphics
        );

        int panelX =
                getPanelX();

        int panelY =
                getPanelY();

        int panelWidth =
                getPanelWidth();

        int panelHeight =
                getPanelHeight();

        ClanLayout layout =
                createLayout();

        ClanThemeRenderer.renderPanel(
                guiGraphics,
                theme,
                panelX,
                panelY,
                panelWidth,
                panelHeight
        );

        /*
         * HEADER
         */

        ScreenRectangle header =
                layout.header();

        ClanHeaderRenderer.render(
                guiGraphics,
                theme,
                header.left(),
                header.top(),
                header.width(),
                header.height()
        );

        /*
         * SIDEBAR
         */

        ClanSidebarRenderer.render(
                guiGraphics,
                layout.sidebar(),
                theme
        );

        /*
         * CONTENU
         */

        ScreenRectangle content =
                layout.content();

        ClanThemeRenderer.renderInnerBox(
                guiGraphics,
                theme,
                content.left(),
                content.top(),
                content.width(),
                content.height()
        );

        currentTab.render(
                guiGraphics,
                theme,
                clanId,
                content.left(),
                content.top(),
                content.width(),
                content.height(),
                mouseX,
                mouseY,
                partialTick
        );

        /*
         * FOOTER
         */

        ScreenRectangle footer =
                layout.footer();

        ClanThemeRenderer.renderInnerBox(
                guiGraphics,
                theme,
                footer.left(),
                footer.top(),
                footer.width(),
                footer.height()
        );

        if (!layout.isCompact()) {

            guiGraphics.drawString(
                    this.font,
                    Component.literal(
                            "MagiusWorld"
                    ),
                    footer.left() + 5,
                    footer.top()
                            + Math.max(
                            4,
                            (footer.height() - 8) / 2
                    ),
                    theme.getTextColor(),
                    false
            );
        }

        super.render(
                guiGraphics,
                mouseX,
                mouseY,
                partialTick
        );
    }

    /*
     * =========================================================
     * CLIC SOURIS
     * =========================================================
     */

    @Override
    public boolean mouseClicked(
            double mouseX,
            double mouseY,
            int button
    ) {

        /*
         * Les widgets Minecraft restent prioritaires.
         *
         * C'est important pour que les boutons de la sidebar
         * continuent de fonctionner normalement.
         */

        if (
                super.mouseClicked(
                        mouseX,
                        mouseY,
                        button
                )
        ) {

            return true;
        }

        ClanLayout layout =
                createLayout();

        ScreenRectangle content =
                layout.content();

        return currentTab.mouseClicked(
                mouseX,
                mouseY,
                button,
                clanId,
                content.left(),
                content.top(),
                content.width(),
                content.height()
        );
    }

    /*
     * =========================================================
     * MOLETTE
     * =========================================================
     */

    @Override
    public boolean mouseScrolled(
            double mouseX,
            double mouseY,
            double delta
    ) {

        ClanLayout layout =
                createLayout();

        ScreenRectangle content =
                layout.content();

        if (
                currentTab.mouseScrolled(
                        mouseX,
                        mouseY,
                        delta,
                        clanId,
                        content.left(),
                        content.top(),
                        content.width(),
                        content.height()
                )
        ) {
            return true;
        }



        return super.mouseScrolled(
                mouseX,
                mouseY,
                delta
        );
    }

    /*
     * =========================================================
     * BOUTONS SIDEBAR
     * =========================================================
     */

    private void addTabButton(
            Component label,
            Item icon,
            int x,
            int y,
            int width,
            int height,
            ClanTab tab
    ) {

        this.addRenderableWidget(
                new ClanSidebarButton(
                        x,
                        y,
                        width,
                        height,
                        label,
                        new ItemStack(icon),
                        theme,
                        () ->
                                currentTab == tab,
                        () ->
                                currentTab = tab
                )
        );
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}