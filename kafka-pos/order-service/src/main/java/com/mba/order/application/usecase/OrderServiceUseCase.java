package com.mba.order.app.application.usecase;

import com.mba.order.app.application.domain.OrderDomain;
import com.mba.order.app.application.port.in.OrderServicePort;
import com.mba.order.app.application.port.out.OrderRepositoryPort;
import com.mba.order.app.utils.UseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class OrderServiceUseCase implements OrderServicePort {

    private final OrderRepositoryPort repositoryPort;

    @Override
    public OrderDomain order(OrderDomain domain) {
        return repositoryPort.order(domain);
    }

    @Override
    public List<OrderDomain> list() {
        return repositoryPort.list();
    }


}
