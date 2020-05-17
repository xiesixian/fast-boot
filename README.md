##使用必读.md
####本文档以MyEclipse IDE为例， Eclipse/Idear请酌情添加
--------

### 目录
- <a href="http://43.241.228.232:9394/doc/" target="_blank">开发手册</a>
- <a href="#md0">环境配置</a>
- <a href="#md1">发布注意</a>
- <a href="#md2">自动发布</a>
- <a href="#md3">代码生成</a>
- <a href="#md4">License</a>

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

### License

## 1. 生成私匙库
# validity：私钥的有效期多少天
# alias：私钥别称
# keystore: 指定私钥库文件的名称(生成在当前目录)
# storepass：指定私钥库的密码(获取keystore信息所需的密码) 
# keypass：指定别名条目的密码(私钥的密码) 
# CN：姓名；OU：组织单位名称；O：组织名称；L：省/市/自治区名称；C：国家/地区代码
keytool -genkeypair -keysize 1024 -validity 3650 -alias "privateKey" -keystore "privateKeys.keystore" -storepass "a123456" -keypass "a123456" -dname "CN=FAST, OU=JavaSoft, O=XSX, L=WUHAN, ST=HUBEI, C=CN"

## 2. 把私匙库内的公匙导出到一个文件当中
# alias：私钥别称
# keystore：指定私钥库的名称(在当前目录查找)
# storepass: 指定私钥库的密码
# file：证书名称
keytool -exportcert -alias "privateKey" -keystore "privateKeys.keystore" -storepass "a123456" -file "certfile.cer"

## 3. 再把这个证书文件导入到公匙库
# alias：公钥别称
# file：证书名称
# keystore：公钥文件名称
# storepass：指定私钥库的密码
keytool -import -alias "publicCert" -file "certfile.cer" -keystore "publicCerts.keystore" -storepass "a123456"

————————————————
版权声明：本文为CSDN博主「tuacy」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/wuyuxing24/article/details/99692762

#### 注意
部分功能需要开启配置，代码生成了才可以使用
</a>

--------