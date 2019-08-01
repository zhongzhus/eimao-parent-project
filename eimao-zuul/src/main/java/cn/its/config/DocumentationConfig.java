package cn.its.config;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class DocumentationConfig implements SwaggerResourcesProvider {

    @Override
    public List<SwaggerResource> get() {
        List res = new ArrayList();
//        product,user等都是网关中配置路径,而且如果有前缀,需要加上
        res.add(swaggerResource("商品系统", "/service/product/v2/api-docs", "2.0"));
        res.add(swaggerResource("登陆信息", "/service/user/v2/api-docs", "2.0"));
        res.add(swaggerResource("平台信息", "/service/user/v2/api-docs", "2.0"));
        return res;
    }
    private SwaggerResource swaggerResource(String name,String location ,String version){
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
