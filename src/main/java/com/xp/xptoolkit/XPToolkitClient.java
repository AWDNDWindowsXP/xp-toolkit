package com.xp.xptoolkit;

import com.xp.xptoolkit.config.ConfigUi;
import com.xp.xptoolkit.config.Configs;
import com.xp.xptoolkit.config.HotkeysCallback;
import com.xp.xptoolkit.config.InputHandler;
import com.xp.xptoolkit.features.SprintLock;
import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.event.TickHandler;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XPToolkitClient implements ClientModInitializer {
    public static final String MOD_ID = "xptoolkit";
    public static final String MOD_NAME = "XPToolkit";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final Minecraft CLIENT = Minecraft.getInstance();

    @Override
    public void onInitializeClient() {
        Configs.INSTANCE.load();
        ConfigManager.getInstance().registerConfigHandler(MOD_ID, Configs.INSTANCE);

        ConfigUi.initMalilibConfig();

        InputEventHandler.getKeybindManager().registerKeybindProvider(InputHandler.getInstance());
        InputEventHandler.getInputManager().registerKeyboardInputHandler(InputHandler.getInstance());

        HotkeysCallback.init();

        TickHandler.getInstance().registerClientTickHandler(mc -> SprintLock.onClientTick());

        LOGGER.info("[{}] initialized", MOD_NAME);
    }
}
