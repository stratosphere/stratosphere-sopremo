package eu.stratosphere.sopremo.type.typed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Assert;
import org.junit.Test;

import eu.stratosphere.sopremo.type.ArrayNode;
import eu.stratosphere.sopremo.type.BooleanNode;
import eu.stratosphere.sopremo.type.INumericNode;
import eu.stratosphere.sopremo.type.IObjectNode;
import eu.stratosphere.sopremo.type.IntNode;
import eu.stratosphere.sopremo.type.ObjectNode;
import eu.stratosphere.sopremo.type.TextNode;

public class TypedObjectTest {
	private final TypedObjectNodeFactory factory = TypedObjectNodeFactory.getInstance();

	@Test
	public void testSimpleObjectCreationForEmptyInterface() {
		EmptyInterface1 dummy;
		dummy = this.factory.getTypedObjectForInterface(EmptyInterface1.class);
		assertNotNull(dummy);
	}

	@Test
	public void testTypedObjectNodeClone() {
		TypedObjectNode dummy1;
		dummy1 = (TypedObjectNode) this.factory.getTypedObjectForInterface(
			EmptyInterface1.class);
		dummy1.backingObject.put("foo", new TextNode("bar"));
		final TypedObjectNode dummy2 = dummy1.clone();
		// not very safe test
		assertEquals(dummy1.getBackingNode().get("foo"), dummy2.getBackingNode().get("foo"));
		assertNotSame(dummy1.getBackingNode().get("foo"), dummy2.getBackingNode().get("foo"));
	}

	@Test
	public void testMultipleInstantiationOfTypedObject() {
		final EmptyInterface3 dummy1 = this.factory.getTypedObjectForInterface(EmptyInterface3.class);
		final EmptyInterface3 dummy2 = this.factory.getTypedObjectForInterface(EmptyInterface3.class);
		assertSame(dummy1.getClass(), dummy2.getClass());
		assertNotSame(dummy1, dummy2);
	}

	@Test
	public void testSimpleObjectCreationForSimpleInterface() {
		final PersonInterface dummy = this.factory.getTypedObjectForInterface(PersonInterface.class);
		final TextNode nameNode = new TextNode("FooBär");
		dummy.setName(nameNode);
		assertSame(nameNode, dummy.getName());
	}

	@Test
	public void testMultipleInheritanceHierarchies() {
		final PersonWithAgeAndWeightInterface dummy = this.factory.getTypedObjectForInterface(
			PersonWithAgeAndWeightInterface.class);
		final TextNode nameNode = new TextNode("FooBär");
		final INumericNode ageNode = new IntNode(3);
		final INumericNode weightNode = new IntNode(14);
		dummy.setName(nameNode);
		dummy.setAge(ageNode);
		dummy.setWeight(weightNode);
		assertSame(nameNode, dummy.getName());
		assertSame(ageNode, dummy.getAge());
		assertSame(weightNode, dummy.getWeight());
	}

	@Test
	public void testSimpleObjectCreationForALessSimpleInterface() {
		final SomeThingInterface dummy = this.factory.getTypedObjectForInterface(
			SomeThingInterface.class);
		final TextNode labelNode = new TextNode("labelText");
		final IntNode ageNode = new IntNode(5);
		final BooleanNode coolNode = new BooleanNode();
		dummy.setName(labelNode);
		assertSame(labelNode, dummy.getName());
		dummy.setAge(ageNode);
		assertSame(ageNode, dummy.getAge());
		dummy.setCool(coolNode);
		assertSame(coolNode, dummy.getCool());
	}

	@Test
	public void testNullReturningForUnsetValues() {
		final SomeThingInterface dummy = this.factory.getTypedObjectForInterface(SomeThingInterface.class);
		assertNull(dummy.getName());
		assertNull(dummy.getAge());
		assertNull(dummy.getCool());
	}

	@Test
	public void testNullReturningForNullSetValues() {
		final SomeThingInterface dummy = this.factory.getTypedObjectForInterface(
			SomeThingInterface.class);
		dummy.setName(null);
		assertNull(dummy.getName());
		dummy.setAge(null);
		assertNull(dummy.getAge());
		dummy.setCool(null);
		assertNull(dummy.getCool());
	}

