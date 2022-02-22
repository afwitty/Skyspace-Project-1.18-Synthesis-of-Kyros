package com.cryotron.skyspaceproject.worldgen.structures;

import net.minecraft.world.level.ChunkPos;

public class ChunkNode {
	ChunkPos chunkPos;
	int y;
	String string;
	
	void setChunkPos(ChunkPos chunkPosParameter) {
		chunkPos = chunkPosParameter;
	}
	
	void setY(int yParameter) {
		y = yParameter;
	}
	
	void setString(String stringParameter) {
		string = stringParameter;
	}
}
