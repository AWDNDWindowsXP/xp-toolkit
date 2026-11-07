package com.xp.xptoolkit.features;

import com.xp.xptoolkit.config.Configs;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public class FeatureToggle {
    public static boolean toggle(ConfigBooleanHotkeyed config) {
        config.toggleBooleanValue();
        Configs.INSTANCE.save();

        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            boolean on = config.getBooleanValue();
            Component msg = Component.literal("功能 " + config.getName() + " ")
                    .append(Component.literal(on ? "开启" : "关闭")
                            .withStyle(on ? ChatFormatting.GREEN : ChatFormatting.RED));
            player.displayClientMessage(msg, true);
        }
        return true;
    }
}
