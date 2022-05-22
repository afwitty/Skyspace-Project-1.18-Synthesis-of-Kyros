package com.cryotron.skyspaceproject.events.energyshield;

import java.util.UUID;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.items.armor.SkyspaceEnergyShieldItem;
import com.cryotron.skyspaceproject.setup.deferredregistries.RegisteredAttributes;
import com.cryotron.skyspaceproject.setup.deferredregistries.RegisteredItems;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skyspace.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ESArmorEquipEvent {
    /*
     * EquipEnergyShieldArmorEvent - Fires this event whenever a living entity changes their equipment.
     * 		 While any living entity can do this, this event is fired from players most. Mainly firing from any armor that uses Energy Shield to add Maximum Energy Shield Modifier.
     */
    
	@SubscribeEvent
	public static void EquipEnergyShieldArmorEvent (LivingEquipmentChangeEvent event) {
		
		ItemStack equipped = event.getTo();
		ItemStack unequipped = event.getFrom();

		//EquipmentSlot slot = event.getSlot();
		LivingEntity le = event.getEntityLiving();
		UUID uuid = UUID.fromString("934cc13f-d185-40ef-9440-b2b2cbbcdee5");
		event.getSlot().getIndex();
		
		//Skyspace.LOGGER.info("Slot Index Fired: " + event.getSlot().getIndex());
		
		SkyspaceEnergyShieldItem ssESitem;
		AttributeModifier am;

		if  ( unequipped.getItem() instanceof SkyspaceEnergyShieldItem && ( event.getSlot().getIndex() != 0 )) {
			if (unequipped.getItem() == RegisteredItems.ENERGY_SHIELD_TEST_ARMOR.get()) {			
				Skyspace.LOGGER.info("Energy Shield Test Armor Unequipped! Unequipped Item: " + unequipped.getItem());
				le.getAttribute(RegisteredAttributes.MAX_ENERGY_SHIELD.get()).removeModifier(uuid);
			}
		}
		
		if (equipped.getItem() instanceof SkyspaceEnergyShieldItem && ( event.getSlot().getIndex() != 0 ) ) {
				ssESitem = (SkyspaceEnergyShieldItem) equipped.getItem();
				am = new AttributeModifier(uuid, "energy_shield_modifier", ssESitem.getEnergyShield(), Operation.ADDITION);
				if (equipped.getItem() == RegisteredItems.ENERGY_SHIELD_TEST_ARMOR.get()) {	
					Skyspace.LOGGER.info("Energy Shield Test Armor Equipped! Equipped Item: " + equipped.getItem());
					if ( !(le.getAttribute(RegisteredAttributes.MAX_ENERGY_SHIELD.get()).hasModifier(am))) {
						le.getAttribute(RegisteredAttributes.MAX_ENERGY_SHIELD.get()).addPermanentModifier(am);
					}
				}
			
		}

	}
	
}
