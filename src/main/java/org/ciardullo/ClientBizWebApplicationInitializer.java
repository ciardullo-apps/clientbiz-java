package org.ciardullo;

import org.ciardullo.config.AppConfig;
import org.ciardullo.config.CustomRequestLoggingFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class ClientBizWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletCxt) {
        // Load Spring web application configuration
        AnnotationConfigWebApplicationContext applicationContext =
                new AnnotationConfigWebApplicationContext();

        // Need to set servlet context to avoid IllegalStateException No ServletContext Set
        // when calling AnnotationConfigWebApplicationContext.refresh()
        applicationContext.setServletContext(servletCxt);

        applicationContext.register(AppConfig.class);
        applicationContext.refresh();

        // Create and register the DispatcherServlet
        DispatcherServlet servlet = new DispatcherServlet(applicationContext);
        ServletRegistration.Dynamic registration = servletCxt.addServlet("app", servlet);
        registration.setLoadOnStartup(1);
//        registration.addMapping("/app/*");
        registration.addMapping("/");

        // http://www.baeldung.com/spring-http-logging
        servletCxt.addFilter("customRequestLoggingFilter",
                CustomRequestLoggingFilter.class)
                .addMappingForServletNames(null, false, "app");
    }

}