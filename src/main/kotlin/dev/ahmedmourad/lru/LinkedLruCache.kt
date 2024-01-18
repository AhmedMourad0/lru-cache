package dev.ahmedmourad.lru

class LinkedLruCache<T>(private val capacity: Int) {

    private val map = hashMapOf<Int, Node<T>>()
    private var head: Node<T>? = null
    private var tail: Node<T>? = null

    private data class Node<T>(
        val key: Int,
        var value: T,
        var next: Node<T>? = null,
        var prev: Node<T>? = null
    )

    operator fun get(key: Int): T? {
        val node = map[key] ?: return null
        moveToHead(node)
        return node.value
    }

    fun put(key: Int, value: T): T {
        val node = map[key]
        if (node != null) {
            node.value = value
            moveToHead(node)
        } else {
            val newNode = Node(key, value)
            if (map.size >= capacity) {
                removeTail()
            }
            addToHead(newNode)
            map[key] = newNode
        }
        return value
    }

    fun remove(key: Int): T? {
        val node = map[key] ?: return null
        removeNode(node)
        map.remove(key)
        return node.value
    }

    private fun moveToHead(node: Node<T>) {
        removeNode(node)
        addToHead(node)
    }

    private fun addToHead(node: Node<T>) {
        node.next = head
        node.prev = null
        head?.prev = node
        head = node
        if (tail == null) {
            tail = node
        }
    }

    private fun removeNode(node: Node<T>) {
        val prevNode = node.prev
        val nextNode = node.next

        if (prevNode != null) {
            prevNode.next = nextNode
        } else {
            head = nextNode
        }

        if (nextNode != null) {
            nextNode.prev = prevNode
        } else {
            tail = prevNode
        }
    }

    private fun removeTail() {
        if (tail != null) {
            map.remove(tail!!.key)
            removeNode(tail!!)
        }
    }
}
