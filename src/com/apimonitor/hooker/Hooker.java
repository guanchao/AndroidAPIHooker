package com.apimonitor.hooker;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.util.Log;

import com.apimonitor.common.ApplicationConfig;
import com.apimonitor.common.Config;
import com.saurik.substrate.MS;

public class Hooker {
	public Hooker(String className, String methodName,
			Class<?>[] parameterTypes, int hookType) {
		if (hookType == Config.METHOD_TYPE) {
			hookMethod(className, methodName, parameterTypes);
		} else {
			hookConstructor(className, methodName, parameterTypes);
		}
	}

	private void hookParameters(String packageName, String className,
			String methodName, Class<?>[] parameterTypes,
			Object[] parameterValues, Object returnValues) {
		StringBuilder sb = new StringBuilder();
		if ((parameterValues != null) && (parameterTypes != null)
				&& (parameterTypes.length != 0)) {
			sb.append("(");
			for (int i = 0; i < parameterTypes.length; i++) {
				if (parameterTypes[i] == ContentValues.class) {
					sb.append(outputContentValues(parameterValues[i]));
				} else if ((parameterTypes[i] == String[].class)
						|| (parameterTypes[i] == Object[].class)) {
					sb.append(outputStringArray(parameterValues[i]));
				} else if (parameterTypes[i] == byte[].class) {
					
					if (parameterValues[i] != null) {
						byte[] bytes = (byte[]) parameterValues[i];
						int len = bytes.length;
						int oct = -1;
						byte val = 0;
						
						for(int k = 0; k < len; k++){
							oct = Integer.parseInt(byteToHex(bytes[k]), 16);
							//过滤掉SUB字符，防止读取文件时被切断
							if(oct == 26){
								bytes[k] = val;
							}
						}
						sb.append(new String(bytes));
					} else {
						sb.append("");
					}
				} else if (parameterTypes[i] == char[].class) {
					if (parameterValues[i] != null) {
						sb.append(new String((char[]) parameterValues[i]));
					} else {
						sb.append("");
					}
				} else {
					sb.append(parameterValues[i]);
				}
				sb.append(",");
			}
			sb.append(")");
		}

		String log = "";
		log = packageName + "==>" + className + "==>" + methodName
				+ sb.toString();
		if ((packageName != null) && (!log.contains("api_monitor"))) {
			//处理日志过大问题
			int maxLogSize = 2000;  
		    for(int i = 0; i <= log.length() / maxLogSize; i++) {  
		        int start = i * maxLogSize;  
		        int end = (i+1) * maxLogSize;  
		        end = end > log.length() ? log.length() : end;  
		        Log.d(Config.LOG_TAG, log.substring(start, end));  
		    } 
		}

	}
	
	public String byteToHex(byte byteArray) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		char[] resultCharArray = new char[1 * 2];
		int index = 0;

		resultCharArray[index++] = hexDigits[byteArray >>> 4 & 0xf];
		resultCharArray[index++] = hexDigits[byteArray & 0xf];

		return new String(resultCharArray);
	}
	
	public void hookConstructor(final String className,
			final String methodName, final Class<?>... parameterTypes) {
		MS.hookClassLoad(className, new MS.ClassLoadHook() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void classLoaded(Class<?> classToHook) {
				try {
					Constructor method = classToHook
							.getConstructor(parameterTypes);
					if (method != null) {
						final MS.MethodPointer old = new MS.MethodPointer();
						MS.hookMethod(classToHook, method, new MS.MethodHook() {
							public Object invoked(Object resources,
									Object... args) throws Throwable {
								Object result = old.invoke(resources, args);
								Hooker.this.hookParameters(
										ApplicationConfig.getPackageName(),
										className, methodName, parameterTypes,
										args, result);
								return result;
							}
						}, old);
					}
				} catch (NoSuchMethodException e) {

				}
			}
		});
	}

	public void hookMethod(final String className, final String methodName,
			final Class<?>... parameterTypes) {
		MS.hookClassLoad(className, new MS.ClassLoadHook() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void classLoaded(Class<?> classToHook) {
				try {
					Method method = classToHook.getMethod(methodName,
							parameterTypes);
					if (method != null) {
						final MS.MethodPointer old = new MS.MethodPointer();
						MS.hookMethod(classToHook, method, new MS.MethodHook() {
							public Object invoked(Object resources,
									Object... args) throws Throwable {
								Object result = old.invoke(resources, args);
								Hooker.this.hookParameters(
										ApplicationConfig.getPackageName(),
										className, methodName, parameterTypes,
										args, result);
								return result;
							}
						}, old);
					}
				} catch (NoSuchMethodException e) {
					
				}
			}
		});
	}

	public String inputStream2String(InputStream stream, int len)
			throws IOException, UnsupportedEncodingException {
		Reader reader = null;
		reader = new InputStreamReader(stream, "UTF-8");
		char[] buffer = new char[len];
		reader.read(buffer);
		return new String(buffer);
	}

	public String outputContentValues(Object parameterValues) {
		if (parameterValues == null)
			return null;
		ContentValues cv = (ContentValues) parameterValues;
		if (cv.size() == 0)
			return null;

		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (Entry<String, Object> item : cv.valueSet()) {
			if (item != null) {
				sb.append("(" + item.getKey() + ", " + item.getValue() + "),");
			}
		}
		sb.append("]");

		return sb.toString();
	}

	public String outputStringArray(Object paramObject) {
		if (paramObject == null)
			return null;
		Object[] arrayObject = (Object[]) paramObject;
		if (arrayObject.length == 0)
			return null;

		StringBuilder sb = new StringBuilder();

		sb.append("[");
		for (int i = 0; i < arrayObject.length; i++) {
			sb.append(arrayObject[i] + ",");
		}
		sb.append("]");

		return sb.toString();

	}

}
