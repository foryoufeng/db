package cn.yuol.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.yuol.db.BaseDB;
import cn.yuol.utils.DBUtils;
import cn.yuol.utils.ModelUtils;

public abstract class BaseDao implements IDao{
	private String table;
	private String prefix;
	private final String SELECT = "select ";
	private final String INSERT = "insert into ";
	private final String WHERE = " where ";
	private final String LIMIT = " limit ";
	private final String DELETE = "delete from ";
	private final String FROM = " from ";
	private final String ALL = "*";
	private final String AND = "and";
	private final String OR = "or";
	private final String ORDER = " order by ";
    private BaseDB db;
	public BaseDao() throws Exception {
		this(DBUtils.getConfig().get("prefix"));
	}
	public BaseDao(String prefix) throws Exception {
		this.prefix = prefix;
		this.table = (prefix + getName()).toLowerCase();
		this.db=new BaseDB();
	}

	private String getName() {
		String name = getClass().getName();
		return name.substring(name.lastIndexOf(".") + 1, name.length() - 3);
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	/**
	 * 解析sql中的字段
	 * @param columns 数据列
	 * @return String
	 */
	private String parseColumns(List<String> columns) {
		StringBuilder sb = new StringBuilder();
		for (String str : columns) {
			sb.append(str + ",");
		}
		return sb.deleteCharAt(sb.lastIndexOf(",")).toString();
	}
	/**
	 * 解析where  flag为true时为and，false是为or
	 * @param where
	 * @param flag
	 * @return
	 */
	private String parseWhere(Map<String, String> where,boolean flag) {
		StringBuilder sb = new StringBuilder();
		String operate=(flag==true)?AND:OR;
		Set<String> set=where.keySet();
		Iterator<String> iterator=set.iterator();
		while(iterator.hasNext()){
			String key=iterator.next();
			String value=where.get(key);
			sb.append(key + value+"? "+operate+" ");
		}
		int start=sb.lastIndexOf(operate);
		return WHERE + sb.delete(start,start+3).toString();
	}
	private String parseWhere(String[] where,boolean flag) {
		StringBuilder sb = new StringBuilder();
		String operate=(flag==true)?AND:OR;
		for (String str : where) {
			sb.append(str + "=? "+operate+" ");
		}
		int start=sb.lastIndexOf(operate);
		return WHERE + sb.delete(start,start+3).toString();
	}
	/**
	 * 解析查询条数
	 * @param limit
	 * @return
	 */
	private String parseLimit(String limit) {
		return LIMIT + limit;
	}
	/**
	 * 解析排列条件
	 * @param ord
	 * @return
	 */
	private String parseOrder(String ord){
		return ORDER+ord;
	}
	/**
	 * 根据条件查出有关的所有数据列
	 * @param where  根据条件
	 * @return sql语句
	 */
    private String selectSql(String[] where){
    	return selectSql(null,where);
    }
    private String selectSql(Map<String, String> where){
    	return selectSql(null,where);
    }
    /**
     * 根据条件查出有关的指定数据列
     * @param columns 指定数据列
     * @param where  根据条件
     * @return sql语句
     */
    private String selectSql(List<String> columns, String[] where) {
		return selectSql(columns,where,null);
	}
    private String selectSql(List<String> columns, Map<String, String> where) {
		return selectSql(columns,where,null);
	}
	/**
	 * 根据条件查出有关的指定数据列，并限制条数
	 * @param columns 指定数据列
	 * @param where  条件
	 * @param limit 限制条数
	 * @return sql语句
	 */
    private String selectSql(List<String> columns, String[] where,String limit) {
		return selectSql(columns, where, true, limit,null);
	}
    private String selectSql(List<String> columns, Map<String, String> where,String limit) {
		return selectSql(columns, where,limit,null,true);
	}
    /**
     * 组成sql语句 
     * @param columns 为空时为 *
     * @param where   
     * @param flag   为true时 查找条件为and，为flase时查找条件为or 默认为true
     * @param limit  只需要字符串如："1,3"
     * @param order  需要字符串如："id DESC"
     * @return sql语句  
     */
    private String selectSql(List<String> columns, String[] where,boolean flag,String limit,String order) {
		String col=(columns==null)?ALL:parseColumns(columns);
		String whe =(where==null)?"":parseWhere(where,flag);
		String lim=(limit==null)?"":parseLimit(limit);
		String ord=(order==null)?"":parseOrder(order);
		return SELECT + col +FROM+table+ whe+ord+lim;
	}
    private String selectSql(List<String> columns,Map<String, String> where,String limit,String order,boolean flag) {
		String col=(columns==null)?ALL:parseColumns(columns);
		String whe =(where==null)?"":parseWhere(where,flag);
		String lim=(limit==null)?"":parseLimit(limit);
		String ord=(order==null)?"":parseOrder(order);
		return SELECT + col +FROM+table+ whe+ord+lim;
	}
    /**
     * 解析查询总数
     * @return
     */
    private String parseCount(){
    	return SELECT+"count(*)"+FROM+table;
    }
    /**
	 * 解析出需要插入的sql语句
	 * @param columns 指定要插入的数据列
	 * @return
	 */
	private String parseInsert(List<String> columns){
		String col= parseColumns(columns);
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<columns.size();i++){
			sb.append("?,");
		}
		sb=sb.deleteCharAt(sb.lastIndexOf(","));
		String sql=INSERT+table+"("+col+") values("+sb.toString()+")";
		return sql;
	}
	/**
	 * 解析删除sql
	 * @param where 字段
	 * @return
	 */
	private String parseDelete(String[] where){
		String sql=DELETE+table;
		sql+=parseWhere(where,true);
		return sql;
	}
	/**
	 * 解析更新字段
	 * @param columns
	 * @param where
	 * @return
	 * @throws SQLException 
	 */
	private String parseUpdate(List<String> columns) throws SQLException{
		StringBuilder sb=new StringBuilder();
		sb.append("update "+table+" set ");
		for(String col:columns){
			sb.append(col+"=?,");
		}
		sb=sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append(parseWhere(new String[]{getKey()}, true));
		return sb.toString();
	}
    /**
     * 返回条数
     * @return
     */
	public int getCount(){
		try {
			return db.getCount(parseCount());
		} catch (Exception e) {
			e.printStackTrace();
		   return 0;
		}
	}
	/**
	 * 根据id查找
	 * @param id
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public <T> T find(int id,Class<T> bean) throws Exception{
		String sql=selectSql(new String[]{getKey()});//根据主键生成sql
		List<Object> objs=new ArrayList<Object>();
		objs.add(id);
		return db.getOne(sql, objs, bean);
	}
	/**
	 * 根据数组进行查找  如 new String[]{"id"} --> id=?
	 * @param where
	 * @param objs
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public <T> T find(String[] where,List<Object> objs,Class<T> bean) throws Exception{
		return db.getOne(selectSql(where), objs, bean);
	}
	/**
	 * 根据map进行多选择查找  map.add("id",">=10");  -->"id >=10"
	 * @param where
	 * @param objs
	 * @param bean
	 * @return  返回传入的bean
	 * @throws Exception
	 */
	public <T> T getOne(Map<String, String> where,List<Object> objs,Class<T> bean) throws Exception{
		return db.getOne(selectSql(where),objs,bean);
	}
	/**
	 * 根据map进行多选择查找  map.add("id",">=10");  -->"id >=10"
	 * @param where
	 * @param objs
	 * @return  返回mp集合
	 * @throws Exception
	 */
	public Map<String, Object> find(Map<String, String> where,List<Object> objs) throws Exception{
		return db.getOne(selectSql(where), objs);
	}
	/**
	 * 根据指定的条件查找多条相关的数据
	 * @param sql
	 * @param objs
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> getAll(Map<String, String> where,List<Object> objs,Class<T> bean)throws Exception{
		return db.getAll(selectSql(where), objs, bean);
	}
	public List<Map<String, Object>> getAll(Map<String, String> where,List<Object> objs) throws Exception{
		return db.getAll(selectSql(where), objs);
	}
	/**
	 * 获取分页数据
	 * @param bean
	 * @param order
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> getPage(Class<T> bean,String order,int...params)throws Exception{
		String limit= (params.length==2)?params[0]+","+params[1]:params[0]+"";//两个参数为params[0],params[1],一个或多个为params[0]
		String ord=(order==null)?getKey()+" DESC":order;//默认按主键由大到小排列
		return db.getAll(selectSql(null,null,false,limit,ord), null, bean);
	}
	/**
	 * 获取分页数据
	 * @param order
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getJsonPage(String order,int...params)throws Exception{
		String limit= (params.length==2)?params[0]+","+params[1]:params[0]+"";//两个参数为params[0],params[1],一个或多个为params[0]
		String ord=(order==null)?getKey()+" DESC":order;//默认按主键由大到小排列
		return db.getAll(selectSql(null,null,false,limit,ord), null);
	}
	/**
	 *  获取分页数据  按主键逆序排列
	 * @param bean
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> getPage(Class<T> bean,int...params)throws Exception{
		return getPage(bean,null, params);
	}
	/**
	 * 获取分页数据  按主键逆序排列
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getJsonPage(int...params)throws Exception{
		return getJsonPage(null, params);
	}
	/**
	 * 在指定字段插入指定的值
	 * @param columns 字段
	 * @param objs 值
	 * @return 插入的条数
	 * @throws SQLException
	 */
	public int insert(Object obj)throws SQLException{
		if(obj!=null){
		List<String> columns=ModelUtils.getBeanNames(obj.getClass(), getKey());
		List<Object> objs = ModelUtils.getBeanValues(columns,obj);
		return db.update(parseInsert(columns), objs);
		}
		return 0;
	}
	/**
	 * 通过字段删除
	 * @param where  条件
	 * @param objs   字段的值
	 * @return
	 * @throws SQLException
	 */
	public int delete(int id) throws SQLException{
		String sql=parseDelete(new String[]{getKey()});//根据主键生成sql
		List<Object> objs=new ArrayList<Object>();
		objs.add(id);
		return db.update(sql, objs);
	}
	/**
	 * 实现更新
	 * @param columns  字段
	 * @param where  条件
	 * @param value  更新的值
	 * @return
	 * @throws SQLException
	 */
	public int update(List<String> columns,List<Object> value) throws SQLException{
		return db.update(parseUpdate(columns), value);
	}
	/**
	 * 执行sql查询语句
	 * @param sql
	 * @param value
	 * @return
	 * @throws SQLException
	 */
	public ResultSet query(String sql,List<Object> value) throws SQLException{
		return db.query(sql, value);
	}
	protected String getKey() throws SQLException{
		return db.getKeyName(table);
	}
}
