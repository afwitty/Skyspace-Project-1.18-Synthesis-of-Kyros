package com.cryotron.skyspaceproject.events.energyshield;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.capabilities.CapabilityList;
import com.cryotron.skyspaceproject.capabilities.energyshield.IEnergyShieldCapability;
import com.cryotron.skyspaceproject.setup.deferredregistries.RegisteredAttributes;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skyspace.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class EnergyShieldRechargeEvent {
	  
	private static float currES, maxES, rechargeCounter, rechargeTime;
	
	  /*
	   * RechargeEnergyShieldEvent - Fires this event each (approximate) second and counts how long a living entity has not taken damage for X seconds.
	   * 		Afterwards, recover their Energy Shield at a rate of Y Energy Shield per second until either the living entity takes damage or have their Current Energy Shield is greater or equal to their Maximum Energy Shield. 
	   * 		X = 4 for Players; 7 for Monsters. Y = Maximum Energy Shield / 3 for Players; Maximum Energy Shield / 7 for Monsters.
	   * 		X value can be modified by "Faster start of Energy Shield recharge" or in this case ENERGY_SHIELD_RECHARGE attribute. 
	   */

	  @SubscribeEvent
	  public static void RechargeEnergyShieldEvent ( LivingUpdateEvent event ) {	// Actually a LivingTickEvent	    
		  
		    LivingEntity entity = event.getEntityLiving();
		    LazyOptional<IEnergyShieldCapability> es = entity.getCapability(CapabilityList.ENERGY_SHIELD);		
		  	
		    if (entity.getLevel().isClientSide) {
		    	return;
		    }
			
			if (!(es.isPresent())) {
				return;	 // If a living entity somehow doesn't have a capability, return.
			}
			
			// Set Max
			if (entity.getAttributes().hasAttribute(RegisteredAttributes.MAX_ENERGY_SHIELD.get())) {
				maxES = (float) entity.getAttributeValue(RegisteredAttributes.MAX_ENERGY_SHIELD.get());	// Get Maximum Energy Shield value from an entity. 
			}
			else {
				return; // If a living entity has no maximum energy shield, it has no business being in this method.
			}
			
			if (entity.getAttributes().hasAttribute(RegisteredAttributes.ENERGY_SHIELD_RECHARGE.get())) {
				rechargeTime = (float) entity.getAttributeValue(RegisteredAttributes.ENERGY_SHIELD_RECHARGE.get()); 
			}
			else {
				return; // If a living entity somehow has no energy shield recharge timer, return. ALL entities have a timer currently.
			}

			if (es.isPresent()) {
				
				// Set Currents
				es.ifPresent(cap -> {
					currES = cap.getEnergyShield(); // Get current Energy Shield value from an entity.
					rechargeCounter = cap.getEnergyShieldRechargeTimer(); // Get Energy Shield Recharge Timer from an entity..
				});
				
				if ( currES > maxES ) {
					currES = maxES;
					es.ifPresent(cap -> {				
						cap.setEnergyShield(currES); 
					});				
				}
				
				if ( rechargeCounter < rechargeTime ) {
					rechargeCounter =  rechargeCounter + (1/20.0f);
					es.ifPresent(cap -> {				
						cap.setEnergyShieldRechargeTimer(rechargeCounter); // Get Energy Shield Recharge Timer from an entity..
					});
				}
				
				if (rechargeCounter >= rechargeTime) {
					currES += (maxES)/60;
					if (currES >= maxES) {
						currES = maxES;
					}
					es.ifPresent(cap -> {
						cap.setEnergyShield(currES);
						//cap.setEnergyShieldRechargeTimer(rechargeCounter); // Get Energy Shield Recharge Timer from an entity..
					});
				}
				
			}
	  }
}
