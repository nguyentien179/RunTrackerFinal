package dev.tien.runnerz.run;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record Run(
        @NotEmpty
        Integer id,
        @NotEmpty
        String title,
        LocalDateTime startOn,
        LocalDateTime endOn,
        @Positive
        Integer kilometers,
        Location location
) {
    public Run {
        if(!endOn.isAfter(startOn)){
            throw new IllegalArgumentException("End on must be after Start On");
        }
    }
}
