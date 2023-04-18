package com.clockworkcaracal.betterbees.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoneyBottleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.Level;

public class ModHoneyBottleItem extends HoneyBottleItem {
	private final MobEffect honeyEffect;
	private final int effectDuration;

	public ModHoneyBottleItem(MobEffect effect, int duration, Item.Properties props) {
		super(props);
		this.honeyEffect = effect;
		this.effectDuration = duration;
	}
	
	public ItemStack finishUsingItem(ItemStack item, Level world, LivingEntity entity) {
		ItemStack usedStack = super.finishUsingItem(item, world, entity);

		if (!world.isClientSide) {
			if (this.effectDuration > 0) {
				entity.addEffect(new MobEffectInstance(this.honeyEffect,this.effectDuration));
			} else {
				entity.removeEffect(this.honeyEffect);
			}
		}
		
		return usedStack;
	}


}
