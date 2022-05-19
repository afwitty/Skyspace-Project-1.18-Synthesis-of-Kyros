package com.cryotron.skyspaceproject.setup.deferredregistries;

import static com.cryotron.skyspaceproject.Skyspace.ID;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.setup.ESParticle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegisteredParticles {
	
    public static final DeferredRegister<ParticleType<?>> PART = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ID);
    
    public static final RegistryObject<SimpleParticleType> ENERGY_SHIELD_DAMAGE_INDICATOR = PART.register("energy_shield_damage_indicator", () -> new SimpleParticleType(true));


}

// Should this class be public? If so, this may need a separate file. I put it here because it's part of Particles. -CT
@EventBusSubscriber(modid = Skyspace.ID, bus = Bus.MOD)
class SkyspaceSetupParticles {
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void registerFactories(ParticleFactoryRegisterEvent event) {
		@SuppressWarnings("resource")
		ParticleEngine particles = Minecraft.getInstance().particleEngine;

		particles.register(RegisteredParticles.ENERGY_SHIELD_DAMAGE_INDICATOR.get(), ESParticle.Factory::new);
	}
	
}
