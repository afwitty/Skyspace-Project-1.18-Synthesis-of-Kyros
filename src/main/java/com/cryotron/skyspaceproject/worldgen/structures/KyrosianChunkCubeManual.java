package com.cryotron.skyspaceproject.worldgen.structures;

import com.cryotron.skyspaceproject.setup.deferredregistries.RegisteredBlocks;
import com.cryotron.skyspaceproject.setup.deferredregistries.SkyspaceRegistration;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

public class KyrosianChunkCubeManual {
	
	private static final BlockState kyrosian = RegisteredBlocks.KYROSIAN_TILE_BLOCK.get().defaultBlockState();
	private static final BlockState kyrosianGlass = RegisteredBlocks.KYROSIAN_GLASS_BLOCK.get().defaultBlockState();
	private static final BlockState kyrosianFrame = RegisteredBlocks.KYROSIAN_EDGE_BLOCK.get().defaultBlockState();

	public static void genChunkCube(int x, int y, int z, ChunkAccess chunk, BlockPos.MutableBlockPos pos) {
		for (int xx = 1; xx < 15; xx++) {
			genBlock(x + xx, y + 1, z + 1, chunk, kyrosianFrame);	
			genBlock(x + xx, y + 1, z + 14, chunk, kyrosianFrame);			
			genBlock(x + xx, y + 14, z + 1, chunk, kyrosianFrame);			
			genBlock(x + xx, y + 14, z + 14, chunk, kyrosianFrame);			
			
			genBlock(x + 1, y + xx, z + 1, chunk, kyrosianFrame);
			genBlock(x + 14, y + xx, z + 1, chunk, kyrosianFrame);
			genBlock(x + 1, y + xx, z + 14, chunk, kyrosianFrame);
			genBlock(x + 14, y + xx, z + 14, chunk, kyrosianFrame);		
			
			genBlock(x + 1, y + 1, z + xx, chunk, kyrosianFrame);			
			genBlock(x + 1, y + 14, z + xx, chunk, kyrosianFrame);			
			genBlock(x + 14, y + 1, z + xx, chunk, kyrosianFrame);			
			genBlock(x + 14, y + 14, z + xx, chunk, kyrosianFrame);			
		}
		
		for (int xx = 2; xx < 14; xx++) {			
			genBlock(x + xx, y + 1, z + 2, chunk, kyrosianGlass);			
			genBlock(x + xx, y + 14, z + 2, chunk, kyrosianGlass);			
			genBlock(x + xx, y + 1, z + 13, chunk, kyrosianGlass);			
			genBlock(x + xx, y + 14, z + 13, chunk, kyrosianGlass);			
			
			genBlock(x + xx, y + 2, z + 1, chunk, kyrosianGlass);			
			genBlock(x + xx, y + 2, z + 14, chunk, kyrosianGlass);			
			genBlock(x + xx, y + 13, z + 1, chunk, kyrosianGlass);			
			genBlock(x + xx, y + 13, z + 14, chunk, kyrosianGlass);		
			
			genBlock(x + 2, y + 1, z + xx, chunk, kyrosianGlass);			
			genBlock(x + 2, y + 14, z + xx, chunk, kyrosianGlass);			
			genBlock(x + 13, y + 1, z + xx, chunk, kyrosianGlass);			
			genBlock(x + 13, y + 14, z + xx, chunk, kyrosianGlass);	
			
			genBlock(x + 1, y + 2, z + xx, chunk, kyrosianGlass);			
			genBlock(x + 14, y + 2, z + xx, chunk, kyrosianGlass);			
			genBlock(x + 1, y + 13, z + xx, chunk, kyrosianGlass);			
			genBlock(x + 14, y + 13, z + xx, chunk, kyrosianGlass);	
			
			genBlock(x + 2, y + xx, z + 1, chunk, kyrosianGlass);			
			genBlock(x + 2, y + xx, z + 14, chunk, kyrosianGlass);			
			genBlock(x + 13, y + xx, z + 1, chunk, kyrosianGlass);			
			genBlock(x + 13, y + xx, z + 14, chunk, kyrosianGlass);	
			
			genBlock(x + 1, y + xx, z + 2, chunk, kyrosianGlass);			
			genBlock(x + 14, y + xx, z + 2, chunk, kyrosianGlass);			
			genBlock(x + 1, y + xx, z + 13, chunk, kyrosianGlass);			
			genBlock(x + 14, y + xx, z + 13, chunk, kyrosianGlass);	
		}	

		for (int xx = 3; xx < 13; xx++) {
			for (int yy = 3; yy < 13; yy++) {
				genBlock(x + xx, y + 1, z + yy, chunk, kyrosian);			
				genBlock(x + xx, y + 14, z + yy, chunk, kyrosian);			
				genBlock(x + xx, y + 1, z + yy, chunk, kyrosian);			
				genBlock(x + xx, y + 14, z + yy, chunk, kyrosian);			
				
				genBlock(x + xx, y + yy, z + 1, chunk, kyrosian);			
				genBlock(x + xx, y + yy, z + 14, chunk, kyrosian);			
				genBlock(x + xx, y + yy, z + 1, chunk, kyrosian);			
				genBlock(x + xx, y + yy, z + 14, chunk, kyrosian);			
				
				genBlock(x + 1, y + xx, z + yy, chunk, kyrosian);			
				genBlock(x + 14, y + xx, z + yy, chunk, kyrosian);			
				genBlock(x + 1, y + xx, z + yy, chunk, kyrosian);			
				genBlock(x + 14, y + xx, z + yy, chunk, kyrosian);			
			}
		}
		

	}

	private static void genBlock(int x, int y, int z, ChunkAccess chunk, BlockState block) {
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();	
		chunk.setBlockState(pos.set(x,y,z), block, false);
	}

}
