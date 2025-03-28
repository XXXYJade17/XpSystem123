package com.XXXYJade17.XpSystem.NetWork.Client;

import com.XXXYJade17.XpSystem.Data.ClientData.PlayerData;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class ClientPayloadHandler {
    private static ClientPayloadHandler INSTANCE;

    private static int clientXp;
    private static int clientLevel;

    public static ClientPayloadHandler getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new ClientPayloadHandler();
        }
        return INSTANCE;
    }

    public void handleXpData(PlayerData data, PlayPayloadContext context) {
        clientXp = data.xp();
        clientLevel = data.level();
        System.out.println("Client received xp data: " + clientXp + ", level: " + clientLevel);
    }

    public static int getClientXp() {
        return clientXp;
    }

    public static int getClientLevel() {
        return clientLevel;
    }
}
