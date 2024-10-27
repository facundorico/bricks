package bricks.trading.adapters.inbound.api.controller;

import bricks.trading.adapters.inbound.api.utils.Paths;
import bricks.trading.application.dto.ApiResponseDto;
import bricks.trading.application.dto.CategoryResponseDto;
import bricks.trading.application.usecases.IBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static bricks.trading.application.constant.Messages.CATEGORY_GET_SUCCESSFUL;

@RestController
@RequestMapping(Paths.CATEGORY_ENDPOINT)
@RequiredArgsConstructor
public class CategoryController {

    private final IBusinessService bussinesService;

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<CategoryResponseDto>>> findAll() {
        List<CategoryResponseDto> responseDtoList = bussinesService.getAllCategories();
        ApiResponseDto<List<CategoryResponseDto>> response = ApiResponseDto.<List<CategoryResponseDto>>builder()
                .ok(Boolean.TRUE)
                .message(CATEGORY_GET_SUCCESSFUL)
                .body(responseDtoList)
                .build();
        return ResponseEntity.ok(response);
    }

}
