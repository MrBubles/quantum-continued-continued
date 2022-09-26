package me.derp.quantum.features.modules.misc;

import me.derp.quantum.features.modules.Module;
import me.derp.quantum.features.setting.Setting;
import me.derp.quantum.features.command.Command;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class AntiEffects extends Module {
    private final Setting<Boolean> jumpboost = this.register(new Setting<Boolean>("JumpBoost", false));
    private final Setting<Boolean> levitation = this.register(new Setting<Boolean>("Levitation", false));
    private final Setting<Boolean> blindness = this.register(new Setting<Boolean>("Blindness", false));
    private final Setting<Boolean> nausea = this.register(new Setting<Boolean>("Nausea", false));
    private final Setting<Boolean> notify = this.register(new Setting<Boolean>("Notify", false));

    public AntiEffects() {
        super("AntiEffects", "Removes Certain Effects.", Module.Category.MISC, false, false, false);
    }

    @Override
    public void onUpdate() {
        if (this.jumpboost.getValue().booleanValue()) {
        if (AntiEffects.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
            AntiEffects.mc.player.removePotionEffect(MobEffects.JUMP_BOOST);
            if (this.notify.getValue().booleanValue()) {
            Command.sendMessage("Blocked Jump Boost Effect.");
        }
        if (this.levitation.getValue().booleanValue()) {
        if (AntiEffects.mc.player.isPotionActive(MobEffects.LEVITATION)) {
            AntiEffects.mc.player.removePotionEffect(MobEffects.LEVITATION);
            if (this.notify.getValue().booleanValue()) {
            Command.sendMessage("Blocked Levitation Effect.");
        }
        if (this.blindness.getValue().booleanValue()) {
        if (AntiEffects.mc.player.isPotionActive(MobEffects.BLINDNESS)) {
            AntiEffects.mc.player.removePotionEffect(MobEffects.BLINDNESS);
            if (this.notify.getValue().booleanValue()) {
            Command.sendMessage("Blocked Blindness Effect.");
        }
        if (this.nausea.getValue().booleanValue()) {
        if (AntiEffects.mc.player.isPotionActive(MobEffects.NAUSEA)) {
            AntiEffects.mc.player.removePotionEffect(MobEffects.NAUSEA);
            if (this.notify.getValue().booleanValue()) {
            Command.sendMessage("Blocked Nausea Effect.");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
