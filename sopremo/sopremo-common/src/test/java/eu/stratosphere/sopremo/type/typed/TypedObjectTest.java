package eu.stratosphere.sopremo.type.typed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.BooleanNode;
import eu.stratosphere.sopremo.type.INumericNode;
import eu.stratosphere.sopremo.type.IObjectNode;
import eu.stratosphere.sopremo.type.IntNode;
import eu.stratosphere.sopremo.type.ObjectNode;
import eu.stratosphere.sopremo.type.TextNode;

public class TypedObjectTest {

	@Test
	public void testSimpleObjectCreationForEmptyInterface() {
		EmptyInterface1 dummy;
		dummy = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(EmptyInterface1.class);
		assertNotNull(dummy);
	}

	@Test
	public void testTypedObjectNodeClone() {
		TypedObjectNode dummy1;
		dummy1 = (TypedObjectNode) TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
			EmptyInterface1.class);
		dummy1.backingObject.put("foo", new TextNode("bar"));
		TypedObjectNode dummy2 = dummy1.clone();
		// not very safe test
		assertEquals(dummy1.getBackingNode().get("foo"), dummy2.getBackingNode().get("foo"));
		assertNotSame(dummy1.getBackingNode().get("foo"), dummy2.getBackingNode().get("foo"));
	}

	@Test
	public void testMultipleInstantiationOfTypedObject() {
		EmptyInterface3 dummy1 = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(EmptyInterface3.class);
		EmptyInterface3 dummy2 = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(EmptyInterface3.class);
		assertSame(dummy1.getClass(), dummy2.getClass());
		assertNotSame(dummy1, dummy2);
	}

	@Test
	public void testSimpleObjectCreationForSimpleInterface() {
		PersonInterface dummy = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(PersonInterface.class);
		TextNode nameNode = new TextNode("FooBär");
		dummy.setName(nameNode);
		assertSame(nameNode, dummy.getName());
	}

	@Test
	public void testMultipleInheritanceHierarchies() {
		PersonWithAgeAndWeightInterface dummy = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
			PersonWithAgeAndWeightInterface.class);
		TextNode nameNode = new TextNode("FooBär");
		INumericNode ageNode = new IntNode(3);
		INumericNode weightNode = new IntNode(14);
		dummy.setName(nameNode);
		dummy.setAge(ageNode);
		dummy.setWeight(weightNode);
		assertSame(nameNode, dummy.getName());
		assertSame(ageNode, dummy.getAge());
		assertSame(weightNode, dummy.getWeight());
	}

	@Test
	public void testSimpleObjectCreationForALessSimpleInterface() {
		SomeThingInterface dummy = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
			SomeThingInterface.class);
		TextNode labelNode = new TextNode("labelText");
		IntNode ageNode = new IntNode(5);
		BooleanNode coolNode = new BooleanNode();
		dummy.setName(labelNode);
		assertSame(labelNode, dummy.getName());
		dummy.setAge(ageNode);
		assertSame(ageNode, dummy.getAge());
		dummy.setCool(coolNode);
		assertSame(coolNode, dummy.getCool());
	}

	@Test
	public void testNullReturningForUnsetValues() {
		SomeThingInterface dummy = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
			SomeThingInterface.class);
		assertNull(dummy.getName());
		assertNull(dummy.getAge());
		assertNull(dummy.getCool());
	}

	@Test
	public void testNullReturningForNullSetValues() {
		SomeThingInterface dummy = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
			SomeThingInterface.class);
		dummy.setName(null);
		assertNull(dummy.getName());
		dummy.setAge(null);
		assertNull(dummy.getAge());
		dummy.setCool(null);
		assertNull(dummy.getCool());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSimpleObjectCreationForAnInterfaceWithJavaTypes() {
		TypedObjectWithJavaTypesProperties dummy = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
			TypedObjectWithJavaTypesProperties.class);
		String labelNode = "labelText";
		dummy.setLabel(labelNode);
		assertSame(labelNode, dummy.getLabel());
	}

	@Test
	public void testTypedObjectCreationForAnInterfaceExtendingAnotherTypedInterface() {
		PersonWithAgeAndWeightInterface person = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
			PersonWithAgeAndWeightInterface.class);
		TextNode nameNode = new TextNode("FooBär");
		INumericNode ageNode = new IntNode(3);
		INumericNode weightNode = new IntNode(14);
		person.setName(nameNode);
		person.setAge(ageNode);
		person.setWeight(weightNode);

		MyInterfaceWithTypedObjectProperty dummy = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
			MyInterfaceWithTypedObjectProperty.class);

		dummy.setPerson(person);
		assertSame(nameNode, dummy.getPerson().getName());
		assertSame(ageNode, dummy.getPerson().getAge());
		assertSame(weightNode, dummy.getPerson().getWeight());
	}

	@Test
	public void testTypedObjectCreationForAnInterfaceWithCyclicTypedObjectProperties() {
		MyInterfaceWithCyclicTypedObjectProperty inner = TypedObjectNodeFactory.getInstance()
			.getTypedObjectForInterface(MyInterfaceWithCyclicTypedObjectProperty.class);

		MyInterfaceWithCyclicTypedObjectProperty outer = TypedObjectNodeFactory.getInstance()
			.getTypedObjectForInterface(MyInterfaceWithCyclicTypedObjectProperty.class);

		MyInterfaceWithCyclicTypedObjectProperty someOther = TypedObjectNodeFactory.getInstance()
			.getTypedObjectForInterface(MyInterfaceWithCyclicTypedObjectProperty.class);

		outer.setCyclicProperty(inner);
		assertSame(inner, outer.getCyclicProperty());
		assertNotSame(someOther, outer.getCyclicProperty());
		assertNull(outer.getCyclicProperty().getCyclicProperty());
	}

	@Test
	public void testTypedInterfaceWithAListsOfTypedObjectNodes() {
		PersonOwningThingsInterface personOwning = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
			PersonOwningThingsInterface.class);

		SomeThingInterface thing1 = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
			SomeThingInterface.class);
		SomeThingInterface thing2 = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
			SomeThingInterface.class);

		ArrayNode<SomeThingInterface> listOfThings = new ArrayNode<SomeThingInterface>();
		listOfThings.add(thing1);
		listOfThings.add(thing2);

		personOwning.setSomeThings(listOfThings);

		assertEquals(2, personOwning.getSomeThings().size());
		assertSame(thing1, personOwning.getSomeThings().get(0));
		assertSame(thing2, personOwning.getSomeThings().get(1));
	}

	@Test
	public void testTypedInterfaceWithAListsOfTypedObjectNodesWithoutPriorClassInstantiation() {
		InterfaceWithATypedObjectProperty owningPerson = TypedObjectNodeFactory.getInstance()
			.getTypedObjectForInterface(InterfaceWithATypedObjectProperty.class);

		IObjectNode thing1 = new ObjectNode();
		TextNode name = new TextNode("foobar");
		thing1.put("name", name);
		INumericNode age = new IntNode(15);
		thing1.put("age", age);
		BooleanNode cool = new BooleanNode();
		thing1.put("cool", cool);

		owningPerson.put("it", thing1);
		SomeThingInterface thing1_1 = owningPerson.getIt();
		assertSame(name, thing1_1.getName());
		assertSame(age, thing1_1.getAge());
		assertSame(cool, thing1_1.getCool());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIfWrongDatatypeInBackingObjectCausesErrorForJSONPrimitives() {
		InterfaceWithATypedObjectProperty dummy = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
			InterfaceWithATypedObjectProperty.class);
		TextNode number = new TextNode("foo");
		dummy.put("number", number);
		assertSame(number, dummy.getNumber());
	}

	@Test
	public void testTypedInterfaceExtendingTwoTypedInterfaces() {
		InterfaceImplementingTwoInterfaces personAndThing = TypedObjectNodeFactory.getInstance()
			.getTypedObjectForInterface(InterfaceImplementingTwoInterfaces.class);
		TextNode name = new TextNode("Foobär");
		personAndThing.setName(name);
		INumericNode age = new IntNode(5);
		personAndThing.setAge(age);
		personAndThing.setCool(new BooleanNode());
		INumericNode weight = new IntNode(55);
		personAndThing.setWeight(weight);
		INumericNode income = new IntNode(555);
		personAndThing.setIncome(income);

		assertSame(name, personAndThing.getName());
		assertSame(age, personAndThing.getAge());
		assertEquals(new BooleanNode(), personAndThing.getCool());
		assertSame(weight, personAndThing.getWeight());
		assertSame(income, personAndThing.getIncome());
		// TODO assert that the most specific type of an attribute is used
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void testTypedInterfaceExtendingTypedInterfacesAndOneOrMoreNoTypedInterfaces() {
		InterfaceImplementingTwoTypedInterfacesAndAnotherNoTypedObjectInterface dummy = TypedObjectNodeFactory
			.getInstance().getTypedObjectForInterface(
				InterfaceImplementingTwoTypedInterfacesAndAnotherNoTypedObjectInterface.class);
	}

	@Test
	public void testDocumentUseCase() {
		Document document = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(Document.class);
		Annotation annotation1 = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(Annotation.class);
		Annotation annotation2 = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(Annotation.class);

		TextNode fooText = new TextNode("foo");
		TextNode barText = new TextNode("bar");

		annotation1.setText(fooText);
		annotation2.setText(barText);

		ArrayNode<Annotation> anAnnotationArray = new ArrayNode<Annotation>();
		anAnnotationArray.add(annotation1);
		anAnnotationArray.add(annotation2);

		document.setText(new TextNode("foo bar foo"));
		document.setAnnotations(anAnnotationArray);

		assertSame(fooText, document.getAnnotations().get(0).getText());
		assertSame(barText, document.getAnnotations().get(1).getText());
	}
}

