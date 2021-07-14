package com.clockworkcaracal.betterbees.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public final class ConfigHolder {

	public static final ForgeConfigSpec CONFIG_SPEC;
	static final BuildConfig CONFIG;
	
	static {
		final Pair<BuildConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(BuildConfig::new);
		CONFIG = specPair.getLeft();
		CONFIG_SPEC = specPair.getRight();
	}
}