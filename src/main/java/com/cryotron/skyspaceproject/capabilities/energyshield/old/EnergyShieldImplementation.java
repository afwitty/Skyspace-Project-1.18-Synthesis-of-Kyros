//package com.cryotron.skyspaceproject.capabilities.energyshield.old;
//
//import net.minecraft.nbt.CompoundTag;
//
//public class EnergyShieldImplementation implements EnergyShieldInterface {
//
//	private static final String NBT_ES = "energy_shield";
//	private float EnergyShield;
//
//	@Override
//	public CompoundTag serializeNBT() {
//		final CompoundTag tag = new CompoundTag();
//		tag.putFloat(NBT_ES, this.EnergyShield);
//		return null;
//	}
//
//	@Override
//	public void deserializeNBT(CompoundTag nbt) {
//		this.EnergyShield = nbt.getFloat(NBT_ES);
//	}
//
//	@Override
//	public float getValue() {
//		return EnergyShield;
//	}
//
//	@Override
//	public void setValue(float value) {
//		this.EnergyShield = value;		
//	}
//
//}
