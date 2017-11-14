/*
 * Copyright 2017 ZomboDB, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package llc.zombodb.utils;

import java.util.PriorityQueue;
import java.util.Stack;

public class IntArrayMergeSortIterator {
    static class ArrayContainer implements Comparable<ArrayContainer> {
        int[] arr;
        int len;
        int index;

        ArrayContainer(int[] arr, int len, int index) {
            this.arr = arr;
            this.len = len;
            this.index = index;
        }

        @Override
        public int compareTo(ArrayContainer o) {
            return Integer.compare(this.arr[this.index], o.arr[o.index]);
        }
    }

    private PriorityQueue<ArrayContainer> queue = new PriorityQueue<>();
    private Stack<Integer> pushback = new Stack<>();
    private int total;

    public IntArrayMergeSortIterator(int[][] values, int[] counts) {
        for (int i = 0; i < values.length; i++) {
            if (counts[i] > 0) {
                total += counts[i];
                queue.add(new ArrayContainer(values[i], counts[i], 0));
            }
        }
    }

    public void push(Integer value) {
        pushback.push(value);
    }

    public int getTotal() {
        return total;
    }

    public int next() {
        if (!pushback.isEmpty())
            return pushback.pop();

        ArrayContainer ac = queue.poll();
        int value = ac.arr[ac.index];

        if (ac.index < ac.len - 1) {
            queue.add(new ArrayContainer(ac.arr, ac.len, ac.index + 1));
        }
        return value;
    }

    public boolean hasNext() {
        return !queue.isEmpty() || !pushback.isEmpty();
    }
}
