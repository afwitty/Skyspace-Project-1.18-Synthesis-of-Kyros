//package com.cryotron.skyspaceproject.capabilities.energyshield.oldII;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//
//import net.minecraft.core.Direction;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.capabilities.CapabilityManager;
//import net.minecraftforge.common.capabilities.CapabilityToken;
//import net.minecraftforge.common.capabilities.ICapabilityProvider;
//import net.minecraftforge.common.util.INBTSerializable;
//import net.minecraftforge.common.util.LazyOptional;
//
//public class EnergyShieldProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
//	
//    public static Capability<EnergyShield> ES = CapabilityManager.get(new CapabilityToken<>(){});
//    
//    private EnergyShield es = null;
//    private final LazyOptional<EnergyShield> opt = LazyOptional.of(this::createEnergyShield);
//    
//    @Nonnull
//    private EnergyShield createEnergyShield() {
//    	if (es == null) {
//    		es = new EnergyShield();
//    	}
//    	return es;
//    }
//
//    @Nonnull
//    @Override
//    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
//        if (cap == ES) {
//            return opt.cast();
//        }
//        return LazyOptional.empty();
//    }
//
//    @Nonnull
//    @Override
//    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//        return getCapability(cap);
//    }
//
//    @Override
//    public CompoundTag serializeNBT() {
//        CompoundTag nbt = new CompoundTag();
//        createEnergyShield().saveNBTData(nbt);
//        return nbt;
//    }
//
//    @Override
//    public void deserializeNBT(CompoundTag nbt) {
//        createEnergyShield().loadNBTData(nbt);
//    }
//
//}
