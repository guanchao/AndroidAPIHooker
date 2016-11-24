package com.apimonitor;

import java.io.File;
import java.io.FileDescriptor;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;

import com.apimonitor.common.Config;
import com.apimonitor.hooker.GlobalContextHooker;
import com.apimonitor.hooker.Hooker;

public class Main
{
  public static final List<String> FILTERED_PACKAGE_NAMES = new ArrayList();
  
  static
  {
    FILTERED_PACKAGE_NAMES.add("android");
    FILTERED_PACKAGE_NAMES.add("com.android.phone");
    FILTERED_PACKAGE_NAMES.add("com.android.music");
    FILTERED_PACKAGE_NAMES.add("com.android.bluetooth");
    FILTERED_PACKAGE_NAMES.add("com.android.location.fused");
    FILTERED_PACKAGE_NAMES.add("com.android.systemui");
    FILTERED_PACKAGE_NAMES.add("com.android.settings");
    FILTERED_PACKAGE_NAMES.add("com.android.inputmethod.latin");
    FILTERED_PACKAGE_NAMES.add("com.android.launcher");
    FILTERED_PACKAGE_NAMES.add("com.android.email");
    FILTERED_PACKAGE_NAMES.add("com.android.deskclock");
    FILTERED_PACKAGE_NAMES.add("com.android.mms");
    FILTERED_PACKAGE_NAMES.add("com.android.calendar");
    FILTERED_PACKAGE_NAMES.add("com.android.providers.downloads");
    FILTERED_PACKAGE_NAMES.add("com.noshufou.android.su");
    FILTERED_PACKAGE_NAMES.add("com.android.providers.calendar");
    FILTERED_PACKAGE_NAMES.add("com.android.providers.settings");
    FILTERED_PACKAGE_NAMES.add("com.android.contacts");
    FILTERED_PACKAGE_NAMES.add("com.android.providers.contacts");
    FILTERED_PACKAGE_NAMES.add("com.android.providers.userdictionary");
    FILTERED_PACKAGE_NAMES.add("com.android.exchange");
    FILTERED_PACKAGE_NAMES.add("com.android.providers.media");
    FILTERED_PACKAGE_NAMES.add("com.android.providers");
    FILTERED_PACKAGE_NAMES.add("com.android.browser");
    FILTERED_PACKAGE_NAMES.add("com.android.quicksearchbox");
    FILTERED_PACKAGE_NAMES.add("com.saurik.substrate");
    FILTERED_PACKAGE_NAMES.add("com.apimonitor");
  }
  
