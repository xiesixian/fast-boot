##使用必读.md
####本文档以MyEclipse IDE为例， Eclipse/Idear请酌情添加
--------

### 目录
- <a href="http://43.241.228.232:9394/doc/" target="_blank">开发手册</a>
- <a href="#md0">环境配置</a>
- <a href="#md1">发布注意</a>
- <a href="#md2">自动发布</a>
- <a href="#md3">代码生成</a>
- <a href="#md4">持久增强</a>

-------- 

<a name="md0">

### 环境配置
进入Java Build Path，添加 Link Source <br>
填写 <br>
name：【comm】 <br>
location：【D:/Projects/gtgjxcom/src/main/java】（你的com目录）<br>
```
<linkedResources>
   <link>
      <name>comm</name>
      <type>2</type>
      <location>D:/Projects/gtgjxcom/src/main/java</location>
   </link>
</linkedResources>
```
添加成功后查看.project文件，如果有则说明成功
</a>

--------

<a name="md1">

### 发布注意
|配置|描述|
|:------------------|------------------:|
| pom.xml			 |本地配置			|
| pom_release.xml	 |线上配置			|
```
注意：
1、不同的服务器需要新建一个对应的gtgjxcom
2、gtgjxcom.path 配置
```
</a>

--------

<a name="md2">

### 自动发布
|环境|名称|服务器|
|:------------------|-------------------------------|------------------:|
| 正式			 	|gtgjxact10-release-611/612		|39.105.145.95@gtgj2|
| 灰度	 			|gtgjxact10-release-9006		|39.105.145.95@gtgj |
| 测试	 			|gtgjxact10-test-9403			|43.241.228.232 	|
| 正式异步	 		|gtgjxact10-release-task-61112	|39.105.145.95@gtgj2|
| 测试异步	 		|gtgjxact10-task-9403 			|43.241.228.232 	|
</a>

--------

<a name="md3">

### 代码生成
|参数|描述|值|
|:------------------|---------------|------------------:|
| outputDir			|输出目录		|D\:\\Projects\\gtgjxact10\\src\\main\\java|
| parent	 		|包路径			|generator			|
| tablePrefix	 	|表前缀			|43.241.228.232 	|
| tableName	 		|表全称			|hbgj_cip_order		|
| author	 		|作者 			|谢思贤				|
| url				|数据连接 		|jdbc:mysql://221.235.53.167:3306/gttest?useUnicode=true&characterEncoding=utf-8 				|
| userName			|用户名 			|gttest 			|
| passWord			|密码 			|gttest0319 		|
```
利用mybatis里的生成插件代码生成，自定义（“高铁航班”风格版）controller、service、emtity 模板代码
1、配置：generator.properties
2、运行：GeneratorMPHelper.java 本地run，成功后会打开outputDir
3、支持：默认已开启AR模式
```
</a>

--------

<a name="md4">

### 持久增强

#### 特点
1、使用标准：JPA（Java Persistence API），不宜懒第三方ORM框架 <br>
2、兼容现有：与现有autoDao互补增强，提高效率<br>
3、动态构建：目前支持select、insert、update，常用操作简化代码开发 <br>
4、特定字段：查询支持字段配置、别名 <br>
5、领域模型：支持AR模式<br>
6、灵活使用：以上特点均解耦可拆分使用<br>

#### 使用
1、测试详见：SQLBuilder.java <br>
2、使用详见：CipOrderService.java

#### 改进
未来需要完善：如果排序order by 、分组group by 、分页limit x、连接查询join....

#### 示例
AR终极版V4
```
	public CipOrder getOrderId(String cipOrderId) {
		// AR领域模型
		return new CipOrder().setOrderId(cipOrderId).find();
	}
```
sql构建版V3
```
	public CipOrder getOrderId(String cipOrderId) {
		// 参数设置
		CipOrder order = new CipOrder().setOrderId(cipOrderId);
		// 动态构建
		SQLContext sqlContext = SQLBuilder.select(order);
		// 执行->填充
		return mapper.fillToMap(autoProcessDao.queryForMap(sqlContext.getSqlToString(), sqlContext.getParamsToArray(), null));
	}
```
mp填充版V2
```
	public CipOrder getOrderId(String cipOrderId) {
		String sql = "select * from hbgj_cip_order where order_id=? ";
		// 执行->填充
		return mapper.fillToMap(autoProcessDao.queryForMap(sql, new Object[] { cipOrderId }, null));
	}
```
原始版本V1
```
	public CipOrder getOrderId4(String cipOrderId) {
		String sql = "select * from hbgj_cip_order where order_id=? ";
		Map<String, Object> map = autoProcessDao.queryForMap(sql, new Object[] { cipOrderId }, null);
		CipOrder order = new CipOrder();
		order.setOrderId(ObjectUtil.getString(map.get("order_id")));
		order.setPhoneId(ObjectUtil.getString(map.get("phone_id")));
		//.....如果是*，则后边还有30个字段
		return order;
	}
```

#### 注意
部分功能需要开启配置，代码生成了才可以使用
</a>

--------