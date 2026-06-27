package com.mba.notification.app.application.port.out;

import com.mba.notification.app.application.domain.NotificationDomain;

public interface NotificationRepositoryPort {
    NotificationDomain notify(NotificationDomain domain);
}
