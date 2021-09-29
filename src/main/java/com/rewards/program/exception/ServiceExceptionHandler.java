package com.rewards.program.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.rewards.program.model.SubErrors;
import com.rewards.program.util.ServiceStatus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



/**
 * Clase que maneja las excepciones lazadas por la aplicacion.
 *
 * @author Alexis Ganga
 * @version 1.0
 */
@RestControllerAdvice
public class ServiceExceptionHandler {

    /**
     * Log de la clase.
     */
    private static Logger log = LogManager.getLogger(ServiceExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setCodigoError(ServiceStatus.INTERNAL_SERVER_ERROR.getCodigo());
        response.setMensajeError(ServiceStatus.INTERNAL_SERVER_ERROR.getDescripcion());
        log.error("[Exception] Error procesando solicitud: ", ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> constraintViolation(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        ExceptionResponse response = new ExceptionResponse();
        response.setCodigoError(ServiceStatus.BAD_REQUEST_INVALID_URL_PARAMETER.getCodigo());
        response.setMensajeError(ServiceStatus.BAD_REQUEST_INVALID_URL_PARAMETER.getDescripcion());
        response.setValidationErrors(constraintViolations);
        log.error("[ConstraintViolationException] Error procesando solicitud: ", ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> messageNotReadable(HttpMessageNotReadableException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setCodigoError(ServiceStatus.BAD_REQUEST.getCodigo());
        response.setMensajeError(ServiceStatus.BAD_REQUEST.getDescripcion());
        log.error("[HttpMessageNotReadableException] Error procesando solicitud: ", ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> requestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setCodigoError(ServiceStatus.METHOD_NOT_ALLOWED.getCodigo());
        response.setMensajeError(ServiceStatus.METHOD_NOT_ALLOWED.getDescripcion());
        log.error("[HttpRequestMethodNotSupportedException] Error procesando solicitud: ", ex);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }
    
    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> mediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setCodigoError(ServiceStatus.UNSUPPORTED_MEDIA_TYPE.getCodigo());
        response.setMensajeError(ServiceStatus.UNSUPPORTED_MEDIA_TYPE.getDescripcion());
        log.error("[HttpMediaTypeNotSupportedException] Error procesando solicitud: ", ex);
        return new ResponseEntity<>(response, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
    
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ExceptionResponse> notFoundException(NotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCodigoError(ServiceStatus.NOT_FOUND.getCodigo());
        exceptionResponse.setMensajeError(ServiceStatus.NOT_FOUND.getDescripcion());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> invalidInput(MethodArgumentNotValidException ex) {
    	log.error("[MethodArgumentNotValidException] Error procesando solicitud: ", ex);
        BindingResult result = ex.getBindingResult();
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCodigoError(ServiceStatus.BAD_REQUEST.getCodigo());
        exceptionResponse.setMensajeError(ServiceStatus.BAD_REQUEST.getDescripcion());
        exceptionResponse.setValidationErrors(result);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(BusinessException.class)
    public final ResponseEntity<ExceptionResponse> handleNegocioException(BusinessException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setCodigoError(ex.getHttpStatus().value());
        exceptionResponse.setMensajeError(ex.getMensajeError());
        return new ResponseEntity<>(exceptionResponse, ex.getHttpStatus());
    }
    
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionResponse> missingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.error("[MissingServletRequestParameterException] Error procesando solicitud: ", ex);
        ExceptionResponse response = new ExceptionResponse();
        response.setCodigoError(ServiceStatus.BAD_REQUEST_INVALID_URL_PARAMETER.getCodigo());
        response.setMensajeError(ServiceStatus.BAD_REQUEST_INVALID_URL_PARAMETER.getDescripcion());
        SubErrors detalleError = new SubErrors(ex.getParameterName(), ex.getMessage(), null);
        response.getSubErrors().add(detalleError);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
  
}
