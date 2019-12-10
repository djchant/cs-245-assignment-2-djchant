import java.util.Arrays;

public class ArrayQueue<T> implements Queue<T>{
	public int start = 0;
	public int end = 0;
	public T[] a;
	public int size;

	public ArrayQueue(int size) {
		this.size = size;
		a = (T[]) new Object[size];
	}


	public void enqueue(T item) {
		if((end + 1) % a.length == start) {
			grow_array();
		}

		a[end++] = item;
		end = end % a.length;
	}

	public T dequeue() throws Exception {
		if(empty()) {
			throw new Exception();
		}

		T temp = a[start];
		start = (start + 1) % a.length;
		return temp;
	}

	public boolean empty() {

		return(start == end);
	}

	public void grow_array() {
		//T[] new_array = new T[a.length * 2];

		T[] new_array = (T[]) new Object[a.length * 2];

		new_array = Arrays.copyOfRange(a, 0, a.length);
		a = new_array;
	}
}