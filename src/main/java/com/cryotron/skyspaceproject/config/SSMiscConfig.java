package com.cryotron.skyspaceproject.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class SSMiscConfig {
	public static final ForgeConfigSpec GENERAL_SPEC;

	public static ForgeConfigSpec.DoubleValue locateMaxTime;

	static {
		ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
		setupConfig(configBuilder);
		GENERAL_SPEC = configBuilder.build();
	}

	private static void setupConfig(ForgeConfigSpec.Builder builder) {
		locateMaxTime = builder
				.comment("\n How long before giving up should locate command, explorer maps, and other locating stuff",
						" do when locating a Skyspace structure. This is in seconds.")
				.translation("repurposedstructures.locatemaxtime")
				.defineInRange("locateMaxTime", 30D, 1D, 1000000D);
	}
}