package com.book.store.system.order.domain;

import com.book.store.system.order.domain.events.*;
import org.springframework.stereotype.Service;

@Service
public interface OrderEventService {
    void save(OrderEvent orderEvent);

    void publishOrderEvents();
}
