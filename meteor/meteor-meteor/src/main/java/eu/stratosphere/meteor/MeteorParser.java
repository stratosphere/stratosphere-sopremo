// $ANTLR 3.5 /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g 2014-03-07 18:16:46
 
package eu.stratosphere.meteor; 

import eu.stratosphere.sopremo.operator.*;
import eu.stratosphere.sopremo.io.*;
import eu.stratosphere.sopremo.query.*;
import eu.stratosphere.sopremo.pact.*;
import eu.stratosphere.sopremo.expressions.*;
import eu.stratosphere.sopremo.function.*;
import eu.stratosphere.sopremo.type.*;
import java.math.*;
import java.util.IdentityHashMap;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;


@SuppressWarnings("all")
public class MeteorParser extends MeteorParserBase {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "AND", "APOSTROPHE", "COMMENT", 
		"DECIMAL", "DIGIT", "ELSE", "ESC_SEQ", "EXPONENT", "EXPRESSION", "FN", 
		"HEX_DIGIT", "ID", "IF", "IN", "INCLUDE", "INTEGER", "JAVAUDF", "LOWER_LETTER", 
		"NOT", "OCTAL_ESC", "OPERATOR", "OR", "QUOTATION", "SIGN", "SLASH", "STAR", 
		"STRING", "UINT", "UNICODE_ESC", "UPPER_LETTER", "VAR", "WS", "'!'", "'!='", 
		"'&&'", "'&'", "'('", "')'", "'+'", "'++'", "','", "'-'", "'--'", "'.'", 
		"':'", "';'", "'<'", "'<='", "'='", "'=='", "'>'", "'>='", "'?'", "'?.'", 
		"'['", "']'", "'false'", "'null'", "'read'", "'true'", "'using'", "'write'", 
		"'{'", "'||'", "'}'", "'~'"
	};
	public static final int EOF=-1;
	public static final int T__36=36;
	public static final int T__37=37;
	public static final int T__38=38;
	public static final int T__39=39;
	public static final int T__40=40;
	public static final int T__41=41;
	public static final int T__42=42;
	public static final int T__43=43;
	public static final int T__44=44;
	public static final int T__45=45;
	public static final int T__46=46;
	public static final int T__47=47;
	public static final int T__48=48;
	public static final int T__49=49;
	public static final int T__50=50;
	public static final int T__51=51;
	public static final int T__52=52;
	public static final int T__53=53;
	public static final int T__54=54;
	public static final int T__55=55;
	public static final int T__56=56;
	public static final int T__57=57;
	public static final int T__58=58;
	public static final int T__59=59;
	public static final int T__60=60;
	public static final int T__61=61;
	public static final int T__62=62;
	public static final int T__63=63;
	public static final int T__64=64;
	public static final int T__65=65;
	public static final int T__66=66;
	public static final int T__67=67;
	public static final int T__68=68;
	public static final int T__69=69;
	public static final int AND=4;
	public static final int APOSTROPHE=5;
	public static final int COMMENT=6;
	public static final int DECIMAL=7;
	public static final int DIGIT=8;
	public static final int ELSE=9;
	public static final int ESC_SEQ=10;
	public static final int EXPONENT=11;
	public static final int EXPRESSION=12;
	public static final int FN=13;
	public static final int HEX_DIGIT=14;
	public static final int ID=15;
	public static final int IF=16;
	public static final int IN=17;
	public static final int INCLUDE=18;
	public static final int INTEGER=19;
	public static final int JAVAUDF=20;
	public static final int LOWER_LETTER=21;
	public static final int NOT=22;
	public static final int OCTAL_ESC=23;
	public static final int OPERATOR=24;
	public static final int OR=25;
	public static final int QUOTATION=26;
	public static final int SIGN=27;
	public static final int SLASH=28;
	public static final int STAR=29;
	public static final int STRING=30;
	public static final int UINT=31;
	public static final int UNICODE_ESC=32;
	public static final int UPPER_LETTER=33;
	public static final int VAR=34;
	public static final int WS=35;

	// delegates
	public MeteorParserBase[] getDelegates() {
		return new MeteorParserBase[] {};
	}

	// delegators


	public MeteorParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public MeteorParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	protected TreeAdaptor adaptor = new CommonTreeAdaptor();

	public void setTreeAdaptor(TreeAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	public TreeAdaptor getTreeAdaptor() {
		return adaptor;
	}
	@Override public String[] getTokenNames() { return MeteorParser.tokenNames; }
	@Override public String getGrammarFileName() { return "/home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g"; }


	  private Stack<String> paraphrase = new Stack<String>();

	  private boolean setInnerOutput(Token VAR, Operator<?> op) {
		  JsonStreamExpression output = new JsonStreamExpression(op.getOutput(objectCreation_stack.peek().mappings.size()));
		  objectCreation_stack.peek().mappings.add(new ObjectCreation.TagMapping(output, new JsonStreamExpression(op)));
		  getVariableRegistry().getRegistry(1).put(VAR.getText(), output);
		  return true;
		}
	  
	  protected EvaluationExpression getInputSelection(Token inputVar) throws RecognitionException {
	      return getVariableSafely(inputVar).toInputSelection(operator_stack.peek().result);
	  }

	  public void parseSinks() throws RecognitionException {
	    script();
	  }


	public static class script_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "script"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:97:1: script : ( statement ';' )+ ->;
	public final MeteorParser.script_return script() throws RecognitionException {
		MeteorParser.script_return retval = new MeteorParser.script_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token char_literal2=null;
		ParserRuleReturnScope statement1 =null;

		EvaluationExpression char_literal2_tree=null;
		RewriteRuleTokenStream stream_49=new RewriteRuleTokenStream(adaptor,"token 49");
		RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:98:2: ( ( statement ';' )+ ->)
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:98:5: ( statement ';' )+
			{
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:98:5: ( statement ';' )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==ID||LA1_0==VAR||LA1_0==49||LA1_0==62||(LA1_0 >= 64 && LA1_0 <= 65)) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:98:6: statement ';'
					{
					pushFollow(FOLLOW_statement_in_script131);
					statement1=statement();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_statement.add(statement1.getTree());
					char_literal2=(Token)match(input,49,FOLLOW_49_in_script133); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_49.add(char_literal2);

					}
					break;

				default :
					if ( cnt1 >= 1 ) break loop1;
					if (state.backtracking>0) {state.failed=true; return retval;}
					EarlyExitException eee = new EarlyExitException(1, input);
					throw eee;
				}
				cnt1++;
			}

			// AST REWRITE
			// elements: 
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 98:22: ->
			{
				root_0 = null;
			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "script"


	public static class statement_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "statement"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:100:1: statement : ( operator | packageImport | adhocSource | definition ||m= functionCall ) ->;
	public final MeteorParser.statement_return statement() throws RecognitionException {
		MeteorParser.statement_return retval = new MeteorParser.statement_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		ParserRuleReturnScope m =null;
		ParserRuleReturnScope operator3 =null;
		ParserRuleReturnScope packageImport4 =null;
		ParserRuleReturnScope adhocSource5 =null;
		ParserRuleReturnScope definition6 =null;

		RewriteRuleSubtreeStream stream_functionCall=new RewriteRuleSubtreeStream(adaptor,"rule functionCall");
		RewriteRuleSubtreeStream stream_definition=new RewriteRuleSubtreeStream(adaptor,"rule definition");
		RewriteRuleSubtreeStream stream_adhocSource=new RewriteRuleSubtreeStream(adaptor,"rule adhocSource");
		RewriteRuleSubtreeStream stream_operator=new RewriteRuleSubtreeStream(adaptor,"rule operator");
		RewriteRuleSubtreeStream stream_packageImport=new RewriteRuleSubtreeStream(adaptor,"rule packageImport");

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:101:2: ( ( operator | packageImport | adhocSource | definition ||m= functionCall ) ->)
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:101:4: ( operator | packageImport | adhocSource | definition ||m= functionCall )
			{
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:101:4: ( operator | packageImport | adhocSource | definition ||m= functionCall )
			int alt2=6;
			switch ( input.LA(1) ) {
			case VAR:
				{
				int LA2_1 = input.LA(2);
				if ( (LA2_1==52) ) {
					int LA2_6 = input.LA(3);
					if ( (LA2_6==ID||LA2_6==62) ) {
						alt2=1;
					}
					else if ( (LA2_6==58) ) {
						alt2=3;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 2, 6, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

				}
				else if ( (LA2_1==44) ) {
					alt2=1;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return retval;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 2, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case 62:
			case 65:
				{
				alt2=1;
				}
				break;
			case ID:
				{
				switch ( input.LA(2) ) {
				case 48:
					{
					int LA2_7 = input.LA(3);
					if ( (LA2_7==ID) ) {
						int LA2_11 = input.LA(4);
						if ( (LA2_11==40) ) {
							alt2=6;
						}
						else if ( (LA2_11==ID||LA2_11==VAR||LA2_11==49) ) {
							alt2=1;
						}

						else {
							if (state.backtracking>0) {state.failed=true; return retval;}
							int nvaeMark = input.mark();
							try {
								for (int nvaeConsume = 0; nvaeConsume < 4 - 1; nvaeConsume++) {
									input.consume();
								}
								NoViableAltException nvae =
									new NoViableAltException("", 2, 11, input);
								throw nvae;
							} finally {
								input.rewind(nvaeMark);
							}
						}

					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 2, 7, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

					}
					break;
				case 52:
					{
					alt2=4;
					}
					break;
				case 40:
					{
					alt2=6;
					}
					break;
				case ID:
				case VAR:
				case 49:
					{
					alt2=1;
					}
					break;
				default:
					if (state.backtracking>0) {state.failed=true; return retval;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 2, 3, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}
				}
				break;
			case 64:
				{
				alt2=2;
				}
				break;
			case 49:
				{
				alt2=5;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 2, 0, input);
				throw nvae;
			}
			switch (alt2) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:101:5: operator
					{
					pushFollow(FOLLOW_operator_in_statement147);
					operator3=operator();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_operator.add(operator3.getTree());
					}
					break;
				case 2 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:101:16: packageImport
					{
					pushFollow(FOLLOW_packageImport_in_statement151);
					packageImport4=packageImport();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_packageImport.add(packageImport4.getTree());
					}
					break;
				case 3 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:101:32: adhocSource
					{
					pushFollow(FOLLOW_adhocSource_in_statement155);
					adhocSource5=adhocSource();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_adhocSource.add(adhocSource5.getTree());
					}
					break;
				case 4 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:101:46: definition
					{
					pushFollow(FOLLOW_definition_in_statement159);
					definition6=definition();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_definition.add(definition6.getTree());
					}
					break;
				case 5 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:103:2: 
					{
					}
					break;
				case 6 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:103:4: m= functionCall
					{
					pushFollow(FOLLOW_functionCall_in_statement169);
					m=functionCall();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_functionCall.add(m.getTree());
					if ( state.backtracking==0 ) { (m!=null?((EvaluationExpression)m.getTree()):null).evaluate(MissingNode.getInstance()); }
					}
					break;

			}

			// AST REWRITE
			// elements: 
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 103:69: ->
			{
				root_0 = null;
			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "statement"


	public static class packageImport_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "packageImport"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:105:1: packageImport : 'using' packageName= ID ( ',' additionalPackage= ID )* ->;
	public final MeteorParser.packageImport_return packageImport() throws RecognitionException {
		MeteorParser.packageImport_return retval = new MeteorParser.packageImport_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token packageName=null;
		Token additionalPackage=null;
		Token string_literal7=null;
		Token char_literal8=null;

		EvaluationExpression packageName_tree=null;
		EvaluationExpression additionalPackage_tree=null;
		EvaluationExpression string_literal7_tree=null;
		EvaluationExpression char_literal8_tree=null;
		RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
		RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
		RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:106:3: ( 'using' packageName= ID ( ',' additionalPackage= ID )* ->)
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:106:6: 'using' packageName= ID ( ',' additionalPackage= ID )*
			{
			string_literal7=(Token)match(input,64,FOLLOW_64_in_packageImport186); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_64.add(string_literal7);

			packageName=(Token)match(input,ID,FOLLOW_ID_in_packageImport190); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_ID.add(packageName);

			if ( state.backtracking==0 ) { getPackageManager().importPackage((packageName!=null?packageName.getText():null)); }
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:107:6: ( ',' additionalPackage= ID )*
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( (LA3_0==44) ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:107:7: ',' additionalPackage= ID
					{
					char_literal8=(Token)match(input,44,FOLLOW_44_in_packageImport201); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_44.add(char_literal8);

					additionalPackage=(Token)match(input,ID,FOLLOW_ID_in_packageImport205); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_ID.add(additionalPackage);

					if ( state.backtracking==0 ) { getPackageManager().importPackage((additionalPackage!=null?additionalPackage.getText():null)); }
					}
					break;

				default :
					break loop3;
				}
			}

			// AST REWRITE
			// elements: 
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 107:98: ->
			{
				root_0 = null;
			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "packageImport"


	public static class definition_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "definition"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:109:1: definition : ( ( ID '=' FN )=> functionDefinition | ( ID '=' JAVAUDF )=> javaudf | constantDefinition );
	public final MeteorParser.definition_return definition() throws RecognitionException {
		MeteorParser.definition_return retval = new MeteorParser.definition_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		ParserRuleReturnScope functionDefinition9 =null;
		ParserRuleReturnScope javaudf10 =null;
		ParserRuleReturnScope constantDefinition11 =null;


		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:110:3: ( ( ID '=' FN )=> functionDefinition | ( ID '=' JAVAUDF )=> javaudf | constantDefinition )
			int alt4=3;
			int LA4_0 = input.LA(1);
			if ( (LA4_0==ID) ) {
				int LA4_1 = input.LA(2);
				if ( (synpred1_Meteor()) ) {
					alt4=1;
				}
				else if ( (synpred2_Meteor()) ) {
					alt4=2;
				}
				else if ( (true) ) {
					alt4=3;
				}

			}

			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 4, 0, input);
				throw nvae;
			}

			switch (alt4) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:110:5: ( ID '=' FN )=> functionDefinition
					{
					root_0 = (EvaluationExpression)adaptor.nil();


					pushFollow(FOLLOW_functionDefinition_in_definition230);
					functionDefinition9=functionDefinition();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, functionDefinition9.getTree());

					}
					break;
				case 2 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:111:5: ( ID '=' JAVAUDF )=> javaudf
					{
					root_0 = (EvaluationExpression)adaptor.nil();


					pushFollow(FOLLOW_javaudf_in_definition246);
					javaudf10=javaudf();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, javaudf10.getTree());

					}
					break;
				case 3 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:112:5: constantDefinition
					{
					root_0 = (EvaluationExpression)adaptor.nil();


					pushFollow(FOLLOW_constantDefinition_in_definition252);
					constantDefinition11=constantDefinition();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, constantDefinition11.getTree());

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "definition"


	public static class functionDefinition_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "functionDefinition"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:114:1: functionDefinition : name= ID '=' func= inlineFunction ->;
	public final MeteorParser.functionDefinition_return functionDefinition() throws RecognitionException {
		MeteorParser.functionDefinition_return retval = new MeteorParser.functionDefinition_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token name=null;
		Token char_literal12=null;
		ParserRuleReturnScope func =null;

		EvaluationExpression name_tree=null;
		EvaluationExpression char_literal12_tree=null;
		RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
		RewriteRuleTokenStream stream_52=new RewriteRuleTokenStream(adaptor,"token 52");
		RewriteRuleSubtreeStream stream_inlineFunction=new RewriteRuleSubtreeStream(adaptor,"rule inlineFunction");

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:115:3: (name= ID '=' func= inlineFunction ->)
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:115:5: name= ID '=' func= inlineFunction
			{
			name=(Token)match(input,ID,FOLLOW_ID_in_functionDefinition264); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_ID.add(name);

			char_literal12=(Token)match(input,52,FOLLOW_52_in_functionDefinition266); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_52.add(char_literal12);

			pushFollow(FOLLOW_inlineFunction_in_functionDefinition270);
			func=inlineFunction();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_inlineFunction.add(func.getTree());
			if ( state.backtracking==0 ) { addFunction((name!=null?name.getText():null), (func!=null?((MeteorParser.inlineFunction_return)func).func:null)); }
			// AST REWRITE
			// elements: 
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 115:78: ->
			{
				root_0 = null;
			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "functionDefinition"


	public static class constantDefinition_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "constantDefinition"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:117:1: constantDefinition : name= ID '=' exp= ternaryExpression ->;
	public final MeteorParser.constantDefinition_return constantDefinition() throws RecognitionException {
		MeteorParser.constantDefinition_return retval = new MeteorParser.constantDefinition_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token name=null;
		Token char_literal13=null;
		ParserRuleReturnScope exp =null;

		EvaluationExpression name_tree=null;
		EvaluationExpression char_literal13_tree=null;
		RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
		RewriteRuleTokenStream stream_52=new RewriteRuleTokenStream(adaptor,"token 52");
		RewriteRuleSubtreeStream stream_ternaryExpression=new RewriteRuleSubtreeStream(adaptor,"rule ternaryExpression");

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:118:3: (name= ID '=' exp= ternaryExpression ->)
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:118:5: name= ID '=' exp= ternaryExpression
			{
			name=(Token)match(input,ID,FOLLOW_ID_in_constantDefinition289); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_ID.add(name);

			char_literal13=(Token)match(input,52,FOLLOW_52_in_constantDefinition291); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_52.add(char_literal13);

			pushFollow(FOLLOW_ternaryExpression_in_constantDefinition295);
			exp=ternaryExpression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_ternaryExpression.add(exp.getTree());
			if ( state.backtracking==0 ) { addConstant((name!=null?name.getText():null), (exp!=null?((EvaluationExpression)exp.getTree()):null)); }
			// AST REWRITE
			// elements: 
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 118:79: ->
			{
				root_0 = null;
			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "constantDefinition"


	public static class inlineFunction_return extends ParserRuleReturnScope {
		public ExpressionFunction func;
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "inlineFunction"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:120:1: inlineFunction returns [ExpressionFunction func] : FN '(' (param= ID ( ',' param= ID )* )? ')' '{' def= expression '}' ->;
	public final MeteorParser.inlineFunction_return inlineFunction() throws RecognitionException {
		MeteorParser.inlineFunction_return retval = new MeteorParser.inlineFunction_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token param=null;
		Token FN14=null;
		Token char_literal15=null;
		Token char_literal16=null;
		Token char_literal17=null;
		Token char_literal18=null;
		Token char_literal19=null;
		ParserRuleReturnScope def =null;

		EvaluationExpression param_tree=null;
		EvaluationExpression FN14_tree=null;
		EvaluationExpression char_literal15_tree=null;
		EvaluationExpression char_literal16_tree=null;
		EvaluationExpression char_literal17_tree=null;
		EvaluationExpression char_literal18_tree=null;
		EvaluationExpression char_literal19_tree=null;
		RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
		RewriteRuleTokenStream stream_68=new RewriteRuleTokenStream(adaptor,"token 68");
		RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
		RewriteRuleTokenStream stream_FN=new RewriteRuleTokenStream(adaptor,"token FN");
		RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
		RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
		RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
		RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");

		 List<Token> params = new ArrayList(); 
		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:122:3: ( FN '(' (param= ID ( ',' param= ID )* )? ')' '{' def= expression '}' ->)
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:122:5: FN '(' (param= ID ( ',' param= ID )* )? ')' '{' def= expression '}'
			{
			FN14=(Token)match(input,FN,FOLLOW_FN_in_inlineFunction321); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_FN.add(FN14);

			char_literal15=(Token)match(input,40,FOLLOW_40_in_inlineFunction323); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_40.add(char_literal15);

			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:123:3: (param= ID ( ',' param= ID )* )?
			int alt6=2;
			int LA6_0 = input.LA(1);
			if ( (LA6_0==ID) ) {
				alt6=1;
			}
			switch (alt6) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:123:4: param= ID ( ',' param= ID )*
					{
					param=(Token)match(input,ID,FOLLOW_ID_in_inlineFunction332); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_ID.add(param);

					if ( state.backtracking==0 ) { params.add(param); }
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:124:3: ( ',' param= ID )*
					loop5:
					while (true) {
						int alt5=2;
						int LA5_0 = input.LA(1);
						if ( (LA5_0==44) ) {
							alt5=1;
						}

						switch (alt5) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:124:4: ',' param= ID
							{
							char_literal16=(Token)match(input,44,FOLLOW_44_in_inlineFunction339); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_44.add(char_literal16);

							param=(Token)match(input,ID,FOLLOW_ID_in_inlineFunction343); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_ID.add(param);

							if ( state.backtracking==0 ) { params.add(param); }
							}
							break;

						default :
							break loop5;
						}
					}

					}
					break;

			}

			char_literal17=(Token)match(input,41,FOLLOW_41_in_inlineFunction354); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_41.add(char_literal17);

			if ( state.backtracking==0 ) { 
			    addConstantScope();
			    for(int index = 0; index < params.size(); index++) 
			      this.getConstantRegistry().put(params.get(index).getText(), new InputSelection(index)); 
			  }
			char_literal18=(Token)match(input,66,FOLLOW_66_in_inlineFunction364); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_66.add(char_literal18);

			pushFollow(FOLLOW_expression_in_inlineFunction368);
			def=expression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_expression.add(def.getTree());
			char_literal19=(Token)match(input,68,FOLLOW_68_in_inlineFunction370); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_68.add(char_literal19);

			if ( state.backtracking==0 ) { 
			    retval.func = new ExpressionFunction(params.size(), (def!=null?((EvaluationExpression)def.getTree()):null));
			    removeConstantScope(); 
			  }
			// AST REWRITE
			// elements: 
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 135:5: ->
			{
				root_0 = null;
			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "inlineFunction"


	public static class javaudf_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "javaudf"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:137:1: javaudf : name= ID '=' JAVAUDF '(' path= STRING ')' ->;
	public final MeteorParser.javaudf_return javaudf() throws RecognitionException {
		MeteorParser.javaudf_return retval = new MeteorParser.javaudf_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token name=null;
		Token path=null;
		Token char_literal20=null;
		Token JAVAUDF21=null;
		Token char_literal22=null;
		Token char_literal23=null;

		EvaluationExpression name_tree=null;
		EvaluationExpression path_tree=null;
		EvaluationExpression char_literal20_tree=null;
		EvaluationExpression JAVAUDF21_tree=null;
		EvaluationExpression char_literal22_tree=null;
		EvaluationExpression char_literal23_tree=null;
		RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
		RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
		RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
		RewriteRuleTokenStream stream_52=new RewriteRuleTokenStream(adaptor,"token 52");
		RewriteRuleTokenStream stream_JAVAUDF=new RewriteRuleTokenStream(adaptor,"token JAVAUDF");
		RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:138:3: (name= ID '=' JAVAUDF '(' path= STRING ')' ->)
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:138:5: name= ID '=' JAVAUDF '(' path= STRING ')'
			{
			name=(Token)match(input,ID,FOLLOW_ID_in_javaudf390); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_ID.add(name);

			char_literal20=(Token)match(input,52,FOLLOW_52_in_javaudf392); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_52.add(char_literal20);

			JAVAUDF21=(Token)match(input,JAVAUDF,FOLLOW_JAVAUDF_in_javaudf394); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_JAVAUDF.add(JAVAUDF21);

			char_literal22=(Token)match(input,40,FOLLOW_40_in_javaudf396); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_40.add(char_literal22);

			path=(Token)match(input,STRING,FOLLOW_STRING_in_javaudf400); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_STRING.add(path);

			char_literal23=(Token)match(input,41,FOLLOW_41_in_javaudf402); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_41.add(char_literal23);

			if ( state.backtracking==0 ) { addFunction(name.getText(), path.getText()); }
			// AST REWRITE
			// elements: 
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 139:53: ->
			{
				root_0 = null;
			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "javaudf"


	protected static class contextAwareExpression_scope {
		EvaluationExpression context;
	}
	protected Stack<contextAwareExpression_scope> contextAwareExpression_stack = new Stack<contextAwareExpression_scope>();

	public static class contextAwareExpression_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "contextAwareExpression"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:141:1: contextAwareExpression[EvaluationExpression contextExpression] : ternaryExpression ;
	public final MeteorParser.contextAwareExpression_return contextAwareExpression(EvaluationExpression contextExpression) throws RecognitionException {
		contextAwareExpression_stack.push(new contextAwareExpression_scope());
		MeteorParser.contextAwareExpression_return retval = new MeteorParser.contextAwareExpression_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		ParserRuleReturnScope ternaryExpression24 =null;


		 contextAwareExpression_stack.peek().context = contextExpression; 
		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:144:3: ( ternaryExpression )
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:144:5: ternaryExpression
			{
			root_0 = (EvaluationExpression)adaptor.nil();


			pushFollow(FOLLOW_ternaryExpression_in_contextAwareExpression430);
			ternaryExpression24=ternaryExpression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, ternaryExpression24.getTree());

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
			contextAwareExpression_stack.pop();
		}
		return retval;
	}
	// $ANTLR end "contextAwareExpression"


	public static class expression_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "expression"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:146:1: expression : ( ( ID ( ID | VAR ) )=> operatorExpression | ternaryExpression );
	public final MeteorParser.expression_return expression() throws RecognitionException {
		MeteorParser.expression_return retval = new MeteorParser.expression_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		ParserRuleReturnScope operatorExpression25 =null;
		ParserRuleReturnScope ternaryExpression26 =null;


		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:147:3: ( ( ID ( ID | VAR ) )=> operatorExpression | ternaryExpression )
			int alt7=2;
			int LA7_0 = input.LA(1);
			if ( (LA7_0==VAR) ) {
				int LA7_1 = input.LA(2);
				if ( (LA7_1==52) && (synpred3_Meteor())) {
					alt7=1;
				}
				else if ( (LA7_1==44) ) {
					int LA7_7 = input.LA(3);
					if ( (LA7_7==VAR) ) {
						int LA7_8 = input.LA(4);
						if ( (synpred3_Meteor()) ) {
							alt7=1;
						}
						else if ( (true) ) {
							alt7=2;
						}

					}
					else if ( (LA7_7==DECIMAL||LA7_7==FN||LA7_7==ID||LA7_7==INTEGER||(LA7_7 >= STRING && LA7_7 <= UINT)||LA7_7==36||(LA7_7 >= 39 && LA7_7 <= 40)||LA7_7==43||LA7_7==46||(LA7_7 >= 58 && LA7_7 <= 63)||(LA7_7 >= 65 && LA7_7 <= 66)||(LA7_7 >= 68 && LA7_7 <= 69)) ) {
						alt7=2;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 7, 7, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

				}
				else if ( (LA7_1==AND||(LA7_1 >= ID && LA7_1 <= IN)||LA7_1==NOT||LA7_1==OR||(LA7_1 >= SLASH && LA7_1 <= STAR)||(LA7_1 >= 37 && LA7_1 <= 38)||(LA7_1 >= 41 && LA7_1 <= 42)||LA7_1==45||LA7_1==47||(LA7_1 >= 50 && LA7_1 <= 51)||(LA7_1 >= 53 && LA7_1 <= 59)||(LA7_1 >= 67 && LA7_1 <= 68)) ) {
					alt7=2;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return retval;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 7, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}
			else if ( (LA7_0==62) && (synpred3_Meteor())) {
				alt7=1;
			}
			else if ( (LA7_0==65) && (synpred3_Meteor())) {
				alt7=1;
			}
			else if ( (LA7_0==ID) ) {
				int LA7_4 = input.LA(2);
				if ( (synpred3_Meteor()) ) {
					alt7=1;
				}
				else if ( (true) ) {
					alt7=2;
				}

			}
			else if ( (LA7_0==DECIMAL||LA7_0==FN||LA7_0==INTEGER||(LA7_0 >= STRING && LA7_0 <= UINT)||LA7_0==36||(LA7_0 >= 39 && LA7_0 <= 40)||LA7_0==43||LA7_0==46||LA7_0==58||(LA7_0 >= 60 && LA7_0 <= 61)||LA7_0==63||LA7_0==66||LA7_0==69) ) {
				alt7=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 7, 0, input);
				throw nvae;
			}

			switch (alt7) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:147:5: ( ID ( ID | VAR ) )=> operatorExpression
					{
					root_0 = (EvaluationExpression)adaptor.nil();


					pushFollow(FOLLOW_operatorExpression_in_expression453);
					operatorExpression25=operatorExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, operatorExpression25.getTree());

					}
					break;
				case 2 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:148:5: ternaryExpression
					{
					root_0 = (EvaluationExpression)adaptor.nil();


					pushFollow(FOLLOW_ternaryExpression_in_expression459);
					ternaryExpression26=ternaryExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, ternaryExpression26.getTree());

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "expression"


	public static class ternaryExpression_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "ternaryExpression"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:150:1: ternaryExpression : ( ( orExpression '?' )=>ifClause= orExpression '?' (ifExpr= orExpression )? ':' elseExpr= orExpression -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause) | ( orExpression IF )=>ifExpr2= orExpression IF ifClause2= orExpression -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause2 $ifExpr2) | orExpression );
	public final MeteorParser.ternaryExpression_return ternaryExpression() throws RecognitionException {
		MeteorParser.ternaryExpression_return retval = new MeteorParser.ternaryExpression_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token char_literal27=null;
		Token char_literal28=null;
		Token IF29=null;
		ParserRuleReturnScope ifClause =null;
		ParserRuleReturnScope ifExpr =null;
		ParserRuleReturnScope elseExpr =null;
		ParserRuleReturnScope ifExpr2 =null;
		ParserRuleReturnScope ifClause2 =null;
		ParserRuleReturnScope orExpression30 =null;

		EvaluationExpression char_literal27_tree=null;
		EvaluationExpression char_literal28_tree=null;
		EvaluationExpression IF29_tree=null;
		RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
		RewriteRuleTokenStream stream_56=new RewriteRuleTokenStream(adaptor,"token 56");
		RewriteRuleTokenStream stream_IF=new RewriteRuleTokenStream(adaptor,"token IF");
		RewriteRuleSubtreeStream stream_orExpression=new RewriteRuleSubtreeStream(adaptor,"rule orExpression");

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:151:2: ( ( orExpression '?' )=>ifClause= orExpression '?' (ifExpr= orExpression )? ':' elseExpr= orExpression -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause) | ( orExpression IF )=>ifExpr2= orExpression IF ifClause2= orExpression -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause2 $ifExpr2) | orExpression )
			int alt9=3;
			switch ( input.LA(1) ) {
			case 43:
				{
				int LA9_1 = input.LA(2);
				if ( (synpred4_Meteor()) ) {
					alt9=1;
				}
				else if ( (synpred5_Meteor()) ) {
					alt9=2;
				}
				else if ( (true) ) {
					alt9=3;
				}

				}
				break;
			case 46:
				{
				int LA9_2 = input.LA(2);
				if ( (synpred4_Meteor()) ) {
					alt9=1;
				}
				else if ( (synpred5_Meteor()) ) {
					alt9=2;
				}
				else if ( (true) ) {
					alt9=3;
				}

				}
				break;
			case 36:
			case 69:
				{
				int LA9_3 = input.LA(2);
				if ( (synpred4_Meteor()) ) {
					alt9=1;
				}
				else if ( (synpred5_Meteor()) ) {
					alt9=2;
				}
				else if ( (true) ) {
					alt9=3;
				}

				}
				break;
			case 40:
				{
				int LA9_4 = input.LA(2);
				if ( (synpred4_Meteor()) ) {
					alt9=1;
				}
				else if ( (synpred5_Meteor()) ) {
					alt9=2;
				}
				else if ( (true) ) {
					alt9=3;
				}

				}
				break;
			case ID:
				{
				int LA9_5 = input.LA(2);
				if ( (synpred4_Meteor()) ) {
					alt9=1;
				}
				else if ( (synpred5_Meteor()) ) {
					alt9=2;
				}
				else if ( (true) ) {
					alt9=3;
				}

				}
				break;
			case 39:
				{
				int LA9_6 = input.LA(2);
				if ( (synpred4_Meteor()) ) {
					alt9=1;
				}
				else if ( (synpred5_Meteor()) ) {
					alt9=2;
				}
				else if ( (true) ) {
					alt9=3;
				}

				}
				break;
			case FN:
				{
				int LA9_7 = input.LA(2);
				if ( (synpred4_Meteor()) ) {
					alt9=1;
				}
				else if ( (synpred5_Meteor()) ) {
					alt9=2;
				}
				else if ( (true) ) {
					alt9=3;
				}

				}
				break;
			case 63:
				{
				int LA9_8 = input.LA(2);
				if ( (synpred4_Meteor()) ) {
					alt9=1;
				}
				else if ( (synpred5_Meteor()) ) {
					alt9=2;
				}
				else if ( (true) ) {
					alt9=3;
				}

				}
				break;
			case 60:
				{
				int LA9_9 = input.LA(2);
				if ( (synpred4_Meteor()) ) {
					alt9=1;
				}
				else if ( (synpred5_Meteor()) ) {
					alt9=2;
				}
				else if ( (true) ) {
					alt9=3;
				}

				}
				break;
			case DECIMAL:
				{
				int LA9_10 = input.LA(2);
				if ( (synpred4_Meteor()) ) {
					alt9=1;
				}
				else if ( (synpred5_Meteor()) ) {
					alt9=2;
				}
				else if ( (true) ) {
					alt9=3;
				}

				}
				break;
			case STRING:
				{
				int LA9_11 = input.LA(2);
				if ( (synpred4_Meteor()) ) {
					alt9=1;
				}
				else if ( (synpred5_Meteor()) ) {
					alt9=2;
				}
				else if ( (true) ) {
					alt9=3;
				}

				}
				break;
			case UINT:
				{
				int LA9_12 = input.LA(2);
				if ( (synpred4_Meteor()) ) {
					alt9=1;
				}
				else if ( (synpred5_Meteor()) ) {
					alt9=2;
				}
				else if ( (true) ) {
					alt9=3;
				}

				}
				break;
			case INTEGER:
				{
				int LA9_13 = input.LA(2);
				if ( (synpred4_Meteor()) ) {
					alt9=1;
				}
				else if ( (synpred5_Meteor()) ) {
					alt9=2;
				}
				else if ( (true) ) {
					alt9=3;
				}

				}
				break;
			case 61:
				{
				int LA9_14 = input.LA(2);
				if ( (synpred4_Meteor()) ) {
					alt9=1;
				}
				else if ( (synpred5_Meteor()) ) {
					alt9=2;
				}
				else if ( (true) ) {
					alt9=3;
				}

				}
				break;
			case VAR:
				{
				int LA9_15 = input.LA(2);
				if ( (synpred4_Meteor()) ) {
					alt9=1;
				}
				else if ( (synpred5_Meteor()) ) {
					alt9=2;
				}
				else if ( (true) ) {
					alt9=3;
				}

				}
				break;
			case 58:
				{
				int LA9_16 = input.LA(2);
				if ( (synpred4_Meteor()) ) {
					alt9=1;
				}
				else if ( (synpred5_Meteor()) ) {
					alt9=2;
				}
				else if ( (true) ) {
					alt9=3;
				}

				}
				break;
			case 66:
				{
				int LA9_17 = input.LA(2);
				if ( (synpred4_Meteor()) ) {
					alt9=1;
				}
				else if ( (synpred5_Meteor()) ) {
					alt9=2;
				}
				else if ( (true) ) {
					alt9=3;
				}

				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 9, 0, input);
				throw nvae;
			}
			switch (alt9) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:151:4: ( orExpression '?' )=>ifClause= orExpression '?' (ifExpr= orExpression )? ':' elseExpr= orExpression
					{
					pushFollow(FOLLOW_orExpression_in_ternaryExpression477);
					ifClause=orExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_orExpression.add(ifClause.getTree());
					char_literal27=(Token)match(input,56,FOLLOW_56_in_ternaryExpression479); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_56.add(char_literal27);

					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:151:57: (ifExpr= orExpression )?
					int alt8=2;
					int LA8_0 = input.LA(1);
					if ( (LA8_0==DECIMAL||LA8_0==FN||LA8_0==ID||LA8_0==INTEGER||(LA8_0 >= STRING && LA8_0 <= UINT)||LA8_0==VAR||LA8_0==36||(LA8_0 >= 39 && LA8_0 <= 40)||LA8_0==43||LA8_0==46||LA8_0==58||(LA8_0 >= 60 && LA8_0 <= 61)||LA8_0==63||LA8_0==66||LA8_0==69) ) {
						alt8=1;
					}
					switch (alt8) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:151:57: ifExpr= orExpression
							{
							pushFollow(FOLLOW_orExpression_in_ternaryExpression483);
							ifExpr=orExpression();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) stream_orExpression.add(ifExpr.getTree());
							}
							break;

					}

					char_literal28=(Token)match(input,48,FOLLOW_48_in_ternaryExpression486); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_48.add(char_literal28);

					pushFollow(FOLLOW_orExpression_in_ternaryExpression490);
					elseExpr=orExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_orExpression.add(elseExpr.getTree());
					// AST REWRITE
					// elements: ifClause
					// token labels: 
					// rule labels: retval, ifClause
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);
					RewriteRuleSubtreeStream stream_ifClause=new RewriteRuleSubtreeStream(adaptor,"rule ifClause",ifClause!=null?ifClause.getTree():null);

					root_0 = (EvaluationExpression)adaptor.nil();
					// 152:2: -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause)
					{
						// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:152:5: ^( EXPRESSION[\"TernaryExpression\"] $ifClause)
						{
						EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
						root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "TernaryExpression"), root_1);
						adaptor.addChild(root_1, stream_ifClause.nextTree());
						adaptor.addChild(root_1,  ifExpr == null ? (ifClause!=null?((EvaluationExpression)ifClause.getTree()):null) : (ifExpr!=null?((EvaluationExpression)ifExpr.getTree()):null) );
						adaptor.addChild(root_1,  (elseExpr!=null?((EvaluationExpression)elseExpr.getTree()):null) );
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;
				case 2 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:153:4: ( orExpression IF )=>ifExpr2= orExpression IF ifClause2= orExpression
					{
					pushFollow(FOLLOW_orExpression_in_ternaryExpression519);
					ifExpr2=orExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_orExpression.add(ifExpr2.getTree());
					IF29=(Token)match(input,IF,FOLLOW_IF_in_ternaryExpression521); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_IF.add(IF29);

					pushFollow(FOLLOW_orExpression_in_ternaryExpression525);
					ifClause2=orExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_orExpression.add(ifClause2.getTree());
					// AST REWRITE
					// elements: ifExpr2, ifClause2
					// token labels: 
					// rule labels: retval, ifExpr2, ifClause2
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);
					RewriteRuleSubtreeStream stream_ifExpr2=new RewriteRuleSubtreeStream(adaptor,"rule ifExpr2",ifExpr2!=null?ifExpr2.getTree():null);
					RewriteRuleSubtreeStream stream_ifClause2=new RewriteRuleSubtreeStream(adaptor,"rule ifClause2",ifClause2!=null?ifClause2.getTree():null);

					root_0 = (EvaluationExpression)adaptor.nil();
					// 154:3: -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause2 $ifExpr2)
					{
						// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:154:6: ^( EXPRESSION[\"TernaryExpression\"] $ifClause2 $ifExpr2)
						{
						EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
						root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "TernaryExpression"), root_1);
						adaptor.addChild(root_1, stream_ifClause2.nextTree());
						adaptor.addChild(root_1, stream_ifExpr2.nextTree());
						adaptor.addChild(root_1,  EvaluationExpression.VALUE );
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;
				case 3 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:155:5: orExpression
					{
					root_0 = (EvaluationExpression)adaptor.nil();


					pushFollow(FOLLOW_orExpression_in_ternaryExpression548);
					orExpression30=orExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, orExpression30.getTree());

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "ternaryExpression"


	public static class orExpression_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "orExpression"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:157:1: orExpression :exprs+= andExpression ( ( OR | '||' ) exprs+= andExpression )* -> { $exprs.size() == 1 }? ->;
	public final MeteorParser.orExpression_return orExpression() throws RecognitionException {
		MeteorParser.orExpression_return retval = new MeteorParser.orExpression_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token OR31=null;
		Token string_literal32=null;
		List<Object> list_exprs=null;
		RuleReturnScope exprs = null;
		EvaluationExpression OR31_tree=null;
		EvaluationExpression string_literal32_tree=null;
		RewriteRuleTokenStream stream_67=new RewriteRuleTokenStream(adaptor,"token 67");
		RewriteRuleTokenStream stream_OR=new RewriteRuleTokenStream(adaptor,"token OR");
		RewriteRuleSubtreeStream stream_andExpression=new RewriteRuleSubtreeStream(adaptor,"rule andExpression");

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:158:3: (exprs+= andExpression ( ( OR | '||' ) exprs+= andExpression )* -> { $exprs.size() == 1 }? ->)
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:158:5: exprs+= andExpression ( ( OR | '||' ) exprs+= andExpression )*
			{
			pushFollow(FOLLOW_andExpression_in_orExpression561);
			exprs=andExpression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_andExpression.add(exprs.getTree());
			if (list_exprs==null) list_exprs=new ArrayList<Object>();
			list_exprs.add(exprs.getTree());
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:158:26: ( ( OR | '||' ) exprs+= andExpression )*
			loop11:
			while (true) {
				int alt11=2;
				int LA11_0 = input.LA(1);
				if ( (LA11_0==OR||LA11_0==67) ) {
					alt11=1;
				}

				switch (alt11) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:158:27: ( OR | '||' ) exprs+= andExpression
					{
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:158:27: ( OR | '||' )
					int alt10=2;
					int LA10_0 = input.LA(1);
					if ( (LA10_0==OR) ) {
						alt10=1;
					}
					else if ( (LA10_0==67) ) {
						alt10=2;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						NoViableAltException nvae =
							new NoViableAltException("", 10, 0, input);
						throw nvae;
					}

					switch (alt10) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:158:28: OR
							{
							OR31=(Token)match(input,OR,FOLLOW_OR_in_orExpression565); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_OR.add(OR31);

							}
							break;
						case 2 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:158:33: '||'
							{
							string_literal32=(Token)match(input,67,FOLLOW_67_in_orExpression569); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_67.add(string_literal32);

							}
							break;

					}

					pushFollow(FOLLOW_andExpression_in_orExpression574);
					exprs=andExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_andExpression.add(exprs.getTree());
					if (list_exprs==null) list_exprs=new ArrayList<Object>();
					list_exprs.add(exprs.getTree());
					}
					break;

				default :
					break loop11;
				}
			}

			// AST REWRITE
			// elements: 
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 159:3: -> { $exprs.size() == 1 }?
			if ( list_exprs.size() == 1 ) {
				adaptor.addChild(root_0,  list_exprs.get(0) );
			}

			else // 160:3: ->
			{
				adaptor.addChild(root_0,  OrExpression.valueOf((List) list_exprs) );
			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "orExpression"


	public static class andExpression_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "andExpression"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:162:1: andExpression :exprs+= elementExpression ( ( AND | '&&' ) exprs+= elementExpression )* -> { $exprs.size() == 1 }? ->;
	public final MeteorParser.andExpression_return andExpression() throws RecognitionException {
		MeteorParser.andExpression_return retval = new MeteorParser.andExpression_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token AND33=null;
		Token string_literal34=null;
		List<Object> list_exprs=null;
		RuleReturnScope exprs = null;
		EvaluationExpression AND33_tree=null;
		EvaluationExpression string_literal34_tree=null;
		RewriteRuleTokenStream stream_AND=new RewriteRuleTokenStream(adaptor,"token AND");
		RewriteRuleTokenStream stream_38=new RewriteRuleTokenStream(adaptor,"token 38");
		RewriteRuleSubtreeStream stream_elementExpression=new RewriteRuleSubtreeStream(adaptor,"rule elementExpression");

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:163:3: (exprs+= elementExpression ( ( AND | '&&' ) exprs+= elementExpression )* -> { $exprs.size() == 1 }? ->)
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:163:5: exprs+= elementExpression ( ( AND | '&&' ) exprs+= elementExpression )*
			{
			pushFollow(FOLLOW_elementExpression_in_andExpression603);
			exprs=elementExpression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_elementExpression.add(exprs.getTree());
			if (list_exprs==null) list_exprs=new ArrayList<Object>();
			list_exprs.add(exprs.getTree());
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:163:30: ( ( AND | '&&' ) exprs+= elementExpression )*
			loop13:
			while (true) {
				int alt13=2;
				int LA13_0 = input.LA(1);
				if ( (LA13_0==AND||LA13_0==38) ) {
					alt13=1;
				}

				switch (alt13) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:163:31: ( AND | '&&' ) exprs+= elementExpression
					{
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:163:31: ( AND | '&&' )
					int alt12=2;
					int LA12_0 = input.LA(1);
					if ( (LA12_0==AND) ) {
						alt12=1;
					}
					else if ( (LA12_0==38) ) {
						alt12=2;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						NoViableAltException nvae =
							new NoViableAltException("", 12, 0, input);
						throw nvae;
					}

					switch (alt12) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:163:32: AND
							{
							AND33=(Token)match(input,AND,FOLLOW_AND_in_andExpression607); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_AND.add(AND33);

							}
							break;
						case 2 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:163:38: '&&'
							{
							string_literal34=(Token)match(input,38,FOLLOW_38_in_andExpression611); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_38.add(string_literal34);

							}
							break;

					}

					pushFollow(FOLLOW_elementExpression_in_andExpression616);
					exprs=elementExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_elementExpression.add(exprs.getTree());
					if (list_exprs==null) list_exprs=new ArrayList<Object>();
					list_exprs.add(exprs.getTree());
					}
					break;

				default :
					break loop13;
				}
			}

			// AST REWRITE
			// elements: 
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 164:3: -> { $exprs.size() == 1 }?
			if ( list_exprs.size() == 1 ) {
				adaptor.addChild(root_0,  list_exprs.get(0) );
			}

			else // 165:3: ->
			{
				adaptor.addChild(root_0,  AndExpression.valueOf((List) list_exprs) );
			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "andExpression"


	public static class elementExpression_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "elementExpression"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:167:1: elementExpression : elem= comparisonExpression ( (not= NOT )? IN set= elementExpression )? -> { set == null }? $elem -> ^( EXPRESSION[\"ElementInSetExpression\"] $elem $set) ;
	public final MeteorParser.elementExpression_return elementExpression() throws RecognitionException {
		MeteorParser.elementExpression_return retval = new MeteorParser.elementExpression_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token not=null;
		Token IN35=null;
		ParserRuleReturnScope elem =null;
		ParserRuleReturnScope set =null;

		EvaluationExpression not_tree=null;
		EvaluationExpression IN35_tree=null;
		RewriteRuleTokenStream stream_IN=new RewriteRuleTokenStream(adaptor,"token IN");
		RewriteRuleTokenStream stream_NOT=new RewriteRuleTokenStream(adaptor,"token NOT");
		RewriteRuleSubtreeStream stream_comparisonExpression=new RewriteRuleSubtreeStream(adaptor,"rule comparisonExpression");
		RewriteRuleSubtreeStream stream_elementExpression=new RewriteRuleSubtreeStream(adaptor,"rule elementExpression");

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:168:2: (elem= comparisonExpression ( (not= NOT )? IN set= elementExpression )? -> { set == null }? $elem -> ^( EXPRESSION[\"ElementInSetExpression\"] $elem $set) )
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:168:4: elem= comparisonExpression ( (not= NOT )? IN set= elementExpression )?
			{
			pushFollow(FOLLOW_comparisonExpression_in_elementExpression645);
			elem=comparisonExpression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_comparisonExpression.add(elem.getTree());
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:168:30: ( (not= NOT )? IN set= elementExpression )?
			int alt15=2;
			int LA15_0 = input.LA(1);
			if ( (LA15_0==IN||LA15_0==NOT) ) {
				alt15=1;
			}
			switch (alt15) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:168:31: (not= NOT )? IN set= elementExpression
					{
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:168:34: (not= NOT )?
					int alt14=2;
					int LA14_0 = input.LA(1);
					if ( (LA14_0==NOT) ) {
						alt14=1;
					}
					switch (alt14) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:168:34: not= NOT
							{
							not=(Token)match(input,NOT,FOLLOW_NOT_in_elementExpression650); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_NOT.add(not);

							}
							break;

					}

					IN35=(Token)match(input,IN,FOLLOW_IN_in_elementExpression653); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_IN.add(IN35);

					pushFollow(FOLLOW_elementExpression_in_elementExpression657);
					set=elementExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_elementExpression.add(set.getTree());
					}
					break;

			}

			// AST REWRITE
			// elements: set, elem, elem
			// token labels: 
			// rule labels: elem, retval, set
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_elem=new RewriteRuleSubtreeStream(adaptor,"rule elem",elem!=null?elem.getTree():null);
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);
			RewriteRuleSubtreeStream stream_set=new RewriteRuleSubtreeStream(adaptor,"rule set",set!=null?set.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 169:2: -> { set == null }? $elem
			if ( set == null ) {
				adaptor.addChild(root_0, stream_elem.nextTree());
			}

			else // 170:2: -> ^( EXPRESSION[\"ElementInSetExpression\"] $elem $set)
			{
				// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:170:5: ^( EXPRESSION[\"ElementInSetExpression\"] $elem $set)
				{
				EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
				root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "ElementInSetExpression"), root_1);
				adaptor.addChild(root_1, stream_elem.nextTree());
				adaptor.addChild(root_1,  not == null ? ElementInSetExpression.Quantor.EXISTS_IN : ElementInSetExpression.Quantor.EXISTS_NOT_IN);
				adaptor.addChild(root_1, stream_set.nextTree());
				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "elementExpression"


	public static class comparisonExpression_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "comparisonExpression"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:173:1: comparisonExpression : e1= arithmeticExpression ( (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' ) e2= comparisonExpression )? -> { $s == null }? $e1 -> { $s.getText().equals(\"!=\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) -> { $s.getText().equals(\"==\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) -> ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) ;
	public final MeteorParser.comparisonExpression_return comparisonExpression() throws RecognitionException {
		MeteorParser.comparisonExpression_return retval = new MeteorParser.comparisonExpression_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token s=null;
		ParserRuleReturnScope e1 =null;
		ParserRuleReturnScope e2 =null;

		EvaluationExpression s_tree=null;
		RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
		RewriteRuleTokenStream stream_51=new RewriteRuleTokenStream(adaptor,"token 51");
		RewriteRuleTokenStream stream_53=new RewriteRuleTokenStream(adaptor,"token 53");
		RewriteRuleTokenStream stream_54=new RewriteRuleTokenStream(adaptor,"token 54");
		RewriteRuleTokenStream stream_37=new RewriteRuleTokenStream(adaptor,"token 37");
		RewriteRuleTokenStream stream_50=new RewriteRuleTokenStream(adaptor,"token 50");
		RewriteRuleSubtreeStream stream_arithmeticExpression=new RewriteRuleSubtreeStream(adaptor,"rule arithmeticExpression");
		RewriteRuleSubtreeStream stream_comparisonExpression=new RewriteRuleSubtreeStream(adaptor,"rule comparisonExpression");

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:174:2: (e1= arithmeticExpression ( (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' ) e2= comparisonExpression )? -> { $s == null }? $e1 -> { $s.getText().equals(\"!=\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) -> { $s.getText().equals(\"==\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) -> ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) )
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:174:4: e1= arithmeticExpression ( (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' ) e2= comparisonExpression )?
			{
			pushFollow(FOLLOW_arithmeticExpression_in_comparisonExpression698);
			e1=arithmeticExpression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_arithmeticExpression.add(e1.getTree());
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:174:28: ( (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' ) e2= comparisonExpression )?
			int alt17=2;
			int LA17_0 = input.LA(1);
			if ( (LA17_0==37||(LA17_0 >= 50 && LA17_0 <= 51)||(LA17_0 >= 53 && LA17_0 <= 55)) ) {
				alt17=1;
			}
			switch (alt17) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:174:29: (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' ) e2= comparisonExpression
					{
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:174:29: (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' )
					int alt16=6;
					switch ( input.LA(1) ) {
					case 51:
						{
						alt16=1;
						}
						break;
					case 55:
						{
						alt16=2;
						}
						break;
					case 50:
						{
						alt16=3;
						}
						break;
					case 54:
						{
						alt16=4;
						}
						break;
					case 53:
						{
						alt16=5;
						}
						break;
					case 37:
						{
						alt16=6;
						}
						break;
					default:
						if (state.backtracking>0) {state.failed=true; return retval;}
						NoViableAltException nvae =
							new NoViableAltException("", 16, 0, input);
						throw nvae;
					}
					switch (alt16) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:174:30: s= '<='
							{
							s=(Token)match(input,51,FOLLOW_51_in_comparisonExpression704); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_51.add(s);

							}
							break;
						case 2 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:174:39: s= '>='
							{
							s=(Token)match(input,55,FOLLOW_55_in_comparisonExpression710); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_55.add(s);

							}
							break;
						case 3 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:174:48: s= '<'
							{
							s=(Token)match(input,50,FOLLOW_50_in_comparisonExpression716); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_50.add(s);

							}
							break;
						case 4 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:174:56: s= '>'
							{
							s=(Token)match(input,54,FOLLOW_54_in_comparisonExpression722); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_54.add(s);

							}
							break;
						case 5 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:174:64: s= '=='
							{
							s=(Token)match(input,53,FOLLOW_53_in_comparisonExpression728); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_53.add(s);

							}
							break;
						case 6 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:174:73: s= '!='
							{
							s=(Token)match(input,37,FOLLOW_37_in_comparisonExpression734); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_37.add(s);

							}
							break;

					}

					pushFollow(FOLLOW_comparisonExpression_in_comparisonExpression739);
					e2=comparisonExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_comparisonExpression.add(e2.getTree());
					}
					break;

			}

			// AST REWRITE
			// elements: e1, e1, e2, e1, e2, e1, e2
			// token labels: 
			// rule labels: retval, e1, e2
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);
			RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.getTree():null);
			RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 175:2: -> { $s == null }? $e1
			if ( s == null ) {
				adaptor.addChild(root_0, stream_e1.nextTree());
			}

			else // 176:3: -> { $s.getText().equals(\"!=\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
			if ( s.getText().equals("!=") ) {
				// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:176:38: ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
				{
				EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
				root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "ComparativeExpression"), root_1);
				adaptor.addChild(root_1, stream_e1.nextTree());
				adaptor.addChild(root_1, ComparativeExpression.BinaryOperator.NOT_EQUAL);
				adaptor.addChild(root_1, stream_e2.nextTree());
				adaptor.addChild(root_0, root_1);
				}

			}

			else // 177:3: -> { $s.getText().equals(\"==\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
			if ( s.getText().equals("==") ) {
				// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:177:38: ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
				{
				EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
				root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "ComparativeExpression"), root_1);
				adaptor.addChild(root_1, stream_e1.nextTree());
				adaptor.addChild(root_1, ComparativeExpression.BinaryOperator.EQUAL);
				adaptor.addChild(root_1, stream_e2.nextTree());
				adaptor.addChild(root_0, root_1);
				}

			}

			else // 178:2: -> ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
			{
				// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:178:6: ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
				{
				EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
				root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "ComparativeExpression"), root_1);
				adaptor.addChild(root_1, stream_e1.nextTree());
				adaptor.addChild(root_1, ComparativeExpression.BinaryOperator.valueOfSymbol((s!=null?s.getText():null)));
				adaptor.addChild(root_1, stream_e2.nextTree());
				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "comparisonExpression"


	public static class arithmeticExpression_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "arithmeticExpression"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:180:1: arithmeticExpression : e1= multiplicationExpression ( (s= '+' |s= '-' ) e2= arithmeticExpression )? -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2) -> $e1;
	public final MeteorParser.arithmeticExpression_return arithmeticExpression() throws RecognitionException {
		MeteorParser.arithmeticExpression_return retval = new MeteorParser.arithmeticExpression_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token s=null;
		ParserRuleReturnScope e1 =null;
		ParserRuleReturnScope e2 =null;

		EvaluationExpression s_tree=null;
		RewriteRuleTokenStream stream_45=new RewriteRuleTokenStream(adaptor,"token 45");
		RewriteRuleTokenStream stream_42=new RewriteRuleTokenStream(adaptor,"token 42");
		RewriteRuleSubtreeStream stream_arithmeticExpression=new RewriteRuleSubtreeStream(adaptor,"rule arithmeticExpression");
		RewriteRuleSubtreeStream stream_multiplicationExpression=new RewriteRuleSubtreeStream(adaptor,"rule multiplicationExpression");

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:181:2: (e1= multiplicationExpression ( (s= '+' |s= '-' ) e2= arithmeticExpression )? -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2) -> $e1)
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:181:4: e1= multiplicationExpression ( (s= '+' |s= '-' ) e2= arithmeticExpression )?
			{
			pushFollow(FOLLOW_multiplicationExpression_in_arithmeticExpression819);
			e1=multiplicationExpression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_multiplicationExpression.add(e1.getTree());
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:181:32: ( (s= '+' |s= '-' ) e2= arithmeticExpression )?
			int alt19=2;
			int LA19_0 = input.LA(1);
			if ( (LA19_0==42||LA19_0==45) ) {
				alt19=1;
			}
			switch (alt19) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:181:33: (s= '+' |s= '-' ) e2= arithmeticExpression
					{
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:181:33: (s= '+' |s= '-' )
					int alt18=2;
					int LA18_0 = input.LA(1);
					if ( (LA18_0==42) ) {
						alt18=1;
					}
					else if ( (LA18_0==45) ) {
						alt18=2;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						NoViableAltException nvae =
							new NoViableAltException("", 18, 0, input);
						throw nvae;
					}

					switch (alt18) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:181:34: s= '+'
							{
							s=(Token)match(input,42,FOLLOW_42_in_arithmeticExpression825); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_42.add(s);

							}
							break;
						case 2 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:181:42: s= '-'
							{
							s=(Token)match(input,45,FOLLOW_45_in_arithmeticExpression831); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_45.add(s);

							}
							break;

					}

					pushFollow(FOLLOW_arithmeticExpression_in_arithmeticExpression836);
					e2=arithmeticExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_arithmeticExpression.add(e2.getTree());
					}
					break;

			}

			// AST REWRITE
			// elements: e1, e1, e2
			// token labels: 
			// rule labels: retval, e1, e2
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);
			RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.getTree():null);
			RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 182:2: -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2)
			if ( s != null ) {
				// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:182:21: ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2)
				{
				EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
				root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "ArithmeticExpression"), root_1);
				adaptor.addChild(root_1, stream_e1.nextTree());
				adaptor.addChild(root_1,  s.getText().equals("+") ? ArithmeticExpression.ArithmeticOperator.ADDITION : ArithmeticExpression.ArithmeticOperator.SUBTRACTION);
				adaptor.addChild(root_1, stream_e2.nextTree());
				adaptor.addChild(root_0, root_1);
				}

			}

			else // 184:2: -> $e1
			{
				adaptor.addChild(root_0, stream_e1.nextTree());
			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "arithmeticExpression"


	public static class multiplicationExpression_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "multiplicationExpression"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:186:1: multiplicationExpression : e1= preincrementExpression ( (s= '*' |s= SLASH ) e2= preincrementExpression )? -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2) -> $e1;
	public final MeteorParser.multiplicationExpression_return multiplicationExpression() throws RecognitionException {
		MeteorParser.multiplicationExpression_return retval = new MeteorParser.multiplicationExpression_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token s=null;
		ParserRuleReturnScope e1 =null;
		ParserRuleReturnScope e2 =null;

		EvaluationExpression s_tree=null;
		RewriteRuleTokenStream stream_STAR=new RewriteRuleTokenStream(adaptor,"token STAR");
		RewriteRuleTokenStream stream_SLASH=new RewriteRuleTokenStream(adaptor,"token SLASH");
		RewriteRuleSubtreeStream stream_preincrementExpression=new RewriteRuleSubtreeStream(adaptor,"rule preincrementExpression");

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:187:2: (e1= preincrementExpression ( (s= '*' |s= SLASH ) e2= preincrementExpression )? -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2) -> $e1)
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:187:4: e1= preincrementExpression ( (s= '*' |s= SLASH ) e2= preincrementExpression )?
			{
			pushFollow(FOLLOW_preincrementExpression_in_multiplicationExpression879);
			e1=preincrementExpression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_preincrementExpression.add(e1.getTree());
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:187:30: ( (s= '*' |s= SLASH ) e2= preincrementExpression )?
			int alt21=2;
			int LA21_0 = input.LA(1);
			if ( ((LA21_0 >= SLASH && LA21_0 <= STAR)) ) {
				alt21=1;
			}
			switch (alt21) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:187:31: (s= '*' |s= SLASH ) e2= preincrementExpression
					{
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:187:31: (s= '*' |s= SLASH )
					int alt20=2;
					int LA20_0 = input.LA(1);
					if ( (LA20_0==STAR) ) {
						alt20=1;
					}
					else if ( (LA20_0==SLASH) ) {
						alt20=2;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						NoViableAltException nvae =
							new NoViableAltException("", 20, 0, input);
						throw nvae;
					}

					switch (alt20) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:187:32: s= '*'
							{
							s=(Token)match(input,STAR,FOLLOW_STAR_in_multiplicationExpression885); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_STAR.add(s);

							}
							break;
						case 2 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:187:40: s= SLASH
							{
							s=(Token)match(input,SLASH,FOLLOW_SLASH_in_multiplicationExpression891); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_SLASH.add(s);

							}
							break;

					}

					pushFollow(FOLLOW_preincrementExpression_in_multiplicationExpression896);
					e2=preincrementExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_preincrementExpression.add(e2.getTree());
					}
					break;

			}

			// AST REWRITE
			// elements: e1, e2, e1
			// token labels: 
			// rule labels: retval, e1, e2
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);
			RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.getTree():null);
			RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 188:2: -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2)
			if ( s != null ) {
				// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:188:21: ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2)
				{
				EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
				root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "ArithmeticExpression"), root_1);
				adaptor.addChild(root_1, stream_e1.nextTree());
				adaptor.addChild(root_1,  s.getText().equals("*") ? ArithmeticExpression.ArithmeticOperator.MULTIPLICATION : ArithmeticExpression.ArithmeticOperator.DIVISION);
				adaptor.addChild(root_1, stream_e2.nextTree());
				adaptor.addChild(root_0, root_1);
				}

			}

			else // 190:2: -> $e1
			{
				adaptor.addChild(root_0, stream_e1.nextTree());
			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "multiplicationExpression"


	public static class preincrementExpression_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "preincrementExpression"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:192:1: preincrementExpression : ( '++' preincrementExpression | '--' preincrementExpression | unaryExpression );
	public final MeteorParser.preincrementExpression_return preincrementExpression() throws RecognitionException {
		MeteorParser.preincrementExpression_return retval = new MeteorParser.preincrementExpression_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token string_literal36=null;
		Token string_literal38=null;
		ParserRuleReturnScope preincrementExpression37 =null;
		ParserRuleReturnScope preincrementExpression39 =null;
		ParserRuleReturnScope unaryExpression40 =null;

		EvaluationExpression string_literal36_tree=null;
		EvaluationExpression string_literal38_tree=null;

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:193:2: ( '++' preincrementExpression | '--' preincrementExpression | unaryExpression )
			int alt22=3;
			switch ( input.LA(1) ) {
			case 43:
				{
				alt22=1;
				}
				break;
			case 46:
				{
				alt22=2;
				}
				break;
			case DECIMAL:
			case FN:
			case ID:
			case INTEGER:
			case STRING:
			case UINT:
			case VAR:
			case 36:
			case 39:
			case 40:
			case 58:
			case 60:
			case 61:
			case 63:
			case 66:
			case 69:
				{
				alt22=3;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 22, 0, input);
				throw nvae;
			}
			switch (alt22) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:193:4: '++' preincrementExpression
					{
					root_0 = (EvaluationExpression)adaptor.nil();


					string_literal36=(Token)match(input,43,FOLLOW_43_in_preincrementExpression937); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					string_literal36_tree = (EvaluationExpression)adaptor.create(string_literal36);
					adaptor.addChild(root_0, string_literal36_tree);
					}

					pushFollow(FOLLOW_preincrementExpression_in_preincrementExpression939);
					preincrementExpression37=preincrementExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, preincrementExpression37.getTree());

					}
					break;
				case 2 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:194:4: '--' preincrementExpression
					{
					root_0 = (EvaluationExpression)adaptor.nil();


					string_literal38=(Token)match(input,46,FOLLOW_46_in_preincrementExpression944); if (state.failed) return retval;
					if ( state.backtracking==0 ) {
					string_literal38_tree = (EvaluationExpression)adaptor.create(string_literal38);
					adaptor.addChild(root_0, string_literal38_tree);
					}

					pushFollow(FOLLOW_preincrementExpression_in_preincrementExpression946);
					preincrementExpression39=preincrementExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, preincrementExpression39.getTree());

					}
					break;
				case 3 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:195:4: unaryExpression
					{
					root_0 = (EvaluationExpression)adaptor.nil();


					pushFollow(FOLLOW_unaryExpression_in_preincrementExpression951);
					unaryExpression40=unaryExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression40.getTree());

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "preincrementExpression"


	public static class unaryExpression_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "unaryExpression"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:197:1: unaryExpression : ( '!' | '~' )? castExpression ;
	public final MeteorParser.unaryExpression_return unaryExpression() throws RecognitionException {
		MeteorParser.unaryExpression_return retval = new MeteorParser.unaryExpression_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token set41=null;
		ParserRuleReturnScope castExpression42 =null;

		EvaluationExpression set41_tree=null;

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:198:2: ( ( '!' | '~' )? castExpression )
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:198:4: ( '!' | '~' )? castExpression
			{
			root_0 = (EvaluationExpression)adaptor.nil();


			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:198:4: ( '!' | '~' )?
			int alt23=2;
			int LA23_0 = input.LA(1);
			if ( (LA23_0==36||LA23_0==69) ) {
				alt23=1;
			}
			switch (alt23) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:
					{
					set41=input.LT(1);
					if ( input.LA(1)==36||input.LA(1)==69 ) {
						input.consume();
						if ( state.backtracking==0 ) adaptor.addChild(root_0, (EvaluationExpression)adaptor.create(set41));
						state.errorRecovery=false;
						state.failed=false;
					}
					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					}
					break;

			}

			pushFollow(FOLLOW_castExpression_in_unaryExpression970);
			castExpression42=castExpression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, castExpression42.getTree());

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "unaryExpression"


	public static class castExpression_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "castExpression"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:204:1: castExpression : ( ( '(' ID ')' )=> '(' type= ID ')' expr= generalPathExpression ->|expr= generalPathExpression ({...}? ID type= ID )? ->);
	public final MeteorParser.castExpression_return castExpression() throws RecognitionException {
		MeteorParser.castExpression_return retval = new MeteorParser.castExpression_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token type=null;
		Token char_literal43=null;
		Token char_literal44=null;
		Token ID45=null;
		ParserRuleReturnScope expr =null;

		EvaluationExpression type_tree=null;
		EvaluationExpression char_literal43_tree=null;
		EvaluationExpression char_literal44_tree=null;
		EvaluationExpression ID45_tree=null;
		RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
		RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
		RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
		RewriteRuleSubtreeStream stream_generalPathExpression=new RewriteRuleSubtreeStream(adaptor,"rule generalPathExpression");

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:205:2: ( ( '(' ID ')' )=> '(' type= ID ')' expr= generalPathExpression ->|expr= generalPathExpression ({...}? ID type= ID )? ->)
			int alt25=2;
			int LA25_0 = input.LA(1);
			if ( (LA25_0==40) ) {
				int LA25_1 = input.LA(2);
				if ( (synpred6_Meteor()) ) {
					alt25=1;
				}
				else if ( (true) ) {
					alt25=2;
				}

			}
			else if ( (LA25_0==DECIMAL||LA25_0==FN||LA25_0==ID||LA25_0==INTEGER||(LA25_0 >= STRING && LA25_0 <= UINT)||LA25_0==VAR||LA25_0==39||LA25_0==58||(LA25_0 >= 60 && LA25_0 <= 61)||LA25_0==63||LA25_0==66) ) {
				alt25=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 25, 0, input);
				throw nvae;
			}

			switch (alt25) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:205:4: ( '(' ID ')' )=> '(' type= ID ')' expr= generalPathExpression
					{
					char_literal43=(Token)match(input,40,FOLLOW_40_in_castExpression990); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_40.add(char_literal43);

					type=(Token)match(input,ID,FOLLOW_ID_in_castExpression994); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_ID.add(type);

					char_literal44=(Token)match(input,41,FOLLOW_41_in_castExpression996); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_41.add(char_literal44);

					pushFollow(FOLLOW_generalPathExpression_in_castExpression1000);
					expr=generalPathExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_generalPathExpression.add(expr.getTree());
					// AST REWRITE
					// elements: 
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (EvaluationExpression)adaptor.nil();
					// 206:3: ->
					{
						adaptor.addChild(root_0,  coerce((type!=null?type.getText():null), (expr!=null?((EvaluationExpression)expr.getTree()):null)) );
					}


					retval.tree = root_0;
					}

					}
					break;
				case 2 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:207:4: expr= generalPathExpression ({...}? ID type= ID )?
					{
					pushFollow(FOLLOW_generalPathExpression_in_castExpression1013);
					expr=generalPathExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_generalPathExpression.add(expr.getTree());
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:207:31: ({...}? ID type= ID )?
					int alt24=2;
					int LA24_0 = input.LA(1);
					if ( (LA24_0==ID) ) {
						int LA24_1 = input.LA(2);
						if ( (LA24_1==ID) ) {
							int LA24_3 = input.LA(3);
							if ( ((input.LT(1).getText().equals("as"))) ) {
								alt24=1;
							}
						}
					}
					switch (alt24) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:207:32: {...}? ID type= ID
							{
							if ( !((input.LT(1).getText().equals("as"))) ) {
								if (state.backtracking>0) {state.failed=true; return retval;}
								throw new FailedPredicateException(input, "castExpression", "input.LT(1).getText().equals(\"as\")");
							}
							ID45=(Token)match(input,ID,FOLLOW_ID_in_castExpression1018); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_ID.add(ID45);

							type=(Token)match(input,ID,FOLLOW_ID_in_castExpression1022); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_ID.add(type);

							}
							break;

					}

					// AST REWRITE
					// elements: 
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (EvaluationExpression)adaptor.nil();
					// 208:2: ->
					{
						adaptor.addChild(root_0,  type == null ? (expr!=null?((EvaluationExpression)expr.getTree()):null) : coerce((type!=null?type.getText():null), (expr!=null?((EvaluationExpression)expr.getTree()):null)));
					}


					retval.tree = root_0;
					}

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "castExpression"


	public static class generalPathExpression_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "generalPathExpression"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:210:1: generalPathExpression : value= valueExpression ( ( pathExpression[EvaluationExpression.VALUE] )=>path= pathExpression[$value.tree] -> $path| -> $value) ;
	public final MeteorParser.generalPathExpression_return generalPathExpression() throws RecognitionException {
		MeteorParser.generalPathExpression_return retval = new MeteorParser.generalPathExpression_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		ParserRuleReturnScope value =null;
		ParserRuleReturnScope path =null;

		RewriteRuleSubtreeStream stream_valueExpression=new RewriteRuleSubtreeStream(adaptor,"rule valueExpression");
		RewriteRuleSubtreeStream stream_pathExpression=new RewriteRuleSubtreeStream(adaptor,"rule pathExpression");

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:211:2: (value= valueExpression ( ( pathExpression[EvaluationExpression.VALUE] )=>path= pathExpression[$value.tree] -> $path| -> $value) )
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:211:4: value= valueExpression ( ( pathExpression[EvaluationExpression.VALUE] )=>path= pathExpression[$value.tree] -> $path| -> $value)
			{
			pushFollow(FOLLOW_valueExpression_in_generalPathExpression1041);
			value=valueExpression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_valueExpression.add(value.getTree());
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:212:4: ( ( pathExpression[EvaluationExpression.VALUE] )=>path= pathExpression[$value.tree] -> $path| -> $value)
			int alt26=2;
			int LA26_0 = input.LA(1);
			if ( (LA26_0==57) && (synpred7_Meteor())) {
				alt26=1;
			}
			else if ( (LA26_0==47) && (synpred7_Meteor())) {
				alt26=1;
			}
			else if ( (LA26_0==58) && (synpred7_Meteor())) {
				alt26=1;
			}
			else if ( (LA26_0==EOF||LA26_0==AND||(LA26_0 >= ID && LA26_0 <= IN)||LA26_0==NOT||LA26_0==OR||(LA26_0 >= SLASH && LA26_0 <= STAR)||(LA26_0 >= 37 && LA26_0 <= 38)||(LA26_0 >= 41 && LA26_0 <= 42)||(LA26_0 >= 44 && LA26_0 <= 45)||(LA26_0 >= 48 && LA26_0 <= 51)||(LA26_0 >= 53 && LA26_0 <= 56)||LA26_0==59||(LA26_0 >= 67 && LA26_0 <= 68)) ) {
				alt26=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 26, 0, input);
				throw nvae;
			}

			switch (alt26) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:212:5: ( pathExpression[EvaluationExpression.VALUE] )=>path= pathExpression[$value.tree]
					{
					pushFollow(FOLLOW_pathExpression_in_generalPathExpression1056);
					path=pathExpression((value!=null?((EvaluationExpression)value.getTree()):null));
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_pathExpression.add(path.getTree());
					// AST REWRITE
					// elements: path
					// token labels: 
					// rule labels: retval, path
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);
					RewriteRuleSubtreeStream stream_path=new RewriteRuleSubtreeStream(adaptor,"rule path",path!=null?path.getTree():null);

					root_0 = (EvaluationExpression)adaptor.nil();
					// 212:85: -> $path
					{
						adaptor.addChild(root_0, stream_path.nextTree());
					}


					retval.tree = root_0;
					}

					}
					break;
				case 2 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:213:7: 
					{
					// AST REWRITE
					// elements: value
					// token labels: 
					// rule labels: retval, value
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);
					RewriteRuleSubtreeStream stream_value=new RewriteRuleSubtreeStream(adaptor,"rule value",value!=null?value.getTree():null);

					root_0 = (EvaluationExpression)adaptor.nil();
					// 213:7: -> $value
					{
						adaptor.addChild(root_0, stream_value.nextTree());
					}


					retval.tree = root_0;
					}

					}
					break;

			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "generalPathExpression"


	public static class contextAwarePathExpression_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "contextAwarePathExpression"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:215:1: contextAwarePathExpression[EvaluationExpression context] : pathExpression[context] ;
	public final MeteorParser.contextAwarePathExpression_return contextAwarePathExpression(EvaluationExpression context) throws RecognitionException {
		MeteorParser.contextAwarePathExpression_return retval = new MeteorParser.contextAwarePathExpression_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		ParserRuleReturnScope pathExpression46 =null;


		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:216:3: ( pathExpression[context] )
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:216:5: pathExpression[context]
			{
			root_0 = (EvaluationExpression)adaptor.nil();


			pushFollow(FOLLOW_pathExpression_in_contextAwarePathExpression1085);
			pathExpression46=pathExpression(context);
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, pathExpression46.getTree());

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "contextAwarePathExpression"


	public static class pathExpression_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "pathExpression"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:218:1: pathExpression[EvaluationExpression inExp] : ( ( '?.' ID '(' )=> '?.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)] -> $path| -> ^( EXPRESSION[\"TernaryExpression\"] $call) ) | ( '.' ID '(' )=> '.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[$call.tree] -> $path| -> $call) |seg= pathSegment ( ( pathSegment )=>path= pathExpression[$seg.tree] -> $path| -> $seg) );
	public final MeteorParser.pathExpression_return pathExpression(EvaluationExpression inExp) throws RecognitionException {
		MeteorParser.pathExpression_return retval = new MeteorParser.pathExpression_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token string_literal47=null;
		Token char_literal48=null;
		ParserRuleReturnScope call =null;
		ParserRuleReturnScope path =null;
		ParserRuleReturnScope seg =null;

		EvaluationExpression string_literal47_tree=null;
		EvaluationExpression char_literal48_tree=null;
		RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
		RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
		RewriteRuleSubtreeStream stream_pathSegment=new RewriteRuleSubtreeStream(adaptor,"rule pathSegment");
		RewriteRuleSubtreeStream stream_methodCall=new RewriteRuleSubtreeStream(adaptor,"rule methodCall");
		RewriteRuleSubtreeStream stream_pathExpression=new RewriteRuleSubtreeStream(adaptor,"rule pathExpression");

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:219:3: ( ( '?.' ID '(' )=> '?.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)] -> $path| -> ^( EXPRESSION[\"TernaryExpression\"] $call) ) | ( '.' ID '(' )=> '.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[$call.tree] -> $path| -> $call) |seg= pathSegment ( ( pathSegment )=>path= pathExpression[$seg.tree] -> $path| -> $seg) )
			int alt30=3;
			switch ( input.LA(1) ) {
			case 57:
				{
				int LA30_1 = input.LA(2);
				if ( (synpred8_Meteor()) ) {
					alt30=1;
				}
				else if ( (true) ) {
					alt30=3;
				}

				}
				break;
			case 47:
				{
				int LA30_2 = input.LA(2);
				if ( (synpred10_Meteor()) ) {
					alt30=2;
				}
				else if ( (true) ) {
					alt30=3;
				}

				}
				break;
			case 58:
				{
				alt30=3;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 30, 0, input);
				throw nvae;
			}
			switch (alt30) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:220:5: ( '?.' ID '(' )=> '?.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)] -> $path| -> ^( EXPRESSION[\"TernaryExpression\"] $call) )
					{
					string_literal47=(Token)match(input,57,FOLLOW_57_in_pathExpression1113); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_57.add(string_literal47);

					pushFollow(FOLLOW_methodCall_in_pathExpression1117);
					call=methodCall(inExp);
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_methodCall.add(call.getTree());
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:221:7: ( ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)] -> $path| -> ^( EXPRESSION[\"TernaryExpression\"] $call) )
					int alt27=2;
					int LA27_0 = input.LA(1);
					if ( (LA27_0==57) && (synpred9_Meteor())) {
						alt27=1;
					}
					else if ( (LA27_0==47) && (synpred9_Meteor())) {
						alt27=1;
					}
					else if ( (LA27_0==58) && (synpred9_Meteor())) {
						alt27=1;
					}
					else if ( (LA27_0==EOF||LA27_0==AND||(LA27_0 >= ID && LA27_0 <= IN)||LA27_0==NOT||LA27_0==OR||(LA27_0 >= SLASH && LA27_0 <= STAR)||(LA27_0 >= 37 && LA27_0 <= 38)||(LA27_0 >= 41 && LA27_0 <= 42)||(LA27_0 >= 44 && LA27_0 <= 45)||(LA27_0 >= 48 && LA27_0 <= 51)||(LA27_0 >= 53 && LA27_0 <= 56)||LA27_0==59||(LA27_0 >= 67 && LA27_0 <= 68)) ) {
						alt27=2;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						NoViableAltException nvae =
							new NoViableAltException("", 27, 0, input);
						throw nvae;
					}

					switch (alt27) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:221:8: ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)]
							{
							pushFollow(FOLLOW_pathExpression_in_pathExpression1134);
							path=pathExpression(new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), (call!=null?((EvaluationExpression)call.getTree()):null)));
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) stream_pathExpression.add(path.getTree());
							// AST REWRITE
							// elements: path
							// token labels: 
							// rule labels: retval, path
							// token list labels: 
							// rule list labels: 
							// wildcard labels: 
							if ( state.backtracking==0 ) {
							retval.tree = root_0;
							RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);
							RewriteRuleSubtreeStream stream_path=new RewriteRuleSubtreeStream(adaptor,"rule path",path!=null?path.getTree():null);

							root_0 = (EvaluationExpression)adaptor.nil();
							// 221:146: -> $path
							{
								adaptor.addChild(root_0, stream_path.nextTree());
							}


							retval.tree = root_0;
							}

							}
							break;
						case 2 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:222:8: 
							{
							// AST REWRITE
							// elements: call
							// token labels: 
							// rule labels: call, retval
							// token list labels: 
							// rule list labels: 
							// wildcard labels: 
							if ( state.backtracking==0 ) {
							retval.tree = root_0;
							RewriteRuleSubtreeStream stream_call=new RewriteRuleSubtreeStream(adaptor,"rule call",call!=null?call.getTree():null);
							RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

							root_0 = (EvaluationExpression)adaptor.nil();
							// 222:8: -> ^( EXPRESSION[\"TernaryExpression\"] $call)
							{
								// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:222:11: ^( EXPRESSION[\"TernaryExpression\"] $call)
								{
								EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
								root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "TernaryExpression"), root_1);
								adaptor.addChild(root_1, new NotNullOrMissingBooleanExpression().withInputExpression(inExp));
								adaptor.addChild(root_1, stream_call.nextTree());
								adaptor.addChild(root_1, inExp);
								adaptor.addChild(root_0, root_1);
								}

							}


							retval.tree = root_0;
							}

							}
							break;

					}

					}
					break;
				case 2 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:224:5: ( '.' ID '(' )=> '.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[$call.tree] -> $path| -> $call)
					{
					char_literal48=(Token)match(input,47,FOLLOW_47_in_pathExpression1184); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_47.add(char_literal48);

					pushFollow(FOLLOW_methodCall_in_pathExpression1188);
					call=methodCall(inExp);
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_methodCall.add(call.getTree());
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:225:7: ( ( pathSegment )=>path= pathExpression[$call.tree] -> $path| -> $call)
					int alt28=2;
					int LA28_0 = input.LA(1);
					if ( (LA28_0==57) && (synpred11_Meteor())) {
						alt28=1;
					}
					else if ( (LA28_0==47) && (synpred11_Meteor())) {
						alt28=1;
					}
					else if ( (LA28_0==58) && (synpred11_Meteor())) {
						alt28=1;
					}
					else if ( (LA28_0==EOF||LA28_0==AND||(LA28_0 >= ID && LA28_0 <= IN)||LA28_0==NOT||LA28_0==OR||(LA28_0 >= SLASH && LA28_0 <= STAR)||(LA28_0 >= 37 && LA28_0 <= 38)||(LA28_0 >= 41 && LA28_0 <= 42)||(LA28_0 >= 44 && LA28_0 <= 45)||(LA28_0 >= 48 && LA28_0 <= 51)||(LA28_0 >= 53 && LA28_0 <= 56)||LA28_0==59||(LA28_0 >= 67 && LA28_0 <= 68)) ) {
						alt28=2;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						NoViableAltException nvae =
							new NoViableAltException("", 28, 0, input);
						throw nvae;
					}

					switch (alt28) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:225:8: ( pathSegment )=>path= pathExpression[$call.tree]
							{
							pushFollow(FOLLOW_pathExpression_in_pathExpression1205);
							path=pathExpression((call!=null?((EvaluationExpression)call.getTree()):null));
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) stream_pathExpression.add(path.getTree());
							// AST REWRITE
							// elements: path
							// token labels: 
							// rule labels: retval, path
							// token list labels: 
							// rule list labels: 
							// wildcard labels: 
							if ( state.backtracking==0 ) {
							retval.tree = root_0;
							RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);
							RewriteRuleSubtreeStream stream_path=new RewriteRuleSubtreeStream(adaptor,"rule path",path!=null?path.getTree():null);

							root_0 = (EvaluationExpression)adaptor.nil();
							// 225:55: -> $path
							{
								adaptor.addChild(root_0, stream_path.nextTree());
							}


							retval.tree = root_0;
							}

							}
							break;
						case 2 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:225:66: 
							{
							// AST REWRITE
							// elements: call
							// token labels: 
							// rule labels: call, retval
							// token list labels: 
							// rule list labels: 
							// wildcard labels: 
							if ( state.backtracking==0 ) {
							retval.tree = root_0;
							RewriteRuleSubtreeStream stream_call=new RewriteRuleSubtreeStream(adaptor,"rule call",call!=null?call.getTree():null);
							RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

							root_0 = (EvaluationExpression)adaptor.nil();
							// 225:66: -> $call
							{
								adaptor.addChild(root_0, stream_call.nextTree());
							}


							retval.tree = root_0;
							}

							}
							break;

					}

					}
					break;
				case 3 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:227:5: seg= pathSegment ( ( pathSegment )=>path= pathExpression[$seg.tree] -> $path| -> $seg)
					{
					pushFollow(FOLLOW_pathSegment_in_pathExpression1231);
					seg=pathSegment();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_pathSegment.add(seg.getTree());
					if ( state.backtracking==0 ) { ((PathSegmentExpression) seg.getTree()).setInputExpression(inExp); }
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:228:5: ( ( pathSegment )=>path= pathExpression[$seg.tree] -> $path| -> $seg)
					int alt29=2;
					int LA29_0 = input.LA(1);
					if ( (LA29_0==57) && (synpred12_Meteor())) {
						alt29=1;
					}
					else if ( (LA29_0==47) && (synpred12_Meteor())) {
						alt29=1;
					}
					else if ( (LA29_0==58) && (synpred12_Meteor())) {
						alt29=1;
					}
					else if ( (LA29_0==EOF||LA29_0==AND||(LA29_0 >= ID && LA29_0 <= IN)||LA29_0==NOT||LA29_0==OR||(LA29_0 >= SLASH && LA29_0 <= STAR)||(LA29_0 >= 37 && LA29_0 <= 38)||(LA29_0 >= 41 && LA29_0 <= 42)||(LA29_0 >= 44 && LA29_0 <= 45)||(LA29_0 >= 48 && LA29_0 <= 51)||(LA29_0 >= 53 && LA29_0 <= 56)||LA29_0==59||(LA29_0 >= 67 && LA29_0 <= 68)) ) {
						alt29=2;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						NoViableAltException nvae =
							new NoViableAltException("", 29, 0, input);
						throw nvae;
					}

					switch (alt29) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:228:6: ( pathSegment )=>path= pathExpression[$seg.tree]
							{
							pushFollow(FOLLOW_pathExpression_in_pathExpression1247);
							path=pathExpression((seg!=null?((EvaluationExpression)seg.getTree()):null));
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) stream_pathExpression.add(path.getTree());
							// AST REWRITE
							// elements: path
							// token labels: 
							// rule labels: retval, path
							// token list labels: 
							// rule list labels: 
							// wildcard labels: 
							if ( state.backtracking==0 ) {
							retval.tree = root_0;
							RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);
							RewriteRuleSubtreeStream stream_path=new RewriteRuleSubtreeStream(adaptor,"rule path",path!=null?path.getTree():null);

							root_0 = (EvaluationExpression)adaptor.nil();
							// 228:53: -> $path
							{
								adaptor.addChild(root_0, stream_path.nextTree());
							}


							retval.tree = root_0;
							}

							}
							break;
						case 2 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:228:64: 
							{
							// AST REWRITE
							// elements: seg
							// token labels: 
							// rule labels: retval, seg
							// token list labels: 
							// rule list labels: 
							// wildcard labels: 
							if ( state.backtracking==0 ) {
							retval.tree = root_0;
							RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);
							RewriteRuleSubtreeStream stream_seg=new RewriteRuleSubtreeStream(adaptor,"rule seg",seg!=null?seg.getTree():null);

							root_0 = (EvaluationExpression)adaptor.nil();
							// 228:64: -> $seg
							{
								adaptor.addChild(root_0, stream_seg.nextTree());
							}


							retval.tree = root_0;
							}

							}
							break;

					}

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (NoViableAltException re) {
			 explainUsage("in a path expression only .field, ?.field, [...], and .method(...) are allowed", re); 
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "pathExpression"


	public static class pathSegment_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "pathSegment"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:231:1: pathSegment : ( ( '?.' )=> '?.' field= ID -> ^( EXPRESSION[\"TernaryExpression\"] ) | ( '.' )=> '.' field= ID -> ^( EXPRESSION[\"ObjectAccess\"] ) | ( '[' )=> arrayAccess );
	public final MeteorParser.pathSegment_return pathSegment() throws RecognitionException {
		MeteorParser.pathSegment_return retval = new MeteorParser.pathSegment_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token field=null;
		Token string_literal49=null;
		Token char_literal50=null;
		ParserRuleReturnScope arrayAccess51 =null;

		EvaluationExpression field_tree=null;
		EvaluationExpression string_literal49_tree=null;
		EvaluationExpression char_literal50_tree=null;
		RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
		RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
		RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");

		  paraphrase.push("a path expression"); 
		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:234:3: ( ( '?.' )=> '?.' field= ID -> ^( EXPRESSION[\"TernaryExpression\"] ) | ( '.' )=> '.' field= ID -> ^( EXPRESSION[\"ObjectAccess\"] ) | ( '[' )=> arrayAccess )
			int alt31=3;
			int LA31_0 = input.LA(1);
			if ( (LA31_0==57) && (synpred13_Meteor())) {
				alt31=1;
			}
			else if ( (LA31_0==47) && (synpred14_Meteor())) {
				alt31=2;
			}
			else if ( (LA31_0==58) && (synpred15_Meteor())) {
				alt31=3;
			}

			switch (alt31) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:235:5: ( '?.' )=> '?.' field= ID
					{
					string_literal49=(Token)match(input,57,FOLLOW_57_in_pathSegment1299); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_57.add(string_literal49);

					field=(Token)match(input,ID,FOLLOW_ID_in_pathSegment1303); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_ID.add(field);

					// AST REWRITE
					// elements: 
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (EvaluationExpression)adaptor.nil();
					// 235:28: -> ^( EXPRESSION[\"TernaryExpression\"] )
					{
						// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:235:31: ^( EXPRESSION[\"TernaryExpression\"] )
						{
						EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
						root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "TernaryExpression"), root_1);
						adaptor.addChild(root_1, new NotNullOrMissingBooleanExpression());
						adaptor.addChild(root_1, new ObjectAccess((field!=null?field.getText():null)));
						adaptor.addChild(root_1, EvaluationExpression.VALUE);
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;
				case 2 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:236:5: ( '.' )=> '.' field= ID
					{
					char_literal50=(Token)match(input,47,FOLLOW_47_in_pathSegment1331); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_47.add(char_literal50);

					field=(Token)match(input,ID,FOLLOW_ID_in_pathSegment1335); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_ID.add(field);

					// AST REWRITE
					// elements: 
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (EvaluationExpression)adaptor.nil();
					// 236:27: -> ^( EXPRESSION[\"ObjectAccess\"] )
					{
						// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:236:30: ^( EXPRESSION[\"ObjectAccess\"] )
						{
						EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
						root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "ObjectAccess"), root_1);
						adaptor.addChild(root_1, (field!=null?field.getText():null));
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;
				case 3 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:237:5: ( '[' )=> arrayAccess
					{
					root_0 = (EvaluationExpression)adaptor.nil();


					pushFollow(FOLLOW_arrayAccess_in_pathSegment1360);
					arrayAccess51=arrayAccess();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, arrayAccess51.getTree());

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
			if ( state.backtracking==0 ) { paraphrase.pop(); }
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "pathSegment"


	public static class arrayAccess_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "arrayAccess"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:239:1: arrayAccess : ( '[' STAR ']' ( ( '.' methodCall[null] )=> '.' call= methodCall[EvaluationExpression.VALUE] -> ^( EXPRESSION[\"ArrayProjection\"] $call) |path= pathSegment -> ^( EXPRESSION[\"ArrayProjection\"] $path) ) | '[' (pos= INTEGER |pos= UINT ) ']' -> ^( EXPRESSION[\"ArrayAccess\"] ) | '[' (start= INTEGER |start= UINT ) ':' (end= INTEGER |end= UINT ) ']' -> ^( EXPRESSION[\"ArrayAccess\"] ) );
	public final MeteorParser.arrayAccess_return arrayAccess() throws RecognitionException {
		MeteorParser.arrayAccess_return retval = new MeteorParser.arrayAccess_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token pos=null;
		Token start=null;
		Token end=null;
		Token char_literal52=null;
		Token STAR53=null;
		Token char_literal54=null;
		Token char_literal55=null;
		Token char_literal56=null;
		Token char_literal57=null;
		Token char_literal58=null;
		Token char_literal59=null;
		Token char_literal60=null;
		ParserRuleReturnScope call =null;
		ParserRuleReturnScope path =null;

		EvaluationExpression pos_tree=null;
		EvaluationExpression start_tree=null;
		EvaluationExpression end_tree=null;
		EvaluationExpression char_literal52_tree=null;
		EvaluationExpression STAR53_tree=null;
		EvaluationExpression char_literal54_tree=null;
		EvaluationExpression char_literal55_tree=null;
		EvaluationExpression char_literal56_tree=null;
		EvaluationExpression char_literal57_tree=null;
		EvaluationExpression char_literal58_tree=null;
		EvaluationExpression char_literal59_tree=null;
		EvaluationExpression char_literal60_tree=null;
		RewriteRuleTokenStream stream_INTEGER=new RewriteRuleTokenStream(adaptor,"token INTEGER");
		RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
		RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
		RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
		RewriteRuleTokenStream stream_STAR=new RewriteRuleTokenStream(adaptor,"token STAR");
		RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
		RewriteRuleTokenStream stream_UINT=new RewriteRuleTokenStream(adaptor,"token UINT");
		RewriteRuleSubtreeStream stream_methodCall=new RewriteRuleSubtreeStream(adaptor,"rule methodCall");
		RewriteRuleSubtreeStream stream_pathSegment=new RewriteRuleSubtreeStream(adaptor,"rule pathSegment");

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:240:3: ( '[' STAR ']' ( ( '.' methodCall[null] )=> '.' call= methodCall[EvaluationExpression.VALUE] -> ^( EXPRESSION[\"ArrayProjection\"] $call) |path= pathSegment -> ^( EXPRESSION[\"ArrayProjection\"] $path) ) | '[' (pos= INTEGER |pos= UINT ) ']' -> ^( EXPRESSION[\"ArrayAccess\"] ) | '[' (start= INTEGER |start= UINT ) ':' (end= INTEGER |end= UINT ) ']' -> ^( EXPRESSION[\"ArrayAccess\"] ) )
			int alt36=3;
			int LA36_0 = input.LA(1);
			if ( (LA36_0==58) ) {
				switch ( input.LA(2) ) {
				case STAR:
					{
					alt36=1;
					}
					break;
				case INTEGER:
					{
					int LA36_3 = input.LA(3);
					if ( (LA36_3==59) ) {
						alt36=2;
					}
					else if ( (LA36_3==48) ) {
						alt36=3;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 36, 3, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

					}
					break;
				case UINT:
					{
					int LA36_4 = input.LA(3);
					if ( (LA36_4==59) ) {
						alt36=2;
					}
					else if ( (LA36_4==48) ) {
						alt36=3;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 36, 4, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

					}
					break;
				default:
					if (state.backtracking>0) {state.failed=true; return retval;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 36, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}
			}

			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 36, 0, input);
				throw nvae;
			}

			switch (alt36) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:240:5: '[' STAR ']' ( ( '.' methodCall[null] )=> '.' call= methodCall[EvaluationExpression.VALUE] -> ^( EXPRESSION[\"ArrayProjection\"] $call) |path= pathSegment -> ^( EXPRESSION[\"ArrayProjection\"] $path) )
					{
					char_literal52=(Token)match(input,58,FOLLOW_58_in_arrayAccess1370); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_58.add(char_literal52);

					STAR53=(Token)match(input,STAR,FOLLOW_STAR_in_arrayAccess1372); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_STAR.add(STAR53);

					char_literal54=(Token)match(input,59,FOLLOW_59_in_arrayAccess1374); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_59.add(char_literal54);

					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:240:18: ( ( '.' methodCall[null] )=> '.' call= methodCall[EvaluationExpression.VALUE] -> ^( EXPRESSION[\"ArrayProjection\"] $call) |path= pathSegment -> ^( EXPRESSION[\"ArrayProjection\"] $path) )
					int alt32=2;
					int LA32_0 = input.LA(1);
					if ( (LA32_0==47) ) {
						int LA32_1 = input.LA(2);
						if ( (synpred16_Meteor()) ) {
							alt32=1;
						}
						else if ( (true) ) {
							alt32=2;
						}

					}
					else if ( ((LA32_0 >= 57 && LA32_0 <= 58)) ) {
						alt32=2;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						NoViableAltException nvae =
							new NoViableAltException("", 32, 0, input);
						throw nvae;
					}

					switch (alt32) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:240:19: ( '.' methodCall[null] )=> '.' call= methodCall[EvaluationExpression.VALUE]
							{
							char_literal55=(Token)match(input,47,FOLLOW_47_in_arrayAccess1385); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_47.add(char_literal55);

							pushFollow(FOLLOW_methodCall_in_arrayAccess1389);
							call=methodCall(EvaluationExpression.VALUE);
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) stream_methodCall.add(call.getTree());
							// AST REWRITE
							// elements: call
							// token labels: 
							// rule labels: call, retval
							// token list labels: 
							// rule list labels: 
							// wildcard labels: 
							if ( state.backtracking==0 ) {
							retval.tree = root_0;
							RewriteRuleSubtreeStream stream_call=new RewriteRuleSubtreeStream(adaptor,"rule call",call!=null?call.getTree():null);
							RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

							root_0 = (EvaluationExpression)adaptor.nil();
							// 241:4: -> ^( EXPRESSION[\"ArrayProjection\"] $call)
							{
								// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:241:7: ^( EXPRESSION[\"ArrayProjection\"] $call)
								{
								EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
								root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "ArrayProjection"), root_1);
								adaptor.addChild(root_1, stream_call.nextTree());
								adaptor.addChild(root_0, root_1);
								}

							}


							retval.tree = root_0;
							}

							}
							break;
						case 2 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:242:6: path= pathSegment
							{
							pushFollow(FOLLOW_pathSegment_in_arrayAccess1412);
							path=pathSegment();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) stream_pathSegment.add(path.getTree());
							// AST REWRITE
							// elements: path
							// token labels: 
							// rule labels: retval, path
							// token list labels: 
							// rule list labels: 
							// wildcard labels: 
							if ( state.backtracking==0 ) {
							retval.tree = root_0;
							RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);
							RewriteRuleSubtreeStream stream_path=new RewriteRuleSubtreeStream(adaptor,"rule path",path!=null?path.getTree():null);

							root_0 = (EvaluationExpression)adaptor.nil();
							// 243:4: -> ^( EXPRESSION[\"ArrayProjection\"] $path)
							{
								// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:243:7: ^( EXPRESSION[\"ArrayProjection\"] $path)
								{
								EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
								root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "ArrayProjection"), root_1);
								adaptor.addChild(root_1, stream_path.nextTree());
								adaptor.addChild(root_0, root_1);
								}

							}


							retval.tree = root_0;
							}

							}
							break;

					}

					}
					break;
				case 2 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:244:5: '[' (pos= INTEGER |pos= UINT ) ']'
					{
					char_literal56=(Token)match(input,58,FOLLOW_58_in_arrayAccess1433); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_58.add(char_literal56);

					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:244:9: (pos= INTEGER |pos= UINT )
					int alt33=2;
					int LA33_0 = input.LA(1);
					if ( (LA33_0==INTEGER) ) {
						alt33=1;
					}
					else if ( (LA33_0==UINT) ) {
						alt33=2;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						NoViableAltException nvae =
							new NoViableAltException("", 33, 0, input);
						throw nvae;
					}

					switch (alt33) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:244:10: pos= INTEGER
							{
							pos=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_arrayAccess1438); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_INTEGER.add(pos);

							}
							break;
						case 2 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:244:24: pos= UINT
							{
							pos=(Token)match(input,UINT,FOLLOW_UINT_in_arrayAccess1444); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_UINT.add(pos);

							}
							break;

					}

					char_literal57=(Token)match(input,59,FOLLOW_59_in_arrayAccess1447); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_59.add(char_literal57);

					// AST REWRITE
					// elements: 
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (EvaluationExpression)adaptor.nil();
					// 245:3: -> ^( EXPRESSION[\"ArrayAccess\"] )
					{
						// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:245:6: ^( EXPRESSION[\"ArrayAccess\"] )
						{
						EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
						root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "ArrayAccess"), root_1);
						adaptor.addChild(root_1,  Integer.valueOf((pos!=null?pos.getText():null)) );
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;
				case 3 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:246:5: '[' (start= INTEGER |start= UINT ) ':' (end= INTEGER |end= UINT ) ']'
					{
					char_literal58=(Token)match(input,58,FOLLOW_58_in_arrayAccess1465); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_58.add(char_literal58);

					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:246:9: (start= INTEGER |start= UINT )
					int alt34=2;
					int LA34_0 = input.LA(1);
					if ( (LA34_0==INTEGER) ) {
						alt34=1;
					}
					else if ( (LA34_0==UINT) ) {
						alt34=2;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						NoViableAltException nvae =
							new NoViableAltException("", 34, 0, input);
						throw nvae;
					}

					switch (alt34) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:246:10: start= INTEGER
							{
							start=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_arrayAccess1470); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_INTEGER.add(start);

							}
							break;
						case 2 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:246:26: start= UINT
							{
							start=(Token)match(input,UINT,FOLLOW_UINT_in_arrayAccess1476); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_UINT.add(start);

							}
							break;

					}

					char_literal59=(Token)match(input,48,FOLLOW_48_in_arrayAccess1479); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_48.add(char_literal59);

					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:246:42: (end= INTEGER |end= UINT )
					int alt35=2;
					int LA35_0 = input.LA(1);
					if ( (LA35_0==INTEGER) ) {
						alt35=1;
					}
					else if ( (LA35_0==UINT) ) {
						alt35=2;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						NoViableAltException nvae =
							new NoViableAltException("", 35, 0, input);
						throw nvae;
					}

					switch (alt35) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:246:43: end= INTEGER
							{
							end=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_arrayAccess1484); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_INTEGER.add(end);

							}
							break;
						case 2 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:246:57: end= UINT
							{
							end=(Token)match(input,UINT,FOLLOW_UINT_in_arrayAccess1490); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_UINT.add(end);

							}
							break;

					}

					char_literal60=(Token)match(input,59,FOLLOW_59_in_arrayAccess1493); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_59.add(char_literal60);

					// AST REWRITE
					// elements: 
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (EvaluationExpression)adaptor.nil();
					// 247:3: -> ^( EXPRESSION[\"ArrayAccess\"] )
					{
						// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:247:6: ^( EXPRESSION[\"ArrayAccess\"] )
						{
						EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
						root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "ArrayAccess"), root_1);
						adaptor.addChild(root_1,  Integer.valueOf((start!=null?start.getText():null)) );
						adaptor.addChild(root_1,  Integer.valueOf((end!=null?end.getText():null)) );
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "arrayAccess"


	public static class valueExpression_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "valueExpression"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:249:1: valueExpression : ( ( ID '(' )=> functionCall | functionReference | ( FN )=>func= inlineFunction -> ^( EXPRESSION[\"ConstantExpression\"] ) | parenthesesExpression | literal | VAR ->| ( ( ID ':' )=>packageName= ID ':' )? constant= ID {...}? => ->| arrayCreation | objectCreation );
	public final MeteorParser.valueExpression_return valueExpression() throws RecognitionException {
		MeteorParser.valueExpression_return retval = new MeteorParser.valueExpression_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token packageName=null;
		Token constant=null;
		Token VAR65=null;
		Token char_literal66=null;
		ParserRuleReturnScope func =null;
		ParserRuleReturnScope functionCall61 =null;
		ParserRuleReturnScope functionReference62 =null;
		ParserRuleReturnScope parenthesesExpression63 =null;
		ParserRuleReturnScope literal64 =null;
		ParserRuleReturnScope arrayCreation67 =null;
		ParserRuleReturnScope objectCreation68 =null;

		EvaluationExpression packageName_tree=null;
		EvaluationExpression constant_tree=null;
		EvaluationExpression VAR65_tree=null;
		EvaluationExpression char_literal66_tree=null;
		RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
		RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
		RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
		RewriteRuleSubtreeStream stream_inlineFunction=new RewriteRuleSubtreeStream(adaptor,"rule inlineFunction");

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:250:2: ( ( ID '(' )=> functionCall | functionReference | ( FN )=>func= inlineFunction -> ^( EXPRESSION[\"ConstantExpression\"] ) | parenthesesExpression | literal | VAR ->| ( ( ID ':' )=>packageName= ID ':' )? constant= ID {...}? => ->| arrayCreation | objectCreation )
			int alt38=9;
			int LA38_0 = input.LA(1);
			if ( (LA38_0==ID) ) {
				int LA38_1 = input.LA(2);
				if ( (LA38_1==48) ) {
					int LA38_9 = input.LA(3);
					if ( (synpred17_Meteor()) ) {
						alt38=1;
					}
					else if ( (true) ) {
						alt38=7;
					}

				}
				else if ( (LA38_1==40) && (synpred17_Meteor())) {
					alt38=1;
				}
				else if ( (LA38_1==EOF||LA38_1==AND||(LA38_1 >= ID && LA38_1 <= IN)||LA38_1==NOT||LA38_1==OR||(LA38_1 >= SLASH && LA38_1 <= STAR)||(LA38_1 >= 37 && LA38_1 <= 38)||(LA38_1 >= 41 && LA38_1 <= 42)||(LA38_1 >= 44 && LA38_1 <= 45)||LA38_1==47||(LA38_1 >= 49 && LA38_1 <= 51)||(LA38_1 >= 53 && LA38_1 <= 59)||(LA38_1 >= 67 && LA38_1 <= 68)) ) {
					alt38=7;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return retval;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 38, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}
			else if ( (LA38_0==39) ) {
				alt38=2;
			}
			else if ( (LA38_0==FN) && (synpred18_Meteor())) {
				alt38=3;
			}
			else if ( (LA38_0==40) ) {
				alt38=4;
			}
			else if ( (LA38_0==DECIMAL||LA38_0==INTEGER||(LA38_0 >= STRING && LA38_0 <= UINT)||(LA38_0 >= 60 && LA38_0 <= 61)||LA38_0==63) ) {
				alt38=5;
			}
			else if ( (LA38_0==VAR) ) {
				alt38=6;
			}
			else if ( (LA38_0==58) ) {
				alt38=8;
			}
			else if ( (LA38_0==66) ) {
				alt38=9;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 38, 0, input);
				throw nvae;
			}

			switch (alt38) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:250:4: ( ID '(' )=> functionCall
					{
					root_0 = (EvaluationExpression)adaptor.nil();


					pushFollow(FOLLOW_functionCall_in_valueExpression1525);
					functionCall61=functionCall();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, functionCall61.getTree());

					}
					break;
				case 2 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:251:4: functionReference
					{
					root_0 = (EvaluationExpression)adaptor.nil();


					pushFollow(FOLLOW_functionReference_in_valueExpression1530);
					functionReference62=functionReference();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, functionReference62.getTree());

					}
					break;
				case 3 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:252:5: ( FN )=>func= inlineFunction
					{
					pushFollow(FOLLOW_inlineFunction_in_valueExpression1543);
					func=inlineFunction();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_inlineFunction.add(func.getTree());
					// AST REWRITE
					// elements: 
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (EvaluationExpression)adaptor.nil();
					// 252:32: -> ^( EXPRESSION[\"ConstantExpression\"] )
					{
						// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:252:35: ^( EXPRESSION[\"ConstantExpression\"] )
						{
						EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
						root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "ConstantExpression"), root_1);
						adaptor.addChild(root_1,  new FunctionNode((func!=null?((MeteorParser.inlineFunction_return)func).func:null)) );
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;
				case 4 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:253:4: parenthesesExpression
					{
					root_0 = (EvaluationExpression)adaptor.nil();


					pushFollow(FOLLOW_parenthesesExpression_in_valueExpression1557);
					parenthesesExpression63=parenthesesExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, parenthesesExpression63.getTree());

					}
					break;
				case 5 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:254:4: literal
					{
					root_0 = (EvaluationExpression)adaptor.nil();


					pushFollow(FOLLOW_literal_in_valueExpression1563);
					literal64=literal();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, literal64.getTree());

					}
					break;
				case 6 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:255:4: VAR
					{
					VAR65=(Token)match(input,VAR,FOLLOW_VAR_in_valueExpression1569); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_VAR.add(VAR65);

					// AST REWRITE
					// elements: 
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (EvaluationExpression)adaptor.nil();
					// 255:8: ->
					{
						adaptor.addChild(root_0,  getInputSelection(VAR65) );
					}


					retval.tree = root_0;
					}

					}
					break;
				case 7 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:256:5: ( ( ID ':' )=>packageName= ID ':' )? constant= ID {...}? =>
					{
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:256:5: ( ( ID ':' )=>packageName= ID ':' )?
					int alt37=2;
					int LA37_0 = input.LA(1);
					if ( (LA37_0==ID) ) {
						int LA37_1 = input.LA(2);
						if ( (LA37_1==48) ) {
							int LA37_2 = input.LA(3);
							if ( (synpred19_Meteor()) ) {
								alt37=1;
							}
						}
					}
					switch (alt37) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:256:6: ( ID ':' )=>packageName= ID ':'
							{
							packageName=(Token)match(input,ID,FOLLOW_ID_in_valueExpression1589); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_ID.add(packageName);

							char_literal66=(Token)match(input,48,FOLLOW_48_in_valueExpression1591); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_48.add(char_literal66);

							}
							break;

					}

					constant=(Token)match(input,ID,FOLLOW_ID_in_valueExpression1597); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_ID.add(constant);

					if ( !(( getScope((packageName!=null?packageName.getText():null)).getConstantRegistry().get((constant!=null?constant.getText():null)) != null )) ) {
						if (state.backtracking>0) {state.failed=true; return retval;}
						throw new FailedPredicateException(input, "valueExpression", " getScope($packageName.text).getConstantRegistry().get($constant.text) != null ");
					}
					// AST REWRITE
					// elements: 
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (EvaluationExpression)adaptor.nil();
					// 257:5: ->
					{
						adaptor.addChild(root_0,  getScope((packageName!=null?packageName.getText():null)).getConstantRegistry().get((constant!=null?constant.getText():null)) );
					}


					retval.tree = root_0;
					}

					}
					break;
				case 8 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:258:4: arrayCreation
					{
					root_0 = (EvaluationExpression)adaptor.nil();


					pushFollow(FOLLOW_arrayCreation_in_valueExpression1617);
					arrayCreation67=arrayCreation();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, arrayCreation67.getTree());

					}
					break;
				case 9 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:259:4: objectCreation
					{
					root_0 = (EvaluationExpression)adaptor.nil();


					pushFollow(FOLLOW_objectCreation_in_valueExpression1623);
					objectCreation68=objectCreation();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, objectCreation68.getTree());

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "valueExpression"


	public static class operatorExpression_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "operatorExpression"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:261:1: operatorExpression : op= operator -> ^( EXPRESSION[\"NestedOperatorExpression\"] ) ;
	public final MeteorParser.operatorExpression_return operatorExpression() throws RecognitionException {
		MeteorParser.operatorExpression_return retval = new MeteorParser.operatorExpression_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		ParserRuleReturnScope op =null;

		RewriteRuleSubtreeStream stream_operator=new RewriteRuleSubtreeStream(adaptor,"rule operator");

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:262:2: (op= operator -> ^( EXPRESSION[\"NestedOperatorExpression\"] ) )
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:262:4: op= operator
			{
			pushFollow(FOLLOW_operator_in_operatorExpression1635);
			op=operator();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_operator.add(op.getTree());
			// AST REWRITE
			// elements: 
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 262:16: -> ^( EXPRESSION[\"NestedOperatorExpression\"] )
			{
				// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:262:19: ^( EXPRESSION[\"NestedOperatorExpression\"] )
				{
				EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
				root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "NestedOperatorExpression"), root_1);
				adaptor.addChild(root_1,  (op!=null?((MeteorParser.operator_return)op).op:null) );
				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "operatorExpression"


	public static class parenthesesExpression_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "parenthesesExpression"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:264:1: parenthesesExpression : ( '(' expression ')' ) -> expression ;
	public final MeteorParser.parenthesesExpression_return parenthesesExpression() throws RecognitionException {
		MeteorParser.parenthesesExpression_return retval = new MeteorParser.parenthesesExpression_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token char_literal69=null;
		Token char_literal71=null;
		ParserRuleReturnScope expression70 =null;

		EvaluationExpression char_literal69_tree=null;
		EvaluationExpression char_literal71_tree=null;
		RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
		RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
		RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:265:2: ( ( '(' expression ')' ) -> expression )
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:265:4: ( '(' expression ')' )
			{
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:265:4: ( '(' expression ')' )
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:265:5: '(' expression ')'
			{
			char_literal69=(Token)match(input,40,FOLLOW_40_in_parenthesesExpression1654); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_40.add(char_literal69);

			pushFollow(FOLLOW_expression_in_parenthesesExpression1656);
			expression70=expression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_expression.add(expression70.getTree());
			char_literal71=(Token)match(input,41,FOLLOW_41_in_parenthesesExpression1658); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_41.add(char_literal71);

			}

			// AST REWRITE
			// elements: expression
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 265:25: -> expression
			{
				adaptor.addChild(root_0, stream_expression.nextTree());
			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "parenthesesExpression"


	public static class methodCall_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "methodCall"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:267:1: methodCall[EvaluationExpression targetExpr] : (packageName= ID ':' )? name= ID '(' ( (param= expression ) ( ',' (param= expression ) )* )? ')' ->;
	public final MeteorParser.methodCall_return methodCall(EvaluationExpression targetExpr) throws RecognitionException {
		MeteorParser.methodCall_return retval = new MeteorParser.methodCall_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token packageName=null;
		Token name=null;
		Token char_literal72=null;
		Token char_literal73=null;
		Token char_literal74=null;
		Token char_literal75=null;
		ParserRuleReturnScope param =null;

		EvaluationExpression packageName_tree=null;
		EvaluationExpression name_tree=null;
		EvaluationExpression char_literal72_tree=null;
		EvaluationExpression char_literal73_tree=null;
		EvaluationExpression char_literal74_tree=null;
		EvaluationExpression char_literal75_tree=null;
		RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
		RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
		RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
		RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
		RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
		RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");

		 List<EvaluationExpression> params = new ArrayList();
		        paraphrase.push("a method call"); 
		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:271:3: ( (packageName= ID ':' )? name= ID '(' ( (param= expression ) ( ',' (param= expression ) )* )? ')' ->)
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:271:5: (packageName= ID ':' )? name= ID '(' ( (param= expression ) ( ',' (param= expression ) )* )? ')'
			{
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:271:5: (packageName= ID ':' )?
			int alt39=2;
			int LA39_0 = input.LA(1);
			if ( (LA39_0==ID) ) {
				int LA39_1 = input.LA(2);
				if ( (LA39_1==48) ) {
					alt39=1;
				}
			}
			switch (alt39) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:271:6: packageName= ID ':'
					{
					packageName=(Token)match(input,ID,FOLLOW_ID_in_methodCall1688); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_ID.add(packageName);

					char_literal72=(Token)match(input,48,FOLLOW_48_in_methodCall1690); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_48.add(char_literal72);

					}
					break;

			}

			name=(Token)match(input,ID,FOLLOW_ID_in_methodCall1696); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_ID.add(name);

			char_literal73=(Token)match(input,40,FOLLOW_40_in_methodCall1698); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_40.add(char_literal73);

			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:272:3: ( (param= expression ) ( ',' (param= expression ) )* )?
			int alt41=2;
			int LA41_0 = input.LA(1);
			if ( (LA41_0==DECIMAL||LA41_0==FN||LA41_0==ID||LA41_0==INTEGER||(LA41_0 >= STRING && LA41_0 <= UINT)||LA41_0==VAR||LA41_0==36||(LA41_0 >= 39 && LA41_0 <= 40)||LA41_0==43||LA41_0==46||LA41_0==58||(LA41_0 >= 60 && LA41_0 <= 63)||(LA41_0 >= 65 && LA41_0 <= 66)||LA41_0==69) ) {
				alt41=1;
			}
			switch (alt41) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:272:4: (param= expression ) ( ',' (param= expression ) )*
					{
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:272:4: (param= expression )
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:272:5: param= expression
					{
					pushFollow(FOLLOW_expression_in_methodCall1707);
					param=expression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_expression.add(param.getTree());
					if ( state.backtracking==0 ) { params.add((param!=null?((EvaluationExpression)param.getTree()):null)); }
					}

					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:273:3: ( ',' (param= expression ) )*
					loop40:
					while (true) {
						int alt40=2;
						int LA40_0 = input.LA(1);
						if ( (LA40_0==44) ) {
							alt40=1;
						}

						switch (alt40) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:273:4: ',' (param= expression )
							{
							char_literal74=(Token)match(input,44,FOLLOW_44_in_methodCall1716); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_44.add(char_literal74);

							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:273:8: (param= expression )
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:273:9: param= expression
							{
							pushFollow(FOLLOW_expression_in_methodCall1721);
							param=expression();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) stream_expression.add(param.getTree());
							if ( state.backtracking==0 ) { params.add((param!=null?((EvaluationExpression)param.getTree()):null)); }
							}

							}
							break;

						default :
							break loop40;
						}
					}

					}
					break;

			}

			char_literal75=(Token)match(input,41,FOLLOW_41_in_methodCall1733); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_41.add(char_literal75);

			// AST REWRITE
			// elements: 
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 274:7: ->
			{
				adaptor.addChild(root_0,  createCheckedMethodCall((packageName!=null?packageName.getText():null), name, targetExpr, params.toArray(new EvaluationExpression[params.size()])) );
			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
			if ( state.backtracking==0 ) { paraphrase.pop(); }
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "methodCall"


	public static class functionCall_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "functionCall"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:276:1: functionCall : methodCall[null] ;
	public final MeteorParser.functionCall_return functionCall() throws RecognitionException {
		MeteorParser.functionCall_return retval = new MeteorParser.functionCall_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		ParserRuleReturnScope methodCall76 =null;


		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:277:2: ( methodCall[null] )
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:277:4: methodCall[null]
			{
			root_0 = (EvaluationExpression)adaptor.nil();


			pushFollow(FOLLOW_methodCall_in_functionCall1748);
			methodCall76=methodCall(null);
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) adaptor.addChild(root_0, methodCall76.getTree());

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "functionCall"


	public static class functionReference_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "functionReference"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:279:1: functionReference : '&' ( ( ID ':' )=>packageName= ID ':' )? name= ID -> ^( EXPRESSION[\"ConstantExpression\"] ) ;
	public final MeteorParser.functionReference_return functionReference() throws RecognitionException {
		MeteorParser.functionReference_return retval = new MeteorParser.functionReference_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token packageName=null;
		Token name=null;
		Token char_literal77=null;
		Token char_literal78=null;

		EvaluationExpression packageName_tree=null;
		EvaluationExpression name_tree=null;
		EvaluationExpression char_literal77_tree=null;
		EvaluationExpression char_literal78_tree=null;
		RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
		RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
		RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:280:3: ( '&' ( ( ID ':' )=>packageName= ID ':' )? name= ID -> ^( EXPRESSION[\"ConstantExpression\"] ) )
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:280:5: '&' ( ( ID ':' )=>packageName= ID ':' )? name= ID
			{
			char_literal77=(Token)match(input,39,FOLLOW_39_in_functionReference1759); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_39.add(char_literal77);

			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:280:9: ( ( ID ':' )=>packageName= ID ':' )?
			int alt42=2;
			int LA42_0 = input.LA(1);
			if ( (LA42_0==ID) ) {
				int LA42_1 = input.LA(2);
				if ( (LA42_1==48) ) {
					int LA42_2 = input.LA(3);
					if ( (LA42_2==ID) ) {
						int LA42_4 = input.LA(4);
						if ( (synpred20_Meteor()) ) {
							alt42=1;
						}
					}
				}
			}
			switch (alt42) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:280:10: ( ID ':' )=>packageName= ID ':'
					{
					packageName=(Token)match(input,ID,FOLLOW_ID_in_functionReference1771); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_ID.add(packageName);

					char_literal78=(Token)match(input,48,FOLLOW_48_in_functionReference1773); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_48.add(char_literal78);

					}
					break;

			}

			name=(Token)match(input,ID,FOLLOW_ID_in_functionReference1779); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_ID.add(name);

			// AST REWRITE
			// elements: 
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 281:9: -> ^( EXPRESSION[\"ConstantExpression\"] )
			{
				// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:281:12: ^( EXPRESSION[\"ConstantExpression\"] )
				{
				EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
				root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "ConstantExpression"), root_1);
				adaptor.addChild(root_1,  new FunctionNode(getSopremoFunction((packageName!=null?packageName.getText():null), name)) );
				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "functionReference"


	public static class fieldAssignment_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "fieldAssignment"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:283:1: fieldAssignment : ( ( ( ID ':' )=> ID ':' expression ->) | ( VAR '.' STAR )=> VAR '.' STAR ->| ( VAR )=>{...}? =>p= generalPathExpression ->|v= valueExpression ':' e2= expression ->);
	public final MeteorParser.fieldAssignment_return fieldAssignment() throws RecognitionException {
		MeteorParser.fieldAssignment_return retval = new MeteorParser.fieldAssignment_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token ID79=null;
		Token char_literal80=null;
		Token VAR82=null;
		Token char_literal83=null;
		Token STAR84=null;
		Token char_literal85=null;
		ParserRuleReturnScope p =null;
		ParserRuleReturnScope v =null;
		ParserRuleReturnScope e2 =null;
		ParserRuleReturnScope expression81 =null;

		EvaluationExpression ID79_tree=null;
		EvaluationExpression char_literal80_tree=null;
		EvaluationExpression VAR82_tree=null;
		EvaluationExpression char_literal83_tree=null;
		EvaluationExpression STAR84_tree=null;
		EvaluationExpression char_literal85_tree=null;
		RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
		RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
		RewriteRuleTokenStream stream_STAR=new RewriteRuleTokenStream(adaptor,"token STAR");
		RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
		RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
		RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
		RewriteRuleSubtreeStream stream_valueExpression=new RewriteRuleSubtreeStream(adaptor,"rule valueExpression");
		RewriteRuleSubtreeStream stream_generalPathExpression=new RewriteRuleSubtreeStream(adaptor,"rule generalPathExpression");

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:284:2: ( ( ( ID ':' )=> ID ':' expression ->) | ( VAR '.' STAR )=> VAR '.' STAR ->| ( VAR )=>{...}? =>p= generalPathExpression ->|v= valueExpression ':' e2= expression ->)
			int alt43=4;
			switch ( input.LA(1) ) {
			case ID:
				{
				int LA43_1 = input.LA(2);
				if ( (synpred21_Meteor()) ) {
					alt43=1;
				}
				else if ( ((synpred23_Meteor()&&( input.LA(2) != '.' ))) ) {
					alt43=3;
				}
				else if ( (true) ) {
					alt43=4;
				}

				}
				break;
			case VAR:
				{
				int LA43_2 = input.LA(2);
				if ( (synpred22_Meteor()) ) {
					alt43=2;
				}
				else if ( ((synpred23_Meteor()&&( input.LA(2) != '.' ))) ) {
					alt43=3;
				}
				else if ( (true) ) {
					alt43=4;
				}

				}
				break;
			case 39:
				{
				int LA43_3 = input.LA(2);
				if ( ((synpred23_Meteor()&&( input.LA(2) != '.' ))) ) {
					alt43=3;
				}
				else if ( (true) ) {
					alt43=4;
				}

				}
				break;
			case FN:
				{
				int LA43_4 = input.LA(2);
				if ( ((synpred23_Meteor()&&( input.LA(2) != '.' ))) ) {
					alt43=3;
				}
				else if ( (true) ) {
					alt43=4;
				}

				}
				break;
			case 40:
				{
				int LA43_5 = input.LA(2);
				if ( ((synpred23_Meteor()&&( input.LA(2) != '.' ))) ) {
					alt43=3;
				}
				else if ( (true) ) {
					alt43=4;
				}

				}
				break;
			case 63:
				{
				int LA43_6 = input.LA(2);
				if ( ((synpred23_Meteor()&&( input.LA(2) != '.' ))) ) {
					alt43=3;
				}
				else if ( (true) ) {
					alt43=4;
				}

				}
				break;
			case 60:
				{
				int LA43_7 = input.LA(2);
				if ( ((synpred23_Meteor()&&( input.LA(2) != '.' ))) ) {
					alt43=3;
				}
				else if ( (true) ) {
					alt43=4;
				}

				}
				break;
			case DECIMAL:
				{
				int LA43_8 = input.LA(2);
				if ( ((synpred23_Meteor()&&( input.LA(2) != '.' ))) ) {
					alt43=3;
				}
				else if ( (true) ) {
					alt43=4;
				}

				}
				break;
			case STRING:
				{
				int LA43_9 = input.LA(2);
				if ( ((synpred23_Meteor()&&( input.LA(2) != '.' ))) ) {
					alt43=3;
				}
				else if ( (true) ) {
					alt43=4;
				}

				}
				break;
			case UINT:
				{
				int LA43_10 = input.LA(2);
				if ( ((synpred23_Meteor()&&( input.LA(2) != '.' ))) ) {
					alt43=3;
				}
				else if ( (true) ) {
					alt43=4;
				}

				}
				break;
			case INTEGER:
				{
				int LA43_11 = input.LA(2);
				if ( ((synpred23_Meteor()&&( input.LA(2) != '.' ))) ) {
					alt43=3;
				}
				else if ( (true) ) {
					alt43=4;
				}

				}
				break;
			case 61:
				{
				int LA43_12 = input.LA(2);
				if ( ((synpred23_Meteor()&&( input.LA(2) != '.' ))) ) {
					alt43=3;
				}
				else if ( (true) ) {
					alt43=4;
				}

				}
				break;
			case 58:
				{
				int LA43_13 = input.LA(2);
				if ( ((synpred23_Meteor()&&( input.LA(2) != '.' ))) ) {
					alt43=3;
				}
				else if ( (true) ) {
					alt43=4;
				}

				}
				break;
			case 66:
				{
				int LA43_14 = input.LA(2);
				if ( ((synpred23_Meteor()&&( input.LA(2) != '.' ))) ) {
					alt43=3;
				}
				else if ( (true) ) {
					alt43=4;
				}

				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 43, 0, input);
				throw nvae;
			}
			switch (alt43) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:284:4: ( ( ID ':' )=> ID ':' expression ->)
					{
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:284:4: ( ( ID ':' )=> ID ':' expression ->)
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:284:5: ( ID ':' )=> ID ':' expression
					{
					ID79=(Token)match(input,ID,FOLLOW_ID_in_fieldAssignment1815); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_ID.add(ID79);

					char_literal80=(Token)match(input,48,FOLLOW_48_in_fieldAssignment1817); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_48.add(char_literal80);

					pushFollow(FOLLOW_expression_in_fieldAssignment1819);
					expression81=expression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_expression.add(expression81.getTree());
					if ( state.backtracking==0 ) { objectCreation_stack.peek().mappings.add(new ObjectCreation.FieldAssignment((ID79!=null?ID79.getText():null), (expression81!=null?((EvaluationExpression)expression81.getTree()):null))); }
					// AST REWRITE
					// elements: 
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (EvaluationExpression)adaptor.nil();
					// 285:104: ->
					{
						root_0 = null;
					}


					retval.tree = root_0;
					}

					}

					}
					break;
				case 2 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:286:5: ( VAR '.' STAR )=> VAR '.' STAR
					{
					VAR82=(Token)match(input,VAR,FOLLOW_VAR_in_fieldAssignment1845); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_VAR.add(VAR82);

					char_literal83=(Token)match(input,47,FOLLOW_47_in_fieldAssignment1847); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_47.add(char_literal83);

					STAR84=(Token)match(input,STAR,FOLLOW_STAR_in_fieldAssignment1849); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_STAR.add(STAR84);

					if ( state.backtracking==0 ) { objectCreation_stack.peek().mappings.add(new ObjectCreation.CopyFields(getInputSelection(VAR82))); }
					// AST REWRITE
					// elements: 
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (EvaluationExpression)adaptor.nil();
					// 286:126: ->
					{
						root_0 = null;
					}


					retval.tree = root_0;
					}

					}
					break;
				case 3 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:287:5: ( VAR )=>{...}? =>p= generalPathExpression
					{
					if ( !(( input.LA(2) != '.' )) ) {
						if (state.backtracking>0) {state.failed=true; return retval;}
						throw new FailedPredicateException(input, "fieldAssignment", " input.LA(2) != '.' ");
					}
					pushFollow(FOLLOW_generalPathExpression_in_fieldAssignment1869);
					p=generalPathExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_generalPathExpression.add(p.getTree());
					if ( state.backtracking==0 ) { objectCreation_stack.peek().mappings.add(new ObjectCreation.FieldAssignment(getAssignmentName((p!=null?((EvaluationExpression)p.getTree()):null)), (p!=null?((EvaluationExpression)p.getTree()):null))); }
					// AST REWRITE
					// elements: 
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (EvaluationExpression)adaptor.nil();
					// 287:171: ->
					{
						root_0 = null;
					}


					retval.tree = root_0;
					}

					}
					break;
				case 4 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:288:5: v= valueExpression ':' e2= expression
					{
					pushFollow(FOLLOW_valueExpression_in_fieldAssignment1881);
					v=valueExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_valueExpression.add(v.getTree());
					char_literal85=(Token)match(input,48,FOLLOW_48_in_fieldAssignment1883); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_48.add(char_literal85);

					pushFollow(FOLLOW_expression_in_fieldAssignment1887);
					e2=expression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_expression.add(e2.getTree());
					if ( state.backtracking==0 ) { objectCreation_stack.peek().mappings.add(new ObjectCreation.TagMapping((v!=null?((EvaluationExpression)v.getTree()):null), (e2!=null?((EvaluationExpression)e2.getTree()):null))); }
					// AST REWRITE
					// elements: 
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (EvaluationExpression)adaptor.nil();
					// 288:126: ->
					{
						root_0 = null;
					}


					retval.tree = root_0;
					}

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}
		catch (MismatchedTokenException | NoViableAltException re) {
			 explainUsage("inside of a json object {...} only <field: expression>, <$var.path>, <$var = operator> or <$var: expression> are allowed", re); 
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "fieldAssignment"


	protected static class objectCreation_scope {
		List<ObjectCreation.Mapping> mappings;
	}
	protected Stack<objectCreation_scope> objectCreation_stack = new Stack<objectCreation_scope>();

	public static class objectCreation_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "objectCreation"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:292:1: objectCreation : '{' ( fieldAssignment ( ',' fieldAssignment )* ( ',' )? )? '}' -> ^( EXPRESSION[\"ObjectCreation\"] ) ;
	public final MeteorParser.objectCreation_return objectCreation() throws RecognitionException {
		objectCreation_stack.push(new objectCreation_scope());
		MeteorParser.objectCreation_return retval = new MeteorParser.objectCreation_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token char_literal86=null;
		Token char_literal88=null;
		Token char_literal90=null;
		Token char_literal91=null;
		ParserRuleReturnScope fieldAssignment87 =null;
		ParserRuleReturnScope fieldAssignment89 =null;

		EvaluationExpression char_literal86_tree=null;
		EvaluationExpression char_literal88_tree=null;
		EvaluationExpression char_literal90_tree=null;
		EvaluationExpression char_literal91_tree=null;
		RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
		RewriteRuleTokenStream stream_68=new RewriteRuleTokenStream(adaptor,"token 68");
		RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
		RewriteRuleSubtreeStream stream_fieldAssignment=new RewriteRuleSubtreeStream(adaptor,"rule fieldAssignment");

		 objectCreation_stack.peek().mappings = new ArrayList<ObjectCreation.Mapping>(); 
		        paraphrase.push("a json object"); 
		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:297:2: ( '{' ( fieldAssignment ( ',' fieldAssignment )* ( ',' )? )? '}' -> ^( EXPRESSION[\"ObjectCreation\"] ) )
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:297:4: '{' ( fieldAssignment ( ',' fieldAssignment )* ( ',' )? )? '}'
			{
			char_literal86=(Token)match(input,66,FOLLOW_66_in_objectCreation1925); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_66.add(char_literal86);

			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:297:8: ( fieldAssignment ( ',' fieldAssignment )* ( ',' )? )?
			int alt46=2;
			int LA46_0 = input.LA(1);
			if ( (LA46_0==DECIMAL||LA46_0==FN||LA46_0==ID||LA46_0==INTEGER||(LA46_0 >= STRING && LA46_0 <= UINT)||LA46_0==VAR||(LA46_0 >= 39 && LA46_0 <= 40)||LA46_0==58||(LA46_0 >= 60 && LA46_0 <= 61)||LA46_0==63||LA46_0==66) ) {
				alt46=1;
			}
			switch (alt46) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:297:9: fieldAssignment ( ',' fieldAssignment )* ( ',' )?
					{
					pushFollow(FOLLOW_fieldAssignment_in_objectCreation1928);
					fieldAssignment87=fieldAssignment();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_fieldAssignment.add(fieldAssignment87.getTree());
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:297:25: ( ',' fieldAssignment )*
					loop44:
					while (true) {
						int alt44=2;
						int LA44_0 = input.LA(1);
						if ( (LA44_0==44) ) {
							int LA44_1 = input.LA(2);
							if ( (LA44_1==DECIMAL||LA44_1==FN||LA44_1==ID||LA44_1==INTEGER||(LA44_1 >= STRING && LA44_1 <= UINT)||LA44_1==VAR||(LA44_1 >= 39 && LA44_1 <= 40)||LA44_1==58||(LA44_1 >= 60 && LA44_1 <= 61)||LA44_1==63||LA44_1==66) ) {
								alt44=1;
							}

						}

						switch (alt44) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:297:26: ',' fieldAssignment
							{
							char_literal88=(Token)match(input,44,FOLLOW_44_in_objectCreation1931); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_44.add(char_literal88);

							pushFollow(FOLLOW_fieldAssignment_in_objectCreation1933);
							fieldAssignment89=fieldAssignment();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) stream_fieldAssignment.add(fieldAssignment89.getTree());
							}
							break;

						default :
							break loop44;
						}
					}

					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:297:48: ( ',' )?
					int alt45=2;
					int LA45_0 = input.LA(1);
					if ( (LA45_0==44) ) {
						alt45=1;
					}
					switch (alt45) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:297:48: ','
							{
							char_literal90=(Token)match(input,44,FOLLOW_44_in_objectCreation1937); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_44.add(char_literal90);

							}
							break;

					}

					}
					break;

			}

			char_literal91=(Token)match(input,68,FOLLOW_68_in_objectCreation1942); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_68.add(char_literal91);

			// AST REWRITE
			// elements: 
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 297:59: -> ^( EXPRESSION[\"ObjectCreation\"] )
			{
				// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:297:62: ^( EXPRESSION[\"ObjectCreation\"] )
				{
				EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
				root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "ObjectCreation"), root_1);
				adaptor.addChild(root_1,  objectCreation_stack.peek().mappings );
				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
			if ( state.backtracking==0 ) { paraphrase.pop(); }
		}
		catch (MissingTokenException re) {
			 explainUsage("expected <,> or <}> after a complete field assignment inside of a json object", re); 
		}

		finally {
			// do for sure before leaving
			objectCreation_stack.pop();
		}
		return retval;
	}
	// $ANTLR end "objectCreation"


	public static class literal_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "literal"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:300:1: literal : (val= 'true' -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= 'false' -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= DECIMAL -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= STRING -> ^( EXPRESSION[\"ConstantExpression\"] ) | (val= UINT |val= INTEGER ) -> ^( EXPRESSION[\"ConstantExpression\"] ) | 'null' ->);
	public final MeteorParser.literal_return literal() throws RecognitionException {
		MeteorParser.literal_return retval = new MeteorParser.literal_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token val=null;
		Token string_literal92=null;

		EvaluationExpression val_tree=null;
		EvaluationExpression string_literal92_tree=null;
		RewriteRuleTokenStream stream_INTEGER=new RewriteRuleTokenStream(adaptor,"token INTEGER");
		RewriteRuleTokenStream stream_DECIMAL=new RewriteRuleTokenStream(adaptor,"token DECIMAL");
		RewriteRuleTokenStream stream_UINT=new RewriteRuleTokenStream(adaptor,"token UINT");
		RewriteRuleTokenStream stream_63=new RewriteRuleTokenStream(adaptor,"token 63");
		RewriteRuleTokenStream stream_60=new RewriteRuleTokenStream(adaptor,"token 60");
		RewriteRuleTokenStream stream_61=new RewriteRuleTokenStream(adaptor,"token 61");
		RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

		 paraphrase.push("a literal"); 
		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:303:2: (val= 'true' -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= 'false' -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= DECIMAL -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= STRING -> ^( EXPRESSION[\"ConstantExpression\"] ) | (val= UINT |val= INTEGER ) -> ^( EXPRESSION[\"ConstantExpression\"] ) | 'null' ->)
			int alt48=6;
			switch ( input.LA(1) ) {
			case 63:
				{
				alt48=1;
				}
				break;
			case 60:
				{
				alt48=2;
				}
				break;
			case DECIMAL:
				{
				alt48=3;
				}
				break;
			case STRING:
				{
				alt48=4;
				}
				break;
			case INTEGER:
			case UINT:
				{
				alt48=5;
				}
				break;
			case 61:
				{
				alt48=6;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 48, 0, input);
				throw nvae;
			}
			switch (alt48) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:303:4: val= 'true'
					{
					val=(Token)match(input,63,FOLLOW_63_in_literal1980); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_63.add(val);

					// AST REWRITE
					// elements: 
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (EvaluationExpression)adaptor.nil();
					// 303:15: -> ^( EXPRESSION[\"ConstantExpression\"] )
					{
						// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:303:18: ^( EXPRESSION[\"ConstantExpression\"] )
						{
						EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
						root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "ConstantExpression"), root_1);
						adaptor.addChild(root_1,  Boolean.TRUE );
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;
				case 2 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:304:4: val= 'false'
					{
					val=(Token)match(input,60,FOLLOW_60_in_literal1996); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_60.add(val);

					// AST REWRITE
					// elements: 
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (EvaluationExpression)adaptor.nil();
					// 304:16: -> ^( EXPRESSION[\"ConstantExpression\"] )
					{
						// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:304:19: ^( EXPRESSION[\"ConstantExpression\"] )
						{
						EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
						root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "ConstantExpression"), root_1);
						adaptor.addChild(root_1,  Boolean.FALSE );
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;
				case 3 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:305:4: val= DECIMAL
					{
					val=(Token)match(input,DECIMAL,FOLLOW_DECIMAL_in_literal2012); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_DECIMAL.add(val);

					// AST REWRITE
					// elements: 
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (EvaluationExpression)adaptor.nil();
					// 305:16: -> ^( EXPRESSION[\"ConstantExpression\"] )
					{
						// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:305:19: ^( EXPRESSION[\"ConstantExpression\"] )
						{
						EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
						root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "ConstantExpression"), root_1);
						adaptor.addChild(root_1,  new BigDecimal((val!=null?val.getText():null)) );
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;
				case 4 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:306:4: val= STRING
					{
					val=(Token)match(input,STRING,FOLLOW_STRING_in_literal2028); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_STRING.add(val);

					// AST REWRITE
					// elements: 
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (EvaluationExpression)adaptor.nil();
					// 306:15: -> ^( EXPRESSION[\"ConstantExpression\"] )
					{
						// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:306:18: ^( EXPRESSION[\"ConstantExpression\"] )
						{
						EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
						root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "ConstantExpression"), root_1);
						adaptor.addChild(root_1,  val.getText() );
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;
				case 5 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:307:5: (val= UINT |val= INTEGER )
					{
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:307:5: (val= UINT |val= INTEGER )
					int alt47=2;
					int LA47_0 = input.LA(1);
					if ( (LA47_0==UINT) ) {
						alt47=1;
					}
					else if ( (LA47_0==INTEGER) ) {
						alt47=2;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						NoViableAltException nvae =
							new NoViableAltException("", 47, 0, input);
						throw nvae;
					}

					switch (alt47) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:307:6: val= UINT
							{
							val=(Token)match(input,UINT,FOLLOW_UINT_in_literal2046); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_UINT.add(val);

							}
							break;
						case 2 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:307:17: val= INTEGER
							{
							val=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_literal2052); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_INTEGER.add(val);

							}
							break;

					}

					// AST REWRITE
					// elements: 
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (EvaluationExpression)adaptor.nil();
					// 307:30: -> ^( EXPRESSION[\"ConstantExpression\"] )
					{
						// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:307:33: ^( EXPRESSION[\"ConstantExpression\"] )
						{
						EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
						root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "ConstantExpression"), root_1);
						adaptor.addChild(root_1,  parseInt((val!=null?val.getText():null)) );
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;
					}

					}
					break;
				case 6 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:308:5: 'null'
					{
					string_literal92=(Token)match(input,61,FOLLOW_61_in_literal2068); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_61.add(string_literal92);

					// AST REWRITE
					// elements: 
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					if ( state.backtracking==0 ) {
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (EvaluationExpression)adaptor.nil();
					// 308:12: ->
					{
						adaptor.addChild(root_0,  ConstantExpression.NULL );
					}


					retval.tree = root_0;
					}

					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
			if ( state.backtracking==0 ) { paraphrase.pop(); }
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "literal"


	public static class arrayCreation_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "arrayCreation"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:310:1: arrayCreation : '[' (elems+= expression ( ',' elems+= expression )* ( ',' )? )? ']' -> ^( EXPRESSION[\"ArrayCreation\"] ) ;
	public final MeteorParser.arrayCreation_return arrayCreation() throws RecognitionException {
		MeteorParser.arrayCreation_return retval = new MeteorParser.arrayCreation_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token char_literal93=null;
		Token char_literal94=null;
		Token char_literal95=null;
		Token char_literal96=null;
		List<Object> list_elems=null;
		RuleReturnScope elems = null;
		EvaluationExpression char_literal93_tree=null;
		EvaluationExpression char_literal94_tree=null;
		EvaluationExpression char_literal95_tree=null;
		EvaluationExpression char_literal96_tree=null;
		RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
		RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
		RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
		RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");

		 paraphrase.push("a json array"); 
		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:313:2: ( '[' (elems+= expression ( ',' elems+= expression )* ( ',' )? )? ']' -> ^( EXPRESSION[\"ArrayCreation\"] ) )
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:313:5: '[' (elems+= expression ( ',' elems+= expression )* ( ',' )? )? ']'
			{
			char_literal93=(Token)match(input,58,FOLLOW_58_in_arrayCreation2092); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_58.add(char_literal93);

			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:313:9: (elems+= expression ( ',' elems+= expression )* ( ',' )? )?
			int alt51=2;
			int LA51_0 = input.LA(1);
			if ( (LA51_0==DECIMAL||LA51_0==FN||LA51_0==ID||LA51_0==INTEGER||(LA51_0 >= STRING && LA51_0 <= UINT)||LA51_0==VAR||LA51_0==36||(LA51_0 >= 39 && LA51_0 <= 40)||LA51_0==43||LA51_0==46||LA51_0==58||(LA51_0 >= 60 && LA51_0 <= 63)||(LA51_0 >= 65 && LA51_0 <= 66)||LA51_0==69) ) {
				alt51=1;
			}
			switch (alt51) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:313:10: elems+= expression ( ',' elems+= expression )* ( ',' )?
					{
					pushFollow(FOLLOW_expression_in_arrayCreation2097);
					elems=expression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_expression.add(elems.getTree());
					if (list_elems==null) list_elems=new ArrayList<Object>();
					list_elems.add(elems.getTree());
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:313:28: ( ',' elems+= expression )*
					loop49:
					while (true) {
						int alt49=2;
						int LA49_0 = input.LA(1);
						if ( (LA49_0==44) ) {
							int LA49_1 = input.LA(2);
							if ( (LA49_1==DECIMAL||LA49_1==FN||LA49_1==ID||LA49_1==INTEGER||(LA49_1 >= STRING && LA49_1 <= UINT)||LA49_1==VAR||LA49_1==36||(LA49_1 >= 39 && LA49_1 <= 40)||LA49_1==43||LA49_1==46||LA49_1==58||(LA49_1 >= 60 && LA49_1 <= 63)||(LA49_1 >= 65 && LA49_1 <= 66)||LA49_1==69) ) {
								alt49=1;
							}

						}

						switch (alt49) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:313:29: ',' elems+= expression
							{
							char_literal94=(Token)match(input,44,FOLLOW_44_in_arrayCreation2100); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_44.add(char_literal94);

							pushFollow(FOLLOW_expression_in_arrayCreation2104);
							elems=expression();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) stream_expression.add(elems.getTree());
							if (list_elems==null) list_elems=new ArrayList<Object>();
							list_elems.add(elems.getTree());
							}
							break;

						default :
							break loop49;
						}
					}

					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:313:53: ( ',' )?
					int alt50=2;
					int LA50_0 = input.LA(1);
					if ( (LA50_0==44) ) {
						alt50=1;
					}
					switch (alt50) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:313:53: ','
							{
							char_literal95=(Token)match(input,44,FOLLOW_44_in_arrayCreation2108); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_44.add(char_literal95);

							}
							break;

					}

					}
					break;

			}

			char_literal96=(Token)match(input,59,FOLLOW_59_in_arrayCreation2113); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_59.add(char_literal96);

			// AST REWRITE
			// elements: 
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 313:64: -> ^( EXPRESSION[\"ArrayCreation\"] )
			{
				// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:313:67: ^( EXPRESSION[\"ArrayCreation\"] )
				{
				EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
				root_1 = (EvaluationExpression)adaptor.becomeRoot((EvaluationExpression)adaptor.create(EXPRESSION, "ArrayCreation"), root_1);
				adaptor.addChild(root_1,  list_elems == null ? new EvaluationExpression[0] : list_elems.toArray(new EvaluationExpression[list_elems.size()]) );
				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
			if ( state.backtracking==0 ) { paraphrase.pop(); }
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "arrayCreation"


	protected static class operator_scope {
		int numInputs;
		Operator<?> result;
	}
	protected Stack<operator_scope> operator_stack = new Stack<operator_scope>();

	public static class operator_return extends ParserRuleReturnScope {
		public Operator<?> op=null;
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "operator"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:319:1: operator returns [Operator<?> op=null] : ( readOperator | writeOperator | genericOperator );
	public final MeteorParser.operator_return operator() throws RecognitionException {
		operator_stack.push(new operator_scope());
		MeteorParser.operator_return retval = new MeteorParser.operator_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		ParserRuleReturnScope readOperator97 =null;
		ParserRuleReturnScope writeOperator98 =null;
		ParserRuleReturnScope genericOperator99 =null;


		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:323:2: ( readOperator | writeOperator | genericOperator )
			int alt52=3;
			switch ( input.LA(1) ) {
			case VAR:
				{
				int LA52_1 = input.LA(2);
				if ( (LA52_1==52) ) {
					int LA52_5 = input.LA(3);
					if ( (LA52_5==62) ) {
						alt52=1;
					}
					else if ( (LA52_5==ID) ) {
						alt52=3;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 52, 5, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

				}
				else if ( (LA52_1==44) ) {
					alt52=3;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return retval;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 52, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case 62:
				{
				alt52=1;
				}
				break;
			case 65:
				{
				alt52=2;
				}
				break;
			case ID:
				{
				alt52=3;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 52, 0, input);
				throw nvae;
			}
			switch (alt52) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:323:4: readOperator
					{
					root_0 = (EvaluationExpression)adaptor.nil();


					pushFollow(FOLLOW_readOperator_in_operator2139);
					readOperator97=readOperator();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, readOperator97.getTree());

					if ( state.backtracking==0 ) { retval.op = (readOperator97!=null?((MeteorParser.readOperator_return)readOperator97).source:null); }
					}
					break;
				case 2 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:324:5: writeOperator
					{
					root_0 = (EvaluationExpression)adaptor.nil();


					pushFollow(FOLLOW_writeOperator_in_operator2147);
					writeOperator98=writeOperator();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, writeOperator98.getTree());

					if ( state.backtracking==0 ) { retval.op = (writeOperator98!=null?((MeteorParser.writeOperator_return)writeOperator98).sink:null); }
					}
					break;
				case 3 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:325:5: genericOperator
					{
					root_0 = (EvaluationExpression)adaptor.nil();


					pushFollow(FOLLOW_genericOperator_in_operator2155);
					genericOperator99=genericOperator();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) adaptor.addChild(root_0, genericOperator99.getTree());

					if ( state.backtracking==0 ) { retval.op = (genericOperator99!=null?((MeteorParser.genericOperator_return)genericOperator99).op:null); }
					}
					break;

			}
			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
			operator_stack.pop();
		}
		return retval;
	}
	// $ANTLR end "operator"


	public static class adhocSource_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "adhocSource"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:327:1: adhocSource : output= VAR '=' exp= arrayCreation ->;
	public final MeteorParser.adhocSource_return adhocSource() throws RecognitionException {
		MeteorParser.adhocSource_return retval = new MeteorParser.adhocSource_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token output=null;
		Token char_literal100=null;
		ParserRuleReturnScope exp =null;

		EvaluationExpression output_tree=null;
		EvaluationExpression char_literal100_tree=null;
		RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
		RewriteRuleTokenStream stream_52=new RewriteRuleTokenStream(adaptor,"token 52");
		RewriteRuleSubtreeStream stream_arrayCreation=new RewriteRuleSubtreeStream(adaptor,"rule arrayCreation");

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:327:12: (output= VAR '=' exp= arrayCreation ->)
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:328:3: output= VAR '=' exp= arrayCreation
			{
			output=(Token)match(input,VAR,FOLLOW_VAR_in_adhocSource2169); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_VAR.add(output);

			char_literal100=(Token)match(input,52,FOLLOW_52_in_adhocSource2171); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_52.add(char_literal100);

			pushFollow(FOLLOW_arrayCreation_in_adhocSource2175);
			exp=arrayCreation();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_arrayCreation.add(exp.getTree());
			if ( state.backtracking==0 ) { 
			  Source source = new Source((exp!=null?((EvaluationExpression)exp.getTree()):null));
			  putVariable(output, new JsonStreamExpression(source));
			}
			// AST REWRITE
			// elements: 
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 332:3: ->
			{
				root_0 = null;
			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "adhocSource"


	public static class readOperator_return extends ParserRuleReturnScope {
		public Source source;
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "readOperator"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:335:1: readOperator returns [Source source] : (output= VAR '=' )? 'read' ( (packageName= ID ':' )? format= ID )? {...}? ID pathExp= ternaryExpression ( confOption[getOperatorInfo($source), $source] )* ->;
	public final MeteorParser.readOperator_return readOperator() throws RecognitionException {
		MeteorParser.readOperator_return retval = new MeteorParser.readOperator_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token output=null;
		Token packageName=null;
		Token format=null;
		Token char_literal101=null;
		Token string_literal102=null;
		Token char_literal103=null;
		Token ID104=null;
		ParserRuleReturnScope pathExp =null;
		ParserRuleReturnScope confOption105 =null;

		EvaluationExpression output_tree=null;
		EvaluationExpression packageName_tree=null;
		EvaluationExpression format_tree=null;
		EvaluationExpression char_literal101_tree=null;
		EvaluationExpression string_literal102_tree=null;
		EvaluationExpression char_literal103_tree=null;
		EvaluationExpression ID104_tree=null;
		RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
		RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
		RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
		RewriteRuleTokenStream stream_52=new RewriteRuleTokenStream(adaptor,"token 52");
		RewriteRuleTokenStream stream_62=new RewriteRuleTokenStream(adaptor,"token 62");
		RewriteRuleSubtreeStream stream_confOption=new RewriteRuleSubtreeStream(adaptor,"rule confOption");
		RewriteRuleSubtreeStream stream_ternaryExpression=new RewriteRuleSubtreeStream(adaptor,"rule ternaryExpression");

		 
		  ConfObjectInfo<? extends SopremoFormat> formatInfo = null;
		  SopremoFormat fileFormat = null;
		  String path = null;

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:341:2: ( (output= VAR '=' )? 'read' ( (packageName= ID ':' )? format= ID )? {...}? ID pathExp= ternaryExpression ( confOption[getOperatorInfo($source), $source] )* ->)
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:341:4: (output= VAR '=' )? 'read' ( (packageName= ID ':' )? format= ID )? {...}? ID pathExp= ternaryExpression ( confOption[getOperatorInfo($source), $source] )*
			{
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:341:4: (output= VAR '=' )?
			int alt53=2;
			int LA53_0 = input.LA(1);
			if ( (LA53_0==VAR) ) {
				alt53=1;
			}
			switch (alt53) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:341:5: output= VAR '='
					{
					output=(Token)match(input,VAR,FOLLOW_VAR_in_readOperator2203); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_VAR.add(output);

					char_literal101=(Token)match(input,52,FOLLOW_52_in_readOperator2205); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_52.add(char_literal101);

					}
					break;

			}

			string_literal102=(Token)match(input,62,FOLLOW_62_in_readOperator2213); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_62.add(string_literal102);

			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:342:11: ( (packageName= ID ':' )? format= ID )?
			int alt55=2;
			int LA55_0 = input.LA(1);
			if ( (LA55_0==ID) ) {
				int LA55_1 = input.LA(2);
				if ( (!(((input.LT(1).getText().equals("from"))))) ) {
					alt55=1;
				}
			}
			switch (alt55) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:342:12: (packageName= ID ':' )? format= ID
					{
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:342:12: (packageName= ID ':' )?
					int alt54=2;
					int LA54_0 = input.LA(1);
					if ( (LA54_0==ID) ) {
						int LA54_1 = input.LA(2);
						if ( (LA54_1==48) ) {
							alt54=1;
						}
					}
					switch (alt54) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:342:13: packageName= ID ':'
							{
							packageName=(Token)match(input,ID,FOLLOW_ID_in_readOperator2219); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_ID.add(packageName);

							char_literal103=(Token)match(input,48,FOLLOW_48_in_readOperator2221); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_48.add(char_literal103);

							}
							break;

					}

					format=(Token)match(input,ID,FOLLOW_ID_in_readOperator2226); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_ID.add(format);

					}
					break;

			}

			if ( !((input.LT(1).getText().equals("from"))) ) {
				if (state.backtracking>0) {state.failed=true; return retval;}
				throw new FailedPredicateException(input, "readOperator", "input.LT(1).getText().equals(\"from\")");
			}
			ID104=(Token)match(input,ID,FOLLOW_ID_in_readOperator2235); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_ID.add(ID104);

			pushFollow(FOLLOW_ternaryExpression_in_readOperator2239);
			pathExp=ternaryExpression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_ternaryExpression.add(pathExp.getTree());
			if ( state.backtracking==0 ) { 
			  path = makeFilePath((pathExp!=null?((EvaluationExpression)pathExp.getTree()):null));
			  formatInfo = findFormat((packageName!=null?packageName.getText():null), format, path);
			  fileFormat = formatInfo.newInstance(); 
			  retval.source = new Source(fileFormat, path); 
			  if(output != null)
			    putVariable(output, new JsonStreamExpression(retval.source));
			}
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:351:4: ( confOption[getOperatorInfo($source), $source] )*
			loop56:
			while (true) {
				int alt56=2;
				int LA56_0 = input.LA(1);
				if ( (LA56_0==ID) ) {
					alt56=1;
				}

				switch (alt56) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:351:4: confOption[getOperatorInfo($source), $source]
					{
					pushFollow(FOLLOW_confOption_in_readOperator2244);
					confOption105=confOption(getOperatorInfo(retval.source), retval.source);
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_confOption.add(confOption105.getTree());
					}
					break;

				default :
					break loop56;
				}
			}

			// AST REWRITE
			// elements: 
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 352:2: ->
			{
				root_0 = null;
			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "readOperator"


	public static class writeOperator_return extends ParserRuleReturnScope {
		public Sink sink;
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "writeOperator"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:355:1: writeOperator returns [Sink sink] : 'write' ( (packageName= ID ':' )? format= ID )? from= VAR {...}? ID pathExp= ternaryExpression ( confOption[getOperatorInfo($sink), $sink] )* ->;
	public final MeteorParser.writeOperator_return writeOperator() throws RecognitionException {
		MeteorParser.writeOperator_return retval = new MeteorParser.writeOperator_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token packageName=null;
		Token format=null;
		Token from=null;
		Token string_literal106=null;
		Token char_literal107=null;
		Token ID108=null;
		ParserRuleReturnScope pathExp =null;
		ParserRuleReturnScope confOption109 =null;

		EvaluationExpression packageName_tree=null;
		EvaluationExpression format_tree=null;
		EvaluationExpression from_tree=null;
		EvaluationExpression string_literal106_tree=null;
		EvaluationExpression char_literal107_tree=null;
		EvaluationExpression ID108_tree=null;
		RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
		RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
		RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
		RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
		RewriteRuleSubtreeStream stream_confOption=new RewriteRuleSubtreeStream(adaptor,"rule confOption");
		RewriteRuleSubtreeStream stream_ternaryExpression=new RewriteRuleSubtreeStream(adaptor,"rule ternaryExpression");

		 
		  ConfObjectInfo<? extends SopremoFormat> formatInfo = null;
		  SopremoFormat fileFormat = null;
		  String path = null;

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:360:3: ( 'write' ( (packageName= ID ':' )? format= ID )? from= VAR {...}? ID pathExp= ternaryExpression ( confOption[getOperatorInfo($sink), $sink] )* ->)
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:360:5: 'write' ( (packageName= ID ':' )? format= ID )? from= VAR {...}? ID pathExp= ternaryExpression ( confOption[getOperatorInfo($sink), $sink] )*
			{
			string_literal106=(Token)match(input,65,FOLLOW_65_in_writeOperator2268); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_65.add(string_literal106);

			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:361:5: ( (packageName= ID ':' )? format= ID )?
			int alt58=2;
			int LA58_0 = input.LA(1);
			if ( (LA58_0==ID) ) {
				alt58=1;
			}
			switch (alt58) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:361:6: (packageName= ID ':' )? format= ID
					{
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:361:6: (packageName= ID ':' )?
					int alt57=2;
					int LA57_0 = input.LA(1);
					if ( (LA57_0==ID) ) {
						int LA57_1 = input.LA(2);
						if ( (LA57_1==48) ) {
							alt57=1;
						}
					}
					switch (alt57) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:361:7: packageName= ID ':'
							{
							packageName=(Token)match(input,ID,FOLLOW_ID_in_writeOperator2279); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_ID.add(packageName);

							char_literal107=(Token)match(input,48,FOLLOW_48_in_writeOperator2281); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_48.add(char_literal107);

							}
							break;

					}

					format=(Token)match(input,ID,FOLLOW_ID_in_writeOperator2286); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_ID.add(format);

					}
					break;

			}

			from=(Token)match(input,VAR,FOLLOW_VAR_in_writeOperator2292); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_VAR.add(from);

			if ( !((input.LT(1).getText().equals("to"))) ) {
				if (state.backtracking>0) {state.failed=true; return retval;}
				throw new FailedPredicateException(input, "writeOperator", "input.LT(1).getText().equals(\"to\")");
			}
			ID108=(Token)match(input,ID,FOLLOW_ID_in_writeOperator2300); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_ID.add(ID108);

			pushFollow(FOLLOW_ternaryExpression_in_writeOperator2304);
			pathExp=ternaryExpression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_ternaryExpression.add(pathExp.getTree());
			if ( state.backtracking==0 ) { 
			  path = makeFilePath((pathExp!=null?((EvaluationExpression)pathExp.getTree()):null));
			  formatInfo = findFormat((packageName!=null?packageName.getText():null), format, path);
			  fileFormat = formatInfo.newInstance();
				retval.sink = new Sink(fileFormat, path);
			  retval.sink.setInputs(getVariableSafely(from).getStream());
			  this.sinks.add(retval.sink);
			}
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:370:3: ( confOption[getOperatorInfo($sink), $sink] )*
			loop59:
			while (true) {
				int alt59=2;
				int LA59_0 = input.LA(1);
				if ( (LA59_0==ID) ) {
					alt59=1;
				}

				switch (alt59) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:370:3: confOption[getOperatorInfo($sink), $sink]
					{
					pushFollow(FOLLOW_confOption_in_writeOperator2308);
					confOption109=confOption(getOperatorInfo(retval.sink), retval.sink);
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_confOption.add(confOption109.getTree());
					}
					break;

				default :
					break loop59;
				}
			}

			// AST REWRITE
			// elements: 
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 370:46: ->
			{
				root_0 = null;
			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "writeOperator"


	public static class genericOperator_return extends ParserRuleReturnScope {
		public Operator<?> op;
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "genericOperator"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:376:1: genericOperator returns [Operator<?> op] : (targets+= VAR ( ',' targets+= VAR )* '=' )? (packageName= ID ':' )? name= ID {...}? => ( ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )* )? ( confOption[operatorInfo, $op] )* ->;
	public final MeteorParser.genericOperator_return genericOperator() throws RecognitionException {
		MeteorParser.genericOperator_return retval = new MeteorParser.genericOperator_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token packageName=null;
		Token name=null;
		Token char_literal110=null;
		Token char_literal111=null;
		Token char_literal112=null;
		Token char_literal114=null;
		Token targets=null;
		List<Object> list_targets=null;
		ParserRuleReturnScope input113 =null;
		ParserRuleReturnScope input115 =null;
		ParserRuleReturnScope confOption116 =null;

		EvaluationExpression packageName_tree=null;
		EvaluationExpression name_tree=null;
		EvaluationExpression char_literal110_tree=null;
		EvaluationExpression char_literal111_tree=null;
		EvaluationExpression char_literal112_tree=null;
		EvaluationExpression char_literal114_tree=null;
		EvaluationExpression targets_tree=null;
		RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
		RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
		RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
		RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
		RewriteRuleTokenStream stream_52=new RewriteRuleTokenStream(adaptor,"token 52");
		RewriteRuleSubtreeStream stream_input=new RewriteRuleSubtreeStream(adaptor,"rule input");
		RewriteRuleSubtreeStream stream_confOption=new RewriteRuleSubtreeStream(adaptor,"rule confOption");

		 
		  ConfObjectInfo<? extends Operator<?>> operatorInfo;

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:382:3: ( (targets+= VAR ( ',' targets+= VAR )* '=' )? (packageName= ID ':' )? name= ID {...}? => ( ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )* )? ( confOption[operatorInfo, $op] )* ->)
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:383:2: (targets+= VAR ( ',' targets+= VAR )* '=' )? (packageName= ID ':' )? name= ID {...}? => ( ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )* )? ( confOption[operatorInfo, $op] )*
			{
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:383:2: (targets+= VAR ( ',' targets+= VAR )* '=' )?
			int alt61=2;
			int LA61_0 = input.LA(1);
			if ( (LA61_0==VAR) ) {
				alt61=1;
			}
			switch (alt61) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:383:2: targets+= VAR ( ',' targets+= VAR )* '='
					{
					targets=(Token)match(input,VAR,FOLLOW_VAR_in_genericOperator2342); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_VAR.add(targets);

					if (list_targets==null) list_targets=new ArrayList<Object>();
					list_targets.add(targets);
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:383:15: ( ',' targets+= VAR )*
					loop60:
					while (true) {
						int alt60=2;
						int LA60_0 = input.LA(1);
						if ( (LA60_0==44) ) {
							alt60=1;
						}

						switch (alt60) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:383:16: ',' targets+= VAR
							{
							char_literal110=(Token)match(input,44,FOLLOW_44_in_genericOperator2345); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_44.add(char_literal110);

							targets=(Token)match(input,VAR,FOLLOW_VAR_in_genericOperator2349); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_VAR.add(targets);

							if (list_targets==null) list_targets=new ArrayList<Object>();
							list_targets.add(targets);
							}
							break;

						default :
							break loop60;
						}
					}

					char_literal111=(Token)match(input,52,FOLLOW_52_in_genericOperator2353); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_52.add(char_literal111);

					}
					break;

			}

			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:384:2: (packageName= ID ':' )?
			int alt62=2;
			int LA62_0 = input.LA(1);
			if ( (LA62_0==ID) ) {
				int LA62_1 = input.LA(2);
				if ( (LA62_1==48) ) {
					alt62=1;
				}
			}
			switch (alt62) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:384:2: packageName= ID ':'
					{
					packageName=(Token)match(input,ID,FOLLOW_ID_in_genericOperator2361); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_ID.add(packageName);

					char_literal112=(Token)match(input,48,FOLLOW_48_in_genericOperator2363); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_48.add(char_literal112);

					}
					break;

			}

			name=(Token)match(input,ID,FOLLOW_ID_in_genericOperator2369); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_ID.add(name);

			if ( !(( (operatorInfo = findOperatorGreedily((packageName!=null?packageName.getText():null), name)) != null  )) ) {
				if (state.backtracking>0) {state.failed=true; return retval;}
				throw new FailedPredicateException(input, "genericOperator", " (operatorInfo = findOperatorGreedily($packageName.text, $name)) != null  ");
			}
			if ( state.backtracking==0 ) { 
			  operator_stack.peek().result = retval.op = operatorInfo.newInstance(); 
			  // add scope for input variables and recursive definition
			  if(state.backtracking == 0) 
			    addScope();   
			}
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:392:2: ( ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )* )?
			int alt64=2;
			int LA64_0 = input.LA(1);
			if ( (LA64_0==VAR) && (synpred24_Meteor())) {
				alt64=1;
			}
			switch (alt64) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:392:2: ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )*
					{
					pushFollow(FOLLOW_input_in_genericOperator2385);
					input113=input(operatorInfo, retval.op);
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_input.add(input113.getTree());
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:392:35: ( ( ',' )=> ',' input[operatorInfo, $op] )*
					loop63:
					while (true) {
						int alt63=2;
						int LA63_0 = input.LA(1);
						if ( (LA63_0==44) ) {
							int LA63_2 = input.LA(2);
							if ( (LA63_2==VAR) ) {
								int LA63_3 = input.LA(3);
								if ( (synpred25_Meteor()) ) {
									alt63=1;
								}

							}

						}

						switch (alt63) {
						case 1 :
							// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:392:36: ( ',' )=> ',' input[operatorInfo, $op]
							{
							char_literal114=(Token)match(input,44,FOLLOW_44_in_genericOperator2394); if (state.failed) return retval; 
							if ( state.backtracking==0 ) stream_44.add(char_literal114);

							pushFollow(FOLLOW_input_in_genericOperator2396);
							input115=input(operatorInfo, retval.op);
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) stream_input.add(input115.getTree());
							}
							break;

						default :
							break loop63;
						}
					}

					}
					break;

			}

			if ( state.backtracking==0 ) { // register output names for explicit references to output 
			  if(list_targets != null)
			    for(int index = 0; index < list_targets.size(); index++)
			      putVariable((Token) list_targets.get(index), new JsonStreamExpression(retval.op.getOutput(index)), 1);   
			}
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:398:11: ( confOption[operatorInfo, $op] )*
			loop65:
			while (true) {
				int alt65=2;
				int LA65_0 = input.LA(1);
				if ( (LA65_0==ID) ) {
					alt65=1;
				}

				switch (alt65) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:398:11: confOption[operatorInfo, $op]
					{
					pushFollow(FOLLOW_confOption_in_genericOperator2406);
					confOption116=confOption(operatorInfo, retval.op);
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_confOption.add(confOption116.getTree());
					}
					break;

				default :
					break loop65;
				}
			}

			// AST REWRITE
			// elements: 
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 399:3: ->
			{
				root_0 = null;
			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
			if ( state.backtracking==0 ) {
			  removeScope();
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "genericOperator"


	public static class confOption_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "confOption"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:401:1: confOption[ConfObjectInfo<?> info, ConfigurableSopremoType object] : name= ID {...}? =>expr= ternaryExpression ->;
	public final MeteorParser.confOption_return confOption(ConfObjectInfo<?> info, ConfigurableSopremoType object) throws RecognitionException {
		MeteorParser.confOption_return retval = new MeteorParser.confOption_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token name=null;
		ParserRuleReturnScope expr =null;

		EvaluationExpression name_tree=null;
		RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
		RewriteRuleSubtreeStream stream_ternaryExpression=new RewriteRuleSubtreeStream(adaptor,"rule ternaryExpression");


		 ConfObjectInfo.ConfObjectPropertyInfo property = null;

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:404:3: (name= ID {...}? =>expr= ternaryExpression ->)
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:405:3: name= ID {...}? =>expr= ternaryExpression
			{
			name=(Token)match(input,ID,FOLLOW_ID_in_confOption2433); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_ID.add(name);

			if ( !(( (property = findPropertyGreedily(object, info, name)) != null )) ) {
				if (state.backtracking>0) {state.failed=true; return retval;}
				throw new FailedPredicateException(input, "confOption", " (property = findPropertyGreedily(object, info, name)) != null ");
			}
			pushFollow(FOLLOW_ternaryExpression_in_confOption2443);
			expr=ternaryExpression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) stream_ternaryExpression.add(expr.getTree());
			if ( state.backtracking==0 ) { property.setValue(object, (expr!=null?((EvaluationExpression)expr.getTree()):null)); }
			// AST REWRITE
			// elements: 
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 407:69: ->
			{
				root_0 = null;
			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "confOption"


	public static class input_return extends ParserRuleReturnScope {
		EvaluationExpression tree;
		@Override
		public EvaluationExpression getTree() { return tree; }
	};


	// $ANTLR start "input"
	// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:409:1: input[ConfObjectInfo<?> info, Operator<?> object] : (name= VAR IN )? from= VAR ({...}? =>expr= ternaryExpression )? ->;
	public final MeteorParser.input_return input(ConfObjectInfo<?> info, Operator<?> object) throws RecognitionException {
		MeteorParser.input_return retval = new MeteorParser.input_return();
		retval.start = input.LT(1);

		EvaluationExpression root_0 = null;

		Token name=null;
		Token from=null;
		Token IN117=null;
		ParserRuleReturnScope expr =null;

		EvaluationExpression name_tree=null;
		EvaluationExpression from_tree=null;
		EvaluationExpression IN117_tree=null;
		RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
		RewriteRuleTokenStream stream_IN=new RewriteRuleTokenStream(adaptor,"token IN");
		RewriteRuleSubtreeStream stream_ternaryExpression=new RewriteRuleSubtreeStream(adaptor,"rule ternaryExpression");


		 ConfObjectInfo.ConfObjectIndexedPropertyInfo inputProperty = null;

		try {
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:412:3: ( (name= VAR IN )? from= VAR ({...}? =>expr= ternaryExpression )? ->)
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:412:5: (name= VAR IN )? from= VAR ({...}? =>expr= ternaryExpression )?
			{
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:412:5: (name= VAR IN )?
			int alt66=2;
			int LA66_0 = input.LA(1);
			if ( (LA66_0==VAR) ) {
				int LA66_1 = input.LA(2);
				if ( (LA66_1==IN) ) {
					alt66=1;
				}
			}
			switch (alt66) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:412:6: name= VAR IN
					{
					name=(Token)match(input,VAR,FOLLOW_VAR_in_input2465); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_VAR.add(name);

					IN117=(Token)match(input,IN,FOLLOW_IN_in_input2467); if (state.failed) return retval; 
					if ( state.backtracking==0 ) stream_IN.add(IN117);

					}
					break;

			}

			from=(Token)match(input,VAR,FOLLOW_VAR_in_input2473); if (state.failed) return retval; 
			if ( state.backtracking==0 ) stream_VAR.add(from);

			if ( state.backtracking==0 ) { 
			  int inputIndex = operator_stack.peek().numInputs++;
			  JsonStreamExpression input = getVariableSafely(from);
			  object.setInput(inputIndex, input.getStream());
			  
			  if(operator_stack.size() == 1) {
				  JsonStreamExpression inputExpression = new JsonStreamExpression(input.getStream(), inputIndex);
				  putVariable(name != null ? name : from, inputExpression);
			  }
			}
			// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:423:2: ({...}? =>expr= ternaryExpression )?
			int alt67=2;
			int LA67_0 = input.LA(1);
			if ( (LA67_0==DECIMAL||LA67_0==FN||LA67_0==INTEGER||(LA67_0 >= STRING && LA67_0 <= UINT)||LA67_0==VAR||LA67_0==36||(LA67_0 >= 39 && LA67_0 <= 40)||LA67_0==43||LA67_0==46||LA67_0==58||(LA67_0 >= 60 && LA67_0 <= 61)||LA67_0==63||LA67_0==66||LA67_0==69) && (( (findInputPropertyRelunctantly(object, info, input.LT(1), false) != null) ))) {
				alt67=1;
			}
			else if ( (LA67_0==ID) ) {
				int LA67_5 = input.LA(2);
				if ( (( (findInputPropertyRelunctantly(object, info, input.LT(1), false) != null) )) ) {
					alt67=1;
				}
			}
			switch (alt67) {
				case 1 :
					// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:423:2: {...}? =>expr= ternaryExpression
					{
					if ( !(( (findInputPropertyRelunctantly(object, info, input.LT(1), false) != null) )) ) {
						if (state.backtracking>0) {state.failed=true; return retval;}
						throw new FailedPredicateException(input, "input", " (findInputPropertyRelunctantly(object, info, input.LT(1), false) != null) ");
					}
					if ( state.backtracking==0 ) { inputProperty = findInputPropertyRelunctantly(object, info, input.LT(1), true); }
					pushFollow(FOLLOW_ternaryExpression_in_input2490);
					expr=ternaryExpression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) stream_ternaryExpression.add(expr.getTree());
					if ( state.backtracking==0 ) { inputProperty.setValue(object, operator_stack.peek().numInputs-1, (expr!=null?((EvaluationExpression)expr.getTree()):null)); }
					}
					break;

			}

			// AST REWRITE
			// elements: 
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			if ( state.backtracking==0 ) {
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (EvaluationExpression)adaptor.nil();
			// 426:4: ->
			{
				root_0 = null;
			}


			retval.tree = root_0;
			}

			}

			retval.stop = input.LT(-1);

			if ( state.backtracking==0 ) {
			retval.tree = (EvaluationExpression)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
			}
		}

		catch (RecognitionException e) {
		  throw e;
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "input"

	// $ANTLR start synpred1_Meteor
	public final void synpred1_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:110:5: ( ID '=' FN )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:110:6: ID '=' FN
		{
		match(input,ID,FOLLOW_ID_in_synpred1_Meteor222); if (state.failed) return;

		match(input,52,FOLLOW_52_in_synpred1_Meteor224); if (state.failed) return;

		match(input,FN,FOLLOW_FN_in_synpred1_Meteor226); if (state.failed) return;

		}

	}
	// $ANTLR end synpred1_Meteor

	// $ANTLR start synpred2_Meteor
	public final void synpred2_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:111:5: ( ID '=' JAVAUDF )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:111:6: ID '=' JAVAUDF
		{
		match(input,ID,FOLLOW_ID_in_synpred2_Meteor238); if (state.failed) return;

		match(input,52,FOLLOW_52_in_synpred2_Meteor240); if (state.failed) return;

		match(input,JAVAUDF,FOLLOW_JAVAUDF_in_synpred2_Meteor242); if (state.failed) return;

		}

	}
	// $ANTLR end synpred2_Meteor

	// $ANTLR start synpred3_Meteor
	public final void synpred3_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:147:5: ( ID ( ID | VAR ) )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:147:6: ID ( ID | VAR )
		{
		match(input,ID,FOLLOW_ID_in_synpred3_Meteor441); if (state.failed) return;

		if ( input.LA(1)==ID||input.LA(1)==VAR ) {
			input.consume();
			state.errorRecovery=false;
			state.failed=false;
		}
		else {
			if (state.backtracking>0) {state.failed=true; return;}
			MismatchedSetException mse = new MismatchedSetException(null,input);
			throw mse;
		}
		}

	}
	// $ANTLR end synpred3_Meteor

	// $ANTLR start synpred4_Meteor
	public final void synpred4_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:151:4: ( orExpression '?' )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:151:5: orExpression '?'
		{
		pushFollow(FOLLOW_orExpression_in_synpred4_Meteor469);
		orExpression();
		state._fsp--;
		if (state.failed) return;

		match(input,56,FOLLOW_56_in_synpred4_Meteor471); if (state.failed) return;

		}

	}
	// $ANTLR end synpred4_Meteor

	// $ANTLR start synpred5_Meteor
	public final void synpred5_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:153:4: ( orExpression IF )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:153:5: orExpression IF
		{
		pushFollow(FOLLOW_orExpression_in_synpred5_Meteor511);
		orExpression();
		state._fsp--;
		if (state.failed) return;

		match(input,IF,FOLLOW_IF_in_synpred5_Meteor513); if (state.failed) return;

		}

	}
	// $ANTLR end synpred5_Meteor

	// $ANTLR start synpred6_Meteor
	public final void synpred6_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:205:4: ( '(' ID ')' )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:205:5: '(' ID ')'
		{
		match(input,40,FOLLOW_40_in_synpred6_Meteor982); if (state.failed) return;

		match(input,ID,FOLLOW_ID_in_synpred6_Meteor984); if (state.failed) return;

		match(input,41,FOLLOW_41_in_synpred6_Meteor986); if (state.failed) return;

		}

	}
	// $ANTLR end synpred6_Meteor

	// $ANTLR start synpred7_Meteor
	public final void synpred7_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:212:5: ( pathExpression[EvaluationExpression.VALUE] )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:212:6: pathExpression[EvaluationExpression.VALUE]
		{
		pushFollow(FOLLOW_pathExpression_in_synpred7_Meteor1049);
		pathExpression(EvaluationExpression.VALUE);
		state._fsp--;
		if (state.failed) return;

		}

	}
	// $ANTLR end synpred7_Meteor

	// $ANTLR start synpred8_Meteor
	public final void synpred8_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:220:5: ( '?.' ID '(' )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:220:6: '?.' ID '('
		{
		match(input,57,FOLLOW_57_in_synpred8_Meteor1105); if (state.failed) return;

		match(input,ID,FOLLOW_ID_in_synpred8_Meteor1107); if (state.failed) return;

		match(input,40,FOLLOW_40_in_synpred8_Meteor1109); if (state.failed) return;

		}

	}
	// $ANTLR end synpred8_Meteor

	// $ANTLR start synpred9_Meteor
	public final void synpred9_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:221:8: ( pathSegment )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:221:9: pathSegment
		{
		pushFollow(FOLLOW_pathSegment_in_synpred9_Meteor1128);
		pathSegment();
		state._fsp--;
		if (state.failed) return;

		}

	}
	// $ANTLR end synpred9_Meteor

	// $ANTLR start synpred10_Meteor
	public final void synpred10_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:224:5: ( '.' ID '(' )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:224:6: '.' ID '('
		{
		match(input,47,FOLLOW_47_in_synpred10_Meteor1176); if (state.failed) return;

		match(input,ID,FOLLOW_ID_in_synpred10_Meteor1178); if (state.failed) return;

		match(input,40,FOLLOW_40_in_synpred10_Meteor1180); if (state.failed) return;

		}

	}
	// $ANTLR end synpred10_Meteor

	// $ANTLR start synpred11_Meteor
	public final void synpred11_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:225:8: ( pathSegment )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:225:9: pathSegment
		{
		pushFollow(FOLLOW_pathSegment_in_synpred11_Meteor1199);
		pathSegment();
		state._fsp--;
		if (state.failed) return;

		}

	}
	// $ANTLR end synpred11_Meteor

	// $ANTLR start synpred12_Meteor
	public final void synpred12_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:228:6: ( pathSegment )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:228:7: pathSegment
		{
		pushFollow(FOLLOW_pathSegment_in_synpred12_Meteor1241);
		pathSegment();
		state._fsp--;
		if (state.failed) return;

		}

	}
	// $ANTLR end synpred12_Meteor

	// $ANTLR start synpred13_Meteor
	public final void synpred13_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:235:5: ( '?.' )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:235:6: '?.'
		{
		match(input,57,FOLLOW_57_in_synpred13_Meteor1295); if (state.failed) return;

		}

	}
	// $ANTLR end synpred13_Meteor

	// $ANTLR start synpred14_Meteor
	public final void synpred14_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:236:5: ( '.' )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:236:6: '.'
		{
		match(input,47,FOLLOW_47_in_synpred14_Meteor1326); if (state.failed) return;

		}

	}
	// $ANTLR end synpred14_Meteor

	// $ANTLR start synpred15_Meteor
	public final void synpred15_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:237:5: ( '[' )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:237:6: '['
		{
		match(input,58,FOLLOW_58_in_synpred15_Meteor1355); if (state.failed) return;

		}

	}
	// $ANTLR end synpred15_Meteor

	// $ANTLR start synpred16_Meteor
	public final void synpred16_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:240:19: ( '.' methodCall[null] )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:240:20: '.' methodCall[null]
		{
		match(input,47,FOLLOW_47_in_synpred16_Meteor1378); if (state.failed) return;

		pushFollow(FOLLOW_methodCall_in_synpred16_Meteor1380);
		methodCall(null);
		state._fsp--;
		if (state.failed) return;

		}

	}
	// $ANTLR end synpred16_Meteor

	// $ANTLR start synpred17_Meteor
	public final void synpred17_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:250:4: ( ID '(' )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:250:5: ID '('
		{
		match(input,ID,FOLLOW_ID_in_synpred17_Meteor1519); if (state.failed) return;

		match(input,40,FOLLOW_40_in_synpred17_Meteor1521); if (state.failed) return;

		}

	}
	// $ANTLR end synpred17_Meteor

	// $ANTLR start synpred18_Meteor
	public final void synpred18_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:252:5: ( FN )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:252:6: FN
		{
		match(input,FN,FOLLOW_FN_in_synpred18_Meteor1537); if (state.failed) return;

		}

	}
	// $ANTLR end synpred18_Meteor

	// $ANTLR start synpred19_Meteor
	public final void synpred19_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:256:6: ( ID ':' )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:256:7: ID ':'
		{
		match(input,ID,FOLLOW_ID_in_synpred19_Meteor1581); if (state.failed) return;

		match(input,48,FOLLOW_48_in_synpred19_Meteor1583); if (state.failed) return;

		}

	}
	// $ANTLR end synpred19_Meteor

	// $ANTLR start synpred20_Meteor
	public final void synpred20_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:280:10: ( ID ':' )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:280:11: ID ':'
		{
		match(input,ID,FOLLOW_ID_in_synpred20_Meteor1763); if (state.failed) return;

		match(input,48,FOLLOW_48_in_synpred20_Meteor1765); if (state.failed) return;

		}

	}
	// $ANTLR end synpred20_Meteor

	// $ANTLR start synpred21_Meteor
	public final void synpred21_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:284:5: ( ID ':' )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:284:6: ID ':'
		{
		match(input,ID,FOLLOW_ID_in_synpred21_Meteor1809); if (state.failed) return;

		match(input,48,FOLLOW_48_in_synpred21_Meteor1811); if (state.failed) return;

		}

	}
	// $ANTLR end synpred21_Meteor

	// $ANTLR start synpred22_Meteor
	public final void synpred22_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:286:5: ( VAR '.' STAR )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:286:6: VAR '.' STAR
		{
		match(input,VAR,FOLLOW_VAR_in_synpred22_Meteor1837); if (state.failed) return;

		match(input,47,FOLLOW_47_in_synpred22_Meteor1839); if (state.failed) return;

		match(input,STAR,FOLLOW_STAR_in_synpred22_Meteor1841); if (state.failed) return;

		}

	}
	// $ANTLR end synpred22_Meteor

	// $ANTLR start synpred23_Meteor
	public final void synpred23_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:287:5: ( VAR )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:287:6: VAR
		{
		match(input,VAR,FOLLOW_VAR_in_synpred23_Meteor1860); if (state.failed) return;

		}

	}
	// $ANTLR end synpred23_Meteor

	// $ANTLR start synpred24_Meteor
	public final void synpred24_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:392:2: ( VAR )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:392:3: VAR
		{
		match(input,VAR,FOLLOW_VAR_in_synpred24_Meteor2381); if (state.failed) return;

		}

	}
	// $ANTLR end synpred24_Meteor

	// $ANTLR start synpred25_Meteor
	public final void synpred25_Meteor_fragment() throws RecognitionException {
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:392:36: ( ',' )
		// /home/arv/workspace/stratosphere-sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:392:37: ','
		{
		match(input,44,FOLLOW_44_in_synpred25_Meteor2390); if (state.failed) return;

		}

	}
	// $ANTLR end synpred25_Meteor

	// Delegated rules

	public final boolean synpred22_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred22_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred8_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred8_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred13_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred13_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred18_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred18_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred1_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred1_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred10_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred10_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred12_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred12_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred11_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred11_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred20_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred20_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred23_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred23_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred7_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred7_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred9_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred9_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred25_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred25_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred14_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred14_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred19_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred19_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred17_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred17_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred2_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred2_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred6_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred6_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred15_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred15_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred3_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred3_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred5_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred5_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred4_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred4_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred21_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred21_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred24_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred24_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred16_Meteor() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred16_Meteor_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}



	public static final BitSet FOLLOW_statement_in_script131 = new BitSet(new long[]{0x0002000000000000L});
	public static final BitSet FOLLOW_49_in_script133 = new BitSet(new long[]{0x4002000400008002L,0x0000000000000003L});
	public static final BitSet FOLLOW_operator_in_statement147 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_packageImport_in_statement151 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_adhocSource_in_statement155 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_definition_in_statement159 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_functionCall_in_statement169 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_64_in_packageImport186 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_ID_in_packageImport190 = new BitSet(new long[]{0x0000100000000002L});
	public static final BitSet FOLLOW_44_in_packageImport201 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_ID_in_packageImport205 = new BitSet(new long[]{0x0000100000000002L});
	public static final BitSet FOLLOW_functionDefinition_in_definition230 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_javaudf_in_definition246 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_constantDefinition_in_definition252 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_functionDefinition264 = new BitSet(new long[]{0x0010000000000000L});
	public static final BitSet FOLLOW_52_in_functionDefinition266 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_inlineFunction_in_functionDefinition270 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_constantDefinition289 = new BitSet(new long[]{0x0010000000000000L});
	public static final BitSet FOLLOW_52_in_constantDefinition291 = new BitSet(new long[]{0xB4004994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_ternaryExpression_in_constantDefinition295 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_FN_in_inlineFunction321 = new BitSet(new long[]{0x0000010000000000L});
	public static final BitSet FOLLOW_40_in_inlineFunction323 = new BitSet(new long[]{0x0000020000008000L});
	public static final BitSet FOLLOW_ID_in_inlineFunction332 = new BitSet(new long[]{0x0000120000000000L});
	public static final BitSet FOLLOW_44_in_inlineFunction339 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_ID_in_inlineFunction343 = new BitSet(new long[]{0x0000120000000000L});
	public static final BitSet FOLLOW_41_in_inlineFunction354 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
	public static final BitSet FOLLOW_66_in_inlineFunction364 = new BitSet(new long[]{0xF4004994C008A080L,0x0000000000000026L});
	public static final BitSet FOLLOW_expression_in_inlineFunction368 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
	public static final BitSet FOLLOW_68_in_inlineFunction370 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_javaudf390 = new BitSet(new long[]{0x0010000000000000L});
	public static final BitSet FOLLOW_52_in_javaudf392 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_JAVAUDF_in_javaudf394 = new BitSet(new long[]{0x0000010000000000L});
	public static final BitSet FOLLOW_40_in_javaudf396 = new BitSet(new long[]{0x0000000040000000L});
	public static final BitSet FOLLOW_STRING_in_javaudf400 = new BitSet(new long[]{0x0000020000000000L});
	public static final BitSet FOLLOW_41_in_javaudf402 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ternaryExpression_in_contextAwareExpression430 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_operatorExpression_in_expression453 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ternaryExpression_in_expression459 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_orExpression_in_ternaryExpression477 = new BitSet(new long[]{0x0100000000000000L});
	public static final BitSet FOLLOW_56_in_ternaryExpression479 = new BitSet(new long[]{0xB4014994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_orExpression_in_ternaryExpression483 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_ternaryExpression486 = new BitSet(new long[]{0xB4004994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_orExpression_in_ternaryExpression490 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_orExpression_in_ternaryExpression519 = new BitSet(new long[]{0x0000000000010000L});
	public static final BitSet FOLLOW_IF_in_ternaryExpression521 = new BitSet(new long[]{0xB4004994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_orExpression_in_ternaryExpression525 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_orExpression_in_ternaryExpression548 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_andExpression_in_orExpression561 = new BitSet(new long[]{0x0000000002000002L,0x0000000000000008L});
	public static final BitSet FOLLOW_OR_in_orExpression565 = new BitSet(new long[]{0xB4004994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_67_in_orExpression569 = new BitSet(new long[]{0xB4004994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_andExpression_in_orExpression574 = new BitSet(new long[]{0x0000000002000002L,0x0000000000000008L});
	public static final BitSet FOLLOW_elementExpression_in_andExpression603 = new BitSet(new long[]{0x0000004000000012L});
	public static final BitSet FOLLOW_AND_in_andExpression607 = new BitSet(new long[]{0xB4004994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_38_in_andExpression611 = new BitSet(new long[]{0xB4004994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_elementExpression_in_andExpression616 = new BitSet(new long[]{0x0000004000000012L});
	public static final BitSet FOLLOW_comparisonExpression_in_elementExpression645 = new BitSet(new long[]{0x0000000000420002L});
	public static final BitSet FOLLOW_NOT_in_elementExpression650 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_IN_in_elementExpression653 = new BitSet(new long[]{0xB4004994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_elementExpression_in_elementExpression657 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_arithmeticExpression_in_comparisonExpression698 = new BitSet(new long[]{0x00EC002000000002L});
	public static final BitSet FOLLOW_51_in_comparisonExpression704 = new BitSet(new long[]{0xB4004994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_55_in_comparisonExpression710 = new BitSet(new long[]{0xB4004994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_50_in_comparisonExpression716 = new BitSet(new long[]{0xB4004994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_54_in_comparisonExpression722 = new BitSet(new long[]{0xB4004994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_53_in_comparisonExpression728 = new BitSet(new long[]{0xB4004994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_37_in_comparisonExpression734 = new BitSet(new long[]{0xB4004994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_comparisonExpression_in_comparisonExpression739 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_multiplicationExpression_in_arithmeticExpression819 = new BitSet(new long[]{0x0000240000000002L});
	public static final BitSet FOLLOW_42_in_arithmeticExpression825 = new BitSet(new long[]{0xB4004994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_45_in_arithmeticExpression831 = new BitSet(new long[]{0xB4004994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_arithmeticExpression_in_arithmeticExpression836 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_preincrementExpression_in_multiplicationExpression879 = new BitSet(new long[]{0x0000000030000002L});
	public static final BitSet FOLLOW_STAR_in_multiplicationExpression885 = new BitSet(new long[]{0xB4004994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_SLASH_in_multiplicationExpression891 = new BitSet(new long[]{0xB4004994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_preincrementExpression_in_multiplicationExpression896 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_43_in_preincrementExpression937 = new BitSet(new long[]{0xB4004994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_preincrementExpression_in_preincrementExpression939 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_46_in_preincrementExpression944 = new BitSet(new long[]{0xB4004994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_preincrementExpression_in_preincrementExpression946 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unaryExpression_in_preincrementExpression951 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_castExpression_in_unaryExpression970 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_40_in_castExpression990 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_ID_in_castExpression994 = new BitSet(new long[]{0x0000020000000000L});
	public static final BitSet FOLLOW_41_in_castExpression996 = new BitSet(new long[]{0xB4000184C008A080L,0x0000000000000004L});
	public static final BitSet FOLLOW_generalPathExpression_in_castExpression1000 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_generalPathExpression_in_castExpression1013 = new BitSet(new long[]{0x0000000000008002L});
	public static final BitSet FOLLOW_ID_in_castExpression1018 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_ID_in_castExpression1022 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_valueExpression_in_generalPathExpression1041 = new BitSet(new long[]{0x0600800000000002L});
	public static final BitSet FOLLOW_pathExpression_in_generalPathExpression1056 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_pathExpression_in_contextAwarePathExpression1085 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_57_in_pathExpression1113 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_methodCall_in_pathExpression1117 = new BitSet(new long[]{0x0600800000000002L});
	public static final BitSet FOLLOW_pathExpression_in_pathExpression1134 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_47_in_pathExpression1184 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_methodCall_in_pathExpression1188 = new BitSet(new long[]{0x0600800000000002L});
	public static final BitSet FOLLOW_pathExpression_in_pathExpression1205 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_pathSegment_in_pathExpression1231 = new BitSet(new long[]{0x0600800000000002L});
	public static final BitSet FOLLOW_pathExpression_in_pathExpression1247 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_57_in_pathSegment1299 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_ID_in_pathSegment1303 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_47_in_pathSegment1331 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_ID_in_pathSegment1335 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_arrayAccess_in_pathSegment1360 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_58_in_arrayAccess1370 = new BitSet(new long[]{0x0000000020000000L});
	public static final BitSet FOLLOW_STAR_in_arrayAccess1372 = new BitSet(new long[]{0x0800000000000000L});
	public static final BitSet FOLLOW_59_in_arrayAccess1374 = new BitSet(new long[]{0x0600800000000000L});
	public static final BitSet FOLLOW_47_in_arrayAccess1385 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_methodCall_in_arrayAccess1389 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_pathSegment_in_arrayAccess1412 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_58_in_arrayAccess1433 = new BitSet(new long[]{0x0000000080080000L});
	public static final BitSet FOLLOW_INTEGER_in_arrayAccess1438 = new BitSet(new long[]{0x0800000000000000L});
	public static final BitSet FOLLOW_UINT_in_arrayAccess1444 = new BitSet(new long[]{0x0800000000000000L});
	public static final BitSet FOLLOW_59_in_arrayAccess1447 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_58_in_arrayAccess1465 = new BitSet(new long[]{0x0000000080080000L});
	public static final BitSet FOLLOW_INTEGER_in_arrayAccess1470 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_UINT_in_arrayAccess1476 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_arrayAccess1479 = new BitSet(new long[]{0x0000000080080000L});
	public static final BitSet FOLLOW_INTEGER_in_arrayAccess1484 = new BitSet(new long[]{0x0800000000000000L});
	public static final BitSet FOLLOW_UINT_in_arrayAccess1490 = new BitSet(new long[]{0x0800000000000000L});
	public static final BitSet FOLLOW_59_in_arrayAccess1493 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_functionCall_in_valueExpression1525 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_functionReference_in_valueExpression1530 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_inlineFunction_in_valueExpression1543 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_parenthesesExpression_in_valueExpression1557 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_literal_in_valueExpression1563 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_VAR_in_valueExpression1569 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_valueExpression1589 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_valueExpression1591 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_ID_in_valueExpression1597 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_arrayCreation_in_valueExpression1617 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_objectCreation_in_valueExpression1623 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_operator_in_operatorExpression1635 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_40_in_parenthesesExpression1654 = new BitSet(new long[]{0xF4004994C008A080L,0x0000000000000026L});
	public static final BitSet FOLLOW_expression_in_parenthesesExpression1656 = new BitSet(new long[]{0x0000020000000000L});
	public static final BitSet FOLLOW_41_in_parenthesesExpression1658 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_methodCall1688 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_methodCall1690 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_ID_in_methodCall1696 = new BitSet(new long[]{0x0000010000000000L});
	public static final BitSet FOLLOW_40_in_methodCall1698 = new BitSet(new long[]{0xF4004B94C008A080L,0x0000000000000026L});
	public static final BitSet FOLLOW_expression_in_methodCall1707 = new BitSet(new long[]{0x0000120000000000L});
	public static final BitSet FOLLOW_44_in_methodCall1716 = new BitSet(new long[]{0xF4004994C008A080L,0x0000000000000026L});
	public static final BitSet FOLLOW_expression_in_methodCall1721 = new BitSet(new long[]{0x0000120000000000L});
	public static final BitSet FOLLOW_41_in_methodCall1733 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_methodCall_in_functionCall1748 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_39_in_functionReference1759 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_ID_in_functionReference1771 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_functionReference1773 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_ID_in_functionReference1779 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_fieldAssignment1815 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_fieldAssignment1817 = new BitSet(new long[]{0xF4004994C008A080L,0x0000000000000026L});
	public static final BitSet FOLLOW_expression_in_fieldAssignment1819 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_VAR_in_fieldAssignment1845 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_fieldAssignment1847 = new BitSet(new long[]{0x0000000020000000L});
	public static final BitSet FOLLOW_STAR_in_fieldAssignment1849 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_generalPathExpression_in_fieldAssignment1869 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_valueExpression_in_fieldAssignment1881 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_fieldAssignment1883 = new BitSet(new long[]{0xF4004994C008A080L,0x0000000000000026L});
	public static final BitSet FOLLOW_expression_in_fieldAssignment1887 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_66_in_objectCreation1925 = new BitSet(new long[]{0xB4000184C008A080L,0x0000000000000014L});
	public static final BitSet FOLLOW_fieldAssignment_in_objectCreation1928 = new BitSet(new long[]{0x0000100000000000L,0x0000000000000010L});
	public static final BitSet FOLLOW_44_in_objectCreation1931 = new BitSet(new long[]{0xB4000184C008A080L,0x0000000000000004L});
	public static final BitSet FOLLOW_fieldAssignment_in_objectCreation1933 = new BitSet(new long[]{0x0000100000000000L,0x0000000000000010L});
	public static final BitSet FOLLOW_44_in_objectCreation1937 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
	public static final BitSet FOLLOW_68_in_objectCreation1942 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_63_in_literal1980 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_60_in_literal1996 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DECIMAL_in_literal2012 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STRING_in_literal2028 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_UINT_in_literal2046 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INTEGER_in_literal2052 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_61_in_literal2068 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_58_in_arrayCreation2092 = new BitSet(new long[]{0xFC004994C008A080L,0x0000000000000026L});
	public static final BitSet FOLLOW_expression_in_arrayCreation2097 = new BitSet(new long[]{0x0800100000000000L});
	public static final BitSet FOLLOW_44_in_arrayCreation2100 = new BitSet(new long[]{0xF4004994C008A080L,0x0000000000000026L});
	public static final BitSet FOLLOW_expression_in_arrayCreation2104 = new BitSet(new long[]{0x0800100000000000L});
	public static final BitSet FOLLOW_44_in_arrayCreation2108 = new BitSet(new long[]{0x0800000000000000L});
	public static final BitSet FOLLOW_59_in_arrayCreation2113 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_readOperator_in_operator2139 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_writeOperator_in_operator2147 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_genericOperator_in_operator2155 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_VAR_in_adhocSource2169 = new BitSet(new long[]{0x0010000000000000L});
	public static final BitSet FOLLOW_52_in_adhocSource2171 = new BitSet(new long[]{0x0400000000000000L});
	public static final BitSet FOLLOW_arrayCreation_in_adhocSource2175 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_VAR_in_readOperator2203 = new BitSet(new long[]{0x0010000000000000L});
	public static final BitSet FOLLOW_52_in_readOperator2205 = new BitSet(new long[]{0x4000000000000000L});
	public static final BitSet FOLLOW_62_in_readOperator2213 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_ID_in_readOperator2219 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_readOperator2221 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_ID_in_readOperator2226 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_ID_in_readOperator2235 = new BitSet(new long[]{0xB4004994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_ternaryExpression_in_readOperator2239 = new BitSet(new long[]{0x0000000000008002L});
	public static final BitSet FOLLOW_confOption_in_readOperator2244 = new BitSet(new long[]{0x0000000000008002L});
	public static final BitSet FOLLOW_65_in_writeOperator2268 = new BitSet(new long[]{0x0000000400008000L});
	public static final BitSet FOLLOW_ID_in_writeOperator2279 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_writeOperator2281 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_ID_in_writeOperator2286 = new BitSet(new long[]{0x0000000400000000L});
	public static final BitSet FOLLOW_VAR_in_writeOperator2292 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_ID_in_writeOperator2300 = new BitSet(new long[]{0xB4004994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_ternaryExpression_in_writeOperator2304 = new BitSet(new long[]{0x0000000000008002L});
	public static final BitSet FOLLOW_confOption_in_writeOperator2308 = new BitSet(new long[]{0x0000000000008002L});
	public static final BitSet FOLLOW_VAR_in_genericOperator2342 = new BitSet(new long[]{0x0010100000000000L});
	public static final BitSet FOLLOW_44_in_genericOperator2345 = new BitSet(new long[]{0x0000000400000000L});
	public static final BitSet FOLLOW_VAR_in_genericOperator2349 = new BitSet(new long[]{0x0010100000000000L});
	public static final BitSet FOLLOW_52_in_genericOperator2353 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_ID_in_genericOperator2361 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_genericOperator2363 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_ID_in_genericOperator2369 = new BitSet(new long[]{0x0000000400008002L});
	public static final BitSet FOLLOW_input_in_genericOperator2385 = new BitSet(new long[]{0x0000100000008002L});
	public static final BitSet FOLLOW_44_in_genericOperator2394 = new BitSet(new long[]{0x0000000400000000L});
	public static final BitSet FOLLOW_input_in_genericOperator2396 = new BitSet(new long[]{0x0000100000008002L});
	public static final BitSet FOLLOW_confOption_in_genericOperator2406 = new BitSet(new long[]{0x0000000000008002L});
	public static final BitSet FOLLOW_ID_in_confOption2433 = new BitSet(new long[]{0xB4004994C008A080L,0x0000000000000024L});
	public static final BitSet FOLLOW_ternaryExpression_in_confOption2443 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_VAR_in_input2465 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_IN_in_input2467 = new BitSet(new long[]{0x0000000400000000L});
	public static final BitSet FOLLOW_VAR_in_input2473 = new BitSet(new long[]{0xB4004994C008A082L,0x0000000000000024L});
	public static final BitSet FOLLOW_ternaryExpression_in_input2490 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_synpred1_Meteor222 = new BitSet(new long[]{0x0010000000000000L});
	public static final BitSet FOLLOW_52_in_synpred1_Meteor224 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_FN_in_synpred1_Meteor226 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_synpred2_Meteor238 = new BitSet(new long[]{0x0010000000000000L});
	public static final BitSet FOLLOW_52_in_synpred2_Meteor240 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_JAVAUDF_in_synpred2_Meteor242 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_synpred3_Meteor441 = new BitSet(new long[]{0x0000000400008000L});
	public static final BitSet FOLLOW_set_in_synpred3_Meteor443 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_orExpression_in_synpred4_Meteor469 = new BitSet(new long[]{0x0100000000000000L});
	public static final BitSet FOLLOW_56_in_synpred4_Meteor471 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_orExpression_in_synpred5_Meteor511 = new BitSet(new long[]{0x0000000000010000L});
	public static final BitSet FOLLOW_IF_in_synpred5_Meteor513 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_40_in_synpred6_Meteor982 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_ID_in_synpred6_Meteor984 = new BitSet(new long[]{0x0000020000000000L});
	public static final BitSet FOLLOW_41_in_synpred6_Meteor986 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_pathExpression_in_synpred7_Meteor1049 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_57_in_synpred8_Meteor1105 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_ID_in_synpred8_Meteor1107 = new BitSet(new long[]{0x0000010000000000L});
	public static final BitSet FOLLOW_40_in_synpred8_Meteor1109 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_pathSegment_in_synpred9_Meteor1128 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_47_in_synpred10_Meteor1176 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_ID_in_synpred10_Meteor1178 = new BitSet(new long[]{0x0000010000000000L});
	public static final BitSet FOLLOW_40_in_synpred10_Meteor1180 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_pathSegment_in_synpred11_Meteor1199 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_pathSegment_in_synpred12_Meteor1241 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_57_in_synpred13_Meteor1295 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_47_in_synpred14_Meteor1326 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_58_in_synpred15_Meteor1355 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_47_in_synpred16_Meteor1378 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_methodCall_in_synpred16_Meteor1380 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_synpred17_Meteor1519 = new BitSet(new long[]{0x0000010000000000L});
	public static final BitSet FOLLOW_40_in_synpred17_Meteor1521 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_FN_in_synpred18_Meteor1537 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_synpred19_Meteor1581 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_synpred19_Meteor1583 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_synpred20_Meteor1763 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_synpred20_Meteor1765 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_synpred21_Meteor1809 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_synpred21_Meteor1811 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_VAR_in_synpred22_Meteor1837 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_synpred22_Meteor1839 = new BitSet(new long[]{0x0000000020000000L});
	public static final BitSet FOLLOW_STAR_in_synpred22_Meteor1841 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_VAR_in_synpred23_Meteor1860 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_VAR_in_synpred24_Meteor2381 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_44_in_synpred25_Meteor2390 = new BitSet(new long[]{0x0000000000000002L});
}
