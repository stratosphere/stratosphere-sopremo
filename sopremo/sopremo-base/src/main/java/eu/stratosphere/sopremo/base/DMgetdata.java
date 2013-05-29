package eu.stratosphere.sopremo.base;

import java.io.BufferedReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import eu.stratosphere.nephele.configuration.Configuration;
import eu.stratosphere.pact.generic.io.InputFormat;
import eu.stratosphere.sopremo.expressions.EvaluationExpression;
import eu.stratosphere.sopremo.expressions.EvaluationExpression.ValueExpression;
import eu.stratosphere.sopremo.expressions.InputSelection;
import eu.stratosphere.sopremo.io.JsonParser;
import eu.stratosphere.sopremo.operator.ElementaryOperator;
import eu.stratosphere.sopremo.operator.InputCardinality;
import eu.stratosphere.sopremo.operator.Name;
import eu.stratosphere.sopremo.operator.Property;
import eu.stratosphere.sopremo.pact.JsonCollector;
import eu.stratosphere.sopremo.pact.SopremoMap;
import eu.stratosphere.sopremo.pact.SopremoUtil;
import eu.stratosphere.sopremo.type.IJsonNode;

    /**
     * {@link InputFormat} subclass that wraps the DataMarket access.
	 * This Program fetches the Datasource from DataMarket.
	 * The result is obtained in an Json-file but is treated as string by this
	 * routine.
	 * @author Tieyan Shan	 
	 * @param ds: the "ds" Parameter of the DataMarekt API
	 * @return {@link String} the unprocessed result form the DataMarket API or
	 *         an empty string if something went wrong.
	 */

@Name(verb = "getdata")
@InputCardinality(1)
public class DMgetdata extends ElementaryOperator<DMgetdata>  {

	protected static final String FIRST_VALUE = "ser_key_first_value";
	protected static final String WHOLE_VALUE = "ser_key_whole_value";


	private static EvaluationExpression firstValue;
	private static EvaluationExpression wholeValue;

		
	@SuppressWarnings("null")
	private static String readtoString(String urlString)  {
	    BufferedReader reader = null;
	    String result="";
	    try {
	        URL url = new URL("http://datamarket.com/api/v1/series.xml?ds="+urlString);
	        //reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        	        
	        JsonParser jp=new JsonParser(url);
	        jp.readValueAsTree();
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 
                result=buffer.toString();
	  
	        return result;
	        
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
// as Input only ds part
	@Property(preferred = true)
	@Name(preposition = "ds")
	public void setDMds(EvaluationExpression value) {
		if (value == null)
			throw new NullPointerException("value expression must not be null");
		DMgetdata.firstValue = value.clone();
		DMgetdata.firstValue = firstValue.replace(new InputSelection(0),
				EvaluationExpression.VALUE);
		System.out.println("set first expression " + firstValue.toString());
	}

	//the whole data transfered into EvaluationExpression
	public void setWholeValue(EvaluationExpression value) {
		if (value == null)
			throw new NullPointerException("value expression must not be null");
		//set the ds to String, and combine with the url
		String ds="";		
		ds=firstValue.toString();		
		String dm=readtoString(ds);
		
		//a constructor is added in EvaluationExpression
		//tmp=new EvaluationExpression(dm);
		
		EvaluationExpression tmp=new ValueExpression(dm);
	
		
		DMgetdata.wholeValue = tmp.clone();
		DMgetdata.wholeValue = wholeValue.replace(new InputSelection(0),
				EvaluationExpression.VALUE);
		System.out.println("set first expression " + wholeValue.toString());
	}
	
	
	public static class Implementation extends SopremoMap {

		private static EvaluationExpression firstValue;
		private static EvaluationExpression wholeValue;
		
		@SuppressWarnings("unused")
		private List<EvaluationExpression> projections = new ArrayList<EvaluationExpression>();

		public void open(Configuration parameters) {
			super.open(parameters);
		
			firstValue = SopremoUtil.getObject(parameters, FIRST_VALUE, null);
			wholeValue = SopremoUtil.getObject(parameters, WHOLE_VALUE, null);
							
		}

		@Override
		protected void map(IJsonNode value, JsonCollector out) {
			
			wholeValue.evaluate(value);
			
			for (EvaluationExpression projection : wholeValue)
				out.collect(projection.evaluate(value));
		}
	}

}