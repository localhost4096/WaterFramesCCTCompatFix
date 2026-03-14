package net.rocketstrong.waterframescctcompat.blocks.DisplayInteract;

import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;

import me.srrapero720.waterframes.common.block.entity.DisplayTile;

public class DisplayInteractAttach {

    private static final ResourceLocation ID =
            new ResourceLocation("waterframescctcompat", "frame_interactable");

    @SubscribeEvent
    public static void attach(AttachCapabilitiesEvent<BlockEntity> event) {

        if(event.getObject() instanceof DisplayTile) {
            event.addCapability(ID, new InteractableProvider());
        }

    }
}