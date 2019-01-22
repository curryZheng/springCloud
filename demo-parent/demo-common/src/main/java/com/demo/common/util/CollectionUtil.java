package com.demo.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class CollectionUtil {
	/**
	 * 判断集合是否为空
	 * 
	 * @return Boolean
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	/**
	 * 判断集合是否为空
	 * 
	 * @return Boolean
	 */
	public static boolean isEmpty(Collection<?>... colls) {
		for (Collection<?> coll : colls) {
			if (isEmpty(coll)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断集合是否不为空
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	/**
	 * 判断集合是否不为空
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isNotEmpty(Collection<?>... colls) {
		return !isEmpty(colls);
	}

	/**
	 * 填充set
	 * 
	 * @param set
	 * @param arr
	 */
	@SafeVarargs
	public static <T> Set<T> asSet(T... arr) {
		return asSet(new HashSet<T>(), arr);
	}

	/**
	 * 填充set
	 * 
	 * @param set
	 * @param arr
	 */
	@SafeVarargs
	public static <T> Set<T> asSet(Set<T> set, T... arr) {
		if (set == null) {
			throw new IllegalArgumentException("Set not null!");
		}
		for (T t : arr) {
			set.add(t);
		}
		return set;
	}

	/**
	 * 判断集合是否为空
	 * 
	 * @return Boolean
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	/**
	 * 判断集合是否不为空
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	/**
	 * 默认list，null => new ArrayList<>()
	 * 
	 * @param list
	 * @return
	 */
	public static <T> List<T> defaultList(List<T> list) {
		return list == null ? new ArrayList<T>() : list;
	}

	/**
	 * 默认set，null => new HashSet<>()
	 * 
	 * @param list
	 * @return
	 */
	public static <T> Set<T> defaultSet(Set<T> set) {
		return set == null ? new HashSet<T>() : set;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(List<T> list) {
		if (CollectionUtil.isEmpty(list)) {
			return null;
		}

		Class<T> clazz = (Class<T>) list.get(0).getClass();

		T[] arr = (T[]) Array.newInstance(clazz, list.size());
		list.toArray(arr);

		return arr;
	}

	/**
	 * 取List的交集，当两个集合都有数据才取交集，一个集合有数据则直接返回有值的那个集合，当都为空时直接返回空
	 * 
	 * @param oneList
	 * @param twoList
	 * @return
	 */
	public static List<String> intersectionStrList(List<String> oneList, List<String> twoList) {
		List<String> intersectList = new ArrayList<>();

		if (CollectionUtil.isEmpty(oneList)) {
			return CollectionUtil.isEmpty(twoList) ? intersectList : twoList;
		}
		if (CollectionUtil.isEmpty(twoList)) {
			return oneList;
		}

		for (String one : oneList) {
			if (twoList.indexOf(one) != -1) {
				intersectList.add(one);
			}
		}

		return intersectList;
	}

	

	/**
	 * 集合转换为数组
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] listToArr(List<T> coll, Class<T> clazz) {
		T[] arr = null;

		if (isNotEmpty(coll)) {
			arr = (T[]) Array.newInstance(clazz, coll.size());

			for (int i = 0; i < coll.size(); i++) {
				arr[i] = coll.get(i);
			}
		}

		return arr;
	}

	/**
	 * Sort <code>List</code> by multiple conditions.<br>
	 * It's like SQL 'Order by' clause. Input multiple <code>Comparator</code> in
	 * List as argument.
	 * 
	 * @param <T>
	 * @param list
	 * @param comparatorList
	 * @throws IllegalArgumentException
	 *             if comparatorList is empty
	 */
	public static <T> void orderBy(List<T> list, final List<Comparator<T>> comparatorList) {
		if (comparatorList.isEmpty()) {// Always equals, if no Comparator.
			throw new IllegalArgumentException("comparatorList is empty.");
		}
		Comparator<T> comparator = new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				for (Comparator<T> c : comparatorList) {
					if (c.compare(o1, o2) > 0) {
						return 1;
					} else if (c.compare(o1, o2) < 0) {
						return -1;
					}
				}
				return 0;
			}
		};
		Collections.sort(list, comparator);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> deepCopyList(List<T> src) {
		List<T> dest = null;
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteOut);
			out.writeObject(src);
			ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
			ObjectInputStream in = new ObjectInputStream(byteIn);
			dest = (List<T>) in.readObject();
		} catch (IOException e) {

		} catch (ClassNotFoundException e) {

		}
		return dest;
	}
}
