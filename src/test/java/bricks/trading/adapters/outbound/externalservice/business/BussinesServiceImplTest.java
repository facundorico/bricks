package bricks.trading.adapters.outbound.externalservice.business;

import bricks.trading.adapters.exception.ExternalServiceException;
import bricks.trading.adapters.outbound.externalservice.business.client.IBusinessClient;
import bricks.trading.application.dto.CategoryResponseDto;
import bricks.trading.application.logging.ILoggerUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class BussinesServiceImplTest {

    @Mock
    private IBusinessClient businessClient;

    @Mock
    private ILoggerUtil logger;

    @InjectMocks
    private BusinessServiceImpl businessServiceImpl;

    private static final String BUSINESS_SERVICE = "BusinessServiceImpl";

    @Test
    @DisplayName("testGetAllCategoriesSuccess - Should return a list of categories successfully test")
    void testGetAllCategoriesSuccess() {
        List<CategoryResponseDto> categories = List.of(
                new CategoryResponseDto(1, "TRA", "Travel", 1, "icon", "some description"),
                new CategoryResponseDto(2, "CAT", "Category", 2, "icon", "some description")
        );

        Mockito.when(businessClient.getAllCategories()).thenReturn(categories);

        List<CategoryResponseDto> result = businessServiceImpl.getAllCategories();

        assertEquals(categories, result);
        Mockito.verify(logger).info(BUSINESS_SERVICE, "getAllCategories");
        Mockito.verify(businessClient, Mockito.times(1)).getAllCategories();
    }

    @Test
    @DisplayName("testGetAllCategoriesThrowsExternalServiceException - When exception occurs then throw a new ExternalServiceException test")
    void testGetAllCategoriesThrowsExternalServiceException() {
        String errorMessage = "Error connecting to external service";
        Mockito.when(businessClient.getAllCategories()).thenThrow(new ExternalServiceException(errorMessage));

        ExternalServiceException exception = assertThrows(ExternalServiceException.class, () -> {
            businessServiceImpl.getAllCategories();
        });

        assertEquals(errorMessage, exception.getMessage());
        Mockito.verify(logger).info(BUSINESS_SERVICE, "getAllCategories");
        Mockito.verify(logger).error("ExternalServiceException: " + errorMessage, BUSINESS_SERVICE + "getAllCategories");
    }

}
