package com.cryotron.skyspaceproject.capabilities.energyshield;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.setup.deferredregistries.SkyspaceRegistration;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class EnergyShieldCapabilityHandler implements IEnergyShieldCapability {
	
	private LivingEntity host;
	private float energyShield;
	private float energyShieldRechargeTimer;

	@Override
	public void setEntity(LivingEntity entity) {
		host = entity;
		
	}
	@Override
	public void update() {

	}
	
	@Override
	public float getEnergyShield() {
		return energyShield;
	}
	
	@Override
	public void setEnergyShield(float amount) {
		energyShield = amount;
	}
	
	@Override
	public float getEnergyShieldRechargeTimer() {
		return energyShieldRechargeTimer;
	}
	
	@Override
	public void setEnergyShieldRechargeTimer(float amount) {
		energyShieldRechargeTimer = amount;
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		tag.putFloat("energy_shield", this.getEnergyShield());
		tag.putFloat("energy_shield_recharge_timer", this.getEnergyShieldRechargeTimer());
		return tag;
	}
	
	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.setEnergyShield(nbt.getFloat("energy_shield"));
		this.setEnergyShield(nbt.getFloat("energy_shield_recharge_timer"));
	}


}
