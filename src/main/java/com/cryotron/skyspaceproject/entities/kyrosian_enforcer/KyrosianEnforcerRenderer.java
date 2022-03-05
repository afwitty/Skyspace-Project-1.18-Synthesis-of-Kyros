package com.cryotron.skyspaceproject.entities.kyrosian_enforcer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class KyrosianEnforcerRenderer extends GeoEntityRenderer<KyrosianEnforcer> {
	private KyrosianEnforcerModel<KyrosianEnforcer> modelProvider = new KyrosianEnforcerModel<KyrosianEnforcer>();
	
	 public KyrosianEnforcerRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager,  new KyrosianEnforcerModel<KyrosianEnforcer>());
		this.modelProvider = modelProvider;
	}
	 
	 
	    @Override
		public void render(KyrosianEnforcer entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn,
				int packedLightIn) {
			stack.scale(2.5f, 2.5f, 2.5f);
			super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
		}
}
