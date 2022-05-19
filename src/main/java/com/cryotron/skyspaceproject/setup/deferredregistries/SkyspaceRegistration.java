package com.cryotron.skyspaceproject.setup.deferredregistries;

import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.StatePredicate;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.monster.Zombie;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import static com.cryotron.skyspaceproject.Skyspace.ID;

import java.util.HashMap;
import java.util.Map;

import com.cryotron.skyspaceproject.Skyspace;
//import com.cryotron.skyspaceproject.biome.worlds.kyros.KyrosQuadrantAxis;
import com.cryotron.skyspaceproject.block.KyrosianEdgeBlock;
import com.cryotron.skyspaceproject.block.KyrosianGlassBlock;
import com.cryotron.skyspaceproject.block.KyrosianTileBlock;
import com.cryotron.skyspaceproject.block.stargate.KyrosianStargateBlock;
import com.cryotron.skyspaceproject.block.stargate.KyrosianStargateFrame;
import com.cryotron.skyspaceproject.entities.kyrosian_archon.KyrosianArchon;
import com.cryotron.skyspaceproject.entities.kyrosian_deacon.KyrosianDeacon;
import com.cryotron.skyspaceproject.entities.kyrosian_enforcer.KyrosianEnforcer;
import com.cryotron.skyspaceproject.entities.kyrosian_mutilator.KyrosianMutilator;
import com.cryotron.skyspaceproject.entities.synthesized_skeleton.SynthesizedSkeleton;
import com.cryotron.skyspaceproject.entities.synthesized_zombie.SynthesizedZombie;
import com.cryotron.skyspaceproject.items.FlintAndRune;
import com.cryotron.skyspaceproject.setup.SkyspaceSetup;
import com.cryotron.skyspaceproject.worldgen.structures.KyrosianMaze;

public class SkyspaceRegistration {
    
    public static void init() {
    	final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    	RegisteredParticles.PART.register(bus);
    	RegisteredBiomes.BIOMES.register(bus);
    	RegisteredAttributes.ATTRIBUTES.register(bus);
    	RegisteredEntities.ENTITIES.register(bus);
    	RegisteredSounds.SFX.register(bus);
    	RegisteredBlocks.BLOCKS.register(bus);
    	RegisteredItems.ITEMS.register(bus);
    	RegisteredStructures.STRUCTURES.register(bus);
    }
    
