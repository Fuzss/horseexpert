package fuzs.horseexpert.data;

import fuzs.horseexpert.init.ForgeModRegistry;
import fuzs.horseexpert.init.ModRegistry;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagsProvider extends TagsProvider<Item> {

    public ModItemTagsProvider(DataGenerator p_126546_, String modId, ExistingFileHelper fileHelperIn) {
        super(p_126546_, Registry.ITEM, modId, fileHelperIn);
    }

    @Override
    protected void addTags() {
        this.tag(ForgeModRegistry.CURIOS_HEAD_TAG).add(ModRegistry.MONOCLE_ITEM.get()).replace(false);
        this.tag(ForgeModRegistry.TRINKETS_HEAD_FACE_TAG).add(ModRegistry.MONOCLE_ITEM.get()).replace(false);
    }
}
