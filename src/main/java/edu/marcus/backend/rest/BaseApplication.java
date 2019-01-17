package edu.marcus.backend.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.v3.jaxrs2.integration.resources.AcceptHeaderOpenApiResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;

@ApplicationPath("api")
public class BaseApplication extends Application {
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new HashSet();
		resources.add(DescriptionREST.class);
		resources.add(OpenApiResource.class);
		resources.add(AcceptHeaderOpenApiResource.class);
		return resources;
	}

}
