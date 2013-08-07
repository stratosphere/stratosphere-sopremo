package eu.stratosphere.sopremo.type.typed;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.stratosphere.util.reflect.ReflectUtil;

/**
 * This class implements a factory for {@link TypedObjectNode}s for a user given
 * interface that extends {@link ITypedObjectNode}. It is implemented as a
 * singleton and already created class instances of {@link TypedObjectNode}s are
 * cached.
 * 
 * @author fabian
 */

public class TypedObjectNodeFactory {
	private static TypedObjectNodeFactory instance = null;

	Map<Class<? extends ITypedObjectNode>, Class<? extends ITypedObjectNode>> typesMap;

	private TypedObjectNodeFactory() {
		this.typesMap = new IdentityHashMap<Class<? extends ITypedObjectNode>, Class<? extends ITypedObjectNode>>();
	}

	public static TypedObjectNodeFactory getInstance() {
		if (instance == null)
			instance = new TypedObjectNodeFactory();
		return instance;
	}

	@SuppressWarnings("unchecked")
	public <T extends ITypedObjectNode> T getTypedObjectForInterface(Class<T> myInterface) {
		Class<T> classObject = (Class<T>) this.typesMap.get(myInterface);
		if (classObject == null)
			classObject = this.createTypedObjectExtendingClassForInterface(myInterface);

		return ReflectUtil.newInstance(classObject);
	}

	private <T extends ITypedObjectNode> Class<T> createTypedObjectExtendingClassForInterface(Class<T> myInterface) {
		@SuppressWarnings("unchecked")
		Class<T> classObject = (Class<T>) this.typesMap.get(myInterface);
		if (classObject != null)
			return classObject;
		Class<T> superClass = this.createNecessarySuperClassImplementations(myInterface);
		ASMClassBuilder classBuilder = new ASMClassBuilder();

		String className = myInterface.getName() + "Impl";
		String[] interfaceNames = new String[] { myInterface.getName().replace(".", "/") };
		classBuilder.initializePublicClass(className.replace(".", "/"), superClass.getName().replace(".", "/"),
			interfaceNames);

		BeanInfo interfaceInfo = getBeanInfo(myInterface);
		PropertyDescriptor[] props = interfaceInfo.getPropertyDescriptors();

		for (PropertyDescriptor prop : props)
			classBuilder.addAccessorsForProperty(prop);
		classObject = this.loadClass(classBuilder.dump(), className);
		this.typesMap.put(myInterface, classObject);
		return classObject;
	}

	/**
	 * explain different strategies
	 * 
	 * @param myInterface
	 * @return
	 * @throws IntrospectionException
	 */
	@SuppressWarnings("unchecked")
	private <T extends ITypedObjectNode> Class<T> createNecessarySuperClassImplementations(Class<T> myInterface) {
		if (myInterface.getInterfaces().length > 1)
			return this.createSuperClassForMultipleInheritingInterface(myInterface);
		for (Class<?> extendedInterface : myInterface.getInterfaces())
			if (extendedInterface == ITypedObjectNode.class)
				return (Class<T>) TypedObjectNode.class;
			else if (ITypedObjectNode.class.isAssignableFrom(extendedInterface))
				return this.createTypedObjectExtendingClassForInterface((Class<T>) extendedInterface);
		return null;
	}

	private <T extends ITypedObjectNode> Class<T> createSuperClassForMultipleInheritingInterface(Class<T> myInterface) {
		Class<T> classObject;
		List<Class<T>> allInterfacesInHierarchyToImplement = this.collectAllInterfacesToImplement(myInterface);
		ASMClassBuilder classBuilder = new ASMClassBuilder();

		String className = myInterface.getName() + "AbstractSupertypeImpl";
		String[] interfaceNames = new String[myInterface.getInterfaces().length];
		int i = 0;
		for (Class<?> extendedInterface : myInterface.getInterfaces()) {
			interfaceNames[i] = extendedInterface.getName().replace(".", "/");
			i++;
			if (!ITypedObjectNode.class.isAssignableFrom(extendedInterface))
				throw new IllegalArgumentException("Your interface extends an interface, which is no subtype of "
					+ ITypedObjectNode.class.getName() + ". Wrong interface is: " + extendedInterface);
		}
		classBuilder.initializePublicClass(className.replace(".", "/"),
			TypedObjectNode.class.getName().replace(".", "/"), interfaceNames);

		Set<String> uniqueProperties = new HashSet<String>();

		for (Class<?> extendedInterface : allInterfacesInHierarchyToImplement) {
			BeanInfo interfaceInfo = getBeanInfo(extendedInterface);
			PropertyDescriptor[] props = interfaceInfo.getPropertyDescriptors();
			for (PropertyDescriptor prop : props)
				if (!uniqueProperties.contains(prop.getName())) {
					uniqueProperties.add(prop.getName());
					classBuilder.addAccessorsForProperty(prop);
				}
		}

		classObject = this.loadClass(classBuilder.dump(), className);
		return classObject;
	}

	private BeanInfo getBeanInfo(Class<?> clazz) {
		try {
			return Introspector.getBeanInfo(clazz);
		} catch (IntrospectionException e) {
			throw new IllegalStateException("Cannot inspect class " + clazz, e);
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends ITypedObjectNode> List<Class<T>> collectAllInterfacesToImplement(Class<T> anInterface) {
		List<Class<T>> allInterfacesToImplement = new ArrayList<Class<T>>();
		for (Class<T> superInterface : (Class<T>[]) anInterface.getInterfaces())
			if (ITypedObjectNode.class.isAssignableFrom(superInterface)) {
				allInterfacesToImplement.add(superInterface);
				allInterfacesToImplement.addAll(this.collectAllInterfacesToImplement(superInterface));
			}
		return allInterfacesToImplement;
	}

	// taken from http://asm.ow2.org/doc/faq.html#Q5
	@SuppressWarnings("unchecked")
	private <T extends ITypedObjectNode> Class<T> loadClass(byte[] b, String className) {
		// override classDefine (as it is protected) and define the class.
		Class<T> clazz = null;
		try {
			ClassLoader loader = ClassLoader.getSystemClassLoader();
			Class<?> cls = Class.forName("java.lang.ClassLoader");
			java.lang.reflect.Method method = cls.getDeclaredMethod("defineClass", new Class[] { String.class,
				byte[].class, int.class, int.class });

			// protected method invocaton
			method.setAccessible(true);
			try {
				Object[] args = new Object[] { className, b, new Integer(0), new Integer(b.length) };
				clazz = (Class<T>) method.invoke(loader, args);
			} finally {
				method.setAccessible(false);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return clazz;
	}
}
