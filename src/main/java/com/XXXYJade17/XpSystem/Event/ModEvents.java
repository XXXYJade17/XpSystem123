package com.XXXYJade17.XpSystem.Event;

import com.XXXYJade17.XpSystem.Capibility.ModCapabilities;
import com.XXXYJade17.XpSystem.Capibility.PlayerXp;
import com.XXXYJade17.XpSystem.Data.ClientData.PlayerData;
import com.XXXYJade17.XpSystem.XpSystem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.slf4j.Logger;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = XpSystem.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    private static final int TICKS_PER_MINUTE = 5*20;
    private static int tickCounter = 0;
    private static final Logger LOGGER = XpSystem.getLOGGER();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER) {
            Optional<PlayerXp> optionalPlayerXp = Optional.ofNullable(event.player.getCapability(ModCapabilities.PLAYER_XP_HANDLER));
            if (optionalPlayerXp.isEmpty()) {
                LOGGER.info("Player XP capability is null for player: {}", event.player.getName().getString());
                optionalPlayerXp.ifPresent(xp -> {
                    if (++tickCounter >= TICKS_PER_MINUTE) {
                        tickCounter = 0;
                        xp.addXp(1);
                        if (event.player instanceof ServerPlayer player) {
                            PacketDistributor.PLAYER.with(player)
                                    .send(new PlayerData(xp.getXp(), xp.getLevel()));
                            LOGGER.info("Player XP:{}", xp.getXp());
                            player.sendSystemMessage(Component.literal("当前XP:" + xp.getXp()));
                        }
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if (event.getEntity() instanceof ServerPlayer player&& event.getLevel() instanceof ServerLevel serverLevel) {
                Optional<PlayerXp> optionalPlayerXp = Optional.ofNullable(player.getCapability(ModCapabilities.PLAYER_XP_HANDLER));
                optionalPlayerXp.ifPresent(xp -> {
                    CompoundTag playerData = player.getPersistentData();
                    if (playerData.contains("XpSystemData")) {
                        CompoundTag xpData = playerData.getCompound("XpSystemData");
                        xp.loadData(xpData);
                        PacketDistributor.PLAYER.with(player)
                                .send(new PlayerData(xp.getXp(), xp.getLevel()));
                        LOGGER.info("Player {} has XP: {} and Level: {}", player.getName().getString(), xp.getXp(), xp.getLevel());
                    }});
            }
         }
    }
}