  public static void initialize()
  {
    new Hooker("android.content.ContextWrapper", "getSharedPreferences", new Class[] { String.class, Integer.TYPE }, Config.METHOD_TYPE);
    new Hooker("android.app.SharedPreferencesImpl$EditorImpl", "putString", new Class[] { String.class, String.class }, Config.METHOD_TYPE);
    new Hooker("android.app.SharedPreferencesImpl$EditorImpl", "putInt", new Class[] { String.class, Integer.TYPE }, Config.METHOD_TYPE);
    new Hooker("android.app.SharedPreferencesImpl$EditorImpl", "putLong", new Class[] { String.class, Long.TYPE }, Config.METHOD_TYPE);
    
    new Hooker("android.app.SharedPreferencesImpl", "getString", new Class[] { String.class, String.class }, Config.METHOD_TYPE);
    new Hooker("android.app.SharedPreferencesImpl", "getStringSet", new Class[] { String.class, Set.class }, Config.METHOD_TYPE);
    new Hooker("android.app.SharedPreferencesImpl", "getAll", new Class[0], Config.METHOD_TYPE);
    new Hooker("android.app.SharedPreferencesImpl", "getBoolean", new Class[] { String.class, Boolean.TYPE }, Config.METHOD_TYPE);
    new Hooker("android.app.SharedPreferencesImpl", "getFloat", new Class[] { String.class, Float.TYPE }, Config.METHOD_TYPE);
    new Hooker("android.app.SharedPreferencesImpl", "getInt", new Class[] { String.class, Integer.TYPE }, Config.METHOD_TYPE);
    new Hooker("android.app.SharedPreferencesImpl", "getLong", new Class[] { String.class, Long.TYPE }, Config.METHOD_TYPE);
    new Hooker("android.app.SharedPreferencesImpl", "contains", new Class[] { String.class }, Config.METHOD_TYPE);
    
    new Hooker("android.content.ContentResolver", "insert", new Class[] { Uri.class, ContentValues.class }, Config.METHOD_TYPE);
    new Hooker("android.content.ContentResolver", "openAssetFileDescriptor", new Class[] { Uri.class, String.class }, Config.METHOD_TYPE);
    new Hooker("android.content.ContentResolver", "openFileDescriptor", new Class[] { Uri.class, String.class }, Config.METHOD_TYPE);
    new Hooker("android.content.ContentResolver", "openOutputStream", new Class[] { Uri.class }, Config.METHOD_TYPE);
    new Hooker("android.content.ContentResolver", "openOutputStream", new Class[] { Uri.class, String.class }, Config.METHOD_TYPE);
    new Hooker("android.content.ContentResolver", "update", new Class[] { Uri.class, ContentValues.class, String.class, String[].class }, Config.METHOD_TYPE);
    
    new Hooker("dalvik.system.DexClassLoader", "DexClassLoader", new Class[] { String.class, String.class, String.class, ClassLoader.class }, Config.CONSTRUCTOR_TYPE);
    new Hooker("android.content.ContextWrapper", "openFileOutput", new Class[] { String.class, Integer.TYPE }, Config.METHOD_TYPE);
    
    new Hooker("java.io.FileOutputStream", "FileOutputStream", new Class[] { String.class }, Config.CONSTRUCTOR_TYPE);
    new Hooker("java.io.FileOutputStream", "FileOutputStream", new Class[] { String.class, Boolean.TYPE }, Config.CONSTRUCTOR_TYPE);
    new Hooker("java.io.FileOutputStream", "FileOutputStream", new Class[] { FileDescriptor.class }, Config.CONSTRUCTOR_TYPE);
    new Hooker("java.io.FileOutputStream", "FileOutputStream", new Class[] { File.class }, Config.CONSTRUCTOR_TYPE);
    new Hooker("java.io.FileOutputStream", "FileOutputStream", new Class[] { File.class, Boolean.TYPE }, Config.CONSTRUCTOR_TYPE);
    
    new Hooker("java.io.FileOutputStream", "write", new Class[] { byte[].class }, Config.METHOD_TYPE);
    new Hooker("java.io.FileOutputStream", "write", new Class[] { byte[].class, Integer.TYPE, Integer.TYPE }, Config.METHOD_TYPE);
    
    new Hooker("java.io.ObjectOutputStream", "write", new Class[] { byte[].class, Integer.TYPE, Integer.TYPE }, Config.METHOD_TYPE);
    new Hooker("java.io.ObjectOutputStream", "writeBytes", new Class[] { String.class }, Config.METHOD_TYPE);
    new Hooker("java.io.ObjectOutputStream", "writeChars", new Class[] { String.class }, Config.METHOD_TYPE);
    new Hooker("java.io.ObjectOutputStream", "writeObject", new Class[] { Object.class }, Config.METHOD_TYPE);
    new Hooker("java.io.ObjectOutputStream", "writeUTF", new Class[] { String.class }, Config.METHOD_TYPE);
    new Hooker("java.io.ObjectOutputStream", "writeUnshared", new Class[] { Object.class }, Config.METHOD_TYPE);
    
    new Hooker("java.io.Writer", "write", new Class[] { char[].class }, Config.METHOD_TYPE);
    new Hooker("java.io.Writer", "write", new Class[] { String.class }, Config.METHOD_TYPE);
    
    new Hooker("java.io.PrintWriter", "PrintWriter", new Class[] { File.class }, Config.CONSTRUCTOR_TYPE);
    new Hooker("java.io.PrintWriter", "PrintWriter", new Class[] { File.class, String.class }, Config.CONSTRUCTOR_TYPE);
    new Hooker("java.io.PrintWriter", "PrintWriter", new Class[] { String.class }, Config.CONSTRUCTOR_TYPE);
    new Hooker("java.io.PrintWriter", "PrintWriter", new Class[] { String.class, String.class }, Config.CONSTRUCTOR_TYPE);
    
    new Hooker("java.io.FileWriter", "FileWriter", new Class[] { File.class }, Config.CONSTRUCTOR_TYPE);
    new Hooker("java.io.FileWriter", "FileWriter", new Class[] { File.class, Boolean.class }, Config.CONSTRUCTOR_TYPE);
    new Hooker("java.io.FileWriter", "FileWriter", new Class[] { String.class }, Config.CONSTRUCTOR_TYPE);
    new Hooker("java.io.FileWriter", "FileWriter", new Class[] { String.class, Boolean.class }, Config.CONSTRUCTOR_TYPE);
    
    new Hooker("java.io.File", "File", new Class[] { String.class }, Config.CONSTRUCTOR_TYPE);
    new Hooker("java.io.File", "File", new Class[] { String.class, String.class }, Config.CONSTRUCTOR_TYPE);
    new Hooker("java.io.File", "File", new Class[] { File.class, String.class }, Config.CONSTRUCTOR_TYPE);
    new Hooker("java.io.File", "File", new Class[] { URI.class }, Config.CONSTRUCTOR_TYPE);
    
    new Hooker("android.content.Intent", "putCharSequenceArrayListExtra", new Class[] { String.class, ArrayList.class }, Config.METHOD_TYPE);
    new Hooker("android.content.Intent", "putExtra", new Class[] { String.class, CharSequence.class }, Config.METHOD_TYPE);
    new Hooker("android.content.Intent", "putExtra", new Class[] { String.class, Bundle.class }, Config.METHOD_TYPE);
    new Hooker("android.content.Intent", "putExtra", new Class[] { String.class, Parcelable[].class }, Config.METHOD_TYPE);
    new Hooker("android.content.Intent", "putExtra", new Class[] { String.class, Serializable.class }, Config.METHOD_TYPE);
    new Hooker("android.content.Intent", "putExtra", new Class[] { String.class, Parcelable.class }, Config.METHOD_TYPE);
    new Hooker("android.content.Intent", "putExtra", new Class[] { String.class, String[].class }, Config.METHOD_TYPE);
    new Hooker("android.content.Intent", "putExtra", new Class[] { String.class, String.class }, Config.METHOD_TYPE);
    new Hooker("android.content.Intent", "putExtras", new Class[] { Bundle.class }, Config.METHOD_TYPE);
    new Hooker("android.content.Intent", "putStringArrayListExtra", new Class[] { String.class, ArrayList.class }, Config.METHOD_TYPE);
    
    new Hooker("android.util.Log", "v", new Class[] { String.class, String.class }, Config.METHOD_TYPE);
    new Hooker("android.util.Log", "w", new Class[] { String.class, String.class }, Config.METHOD_TYPE);
    new Hooker("android.util.Log", "i", new Class[] { String.class, String.class }, Config.METHOD_TYPE);
    new Hooker("android.util.Log", "d", new Class[] { String.class, String.class }, Config.METHOD_TYPE);
    new Hooker("android.util.Log", "e", new Class[] { String.class, String.class }, Config.METHOD_TYPE);
    
    new Hooker("android.database.sqlite.SQLiteDatabase", "openOrCreateDatabase", new Class[] { String.class, SQLiteDatabase.CursorFactory.class }, Config.METHOD_TYPE);
    new Hooker("android.database.sqlite.SQLiteDatabase", "openOrCreateDatabase", new Class[] { File.class, SQLiteDatabase.CursorFactory.class }, Config.METHOD_TYPE);
    new Hooker("android.database.sqlite.SQLiteDatabase", "openOrCreateDatabase", new Class[] { String.class, SQLiteDatabase.CursorFactory.class, DatabaseErrorHandler.class }, Config.METHOD_TYPE);
    
    new Hooker("android.content.CursorLoader", "CursorLoader", new Class[] { Context.class, Uri.class, String[].class, String.class, String[].class, String.class }, Config.CONSTRUCTOR_TYPE);
    new Hooker("android.app.Activity", "managedQuery", new Class[] { Uri.class, String[].class, String.class, String[].class, String.class }, Config.METHOD_TYPE);
    new Hooker("android.content.ContextWrapper", "getDatabasePath", new Class[] { String.class }, Config.METHOD_TYPE);
    new Hooker("android.database.sqlite.SQLiteDatabase", "execSQL", new Class[] { String.class }, Config.METHOD_TYPE);
    new Hooker("android.database.sqlite.SQLiteDatabase", "execSQL", new Class[] { String.class, Object[].class }, Config.METHOD_TYPE);
    new Hooker("android.database.sqlite.SQLiteDatabase", "update", new Class[] { String.class, ContentValues.class, String.class, String[].class }, Config.METHOD_TYPE);
    new Hooker("android.database.sqlite.SQLiteDatabase", "insert", new Class[] { String.class, String.class, ContentValues.class }, Config.METHOD_TYPE);
    
    new Hooker("java.io.PrintStream", "println", new Class[] { String.class }, Config.METHOD_TYPE);
    new Hooker("java.io.PrintStream", "print", new Class[] { String.class }, Config.METHOD_TYPE);
    
    new Hooker("javax.crypto.spec.SecretKeySpec", "SecretKeySpec", new Class[] { byte[].class, String.class }, Config.CONSTRUCTOR_TYPE);
//    new Hooker("javax.crypto.Cipher", "doFinal", new Class[] { byte[].class }, Config.METHOD_TYPE);
//    new Hooker("java.security.SecureRandom", "setSeed", new Class[] { byte[].class }, Config.METHOD_TYPE);
    new Hooker("javax.crypto.spec.PBEKeySpec", "PBEKeySpec", new Class[] { char[].class, byte[].class, Integer.TYPE, Integer.TYPE }, Config.CONSTRUCTOR_TYPE);
    
    new GlobalContextHooker().hook(FILTERED_PACKAGE_NAMES);
  }
}
