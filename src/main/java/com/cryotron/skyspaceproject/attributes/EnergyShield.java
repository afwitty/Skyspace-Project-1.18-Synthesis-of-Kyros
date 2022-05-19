package com.cryotron.skyspaceproject.attributes;

import com.cryotron.skyspaceproject.setup.SkyspaceRegistration;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class EnergyShield extends Attributes {
	
	
	
	   private final double minValue;
	   private final double maxValue;

	   public EnergyShield(String p_22310_, double p_22311_, double p_22312_, double p_22313_) {
	      super();
	      this.minValue = p_22312_;
	      this.maxValue = p_22313_;
	      if (p_22312_ > p_22313_) {
	         throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
	      } else if (p_22311_ < p_22312_) {
	         throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
	      } else if (p_22311_ > p_22313_) {
	         throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
	      }
	   }

	   public double getMinValue() {
	      return this.minValue;
	   }

	   public double getMaxValue() {
	      return this.maxValue;
	   }

	   public double sanitizeValue(double p_22315_) {
	      return Mth.clamp(p_22315_, this.minValue, this.maxValue);
	   }
	   

	   
	   
//	   public void removeAttributeModifiers(LivingEntity p_19417_, AttributeMap p_19418_, int p_19419_) {
//		      p_19417_.setAbsorptionAmount(p_19417_.getAbsorptionAmount() - (float)(4 * (p_19419_ + 1)));
//		      super.removeAttributeModifiers(p_19417_, p_19418_, p_19419_);
//		   }
//
//		   public void addAttributeModifiers(LivingEntity p_19421_, AttributeMap p_19422_, int p_19423_) {
//		      p_19421_.setAbsorptionAmount(p_19421_.getAbsorptionAmount() + (float)(4 * (p_19423_ + 1)));
//		      super.addAttributeModifiers(p_19421_, p_19422_, p_19423_);
//		   }
}
