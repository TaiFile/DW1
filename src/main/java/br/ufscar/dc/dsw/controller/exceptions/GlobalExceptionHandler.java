package br.ufscar.dc.dsw.controller.exceptions;

import br.ufscar.dc.dsw.exceptions.BadRequestException;
import br.ufscar.dc.dsw.exceptions.ResourceNotFoundException;
import br.ufscar.dc.dsw.exceptions.StorageException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 * ControllerAdvice responsável por tratar exceções globais da aplicação, lançadas explícitamente no código.
 * Redireciona exceções personalizadas para uma página de erro amigável.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // 4xx
    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("status", HttpStatus.NOT_FOUND.value());
        model.addObject("error", "exception.not_found");
        model.addObject("message", e.getMessage());
        model.addObject("path", request.getRequestURI());
        return model;
    }


    @ExceptionHandler(BadRequestException.class)
    public ModelAndView handleBadRequestException(BadRequestException e, HttpServletRequest request) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("status", HttpStatus.BAD_REQUEST.value());
        model.addObject("error", "exception.bad_request");
        model.addObject("message", e.getMessage());
        model.addObject("path", request.getRequestURI());
        return model;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxUploadSizeExceeded(MaxUploadSizeExceededException e,
                                                    HttpServletRequest request,
                                                    RedirectAttributes redirectAttributes) {
        String redirectUrl = "/store/home";
        redirectAttributes.addFlashAttribute("fail",
                "Upload falhou: arquivos muito grandes. Limite máximo: 10MB.");

        return new ModelAndView(new RedirectView(redirectUrl, true));
    }

    // 5xx
    @ExceptionHandler(StorageException.class)
    public ModelAndView handleStorageException(StorageException e, HttpServletRequest request) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addObject("error", "exception.storage");
        model.addObject("message", e.getMessage());
        model.addObject("path", request.getRequestURI());
        return model;
    }

//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleGenericException(Exception e, HttpServletRequest request) {
//        ModelAndView model = new ModelAndView("error");
//        model.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
//        model.addObject("error", "exception.internal_server_error");
//        model.addObject("message", "Ocorreu um erro inesperado no servidor");
//        model.addObject("path", request.getRequestURI());
//        return model;
//    }
}
