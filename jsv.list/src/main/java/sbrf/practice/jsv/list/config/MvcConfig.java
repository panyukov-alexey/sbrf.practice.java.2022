package sbrf.practice.jsv.list.config;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;
 
@Configuration
public class MvcConfig implements WebMvcConfigurer {
 
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
     
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/logout").setViewName("logout");
         
    }
}