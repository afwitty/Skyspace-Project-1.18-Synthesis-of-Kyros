package com.cryotron.skyspaceproject.setup.deferredregistries;

import static com.cryotron.skyspaceproject.Skyspace.ID;

import com.cryotron.skyspaceproject.items.FlintAndRune;
import com.cryotron.skyspaceproject.items.armor.SkyspaceArmorItem;
import com.cryotron.skyspaceproject.items.armor.SkyspaceEnergyShieldItem;
import com.cryotron.skyspaceproject.items.armor.enums.EnergyShieldMaterials;
import com.cryotron.skyspaceproject.items.armor.enums.SkyspaceArmorMaterials;
import com.cryotron.skyspaceproject.setup.SkyspaceSetup;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegisteredItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ID);
    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(SkyspaceSetup.ITEM_GROUP);
    
    /*
     * Spawn Eggs
     */
    
     // Kyrosian Basic
	 public static final RegistryObject<Item> SYNTHESIZED_ZOMBIE_EGG 			= ITEMS.register("synthesized_zombie", () -> 		new ForgeSpawnEggItem(RegisteredEntities.SYNTHESIZED_ZOMBIE, 0xff0000, 0x00ff00, ITEM_PROPERTIES));
	 public static final RegistryObject<Item> SYNTHESIZED_SKELETON_EGG	 	= ITEMS.register("synthesized_skeleton", () ->		new ForgeSpawnEggItem(RegisteredEntities.SYNTHESIZED_SKELETON, 0xff0000, 0x00ff00, ITEM_PROPERTIES));
	 
	 // Kyrosian Advanced
	 public static final RegistryObject<Item> KYROSIAN_ARCHON_EGG 				= ITEMS.register("kyrosian_archon", () -> 				new ForgeSpawnEggItem(RegisteredEntities.KYROSIAN_ARCHON, 0xff0000, 0x00ff00, ITEM_PROPERTIES));
	 public static final RegistryObject<Item> KYROSIAN_ENFORCER_EGG 			= ITEMS.register("kyrosian_enforcer", () -> 			new ForgeSpawnEggItem(RegisteredEntities.KYROSIAN_ENFORCER, 0xff0000, 0x00ff00, ITEM_PROPERTIES));
	 public static final RegistryObject<Item> KYROSIAN_MUTILATOR_EGG 			= ITEMS.register("kyrosian_mutilator", () -> 			new ForgeSpawnEggItem(RegisteredEntities.KYROSIAN_MUTILATOR, 0xff0000, 0x00ff00, ITEM_PROPERTIES));
	 public static final RegistryObject<Item> KYROSIAN_DEACON_EGG 				= ITEMS.register("kyrosian_deacon", () -> 			new ForgeSpawnEggItem(RegisteredEntities.KYROSIAN_DEACON, 0xff0000, 0x00ff00, ITEM_PROPERTIES));

	 /*
	  * Items
	  */
	 
	  // Gem
	  public static final RegistryObject<Item> RUBY 			= ITEMS.register("ruby", () -> 			new Item(new Item.Properties().tab(SkyspaceSetup.ITEM_GROUP)));
	  public static final RegistryObject<Item> SAPPHIRE 	= ITEMS.register("sapphire", () -> 	new Item(new Item.Properties().tab(SkyspaceSetup.ITEM_GROUP)));
	  public static final RegistryObject<Item> TOPAZ 			= ITEMS.register("topaz", () -> 			new Item(new Item.Properties().tab(SkyspaceSetup.ITEM_GROUP)));
	  public static final RegistryObject<Item> JADE 			= ITEMS.register("jade", () -> 			new Item(new Item.Properties().tab(SkyspaceSetup.ITEM_GROUP))); 
	  public static final RegistryObject<Item> OPAL 			= ITEMS.register("opal", () -> 			new Item(new Item.Properties().tab(SkyspaceSetup.ITEM_GROUP)));
	  public static final RegistryObject<Item> ONYX 			= ITEMS.register("onyx", () -> 			new Item(new Item.Properties().tab(SkyspaceSetup.ITEM_GROUP)));
	  
	  // Misc
	  public static final RegistryObject<Item> FLINT_AND_RUNE = ITEMS.register("flint_and_rune", () -> new FlintAndRune(new Item.Properties().tab(SkyspaceSetup.ITEM_GROUP).durability(64)));
	  public static final RegistryObject<Item> ENERGY_SHIELD_TEST_ARMOR = ITEMS.register("energy_shield_test_armor", () -> new SkyspaceEnergyShieldItem(SkyspaceArmorMaterials.TEST,  EquipmentSlot.CHEST, new Item.Properties().tab(SkyspaceSetup.ITEM_GROUP)));
	 
	 /*
	  * Blocks
	  */	  
	  
	  // Kyrosian Blocks
      public static final RegistryObject<Item> KYROSIAN_TILE_BLOCK_ITEM 				= fromBlock(RegisteredBlocks.KYROSIAN_TILE_BLOCK);
      public static final RegistryObject<Item> KYROSIAN_GLASS_BLOCK_ITEM			= fromBlock(RegisteredBlocks.KYROSIAN_GLASS_BLOCK);
      public static final RegistryObject<Item> KYROSIAN_EDGE_BLOCK_ITEM			= fromBlock(RegisteredBlocks.KYROSIAN_EDGE_BLOCK);
	  public static final RegistryObject<Item> KYROSIAN_TILE_STAIRS_ITEM				= fromBlock(RegisteredBlocks.KYROSIAN_TILE_STAIRS);
	  public static final RegistryObject<Item> KYROSIAN_GLASS_STAIRS_ITEM			= fromBlock(RegisteredBlocks.KYROSIAN_GLASS_STAIRS);
	  public static final RegistryObject<Item> KYROSIAN_EDGE_STAIRS_ITEM			= fromBlock(RegisteredBlocks.KYROSIAN_EDGE_STAIRS);
	  public static final RegistryObject<Item> KYROSIAN_STARGATE_FRAME_ITEM	= fromBlock(RegisteredBlocks.KYROSIAN_STARGATE_FRAME);
	  
	  // Zeus Units
	   public static final RegistryObject<Item> RUBY_BLOCK_ITEM 									= fromBlock(RegisteredBlocks.RUBY_BLOCK);
	   public static final RegistryObject<Item> SAPPHIRE_BLOCK_ITEM 						= fromBlock(RegisteredBlocks.SAPPHIRE_BLOCK);
	   public static final RegistryObject<Item> TOPAZ_BLOCK_ITEM 								= fromBlock(RegisteredBlocks.TOPAZ_BLOCK);
	   public static final RegistryObject<Item> JADE_BLOCK_ITEM 									= fromBlock(RegisteredBlocks.JADE_BLOCK);
	   public static final RegistryObject<Item> OPAL_BLOCK_ITEM 									= fromBlock(RegisteredBlocks.OPAL_BLOCK);
	   public static final RegistryObject<Item> ONYX_BLOCK_ITEM 									= fromBlock(RegisteredBlocks.ONYX_BLOCK);

	   public static final RegistryObject<Item> RUBY_ORE_ITEM 										= fromBlock(RegisteredBlocks.RUBY_ORE); 
	   public static final RegistryObject<Item> SAPPHIRE_ORE_ITEM 								= fromBlock(RegisteredBlocks.SAPPHIRE_ORE);
	   public static final RegistryObject<Item> TOPAZ_ORE_ITEM 										= fromBlock(RegisteredBlocks.TOPAZ_ORE);
	   public static final RegistryObject<Item> JADE_ORE_ITEM 										= fromBlock(RegisteredBlocks.JADE_ORE);
	   public static final RegistryObject<Item> OPAL_ORE_ITEM 										= fromBlock(RegisteredBlocks.OPAL_ORE);
	   public static final RegistryObject<Item> ONYX_ORE_ITEM 										= fromBlock(RegisteredBlocks.ONYX_ORE);

	   public static final RegistryObject<Item> NETHER_RUBY_ORE_ITEM 					= fromBlock(RegisteredBlocks.NETHER_RUBY_ORE);
	   public static final RegistryObject<Item> NETHER_SAPPHIRE_ORE_ITEM 			= fromBlock(RegisteredBlocks.NETHER_SAPPHIRE_ORE);
	   public static final RegistryObject<Item> NETHER_TOPAZ_ORE_ITEM 					= fromBlock(RegisteredBlocks.NETHER_TOPAZ_ORE);
	   public static final RegistryObject<Item> NETHER_JADE_ORE_ITEM 					= fromBlock(RegisteredBlocks.NETHER_JADE_ORE);
	   public static final RegistryObject<Item> NETHER_OPAL_ORE_ITEM 					= fromBlock(RegisteredBlocks.NETHER_OPAL_ORE);
	   public static final RegistryObject<Item> NETHER_ONYX_ORE_ITEM 					= fromBlock(RegisteredBlocks.NETHER_ONYX_ORE);
	   
	   //public static final RegistryObject<Item> JEWELCRAFTER_ITEM = ;
		
	  // Convenient function: Take a RegistryObject<Block> and make a corresponding RegistryObject<Item> from it
	  public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
		   return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ITEM_PROPERTIES));
	  }
		
}
