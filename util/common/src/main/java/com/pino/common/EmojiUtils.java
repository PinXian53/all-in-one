package com.pino.common;

public final class EmojiUtils {

    private EmojiUtils() {
    }

    // 參考來源：https://www.cwb.gov.tw/V8/C/K/Weather_Icon.html
    // 太多了就沒列完整了，只列了大部分
    public static String getWeatherEmoji(String key) {
        return switch (key) {
            case "晴天" -> "☀";
            case "晴時多雲" -> "\uD83C\uDF24";
            case "多雲時晴" -> "⛅";
            case "多雲", "多雲時多雲", "陰時多雲", "陰天", "多雲時陰" -> "☁";
            case "多雲陣雨", "多雲短暫雨", "多雲短暫陣雨", "午後短暫陣雨", "短暫陣雨", "多雲時晴短暫陣雨", "多雲時晴短暫雨",
                "晴時多雲短暫陣雨", "晴短暫陣雨", "短暫雨", "多雲時陰短暫雨", "多雲時陰短暫陣雨", "陰時多雲短暫雨", "陰時多雲短暫陣雨",
                "雨天", "晴午後陰短暫雨", "晴午後陰短暫陣雨", "陰短暫雨", "陰短暫陣雨", "陰午後短暫陣雨", "多雲時陰有雨",
                "多雲時陰陣雨", "晴時多雲陣雨", "多雲時晴陣雨", "陰時多雲有雨", "陰時多雲有陣雨", "陰時多雲陣雨", "陰有雨", "陰有陣雨",
                "陰雨", "陰陣雨", "午後陣雨", "有雨" -> "\uD83C\uDF27";
            case "多雲陣雨或雷雨", "多雲短暫陣雨或雷雨", "多雲短暫雷陣雨", "多雲雷陣雨", "短暫陣雨或雷雨後多雲", "短暫雷陣雨後多雲",
                "短暫陣雨或雷雨", "晴時多雲短暫陣雨或雷雨", "晴短暫陣雨或雷雨", "多雲時晴短暫陣雨或雷雨", "午後短暫雷陣雨",
                "多雲時陰陣雨或雷雨", "多雲時陰短暫陣雨或雷雨" -> "⛈";
            default -> "";
        };
    }

    public static String getTimeEmoji(int num) {
        int timeNum = num <= 24 ? num % 12 : num;
        return switch (timeNum) {
            case 0 -> "\uD83D\uDD5B";
            case 1 -> "\uD83D\uDD50";
            case 2 -> "2️\uD83D\uDD51";
            case 3 -> "3️\uD83D\uDD52";
            case 4 -> "\uD83D\uDD53";
            case 5 -> "\uD83D\uDD54";
            case 6 -> "\uD83D\uDD55";
            case 7 -> "\uD83D\uDD56";
            case 8 -> "8️\uD83D\uDD57";
            case 9 -> "\uD83D\uDD58";
            case 10 -> "\uD83D\uDD59";
            case 11 -> "\uD83D\uDD5A";
            default -> "";
        };
    }

    public static String getNumberEmoji(int num) {
        return switch (num) {
            case 0 -> "0️⃣";
            case 1 -> "1️⃣";
            case 2 -> "2️⃣";
            case 3 -> "3️⃣";
            case 4 -> "4️⃣";
            case 5 -> "5️⃣";
            case 6 -> "6️⃣";
            case 7 -> "7️⃣";
            case 8 -> "8️⃣";
            case 9 -> "9️⃣";
            case 10 -> "\uD83D\uDD1F";
            default -> String.valueOf(num);
        };
    }
}
