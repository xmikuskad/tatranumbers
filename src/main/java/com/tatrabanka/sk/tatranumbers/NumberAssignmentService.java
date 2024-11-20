package com.tatrabanka.sk.tatranumbers;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class NumberAssignmentService {
    // A hardcoded list of unique numbers
    private List<String> numberList;

    // Map to store user and assigned number
    private final Map<String, String> userNumberMap = new ConcurrentHashMap<>();

    // Atomic index to keep track of next available number
    private final AtomicInteger currentIndex = new AtomicInteger(0);

    @PostConstruct
    public void init() {
        this.numberList = new NumberList().getNumbers();
    }

    public void resetNumber() {
        currentIndex.set(0);
        userNumberMap.clear();
    }

    /**
     * Assigns a unique number to the user if not already assigned.
     * 
     * @param userName the name of the user
     * @return assigned number
     */
    public String assignNumber(String userName) {
        // Check if user already has a number
        return userNumberMap.computeIfAbsent(userName, name -> {
            // Assign the next available number
            int index = currentIndex.getAndIncrement();
            if (index < numberList.size()) {
                return numberList.get(index);
            } else {
                throw new RuntimeException("No more numbers available.");
            }
        });
    }

    public Map<String, String> getMap() {
        return Collections.unmodifiableMap(userNumberMap);
    }

    public List<String> getNumList() {
        return this.numberList;
    }

    public void setNumList(String list) {
        this.numberList = Arrays.asList(list.split("\\r?\\n")).stream().filter(i -> !Objects.isNull(i) && !i.isBlank())
                .toList();
    }
}
