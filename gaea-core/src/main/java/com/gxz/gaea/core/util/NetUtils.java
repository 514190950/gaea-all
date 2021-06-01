package com.gxz.gaea.core.util;


import javafx.util.Pair;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class NetUtils extends cn.hutool.core.net.NetUtil {

    /**
     * ipv4最小最大值
     */
    public final static long MIN_IPV4 = 0L;

    public final static long MAX_IPV4 = 4294967295L;

    /****
     * 通过IP段字符串 返回long值ip范围
     * @author gxz
     * @param ip "127.0.0.1/15"
     * @return javafx.util.Pair<java.lang.Long, java.lang.Long>
     **/
    public static Pair<Long, Long> getRange(String ip) {
        long ipN = ipToLong(ip.split("/")[0]);
        int tag = (int) Math.pow(2.0, (32 - Double.parseDouble(ip.split("/")[1])));
        return new Pair<>(ipN, ipN + tag - 1);
    }

    /**
     * @param ipN 十进制ipv4地址
     * @return IPv4地址字符串，以'.'分割
     * 若转换错误则返回 null
     * @author Insomnia
     */
    public static String longToIp(long ipN) {
        if (ipN < 0 || ipN > MAX_IPV4) {
            return null;
        }
        return (ipN >>> 24) +
                "." +
                ((ipN & 0x00FFFFFF) >>> 16) +
                "." +
                ((ipN & 0x0000FFFF) >>> 8) +
                "." +
                (ipN & 0x000000FF);
    }

    /**
     * @param ip IPv4地址字符串，以'.'分割
     * @return 十进制ip地址
     * 若转换错误则返回 -1L
     * @author Insomnia
     */
    public static Long ipToLong(String ip) {
        if (null == ip) {
            return -1L;
        }
        long ipN = 0;
        String[] numbers = ip.split("\\.");
        if (numbers.length != 4) {
            return -1L;
        }
        for (int i = 0; i < 4; ++i) {
            int num;
            try {
                num = Integer.parseInt(numbers[i]);
            } catch (Exception e) {
                return -1L;
            }
            if (num < 0 || num > 255) {
                return -1L;
            }
            ipN = ipN << 8 | num;
        }
        return ipN;
    }

    /**
     * 是否ipv4
     *
     * @param ip IP地址字符串
     * @return 是否ipv4
     */
    public static boolean isIpv4(String ip) {
        try {
            if (ip == null || ip.length() == 0) {
                return false;
            }
            String[] elements = ip.split("\\.");
            if (elements.length != 4) {
                return false;
            }
            for (String element : elements) {
                int n = Integer.parseInt(element);
                if (n < 0 || n > 255) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String arrangeIp(String ipStr) {
        return ipStr.length() == 32 ? iPv6Hex2Host(ipStr) : longToIp(Long.parseLong(ipStr));
    }

    /**
     * @param ipStr ipv6 32位串
     * @return ipv6简写串
     * 若转换错误则返回 null
     * @author Insomnia
     */
    public static String iPv6Hex2Host(String ipStr) {
        return ipStr;
    }


}
