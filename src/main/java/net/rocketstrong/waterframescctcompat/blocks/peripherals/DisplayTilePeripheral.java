package net.rocketstrong.waterframescctcompat.blocks.peripherals;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import me.srrapero720.waterframes.WaterFrames;
import me.srrapero720.waterframes.common.block.entity.DisplayTile;
import me.srrapero720.waterframes.common.network.DisplayNetwork;
import me.srrapero720.waterframes.common.network.packets.TimePacket;
import me.srrapero720.waterframes.common.network.packets.VolumePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.rocketstrong.waterframescctcompat.Watermediacccompat;
import net.rocketstrong.waterframescctcompat.blocks.DisplayInteract.InteractableProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DisplayTilePeripheral implements IPeripheral {

    private final Set<IComputerAccess> computers = Collections.newSetFromMap(new ConcurrentHashMap());
    private final BlockPos blockPos;
    private final Level level;
    private final DisplayTile Tile;
    private boolean interactable = true;

    public DisplayTilePeripheral(@NotNull BlockPos blockPos, Level level) {
        this.blockPos = blockPos;
        this.level = level;
        this.Tile = (DisplayTile) level.getBlockEntity(blockPos);
    }

    @Override
    public String getType() {
        return "MediaDisplay";
    }

    @LuaFunction
    public final void setInteractable(boolean state){
        interactable = state;
        Tile.getCapability(InteractableProvider.CAPABILITY)
                .ifPresent(cap -> cap.setInteractable(state));
    }

    @LuaFunction
    public final boolean isInteractable() {
        return interactable;
    }

    @Override
    public Set<String> getAdditionalTypes() {
        return IPeripheral.super.getAdditionalTypes();
    }

    @Override
    public void attach(IComputerAccess computer) {
        this.computers.add(computer);
    }

    @Override
    public void detach(IComputerAccess computer) {
        this.computers.remove(computer);
    }

    @Nullable
    @Override
    public Object getTarget() {
        return IPeripheral.super.getTarget();
    }

    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) {
        return this == iPeripheral;
    }

    @LuaFunction
    public final String getUrl() {
        return Tile.data.hasUri() ? Tile.data.getUri().toString() : "";
    }

    @LuaFunction
    public final int getTick() {
        return Tile.data.tick;
    }

    @LuaFunction
    public final int getMaxTick() {
        return Tile.data.tickMax;
    }

    @LuaFunction
    public final float getVolume() {
        return Tile.data.volume;
    }

    @LuaFunction
    public final boolean getPaused() {
        return Tile.data.paused;
    }

    @LuaFunction
    public final int getTransparency() {
        return Tile.data.alpha;
    }

    @LuaFunction(mainThread = true)
    public final void setTick(int tick) {
        DisplayNetwork.sendClient(new TimePacket(Tile.getBlockPos(), Math.max(0, Math.min(Tile.data.tickMax, tick)), Tile.data.tickMax, true), Tile);
    }

    @LuaFunction(mainThread = true)
    public final void setVolume(int volume) {
        DisplayNetwork.sendClient(new VolumePacket(Tile.getBlockPos(), volume, true), Tile);
    }

    @LuaFunction(mainThread = true)
    public final boolean setUrl(String url) {

        URI uri = WaterFrames.createURI(url);

        if (uri != null) {
            Tile.data.uri = uri;
            Tile.setDirty();
            return true;
        } else {
            return false;
        }
    }

    @LuaFunction(mainThread = true)
    public final void play() {
        Tile.setPause(false, false);
    }

    @LuaFunction(mainThread = true)
    public final void pause() {
        Tile.setPause(false, true);
    }

    @LuaFunction(mainThread = true)
    public final void stop() {
        Tile.setStop(false);
    }

    @LuaFunction(mainThread = true)
    public final void loop(boolean enabled) {
        Tile.loop(false, enabled);
    }

    @LuaFunction(mainThread = true)
    public final void setTransparency(int alpha) {
        Tile.data.alpha = Math.max(0, Math.min(255, alpha));
        Tile.setDirty();
    }

}
