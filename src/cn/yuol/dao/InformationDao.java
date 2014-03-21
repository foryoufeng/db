package cn.yuol.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.yuol.model.Information;

public class InformationDao extends BaseDao{
	public InformationDao() throws Exception {
	}

	public InformationDao(String prefix) throws Exception {
		super(prefix);
	}

	/**
	 * 插入数据
	 * @param information
	 * @return
	 * @throws SQLException
	 */
	public int insert(Information information) throws SQLException {
		return super.insert(information);
	}
	/**
	 * 根据指定的条件查找一条数据 条件为 =
	 * @param where
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public Information find(String[] where,List<Object> objs) throws Exception{
		return find(where, objs, Information.class);
	}
	/**
	 * 根据指定的条件查找一条数据 条件为 map中的 value
	 * @param where
	 * @param objs
	 * @return
	 * @throws Exception
	 */
	public Information getOne(Map<String, String> where,List<Object> objs) throws Exception{
		return super.getOne(where, objs,Information.class);
	}
	/**
	     根据指定的条件查找一条数据 条件为 map中的 value
	 * @param where
	 * @param objs
	 * @return  返回map集合
	 */
	public Map<String, Object> find(Map<String, String> where,List<Object> objs)throws Exception{
		return super.find(where, objs);
	}
	/**
	 * 根据指定的id查找一条数据
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Information find(int id) throws Exception{
		return  find(id, Information.class);
	}
	/**
	 * 获取指定条数的结果集
	 * @param order 指定排序条件 如:"name DESC"
	 * @param params  条数 如：3->取3条记录    1，3->从1开始选3条
	 * @return
	 * @throws Exception
	 */
	public List<Information> getPage(String order,int... params) throws Exception{
		return getPage(Information.class,order,params);
	}
	/**
	 * 获取指定条数的结果集   按主键逆序排列
	 * @param params  条数 如：3->取3条记录    1，3->从1开始选3条
	 * @return
	 * @throws Exception
	 */
	public List<Information> getPage(int... params) throws Exception{
		return super.getPage(Information.class,params);
	}
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int delete(int id) throws SQLException{
		return  super.delete(id);
	}
	/**
	 * 更新数据
	 * @param information
	 * @return
	 * @throws SQLException
	 */
	public int update(Information information) throws SQLException{
		List<Object> objs=new ArrayList<Object>();
		objs.add(information.getName());
		objs.add(information.getInfo_id());
		List<String> columns=new ArrayList<String>();
		columns.add("name");
		return  update(columns, objs);
	}
}
