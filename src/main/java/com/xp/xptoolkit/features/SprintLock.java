package com.xp.xptoolkit.features;

import com.xp.xptoolkit.config.Configs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public class SprintLock {
    public static void onClientTick() {
        if (!Configs.SPRINT_LOCK.getBooleanValue()) return;

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null || mc.level == null) return;
        if (player.isSpectator() || player.isPassenger()) return;

        if (player.zza <= 0.0F) return;

        boolean creative = player.getAbilities().instabuild;
        if (!creative && player.getFoodData().getFoodLevel() <= 6) return;

        if (player.isUsingItem()) return;

        if (!player.isSprinting()) {
            player.setSprinting(true);
        }
    }
}
