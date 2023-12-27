package br.com.bridi.rinhabackend.utils;

import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class Utils {
  
  public static String getStackString(List<String> stacks) {
    return stacks != null ? StringUtils.join(stacks, ";") : null; 
  }
  
  public static String[] getArrayStack(String stack) {
    return StringUtils.isNotEmpty(stack) ? StringUtils.split(stack, ";") : null;
  }


}
