package com.cryotron.skyspaceproject.entities.synthesized_zombie;

import net.minecraft.client.model.AbstractZombieModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SynthesizedZombieModel<T extends Zombie> extends ZombieModel<T> {
	
   public SynthesizedZombieModel(ModelPart p_171090_) {
      super(p_171090_);
   }

   public boolean isAggressive(T p_104155_) {
      return p_104155_.isAggressive();
   }
}