package fuzs.horseexpert;

import fuzs.horseexpert.data.ModItemModelProvider;
import fuzs.horseexpert.data.ModItemTagsProvider;
import fuzs.horseexpert.data.ModLanguageProvider;
import fuzs.horseexpert.data.ModRecipeProvider;
import fuzs.horseexpert.init.ForgeModRegistry;
import fuzs.horseexpert.init.ModRegistry;
import fuzs.puzzleslib.core.CoreServices;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.ICurio;

@Mod(HorseExpert.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class HorseExpertForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        CoreServices.FACTORIES.modConstructor(HorseExpert.MOD_ID).accept(new HorseExpert());
        ForgeModRegistry.touch();
        MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, HorseExpertForge::onAttachCapabilities);
    }

    @SubscribeEvent
    public static void onInterModEnqueue(final InterModEnqueueEvent evt) {
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().build());
    }

    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent evt) {
        DataGenerator generator = evt.getGenerator();
        final ExistingFileHelper existingFileHelper = evt.getExistingFileHelper();
        generator.addProvider(true, new ModRecipeProvider(generator));
        generator.addProvider(true, new ModLanguageProvider(generator, HorseExpert.MOD_ID));
        generator.addProvider(true, new ModItemTagsProvider(generator, HorseExpert.MOD_ID, existingFileHelper));
        generator.addProvider(true, new ModItemModelProvider(generator, HorseExpert.MOD_ID, existingFileHelper));
    }

    public static void onAttachCapabilities(final AttachCapabilitiesEvent<ItemStack> evt) {
        if (!evt.getObject().is(ModRegistry.MONOCLE_ITEM.get())) return;
        evt.addCapability(CuriosCapability.ID_ITEM, new ICapabilityProvider() {
            private final ICurio curio = new ICurio() {

                @Override
                public ItemStack getStack() {
                    return evt.getObject();
                }

                @Override
                public boolean canEquipFromUse(SlotContext ctx) {
                    return true;
                }
            };

            @NotNull
            @Override
            public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                return CuriosCapability.ITEM.orEmpty(cap, LazyOptional.of(() -> this.curio));
            }
        });
    }
}
