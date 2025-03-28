package com.XXXYJade17.XpSystem.Capibility;

import com.XXXYJade17.XpSystem.Config.XpConfig;
import net.minecraft.nbt.CompoundTag;

public class PlayerXp {
    private int xp=0;
    private int level=1;

    public void addXp(int addedXp){
        xp+=addedXp;
    }

    public int getRequiredXp(int level){
        return XpConfig.getRequiredXp(level);
    }

    public void saveData(CompoundTag nbt) {
        nbt.putInt("level", level);
        nbt.putInt("xp", xp);
    }

    public void loadData(CompoundTag nbt) {
        level = nbt.getInt("level");
        xp = nbt.getInt("xp");
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }
}
