package com.xiesx.gotv.support.datebase.mpbatis.base;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * 实体父类
 * 
 * @author Sixian.Xie
 * @date 2018-01-29
 * @param <T>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BmpEntity<T extends Model<T>> extends Model<T> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@JSONField(ordinal = 1)
	@TableId(type = IdType.ASSIGN_ID)
	public Long id;

	/**
	 * 创建时间
	 */
	@JSONField(ordinal = 2)
	@TableField(value = "create_time", fill = FieldFill.INSERT, insertStrategy = FieldStrategy.NOT_EMPTY)
	public Date createTime;

	/**
	 * 更新时间
	 */
	@JSONField(ordinal = 3)
	@TableField(value = "modify_time", fill = FieldFill.INSERT_UPDATE, updateStrategy = FieldStrategy.NOT_EMPTY)
	public Date modifyTime;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
