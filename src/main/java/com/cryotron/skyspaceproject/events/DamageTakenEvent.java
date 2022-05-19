package com.cryotron.skyspaceproject.events;

import java.util.Random;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.capabilities.CapabilityList;
import com.cryotron.skyspaceproject.capabilities.energyshield.EnergyShieldCapabilityHandler;
import com.cryotron.skyspaceproject.capabilities.energyshield.IEnergyShieldCapability;
import com.cryotron.skyspaceproject.entities.EnergyShieldEntity;
import com.cryotron.skyspaceproject.setup.CustomisableParticleType;
import com.cryotron.skyspaceproject.setup.ESParticle;
import com.cryotron.skyspaceproject.setup.SkyspaceRegistration;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.CritParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skyspace.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class DamageTakenEvent {
	
//	static Attribute ENERGY_SHIELD;
	static Attribute EVASION;
	static Attribute ACCURACY;
	static Double ENERGY_SHIELD_VALUE ;
	static Double EVASION_VALUE;
	static Double ACCURACY_VALUE;
    static Random rand = new Random();
    static int hundred_sided_die = 0;

	@SubscribeEvent
	public static void AttackEvent(LivingAttackEvent event )  {
		LivingEntity entity = event.getEntityLiving();
		double dammie = event.getAmount();
//		Skyspace.LOGGER.info("LivingAttackEvent Fired! Entity fired: " + entity.getName().getString() + " for " + dammie + " damage.");
		ACCURACY_VALUE = 1.0;	// Placeholder Value
		
		// Avoidance
		if ( entity.getAttribute(SkyspaceRegistration.EVASION.get()) != null ) {
			
			Skyspace.LOGGER.info(entity.getName().getString() + " Evasion Amount: " + entity.getAttributeValue(SkyspaceRegistration.EVASION.get()));			
			
			
			// If the Damage Source is Magic...
			if (event.getSource().isMagic() == true) {
				Skyspace.LOGGER.info("Damage Source is magical and cannot be evaded.");				
			}
			
			// If the Damage Source is Environmental...
			if (event.getSource().isExplosion() || event.getSource().isFall() || event.getSource().isFire() || event.getSource().isFire() == true) {
				Skyspace.LOGGER.info("Damage Source environmental and cannot be evaded.");					
			}
			
			// Else...
			if (event.getSource().isMagic() || event.getSource().isExplosion() || event.getSource().isFall() || event.getSource().isFire() || event.getSource().isFire() 
					|| event.getSource().isBypassArmor() ||  event.getSource().isBypassInvul() || event.getSource().isBypassMagic() == false) {
				
				EVASION_VALUE = entity.getAttributeValue(SkyspaceRegistration.EVASION.get());
				double Evasion_Chance = EVASION_VALUE/ACCURACY_VALUE;
				hundred_sided_die = rand.nextInt(100);
				
				if ((hundred_sided_die + Evasion_Chance) > 100) {
					Skyspace.LOGGER.info("Entity successfully evades a hit.");
					dammie = 0;
					event.setCanceled(true);
				}
				else {
					Skyspace.LOGGER.info("Entity fails to evade a hit.");
				}
				
			}
			
		}
		
	}

	@SubscribeEvent
	public static void DamageEvent(LivingDamageEvent event)  {
		LivingEntity entity = event.getEntityLiving();		
		float dammie = event.getAmount();
		Level worldin = event.getEntity().level;
		ServerLevel sworldin = (ServerLevel) event.getEntity().level;
		int randNum = rand.nextInt(2);
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
				
		es.ifPresent(cap -> {
			ENERGY_SHIELD_VALUE = (double) cap.getEnergyShield();
		});;
			
		Skyspace.LOGGER.info("LivingDamageEvent Fired! Entity fired: " + entity.getName().getString() + " for " + dammie + " damage in " + blockpos.getX() + ", " + blockpos.getY() + ", " + blockpos.getZ() + " in " + worldin.dimension().toString());

		if (entity.getCapability(CapabilityList.ENERGY_SHIELD).isPresent()) {
			
			Skyspace.LOGGER.info("HIT: Entity's Energy Shield Value is... " + ENERGY_SHIELD_VALUE );
			Skyspace.LOGGER.info("HIT: Entity's Health Value is... " + entity.getHealth() );
			
			if ( ENERGY_SHIELD_VALUE > 0.00f ) {
				if ( ENERGY_SHIELD_VALUE - dammie > 0 ) {
					
					
					es.ifPresent(cap -> {
						cap.setEnergyShield((float) (ENERGY_SHIELD_VALUE - dammie));						
					});					
					
					event.setAmount(0);
					event.getSource();
				
					
					Skyspace.LOGGER.info("Damage Source is: " + event.getSource());
					
					if (sourcestring.contains("EntityDamageSource")) {
						sworldin.sendParticles(SkyspaceRegistration.ENERGY_SHIELD_DAMAGE_INDICATOR.get(), px, py, pz, (int) dammie, vx, vy, vz, 0);
					}
					
				}
				if ( ENERGY_SHIELD_VALUE - dammie <= 0.00f ) {
					es.ifPresent(cap -> {
						cap.setEnergyShield(0);						
					});		
					
					event.setAmount((float) (dammie - ENERGY_SHIELD_VALUE));
					
					Skyspace.LOGGER.info("RESOLUTION: Shield Depleted! The remaining damage value is... " + (dammie - ENERGY_SHIELD_VALUE) );					
					entity.setHealth((float) (entity.getHealth() - (dammie - ENERGY_SHIELD_VALUE)));

				}
				
				ENERGY_SHIELD_VALUE = ENERGY_SHIELD_VALUE - dammie;
				if (ENERGY_SHIELD_VALUE <= 0) {
					ENERGY_SHIELD_VALUE = 0.0d;
				}
				Skyspace.LOGGER.info("RESOLUTION: Entity's Energy Shield Value is... " + ENERGY_SHIELD_VALUE );
				Skyspace.LOGGER.info("RESOLUTION: Entity's Health Value is... " + entity.getHealth() );
			}

				
		}
		
	}
}
