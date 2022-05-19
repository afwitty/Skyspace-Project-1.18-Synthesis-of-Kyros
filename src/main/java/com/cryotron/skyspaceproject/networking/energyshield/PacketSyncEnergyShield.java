package com.cryotron.skyspaceproject.networking.energyshield;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import com.cryotron.skyspaceproject.Skyspace;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class PacketSyncEnergyShield {
	
	private final float currES;
	private final float recTimer;
	
	public PacketSyncEnergyShield(float currES, float recTimer) {
		this.currES = currES;
		this.recTimer = recTimer;
	}
	
	public PacketSyncEnergyShield(FriendlyByteBuf buf) {
		currES = buf.readFloat();
		recTimer = buf.readFloat();
	}
	
	public void toBytes (FriendlyByteBuf buf) {
		buf.writeFloat(currES);
		buf.writeFloat(recTimer);
	}
	
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        final var success = new AtomicBoolean(false);
        ctx.enqueueWork(() -> {
            // Here we are client side.
            // Be very careful not to access client-only classes here! (like Minecraft) because
            // this packet needs to be available server-side too
//        	DistExecutor.unsafeRunWhenOn(Dist.CLIENT, 
//        			() -> () -> ClientEnergyShieldData.set(currES, recTimer));            
        	ClientEnergyShieldData.set(currES, recTimer);
        });
        ctx.setPacketHandled(true);
        return success.get();
    }

}
