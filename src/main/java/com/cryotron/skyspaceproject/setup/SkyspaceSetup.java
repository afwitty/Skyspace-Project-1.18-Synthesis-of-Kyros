package com.cryotron.skyspaceproject.setup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.entities.synthesized_zombie.SynthesizedZombie;
import com.cryotron.skyspaceproject.entities.synthesized_skeleton.SynthesizedSkeleton;
import com.cryotron.skyspaceproject.worldgen.dimensions.Dimensions;
//import com.cryotron.skyspaceproject.worldgen.structures.Structures;
import com.cryotron.skyspaceproject.worldgen.structures.*;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
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
     
    public static void preInit() {
		IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
		
        //Initalize GeckoLib
    	GeckoLibMod.DISABLE_IN_DEV = true;
        GeckoLib.initialize();
		
		KyrosianMaze.mapChunkNodes();
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
        //RenderingRegistry.registerEntityRenderingHandler(SkyspaceRegistration.SYNTHESIZED_ZOMBIE.get(), StalkerRender::new);
    }
}
