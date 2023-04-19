package com.clockworkcaracal.betterbees.register;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.food.Foods;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import com.clockworkcaracal.betterbees.BetterBeekeeping;
import com.clockworkcaracal.betterbees.items.ModHoneyBottleItem;

public final class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BetterBeekeeping.MOD_ID);

    //Items
    public static final RegistryObject<Item> SPICY_HONEY_BOTTLE = ITEMS.register("spicy_honey_bottle", () -> new ModHoneyBottleItem(MobEffects.MOVEMENT_SLOWDOWN, 0, (new Item.Properties()).craftRemainder(Items.GLASS_BOTTLE).food(Foods.HONEY_BOTTLE).tab(CreativeModeTab.TAB_FOOD).stacksTo(16)));
    public static final RegistryObject<Item> WARM_HONEY_BOTTLE = ITEMS.register("warm_honey_bottle", () -> new ModHoneyBottleItem(MobEffects.WEAKNESS, 0, (new Item.Properties()).craftRemainder(Items.GLASS_BOTTLE).food(Foods.HONEY_BOTTLE).tab(CreativeModeTab.TAB_FOOD).stacksTo(16)));
    public static final RegistryObject<Item> BRIGHT_HONEY_BOTTLE = ITEMS.register("bright_honey_bottle", () -> new ModHoneyBottleItem(MobEffects.BLINDNESS, 0, (new Item.Properties()).craftRemainder(Items.GLASS_BOTTLE).food(Foods.HONEY_BOTTLE).tab(CreativeModeTab.TAB_FOOD).stacksTo(16)));
    public static final RegistryObject<Item> LIVELY_HONEY_BOTTLE = ITEMS.register("lively_honey_bottle", () -> new ModHoneyBottleItem(MobEffects.REGENERATION, 4, (new Item.Properties()).craftRemainder(Items.GLASS_BOTTLE).food(Foods.HONEY_BOTTLE).tab(CreativeModeTab.TAB_FOOD).stacksTo(16)));
    public static final RegistryObject<Item> COOL_HONEY_BOTTLE = ITEMS.register("cool_honey_bottle", () -> new ModHoneyBottleItem(MobEffects.HUNGER, 0, (new Item.Properties()).craftRemainder(Items.GLASS_BOTTLE).food(Foods.HONEY_BOTTLE).tab(CreativeModeTab.TAB_FOOD).stacksTo(16)));
    public static final RegistryObject<Item> STRANGE_HONEY_BOTTLE = ITEMS.register("strange_honey_bottle", () -> new ModHoneyBottleItem(MobEffects.WITHER, 0, (new Item.Properties()).craftRemainder(Items.GLASS_BOTTLE).food(Foods.HONEY_BOTTLE).tab(CreativeModeTab.TAB_FOOD).stacksTo(16)));
    
    //ItemBlocks
    public static final RegistryObject<Item> BEE_SMOKER_ITEMBLOCK = ITEMS.register("bee_smoker_block", () -> new BlockItem(ModBlocks.BEE_SMOKER_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<Item> OAK_BEEHIVE_ITEM = ITEMS.register("oak_beehive", () -> new BlockItem(ModBlocks.OAK_BEEHIVE.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<Item> SPRUCE_BEEHIVE_ITEM = ITEMS.register("spruce_beehive", () -> new BlockItem(ModBlocks.SPRUCE_BEEHIVE.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<Item> ACACIA_BEEHIVE_ITEM = ITEMS.register("acacia_beehive", () -> new BlockItem(ModBlocks.ACACIA_BEEHIVE.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<Item> BIRCH_BEEHIVE_ITEM = ITEMS.register("birch_beehive", () -> new BlockItem(ModBlocks.BIRCH_BEEHIVE.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<Item> DARK_OAK_BEEHIVE_ITEM = ITEMS.register("dark_oak_beehive", () -> new BlockItem(ModBlocks.DARK_OAK_BEEHIVE.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<Item> JUNGLE_BEEHIVE_ITEM = ITEMS.register("jungle_beehive", () -> new BlockItem(ModBlocks.JUNGLE_BEEHIVE.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<Item> CRIMSON_BEEHIVE_ITEM = ITEMS.register("crimson_beehive", () -> new BlockItem(ModBlocks.CRIMSON_BEEHIVE.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<Item> WARPED_BEEHIVE_ITEM = ITEMS.register("warped_beehive", () -> new BlockItem(ModBlocks.WARPED_BEEHIVE.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<Item> MANGROVE_BEEHIVE_ITEM = ITEMS.register("mangrove_beehive", () -> new BlockItem(ModBlocks.MANGROVE_BEEHIVE.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));


}