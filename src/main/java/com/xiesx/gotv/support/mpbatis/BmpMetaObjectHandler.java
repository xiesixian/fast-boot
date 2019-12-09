package com.xiesx.gotv.support.mpbatis;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * mybatisplus自定义填充公共字段 ,即没有传的字段自动填充
 * 
 * @author Sixian.Xie
 * @date 2018-01-29
 */
@Slf4j
@Component
public class BmpMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		try {
			// 添加时间
			Object createDate = metaObject.getValue("createDate");
			if (null == createDate) {
				metaObject.setValue("createDate", new Date());
			}
			// 修改时间
			Object modifyDate = metaObject.getValue("modifyDate");
			if (null == modifyDate) {
				metaObject.setValue("modifyDate", new Date());
			}
		} catch (Exception e) {
			log.debug("insertFill : " + e.getMessage());
		}
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		// 修改时间
		try {
			Object modifyDate = metaObject.getValue("modifyDate");
			if (null == modifyDate) {
				metaObject.setValue("modifyDate", new Date());
			}
		} catch (Exception e) {
			log.debug("updateFill : " + e.getMessage());
		}
	}
}