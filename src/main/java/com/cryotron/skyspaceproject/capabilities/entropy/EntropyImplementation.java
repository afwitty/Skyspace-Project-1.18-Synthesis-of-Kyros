package com.cryotron.skyspaceproject.capabilities.entropy;

import net.minecraft.nbt.CompoundTag;

public class EntropyImplementation implements EntropyInterface {

	private static final String NBT_EN = "entropy";
	private float Entropy;

	@Override
	public CompoundTag serializeNBT() {
		final CompoundTag tag = new CompoundTag();
		tag.putFloat(NBT_EN, this.Entropy);
		return null;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.Entropy = nbt.getFloat(NBT_EN);
	}

	@Override
	public float getValue() {
		return Entropy;
	}

	@Override
	public void setValue(float value) {
		this.Entropy = value;		
	}

}
