package com.cryotron.skyspaceproject.items.armor.interfaces;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.crafting.Ingredient;

public interface EvasionMaterial {
	   int getDurabilityForSlot(EquipmentSlot pSlot);

	   int getEvasionForSlot(EquipmentSlot pSlot);

	   int getEnchantmentValue();

	   SoundEvent getEquipSound();

	   Ingredient getRepairIngredient();

	   String getName();
}