interface EmptyInterface1 extends TypedInterface {

}

interface EmptyInterface2 extends TypedInterface {

}

interface EmptyInterfaceNotExtendingTypedInterface {

}

interface EmptyInterface3 extends TypedInterface {

}

interface EmptyInterface4 extends EmptyInterface2, EmptyInterface3 {

}

interface PersonInterface extends TypedInterface {
	public TextNode getName();

	public void setName(TextNode aNewName);
}

interface PersonWithAgeInterface extends PersonInterface {
	public INumericNode getAge();

	public void setAge(INumericNode anAge);
}

interface PersonWithAgeAndWeightInterface extends PersonWithAgeInterface {
	public INumericNode getWeight();

	public void setWeight(INumericNode aWeight);
}

interface PersonWithAgeAndWeightAndIncomeInterface extends PersonWithAgeAndWeightInterface {
	public INumericNode getIncome();

	public void setIncome(INumericNode anIncome);
}

interface SomeThingInterface extends TypedInterface {
	public TextNode getName();

	public void setName(TextNode aNewName);

	public INumericNode getAge();

	public void setAge(INumericNode aNewAge);

	public BooleanNode getCool();

	public void setCool(BooleanNode coolness);
}

interface TypedObjectWithJavaTypesProperties extends TypedInterface {
	public String getLabel();

