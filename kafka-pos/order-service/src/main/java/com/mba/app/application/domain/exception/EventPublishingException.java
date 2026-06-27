package com.mba.app.application.domain.exception;

/**
 * Lançada quando a publicação de um evento de domínio falha.
 * Permite que a camada de aplicação reaja a problemas de integração
 * sem acoplar-se a exceções específicas do Kafka.
 */
public class EventPublishingException extends RuntimeException {

    public EventPublishingException(String message, Throwable cause) {
        super(message, cause);
    }
}
