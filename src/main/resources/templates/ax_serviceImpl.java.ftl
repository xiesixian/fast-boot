package ${package.ServiceImpl};

import java.util.List;

import lombok.NonNull;

import org.springframework.beans.factory.annotation.Autowired;

import ${package.Entity}.${entity};
import org.springframework.stereotype.Service;

import com.huoli.gtgjx.act10.support.jdbc.row.DefaultRowMappler;
import com.huoli.gtgjx.com.dao.db.AutoProcessDao;

 /**
 * @title ${table.serviceImplName}.java
 * @description ${table.comment} 业务类
 * @author ${author}
 * @date ${date}
 */
@Service
public class Ax${table.serviceImplName} {

	private DefaultRowMappler<${entity}> mapper = new DefaultRowMappler<${entity}>(${entity}.class);
	
	@Autowired
	private AutoProcessDao autoProcessDao;
	
	/**
	 * 查询List
	 * 
	 * @return
	 */
	public List<${entity}> getList(@NonNull String id) {
		String sql = "select id from ${table.name} where id=? ";
		return mapper.fillToList(autoProcessDao.queryForList(sql, new Object[] { id }, null));
	}
	
	/**
	 * 查询Info
	 * 
	 * @return
	 */
	public ${entity} getInfo(@NonNull String id) {
		String sql = "select id from ${table.name} where id=? ";
		return mapper.fillToMap(autoProcessDao.queryForMap(sql, new Object[] { id }, null));
	}
	
	/**
	 * 添加
	 * 
	 * @param id
	 * @return
	 */
	public Integer insert(@NonNull String id) {
		String sql = "insert ignore into ${table.name} (`id`,`update_time`) values(?,current_timestamp) ";
		return autoProcessDao.update(sql, new Object[] { id, id }, null);
	}
	
	/**
	 * 修改
	 * 
	 * @param id
	 * @return
	 */
	public Integer update(@NonNull String id) {
		String sql = "update ${table.name} set xx = ? where order_id = ? ";
		return autoProcessDao.update(sql, new Object[] { id, id }, null);
	}
}
