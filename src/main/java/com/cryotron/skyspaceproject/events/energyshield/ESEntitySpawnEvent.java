package com.cryotron.skyspaceproject.events.energyshield;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.setup.deferredregistries.RegisteredAttributes;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skyspace.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ESEntitySpawnEvent {
	  /*
	   * SpawnEvent - Fires this event if a living entity spawns with their Maximum Energy Shield Attribute set and also set their Current Energy Shield Capability equal to its Maximum. 
	   */
	  
		@SubscribeEvent
		public static void SpawnEvent(EntityJoinWorldEvent event)  {
					
			Entity en = event.getEntity();
			
			if (en instanceof LivingEntity) {
				LivingEntity len = (LivingEntity) en;
				
				if ((len.getAttribute(RegisteredAttributes.MAX_ENERGY_SHIELD.get()) != null) && (len.getAttributeValue(RegisteredAttributes.MAX_ENERGY_SHIELD.get()) != 0.0)) {
			
//					if (len.getCapability(CapabilityList.ENERGY_SHIELD).isPresent()) {								
//						len.getCapability(CapabilityList.ENERGY_SHIELD).ifPresent(cap -> {
//							cap.setEnergyShield(cap.getEnergyShield());						
//							cap.setEnergyShieldRechargeTimer(cap.getEnergyShieldRechargeTimer());					
//						});
//					}			
				}
			}			
		}	
}
