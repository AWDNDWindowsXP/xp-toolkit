package com.xp.xptoolkit.mixin;

import com.xp.xptoolkit.config.Configs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin {
    @Shadow
    protected EditBox input;

    // Enter 发送后原版会 setScreen(null) 关闭聊天框；开启功能时改为清空输入并保持打开。
    @Redirect(
            method = "keyPressed(Lnet/minecraft/client/input/KeyEvent;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Minecraft;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V"
            )
    )
    private void xptoolkit$keepChatOpen(Minecraft mc, Screen screen) {
        if (Configs.KEEP_CHAT_OPEN.getBooleanValue()) {
            this.input.setValue("");
        } else {
            mc.setScreen(screen);
        }
    }

    // shouldDiscardDraft() 仅在 INTENTIONAL(Esc 关闭) 分支读取原版 saveChatDrafts 选项来决定是否丢弃草稿。
    // 开启功能时把该选项强制视为 true，使 Esc 关闭也保留未发送内容；
    // 这个 get() 调用只出现在 INTENTIONAL 分支，因此不影响 DONE(已发送)/INTERRUPTED 分支。
    @Redirect(
            method = "shouldDiscardDraft",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/OptionInstance;get()Ljava/lang/Object;"
            )
    )
    private Object xptoolkit$forceSaveChatDrafts(OptionInstance<?> instance) {
        if (Configs.KEEP_UNSENT_MESSAGE.getBooleanValue()) {
            return Boolean.TRUE;
        }
        return instance.get();
    }
}
