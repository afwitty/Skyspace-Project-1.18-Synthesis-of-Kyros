//package com.cryotron.skyspaceproject.events;
//
//import java.util.UUID;
//
//import com.cryotron.skyspaceproject.Skyspace;
//import com.cryotron.skyspaceproject.capabilities.CapabilityList;
//import com.cryotron.skyspaceproject.capabilities.energyshield.IEnergyShieldCapability;
//import com.cryotron.skyspaceproject.setup.deferredregistries.RegisteredAttributes;
//
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.ai.attributes.AttributeModifier;
//import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.common.util.LazyOptional;
//import net.minecraftforge.event.TickEvent;
//import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//
//@Mod.EventBusSubscriber(modid = Skyspace.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
//public class GeneralEventHandlers {
//
//	  public static final int WAIT_INTERVAL = 40;
//	  public static int COUNTER = WAIT_INTERVAL;
//
//	  @SubscribeEvent
//	  public static void EventCounter( TickEvent.ServerTickEvent event ) {
//	    if (COUNTER <= 0) {
//	    	COUNTER = WAIT_INTERVAL;
//	    	//Skyspace.LOGGER.info("One second ticked!");
//	        return;
//	    } else {
//	        --COUNTER;
//	    }
//	}
//	  
//	  public static boolean OneSecondTicked() {
//		  if (WAIT_INTERVAL == COUNTER) {
//			  
//			  return true;
//		  } else {
//			  return false;
//		  }
//	  }
//	
//	  /*
//	   * UpdateAllAttributes - Experimental Event that pushes all registered attributes into datas of all living entities.
//	   */
//
////	  @SubscribeEvent
////	  public static void UpdateAllAttributes ( LivingUpdateEvent event ) {
////		  AttributeModifier am, rc;
////		  LivingEntity le = event.getEntityLiving();
////		  UUID uuid = UUID.fromString("a91ec624-a0cf-4f62-9b08-8986cd9f3ffd");
////		  UUID uuid2 = UUID.fromString("56ccdac4-7528-4d7e-a48d-421fd8392961");
////		  
////		  am = new AttributeModifier(uuid, "General", 0, Operation.ADDITION);
////		  rc = new AttributeModifier(uuid2, "Energy_Shield_Recharge_Time", 4.0f, Operation.ADDITION);
////		  
////		  // Energy Shield
////		  if ((le.getAttributes().hasAttribute(RegisteredAttributes.MAX_ENERGY_SHIELD.get()))) {
////				le.getAttribute(RegisteredAttributes.MAX_ENERGY_SHIELD.get()).addPermanentModifier(am);	
////			  	le.getAttribute(RegisteredAttributes.MAX_ENERGY_SHIELD.get()).removeModifier(uuid);
////		  }
////		  if ((le.getAttributes().hasAttribute(RegisteredAttributes.ENERGY_SHIELD_RECHARGE.get()))) {
////			  	le.getAttribute(RegisteredAttributes.ENERGY_SHIELD_RECHARGE.get()).addPermanentModifier(rc);
////			  	le.getAttribute(RegisteredAttributes.ENERGY_SHIELD_RECHARGE.get()).removeModifier(uuid2);		  
////		  }
////		  
////		  // Evasion
////		  if ((le.getAttributes().hasAttribute(RegisteredAttributes.EVASION.get()))) {
////				le.getAttribute(RegisteredAttributes.EVASION.get()).addPermanentModifier(am);	
////			  	le.getAttribute(RegisteredAttributes.EVASION.get()).removeModifier(uuid);
////		  }
////		  if ((le.getAttributes().hasAttribute(RegisteredAttributes.ACCURACY.get()))) {
////			  	le.getAttribute(RegisteredAttributes.ACCURACY.get()).addPermanentModifier(am);
////			  	le.getAttribute(RegisteredAttributes.ACCURACY.get()).removeModifier(uuid);		  
////		  }
////		  
////	  }
//	  
//}
