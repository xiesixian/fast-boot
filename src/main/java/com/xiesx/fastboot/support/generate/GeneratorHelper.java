package com.xiesx.fastboot.support.generate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiesx.fastboot.support.generate.bean.GeneratorDTO;
import com.xiesx.fastboot.support.generate.entity.GTemplate;
import com.xiesx.fastboot.utils.ArrayUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @title GoGenerator.java
 * @description 利用mybatis里的生成插件代码生成
 * @author Sixian.xie
 * @date 2016年5月1日 下午15:14:10
 */
public class GeneratorHelper {

    public static class MyFreemarkerTemplateEngine extends AbstractTemplateEngine {

        private Configuration configuration;

        @Override
        public MyFreemarkerTemplateEngine init(ConfigBuilder configBuilder) {
            super.init(configBuilder);
            configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            configuration.setDefaultEncoding(ConstVal.UTF8);
            try {
                configuration.setDirectoryForTemplateLoading(new File(rootPath + File.separator + getTmplPath().toFile().getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        @Override
        public String templateFilePath(String filePath) {
            return filePath + ".ftl";
        }

        @Override
        public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
            Template template = configuration.getTemplate(templatePath);
            try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
                template.process(objectMap, new OutputStreamWriter(fileOutputStream, ConstVal.UTF8));
            }
            logger.debug("模板:" + templatePath + ";  文件:" + outputFile);
        }
    }

    public static final String FTL_CONTROLLER = "controller.java.ftl";

    public static final String FTL_SERVICE = "service.java.ftl";

    public static final String FTL_SERVICEIMPL = "serviceImpl.java.ftl";

    public static final String FTL_MAPPER = "mapper.java.ftl";

    public static final String FTL_XML = "xml.ftl";

    public static final String FTL_ENTITY = "entity.java.ftl";

    public static final String ON = "on";

    private static String userId;

    private static String rootPath, outputPath;

    public static Path getOutPath() {
        // 生成目录
        Path outputPath = Paths.get("generator/", Strings.padStart(userId, 5, '0'), "output");
        if (Files.notExists(outputPath)) {
            try {
                Files.createDirectories(outputPath);
            } catch (IOException e) {
                return null;
            }
        }
        return outputPath;
    }

    public static Path getTmplPath() {
        // 模板目录
        Path rootPath = Paths.get("generator/", Strings.padStart(userId, 5, '0'));
        if (Files.notExists(rootPath)) {
            try {
                Files.createDirectories(rootPath);
            } catch (IOException e) {
                return null;
            }
        }
        return rootPath;
    }

    private Path controller, service, serviceImpl, mapper, xml, entity;

    private GTemplate tmpl;

    private GeneratorDTO dto;

    public GeneratorHelper(String mUserId, GTemplate mGTemplate, GeneratorDTO mGeneratorDTO) throws IOException {
        super();
        userId = mUserId;
        this.tmpl = mGTemplate;
        this.dto = mGeneratorDTO;
        init();
    }

    private void init() throws IOException {
        // 根目录
        rootPath = System.getProperty("user.dir");
        // 输出目录
        outputPath = rootPath + File.separator + getOutPath().toFile().getPath() + File.separator;
        // 模板目录
        Path tempPath = getTmplPath();
        // 控制器
        if (ObjectUtils.isNotEmpty(tmpl.getController())) {
            controller = tempPath.resolve(GeneratorHelper.FTL_CONTROLLER);
            Files.write(controller, tmpl.getController().getBytes());
        }
        // 业务层
        if (ObjectUtils.isNotEmpty(tmpl.getService())) {
            service = tempPath.resolve(GeneratorHelper.FTL_SERVICE);
            Files.write(service, tmpl.getService().getBytes());
        }
        if (ObjectUtils.isNotEmpty(tmpl.getServiceImpl())) {
            serviceImpl = tempPath.resolve(GeneratorHelper.FTL_SERVICEIMPL);
            Files.write(serviceImpl, tmpl.getServiceImpl().getBytes());
        }
        // 持久层
        if (ObjectUtils.isNotEmpty(tmpl.getMapper())) {
            mapper = tempPath.resolve(GeneratorHelper.FTL_MAPPER);
            Files.write(mapper, tmpl.getMapper().getBytes());
        }
        if (ObjectUtils.isNotEmpty(tmpl.getXml())) {
            xml = tempPath.resolve(GeneratorHelper.FTL_XML);
            Files.write(xml, tmpl.getXml().getBytes());
        }
        // 实体类
        if (ObjectUtils.isNotEmpty(tmpl.getEntity())) {
            entity = tempPath.resolve(GeneratorHelper.FTL_ENTITY);
            Files.write(entity, tmpl.getEntity().getBytes());
        }
    }

    public void execute() {
        // 自定生成
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        mpg.setGlobalConfig(globalConfig());
        // 数据源配置
        mpg.setDataSource(dataSourceConfig());
        // 策略配置
        mpg.setStrategy(strategyConfig());
        // 模板配置
        mpg.setTemplate(templateConfig());
        // 自定义内容
        mpg.setCfg(customerConfig());
        // 模板引擎
        mpg.setTemplateEngine(new MyFreemarkerTemplateEngine());
        // 执行生成
        mpg.execute();
    }

    /**
     * 自定义生成
     */
    private InjectionConfig customerConfig() {
        // 这里是包路径
        String packagepath = outputPath + File.separator + dto.getPackagePath() + File.separator;
        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        InjectionConfig config = new InjectionConfig() {

            @Override
            public void initMap() {
                Map<String, Object> map = Maps.newConcurrentMap();
                map.put("author", this.getConfig().getGlobalConfig().getAuthor());
                this.setMap(map);
            }
        };
        List<FileOutConfig> files = Lists.newArrayList();
        if (Objects.equal(dto.getTplController(), ON)) {
            files.add(new FileOutConfig(FTL_CONTROLLER) {

                @Override
                public String outputFile(TableInfo tableInfo) {
                    return packagepath + tableInfo.getControllerName() + ".java";
                }
            });
        }
        if (Objects.equal(dto.getTplService(), ON)) {
            files.add(new FileOutConfig(FTL_SERVICE) {

                @Override
                public String outputFile(TableInfo tableInfo) {
                    return packagepath + tableInfo.getServiceName() + ".java";
                }
            });
        }
        if (Objects.equal(dto.getTplServiceImpl(), ON)) {
            files.add(new FileOutConfig(FTL_SERVICEIMPL) {

                @Override
                public String outputFile(TableInfo tableInfo) {
                    return packagepath + tableInfo.getServiceImplName() + ".java";
                }
            });
        }
        if (Objects.equal(dto.getTplMapper(), ON)) {
            files.add(new FileOutConfig(FTL_MAPPER) {

                @Override
                public String outputFile(TableInfo tableInfo) {
                    return packagepath + tableInfo.getMapperName() + ".java";
                }
            });
        }
        if (Objects.equal(dto.getTplXml(), ON)) {
            files.add(new FileOutConfig(FTL_XML) {

                @Override
                public String outputFile(TableInfo tableInfo) {
                    return packagepath + tableInfo.getXmlName() + ".xml";
                }
            });
        }
        if (Objects.equal(dto.getTplEntity(), ON)) {
            files.add(new FileOutConfig(FTL_ENTITY) {

                @Override
                public String outputFile(TableInfo tableInfo) {
                    return packagepath + tableInfo.getEntityName() + ".java";
                }
            });
        }
        config.setFileOutConfigList(files);
        return config;
    }

    /**
     * 数据源配置
     *
     * @param rb
     * @return
     */
    private DataSourceConfig dataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert());
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/gotv-admin?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&useSSL=false&serverTimezone=Asia/Shanghai");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        return dsc;
    }

