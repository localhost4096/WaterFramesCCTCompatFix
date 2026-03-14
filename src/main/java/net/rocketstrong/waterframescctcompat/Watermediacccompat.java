package net.rocketstrong.waterframescctcompat;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.rocketstrong.waterframescctcompat.blocks.DisplayInteract.DisplayInteractAttach;
import net.rocketstrong.waterframescctcompat.blocks.peripherals.DisplayBlockHandler;
import org.slf4j.Logger;

import dan200.computercraft.api.ForgeComputerCraftAPI;

import net.rocketstrong.waterframescctcompat.blocks.peripherals.PeripheralProvider;

import java.util.HashMap;
import java.util.Map;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Watermediacccompat.MODID)
public class Watermediacccompat {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "waterframescctcompat";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public Watermediacccompat() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(DisplayBlockHandler.class);
        MinecraftForge.EVENT_BUS.register(DisplayInteractAttach.class);

        ForgeComputerCraftAPI.registerPeripheralProvider(new PeripheralProvider());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
        }
    }
}
