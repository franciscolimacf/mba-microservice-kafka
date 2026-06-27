package com.mba.order.app.application.port.out;

import com.mba.order.app.application.domain.OrderDomain;

import java.util.List;

public interface OrderRepositoryPort {
    public OrderDomain order(OrderDomain domain);
    public List<OrderDomain> list();
}
