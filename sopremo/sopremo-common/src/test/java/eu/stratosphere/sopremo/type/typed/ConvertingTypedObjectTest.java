package eu.stratosphere.sopremo.type.typed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import eu.stratosphere.sopremo.type.BooleanNode;
import eu.stratosphere.sopremo.type.IObjectNode;
import eu.stratosphere.sopremo.type.IntNode;
import eu.stratosphere.sopremo.type.ObjectNode;
import eu.stratosphere.sopremo.type.TextNode;

public class ConvertingTypedObjectTest {

	@Test
	public void testUnmodifiableType() {
		final JavaPersonInterface dummy =
			TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(JavaPersonInterface.class);
		dummy.setName("FooBär");
		assertEquals("FooBär", dummy.getName());
	}

	@Test
	public void testSetModifiableType() {
		final JavaPersonModifiableInterface dummy =
			TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(JavaPersonModifiableInterface.class);
		final StringBuilder input = new StringBuilder("FooBär");
		dummy.setName(input);
		assertEquals("FooBär", dummy.getName().toString());
		assertSame(input, dummy.getName());
	}

	@Test
	public void testCreateCacheObjectForModifiableType() {
		final JavaPersonModifiableInterface dummy =
			TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(JavaPersonModifiableInterface.class);

		dummy.put("name", new TextNode("BärFoo"));
		assertEquals("BärFoo", dummy.getName().toString());
	}

	@Test
	public void testMultipleInheritanceHierarchies() {
		final JavaPersonWithAgeAndWeightInterface dummy =
			TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
				JavaPersonWithAgeAndWeightInterface.class);
		dummy.setName("FooBär");
		dummy.setAge(3);
		dummy.setWeight(14.5);
		assertEquals("FooBär", dummy.getName());
		assertEquals(Integer.valueOf(3), dummy.getAge());
		assertEquals(Double.valueOf(14.5), dummy.getWeight());
	}

	@Test
	public void testSimpleObjectCreationForALessSimpleInterface() {
		final JavaSomeThingInterface dummy = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
			JavaSomeThingInterface.class);
		dummy.setName("labelText");
		assertEquals("labelText", dummy.getName());
		dummy.setAge(5);
		assertEquals(Integer.valueOf(5), dummy.getAge());
		dummy.setCool(true);
		assertSame(Boolean.TRUE, dummy.getCool());
	}

	@Test
	public void testNullReturningForUnsetValues() {
		final JavaSomeThingInterface dummy = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
			JavaSomeThingInterface.class);
		assertNull(dummy.getName());
		assertNull(dummy.getCool());
	}

	@Test
	public void testErrorForUnsetPrimitiveValues() {
		final JavaSomeThingInterface dummy = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
			JavaSomeThingInterface.class);
		assertNull(dummy.getAge());
	}

	@Test
	public void testNullReturningForNullSetValues() {
		final JavaSomeThingInterface dummy = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
			JavaSomeThingInterface.class);
		dummy.setName(null);
		assertNull(dummy.getName());
		dummy.setCool(null);
		assertNull(dummy.getCool());
	}

	@Test
	public void testErrorForSetNullInPrimitiveValues() {
		final JavaSomeThingInterface dummy = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
			JavaSomeThingInterface.class);
		assertNull(dummy.getAge());
	}

	@Test
	public void testTypedObjectCreationForAnInterfaceExtendingAnotherTypedInterface() {
		final JavaPersonWithAgeAndWeightInterface person =
			TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
				JavaPersonWithAgeAndWeightInterface.class);
		person.setName("FooBär");
		person.setAge(3);
		person.setWeight(14.5);

		final JavaMyInterfaceWithTypedObjectProperty dummy =
			TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
				JavaMyInterfaceWithTypedObjectProperty.class);

		dummy.setPerson(person);
		assertEquals("FooBär", dummy.getPerson().getName());
		assertEquals(Integer.valueOf(3), dummy.getPerson().getAge());
		assertEquals(Double.valueOf(14.5), dummy.getPerson().getWeight());
	}

	@Test
	public void testTypedInterfaceWithAListsOfTypedObjectNodes() {
		final JavaPersonOwningThingsInterface personOwning =
			TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
				JavaPersonOwningThingsInterface.class);

		final JavaSomeThingInterface thing1 = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
			JavaSomeThingInterface.class);
		final JavaSomeThingInterface thing2 = TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
			JavaSomeThingInterface.class);

		final List<JavaSomeThingInterface> listOfThings = new ArrayList<JavaSomeThingInterface>();
		listOfThings.add(thing1);
		listOfThings.add(thing2);

		personOwning.setSomeThings(listOfThings);

		assertEquals(2, personOwning.getSomeThings().size());
		assertSame(thing1, personOwning.getSomeThings().get(0));
		assertSame(thing2, personOwning.getSomeThings().get(1));
	}

	@Test
	public void testTypedInterfaceWithAListsOfTypedObjectNodesWithoutPriorClassInstantiation() {
		final JavaInterfaceWithATypedObjectProperty owningPerson = TypedObjectNodeFactory.getInstance()
			.getTypedObjectForInterface(JavaInterfaceWithATypedObjectProperty.class);

		final IObjectNode thing1 = new ObjectNode();
		thing1.put("name", new TextNode("foobar"));
		thing1.put("age", new IntNode(15));
		thing1.put("cool", BooleanNode.TRUE);

		owningPerson.put("it", thing1);
		final JavaSomeThingInterface thing1_1 = owningPerson.getIt();
		assertEquals("foobar", thing1_1.getName());
		assertEquals(Integer.valueOf(15), thing1_1.getAge());
		assertEquals(Boolean.TRUE, thing1_1.getCool());
	}

	@Test(expected = ClassCastException.class)
	public void testIfWrongDatatypeInBackingObjectCausesErrorForJSONPrimitives() {
		final JavaInterfaceWithATypedObjectProperty dummy =
			TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(
				JavaInterfaceWithATypedObjectProperty.class);
		dummy.put("number", new TextNode("foo"));
		dummy.getNumber();
	}

	@Test
	public void testTypedInterfaceExtendingTwoTypedInterfaces() {
		final JavaInterfaceImplementingTwoInterfaces personAndThing = TypedObjectNodeFactory.getInstance()
			.getTypedObjectForInterface(JavaInterfaceImplementingTwoInterfaces.class);
		personAndThing.setName("Foobär");
		personAndThing.setAge(5);
		personAndThing.setCool(true);
		personAndThing.setWeight(55.5);
		personAndThing.setIncome(new BigDecimal("123.4"));

		assertEquals("Foobär", personAndThing.getName());
		assertEquals(Integer.valueOf(5), personAndThing.getAge());
		assertEquals(true, personAndThing.getCool());
		assertEquals(Double.valueOf(55.5), personAndThing.getWeight(), 0);
		assertEquals(new BigDecimal("123.4"), personAndThing.getIncome());
	}

	@Test
	public void testDocumentUseCase() {
		final JavaDocument document =
			TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(JavaDocument.class);
		final JavaParagraph annotation1 =
			TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(JavaParagraph.class);
		final JavaParagraph annotation2 =
			TypedObjectNodeFactory.getInstance().getTypedObjectForInterface(JavaParagraph.class);

		annotation1.setPosition(1);
		annotation1.setAnnotationType(JavaAnnotationType.PERSON);
		annotation2.setPosition(11);
		annotation2.setAnnotationType(JavaAnnotationType.PROTEIN);

		final List<JavaParagraph> anAnnotationArray = new ArrayList<JavaParagraph>();
		anAnnotationArray.add(annotation1);
		anAnnotationArray.add(annotation2);

		document.setText("foo bar foo");
		document.setAnnotations(anAnnotationArray);

		assertEquals(Integer.valueOf(1), document.getAnnotations().get(0).getPosition());
		assertEquals(Integer.valueOf(11), document.getAnnotations().get(1).getPosition());
		assertEquals(JavaAnnotationType.PERSON, document.getAnnotations().get(0).getAnnotationType());
		assertEquals(JavaAnnotationType.PROTEIN, document.getAnnotations().get(1).getAnnotationType());
	}
}

