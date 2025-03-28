package com.XXXYJade17.XpSystem;

import com.XXXYJade17.XpSystem.Config.XpConfig;
import com.XXXYJade17.XpSystem.Event.HideVanillaXpHudEvent;
import com.XXXYJade17.XpSystem.Event.ModEvents;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(XpSystem.MODID)
public class XpSystem
{
    public static final String MODID = "xpsystem";
    private static final Logger LOGGER = LogUtils.getLogger();

    private static XpConfig XP_CONFIG;

    public XpSystem(IEventBus modEventBus, ModContainer modContainer) {
        XP_CONFIG = XpConfig.getINSTANCE();
        NeoForge.EVENT_BUS.register(ModEvents.class);
        NeoForge.EVENT_BUS.register(HideVanillaXpHudEvent.class);
    }

    public static Logger getLOGGER(){
        return LOGGER;
    }

    public static XpConfig getXpConfig(){
        return XP_CONFIG;
    }

}
