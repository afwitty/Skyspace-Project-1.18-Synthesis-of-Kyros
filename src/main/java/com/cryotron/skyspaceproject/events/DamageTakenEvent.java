package com.cryotron.skyspaceproject.events;

import java.util.Random;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.entities.EnergyShieldEntity;
import com.cryotron.skyspaceproject.setup.SkyspaceRegistration;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skyspace.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class DamageTakenEvent {
	
	static Attribute ENERGY_SHIELD;
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

		double dammie = event.getAmount();
		
		Skyspace.LOGGER.info("LivingDamageEvent Fired! Entity fired: " + entity.getName().getString() + " for " + dammie + " damage.");

		if ( entity.getAttribute(SkyspaceRegistration.MAX_ENERGY_SHIELD.get()) != null) {
			
			Skyspace.LOGGER.info("Energy Shield instance of test: " + (entity instanceof EnergyShieldEntity));

			
//			if (entity instanceof SkyspaceEntity) {
//				Skyspace.LOGGER.info("Shield before hit:  " + SSEntity.getEnergyShieldValue() + " Energy Shield.");
//			}
			
			//Skyspace.LOGGER.info("Damaged Entity has Energy Shield of... " + entity.getAttributeValue(SkyspaceRegistration.MAX_ENERGY_SHIELD.get()));
//			Skyspace.LOGGER.info("Shield before hit:  " +  + " Energy Shield.");
//			if (((SkyspaceEntity) entity).getEnergyShieldValue() > 0) {
//				((SkyspaceEntity) entity).setEnergyShieldValue(((SkyspaceEntity) entity).getEnergyShieldValue() - dammie);
//				dammie = 0;
//			}
//			Skyspace.LOGGER.info("Shield after hit:  " + (((SkyspaceEntity) entity).getEnergyShieldValue()) + " Energy Shield.");
		}
		//Skyspace.LOGGER.info("Entity Resolution:  " + (entity.getHealth()-dammie) + " Health.");
		
	}
	
}
