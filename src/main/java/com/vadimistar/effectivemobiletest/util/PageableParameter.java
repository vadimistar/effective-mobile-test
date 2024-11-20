package com.vadimistar.effectivemobiletest.util;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Parameters({
        @Parameter(name = "page", description = "Индекс страницы (0..N)",
                in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
        @Parameter(name = "size", description = "Размер страницы",
                in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
        @Parameter(name = "sort", description = "Сортировка в формате: поле,(asc|desc). Стандартная сортировка - по " +
                "возрастанию. Есть поддержка сортировки по нескольким полям",
                in = ParameterIn.QUERY, schema = @Schema(type = "string"))
})
public @interface PageableParameter {
}
