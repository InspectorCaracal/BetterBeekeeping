package com.clockworkcaracal.betterbees.register;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import com.clockworkcaracal.betterbees.BetterBeekeeping;
import com.clockworkcaracal.betterbees.blocks.BeeSmokerBlock;
import com.clockworkcaracal.betterbees.blocks.ModBeehiveBlock;

public final class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BetterBeekeeping.MOD_ID);

    public static final RegistryObject<Block> BEE_SMOKER_BLOCK = BLOCKS.register("bee_smoker_block", () -> new BeeSmokerBlock(Block.Properties.of(Material.METAL).strength(3.5F).sound(SoundType.METAL)));

    // new Beehive blocks
    public static final RegistryObject<Block> OAK_BEEHIVE = BLOCKS.register("oak_beehive", () -> new ModBeehiveBlock(Block.Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> SPRUCE_BEEHIVE = BLOCKS.register("spruce_beehive", () -> new ModBeehiveBlock(Block.Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> BIRCH_BEEHIVE = BLOCKS.register("birch_beehive", () -> new ModBeehiveBlock(Block.Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DARK_OAK_BEEHIVE = BLOCKS.register("dark_oak_beehive", () -> new ModBeehiveBlock(Block.Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> JUNGLE_BEEHIVE = BLOCKS.register("jungle_beehive", () -> new ModBeehiveBlock(Block.Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> ACACIA_BEEHIVE = BLOCKS.register("acacia_beehive", () -> new ModBeehiveBlock(Block.Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> CRIMSON_BEEHIVE = BLOCKS.register("crimson_beehive", () -> new ModBeehiveBlock(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> WARPED_BEEHIVE = BLOCKS.register("warped_beehive", () -> new ModBeehiveBlock(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> MANGROVE_BEEHIVE = BLOCKS.register("mangrove_beehive", () -> new ModBeehiveBlock(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

}