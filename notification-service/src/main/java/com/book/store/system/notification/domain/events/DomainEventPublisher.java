package com.book.store.system.notification.domain.events;

public interface DomainEventPublisher<T extends DomainEvent> {
    void publish(T domainEvent);
}
