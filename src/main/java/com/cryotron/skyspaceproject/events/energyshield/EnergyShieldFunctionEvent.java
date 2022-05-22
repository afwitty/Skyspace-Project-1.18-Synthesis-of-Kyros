package com.cryotron.skyspaceproject.events.energyshield;

import java.util.Random;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.capabilities.CapabilityList;
import com.cryotron.skyspaceproject.capabilities.energyshield.IEnergyShieldCapability;
import com.cryotron.skyspaceproject.setup.deferredregistries.RegisteredParticles;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skyspace.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class EnergyShieldFunctionEvent {

	private static float currES;
    private static Random rand = new Random();
	
	/*
	 * EnergyShieldFunctionEvent - Fires this event whenever a living entity takes damage with Energy Shield. This is the Energy Shield's Main Function.
	 */
	
	@SubscribeEvent
	public static void EnergyShieldFunctionEvent( LivingDamageEvent event)  {
		LivingEntity entity = event.getEntityLiving();		
		float dammie = event.getAmount();
		Level worldin = event.getEntity().level;
		ServerLevel sworldin = (ServerLevel) event.getEntity().level;

		BlockPos blockpos = event.getEntity().blockPosition();
		String sourcestring = event.getSource().toString();
		double px = blockpos.getX() + rand.nextFloat();
		double py = blockpos.getY() + rand.nextFloat() + 1;
		double pz = blockpos.getZ() + rand.nextFloat();
		double vx = (rand.nextFloat() - 0.5) / 2.;
		double vy = (rand.nextFloat() - 0.5) / 2.;
		double vz = (rand.nextFloat() - 0.5) / 2.;
//		DamageSource source = new DamageSource("EntityDamage");
		LazyOptional<IEnergyShieldCapability> es = entity.getCapability(CapabilityList.ENERGY_SHIELD);		
		
		if (!(es.isPresent())) {
			return;
		}
					
		es.ifPresent(cap -> {
			currES = cap.getEnergyShield();
			cap.setEnergyShieldRechargeTimer(0.0f);
		});

		if (entity.getCapability(CapabilityList.ENERGY_SHIELD).isPresent()) {			
			if ( currES > 0.00f ) {
				if ( currES - dammie > 0 ) {				
					es.ifPresent(cap -> {
						cap.setEnergyShield((float) (currES - dammie));					
						cap.setEnergyShieldRechargeTimer(0.0f);
					});									
					event.setAmount(0);
					event.getSource();				
					if (sourcestring.contains("EntityDamageSource")) {
						sworldin.sendParticles(RegisteredParticles.ENERGY_SHIELD_DAMAGE_INDICATOR.get(), px, py, pz, (int) dammie, vx, vy, vz, 0);
					}			
				}
				if ( currES - dammie <= 0.00f ) {
					es.ifPresent(cap -> {
						cap.setEnergyShield(0);						
						cap.setEnergyShieldRechargeTimer(0.0f);
					});						
					event.setAmount((float) (dammie - currES));
					entity.setHealth((float) (entity.getHealth() - (dammie - currES)));
				}				
				currES = currES - dammie;
				if (currES <= 0) {
					currES = 0.0f;
				}
			}			
		}		
	}
	
}
