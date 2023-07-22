package com.pino.restapi;

import com.pino.exception.BadRequestException;
import com.pino.exception.InternalServerErrorException;
import com.pino.exception.ShortUrlNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@ResponseBody
@Slf4j
@SuppressWarnings({ "unused" })
public class GlobalExceptionHandler {

    /**
     * status code 400
     */
    @ExceptionHandler({
        BadRequestException.class,
        ShortUrlNotFoundException.class
    })
    public ResponseEntity<String> handleBadRequestException(
        HttpServletRequest request,
        Exception e,
        HandlerMethod handlerMethod) {
        String errMsg;
        if (e instanceof ShortUrlNotFoundException) {
            errMsg = "連結已過期或不存在!";
        } else {
            errMsg = String.format("%s", e.getMessage());
            log.error(errMsg);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(errMsg);
    }

    /**
     * status code 500
     */
    @ExceptionHandler({ InternalServerErrorException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> internalServerErrorExceptionHandler(
        InternalServerErrorException e
    ) {
        log.error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("系統發生錯誤，請稍後再試 \uD83D\uDE2D\uD83D\uDE2D");
    }

}