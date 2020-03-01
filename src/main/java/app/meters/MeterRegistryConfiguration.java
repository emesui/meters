package app.meters;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.jmx.JmxConfig;
import io.micrometer.jmx.JmxMeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MeterRegistryConfiguration {

    @Bean
    MeterRegistry meterRegistry() {
        MeterRegistry registry = new JmxMeterRegistry(
            JmxConfig.DEFAULT,
            Clock.SYSTEM
        );
        return registry;
    }

}
