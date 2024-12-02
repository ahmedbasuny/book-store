package com.book.store.system.order.domain;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public String getLoggedInUserName() {
        return "ahmedbasuny";
    }
}
