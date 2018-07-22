package com.spring.example.d1;

/**
 * Created by adnenehamdouni on 20/07/2018.
 */

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class TrackingUtils {

    // All alphanum chars, excepts the look-alikes
    private static final String validCharacters = "ABCDEFGHJKLMNPQRSTWXYZ23456789";
    private static final Random random = new Random();
    private static final int length = 8;

    public static String generateKey() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<length; i++) {
            sb.append(validCharacters.charAt(random.nextInt(validCharacters.length())));
        }
        return sb.toString();
    }
}
