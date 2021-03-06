package com.cryotron.skyspaceproject.capabilities.energyshield;

import com.cryotron.skyspaceproject.Skyspace;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.INBTSerializable;

public interface IEnergyShieldCapability extends INBTSerializable<CompoundTag> {
	
	ResourceLocation ID = new ResourceLocation(Skyspace.ID, "energy_shield");
	ResourceLocation ID2 = new ResourceLocation(Skyspace.ID, "energy_shield_recharge_timer");

	void setEntity(LivingEntity entity);

	void update();

//	int shieldsLeft();
//
//	void initEnergyShield(float es);
	
	float getEnergyShield();
	
//	int temporaryShieldsLeft();
//
//	int permanentShieldsLeft();
//
//	void breakShield();
//
//	void replenishShield();

	void setEnergyShield(float amount);
	
	float getEnergyShieldRechargeTimer();
	
	void setEnergyShieldRechargeTimer(float amount);

//    void addEnergyShield(float amount);


    
}
