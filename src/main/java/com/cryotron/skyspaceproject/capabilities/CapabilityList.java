package com.cryotron.skyspaceproject.capabilities;

import javax.annotation.Nonnull;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.capabilities.energyshield.EnergyShieldCapabilityHandler;
import com.cryotron.skyspaceproject.capabilities.energyshield.IEnergyShieldCapability;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

import javax.annotation.Nonnull;

public class CapabilityList {

	// Entity Capabilities
	public static final Capability<IEnergyShieldCapability> ENERGY_SHIELD = CapabilityManager.get(new CapabilityToken<>(){});

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
    	event.register(IEnergyShieldCapability.class);
    }
	
    
    /*
     * ATTACH ENERGY SHIELD
     */
	public static void attachEnergyShieldEntityCapability(AttachCapabilitiesEvent<Entity> e) {
		if (e.getObject() instanceof LivingEntity) {
			
			e.addCapability(IEnergyShieldCapability.ID, new ICapabilitySerializable<CompoundTag>() {

				LazyOptional<IEnergyShieldCapability> inst = LazyOptional.of(() -> {
					EnergyShieldCapabilityHandler i = new EnergyShieldCapabilityHandler();
					i.setEntity((LivingEntity) e.getObject());
					return i;
				});

				@Nonnull
				@Override
				public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing) {
					return ENERGY_SHIELD.orEmpty(capability, inst.cast());
				}

				@Override
				public CompoundTag serializeNBT() {
					return inst.orElseThrow(NullPointerException::new).serializeNBT();
				}

				@Override
				public void deserializeNBT(CompoundTag nbt) {
					inst.orElseThrow(NullPointerException::new).deserializeNBT(nbt);
				}
			});
		}
	}

}
