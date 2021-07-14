package com.clockworkcaracal.betterbees.config;

import net.minecraftforge.fml.config.ModConfig;

public final class BetterBeeConfig {

	public static boolean hibernate;
	public static boolean flavoredHoney;

	public static void bakeConfig(final ModConfig config) {
		hibernate = ConfigHolder.CONFIG.hibernate.get();
		flavoredHoney = ConfigHolder.CONFIG.flavoredHoney.get();
	}

}
