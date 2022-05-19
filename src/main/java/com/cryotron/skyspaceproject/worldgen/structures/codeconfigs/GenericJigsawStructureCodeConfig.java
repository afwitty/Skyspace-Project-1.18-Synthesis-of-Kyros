package com.cryotron.skyspaceproject.worldgen.structures.codeconfigs;

//import com.telepathicgrunt.repurposedstructures.modinit.RSStructureTagMap;
//import com.telepathicgrunt.repurposedstructures.modinit.RSStructures;


import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.util.Lazy;

import java.util.HashSet;
import java.util.Set;

import com.cryotron.skyspaceproject.setup.SSStructureTagMap;
import com.cryotron.skyspaceproject.setup.deferredregistries.RegisteredStructures;

public class GenericJigsawStructureCodeConfig {

    public final ResourceLocation startPool;
    public final Lazy<Integer> structureSize;
    public final int centerOffset;
    public final int biomeRange;
    public final int structureBlacklistRange;
    public final Set<SSStructureTagMap.STRUCTURE_TAGS> avoidStructuresSet;
    public final int allowTerrainHeightRange;
    public final int terrainHeightRadius;
    public final int minHeightLimit;
    public final int fixedYSpawn;
    public final boolean useHeightmap;
    public final boolean cannotSpawnInWater;
    public final Set<ResourceLocation> poolsThatIgnoreBounds;

    public GenericJigsawStructureCodeConfig(ResourceLocation poolID, Lazy<Integer> structureSize, int centerOffset, int biomeRange,
                                            int structureBlacklistRange, Set<SSStructureTagMap.STRUCTURE_TAGS> avoidStructuresSet,
                                            int allowTerrainHeightRange, int terrainHeightRadius, int minHeightLimit,
                                            int fixedYSpawn, boolean useHeightmap, boolean cannotSpawnInWater,
                                            Set<ResourceLocation> poolsThatIgnoreBounds)
    {
        this.startPool = poolID;
        this.structureSize = structureSize;
        this.centerOffset = centerOffset;
        this.biomeRange = biomeRange;
        this.structureBlacklistRange = structureBlacklistRange;
        this.avoidStructuresSet = avoidStructuresSet;
        this.allowTerrainHeightRange = allowTerrainHeightRange;
        this.terrainHeightRadius = terrainHeightRadius;
        this.minHeightLimit = minHeightLimit;
        this.fixedYSpawn = fixedYSpawn;
        this.useHeightmap = useHeightmap;
        this.cannotSpawnInWater = cannotSpawnInWater;
        this.poolsThatIgnoreBounds = poolsThatIgnoreBounds;

        RegisteredStructures.SS_STRUCTURE_START_PIECES.add(startPool);
    }

    public static class Builder<T extends Builder<T>> {
        protected final ResourceLocation startPool;
        protected Lazy<Integer> structureSize = () -> 1;
        protected int centerOffset = 0;
        protected int biomeRange = 0;
        protected int structureBlacklistRange = 0;
        protected Set<SSStructureTagMap.STRUCTURE_TAGS> avoidStructuresSet = new HashSet<>();
        protected int allowTerrainHeightRange = -1;
        protected int terrainHeightRadius = 0;
        protected int minHeightLimit = Integer.MIN_VALUE;
        protected int fixedYSpawn = 0;
        protected boolean useHeightmap = true;
        protected boolean cannotSpawnInWater = false;
        protected Set<ResourceLocation> poolsThatIgnoreBounds;

        public Builder(ResourceLocation startPool) {
            this.startPool = startPool;
        }

        @SuppressWarnings("unchecked")
        protected T getThis() {
            return (T) this;
        }

        public T setStructureSize(int structureSize) {
            this.structureSize = () -> structureSize;
            return getThis();
        }

        public T setStructureSize(ForgeConfigSpec.IntValue structureSize) {
            this.structureSize = Lazy.of(structureSize::get);
            return getThis();
        }

        public T setCenterOffset(int centerOffset) {
            this.centerOffset = centerOffset;
            return getThis();
        }

        public T setBiomeRange(int biomeRange) {
            this.biomeRange = biomeRange;
            return getThis();
        }

        public T setStructureBlacklistRange(int structureBlacklistRange) {
            this.structureBlacklistRange = structureBlacklistRange;
            return getThis();
        }

        public T setAvoidStructuresSet(Set<SSStructureTagMap.STRUCTURE_TAGS> avoidStructuresSet) {
            this.avoidStructuresSet = avoidStructuresSet;
            return getThis();
        }

        public T setAllowTerrainHeightRange(int allowTerrainHeightRange) {
            this.allowTerrainHeightRange = allowTerrainHeightRange;
            return getThis();
        }

        public T setTerrainHeightRadius(int terrainHeightRadius) {
            this.terrainHeightRadius = terrainHeightRadius;
            return getThis();
        }

        public T setMinHeightLimit(int minHeightLimit) {
            this.minHeightLimit = minHeightLimit;
            return getThis();
        }

        public T setFixedYSpawn(int fixedYSpawn) {
            this.fixedYSpawn = fixedYSpawn;
            return getThis();
        }

        public T doNotUseHeightmap() {
            this.useHeightmap = false;
            return getThis();
        }

        public T cannotSpawnInWater() {
            this.cannotSpawnInWater = true;
            return getThis();
        }

        public T setPoolsThatIgnoreBounds(Set<ResourceLocation> poolsThatIgnoreBounds) {
            this.poolsThatIgnoreBounds = poolsThatIgnoreBounds;
            return getThis();
        }

        public GenericJigsawStructureCodeConfig build() {
            return new GenericJigsawStructureCodeConfig(
                    startPool,
                    structureSize,
                    centerOffset,
                    biomeRange,
                    structureBlacklistRange,
                    avoidStructuresSet,
                    allowTerrainHeightRange,
                    terrainHeightRadius,
                    minHeightLimit,
                    fixedYSpawn,
                    useHeightmap,
                    cannotSpawnInWater,
                    poolsThatIgnoreBounds
            );
        }
    }
}