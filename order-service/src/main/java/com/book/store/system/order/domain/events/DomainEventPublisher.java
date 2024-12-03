package com.book.store.system.order.domain.events;

public interface DomainEventPublisher<T extends DomainEvent> {
    void publish(T domainEvent);
}
