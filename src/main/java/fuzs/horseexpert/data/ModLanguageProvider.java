package fuzs.horseexpert.data;

import fuzs.horseexpert.registry.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(DataGenerator gen, String modid) {
        super(gen, modid, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add(ModRegistry.MONOCLE_ITEM.get(), "Monocle");
        this.add("horse.tooltip.min", "Min: %s");
        this.add("horse.tooltip.max", "Max: %s");
    }
}
