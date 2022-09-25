package me.derp.quantum.features.gui.components.items.buttons;

import me.derp.quantum.Quantum;
import me.derp.quantum.features.gui.components.Component;
import me.derp.quantum.features.gui.components.items.Item;
import me.derp.quantum.features.modules.Module;
import me.derp.quantum.features.setting.Bind;
import me.derp.quantum.features.setting.Setting;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ModuleButton
        extends Button {
    private final Module module;
    private final ResourceLocation logo = new ResourceLocation("textures/oyvey.png");
    private List<Item> items = new ArrayList<Item>();
    private boolean subOpen;

    public ModuleButton(Module module) {
        super(module.getName());
        this.module = module;
        this.initSettings();
    }

    public static void drawCompleteImage(float f, float f2, int n, int n2) {
        GL11.glPushMatrix();
        GL11.glTranslatef(f, f2, 0.0f);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3f(0.0f, n2, 0.0f);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3f(n, n2, 0.0f);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3f(n, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    public void initSettings() {
        ArrayList<Item> arrayList = new ArrayList<Item>();
        if (!this.module.getSettings().isEmpty()) {
            for (Setting setting : this.module.getSettings()) {
                if (setting.getValue() instanceof Boolean && !setting.getName().equals("Enabled")) {
                    arrayList.add(new BooleanButton(setting));
                }
                if (setting.getValue() instanceof Bind && !setting.getName().equalsIgnoreCase("Keybind") && !this.module.getName().equalsIgnoreCase("Hud")) {
                    arrayList.add(new BindButton(setting));
                }
                if ((setting.getValue() instanceof String || setting.getValue() instanceof Character) && !setting.getName().equalsIgnoreCase("displayName")) {
                    arrayList.add(new StringButton(setting));
                }
                if (setting.isNumberSetting() && setting.hasRestriction()) {
                    arrayList.add(new Slider(setting));
                    continue;
                }
                if (!setting.isEnumSetting()) continue;
                arrayList.add(new EnumButton(setting));
            }
        }
        arrayList.add(new BindButton(this.module.getSettingByName("Keybind")));
        this.items = arrayList;
    }

    @Override
    public void drawScreen(int n, int n2, float f) {
        super.drawScreen(n, n2, f);
        if (!this.items.isEmpty()) {
            if (this.subOpen) {
                Quantum.textManager.drawString("-",this.x + this.width - 9, (float)this.y + 4.0f, 0xFFFFFF, true);
                float f2 = 1.0f;
                for (Item item : this.items) {
                    Component.counter1[0] = Component.counter1[0] + 1;
                    if (!item.isHidden()) {
                        item.setLocation(this.x + 1.0f, this.y + (f2 += 15.0f));
                        item.setHeight(15);
                        item.setWidth(this.width - 9);
                        item.drawScreen(n, n2, f);
                    }
                    item.update();
                }
            }
            else
            {
                Quantum.textManager.drawString("+",this.x + this.width - 9, (float)this.y + 4.0f, 0xFFFFFF, true);
            }
        }
    }

    @Override
    public void mouseClicked(int n, int n2, int n3) {
        super.mouseClicked(n, n2, n3);
        if (!this.items.isEmpty()) {
            if (n3 == 1 && this.isHovering(n, n2)) {
                this.subOpen = !this.subOpen;
                mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
            }
            if (this.subOpen) {
                for (Item item : this.items) {
                    if (item.isHidden()) continue;
                    item.mouseClicked(n, n2, n3);
                }
            }
        }
    }

    @Override
    public void onKeyTyped(char c, int n) {
        super.onKeyTyped(c, n);
        if (!this.items.isEmpty() && this.subOpen) {
            for (Item item : this.items) {
                if (item.isHidden()) continue;
                item.onKeyTyped(c, n);
            }
        }
    }

    @Override
    public int getHeight() {
        if (this.subOpen) {
            int n = 14;
            for (Item item : this.items) {
                if (item.isHidden()) continue;
                n += item.getHeight() + 1;
            }
            return n + 2;
        }
        return 14;
    }

    public Module getModule() {
        return this.module;
    }

    @Override
    public void toggle() {
        this.module.toggle();
    }

    @Override
    public boolean getState() {
        return this.module.isEnabled();
    }
}
 