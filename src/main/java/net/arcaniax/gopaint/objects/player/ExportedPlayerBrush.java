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
import net.arcaniax.gopaint.objects.brush.Brush;
import net.arcaniax.gopaint.objects.other.BlockType;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ExportedPlayerBrush {

    Boolean surfaceEnabled = false;
    Boolean maskEnabled = false;
    Brush b;
    int size = 10;
    int chance = 50;
    int thickness = 1;
    int angleDistance = 2;
    int fractureDistance = 2;
    int falloffStrength = 50;
    int mixingStrength = 50;
    Double minAngleHeightDifference = 2.5;
    String axis = "y";
    List<BlockType> blocks;
    BlockType mask;

    public ExportedPlayerBrush(String name, List<String> lore) {
        b = GoPaintPlugin.getBrushManager().getBrush(name.replaceAll(" §b♦ ", ""));
        blocks = new ArrayList<>();
        for (String s : lore) {
            if (s.startsWith("§8尺寸: ")) {
                size = Integer.parseInt(s.replaceAll("§8尺寸: ", ""));
            } else if (s.startsWith("§8機率: ")) {
                chance = Integer.parseInt(s.replaceAll("§8機率: ", "").replaceAll("%", ""));
            }
            if (s.startsWith("§8厚度: ")) {
                thickness = Integer.parseInt(s.replaceAll("§8厚度: ", ""));
            }
            if (s.startsWith("§8軸: ")) {
                axis = s.replaceAll("§8軸: ", "");
            }
            if (s.startsWith("§8斷裂距離: ")) {
                fractureDistance = Integer.parseInt(s.replaceAll("§8斷裂距離: ", ""));
            }
            if (s.startsWith("§8角距離: ")) {
                angleDistance = Integer.parseInt(s.replaceAll("§8角距離: ", ""));
            }
            if (s.startsWith("§8角高差: ")) {
                minAngleHeightDifference = Double.parseDouble(s.replaceAll("§8角高差: ", ""));
            }
            if (s.startsWith("§8混合度: ")) {
                mixingStrength = Integer.parseInt(s.replaceAll("§8混合度: ", ""));
            }
            if (s.startsWith("§8衰減: ")) {
                falloffStrength = Integer.parseInt(s.replaceAll("§8衰減: ", ""));
            }
            if (s.startsWith("§8方塊: ")) {
                s = s.replaceAll("§8方塊: ", "");
                if (!s.equals("none")) {
                    for (String s2 : s.split(" ")) {
                        String[] type = s2.split("\\:");
                        Material mat = Material.getMaterial(type[0].toUpperCase());
                        int data = Integer.parseInt(type[1]);
                        blocks.add(new BlockType(mat, (short) data));
                    }
                }
            }
            if (s.startsWith("§8遮罩: ")) {
                s = s.replaceAll("§8遮罩: ", "");
                String[] type = s.split("\\:");
                Material mat = Material.getMaterial(type[0].toUpperCase());
                int data = Integer.parseInt(type[1]);
                mask = new BlockType(mat, (short) data);
                maskEnabled = true;
            }
            if (s.startsWith("§8表面模式")) {
                surfaceEnabled = true;
            }
        }
    }

    public Brush getBrush() {
        return b;
    }

    public BlockType getMask() {
        return mask;
    }

    public List<BlockType> getBlocks() {
        return blocks;
    }

    public int getBrushSize() {
        return size;
    }

    public int getChance() {
        return chance;
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

    public boolean isMaskEnabled() {
        return maskEnabled;
    }

    public boolean isSurfaceModeEnabled() {
        return surfaceEnabled;
    }

    public int getThickness() {
        return thickness;
    }


    public int getFalloffStrength() {
        return falloffStrength;
    }

    public int getMixingStrength() {
        return mixingStrength;
    }

    public String getAxis() {
        return axis;
    }

}
