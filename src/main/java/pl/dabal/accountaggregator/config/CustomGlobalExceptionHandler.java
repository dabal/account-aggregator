package pl.dabal.accountaggregator.config;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Let Spring BasicErrorController handle the exception, we just override the status code
    @ExceptionHandler(ResourceNotFoundException.class)
    public void springHandleNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }


    // @Validate For Validating Path Variables and Request Parameters
    @ExceptionHandler(ConstraintViolationException.class)
    public void constraintViolationException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    // error handle for @Valid
    @Override
    protected ResponseEntity<Object>
    handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                 HttpHeaders headers,
                                 HttpStatus status, WebRequest request) {

Map<String, List<String>> fieldErrors= new HashMap<>();

for(FieldError fieldError:ex.getBindingResult().getFieldErrors()){
    if(fieldErrors.containsKey(fieldError.getField())){
        List<String> currenList= (List<String>)fieldErrors.get(fieldError.getField());
        currenList.add(fieldError.getDefaultMessage());
        fieldErrors.replace(fieldError.getField(),currenList);
    }
    else{
        List<String> currentList=new ArrayList<>();
        currentList.add(fieldError.getDefaultMessage());
        fieldErrors.put(fieldError.getField(), currentList);
    }
}

         return new ResponseEntity(fieldErrors, headers, status);
        //tu było new ResponseEntity<>(body, headers, status); - trz
    }

}