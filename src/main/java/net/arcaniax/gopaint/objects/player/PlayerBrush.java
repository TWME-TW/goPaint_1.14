/*
 * goPaint is designed to simplify painting inside of Minecraft.
 * Copyright (C) Arcaniax-Development
 * Copyright (C) Arcaniax team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.arcaniax.gopaint.objects.player;

import net.arcaniax.gopaint.GoPaintPlugin;
import net.arcaniax.gopaint.objects.brush.AngleBrush;
import net.arcaniax.gopaint.objects.brush.Brush;
import net.arcaniax.gopaint.objects.brush.DiscBrush;
import net.arcaniax.gopaint.objects.brush.FractureBrush;
import net.arcaniax.gopaint.objects.brush.GradientBrush;
import net.arcaniax.gopaint.objects.brush.OverlayBrush;
import net.arcaniax.gopaint.objects.brush.SplatterBrush;
import net.arcaniax.gopaint.objects.brush.SprayBrush;
import net.arcaniax.gopaint.objects.other.BlockType;
import net.arcaniax.gopaint.utils.GUI;
import net.arcaniax.gopaint.utils.XMaterial;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayerBrush {

    Boolean surfaceEnabled;
    Boolean maskEnabled;
    Boolean enabled;
    int brushSize;
    int chance;
    int thickness;
    int fractureDistance;
    int angleDistance;
    int falloffStrength;
    int mixingStrength;
    double minAngleHeightDifference;
    String axis;
    Brush brush;
    Inventory gui;
    BlockType mask;
    List<BlockType> blocks;


    public PlayerBrush() {
        surfaceEnabled = GoPaintPlugin.getSettings().isSurfaceModeEnabledDefault();
        maskEnabled = GoPaintPlugin.getSettings().isMaskEnabledDefault();
        enabled = GoPaintPlugin.getSettings().isEnabledDefault();
        chance = GoPaintPlugin.getSettings().getDefaultChance();
        thickness = GoPaintPlugin.getSettings().getDefaultThickness();
        fractureDistance = GoPaintPlugin.getSettings().getDefaultFractureDistance();
        angleDistance = GoPaintPlugin.getSettings().getDefaultAngleDistance();
        minAngleHeightDifference = GoPaintPlugin.getSettings().getDefaultAngleHeightDifference();
        falloffStrength = 50;
        mixingStrength = 50;
        axis = "y";
        brush = GoPaintPlugin.getBrushManager().cycle(brush);
        brushSize = GoPaintPlugin.getSettings().getDefaultSize();
        blocks = new ArrayList<>();
        blocks.add(new BlockType(XMaterial.STONE.parseMaterial(), (short) 0));
        mask = new BlockType(XMaterial.SPONGE.parseMaterial(), (short) 0);
        gui = GUI.Generate(this);
    }

    public void updateInventory() {
        GUI.Update(gui, this);
    }

    public Brush getBrush() {
        return brush;
    }

    public void setBrush(Brush b) {
        this.brush = b;
    }

    public int getFalloffStrength() {
        return falloffStrength;
    }

    public int getMixingStrength() {
        return mixingStrength;
    }

    public void increaseFalloffStrength() {
        if (falloffStrength <= 90) {
            falloffStrength += 10;
        }
        updateInventory();
    }

    public void decreaseFalloffStrength() {
        if (falloffStrength >= 10) {
            falloffStrength -= 10;
        }
        updateInventory();
    }

    public void increaseMixingStrength() {
        if (mixingStrength <= 90) {
            mixingStrength += 10;
        }
        updateInventory();
    }

    public void decreaseMixingStrength() {
        if (mixingStrength >= 10) {
            mixingStrength -= 10;
        }
        updateInventory();
    }

    public Double getMinHeightDifference() {
        return this.minAngleHeightDifference;
    }

    public int getAngleDistance() {
        return this.angleDistance;
    }

    public int getFractureDistance() {
        return this.fractureDistance;
    }

    public BlockType getMask() {
        return mask;
    }

    public void setMask(BlockType bt) {
        mask = bt;
        updateInventory();
    }

    public List<BlockType> getBlocks() {
        return blocks;
    }

    public void addBlock(BlockType bt, int slot) {
        if (blocks.size() >= slot) {
            blocks.set(slot - 1, bt);
        } else {
            blocks.add(bt);
        }
        updateInventory();
    }

    public void removeBlock(int slot) {
        if (blocks.size() >= slot) {
            blocks.remove(slot - 1);
            updateInventory();
        }
    }

    public void cycleBrush() {
        brush = GoPaintPlugin.getBrushManager().cycle(brush);
        updateInventory();
    }

    public void cycleBrushBackwards() {
        brush = GoPaintPlugin.getBrushManager().cycleBack(brush);
        updateInventory();
    }

    public int getBrushSize() {
        return brushSize;
    }

    public void setBrushSize(int size) {
        if (size <= GoPaintPlugin.getSettings().getMaxSize() && size > 0) {
            brushSize = size;
        } else if (size > GoPaintPlugin.getSettings().getMaxSize()) {
            brushSize = GoPaintPlugin.getSettings().getMaxSize();
        } else {
            brushSize = 1;
        }
        updateInventory();
    }

    public Inventory getInventory() {
        return gui;
    }

    public void increaseBrushSize(boolean x10) {
        if (x10) {
            if (brushSize + 10 <= GoPaintPlugin.getSettings().getMaxSize()) {
                brushSize += 10;
            } else {
                brushSize = GoPaintPlugin.getSettings().getMaxSize();
            }
        } else {
            if (brushSize < GoPaintPlugin.getSettings().getMaxSize()) {
                brushSize += 1;
            }
        }
        updateInventory();
    }

    public void decreaseBrushSize(boolean x10) {
        if (x10) {
            if (brushSize - 10 >= 1) {
                brushSize -= 10;
            } else {
                brushSize = 1;
            }
        } else {
            if (brushSize > 1) {
                brushSize -= 1;
            }
        }
        updateInventory();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void toggleEnabled() {
        enabled = !enabled;
        updateInventory();
    }

    public int getChance() {
        return chance;
    }

    public void increaseChance() {
        if (chance < 90) {
            chance += 10;
        }
        updateInventory();
    }

    public void decreaseChance() {
        if (chance > 10) {
            chance -= 10;
        }
        updateInventory();
    }

    public void increaseThickness() {
        if (thickness < GoPaintPlugin.getSettings().getMaxThickness()) {
            thickness += 1;
        }
        updateInventory();
    }

    public void decreaseThickness() {
        if (thickness > 1) {
            thickness -= 1;
        }
        updateInventory();
    }

    public void increaseAngleDistance() {
        if (angleDistance < GoPaintPlugin.getSettings().getMaxAngleDistance()) {
            angleDistance += 1;
        }
        updateInventory();
    }

    public void decreaseAngleDistance() {
        if (angleDistance > 1) {
            angleDistance -= 1;
        }
        updateInventory();
    }

    public void increaseFractureDistance() {
        if (this.fractureDistance < GoPaintPlugin.getSettings().getMaxFractureDistance()) {
            this.fractureDistance += 1;
        }
        updateInventory();
    }

    public void decreaseFractureDistance() {
        if (this.fractureDistance > 1) {
            this.fractureDistance -= 1;
        }
        updateInventory();
    }

    public void increaseAngleHeightDifference(boolean d15) {
        if (d15) {
            minAngleHeightDifference += 15.0;
        } else {
            minAngleHeightDifference += 5.0;
        }
        if (minAngleHeightDifference > GoPaintPlugin.getSettings().getMaxAngleHeightDifference()) {
            minAngleHeightDifference = GoPaintPlugin.getSettings().getMaxAngleHeightDifference();
        }
        updateInventory();
    }

    public void decreaseAngleHeightDifference(boolean d15) {
        if (d15) {
            minAngleHeightDifference -= 15.0;
        } else {
            minAngleHeightDifference -= 5.0;
        }
        if (minAngleHeightDifference < GoPaintPlugin.getSettings().getMinAngleHeightDifference()) {
            minAngleHeightDifference = GoPaintPlugin.getSettings().getMinAngleHeightDifference();
        }
        updateInventory();
    }

    public boolean isMaskEnabled() {
        return maskEnabled;
    }

    public void toggleMask() {
        maskEnabled = !maskEnabled;
        updateInventory();
    }

    public boolean isSurfaceModeEnabled() {
        return surfaceEnabled;
    }

    public void toggleSurfaceMode() {
        surfaceEnabled = !surfaceEnabled;
        updateInventory();
    }

    public int getThickness() {
        return thickness;
    }

    public String getAxis() {
        return axis;
    }

    public void cycleAxis() {
        switch (axis) {
            case "y":
                axis = "z";
                break;
            case "z":
                axis = "x";
                break;
            case "x":
                axis = "y";
                break;
        }
        updateInventory();
    }

    public ItemStack export(ItemStack i) {
        StringBuilder lore = new StringBuilder("___&8尺寸: " + brushSize);
        if (brush instanceof SplatterBrush || brush instanceof SprayBrush) {
            lore.append("___&8機率: ").append(chance).append("%");
        } else if (brush instanceof OverlayBrush) {
            lore.append("___&8厚度: ").append(thickness);
        } else if (brush instanceof DiscBrush) {
            lore.append("___&8軸: ").append(axis);
        } else if (brush instanceof AngleBrush) {
            lore.append("___&8角距離: ").append(this.angleDistance);
            lore.append("___&8角高差: ").append(this.minAngleHeightDifference);
        } else if (brush instanceof GradientBrush) {
            lore.append("___&8混合度: ").append(this.mixingStrength);
            lore.append("___&8衰減: ").append(this.falloffStrength);
        } else if (brush instanceof FractureBrush) {
            lore.append("___&8斷裂距離: ").append(this.fractureDistance);
        }
        lore.append("___&8方塊:");
        if (blocks.isEmpty()) {
            lore.append(" none");
        } else {
            for (BlockType bt : blocks) {
                lore.append(" ").append(bt.getMaterial().toString().toLowerCase()).append(":").append(bt.getData());
            }
        }
        if (maskEnabled) {
            lore.append("___&8遮罩: ").append(mask.getMaterial().toString().toLowerCase()).append(":").append(mask.getData());
        }
        if (surfaceEnabled) {
            lore.append("___&8表面模式");
        }
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(" §b♦ " + brush.getName() + " §b♦ ");
        if (!lore.toString().equals("")) {
            String[] loreListArray = lore.toString().split("___");
            List<String> loreList = new ArrayList<String>();
            for (String s : loreListArray) {
                loreList.add(s.replace("&", "§"));
            }
            im.setLore(loreList);
        }
        im.addEnchant(Enchantment.ARROW_INFINITE, 10, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(im);
        return i;
    }

}
