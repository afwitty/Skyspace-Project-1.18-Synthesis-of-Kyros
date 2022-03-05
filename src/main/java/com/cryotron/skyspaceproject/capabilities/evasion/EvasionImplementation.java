package com.cryotron.skyspaceproject.capabilities.evasion;

import net.minecraft.nbt.CompoundTag;

public class EvasionImplementation implements EvasionInterface {

	private static final String NBT_EV = "evasion";
	private float Evasion;

	@Override
	public CompoundTag serializeNBT() {
		final CompoundTag tag = new CompoundTag();
		tag.putFloat(NBT_EV, this.Evasion);
		return null;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.Evasion = nbt.getFloat(NBT_EV);
	}

	@Override
	public float getValue() {
		return Evasion;
	}

	@Override
	public void setValue(float value) {
		this.Evasion = value;		
	}

}