interface JavaPersonInterface extends ITypedObjectNode {
	public String getName();

	public void setName(String aNewName);
}

interface JavaPersonModifiableInterface extends ITypedObjectNode {
	public StringBuilder getName();

	public void setName(StringBuilder aNewName);
}

interface JavaPersonWithAgeInterface extends JavaPersonInterface {
	public Integer getAge();

	public void setAge(Integer anAge);
}

interface JavaPersonWithAgeAndWeightInterface extends JavaPersonWithAgeInterface {
	public Double getWeight();

	public void setWeight(Double aWeight);
}

interface JavaPersonWithAgeAndWeightAndIncomeInterface extends JavaPersonWithAgeAndWeightInterface {
	public BigDecimal getIncome();

	public void setIncome(BigDecimal anIncome);
}

interface JavaSomeThingInterface extends ITypedObjectNode {
	public String getName();

	public void setName(String aNewName);

	public Integer getAge();

	public void setAge(Integer aNewAge);

	public Boolean getCool();

	public void setCool(Boolean coolness);
}

interface JavaMyInterfaceWithTypedObjectProperty extends ITypedObjectNode {
	public JavaPersonWithAgeAndWeightInterface getPerson();

	public void setPerson(JavaPersonWithAgeAndWeightInterface aPerson);
}

interface JavaPersonOwningThingsInterface extends JavaPersonInterface {
	public List<JavaSomeThingInterface> getSomeThings();

	public void setSomeThings(List<JavaSomeThingInterface> aListOfSomeThings);
}

interface JavaInterfaceImplementingTwoInterfaces extends JavaPersonWithAgeAndWeightAndIncomeInterface,
		JavaSomeThingInterface {

}

interface JavaInterfaceImplementingTwoTypedInterfacesAndAnotherNoTypedObjectInterface extends
		JavaPersonWithAgeAndWeightAndIncomeInterface, JavaSomeThingInterface, NoTypedInterface {

}

interface JavaInterfaceWithATypedObjectProperty extends ITypedObjectNode {
	public JavaSomeThingInterface getIt();

	public void setIt(JavaSomeThingInterface aTypedObject);

	public Integer getNumber();

	public void setNumber(Integer aNumericNode);
}

interface JavaDocument extends ITypedObjectNode {
	public String getText();

	public void setText(String aText);

	public List<JavaParagraph> getAnnotations();

	public void setAnnotations(List<JavaParagraph> anAnnotationsArray);
}

interface JavaParagraph extends ITypedObjectNode {
	public Integer getPosition();

	public void setPosition(Integer pos);

	public JavaAnnotationType getAnnotationType();

	public void setAnnotationType(JavaAnnotationType type);
}

enum JavaAnnotationType {
	PERSON, PROTEIN;
}