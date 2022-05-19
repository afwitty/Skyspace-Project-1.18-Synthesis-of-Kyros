package com.cryotron.skyspaceproject.setup.deferredregistries;

import static com.cryotron.skyspaceproject.Skyspace.ID;

import com.cryotron.skyspaceproject.block.stargate.KyrosianStargateBlock;
import com.cryotron.skyspaceproject.block.stargate.KyrosianStargateFrame;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegisteredBlocks {
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ID);
	
    public static final BlockBehaviour.Properties ORE_PROPERTIES = BlockBehaviour.Properties.of(Material.STONE).strength(2f).requiresCorrectToolForDrops();
	
    /*
     * Kyrosian Blocks
     * TODO: Find a way to make the Kyrosian Glass transparent.
     */
    public static final RegistryObject<Block> KYROSIAN_TILE_BLOCK 			= BLOCKS.register("kyrosian_tile_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(2.0F, 6.0F).sound(SoundType.METAL)));
	public static final RegistryObject<Block> KYROSIAN_GLASS_BLOCK		= BLOCKS.register("kyrosian_glass_block", () -> new GlassBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2.0F, 6.0F).sound(SoundType.METAL).noOcclusion()));
	public static final RegistryObject<Block> KYROSIAN_EDGE_BLOCK		= BLOCKS.register("kyrosian_edge_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(2.0F, 6.0F).sound(SoundType.METAL)));
	public static final RegistryObject<Block> KYROSIAN_TILE_STAIRS			= BLOCKS.register("kyrosian_tile_stairs", ()  -> new StairBlock(() -> KYROSIAN_TILE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(KYROSIAN_TILE_BLOCK.get())));
	public static final RegistryObject<Block> KYROSIAN_GLASS_STAIRS		= BLOCKS.register("kyrosian_glass_stairs", ()  -> new StairBlock(() -> KYROSIAN_TILE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(KYROSIAN_TILE_BLOCK.get())));
	public static final RegistryObject<Block> KYROSIAN_EDGE_STAIRS		= BLOCKS.register("kyrosian_edge_stairs",  ()  -> new StairBlock(() -> KYROSIAN_TILE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(KYROSIAN_TILE_BLOCK.get())));
	public static final RegistryObject<Block> KYROSIAN_STARGATE_BLOCK	= BLOCKS.register("kyrosian_stargate_block", () -> new KyrosianStargateBlock());
	public static final RegistryObject<Block> KYROSIAN_STARGATE_FRAME	= BLOCKS.register("kyrosian_stargate_frame", () -> new KyrosianStargateFrame());
	
	/*
	 * Zeus Units
	 * TODO: Add Deepslate version.
	 */
   public static final RegistryObject<Block> RUBY_BLOCK = BLOCKS.register("ruby_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)));
   public static final RegistryObject<Block> SAPPHIRE_BLOCK = BLOCKS.register("sapphire_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)));
   public static final RegistryObject<Block> TOPAZ_BLOCK = BLOCKS.register("topaz_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)));
   public static final RegistryObject<Block> JADE_BLOCK = BLOCKS.register("jade_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL))); 
   public static final RegistryObject<Block> OPAL_BLOCK = BLOCKS.register("opal_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)));
   public static final RegistryObject<Block> ONYX_BLOCK = BLOCKS.register("onyx_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)));

   public static final RegistryObject<Block> RUBY_ORE = BLOCKS.register("ruby_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).sound(SoundType.METAL)));
   public static final RegistryObject<Block> SAPPHIRE_ORE = BLOCKS.register("sapphire_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).sound(SoundType.METAL)));
   public static final RegistryObject<Block> TOPAZ_ORE = BLOCKS.register("topaz_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).sound(SoundType.METAL)));
   public static final RegistryObject<Block> JADE_ORE = BLOCKS.register("jade_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).sound(SoundType.METAL))); 
   public static final RegistryObject<Block> OPAL_ORE = BLOCKS.register("opal_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).sound(SoundType.METAL)));
   public static final RegistryObject<Block> ONYX_ORE = BLOCKS.register("onyx_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).sound(SoundType.METAL)));

   public static final RegistryObject<Block> NETHER_RUBY_ORE = BLOCKS.register("nether_ruby_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).sound(SoundType.METAL)));
   public static final RegistryObject<Block> NETHER_SAPPHIRE_ORE = BLOCKS.register("nether_sapphire_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).sound(SoundType.METAL)));
   public static final RegistryObject<Block> NETHER_TOPAZ_ORE = BLOCKS.register("nether_topaz_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).sound(SoundType.METAL)));
   public static final RegistryObject<Block> NETHER_JADE_ORE = BLOCKS.register("nether_jade_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).sound(SoundType.METAL)));
   public static final RegistryObject<Block> NETHER_OPAL_ORE = BLOCKS.register("nether_opal_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).sound(SoundType.METAL)));
   public static final RegistryObject<Block> NETHER_ONYX_ORE = BLOCKS.register("nether_onyx_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).sound(SoundType.METAL)));
  
   //public static final RegistryObject<Block> JEWELCRAFTER = BLOCKS.register("jewelcrafter", () -> new JewelCrafterBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE).sound(SoundType.WOOD)));

	
}
