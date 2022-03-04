package fuzs.horseexpert.config;

import fuzs.puzzleslib.config.AbstractConfig;
import fuzs.puzzleslib.config.annotation.Config;
import fuzs.puzzleslib.config.serialization.EntryCollectionBuilder;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;

public class ServerConfig extends AbstractConfig {
    @Config(description = "Chance a mob will drop a common loot pouch.")
    @Config.DoubleRange(min = 0.0, max = 1.0)
    public double commonDropChance = 0.02;
    @Config(description = "Chance a mob will drop an uncommon loot pouch.")
    @Config.DoubleRange(min = 0.0, max = 1.0)
    public double uncommonDropChance = 0.01;
    @Config(description = "Chance a mob will drop a rare loot pouch.")
    @Config.DoubleRange(min = 0.0, max = 1.0)
    public double rareDropChance = 0.005;
    @Config(description = {"A certain type of mob will drop loot pouches less often the more are killed in a row.", "Intended to easily collect pouches through mob farms, although farms with multiple mob types will still yield them in usual quantities."})
    public boolean decreasingScales = true;
    @Config(name = "mob_blacklist", description = {"Mobs that should not be able to drop loot pouches despite being allowed by default.", EntryCollectionBuilder.CONFIG_DESCRIPTION})
    List<String> mobBlacklistRaw = EntryCollectionBuilder.getKeyList(ForgeRegistries.ENTITIES, EntityType.ENDER_DRAGON, EntityType.WITHER);
    @Config(name = "mob_whitelist", description = {"Mobs that should be able to drop loot pouches despite being disabled by default.", EntryCollectionBuilder.CONFIG_DESCRIPTION})
    List<String> mobWhitelistRaw = EntryCollectionBuilder.getKeyList(ForgeRegistries.ENTITIES);

    public Set<EntityType<?>> mobBlacklist;
    public Set<EntityType<?>> mobWhitelist;

    public ServerConfig() {
        super("");
    }

    @Override
    protected void afterConfigReload() {
        this.mobBlacklist = EntryCollectionBuilder.of(ForgeRegistries.ENTITIES).buildSet(this.mobBlacklistRaw);
        this.mobWhitelist = EntryCollectionBuilder.of(ForgeRegistries.ENTITIES).buildSet(this.mobWhitelistRaw);
    }
}
