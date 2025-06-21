package br.ufscar.dc.dsw.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import br.ufscar.dc.dsw.conversor.BigDecimalConversor;

@Configuration
@ComponentScan(basePackages = "br.ufscar.dc.dsw.config")
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/home");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/client/register").setViewName("client/register");
        registry.addViewController("/client/home").setViewName("client/home");
        registry.addViewController("/client/offer").setViewName("viewOfferClient");
        registry.addViewController("/client/status/offer").setViewName("statusOfferClient");
        registry.addViewController("/store/register").setViewName("store/register");
        registry.addViewController("/store/home").setViewName("store/home");
        registry.addViewController("/store/offer").setViewName("store/offerList");
        registry.addViewController("/vehicle/register").setViewName("vehicle/register");
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
}