    /**
     * 全局配置
     *
     * @param rb
     * @return
     * @throws IOException
     */
    private GlobalConfig globalConfig() {
        GlobalConfig gc = new GlobalConfig();
        // 生成目录
        gc.setOutputDir(outputPath);
        // 是否覆盖
        gc.setFileOverride(true);
        // 是否打开文件夹
        gc.setOpen(true);
        // 设置作者信息
        gc.setAuthor(dto.getAuthor());
        // 是否在xml中添加二级缓存配置
        gc.setEnableCache(Objects.equal(dto.getEnableCache(), ON));
        // 开启 Kotlin 模式
        gc.setKotlin(false);
        // 开启 swagger2 模式
        gc.setSwagger2(Objects.equal(dto.getSwagger2(), ON));
        // 开启 activeRecord 模式
        gc.setActiveRecord(Objects.equal(dto.getActiveRecord(), ON));
        // 开启 BaseResultMap
        gc.setBaseResultMap(Objects.equal(dto.getBaseResultMap(), ON));
        // 开启 BaseColumnList
        gc.setBaseColumnList(Objects.equal(dto.getBaseColumnList(), ON));
        // 时间类型使用Date
        gc.setDateType(DateType.ONLY_DATE);
        // 指定生成的主键的ID类型
        gc.setIdType(IdType.ASSIGN_UUID);
        return gc;
    }

