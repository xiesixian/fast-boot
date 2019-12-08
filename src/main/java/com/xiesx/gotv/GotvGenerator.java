package com.xiesx.gotv;

import java.util.Map;
import java.util.ResourceBundle;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.google.common.collect.Maps;

/**
 * @title GeneratorMPHelper.java
 * @description 利用mybatis里的生成插件代码生成，自定义（“高铁航班”风格版）controller、service、emtity模板代码，后续粘贴复制，so easy
 * @author Sixian.xie
 * @date 2016年5月1日 下午15:14:10
 */
public class GotvGenerator {

	/**
	 * 用来获取GeneratorCode.properties文件的配置信息
	 */
	public static ResourceBundle bundle = ResourceBundle.getBundle("GeneratorCode");

	public static void main(String[] args) {
		// 自定生成
		AutoGenerator mpg = new AutoGenerator();
		// 全局配置
		mpg.setGlobalConfig(globalConfig());
		// 数据源配置
		mpg.setDataSource(dataSourceConfig());
		// 策略配置
		mpg.setStrategy(strategyConfig());
		// 路径配置
		mpg.setPackageInfo(packageConfig());
		// 模板配置
		mpg.setTemplate(templateConfig());
		// 自定义内容
		mpg.setCfg(customerConfig());
		//
		mpg.setTemplateEngine(new FreemarkerTemplateEngine());
		// 执行生成
		mpg.execute();
	}

	/**
	 * 全局配置
	 * 
	 * @param rb
	 * @return
	 */
	protected static GlobalConfig globalConfig() {
		GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir(bundle.getString("outputDir"));
		gc.setFileOverride(true);//是否覆盖
		gc.setOpen(true);//是否打开文件夹
		gc.setActiveRecord(true);// 开启activeRecord模式
		gc.setAuthor(bundle.getString("author"));
		// 设置名称
		gc.setControllerName("%sController");
		gc.setServiceImplName("%sService");
		return gc;
	}

	/**
	 * 数据源配置
	 * 
	 * @param rb
	 * @return
	 */
	protected static DataSourceConfig dataSourceConfig() {
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.MYSQL);
		dsc.setUrl(bundle.getString("url"));
		dsc.setDriverName("com.mysql.jdbc.Driver");
		dsc.setUsername(bundle.getString("userName"));
		dsc.setPassword(bundle.getString("passWord"));
		dsc.setTypeConvert(new MySqlTypeConvert());
		//		dsc.setTypeConvert(new MySqlTypeConvert() {
		//
		//			@Override
		//			public DbColumnType processTypeConvert(String fieldType) {
		//				System.out.println("转换类型：" + fieldType);
		//				// if ( fieldType.toLowerCase().contains( "int" ) ) {
		//				//    return DbColumnType.BASE_INT;
		//				// }
		//				return super.processTypeConvert(fieldType);
		//			}
		//		});
		return dsc;
	}

	/**
	 * 策略配置
	 * 
	 * @param rb
	 * @return
	 */
	protected static StrategyConfig strategyConfig() {
		StrategyConfig strategy = new StrategyConfig();
		strategy.setTablePrefix(new String[] { bundle.getString("tablePrefix") });// 此处可以修改为您的表前缀
		strategy.setInclude(new String[] { bundle.getString("tableName") }); // 需要生成的表
		strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
		strategy.setRestControllerStyle(true);//RestStyle
		//strategy.setControllerMappingHyphenStyle(true);//HyphenStyle
		strategy.setEntityBuilderModel(true);// 是否使用建造者模型
		strategy.setEntityLombokModel(true);// 是否使用Lombok
		strategy.setEntityColumnConstant(true);//常量
		strategy.setSuperEntityClass("com.huoli.gtgjx.act10.support.jdbc.base.BaseEntity");
		strategy.setSuperControllerClass("com.huoli.gtgjx.com.controller.AbstractCommGateWayController");
		return strategy;
	}

	/**
	 * 路径配置
	 * 
	 * @param rb
	 * @return
	 */
	protected static PackageConfig packageConfig() {
		PackageConfig pc = new PackageConfig();
		pc.setParent(bundle.getString("parent"));// 自定义包路径
		pc.setController("controller");
		pc.setEntity("entity");
		pc.setServiceImpl("service");
		return pc;
	}

	/**
	 * 自定义生成内容
	 */
	private static TemplateConfig templateConfig() {
		TemplateConfig tc = new TemplateConfig();
		//tc.setController("");//默认
		//tc.setEntity("");//默认
		tc.setMapper(null);//不生成
		tc.setXml(null);//不生成
		tc.setService(null);//不生成
		//tc.setServiceImpl("");//默认
		return tc;
	}

	/**
	 * 自定义生成内容
	 */
	private static InjectionConfig customerConfig() {
		//注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
		InjectionConfig config = new InjectionConfig() {

			@Override
			public void initMap() {
				Map<String, Object> map = Maps.newConcurrentMap();
				map.put("author", this.getConfig().getGlobalConfig().getAuthor());
				this.setMap(map);
			}
		};
		//		List<FileOutConfig> files = new ArrayList<FileOutConfig>();
		//		files.add(new FileOutConfig("/templates/entity.java.ftl") {
		//
		//			@Override
		//			public String outputFile(TableInfo tableInfo) {
		//				String expand = bundle.getString("outputDir") + "/" + bundle.getString("parent") + "/entity/";
		//				return String.format((expand + File.separator + "%s" + ".java"), tableInfo.getEntityName());
		//			}
		//		});
		//		config.setFileOutConfigList(files);
		return config;
	}
}