package cn.yuol.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IDao {
	/**
	 * 插入数据
	 * 
	 * @param information
	 * @return
	 * @throws SQLException
	 */
	int insert(Object object) throws SQLException;

	/**
	 * 根据指定的id查找一条数据
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	<T> T find(int id, Class<T> bean) throws Exception;

	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	int delete(int id) throws SQLException;

	/**
	 * 更新数据
	 * 
	 * @param information
	 * @return
	 * @throws SQLException
	 */
	int update(List<String> columns, List<Object> value) throws SQLException;

	/**
	 * 根据指定的条件查找多条相关的数据
	 * 
	 * @param sql
	 * @param objs
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	<T> List<T> getAll(Map<String, String> where, List<Object> objs,
			Class<T> bean) throws Exception;

	/**
	 * 执行sql查询语句
	 * @param sql
	 * @param value
	 * @return
	 * @throws SQLException
	 */
	public ResultSet query(String sql, List<Object> value) throws SQLException;
}