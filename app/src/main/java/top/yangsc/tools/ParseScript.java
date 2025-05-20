package top.yangsc.tools;

import java.util.Arrays;

/**
 * 描述：top.yangsc.tools
 *
 * @author yang
 * @date 2025/5/20 14:38
 */
public class ParseScript {

    public static String[] parseToJson(String script,String key) {
        String[] split = script.split(key);
        String[] result = new String[split.length-1];
        int resultIndex = 0;
        if (split.length >=2){
            for (int j=1;j<split.length;j++){
                String replace = split[j].replaceFirst(":","");
                byte[] bytes = replace.getBytes();
                int count = 0;
                int countByte  = 0;
                for (int k=0;k<bytes.length;k++){
                    byte b = bytes[k];
                    if (b == '{'){
                        count ++;
                        continue;
                    }
                    if (b == '}' ){
                        count --;
                        if (count == 0){
                            byte[] resultByte = new byte[k+1];
                            System.arraycopy(bytes,0,resultByte,0,k+1);
                            result[resultIndex] = new String(resultByte);
                            break;
                        }
                    }

                }
            }
            return result;
        }else {
            return null;
        }
    }
}
