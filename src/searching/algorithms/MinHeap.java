package searching.algorithms;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class MinHeap<T extends Comparable<T>> extends PriorityQueue<T> {
	private static final long serialVersionUID = 1L;
	private ArrayList<T> heap;
		
	public MinHeap() {
		this.heap = new ArrayList<T>();
	}
	public MinHeap(T node) {
		this();
		heap.add(node);
	}
	
	public T peek() {
		return heap.get(0);
	}
	
	public T poll() {
		if(heap.size() == 0)
			return null;
		
		if(heap.size() == 1)
			return heap.remove(0);
		
		T root = heap.get(0);
		T newRoot = heap.remove(heap.size()-1);
		heap.set(0, newRoot);
		heapifyDown(0);
		
		return root;
	}
	
	public boolean add(T value) {
		heap.add(value);
		heapifyUp(heap.size()-1);
		return true;
	}
	
	public void clear() {
		heap.clear();
	}
	
	public boolean contains(T value) {
		return heap.contains(value);
	}
	
	public boolean isEmpty() {
		return heap.size() > 0;
	}
	
	private void heapifyDown(int nodeIdx) {
		T node = heap.get(nodeIdx);
		T smallest = node;
		
		int leftNodeIdx = nodeIdx*2+1;
		int rightNodeIdx = leftNodeIdx+1;
		
		T leftNode;
		if(leftNodeIdx < heap.size())
			leftNode = heap.get(leftNodeIdx);
		else
			leftNode = null;
		
		T rightNode;
		if(rightNodeIdx < heap.size())
			rightNode = heap.get(rightNodeIdx);
		else
			rightNode = null;
		
		if(leftNode != null && leftNode.compareTo(smallest) < 0)
			smallest = leftNode;
		
		if(rightNode != null && rightNode.compareTo(smallest) < 0)
			smallest = rightNode;
		
		if(smallest == node)
			return;
		
		if(smallest == leftNode) {
			swapNodes(nodeIdx, leftNodeIdx);
			heapifyDown(leftNodeIdx);
		} else {
			swapNodes(nodeIdx, rightNodeIdx);
			heapifyDown(rightNodeIdx);
		}
	}
	
	private void heapifyUp(int nodeIdx) {
		if(nodeIdx == 0)
			return;
		
		T node = heap.get(nodeIdx);
		int parentNodeIdx = (nodeIdx - 1) / 2;
		T parentNode = heap.get(parentNodeIdx);
		
		if(node.compareTo(parentNode) < 0) {
			swapNodes(nodeIdx, parentNodeIdx);
			heapifyUp(parentNodeIdx);
		}
	}
	
	private void swapNodes(int firstIdx, int secondIdx) {
		T firstNode = heap.get(firstIdx);
		heap.set(firstIdx, heap.get(secondIdx));
		heap.set(secondIdx, firstNode);
	}
	
}
