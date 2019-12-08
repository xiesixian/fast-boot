package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};

 /**
 * @title ${table.mapperName}.java
 * @description ${table.comment} 持久类
 * @author ${author}
 * @date ${date}
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}
</#if>
