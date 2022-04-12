package com.cryotron.skyspaceproject.setup;

import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SuspendedTownParticle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.Level;

public  class ESParticle extends TextureSheetParticle  {

	public ESParticle(ClientLevel world, double x, double y, double z, double velX, double velY, double velZ) {
		super(world, x, y, z, velX, velY, velZ);
//	      this.friction = 0.7F;
//	      this.gravity = 0.5F;
//	      this.xd *= (double)0.1F;
//	      this.yd *= (double)0.1F;
//	      this.zd *= (double)0.1F;
//	      this.xd += velX * 0.4D;
//	      this.yd += velY * 0.4D;
//	      this.zd += velZ * 0.4D;
//	      float f = (float)(Math.random() * (double)0.3F + (double)0.6F);
//	      this.rCol = f;
//	      this.gCol = f;
//	      this.bCol = f;
//	      this.quadSize *= 0.75F;
//	      this.lifetime = Math.max((int)(6.0D / (Math.random() * 0.8D + 0.6D)), 1);
//	      this.hasPhysics = false;
//	      this.tick();
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
	
//	@Override
//	   public void tick() {
//	      super.tick();
//	      this.gCol = (float)((double)this.gCol * 0.96D);
//	      this.bCol = (float)((double)this.bCol * 0.9D);
//	   }

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
