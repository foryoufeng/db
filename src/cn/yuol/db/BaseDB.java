package cn.yuol.db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.yuol.utils.DBUtils;

public class BaseDB{
    private Connection con;
    private PreparedStatement pre;
	public BaseDB() throws Exception {
		this.con=DBUtils.getConnect();
	}
	public Connection getCon() {
		return con;
	}
	/**
	 * 执行查找操作
	 * @param sql
	 * @param objs
	 * @return
	 * @throws SQLException
	 */
    private ResultSet select(String sql,List<Object> objs) throws SQLException{
    	if(checkParams(sql,objs)){
    	pre= con.prepareStatement(sql);
    	setValue(objs);
    	return pre.executeQuery();
    	}
    	return null;
    }
    /**
     * 执行sql查询语句
     * @param sql
     * @param objs
     * @return
     * @throws SQLException
     */
    public ResultSet query(String sql,List<Object> objs)throws SQLException{
    	return select(sql, objs);
    }
    /**
     * 执行更新操作 I D U
     * @param sql
     * @param objs
     * @return
     * @throws SQLException
     */
    public int update(String sql,List<Object> objs)throws SQLException{
    	if(checkParams(sql,objs)){
    	pre=con.prepareStatement(sql);
    	setValue(objs);
    	return pre.executeUpdate();
    	}
    	return 0;
    }
    /**
     * 检察参数是否不为空
     * @param sql
     * @param objs
     * @return
     */
    private boolean checkParams(String sql,List<Object> objs){
    	if(sql!=null&&!sql.isEmpty()){
    		return true;
    	}
    	return false;
    }
    /**
     * 为查询的占位符赋值
     * @param objs
     * @throws SQLException
     */
    private void setValue(List<Object> objs) throws SQLException{
    	if(objs!=null){
    		int index=1;
    		for(Object obj:objs){
    			pre.setObject(index, obj);
    			index++;
    		}
    	}
    }
    /**
     * 根据查询条件返回一条结果集
     * @param sql  预处理语句
     * @param objs 查询条件的值
     * @param bean 将结果反射进bean中
     * @return
     * @throws Exception
     */
    public <T> T getOne(String sql,List<Object> objs,Class<T> bean) throws Exception{
    	ResultSet result=select(sql, objs);
    	T t=null;
    	if(result.first()){
    	t=reflectValue(result, bean);
    	}
    	result.close();
    	pre.close();
    	return t;
    }
    /**
     * 根据查询条件返回一条结果集
     * @param sql 预处理语句
     * @param objs  查询条件的值
     * @return  装入map中
     * @throws Exception
     */
    public Map<String, Object> getOne(String sql,List<Object> objs) throws Exception{
    	ResultSet result=select(sql, objs);
    	Map<String, Object> map=null;
    	if(result.first()){
    		map =mapValue(result);
        }
    	result.close();
    	pre.close();
    	return map;
    }
    /**
     * 根据查询条件返回多条结果集
     * @param sql
     * @param objs
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getAll(String sql,List<Object> objs)throws Exception{
    	List<Map<String, Object>> list=null;
    	ResultSet result=select(sql, objs);
    	if(result!=null){
    		list=new ArrayList<Map<String,Object>>();
    		while(result.next()){
    		list.add(mapValue(result));
    		}
    	}
    	result.close();
    	pre.close();
    	return list;
    }
    /**
     * 根据查询条件返回多条结果集
     * @param sql  预处理语句
     * @param objs 查询条件的值
     * @param bean 将结果反射进bean中
     * @return   list集合
     * @throws Exception
     */
    public  <T> List<T> getAll(String sql,List<Object> objs,Class<T> bean)throws Exception{
    	ResultSet result=select(sql, objs);
    	List<T> lists=null;
    	if(result!=null){
    	    lists=new ArrayList<T>();
    	    while(result.next()){
    		     lists.add(reflectValue(result, bean));
    	     }
    	}
    	result.close();
    	pre.close();
    	return lists;
    }
    /**
     * 将值反射到bean中去
     * @param result
     * @param bean
     * @return
     * @throws Exception
     */
    private <T> T reflectValue(ResultSet result,Class<T> bean)throws Exception{
    	T model=bean.newInstance();
    	ResultSetMetaData metaData=result.getMetaData();
    	int count=metaData.getColumnCount();
    	for (int i = 1; i <=count; i++) {
			String name=metaData.getColumnName(i);
			Object value= (result.getObject(name)==null)?"":result.getObject(name);
			Field field=bean.getDeclaredField(name);
			field.setAccessible(true);
			field.set(model, value);
		}
    	return model;
    }
    /**
     * 返回list集合的结果集
     * @param result
     * @return
     * @throws Exception
     */
    private Map<String, Object> mapValue(ResultSet result)throws Exception{
    	Map<String, Object> map=new HashMap<String, Object>();
    	ResultSetMetaData metaData=result.getMetaData();
    	int count=metaData.getColumnCount();
    	for (int i = 1; i <=count; i++) {
    		String name=metaData.getColumnName(i);
			Object value= (result.getObject(name)==null)?"":result.getObject(name);
			map.put(name, value);
    	}
    	return map;
    }
    /**
     * 获取数据总数
     * @param sql
     * @return
     * @throws Exception
     */
    public int getCount(String sql) throws Exception{
    	ResultSet result=select(sql, null);
		if(result.first()){
			return result.getInt(1);
		}
		return 0;
    }
    public void close() throws SQLException{
    	con.close();
    }
    public String getKeyName(String name){
    	ResultSet result=null;
    	String keyName=null;
    	try{
    		result=con.getMetaData().getPrimaryKeys(null, null, name);
    	if(result!=null && result.first()){
    		keyName=result.getString(4);
    	}
    	return keyName;
    	}catch(SQLException e){
    		e.printStackTrace();
    		return keyName;
    	}finally{
    		if(result!=null)
				try {
					result.close();
				} catch (SQLException e) {
				}
    	}
    }
}
