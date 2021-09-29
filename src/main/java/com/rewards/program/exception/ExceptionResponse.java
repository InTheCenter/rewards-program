package com.rewards.program.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import com.rewards.program.model.SubErrors;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;


/**
 * Respuesta del servicio en caso de error.
 * 
 * @author Alexis Ganga
 * @version 1.0
 */
public class ExceptionResponse implements Serializable {

    /**
     * Identificador de serializacion de la clase.
     */
    private static final long serialVersionUID = -1600443760712594473L;

    /**
     * Codigo del error.
     */
    private int codigoError;

    /**
     * Mensaje asociado al error.
     */
    private String mensajeError;

    /**
     * Detalle del error.
     */
    private transient List<SubErrors> subErrors = new ArrayList<>();

    public int getCodigoError() {
        return codigoError;
    }

    public void setCodigoError(int codigoError) {
        this.codigoError = codigoError;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public List<SubErrors> getSubErrors() {
        return subErrors;
    }

    public void setValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(item -> subErrors
            .add(new SubErrors(item.getPropertyPath().toString(), item.getMessage(), item.getInvalidValue())));
    }

    public void setValidationErrors(Errors errors) {
        List<FieldError> fieldErrors = errors.getFieldErrors();
        fieldErrors
            .forEach(f -> subErrors.add(new SubErrors(f.getField(), f.getDefaultMessage(), f.getRejectedValue())));
    }
    
}
