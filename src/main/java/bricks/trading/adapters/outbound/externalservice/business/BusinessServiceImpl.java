package bricks.trading.adapters.outbound.externalservice.business;

import bricks.trading.adapters.exception.ExternalServiceException;
import bricks.trading.adapters.outbound.externalservice.business.client.IBusinessClient;
import bricks.trading.application.dto.CategoryResponseDto;
import bricks.trading.application.logging.ILoggerUtil;
import bricks.trading.application.usecases.IBusinessService;
import bricks.trading.config.CacheConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements IBusinessService {

    private final IBusinessClient businessClient;
    private final ILoggerUtil logger;
    private static final String BUSINESS_SERVICE = "BusinessServiceImpl";

    @Override
    @Cacheable(value = CacheConfig.CATEGORY_CACHE, unless = "#result == null")
    public List<CategoryResponseDto> getAllCategories() {
        logger.info(BUSINESS_SERVICE, "getAllCategories");
        try {
            return businessClient.getAllCategories();
        } catch (ExternalServiceException error){
            logger.error("ExternalServiceException: "+error.getMessage(), BUSINESS_SERVICE+"getAllCategories");
            throw new ExternalServiceException(error.getMessage());
        }
    }
}
