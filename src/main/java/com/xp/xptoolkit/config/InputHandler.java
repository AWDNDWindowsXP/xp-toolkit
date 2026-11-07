package com.xp.xptoolkit.config;

import com.xp.xptoolkit.XPToolkitClient;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybindManager;
import fi.dy.masa.malilib.hotkeys.IKeybindProvider;
import fi.dy.masa.malilib.hotkeys.IKeyboardInputHandler;

public class InputHandler implements IKeybindProvider, IKeyboardInputHandler {
    private static final InputHandler INSTANCE = new InputHandler();

    public static InputHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void addKeysToMap(IKeybindManager manager) {
        for (IHotkey hotkey : Configs.KEY_LIST) {
            manager.addKeybindToMap(hotkey.getKeybind());
        }
    }

    @Override
    public void addHotkeys(IKeybindManager manager) {
        manager.addHotkeysForCategory(XPToolkitClient.MOD_ID, "按下式", Configs.KEY_LIST);
    }
}
