package project.protocol.utility;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class Utility {
    public static String convertToMd5(String convert) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");

            byte[] array = md.digest(convert.getBytes());
            StringBuilder sb = new StringBuilder();

            for (byte anArray : array) {
                sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
            }

            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException ignored) {}
        return null;
    }

    public static String getHostString(SocketAddress socketAddress) {
        return ((InetSocketAddress) socketAddress).getHostString();
    }

}
