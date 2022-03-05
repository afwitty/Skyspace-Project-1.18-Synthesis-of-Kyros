package com.cryotron.skyspaceproject.entities.kyrosian_mutilator;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.entities.kyrosian_enforcer.KyrosianEnforcer;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class KyrosianMutilatorModel  <T extends KyrosianMutilator> extends AnimatedGeoModel<T>  {
	@Override
	public ResourceLocation getAnimationFileLocation(T animatable) {
		// TODO Auto-generated method stub
		return new ResourceLocation(Skyspace.ID, "animations/mutilator.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(T object) {
		// TODO Auto-generated method stub
		return new ResourceLocation(Skyspace.ID, "geo/mutilator.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(T object) {
		// TODO Auto-generated method stub
		return new ResourceLocation(Skyspace.ID, "textures/entity/kyrosian_mutilator.png");
	}
}
