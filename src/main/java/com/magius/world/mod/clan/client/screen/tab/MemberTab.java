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

    private static final ResourceLocation SWORDSOUL_QUEST_1 =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul_masterless_sword"
            );

    private static final ResourceLocation SWORDSOUL_QUEST_2 =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul_mo_ye_synchronization"
            );

    private static final ResourceLocation SWORDSOUL_QUEST_3 =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "swordsoul_taia_spiritual_path"
            );

    private static final ResourceLocation SWORDSOUL_MEMBER_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(
                    MagiusWorldMod.MOD_ID,
                    "textures/entity/swordsoul/emissary.png"
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
    private static final List<MemberEntry> SWORDSOUL_MEMBERS =
            List.of(
                    new MemberEntry(
                            "Émissaire Swordsoul",
                            "Gardien du Sanctuaire",
                            "Guide les nouveaux disciples vers l'Épée sans maître.",
                            1
                    ),

                    new MemberEntry(
                            "Mo Ye",
                            "Maîtresse de la Synchronisation",
                            "Enseigne l'union entre la lame, le jeton et l'esprit.",
                            2
                    ),

                    new MemberEntry(
                            "Taia",
                            "Maître des Sept Voies",
                            "Enseigne l'infusion des attributs dans les lames spirituelles.",
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
// CLAN ET MEMBRES À AFFICHER
// =====================================================

        List<MemberEntry> members;
        ResourceLocation quest1Id;
        ResourceLocation quest2Id;
        ResourceLocation quest3Id;
        ResourceLocation memberTexture;
        boolean playerSkinTexture;

        switch (clanId.getPath()) {
            case "dragonmaid" -> {
                members = DRAGONMAID_MEMBERS;
                quest1Id = QUEST_1;
                quest2Id = QUEST_2;
                quest3Id = QUEST_3;
                memberTexture = DRAGONMAID_MEMBER_TEXTURE;
                playerSkinTexture = false;
            }

            case "swordsoul" -> {
                members = SWORDSOUL_MEMBERS;
                quest1Id = SWORDSOUL_QUEST_1;
                quest2Id = SWORDSOUL_QUEST_2;
                quest3Id = SWORDSOUL_QUEST_3;
                memberTexture = SWORDSOUL_MEMBER_TEXTURE;
                playerSkinTexture = true;
            }

            default -> {
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
                                quest1Id
                        );

                quest2Status =
                        QuestManager.getStatus(
                                questData,
                                quest2Id
                        );

                quest3Status =
                        QuestManager.getStatus(
                                questData,
                                quest3Id
                        );
            }
        }

        int memberY =
                y + 32;

        for (
                MemberEntry member
                : members

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
                    memberTexture,
                    playerSkinTexture,
                    x + 12,
                    memberY,
                    width - 24
            );

            memberY += 49;
        }
    }


    private void renderMember(
            GuiGraphics guiGraphics,
            ClanTheme theme,
            MemberEntry member,
            boolean discovered,
            ResourceLocation memberTexture,
            boolean playerSkinTexture,
            int x,
            int y,
            int width
    ) {

        var font =
                Minecraft.getInstance().font;


        // =====================================================
        // FOND DE LA FICHE
        // =====================================================

        guiGraphics.fill(
                x,
                y,
                x + width,
                y + 47,
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
        if (playerSkinTexture) {
            /*
             * Visage 8 × 8 extrait de la texture
             * d'entité 64 × 64, agrandi en 32 × 32.
             */
            guiGraphics.blit(
                    memberTexture,
                    x + 8,
                    y + 8,
                    32,
                    32,
                    8.0F,
                    8.0F,
                    8,
                    8,
                    64,
                    64
            );

            /*
             * Seconde couche de la tête :
             * cheveux, capuche ou accessoires.
             */
            guiGraphics.blit(
                    memberTexture,
                    x + 8,
                    y + 8,
                    32,
                    32,
                    40.0F,
                    8.0F,
                    8,
                    8,
                    64,
                    64
            );
        } else {
            /*
             * Portrait GUI Dragonmaid déjà préparé
             * directement au format 32 × 32.
             */
            guiGraphics.blit(
                    memberTexture,
                    x + 8,
                    y + 8,
                    0,
                    0,
                    32,
                    32,
                    32,
                    32
            );
        }

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
                y + 29;

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

            descriptionY += 9;
        }
    }
}