package bricks.trading.adapters.inbound.api.controller;

import bricks.trading.application.dto.ApiResponseDto;
import bricks.trading.application.dto.CategoryResponseDto;
import bricks.trading.application.usecases.IBusinessService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static bricks.trading.application.constant.Messages.CATEGORY_GET_SUCCESSFUL;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @Mock
    private IBusinessService businessService;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    @DisplayName("findAll - Should return all categories successfully test")
    void testFindAll() {
        List<CategoryResponseDto> categoryList = List.of(
                new CategoryResponseDto(1, "TRA", "Travel", 1, "icon", "some description"),
                new CategoryResponseDto(2, "CAT", "Category", 2, "icon", "some description")
        );

        Mockito.when(businessService.getAllCategories()).thenReturn(categoryList);

        ResponseEntity<ApiResponseDto<List<CategoryResponseDto>>> responseEntity = categoryController.findAll();

        assertEquals(Boolean.TRUE, responseEntity.getBody().isOk());
        assertEquals(CATEGORY_GET_SUCCESSFUL, responseEntity.getBody().getMessage());
        assertEquals(categoryList, responseEntity.getBody().getBody());

        Mockito.verify(businessService, Mockito.times(1)).getAllCategories();
    }
}
