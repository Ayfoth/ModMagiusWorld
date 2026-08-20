package com.magius.world.mod.clan.client.screen.tab;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.clan.quest.api.QuestStatus;
import com.magius.world.mod.clan.quest.manager.QuestManager;
import com.magius.world.mod.clan.theme.ClanTheme;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class MemberTab implements ClanTab {

    private static final ResourceLocation QUEST_1 =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_first_contact"
            );

    private static final ResourceLocation QUEST_2 =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_unexpected_guest"
            );

    private static final ResourceLocation QUEST_3 =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "dragonmaid_forgotten_home"
            );
    private static final ResourceLocation DRAGONMAID_MEMBER_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(
                    "magiusworldmod",
                    "textures/gui/clan/dragonmaid_member.png"
            );

    private record MemberEntry(
            String name,
            String role,
            String description,
            int discoveryQuest
    ) {
    }

    private static final List<MemberEntry> DRAGONMAID_MEMBERS =
            List.of(

                    new MemberEntry(
                            "Émissaire Dragonmaid",
                            "Émissaire du Foyer",
                            "Guide les voyageurs vers l'ancien héritage Dragonmaid.",
                            1
                    ),

                    new MemberEntry(
                            "Nurse",
                            "Gardienne du Foyer",
                            "Veille sur les membres du clan et les ruines du village.",
                            2
                    ),

                    new MemberEntry(
                            "Tinkhec",
                            "Artisane du Foyer",
                            "Participe au réveil et à la reconstruction du Foyer.",
                            3
                    )
            );


    @Override
    public Component getTitle() {
        return Component.literal("Membres");
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

        var minecraft =
                Minecraft.getInstance();

        var font =
                minecraft.font;


        // =====================================================
        // TITRE
        // =====================================================

        guiGraphics.drawString(
                font,
                Component.literal(
                        "Membres du clan"
                ),
                x + 12,
                y + 10,
                theme.getTitleColor(),
                false
        );


        // =====================================================
        // DRAGONMAID
        // =====================================================

        if (!"dragonmaid".equals(
                clanId.getPath()
        )) {

            guiGraphics.drawString(
                    font,
                    Component.literal(
                            "Aucun membre connu."
                    ),
                    x + 12,
                    y + 32,
                    0xFFAAAAAA,
                    false
            );

            return;
        }


        // =====================================================
        // LISTE
        // =====================================================

        QuestStatus quest1Status =
                QuestStatus.NOT_STARTED;

        QuestStatus quest2Status =
                QuestStatus.NOT_STARTED;

        QuestStatus quest3Status =
                QuestStatus.NOT_STARTED;

        if (minecraft.player != null) {

            var questDataOptional =
                    QuestManager.get(
                            minecraft.player
                    );

            if (questDataOptional.isPresent()) {

                var questData =
                        questDataOptional.resolve().get();

                quest1Status =
                        QuestManager.getStatus(
                                questData,
                                QUEST_1
                        );

                quest2Status =
                        QuestManager.getStatus(
                                questData,
                                QUEST_2
                        );

                quest3Status =
                        QuestManager.getStatus(
                                questData,
                                QUEST_3
                        );
            }
        }

        int memberY =
                y + 32;

        for (
                MemberEntry member
                : DRAGONMAID_MEMBERS
        ) {
            boolean discovered =
                    switch (member.discoveryQuest()) {

                        case 1 ->
                                quest1Status
                                        != QuestStatus.NOT_STARTED;

                        case 2 ->
                                quest2Status
                                        != QuestStatus.NOT_STARTED;

                        case 3 ->
                                quest3Status
                                        != QuestStatus.NOT_STARTED;

                        default -> false;
                    };

            renderMember(
                    guiGraphics,
                    theme,
                    member,
                    discovered,
                    x + 12,
                    memberY,
                    width - 24
            );

            memberY += 55;
        }
    }


    private void renderMember(
            GuiGraphics guiGraphics,
            ClanTheme theme,
            MemberEntry member,
            boolean discovered,
            int x,
            int y,
            int width
    ){

        var font =
                Minecraft.getInstance().font;


        // =====================================================
        // FOND DE LA FICHE
        // =====================================================

        guiGraphics.fill(
                x,
                y,
                x + width,
                y + 52,
                0x66000000
        );

        if (!discovered) {

            guiGraphics.fill(
                    x + 5,
                    y + 5,
                    x + 42,
                    y + 42,
                    0xFF202020
            );

            guiGraphics.drawString(
                    font,
                    "?",
                    x + 21,
                    y + 19,
                    0xFF777777,
                    false
            );

            guiGraphics.drawString(
                    font,
                    Component.literal("???"),
                    x + 50,
                    y + 8,
                    0xFF777777,
                    false
            );

            guiGraphics.drawString(
                    font,
                    Component.literal("Membre inconnu"),
                    x + 50,
                    y + 22,
                    0xFF666666,
                    false
            );

            return;
        }


        // =====================================================
// PORTRAIT DU MEMBRE
// =====================================================

// Petit fond / cadre
        guiGraphics.fill(
                x + 5,
                y + 5,
                x + 42,
                y + 42,
                0xFF3A2028
        );

// Portrait 32x32
        guiGraphics.blit(
                DRAGONMAID_MEMBER_TEXTURE,
                x + 8,
                y + 8,
                0,
                0,
                32,
                32,
                32,
                32
        );


        // =====================================================
        // NOM
        // =====================================================

        guiGraphics.drawString(
                font,
                Component.literal(
                        member.name()
                ),
                x + 50,
                y + 6,
                theme.getTitleColor(),
                false
        );


        // =====================================================
        // RÔLE
        // =====================================================

        guiGraphics.drawString(
                font,
                Component.literal(
                        member.role()
                ),
                x + 50,
                y + 18,
                0xFFD6B36A,
                false
        );


        // =====================================================
        // DESCRIPTION
        // =====================================================

        var descriptionLines =
                font.split(
                        Component.literal(
                                member.description()
                        ),
                        width - 60
                );

        int descriptionY =
                y + 31;

        for (int i = 0;
             i < Math.min(2, descriptionLines.size());
             i++) {

            guiGraphics.drawString(
                    font,
                    descriptionLines.get(i),
                    x + 50,
                    descriptionY,
                    0xFFAAAAAA,
                    false
            );

            descriptionY += 10;
        }
    }
}