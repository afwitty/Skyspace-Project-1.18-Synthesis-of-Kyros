package com.cryotron.skyspaceproject.setup;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.client.render.SkyspaceDimensionRender;
import com.cryotron.skyspaceproject.entities.synthesized_zombie.SynthesizedZombieRenderer;
import com.cryotron.skyspaceproject.entities.synthesized_skeleton.SynthesizedSkeletonRenderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Skyspace.ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeClientSetup {
    public static void init(FMLClientSetupEvent event) {
    	SkyspaceDimensionRender.init();
    }

    // If using layers.
//    @SubscribeEvent
//    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
//        event.registerLayerDefinition(ThiefModel.THIEF_LAYER, ThiefModel::createBodyLayer);
//    }

    @SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(SkyspaceRegistration.SYNTHESIZED_ZOMBIE.get(), SynthesizedZombieRenderer::new);
        event.registerEntityRenderer(SkyspaceRegistration.SYNTHESIZED_SKELETON.get(), SynthesizedSkeletonRenderer::new);
    }

}
