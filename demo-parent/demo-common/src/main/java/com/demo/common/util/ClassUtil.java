package com.demo.common.util;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 */
public class ClassUtil implements Serializable {

	private static final long serialVersionUID = -1807040111572725347L;

	@SuppressWarnings("rawtypes")
	public static Object newInstance(Class entityClass) {

		Object obj = null;
		try {
			obj = entityClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return obj;
	}

	public static Object newInstance(String className) {

		Object obj = null;
		try {
			obj = Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return obj;
	}

	/**
	 * 获得包下面的所有的class
	 * 
	 * @param pack
	 *            package完整名称
	 * @return List包含所有class的实例
	 */
	public static List<Class<?>> getClasssFromPackage(String pack) {
		List<Class<?>> clazzs = new ArrayList<Class<?>>();

		// 是否循环搜索子包
		boolean recursive = true;
		// 包名字
		String packageName = pack;
		// 包名对应的路径名称
		String packageDirName = packageName.replace('.', '/');
		Enumeration<URL> dirs;

		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			while (dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
				String protocol = url.getProtocol();
				if ("file".equals(protocol)) {
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					findClassInPackageByFile(packageName, filePath, recursive, clazzs);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return clazzs;
	}

	/**
	 * 在package对应的路径下找到所有的class
	 * 
	 * @param packageName
	 *            package名称
	 * @param filePath
	 *            package对应的路径
	 * @param recursive
	 *            是否查找子package
	 * @param clazzs
	 *            找到class以后存放的集合
	 */
	private static void findClassInPackageByFile(String packageName, String filePath, final boolean recursive,
			List<Class<?>> clazzs) {
		File dir = new File(filePath);
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		// 在给定的目录下找到所有的文件，并且进行条件过滤
		File[] dirFiles = dir.listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {
				boolean acceptDir = recursive && file.isDirectory();// 接受dir目录
				boolean acceptClass = file.getName().endsWith("class");// 接受class文件
				return acceptDir || acceptClass;
			}
		});

		for (File file : dirFiles) {
			if (file.isDirectory()) {
				findClassInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, clazzs);
			} else {
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					clazzs.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static List<Field> getAllFields(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}
		
		List<Field> list = new ArrayList<>();
		
		while(clazz != null) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (Modifier.isStatic(field.getModifiers())) {
					continue;
				}
				list.add(field);
			}
			
			clazz = clazz.getSuperclass();
		}
		
		return list;
	}
}
