package bricks.trading.adapters.outbound.externalservice.business;

import bricks.trading.adapters.exception.ExternalServiceException;
import bricks.trading.adapters.outbound.externalservice.business.client.IBusinessClient;
import bricks.trading.application.dto.CategoryResponseDto;
import bricks.trading.application.usecases.IBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements IBusinessService {

    private final IBusinessClient businessClient;

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        try {
            List<CategoryResponseDto> categoriesList = businessClient.getAllCategories();
            return categoriesList;
        } catch (ExternalServiceException error){
            throw new ExternalServiceException(error.getMessage());
        }
    }
}
