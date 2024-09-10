package ggudock.config.component;

import ggudock.domain.subscription.model.ScheduleState;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToScheduleStateConverter implements Converter<String, ScheduleState> {
    @Override
    public ScheduleState convert(String source) {
        try {
            return ScheduleState.fromString(source);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid ScheduleState value: " + source);
        }
    }
}
