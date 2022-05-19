package com.cryotron.skyspaceproject.setup.deferredregistries;

import static com.cryotron.skyspaceproject.Skyspace.ID;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegisteredAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ID);
    
    // Energy Shield
	public static final RegistryObject<Attribute> MAX_ENERGY_SHIELD = ATTRIBUTES.register("max_energy_shield", () -> new RangedAttribute("max_energy_shield", 0.0D, 0.0D, 1024.0D).setSyncable(true));
	public static final RegistryObject<Attribute> ENERGY_SHIELD_RECHARGE = ATTRIBUTES.register("energy_shield_recharge", () -> new RangedAttribute("energy_shield_recharge", 4.0D, 0.0D, 1024.0D).setSyncable(true));
	
	public static final RegistryObject<Attribute> EVASION = ATTRIBUTES.register("evasion", () -> new RangedAttribute("evasion", 1.0D, 0.0D, 1024.0D).setSyncable(true));	
	public static final RegistryObject<Attribute> ACCURACY = ATTRIBUTES.register("accuracy", () -> new RangedAttribute("accuracy", 1.0D, 0.0D, 1024.0D).setSyncable(true));	
	
}
