package com.cryotron.skyspaceproject.capabilities.evasion;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface EvasionInterface extends INBTSerializable<CompoundTag> {
	float getValue();
	
	void setValue(float value);
}
