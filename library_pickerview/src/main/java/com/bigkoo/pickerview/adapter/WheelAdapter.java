package com.bigkoo.pickerview.adapter;

public interface WheelAdapter<T> {

	int getItemsCount();

	T getItem(int index);
	
	int indexOf(T o);
}
