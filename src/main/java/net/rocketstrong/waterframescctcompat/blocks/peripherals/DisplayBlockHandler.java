package net.rocketstrong.waterframescctcompat.blocks.peripherals;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;

import me.srrapero720.waterframes.common.block.entity.DisplayTile;
import net.rocketstrong.waterframescctcompat.Watermediacccompat;
import net.rocketstrong.waterframescctcompat.blocks.DisplayInteract.InteractableProvider;

public class DisplayBlockHandler {

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        BlockEntity entity = event.getLevel().getBlockEntity(event.getPos());

        if (entity instanceof DisplayTile display) {

            display.getCapability(InteractableProvider.CAPABILITY)
                    .ifPresent(cap -> {

                        if (!cap.isInteractable()) {
                            event.setCanceled(true);
                        }

                    });
        }
    }
}