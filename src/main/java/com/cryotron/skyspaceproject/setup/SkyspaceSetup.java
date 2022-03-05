package com.cryotron.skyspaceproject.setup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cryotron.skyspaceproject.Skyspace;
<<<<<<< Updated upstream
=======
import com.cryotron.skyspaceproject.capabilities.CapabilityList;
import com.cryotron.skyspaceproject.capabilities.energyshield.IEnergyShieldCapability;
>>>>>>> Stashed changes
import com.cryotron.skyspaceproject.entities.synthesized_zombie.SynthesizedZombie;
import com.cryotron.skyspaceproject.entities.synthesized_skeleton.SynthesizedSkeleton;
import com.cryotron.skyspaceproject.worldgen.dimensions.Dimensions;
//import com.cryotron.skyspaceproject.worldgen.structures.Structures;
import com.cryotron.skyspaceproject.worldgen.structures.*;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
<<<<<<< Updated upstream
=======
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
>>>>>>> Stashed changes
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = Skyspace.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SkyspaceSetup {
	
	public static final String TAB_NAME = "Skyspace Project";

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(Skyspace.ID + " Mod Event Subscriber");
	
    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(TAB_NAME) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.DIAMOND);
        }
    };
    
    // Test
    
     
    public static void preInit() {
		IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
		
        //Initalize GeckoLib
    	GeckoLibMod.DISABLE_IN_DEV = true;
        GeckoLib.initialize();
		
<<<<<<< Updated upstream
		KyrosianMaze.mapChunkNodes();
=======
		KyrosianMaze.mapChunkNodes();			// Mapping Quadrant I Maze
		KyrosianMaze.mapChunkNodesII();		// Mapping Quadrant II Maze
		KyrosianMaze.mapChunkNodesIII();		// Mapping Quadrant III Maze
		KyrosianMaze.mapChunkNodesIV();		// Mapping Quadrant IV Maze
		
		modbus.addListener(CapabilityList::registerCapabilities);
		MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, CapabilityList::attachEntityCapability);
>>>>>>> Stashed changes
		modbus.addListener(SkyspaceSetup::setup);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modbus.addListener(ForgeClientSetup::init));
    }
    
    public static void setup(FMLCommonSetupEvent event) {
    	event.enqueueWork(() -> {
    		Dimensions.register();
    		SkyspaceRegistration.setupStructures();
    		SSConfiguredStructures.registerStructureFeatures();
    		//MazeConfig.registerConfiguredStructures();
    	});
    }
    
    public static void postInit() {
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(EventPriority.NORMAL, Structures::addDimensionalSpacing);
        //forgeBus.addListener(EventPriority.NORMAL, KyrosianMaze::setupStructureSpawns);
    }
    
    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(SkyspaceRegistration.SYNTHESIZED_ZOMBIE.get(), SynthesizedZombie.createAttributes().build());
        event.put(SkyspaceRegistration.SYNTHESIZED_SKELETON.get(), SynthesizedSkeleton.createAttributes().build());
<<<<<<< Updated upstream
        //RenderingRegistry.registerEntityRenderingHandler(SkyspaceRegistration.SYNTHESIZED_ZOMBIE.get(), StalkerRender::new);
    }
=======
        event.put(SkyspaceRegistration.KYROSIAN_ARCHON.get(), KyrosianArchon.createAttributes().build());
        event.put(SkyspaceRegistration.KYROSIAN_ENFORCER.get(), KyrosianArchon.createAttributes().build());
        event.put(SkyspaceRegistration.KYROSIAN_MUTILATOR.get(), KyrosianArchon.createAttributes().build());
        event.put(SkyspaceRegistration.KYROSIAN_DEACON.get(), KyrosianArchon.createAttributes().build());
        //(SkyspaceRegistration.KYROSIAN_ARCHON.get(), KyrosianArchonRenderer::new);
    }
    

>>>>>>> Stashed changes
}
