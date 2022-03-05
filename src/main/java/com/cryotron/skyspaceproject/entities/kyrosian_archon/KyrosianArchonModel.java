package com.cryotron.skyspaceproject.entities.kyrosian_archon;

import com.cryotron.skyspaceproject.Skyspace;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class KyrosianArchonModel  <T extends KyrosianArchon> extends AnimatedGeoModel<T> {

	@Override
	public ResourceLocation getAnimationFileLocation(T animatable) {
		// TODO Auto-generated method stub
		return new ResourceLocation(Skyspace.ID, "animations/archon.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(T object) {
		// TODO Auto-generated method stub
		return new ResourceLocation(Skyspace.ID, "geo/archon.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(T object) {
		// TODO Auto-generated method stub
		return new ResourceLocation(Skyspace.ID, "textures/entity/kyrosian_archon.png");
	}
	
}
