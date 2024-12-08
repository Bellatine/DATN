package com.namng7.datn_v1.util;

import com.namng7.datn_v1.cache.CacheManager;
import com.namng7.datn_v1.cache.Key;

import org.apache.logging.log4j.Logger;

public class MessageUtil {
    private static StringBuilder log = new StringBuilder();

    public static String getMessage(String key, Logger logger){
        String content = CacheManager.Message.MapMessageByMessageCode.get(key);
        if(content == null || "".equals(content)){
            content = Key.Message.NULL_MESSAGE;
        }
        log.setLength(0);
        log.append("Lay message: ").append(key)
                .append(". Noi dung: ").append(content);
        logger.info(log.toString());
        return content;
    }
}
