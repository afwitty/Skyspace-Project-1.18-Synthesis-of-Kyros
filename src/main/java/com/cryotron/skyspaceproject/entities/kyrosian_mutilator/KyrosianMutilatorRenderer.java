package com.cryotron.skyspaceproject.entities.kyrosian_mutilator;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class KyrosianMutilatorRenderer extends GeoEntityRenderer<KyrosianMutilator> {
	private KyrosianMutilatorModel<KyrosianMutilator> modelProvider = new KyrosianMutilatorModel<KyrosianMutilator>();
	
	 public KyrosianMutilatorRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager,  new KyrosianMutilatorModel<KyrosianMutilator>());
		this.modelProvider = modelProvider;
	}
	 
	 
	    @Override
		public void render(KyrosianMutilator entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn,
				int packedLightIn) {
			stack.scale(3.5f, 3.5f, 3.5f);
			super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
		}
}
