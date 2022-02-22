package com.cryotron.skyspaceproject.client.render;

import javax.annotation.Nullable;

import com.cryotron.skyspaceproject.Skyspace;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
 
public final class SkyspaceDimensionRender {

	/* Dimension Special Effect Constructor Parameters:
     	cloudLevel - Float - Cloud render height in blocks. Set to Float.NaN to remove clouds
     	hasGround - Boolean - Whether the dim is a non-sky-dim. Affects sky colour
     	skyType - DimensionSpecialEffects.SkyType -  Sky render preset type. Affects sky rendering as well as fog
     	forceBrightLightmap - Boolean - Affects lighting colour
     	constantAmbientLight - Boolean - should blocks be equally lit on all sides
	*/
	
	// Check to see if there are clouds next time 1/21/2022
	public static void init() {
		DimensionSpecialEffects.EFFECTS.put(KyrosianEffect.ID, new KyrosianEffect());
	}
	
	public static class KyrosianEffect extends DimensionSpecialEffects {
		public static final ResourceLocation ID = new ResourceLocation(Skyspace.ID, "kyrosianeffect");
		
		public KyrosianEffect() {
			super(Float.NaN, true, DimensionSpecialEffects.SkyType.NONE, false, true);
			// Maybe find a way to implement a Cube for the Sun while keeping this sky type?
		}

		@Override
		public Vec3 getBrightnessDependentFogColor(Vec3 biomeFogColor, float celestialAngle) {
			// TODO Auto-generated method stub
			return biomeFogColor;
		}

		@Override
		public boolean isFoggyAt(int x, int z) {
			// TODO Auto-generated method stub
			return true;
		}
		
		@Nullable
		@Override // Adjust fog/sky colour for sunset/sunrise
		public float[] getSunriseColor(float celestialAngle, float partialTicks) {
			return null;
		}
		
		
	}
}
