package com.duguang.baseanimation.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.duguang.baseanimation.app.MainApplication;
import com.duguang.baseanimation.ui.gesturepassword.homekey.GesturePasswordDemoActivity;

/**
 * 缓存的工具类
 * 
 *
 */
public class SharedPreferenceUtil {
	/*
	 * the default mode, where the created file can only be accessed by the calling application 
	 * (or all applications sharing the same user ID).
	 */
	public static final int MODE_PRIVATE = 0;
	/*
	 * allow all other applications to have read access to the created file
	 * This constant was deprecated in API level 17.
	 */
    public static final int MODE_WORLD_READABLE = 1;
    /*
     * allow all other applications to have write access to the created file.
     * This constant was deprecated in API level 17.
     */
    public static final int MODE_WORLD_WRITEABLE = 2;
    
    /**
     * 将一个String数据存入到缓存中
     * @param spName 缓存的名字
     * @param keyStr 要存入缓存中的key
     * @param valueStr 要存入缓存中的value
     */
    public static void setStringDataIntoSP(Context context,String spName, String keyStr, String valueStr) {
    	SharedPreferences sp = context.getSharedPreferences(spName, MODE_PRIVATE);
    	sp.edit().putString(keyStr, valueStr).commit();
    }
    
    /**
     * 将一个Boolean数据存入到缓存中
     * @param spName 缓存的名字
     * @param keyStr 要存入缓存中的key
     * @param valueStr 要存入缓存中的value
     */
    public static void setBooleanDataIntoSP(Context context,String spName, String keyStr, Boolean valueStr) {
    	SharedPreferences sp = context.getSharedPreferences(spName, MODE_PRIVATE);
    	sp.edit().putBoolean(keyStr, valueStr).commit();
    }
    
    /**
     * 将一个Int数据存入到缓存中
     * @param spName 缓存的名字
     * @param keyStr 要存入缓存中的key
     * @param valueStr 要存入缓存中的value
     */
    public static void setIntDataIntoSP(Context context,String spName, String keyStr, int valueStr) {
    	SharedPreferences sp = context.getSharedPreferences(spName, MODE_PRIVATE);
    	sp.edit().putInt(keyStr, valueStr).commit();
    }
    
    /**
     * 将一个Float数据存入到缓存中
     * @param spName 缓存的名字
     * @param keyStr 要存入缓存中的key
     * @param valueStr 要存入缓存中的value
     */
    public static void setFloatDataIntoSP(Context context,String spName, String keyStr, Float valueStr) {
    	SharedPreferences sp = context.getSharedPreferences(spName, MODE_PRIVATE);
    	sp.edit().putFloat(keyStr, valueStr).commit();
    }
    
    /**
     * 将一个Long数据存入到缓存中
     * @param spName 缓存的名字
     * @param keyStr 要存入缓存中的key
     * @param valueStr 要存入缓存中的value
     */
    public static void setLongDataIntoSP(Context context,String spName, String keyStr, Long valueStr) {
    	SharedPreferences sp = context.getSharedPreferences(spName, MODE_PRIVATE);
    	sp.edit().putLong(keyStr, valueStr).commit();
    }
    
    /**
     * 将一系列String数据存入到缓存中
     * @param spName 缓存的名字
     * @param keyStr 要存入缓存中的key
     * @param valueStringSet 一系列的String数据
     */
//    public static void setStringSetDataIntoSP(String spName, String keyStr, Set<String> valueStringSet) {
//    	if(Build.VERSION.SDK_INT >= 11) {
//	    	SharedPreferences sp = BaseApplication.getApplication().getSharedPreferences(spName, MODE_PRIVATE);
//	    	sp.edit().putStringSet(keyStr, valueStringSet).commit();
//    	}
//    }
    
    /**
     * 获取缓存中的一个String数据
     * @param spName 缓存的名字
     * @param keyStr 已存入缓存中的key
     * @return 缓存中对应参数key的value
     */
    public static String getStringValueFromSP(Context context,String spName, String keyStr) {
    	SharedPreferences sp = context.getSharedPreferences(spName, MODE_PRIVATE);
    	return sp.getString(keyStr, "");
    }
    
    /**
     * 获取缓存中的一个Float数据
     * @param spName 缓存的名字
     * @param keyStr 已存入缓存中的key
     * @return 缓存中对应参数key的value
     */
    public static Float getFloatValueFromSP(Context context,String spName, String keyStr) {
    	SharedPreferences sp = context.getSharedPreferences(spName, MODE_PRIVATE);
    	return sp.getFloat(keyStr, 0.0f);
    }
    
    /**
     * 获取缓存中的一个Int数据
     * @param spName 缓存的名字
     * @param keyStr 已存入缓存中的key
     * @return 缓存中对应参数key的value
     */
    public static int getIntValueFromSP(Context context,String spName, String keyStr) {
    	SharedPreferences sp = context.getSharedPreferences(spName, MODE_PRIVATE);
    	return sp.getInt(keyStr, 0);
    }
    
