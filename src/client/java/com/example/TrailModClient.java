package com.example;

import net.fabricmc.api.ClientModInitializer;
import com.example.keybind.TrailKeybinds;
import com.example.trail.TrailManager;

public class TrailModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        System.out.println("CLIENT MOD LOADED");

        TrailKeybinds.register();
        TrailManager.register();
    }
}