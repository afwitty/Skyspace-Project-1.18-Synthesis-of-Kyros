//package com.cryotron.skyspaceproject.capabilities.energyshield.oldII;
//
//import com.cryotron.skyspaceproject.Skyspace;
//
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
//import net.minecraftforge.event.AttachCapabilitiesEvent;
//import net.minecraftforge.event.TickEvent;
//import net.minecraftforge.event.entity.living.LivingDamageEvent;
//import net.minecraftforge.event.entity.player.PlayerEvent;
//
//public class EnergyShieldEvents {
//
//    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<LivingEntity> event){
//        if (event.getObject() instanceof Player) {
//
//        }
//    	
//        if (event.getObject() instanceof LivingEntity) {
//            if (!event.getObject().getCapability(EnergyShieldProvider.ES).isPresent()) {
//                event.addCapability(new ResourceLocation(Skyspace.ID, "energy_shield"), new EnergyShieldProvider());
//            }        	
//        }
//    }
//
//    public static void onPlayerCloned(PlayerEvent.Clone event) {
//        if (event.isWasDeath()) {
//            // We need to copyFrom the capabilities
//
//        }
//    }
//
//    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
//    	event.register(EnergyShield.class);
//    }
//    
//    public static void onWorldTick(TickEvent.WorldTickEvent event) {
//        // Don't do anything client side
//        if (event.world.isClientSide) {
//            return;
//        }
//        if (event.phase == TickEvent.Phase.START) {
//            return;
//        }
//        //ManaManager manager = ManaManager.get(event.world);
//        //manager.tick(event.world);
//    }
//    
//}
