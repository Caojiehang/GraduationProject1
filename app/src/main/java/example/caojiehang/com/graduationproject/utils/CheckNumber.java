package example.caojiehang.com.graduationproject.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckNumber {
    public Float Number(String str1) {
        String a = str1;
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(a);
        String m2 = m.replaceAll("").trim();
        Float realValue = Float.parseFloat(m2);
        return realValue;
    }
}
