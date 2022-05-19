package com.cryotron.skyspaceproject.setup.deferredregistries;

import static com.cryotron.skyspaceproject.Skyspace.ID;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegisteredSounds {

    public static final DeferredRegister<SoundEvent> SFX = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ID);
    
	public static final RegistryObject<SoundEvent> ENTITY_KYROSIAN_ZOMBIE_AMBIENT = registerSound("synthesized_zombie_ambient", "entity.synthesized_zombie.ambient");
	public static final RegistryObject<SoundEvent> ENTITY_KYROSIAN_ZOMBIE_DEATH = registerSound("synthesized_zombie_death", "entity.synthesized_zombie.death");
	public static final RegistryObject<SoundEvent> ENTITY_KYROSIAN_ZOMBIE_HURT = registerSound("synthesized_zombie_hurt", "entity.synthesized_zombie.hurt");
	public static final RegistryObject<SoundEvent> ENTITY_KYROSIAN_ZOMBIE_STEP = registerSound("synthesized_zombie_step", "entity.synthesized_zombie.step");
	public static final RegistryObject<SoundEvent> ENTITY_KYROSIAN_SKELETON_AMBIENT = registerSound("synthesized_skeleton_ambient", "entity.synthesized_skeleton.ambient");
	public static final RegistryObject<SoundEvent> ENTITY_KYROSIAN_SKELETON_DEATH = registerSound("synthesized_skeleton_death", "entity.synthesized_skeleton.death");
	public static final RegistryObject<SoundEvent> ENTITY_KYROSIAN_SKELETON_HURT = registerSound("synthesized_skeleton_hurt", "entity.synthesized_skeleton.hurt");
	public static final RegistryObject<SoundEvent> ENTITY_KYROSIAN_SKELETON_STEP = registerSound("synthesized_skeleton_step", "entity.synthesized_skeleton.step");
	
	public static final RegistryObject<SoundEvent> AMBIENT_STARGATE = registerSound("ambient_stargate", "ambient.stargate");
    
	public static final RegistryObject<SoundEvent> AMBIENT_KYROS_LOOP = registerSound("ambient_kyros_loop", "ambient.kyrosloop");
	public static final RegistryObject<SoundEvent> KYROS_MUSIC = registerSound("kyros_music", "music.kyrosmusic");
	
	private static RegistryObject<SoundEvent> registerSound(String registryName, String soundPath) {
		return SFX.register(registryName, () -> createSoundEvent(soundPath));
	}

	// Using AOA3 Sound methods for sound effects.
	private static SoundEvent createSoundEvent(String soundPath) {
//		if (HolidayUtil.isChristmas() && soundPath.endsWith(".fire") && !DatagenModLoader.isRunningDataGen())
//			soundPath = "misc.jingle_bells";
		return new SoundEvent(new ResourceLocation("skyspaceproject", soundPath));
	}
	
}
