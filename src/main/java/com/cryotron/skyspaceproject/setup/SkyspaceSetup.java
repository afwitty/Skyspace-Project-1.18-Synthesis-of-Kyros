package com.cryotron.skyspaceproject.setup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.capabilities.CapabilityList;
import com.cryotron.skyspaceproject.capabilities.energyshield.IEnergyShieldCapability;
import com.cryotron.skyspaceproject.entities.synthesized_zombie.SynthesizedZombie;
import com.cryotron.skyspaceproject.entities.synthesized_skeleton.SynthesizedSkeleton;
import com.cryotron.skyspaceproject.entities.kyrosian_archon.*;
import com.cryotron.skyspaceproject.entities.kyrosian_enforcer.*;
import com.cryotron.skyspaceproject.entities.kyrosian_mutilator.*;
import com.cryotron.skyspaceproject.entities.kyrosian_deacon.*;
import com.cryotron.skyspaceproject.worldgen.dimensions.Dimensions;
import com.cryotron.skyspaceproject.worldgen.structures.Structures;
import com.cryotron.skyspaceproject.worldgen.structures.KyrosianMaze;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.client.RenderProperties;
import net.minecraftforge.client.event.EntityRenderersEvent;


@Mod.EventBusSubscriber(modid = Skyspace.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SkyspaceSetup {
	
	public static final String TAB_NAME = "skyspace_group";

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger(Skyspace.ID + " Mod Event Subscriber");
	
    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(TAB_NAME) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.DEEPSLATE_LAPIS_ORE);
        }
    };
     
    public static void preInit() {
		IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
		
        //Initalize GeckoLib
    	GeckoLibMod.DISABLE_IN_DEV = true;
        GeckoLib.initialize();
		
		KyrosianMaze.mapChunkNodes();			// Mapping Quadrant I Maze
		KyrosianMaze.mapChunkNodesII();		// Mapping Quadrant II Maze
		KyrosianMaze.mapChunkNodesIII();		// Mapping Quadrant III Maze
		KyrosianMaze.mapChunkNodesIV();		// Mapping Quadrant IV Maze
		
		modbus.addListener(CapabilityList::registerCapabilities);
		MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, CapabilityList::attachEntityCapability);
		modbus.addListener(SkyspaceSetup::setup);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modbus.addListener(ForgeClientSetup::init));
    }
    
    public static void setup(FMLCommonSetupEvent event) {
    	event.enqueueWork(() -> {
    		Dimensions.register();
    		SSStructures.setupStructures();
    		SSConfiguredStructures.registerStructureFeatures();
//    		MazeConfig.registerConfiguredStructures();
    		
            SpawnPlacements.register(SkyspaceRegistration.SYNTHESIZED_ZOMBIE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE,
                    Monster::checkAnyLightMonsterSpawnRules);
            SpawnPlacements.register(SkyspaceRegistration.SYNTHESIZED_SKELETON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE,
                    Monster::checkAnyLightMonsterSpawnRules);
    	});
    }
    
    public static void postInit() {
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(EventPriority.NORMAL, Structures::addDimensionalSpacing);
        forgeBus.addListener(EventPriority.NORMAL, KyrosianMaze::setupStructureSpawns);
    }
    
    @SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
	
    	event.registerEntityRenderer(SkyspaceRegistration.KYROSIAN_ARCHON.get(), KyrosianArchonRenderer::new);
    	event.registerEntityRenderer(SkyspaceRegistration.KYROSIAN_ENFORCER.get(), KyrosianEnforcerRenderer::new);
    	event.registerEntityRenderer(SkyspaceRegistration.KYROSIAN_MUTILATOR.get(), KyrosianMutilatorRenderer::new);
    	event.registerEntityRenderer(SkyspaceRegistration.KYROSIAN_DEACON.get(), KyrosianDeaconRenderer::new);

    }
    
    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(SkyspaceRegistration.SYNTHESIZED_ZOMBIE.get(), SynthesizedZombie.createAttributes().build());
        event.put(SkyspaceRegistration.SYNTHESIZED_SKELETON.get(), SynthesizedSkeleton.createAttributes().build());
        event.put(SkyspaceRegistration.KYROSIAN_ARCHON.get(), KyrosianArchon.createAttributes().build());
        event.put(SkyspaceRegistration.KYROSIAN_ENFORCER.get(), KyrosianArchon.createAttributes().build());
        event.put(SkyspaceRegistration.KYROSIAN_MUTILATOR.get(), KyrosianArchon.createAttributes().build());
        event.put(SkyspaceRegistration.KYROSIAN_DEACON.get(), KyrosianArchon.createAttributes().build());
        //(SkyspaceRegistration.KYROSIAN_ARCHON.get(), KyrosianArchonRenderer::new);
    }
    
}
