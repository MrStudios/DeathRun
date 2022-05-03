package pl.mrstudios.deathrun.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptUtil {

    public static String md5(String input) {

        String output = "none";
        byte[] defaultBytes = input.getBytes();

        try {

            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();
            messageDigest.update(defaultBytes);
            byte[] bytes = messageDigest.digest();

            StringBuilder stringBuilder = new StringBuilder();
            for (byte element : bytes) {

                String hex = Integer.toHexString(0xFF & element);
                if (hex.length() == 1) stringBuilder.append('0');

                stringBuilder.append(hex);

            }

            output = stringBuilder + "";

        } catch (NoSuchAlgorithmException exception) {

            return output;

        }

        return output;

    }

}
