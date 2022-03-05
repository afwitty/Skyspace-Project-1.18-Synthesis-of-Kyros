package com.cryotron.skyspaceproject.setup;

import net.minecraft.SharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
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
<<<<<<< Updated upstream
=======
import com.cryotron.skyspaceproject.entities.kyrosian_archon.KyrosianArchon;
import com.cryotron.skyspaceproject.entities.kyrosian_deacon.KyrosianDeacon;
import com.cryotron.skyspaceproject.entities.kyrosian_enforcer.KyrosianEnforcer;
import com.cryotron.skyspaceproject.entities.kyrosian_mutilator.KyrosianMutilator;
>>>>>>> Stashed changes
import com.cryotron.skyspaceproject.entities.synthesized_skeleton.SynthesizedSkeleton;
import com.cryotron.skyspaceproject.entities.synthesized_zombie.SynthesizedZombie;
import com.cryotron.skyspaceproject.worldgen.structures.KyrosianMaze;

public class SkyspaceRegistration {

	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, ID);
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ID);
    public static final DeferredRegister<SoundEvent> SFX = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ID);
    
    public static void init() {
    	IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    	BIOMES.register(bus);
    	ATTRIBUTES.register(bus);
    	ENTITIES.register(bus);
    	SFX.register(bus);
    	BLOCKS.register(bus);
    	ITEMS.register(bus);
    	SSStructures.STRUCTURES.register(bus);
    }
    
    // Some common properties for our blocks and items
    public static final BlockBehaviour.Properties ORE_PROPERTIES = BlockBehaviour.Properties.of(Material.STONE).strength(2f).requiresCorrectToolForDrops();
    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(SkyspaceSetup.ITEM_GROUP);
    
	public static final ResourceKey<Biome> KYROS_QUADRANT_AXIS = makeQuadAxis("kyros_quadrant_axis"); 
