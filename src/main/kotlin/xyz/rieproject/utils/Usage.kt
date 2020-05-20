package xyz.rieproject.utils

import java.util.stream.Collectors

class Usage {
    private val map = HashMap<Long, Int>()
    fun increment(key: Long) {
        map[key] = map.getOrDefault(key, 0) + 1
    }

    fun getMap(): Map<Long, Int> {
        return map
    }

    fun higher(`val`: Int): List<Map.Entry<Long, Int>> {
        return map.entries.stream()
            .filter { e: Map.Entry<Long, Int> -> e.value >= `val` }
            .collect(Collectors.toList())
    }
}