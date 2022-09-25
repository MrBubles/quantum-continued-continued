package me.derp.quantum.features.modules.misc;

import me.derp.quantum.features.modules.Module;
import me.derp.quantum.features.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.util.EnumHand;

public class AutoFrameDupe
extends Module {
    public Setting<Integer> turns;
    public Setting<Integer> ticks;
    public Setting<Integer> range;
    public Setting<Boolean> shulkersonly;
    private int timeout_ticks;

    public AutoFrameDupe() {
        super("FrameDupe", "6b dupe.", Module.Category.MISC, true, false, false);
        this.shulkersonly = this.register(new Setting<Boolean>("Shulkers Only", true));
        this.range = this.register(new Setting<Integer>("Range", 5, 0, 6));
        this.turns = this.register(new Setting<Integer>("Turns", 1, 0, 3));
        this.ticks = this.register(new Setting<Integer>("Ticks", 10, 1, 20));
        this.timeout_ticks = 0;
    }

    private int getShulkerSlot() {
        int n = -1;
        for (int i = 0; i < 9; ++i) {
            Item item = AutoFrameDupe.mc.player.inventory.getStackInSlot(i).getItem();
            if (!(item instanceof ItemShulkerBox)) continue;
            n = i;
        }
        return n;
    }

    @Override
    public void onUpdate() {
        if (AutoFrameDupe.mc.player != null && AutoFrameDupe.mc.world != null) {
            int n;
            if (this.shulkersonly.getValue().booleanValue() && (n = this.getShulkerSlot()) != -1) {
                AutoFrameDupe.mc.player.inventory.currentItem = n;
            }
            for (Entity entity : AutoFrameDupe.mc.world.loadedEntityList) {
                if (!(entity instanceof EntityItemFrame) || !(AutoFrameDupe.mc.player.getDistance(entity) <= (float)this.range.getValue().intValue())) continue;
                if (this.timeout_ticks >= this.ticks.getValue()) {
                    if (((EntityItemFrame)entity).getDisplayedItem().getItem() == Items.AIR && !AutoFrameDupe.mc.player.getHeldItemMainhand().isEmpty) {
                        AutoFrameDupe.mc.playerController.interactWithEntity((EntityPlayer)AutoFrameDupe.mc.player, entity, EnumHand.MAIN_HAND);
                    }
                    if (((EntityItemFrame)entity).getDisplayedItem().getItem() != Items.AIR) {
                        for (int i = 0; i < this.turns.getValue(); ++i) {
                            AutoFrameDupe.mc.playerController.interactWithEntity((EntityPlayer)AutoFrameDupe.mc.player, entity, EnumHand.MAIN_HAND);
                        }
                        AutoFrameDupe.mc.playerController.attackEntity((EntityPlayer)AutoFrameDupe.mc.player, entity);
                        this.timeout_ticks = 0;
                    }
                }
                ++this.timeout_ticks;
            }
        }
    }
}

