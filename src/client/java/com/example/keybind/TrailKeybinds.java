package com.example.keybind;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class TrailKeybinds {

    private static KeyMapping toggleKey;
    public static boolean enabled = true;

    public static void register() {

        toggleKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.trailmod.toggle",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_P,
                        net.minecraft.client.KeyMapping.Category.MISC
                )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.consumeClick()) {
                enabled = !enabled;

                if (client.player != null) {
                    client.player.displayClientMessage(
                            Component.literal("Trail: " + (enabled ? "ON" : "OFF")),
                            true
                    );
                }
            }
        });
    }
}