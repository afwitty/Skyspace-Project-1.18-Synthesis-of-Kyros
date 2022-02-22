package com.cryotron.skyspaceproject;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cryotron.skyspaceproject.misc.StructurePieceCountsManager;
import com.cryotron.skyspaceproject.setup.SkyspaceRegistration;
import com.cryotron.skyspaceproject.setup.SkyspaceSetup;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;


@Mod(Skyspace.ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Skyspace {

	public static final String ID = "skyspaceproject";
	
	public static final Logger LOGGER = LogManager.getLogger(ID);
	
	public static StructurePieceCountsManager structurePieceCountsManager = new StructurePieceCountsManager();
	
	public Skyspace() {
    	//Skyspace Online!
    	LOGGER.info("Skyspace Project Online!");
    	
    	// Registy
    	SkyspaceRegistration.init();
	
    	// PreInit
    	SkyspaceSetup.preInit();
    	
    	// PostInit
    	SkyspaceSetup.postInit();
	}
	
	public static ResourceLocation prefix(String name) {
		return new ResourceLocation(ID, name.toLowerCase(Locale.ROOT));
	}
}



