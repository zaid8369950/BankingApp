package com.banking.app.swagger;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	private ApiKey apiKey() {
		return new ApiKey("Authorization-key", "Authorization", "header");
	}

	@Bean
	public Docket api() {

		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = new AuthorizationScopeBuilder().scope("global").description("full access").build();
		SecurityReference reference = SecurityReference.builder().reference("Authorization-key")
				.scopes(authorizationScopes).build();
		ArrayList<SecurityContext> securityContexts = Lists
				.newArrayList(SecurityContext.builder().securityReferences(Lists.newArrayList(reference)).build());
		return new Docket(DocumentationType.SWAGGER_2).securityContexts(securityContexts)
				.securitySchemes(Arrays.asList(apiKey())).select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
				.paths(PathSelectors.ant("/api/v1/**")).build().apiInfo(apiInfo());
	}
    
	private ApiInfo apiInfo() {
		return new ApiInfo("HMS", "Endpoints for change in HMS service.", "1.0", "Terms of service",
				new Contact("HMS", "https://www.mhs.com", "info@hms.com"), "License of API", "API license URL",Collections.emptyList());
		//http://localhost:8080/swagger-ui.html#/
	
	}

}