	public void setLabel(String aNewName);
}

interface MyInterfaceWithTypedObjectProperty extends TypedInterface {
	public PersonWithAgeAndWeightInterface getPerson();

	public void setPerson(PersonWithAgeAndWeightInterface aPerson);
}

interface MyInterfaceWithCyclicTypedObjectProperty extends TypedInterface {
	public MyInterfaceWithCyclicTypedObjectProperty getCyclicProperty();

	public void setCyclicProperty(MyInterfaceWithCyclicTypedObjectProperty anInner);
}

interface PersonOwningThingsInterface extends PersonInterface {
	public ArrayNode<SomeThingInterface> getSomeThings();

	public void setSomeThings(ArrayNode<SomeThingInterface> aListOfSomeThings);
}

interface NoTypedInterface {
	public String getString();

	public void setString(String aString);
}

interface InterfaceImplementingTwoInterfaces extends PersonWithAgeAndWeightAndIncomeInterface, SomeThingInterface {

}

interface InterfaceImplementingTwoTypedInterfacesAndAnotherNoTypedObjectInterface extends
		PersonWithAgeAndWeightAndIncomeInterface, SomeThingInterface, NoTypedInterface {

}

interface InterfaceWithATypedObjectProperty extends TypedInterface {
	public SomeThingInterface getIt();

	public void setIt(SomeThingInterface aTypedObject);

	public INumericNode getNumber();

	public void setNumber(INumericNode aNumericNode);
}

interface ATypedObject extends TypedInterface {

}

interface Document extends TypedInterface {
	public TextNode getText();

	public void setText(TextNode aText);

	public ArrayNode<Annotation> getAnnotations();

	public void setAnnotations(ArrayNode<Annotation> anAnnotationsArray);
}

interface Annotation extends TypedInterface {
	public TextNode getText();

	public void setText(TextNode aText);
}
