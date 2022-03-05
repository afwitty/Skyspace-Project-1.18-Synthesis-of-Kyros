package com.cryotron.skyspaceproject.capabilities.energyshield;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface EnergyShieldInterface extends INBTSerializable<CompoundTag> {
	float getValue();
	
	void setValue(float value);
}
