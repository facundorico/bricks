package bricks.trading.adapters.outbound.externalservice.business.client;

import bricks.trading.application.dto.CategoryResponseDto;

import java.util.List;

public interface IBusinessClient {

    List<CategoryResponseDto> getAllCategories();
}
