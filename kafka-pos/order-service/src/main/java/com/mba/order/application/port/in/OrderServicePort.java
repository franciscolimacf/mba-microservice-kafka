package com.mba.order.app.application.port.in;

import com.mba.order.app.application.domain.OrderDomain;

import java.util.List;

public interface OrderServicePort {
    OrderDomain order(OrderDomain domain);
    List<OrderDomain> list();
}
