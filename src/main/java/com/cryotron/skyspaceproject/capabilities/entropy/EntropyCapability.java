package com.cryotron.skyspaceproject.capabilities.entropy;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class EntropyCapability {

	public static final Capability<EntropyInterface> INST = CapabilityManager.get(new CapabilityToken<>() {} );
	
	public static void register(RegisterCapabilitiesEvent event) {
		event.register(EntropyInterface.class);
	}
	
	private EntropyCapability() {}
	
}