	@Test
	public void testTypedObjectCreationForAnInterfaceExtendingAnotherTypedInterface() {
		final PersonWithAgeAndWeightInterface person = this.factory.getTypedObjectForInterface(
			PersonWithAgeAndWeightInterface.class);
		final TextNode nameNode = new TextNode("FooBär");
		final INumericNode ageNode = new IntNode(3);
		final INumericNode weightNode = new IntNode(14);
		person.setName(nameNode);
		person.setAge(ageNode);
		person.setWeight(weightNode);

		final MyInterfaceWithTypedObjectProperty dummy = this.factory.getTypedObjectForInterface(
			MyInterfaceWithTypedObjectProperty.class);

		dummy.setPerson(person);
		assertSame(nameNode, dummy.getPerson().getName());
		assertSame(ageNode, dummy.getPerson().getAge());
		assertSame(weightNode, dummy.getPerson().getWeight());
	}

	@Test
	public void testTypedObjectCreationForAnInterfaceWithCyclicTypedObjectProperties() {
		final MyInterfaceWithCyclicTypedObjectProperty inner = this.factory
			.getTypedObjectForInterface(MyInterfaceWithCyclicTypedObjectProperty.class);

		final MyInterfaceWithCyclicTypedObjectProperty outer = this.factory
			.getTypedObjectForInterface(MyInterfaceWithCyclicTypedObjectProperty.class);

		final MyInterfaceWithCyclicTypedObjectProperty someOther = this.factory
			.getTypedObjectForInterface(MyInterfaceWithCyclicTypedObjectProperty.class);

		outer.setCyclicProperty(inner);
		assertSame(inner, outer.getCyclicProperty());
		assertNotSame(someOther, outer.getCyclicProperty());
		assertNull(outer.getCyclicProperty().getCyclicProperty());
	}

	@Test
	public void testTypedInterfaceWithAListsOfTypedObjectNodes() {
		final PersonOwningThingsInterface personOwning = this.factory.getTypedObjectForInterface(
			PersonOwningThingsInterface.class);

		final SomeThingInterface thing1 = this.factory.getTypedObjectForInterface(
			SomeThingInterface.class);
		final SomeThingInterface thing2 = this.factory.getTypedObjectForInterface(
			SomeThingInterface.class);

		final ArrayNode<SomeThingInterface> listOfThings = new ArrayNode<SomeThingInterface>();
		listOfThings.add(thing1);
		listOfThings.add(thing2);

		personOwning.setSomeThings(listOfThings);

		assertEquals(2, personOwning.getSomeThings().size());
		assertSame(thing1, personOwning.getSomeThings().get(0));
		assertSame(thing2, personOwning.getSomeThings().get(1));
	}

	@Test
	public void testTypedInterfaceWithAListsOfTypedObjectNodesWithoutPriorClassInstantiation() {
		final InterfaceWithATypedObjectProperty owningPerson = this.factory
			.getTypedObjectForInterface(InterfaceWithATypedObjectProperty.class);

		final IObjectNode thing1 = new ObjectNode();
		final TextNode name = new TextNode("foobar");
		thing1.put("name", name);
		final INumericNode age = new IntNode(15);
		thing1.put("age", age);
		final BooleanNode cool = new BooleanNode();
		thing1.put("cool", cool);

		owningPerson.put("it", thing1);
		final SomeThingInterface thing1_1 = owningPerson.getIt();
		assertSame(name, thing1_1.getName());
		assertSame(age, thing1_1.getAge());
		assertSame(cool, thing1_1.getCool());
	}

	@Test(expected = ClassCastException.class)
	public void testIfWrongDatatypeInBackingObjectCausesErrorForJSONPrimitives() {
		final InterfaceWithATypedObjectProperty dummy = this.factory.getTypedObjectForInterface(
			InterfaceWithATypedObjectProperty.class);
		final TextNode number = new TextNode("foo");
		dummy.put("number", number);
		assertSame(number, dummy.getNumber());
	}

