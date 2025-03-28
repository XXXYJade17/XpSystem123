package com.XXXYJade17.XpSystem.Capibility.Provider;

import com.XXXYJade17.XpSystem.Capibility.PlayerXp;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerXpProvider implements ICapabilityProvider<Player, Void, PlayerXp>, INBTSerializable<CompoundTag> {
    private PlayerXp xp;

    private PlayerXp getPlayerXp() {
        if (this.xp == null) {
            this.xp = new PlayerXp();
        }
        return this.xp;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        getPlayerXp().saveData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(@NotNull CompoundTag nbt) {
        getPlayerXp().loadData(nbt);
    }

    @Override
    public @Nullable PlayerXp getCapability(Player o, Void unused) {
        return getPlayerXp();
    }
}
