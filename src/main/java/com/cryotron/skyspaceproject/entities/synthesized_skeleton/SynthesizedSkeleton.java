package com.cryotron.skyspaceproject.entities.synthesized_skeleton;

import com.cryotron.skyspaceproject.setup.SkyspaceRegistration;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SynthesizedSkeleton extends AbstractSkeleton {

	public SynthesizedSkeleton(EntityType<? extends SynthesizedSkeleton> p_32133_, Level p_32134_) {
		super(p_32133_, p_32134_);
		// TODO Auto-generated constructor stub
	}

	   public static AttributeSupplier.Builder createAttributes() {
		      return Monster.createMonsterAttributes()
		    		  .add(Attributes.MAX_HEALTH, 20.0D)
		    		  .add(SkyspaceRegistration.EVASION.get(), 20.0D)
		    		  .add(Attributes.FOLLOW_RANGE, 32.0D)
		    		  .add(Attributes.MOVEMENT_SPEED, (double)0.20F);
		   }
	
	   @Override
	   public SoundEvent getAmbientSound() {
		   return SkyspaceRegistration.ENTITY_KYROSIAN_SKELETON_AMBIENT.get();
	   }

	   @Override
	   public SoundEvent getHurtSound(DamageSource p_34404_) {
		   return SkyspaceRegistration.ENTITY_KYROSIAN_SKELETON_HURT.get();
	   }

	   @Override
	   public SoundEvent getDeathSound() {
		   return SkyspaceRegistration.ENTITY_KYROSIAN_SKELETON_DEATH.get();
	   }
	   
	   @Override
	   public SoundEvent getStepSound() {
		   return SkyspaceRegistration.ENTITY_KYROSIAN_SKELETON_STEP.get();
	   }


	   protected AbstractArrow getArrow(ItemStack p_33846_, float p_33847_) {
		      AbstractArrow abstractarrow = super.getArrow(p_33846_, p_33847_);
		      if (abstractarrow instanceof Arrow) {
		         ((Arrow)abstractarrow).addEffect(new MobEffectInstance(MobEffects.HARM,1));
		      }

		      return abstractarrow;
		   }
	
}
