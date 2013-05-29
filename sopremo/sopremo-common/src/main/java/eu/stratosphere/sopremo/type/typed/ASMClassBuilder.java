package eu.stratosphere.sopremo.type.typed;

import java.beans.PropertyDescriptor;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import eu.stratosphere.sopremo.type.IJsonNode;

/**
 * This class uses the ASM framework to build instances of {@link TypedObjectNode}s that implement a certain interface (
 * extending {@link TypedInterface}), specified by the user. The main idea is to create
 * the getters and setters given in the interface which delegate the data to and
 * from the underlying backingObject of a {@link TypedObject}. These methods are
 * generated at JVM byte code level.
 * 
 * @author ftschirschnitz
 */

public class ASMClassBuilder implements Opcodes {
	private ClassWriter classWriter = new ClassWriter(0);

	private MethodVisitor methodVisiter;

	private String className;

	protected void initializePublicClass(String aClassName, String extendedClass, String[] interfaceNames) {
		this.className = aClassName;

		this.classWriter.visit(V1_5, ACC_PUBLIC + ACC_SUPER, aClassName, null, extendedClass, interfaceNames);
		{
			this.methodVisiter = this.classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
			this.methodVisiter.visitCode();
			this.methodVisiter.visitVarInsn(ALOAD, 0);
			this.methodVisiter.visitMethodInsn(INVOKESPECIAL, extendedClass, "<init>", "()V");
			this.methodVisiter.visitInsn(RETURN);
			this.methodVisiter.visitMaxs(1, 1);
			this.methodVisiter.visitEnd();
		}

	}

	protected byte[] dump() {
		return this.classWriter.toByteArray();
	}

	public void addAccessorsForProperty(PropertyDescriptor prop) {
		this.addGetterMethod(prop);
		this.addSetterMethod(prop);
	}

	private void addGetterMethod(PropertyDescriptor prop) {
		String propertyTypeName = this.getPropertyTypeName(prop);
		this.methodVisiter = this.classWriter.visitMethod(ACC_PUBLIC, prop.getReadMethod().getName(), "()L"
			+ propertyTypeName + ";", null, null);
		this.methodVisiter.visitCode();
		this.methodVisiter.visitVarInsn(ALOAD, 0);
		this.methodVisiter.visitLdcInsn(prop.getName());
		this.methodVisiter.visitLdcInsn(Type.getType(prop.getPropertyType()));
		this.methodVisiter.visitMethodInsn(INVOKEVIRTUAL, this.className, "get",
			"(Ljava/lang/String;Ljava/lang/Class;)Leu/stratosphere/sopremo/type/IJsonNode;");
		this.methodVisiter.visitTypeInsn(CHECKCAST, propertyTypeName);
		this.methodVisiter.visitInsn(ARETURN);
		this.methodVisiter.visitMaxs(3, 1);
		this.methodVisiter.visitEnd();

	}

	private String getPropertyTypeName(PropertyDescriptor prop) {
		if (!(IJsonNode.class.isAssignableFrom(prop.getPropertyType()) || TypedInterface.class.isAssignableFrom(prop
			.getPropertyType())))
			throw new IllegalArgumentException(
				"Only subtypes of IJsonNode are allowed as property types for typed objects so far. "
					+ prop.getPropertyType().getSimpleName() + " is not allowed.");
		return prop.getPropertyType().getName().replace(".", "/");
	}

	private void addSetterMethod(PropertyDescriptor prop) {
		String propertyTypeName = this.getPropertyTypeName(prop);
		this.methodVisiter = this.classWriter.visitMethod(ACC_PUBLIC, prop.getWriteMethod().getName(), "(L"
			+ propertyTypeName + ";)V", null, null);
		this.methodVisiter.visitCode();
		this.methodVisiter.visitVarInsn(ALOAD, 0);
		this.methodVisiter.visitLdcInsn(prop.getName());
		this.methodVisiter.visitVarInsn(ALOAD, 1);
		this.methodVisiter.visitMethodInsn(INVOKEVIRTUAL, this.className, "put",
			"(Ljava/lang/String;Leu/stratosphere/sopremo/type/IJsonNode;)Leu/stratosphere/sopremo/type/IObjectNode;");
		this.methodVisiter.visitInsn(POP);
		this.methodVisiter.visitInsn(RETURN);
		this.methodVisiter.visitMaxs(3, 2);
		this.methodVisiter.visitEnd();
	}
}