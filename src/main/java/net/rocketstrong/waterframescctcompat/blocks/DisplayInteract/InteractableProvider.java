package net.rocketstrong.waterframescctcompat.blocks.DisplayInteract;

import me.srrapero720.waterframes.common.block.entity.DisplayTile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraft.core.Direction;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;

public class InteractableProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static final Capability<IDisplayInteractable> CAPABILITY =
            CapabilityManager.get(new CapabilityToken<>() {});

    private final DisplayInteractable backend = new DisplayInteractable();

    private final LazyOptional<IDisplayInteractable> optional =
            LazyOptional.of(() -> backend);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if(cap == CAPABILITY) return optional.cast();
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("interactable", backend.isInteractable());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        backend.setInteractable(nbt.getBoolean("interactable"));
    }

    public void invalidate() {
        optional.invalidate();
    }

}