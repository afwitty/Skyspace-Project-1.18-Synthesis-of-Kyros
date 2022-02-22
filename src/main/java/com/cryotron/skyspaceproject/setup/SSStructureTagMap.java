package com.cryotron.skyspaceproject.setup;

import net.minecraft.world.level.levelgen.feature.StructureFeature;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SSStructureTagMap {

    private SSStructureTagMap() {}

    public enum STRUCTURE_TAGS {
		MAZE
    }

    public static final Map<StructureFeature<?>, Set<STRUCTURE_TAGS>> TAGGED_STRUCTURES = new HashMap<>();
    public static final Map<STRUCTURE_TAGS, Set<StructureFeature<?>>> REVERSED_TAGGED_STRUCTURES = new HashMap<>();
    
    public static void setupTags(){
        addTags(SSStructures.KYROSIAN_MAZE.get(), Stream.of(STRUCTURE_TAGS.MAZE).collect(Collectors.toSet()));
    }
	
    /**
     * Helper method to setup the tags to structure and structure to tag maps.
     *
     * Only does additions and no replacements/deletions.
     */
    private static void addTags(StructureFeature<?> structure, Set<STRUCTURE_TAGS> tags){
        if(!TAGGED_STRUCTURES.containsKey(structure)){
            TAGGED_STRUCTURES.put(structure, new HashSet<>());
        }
        TAGGED_STRUCTURES.get(structure).addAll(tags);

        for(STRUCTURE_TAGS tag : tags){
            if(!REVERSED_TAGGED_STRUCTURES.containsKey(tag)){
                REVERSED_TAGGED_STRUCTURES.put(tag, new HashSet<>());
            }

            REVERSED_TAGGED_STRUCTURES.get(tag).add(structure);
        }
    }
	
}
