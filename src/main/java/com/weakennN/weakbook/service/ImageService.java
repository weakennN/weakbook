package com.weakennN.weakbook.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class ImageService {

    public String generateRandomUrl(int length) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        String choices = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_";
        String result = "/";

        for (int i = 0; i < length; i++) {
            result += choices.charAt(random.nextInt(0, choices.length()));
        }

        return result += ".png";
    }
}
