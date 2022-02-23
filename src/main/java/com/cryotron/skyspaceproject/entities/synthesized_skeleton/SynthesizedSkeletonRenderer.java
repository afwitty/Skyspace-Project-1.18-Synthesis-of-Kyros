package com.cryotron.skyspaceproject.entities.synthesized_skeleton;

import com.cryotron.skyspaceproject.Skyspace;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class SynthesizedSkeletonRenderer extends SkeletonRenderer {
	   private static final ResourceLocation TEXTURE = new ResourceLocation(Skyspace.ID, "textures/entity/synthesized_skeleton.png");

	   public SynthesizedSkeletonRenderer(EntityRendererProvider.Context p_174409_) {
	      super(p_174409_, ModelLayers.STRAY, ModelLayers.STRAY_INNER_ARMOR, ModelLayers.STRAY_OUTER_ARMOR);
	      this.addLayer(new SynthesizedSkeletonLayer<>(this, p_174409_.getModelSet()));
	   }

	   public ResourceLocation getTextureLocation(AbstractSkeleton p_116049_) {
	      return TEXTURE;
	   }
}
