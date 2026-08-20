package com.magius.world.mod.library.books;

import com.magius.world.mod.MagiusWorldMod;
import com.magius.world.mod.library.BookCategory;
import com.magius.world.mod.library.BookData;
import com.magius.world.mod.library.BookRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public final class DragonmaidBooks {

    public static final ResourceLocation ORIGINS_ID =
            ResourceLocation.fromNamespaceAndPath(MagiusWorldMod.MOD_ID, "dragonmaid_origins");

    private DragonmaidBooks() {
    }

    public static void register() {

        BookRegistry.register(

                new BookData(
                        ORIGINS_ID,
                        BookCategory.DRAGONMAID,
                        Component.translatable(
                                "book.magiusworldmod.dragonmaid.origins.title"
                        ),
                        List.of(
                                Component.translatable("book.magiusworldmod.dragonmaid.origins.page1"),
                                Component.translatable("book.magiusworldmod.dragonmaid.origins.page2"),
                                Component.translatable("book.magiusworldmod.dragonmaid.origins.page3"),
                                Component.translatable("book.magiusworldmod.dragonmaid.origins.page4"),
                                Component.translatable("book.magiusworldmod.dragonmaid.origins.page5"),
                                Component.translatable("book.magiusworldmod.dragonmaid.origins.page6")
                        )
                )

        );

    }

}