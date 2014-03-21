package cn.yuol.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

import cn.yuol.dao.IDao;
import cn.yuol.dao.InformationDao;
import cn.yuol.model.Information;
import cn.yuol.utils.DBUtils;
import cn.yuol.utils.ModelUtils;

public class DBTest {

	@Test
	public void test() {
		try {
			Connection con = DBUtils.getConnect();
			String sql = "select count(*) from shc_information";
			PreparedStatement pre = con.prepareStatement(sql);
			ResultSet result = pre.executeQuery();
			while (result.next()) {
				int count = result.getInt(1);
				System.out.println(count);
			}
			String name = getClass().getName();
			name = name.substring(name.lastIndexOf(".") + 1, name.length() - 3);
			System.out.println(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test1() throws Exception {
		Map<String, String> map=DBUtils.getConfig();
		System.out.println(map.get("prefix"));
		System.out.println(DBUtils.getConnect()==null);
	}
	@Test
	public void test2() throws Exception {
		 InformationDao dao=new InformationDao();
		 Information information=new Information(58, "aaa", 150, "jim1", "123456789", "1234567788", "1.jpg"
				 , "mmmmmmmmm", ModelUtils.parseDate(), 2141);
		/* List<String> columns=ModelUtils.getBeanNames(Information.class, "info_id");
			String sql = dao.insertSql(columns);
			List<Object> objs = ModelUtils.getBeanValues(columns,information);
			System.out.println(columns);
			System.out.println(sql);
			System.out.println(objs);*/
		// System.out.println(dao.insert(information));
		 System.out.println(dao.find(80));
		 System.out.println(dao.getCount());
		 List<Object> objs=new ArrayList<Object>();
		 objs.add("jim");
		// System.out.println(dao.getAll(new String[]{"name"},objs).size());
		 System.out.println(dao.update(information));
		}
	@Test
	public void test3() throws Exception {
		 IDao dao=new InformationDao("shc_");
		 Information information=new Information();
		/* information.setInfo_id(50);
		 information.setName("bob");
		 System.out.println(dao.update(information));*/
		 List<Object> objs=new ArrayList<Object>();
		 objs.add(60);
		 /*String sql="select * from "+dao.getTable()+" where info_id>?";
		 System.out.println(sql);
		 ResultSet resultSet=dao.query(sql, objs);
		 while(resultSet.next()){
			 int info_id=resultSet.getInt("info_id");
			 String name=resultSet.getString("name");
			 information.setName(name);
			 information.setInfo_id(info_id);
		 }
		 System.out.println(information);*/
		 //System.out.println(dao.getOne(new String[]{"info_id"}, objs));
	}
	@Test
	public void test4() throws Exception{
		InformationDao dao=new InformationDao("shc_");
		Map<String, String> maps=new HashMap<String, String>();
		maps.put("id", "=");
		maps.put("password", ">=");
		maps.put("name", "!=");
		System.out.println(dao.find(maps, null));
	}
	@Test
	public void test5() throws Exception{
		 InformationDao dao=new InformationDao("shc_");
		/*ResultSet result=dao.getKey();
		result.first();
		ResultSetMetaData metaData=result.getMetaData();
    	int count=metaData.getColumnCount();
    	for (int i = 1; i <=count; i++) {
			String name=metaData.getColumnName(i);
			Object value= (result.getObject(name)==null)?"":result.getObject(name);
			System.out.println(name+"=="+value);
		}
    	result.close();*/
		 Information information=new Information();
		 Map<String, String> map=new HashMap<String, String>();
		 map.put("name","=");
		 List<Object> objs=new ArrayList<Object>();
		 objs.add("bob");
		 System.out.println(dao.getAll(map, objs));
	}
}
