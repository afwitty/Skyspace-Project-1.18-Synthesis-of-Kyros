package com.cryotron.skyspaceproject.setup;

import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SuspendedTownParticle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;

public  class ESParticle extends TextureSheetParticle  {

	ESParticle(ClientLevel world, double x, double y, double z, double velX, double velY, double velZ) {
		super(world, x, y, z, velX, velY, velZ);
	}

//	@Override
//	public int getLightColor(float partialTicks) {
//		return 0x0000F0;
//	}
	@Override
	public ParticleRenderType getRenderType() {
		// TODO Auto-generated method stub
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	public static class Factory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;

		public Factory(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(SimpleParticleType data, ClientLevel world, double x, double y, double z, double vx, double vy, double vz) {
			ESParticle particle = new ESParticle(world, x, y, z, vx, vy, vz);
			particle.pickSprite(this.spriteSet);
			//particle.setColor(1.0F, 1.0F, 1.0F);
			return particle;
		}
	}



}
