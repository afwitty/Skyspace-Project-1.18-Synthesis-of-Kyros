package com.cryotron.skyspaceproject.registers;

import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SkyspaceIcon {
	  public static final Item gem_icon = null;

	  @SubscribeEvent
	  public static void registerItems(final RegistryEvent.Register<Item> event)
	  {

	    event.getRegistry().register(new Item(new Item.Properties()).setRegistryName("gem_icon"));

	  }
}
