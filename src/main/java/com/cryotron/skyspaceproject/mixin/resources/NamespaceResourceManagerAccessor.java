package com.cryotron.skyspaceproject.mixin.resources;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.FallbackResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Mixin(FallbackResourceManager.class)
public interface NamespaceResourceManagerAccessor {
    @Accessor("fallbacks")
    List<PackResources> repurposedstructures_getFallbacks();

    @Invoker("getWrappedResource")
    InputStream repurposedstructures_callGetWrappedResource(ResourceLocation id, PackResources pack) throws IOException;
}