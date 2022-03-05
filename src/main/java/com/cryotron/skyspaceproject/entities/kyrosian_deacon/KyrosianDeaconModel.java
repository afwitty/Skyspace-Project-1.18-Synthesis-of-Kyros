package com.cryotron.skyspaceproject.entities.kyrosian_deacon;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.entities.kyrosian_archon.KyrosianArchon;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class KyrosianDeaconModel   <T extends KyrosianDeacon> extends AnimatedGeoModel<T>  {
	@Override
	public ResourceLocation getAnimationFileLocation(T animatable) {
		// TODO Auto-generated method stub
		return new ResourceLocation(Skyspace.ID, "animations/deacon.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(T object) {
		// TODO Auto-generated method stub
		return new ResourceLocation(Skyspace.ID, "geo/deacon.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(T object) {
		// TODO Auto-generated method stub
		return new ResourceLocation(Skyspace.ID, "textures/entity/kyrosian_deacon.png");
	}
}
