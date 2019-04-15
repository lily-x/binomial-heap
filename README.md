# Binomial heap

Java implementation of binomial heaps.


## Overview

We can consider implementing priority queues with more efficient amortized runtime to improve the overall runtime of common algorithms such as Dijkstra's algorithm. Instead of a priority queue implemented as a traditional min-heap, we consider implementation with a binomial heap.


## Implementation

The operations implemented include:

- makeBinomialHeap
- insert
- findMin
- deleteMin
- decreaseKey
- delete
- union

The class BinomialHeap maintains a collection of BinomialTree objects, connected in a circular doubly-linked list with a sentinel.


## Performance

We conducted two tests, a small-scale and a large-scale test. The small-scale test contains up to 200 items in the heap; the large-scale test contains up to 60 million items.

The total runtime for the small-scale test was 3,561,820 ns, or approximately .04 seconds. The total runtime for the large-scale test was 71,882,021,983 ns, or approximately 72 seconds.
