package co.diegofer.inventoryclean.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.diegofer.inventoryclean.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+Repository$"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+Updater$"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+Bus$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {
}
