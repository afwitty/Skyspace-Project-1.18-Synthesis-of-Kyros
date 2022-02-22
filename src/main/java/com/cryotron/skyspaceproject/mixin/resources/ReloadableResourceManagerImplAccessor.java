package com.cryotron.skyspaceproject.mixin.resources;

import net.minecraft.server.packs.resources.FallbackResourceManager;
import net.minecraft.server.packs.resources.SimpleReloadableResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(SimpleReloadableResourceManager.class)
public interface ReloadableResourceManagerImplAccessor {
    @Accessor("namespacedPacks")
    Map<String, FallbackResourceManager> repurposedstructures_getNamespacedPacks();
}