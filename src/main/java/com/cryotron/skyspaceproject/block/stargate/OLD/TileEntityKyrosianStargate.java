//package com.cryotron.skyspaceproject.block.stargate;
//
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.minecraft.world.level.block.entity.BlockEntityType;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Registry;
//import net.minecraft.world.level.Level;
//import net.minecraftforge.registries.ObjectHolder;
//
//public class TileEntityKyrosianStargate extends BlockEntity {
//    public static final String REG_NAME = "tileentity_gold_portal";
//
//    public static BlockEntityType<TileEntityKyrosianStargate> TYPE;
//
//    private double destX = 0, destY = -10000, destZ = 0;
//    private int cooldown = 80;
//    private int lastUpdate = 0;
//    private String destDimension = "minecraft:overworld";
//
//    public TileEntityKyrosianStargate(BlockPos pos, BlockState state)
//    {
//	super(TYPE, pos, state);
//    }
//
//    @Override
//    public void load(CompoundTag compound)
//    {
//	super.load(compound);
//	if (compound.contains("destX") && compound.contains("destY") && compound.contains("destZ"))
//	{
//	    this.destX = compound.getDouble("destX");
//	    this.destY = compound.getDouble("destY");
//	    this.destZ = compound.getDouble("destZ");
//	}
//	if (compound.contains("cooldown"))
//	{
//	    this.cooldown = compound.getInt("cooldown");
//	}
//
//	// default value for portals that players created before I allowed portals to other dimensions 
//	if (compound.contains("destDimension"))
//	{
//	    this.destDimension = compound.getString("destDimension");
//	}
//	else
//	{
//	    this.destDimension = "minecraft:overworld";
//	}
//    }
//
//    @Override
//    protected void saveAdditional(CompoundTag compound)
//    {
//	compound.putDouble("destX", this.destX);
//	compound.putDouble("destY", this.destY);
//	compound.putDouble("destZ", this.destZ);
//	compound.putInt("cooldown", this.cooldown);
//	compound.putString("destDimension", this.destDimension);
//    }
//
//    public void setDestination(double posX, double posY, double posZ, String destDim)
//    {
//	this.destX = posX;
//	this.destY = posY;
//	this.destZ = posZ;
//	this.destDimension = destDim;
//    }
//
//    public BlockPos getDestination()
//    {
//	return new BlockPos(destX, destY, destZ);
//    }
//
//    public int getCooldown()
//    {
//	return cooldown;
//    }
//
//    public ResourceKey<Level> getDestinationDimension()
//    {
//	return ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(destDimension));
//    }
//
//    public boolean needsUpdateThisTick(int tick)
//    {
//	return tick > lastUpdate;
//    }
//
//    // return true if recursion is necessary
//    public boolean setCooldown(int value, Level worldIn, BlockPos pos, int currentServerTick)
//    {
//	if (cooldown == value || currentServerTick <= lastUpdate)
//	{
//	    return false;
//	}
//
//	cooldown = value;
//	lastUpdate = currentServerTick;
//
//	// search each neighboring block for siblings and update them too
//	int numBlocksUpdated = 0;
//	if (worldIn.getBlockEntity(pos.west()) instanceof TileEntityKyrosianStargate)
//	{
//	    numBlocksUpdated += ((TileEntityKyrosianStargate) worldIn.getBlockEntity(pos.west())).setCooldown(value, worldIn, pos.west(), currentServerTick) ? 1 : 0;
//	}
//	if (worldIn.getBlockEntity(pos.east()) instanceof TileEntityKyrosianStargate)
//	{
//	    numBlocksUpdated += ((TileEntityKyrosianStargate) worldIn.getBlockEntity(pos.east())).setCooldown(value, worldIn, pos.east(), currentServerTick) ? 1 : 0;
//	}
//	if (worldIn.getBlockEntity(pos.north()) instanceof TileEntityKyrosianStargate)
//	{
//	    numBlocksUpdated += ((TileEntityKyrosianStargate) worldIn.getBlockEntity(pos.north())).setCooldown(value, worldIn, pos.north(), currentServerTick) ? 1 : 0;
//	}
//	if (worldIn.getBlockEntity(pos.south()) instanceof TileEntityKyrosianStargate)
//	{
//	    numBlocksUpdated += ((TileEntityKyrosianStargate) worldIn.getBlockEntity(pos.south())).setCooldown(value, worldIn, pos.south(), currentServerTick) ? 1 : 0;
//	}
//	if (worldIn.getBlockEntity(pos.above()) instanceof TileEntityKyrosianStargate)
//	{
//	    numBlocksUpdated += ((TileEntityKyrosianStargate) worldIn.getBlockEntity(pos.above())).setCooldown(value, worldIn, pos.above(), currentServerTick) ? 1 : 0;
//	}
//	if (worldIn.getBlockEntity(pos.below()) instanceof TileEntityKyrosianStargate)
//	{
//	    numBlocksUpdated += ((TileEntityKyrosianStargate) worldIn.getBlockEntity(pos.below())).setCooldown(value, worldIn, pos.below(), currentServerTick) ? 1 : 0;
//	}
//
//	return numBlocksUpdated > 0;
//    }
//}