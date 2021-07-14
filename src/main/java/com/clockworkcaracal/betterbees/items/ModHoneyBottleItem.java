package com.clockworkcaracal.betterbees.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class ModHoneyBottleItem extends HoneyBottleItem {
	private final Effect honeyEffect;
	private final int effectDuration;

	public ModHoneyBottleItem(Effect effect, int duration, Item.Properties props) {
		super(props);
		this.honeyEffect = effect;
		this.effectDuration = duration;
	}
	
	public ItemStack finishUsingItem(ItemStack item, World world, LivingEntity entity) {
		ItemStack usedStack = super.finishUsingItem(item, world, entity);

		if (!world.isClientSide) {
			if (this.effectDuration > 0) {
				entity.addEffect(new EffectInstance(this.honeyEffect,this.effectDuration));
			} else {
				entity.removeEffect(this.honeyEffect);
			}
		}
		
		return usedStack;
	}


}
