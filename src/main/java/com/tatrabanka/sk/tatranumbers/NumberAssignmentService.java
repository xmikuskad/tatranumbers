package com.tatrabanka.sk.tatranumbers;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class NumberAssignmentService {

    // A hardcoded list of unique numbers
    // private final List<Integer> numberList = List.of(
    //         101, 102
    //         // Add more numbers as needed
    // );

        // A hardcoded list of unique numbers
        private final List<Integer> numberList = List.of(
            101, 102, 103, 104, 105, 106, 107, 108, 109, 110,
            111, 112, 113, 114, 115, 116, 117, 118, 119, 120
            // Add more numbers as needed
    );

    // Map to store user and assigned number
    private final Map<String, Integer> userNumberMap = new ConcurrentHashMap<>();

    // Atomic index to keep track of next available number
    private final AtomicInteger currentIndex = new AtomicInteger(0);

    public void resetNumber() {
        currentIndex.set(0);
    }
    

    /**
     * Assigns a unique number to the user if not already assigned.
     * @param userName the name of the user
     * @return assigned number
     */
    public Integer assignNumber(String userName) {
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
}

