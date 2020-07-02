package com.demo.cloud.system.util;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 工具类
 *
 * @author sheng
 * @date 2020/07/01
 */
public class Tools {

    public Tools() {
    }

    public static List<PageData> convertMenu(List<PageData> list, String parm, String fparm, String pval) {
        List<PageData> listTem = new ArrayList();

        for(int i = 0; i < list.size(); ++i) {
            if ("0".equals(((PageData)list.get(i)).getString(fparm))) {
                PageData pd = (PageData)list.get(i);
                pd.put("childs", transMenu(list, parm, fparm, ((PageData)list.get(i)).getString(parm)));
                listTem.add(pd);
            }
        }

        return listTem;
    }

    public static List<PageData> transMenu(List<PageData> list, String parm, String fparm, String pval) {
        List<PageData> listTem = new ArrayList();

        for(int i = 0; i < list.size(); ++i) {
            if (pval.equals(((PageData)list.get(i)).getString(fparm))) {
                PageData pd = (PageData)list.get(i);
                pd.put("childs", transMenu(list, parm, fparm, ((PageData)list.get(i)).getString(parm)));
                listTem.add(pd);
            }
        }

        return listTem;
    }

    public static int getRandomNum() {
        return getRandomNum(6);
    }

    public static int getRandomNum(int digit) {
        String str = "";

        for(int i = 1; i < digit; ++i) {
            str = str + "0";
        }
        Random r = new Random();
        return r.nextInt(Integer.valueOf("9" + str)) + Integer.valueOf("1" + str);
    }

    public static String getClasspath() {
        String path = (Thread.currentThread().getContextClassLoader().getResource("") + "../../").replaceAll("file:/", "").replaceAll("%20", " ").trim();
        if (path.indexOf(":") != 1) {
            path = File.separator + path;
        }

        return path;
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
