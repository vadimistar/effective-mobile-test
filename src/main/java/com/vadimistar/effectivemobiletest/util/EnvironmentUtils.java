package com.vadimistar.effectivemobiletest.util;

import lombok.experimental.UtilityClass;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@UtilityClass
public class EnvironmentUtils {

    public static boolean isProfileActive(Environment environment, String profile) {
        return Arrays.stream(environment.getActiveProfiles())
                .anyMatch(env -> env.equalsIgnoreCase(profile));
    }
}
