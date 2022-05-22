package com.cryotron.skyspaceproject.events.energyshield;

import java.util.List;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.items.armor.SkyspaceEnergyShieldItem;
import com.cryotron.skyspaceproject.setup.deferredregistries.RegisteredItems;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skyspace.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ESArmorTooltipEvent {
	
	/*
	 * TooltipEnergyShieldArmorEvent - Fire this event whenever a player hovers their cursor over a specific item, namely any items that grants Energy Shield instead of armor.
	 */
	
	  @SubscribeEvent
	  public static void TooltipEnergyShieldArmorEvent ( ItemTooltipEvent event ) {

		  ItemStack item = event.getItemStack();
		  List<Component> list = event.getToolTip();
		  
		  if ( item.getItem() == RegisteredItems.ENERGY_SHIELD_TEST_ARMOR.get() && (item.getItem() instanceof SkyspaceEnergyShieldItem)) {
			  SkyspaceEnergyShieldItem ssESitem = (SkyspaceEnergyShieldItem) item.getItem();
	          list.add(new TextComponent("+" + ssESitem.getEnergyShield() + " Energy Shield").withStyle(ChatFormatting.DARK_AQUA));
		  }
	  }
}
