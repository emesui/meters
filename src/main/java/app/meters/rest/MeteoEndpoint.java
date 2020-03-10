package app.meters.rest;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequiredArgsConstructor
class MeteoEndpoint {

    final MeterRegistry meterRegistry;

    @PostMapping(path = "/temperature", consumes = "application/json")
    ResponseEntity reportTemperatureMeasurement(@RequestBody TemperatureMeasurement request) {

        request.temp.forEach((measurePoint, measure) ->
            DistributionSummary.builder("t7")
                .publishPercentileHistogram()
                .register(meterRegistry)
                .record(measure)
        );

        return ResponseEntity.accepted().build();
    }
}

@Data
class TemperatureMeasurement {
    Map<String, Double> temp;
}
