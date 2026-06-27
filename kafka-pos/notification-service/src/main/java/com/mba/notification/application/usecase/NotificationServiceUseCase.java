package com.mba.notification.app.application.usecase;

import com.mba.notification.app.application.domain.NotificationDomain;
import com.mba.notification.app.application.port.in.NotificationServicePort;
import com.mba.notification.app.application.port.out.NotificationRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationServiceUseCase implements NotificationServicePort {
    private final NotificationRepositoryPort repositoryPort;

    @Override
    public NotificationDomain notify (NotificationDomain domain) {
        return repositoryPort.notify(domain);
    }
}
