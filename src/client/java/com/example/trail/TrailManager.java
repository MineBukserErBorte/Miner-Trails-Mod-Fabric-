package com.example.trail;

import com.example.keybind.TrailKeybinds;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.DustParticleOptions;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TrailManager {

    private static final List<TrailMarker> markers = new ArrayList<>();
    private static int tickCounter = 0;

    public static void register() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;

            if (player == null || mc.level == null) return;

            tickCounter++;

            // Every 1 second (20 ticks)
            if (tickCounter >= 10 && TrailKeybinds.enabled) {
                tickCounter = 0;

                // Only if moving
                if (player.getDeltaMovement().length() > 0.01) {

                    markers.add(new TrailMarker(
                            player.getX(),
                            player.getY(),
                            player.getZ(),
                            200 // 10 seconds (200 ticks)
                    ));
                }
            }

            // Update markers
            Iterator<TrailMarker> iterator = markers.iterator();

            while (iterator.hasNext()) {
                TrailMarker marker = iterator.next();

                marker.lifetime--;

                // Spawn particle
                for (int i = 0; i < 4; i++) {
                    mc.level.addParticle(
                            net.minecraft.core.particles.ParticleTypes.END_ROD,
                            marker.x + (Math.random() - 0.5) * 0.3,
                            marker.y + 0.2,
                            marker.z + (Math.random() - 0.5) * 0.3,
                            0, 0, 0
                    );
                }

                // Remove when expired
                if (marker.lifetime <= 0) {
                    iterator.remove();
                }
            }
        });
    }
}