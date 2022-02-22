package com.cryotron.skyspaceproject.entities.synthesized_zombie;


import com.cryotron.skyspaceproject.Skyspace;

import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SynthesizedZombieRenderer extends AbstractZombieRenderer<SynthesizedZombie, SynthesizedZombieModel<SynthesizedZombie>> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(Skyspace.ID, "textures/entity/synthesized_zombie.png");
	
   public SynthesizedZombieRenderer(EntityRendererProvider.Context p_174456_) {
      this(p_174456_, ModelLayers.ZOMBIE, ModelLayers.ZOMBIE_INNER_ARMOR, ModelLayers.ZOMBIE_OUTER_ARMOR);
   }

   public SynthesizedZombieRenderer(EntityRendererProvider.Context p_174458_, ModelLayerLocation p_174459_, ModelLayerLocation p_174460_, ModelLayerLocation p_174461_) {
      super(p_174458_, new SynthesizedZombieModel<>(p_174458_.bakeLayer(p_174459_)), new SynthesizedZombieModel<>(p_174458_.bakeLayer(p_174460_)), new SynthesizedZombieModel<>(p_174458_.bakeLayer(p_174461_)));
   }
   
	public ResourceLocation getTextureLocation(Zombie entity) {
		// TODO Auto-generated method stub
		return TEXTURE;
	}
}