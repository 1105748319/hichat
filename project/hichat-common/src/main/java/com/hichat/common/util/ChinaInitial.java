package com.hichat.common.util;

import com.hichat.common.pojo.User;
import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChinaInitial {

    public static void setUserHeader(User user){
        String nickname = user.getNickname();
        char first = nickname.charAt(0);
        if(first>='a'&&first<='z'||first>='A'&&first<='Z'){
            user.setHeader(String.valueOf(first).toUpperCase());
        }else{
            //判断是否是汉字
            boolean chinese = ChinaInitial.isChinese(String.valueOf(first));
            if(chinese){
                String[] str = PinyinHelper.toHanyuPinyinStringArray(first);
                String header = str[0].substring(0, 1).toUpperCase();
                user.setHeader(header);
            }else{
                //其他非法字符
                user.setHeader("#");
            }
        }
    }


    public static boolean isChinese(String str) {
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find())
            flg = true;

        return flg;
    }
}
