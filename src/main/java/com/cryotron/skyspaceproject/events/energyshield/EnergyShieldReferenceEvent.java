package com.cryotron.skyspaceproject.events.energyshield;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.capabilities.CapabilityList;
import com.cryotron.skyspaceproject.capabilities.energyshield.IEnergyShieldCapability;
import com.cryotron.skyspaceproject.setup.deferredregistries.RegisteredAttributes;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skyspace.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class EnergyShieldReferenceEvent {
	
	public static float currES, maxES, rechargeCounter, rechargeTime;

	@SubscribeEvent
  public static void onWorldTick(TickEvent.WorldTickEvent event) {
		event.world.players().forEach(player -> {

			LazyOptional<IEnergyShieldCapability> es = player.getCapability(CapabilityList.ENERGY_SHIELD);					
			
			es.ifPresent(cap -> {
				currES = cap.getEnergyShield();
				rechargeCounter = cap.getEnergyShieldRechargeTimer();
			});								
			
			maxES = (float) player.getAttributeValue(RegisteredAttributes.MAX_ENERGY_SHIELD.get());
			rechargeTime = (float) player.getAttributeValue(RegisteredAttributes.ENERGY_SHIELD_RECHARGE.get());
			
		});
	}
	
	/*
	 * TODO:
	 * 		- OPTIONAL: Targeted debugging tool to showcase attributes and data.
	 * 		- MODIFICATION: Monster ES Recharge Timer: From 4 to all Living Entities to 7 for all Living Entities that is not a player (EXPANSION: or boss).
	 * 			> EXPANSION: Boss ES Recharge Timer: From 4 to all Living Entities to 12 for all Boss Entities.
	 * 		- MODIFICATION: Monster ES Recharge Recovery Rate: When N is MaxES for each living entity, from N/3 per second to all Living Entities to N/6 to all Living Entities that is not a player (EXPANSION: or boss).
	 * 			> EXPANSION: Boss ES Recharge Recovery Rate: From N/3 to all Living Entities to N/12 for all Boss Entities.
	 * 		- IMPLEMENTATION: Showcase Recharge Timer below hexes in Client-side.
	 * 		- IMPLEMENTATION: Two Attributes - #% Faster Start of Energy Shield Recharge & #% Increased Energy Shield Recharge Rate
	 * 		- ADJUSTMENT: Shield Damage Indicator only on Client-Side instead of Server-Side.
	 * 		- ADJUSTMENT: Damage done to shields make Living Entities glow blue instead of red and shield damaging sounds emits. 
	 */
	
}
