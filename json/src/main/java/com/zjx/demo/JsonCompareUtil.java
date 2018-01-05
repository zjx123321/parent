package com.zjx.demo;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Feng erhu
 * Date: 2018-01-04
 * Time: 11:24
 */
public class JsonCompareUtil {
    private static List<String> list = new ArrayList<String>();
    private static String detail = "";

    public static boolean compare(String str1, String str2) {

        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(str1);
        JsonParser parser1 = new JsonParser();
        JsonObject obj1 = (JsonObject) parser1.parse(str2);
        return compare(obj, obj1);
    }


    public static boolean compare(JsonArray o1, JsonArray o2) {
        {

            if (o1.size() != o2.size()) {
                detail="数组长度不一致,"+o1.size()+","+o2.size();
                return false;
            }
            for (JsonElement o11 : (JsonArray) o1) {
                //只要发现value2存在
                boolean flag1 = false;
                for (JsonElement o22 : (JsonArray) o2) {
                    if (o11.getClass() != o22.getClass()) {
                        continue;
                    }
                    boolean b = false;
                    if (o11 instanceof JsonObject) {
                        b = compare((JsonObject) o11, (JsonObject) o22);

                    } else if (o11 instanceof JsonArray) {
                        b = compare((JsonArray) o11, (JsonArray) o22);
                    } else {
                        b = o11.equals(o22);
                    }
                    if (b) {
                        flag1 = true;
                        break;
                    }
                    if (!(o11 instanceof JsonArray)) {
                        list.remove(list.size() - 1);
                    }
                }
                if (!flag1) {
                    detail = o11.toString();
                    return false;
                }


            }

            for (JsonElement o22 : (JsonArray) o2) {
                //只要发现value2存在
                boolean flag1 = false;
                for (JsonElement o11 : (JsonArray) o1) {
                    if (o11.getClass() != o22.getClass()) {
                        continue;
                    }
                    boolean b = false;
                    if (o11 instanceof JsonObject) {
                        b = compare((JsonObject) o11, (JsonObject) o22);
                    } else if (o11 instanceof JsonArray) {
                        b = compare((JsonArray) o11, (JsonArray) o22);
                    } else {
                        b = o11.equals(o22);
                    }
                    if (b) {
                        flag1 = true;
                        break;
                    }
                    if (!(o11 instanceof JsonArray)) {
                        list.remove(list.size() - 1);
                    }
                }
                if (!flag1) {
                    detail = o22.toString();
                    return false;
                }
            }


            return true;
        }
    }

