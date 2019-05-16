package com.yy.lieyin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

//注解表示这个是一个配置文件，让spring来加载该类配置
//注解表示启用Swagger2
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    //注解表示交由bean容器去管理
    @Bean
    public Docket newsApi() {

        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        ticketPar.name("Authorization").description("user token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的Authorization参数非必填，传空也可以
        pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数

        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.enable(true);
        //apiInfo()用来创建该Api的基本信息（这些基本信息会展现在文档页面中）。
        //select()函数返回一个ApiSelectorBuilder实例用来控制哪些接口暴露给Swagger来展现，本例采用指定扫描的包路径来定义，Swagger会扫描该包下所有Controller定义的API，并产生文档内容（除了被@ApiIgnore指定的请求）。
        docket.apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yy.lieyin.web"))
                .paths(PathSelectors.any()).build().globalOperationParameters(pars);
        return docket;
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("yy", "http://yylovell.com.cn", null);
        return new ApiInfoBuilder().title("猎鹰项目").description("在这里你可以浏览项目所有接口，并提供相关测试工具")
                .contact(contact)
                .version("0.0.1").build();
    }

}
