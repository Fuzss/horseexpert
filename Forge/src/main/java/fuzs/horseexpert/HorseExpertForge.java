package fuzs.horseexpert;

import fuzs.horseexpert.data.ModItemTagsProvider;
import fuzs.horseexpert.data.ModLanguageProvider;
import fuzs.horseexpert.data.ModModelProvider;
import fuzs.horseexpert.data.ModRecipeProvider;
import fuzs.horseexpert.handler.CuriosCapabilityHandler;
import fuzs.horseexpert.init.ForgeModRegistry;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.util.concurrent.CompletableFuture;

@Mod(HorseExpert.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class HorseExpertForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ModConstructor.construct(HorseExpert.MOD_ID, HorseExpert::new);
        ForgeModRegistry.touch();
        registerHandlers();
    }

    private static void registerHandlers() {
        MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, CuriosCapabilityHandler::onAttachCapabilities);
    }

    @SubscribeEvent
    public static void onInterModEnqueue(final InterModEnqueueEvent evt) {
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().build());
    }

    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent evt) {
        final DataGenerator dataGenerator = evt.getGenerator();
        final PackOutput packOutput = dataGenerator.getPackOutput();
        final CompletableFuture<HolderLookup.Provider> lookupProvider = evt.getLookupProvider();
        final ExistingFileHelper fileHelper = evt.getExistingFileHelper();
        dataGenerator.addProvider(true, new ModRecipeProvider(packOutput));
        dataGenerator.addProvider(true, new ModLanguageProvider(packOutput, HorseExpert.MOD_ID));
        dataGenerator.addProvider(true, new ModItemTagsProvider(packOutput, lookupProvider, HorseExpert.MOD_ID, fileHelper));
        dataGenerator.addProvider(true, new ModModelProvider(packOutput, HorseExpert.MOD_ID, fileHelper));
    }
}
