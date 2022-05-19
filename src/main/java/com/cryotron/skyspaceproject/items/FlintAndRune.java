package com.cryotron.skyspaceproject.items;

import com.cryotron.skyspaceproject.Skyspace;
import com.cryotron.skyspaceproject.block.stargate.KyrosianStargateBlock;
import com.cryotron.skyspaceproject.setup.SkyspaceRegistration;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

//import net.minecraftforge.registries.ObjectHolder;
//
//import net.minecraft.world.World;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.ActionResultType;
//import net.minecraft.item.ItemUseContext;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.ItemGroup;

//import net.minecraft.entity.player.PlayerEntity;

public class FlintAndRune extends Item {
	public static final Item block = null;
	
	public FlintAndRune(Properties props) {
		super(props);
	}


	@Override
	public InteractionResult useOn(UseOnContext context) {
		Player entity = context.getPlayer();
		BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
		ItemStack itemstack = context.getItemInHand();
		Level world = context.getLevel();
		if (!entity.mayUseItemAt(pos, context.getClickedFace(), itemstack)) {
			return InteractionResult.FAIL;
		} else {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			boolean success = false;
			if (world.isEmptyBlock(pos) && true) {
				KyrosianStargateBlock.portalSpawn(world, pos);	// Link this to Kyros somehow.
				itemstack.hurtAndBreak(1, entity, c -> c.broadcastBreakEvent(context.getHand()));
				success = true;
			}
			return success ? InteractionResult.SUCCESS : InteractionResult.FAIL;
		}
	}

}
