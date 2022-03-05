package com.cryotron.skyspaceproject.capabilities.energyshield;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.setup.SkyspaceRegistration;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

public class EnergyShieldCapabilityHandler implements IEnergyShieldCapability {
	
	
	public LivingEntity host;
	public float energyShield = 0;
	
	static EnergyShieldCapabilityHandler eshand = new EnergyShieldCapabilityHandler();
	
	public static float getEnergyShieldValue() {
		return eshand.getEnergyShield();
	}
	
	public static void setEnergyShieldValue(float amt) {
		eshand.setEnergyShield(amt);
	}

	@Override
	public void setEntity(LivingEntity entity) {
		host = entity;
		
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void initEnergyShield(float es) {
			energyShield = es;
	}
	
	@Override
	public float getEnergyShield() {
		// TODO Auto-generated method stub
		return energyShield;
	}
	
	@Override
	public void setEnergyShield(float amount) {
		// TODO Auto-generated method stub
		energyShield = amount;
		
		
	}
	
//	@Override
//	public void addEnergyShield(float amount) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		tag.putFloat("energy_shield", this.getEnergyShield());
		return tag;
	}
	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.initEnergyShield(nbt.getFloat("energy_shield"));
	}
	

}
