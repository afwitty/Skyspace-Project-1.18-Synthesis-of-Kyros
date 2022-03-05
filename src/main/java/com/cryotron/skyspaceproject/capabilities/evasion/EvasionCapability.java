package com.cryotron.skyspaceproject.capabilities.evasion;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class EvasionCapability {

	public static final Capability<EvasionInterface> INST = CapabilityManager.get(new CapabilityToken<>() {} );
	
	public static void register(RegisterCapabilitiesEvent event) {
		event.register(EvasionInterface.class);
	}
	
	private EvasionCapability() {}
	
}
