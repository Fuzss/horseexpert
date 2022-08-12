package fuzs.horseexpert;

import fuzs.horseexpert.config.ClientConfig;
import fuzs.horseexpert.data.ModItemTagsProvider;
import fuzs.horseexpert.data.ModLanguageProvider;
import fuzs.horseexpert.data.ModRecipeProvider;
import fuzs.horseexpert.registry.ModRegistry;
import fuzs.puzzleslib.config.AbstractConfig;
import fuzs.puzzleslib.config.ConfigHolder;
import fuzs.puzzleslib.config.ConfigHolderImpl;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.ICurio;

@Mod(HorseExpert.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class HorseExpert {
    public static final String MOD_ID = "horseexpert";
    public static final String MOD_NAME = "Horse Expert";
    public static final Logger LOGGER = LogManager.getLogger(HorseExpert.MOD_NAME);

    @SuppressWarnings("Convert2MethodRef")
    public static final ConfigHolder<ClientConfig, AbstractConfig> CONFIG = ConfigHolder.client(() -> new ClientConfig());

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ((ConfigHolderImpl<?, ?>) CONFIG).addConfigs(MOD_ID);
        ModRegistry.touch();
        MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, HorseExpert::onAttachCapabilities);
    }

    @SubscribeEvent
    public static void onInterModEnqueue(final InterModEnqueueEvent evt) {
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().build());
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

    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent evt) {
        DataGenerator generator = evt.getGenerator();
        final ExistingFileHelper existingFileHelper = evt.getExistingFileHelper();
        generator.addProvider(new ModRecipeProvider(generator));
        generator.addProvider(new ModLanguageProvider(generator, MOD_ID));
        generator.addProvider(new ModItemTagsProvider(generator, MOD_ID, existingFileHelper));
    }
}
