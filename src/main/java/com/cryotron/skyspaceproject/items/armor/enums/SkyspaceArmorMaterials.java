package com.cryotron.skyspaceproject.items.armor.enums;

import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;

import java.util.function.Supplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.crafting.Ingredient;

public enum SkyspaceArmorMaterials implements ArmorMaterial {
			NONE("none", 1, new int[]{0, 0, 0, 0}, new int[] {0,0,0,0}, 1, SoundEvents.ARMOR_EQUIP_GENERIC, 0.0F, 0.0F, () -> {
		      return Ingredient.of(Items.LAPIS_LAZULI);
		   }),
			TEST("test", 33, new int[]{0, 0, 0, 0}, new int[] {3,6,32,3}, 12, SoundEvents.ARMOR_EQUIP_DIAMOND, 0.0F, 0.0F, () -> {
			  return Ingredient.of(Items.DIAMOND);
		    });

		   private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
		   private final String name;
		   private final int durabilityMultiplier;
		   
		   private final int[] slotArmor;
		   private final int[] slotEnergyShield;
		   
		   private final int enchantmentValue;
		   private final SoundEvent sound;
		   private final float toughness;
		   private final float knockbackResistance;
		   private final LazyLoadedValue<Ingredient> repairIngredient;

		   private SkyspaceArmorMaterials(String name, int mul, int[] armor, int[] es, int enchant, SoundEvent equipsound, float toughness, float knockbackres, Supplier<Ingredient> repair) {
		      this.name = name;
		      this.durabilityMultiplier = mul;
		      
		      this.slotArmor = armor;
		      this.slotEnergyShield = es;
		      
		      this.enchantmentValue = enchant;
		      this.sound = equipsound;
		      
		      this.toughness = toughness;
		      this.knockbackResistance = knockbackres;
		      
		      this.repairIngredient = new LazyLoadedValue<>(repair);
		   }


		public int getDurabilityForSlot(EquipmentSlot pSlot) {
		      return HEALTH_PER_SLOT[pSlot.getIndex()] * this.durabilityMultiplier;
		   }

		public int getDefenseForSlot(EquipmentSlot pSlot) {
		      return this.slotArmor[pSlot.getIndex()];
		}
		   
		public int getEnergyShieldForSlot(EquipmentSlot pSlot) {
			  return this.slotEnergyShield[pSlot.getIndex()];
		}

		   public int getEnchantmentValue() {
		      return this.enchantmentValue;
		   }

		   public SoundEvent getEquipSound() {
		      return this.sound;
		   }

		   public Ingredient getRepairIngredient() {
		      return this.repairIngredient.get();
		   }

		   public String getName() {
		      return this.name;
		   }

		   public float getToughness() {
		      return this.toughness;
		   }

		   /**
		    * Gets the percentage of knockback resistance provided by armor of the material.
		    */
		   public float getKnockbackResistance() {
		      return this.knockbackResistance;
		   }
}