    /**
     * 获取缓存中的一个Boolean数据
     * @param context 
     * @param spName 缓存的名字
     * @param keyStr 已存入缓存中的key
     * @return 缓存中对应参数key的value
     */
    public static boolean getBooleanValueFromSP(Context context, String spName, String keyStr) {
    	SharedPreferences sp =  context.getSharedPreferences(spName, MODE_PRIVATE);
    	return sp.getBoolean(keyStr, false);
    }
    
    /**
     * 获取缓存中的一个Long数据
     * @param spName 缓存的名字
     * @param keyStr 已存入缓存中的key
     * @return 缓存中对应参数key的value
     */
    public static Long getLongValueFromSP(Context context,String spName, String keyStr) {
    	SharedPreferences sp = context.getSharedPreferences(spName, MODE_PRIVATE);
    	return sp.getLong(keyStr, 0l);
    }
    
    /**
     * 获取缓存中的一系列String数据
     * @param spName 缓存的名字
     * @param keyStr 已存入缓存中的key
     * @return 缓存中对应参数key的一系列String值
     */
//    public static Set<String> getStringSetValueFromSP(String spName, String keyStr) {
//    	Set<String> sets = new HashSet<String>();
//    	if(Build.VERSION.SDK_INT >= 11) {
//    		SharedPreferences sp = BaseApplication.getApplication().getSharedPreferences(spName, MODE_PRIVATE);
//    		sets = sp.getStringSet(keyStr, null);
//    	}
//    	return sets;
//    }
    
    /**
     * 将键值对数组，存入到缓存中
     * @param spName 缓存的名称
     * @param keyValueMap 要存入缓存中的键值对
     */
    public static void setDataIntoSP(Context context,String spName, HashMap<String, Object> keyValueMap) {
    	SharedPreferences sp =  context.getSharedPreferences(spName, MODE_PRIVATE);
    	Editor editor = sp.edit();
    	if(keyValueMap != null && !keyValueMap.isEmpty()) {
    		Set<String> keySet = keyValueMap.keySet();
    		Iterator<String> iterator = keySet.iterator();
    		while (iterator.hasNext()) {
				String key = (String) iterator.next();
				Object value = keyValueMap.get(key);
				if(value.getClass() == String.class) {
					editor.putString(key, (String)value);
				} else if(value.getClass() == Integer.class) {
					editor.putInt(key, (Integer)value);
				} else if(value.getClass() == Boolean.class) {
					editor.putBoolean(key, (Boolean)value);
				} else if(value.getClass() == Long.class) {
					editor.putLong(key, (Long)value);
				} else if(value.getClass() == Float.class) {
					editor.putFloat(key, (Float)value);
				}
			}
    		editor.commit();
    	}
    }
    
    /**
     * 获取多个key值对应的values
     * @param spName 缓存的名字
     * @param keyValueMap 要获取的缓存中的key值
     * @return
     */
    public static List<Object> getValuesFromSP(Context context,String spName, HashMap<String, Object> keyValueMap) {
    	List<Object> values = new ArrayList<Object>();
    	SharedPreferences sp = context.getSharedPreferences(spName, MODE_PRIVATE);
    	if(keyValueMap != null && !keyValueMap.isEmpty()) {
    		Set<String> keySet = keyValueMap.keySet();
    		Iterator<String> iterator = keySet.iterator();
    		while (iterator.hasNext()) {
				String key = (String) iterator.next();
				Object value = keyValueMap.get(key);
				if(value == String.class) {
					values.add(sp.getString(key, ""));
				} else if(value == Integer.class) {
					values.add(sp.getInt(key, 0));
				} else if(value == Boolean.class) {
					values.add(sp.getBoolean(key, false));
				} else if(value == Long.class) {
					values.add(sp.getLong(key, 0l));
				} else if(value == Float.class) {
					values.add(sp.getFloat(key, 0.0f));
				}
			}
    	}
    	return values;
    }
    
    /**
     * 获取缓存中所有的数据
     * @param spName 缓存的数据
     * @return 
     */
    public static Map<String, ?> getAllFromSP(Context context,String spName) {
    	SharedPreferences sp = context.getSharedPreferences(spName, MODE_PRIVATE);
    	return sp.getAll();
    }
    
    /**
     * 验证缓存中是否已经有某个key值
     * @param spName 缓存的名字
     * @param key 要查询的key值
     * @return
     */
    public static boolean hasKeyInSP(Context context,String spName, String key) {
    	SharedPreferences sp = context.getSharedPreferences(spName, MODE_PRIVATE);
    	return sp.contains(key);
    }
    
    /**
     * 删除缓存中的某个键值对
     * @param spName 缓存的名字
     * @param key 欲删除的缓存中的key值
     */
    public static void deleteValueInSP(Context context,String spName, String key) {
    	SharedPreferences sp = MainApplication.getInstance().getSharedPreferences(spName, MODE_PRIVATE);
    	if(sp.contains(key)) {
    		sp.edit().remove(key).commit();
    	}
    }
    
    /**
     * 删除缓存中的所有值
     * @param spName 缓存的名字
     */
    public static void deleteAllInSP(Context context,String spName) {
    	SharedPreferences sp = context.getApplicationContext().getSharedPreferences(spName, MODE_PRIVATE);
    	sp.edit().clear().commit();
    }
}
