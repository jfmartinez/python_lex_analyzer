package icom4036.assignment1.nodestack;

public class NodeStack<E> implements Stack<E>{
	
	protected Node<E> top;
	protected int size;
	public NodeStack(){
		
		top = null;
		size = 0;
	}
	
	public int size()
	{
		return size;
	}
	
	public boolean isEmpty()
	{
		if(top == null)
			return true;
		return false;
	}
	
	public void push(E elem)
	{
		Node<E> v = new Node<E>(elem,top);
		top = v;
		size++;
	}
	
	public E top()
	{
		if(isEmpty())
			throw new EmptyStackException("Stack is empty");
		
		return top.getElement();
		
	}
	
	public E pop() throws EmptyStackException{
		if(isEmpty()) throw new EmptyStackException("Stack is empty.");
		
		E temp = top.getElement();
		top = top.getNext();
		size--;
		return temp;
	}
	
	
	
	
	
	private class Node<E>
	{
		private E element;
		private Node<E> next;
		
		public Node()
		{
			this(null,null);
		}
		
		public Node(E e, Node<E> n)
		{
			element = e;
			next = n;
		}
		
		public E getElement()
		{
			return element;
		}
		
		public Node<E> getNext()
		{
			return next;
		}
		
		public void setElement(E newElem)
		{
			element =newElem;
		}
		
		public void setNext(Node<E> newNext)
		{
			next = newNext;
		}
		
		
		
		
		
	}

}
