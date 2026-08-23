package com.magius.world.mod.clan.client.screen;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.manager.ClanManager;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.quest.swordsoul.SwordsoulMoYeQuest;
import com.magius.world.mod.network.ModMessages;
import com.magius.world.mod.network.packet.C2SClaimSwordsoulMoYeQuestRewardPacket;
import com.magius.world.mod.network.packet.C2SJoinSwordsoulClanPacket;
import com.magius.world.mod.network.packet.C2SStartSwordsoulMoYeQuestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SwordsoulMoYeDialogueScreen extends Screen {

    private static final ResourceLocation FIRST_QUEST_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul_masterless_sword"
            );

    private static final ResourceLocation SWORDSOUL_ID =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul"
            );

    private QuestStatus firstQuestStatus =
            QuestStatus.NOT_STARTED;

    private QuestStatus moYeQuestStatus =
            QuestStatus.NOT_STARTED;

    private boolean swordsoulActive = false;

    public SwordsoulMoYeDialogueScreen() {
        super(Component.literal("Mo Ye"));
    }

    @Override
    protected void init() {

        int centerX = width / 2;
        int centerY = height / 2;

        Minecraft minecraft =
                Minecraft.getInstance();

        if (minecraft.player != null) {

            QuestManager.get(minecraft.player)
                    .ifPresent(data -> {

                        firstQuestStatus =
                                QuestManager.getStatus(
                                        data,
                                        FIRST_QUEST_ID
                                );

                        moYeQuestStatus =
                                QuestManager.getStatus(
                                        data,
                                        SwordsoulMoYeQuest.ID
                                );
                    });

            ClanManager.get(minecraft.player)
                    .ifPresent(clanData ->
                            swordsoulActive =
                                    SWORDSOUL_ID.equals(
                                            clanData.getActiveClanId()
                                    )
                    );
        }

        if (firstQuestStatus != QuestStatus.REWARDED) {

            addCloseButton(
                    centerX,
                    centerY,
                    "Je reviendrai"
            );

            return;
        }

        switch (moYeQuestStatus) {

            case NOT_STARTED ->
                    addRenderableWidget(
                            Button.builder(
                                            Component.literal(
                                                    "Accepter la quête"
                                            ),
                                            button -> {

                                                ModMessages.sendToServer(
                                                        new C2SStartSwordsoulMoYeQuestPacket()
                                                );

                                                onClose();
                                            }
                                    )
                                    .bounds(
                                            centerX - 100,
                                            centerY + 45,
                                            200,
                                            20
                                    )
                                    .build()
                    );

            case IN_PROGRESS ->
                    addCloseButton(
                            centerX,
                            centerY,
                            "Je forgerai cette lame"
                    );

            case COMPLETED -> {

                if (!swordsoulActive) {

                    addRenderableWidget(
                            Button.builder(
                                            Component.literal(
                                                    "Activer le clan Swordsoul"
                                            ),
                                            button -> {

                                                ModMessages.sendToServer(
                                                        new C2SJoinSwordsoulClanPacket()
                                                );

                                                onClose();
                                            }
                                    )
                                    .bounds(
                                            centerX - 100,
                                            centerY + 45,
                                            200,
                                            20
                                    )
                                    .build()
                    );

                } else {

                    addRenderableWidget(
                            Button.builder(
                                            Component.literal(
                                                    "Recevoir le sceau"
                                            ),
                                            button -> {

                                                ModMessages.sendToServer(
                                                        new C2SClaimSwordsoulMoYeQuestRewardPacket()
                                                );

                                                onClose();
                                            }
                                    )
                                    .bounds(
                                            centerX - 100,
                                            centerY + 45,
                                            200,
                                            20
                                    )
                                    .build()
                    );
                }
            }

            case REWARDED ->
                    addCloseButton(
                            centerX,
                            centerY,
                            "À bientôt, Mo Ye"
                    );
        }
    }

    private void addCloseButton(
            int centerX,
            int centerY,
            String label
    ) {
        addRenderableWidget(
                Button.builder(
                                Component.literal(label),
                                button -> onClose()
                        )
                        .bounds(
                                centerX - 100,
                                centerY + 45,
                                200,
                                20
                        )
                        .build()
        );
    }

    private Component getDialogue() {

        if (firstQuestStatus != QuestStatus.REWARDED) {
            return Component.literal(
                    "« Cette lame ne répond pas encore à ton âme. "
                            + "Retrouve d'abord l'Épée sans maître "
                            + "et accomplis la volonté de l'Émissaire. »"
            );
        }

        return switch (moYeQuestStatus) {

            case NOT_STARTED ->
                    Component.literal(
                            "« Tu as retrouvé la lame, mais elle demeure brisée. "
                                    + "Porte-la jusqu'à la Forge de synchronisation. "
                                    + "Un jeton et le Sceau de l'Émergence "
                                    + "révéleront sa véritable forme. »"
                    );

            case IN_PROGRESS ->
                    Component.literal(
                            "« La forge ne répond ni à la force ni à la hâte. "
                                    + "Présente la lame, le jeton correspondant "
                                    + "et le Sceau de l'Émergence. »"
                    );

            case COMPLETED ->
                    Component.literal(
                            "« La lame a reconnu ton esprit. "
                                    + "Reçois maintenant l'un des Sept Sceaux "
                                    + "et choisis la voie qui guidera son pouvoir. »"
                    );

            case REWARDED ->
                    Component.literal(
                            "« Ton attribut n'est pas une limite, "
                                    + "mais la première voie de ta maîtrise. "
                                    + "Étudie les Sept Voies spirituelles. »"
                    );
        };
    }

    @Override
    public void render(
            GuiGraphics guiGraphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {
        Font font =
                Minecraft.getInstance().font;

        guiGraphics.fill(
                0,
                0,
                width,
                height,
                0x99000000
        );

        int panelWidth = 280;
        int panelHeight = 170;

        int x = (width - panelWidth) / 2;
        int y = (height - panelHeight) / 2;

        guiGraphics.fill(
                x + 4,
                y + 4,
                x + panelWidth + 4,
                y + panelHeight + 4,
                0x88000000
        );

        guiGraphics.fill(
                x,
                y,
                x + panelWidth,
                y + panelHeight,
                0xFF02080D
        );

        guiGraphics.fill(
                x + 2,
                y + 2,
                x + panelWidth - 2,
                y + panelHeight - 2,
                0xFF8DD9F2
        );

        guiGraphics.fill(
                x + 4,
                y + 4,
                x + panelWidth - 4,
                y + panelHeight - 4,
                0xFF315C88
        );

        guiGraphics.fill(
                x + 6,
                y + 6,
                x + panelWidth - 6,
                y + panelHeight - 6,
                0xFF071521
        );

        guiGraphics.drawCenteredString(
                font,
                Component.literal("Mo Ye"),
                width / 2,
                y + 15,
                0xFF8DD9F2
        );

        guiGraphics.fill(
                x + 15,
                y + 30,
                x + panelWidth - 15,
                y + 31,
                0xFF315C88
        );

        var lines =
                font.split(
                        getDialogue(),
                        panelWidth - 30
                );

        int textY = y + 43;

        for (int i = 0; i < lines.size(); i++) {
            guiGraphics.drawString(
                    font,
                    lines.get(i),
                    x + 15,
                    textY + i * 11,
                    0xFFDCECF2,
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

    @Override
    public void onClose() {
        if (minecraft != null) {
            minecraft.setScreen(null);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
