package bricks.trading.adapters.outbound.externalservice.business.client;

import bricks.trading.adapters.exception.ExternalServiceException;
import bricks.trading.application.constant.Messages;
import bricks.trading.application.dto.CategoryResponseDto;
import bricks.trading.application.logging.ILoggerUtil;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class BusinessClientImpl implements IBusinessClient{

    private final RestClient restClient;
    private static final String CATEGORY_URL = "/business/category";
    private final ILoggerUtil logger;
    private static final String BUSINESS_CLIENT = "BusinessClientImpl - ";

    public BusinessClientImpl(RestClient restClient, ILoggerUtil logger) {
        this.restClient = restClient;
        this.logger = logger;
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        logger.info(BUSINESS_CLIENT, "getAllCategories");
        ResponseEntity<CategoryResponseDto[]> response = restClient.get()
                .uri(uriBuilder -> uriBuilder.path(CATEGORY_URL)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> handleError(res))
                .toEntity(CategoryResponseDto[].class);

        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    private void handleError(ClientHttpResponse response) {
        String errorMessage = Messages.CATEGORY_GET_ERROR + response.toString();
        logger.error(errorMessage, BUSINESS_CLIENT);
        throw new ExternalServiceException(errorMessage);
    }
}
