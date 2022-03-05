package com.cryotron.skyspaceproject.entities.kyrosian_deacon;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class KyrosianDeaconRenderer extends GeoEntityRenderer<KyrosianDeacon> {
	private KyrosianDeaconModel<KyrosianDeacon> modelProvider = new KyrosianDeaconModel<KyrosianDeacon>();
	
	 public KyrosianDeaconRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager,  new KyrosianDeaconModel<KyrosianDeacon>());
		this.modelProvider = modelProvider;
	}
	 
	 
	    @Override
		public void render(KyrosianDeacon entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn,
				int packedLightIn) {
			stack.scale(3.5f, 3.5f, 3.5f);
			super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
		}
}
