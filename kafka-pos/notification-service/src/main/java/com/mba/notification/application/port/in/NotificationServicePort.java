package com.mba.notification.app.application.port.in;

import com.mba.notification.app.application.domain.NotificationDomain;

public interface NotificationServicePort {
    NotificationDomain notify(NotificationDomain domain);
}
