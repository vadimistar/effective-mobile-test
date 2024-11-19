package com.vadimistar.effectivemobiletest.config;

import com.vadimistar.effectivemobiletest.dto.ErrorDto;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        ResolvedSchema errorSchema = ModelConverters.getInstance().resolveAsResolvedSchema(
                new AnnotatedType(ErrorDto.class)
        );

        Content errorContent = new Content().addMediaType(
                "application/json",
                new MediaType().schema(errorSchema.schema)
        );

        return openApi -> {
            for (PathItem path : openApi.getPaths().values()) {
                for (Operation operation : path.readOperations()) {
                    if (operation.getSecurity() == null) {
                        continue;
                    }

                    addErrorResponses(operation.getResponses(), errorContent);
                }
            }
        };
    }

    private void addErrorResponses(ApiResponses apiResponses, Content errorContent) {
        apiResponses.addApiResponse("400", new ApiResponse()
                .description("Неверный запрос")
                .content(errorContent));
        apiResponses.addApiResponse("401", new ApiResponse()
                .description("Пользователь не авторизован, или неверный токен")
                .content(new Content()));
        apiResponses.addApiResponse("405", new ApiResponse()
                .description("Внутреняя ошибка сервера")
                .content(errorContent));
    }
}