   // public static final RegistryObject<ParticleType<CustomisableParticleType.Data>> ENERGY_SHIELD_DAMAGE_INDICATOR_II = PART.register("energy_shield_damage_indicator_ii", () -> new CustomisableParticleType(true));    
    

//	BIOMES.register("kyros_quadrant_axis", () ->
//		new KyrosQuadrantAxis(
//				new Biome.Builder()
//						.category(Biome.Category.ICY)
//						.temperature(0)
//						.downfall(0.1F)
//						.precipitation(Biome.RainType.SNOW)
//		));//
	
//	public static final Music KYROS_MUSE = new Music(KYROS_MUSIC.get(), 20, 3000, true);
	
//    // Helmet Alternatives
//    public static final RegistryObject<Item> LEATHER_HELMET	= null;	// Str
//    public static final RegistryObject<Item> LEATHER_HOOD	= null;	// Dex
//    public static final RegistryObject<Item> LEATHER_CIRCLET	= null;	// Int
//    public static final RegistryObject<Item> LEATHER_BASCINET 	= null; //Str + Dex
//    public static final RegistryObject<Item> LEATHER_CROWN	= null; // Str + Int
//    public static final RegistryObject<Item> LEATHER_MASK 	= null; // Dex + Int
//    public static final RegistryObject<Item> CHAINMAIL_WARHELM	= null;	// Str
//    public static final RegistryObject<Item> CHAINMAIL_HOOD	= null;	// Dex
//    public static final RegistryObject<Item> CHAINMAIL_CIRCLET	= null;	// Int
//    public static final RegistryObject<Item> CHAINMAIL_BASCINET 	= null; //Str + Dex
//    public static final RegistryObject<Item> CHAINMAIL_CROWN	= null; // Str + Int
//    public static final RegistryObject<Item> CHAINMAIL_MASK 	= null; // Dex + Int
//    public static final RegistryObject<Item> IRON_WARHELM	= null;	// Str
//    public static final RegistryObject<Item> IRON_HOOD = null;	// Dex
//    public static final RegistryObject<Item> IRON_CIRCLET	= null;	// Int
//    public static final RegistryObject<Item> IRON_BASCINET 	= null; //Str + Dex
//    public static final RegistryObject<Item> IRON_CROWN	= null; // Str + Int
//    public static final RegistryObject<Item> IRON_MASK 	= null; // Dex + Int
//    public static final RegistryObject<Item> GOLDEN_WARHELM	= null;	// Str
//    public static final RegistryObject<Item> GOLDEN_HOOD	= null;	// Dex
//    public static final RegistryObject<Item> GOLDEN_CIRCLET	= null;	// Int
//    public static final RegistryObject<Item> GOLDEN_BASCINET 	= null; //Str + Dex
//    public static final RegistryObject<Item> GOLDEN_CROWN	= null; // Str + Int
//    public static final RegistryObject<Item> GOLDEN_MASK 	= null; // Dex + Int
//    public static final RegistryObject<Item> DIAMOND_WARHELM	= null;	// Str
//    public static final RegistryObject<Item> DIAMOND_HOOD	= null;	// Dex
//    public static final RegistryObject<Item> DIAMOND_CIRCLET	= null;	// Int
//    public static final RegistryObject<Item> DIAMOND_BASCINET 	= null; //Str + Dex
//    public static final RegistryObject<Item> DIAMOND_CROWN	= null; // Str + Int
//    public static final RegistryObject<Item> DIAMOND_MASK 	= null; // Dex + Int
//    public static final RegistryObject<Item> NETHERITE_WARHELM	= null;	// Str
//    public static final RegistryObject<Item> NETHERITE_HOOD	= null;	// Dex
//    public static final RegistryObject<Item> NETHERITE_CIRCLET	= null;	// Int
//    public static final RegistryObject<Item> NETHERITE_BASCINET 	= null; //Str + Dex
//    public static final RegistryObject<Item> NETHERITE_CROWN	= null; // Str + Int
//    public static final RegistryObject<Item> NETHERITE_MASK 	= null; // Dex + Int
//    
//    // Chestplate Alternatives
//    public static final RegistryObject<Item> LEATHER_PLATE	= null;	// Str
//    public static final RegistryObject<Item> LEATHER_GARB	= null;	// Dex
//    public static final RegistryObject<Item> LEATHER_REGALIA	= null;	// Int
//    public static final RegistryObject<Item> LEATHER_BRIGANDINE 	= null; //Str + Dex
//    public static final RegistryObject<Item> LEATHER_HAUBERK	= null; // Str + Int
//    public static final RegistryObject<Item> LEATHER_COAT 	= null; // Dex + Int
//    public static final RegistryObject<Item> CHAINMAIL_WARPLATE	= null;	// Str
//    public static final RegistryObject<Item> CHAINMAIL_GARB	= null;	// Dex
//    public static final RegistryObject<Item> CHAINMAIL_REGALIA	= null;	// Int
//    public static final RegistryObject<Item> CHAINMAIL_BRIGANDINE 	= null; //Str + Dex
//    public static final RegistryObject<Item> CHAINMAIL_HAUBERK	= null; // Str + Int
//    public static final RegistryObject<Item> CHAINMAIL_COAT 	= null; // Dex + Int
//    public static final RegistryObject<Item> IRON_WARPLATE	= null;	// Str
//    public static final RegistryObject<Item> IRON_GARB	= null;	// Dex
//    public static final RegistryObject<Item> IRON_REGALIA	= null;	// Int
//    public static final RegistryObject<Item> IRON_BRIGANDINE 	= null; //Str + Dex
//    public static final RegistryObject<Item> IRON_HAUBERK	= null; // Str + Int
//    public static final RegistryObject<Item> IRON_COAT 	= null; // Dex + Int
//    public static final RegistryObject<Item> GOLDEN_WARPLATE	= null;	// Str
//    public static final RegistryObject<Item> GOLDEN_GARB	= null;	// Dex
//    public static final RegistryObject<Item> GOLDEN_REGALIA	= null;	// Int
//    public static final RegistryObject<Item> GOLDEN_BRIGANDINE 	= null; //Str + Dex
//    public static final RegistryObject<Item> GOLDEN_HAUBERK	= null; // Str + Int
//    public static final RegistryObject<Item> GOLDEN_COAT 	= null; // Dex + Int
//    public static final RegistryObject<Item> DIAMOND_WARPLATE	= null;	// Str
//    public static final RegistryObject<Item> DIAMOND_GARB	= null;	// Dex
//    public static final RegistryObject<Item> DIAMOND_REGALIA	= null;	// Int
//    public static final RegistryObject<Item> DIAMOND_BRIGANDINE 	= null; //Str + Dex
//    public static final RegistryObject<Item> DIAMOND_HAUBERK	= null; // Str + Int
//    public static final RegistryObject<Item> DIAMOND_COAT 	= null; // Dex + Int
//    public static final RegistryObject<Item> NETHERITE_WARPLATE	= null;	// Str
//    public static final RegistryObject<Item> NETHERITE_GARB	= null;	// Dex
//    public static final RegistryObject<Item> NETHERITE_REGALIA	= null;	// Int
//    public static final RegistryObject<Item> NETHERITE_BRIGANDINE 	= null; //Str + Dex
//    public static final RegistryObject<Item> NETHERITE_HAUBERK	= null; // Str + Int
//    public static final RegistryObject<Item> NETHERITE_COAT 	= null; // Dex + Int
//    
//    // Leggings Alternatives
//    public static final RegistryObject<Item> LEATHER_PLATE	= null;	// Str
//    public static final RegistryObject<Item> LEATHER_GARB	= null;	// Dex
//    public static final RegistryObject<Item> LEATHER_REGALIA	= null;	// Int
//    public static final RegistryObject<Item> LEATHER_BRIGANDINE 	= null; //Str + Dex
//    public static final RegistryObject<Item> LEATHER_HAUBERK	= null; // Str + Int
//    public static final RegistryObject<Item> LEATHER_COAT 	= null; // Dex + Int
//    public static final RegistryObject<Item> CHAINMAIL_WARPLATE	= null;	// Str
//    public static final RegistryObject<Item> CHAINMAIL_GARB	= null;	// Dex
//    public static final RegistryObject<Item> CHAINMAIL_REGALIA	= null;	// Int
//    public static final RegistryObject<Item> CHAINMAIL_BRIGANDINE 	= null; //Str + Dex
//    public static final RegistryObject<Item> CHAINMAIL_HAUBERK	= null; // Str + Int
//    public static final RegistryObject<Item> CHAINMAIL_COAT 	= null; // Dex + Int
//    public static final RegistryObject<Item> IRON_WARPLATE	= null;	// Str
//    public static final RegistryObject<Item> IRON_GARB	= null;	// Dex
//    public static final RegistryObject<Item> IRON_REGALIA	= null;	// Int
//    public static final RegistryObject<Item> IRON_BRIGANDINE 	= null; //Str + Dex
//    public static final RegistryObject<Item> IRON_HAUBERK	= null; // Str + Int
//    public static final RegistryObject<Item> IRON_COAT 	= null; // Dex + Int
//    public static final RegistryObject<Item> GOLDEN_WARPLATE	= null;	// Str
//    public static final RegistryObject<Item> GOLDEN_GARB	= null;	// Dex
//    public static final RegistryObject<Item> GOLDEN_REGALIA	= null;	// Int
//    public static final RegistryObject<Item> GOLDEN_BRIGANDINE 	= null; //Str + Dex
//    public static final RegistryObject<Item> GOLDEN_HAUBERK	= null; // Str + Int
//    public static final RegistryObject<Item> GOLDEN_COAT 	= null; // Dex + Int
//    public static final RegistryObject<Item> DIAMOND_WARPLATE	= null;	// Str
//    public static final RegistryObject<Item> DIAMOND_GARB	= null;	// Dex
//    public static final RegistryObject<Item> DIAMOND_REGALIA	= null;	// Int
//    public static final RegistryObject<Item> DIAMOND_BRIGANDINE 	= null; //Str + Dex
//    public static final RegistryObject<Item> DIAMOND_HAUBERK	= null; // Str + Int
//    public static final RegistryObject<Item> DIAMOND_COAT 	= null; // Dex + Int
//    public static final RegistryObject<Item> NETHERITE_WARPLATE	= null;	// Str
//    public static final RegistryObject<Item> NETHERITE_GARB	= null;	// Dex
//    public static final RegistryObject<Item> NETHERITE_REGALIA	= null;	// Int
//    public static final RegistryObject<Item> NETHERITE_BRIGANDINE 	= null; //Str + Dex
//    public static final RegistryObject<Item> NETHERITE_HAUBERK	= null; // Str + Int
//    public static final RegistryObject<Item> NETHERITE_COAT 	= null; // Dex + Int
//    
//    // Boots Alternatives
//    public static final RegistryObject<Item> LEATHER_GREAVES	= null;	// Str
//    public static final RegistryObject<Item> LEATHER_STEALTHBOOTS	= null;	// Dex
//    public static final RegistryObject<Item> LEATHER_SHOES	= null;	// Int
//    public static final RegistryObject<Item> LEATHER_SABATONS 	= null; //Str + Dex
//    public static final RegistryObject<Item> LEATHER_CALIGAE	= null; // Str + Int
//    public static final RegistryObject<Item> LEATHER_COAT 	= null; // Dex + Int
//    public static final RegistryObject<Item> CHAINMAIL_GREAVES	= null;	// Str
//    public static final RegistryObject<Item> CHAINMAIL_STEALTHBOOTS	= null;	// Dex
//    public static final RegistryObject<Item> CHAINMAIL_SHOES	= null;	// Int
//    public static final RegistryObject<Item> CHAINMAIL_SABATONS 	= null; //Str + Dex
//    public static final RegistryObject<Item> CHAINMAIL_CALIGAE	= null; // Str + Int
//    public static final RegistryObject<Item> CHAINMAIL_COAT 	= null; // Dex + Int
//    public static final RegistryObject<Item> IRON_GREAVES	= null;	// Str
//    public static final RegistryObject<Item> IRON_STEALTHBOOTS	= null;	// Dex
//    public static final RegistryObject<Item> IRON_SHOES	= null;	// Int
//    public static final RegistryObject<Item> IRON_SABATONS 	= null; //Str + Dex
//    public static final RegistryObject<Item> IRON_CALIGAE	= null; // Str + Int
//    public static final RegistryObject<Item> IRON_COAT 	= null; // Dex + Int
//    public static final RegistryObject<Item> GOLDEN_GREAVES	= null;	// Str
//    public static final RegistryObject<Item> GOLDEN_STEALTHBOOTS	= null;	// Dex
//    public static final RegistryObject<Item> GOLDEN_SHOES	= null;	// Int
//    public static final RegistryObject<Item> GOLDEN_SABATONS 	= null; //Str + Dex
//    public static final RegistryObject<Item> GOLDEN_CALIGAE	= null; // Str + Int
//    public static final RegistryObject<Item> GOLDEN_COAT 	= null; // Dex + Int
//    public static final RegistryObject<Item> DIAMOND_GREAVES	= null;	// Str
//    public static final RegistryObject<Item> DIAMOND_STEALTHBOOTS	= null;	// Dex
//    public static final RegistryObject<Item> DIAMOND_SHOES	= null;	// Int
//    public static final RegistryObject<Item> DIAMOND_SABATONS 	= null; //Str + Dex
//    public static final RegistryObject<Item> DIAMOND_CALIGAE	= null; // Str + Int
//    public static final RegistryObject<Item> DIAMOND_COAT 	= null; // Dex + Int
//    public static final RegistryObject<Item> NETHERITE_GREAVES	= null;	// Str
//    public static final RegistryObject<Item> NETHERITE_STEALTHBOOTS 	= null;	// Dex
//    public static final RegistryObject<Item> NETHERITE_SHOES	= null;	// Int
//    public static final RegistryObject<Item> NETHERITE_SABATONS 	= null; //Str + Dex
//    public static final RegistryObject<Item> NETHERITE_CALIGAE	= null; // Str + Int
//    public static final RegistryObject<Item> NETHERITE_COAT 	= null; // Dex + Int
	

    
//	private static ResourceKey<Biome> makeKey(String name) {
//		// Apparently this resolves biome shuffling /shrug
//		BIOMES.register(name, () -> new Biome.BiomeBuilder()
//				.precipitation(Biome.Precipitation.NONE)
//				.biomeCategory(Biome.BiomeCategory.NONE)
//				//.depth(0)
//				.downfall(0)
//				//.scale(0)
//				.temperature(0)
//				.specialEffects(new BiomeSpecialEffects.Builder().fogColor(0).waterColor(0).waterFogColor(0).skyColor(0).build())
//				.generationSettings(new BiomeGenerationSettings.Builder().build())
//				.mobSpawnSettings(new MobSpawnSettings.Builder().build())
//				.temperatureAdjustment(Biome.TemperatureModifier.NONE)
//				.build());
//		return ResourceKey.create(Registry.BIOME_REGISTRY, Skyspace.prefix(name));
//	}
	
}
