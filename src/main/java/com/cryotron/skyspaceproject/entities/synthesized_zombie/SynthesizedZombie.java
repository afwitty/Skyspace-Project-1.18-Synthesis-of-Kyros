package com.cryotron.skyspaceproject.entities.synthesized_zombie;

import com.cryotron.skyspaceproject.setup.SkyspaceRegistration;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

public class SynthesizedZombie extends Zombie {

	public SynthesizedZombie(EntityType<? extends Zombie> p_34271_, Level p_34272_) {
		super(p_34271_, p_34272_);
		// TODO Auto-generated constructor stub
	}

	   public static AttributeSupplier.Builder createAttributes() {
		      return Monster.createMonsterAttributes()
		    		  .add(Attributes.MAX_HEALTH, 20.0D)
		    		  .add(SkyspaceRegistration.MAX_ENERGY_SHIELD.get(), 20.0D)
		    		  .add(Attributes.FOLLOW_RANGE, 32.0D)
		    		  .add(Attributes.MOVEMENT_SPEED, (double)0.20F)
		    		  .add(Attributes.ATTACK_DAMAGE, 4.0D)
		    		  .add(Attributes.ARMOR, 4.0D)
		    		  .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
		   }

	
	   @Override
	   public SoundEvent getAmbientSound() {
		   return SkyspaceRegistration.ENTITY_KYROSIAN_ZOMBIE_AMBIENT.get();
	   }

	   @Override
	   public SoundEvent getHurtSound(DamageSource p_34404_) {
		   return SkyspaceRegistration.ENTITY_KYROSIAN_ZOMBIE_HURT.get();
	   }

	   @Override
	   public SoundEvent getDeathSound() {
		   return SkyspaceRegistration.ENTITY_KYROSIAN_ZOMBIE_DEATH.get();
	   }
	   
	   @Override
	   public SoundEvent getStepSound() {
		   return SkyspaceRegistration.ENTITY_KYROSIAN_ZOMBIE_STEP.get();
	   }

}
