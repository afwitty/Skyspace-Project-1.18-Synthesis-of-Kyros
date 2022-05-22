//package com.cryotron.skyspaceproject.gui.overlay;
//
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.network.PacketDistributor;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.eventbus.api.EventPriority;
//import net.minecraftforge.client.event.RenderGameOverlayEvent;
//import net.minecraftforge.common.util.LazyOptional;
//import net.minecraftforge.event.TickEvent;
//import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import net.minecraftforge.api.distmarker.Dist;
//
//import net.minecraft.world.level.Level;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//
//import com.cryotron.skyspaceproject.Skyspace;
//import com.cryotron.skyspaceproject.capabilities.CapabilityList;
//import com.cryotron.skyspaceproject.capabilities.energyshield.IEnergyShieldCapability;
//import com.cryotron.skyspaceproject.networking.Messages;
//import com.cryotron.skyspaceproject.networking.energyshield.ClientEnergyShieldData;
//import com.cryotron.skyspaceproject.networking.energyshield.PacketSyncEnergyShield;
////import com.cryotron.skyspaceproject.networking.Messages;
////import com.cryotron.skyspaceproject.networking.PacketEnergyShield;
//import com.cryotron.skyspaceproject.setup.deferredregistries.RegisteredAttributes;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.server.level.ServerPlayer;
//
//@Mod.EventBusSubscriber(modid = Skyspace.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
//public class EnergyShieldDebugHandler {
//	
//	public static float currES, maxES, rechargeCounter, rechargeTime;
//	
//	@SubscribeEvent
//	public static void eventHandler(RenderGameOverlayEvent.Post event) {
//		if (event.getType() == RenderGameOverlayEvent.ElementType.CHAT) {
//			
//			LivingEntity le = Minecraft.getInstance().player;
//			  
//			LazyOptional<IEnergyShieldCapability> es = le.getCapability(CapabilityList.ENERGY_SHIELD);		
//			 
//			maxES = (float) le.getAttributeValue(RegisteredAttributes.MAX_ENERGY_SHIELD.get());
//			rechargeTime = (float) le.getAttributeValue(RegisteredAttributes.ENERGY_SHIELD_RECHARGE.get());		
//				
//			String one = "Current ES: " + currES;
//			String two = "Maximum ES: " + maxES;
//			String three = "Recharge Counter: " + (int) rechargeCounter;
//			String four = ("Recharge Time: " + (int) rechargeTime);
//			
//			//Messages.sendToServer(new PacketEnergyShield());
//			
//			int w = event.getWindow().getGuiScaledWidth();
//			int h = event.getWindow().getGuiScaledHeight();
//			int posX = w / 2;
//			int posY = h / 2;
//			Level _world = null;
//			double _x = 0;
//			double _y = 0;
//			double _z = 0;
//			Player entity = Minecraft.getInstance().player;
//			if (entity != null) {
//				_world = entity.level;
//				_x = entity.getX();
//				_y = entity.getY();
//				_z = entity.getZ();
//			}
//			Level world = _world;
//			double x = _x;
//			double y = _y;
//			double z = _z;
//			if (true) { // x = -90
//				Minecraft.getInstance().font.draw(event.getMatrixStack(), one, posX + -120, posY - 125, -12829636); //+38
//				Minecraft.getInstance().font.draw(event.getMatrixStack(), two, posX + -120, posY - 115, -12829636); //+48
//				Minecraft.getInstance().font.draw(event.getMatrixStack(), three, posX + -120, posY - 105, -12829636); //+58
//				Minecraft.getInstance().font.draw(event.getMatrixStack(), four, posX + -120, posY - 95, -12829636); //+68
//			}
//		}
//	}
//	
//	/*
//	 * For each World Tick, update the Current ES and Recharge Counter.
//	 */
//
//
//	@SubscribeEvent
//    public static void onWorldTick(TickEvent.WorldTickEvent event) {
//        // Don't do anything client side
//        if (event.world.isClientSide) {
//            return;
//        }
//        if (event.phase == TickEvent.Phase.START) {
//            return;
//        }
//        tick(event.world);
//    }
//	
//	public static void tick(Level level) {
//		level.players().forEach(player -> {
//
//					LazyOptional<IEnergyShieldCapability> es = player.getCapability(CapabilityList.ENERGY_SHIELD);					
//					
//					es.ifPresent(cap -> {
//						currES = cap.getEnergyShield();
//						rechargeCounter = cap.getEnergyShieldRechargeTimer();
//					});								
//		});
//	}
//}
