package ca.charland.test.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

/**
 * Used for JUnit testing. Uses reflection to get access to private fields.
 * 
 * @author mcharland
 */
public final class Reflection {

	/**
	 * Prevents an instant from being created.
	 */
	private Reflection() {

	}

	/**
	 * Get an object. Throws ReflectionException if there was a problem casting the object.
	 * 
	 * @param o
	 *            The object to get the object from.
	 * @param string
	 *            The name of the object to get.
	 * @return The value of the object.
	 */
	public static Object getObject(final Object o, final String string) {

		Field f = getField(o, string);
		Object result = null;

		try {
			result = f.get(o);
		} catch (IllegalAccessException iae) {
			throw new ReflectionException("Problem converting to String.");
		}

		return result;

	}

	/**
	 * Get a string value. Throws ReflectionException if there was a problem casting the object.
	 * 
	 * @param o
	 *            The object to get the string value from.
	 * @param string
	 *            The name of the string to get.
	 * @return The value of the string.
	 */
	public static String getString(final Object o, final String string) {

		Field f = getField(o, string);
		String result = null;

		try {
			result = f.get(o).toString();
		} catch (IllegalAccessException iae) {
			throw new ReflectionException("Problem converting to String.");
		}

		return result;

	}

	/**
	 * Get a string value. Throws a reflection exception if there was a problem converting to the integer.
	 * 
	 * @param o
	 *            The object to get the string value from.
	 * @param string
	 *            The name of the string to get.
	 * @return The value of the string.
	 */
	public static int getInt(final Object o, final String string) {
		final String result = getString(o, string);
		final Integer integer = Integer.parseInt(result);
		return integer.intValue();

	}

	/**
	 * Get a string buffer value. Throws ReflectionException if there was a problem casting the object.
	 * 
	 * @param o
	 *            The object to get the string buffer value from.
	 * @param string
	 *            The name of the string buffer to get.
	 * @return The value of the string buffer.
	 */
	public static StringBuffer getStringBuffer(final Object o, final String string) {

		Field f = getField(o, string);
		StringBuffer result = new StringBuffer();

		try {
			result = new StringBuffer(f.get(o).toString());
		} catch (IllegalAccessException iae) {
			throw new ReflectionException("Problem converting to StringBuffer.");
		}

		return result;

	}

	/**
	 * Get a boolean value. Throws ReflectionException if there was a problem casting the object.
	 * 
	 * @param o
	 *            The object to get the boolean value from.
	 * @param string
	 *            The name of the boolean to get.
	 * @return The value of the boolean.
	 */
	public static boolean getBoolean(final Object o, final String string) {

		Field f = getField(o, string);
		boolean result = false;

		try {
			result = Boolean.parseBoolean(f.get(o).toString());
		} catch (IllegalAccessException iae) {
			throw new ReflectionException("Problem converting to boolean.");

		}

		return result;

	}

	/**
	 * Get a properties object. Throws ReflectionException if there was a problem casting the object.
	 * 
	 * @param o
	 *            The object to get the properties object from.
	 * @param property
	 *            The name of the properties field.
	 * @return The properties object. @
	 */
	public static Properties getProperties(final Object o, final String property) {

		Field f = getField(o, property);
		Properties result = null;

		try {
			result = (Properties) f.get(o);
		} catch (IllegalAccessException iae) {
			throw new ReflectionException("Problem converting to Properties.");

		}

		return result;

	}

	/**
	 * Gets a field.
	 * 
	 * @param o
	 *            The object to get the field from.
	 * @param fieldNameToGet
	 *            The name of the field to get.
	 * @return The field.
	 */
	public static Field getField(final Object o, final String fieldNameToGet) {

		ArrayList<Field> fields = getFields(o.getClass(), new ArrayList<Field>());

		return getField(fields, fieldNameToGet);

	}

	/**
	 * Recursively gets an array list of fields.
	 * 
	 * @param c
	 *            The name of the class to for the fields in.
	 * @param fields
	 *            The fields found.
	 * @return The fields found.
	 */
	private static ArrayList<Field> getFields(final Class<?> c, final ArrayList<Field> fields) {

		Class<?> superClass = c.getSuperclass();
		Field[] f = c.getDeclaredFields();
		fields.addAll(Arrays.asList(f));

		if (superClass != null) {
			getFields(superClass, fields);
		}

		return fields;

	}

