package top.yangsc.tools;

public class TableUtil {

    public static String toTab(String s){

        int i=0;
        char c = s.charAt(i);
        if (65<=c&& c<=90){
            s = s.replace(String.valueOf(c), String.valueOf((char) (c + 32)));
            i++;
        }
        while (i<s.length()){
            c=s.charAt(i);
            if (65<=c&& c<=90){
                s=s.replaceFirst(String.valueOf(c),"_"+((char)(c+32)));
            }
            i++;
        }
        return s;

    }

}
