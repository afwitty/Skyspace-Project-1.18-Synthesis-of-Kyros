package com.cryotron.skyspaceproject.entities.kyrosian_enforcer;

import com.cryotron.skyspaceproject.entities.kyrosian_deacon.KyrosianDeacon;
import com.cryotron.skyspaceproject.setup.SkyspaceRegistration;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class KyrosianEnforcer extends Monster implements IAnimatable {
	public KyrosianEnforcer(EntityType<? extends Monster> p_33002_, Level p_33003_) {
		super(p_33002_, p_33003_);
		
		
		// TODO Auto-generated constructor stub
	}


		//Animation
		
		private AnimationFactory factory = new AnimationFactory(this);
		
		 private <E extends IAnimatable> PlayState animationPredicate(AnimationEvent<E> event) {
	         event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.enforcer.idle", true));
	         return PlayState.CONTINUE;
	         
	 	}
	     
	     @Override
	     public void registerControllers(AnimationData data)
	     {
	        data.addAnimationController(new AnimationController<KyrosianEnforcer>(this, "controller", 0, this::animationPredicate));
	     }

	     @Override
	     public AnimationFactory getFactory()
	     {
	         return this.factory;
	     }
	
	     // Attributes
	     
	     public static AttributeSupplier.Builder createAttributes() {
		      return Monster.createMonsterAttributes()
		    		  .add(Attributes.MAX_HEALTH, 2.0D)
		    		  .add(SkyspaceRegistration.MAX_ENERGY_SHIELD.get(), 200.0D)
		    		  .add(Attributes.FOLLOW_RANGE, 32.0D)
		    		  .add(Attributes.MOVEMENT_SPEED, (double)0.66F);	   
		   }
	     
		    @Override
		    public float getBrightness() {
		    	return 16f;
		    }
		    
			@Override
			public boolean removeWhenFarAway(double p_213397_1_) {
				return false;
			}
}
