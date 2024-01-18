package dev.ahmedmourad.lru

class SneakyLruCache<K, V>(private val capacity: Int) : LinkedHashMap<K, V>(capacity, 0.75f, true) {
    override fun removeEldestEntry(eldest: Map.Entry<K, V>?): Boolean {
        return size > capacity
    }
}
