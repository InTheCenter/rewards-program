package com.rewards.program.util;

/**
 * Enum that contains status codes to be sended as service response
 *
 * @author Alexis Ganga
 * @version 1.0
 */
public enum ServiceStatus {

	SUCCESS(0, "OK"), 
	BAD_REQUEST(400, "Bad Request"),
	BAD_REQUEST_INVALID_BODY_FIELD(4, "Invalid body field"),
	BAD_REQUEST_INVALID_URL_PARAMETER(400, "Invalid URL parameter value"),
	NOT_FOUND(404, "Recurso no encontrado: %s"),
	METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
	
	EXCEEDS_MAX_LEN(452, "Exceeds Maximum Length"), 
	DATA_BASE_ERROR(455, "Database Error"),
	DATA_BASE_ERROR_CONNECTION(455, "Database Error. No se pudo procesar la petición."),
	INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    /**
     * Codigo del estado de la operacion.
     */
    private final int codigo;

    /**
     * Descripcion del estado de la operacion.
     */
    private final String descripcion;

    private ServiceStatus(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
