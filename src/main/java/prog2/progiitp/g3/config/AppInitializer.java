package prog2.progiitp.g3.config;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import javax.servlet.FilterRegistration;
import org.springframework.web.filter.DelegatingFilterProxy;

public class AppInitializer implements WebApplicationInitializer {
 @Override
public void onStartup(ServletContext servletContext) throws ServletException {
    AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class, PersistenceJPAConfig.class,SecurityConfig.class);
        context.setServletContext(servletContext);
        
        FilterRegistration.Dynamic securityFilter = servletContext.addFilter(
                "springSecurityFilterChain", 
                new DelegatingFilterProxy("springSecurityFilterChain")
        );
        securityFilter.addMappingForUrlPatterns(null, false, "/*");
        
        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
    }
}
