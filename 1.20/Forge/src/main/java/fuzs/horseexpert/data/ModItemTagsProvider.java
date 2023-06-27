package fuzs.horseexpert.data;

import fuzs.horseexpert.init.ForgeModRegistry;
import fuzs.horseexpert.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends AbstractTagProvider.Items {

    public ModItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, ExistingFileHelper fileHelper) {
        super(packOutput, lookupProvider, modId, fileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ForgeModRegistry.CURIOS_HEAD_TAG).add(ModRegistry.MONOCLE_ITEM.get());
        this.tag(ForgeModRegistry.TRINKETS_HEAD_FACE_TAG).add(ModRegistry.MONOCLE_ITEM.get());
    }
}
