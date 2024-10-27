package bricks.trading.adapters.inbound.api.advice;

import bricks.trading.adapters.exception.CategoryNotFoundException;
import bricks.trading.adapters.exception.RecordNotFoundException;
import bricks.trading.application.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleRecordNotFoundException(RecordNotFoundException ex){
        ApiResponseDto<Void> responseDto = ApiResponseDto.<Void>builder().ok(false).message(ex.getMessage()).build();
        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleCategoryNotFoundException(CategoryNotFoundException ex){
        ApiResponseDto<Void> responseDto = ApiResponseDto.<Void>builder().ok(false).message(ex.getMessage()).build();
        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }

}
