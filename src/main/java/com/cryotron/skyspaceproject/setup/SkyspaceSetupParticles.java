package com.cryotron.skyspaceproject.setup;

import com.cryotron.skyspaceproject.Skyspace;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Skyspace.ID, bus = Bus.MOD)
public class SkyspaceSetupParticles {

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void registerFactories(ParticleFactoryRegisterEvent event) {
		@SuppressWarnings("resource")
		ParticleEngine particles = Minecraft.getInstance().particleEngine;

		particles.register(SkyspaceRegistration.ENERGY_SHIELD_DAMAGE_INDICATOR.get(), ESParticle.Factory::new);
	}
	
}
