package com.cryotron.skyspaceproject.entities.kyrosian_enforcer;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.entities.kyrosian_deacon.KyrosianDeacon;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class KyrosianEnforcerModel  <T extends KyrosianEnforcer> extends AnimatedGeoModel<T> {
	@Override
	public ResourceLocation getAnimationFileLocation(T animatable) {
		// TODO Auto-generated method stub
		return new ResourceLocation(Skyspace.ID, "animations/enforcer.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(T object) {
		// TODO Auto-generated method stub
		return new ResourceLocation(Skyspace.ID, "geo/enforcer.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(T object) {
		// TODO Auto-generated method stub
		return new ResourceLocation(Skyspace.ID, "textures/entity/kyrosian_enforcer.png");
	}
}