    /**
     * 策略配置
     *
     * @param rb
     * @return
     */
    private StrategyConfig strategyConfig() {
        StrategyConfig strategy = new StrategyConfig();
        // 是否大写命名
        strategy.setCapitalMode(false);
        // 是否跳过视图
        strategy.setSkipView(false);
        // 表/字段名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 表/字段前缀
        strategy.setTablePrefix(ArrayUtils.listToArray(ArrayUtils.splitToList(dto.getTablePrefixs())));
        strategy.setFieldPrefix(ArrayUtils.listToArray(ArrayUtils.splitToList(dto.getFieldPrefixs())));
        // 自定义继承，带包名
        strategy.setSuperControllerClass(dto.getSuperControllerClass());
        strategy.setSuperServiceClass(dto.getSuperServiceClass());
        strategy.setSuperServiceImplClass(dto.getSuperServiceImplClass());
        strategy.setSuperMapperClass(dto.getSuperMapperClass());
        strategy.setSuperEntityClass(dto.getSuperEntityClass());
        // strategy.setSuperEntityColumns(ArrayUtils.listToArray(ArrayUtils.splitToList(dto.getSuperEntityColumns())));
        // 包含/排除表
        if (ObjectUtils.isNotEmpty(dto.getInclude())) {
            strategy.setLikeTable(new LikeTable(dto.getInclude()));
        } else {
            strategy.setNotLikeTable(new LikeTable(dto.getExclude()));
        }
        // 实体
        // 实体是否生成 serialVersionUID
        strategy.setEntitySerialVersionUID(true);
        // 实体是否生成字段常量
        strategy.setEntityColumnConstant(Objects.equal(dto.getEntityColumnConstant(), ON));
        // 实体是否生成字段注解
        strategy.setEntityTableFieldAnnotationEnable(Objects.equal(dto.getEntityTableFieldAnnotationEnable(), ON));
        // 实体是否使用Builder模型
        strategy.setEntityBuilderModel(Objects.equal(dto.getEntityBuilderModel(), ON));
        // 实体是否使用Lombok模型
        strategy.setEntityLombokModel(Objects.equal(dto.getEntityLombokModel(), ON));
        // 实体是否移除is前缀
        strategy.setEntityBooleanColumnRemoveIsPrefix(Objects.equal(dto.getEntityBooleanColumnRemoveIsPrefix(), ON));
        // 乐观锁属性名称
        strategy.setVersionFieldName(dto.getVersionFieldName());
        // 逻辑删除属性名称
        strategy.setLogicDeleteFieldName(dto.getLogicDeleteFieldName());
        // 启用sql过滤
        // strategy.setEnableSqlFilter(false);
        // 控制器
        // 控制器RestStyle风格
        strategy.setRestControllerStyle(Objects.equal(dto.getRestControllerStyle(), ON));
        // 控制器驼峰转连字符
        strategy.setControllerMappingHyphenStyle(false);
        return strategy;
    }

    /**
     * 生成配置
     */
    private TemplateConfig templateConfig() {
        TemplateConfig tc = new TemplateConfig();
        tc.setController(Objects.equal(dto.getTplController(), ON) ? "" : null);
        tc.setService(Objects.equal(dto.getTplService(), ON) ? "" : null);
        tc.setServiceImpl(Objects.equal(dto.getTplServiceImpl(), ON) ? "" : null);
        tc.setMapper(Objects.equal(dto.getTplMapper(), ON) ? "" : null);
        tc.setXml(Objects.equal(dto.getTplXml(), ON) ? "" : null);
        tc.setEntity(Objects.equal(dto.getTplEntity(), ON) ? "" : null);
        return tc;
    }
}
