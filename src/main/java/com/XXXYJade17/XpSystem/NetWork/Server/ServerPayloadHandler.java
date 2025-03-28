package com.XXXYJade17.XpSystem.NetWork.Server;

import com.XXXYJade17.XpSystem.Capibility.ModCapabilities;
import com.XXXYJade17.XpSystem.Data.ClientData.PlayerData;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.Optional;

public class ServerPayloadHandler {
    private static ServerPayloadHandler INSTANCE;

    public static ServerPayloadHandler getINSTANCE() {
        if(INSTANCE==null){
            INSTANCE=new ServerPayloadHandler();
        }
        return INSTANCE;
    }

    public void handleXpData(PlayerData data, PlayPayloadContext context) {
        context.player().ifPresent(player -> {
            if (player instanceof ServerPlayer serverPlayer) {
                Optional.ofNullable(player.getCapability(ModCapabilities.PLAYER_XP_HANDLER))
                        .ifPresent(xp -> {
                            PacketDistributor.PLAYER.with(serverPlayer)
                                    .send(new PlayerData(xp.getXp(), xp.getLevel()));
                        });
            }
        });
    }
}