	/**
	 * Gets a field. Throws ReflectionException if the field cannot be found.
	 * 
	 * @param fields
	 *            The name of the fields.
	 * @param fieldNameToGet
	 *            The name of field name to get.
	 * @return The field.
	 */
	private static Field getField(final ArrayList<Field> fields, final String fieldNameToGet) {

		for (Field f : fields) {
			if (f.getName().equals(fieldNameToGet)) {
				if (f.toString().contains("private") || f.toString().contains("protected")) {
					f.setAccessible(true);
				}
				return f;
			}
		}

		throw new ReflectionException(fieldNameToGet + " not found.");
	}

	/**
	 * Run a private or protected method. Throws ReflectionException if the arguments are invalid or if you don't have permission to run the method or
	 * if the method calls an exception.
	 * 
	 * @param o
	 *            The object you want run the method in.
	 * @param methodToRun
	 *            The name of the method to run.
	 * @param args
	 *            Arguments for the method to be run.
	 * @return The result of running the method. If any.
	 */
	public static Object runMethod(final Object o, final String methodToRun, final Object... args) {
		return runStaticMethod(o.getClass(), methodToRun, args);
	}

	/**
	 * Run a private or protected static method. Throws ReflectionException if the arguments are invalid or if you don't have permission to run the
	 * method or if the method calls an exception.
	 * 
	 * @param className
	 *            The name of the class of the method you want to run.
	 * @param methodToRun
	 *            The name of the method to run.
	 * @param args
	 *            Arguments for the method to be run.
	 * @return The result of running the method. If any.
	 */
	public static Object runStaticMethod(final Class<?> className, final String methodToRun, final Object... args) {

		Object r = null;
		Method m = getMethod(className, methodToRun);

		try {
			r = m.invoke(className, args);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			Throwable c = e.getCause();
			if (c instanceof RuntimeException) {
				RuntimeException r1 = (RuntimeException) c;
				throw r1;
			}
		}

		if (!m.getReturnType().toString().equals("void") && r == null) {
			throw new ReflectionException("There was a problem reflecting the method.");
		}

		return r;
	}

	/**
	 * Gets a your method.
	 * 
	 * @param c
	 *            The object to get the method from.
	 * @param methodNameToGet
	 *            The name of the method to get.
	 * @return The method.
	 */
	private static Method getMethod(final Class<?> c, final String methodNameToGet) {

		ArrayList<Method> methods = getMethods(c, new ArrayList<Method>());

		return getMethod(methods, methodNameToGet);

	}

	/**
	 * Recursively gets an array list of methods.
	 * 
	 * @param c
	 *            The name of the class to for the methods in.
	 * @param fields
	 *            The methods found.
	 * @return The methods found.
	 */
	private static ArrayList<Method> getMethods(final Class<?> c, final ArrayList<Method> fields) {

		Class<?> superClass = c.getSuperclass();

		Method[] f = c.getDeclaredMethods();

		fields.addAll(Arrays.asList(f));

		if (superClass != null) {

			getMethods(superClass, fields);

		}

		return fields;

	}

	/**
	 * Gets a method. Throws ReflectionException if the method cannot be found.
	 * 
	 * @param methods
	 *            The name of the methods.
	 * @param methodNameToGet
	 *            The name of method name to get.
	 * @return The method.
	 */
	private static Method getMethod(final ArrayList<Method> methods, final String methodNameToGet) {

		for (Method m : methods) {
			if (m.getName().equals(methodNameToGet)) {
				if (m.toString().contains("private") || m.toString().contains("protected")) {
					m.setAccessible(true);

				}
				return m;
			}
		}

		throw new ReflectionException(methodNameToGet + " not found.");
	}

	/**
	 * Create a new instance of the class.
	 * 
	 * @param className
	 *            The name of the class to create.
	 * @param args
	 *            The arguments to pass into the constructor.
	 * @throws Throwable
	 *             When the construction of a new instance fails.
	 * @return A new instance of the class.
	 */
	public static Object constructNewInstance(final String className, final Object[] args) throws Throwable {

		Class<?> c = null;
		try {
			c = Class.forName(className);
		} catch (Exception e) {
			try {
				throw e.getCause();
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
		}
		Constructor<?>[] cs = c.getDeclaredConstructors();

		// ReadQuestions
		Object result = null;
		try {
			Constructor<?> c0 = cs[0];
			c0.setAccessible(true);
			result = c0.newInstance(args);
		} catch (Throwable t) {
			throw t.getCause();
		}
		return result;
	}

}