    public static boolean compare(JsonObject o1, JsonObject o2) {

        if (o1 == o2)
            return true;
        Set<Map.Entry<String, JsonElement>> entries = o1.entrySet();
        Set<Map.Entry<String, JsonElement>> entries2 = o2.entrySet();


        if (entries.size() != entries2.size()) {
            StringBuilder builder1 = new StringBuilder();
            StringBuilder builder2 = new StringBuilder();
            for (Map.Entry<String, JsonElement> e : entries) {
                boolean has = false;
                for (Map.Entry<String, JsonElement> e2 : entries2) {
                    if (e.getKey().equals(e2.getKey())) {
                        has = true;
                        continue;
                    }
                }
                if (!has) {
                    builder1.append(e.getKey() + ",");
                }
            }
            for (Map.Entry<String, JsonElement> e : entries2) {
                boolean has = false;
                for (Map.Entry<String, JsonElement> e2 : entries) {
                    if (e.getKey().equals(e2.getKey())) {
                        has = true;
                        continue;
                    }
                }
                if (!has) {
                    builder2.append(e.getKey() + ",");
                }
            }
            detail = "map长度不一致";
            String s = builder1.toString();
            if (StringUtils.isNotEmpty(s)) {
                detail += "，前者多的属性:" + s.substring(0, s.length() - 1);
            }
            String s2 = builder2.toString();
            if (StringUtils.isNotEmpty(s2)) {
                detail += "，后者多的属性:" + s2.substring(0, s2.length() - 1);
            }
            return false;
        }

        try {
            Iterator<Map.Entry<String, JsonElement>> i = entries.iterator();
            while (i.hasNext()) {
                Map.Entry<String, JsonElement> e = i.next();
                String key = e.getKey();
                list.add(key);
                JsonElement value = e.getValue();
                if (value == null) {
                    if (!(o2.get(key) == null && o2.has(key)))
                        return false;
                } else {
                    JsonElement value2 = o2.get(key);
                    if (value2 == null) {
                        return false;
                    }
                    if (value.getClass() != value2.getClass()) {
                        return false;
                    }
                    if (value instanceof JsonArray) {
                        //无序
                        boolean compare = compare((JsonArray) value, (JsonArray) value2);
                        if (!compare) {
                            return false;
                        }
                    } else if (value instanceof JsonObject) {
                        boolean compare = compare((JsonObject) value, (JsonObject) value2);
                        if (!compare) {
                            return false;
                        }
                    } else {
                        if (!value.equals(o2.get(key))){
                            detail = value.toString();
                            return false;
                        }
                    }


                }
                list.remove(list.size() - 1);
            }
        } catch (ClassCastException unused) {
            return false;
        } catch (NullPointerException unused) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        boolean result = compare("{\"Code\":0,\"Data\":[{\"tId\":0,\"tName\":\"全部赛事\",\"tag\":\"ALL\",\"Data\":[{\"cId\":90001,\"cName\":\"欧洲赛事\",\"Data\":[{\"nId\":10001,\"nName\":\"英格兰\",\"Data\":[{\"uId\":17,\"uName\":\"英超\"},{\"uId\":18,\"uName\":\"英冠\"},{\"uId\":24,\"uName\":\"英甲\"},{\"uId\":25,\"uName\":\"英乙\"},{\"uId\":19,\"uName\":\"英足总杯\"},{\"uId\":21,\"uName\":\"英联杯\"},{\"uId\":334,\"uName\":\"英锦赛\"},{\"uId\":346,\"uName\":\"社区盾杯\"}]},{\"nId\":10031,\"nName\":\"意大利\",\"Data\":[{\"uId\":23,\"uName\":\"意甲\"},{\"uId\":53,\"uName\":\"意乙\"},{\"uId\":328,\"uName\":\"意大利杯\"},{\"uId\":341,\"uName\":\"意超杯\"}]},{\"nId\":10032,\"nName\":\"西班牙\",\"Data\":[{\"uId\":8,\"uName\":\"西甲\"},{\"uId\":54,\"uName\":\"西乙\"},{\"uId\":329,\"uName\":\"西班牙杯\"},{\"uId\":213,\"uName\":\"西超杯\"}]},{\"nId\":10030,\"nName\":\"德国\",\"Data\":[{\"uId\":35,\"uName\":\"德甲\"},{\"uId\":44,\"uName\":\"德乙\"},{\"uId\":217,\"uName\":\"德国杯\"},{\"uId\":799,\"uName\":\"德超杯\"}]},{\"nId\":10007,\"nName\":\"法国\",\"Data\":[{\"uId\":34,\"uName\":\"法甲\"},{\"uId\":182,\"uName\":\"法乙\"},{\"uId\":335,\"uName\":\"法国杯\"},{\"uId\":333,\"uName\":\"法联杯\"},{\"uId\":339,\"uName\":\"法超杯\"}]},{\"nId\":10044,\"nName\":\"葡萄牙\",\"Data\":[{\"uId\":238,\"uName\":\"葡超\"},{\"uId\":239,\"uName\":\"葡甲\"},{\"uId\":336,\"uName\":\"葡萄牙杯\"},{\"uId\":327,\"uName\":\"葡联杯\"},{\"uId\":345,\"uName\":\"葡超杯\"}]},{\"nId\":10022,\"nName\":\"苏格兰\",\"Data\":[{\"uId\":36,\"uName\":\"苏超\"},{\"uId\":206,\"uName\":\"苏冠\"},{\"uId\":347,\"uName\":\"苏足总杯\"},{\"uId\":332,\"uName\":\"苏联杯\"}]},{\"nId\":10035,\"nName\":\"荷兰\",\"Data\":[{\"uId\":37,\"uName\":\"荷甲\"},{\"uId\":131,\"uName\":\"荷乙\"},{\"uId\":330,\"uName\":\"荷兰杯\"}]},{\"nId\":10033,\"nName\":\"比利时\",\"Data\":[{\"uId\":38,\"uName\":\"比甲\"},{\"uId\":9,\"uName\":\"比乙\"},{\"uId\":326,\"uName\":\"比利时杯\"},{\"uId\":338,\"uName\":\"比超杯\"}]},{\"nId\":10009,\"nName\":\"瑞典\",\"Data\":[{\"uId\":40,\"uName\":\"瑞超\"},{\"uId\":46,\"uName\":\"瑞甲\"},{\"uId\":80,\"uName\":\"瑞典杯\"},{\"uId\":642,\"uName\":\"瑞超杯\"}]},{\"nId\":10019,\"nName\":\"芬兰\",\"Data\":[{\"uId\":41,\"uName\":\"芬超\"},{\"uId\":55,\"uName\":\"芬甲\"},{\"uId\":220,\"uName\":\"芬兰杯\"},{\"uId\":83,\"uName\":\"芬联杯\"}]},{\"nId\":10005,\"nName\":\"挪威\",\"Data\":[{\"uId\":20,\"uName\":\"挪超\"},{\"uId\":22,\"uName\":\"挪甲\"},{\"uId\":29,\"uName\":\"挪威杯\"},{\"uId\":351,\"uName\":\"挪超杯\"}]},{\"nId\":10008,\"nName\":\"丹麦\",\"Data\":[{\"uId\":39,\"uName\":\"丹超\"},{\"uId\":47,\"uName\":\"丹甲\"},{\"uId\":76,\"uName\":\"丹麦杯\"}]},{\"nId\":10017,\"nName\":\"奥地利\",\"Data\":[{\"uId\":45,\"uName\":\"奥甲\"},{\"uId\":135,\"uName\":\"奥乙\"},{\"uId\":445,\"uName\":\"奥地利杯\"}]},{\"nId\":10025,\"nName\":\"瑞士\",\"Data\":[{\"uId\":215,\"uName\":\"瑞士超\"},{\"uId\":216,\"uName\":\"瑞士甲\"},{\"uId\":399,\"uName\":\"瑞士杯\"}]},{\"nId\":10051,\"nName\":\"爱尔兰\",\"Data\":[{\"uId\":192,\"uName\":\"爱超\"},{\"uId\":193,\"uName\":\"爱甲\"},{\"uId\":195,\"uName\":\"爱足总杯\"},{\"uId\":194,\"uName\":\"爱联杯\"}]},{\"nId\":10130,\"nName\":\"北爱尔兰\",\"Data\":[{\"uId\":200,\"uName\":\"北爱超\"},{\"uId\":767,\"uName\":\"北爱杯\"},{\"uId\":611,\"uName\":\"北爱联杯\"}]},{\"nId\":10021,\"nName\":\"俄罗斯\",\"Data\":[{\"uId\":203,\"uName\":\"俄超\"},{\"uId\":204,\"uName\":\"俄甲\"},{\"uId\":91,\"uName\":\"俄杯\"},{\"uId\":397,\"uName\":\"俄超杯\"}]},{\"nId\":10047,\"nName\":\"波兰\",\"Data\":[{\"uId\":202,\"uName\":\"波兰甲\"},{\"uId\":281,\"uName\":\"波兰杯\"},{\"uId\":511,\"uName\":\"波超杯\"}]},{\"nId\":10086,\"nName\":\"乌克兰\",\"Data\":[{\"uId\":218,\"uName\":\"乌克超\"},{\"uId\":312,\"uName\":\"乌克兰杯\"},{\"uId\":219,\"uName\":\"乌超杯\"}]},{\"nId\":10018,\"nName\":\"捷克\",\"Data\":[{\"uId\":172,\"uName\":\"捷克甲\"},{\"uId\":282,\"uName\":\"捷克杯\"},{\"uId\":798,\"uName\":\"捷超杯\"}]},{\"nId\":10067,\"nName\":\"希腊\",\"Data\":[{\"uId\":185,\"uName\":\"希腊超\"},{\"uId\":375,\"uName\":\"希腊杯\"}]},{\"nId\":10077,\"nName\":\"罗马尼亚\",\"Data\":[{\"uId\":152,\"uName\":\"罗甲\"},{\"uId\":355,\"uName\":\"罗杯\"},{\"uId\":698,\"uName\":\"罗超杯\"}]},{\"nId\":10010,\"nName\":\"冰岛\",\"Data\":[{\"uId\":188,\"uName\":\"冰岛超\"},{\"uId\":675,\"uName\":\"冰甲\"},{\"uId\":504,\"uName\":\"冰岛杯\"},{\"uId\":786,\"uName\":\"冰超杯\"}]},{\"nId\":10011,\"nName\":\"匈牙利\",\"Data\":[{\"uId\":187,\"uName\":\"匈甲\"}]},{\"nId\":10046,\"nName\":\"土耳其\",\"Data\":[{\"uId\":52,\"uName\":\"土超\"},{\"uId\":96,\"uName\":\"土耳其杯\"},{\"uId\":505,\"uName\":\"土超杯\"}]},{\"nId\":10312,\"nName\":\"白俄罗斯\",\"Data\":[{\"uId\":169,\"uName\":\"白俄超\"}]},{\"nId\":10317,\"nName\":\"保加利亚\",\"Data\":[{\"uId\":247,\"uName\":\"保超\"}]},{\"nId\":10327,\"nName\":\"克罗地亚\",\"Data\":[{\"uId\":170,\"uName\":\"克甲\"}]},{\"nId\":10329,\"nName\":\"塞浦路斯\",\"Data\":[{\"uId\":171,\"uName\":\"塞浦甲\"}]},{\"nId\":10335,\"nName\":\"爱沙尼亚\",\"Data\":[{\"uId\":178,\"uName\":\"爱沙甲\"}]},{\"nId\":10352,\"nName\":\"以色列\",\"Data\":[{\"uId\":266,\"uName\":\"以超\"},{\"uId\":370,\"uName\":\"以杯\"}]},{\"nId\":10401,\"nName\":\"斯洛伐克\",\"Data\":[{\"uId\":211,\"uName\":\"斯洛伐超\"}]},{\"nId\":10402,\"nName\":\"斯洛文尼亚\",\"Data\":[{\"uId\":212,\"uName\":\"斯洛文甲\"}]},{\"nId\":10421,\"nName\":\"威尔士\",\"Data\":[{\"uId\":254,\"uName\":\"威超\"},{\"uId\":388,\"uName\":\"威尔士杯\"},{\"uId\":367,\"uName\":\"威联杯\"}]}]},{\"cId\":90002,\"cName\":\"美洲赛事\",\"Data\":[{\"nId\":10013,\"nName\":\"巴西\",\"Data\":[{\"uId\":325,\"uName\":\"巴甲\"},{\"uId\":390,\"uName\":\"巴乙\"},{\"uId\":373,\"uName\":\"巴西杯\"}]},{\"nId\":10048,\"nName\":\"阿根廷\",\"Data\":[{\"uId\":155,\"uName\":\"阿甲\"},{\"uId\":703,\"uName\":\"阿乙\"},{\"uId\":1024,\"uName\":\"阿根廷杯\"}]},{\"nId\":10026,\"nName\":\"美国\",\"Data\":[{\"uId\":242,\"uName\":\"美职\"},{\"uId\":495,\"uName\":\"美公开杯\"}]},{\"nId\":10012,\"nName\":\"墨西哥\",\"Data\":[{\"uId\":352,\"uName\":\"墨超\"}]},{\"nId\":10322,\"nName\":\"智利\",\"Data\":[{\"uId\":244,\"uName\":\"智甲\"}]},{\"nId\":10325,\"nName\":\"哥伦比亚\",\"Data\":[{\"uId\":241,\"uName\":\"哥伦甲\"}]},{\"nId\":10392,\"nName\":\"巴拉圭\",\"Data\":[{\"uId\":693,\"uName\":\"巴拉圭甲\"}]},{\"nId\":10416,\"nName\":\"乌拉圭\",\"Data\":[{\"uId\":278,\"uName\":\"乌拉圭甲\"}]}]},{\"cId\":90003,\"cName\":\"亚洲赛事\",\"Data\":[{\"nId\":10323,\"nName\":\"中国\",\"Data\":[{\"uId\":649,\"uName\":\"中超\"},{\"uId\":782,\"uName\":\"中甲\"},{\"uId\":882,\"uName\":\"中协杯\"}]},{\"nId\":10052,\"nName\":\"日本\",\"Data\":[{\"uId\":196,\"uName\":\"J联赛\"},{\"uId\":402,\"uName\":\"J2联赛\"},{\"uId\":323,\"uName\":\"天皇杯\"},{\"uId\":101,\"uName\":\"日联杯\"},{\"uId\":393,\"uName\":\"日超杯\"}]},{\"nId\":10291,\"nName\":\"韩国\",\"Data\":[{\"uId\":410,\"uName\":\"K联赛\"},{\"uId\":777,\"uName\":\"K2联赛\"},{\"uId\":615,\"uName\":\"韩足总杯\"},{\"uId\":687,\"uName\":\"韩联杯\"}]},{\"nId\":10034,\"nName\":\"澳大利亚\",\"Data\":[{\"uId\":136,\"uName\":\"澳A联\"}]},{\"nId\":10350,\"nName\":\"伊朗\",\"Data\":[{\"uId\":915,\"uName\":\"伊朗超\"}]},{\"nId\":10396,\"nName\":\"卡塔尔\",\"Data\":[{\"uId\":825,\"uName\":\"卡塔尔联\"}]},{\"nId\":10398,\"nName\":\"沙特\",\"Data\":[{\"uId\":955,\"uName\":\"沙特联\"}]},{\"nId\":10400,\"nName\":\"新加坡\",\"Data\":[{\"uId\":634,\"uName\":\"新加坡联\"}]},{\"nId\":10409,\"nName\":\"泰国\",\"Data\":[{\"uId\":1032,\"uName\":\"泰超\"}]}]},{\"cId\":99999,\"cName\":\"洲际赛事\",\"Data\":[{\"nId\":90001,\"nName\":\"欧洲赛事\",\"Data\":[{\"uId\":7,\"uName\":\"欧冠杯\"},{\"uId\":679,\"uName\":\"欧联杯\"},{\"uId\":1,\"uName\":\"欧洲杯\"},{\"uId\":465,\"uName\":\"欧超杯\"},{\"uId\":27,\"uName\":\"欧预选\"},{\"uId\":454,\"uName\":\"U21欧洲杯\"},{\"uId\":26,\"uName\":\"欧预选21\"},{\"uId\":477,\"uName\":\"欧女锦\"},{\"uId\":886,\"uName\":\"奥迪杯\"}]},{\"nId\":90002,\"nName\":\"美洲赛事\",\"Data\":[{\"uId\":384,\"uName\":\"解放者杯\"},{\"uId\":498,\"uName\":\"美冠杯\"},{\"uId\":133,\"uName\":\"美洲杯\"},{\"uId\":140,\"uName\":\"金杯赛\"},{\"uId\":480,\"uName\":\"南俱杯\"},{\"uId\":1250,\"uName\":\"南超杯\"}]},{\"nId\":90003,\"nName\":\"亚洲赛事\",\"Data\":[{\"uId\":463,\"uName\":\"亚冠杯\"},{\"uId\":246,\"uName\":\"亚洲杯\"},{\"uId\":392,\"uName\":\"东亚杯\"},{\"uId\":474,\"uName\":\"亚运男足\"},{\"uId\":28,\"uName\":\"亚洲杯预\"},{\"uId\":404,\"uName\":\"女东亚杯\"},{\"uId\":622,\"uName\":\"海湾杯\"}]},{\"nId\":90004,\"nName\":\"非洲赛事\",\"Data\":[{\"uId\":270,\"uName\":\"非洲杯\"}]},{\"nId\":90999,\"nName\":\"国际赛事\",\"Data\":[{\"uId\":16,\"uName\":\"世界杯\"},{\"uId\":11,\"uName\":\"世欧预\"},{\"uId\":308,\"uName\":\"世亚预\"},{\"uId\":13,\"uName\":\"世非预\"},{\"uId\":14,\"uName\":\"世北美预\"},{\"uId\":295,\"uName\":\"世南美预\"},{\"uId\":290,\"uName\":\"世界杯女\"},{\"uId\":279,\"uName\":\"U17世界杯\"},{\"uId\":453,\"uName\":\"世青赛\"},{\"uId\":357,\"uName\":\"世俱杯\"},{\"uId\":436,\"uName\":\"奥运男足\"},{\"uId\":437,\"uName\":\"奥运女足\"},{\"uId\":411,\"uName\":\"阿尔加夫杯\"},{\"uId\":452,\"uName\":\"土伦杯21\"},{\"uId\":851,\"uName\":\"国际友谊\"},{\"uId\":460,\"uName\":\"联合会杯\"},{\"uId\":804,\"uName\":\"世女U20\"},{\"uId\":1337,\"uName\":\"吉尼斯杯\"}]}]}]},{\"tId\":17,\"tName\":\"英超\",\"tag\":\"HOT\",\"Data\":[]},{\"tId\":8,\"tName\":\"西甲\",\"tag\":\"HOT\",\"Data\":[]},{\"tId\":35,\"tName\":\"德甲\",\"tag\":\"HOT\",\"Data\":[]},{\"tId\":34,\"tName\":\"法甲\",\"tag\":\"HOT\",\"Data\":[]},{\"tId\":23,\"tName\":\"意甲\",\"tag\":\"HOT\",\"Data\":[]},{\"tId\":649,\"tName\":\"中超\",\"tag\":\"HOT\",\"Data\":[]},{\"tId\":238,\"tName\":\"葡超\",\"tag\":\"RECENT\",\"Data\":[]},{\"tId\":328,\"tName\":\"意大利杯\",\"tag\":\"RECENT\",\"Data\":[]},{\"tId\":136,\"tName\":\"澳A联\",\"tag\":\"RECENT\",\"Data\":[]},{\"tId\":329,\"tName\":\"西班牙杯\",\"tag\":\"RECENT\",\"Data\":[]}],\"Msg\":\"\",\"Stamp\":\"\"}"
                ,"{\"Code\":0,\"Data\":[{\"tId\":0,\"Data\":[{\"cName\":\"欧洲赛事\",\"cId\":90001,\"Data\":[{\"Data\":[{\"uName\":\"英超\",\"uId\":17},{\"uName\":\"英冠\",\"uId\":18},{\"uName\":\"英甲\",\"uId\":24},{\"uName\":\"英乙\",\"uId\":25},{\"uName\":\"英足总杯\",\"uId\":19},{\"uName\":\"英联杯\",\"uId\":21},{\"uName\":\"英锦赛\",\"uId\":334},{\"uName\":\"社区盾杯\",\"uId\":346}],\"nId\":10001,\"nName\":\"英格兰\"},{\"Data\":[{\"uName\":\"意甲\",\"uId\":23},{\"uName\":\"意乙\",\"uId\":53},{\"uName\":\"意大利杯\",\"uId\":328},{\"uName\":\"意超杯\",\"uId\":341}],\"nId\":10031,\"nName\":\"意大利\"},{\"Data\":[{\"uName\":\"西甲\",\"uId\":8},{\"uName\":\"西乙\",\"uId\":54},{\"uName\":\"西班牙杯\",\"uId\":329},{\"uName\":\"西超杯\",\"uId\":213}],\"nId\":10032,\"nName\":\"西班牙\"},{\"Data\":[{\"uName\":\"德甲\",\"uId\":35},{\"uName\":\"德乙\",\"uId\":44},{\"uName\":\"德国杯\",\"uId\":217},{\"uName\":\"德超杯\",\"uId\":799}],\"nId\":10030,\"nName\":\"德国\"},{\"Data\":[{\"uName\":\"法甲\",\"uId\":34},{\"uName\":\"法乙\",\"uId\":182},{\"uName\":\"法国杯\",\"uId\":335},{\"uName\":\"法联杯\",\"uId\":333},{\"uName\":\"法超杯\",\"uId\":339}],\"nId\":10007,\"nName\":\"法国\"},{\"Data\":[{\"uName\":\"葡超\",\"uId\":238},{\"uName\":\"葡甲\",\"uId\":239},{\"uName\":\"葡萄牙杯\",\"uId\":336},{\"uName\":\"葡联杯\",\"uId\":327},{\"uName\":\"葡超杯\",\"uId\":345}],\"nId\":10044,\"nName\":\"葡萄牙\"},{\"Data\":[{\"uName\":\"苏超\",\"uId\":36},{\"uName\":\"苏冠\",\"uId\":206},{\"uName\":\"苏足总杯\",\"uId\":347},{\"uName\":\"苏联杯\",\"uId\":332}],\"nId\":10022,\"nName\":\"苏格兰\"},{\"Data\":[{\"uName\":\"荷甲\",\"uId\":37},{\"uName\":\"荷乙\",\"uId\":131},{\"uName\":\"荷兰杯\",\"uId\":330}],\"nId\":10035,\"nName\":\"荷兰\"},{\"Data\":[{\"uName\":\"比甲\",\"uId\":38},{\"uName\":\"比乙\",\"uId\":9},{\"uName\":\"比利时杯\",\"uId\":326},{\"uName\":\"比超杯\",\"uId\":338}],\"nId\":10033,\"nName\":\"比利时\"},{\"Data\":[{\"uName\":\"瑞超\",\"uId\":40},{\"uName\":\"瑞甲\",\"uId\":46},{\"uName\":\"瑞典杯\",\"uId\":80},{\"uName\":\"瑞超杯\",\"uId\":642}],\"nId\":10009,\"nName\":\"瑞典\"},{\"Data\":[{\"uName\":\"芬超\",\"uId\":41},{\"uName\":\"芬甲\",\"uId\":55},{\"uName\":\"芬兰杯\",\"uId\":220},{\"uName\":\"芬联杯\",\"uId\":83}],\"nId\":10019,\"nName\":\"芬兰\"},{\"Data\":[{\"uName\":\"挪超\",\"uId\":20},{\"uName\":\"挪甲\",\"uId\":22},{\"uName\":\"挪威杯\",\"uId\":29},{\"uName\":\"挪超杯\",\"uId\":351}],\"nId\":10005,\"nName\":\"挪威\"},{\"Data\":[{\"uName\":\"丹超\",\"uId\":39},{\"uName\":\"丹甲\",\"uId\":47},{\"uName\":\"丹麦杯\",\"uId\":76}],\"nId\":10008,\"nName\":\"丹麦\"},{\"Data\":[{\"uName\":\"奥甲\",\"uId\":45},{\"uName\":\"奥乙\",\"uId\":135},{\"uName\":\"奥地利杯\",\"uId\":445}],\"nId\":10017,\"nName\":\"奥地利\"},{\"Data\":[{\"uName\":\"瑞士超\",\"uId\":215},{\"uName\":\"瑞士甲\",\"uId\":216},{\"uName\":\"瑞士杯\",\"uId\":399}],\"nId\":10025,\"nName\":\"瑞士\"},{\"Data\":[{\"uName\":\"爱超\",\"uId\":192},{\"uName\":\"爱甲\",\"uId\":193},{\"uName\":\"爱足总杯\",\"uId\":195},{\"uName\":\"爱联杯\",\"uId\":194}],\"nId\":10051,\"nName\":\"爱尔兰\"},{\"Data\":[{\"uName\":\"北爱超\",\"uId\":200},{\"uName\":\"北爱杯\",\"uId\":767},{\"uName\":\"北爱联杯\",\"uId\":611}],\"nId\":10130,\"nName\":\"北爱尔兰\"},{\"Data\":[{\"uName\":\"俄超\",\"uId\":203},{\"uName\":\"俄甲\",\"uId\":204},{\"uName\":\"俄杯\",\"uId\":91},{\"uName\":\"俄超杯\",\"uId\":397}],\"nId\":10021,\"nName\":\"俄罗斯\"},{\"Data\":[{\"uName\":\"波兰甲\",\"uId\":202},{\"uName\":\"波兰杯\",\"uId\":281},{\"uName\":\"波超杯\",\"uId\":511}],\"nId\":10047,\"nName\":\"波兰\"},{\"Data\":[{\"uName\":\"乌克超\",\"uId\":218},{\"uName\":\"乌克兰杯\",\"uId\":312},{\"uName\":\"乌超杯\",\"uId\":219}],\"nId\":10086,\"nName\":\"乌克兰\"},{\"Data\":[{\"uName\":\"捷克甲\",\"uId\":172},{\"uName\":\"捷克杯\",\"uId\":282},{\"uName\":\"捷超杯\",\"uId\":798}],\"nId\":10018,\"nName\":\"捷克\"},{\"Data\":[{\"uName\":\"希腊超\",\"uId\":185},{\"uName\":\"希腊杯\",\"uId\":375}],\"nId\":10067,\"nName\":\"希腊\"},{\"Data\":[{\"uName\":\"罗甲\",\"uId\":152},{\"uName\":\"罗杯\",\"uId\":355},{\"uName\":\"罗超杯\",\"uId\":698}],\"nId\":10077,\"nName\":\"罗马尼亚\"},{\"Data\":[{\"uName\":\"冰岛超\",\"uId\":188},{\"uName\":\"冰甲\",\"uId\":675},{\"uName\":\"冰岛杯\",\"uId\":504},{\"uName\":\"冰超杯\",\"uId\":786}],\"nId\":10010,\"nName\":\"冰岛\"},{\"Data\":[{\"uName\":\"匈甲\",\"uId\":187}],\"nId\":10011,\"nName\":\"匈牙利\"},{\"Data\":[{\"uName\":\"土超\",\"uId\":52},{\"uName\":\"土耳其杯\",\"uId\":96},{\"uName\":\"土超杯\",\"uId\":505}],\"nId\":10046,\"nName\":\"土耳其\"},{\"Data\":[{\"uName\":\"白俄超\",\"uId\":169}],\"nId\":10312,\"nName\":\"白俄罗斯\"},{\"Data\":[{\"uName\":\"保超\",\"uId\":247}],\"nId\":10317,\"nName\":\"保加利亚\"},{\"Data\":[{\"uName\":\"克甲\",\"uId\":170}],\"nId\":10327,\"nName\":\"克罗地亚\"},{\"Data\":[{\"uName\":\"塞浦甲\",\"uId\":171}],\"nId\":10329,\"nName\":\"塞浦路斯\"},{\"Data\":[{\"uName\":\"爱沙甲\",\"uId\":178}],\"nId\":10335,\"nName\":\"爱沙尼亚\"},{\"Data\":[{\"uName\":\"以超\",\"uId\":266},{\"uName\":\"以杯\",\"uId\":370}],\"nId\":10352,\"nName\":\"以色列\"},{\"Data\":[{\"uName\":\"斯洛伐超\",\"uId\":211}],\"nId\":10401,\"nName\":\"斯洛伐克\"},{\"Data\":[{\"uName\":\"斯洛文甲\",\"uId\":212}],\"nId\":10402,\"nName\":\"斯洛文尼亚\"},{\"Data\":[{\"uName\":\"威超\",\"uId\":254},{\"uName\":\"威尔士杯\",\"uId\":388},{\"uName\":\"威联杯\",\"uId\":367}],\"nId\":10421,\"nName\":\"威尔士\"}]},{\"cName\":\"美洲赛事\",\"cId\":90002,\"Data\":[{\"Data\":[{\"uName\":\"巴甲\",\"uId\":325},{\"uName\":\"巴乙\",\"uId\":390},{\"uName\":\"巴西杯\",\"uId\":373}],\"nId\":10013,\"nName\":\"巴西\"},{\"Data\":[{\"uName\":\"阿甲\",\"uId\":155},{\"uName\":\"阿乙\",\"uId\":703},{\"uName\":\"阿根廷杯\",\"uId\":1024}],\"nId\":10048,\"nName\":\"阿根廷\"},{\"Data\":[{\"uName\":\"美职\",\"uId\":242},{\"uName\":\"美公开杯\",\"uId\":495}],\"nId\":10026,\"nName\":\"美国\"},{\"Data\":[{\"uName\":\"墨超\",\"uId\":352}],\"nId\":10012,\"nName\":\"墨西哥\"},{\"Data\":[{\"uName\":\"智甲\",\"uId\":244}],\"nId\":10322,\"nName\":\"智利\"},{\"Data\":[{\"uName\":\"哥伦甲\",\"uId\":241}],\"nId\":10325,\"nName\":\"哥伦比亚\"},{\"Data\":[{\"uName\":\"巴拉圭甲\",\"uId\":693}],\"nId\":10392,\"nName\":\"巴拉圭\"},{\"Data\":[{\"uName\":\"乌拉圭甲\",\"uId\":278}],\"nId\":10416,\"nName\":\"乌拉圭\"}]},{\"cName\":\"亚洲赛事\",\"cId\":90003,\"Data\":[{\"Data\":[{\"uName\":\"中超\",\"uId\":649},{\"uName\":\"中甲\",\"uId\":782},{\"uName\":\"中协杯\",\"uId\":882}],\"nId\":10323,\"nName\":\"中国\"},{\"Data\":[{\"uName\":\"J联赛\",\"uId\":196},{\"uName\":\"J2联赛\",\"uId\":402},{\"uName\":\"天皇杯\",\"uId\":323},{\"uName\":\"日联杯\",\"uId\":101},{\"uName\":\"日超杯\",\"uId\":393}],\"nId\":10052,\"nName\":\"日本\"},{\"Data\":[{\"uName\":\"K联赛\",\"uId\":410},{\"uName\":\"K2联赛\",\"uId\":777},{\"uName\":\"韩足总杯\",\"uId\":615},{\"uName\":\"韩联杯\",\"uId\":687}],\"nId\":10291,\"nName\":\"韩国\"},{\"Data\":[{\"uName\":\"澳A联\",\"uId\":136}],\"nId\":10034,\"nName\":\"澳大利亚\"},{\"Data\":[{\"uName\":\"伊朗超\",\"uId\":915}],\"nId\":10350,\"nName\":\"伊朗\"},{\"Data\":[{\"uName\":\"卡塔尔联\",\"uId\":825}],\"nId\":10396,\"nName\":\"卡塔尔\"},{\"Data\":[{\"uName\":\"沙特联\",\"uId\":955}],\"nId\":10398,\"nName\":\"沙特\"},{\"Data\":[{\"uName\":\"新加坡联\",\"uId\":634}],\"nId\":10400,\"nName\":\"新加坡\"},{\"Data\":[{\"uName\":\"泰超\",\"uId\":1032}],\"nId\":10409,\"nName\":\"泰国\"}]},{\"cName\":\"洲际赛事\",\"cId\":99999,\"Data\":[{\"Data\":[{\"uName\":\"欧冠杯\",\"uId\":7},{\"uName\":\"欧联杯\",\"uId\":679},{\"uName\":\"欧洲杯\",\"uId\":1},{\"uName\":\"欧超杯\",\"uId\":465},{\"uName\":\"欧预选\",\"uId\":27},{\"uName\":\"U21欧洲杯\",\"uId\":454},{\"uName\":\"欧预选21\",\"uId\":26},{\"uName\":\"欧女锦\",\"uId\":477},{\"uName\":\"奥迪杯\",\"uId\":886}],\"nId\":90001,\"nName\":\"欧洲赛事\"},{\"Data\":[{\"uName\":\"解放者杯\",\"uId\":384},{\"uName\":\"美冠杯\",\"uId\":498},{\"uName\":\"美洲杯\",\"uId\":133},{\"uName\":\"金杯赛\",\"uId\":140},{\"uName\":\"南俱杯\",\"uId\":480},{\"uName\":\"南超杯\",\"uId\":1250}],\"nId\":90002,\"nName\":\"美洲赛事\"},{\"Data\":[{\"uName\":\"亚冠杯\",\"uId\":463},{\"uName\":\"亚洲杯\",\"uId\":246},{\"uName\":\"东亚杯\",\"uId\":392},{\"uName\":\"亚运男足\",\"uId\":474},{\"uName\":\"亚洲杯预\",\"uId\":28},{\"uName\":\"女东亚杯\",\"uId\":404},{\"uName\":\"海湾杯\",\"uId\":622}],\"nId\":90003,\"nName\":\"亚洲赛事\"},{\"Data\":[{\"uName\":\"非洲杯\",\"uId\":270}],\"nId\":90004,\"nName\":\"非洲赛事\"},{\"Data\":[{\"uName\":\"世界杯\",\"uId\":16},{\"uName\":\"世欧预\",\"uId\":11},{\"uName\":\"世亚预\",\"uId\":308},{\"uName\":\"世非预\",\"uId\":13},{\"uName\":\"世北美预\",\"uId\":14},{\"uName\":\"世南美预\",\"uId\":295},{\"uName\":\"世界杯女\",\"uId\":290},{\"uName\":\"U17世界杯\",\"uId\":279},{\"uName\":\"世青赛\",\"uId\":453},{\"uName\":\"世俱杯\",\"uId\":357},{\"uName\":\"奥运男足\",\"uId\":436},{\"uName\":\"奥运女足\",\"uId\":437},{\"uName\":\"阿尔加夫杯\",\"uId\":411},{\"uName\":\"土伦杯21\",\"uId\":452},{\"uName\":\"国际友谊\",\"uId\":851},{\"uName\":\"联合会杯\",\"uId\":460},{\"uName\":\"世女U20\",\"uId\":804},{\"uName\":\"吉尼斯杯\",\"uId\":1337}],\"nId\":90999,\"nName\":\"国际赛事\"}]}],\"tag\":\"ALL\",\"tName\":\"全部赛事\"},{\"tId\":17,\"Data\":[],\"tag\":\"HOT\",\"tName\":\"英超\"},{\"tId\":8,\"Data\":[],\"tag\":\"HOT\",\"tName\":\"西甲\"},{\"tId\":35,\"Data\":[],\"tag\":\"HOT\",\"tName\":\"德甲\"},{\"tId\":34,\"Data\":[],\"tag\":\"HOT\",\"tName\":\"法甲\"},{\"tId\":23,\"Data\":[],\"tag\":\"HOT\",\"tName\":\"意甲\"},{\"tId\":649,\"Data\":[],\"tag\":\"HOT\",\"tName\":\"中超\"},{\"tId\":238,\"Data\":[],\"tag\":\"RECENT\",\"tName\":\"葡超\"},{\"tId\":328,\"Data\":[],\"tag\":\"RECENT\",\"tName\":\"意大利杯\"},{\"tId\":136,\"Data\":[],\"tag\":\"RECENT\",\"tName\":\"澳A联\"},{\"tId\":329,\"Data\":[],\"tag\":\"RECENT\",\"tName\":\"西班牙杯\"}],\"Stamp\":\"\",\"Msg\":\"\"}");
        if (result) {
            System.out.println("匹配成功");
            return;
        }
        String path = "";
        for (String s : list) {
            path += "->" + s;
        }

        System.out.println("不匹配路径:" + path);
        if (StringUtils.isNotEmpty(detail)) {
            System.out.println("不匹配信息:" + detail);
        }
    }

}
