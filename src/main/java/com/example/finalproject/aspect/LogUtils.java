package com.example.finalproject.aspect;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogUtils {

    public static String getDaoResultLogInfo(final Logger log, final Object result) {
        StringBuilder resultInfo = new StringBuilder();

        if (result instanceof List) {
            resultInfo.append("RESULT_SIZE=").append(((List<?>) result).size());
        }

        if (log.isDebugEnabled() || !(result instanceof List)) {
            if (resultInfo.length() > 0) {
                resultInfo.append(" ");
            }
            resultInfo.append(result);
        }

        return resultInfo.toString();
    }
}