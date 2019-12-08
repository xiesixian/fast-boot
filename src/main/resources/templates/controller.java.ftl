package ${package.Controller};

import lombok.extern.slf4j.Slf4j;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import com.alibaba.fastjson.JSONObject;
import com.huoli.gtgjx.act10.controller.simple.bo.SimpleResult;
import com.huoli.gtgjx.act10.controller.simple.vo.SimpleVO;
import com.huoli.gtgjx.com.controller.model.BaseGateWayModel;
import com.huoli.gtgjx.com.controller.model.BaseResult;

 /**
 * @title ${table.controllerName}.java
 * @description ${table.comment} 控制器
 * @author ${author}
 * @date ${date}
 */
@Slf4j
@Validated
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
	 
	@RequestMapping(value = "/str")
	public BaseResult<String> str(@NotEmpty(message = "{act.empty}") String data) {
		return BaseResult.success0(data);
	}
	
	@RequestMapping(value = "/obj")
	public BaseResult<SimpleResult> obj(BaseGateWayModel base, @Validated SimpleVO param) {
		log.info(JSONObject.toJSONString(param));
		SimpleResult tr = new SimpleResult();
		tr.setStr(base.getPhoneid() + "--" + param.getId() + "--" + param.getName());
		return BaseResult.success0(tr);
	}
	
	@RequestMapping(value = "/json", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String json(@RequestBody JSONObject param) {
		JSONObject result = new JSONObject();
		result.put("msg", "ok");
		result.put("method", "json");
		result.put("data", param);
		return result.toJSONString();
	}
}
</#if>
