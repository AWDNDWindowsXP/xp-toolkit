package com.xp.xptoolkit.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.util.data.json.JsonUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.xp.xptoolkit.XPToolkitClient.MOD_ID;
import static fi.dy.masa.malilib.hotkeys.KeybindSettings.DEFAULT;
import static fi.dy.masa.malilib.hotkeys.KeybindSettings.PRESS_ALLOWEXTRA;

public class Configs implements IConfigHandler {
    public static final Configs INSTANCE = new Configs();

    private static final Path CONFIG_DIR = Paths.get("./config");
    private static final Path CONFIG_FILE = CONFIG_DIR.resolve(MOD_ID + ".json");

    public static final ConfigBooleanHotkeyed SPRINT_LOCK = new ConfigBooleanHotkeyed(
            "疾跑锁定",
            false,
            "",
            PRESS_ALLOWEXTRA,
            "开启后，只要有前进输入并且满足原版疾跑条件（食物值 > 6、未使用物品等），玩家会持续保持疾跑状态，无需一直按住疾跑键。\n右侧热键用于快速切换开关，切换时动作栏会显示当前状态。"
    );

    public static final ConfigBooleanHotkeyed KEEP_CHAT_OPEN = new ConfigBooleanHotkeyed(
            "聊天框不关闭",
            false,
            "",
            PRESS_ALLOWEXTRA,
            "开启后，发送聊天消息或命令后聊天框保持打开并自动清空输入，方便连续发送多条消息。\n右侧热键用于快速切换开关，切换时动作栏会显示当前状态。"
    );

    public static final ConfigBooleanHotkeyed KEEP_UNSENT_MESSAGE = new ConfigBooleanHotkeyed(
            "保留未发送消息",
            false,
            "",
            PRESS_ALLOWEXTRA,
            "开启后，按 Esc 关闭聊天框时，未发送的输入内容会被保留为草稿，下次打开聊天框自动恢复。\n无视原版“保存聊天草稿”选项，始终生效；已发送的消息不会被保留。\n右侧热键用于快速切换开关，切换时动作栏会显示当前状态。"
    );

    public static final ConfigHotkey OPEN_CONFIG = new ConfigHotkey(
            "打开设置页面",
            "",
            DEFAULT,
            "按下打开 XPToolkit 的设置页面。仅热键，无开关。"
    );

    public static final ImmutableList<IHotkey> KEY_LIST = ImmutableList.of(
            SPRINT_LOCK,
            KEEP_CHAT_OPEN,
            KEEP_UNSENT_MESSAGE,
            OPEN_CONFIG
    );

    public static final ImmutableList<IConfigBase> ALL_CONFIGS = ImmutableList.of(
            SPRINT_LOCK,
            KEEP_CHAT_OPEN,
            KEEP_UNSENT_MESSAGE,
            OPEN_CONFIG
    );

    @Override
    public void load() {
        if (CONFIG_FILE.toFile().isFile() && CONFIG_FILE.toFile().exists()) {
            JsonElement jsonElement = JsonUtils.parseJsonFile(CONFIG_FILE);
            if (jsonElement != null && jsonElement.isJsonObject()) {
                JsonObject obj = jsonElement.getAsJsonObject();
                ConfigUtils.readConfigBase(obj, MOD_ID, ALL_CONFIGS);
            }
        }
    }

    @Override
    public void save() {
        if ((CONFIG_DIR.toFile().exists() && CONFIG_DIR.toFile().isDirectory()) || CONFIG_DIR.toFile().mkdirs()) {
            JsonObject configRoot = new JsonObject();
            ConfigUtils.writeConfigBase(configRoot, MOD_ID, ALL_CONFIGS);
            JsonUtils.writeJsonToFile(configRoot, CONFIG_FILE);
        }
    }
}
