package com.example.training.service;

import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class APIKeyService {

    public String generateApiKey() {
        return UUID.randomUUID().toString();
    }
}

