package com.cryotron.skyspaceproject.worldgen.structures;

import com.cryotron.skyspaceproject.setup.deferredregistries.RegisteredBlocks;
import com.cryotron.skyspaceproject.setup.deferredregistries.SkyspaceRegistration;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

public class KyrosianIntersectionManual {
	private static final BlockState kyrosian = RegisteredBlocks.KYROSIAN_TILE_BLOCK.get().defaultBlockState();
	private static final BlockState kyrosianGlass = RegisteredBlocks.KYROSIAN_GLASS_BLOCK.get().defaultBlockState();
	private static final BlockState kyrosianFrame = RegisteredBlocks.KYROSIAN_EDGE_BLOCK.get().defaultBlockState();

	public static void genIntersection(int x, int y, int z, ChunkAccess chunk, BlockPos.MutableBlockPos pos) {
		for(int xx = 0; xx < 16; xx++) {
			for (int zz = 0; zz < 16; zz++) {
				if (xx >= 5 && xx <=10 && zz >= 5 && z<=10) {
					genBlock(x + xx, y, z + zz, chunk, kyrosian);						
				}
				
				if (xx == 4 && zz >= 4 && zz <= 11) {
					genBlock(x + xx, y, z + zz, chunk, kyrosianGlass);									
				}
				if (zz == 4 && xx >= 4 && xx <= 11) {
					genBlock(x + xx, y, z + zz, chunk, kyrosianGlass);								
				}
				
				if (xx == 3 && zz >= 3 && zz <= 12) {
					genBlock(x + xx, y, z + zz, chunk, kyrosianFrame);									
				}
				if (zz == 3 && xx >= 3 && xx <= 12) {
					genBlock(x + xx, y, z + zz, chunk, kyrosianFrame);								
				}
				
				
			}
		}
		
	}

	private static void genBlock(int x, int y, int z, ChunkAccess chunk, BlockState block) {
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();	
		chunk.setBlockState(pos.set(x,y,z), block, false);
	}
}
