package com.xp.xptoolkit.config;

import com.xp.xptoolkit.features.FeatureToggle;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import net.minecraft.client.Minecraft;

import static com.xp.xptoolkit.config.Configs.KEEP_CHAT_OPEN;
import static com.xp.xptoolkit.config.Configs.KEEP_UNSENT_MESSAGE;
import static com.xp.xptoolkit.config.Configs.OPEN_CONFIG;
import static com.xp.xptoolkit.config.Configs.SPRINT_LOCK;

public class HotkeysCallback implements IHotkeyCallback {
    @Override
    public boolean onKeyAction(KeyAction action, IKeybind key) {
        if (key == SPRINT_LOCK.getKeybind()) {
            return FeatureToggle.toggle(SPRINT_LOCK);
        }
        if (key == KEEP_CHAT_OPEN.getKeybind()) {
            return FeatureToggle.toggle(KEEP_CHAT_OPEN);
        }
        if (key == KEEP_UNSENT_MESSAGE.getKeybind()) {
            return FeatureToggle.toggle(KEEP_UNSENT_MESSAGE);
        }
        if (key == OPEN_CONFIG.getKeybind()) {
            Minecraft.getInstance().setScreen(new ConfigUi());
            return true;
        }
        return false;
    }

    public static void init() {
        HotkeysCallback callback = new HotkeysCallback();
        for (IHotkey hotkey : Configs.KEY_LIST) {
            hotkey.getKeybind().setCallback(callback);
        }
    }
}
