package org.abubusoft.foc.web;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.PathResourceResolver;

public class AngularResourceResolver extends PathResourceResolver {

    @Override
    protected Resource getResource(String resourcePath, Resource location) throws IOException {
        Resource requestedResource = location.createRelative(resourcePath);     
        
        //Resource defaultResource=new ClassPathResource("META-INF/resources/"+resourcePath);
        if (resourcePath.startsWith("public")) {
        	resourcePath="/public/index.html";
        } else {
        	resourcePath="/index.html";
        }
        Resource defaultResource=location.createRelative(resourcePath);        
        Resource resource = requestedResource.exists() && requestedResource.isReadable() ? requestedResource : defaultResource;
        return resource;
    }
}