	@Test
	public void testTypedInterfaceExtendingTwoTypedInterfaces() {
		final InterfaceImplementingTwoInterfaces personAndThing = this.factory
			.getTypedObjectForInterface(InterfaceImplementingTwoInterfaces.class);
		final TextNode name = new TextNode("Foobär");
		personAndThing.setName(name);
		final INumericNode age = new IntNode(5);
		personAndThing.setAge(age);
		personAndThing.setCool(new BooleanNode());
		final INumericNode weight = new IntNode(55);
		personAndThing.setWeight(weight);
		final INumericNode income = new IntNode(555);
		personAndThing.setIncome(income);

		assertSame(name, personAndThing.getName());
		assertSame(age, personAndThing.getAge());
		assertEquals(new BooleanNode(), personAndThing.getCool());
		assertSame(weight, personAndThing.getWeight());
		assertSame(income, personAndThing.getIncome());
		// TODO assert that the most specific type of an attribute is used
	}

	public void testTypedInterfaceExtendingTypedInterfacesAndOneOrMoreNoTypedInterfaces() {
		final InterfaceImplementingTwoTypedInterfacesAndAnotherNoTypedObjectInterface dummy = TypedObjectNodeFactory
			.getInstance().getTypedObjectForInterface(
				InterfaceImplementingTwoTypedInterfacesAndAnotherNoTypedObjectInterface.class);
		Assert.assertNotNull(dummy);
	}

	@Test
	public void testDocumentUseCase() {
		final Document document = this.factory.getTypedObjectForInterface(Document.class);
		final Annotation annotation1 = this.factory.getTypedObjectForInterface(Annotation.class);
		final Annotation annotation2 = this.factory.getTypedObjectForInterface(Annotation.class);

		final TextNode fooText = new TextNode("foo");
		final TextNode barText = new TextNode("bar");

		annotation1.setText(fooText);
		annotation2.setText(barText);

		final ArrayNode<Annotation> anAnnotationArray = new ArrayNode<Annotation>();
		anAnnotationArray.add(annotation1);
		anAnnotationArray.add(annotation2);

		document.setText(new TextNode("foo bar foo"));
		document.setAnnotations(anAnnotationArray);

		assertSame(fooText, document.getAnnotations().get(0).getText());
		assertSame(barText, document.getAnnotations().get(1).getText());
	}
}

interface EmptyInterface1 extends ITypedObjectNode {

}

interface EmptyInterface2 extends ITypedObjectNode {

}

interface EmptyInterfaceNotExtendingTypedInterface {

}

interface EmptyInterface3 extends ITypedObjectNode {

}

interface EmptyInterface4 extends EmptyInterface2, EmptyInterface3 {

}

interface PersonInterface extends ITypedObjectNode {
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

interface SomeThingInterface extends ITypedObjectNode {
	public TextNode getName();

	public void setName(TextNode aNewName);

	public INumericNode getAge();

	public void setAge(INumericNode aNewAge);

	public BooleanNode getCool();

	public void setCool(BooleanNode coolness);
}

interface MyInterfaceWithTypedObjectProperty extends ITypedObjectNode {
	public PersonWithAgeAndWeightInterface getPerson();

	public void setPerson(PersonWithAgeAndWeightInterface aPerson);
}

interface MyInterfaceWithCyclicTypedObjectProperty extends ITypedObjectNode {
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

interface InterfaceWithATypedObjectProperty extends ITypedObjectNode {
	public SomeThingInterface getIt();

	public void setIt(SomeThingInterface aTypedObject);

	public INumericNode getNumber();

	public void setNumber(INumericNode aNumericNode);
}

interface ATypedObject extends ITypedObjectNode {

}

interface Document extends ITypedObjectNode {
	public TextNode getText();

	public void setText(TextNode aText);

	public ArrayNode<Annotation> getAnnotations();

	public void setAnnotations(ArrayNode<Annotation> anAnnotationsArray);
}

interface Annotation extends ITypedObjectNode {
	public TextNode getText();

	public void setText(TextNode aText);
}