//	BIOMES.register("kyros_quadrant_axis", () ->
//		new KyrosQuadrantAxis(
//				new Biome.Builder()
//						.category(Biome.Category.ICY)
//						.temperature(0)
//						.downfall(0.1F)
//						.precipitation(Biome.RainType.SNOW)
//		));//

    public static final RegistryObject<Block> KYROSIAN_TILE_BLOCK 			= BLOCKS.register("kyrosian_tile_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(2.0F, 6.0F).sound(SoundType.METAL)));
	public static final RegistryObject<Block> KYROSIAN_GLASS_BLOCK		= BLOCKS.register("kyrosian_glass_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(2.0F, 6.0F).sound(SoundType.METAL)));
	public static final RegistryObject<Block> KYROSIAN_EDGE_BLOCK		= BLOCKS.register("kyrosian_edge_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(2.0F, 6.0F).sound(SoundType.METAL)));
	public static final RegistryObject<Block> KYROSIAN_TILE_STAIRS			= BLOCKS.register("kyrosian_tile_stairs", ()  -> new StairBlock(() -> KYROSIAN_TILE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(KYROSIAN_TILE_BLOCK.get())));
	public static final RegistryObject<Block> KYROSIAN_GLASS_STAIRS		= BLOCKS.register("kyrosian_glass_stairs", ()  -> new StairBlock(() -> KYROSIAN_TILE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(KYROSIAN_TILE_BLOCK.get())));;
	public static final RegistryObject<Block> KYROSIAN_EDGE_STAIRS		= BLOCKS.register("kyrosian_edge_stairs",  ()  -> new StairBlock(() -> KYROSIAN_TILE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(KYROSIAN_TILE_BLOCK.get())));;
    
	public static final RegistryObject<Attribute> ENERGY_SHIELD = ATTRIBUTES.register("energy_shield", () -> new RangedAttribute("energy_shield", 0.0D, 0.0D, 1024.0D).setSyncable(true));
	public static final RegistryObject<Attribute> EVASION = ATTRIBUTES.register("evasion", () -> new RangedAttribute("evasion", 1.0D, 0.0D, 1024.0D).setSyncable(true));	
	public static final RegistryObject<Attribute> ACCURACY = ATTRIBUTES.register("accuracy", () -> new RangedAttribute("accuracy", 1.0D, 0.0D, 1024.0D).setSyncable(true));	
	
	public static final RegistryObject<EntityType<SynthesizedZombie>> SYNTHESIZED_ZOMBIE = ENTITIES.register("synthesized_zombie", () -> EntityType.Builder.of(SynthesizedZombie::new, MobCategory.MONSTER)
				.sized(0.7f, 2.1f)
				.clientTrackingRange(16)
				.setShouldReceiveVelocityUpdates(false)
				.build("synthesized_zombie")
			);
	 public static final RegistryObject<Item> SYNTHESIZED_ZOMBIE_EGG = ITEMS.register("synthesized_zombie", () -> new ForgeSpawnEggItem(SYNTHESIZED_ZOMBIE, 0xff0000, 0x00ff00, ITEM_PROPERTIES));
	 
	public static final RegistryObject<EntityType<SynthesizedSkeleton>> SYNTHESIZED_SKELETON = ENTITIES.register("synthesized_skeleton", () -> EntityType.Builder.of(SynthesizedSkeleton::new, MobCategory.MONSTER)
				.sized(0.7f, 2.1f)
				.clientTrackingRange(16)
				.setShouldReceiveVelocityUpdates(false)
				.build("synthesized_skeleton")
			);
	 public static final RegistryObject<Item> SYNTHESIZED_SKELETON_EGG = ITEMS.register("synthesized_skeleton", () -> new ForgeSpawnEggItem(SYNTHESIZED_SKELETON, 0xff0000, 0x00ff00, ITEM_PROPERTIES));
<<<<<<< Updated upstream
=======
	 
	 public static final RegistryObject<EntityType<KyrosianArchon>> KYROSIAN_ARCHON = ENTITIES.register("kyrosian_archon", () -> EntityType.Builder.of(KyrosianArchon::new, MobCategory.MONSTER)
				.sized(0.7f, 2.1f)
				.clientTrackingRange(16)
				.setShouldReceiveVelocityUpdates(false)
				.build("kyrosian_archon")
			 );
	 public static final RegistryObject<Item> KYROSIAN_ARCHON_EGG = ITEMS.register("kyrosian_archon", () -> new ForgeSpawnEggItem(KYROSIAN_ARCHON, 0xff0000, 0x00ff00, ITEM_PROPERTIES));
	 
	 public static final RegistryObject<EntityType<KyrosianEnforcer>> KYROSIAN_ENFORCER = ENTITIES.register("kyrosian_enforcer", () -> EntityType.Builder.of(KyrosianEnforcer::new, MobCategory.MONSTER)
				.sized(0.7f, 2.1f)
				.clientTrackingRange(16)
				.setShouldReceiveVelocityUpdates(false)
				.build("kyrosian_enforcer")
			 );
	 public static final RegistryObject<Item> KYROSIAN_ENFORCER_EGG = ITEMS.register("kyrosian_enforcer", () -> new ForgeSpawnEggItem(KYROSIAN_ENFORCER, 0xff0000, 0x00ff00, ITEM_PROPERTIES));
	 
	 public static final RegistryObject<EntityType<KyrosianMutilator>> KYROSIAN_MUTILATOR = ENTITIES.register("kyrosian_mutilator", () -> EntityType.Builder.of(KyrosianMutilator::new, MobCategory.MONSTER)
				.sized(0.7f, 2.1f)
				.clientTrackingRange(16)
				.setShouldReceiveVelocityUpdates(false)
				.build("kyrosian_mutilator")
			 );
	 public static final RegistryObject<Item> KYROSIAN_MUTILATOR_EGG = ITEMS.register("kyrosian_mutilator", () -> new ForgeSpawnEggItem(KYROSIAN_MUTILATOR, 0xff0000, 0x00ff00, ITEM_PROPERTIES));
	 
	 public static final RegistryObject<EntityType<KyrosianDeacon>> KYROSIAN_DEACON = ENTITIES.register("kyrosian_deacon", () -> EntityType.Builder.of(KyrosianDeacon::new, MobCategory.MONSTER)
				.sized(0.7f, 2.1f)
				.clientTrackingRange(16)
				.setShouldReceiveVelocityUpdates(false)
				.build("kyrosian_deacon")
			 );
	 public static final RegistryObject<Item> KYROSIAN_DEACON_EGG = ITEMS.register("kyrosian_deacon", () -> new ForgeSpawnEggItem(KYROSIAN_DEACON, 0xff0000, 0x00ff00, ITEM_PROPERTIES));
>>>>>>> Stashed changes
	
    public static final RegistryObject<Item> KYROSIAN_TILE_BLOCK_ITEM 			= fromBlock(KYROSIAN_TILE_BLOCK);
    public static final RegistryObject<Item> KYROSIAN_GLASS_BLOCK_ITEM		= fromBlock(KYROSIAN_GLASS_BLOCK);
    public static final RegistryObject<Item> KYROSIAN_EDGE_BLOCK_ITEM			= fromBlock(KYROSIAN_EDGE_BLOCK);
	public static final RegistryObject<Item> KYROSIAN_TILE_STAIRS_ITEM			= fromBlock(KYROSIAN_TILE_STAIRS);
	public static final RegistryObject<Item> KYROSIAN_GLASS_STAIRS_ITEM		= fromBlock(KYROSIAN_GLASS_STAIRS);
	public static final RegistryObject<Item> KYROSIAN_EDGE_STAIRS_ITEM			= fromBlock(KYROSIAN_EDGE_STAIRS);
	
	public static final RegistryObject<SoundEvent> ENTITY_KYROSIAN_ZOMBIE_AMBIENT = registerSound("synthesized_zombie_ambient", "entity.synthesized_zombie.ambient");
	public static final RegistryObject<SoundEvent> ENTITY_KYROSIAN_ZOMBIE_DEATH = registerSound("synthesized_zombie_death", "entity.synthesized_zombie.death");
	public static final RegistryObject<SoundEvent> ENTITY_KYROSIAN_ZOMBIE_HURT = registerSound("synthesized_zombie_hurt", "entity.synthesized_zombie.hurt");
	public static final RegistryObject<SoundEvent> ENTITY_KYROSIAN_ZOMBIE_STEP = registerSound("synthesized_zombie_step", "entity.synthesized_zombie.step");
	public static final RegistryObject<SoundEvent> ENTITY_KYROSIAN_SKELETON_AMBIENT = registerSound("synthesized_skeleton_ambient", "entity.synthesized_skeleton.ambient");
	public static final RegistryObject<SoundEvent> ENTITY_KYROSIAN_SKELETON_DEATH = registerSound("synthesized_skeleton_death", "entity.synthesized_skeleton.death");
	public static final RegistryObject<SoundEvent> ENTITY_KYROSIAN_SKELETON_HURT = registerSound("synthesized_skeleton_hurt", "entity.synthesized_skeleton.hurt");
	public static final RegistryObject<SoundEvent> ENTITY_KYROSIAN_SKELETON_STEP = registerSound("synthesized_skeleton_step", "entity.synthesized_skeleton.step");
    
	public static final RegistryObject<SoundEvent> AMBIENT_KYROS_LOOP = registerSound("ambient_kyros_loop", "ambient.kyrosloop");
	public static final RegistryObject<SoundEvent> KYROS_MUSIC = registerSound("kyros_music", "music.kyrosmusic");
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
	


//    public static final RegistryObject<StructureFeature<JigsawConfiguration>> KYROSIAN_X_ROAD = STRUCTURES.register("kyrosian_x_road", () -> (new KyrosianXRoad(JigsawConfiguration.CODEC)));
//    public static final RegistryObject<StructureFeature<JigsawConfiguration>> KYROSIAN_Z_ROAD = STRUCTURES.register("kyrosian_z_road", () -> (new KyrosianZRoad(JigsawConfiguration.CODEC)));
//    public static final RegistryObject<StructureFeature<JigsawConfiguration>> KYROSIAN_INTERSECTION = STRUCTURES.register("kyrosian_platform", () -> (new KyrosianIntersection(JigsawConfiguration.CODEC)));

	public static final BiomeDictionary.Type KYROS = BiomeDictionary.Type.getType("KYROS");
	
	public static void addBiomeTypes() {
		BiomeDictionary.addTypes(KYROS_QUADRANT_AXIS, BiomeDictionary.Type.VOID, BiomeDictionary.Type.END);
//		BiomeDictionary.addTypes(KYROS_LYSNY_QUADRANT, BiomeDictionary.Type.WET);				// Lysny's Quadrant is poisonous and is reminescent of Ryslatha from Path of Exile.
//		BiomeDictionary.addTypes(KYROS_ANGELA_QUADRANT, BiomeDictionary.Type.HOT);			// Angela's Quadrant is fiery and is reminescent of Kaom from Path of Exile.
//		BiomeDictionary.addTypes(KYROS_MELISSA_QUADRANT, BiomeDictionary.Type.COLD);		// Melissa's Quadrant is frozen and is remines
	}
    
	private static ResourceKey<Biome> makeKey(String name) {
		// Apparently this resolves biome shuffling /shrug
		BIOMES.register(name, () -> new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.NONE)
				.biomeCategory(Biome.BiomeCategory.NONE)
				//.depth(0)
				.downfall(0)
				//.scale(0)
				.temperature(0)
				.specialEffects(new BiomeSpecialEffects.Builder().fogColor(0).waterColor(0).waterFogColor(0).skyColor(0).build())
				.generationSettings(new BiomeGenerationSettings.Builder().build())
				.mobSpawnSettings(new MobSpawnSettings.Builder().build())
				.temperatureAdjustment(Biome.TemperatureModifier.NONE)
				.build());
		return ResourceKey.create(Registry.BIOME_REGISTRY, Skyspace.prefix(name));
	}
	
	private static ResourceKey<Biome> makeQuadAxis(String name) {
		BIOMES.register(name, () -> new Biome.BiomeBuilder()
				.precipitation(Biome.Precipitation.NONE)
				.biomeCategory(Biome.BiomeCategory.NONE)
				//.depth(0)
				.downfall(0)
				//.scale(0)
				.temperature(0)
				.specialEffects(new BiomeSpecialEffects.Builder()
						.fogColor(37)
						.waterColor(205)
						.waterFogColor(64)
						.skyColor(137)
						.ambientLoopSound(AMBIENT_KYROS_LOOP.get())
						// We'll get to music implementation later...
						.build())
				.generationSettings(new BiomeGenerationSettings.Builder().build())
				.mobSpawnSettings(new MobSpawnSettings.Builder().build())
				.temperatureAdjustment(Biome.TemperatureModifier.FROZEN)
				.build());
		return ResourceKey.create(Registry.BIOME_REGISTRY, Skyspace.prefix(name));
	}
    
    // Convenient function: Take a RegistryObject<Block> and make a corresponding RegistryObject<Item> from it
    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ITEM_PROPERTIES));
    }
    
    /**
     * This is where we set the rarity of your structures and determine if land conforms to it.
     * See the comments in below for more details.
     */
    public static void setupStructures() {
        setupMapSpacingAndLand(
                SSStructures.KYROSIAN_MAZE.get(), /* The instance of the structure */
                new StructureFeatureConfiguration(1 /* average distance apart in chunks between spawn attempts */,
                        0 /* minimum distance apart in chunks between spawn attempts. MUST BE LESS THAN ABOVE VALUE*/,
                        1 /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */),
                true);


        // Add more structures here and so on
    }
    
    /**
     * Adds the provided structure to the registry, and adds the separation settings.
     * The rarity of the structure is determined based on the values passed into
     * this method in the StructureFeatureConfiguration argument.
     * This method is called by setupStructures above.
     */
    public static <F extends StructureFeature<?>> void setupMapSpacingAndLand(
            F structure,
            StructureFeatureConfiguration structureFeatureConfiguration,
            boolean transformSurroundingLand)
    {
        /*
         * We need to add our structures into the map in StructureFeature class
         * alongside vanilla structures or else it will cause errors.
         *
         * If the registration is setup properly for the structure,
         * getRegistryName() should never return null.
         */
        StructureFeature.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

        /*
         * Whether surrounding land will be modified automatically to conform to the bottom of the structure.
         * Basically, it adds land at the base of the structure like it does for Villages and Outposts.
         * Doesn't work well on structure that have pieces stacked vertically or change in heights.
         *
         * Note: The air space this method will create will be filled with water if the structure is below sealevel.
         * This means this is best for structure above sealevel so keep that in mind.
         *
         * NOISE_AFFECTING_FEATURES requires AccessTransformer  (See resources/META-INF/accesstransformer.cfg)
         */
        if(transformSurroundingLand){
            StructureFeature.NOISE_AFFECTING_FEATURES =
                    ImmutableList.<StructureFeature<?>>builder()
                            .addAll(StructureFeature.NOISE_AFFECTING_FEATURES)
                            .add(structure)
                            .build();
        }

        /*
         * This is the map that holds the default spacing of all structures.
         * Always add your structure to here so that other mods can utilize it if needed.
         *
         * However, while it does propagate the spacing to some correct dimensions from this map,
         * it seems it doesn't always work for code made dimensions as they read from this list beforehand.
         *
         * Instead, we will use the WorldEvent.Load event in StructureTutorialMain to add the structure
         * spacing from this list into that dimension or to do dimension blacklisting properly.
         *
         * DEFAULTS requires AccessTransformer  (See resources/META-INF/accesstransformer.cfg)
         */
        StructureSettings.DEFAULTS =
                ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder()
                        .putAll(StructureSettings.DEFAULTS)
                        .put(structure, structureFeatureConfiguration)
                        .build();


        /*
         * There are very few mods that relies on seeing your structure in the noise settings registry before the world is made.
         *
         * You may see some mods add their spacings to DimensionSettings.BUILTIN_OVERWORLD instead of the NOISE_GENERATOR_SETTINGS loop below but
         * that field only applies for the default overworld and won't add to other worldtypes or dimensions (like amplified or Nether).
         * So yeah, don't do DimensionSettings.BUILTIN_OVERWORLD. Use the NOISE_GENERATOR_SETTINGS loop below instead if you must.
         */
        BuiltinRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<StructureFeature<?>, StructureFeatureConfiguration> structureMap = settings.getValue().structureSettings().structureConfig();

            /*
             * Pre-caution in case a mod makes the structure map immutable like datapacks do.
             * I take no chances myself. You never know what another mods does...
             *
             * structureConfig requires AccessTransformer (See resources/META-INF/accesstransformer.cfg)
             */
            if(structureMap instanceof ImmutableMap){
                Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, structureFeatureConfiguration);
                settings.getValue().structureSettings().structureConfig = tempMap;
            }
            else{
                structureMap.put(structure, structureFeatureConfiguration);
            }
        });
    }
    
    
    
	private static RegistryObject<SoundEvent> registerSound(String registryName, String soundPath) {
		return SFX.register(registryName, () -> createSoundEvent(soundPath));
	}

	// Using AOA3 Sound methods for sound effects.
	private static SoundEvent createSoundEvent(String soundPath) {
//		if (HolidayUtil.isChristmas() && soundPath.endsWith(".fire") && !DatagenModLoader.isRunningDataGen())
//			soundPath = "misc.jingle_bells";

		return new SoundEvent(new ResourceLocation("skyspaceproject", soundPath));
	}
	
}
