package com.cryotron.skyspaceproject.setup.deferredregistries;

import static com.cryotron.skyspaceproject.Skyspace.ID;

import com.cryotron.skyspaceproject.entities.kyrosian_archon.KyrosianArchon;
import com.cryotron.skyspaceproject.entities.kyrosian_deacon.KyrosianDeacon;
import com.cryotron.skyspaceproject.entities.kyrosian_enforcer.KyrosianEnforcer;
import com.cryotron.skyspaceproject.entities.kyrosian_mutilator.KyrosianMutilator;
import com.cryotron.skyspaceproject.entities.synthesized_skeleton.SynthesizedSkeleton;
import com.cryotron.skyspaceproject.entities.synthesized_zombie.SynthesizedZombie;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegisteredEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, ID);
    
    /*
     * Living Entities
     */
	
	public static final RegistryObject<EntityType<SynthesizedZombie>> SYNTHESIZED_ZOMBIE = ENTITIES.register("synthesized_zombie", () -> EntityType.Builder.of(SynthesizedZombie::new, MobCategory.MONSTER)
				.sized(0.7f, 2.1f)
				.clientTrackingRange(16)
				.setShouldReceiveVelocityUpdates(false)
				.build("synthesized_zombie")
			);
	 
	public static final RegistryObject<EntityType<SynthesizedSkeleton>> SYNTHESIZED_SKELETON = ENTITIES.register("synthesized_skeleton", () -> EntityType.Builder.of(SynthesizedSkeleton::new, MobCategory.MONSTER)
				.sized(0.7f, 2.1f)
				.clientTrackingRange(16)
				.setShouldReceiveVelocityUpdates(false)
				.build("synthesized_skeleton")
			);
	 
	 public static final RegistryObject<EntityType<KyrosianArchon>> KYROSIAN_ARCHON = ENTITIES.register("kyrosian_archon", () -> EntityType.Builder.of(KyrosianArchon::new, MobCategory.MONSTER)
				.sized(0.7f, 2.1f)
				.clientTrackingRange(16)
				.setShouldReceiveVelocityUpdates(false)
				.build("kyrosian_archon")
			 );
	 
	 public static final RegistryObject<EntityType<KyrosianEnforcer>> KYROSIAN_ENFORCER = ENTITIES.register("kyrosian_enforcer", () -> EntityType.Builder.of(KyrosianEnforcer::new, MobCategory.MONSTER)
				.sized(0.7f, 2.1f)
				.clientTrackingRange(16)
				.setShouldReceiveVelocityUpdates(false)
				.build("kyrosian_enforcer")
			 );
	 
	 public static final RegistryObject<EntityType<KyrosianMutilator>> KYROSIAN_MUTILATOR = ENTITIES.register("kyrosian_mutilator", () -> EntityType.Builder.of(KyrosianMutilator::new, MobCategory.MONSTER)
				.sized(0.7f, 2.1f)
				.clientTrackingRange(16)
				.setShouldReceiveVelocityUpdates(false)
				.build("kyrosian_mutilator")
			 );
	 
	 public static final RegistryObject<EntityType<KyrosianDeacon>> KYROSIAN_DEACON = ENTITIES.register("kyrosian_deacon", () -> EntityType.Builder.of(KyrosianDeacon::new, MobCategory.MONSTER)
				.sized(0.7f, 2.1f)
				.clientTrackingRange(16)
				.setShouldReceiveVelocityUpdates(false)
				.build("kyrosian_deacon")
			 );
    
	
}
