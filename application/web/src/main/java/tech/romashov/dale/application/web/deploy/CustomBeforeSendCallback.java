package tech.romashov.dale.application.web.deploy;

import io.sentry.SentryEvent;
import io.sentry.SentryOptions;
import org.springframework.stereotype.Component;
import tech.romashov.dale.application.web.DaleException;
import tech.romashov.dale.application.web.retails.RetailException;

@Component
public class CustomBeforeSendCallback implements SentryOptions.BeforeSendCallback {
    @Override
    public SentryEvent execute(SentryEvent event, Object hint) {
        if (event.getThrowable() instanceof DaleException
                || event.getThrowable() instanceof RetailException
        ) {
            return null;
        } else {
            return event;
        }
    }
}
