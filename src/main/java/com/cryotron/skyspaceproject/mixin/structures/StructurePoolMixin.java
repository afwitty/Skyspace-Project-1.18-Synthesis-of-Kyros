package com.cryotron.skyspaceproject.mixin.structures;

import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@SuppressWarnings("UnresolvedMixinReference")
@Mixin(StructureTemplatePool.class)
public class StructurePoolMixin {
    /**
     * Increases the weight limit that mojang slapped on that was a workaround for https://bugs.mojang.com/browse/MC-203131
     * @author - TelepathicGrunt (Adjusted by Cryotron)
     * @return - The higher weight that is a more reasonable limit.
     * 
     */
    @ModifyConstant(
            method = "lambda$static$1",
            constant = @Constant(intValue = 150),
            remap = false,
            require = 0
    )
    private static int skyspace_increaseWeightLimitDev(int constant) {
        return 5000;
    }

    /**
     * Increases the weight limit that mojang slapped on that was a workaround for https://bugs.mojang.com/browse/MC-203131
     * @author - TelepathicGrunt (Adjusted by Cryotron)
     * @return - The higher weight that is a more reasonable limit.
     */
    @ModifyConstant(
            method = "m_69266_",
            constant = @Constant(intValue = 150),
            remap = false,
            require = 0
    )
    private static int skyspace_increaseWeightLimitProd(int constant) {
        return 5000;
    }
}