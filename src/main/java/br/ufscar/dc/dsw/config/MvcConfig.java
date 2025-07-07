package br.ufscar.dc.dsw.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import br.ufscar.dc.dsw.conversor.BigDecimalConversor;

@Configuration
@ComponentScan(basePackages = "br.ufscar.dc.dsw")
public class MvcConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String fileUploadDir;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/home");
        registry.addViewController("/login").setViewName("login");
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("pt", "BR"));
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new BigDecimalConversor());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadPath = Paths.get(fileUploadDir).toAbsolutePath().normalize();
        String physicalUploadDirLocation = uploadPath.toString();
        if (!physicalUploadDirLocation.endsWith("/") && !physicalUploadDirLocation.endsWith("\\")) {
            physicalUploadDirLocation += "/";
        }


        String os = System.getProperty("os.name").toLowerCase();
        String fileResourcePrefix = os.contains("windows") ? "file:///" : "file:";

        registry.addResourceHandler("/" + fileUploadDir + "/**")
                .addResourceLocations(fileResourcePrefix + physicalUploadDirLocation)
                .setCacheControl(CacheControl.maxAge(Duration.ofDays(30)));

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/", "classpath:/public/", "classpath:/resources/", "classpath:/META-INF/resources/")
                .setCacheControl(CacheControl.maxAge(Duration.ofDays(30)));
    }
}