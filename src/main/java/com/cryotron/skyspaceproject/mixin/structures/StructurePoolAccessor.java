package com.cryotron.skyspaceproject.mixin.structures;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElement;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

// REFERENCE: Repurposed Structures
@Mixin(StructureTemplatePool.class)
public interface StructurePoolAccessor {
    @Accessor("rawTemplates")
    List<Pair<StructurePoolElement, Integer>> skyspace_getRawTemplates();

    @Mutable
    @Accessor("rawTemplates")
    void skyspace_setRawTemplates(List<Pair<StructurePoolElement, Integer>> elementCounts);

    @Accessor("templates")
    List<StructurePoolElement> skyspace_getTemplates();

    @Mutable
    @Accessor("templates")
    void skyspace_setTemplates(List<StructurePoolElement> elements);
}