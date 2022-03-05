package com.cryotron.skyspaceproject.capabilities.entropy;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface EntropyInterface extends INBTSerializable<CompoundTag> {
	float getValue();
	
	void setValue(float value);
}
