package com.apimonitor.common;

import android.content.Context;

public class ApplicationConfig
{
  private static Context context;
  private static String packageName;
  
  public static Context getContext()
  {
    return context;
  }
  
  public static String getPackageName()
  {
    return packageName;
  }
  
  public static void setContext(Context paramContext)
  {
    context = paramContext;
  }
  
  public static void setPackageName(String paramString)
  {
    packageName = paramString;
  }
}
