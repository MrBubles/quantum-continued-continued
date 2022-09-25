package me.derp.quantum.features.gui.components.items.buttons;

import me.derp.quantum.Quantum;
import me.derp.quantum.features.gui.OyVeyGui;
import me.derp.quantum.features.gui.components.Component;
import me.derp.quantum.features.gui.components.items.Item;
import me.derp.quantum.features.modules.client.ClickGui;
import me.derp.quantum.util.RenderUtil;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

public class Button
        extends Item {
    private boolean state;

    public Button(String string) {
        super(string);
        this.height = 15;
    }

    @Override
    public void drawScreen(int n, int n2, float f) {
        RenderUtil.drawRect(this.x, this.y, this.x + (float)this.width, this.y + (float)this.height - 0.5f, this.getState() ? (!this.isHovering(n, n2) ? Quantum.colorManager.getColorWithAlpha(Quantum.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue()) : Quantum.colorManager.getColorWithAlpha(Quantum.moduleManager.getModuleByClass(ClickGui.class).alpha.getValue())) : (!this.isHovering(n, n2) ? 0x35555555 : -2007673515));
        Quantum.textManager.drawStringWithShadow(this.getName(), this.x + 2.3f, this.y - 2.0f - (float)OyVeyGui.getClickGui().getTextOffset(), this.getState() ? -1 : -5592406);
    }

    @Override
    public void mouseClicked(int n, int n2, int n3) {
        if (n3 == 0 && this.isHovering(n, n2)) {
            this.onMouseClick();
        }
    }

    public void onMouseClick() {
        this.state = !this.state;
        this.toggle();
        mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
    }

    public void toggle() {
    }

    public boolean getState() {
        return this.state;
    }

    @Override
    public int getHeight() {
        return 14;
    }

    public boolean isHovering(int n, int n2) {
        for (Component component : OyVeyGui.getClickGui().getComponents()) {
            if (!component.drag) continue;
            return false;
        }
        return (float)n >= this.getX() && (float)n <= this.getX() + (float)this.getWidth() && (float)n2 >= this.getY() && (float)n2 <= this.getY() + (float)this.height;
    }
}
 