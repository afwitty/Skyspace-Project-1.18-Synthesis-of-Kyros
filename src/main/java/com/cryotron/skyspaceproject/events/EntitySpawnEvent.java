package com.cryotron.skyspaceproject.events;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.capabilities.CapabilityList;
import com.cryotron.skyspaceproject.capabilities.energyshield.EnergyShieldCapabilityHandler;
import com.cryotron.skyspaceproject.capabilities.energyshield.IEnergyShieldCapability;
import com.cryotron.skyspaceproject.setup.SkyspaceRegistration;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skyspace.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class EntitySpawnEvent {
	
	@SubscribeEvent
	public static void SpawnEvent(EntityJoinWorldEvent event)  {
		Entity en = event.getEntity();

		
		if (en instanceof LivingEntity) {
			LivingEntity len = (LivingEntity) en;
			Skyspace.LOGGER.info("A Living Entity Join World Event fired from: " + event.getEntity());
			
			if (len.getAttribute(SkyspaceRegistration.MAX_ENERGY_SHIELD.get()) != null) {
				double es = EnergyShieldCapabilityHandler.getEnergyShieldValue();
				Skyspace.LOGGER.info("Fired Entity has a Maximum Energy Shield Attribute of... " + len.getAttributeValue(SkyspaceRegistration.MAX_ENERGY_SHIELD.get()));
				Skyspace.LOGGER.info("Initial Energy Shield Value: " + es);
				EnergyShieldCapabilityHandler.setEnergyShieldValue((float) len.getAttributeValue(SkyspaceRegistration.MAX_ENERGY_SHIELD.get()));
				Skyspace.LOGGER.info("Final Energy Shield Value: " + es);
			}
			
		}
				
		}
			
}
