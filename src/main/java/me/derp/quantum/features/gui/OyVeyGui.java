package me.derp.quantum.features.gui;

import me.derp.quantum.Quantum;
import me.derp.quantum.features.Feature;
import me.derp.quantum.features.gui.components.Component;
import me.derp.quantum.features.gui.components.items.Item;
import me.derp.quantum.features.gui.components.items.buttons.ModuleButton;
import me.derp.quantum.features.modules.Module;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class OyVeyGui
        extends GuiScreen {
    private static OyVeyGui oyveyGui;
    private static OyVeyGui INSTANCE;
    private final ArrayList<Component> components = new ArrayList();

    public OyVeyGui() {
        this.setInstance();
        this.load();
    }

    public static OyVeyGui getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OyVeyGui();
        }
        return INSTANCE;
    }

    public static OyVeyGui getClickGui() {
        return OyVeyGui.getInstance();
    }

    private void setInstance() {
        INSTANCE = this;
    }

    private void load() {
        int n = -100;
        Quantum.moduleManager.getCategories().stream().sorted(Comparator.comparingInt(category -> category.getName().length())).forEach(category -> {});
        for (final Module.Category category2 : Quantum.moduleManager.getCategories()) {
            this.components.add(new Component(category2.getName(), n += 102, 4, true){

                @Override
                public void setupItems() {
                    counter1 = new int[]{1};
                    Quantum.moduleManager.getModulesByCategory(category2).forEach(module -> {
                        if (!module.hidden) {
                            this.addButton(new ModuleButton((Module)module));
                        }
                    });
                }
            });
        }
        this.components.forEach(component -> component.getItems().sort(Comparator.comparing(Feature::getName)));
    }

    public void updateModule(Module module) {
        for (Component component : this.components) {
            for (Item item : component.getItems()) {
                if (!(item instanceof ModuleButton)) continue;
                ModuleButton moduleButton = (ModuleButton)item;
                Module module2 = moduleButton.getModule();
                if (module == null || !module.equals(module2)) continue;
                moduleButton.initSettings();
            }
        }
    }

    public void drawScreen(int n, int n2, float f) {
        this.checkMouseWheel();
        this.drawDefaultBackground();
        this.components.forEach(component -> component.drawScreen(n, n2, f));
    }

    public void mouseClicked(int n, int n2, int n3) {
        this.components.forEach(component -> component.mouseClicked(n, n2, n3));
    }

    public void mouseReleased(int n, int n2, int n3) {
        this.components.forEach(component -> component.mouseReleased(n, n2, n3));
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public final ArrayList<Component> getComponents() {
        return this.components;
    }

    public void checkMouseWheel() {
        int n = Mouse.getDWheel();
        if (n < 0) {
            this.components.forEach(component -> component.setY(component.getY() - 10));
        } else if (n > 0) {
            this.components.forEach(component -> component.setY(component.getY() + 10));
        }
    }

    public int getTextOffset() {
        return -6;
    }

    public Component getComponentByName(String string) {
        for (Component component : this.components) {
            if (!component.getName().equalsIgnoreCase(string)) continue;
            return component;
        }
        return null;
    }

    public void keyTyped(char c, int n) throws IOException {
        super.keyTyped(c, n);
        this.components.forEach(component -> component.onKeyTyped(c, n));
    }

    static {
        INSTANCE = new OyVeyGui();
    }
}