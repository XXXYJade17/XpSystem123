package com.XXXYJade17.XpSystem.Data.ClientData;

import com.XXXYJade17.XpSystem.XpSystem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record PlayerData(int xp, int level) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(XpSystem.MODID, "xp_data");

    public PlayerData(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readInt());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(xp);
        buf.writeInt(level);
    }

    public ResourceLocation id() {
        return ID;
    }
}
