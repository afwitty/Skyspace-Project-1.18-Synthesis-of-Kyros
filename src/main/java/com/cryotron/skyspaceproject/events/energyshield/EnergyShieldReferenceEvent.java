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
}
