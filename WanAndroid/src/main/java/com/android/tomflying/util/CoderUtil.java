package com.android.tomflying.util;

import com.android.tomflying.bean.ActiviteBean;
import com.tom.baselib.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 作者：tom_flying
 * 邮箱：tom_flying@163.com
 * 博客: www.tianfeifei.com
 * 创建日期: 2018/7/13
 * 描述：程序员老黄历工具
 */
public class CoderUtil {


    private static String[] drinks = new String[]{"水", "茶", "红茶", "绿茶", "咖啡", "奶茶", "可乐", "鲜奶", "豆奶", "果汁", "果味汽水", "苏打水", "运动饮料", "酸奶", "酒"};
    private static String[] directions = new String[]{"北方", "东北方", "东方", "东南方", "南方", "西南方", "西方", "西北方"};
    private static String[] tools = new String[]{"Eclipse写程序", "Office写文档", "记事本写程序", "Windows8", "Linux", "MacOS", "IE", "Android设备", "iOS设备"};
    private static String[] varNames = new String[]{"jieguo", "huodong", "pay", "expire", "zhangdan", "every", "free", "i1", "a", "virtual", "ad", "spider", "mima", "pass", "ui"};

    public static String getNvshen() {
        int num = random(6) % 5 + 1;
        String result = "";
        int i = 0;
        while (i < num) {
            result += "★";
            i++;
        }
        while (i < 5) {
            result += "☆";
            i++;
        }
        return result;

    }

    private static Integer random(Integer indexSeed) {
        Integer n = Integer.parseInt(TimeUtils.getNowString(new SimpleDateFormat("yyyyMMdd"))) % 11117;
        for (int i = 0; i < 100 + indexSeed; i++) {
            n = n * n;
            //11117是个质数
            n = n % 11117;
        }
        return n;
    }

    public static List<String> getDrink() {
        List<String> result = new ArrayList<>(Arrays.asList(drinks));

        for (int i = 0; i < drinks.length - 2; i++) {
            int index = random(i) % result.size();
            result.remove(index);
        }
        return result;

    }

    public static String getZuowei() {
        return directions[random(2) % directions.length];
    }

    public static List<Map<String, String>> getGoods() {
        List<Map<String, String>> events = getPickActivites();
        List<Map<String, String>> picks = new ArrayList<>();
        Integer numGood = random(98) % 3 + 2;
        for (int i = 0; i < numGood; i++) {
            Map<String, String> pick = new HashMap<>();
            pick.put("thing", events.get(i).get("name"));
            pick.put("result", events.get(i).get("good"));
            picks.add(pick);
        }
        return picks;
    }

    private static List<Map<String, String>> getPickActivites() {
        List<ActiviteBean> activities = filter();
        Integer numGood = random(98) % 3 + 2;
        Integer numBad = random(87) % 3 + 2;
        return pickRandomActivite(activities, numGood + numBad);
    }

    private static List<ActiviteBean> filter() {
        List<ActiviteBean> thisEnum = new ArrayList<>();

        // 周末的话，只留下 weekend = true 的事件
        if (isWeekend()) {
            for (ActiviteBean e : getAllActivites()) {
                if (e.isWeek()) {
                    thisEnum.add(e);
                }
            }
            return thisEnum;
        }
        return getAllActivites();
    }

    //  枚举 中随机挑选 size 个
    private static List<Map<String, String>> pickRandomActivite(List<ActiviteBean> _activities, Integer size) {
        List<ActiviteBean> picked_events = pickRandom(_activities, size);
        List<Map<String, String>> mapList = new ArrayList<>();
        for (int i = 0; i < picked_events.size(); i++) {
            mapList.add(parse(picked_events.get(i)));
        }
        return mapList;
    }

    private static Boolean isWeekend() {
        Locale.setDefault(Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK) == 1 || calendar.get(Calendar.DAY_OF_WEEK) == 7;
    }

