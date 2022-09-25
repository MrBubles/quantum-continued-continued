package me.derp.quantum.features.modules.render;

import me.derp.quantum.features.modules.Module;
import me.derp.quantum.features.setting.Setting;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.entity.player.EntityPlayer;

public class Animations
extends Module {
    public static Setting<AnimationVersion> swingAnimationVersion;
    public static Setting<Boolean> playersDisableAnimations;
    public static Setting<Boolean> changeMainhand;
    public static Setting<Float> mainhand;
    public static Setting<Boolean> changeOffhand;
    public static Setting<Float> offhand;
    public static Setting<Boolean> slow;

    public Animations() {
        super("Animations", "Allows you to change animations in your hand", Module.Category.RENDER, true, false, false);
        swingAnimationVersion = this.register(new Setting<AnimationVersion>("Version", AnimationVersion.OneDotEight));
        playersDisableAnimations = this.register(new Setting<Boolean>("Disable Animations", false));
        changeMainhand = this.register(new Setting<Boolean>("Change Mainhand", true));
        mainhand = this.register(new Setting<Float>("Mainhand", Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(4.7509747f) ^ 0x7F1807FC)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(1.63819E38f) ^ 0x7EF67CC9)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(30.789412f) ^ 0x7E7650B7))));
        changeOffhand = this.register(new Setting<Boolean>("Change Offhand", true));
        offhand = this.register(new Setting<Float>("Offhand", Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(15.8065405f) ^ 0x7EFCE797)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(3.3688825E38f) ^ 0x7F7D7251)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(7.3325067f) ^ 0x7F6AA3E5))));
        slow = this.register(new Setting<Boolean>("SlowSwing", false));
    }

    @Override
    public void onUpdate() {
        if (playersDisableAnimations.getValue().booleanValue()) {
            for (EntityPlayer player : Animations.mc.world.playerEntities) {
                player.limbSwing = Float.intBitsToFloat(Float.floatToIntBits(1.8755627E38f) ^ 0x7F0D1A06);
                player.limbSwingAmount = Float.intBitsToFloat(Float.floatToIntBits(6.103741E37f) ^ 0x7E37AD83);
                player.prevLimbSwingAmount = Float.intBitsToFloat(Float.floatToIntBits(4.8253957E37f) ^ 0x7E11357F);
            }
        }
        if (changeMainhand.getValue().booleanValue() && Animations.mc.entityRenderer.itemRenderer.equippedProgressMainHand != mainhand.getValue().floatValue()) {
            Animations.mc.entityRenderer.itemRenderer.equippedProgressMainHand = mainhand.getValue().floatValue();
            Animations.mc.entityRenderer.itemRenderer.itemStackMainHand = Animations.mc.player.getHeldItemMainhand();
        }
        if (changeOffhand.getValue().booleanValue() && Animations.mc.entityRenderer.itemRenderer.equippedProgressOffHand != offhand.getValue().floatValue()) {
            Animations.mc.entityRenderer.itemRenderer.equippedProgressOffHand = offhand.getValue().floatValue();
            Animations.mc.entityRenderer.itemRenderer.itemStackOffHand = Animations.mc.player.getHeldItemOffhand();
        }
        if (swingAnimationVersion.getValue() == AnimationVersion.OneDotEight && (double)Animations.mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9) {
            Animations.mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0f;
            Animations.mc.entityRenderer.itemRenderer.itemStackMainHand = Animations.mc.player.getHeldItemMainhand();
        }
        if (this.slow.getValue().booleanValue()) {
            Animations.mc.player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 420000));
        }
    }

    @Override
    public void onDisable() {
        if (this.slow.getValue().booleanValue()) {
            Animations.mc.player.removePotionEffect(MobEffects.MINING_FATIGUE);
        }
    }

    public static enum AnimationVersion {
        OneDotEight,
        OneDotTwelve;

    }
}

