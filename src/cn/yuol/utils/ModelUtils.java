package cn.yuol.utils;

import java.lang.reflect.Field;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

public class ModelUtils {
  
     public static List<Object> getBeanValues(List<String> columns, Object bean){
    	 List<Object> list=new ArrayList<Object>();
    	 for(String col:columns){
    		 try {
				list.add(BeanUtils.getProperty(bean, col));
			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
    	 }
    	 return list;
     }
     public static Date parseDate(){
    	 Date date=new Date(System.currentTimeMillis());
    	 SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    	 format.format(date);
    	 return date;
     }
     /**
      * 获取bean的所有属性
      * @param bean
      * @return
      */
     public static  List<String> getBeanNames(Class<?> bean){
    	return getBeanNames(bean,null);
     }
     /**
      * 获取bean的所有属性 除了pass
      * @param bean
      * @param pass
      * @return
      */
     public static  List<String> getBeanNames(Class<?> bean,String pass){
    	 List<String> list=new ArrayList<String>();
    	 Field[] fields= bean.getDeclaredFields();
		 for(Field field:fields){
			 if(pass!=null &&field.getName().equals(pass))
				 continue;
			 list.add(field.getName());
		 }
    	 return list;
     }
}
