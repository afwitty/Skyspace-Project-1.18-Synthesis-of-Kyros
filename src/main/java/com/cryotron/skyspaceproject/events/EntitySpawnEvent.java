package com.cryotron.skyspaceproject.events;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.capabilities.CapabilityList;
import com.cryotron.skyspaceproject.capabilities.energyshield.EnergyShieldCapabilityHandler;
import com.cryotron.skyspaceproject.capabilities.energyshield.IEnergyShieldCapability;
import com.cryotron.skyspaceproject.setup.SkyspaceRegistration;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skyspace.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class EntitySpawnEvent {	
	
	@SubscribeEvent
	public static void SpawnEvent(EntityJoinWorldEvent event)  {
		Entity en = event.getEntity();

		if (en instanceof LivingEntity) {
			LivingEntity len = (LivingEntity) en;
			
			if ((len.getAttribute(SkyspaceRegistration.MAX_ENERGY_SHIELD.get()) != null) && (len.getAttributeValue(SkyspaceRegistration.MAX_ENERGY_SHIELD.get()) != 0.0)) {
		
				if (len.getCapability(CapabilityList.ENERGY_SHIELD).isPresent()) {								
					len.getCapability(CapabilityList.ENERGY_SHIELD).ifPresent(cap -> {
						cap.setEnergyShield((float) len.getAttributeValue(SkyspaceRegistration.MAX_ENERGY_SHIELD.get()));						
					});
				}			
			}
		}			
	}	
	
}
