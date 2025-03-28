package com.XXXYJade17.XpSystem.Config;

import com.XXXYJade17.XpSystem.XpSystem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class XpConfig {
    private static Map<Integer, Integer> levelXpMap;
    private static Logger LOGGER;
    private static Gson gson;
    private static XpConfig INSTANCE;

    public static XpConfig getINSTANCE() {
        if(INSTANCE == null){
            INSTANCE=new XpConfig();
        }
        return INSTANCE;
    }

    private XpConfig(){
        levelXpMap = new HashMap<>();
        LOGGER = XpSystem.getLOGGER();
        gson=new Gson();

        loadXpConfig();
    }

    public static void loadXpConfig() {
        try{
            //创建目录
            Path dir = Paths.get("config", "xpsystem");
            Files.createDirectories(dir);
            //创建文件
            Path path = dir.resolve("level.json");
            if (Files.notExists(path)) { // 修改：检查文件是否存在而不是目录
                try (InputStream inputStream = XpConfig.class.getResourceAsStream("/config/xpsystem/level.json")) {
                    if (inputStream != null) {
                        Files.copy(inputStream, path);
                    } else {
                        LOGGER.warn("XpFile is empty!");
                        return;
                    }
                    LOGGER.info("XpFile has created!");
                }
            }
            //读取文件
            try(FileReader reader = new FileReader(path.toFile())) {
                Map<Integer, Integer> loadedXp = gson.fromJson(reader, new TypeToken<Map<Integer, Integer>>() {}.getType());
                if (loadedXp != null) {
                    levelXpMap.putAll(loadedXp);
                }
                LOGGER.info("XpLevel has loaded!");
            }
        } catch (IOException e) {
            LOGGER.error("XpLevel is failed to load : ",e);
        }
    }

    public static int getRequiredXp(int level) {
        return levelXpMap.getOrDefault(level,0);
    }

    public static void testOutput(){
        LOGGER.info("Test Test!");
        for (Map.Entry<Integer, Integer> entry : levelXpMap.entrySet()) {
            LOGGER.info("Level: " + entry.getKey() + ", Required XP: " + entry.getValue());
        }
    }
}
