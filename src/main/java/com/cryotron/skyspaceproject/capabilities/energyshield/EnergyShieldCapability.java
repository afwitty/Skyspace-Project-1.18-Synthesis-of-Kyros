package com.cryotron.skyspaceproject.capabilities.energyshield;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class EnergyShieldCapability {

	public static final Capability<EnergyShieldInterface> INST = CapabilityManager.get(new CapabilityToken<>() {} );
	
	public static void register(RegisterCapabilitiesEvent event) {
		event.register(EnergyShieldInterface.class);
	}
	
	private EnergyShieldCapability() {}
	
}
