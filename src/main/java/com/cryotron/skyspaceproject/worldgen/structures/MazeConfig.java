package com.cryotron.skyspaceproject.worldgen.structures;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.setup.deferredregistries.RegisteredStructures;
import com.cryotron.skyspaceproject.setup.deferredregistries.SkyspaceRegistration;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.PlainVillagePools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraftforge.common.ForgeConfigSpec;

public class MazeConfig {
    /**
     * Static instance of our structure so we can reference it and add it to biomes easily.
     */

	
//    public static ConfiguredStructureFeature<?, ?> CONFIGURED_KYROSIAN_MAZE = SSStructures.KYROSIAN_MAZE.get()
//            .configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
    // Dummy JigsawConfiguration values for now. We will modify the pool at runtime since we cannot get json pool files here at mod init.
    // You can create and register your pools in code, pass in the code create pool here, and delete both newConfig and newContext in RunDownHouseStructure's createPiecesGenerator.
    // Note: JigsawConfiguration only takes 0 - 7 size so that's another reason why we are going to bypass that "codec" by changing size at runtime to get higher sizes.

    /**
     * Registers the configured structure which is what gets added to the biomes.
     * Noticed we are not using a forge registry because there is none for configured structures.
     *
     * We can register configured structures at any time before a world is clicked on and made.
     * But the best time to register configured features by code is honestly to do it in FMLCommonSetupEvent.
     */
//    public static void registerConfiguredStructures() {
//        Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
//        Registry.register(registry, new ResourceLocation(Skyspace.ID, "configured_kyrosian_maze"), CONFIGURED_KYROSIAN_MAZE);
//    }
	
	public static final ForgeConfigSpec GENERAL_SPEC;

	public static ForgeConfigSpec.IntValue chunkDistance;

	static {
		ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
		setupConfig(configBuilder);
		GENERAL_SPEC = configBuilder.build();
	}

	private static void setupConfig(ForgeConfigSpec.Builder builder) {
		chunkDistance = builder
				.comment("\n Average distance between spawn attempts for Maze Piecess.",
						" 1 for spawning in most chunks and 10001 for none.")
				.translation("repurposedstructures.citiesoverworldaveragechunkdistance")
				.defineInRange("citiesOverworldAverageChunkDistance", 180, 1, 1);
	}
}
