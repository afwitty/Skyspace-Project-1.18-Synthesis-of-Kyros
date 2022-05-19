//package com.cryotron.skyspaceproject.capabilities.energyshield.old;
//
//import org.jetbrains.annotations.NotNull;
//
//import com.cryotron.skyspaceproject.Skyspace;
//import com.cryotron.skyspaceproject.capabilities.energyshield.oldII.EnergyShield;
//import com.cryotron.skyspaceproject.capabilities.energyshield.oldII.EnergyShieldProvider;
//import com.cryotron.skyspaceproject.entities.EnergyShieldEntity;
//import com.cryotron.skyspaceproject.entities.kyrosian_archon.KyrosianArchon;
//
//import net.minecraft.core.Direction;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.capabilities.CapabilityManager;
//import net.minecraftforge.common.capabilities.CapabilityToken;
//import net.minecraftforge.common.capabilities.ICapabilityProvider;
//import net.minecraftforge.common.util.INBTSerializable;
//import net.minecraftforge.common.util.LazyOptional;
//import net.minecraftforge.event.AttachCapabilitiesEvent;
//
//public class EnergyShieldAttacher {
//
//	private static class EnergyShieldProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
//		
//		public static final ResourceLocation ID = new ResourceLocation(Skyspace.ID, "EnergyShield");
//		
//		private final EnergyShieldInterface backend = new EnergyShieldImplementation();
//		private final LazyOptional<EnergyShieldInterface> data = LazyOptional.of(() -> backend);
//		
//		@NotNull
//		@Override
//		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
//			return EnergyShieldCapability.INST.orEmpty(cap, data);
//		}
//		
//		void invalidate() {
//			this.data.invalidate();
//		}
//		
//		@Override
//		public CompoundTag serializeNBT() {
//			return this.backend.serializeNBT();
//		}
//		@Override
//		public void deserializeNBT(CompoundTag nbt) {
//			this.backend.deserializeNBT(nbt);
//			
//		}	
//	}
//	
////	public float getValue() {
////		return EnergyShield;
////	}
////
////	public void setValue(float value) {
////		this.EnergyShield = value;		
////	}
//	
//	public static void attach(final AttachCapabilitiesEvent<LivingEntity> event) {
//        if (event.getObject() instanceof Player) {
//
//        }
//    	
//        if (event.getObject() instanceof LivingEntity) {
//            if (!event.getObject().getCapability(EnergyShieldCapability.INST).isPresent()) {
//                event.addCapability(new ResourceLocation(Skyspace.ID, "energy_shield"), new EnergyShieldProvider());
//            }        	
//        }
//		
//	}
//	
//	private EnergyShieldAttacher() {}
//}