    private static List<ActiviteBean> getAllActivites() {
        List<ActiviteBean> activites = new ArrayList<>();
        activites.add(new ActiviteBean("写单元测试", "写单元测试将减少出错", "写单元测试会降低你的开发效率", false));
        activites.add(new ActiviteBean("洗澡", "你几天没洗澡了？", "会把设计方面的灵感洗掉", true));
        activites.add(new ActiviteBean("锻炼一下身体", "", "能量没消耗多少，吃得却更多", true));
        activites.add(new ActiviteBean("抽烟", "抽烟有利于提神，增加思维敏捷", "除非你活够了，死得早点没关系", true));
        activites.add(new ActiviteBean("白天上线", "今天白天上线是安全的", "可能导致灾难性后果", false));
        activites.add(new ActiviteBean("重构", "代码质量得到提高", "你很有可能会陷入泥潭", false));
        activites.add(new ActiviteBean("使用%t", "你看起来更有品位", "别人会觉得你在装逼", false));
        activites.add(new ActiviteBean("跳槽", "该放手时就放手", "鉴于当前的经济形势，你的下一份工作未必比现在强", false));
        activites.add(new ActiviteBean("招人", "你面前这位有成为牛人的潜质", "这人会写程序吗？", false));
        activites.add(new ActiviteBean("面试", "面试官今天心情很好", "面试官不爽，会拿你出气", false));
        activites.add(new ActiviteBean("提交辞职申请", "公司找到了一个比你更能干更便宜的家伙，巴不得你赶快滚蛋", "鉴于当前的经济形势，你的下一份工作未必比现在强", false));
        activites.add(new ActiviteBean("申请加薪", "老板今天心情很好", "公司正在考虑裁员", false));
        activites.add(new ActiviteBean("晚上加班", "晚上是程序员精神最好的时候", "", true));
        activites.add(new ActiviteBean("在妹子面前吹牛", "改善你矮穷挫的形象", "会被识破", true));
        activites.add(new ActiviteBean("撸管", "避免缓冲区溢出", "强撸灰飞烟灭", true));
        activites.add(new ActiviteBean("浏览成人网站", "重拾对生活的信心", "你会心神不宁", true));
        activites.add(new ActiviteBean("命名变量%v", "", "", false));
        activites.add(new ActiviteBean("写超过%l行的方法", "你的代码组织的很好，长一点没关系", "你的代码将混乱不堪，你自己都看不懂", false));
        activites.add(new ActiviteBean("提交代码", "遇到冲突的几率是最低的", "你遇到的一大堆冲突会让你觉得自己是不是时间穿越了", false));
        activites.add(new ActiviteBean("代码复审", "发现重要问题的几率大大增加", "你什么问题都发现不了，白白浪费时间", false));
        activites.add(new ActiviteBean("开会", "写代码之余放松一下打个盹，有益健康", "小心被扣屎盆子背黑锅", false));
        activites.add(new ActiviteBean("打DOTA", "你将有如神助", "你会被虐的很惨", true));
        activites.add(new ActiviteBean("晚上上线", "晚上是程序员精神最好的时候", "你白天已经筋疲力尽了", false));
        activites.add(new ActiviteBean("修复BUG", "你今天对BUG的嗅觉大大提高", "新产生的BUG将比修复的更多", false));
        activites.add(new ActiviteBean("设计评审", "设计评审会议将变成头脑风暴", "人人筋疲力尽，评审就这么过了", false));
        activites.add(new ActiviteBean("需求评审", "", "", false));
        activites.add(new ActiviteBean("上微博", "今天发生的事不能错过", "今天的微博充满负能量", true));
        activites.add(new ActiviteBean("上AB站", "还需要理由吗？", "满屏兄贵亮瞎你的眼", true));
        activites.add(new ActiviteBean("玩FlappyBird", "今天破纪录的几率很高", "除非你想玩到把手机砸了", true));

        return activites;
    }

    /**
     * 从数组中随机挑选 size 个
     *
     * @param size
     * @return
     */
    private static List<ActiviteBean> pickRandom(List<ActiviteBean> _activities, Integer size) {
        List<ActiviteBean> result = new ArrayList<>();

        for (ActiviteBean ae : _activities) {
            result.add(ae);
        }

        for (int i = 0; i < _activities.size() - size; i++) {
            int index = random(i) % result.size();
            result.remove(index);
        }
        return result;
    }


    /**
     * 解析占位符并替换成随机内容
     *
     * @param ae
     * @return
     */
    private static Map<String, String> parse(ActiviteBean ae) {

        Map<String, String> map = new HashMap<>();
        map.put("name", ae.getThing());
        map.put("good", ae.getGood());
        map.put("bad", ae.getBad());

        if (map.get("name").indexOf("%v") != -1) {
            map.put("name", map.get("name").replaceAll("%v", varNames[random(12) % varNames.length]));
        }
        if (map.get("name").indexOf("%t") != -1) {
            map.put("name", map.get("name").replaceAll("%t", tools[random(11) % tools.length]));
        }
        if (map.get("name").indexOf("%l") != -1) {
            map.put("name", map.get("name").replaceAll("%l", (random(12) % 247 + 30) + ""));
        }
        return map;
    }


    public static List<Map<String, String>> getBads() {
        List<Map<String, String>> events = getPickActivites();
        List<Map<String, String>> picks = new ArrayList<>();
        Integer numGood = random(98) % 3 + 2;
        Integer numBad = random(87) % 3 + 2;

        for (int i = 0; i < numBad; i++) {
            Map<String, String> pick = new HashMap<>();
            pick.put("thing", events.get(numGood + i).get("name"));
            pick.put("result", events.get(numGood + i).get("bad"));
            picks.add(pick);
        }
        return picks;

    }
}
