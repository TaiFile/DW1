package br.ufscar.dc.dsw.controller.exceptions;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller de visualização de erro personalizado que trata respostas de erro HTTP na aplicação.
 * Este componente é usado pelo Spring Security e Spring Web, por baixo dos panos, para fornecer páginas de erro personalizadas
 * para diferentes códigos de status HTTP (403, 404, e outros).
 */
@Component
public class ErrorViewController implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> map) {

        ModelAndView model = new ModelAndView("error");
        model.addObject("status", status.value());
        switch (status.value()) {
            case 403:
                model.addObject("error", "403.error");
                model.addObject("message", "403.message");
                break;
            case 404:
                model.addObject("error", "404.error");
                model.addObject("message", "404.message");
                break;
            default:
                model.addObject("error", "default.error");
                model.addObject("message", "default.message");
                break;
        }
        return model;
    }
}