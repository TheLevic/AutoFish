package com.thelevic.autofish;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Items;
import org.lwjgl.glfw.GLFW;

public class AutoFish implements ModInitializer {

    private int counter = 60;
    private static boolean casted = false;
    private static boolean enabled = false;
    private static final KeyBinding keyBind = KeyBindingHelper.registerKeyBinding(new KeyBinding("Toggle Auto Fish", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_PERIOD,"Auto Fish"));

    public AutoFish(){
        AutoFish INSTANCE = this;
    }



    public static void setCasted(boolean bool){
        casted = bool;
    }
    private void toggleEnabled(){
        if (keyBind.wasPressed()){
            enabled = !enabled;
        }
    }
    public static boolean getEnabled(){
        return enabled;
    }
    private void castRod(MinecraftClient mc){
        if (mc.player != null && mc.world != null){
            if (HoldingFishingRod(mc)){
                mc.interactionManager.interactItem(mc.player,mc.player.getActiveHand());
                casted = true;
                counter = 60;
            }
        }
    }

    private void subtractCounter(){
        if (counter > 0 ){
            counter--;
        }
    }

    private boolean HoldingFishingRod(MinecraftClient mc){
        if (mc.player != null){
            return mc.player.getMainHandStack().getItem() == Items.FISHING_ROD;
        }
        return false;
    }

    private void clickTickEvent(MinecraftClient mc){
        toggleEnabled();
        subtractCounter();
        if (getEnabled() && !casted && counter <= 0){
            castRod(mc);
        }
    }
    @Override
    public void onInitialize(){
        ClientTickEvents.END_CLIENT_TICK.register(this::clickTickEvent);
    }
}
