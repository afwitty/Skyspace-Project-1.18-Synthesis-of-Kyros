package com.cryotron.skyspaceproject.capabilities.evasion;

import org.jetbrains.annotations.NotNull;

import com.cryotron.skyspaceproject.Skyspace;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class EvasionAttacher {

	private static class EvasionProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
		
		public static final ResourceLocation ID = new ResourceLocation(Skyspace.ID, "Evasion");
		
		private final EvasionInterface backend = new EvasionImplementation();
		private final LazyOptional<EvasionInterface> data = LazyOptional.of(() -> backend);
		
		@NotNull
		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return EvasionCapability.INST.orEmpty(cap, data);
		}
		
		void invalidate() {
			this.data.invalidate();
		}
		
		@Override
		public CompoundTag serializeNBT() {
			return this.backend.serializeNBT();
		}
		@Override
		public void deserializeNBT(CompoundTag nbt) {
			this.backend.deserializeNBT(nbt);
			
		}

		
	}
	
	public static void attach(final AttachCapabilitiesEvent<LivingEntity> event) {
//		if (!(event.getObject() instanceof EvasionEntity)) {
//			Skyspace.LOGGER.info("EvasionEntity is not an instance of a LivingEntity.");
//			return;
//		}
		
		
	}
	
	private EvasionAttacher() {}
}
