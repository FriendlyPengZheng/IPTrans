package trans;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestMain {

    private static String ID = null;
    private static String start_ip = null;
    private static String end_ip = null;
    private static String province_code = null;
    private static String isp = null;
    private static String start_ip1 = null;
    private static String end_ip1 = null;
    private static String flagProvince = null;
    private static String code2 = null;
    private static String codeItem = null;
    private static String codeFlag = null;

    public static void main(String[] args) {

        Map<String, String> map = new HashMap<String, String>();
        StringBuffer sbPovince = new StringBuffer();
        //	start_ip, end_ip, code2, start_ip1, end_ip1
        List<String> countryList = DbUtil.selectFromCountryIp();
        int i = 0;
        for (String s : countryList) {
            String[] items = s.split("#");
            //ID = items[0];
            start_ip = items[0];
            end_ip = items[1];
            code2 = items[2];
            if ("CN".equals(code2)) {
                codeItem = "China";
            } else if ("null".equals(code2)) {
                codeItem = "unknow";
            } else if ("IA".equals(code2)) {
                codeItem = "unknow";
            } else {
                codeItem = "foreign";
            }
            start_ip1 = items[3];
            end_ip1 = items[4];

            String start1 = null;
            String end1 = null;
            String startIp1 = null;
            String endIp1 = null;
            if ("China".equals(codeItem)) {
                continue;
            } else {
                if (map.isEmpty()) {
                    String valueString = start_ip + "#" + end_ip + "#" + start_ip1 + "#" + end_ip1;
                    map.put(codeItem, valueString);
                    codeFlag = codeItem;
                } else {
                    if (map.containsKey(codeItem)) {
                        //获取出value同当前IP区间拼接
                        String[] values = map.get(codeItem).split("#");
                        start1 = values[0];
                        end1 = values[1];
                        startIp1 = values[2];
                        endIp1 = values[3];
                        if ((Long.parseLong(end1) + 1L) == Long.parseLong(start_ip)) {
                            String valueString = start1 + "#" + end_ip + "#" + startIp1 + "#" + end_ip1;
                            map.put(codeItem, valueString);
                        } else {
                            new Throwable("ip is not continuous");
                        }
                        codeFlag = codeItem;
                    } else {
                        i++;
                        //输出map，同时清空map，然后将当前内容写入map
                        String[] values = null;
                        if (map.get(codeFlag) == null) {
                            System.out.println(codeFlag);
                        }
//						ID, start_ip, end_ip, province_code, isp_code, start_ip1, end_ip1
                        values = map.get(codeFlag).split("#");
                        if (i % 1000 == 1) {
                            sbPovince.append("INSERT INTO t_province_ip VALUES ");
                        }
                        sbPovince.append("(" + i + ",");
                        sbPovince.append(values[0] + ",");
                        sbPovince.append(values[1] + ",");
                        if ("unknow".equals(codeFlag)) {
                            sbPovince.append(0 + ",");
                        } else if ("foreign".equals(codeFlag)) {
                            sbPovince.append(830000 + ",");
                        } else {
                            new Throwable(codeFlag + "is not found");
                        }

                        //增加isp_code（目前得到的是isp）
                        //isp_code统一为0
                        sbPovince.append(  0  + ",");
                        sbPovince.append("'" + values[2] + "'" + ",");
                        if (i % 1000 == 0) {
                            sbPovince.append("'" + values[3] + "'" + ");");
                            sbPovince.append("\r\n");
                        } else {
                            sbPovince.append("'" + values[3] + "'" + "),");
                        }
                        map.clear();
                        String valueString = start_ip + "#" + end_ip + "#" + start_ip1 + "#" + end_ip1;
                        map.put(codeItem, valueString);
                        codeFlag = codeItem;
                    }
                }
            }
        }

        ReadFIle.writeFile(sbPovince.toString(),new File("D:\\sqlProvince_other.sql"));
        //System.out.println(sbPovince.toString());

        /*try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
