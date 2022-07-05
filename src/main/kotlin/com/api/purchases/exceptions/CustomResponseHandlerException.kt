package com.api.purchases.exceptions

import com.api.purchases.enuns.CodeError
import feign.FeignException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.sql.Timestamp

import java.time.Instant

@ControllerAdvice
class CustomResponseHandlerException: ResponseEntityExceptionHandler() {

    @ExceptionHandler(EntityException::class)
    fun entityExceptionHandler(ex: EntityException): ResponseEntity<ResponseErro>{
        var error: ResponseErro =  ResponseErro(
            Timestamp.from(Instant.now()),
            "Erro na requisicao",
            ex.codeError.name,
            ex.codeError.value,
            ex.localizedMessage
        )
        return ResponseEntity(error,
            HttpHeaders(),
            HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun illegalArgumentExceptionHandler(ex: MethodArgumentTypeMismatchException): ResponseEntity<ResponseErro>{
        var error: ResponseErro =  ResponseErro(
            Timestamp.from(Instant.now()),
            "Erro na requisicao",
            CodeError.PARAM_INVALID.name,
            CodeError.PARAM_INVALID.value,
            ex.localizedMessage
        )
        return ResponseEntity(error,
            HttpHeaders(),
            HttpStatus.BAD_REQUEST)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        var error: ResponseErro =  ResponseErro(
            Timestamp.from(Instant.now()),
            "Formato invalido",
            CodeError.FORMAT_INVALID.name,
            CodeError.FORMAT_INVALID.value,
            ex.localizedMessage
        )
        return ResponseEntity.badRequest().body(error)
    }

    @ExceptionHandler(FeignException::class)
    fun feingnExecption(ex: FeignException): ResponseEntity<ResponseErro> {
        var error: ResponseErro =  ResponseErro(
            Timestamp.from(Instant.now()),
            "Erro interno",
            CodeError.REST_ERROR.name,
            CodeError.REST_ERROR.value,
            ex.localizedMessage
        )
        return ResponseEntity.badRequest().body(error)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        var error: ResponseErro = ResponseErro(
            Timestamp.from(Instant.now()),
            "Formato invalido",
            CodeError.FORMAT_INVALID.name,
            CodeError.FORMAT_INVALID.value,
            ex.localizedMessage
        )
        return ResponseEntity.badRequest().body(error)
    }

}