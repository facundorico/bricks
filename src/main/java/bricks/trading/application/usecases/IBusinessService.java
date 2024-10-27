package bricks.trading.application.usecases;

import bricks.trading.application.dto.CategoryResponseDto;

import java.util.List;

public interface IBusinessService {

    List<CategoryResponseDto> getAllCategories();
}
