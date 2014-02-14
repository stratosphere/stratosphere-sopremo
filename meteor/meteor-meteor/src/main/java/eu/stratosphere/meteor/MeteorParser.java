// $ANTLR 3.4 /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g 2014-02-14 14:45:17
 
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


@SuppressWarnings({"all", "warnings", "unchecked"})
public class MeteorParser extends MeteorParserBase {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "AND", "APOSTROPHE", "COMMENT", "DECIMAL", "DIGIT", "ELSE", "ESC_SEQ", "EXPONENT", "EXPRESSION", "FN", "HEX_DIGIT", "ID", "IF", "IN", "INTEGER", "JAVAUDF", "LOWER_LETTER", "NOT", "OCTAL_ESC", "OPERATOR", "OR", "QUOTATION", "SIGN", "SLASH", "STAR", "STRING", "UINT", "UNICODE_ESC", "UPPER_LETTER", "VAR", "WS", "'!'", "'!='", "'&&'", "'&'", "'('", "')'", "'+'", "'++'", "','", "'-'", "'--'", "'.'", "':'", "';'", "'<'", "'<='", "'='", "'=='", "'>'", "'>='", "'?'", "'?.'", "'['", "']'", "'false'", "'null'", "'read'", "'true'", "'using'", "'write'", "'{'", "'||'", "'}'", "'~'"
    };

    public static final int EOF=-1;
    public static final int T__35=35;
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
    public static final int INTEGER=18;
    public static final int JAVAUDF=19;
    public static final int LOWER_LETTER=20;
    public static final int NOT=21;
    public static final int OCTAL_ESC=22;
    public static final int OPERATOR=23;
    public static final int OR=24;
    public static final int QUOTATION=25;
    public static final int SIGN=26;
    public static final int SLASH=27;
    public static final int STAR=28;
    public static final int STRING=29;
    public static final int UINT=30;
    public static final int UNICODE_ESC=31;
    public static final int UPPER_LETTER=32;
    public static final int VAR=33;
    public static final int WS=34;

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
    public String[] getTokenNames() { return MeteorParser.tokenNames; }
    public String getGrammarFileName() { return "/home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g"; }


      private Stack<String> paraphrase = new Stack<String>();

      private boolean setInnerOutput(Token VAR, Operator<?> op) {
    	  JsonStreamExpression output = new JsonStreamExpression(op.getOutput(((objectCreation_scope)objectCreation_stack.peek()).mappings.size()));
    	  ((objectCreation_scope)objectCreation_stack.peek()).mappings.add(new ObjectCreation.TagMapping(output, new JsonStreamExpression(op)));
    	  getVariableRegistry().getRegistry(1).put(VAR.getText(), output);
    	  return true;
    	}
      
      protected EvaluationExpression getInputSelection(Token inputVar) throws RecognitionException {
          return getVariableSafely(inputVar).toInputSelection(((operator_scope)operator_stack.peek()).result);
      }

      public void parseSinks() throws RecognitionException {
        script();
      }


    public static class script_return extends ParserRuleReturnScope {
        EvaluationExpression tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "script"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:60:1: script : ( statement ';' )+ ->;
    public final MeteorParser.script_return script() throws RecognitionException {
        MeteorParser.script_return retval = new MeteorParser.script_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token char_literal2=null;
        MeteorParser.statement_return statement1 =null;


        EvaluationExpression char_literal2_tree=null;
        RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:61:2: ( ( statement ';' )+ ->)
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:61:5: ( statement ';' )+
            {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:61:5: ( statement ';' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==ID||LA1_0==VAR||LA1_0==61||(LA1_0 >= 63 && LA1_0 <= 64)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:61:6: statement ';'
            	    {
            	    pushFollow(FOLLOW_statement_in_script121);
            	    statement1=statement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_statement.add(statement1.getTree());

            	    char_literal2=(Token)match(input,48,FOLLOW_48_in_script123); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_48.add(char_literal2);


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 61:22: ->
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "statement"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:63:1: statement : ( operator | packageImport | adhocSource | definition |m= functionCall ) ->;
    public final MeteorParser.statement_return statement() throws RecognitionException {
        MeteorParser.statement_return retval = new MeteorParser.statement_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.functionCall_return m =null;

        MeteorParser.operator_return operator3 =null;

        MeteorParser.packageImport_return packageImport4 =null;

        MeteorParser.adhocSource_return adhocSource5 =null;

        MeteorParser.definition_return definition6 =null;


        RewriteRuleSubtreeStream stream_functionCall=new RewriteRuleSubtreeStream(adaptor,"rule functionCall");
        RewriteRuleSubtreeStream stream_definition=new RewriteRuleSubtreeStream(adaptor,"rule definition");
        RewriteRuleSubtreeStream stream_adhocSource=new RewriteRuleSubtreeStream(adaptor,"rule adhocSource");
        RewriteRuleSubtreeStream stream_operator=new RewriteRuleSubtreeStream(adaptor,"rule operator");
        RewriteRuleSubtreeStream stream_packageImport=new RewriteRuleSubtreeStream(adaptor,"rule packageImport");
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:64:2: ( ( operator | packageImport | adhocSource | definition |m= functionCall ) ->)
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:64:4: ( operator | packageImport | adhocSource | definition |m= functionCall )
            {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:64:4: ( operator | packageImport | adhocSource | definition |m= functionCall )
            int alt2=5;
            switch ( input.LA(1) ) {
            case VAR:
                {
                int LA2_1 = input.LA(2);

                if ( (LA2_1==51) ) {
                    int LA2_5 = input.LA(3);

                    if ( (LA2_5==ID||LA2_5==61) ) {
                        alt2=1;
                    }
                    else if ( (LA2_5==57) ) {
                        alt2=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 2, 5, input);

                        throw nvae;

                    }
                }
                else if ( (LA2_1==43) ) {
                    alt2=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 1, input);

                    throw nvae;

                }
                }
                break;
            case 61:
            case 64:
                {
                alt2=1;
                }
                break;
            case ID:
                {
                switch ( input.LA(2) ) {
                case 47:
                    {
                    int LA2_6 = input.LA(3);

                    if ( (LA2_6==ID) ) {
                        int LA2_10 = input.LA(4);

                        if ( (LA2_10==39) ) {
                            alt2=5;
                        }
                        else if ( (LA2_10==ID||LA2_10==VAR||LA2_10==48) ) {
                            alt2=1;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 2, 10, input);

                            throw nvae;

                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 2, 6, input);

                        throw nvae;

                    }
                    }
                    break;
                case 51:
                    {
                    alt2=4;
                    }
                    break;
                case 39:
                    {
                    alt2=5;
                    }
                    break;
                case ID:
                case VAR:
                case 48:
                    {
                    alt2=1;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 3, input);

                    throw nvae;

                }

                }
                break;
            case 63:
                {
                alt2=2;
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
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:64:5: operator
                    {
                    pushFollow(FOLLOW_operator_in_statement137);
                    operator3=operator();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_operator.add(operator3.getTree());

                    }
                    break;
                case 2 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:64:16: packageImport
                    {
                    pushFollow(FOLLOW_packageImport_in_statement141);
                    packageImport4=packageImport();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_packageImport.add(packageImport4.getTree());

                    }
                    break;
                case 3 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:64:32: adhocSource
                    {
                    pushFollow(FOLLOW_adhocSource_in_statement145);
                    adhocSource5=adhocSource();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_adhocSource.add(adhocSource5.getTree());

                    }
                    break;
                case 4 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:64:46: definition
                    {
                    pushFollow(FOLLOW_definition_in_statement149);
                    definition6=definition();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_definition.add(definition6.getTree());

                    }
                    break;
                case 5 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:66:4: m= functionCall
                    {
                    pushFollow(FOLLOW_functionCall_in_statement157);
                    m=functionCall();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_functionCall.add(m.getTree());

                    if ( state.backtracking==0 ) { (m!=null?((EvaluationExpression)m.tree):null).evaluate(MissingNode.getInstance()); }

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
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 66:69: ->
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "packageImport"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:68:1: packageImport : 'using' packageName= ID ( ',' additionalPackage= ID )* ->;
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
        RewriteRuleTokenStream stream_43=new RewriteRuleTokenStream(adaptor,"token 43");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_63=new RewriteRuleTokenStream(adaptor,"token 63");

        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:69:3: ( 'using' packageName= ID ( ',' additionalPackage= ID )* ->)
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:69:6: 'using' packageName= ID ( ',' additionalPackage= ID )*
            {
            string_literal7=(Token)match(input,63,FOLLOW_63_in_packageImport174); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_63.add(string_literal7);


            packageName=(Token)match(input,ID,FOLLOW_ID_in_packageImport178); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(packageName);


            if ( state.backtracking==0 ) { getPackageManager().importPackage((packageName!=null?packageName.getText():null)); }

            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:70:6: ( ',' additionalPackage= ID )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==43) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:70:7: ',' additionalPackage= ID
            	    {
            	    char_literal8=(Token)match(input,43,FOLLOW_43_in_packageImport189); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_43.add(char_literal8);


            	    additionalPackage=(Token)match(input,ID,FOLLOW_ID_in_packageImport193); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_ID.add(additionalPackage);


            	    if ( state.backtracking==0 ) { getPackageManager().importPackage((additionalPackage!=null?additionalPackage.getText():null)); }

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 70:98: ->
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "definition"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:72:1: definition : ( ( ID '=' FN )=> functionDefinition | ( ID '=' JAVAUDF )=> javaudf | constantDefinition );
    public final MeteorParser.definition_return definition() throws RecognitionException {
        MeteorParser.definition_return retval = new MeteorParser.definition_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.functionDefinition_return functionDefinition9 =null;

        MeteorParser.javaudf_return javaudf10 =null;

        MeteorParser.constantDefinition_return constantDefinition11 =null;



        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:73:3: ( ( ID '=' FN )=> functionDefinition | ( ID '=' JAVAUDF )=> javaudf | constantDefinition )
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
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 1, input);

                    throw nvae;

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
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:73:5: ( ID '=' FN )=> functionDefinition
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_functionDefinition_in_definition218);
                    functionDefinition9=functionDefinition();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, functionDefinition9.getTree());

                    }
                    break;
                case 2 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:74:5: ( ID '=' JAVAUDF )=> javaudf
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_javaudf_in_definition234);
                    javaudf10=javaudf();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, javaudf10.getTree());

                    }
                    break;
                case 3 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:75:5: constantDefinition
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_constantDefinition_in_definition240);
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "functionDefinition"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:77:1: functionDefinition : name= ID '=' func= inlineFunction ->;
    public final MeteorParser.functionDefinition_return functionDefinition() throws RecognitionException {
        MeteorParser.functionDefinition_return retval = new MeteorParser.functionDefinition_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token name=null;
        Token char_literal12=null;
        MeteorParser.inlineFunction_return func =null;


        EvaluationExpression name_tree=null;
        EvaluationExpression char_literal12_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_51=new RewriteRuleTokenStream(adaptor,"token 51");
        RewriteRuleSubtreeStream stream_inlineFunction=new RewriteRuleSubtreeStream(adaptor,"rule inlineFunction");
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:78:3: (name= ID '=' func= inlineFunction ->)
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:78:5: name= ID '=' func= inlineFunction
            {
            name=(Token)match(input,ID,FOLLOW_ID_in_functionDefinition252); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(name);


            char_literal12=(Token)match(input,51,FOLLOW_51_in_functionDefinition254); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_51.add(char_literal12);


            pushFollow(FOLLOW_inlineFunction_in_functionDefinition258);
            func=inlineFunction();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_inlineFunction.add(func.getTree());

            if ( state.backtracking==0 ) { addFunction((name!=null?name.getText():null), (func!=null?func.func:null)); }

            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 78:78: ->
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "constantDefinition"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:80:1: constantDefinition : name= ID '=' exp= ternaryExpression ->;
    public final MeteorParser.constantDefinition_return constantDefinition() throws RecognitionException {
        MeteorParser.constantDefinition_return retval = new MeteorParser.constantDefinition_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token name=null;
        Token char_literal13=null;
        MeteorParser.ternaryExpression_return exp =null;


        EvaluationExpression name_tree=null;
        EvaluationExpression char_literal13_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_51=new RewriteRuleTokenStream(adaptor,"token 51");
        RewriteRuleSubtreeStream stream_ternaryExpression=new RewriteRuleSubtreeStream(adaptor,"rule ternaryExpression");
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:81:3: (name= ID '=' exp= ternaryExpression ->)
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:81:5: name= ID '=' exp= ternaryExpression
            {
            name=(Token)match(input,ID,FOLLOW_ID_in_constantDefinition277); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(name);


            char_literal13=(Token)match(input,51,FOLLOW_51_in_constantDefinition279); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_51.add(char_literal13);


            pushFollow(FOLLOW_ternaryExpression_in_constantDefinition283);
            exp=ternaryExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_ternaryExpression.add(exp.getTree());

            if ( state.backtracking==0 ) { addConstant((name!=null?name.getText():null), (exp!=null?((EvaluationExpression)exp.tree):null)); }

            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 81:79: ->
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "inlineFunction"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:83:1: inlineFunction returns [ExpressionFunction func] : FN '(' (param= ID ( ',' param= ID )* )? ')' '{' def= expression '}' ->;
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
        MeteorParser.expression_return def =null;


        EvaluationExpression param_tree=null;
        EvaluationExpression FN14_tree=null;
        EvaluationExpression char_literal15_tree=null;
        EvaluationExpression char_literal16_tree=null;
        EvaluationExpression char_literal17_tree=null;
        EvaluationExpression char_literal18_tree=null;
        EvaluationExpression char_literal19_tree=null;
        RewriteRuleTokenStream stream_67=new RewriteRuleTokenStream(adaptor,"token 67");
        RewriteRuleTokenStream stream_43=new RewriteRuleTokenStream(adaptor,"token 43");
        RewriteRuleTokenStream stream_FN=new RewriteRuleTokenStream(adaptor,"token FN");
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
         List<Token> params = new ArrayList(); 
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:85:3: ( FN '(' (param= ID ( ',' param= ID )* )? ')' '{' def= expression '}' ->)
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:85:5: FN '(' (param= ID ( ',' param= ID )* )? ')' '{' def= expression '}'
            {
            FN14=(Token)match(input,FN,FOLLOW_FN_in_inlineFunction309); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_FN.add(FN14);


            char_literal15=(Token)match(input,39,FOLLOW_39_in_inlineFunction311); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_39.add(char_literal15);


            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:86:3: (param= ID ( ',' param= ID )* )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==ID) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:86:4: param= ID ( ',' param= ID )*
                    {
                    param=(Token)match(input,ID,FOLLOW_ID_in_inlineFunction320); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(param);


                    if ( state.backtracking==0 ) { params.add(param); }

                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:87:3: ( ',' param= ID )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==43) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:87:4: ',' param= ID
                    	    {
                    	    char_literal16=(Token)match(input,43,FOLLOW_43_in_inlineFunction327); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_43.add(char_literal16);


                    	    param=(Token)match(input,ID,FOLLOW_ID_in_inlineFunction331); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_ID.add(param);


                    	    if ( state.backtracking==0 ) { params.add(param); }

                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);


                    }
                    break;

            }


            char_literal17=(Token)match(input,40,FOLLOW_40_in_inlineFunction342); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_40.add(char_literal17);


            if ( state.backtracking==0 ) { 
                addConstantScope();
                for(int index = 0; index < params.size(); index++) 
                  this.getConstantRegistry().put(params.get(index).getText(), new InputSelection(index)); 
              }

            char_literal18=(Token)match(input,65,FOLLOW_65_in_inlineFunction352); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_65.add(char_literal18);


            pushFollow(FOLLOW_expression_in_inlineFunction356);
            def=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expression.add(def.getTree());

            char_literal19=(Token)match(input,67,FOLLOW_67_in_inlineFunction358); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_67.add(char_literal19);


            if ( state.backtracking==0 ) { 
                retval.func = new ExpressionFunction(params.size(), (def!=null?((EvaluationExpression)def.tree):null));
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
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 98:5: ->
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "javaudf"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:100:1: javaudf : name= ID '=' JAVAUDF '(' path= STRING ')' ->;
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
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_51=new RewriteRuleTokenStream(adaptor,"token 51");
        RewriteRuleTokenStream stream_JAVAUDF=new RewriteRuleTokenStream(adaptor,"token JAVAUDF");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:101:3: (name= ID '=' JAVAUDF '(' path= STRING ')' ->)
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:101:5: name= ID '=' JAVAUDF '(' path= STRING ')'
            {
            name=(Token)match(input,ID,FOLLOW_ID_in_javaudf378); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(name);


            char_literal20=(Token)match(input,51,FOLLOW_51_in_javaudf380); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_51.add(char_literal20);


            JAVAUDF21=(Token)match(input,JAVAUDF,FOLLOW_JAVAUDF_in_javaudf382); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_JAVAUDF.add(JAVAUDF21);


            char_literal22=(Token)match(input,39,FOLLOW_39_in_javaudf384); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_39.add(char_literal22);


            path=(Token)match(input,STRING,FOLLOW_STRING_in_javaudf388); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_STRING.add(path);


            char_literal23=(Token)match(input,40,FOLLOW_40_in_javaudf390); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_40.add(char_literal23);


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
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 102:53: ->
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
    protected Stack contextAwareExpression_stack = new Stack();


    public static class contextAwareExpression_return extends ParserRuleReturnScope {
        EvaluationExpression tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "contextAwareExpression"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:104:1: contextAwareExpression[EvaluationExpression contextExpression] : ternaryExpression ;
    public final MeteorParser.contextAwareExpression_return contextAwareExpression(EvaluationExpression contextExpression) throws RecognitionException {
        contextAwareExpression_stack.push(new contextAwareExpression_scope());
        MeteorParser.contextAwareExpression_return retval = new MeteorParser.contextAwareExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.ternaryExpression_return ternaryExpression24 =null;



         ((contextAwareExpression_scope)contextAwareExpression_stack.peek()).context = contextExpression; 
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:107:3: ( ternaryExpression )
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:107:5: ternaryExpression
            {
            root_0 = (EvaluationExpression)adaptor.nil();


            pushFollow(FOLLOW_ternaryExpression_in_contextAwareExpression418);
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "expression"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:109:1: expression : ( ( ID ( ID | VAR ) )=> operatorExpression | ternaryExpression );
    public final MeteorParser.expression_return expression() throws RecognitionException {
        MeteorParser.expression_return retval = new MeteorParser.expression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.operatorExpression_return operatorExpression25 =null;

        MeteorParser.ternaryExpression_return ternaryExpression26 =null;



        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:110:3: ( ( ID ( ID | VAR ) )=> operatorExpression | ternaryExpression )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==VAR) ) {
                int LA7_1 = input.LA(2);

                if ( (LA7_1==51) && (synpred3_Meteor())) {
                    alt7=1;
                }
                else if ( (LA7_1==43) ) {
                    int LA7_7 = input.LA(3);

                    if ( (LA7_7==VAR) ) {
                        int LA7_8 = input.LA(4);

                        if ( (synpred3_Meteor()) ) {
                            alt7=1;
                        }
                        else if ( (true) ) {
                            alt7=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 7, 8, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA7_7==DECIMAL||LA7_7==FN||LA7_7==ID||LA7_7==INTEGER||(LA7_7 >= STRING && LA7_7 <= UINT)||LA7_7==35||(LA7_7 >= 38 && LA7_7 <= 39)||LA7_7==42||LA7_7==45||(LA7_7 >= 57 && LA7_7 <= 62)||(LA7_7 >= 64 && LA7_7 <= 65)||(LA7_7 >= 67 && LA7_7 <= 68)) ) {
                        alt7=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 7, 7, input);

                        throw nvae;

                    }
                }
                else if ( (LA7_1==AND||(LA7_1 >= ID && LA7_1 <= IN)||LA7_1==NOT||LA7_1==OR||(LA7_1 >= SLASH && LA7_1 <= STAR)||(LA7_1 >= 36 && LA7_1 <= 37)||(LA7_1 >= 40 && LA7_1 <= 41)||LA7_1==44||LA7_1==46||(LA7_1 >= 49 && LA7_1 <= 50)||(LA7_1 >= 52 && LA7_1 <= 58)||(LA7_1 >= 66 && LA7_1 <= 67)) ) {
                    alt7=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA7_0==61) && (synpred3_Meteor())) {
                alt7=1;
            }
            else if ( (LA7_0==64) && (synpred3_Meteor())) {
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
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 4, input);

                    throw nvae;

                }
            }
            else if ( (LA7_0==DECIMAL||LA7_0==FN||LA7_0==INTEGER||(LA7_0 >= STRING && LA7_0 <= UINT)||LA7_0==35||(LA7_0 >= 38 && LA7_0 <= 39)||LA7_0==42||LA7_0==45||LA7_0==57||(LA7_0 >= 59 && LA7_0 <= 60)||LA7_0==62||LA7_0==65||LA7_0==68) ) {
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
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:110:5: ( ID ( ID | VAR ) )=> operatorExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_operatorExpression_in_expression441);
                    operatorExpression25=operatorExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, operatorExpression25.getTree());

                    }
                    break;
                case 2 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:111:5: ternaryExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_ternaryExpression_in_expression447);
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "ternaryExpression"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:113:1: ternaryExpression : ( ( orExpression '?' )=>ifClause= orExpression '?' (ifExpr= orExpression )? ':' elseExpr= orExpression -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause) | ( orExpression IF )=>ifExpr2= orExpression IF ifClause2= orExpression -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause2 $ifExpr2) | orExpression );
    public final MeteorParser.ternaryExpression_return ternaryExpression() throws RecognitionException {
        MeteorParser.ternaryExpression_return retval = new MeteorParser.ternaryExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token char_literal27=null;
        Token char_literal28=null;
        Token IF29=null;
        MeteorParser.orExpression_return ifClause =null;

        MeteorParser.orExpression_return ifExpr =null;

        MeteorParser.orExpression_return elseExpr =null;

        MeteorParser.orExpression_return ifExpr2 =null;

        MeteorParser.orExpression_return ifClause2 =null;

        MeteorParser.orExpression_return orExpression30 =null;


        EvaluationExpression char_literal27_tree=null;
        EvaluationExpression char_literal28_tree=null;
        EvaluationExpression IF29_tree=null;
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleTokenStream stream_IF=new RewriteRuleTokenStream(adaptor,"token IF");
        RewriteRuleSubtreeStream stream_orExpression=new RewriteRuleSubtreeStream(adaptor,"rule orExpression");
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:114:2: ( ( orExpression '?' )=>ifClause= orExpression '?' (ifExpr= orExpression )? ':' elseExpr= orExpression -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause) | ( orExpression IF )=>ifExpr2= orExpression IF ifClause2= orExpression -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause2 $ifExpr2) | orExpression )
            int alt9=3;
            switch ( input.LA(1) ) {
            case 42:
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
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 1, input);

                    throw nvae;

                }
                }
                break;
            case 45:
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
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 2, input);

                    throw nvae;

                }
                }
                break;
            case 35:
            case 68:
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
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 3, input);

                    throw nvae;

                }
                }
                break;
            case 39:
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
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 4, input);

                    throw nvae;

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
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 5, input);

                    throw nvae;

                }
                }
                break;
            case 38:
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
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 6, input);

                    throw nvae;

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
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 7, input);

                    throw nvae;

                }
                }
                break;
            case 62:
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
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 8, input);

                    throw nvae;

                }
                }
                break;
            case 59:
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
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 9, input);

                    throw nvae;

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
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 10, input);

                    throw nvae;

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
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 11, input);

                    throw nvae;

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
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 12, input);

                    throw nvae;

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
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 13, input);

                    throw nvae;

                }
                }
                break;
            case 60:
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
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 14, input);

                    throw nvae;

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
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 15, input);

                    throw nvae;

                }
                }
                break;
            case 57:
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
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 16, input);

                    throw nvae;

                }
                }
                break;
            case 65:
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
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 17, input);

                    throw nvae;

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
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:114:4: ( orExpression '?' )=>ifClause= orExpression '?' (ifExpr= orExpression )? ':' elseExpr= orExpression
                    {
                    pushFollow(FOLLOW_orExpression_in_ternaryExpression465);
                    ifClause=orExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_orExpression.add(ifClause.getTree());

                    char_literal27=(Token)match(input,55,FOLLOW_55_in_ternaryExpression467); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_55.add(char_literal27);


                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:114:57: (ifExpr= orExpression )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==DECIMAL||LA8_0==FN||LA8_0==ID||LA8_0==INTEGER||(LA8_0 >= STRING && LA8_0 <= UINT)||LA8_0==VAR||LA8_0==35||(LA8_0 >= 38 && LA8_0 <= 39)||LA8_0==42||LA8_0==45||LA8_0==57||(LA8_0 >= 59 && LA8_0 <= 60)||LA8_0==62||LA8_0==65||LA8_0==68) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:114:57: ifExpr= orExpression
                            {
                            pushFollow(FOLLOW_orExpression_in_ternaryExpression471);
                            ifExpr=orExpression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_orExpression.add(ifExpr.getTree());

                            }
                            break;

                    }


                    char_literal28=(Token)match(input,47,FOLLOW_47_in_ternaryExpression474); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_47.add(char_literal28);


                    pushFollow(FOLLOW_orExpression_in_ternaryExpression478);
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
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_ifClause=new RewriteRuleSubtreeStream(adaptor,"rule ifClause",ifClause!=null?ifClause.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 115:2: -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause)
                    {
                        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:115:5: ^( EXPRESSION[\"TernaryExpression\"] $ifClause)
                        {
                        EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                        root_1 = (EvaluationExpression)adaptor.becomeRoot(
                        (EvaluationExpression)adaptor.create(EXPRESSION, "TernaryExpression")
                        , root_1);

                        adaptor.addChild(root_1, stream_ifClause.nextTree());

                        adaptor.addChild(root_1,  ifExpr == null ? (ifClause!=null?((EvaluationExpression)ifClause.tree):null) : (ifExpr!=null?((EvaluationExpression)ifExpr.tree):null) );

                        adaptor.addChild(root_1,  (elseExpr!=null?((EvaluationExpression)elseExpr.tree):null) );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 2 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:116:4: ( orExpression IF )=>ifExpr2= orExpression IF ifClause2= orExpression
                    {
                    pushFollow(FOLLOW_orExpression_in_ternaryExpression507);
                    ifExpr2=orExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_orExpression.add(ifExpr2.getTree());

                    IF29=(Token)match(input,IF,FOLLOW_IF_in_ternaryExpression509); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_IF.add(IF29);


                    pushFollow(FOLLOW_orExpression_in_ternaryExpression513);
                    ifClause2=orExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_orExpression.add(ifClause2.getTree());

                    // AST REWRITE
                    // elements: ifClause2, ifExpr2
                    // token labels: 
                    // rule labels: retval, ifExpr2, ifClause2
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_ifExpr2=new RewriteRuleSubtreeStream(adaptor,"rule ifExpr2",ifExpr2!=null?ifExpr2.tree:null);
                    RewriteRuleSubtreeStream stream_ifClause2=new RewriteRuleSubtreeStream(adaptor,"rule ifClause2",ifClause2!=null?ifClause2.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 117:3: -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause2 $ifExpr2)
                    {
                        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:117:6: ^( EXPRESSION[\"TernaryExpression\"] $ifClause2 $ifExpr2)
                        {
                        EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                        root_1 = (EvaluationExpression)adaptor.becomeRoot(
                        (EvaluationExpression)adaptor.create(EXPRESSION, "TernaryExpression")
                        , root_1);

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
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:118:5: orExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_orExpression_in_ternaryExpression536);
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "orExpression"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:120:1: orExpression :exprs+= andExpression ( ( OR | '||' ) exprs+= andExpression )* -> { $exprs.size() == 1 }? ->;
    public final MeteorParser.orExpression_return orExpression() throws RecognitionException {
        MeteorParser.orExpression_return retval = new MeteorParser.orExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token OR31=null;
        Token string_literal32=null;
        List list_exprs=null;
        RuleReturnScope exprs = null;
        EvaluationExpression OR31_tree=null;
        EvaluationExpression string_literal32_tree=null;
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_OR=new RewriteRuleTokenStream(adaptor,"token OR");
        RewriteRuleSubtreeStream stream_andExpression=new RewriteRuleSubtreeStream(adaptor,"rule andExpression");
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:121:3: (exprs+= andExpression ( ( OR | '||' ) exprs+= andExpression )* -> { $exprs.size() == 1 }? ->)
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:121:5: exprs+= andExpression ( ( OR | '||' ) exprs+= andExpression )*
            {
            pushFollow(FOLLOW_andExpression_in_orExpression549);
            exprs=andExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_andExpression.add(exprs.getTree());
            if (list_exprs==null) list_exprs=new ArrayList();
            list_exprs.add(exprs.getTree());


            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:121:26: ( ( OR | '||' ) exprs+= andExpression )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==OR||LA11_0==66) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:121:27: ( OR | '||' ) exprs+= andExpression
            	    {
            	    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:121:27: ( OR | '||' )
            	    int alt10=2;
            	    int LA10_0 = input.LA(1);

            	    if ( (LA10_0==OR) ) {
            	        alt10=1;
            	    }
            	    else if ( (LA10_0==66) ) {
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
            	            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:121:28: OR
            	            {
            	            OR31=(Token)match(input,OR,FOLLOW_OR_in_orExpression553); if (state.failed) return retval; 
            	            if ( state.backtracking==0 ) stream_OR.add(OR31);


            	            }
            	            break;
            	        case 2 :
            	            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:121:33: '||'
            	            {
            	            string_literal32=(Token)match(input,66,FOLLOW_66_in_orExpression557); if (state.failed) return retval; 
            	            if ( state.backtracking==0 ) stream_66.add(string_literal32);


            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_andExpression_in_orExpression562);
            	    exprs=andExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_andExpression.add(exprs.getTree());
            	    if (list_exprs==null) list_exprs=new ArrayList();
            	    list_exprs.add(exprs.getTree());


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);


            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 122:3: -> { $exprs.size() == 1 }?
            if ( list_exprs.size() == 1 ) {
                adaptor.addChild(root_0,  list_exprs.get(0) );

            }

            else // 123:3: ->
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "andExpression"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:125:1: andExpression :exprs+= elementExpression ( ( AND | '&&' ) exprs+= elementExpression )* -> { $exprs.size() == 1 }? ->;
    public final MeteorParser.andExpression_return andExpression() throws RecognitionException {
        MeteorParser.andExpression_return retval = new MeteorParser.andExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token AND33=null;
        Token string_literal34=null;
        List list_exprs=null;
        RuleReturnScope exprs = null;
        EvaluationExpression AND33_tree=null;
        EvaluationExpression string_literal34_tree=null;
        RewriteRuleTokenStream stream_AND=new RewriteRuleTokenStream(adaptor,"token AND");
        RewriteRuleTokenStream stream_37=new RewriteRuleTokenStream(adaptor,"token 37");
        RewriteRuleSubtreeStream stream_elementExpression=new RewriteRuleSubtreeStream(adaptor,"rule elementExpression");
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:126:3: (exprs+= elementExpression ( ( AND | '&&' ) exprs+= elementExpression )* -> { $exprs.size() == 1 }? ->)
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:126:5: exprs+= elementExpression ( ( AND | '&&' ) exprs+= elementExpression )*
            {
            pushFollow(FOLLOW_elementExpression_in_andExpression591);
            exprs=elementExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_elementExpression.add(exprs.getTree());
            if (list_exprs==null) list_exprs=new ArrayList();
            list_exprs.add(exprs.getTree());


            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:126:30: ( ( AND | '&&' ) exprs+= elementExpression )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==AND||LA13_0==37) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:126:31: ( AND | '&&' ) exprs+= elementExpression
            	    {
            	    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:126:31: ( AND | '&&' )
            	    int alt12=2;
            	    int LA12_0 = input.LA(1);

            	    if ( (LA12_0==AND) ) {
            	        alt12=1;
            	    }
            	    else if ( (LA12_0==37) ) {
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
            	            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:126:32: AND
            	            {
            	            AND33=(Token)match(input,AND,FOLLOW_AND_in_andExpression595); if (state.failed) return retval; 
            	            if ( state.backtracking==0 ) stream_AND.add(AND33);


            	            }
            	            break;
            	        case 2 :
            	            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:126:38: '&&'
            	            {
            	            string_literal34=(Token)match(input,37,FOLLOW_37_in_andExpression599); if (state.failed) return retval; 
            	            if ( state.backtracking==0 ) stream_37.add(string_literal34);


            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_elementExpression_in_andExpression604);
            	    exprs=elementExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_elementExpression.add(exprs.getTree());
            	    if (list_exprs==null) list_exprs=new ArrayList();
            	    list_exprs.add(exprs.getTree());


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);


            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 127:3: -> { $exprs.size() == 1 }?
            if ( list_exprs.size() == 1 ) {
                adaptor.addChild(root_0,  list_exprs.get(0) );

            }

            else // 128:3: ->
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "elementExpression"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:130:1: elementExpression : elem= comparisonExpression ( (not= NOT )? IN set= comparisonExpression )? -> { set == null }? $elem -> ^( EXPRESSION[\"ElementInSetExpression\"] $elem $set) ;
    public final MeteorParser.elementExpression_return elementExpression() throws RecognitionException {
        MeteorParser.elementExpression_return retval = new MeteorParser.elementExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token not=null;
        Token IN35=null;
        MeteorParser.comparisonExpression_return elem =null;

        MeteorParser.comparisonExpression_return set =null;


        EvaluationExpression not_tree=null;
        EvaluationExpression IN35_tree=null;
        RewriteRuleTokenStream stream_IN=new RewriteRuleTokenStream(adaptor,"token IN");
        RewriteRuleTokenStream stream_NOT=new RewriteRuleTokenStream(adaptor,"token NOT");
        RewriteRuleSubtreeStream stream_comparisonExpression=new RewriteRuleSubtreeStream(adaptor,"rule comparisonExpression");
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:131:2: (elem= comparisonExpression ( (not= NOT )? IN set= comparisonExpression )? -> { set == null }? $elem -> ^( EXPRESSION[\"ElementInSetExpression\"] $elem $set) )
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:131:4: elem= comparisonExpression ( (not= NOT )? IN set= comparisonExpression )?
            {
            pushFollow(FOLLOW_comparisonExpression_in_elementExpression633);
            elem=comparisonExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_comparisonExpression.add(elem.getTree());

            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:131:30: ( (not= NOT )? IN set= comparisonExpression )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==IN||LA15_0==NOT) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:131:31: (not= NOT )? IN set= comparisonExpression
                    {
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:131:34: (not= NOT )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==NOT) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:131:34: not= NOT
                            {
                            not=(Token)match(input,NOT,FOLLOW_NOT_in_elementExpression638); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_NOT.add(not);


                            }
                            break;

                    }


                    IN35=(Token)match(input,IN,FOLLOW_IN_in_elementExpression641); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_IN.add(IN35);


                    pushFollow(FOLLOW_comparisonExpression_in_elementExpression645);
                    set=comparisonExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_comparisonExpression.add(set.getTree());

                    }
                    break;

            }


            // AST REWRITE
            // elements: elem, elem, set
            // token labels: 
            // rule labels: elem, retval, set
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_elem=new RewriteRuleSubtreeStream(adaptor,"rule elem",elem!=null?elem.tree:null);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_set=new RewriteRuleSubtreeStream(adaptor,"rule set",set!=null?set.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 132:2: -> { set == null }? $elem
            if ( set == null ) {
                adaptor.addChild(root_0, stream_elem.nextTree());

            }

            else // 133:2: -> ^( EXPRESSION[\"ElementInSetExpression\"] $elem $set)
            {
                // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:133:5: ^( EXPRESSION[\"ElementInSetExpression\"] $elem $set)
                {
                EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                root_1 = (EvaluationExpression)adaptor.becomeRoot(
                (EvaluationExpression)adaptor.create(EXPRESSION, "ElementInSetExpression")
                , root_1);

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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "comparisonExpression"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:136:1: comparisonExpression : e1= arithmeticExpression ( (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' ) e2= arithmeticExpression )? -> { $s == null }? $e1 -> { $s.getText().equals(\"!=\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) -> { $s.getText().equals(\"==\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) -> ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) ;
    public final MeteorParser.comparisonExpression_return comparisonExpression() throws RecognitionException {
        MeteorParser.comparisonExpression_return retval = new MeteorParser.comparisonExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token s=null;
        MeteorParser.arithmeticExpression_return e1 =null;

        MeteorParser.arithmeticExpression_return e2 =null;


        EvaluationExpression s_tree=null;
        RewriteRuleTokenStream stream_49=new RewriteRuleTokenStream(adaptor,"token 49");
        RewriteRuleTokenStream stream_52=new RewriteRuleTokenStream(adaptor,"token 52");
        RewriteRuleTokenStream stream_36=new RewriteRuleTokenStream(adaptor,"token 36");
        RewriteRuleTokenStream stream_53=new RewriteRuleTokenStream(adaptor,"token 53");
        RewriteRuleTokenStream stream_54=new RewriteRuleTokenStream(adaptor,"token 54");
        RewriteRuleTokenStream stream_50=new RewriteRuleTokenStream(adaptor,"token 50");
        RewriteRuleSubtreeStream stream_arithmeticExpression=new RewriteRuleSubtreeStream(adaptor,"rule arithmeticExpression");
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:137:2: (e1= arithmeticExpression ( (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' ) e2= arithmeticExpression )? -> { $s == null }? $e1 -> { $s.getText().equals(\"!=\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) -> { $s.getText().equals(\"==\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) -> ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) )
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:137:4: e1= arithmeticExpression ( (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' ) e2= arithmeticExpression )?
            {
            pushFollow(FOLLOW_arithmeticExpression_in_comparisonExpression686);
            e1=arithmeticExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_arithmeticExpression.add(e1.getTree());

            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:137:28: ( (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' ) e2= arithmeticExpression )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==36||(LA17_0 >= 49 && LA17_0 <= 50)||(LA17_0 >= 52 && LA17_0 <= 54)) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:137:29: (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' ) e2= arithmeticExpression
                    {
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:137:29: (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' )
                    int alt16=6;
                    switch ( input.LA(1) ) {
                    case 50:
                        {
                        alt16=1;
                        }
                        break;
                    case 54:
                        {
                        alt16=2;
                        }
                        break;
                    case 49:
                        {
                        alt16=3;
                        }
                        break;
                    case 53:
                        {
                        alt16=4;
                        }
                        break;
                    case 52:
                        {
                        alt16=5;
                        }
                        break;
                    case 36:
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
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:137:30: s= '<='
                            {
                            s=(Token)match(input,50,FOLLOW_50_in_comparisonExpression692); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_50.add(s);


                            }
                            break;
                        case 2 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:137:39: s= '>='
                            {
                            s=(Token)match(input,54,FOLLOW_54_in_comparisonExpression698); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_54.add(s);


                            }
                            break;
                        case 3 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:137:48: s= '<'
                            {
                            s=(Token)match(input,49,FOLLOW_49_in_comparisonExpression704); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_49.add(s);


                            }
                            break;
                        case 4 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:137:56: s= '>'
                            {
                            s=(Token)match(input,53,FOLLOW_53_in_comparisonExpression710); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_53.add(s);


                            }
                            break;
                        case 5 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:137:64: s= '=='
                            {
                            s=(Token)match(input,52,FOLLOW_52_in_comparisonExpression716); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_52.add(s);


                            }
                            break;
                        case 6 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:137:73: s= '!='
                            {
                            s=(Token)match(input,36,FOLLOW_36_in_comparisonExpression722); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_36.add(s);


                            }
                            break;

                    }


                    pushFollow(FOLLOW_arithmeticExpression_in_comparisonExpression727);
                    e2=arithmeticExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_arithmeticExpression.add(e2.getTree());

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
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);
            RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 138:2: -> { $s == null }? $e1
            if ( s == null ) {
                adaptor.addChild(root_0, stream_e1.nextTree());

            }

            else // 139:3: -> { $s.getText().equals(\"!=\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
            if ( s.getText().equals("!=") ) {
                // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:139:38: ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
                {
                EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                root_1 = (EvaluationExpression)adaptor.becomeRoot(
                (EvaluationExpression)adaptor.create(EXPRESSION, "ComparativeExpression")
                , root_1);

                adaptor.addChild(root_1, stream_e1.nextTree());

                adaptor.addChild(root_1, ComparativeExpression.BinaryOperator.NOT_EQUAL);

                adaptor.addChild(root_1, stream_e2.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            else // 140:3: -> { $s.getText().equals(\"==\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
            if ( s.getText().equals("==") ) {
                // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:140:38: ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
                {
                EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                root_1 = (EvaluationExpression)adaptor.becomeRoot(
                (EvaluationExpression)adaptor.create(EXPRESSION, "ComparativeExpression")
                , root_1);

                adaptor.addChild(root_1, stream_e1.nextTree());

                adaptor.addChild(root_1, ComparativeExpression.BinaryOperator.EQUAL);

                adaptor.addChild(root_1, stream_e2.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            else // 141:2: -> ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
            {
                // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:141:6: ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
                {
                EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                root_1 = (EvaluationExpression)adaptor.becomeRoot(
                (EvaluationExpression)adaptor.create(EXPRESSION, "ComparativeExpression")
                , root_1);

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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "arithmeticExpression"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:143:1: arithmeticExpression : e1= multiplicationExpression ( (s= '+' |s= '-' ) e2= multiplicationExpression )? -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2) -> $e1;
    public final MeteorParser.arithmeticExpression_return arithmeticExpression() throws RecognitionException {
        MeteorParser.arithmeticExpression_return retval = new MeteorParser.arithmeticExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token s=null;
        MeteorParser.multiplicationExpression_return e1 =null;

        MeteorParser.multiplicationExpression_return e2 =null;


        EvaluationExpression s_tree=null;
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
        RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
        RewriteRuleSubtreeStream stream_multiplicationExpression=new RewriteRuleSubtreeStream(adaptor,"rule multiplicationExpression");
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:144:2: (e1= multiplicationExpression ( (s= '+' |s= '-' ) e2= multiplicationExpression )? -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2) -> $e1)
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:144:4: e1= multiplicationExpression ( (s= '+' |s= '-' ) e2= multiplicationExpression )?
            {
            pushFollow(FOLLOW_multiplicationExpression_in_arithmeticExpression807);
            e1=multiplicationExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_multiplicationExpression.add(e1.getTree());

            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:144:32: ( (s= '+' |s= '-' ) e2= multiplicationExpression )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==41||LA19_0==44) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:144:33: (s= '+' |s= '-' ) e2= multiplicationExpression
                    {
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:144:33: (s= '+' |s= '-' )
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0==41) ) {
                        alt18=1;
                    }
                    else if ( (LA18_0==44) ) {
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
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:144:34: s= '+'
                            {
                            s=(Token)match(input,41,FOLLOW_41_in_arithmeticExpression813); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_41.add(s);


                            }
                            break;
                        case 2 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:144:42: s= '-'
                            {
                            s=(Token)match(input,44,FOLLOW_44_in_arithmeticExpression819); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_44.add(s);


                            }
                            break;

                    }


                    pushFollow(FOLLOW_multiplicationExpression_in_arithmeticExpression824);
                    e2=multiplicationExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_multiplicationExpression.add(e2.getTree());

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
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);
            RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 145:2: -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2)
            if ( s != null ) {
                // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:145:21: ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2)
                {
                EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                root_1 = (EvaluationExpression)adaptor.becomeRoot(
                (EvaluationExpression)adaptor.create(EXPRESSION, "ArithmeticExpression")
                , root_1);

                adaptor.addChild(root_1, stream_e1.nextTree());

                adaptor.addChild(root_1,  s.getText().equals("+") ? ArithmeticExpression.ArithmeticOperator.ADDITION : ArithmeticExpression.ArithmeticOperator.SUBTRACTION);

                adaptor.addChild(root_1, stream_e2.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            else // 147:2: -> $e1
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "multiplicationExpression"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:149:1: multiplicationExpression : e1= preincrementExpression ( (s= '*' |s= SLASH ) e2= preincrementExpression )? -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2) -> $e1;
    public final MeteorParser.multiplicationExpression_return multiplicationExpression() throws RecognitionException {
        MeteorParser.multiplicationExpression_return retval = new MeteorParser.multiplicationExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token s=null;
        MeteorParser.preincrementExpression_return e1 =null;

        MeteorParser.preincrementExpression_return e2 =null;


        EvaluationExpression s_tree=null;
        RewriteRuleTokenStream stream_STAR=new RewriteRuleTokenStream(adaptor,"token STAR");
        RewriteRuleTokenStream stream_SLASH=new RewriteRuleTokenStream(adaptor,"token SLASH");
        RewriteRuleSubtreeStream stream_preincrementExpression=new RewriteRuleSubtreeStream(adaptor,"rule preincrementExpression");
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:150:2: (e1= preincrementExpression ( (s= '*' |s= SLASH ) e2= preincrementExpression )? -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2) -> $e1)
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:150:4: e1= preincrementExpression ( (s= '*' |s= SLASH ) e2= preincrementExpression )?
            {
            pushFollow(FOLLOW_preincrementExpression_in_multiplicationExpression867);
            e1=preincrementExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_preincrementExpression.add(e1.getTree());

            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:150:30: ( (s= '*' |s= SLASH ) e2= preincrementExpression )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( ((LA21_0 >= SLASH && LA21_0 <= STAR)) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:150:31: (s= '*' |s= SLASH ) e2= preincrementExpression
                    {
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:150:31: (s= '*' |s= SLASH )
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
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:150:32: s= '*'
                            {
                            s=(Token)match(input,STAR,FOLLOW_STAR_in_multiplicationExpression873); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_STAR.add(s);


                            }
                            break;
                        case 2 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:150:40: s= SLASH
                            {
                            s=(Token)match(input,SLASH,FOLLOW_SLASH_in_multiplicationExpression879); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_SLASH.add(s);


                            }
                            break;

                    }


                    pushFollow(FOLLOW_preincrementExpression_in_multiplicationExpression884);
                    e2=preincrementExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_preincrementExpression.add(e2.getTree());

                    }
                    break;

            }


            // AST REWRITE
            // elements: e2, e1, e1
            // token labels: 
            // rule labels: retval, e1, e2
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);
            RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 151:2: -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2)
            if ( s != null ) {
                // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:151:21: ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2)
                {
                EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                root_1 = (EvaluationExpression)adaptor.becomeRoot(
                (EvaluationExpression)adaptor.create(EXPRESSION, "ArithmeticExpression")
                , root_1);

                adaptor.addChild(root_1, stream_e1.nextTree());

                adaptor.addChild(root_1,  s.getText().equals("*") ? ArithmeticExpression.ArithmeticOperator.MULTIPLICATION : ArithmeticExpression.ArithmeticOperator.DIVISION);

                adaptor.addChild(root_1, stream_e2.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            else // 153:2: -> $e1
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "preincrementExpression"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:155:1: preincrementExpression : ( '++' preincrementExpression | '--' preincrementExpression | unaryExpression );
    public final MeteorParser.preincrementExpression_return preincrementExpression() throws RecognitionException {
        MeteorParser.preincrementExpression_return retval = new MeteorParser.preincrementExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token string_literal36=null;
        Token string_literal38=null;
        MeteorParser.preincrementExpression_return preincrementExpression37 =null;

        MeteorParser.preincrementExpression_return preincrementExpression39 =null;

        MeteorParser.unaryExpression_return unaryExpression40 =null;


        EvaluationExpression string_literal36_tree=null;
        EvaluationExpression string_literal38_tree=null;

        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:156:2: ( '++' preincrementExpression | '--' preincrementExpression | unaryExpression )
            int alt22=3;
            switch ( input.LA(1) ) {
            case 42:
                {
                alt22=1;
                }
                break;
            case 45:
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
            case 35:
            case 38:
            case 39:
            case 57:
            case 59:
            case 60:
            case 62:
            case 65:
            case 68:
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
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:156:4: '++' preincrementExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    string_literal36=(Token)match(input,42,FOLLOW_42_in_preincrementExpression925); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal36_tree = 
                    (EvaluationExpression)adaptor.create(string_literal36)
                    ;
                    adaptor.addChild(root_0, string_literal36_tree);
                    }

                    pushFollow(FOLLOW_preincrementExpression_in_preincrementExpression927);
                    preincrementExpression37=preincrementExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, preincrementExpression37.getTree());

                    }
                    break;
                case 2 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:157:4: '--' preincrementExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    string_literal38=(Token)match(input,45,FOLLOW_45_in_preincrementExpression932); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal38_tree = 
                    (EvaluationExpression)adaptor.create(string_literal38)
                    ;
                    adaptor.addChild(root_0, string_literal38_tree);
                    }

                    pushFollow(FOLLOW_preincrementExpression_in_preincrementExpression934);
                    preincrementExpression39=preincrementExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, preincrementExpression39.getTree());

                    }
                    break;
                case 3 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:158:4: unaryExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_unaryExpression_in_preincrementExpression939);
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "unaryExpression"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:160:1: unaryExpression : ( '!' | '~' )? castExpression ;
    public final MeteorParser.unaryExpression_return unaryExpression() throws RecognitionException {
        MeteorParser.unaryExpression_return retval = new MeteorParser.unaryExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token set41=null;
        MeteorParser.castExpression_return castExpression42 =null;


        EvaluationExpression set41_tree=null;

        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:161:2: ( ( '!' | '~' )? castExpression )
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:161:4: ( '!' | '~' )? castExpression
            {
            root_0 = (EvaluationExpression)adaptor.nil();


            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:161:4: ( '!' | '~' )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==35||LA23_0==68) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:
                    {
                    set41=(Token)input.LT(1);

                    if ( input.LA(1)==35||input.LA(1)==68 ) {
                        input.consume();
                        if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                        (EvaluationExpression)adaptor.create(set41)
                        );
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


            pushFollow(FOLLOW_castExpression_in_unaryExpression958);
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "castExpression"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:167:1: castExpression : ( ( '(' ID ')' )=> '(' type= ID ')' expr= generalPathExpression ->|expr= generalPathExpression ({...}? ID type= ID )? ->);
    public final MeteorParser.castExpression_return castExpression() throws RecognitionException {
        MeteorParser.castExpression_return retval = new MeteorParser.castExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token type=null;
        Token char_literal43=null;
        Token char_literal44=null;
        Token ID45=null;
        MeteorParser.generalPathExpression_return expr =null;


        EvaluationExpression type_tree=null;
        EvaluationExpression char_literal43_tree=null;
        EvaluationExpression char_literal44_tree=null;
        EvaluationExpression ID45_tree=null;
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleSubtreeStream stream_generalPathExpression=new RewriteRuleSubtreeStream(adaptor,"rule generalPathExpression");
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:168:2: ( ( '(' ID ')' )=> '(' type= ID ')' expr= generalPathExpression ->|expr= generalPathExpression ({...}? ID type= ID )? ->)
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==39) ) {
                int LA25_1 = input.LA(2);

                if ( (synpred6_Meteor()) ) {
                    alt25=1;
                }
                else if ( (true) ) {
                    alt25=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 25, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA25_0==DECIMAL||LA25_0==FN||LA25_0==ID||LA25_0==INTEGER||(LA25_0 >= STRING && LA25_0 <= UINT)||LA25_0==VAR||LA25_0==38||LA25_0==57||(LA25_0 >= 59 && LA25_0 <= 60)||LA25_0==62||LA25_0==65) ) {
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
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:168:4: ( '(' ID ')' )=> '(' type= ID ')' expr= generalPathExpression
                    {
                    char_literal43=(Token)match(input,39,FOLLOW_39_in_castExpression978); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_39.add(char_literal43);


                    type=(Token)match(input,ID,FOLLOW_ID_in_castExpression982); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(type);


                    char_literal44=(Token)match(input,40,FOLLOW_40_in_castExpression984); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_40.add(char_literal44);


                    pushFollow(FOLLOW_generalPathExpression_in_castExpression988);
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
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 169:3: ->
                    {
                        adaptor.addChild(root_0,  coerce((type!=null?type.getText():null), (expr!=null?((EvaluationExpression)expr.tree):null)) );

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 2 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:170:4: expr= generalPathExpression ({...}? ID type= ID )?
                    {
                    pushFollow(FOLLOW_generalPathExpression_in_castExpression1001);
                    expr=generalPathExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_generalPathExpression.add(expr.getTree());

                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:170:31: ({...}? ID type= ID )?
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
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:170:32: {...}? ID type= ID
                            {
                            if ( !((input.LT(1).getText().equals("as"))) ) {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                throw new FailedPredicateException(input, "castExpression", "input.LT(1).getText().equals(\"as\")");
                            }

                            ID45=(Token)match(input,ID,FOLLOW_ID_in_castExpression1006); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_ID.add(ID45);


                            type=(Token)match(input,ID,FOLLOW_ID_in_castExpression1010); if (state.failed) return retval; 
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
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 171:2: ->
                    {
                        adaptor.addChild(root_0,  type == null ? (expr!=null?((EvaluationExpression)expr.tree):null) : coerce((type!=null?type.getText():null), (expr!=null?((EvaluationExpression)expr.tree):null)));

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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "generalPathExpression"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:173:1: generalPathExpression : value= valueExpression ( ( pathExpression[EvaluationExpression.VALUE] )=>path= pathExpression[$value.tree] -> $path| -> $value) ;
    public final MeteorParser.generalPathExpression_return generalPathExpression() throws RecognitionException {
        MeteorParser.generalPathExpression_return retval = new MeteorParser.generalPathExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.valueExpression_return value =null;

        MeteorParser.pathExpression_return path =null;


        RewriteRuleSubtreeStream stream_valueExpression=new RewriteRuleSubtreeStream(adaptor,"rule valueExpression");
        RewriteRuleSubtreeStream stream_pathExpression=new RewriteRuleSubtreeStream(adaptor,"rule pathExpression");
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:174:2: (value= valueExpression ( ( pathExpression[EvaluationExpression.VALUE] )=>path= pathExpression[$value.tree] -> $path| -> $value) )
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:174:4: value= valueExpression ( ( pathExpression[EvaluationExpression.VALUE] )=>path= pathExpression[$value.tree] -> $path| -> $value)
            {
            pushFollow(FOLLOW_valueExpression_in_generalPathExpression1029);
            value=valueExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_valueExpression.add(value.getTree());

            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:175:4: ( ( pathExpression[EvaluationExpression.VALUE] )=>path= pathExpression[$value.tree] -> $path| -> $value)
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==56) && (synpred7_Meteor())) {
                alt26=1;
            }
            else if ( (LA26_0==46) && (synpred7_Meteor())) {
                alt26=1;
            }
            else if ( (LA26_0==57) && (synpred7_Meteor())) {
                alt26=1;
            }
            else if ( (LA26_0==EOF||LA26_0==AND||(LA26_0 >= ID && LA26_0 <= IN)||LA26_0==NOT||LA26_0==OR||(LA26_0 >= SLASH && LA26_0 <= STAR)||(LA26_0 >= 36 && LA26_0 <= 37)||(LA26_0 >= 40 && LA26_0 <= 41)||(LA26_0 >= 43 && LA26_0 <= 44)||(LA26_0 >= 47 && LA26_0 <= 50)||(LA26_0 >= 52 && LA26_0 <= 55)||LA26_0==58||(LA26_0 >= 66 && LA26_0 <= 67)) ) {
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
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:175:5: ( pathExpression[EvaluationExpression.VALUE] )=>path= pathExpression[$value.tree]
                    {
                    pushFollow(FOLLOW_pathExpression_in_generalPathExpression1044);
                    path=pathExpression((value!=null?((EvaluationExpression)value.tree):null));

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
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_path=new RewriteRuleSubtreeStream(adaptor,"rule path",path!=null?path.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 175:85: -> $path
                    {
                        adaptor.addChild(root_0, stream_path.nextTree());

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 2 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:176:7: 
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
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_value=new RewriteRuleSubtreeStream(adaptor,"rule value",value!=null?value.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 176:7: -> $value
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "contextAwarePathExpression"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:178:1: contextAwarePathExpression[EvaluationExpression context] : pathExpression[context] ;
    public final MeteorParser.contextAwarePathExpression_return contextAwarePathExpression(EvaluationExpression context) throws RecognitionException {
        MeteorParser.contextAwarePathExpression_return retval = new MeteorParser.contextAwarePathExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.pathExpression_return pathExpression46 =null;



        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:179:3: ( pathExpression[context] )
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:179:5: pathExpression[context]
            {
            root_0 = (EvaluationExpression)adaptor.nil();


            pushFollow(FOLLOW_pathExpression_in_contextAwarePathExpression1073);
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "pathExpression"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:181:1: pathExpression[EvaluationExpression inExp] : ( ( '?.' ID '(' )=> '?.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)] -> $path| -> ^( EXPRESSION[\"TernaryExpression\"] $call) ) | ( '.' ID '(' )=> '.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[$call.tree] -> $path| -> $call) |seg= pathSegment ( ( pathSegment )=>path= pathExpression[$seg.tree] -> $path| -> $seg) );
    public final MeteorParser.pathExpression_return pathExpression(EvaluationExpression inExp) throws RecognitionException {
        MeteorParser.pathExpression_return retval = new MeteorParser.pathExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token string_literal47=null;
        Token char_literal48=null;
        MeteorParser.methodCall_return call =null;

        MeteorParser.pathExpression_return path =null;

        MeteorParser.pathSegment_return seg =null;


        EvaluationExpression string_literal47_tree=null;
        EvaluationExpression char_literal48_tree=null;
        RewriteRuleTokenStream stream_56=new RewriteRuleTokenStream(adaptor,"token 56");
        RewriteRuleTokenStream stream_46=new RewriteRuleTokenStream(adaptor,"token 46");
        RewriteRuleSubtreeStream stream_pathSegment=new RewriteRuleSubtreeStream(adaptor,"rule pathSegment");
        RewriteRuleSubtreeStream stream_methodCall=new RewriteRuleSubtreeStream(adaptor,"rule methodCall");
        RewriteRuleSubtreeStream stream_pathExpression=new RewriteRuleSubtreeStream(adaptor,"rule pathExpression");
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:182:3: ( ( '?.' ID '(' )=> '?.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)] -> $path| -> ^( EXPRESSION[\"TernaryExpression\"] $call) ) | ( '.' ID '(' )=> '.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[$call.tree] -> $path| -> $call) |seg= pathSegment ( ( pathSegment )=>path= pathExpression[$seg.tree] -> $path| -> $seg) )
            int alt30=3;
            switch ( input.LA(1) ) {
            case 56:
                {
                int LA30_1 = input.LA(2);

                if ( (synpred8_Meteor()) ) {
                    alt30=1;
                }
                else if ( (true) ) {
                    alt30=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 30, 1, input);

                    throw nvae;

                }
                }
                break;
            case 46:
                {
                int LA30_2 = input.LA(2);

                if ( (synpred10_Meteor()) ) {
                    alt30=2;
                }
                else if ( (true) ) {
                    alt30=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 30, 2, input);

                    throw nvae;

                }
                }
                break;
            case 57:
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
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:183:5: ( '?.' ID '(' )=> '?.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)] -> $path| -> ^( EXPRESSION[\"TernaryExpression\"] $call) )
                    {
                    string_literal47=(Token)match(input,56,FOLLOW_56_in_pathExpression1101); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_56.add(string_literal47);


                    pushFollow(FOLLOW_methodCall_in_pathExpression1105);
                    call=methodCall(inExp);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_methodCall.add(call.getTree());

                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:184:7: ( ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)] -> $path| -> ^( EXPRESSION[\"TernaryExpression\"] $call) )
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( (LA27_0==56) && (synpred9_Meteor())) {
                        alt27=1;
                    }
                    else if ( (LA27_0==46) && (synpred9_Meteor())) {
                        alt27=1;
                    }
                    else if ( (LA27_0==57) && (synpred9_Meteor())) {
                        alt27=1;
                    }
                    else if ( (LA27_0==EOF||LA27_0==AND||(LA27_0 >= ID && LA27_0 <= IN)||LA27_0==NOT||LA27_0==OR||(LA27_0 >= SLASH && LA27_0 <= STAR)||(LA27_0 >= 36 && LA27_0 <= 37)||(LA27_0 >= 40 && LA27_0 <= 41)||(LA27_0 >= 43 && LA27_0 <= 44)||(LA27_0 >= 47 && LA27_0 <= 50)||(LA27_0 >= 52 && LA27_0 <= 55)||LA27_0==58||(LA27_0 >= 66 && LA27_0 <= 67)) ) {
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
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:184:8: ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)]
                            {
                            pushFollow(FOLLOW_pathExpression_in_pathExpression1122);
                            path=pathExpression(new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), (call!=null?((EvaluationExpression)call.tree):null)));

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
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                            RewriteRuleSubtreeStream stream_path=new RewriteRuleSubtreeStream(adaptor,"rule path",path!=null?path.tree:null);

                            root_0 = (EvaluationExpression)adaptor.nil();
                            // 184:146: -> $path
                            {
                                adaptor.addChild(root_0, stream_path.nextTree());

                            }


                            retval.tree = root_0;
                            }

                            }
                            break;
                        case 2 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:185:8: 
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
                            RewriteRuleSubtreeStream stream_call=new RewriteRuleSubtreeStream(adaptor,"rule call",call!=null?call.tree:null);
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (EvaluationExpression)adaptor.nil();
                            // 185:8: -> ^( EXPRESSION[\"TernaryExpression\"] $call)
                            {
                                // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:185:11: ^( EXPRESSION[\"TernaryExpression\"] $call)
                                {
                                EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                                root_1 = (EvaluationExpression)adaptor.becomeRoot(
                                (EvaluationExpression)adaptor.create(EXPRESSION, "TernaryExpression")
                                , root_1);

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
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:187:5: ( '.' ID '(' )=> '.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[$call.tree] -> $path| -> $call)
                    {
                    char_literal48=(Token)match(input,46,FOLLOW_46_in_pathExpression1172); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_46.add(char_literal48);


                    pushFollow(FOLLOW_methodCall_in_pathExpression1176);
                    call=methodCall(inExp);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_methodCall.add(call.getTree());

                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:188:7: ( ( pathSegment )=>path= pathExpression[$call.tree] -> $path| -> $call)
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==56) && (synpred11_Meteor())) {
                        alt28=1;
                    }
                    else if ( (LA28_0==46) && (synpred11_Meteor())) {
                        alt28=1;
                    }
                    else if ( (LA28_0==57) && (synpred11_Meteor())) {
                        alt28=1;
                    }
                    else if ( (LA28_0==EOF||LA28_0==AND||(LA28_0 >= ID && LA28_0 <= IN)||LA28_0==NOT||LA28_0==OR||(LA28_0 >= SLASH && LA28_0 <= STAR)||(LA28_0 >= 36 && LA28_0 <= 37)||(LA28_0 >= 40 && LA28_0 <= 41)||(LA28_0 >= 43 && LA28_0 <= 44)||(LA28_0 >= 47 && LA28_0 <= 50)||(LA28_0 >= 52 && LA28_0 <= 55)||LA28_0==58||(LA28_0 >= 66 && LA28_0 <= 67)) ) {
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
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:188:8: ( pathSegment )=>path= pathExpression[$call.tree]
                            {
                            pushFollow(FOLLOW_pathExpression_in_pathExpression1193);
                            path=pathExpression((call!=null?((EvaluationExpression)call.tree):null));

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
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                            RewriteRuleSubtreeStream stream_path=new RewriteRuleSubtreeStream(adaptor,"rule path",path!=null?path.tree:null);

                            root_0 = (EvaluationExpression)adaptor.nil();
                            // 188:55: -> $path
                            {
                                adaptor.addChild(root_0, stream_path.nextTree());

                            }


                            retval.tree = root_0;
                            }

                            }
                            break;
                        case 2 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:188:66: 
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
                            RewriteRuleSubtreeStream stream_call=new RewriteRuleSubtreeStream(adaptor,"rule call",call!=null?call.tree:null);
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (EvaluationExpression)adaptor.nil();
                            // 188:66: -> $call
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
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:190:5: seg= pathSegment ( ( pathSegment )=>path= pathExpression[$seg.tree] -> $path| -> $seg)
                    {
                    pushFollow(FOLLOW_pathSegment_in_pathExpression1219);
                    seg=pathSegment();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_pathSegment.add(seg.getTree());

                    if ( state.backtracking==0 ) { ((PathSegmentExpression) seg.getTree()).setInputExpression(inExp); }

                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:191:5: ( ( pathSegment )=>path= pathExpression[$seg.tree] -> $path| -> $seg)
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==56) && (synpred12_Meteor())) {
                        alt29=1;
                    }
                    else if ( (LA29_0==46) && (synpred12_Meteor())) {
                        alt29=1;
                    }
                    else if ( (LA29_0==57) && (synpred12_Meteor())) {
                        alt29=1;
                    }
                    else if ( (LA29_0==EOF||LA29_0==AND||(LA29_0 >= ID && LA29_0 <= IN)||LA29_0==NOT||LA29_0==OR||(LA29_0 >= SLASH && LA29_0 <= STAR)||(LA29_0 >= 36 && LA29_0 <= 37)||(LA29_0 >= 40 && LA29_0 <= 41)||(LA29_0 >= 43 && LA29_0 <= 44)||(LA29_0 >= 47 && LA29_0 <= 50)||(LA29_0 >= 52 && LA29_0 <= 55)||LA29_0==58||(LA29_0 >= 66 && LA29_0 <= 67)) ) {
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
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:191:6: ( pathSegment )=>path= pathExpression[$seg.tree]
                            {
                            pushFollow(FOLLOW_pathExpression_in_pathExpression1235);
                            path=pathExpression((seg!=null?((EvaluationExpression)seg.tree):null));

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
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                            RewriteRuleSubtreeStream stream_path=new RewriteRuleSubtreeStream(adaptor,"rule path",path!=null?path.tree:null);

                            root_0 = (EvaluationExpression)adaptor.nil();
                            // 191:53: -> $path
                            {
                                adaptor.addChild(root_0, stream_path.nextTree());

                            }


                            retval.tree = root_0;
                            }

                            }
                            break;
                        case 2 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:191:64: 
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
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                            RewriteRuleSubtreeStream stream_seg=new RewriteRuleSubtreeStream(adaptor,"rule seg",seg!=null?seg.tree:null);

                            root_0 = (EvaluationExpression)adaptor.nil();
                            // 191:64: -> $seg
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "pathSegment"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:194:1: pathSegment : ( ( '?.' )=> '?.' field= ID -> ^( EXPRESSION[\"TernaryExpression\"] ) | ( '.' )=> '.' field= ID -> ^( EXPRESSION[\"ObjectAccess\"] ) | ( '[' )=> arrayAccess );
    public final MeteorParser.pathSegment_return pathSegment() throws RecognitionException {
        MeteorParser.pathSegment_return retval = new MeteorParser.pathSegment_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token field=null;
        Token string_literal49=null;
        Token char_literal50=null;
        MeteorParser.arrayAccess_return arrayAccess51 =null;


        EvaluationExpression field_tree=null;
        EvaluationExpression string_literal49_tree=null;
        EvaluationExpression char_literal50_tree=null;
        RewriteRuleTokenStream stream_56=new RewriteRuleTokenStream(adaptor,"token 56");
        RewriteRuleTokenStream stream_46=new RewriteRuleTokenStream(adaptor,"token 46");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");

          paraphrase.push("a path expression"); 
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:197:3: ( ( '?.' )=> '?.' field= ID -> ^( EXPRESSION[\"TernaryExpression\"] ) | ( '.' )=> '.' field= ID -> ^( EXPRESSION[\"ObjectAccess\"] ) | ( '[' )=> arrayAccess )
            int alt31=3;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==56) && (synpred13_Meteor())) {
                alt31=1;
            }
            else if ( (LA31_0==46) && (synpred14_Meteor())) {
                alt31=2;
            }
            else if ( (LA31_0==57) && (synpred15_Meteor())) {
                alt31=3;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;

            }
            switch (alt31) {
                case 1 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:198:5: ( '?.' )=> '?.' field= ID
                    {
                    string_literal49=(Token)match(input,56,FOLLOW_56_in_pathSegment1287); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_56.add(string_literal49);


                    field=(Token)match(input,ID,FOLLOW_ID_in_pathSegment1291); if (state.failed) return retval; 
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
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 198:28: -> ^( EXPRESSION[\"TernaryExpression\"] )
                    {
                        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:198:31: ^( EXPRESSION[\"TernaryExpression\"] )
                        {
                        EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                        root_1 = (EvaluationExpression)adaptor.becomeRoot(
                        (EvaluationExpression)adaptor.create(EXPRESSION, "TernaryExpression")
                        , root_1);

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
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:199:5: ( '.' )=> '.' field= ID
                    {
                    char_literal50=(Token)match(input,46,FOLLOW_46_in_pathSegment1319); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_46.add(char_literal50);


                    field=(Token)match(input,ID,FOLLOW_ID_in_pathSegment1323); if (state.failed) return retval; 
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
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 199:27: -> ^( EXPRESSION[\"ObjectAccess\"] )
                    {
                        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:199:30: ^( EXPRESSION[\"ObjectAccess\"] )
                        {
                        EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                        root_1 = (EvaluationExpression)adaptor.becomeRoot(
                        (EvaluationExpression)adaptor.create(EXPRESSION, "ObjectAccess")
                        , root_1);

                        adaptor.addChild(root_1, (field!=null?field.getText():null));

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 3 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:200:5: ( '[' )=> arrayAccess
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_arrayAccess_in_pathSegment1348);
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "arrayAccess"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:202:1: arrayAccess : ( '[' STAR ']' ( ( '.' methodCall[null] )=> '.' call= methodCall[EvaluationExpression.VALUE] -> ^( EXPRESSION[\"ArrayProjection\"] $call) |path= pathSegment -> ^( EXPRESSION[\"ArrayProjection\"] $path) ) | '[' (pos= INTEGER |pos= UINT ) ']' -> ^( EXPRESSION[\"ArrayAccess\"] ) | '[' (start= INTEGER |start= UINT ) ':' (end= INTEGER |end= UINT ) ']' -> ^( EXPRESSION[\"ArrayAccess\"] ) );
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
        MeteorParser.methodCall_return call =null;

        MeteorParser.pathSegment_return path =null;


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
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_STAR=new RewriteRuleTokenStream(adaptor,"token STAR");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleTokenStream stream_46=new RewriteRuleTokenStream(adaptor,"token 46");
        RewriteRuleTokenStream stream_UINT=new RewriteRuleTokenStream(adaptor,"token UINT");
        RewriteRuleSubtreeStream stream_methodCall=new RewriteRuleSubtreeStream(adaptor,"rule methodCall");
        RewriteRuleSubtreeStream stream_pathSegment=new RewriteRuleSubtreeStream(adaptor,"rule pathSegment");
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:203:3: ( '[' STAR ']' ( ( '.' methodCall[null] )=> '.' call= methodCall[EvaluationExpression.VALUE] -> ^( EXPRESSION[\"ArrayProjection\"] $call) |path= pathSegment -> ^( EXPRESSION[\"ArrayProjection\"] $path) ) | '[' (pos= INTEGER |pos= UINT ) ']' -> ^( EXPRESSION[\"ArrayAccess\"] ) | '[' (start= INTEGER |start= UINT ) ':' (end= INTEGER |end= UINT ) ']' -> ^( EXPRESSION[\"ArrayAccess\"] ) )
            int alt36=3;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==57) ) {
                switch ( input.LA(2) ) {
                case STAR:
                    {
                    alt36=1;
                    }
                    break;
                case INTEGER:
                    {
                    int LA36_3 = input.LA(3);

                    if ( (LA36_3==58) ) {
                        alt36=2;
                    }
                    else if ( (LA36_3==47) ) {
                        alt36=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 36, 3, input);

                        throw nvae;

                    }
                    }
                    break;
                case UINT:
                    {
                    int LA36_4 = input.LA(3);

                    if ( (LA36_4==58) ) {
                        alt36=2;
                    }
                    else if ( (LA36_4==47) ) {
                        alt36=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 36, 4, input);

                        throw nvae;

                    }
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 36, 1, input);

                    throw nvae;

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
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:203:5: '[' STAR ']' ( ( '.' methodCall[null] )=> '.' call= methodCall[EvaluationExpression.VALUE] -> ^( EXPRESSION[\"ArrayProjection\"] $call) |path= pathSegment -> ^( EXPRESSION[\"ArrayProjection\"] $path) )
                    {
                    char_literal52=(Token)match(input,57,FOLLOW_57_in_arrayAccess1358); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal52);


                    STAR53=(Token)match(input,STAR,FOLLOW_STAR_in_arrayAccess1360); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_STAR.add(STAR53);


                    char_literal54=(Token)match(input,58,FOLLOW_58_in_arrayAccess1362); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_58.add(char_literal54);


                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:203:18: ( ( '.' methodCall[null] )=> '.' call= methodCall[EvaluationExpression.VALUE] -> ^( EXPRESSION[\"ArrayProjection\"] $call) |path= pathSegment -> ^( EXPRESSION[\"ArrayProjection\"] $path) )
                    int alt32=2;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==46) ) {
                        int LA32_1 = input.LA(2);

                        if ( (synpred16_Meteor()) ) {
                            alt32=1;
                        }
                        else if ( (true) ) {
                            alt32=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 32, 1, input);

                            throw nvae;

                        }
                    }
                    else if ( ((LA32_0 >= 56 && LA32_0 <= 57)) ) {
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
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:203:19: ( '.' methodCall[null] )=> '.' call= methodCall[EvaluationExpression.VALUE]
                            {
                            char_literal55=(Token)match(input,46,FOLLOW_46_in_arrayAccess1373); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_46.add(char_literal55);


                            pushFollow(FOLLOW_methodCall_in_arrayAccess1377);
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
                            RewriteRuleSubtreeStream stream_call=new RewriteRuleSubtreeStream(adaptor,"rule call",call!=null?call.tree:null);
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (EvaluationExpression)adaptor.nil();
                            // 204:4: -> ^( EXPRESSION[\"ArrayProjection\"] $call)
                            {
                                // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:204:7: ^( EXPRESSION[\"ArrayProjection\"] $call)
                                {
                                EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                                root_1 = (EvaluationExpression)adaptor.becomeRoot(
                                (EvaluationExpression)adaptor.create(EXPRESSION, "ArrayProjection")
                                , root_1);

                                adaptor.addChild(root_1, stream_call.nextTree());

                                adaptor.addChild(root_0, root_1);
                                }

                            }


                            retval.tree = root_0;
                            }

                            }
                            break;
                        case 2 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:205:6: path= pathSegment
                            {
                            pushFollow(FOLLOW_pathSegment_in_arrayAccess1400);
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
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                            RewriteRuleSubtreeStream stream_path=new RewriteRuleSubtreeStream(adaptor,"rule path",path!=null?path.tree:null);

                            root_0 = (EvaluationExpression)adaptor.nil();
                            // 206:4: -> ^( EXPRESSION[\"ArrayProjection\"] $path)
                            {
                                // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:206:7: ^( EXPRESSION[\"ArrayProjection\"] $path)
                                {
                                EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                                root_1 = (EvaluationExpression)adaptor.becomeRoot(
                                (EvaluationExpression)adaptor.create(EXPRESSION, "ArrayProjection")
                                , root_1);

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
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:207:5: '[' (pos= INTEGER |pos= UINT ) ']'
                    {
                    char_literal56=(Token)match(input,57,FOLLOW_57_in_arrayAccess1421); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal56);


                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:207:9: (pos= INTEGER |pos= UINT )
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
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:207:10: pos= INTEGER
                            {
                            pos=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_arrayAccess1426); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_INTEGER.add(pos);


                            }
                            break;
                        case 2 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:207:24: pos= UINT
                            {
                            pos=(Token)match(input,UINT,FOLLOW_UINT_in_arrayAccess1432); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_UINT.add(pos);


                            }
                            break;

                    }


                    char_literal57=(Token)match(input,58,FOLLOW_58_in_arrayAccess1435); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_58.add(char_literal57);


                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 208:3: -> ^( EXPRESSION[\"ArrayAccess\"] )
                    {
                        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:208:6: ^( EXPRESSION[\"ArrayAccess\"] )
                        {
                        EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                        root_1 = (EvaluationExpression)adaptor.becomeRoot(
                        (EvaluationExpression)adaptor.create(EXPRESSION, "ArrayAccess")
                        , root_1);

                        adaptor.addChild(root_1,  Integer.valueOf((pos!=null?pos.getText():null)) );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 3 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:209:5: '[' (start= INTEGER |start= UINT ) ':' (end= INTEGER |end= UINT ) ']'
                    {
                    char_literal58=(Token)match(input,57,FOLLOW_57_in_arrayAccess1453); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(char_literal58);


                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:209:9: (start= INTEGER |start= UINT )
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
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:209:10: start= INTEGER
                            {
                            start=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_arrayAccess1458); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_INTEGER.add(start);


                            }
                            break;
                        case 2 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:209:26: start= UINT
                            {
                            start=(Token)match(input,UINT,FOLLOW_UINT_in_arrayAccess1464); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_UINT.add(start);


                            }
                            break;

                    }


                    char_literal59=(Token)match(input,47,FOLLOW_47_in_arrayAccess1467); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_47.add(char_literal59);


                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:209:42: (end= INTEGER |end= UINT )
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
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:209:43: end= INTEGER
                            {
                            end=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_arrayAccess1472); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_INTEGER.add(end);


                            }
                            break;
                        case 2 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:209:57: end= UINT
                            {
                            end=(Token)match(input,UINT,FOLLOW_UINT_in_arrayAccess1478); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_UINT.add(end);


                            }
                            break;

                    }


                    char_literal60=(Token)match(input,58,FOLLOW_58_in_arrayAccess1481); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_58.add(char_literal60);


                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 210:3: -> ^( EXPRESSION[\"ArrayAccess\"] )
                    {
                        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:210:6: ^( EXPRESSION[\"ArrayAccess\"] )
                        {
                        EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                        root_1 = (EvaluationExpression)adaptor.becomeRoot(
                        (EvaluationExpression)adaptor.create(EXPRESSION, "ArrayAccess")
                        , root_1);

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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "valueExpression"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:212:1: valueExpression : ( ( ID '(' )=> functionCall | functionReference | ( FN )=>func= inlineFunction -> ^( EXPRESSION[\"ConstantExpression\"] ) | parenthesesExpression | literal | VAR ->| ( ( ID ':' )=>packageName= ID ':' )? constant= ID {...}? => ->| arrayCreation | objectCreation );
    public final MeteorParser.valueExpression_return valueExpression() throws RecognitionException {
        MeteorParser.valueExpression_return retval = new MeteorParser.valueExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token packageName=null;
        Token constant=null;
        Token VAR65=null;
        Token char_literal66=null;
        MeteorParser.inlineFunction_return func =null;

        MeteorParser.functionCall_return functionCall61 =null;

        MeteorParser.functionReference_return functionReference62 =null;

        MeteorParser.parenthesesExpression_return parenthesesExpression63 =null;

        MeteorParser.literal_return literal64 =null;

        MeteorParser.arrayCreation_return arrayCreation67 =null;

        MeteorParser.objectCreation_return objectCreation68 =null;


        EvaluationExpression packageName_tree=null;
        EvaluationExpression constant_tree=null;
        EvaluationExpression VAR65_tree=null;
        EvaluationExpression char_literal66_tree=null;
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_inlineFunction=new RewriteRuleSubtreeStream(adaptor,"rule inlineFunction");
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:213:2: ( ( ID '(' )=> functionCall | functionReference | ( FN )=>func= inlineFunction -> ^( EXPRESSION[\"ConstantExpression\"] ) | parenthesesExpression | literal | VAR ->| ( ( ID ':' )=>packageName= ID ':' )? constant= ID {...}? => ->| arrayCreation | objectCreation )
            int alt38=9;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==ID) ) {
                int LA38_1 = input.LA(2);

                if ( (LA38_1==47) ) {
                    int LA38_9 = input.LA(3);

                    if ( (synpred17_Meteor()) ) {
                        alt38=1;
                    }
                    else if ( (true) ) {
                        alt38=7;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 38, 9, input);

                        throw nvae;

                    }
                }
                else if ( (LA38_1==39) && (synpred17_Meteor())) {
                    alt38=1;
                }
                else if ( (LA38_1==EOF||LA38_1==AND||(LA38_1 >= ID && LA38_1 <= IN)||LA38_1==NOT||LA38_1==OR||(LA38_1 >= SLASH && LA38_1 <= STAR)||(LA38_1 >= 36 && LA38_1 <= 37)||(LA38_1 >= 40 && LA38_1 <= 41)||(LA38_1 >= 43 && LA38_1 <= 44)||LA38_1==46||(LA38_1 >= 48 && LA38_1 <= 50)||(LA38_1 >= 52 && LA38_1 <= 58)||(LA38_1 >= 66 && LA38_1 <= 67)) ) {
                    alt38=7;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 38, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA38_0==38) ) {
                alt38=2;
            }
            else if ( (LA38_0==FN) && (synpred18_Meteor())) {
                alt38=3;
            }
            else if ( (LA38_0==39) ) {
                alt38=4;
            }
            else if ( (LA38_0==DECIMAL||LA38_0==INTEGER||(LA38_0 >= STRING && LA38_0 <= UINT)||(LA38_0 >= 59 && LA38_0 <= 60)||LA38_0==62) ) {
                alt38=5;
            }
            else if ( (LA38_0==VAR) ) {
                alt38=6;
            }
            else if ( (LA38_0==57) ) {
                alt38=8;
            }
            else if ( (LA38_0==65) ) {
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
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:213:4: ( ID '(' )=> functionCall
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_functionCall_in_valueExpression1513);
                    functionCall61=functionCall();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, functionCall61.getTree());

                    }
                    break;
                case 2 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:214:4: functionReference
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_functionReference_in_valueExpression1518);
                    functionReference62=functionReference();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, functionReference62.getTree());

                    }
                    break;
                case 3 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:215:5: ( FN )=>func= inlineFunction
                    {
                    pushFollow(FOLLOW_inlineFunction_in_valueExpression1531);
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
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 215:32: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:215:35: ^( EXPRESSION[\"ConstantExpression\"] )
                        {
                        EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                        root_1 = (EvaluationExpression)adaptor.becomeRoot(
                        (EvaluationExpression)adaptor.create(EXPRESSION, "ConstantExpression")
                        , root_1);

                        adaptor.addChild(root_1,  new FunctionNode((func!=null?func.func:null)) );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 4 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:216:4: parenthesesExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_parenthesesExpression_in_valueExpression1545);
                    parenthesesExpression63=parenthesesExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, parenthesesExpression63.getTree());

                    }
                    break;
                case 5 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:217:4: literal
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_literal_in_valueExpression1551);
                    literal64=literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, literal64.getTree());

                    }
                    break;
                case 6 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:218:4: VAR
                    {
                    VAR65=(Token)match(input,VAR,FOLLOW_VAR_in_valueExpression1557); if (state.failed) return retval; 
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
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 218:8: ->
                    {
                        adaptor.addChild(root_0,  getInputSelection(VAR65) );

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 7 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:219:5: ( ( ID ':' )=>packageName= ID ':' )? constant= ID {...}? =>
                    {
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:219:5: ( ( ID ':' )=>packageName= ID ':' )?
                    int alt37=2;
                    int LA37_0 = input.LA(1);

                    if ( (LA37_0==ID) ) {
                        int LA37_1 = input.LA(2);

                        if ( (LA37_1==47) ) {
                            int LA37_2 = input.LA(3);

                            if ( (synpred19_Meteor()) ) {
                                alt37=1;
                            }
                        }
                    }
                    switch (alt37) {
                        case 1 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:219:6: ( ID ':' )=>packageName= ID ':'
                            {
                            packageName=(Token)match(input,ID,FOLLOW_ID_in_valueExpression1577); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_ID.add(packageName);


                            char_literal66=(Token)match(input,47,FOLLOW_47_in_valueExpression1579); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_47.add(char_literal66);


                            }
                            break;

                    }


                    constant=(Token)match(input,ID,FOLLOW_ID_in_valueExpression1585); if (state.failed) return retval; 
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
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 220:5: ->
                    {
                        adaptor.addChild(root_0,  getScope((packageName!=null?packageName.getText():null)).getConstantRegistry().get((constant!=null?constant.getText():null)) );

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 8 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:221:4: arrayCreation
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_arrayCreation_in_valueExpression1605);
                    arrayCreation67=arrayCreation();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arrayCreation67.getTree());

                    }
                    break;
                case 9 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:222:4: objectCreation
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_objectCreation_in_valueExpression1611);
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "operatorExpression"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:224:1: operatorExpression : op= operator -> ^( EXPRESSION[\"NestedOperatorExpression\"] ) ;
    public final MeteorParser.operatorExpression_return operatorExpression() throws RecognitionException {
        MeteorParser.operatorExpression_return retval = new MeteorParser.operatorExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.operator_return op =null;


        RewriteRuleSubtreeStream stream_operator=new RewriteRuleSubtreeStream(adaptor,"rule operator");
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:225:2: (op= operator -> ^( EXPRESSION[\"NestedOperatorExpression\"] ) )
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:225:4: op= operator
            {
            pushFollow(FOLLOW_operator_in_operatorExpression1623);
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
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 225:16: -> ^( EXPRESSION[\"NestedOperatorExpression\"] )
            {
                // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:225:19: ^( EXPRESSION[\"NestedOperatorExpression\"] )
                {
                EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                root_1 = (EvaluationExpression)adaptor.becomeRoot(
                (EvaluationExpression)adaptor.create(EXPRESSION, "NestedOperatorExpression")
                , root_1);

                adaptor.addChild(root_1,  (op!=null?op.op:null) );

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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "parenthesesExpression"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:227:1: parenthesesExpression : ( '(' expression ')' ) -> expression ;
    public final MeteorParser.parenthesesExpression_return parenthesesExpression() throws RecognitionException {
        MeteorParser.parenthesesExpression_return retval = new MeteorParser.parenthesesExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token char_literal69=null;
        Token char_literal71=null;
        MeteorParser.expression_return expression70 =null;


        EvaluationExpression char_literal69_tree=null;
        EvaluationExpression char_literal71_tree=null;
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:228:2: ( ( '(' expression ')' ) -> expression )
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:228:4: ( '(' expression ')' )
            {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:228:4: ( '(' expression ')' )
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:228:5: '(' expression ')'
            {
            char_literal69=(Token)match(input,39,FOLLOW_39_in_parenthesesExpression1642); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_39.add(char_literal69);


            pushFollow(FOLLOW_expression_in_parenthesesExpression1644);
            expression70=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expression.add(expression70.getTree());

            char_literal71=(Token)match(input,40,FOLLOW_40_in_parenthesesExpression1646); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_40.add(char_literal71);


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
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 228:25: -> expression
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "methodCall"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:230:1: methodCall[EvaluationExpression targetExpr] : (packageName= ID ':' )? name= ID '(' ( (param= expression ) ( ',' (param= expression ) )* )? ')' ->;
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
        MeteorParser.expression_return param =null;


        EvaluationExpression packageName_tree=null;
        EvaluationExpression name_tree=null;
        EvaluationExpression char_literal72_tree=null;
        EvaluationExpression char_literal73_tree=null;
        EvaluationExpression char_literal74_tree=null;
        EvaluationExpression char_literal75_tree=null;
        RewriteRuleTokenStream stream_43=new RewriteRuleTokenStream(adaptor,"token 43");
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
         List<EvaluationExpression> params = new ArrayList();
                paraphrase.push("a method call"); 
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:234:3: ( (packageName= ID ':' )? name= ID '(' ( (param= expression ) ( ',' (param= expression ) )* )? ')' ->)
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:234:5: (packageName= ID ':' )? name= ID '(' ( (param= expression ) ( ',' (param= expression ) )* )? ')'
            {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:234:5: (packageName= ID ':' )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==ID) ) {
                int LA39_1 = input.LA(2);

                if ( (LA39_1==47) ) {
                    alt39=1;
                }
            }
            switch (alt39) {
                case 1 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:234:6: packageName= ID ':'
                    {
                    packageName=(Token)match(input,ID,FOLLOW_ID_in_methodCall1676); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(packageName);


                    char_literal72=(Token)match(input,47,FOLLOW_47_in_methodCall1678); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_47.add(char_literal72);


                    }
                    break;

            }


            name=(Token)match(input,ID,FOLLOW_ID_in_methodCall1684); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(name);


            char_literal73=(Token)match(input,39,FOLLOW_39_in_methodCall1686); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_39.add(char_literal73);


            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:235:3: ( (param= expression ) ( ',' (param= expression ) )* )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==DECIMAL||LA41_0==FN||LA41_0==ID||LA41_0==INTEGER||(LA41_0 >= STRING && LA41_0 <= UINT)||LA41_0==VAR||LA41_0==35||(LA41_0 >= 38 && LA41_0 <= 39)||LA41_0==42||LA41_0==45||LA41_0==57||(LA41_0 >= 59 && LA41_0 <= 62)||(LA41_0 >= 64 && LA41_0 <= 65)||LA41_0==68) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:235:4: (param= expression ) ( ',' (param= expression ) )*
                    {
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:235:4: (param= expression )
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:235:5: param= expression
                    {
                    pushFollow(FOLLOW_expression_in_methodCall1695);
                    param=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(param.getTree());

                    if ( state.backtracking==0 ) { params.add((param!=null?((EvaluationExpression)param.tree):null)); }

                    }


                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:236:3: ( ',' (param= expression ) )*
                    loop40:
                    do {
                        int alt40=2;
                        int LA40_0 = input.LA(1);

                        if ( (LA40_0==43) ) {
                            alt40=1;
                        }


                        switch (alt40) {
                    	case 1 :
                    	    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:236:4: ',' (param= expression )
                    	    {
                    	    char_literal74=(Token)match(input,43,FOLLOW_43_in_methodCall1704); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_43.add(char_literal74);


                    	    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:236:8: (param= expression )
                    	    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:236:9: param= expression
                    	    {
                    	    pushFollow(FOLLOW_expression_in_methodCall1709);
                    	    param=expression();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_expression.add(param.getTree());

                    	    if ( state.backtracking==0 ) { params.add((param!=null?((EvaluationExpression)param.tree):null)); }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop40;
                        }
                    } while (true);


                    }
                    break;

            }


            char_literal75=(Token)match(input,40,FOLLOW_40_in_methodCall1721); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_40.add(char_literal75);


            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 237:7: ->
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "functionCall"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:239:1: functionCall : methodCall[null] ;
    public final MeteorParser.functionCall_return functionCall() throws RecognitionException {
        MeteorParser.functionCall_return retval = new MeteorParser.functionCall_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.methodCall_return methodCall76 =null;



        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:240:2: ( methodCall[null] )
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:240:4: methodCall[null]
            {
            root_0 = (EvaluationExpression)adaptor.nil();


            pushFollow(FOLLOW_methodCall_in_functionCall1736);
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "functionReference"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:242:1: functionReference : '&' ( ( ID ':' )=>packageName= ID ':' )? name= ID -> ^( EXPRESSION[\"ConstantExpression\"] ) ;
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
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_38=new RewriteRuleTokenStream(adaptor,"token 38");

        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:243:3: ( '&' ( ( ID ':' )=>packageName= ID ':' )? name= ID -> ^( EXPRESSION[\"ConstantExpression\"] ) )
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:243:5: '&' ( ( ID ':' )=>packageName= ID ':' )? name= ID
            {
            char_literal77=(Token)match(input,38,FOLLOW_38_in_functionReference1747); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_38.add(char_literal77);


            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:243:9: ( ( ID ':' )=>packageName= ID ':' )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==ID) ) {
                int LA42_1 = input.LA(2);

                if ( (LA42_1==47) ) {
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
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:243:10: ( ID ':' )=>packageName= ID ':'
                    {
                    packageName=(Token)match(input,ID,FOLLOW_ID_in_functionReference1759); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(packageName);


                    char_literal78=(Token)match(input,47,FOLLOW_47_in_functionReference1761); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_47.add(char_literal78);


                    }
                    break;

            }


            name=(Token)match(input,ID,FOLLOW_ID_in_functionReference1767); if (state.failed) return retval; 
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
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 244:9: -> ^( EXPRESSION[\"ConstantExpression\"] )
            {
                // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:244:12: ^( EXPRESSION[\"ConstantExpression\"] )
                {
                EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                root_1 = (EvaluationExpression)adaptor.becomeRoot(
                (EvaluationExpression)adaptor.create(EXPRESSION, "ConstantExpression")
                , root_1);

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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "fieldAssignment"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:246:1: fieldAssignment : ( ( ( ID ':' )=> ID ':' expression ->) | VAR ( '.' STAR ->|p= contextAwarePathExpression[getVariableSafely($VAR).toInputSelection($operator::result)] ( ':' e2= expression ->| ->) ) );
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
        MeteorParser.contextAwarePathExpression_return p =null;

        MeteorParser.expression_return e2 =null;

        MeteorParser.expression_return expression81 =null;


        EvaluationExpression ID79_tree=null;
        EvaluationExpression char_literal80_tree=null;
        EvaluationExpression VAR82_tree=null;
        EvaluationExpression char_literal83_tree=null;
        EvaluationExpression STAR84_tree=null;
        EvaluationExpression char_literal85_tree=null;
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_STAR=new RewriteRuleTokenStream(adaptor,"token STAR");
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleTokenStream stream_46=new RewriteRuleTokenStream(adaptor,"token 46");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_contextAwarePathExpression=new RewriteRuleSubtreeStream(adaptor,"rule contextAwarePathExpression");
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:247:2: ( ( ( ID ':' )=> ID ':' expression ->) | VAR ( '.' STAR ->|p= contextAwarePathExpression[getVariableSafely($VAR).toInputSelection($operator::result)] ( ':' e2= expression ->| ->) ) )
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==ID) && (synpred21_Meteor())) {
                alt45=1;
            }
            else if ( (LA45_0==VAR) ) {
                alt45=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 45, 0, input);

                throw nvae;

            }
            switch (alt45) {
                case 1 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:247:4: ( ( ID ':' )=> ID ':' expression ->)
                    {
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:247:4: ( ( ID ':' )=> ID ':' expression ->)
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:247:5: ( ID ':' )=> ID ':' expression
                    {
                    ID79=(Token)match(input,ID,FOLLOW_ID_in_fieldAssignment1803); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(ID79);


                    char_literal80=(Token)match(input,47,FOLLOW_47_in_fieldAssignment1805); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_47.add(char_literal80);


                    pushFollow(FOLLOW_expression_in_fieldAssignment1807);
                    expression81=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(expression81.getTree());

                    if ( state.backtracking==0 ) { ((objectCreation_scope)objectCreation_stack.peek()).mappings.add(new ObjectCreation.FieldAssignment((ID79!=null?ID79.getText():null), (expression81!=null?((EvaluationExpression)expression81.tree):null))); }

                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 248:104: ->
                    {
                        root_0 = null;
                    }


                    retval.tree = root_0;
                    }

                    }


                    }
                    break;
                case 2 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:249:5: VAR ( '.' STAR ->|p= contextAwarePathExpression[getVariableSafely($VAR).toInputSelection($operator::result)] ( ':' e2= expression ->| ->) )
                    {
                    VAR82=(Token)match(input,VAR,FOLLOW_VAR_in_fieldAssignment1824); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_VAR.add(VAR82);


                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:250:5: ( '.' STAR ->|p= contextAwarePathExpression[getVariableSafely($VAR).toInputSelection($operator::result)] ( ':' e2= expression ->| ->) )
                    int alt44=2;
                    int LA44_0 = input.LA(1);

                    if ( (LA44_0==46) ) {
                        int LA44_1 = input.LA(2);

                        if ( (LA44_1==STAR) ) {
                            alt44=1;
                        }
                        else if ( (LA44_1==ID) ) {
                            alt44=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 44, 1, input);

                            throw nvae;

                        }
                    }
                    else if ( ((LA44_0 >= 56 && LA44_0 <= 57)) ) {
                        alt44=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 44, 0, input);

                        throw nvae;

                    }
                    switch (alt44) {
                        case 1 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:250:7: '.' STAR
                            {
                            char_literal83=(Token)match(input,46,FOLLOW_46_in_fieldAssignment1833); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_46.add(char_literal83);


                            STAR84=(Token)match(input,STAR,FOLLOW_STAR_in_fieldAssignment1835); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_STAR.add(STAR84);


                            if ( state.backtracking==0 ) { ((objectCreation_scope)objectCreation_stack.peek()).mappings.add(new ObjectCreation.CopyFields(getInputSelection(VAR82))); }

                            // AST REWRITE
                            // elements: 
                            // token labels: 
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            if ( state.backtracking==0 ) {

                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (EvaluationExpression)adaptor.nil();
                            // 250:107: ->
                            {
                                root_0 = null;
                            }


                            retval.tree = root_0;
                            }

                            }
                            break;
                        case 2 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:252:9: p= contextAwarePathExpression[getVariableSafely($VAR).toInputSelection($operator::result)] ( ':' e2= expression ->| ->)
                            {
                            pushFollow(FOLLOW_contextAwarePathExpression_in_fieldAssignment1852);
                            p=contextAwarePathExpression(getVariableSafely(VAR82).toInputSelection(((operator_scope)operator_stack.peek()).result));

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_contextAwarePathExpression.add(p.getTree());

                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:253:7: ( ':' e2= expression ->| ->)
                            int alt43=2;
                            int LA43_0 = input.LA(1);

                            if ( (LA43_0==47) ) {
                                alt43=1;
                            }
                            else if ( (LA43_0==43||LA43_0==67) ) {
                                alt43=2;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 43, 0, input);

                                throw nvae;

                            }
                            switch (alt43) {
                                case 1 :
                                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:253:9: ':' e2= expression
                                    {
                                    char_literal85=(Token)match(input,47,FOLLOW_47_in_fieldAssignment1863); if (state.failed) return retval; 
                                    if ( state.backtracking==0 ) stream_47.add(char_literal85);


                                    pushFollow(FOLLOW_expression_in_fieldAssignment1867);
                                    e2=expression();

                                    state._fsp--;
                                    if (state.failed) return retval;
                                    if ( state.backtracking==0 ) stream_expression.add(e2.getTree());

                                    if ( state.backtracking==0 ) { ((objectCreation_scope)objectCreation_stack.peek()).mappings.add(new ObjectCreation.TagMapping((p!=null?((EvaluationExpression)p.tree):null), (e2!=null?((EvaluationExpression)e2.tree):null))); }

                                    // AST REWRITE
                                    // elements: 
                                    // token labels: 
                                    // rule labels: retval
                                    // token list labels: 
                                    // rule list labels: 
                                    // wildcard labels: 
                                    if ( state.backtracking==0 ) {

                                    retval.tree = root_0;
                                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                                    root_0 = (EvaluationExpression)adaptor.nil();
                                    // 253:112: ->
                                    {
                                        root_0 = null;
                                    }


                                    retval.tree = root_0;
                                    }

                                    }
                                    break;
                                case 2 :
                                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:254:23: 
                                    {
                                    if ( state.backtracking==0 ) { ((objectCreation_scope)objectCreation_stack.peek()).mappings.add(new ObjectCreation.FieldAssignment(getAssignmentName((p!=null?((EvaluationExpression)p.tree):null)), (p!=null?((EvaluationExpression)p.tree):null))); }

                                    // AST REWRITE
                                    // elements: 
                                    // token labels: 
                                    // rule labels: retval
                                    // token list labels: 
                                    // rule list labels: 
                                    // wildcard labels: 
                                    if ( state.backtracking==0 ) {

                                    retval.tree = root_0;
                                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                                    root_0 = (EvaluationExpression)adaptor.nil();
                                    // 254:131: ->
                                    {
                                        root_0 = null;
                                    }


                                    retval.tree = root_0;
                                    }

                                    }
                                    break;

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
    protected Stack objectCreation_stack = new Stack();


    public static class objectCreation_return extends ParserRuleReturnScope {
        EvaluationExpression tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "objectCreation"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:259:1: objectCreation : '{' ( fieldAssignment ( ',' fieldAssignment )* ( ',' )? )? '}' -> ^( EXPRESSION[\"ObjectCreation\"] ) ;
    public final MeteorParser.objectCreation_return objectCreation() throws RecognitionException {
        objectCreation_stack.push(new objectCreation_scope());
        MeteorParser.objectCreation_return retval = new MeteorParser.objectCreation_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token char_literal86=null;
        Token char_literal88=null;
        Token char_literal90=null;
        Token char_literal91=null;
        MeteorParser.fieldAssignment_return fieldAssignment87 =null;

        MeteorParser.fieldAssignment_return fieldAssignment89 =null;


        EvaluationExpression char_literal86_tree=null;
        EvaluationExpression char_literal88_tree=null;
        EvaluationExpression char_literal90_tree=null;
        EvaluationExpression char_literal91_tree=null;
        RewriteRuleTokenStream stream_67=new RewriteRuleTokenStream(adaptor,"token 67");
        RewriteRuleTokenStream stream_43=new RewriteRuleTokenStream(adaptor,"token 43");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleSubtreeStream stream_fieldAssignment=new RewriteRuleSubtreeStream(adaptor,"rule fieldAssignment");
         ((objectCreation_scope)objectCreation_stack.peek()).mappings = new ArrayList<ObjectCreation.Mapping>(); 
                paraphrase.push("a json object"); 
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:264:2: ( '{' ( fieldAssignment ( ',' fieldAssignment )* ( ',' )? )? '}' -> ^( EXPRESSION[\"ObjectCreation\"] ) )
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:264:4: '{' ( fieldAssignment ( ',' fieldAssignment )* ( ',' )? )? '}'
            {
            char_literal86=(Token)match(input,65,FOLLOW_65_in_objectCreation1932); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_65.add(char_literal86);


            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:264:8: ( fieldAssignment ( ',' fieldAssignment )* ( ',' )? )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==ID||LA48_0==VAR) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:264:9: fieldAssignment ( ',' fieldAssignment )* ( ',' )?
                    {
                    pushFollow(FOLLOW_fieldAssignment_in_objectCreation1935);
                    fieldAssignment87=fieldAssignment();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_fieldAssignment.add(fieldAssignment87.getTree());

                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:264:25: ( ',' fieldAssignment )*
                    loop46:
                    do {
                        int alt46=2;
                        int LA46_0 = input.LA(1);

                        if ( (LA46_0==43) ) {
                            int LA46_1 = input.LA(2);

                            if ( (LA46_1==ID||LA46_1==VAR) ) {
                                alt46=1;
                            }


                        }


                        switch (alt46) {
                    	case 1 :
                    	    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:264:26: ',' fieldAssignment
                    	    {
                    	    char_literal88=(Token)match(input,43,FOLLOW_43_in_objectCreation1938); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_43.add(char_literal88);


                    	    pushFollow(FOLLOW_fieldAssignment_in_objectCreation1940);
                    	    fieldAssignment89=fieldAssignment();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_fieldAssignment.add(fieldAssignment89.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop46;
                        }
                    } while (true);


                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:264:48: ( ',' )?
                    int alt47=2;
                    int LA47_0 = input.LA(1);

                    if ( (LA47_0==43) ) {
                        alt47=1;
                    }
                    switch (alt47) {
                        case 1 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:264:48: ','
                            {
                            char_literal90=(Token)match(input,43,FOLLOW_43_in_objectCreation1944); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_43.add(char_literal90);


                            }
                            break;

                    }


                    }
                    break;

            }


            char_literal91=(Token)match(input,67,FOLLOW_67_in_objectCreation1949); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_67.add(char_literal91);


            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 264:59: -> ^( EXPRESSION[\"ObjectCreation\"] )
            {
                // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:264:62: ^( EXPRESSION[\"ObjectCreation\"] )
                {
                EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                root_1 = (EvaluationExpression)adaptor.becomeRoot(
                (EvaluationExpression)adaptor.create(EXPRESSION, "ObjectCreation")
                , root_1);

                adaptor.addChild(root_1,  ((objectCreation_scope)objectCreation_stack.peek()).mappings );

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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "literal"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:267:1: literal : (val= 'true' -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= 'false' -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= DECIMAL -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= STRING -> ^( EXPRESSION[\"ConstantExpression\"] ) | (val= UINT |val= INTEGER ) -> ^( EXPRESSION[\"ConstantExpression\"] ) | 'null' ->);
    public final MeteorParser.literal_return literal() throws RecognitionException {
        MeteorParser.literal_return retval = new MeteorParser.literal_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token val=null;
        Token string_literal92=null;

        EvaluationExpression val_tree=null;
        EvaluationExpression string_literal92_tree=null;
        RewriteRuleTokenStream stream_INTEGER=new RewriteRuleTokenStream(adaptor,"token INTEGER");
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleTokenStream stream_DECIMAL=new RewriteRuleTokenStream(adaptor,"token DECIMAL");
        RewriteRuleTokenStream stream_62=new RewriteRuleTokenStream(adaptor,"token 62");
        RewriteRuleTokenStream stream_UINT=new RewriteRuleTokenStream(adaptor,"token UINT");
        RewriteRuleTokenStream stream_60=new RewriteRuleTokenStream(adaptor,"token 60");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

         paraphrase.push("a literal"); 
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:270:2: (val= 'true' -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= 'false' -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= DECIMAL -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= STRING -> ^( EXPRESSION[\"ConstantExpression\"] ) | (val= UINT |val= INTEGER ) -> ^( EXPRESSION[\"ConstantExpression\"] ) | 'null' ->)
            int alt50=6;
            switch ( input.LA(1) ) {
            case 62:
                {
                alt50=1;
                }
                break;
            case 59:
                {
                alt50=2;
                }
                break;
            case DECIMAL:
                {
                alt50=3;
                }
                break;
            case STRING:
                {
                alt50=4;
                }
                break;
            case INTEGER:
            case UINT:
                {
                alt50=5;
                }
                break;
            case 60:
                {
                alt50=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 50, 0, input);

                throw nvae;

            }

            switch (alt50) {
                case 1 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:270:4: val= 'true'
                    {
                    val=(Token)match(input,62,FOLLOW_62_in_literal1987); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_62.add(val);


                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 270:15: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:270:18: ^( EXPRESSION[\"ConstantExpression\"] )
                        {
                        EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                        root_1 = (EvaluationExpression)adaptor.becomeRoot(
                        (EvaluationExpression)adaptor.create(EXPRESSION, "ConstantExpression")
                        , root_1);

                        adaptor.addChild(root_1,  Boolean.TRUE );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 2 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:271:4: val= 'false'
                    {
                    val=(Token)match(input,59,FOLLOW_59_in_literal2003); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_59.add(val);


                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 271:16: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:271:19: ^( EXPRESSION[\"ConstantExpression\"] )
                        {
                        EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                        root_1 = (EvaluationExpression)adaptor.becomeRoot(
                        (EvaluationExpression)adaptor.create(EXPRESSION, "ConstantExpression")
                        , root_1);

                        adaptor.addChild(root_1,  Boolean.FALSE );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 3 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:272:4: val= DECIMAL
                    {
                    val=(Token)match(input,DECIMAL,FOLLOW_DECIMAL_in_literal2019); if (state.failed) return retval; 
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
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 272:16: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:272:19: ^( EXPRESSION[\"ConstantExpression\"] )
                        {
                        EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                        root_1 = (EvaluationExpression)adaptor.becomeRoot(
                        (EvaluationExpression)adaptor.create(EXPRESSION, "ConstantExpression")
                        , root_1);

                        adaptor.addChild(root_1,  new BigDecimal((val!=null?val.getText():null)) );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 4 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:273:4: val= STRING
                    {
                    val=(Token)match(input,STRING,FOLLOW_STRING_in_literal2035); if (state.failed) return retval; 
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
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 273:15: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:273:18: ^( EXPRESSION[\"ConstantExpression\"] )
                        {
                        EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                        root_1 = (EvaluationExpression)adaptor.becomeRoot(
                        (EvaluationExpression)adaptor.create(EXPRESSION, "ConstantExpression")
                        , root_1);

                        adaptor.addChild(root_1,  val.getText() );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 5 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:274:5: (val= UINT |val= INTEGER )
                    {
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:274:5: (val= UINT |val= INTEGER )
                    int alt49=2;
                    int LA49_0 = input.LA(1);

                    if ( (LA49_0==UINT) ) {
                        alt49=1;
                    }
                    else if ( (LA49_0==INTEGER) ) {
                        alt49=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 49, 0, input);

                        throw nvae;

                    }
                    switch (alt49) {
                        case 1 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:274:6: val= UINT
                            {
                            val=(Token)match(input,UINT,FOLLOW_UINT_in_literal2053); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_UINT.add(val);


                            }
                            break;
                        case 2 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:274:17: val= INTEGER
                            {
                            val=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_literal2059); if (state.failed) return retval; 
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
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 274:30: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:274:33: ^( EXPRESSION[\"ConstantExpression\"] )
                        {
                        EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                        root_1 = (EvaluationExpression)adaptor.becomeRoot(
                        (EvaluationExpression)adaptor.create(EXPRESSION, "ConstantExpression")
                        , root_1);

                        adaptor.addChild(root_1,  parseInt((val!=null?val.getText():null)) );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 6 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:275:5: 'null'
                    {
                    string_literal92=(Token)match(input,60,FOLLOW_60_in_literal2075); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_60.add(string_literal92);


                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {

                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 275:12: ->
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "arrayCreation"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:277:1: arrayCreation : '[' (elems+= expression ( ',' elems+= expression )* ( ',' )? )? ']' -> ^( EXPRESSION[\"ArrayCreation\"] ) ;
    public final MeteorParser.arrayCreation_return arrayCreation() throws RecognitionException {
        MeteorParser.arrayCreation_return retval = new MeteorParser.arrayCreation_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token char_literal93=null;
        Token char_literal94=null;
        Token char_literal95=null;
        Token char_literal96=null;
        List list_elems=null;
        RuleReturnScope elems = null;
        EvaluationExpression char_literal93_tree=null;
        EvaluationExpression char_literal94_tree=null;
        EvaluationExpression char_literal95_tree=null;
        EvaluationExpression char_literal96_tree=null;
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_43=new RewriteRuleTokenStream(adaptor,"token 43");
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
         paraphrase.push("a json array"); 
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:280:2: ( '[' (elems+= expression ( ',' elems+= expression )* ( ',' )? )? ']' -> ^( EXPRESSION[\"ArrayCreation\"] ) )
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:280:5: '[' (elems+= expression ( ',' elems+= expression )* ( ',' )? )? ']'
            {
            char_literal93=(Token)match(input,57,FOLLOW_57_in_arrayCreation2099); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_57.add(char_literal93);


            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:280:9: (elems+= expression ( ',' elems+= expression )* ( ',' )? )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==DECIMAL||LA53_0==FN||LA53_0==ID||LA53_0==INTEGER||(LA53_0 >= STRING && LA53_0 <= UINT)||LA53_0==VAR||LA53_0==35||(LA53_0 >= 38 && LA53_0 <= 39)||LA53_0==42||LA53_0==45||LA53_0==57||(LA53_0 >= 59 && LA53_0 <= 62)||(LA53_0 >= 64 && LA53_0 <= 65)||LA53_0==68) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:280:10: elems+= expression ( ',' elems+= expression )* ( ',' )?
                    {
                    pushFollow(FOLLOW_expression_in_arrayCreation2104);
                    elems=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(elems.getTree());
                    if (list_elems==null) list_elems=new ArrayList();
                    list_elems.add(elems.getTree());


                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:280:28: ( ',' elems+= expression )*
                    loop51:
                    do {
                        int alt51=2;
                        int LA51_0 = input.LA(1);

                        if ( (LA51_0==43) ) {
                            int LA51_1 = input.LA(2);

                            if ( (LA51_1==DECIMAL||LA51_1==FN||LA51_1==ID||LA51_1==INTEGER||(LA51_1 >= STRING && LA51_1 <= UINT)||LA51_1==VAR||LA51_1==35||(LA51_1 >= 38 && LA51_1 <= 39)||LA51_1==42||LA51_1==45||LA51_1==57||(LA51_1 >= 59 && LA51_1 <= 62)||(LA51_1 >= 64 && LA51_1 <= 65)||LA51_1==68) ) {
                                alt51=1;
                            }


                        }


                        switch (alt51) {
                    	case 1 :
                    	    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:280:29: ',' elems+= expression
                    	    {
                    	    char_literal94=(Token)match(input,43,FOLLOW_43_in_arrayCreation2107); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_43.add(char_literal94);


                    	    pushFollow(FOLLOW_expression_in_arrayCreation2111);
                    	    elems=expression();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_expression.add(elems.getTree());
                    	    if (list_elems==null) list_elems=new ArrayList();
                    	    list_elems.add(elems.getTree());


                    	    }
                    	    break;

                    	default :
                    	    break loop51;
                        }
                    } while (true);


                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:280:53: ( ',' )?
                    int alt52=2;
                    int LA52_0 = input.LA(1);

                    if ( (LA52_0==43) ) {
                        alt52=1;
                    }
                    switch (alt52) {
                        case 1 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:280:53: ','
                            {
                            char_literal95=(Token)match(input,43,FOLLOW_43_in_arrayCreation2115); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_43.add(char_literal95);


                            }
                            break;

                    }


                    }
                    break;

            }


            char_literal96=(Token)match(input,58,FOLLOW_58_in_arrayCreation2120); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_58.add(char_literal96);


            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 280:64: -> ^( EXPRESSION[\"ArrayCreation\"] )
            {
                // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:280:67: ^( EXPRESSION[\"ArrayCreation\"] )
                {
                EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                root_1 = (EvaluationExpression)adaptor.becomeRoot(
                (EvaluationExpression)adaptor.create(EXPRESSION, "ArrayCreation")
                , root_1);

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
    protected Stack operator_stack = new Stack();


    public static class operator_return extends ParserRuleReturnScope {
        public Operator<?> op=null;
        EvaluationExpression tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "operator"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:286:1: operator returns [Operator<?> op=null] : ( readOperator | writeOperator | genericOperator );
    public final MeteorParser.operator_return operator() throws RecognitionException {
        operator_stack.push(new operator_scope());
        MeteorParser.operator_return retval = new MeteorParser.operator_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.readOperator_return readOperator97 =null;

        MeteorParser.writeOperator_return writeOperator98 =null;

        MeteorParser.genericOperator_return genericOperator99 =null;



        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:290:2: ( readOperator | writeOperator | genericOperator )
            int alt54=3;
            switch ( input.LA(1) ) {
            case VAR:
                {
                int LA54_1 = input.LA(2);

                if ( (LA54_1==51) ) {
                    int LA54_5 = input.LA(3);

                    if ( (LA54_5==61) ) {
                        alt54=1;
                    }
                    else if ( (LA54_5==ID) ) {
                        alt54=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 54, 5, input);

                        throw nvae;

                    }
                }
                else if ( (LA54_1==43) ) {
                    alt54=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 54, 1, input);

                    throw nvae;

                }
                }
                break;
            case 61:
                {
                alt54=1;
                }
                break;
            case 64:
                {
                alt54=2;
                }
                break;
            case ID:
                {
                alt54=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 54, 0, input);

                throw nvae;

            }

            switch (alt54) {
                case 1 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:290:4: readOperator
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_readOperator_in_operator2146);
                    readOperator97=readOperator();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, readOperator97.getTree());

                    if ( state.backtracking==0 ) { retval.op = (readOperator97!=null?readOperator97.source:null); }

                    }
                    break;
                case 2 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:291:5: writeOperator
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_writeOperator_in_operator2154);
                    writeOperator98=writeOperator();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, writeOperator98.getTree());

                    if ( state.backtracking==0 ) { retval.op = (writeOperator98!=null?writeOperator98.sink:null); }

                    }
                    break;
                case 3 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:292:5: genericOperator
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_genericOperator_in_operator2162);
                    genericOperator99=genericOperator();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, genericOperator99.getTree());

                    if ( state.backtracking==0 ) { retval.op = (genericOperator99!=null?genericOperator99.op:null); }

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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "adhocSource"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:294:1: adhocSource : output= VAR '=' exp= arrayCreation ->;
    public final MeteorParser.adhocSource_return adhocSource() throws RecognitionException {
        MeteorParser.adhocSource_return retval = new MeteorParser.adhocSource_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token output=null;
        Token char_literal100=null;
        MeteorParser.arrayCreation_return exp =null;


        EvaluationExpression output_tree=null;
        EvaluationExpression char_literal100_tree=null;
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_51=new RewriteRuleTokenStream(adaptor,"token 51");
        RewriteRuleSubtreeStream stream_arrayCreation=new RewriteRuleSubtreeStream(adaptor,"rule arrayCreation");
        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:294:12: (output= VAR '=' exp= arrayCreation ->)
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:295:3: output= VAR '=' exp= arrayCreation
            {
            output=(Token)match(input,VAR,FOLLOW_VAR_in_adhocSource2176); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_VAR.add(output);


            char_literal100=(Token)match(input,51,FOLLOW_51_in_adhocSource2178); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_51.add(char_literal100);


            pushFollow(FOLLOW_arrayCreation_in_adhocSource2182);
            exp=arrayCreation();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_arrayCreation.add(exp.getTree());

            if ( state.backtracking==0 ) { 
              Source source = new Source((exp!=null?((EvaluationExpression)exp.tree):null));
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
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 299:3: ->
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "readOperator"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:302:1: readOperator returns [Source source] : (output= VAR '=' )? 'read' ( (packageName= ID ':' )? format= ID )? {...}? ID pathExp= ternaryExpression ( confOption[getOperatorInfo($source), $source] )* ->;
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
        MeteorParser.ternaryExpression_return pathExp =null;

        MeteorParser.confOption_return confOption105 =null;


        EvaluationExpression output_tree=null;
        EvaluationExpression packageName_tree=null;
        EvaluationExpression format_tree=null;
        EvaluationExpression char_literal101_tree=null;
        EvaluationExpression string_literal102_tree=null;
        EvaluationExpression char_literal103_tree=null;
        EvaluationExpression ID104_tree=null;
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_51=new RewriteRuleTokenStream(adaptor,"token 51");
        RewriteRuleTokenStream stream_61=new RewriteRuleTokenStream(adaptor,"token 61");
        RewriteRuleSubtreeStream stream_confOption=new RewriteRuleSubtreeStream(adaptor,"rule confOption");
        RewriteRuleSubtreeStream stream_ternaryExpression=new RewriteRuleSubtreeStream(adaptor,"rule ternaryExpression");
         
          ConfObjectInfo<? extends SopremoFormat> formatInfo = null;
          SopremoFormat fileFormat = null;
          String path = null;

        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:308:2: ( (output= VAR '=' )? 'read' ( (packageName= ID ':' )? format= ID )? {...}? ID pathExp= ternaryExpression ( confOption[getOperatorInfo($source), $source] )* ->)
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:308:4: (output= VAR '=' )? 'read' ( (packageName= ID ':' )? format= ID )? {...}? ID pathExp= ternaryExpression ( confOption[getOperatorInfo($source), $source] )*
            {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:308:4: (output= VAR '=' )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==VAR) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:308:5: output= VAR '='
                    {
                    output=(Token)match(input,VAR,FOLLOW_VAR_in_readOperator2210); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_VAR.add(output);


                    char_literal101=(Token)match(input,51,FOLLOW_51_in_readOperator2212); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_51.add(char_literal101);


                    }
                    break;

            }


            string_literal102=(Token)match(input,61,FOLLOW_61_in_readOperator2220); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_61.add(string_literal102);


            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:309:11: ( (packageName= ID ':' )? format= ID )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==ID) ) {
                int LA57_1 = input.LA(2);

                if ( (!(((input.LT(1).getText().equals("from"))))) ) {
                    alt57=1;
                }
            }
            switch (alt57) {
                case 1 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:309:12: (packageName= ID ':' )? format= ID
                    {
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:309:12: (packageName= ID ':' )?
                    int alt56=2;
                    int LA56_0 = input.LA(1);

                    if ( (LA56_0==ID) ) {
                        int LA56_1 = input.LA(2);

                        if ( (LA56_1==47) ) {
                            alt56=1;
                        }
                    }
                    switch (alt56) {
                        case 1 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:309:13: packageName= ID ':'
                            {
                            packageName=(Token)match(input,ID,FOLLOW_ID_in_readOperator2226); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_ID.add(packageName);


                            char_literal103=(Token)match(input,47,FOLLOW_47_in_readOperator2228); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_47.add(char_literal103);


                            }
                            break;

                    }


                    format=(Token)match(input,ID,FOLLOW_ID_in_readOperator2233); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(format);


                    }
                    break;

            }


            if ( !((input.LT(1).getText().equals("from"))) ) {
                if (state.backtracking>0) {state.failed=true; return retval;}
                throw new FailedPredicateException(input, "readOperator", "input.LT(1).getText().equals(\"from\")");
            }

            ID104=(Token)match(input,ID,FOLLOW_ID_in_readOperator2242); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID104);


            pushFollow(FOLLOW_ternaryExpression_in_readOperator2246);
            pathExp=ternaryExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_ternaryExpression.add(pathExp.getTree());

            if ( state.backtracking==0 ) { 
              path = makeFilePath((pathExp!=null?((EvaluationExpression)pathExp.tree):null));
              formatInfo = findFormat((packageName!=null?packageName.getText():null), format, path);
              fileFormat = formatInfo.newInstance(); 
              retval.source = new Source(fileFormat, path); 
              if(output != null)
                putVariable(output, new JsonStreamExpression(retval.source));
            }

            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:318:4: ( confOption[getOperatorInfo($source), $source] )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==ID) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:318:4: confOption[getOperatorInfo($source), $source]
            	    {
            	    pushFollow(FOLLOW_confOption_in_readOperator2251);
            	    confOption105=confOption(getOperatorInfo(retval.source), retval.source);

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_confOption.add(confOption105.getTree());

            	    }
            	    break;

            	default :
            	    break loop58;
                }
            } while (true);


            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 319:2: ->
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "writeOperator"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:322:1: writeOperator returns [Sink sink] : 'write' ( (packageName= ID ':' )? format= ID )? from= VAR {...}? ID pathExp= ternaryExpression ( confOption[getOperatorInfo($sink), $sink] )* ->;
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
        MeteorParser.ternaryExpression_return pathExp =null;

        MeteorParser.confOption_return confOption109 =null;


        EvaluationExpression packageName_tree=null;
        EvaluationExpression format_tree=null;
        EvaluationExpression from_tree=null;
        EvaluationExpression string_literal106_tree=null;
        EvaluationExpression char_literal107_tree=null;
        EvaluationExpression ID108_tree=null;
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_confOption=new RewriteRuleSubtreeStream(adaptor,"rule confOption");
        RewriteRuleSubtreeStream stream_ternaryExpression=new RewriteRuleSubtreeStream(adaptor,"rule ternaryExpression");
         
          ConfObjectInfo<? extends SopremoFormat> formatInfo = null;
          SopremoFormat fileFormat = null;
          String path = null;

        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:327:3: ( 'write' ( (packageName= ID ':' )? format= ID )? from= VAR {...}? ID pathExp= ternaryExpression ( confOption[getOperatorInfo($sink), $sink] )* ->)
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:327:5: 'write' ( (packageName= ID ':' )? format= ID )? from= VAR {...}? ID pathExp= ternaryExpression ( confOption[getOperatorInfo($sink), $sink] )*
            {
            string_literal106=(Token)match(input,64,FOLLOW_64_in_writeOperator2275); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_64.add(string_literal106);


            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:328:5: ( (packageName= ID ':' )? format= ID )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==ID) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:328:6: (packageName= ID ':' )? format= ID
                    {
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:328:6: (packageName= ID ':' )?
                    int alt59=2;
                    int LA59_0 = input.LA(1);

                    if ( (LA59_0==ID) ) {
                        int LA59_1 = input.LA(2);

                        if ( (LA59_1==47) ) {
                            alt59=1;
                        }
                    }
                    switch (alt59) {
                        case 1 :
                            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:328:7: packageName= ID ':'
                            {
                            packageName=(Token)match(input,ID,FOLLOW_ID_in_writeOperator2286); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_ID.add(packageName);


                            char_literal107=(Token)match(input,47,FOLLOW_47_in_writeOperator2288); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_47.add(char_literal107);


                            }
                            break;

                    }


                    format=(Token)match(input,ID,FOLLOW_ID_in_writeOperator2293); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(format);


                    }
                    break;

            }


            from=(Token)match(input,VAR,FOLLOW_VAR_in_writeOperator2299); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_VAR.add(from);


            if ( !((input.LT(1).getText().equals("to"))) ) {
                if (state.backtracking>0) {state.failed=true; return retval;}
                throw new FailedPredicateException(input, "writeOperator", "input.LT(1).getText().equals(\"to\")");
            }

            ID108=(Token)match(input,ID,FOLLOW_ID_in_writeOperator2307); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(ID108);


            pushFollow(FOLLOW_ternaryExpression_in_writeOperator2311);
            pathExp=ternaryExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_ternaryExpression.add(pathExp.getTree());

            if ( state.backtracking==0 ) { 
              path = makeFilePath((pathExp!=null?((EvaluationExpression)pathExp.tree):null));
              formatInfo = findFormat((packageName!=null?packageName.getText():null), format, path);
              fileFormat = formatInfo.newInstance();
            	retval.sink = new Sink(fileFormat, path);
              retval.sink.setInputs(getVariableSafely(from).getStream());
              this.sinks.add(retval.sink);
            }

            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:337:3: ( confOption[getOperatorInfo($sink), $sink] )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==ID) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:337:3: confOption[getOperatorInfo($sink), $sink]
            	    {
            	    pushFollow(FOLLOW_confOption_in_writeOperator2315);
            	    confOption109=confOption(getOperatorInfo(retval.sink), retval.sink);

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_confOption.add(confOption109.getTree());

            	    }
            	    break;

            	default :
            	    break loop61;
                }
            } while (true);


            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 337:46: ->
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "genericOperator"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:343:1: genericOperator returns [Operator<?> op] : (targets+= VAR ( ',' targets+= VAR )* '=' )? (packageName= ID ':' )? name= ID {...}? => ( ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )* )? ( confOption[operatorInfo, $op] )* ->;
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
        List list_targets=null;
        MeteorParser.input_return input113 =null;

        MeteorParser.input_return input115 =null;

        MeteorParser.confOption_return confOption116 =null;


        EvaluationExpression packageName_tree=null;
        EvaluationExpression name_tree=null;
        EvaluationExpression char_literal110_tree=null;
        EvaluationExpression char_literal111_tree=null;
        EvaluationExpression char_literal112_tree=null;
        EvaluationExpression char_literal114_tree=null;
        EvaluationExpression targets_tree=null;
        RewriteRuleTokenStream stream_43=new RewriteRuleTokenStream(adaptor,"token 43");
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_51=new RewriteRuleTokenStream(adaptor,"token 51");
        RewriteRuleSubtreeStream stream_input=new RewriteRuleSubtreeStream(adaptor,"rule input");
        RewriteRuleSubtreeStream stream_confOption=new RewriteRuleSubtreeStream(adaptor,"rule confOption");
         
          ConfObjectInfo<? extends Operator<?>> operatorInfo;

        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:349:3: ( (targets+= VAR ( ',' targets+= VAR )* '=' )? (packageName= ID ':' )? name= ID {...}? => ( ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )* )? ( confOption[operatorInfo, $op] )* ->)
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:350:2: (targets+= VAR ( ',' targets+= VAR )* '=' )? (packageName= ID ':' )? name= ID {...}? => ( ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )* )? ( confOption[operatorInfo, $op] )*
            {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:350:2: (targets+= VAR ( ',' targets+= VAR )* '=' )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==VAR) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:350:2: targets+= VAR ( ',' targets+= VAR )* '='
                    {
                    targets=(Token)match(input,VAR,FOLLOW_VAR_in_genericOperator2349); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_VAR.add(targets);

                    if (list_targets==null) list_targets=new ArrayList();
                    list_targets.add(targets);


                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:350:15: ( ',' targets+= VAR )*
                    loop62:
                    do {
                        int alt62=2;
                        int LA62_0 = input.LA(1);

                        if ( (LA62_0==43) ) {
                            alt62=1;
                        }


                        switch (alt62) {
                    	case 1 :
                    	    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:350:16: ',' targets+= VAR
                    	    {
                    	    char_literal110=(Token)match(input,43,FOLLOW_43_in_genericOperator2352); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_43.add(char_literal110);


                    	    targets=(Token)match(input,VAR,FOLLOW_VAR_in_genericOperator2356); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_VAR.add(targets);

                    	    if (list_targets==null) list_targets=new ArrayList();
                    	    list_targets.add(targets);


                    	    }
                    	    break;

                    	default :
                    	    break loop62;
                        }
                    } while (true);


                    char_literal111=(Token)match(input,51,FOLLOW_51_in_genericOperator2360); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_51.add(char_literal111);


                    }
                    break;

            }


            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:351:2: (packageName= ID ':' )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==ID) ) {
                int LA64_1 = input.LA(2);

                if ( (LA64_1==47) ) {
                    alt64=1;
                }
            }
            switch (alt64) {
                case 1 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:351:2: packageName= ID ':'
                    {
                    packageName=(Token)match(input,ID,FOLLOW_ID_in_genericOperator2368); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(packageName);


                    char_literal112=(Token)match(input,47,FOLLOW_47_in_genericOperator2370); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_47.add(char_literal112);


                    }
                    break;

            }


            name=(Token)match(input,ID,FOLLOW_ID_in_genericOperator2376); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(name);


            if ( !(( (operatorInfo = findOperatorGreedily((packageName!=null?packageName.getText():null), name)) != null  )) ) {
                if (state.backtracking>0) {state.failed=true; return retval;}
                throw new FailedPredicateException(input, "genericOperator", " (operatorInfo = findOperatorGreedily($packageName.text, $name)) != null  ");
            }

            if ( state.backtracking==0 ) { 
              ((operator_scope)operator_stack.peek()).result = retval.op = operatorInfo.newInstance(); 
              // add scope for input variables and recursive definition
              if(state.backtracking == 0) 
                addScope();   
            }

            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:359:2: ( ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )* )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==VAR) && (synpred22_Meteor())) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:359:2: ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )*
                    {
                    pushFollow(FOLLOW_input_in_genericOperator2392);
                    input113=input(operatorInfo, retval.op);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_input.add(input113.getTree());

                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:359:35: ( ( ',' )=> ',' input[operatorInfo, $op] )*
                    loop65:
                    do {
                        int alt65=2;
                        int LA65_0 = input.LA(1);

                        if ( (LA65_0==43) ) {
                            int LA65_2 = input.LA(2);

                            if ( (LA65_2==VAR) ) {
                                int LA65_3 = input.LA(3);

                                if ( (synpred23_Meteor()) ) {
                                    alt65=1;
                                }


                            }


                        }


                        switch (alt65) {
                    	case 1 :
                    	    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:359:36: ( ',' )=> ',' input[operatorInfo, $op]
                    	    {
                    	    char_literal114=(Token)match(input,43,FOLLOW_43_in_genericOperator2401); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_43.add(char_literal114);


                    	    pushFollow(FOLLOW_input_in_genericOperator2403);
                    	    input115=input(operatorInfo, retval.op);

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_input.add(input115.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop65;
                        }
                    } while (true);


                    }
                    break;

            }


            if ( state.backtracking==0 ) { // register output names for explicit references to output 
              if(list_targets != null)
                for(int index = 0; index < list_targets.size(); index++)
                  putVariable((Token) list_targets.get(index), new JsonStreamExpression(retval.op.getOutput(index)), 1);   
            }

            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:365:11: ( confOption[operatorInfo, $op] )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==ID) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:365:11: confOption[operatorInfo, $op]
            	    {
            	    pushFollow(FOLLOW_confOption_in_genericOperator2413);
            	    confOption116=confOption(operatorInfo, retval.op);

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_confOption.add(confOption116.getTree());

            	    }
            	    break;

            	default :
            	    break loop67;
                }
            } while (true);


            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 366:3: ->
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "confOption"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:368:1: confOption[ConfObjectInfo<?> info, ConfigurableSopremoType object] : name= ID {...}? =>expr= ternaryExpression ->;
    public final MeteorParser.confOption_return confOption(ConfObjectInfo<?> info, ConfigurableSopremoType object) throws RecognitionException {
        MeteorParser.confOption_return retval = new MeteorParser.confOption_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token name=null;
        MeteorParser.ternaryExpression_return expr =null;


        EvaluationExpression name_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_ternaryExpression=new RewriteRuleSubtreeStream(adaptor,"rule ternaryExpression");

         ConfObjectInfo.ConfObjectPropertyInfo property = null;

        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:371:3: (name= ID {...}? =>expr= ternaryExpression ->)
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:372:3: name= ID {...}? =>expr= ternaryExpression
            {
            name=(Token)match(input,ID,FOLLOW_ID_in_confOption2440); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(name);


            if ( !(( (property = findPropertyGreedily(object, info, name)) != null )) ) {
                if (state.backtracking>0) {state.failed=true; return retval;}
                throw new FailedPredicateException(input, "confOption", " (property = findPropertyGreedily(object, info, name)) != null ");
            }

            pushFollow(FOLLOW_ternaryExpression_in_confOption2450);
            expr=ternaryExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_ternaryExpression.add(expr.getTree());

            if ( state.backtracking==0 ) { property.setValue(object, (expr!=null?((EvaluationExpression)expr.tree):null)); }

            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {

            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 374:69: ->
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
        public Object getTree() { return tree; }
    };


    // $ANTLR start "input"
    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:376:1: input[ConfObjectInfo<?> info, Operator<?> object] : (name= VAR IN )? from= VAR ({...}? =>expr= ternaryExpression )? ->;
    public final MeteorParser.input_return input(ConfObjectInfo<?> info, Operator<?> object) throws RecognitionException {
        MeteorParser.input_return retval = new MeteorParser.input_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token name=null;
        Token from=null;
        Token IN117=null;
        MeteorParser.ternaryExpression_return expr =null;


        EvaluationExpression name_tree=null;
        EvaluationExpression from_tree=null;
        EvaluationExpression IN117_tree=null;
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_IN=new RewriteRuleTokenStream(adaptor,"token IN");
        RewriteRuleSubtreeStream stream_ternaryExpression=new RewriteRuleSubtreeStream(adaptor,"rule ternaryExpression");

         ConfObjectInfo.ConfObjectIndexedPropertyInfo inputProperty = null;

        try {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:379:3: ( (name= VAR IN )? from= VAR ({...}? =>expr= ternaryExpression )? ->)
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:379:5: (name= VAR IN )? from= VAR ({...}? =>expr= ternaryExpression )?
            {
            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:379:5: (name= VAR IN )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==VAR) ) {
                int LA68_1 = input.LA(2);

                if ( (LA68_1==IN) ) {
                    alt68=1;
                }
            }
            switch (alt68) {
                case 1 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:379:6: name= VAR IN
                    {
                    name=(Token)match(input,VAR,FOLLOW_VAR_in_input2472); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_VAR.add(name);


                    IN117=(Token)match(input,IN,FOLLOW_IN_in_input2474); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_IN.add(IN117);


                    }
                    break;

            }


            from=(Token)match(input,VAR,FOLLOW_VAR_in_input2480); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_VAR.add(from);


            if ( state.backtracking==0 ) { 
              int inputIndex = ((operator_scope)operator_stack.peek()).numInputs++;
              JsonStreamExpression input = getVariableSafely(from);
              object.setInput(inputIndex, input.getStream());
              
              if(operator_stack.size() == 1) {
            	  JsonStreamExpression inputExpression = new JsonStreamExpression(input.getStream(), inputIndex);
            	  putVariable(name != null ? name : from, inputExpression);
              }
            }

            // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:390:2: ({...}? =>expr= ternaryExpression )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==DECIMAL||LA69_0==FN||LA69_0==INTEGER||(LA69_0 >= STRING && LA69_0 <= UINT)||LA69_0==VAR||LA69_0==35||(LA69_0 >= 38 && LA69_0 <= 39)||LA69_0==42||LA69_0==45||LA69_0==57||(LA69_0 >= 59 && LA69_0 <= 60)||LA69_0==62||LA69_0==65||LA69_0==68) && (( (findInputPropertyRelunctantly(object, info, input.LT(1), false) != null) ))) {
                alt69=1;
            }
            else if ( (LA69_0==ID) ) {
                int LA69_5 = input.LA(2);

                if ( (( (findInputPropertyRelunctantly(object, info, input.LT(1), false) != null) )) ) {
                    alt69=1;
                }
            }
            switch (alt69) {
                case 1 :
                    // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:390:2: {...}? =>expr= ternaryExpression
                    {
                    if ( !(( (findInputPropertyRelunctantly(object, info, input.LT(1), false) != null) )) ) {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        throw new FailedPredicateException(input, "input", " (findInputPropertyRelunctantly(object, info, input.LT(1), false) != null) ");
                    }

                    if ( state.backtracking==0 ) { inputProperty = findInputPropertyRelunctantly(object, info, input.LT(1), true); }

                    pushFollow(FOLLOW_ternaryExpression_in_input2497);
                    expr=ternaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_ternaryExpression.add(expr.getTree());

                    if ( state.backtracking==0 ) { inputProperty.setValue(object, ((operator_scope)operator_stack.peek()).numInputs-1, (expr!=null?((EvaluationExpression)expr.tree):null)); }

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
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 393:4: ->
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
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:73:5: ( ID '=' FN )
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:73:6: ID '=' FN
        {
        match(input,ID,FOLLOW_ID_in_synpred1_Meteor210); if (state.failed) return ;

        match(input,51,FOLLOW_51_in_synpred1_Meteor212); if (state.failed) return ;

        match(input,FN,FOLLOW_FN_in_synpred1_Meteor214); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred1_Meteor

    // $ANTLR start synpred2_Meteor
    public final void synpred2_Meteor_fragment() throws RecognitionException {
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:74:5: ( ID '=' JAVAUDF )
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:74:6: ID '=' JAVAUDF
        {
        match(input,ID,FOLLOW_ID_in_synpred2_Meteor226); if (state.failed) return ;

        match(input,51,FOLLOW_51_in_synpred2_Meteor228); if (state.failed) return ;

        match(input,JAVAUDF,FOLLOW_JAVAUDF_in_synpred2_Meteor230); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred2_Meteor

    // $ANTLR start synpred3_Meteor
    public final void synpred3_Meteor_fragment() throws RecognitionException {
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:110:5: ( ID ( ID | VAR ) )
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:110:6: ID ( ID | VAR )
        {
        match(input,ID,FOLLOW_ID_in_synpred3_Meteor429); if (state.failed) return ;

        if ( input.LA(1)==ID||input.LA(1)==VAR ) {
            input.consume();
            state.errorRecovery=false;
            state.failed=false;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            MismatchedSetException mse = new MismatchedSetException(null,input);
            throw mse;
        }


        }

    }
    // $ANTLR end synpred3_Meteor

    // $ANTLR start synpred4_Meteor
    public final void synpred4_Meteor_fragment() throws RecognitionException {
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:114:4: ( orExpression '?' )
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:114:5: orExpression '?'
        {
        pushFollow(FOLLOW_orExpression_in_synpred4_Meteor457);
        orExpression();

        state._fsp--;
        if (state.failed) return ;

        match(input,55,FOLLOW_55_in_synpred4_Meteor459); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred4_Meteor

    // $ANTLR start synpred5_Meteor
    public final void synpred5_Meteor_fragment() throws RecognitionException {
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:116:4: ( orExpression IF )
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:116:5: orExpression IF
        {
        pushFollow(FOLLOW_orExpression_in_synpred5_Meteor499);
        orExpression();

        state._fsp--;
        if (state.failed) return ;

        match(input,IF,FOLLOW_IF_in_synpred5_Meteor501); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred5_Meteor

    // $ANTLR start synpred6_Meteor
    public final void synpred6_Meteor_fragment() throws RecognitionException {
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:168:4: ( '(' ID ')' )
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:168:5: '(' ID ')'
        {
        match(input,39,FOLLOW_39_in_synpred6_Meteor970); if (state.failed) return ;

        match(input,ID,FOLLOW_ID_in_synpred6_Meteor972); if (state.failed) return ;

        match(input,40,FOLLOW_40_in_synpred6_Meteor974); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred6_Meteor

    // $ANTLR start synpred7_Meteor
    public final void synpred7_Meteor_fragment() throws RecognitionException {
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:175:5: ( pathExpression[EvaluationExpression.VALUE] )
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:175:6: pathExpression[EvaluationExpression.VALUE]
        {
        pushFollow(FOLLOW_pathExpression_in_synpred7_Meteor1037);
        pathExpression(EvaluationExpression.VALUE);

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred7_Meteor

    // $ANTLR start synpred8_Meteor
    public final void synpred8_Meteor_fragment() throws RecognitionException {
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:183:5: ( '?.' ID '(' )
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:183:6: '?.' ID '('
        {
        match(input,56,FOLLOW_56_in_synpred8_Meteor1093); if (state.failed) return ;

        match(input,ID,FOLLOW_ID_in_synpred8_Meteor1095); if (state.failed) return ;

        match(input,39,FOLLOW_39_in_synpred8_Meteor1097); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred8_Meteor

    // $ANTLR start synpred9_Meteor
    public final void synpred9_Meteor_fragment() throws RecognitionException {
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:184:8: ( pathSegment )
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:184:9: pathSegment
        {
        pushFollow(FOLLOW_pathSegment_in_synpred9_Meteor1116);
        pathSegment();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred9_Meteor

    // $ANTLR start synpred10_Meteor
    public final void synpred10_Meteor_fragment() throws RecognitionException {
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:187:5: ( '.' ID '(' )
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:187:6: '.' ID '('
        {
        match(input,46,FOLLOW_46_in_synpred10_Meteor1164); if (state.failed) return ;

        match(input,ID,FOLLOW_ID_in_synpred10_Meteor1166); if (state.failed) return ;

        match(input,39,FOLLOW_39_in_synpred10_Meteor1168); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred10_Meteor

    // $ANTLR start synpred11_Meteor
    public final void synpred11_Meteor_fragment() throws RecognitionException {
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:188:8: ( pathSegment )
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:188:9: pathSegment
        {
        pushFollow(FOLLOW_pathSegment_in_synpred11_Meteor1187);
        pathSegment();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred11_Meteor

    // $ANTLR start synpred12_Meteor
    public final void synpred12_Meteor_fragment() throws RecognitionException {
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:191:6: ( pathSegment )
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:191:7: pathSegment
        {
        pushFollow(FOLLOW_pathSegment_in_synpred12_Meteor1229);
        pathSegment();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred12_Meteor

    // $ANTLR start synpred13_Meteor
    public final void synpred13_Meteor_fragment() throws RecognitionException {
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:198:5: ( '?.' )
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:198:6: '?.'
        {
        match(input,56,FOLLOW_56_in_synpred13_Meteor1283); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred13_Meteor

    // $ANTLR start synpred14_Meteor
    public final void synpred14_Meteor_fragment() throws RecognitionException {
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:199:5: ( '.' )
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:199:6: '.'
        {
        match(input,46,FOLLOW_46_in_synpred14_Meteor1314); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred14_Meteor

    // $ANTLR start synpred15_Meteor
    public final void synpred15_Meteor_fragment() throws RecognitionException {
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:200:5: ( '[' )
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:200:6: '['
        {
        match(input,57,FOLLOW_57_in_synpred15_Meteor1343); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred15_Meteor

    // $ANTLR start synpred16_Meteor
    public final void synpred16_Meteor_fragment() throws RecognitionException {
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:203:19: ( '.' methodCall[null] )
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:203:20: '.' methodCall[null]
        {
        match(input,46,FOLLOW_46_in_synpred16_Meteor1366); if (state.failed) return ;

        pushFollow(FOLLOW_methodCall_in_synpred16_Meteor1368);
        methodCall(null);

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred16_Meteor

    // $ANTLR start synpred17_Meteor
    public final void synpred17_Meteor_fragment() throws RecognitionException {
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:213:4: ( ID '(' )
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:213:5: ID '('
        {
        match(input,ID,FOLLOW_ID_in_synpred17_Meteor1507); if (state.failed) return ;

        match(input,39,FOLLOW_39_in_synpred17_Meteor1509); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred17_Meteor

    // $ANTLR start synpred18_Meteor
    public final void synpred18_Meteor_fragment() throws RecognitionException {
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:215:5: ( FN )
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:215:6: FN
        {
        match(input,FN,FOLLOW_FN_in_synpred18_Meteor1525); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred18_Meteor

    // $ANTLR start synpred19_Meteor
    public final void synpred19_Meteor_fragment() throws RecognitionException {
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:219:6: ( ID ':' )
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:219:7: ID ':'
        {
        match(input,ID,FOLLOW_ID_in_synpred19_Meteor1569); if (state.failed) return ;

        match(input,47,FOLLOW_47_in_synpred19_Meteor1571); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred19_Meteor

    // $ANTLR start synpred20_Meteor
    public final void synpred20_Meteor_fragment() throws RecognitionException {
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:243:10: ( ID ':' )
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:243:11: ID ':'
        {
        match(input,ID,FOLLOW_ID_in_synpred20_Meteor1751); if (state.failed) return ;

        match(input,47,FOLLOW_47_in_synpred20_Meteor1753); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred20_Meteor

    // $ANTLR start synpred21_Meteor
    public final void synpred21_Meteor_fragment() throws RecognitionException {
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:247:5: ( ID ':' )
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:247:6: ID ':'
        {
        match(input,ID,FOLLOW_ID_in_synpred21_Meteor1797); if (state.failed) return ;

        match(input,47,FOLLOW_47_in_synpred21_Meteor1799); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred21_Meteor

    // $ANTLR start synpred22_Meteor
    public final void synpred22_Meteor_fragment() throws RecognitionException {
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:359:2: ( VAR )
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:359:3: VAR
        {
        match(input,VAR,FOLLOW_VAR_in_synpred22_Meteor2388); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred22_Meteor

    // $ANTLR start synpred23_Meteor
    public final void synpred23_Meteor_fragment() throws RecognitionException {
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:359:36: ( ',' )
        // /home/arvid/workspace/sopremo/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:359:37: ','
        {
        match(input,43,FOLLOW_43_in_synpred23_Meteor2397); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred23_Meteor

    // Delegated rules

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


 

    public static final BitSet FOLLOW_statement_in_script121 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_script123 = new BitSet(new long[]{0xA000000200008002L,0x0000000000000001L});
    public static final BitSet FOLLOW_operator_in_statement137 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_packageImport_in_statement141 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_adhocSource_in_statement145 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_definition_in_statement149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functionCall_in_statement157 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_packageImport174 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_packageImport178 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_43_in_packageImport189 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_packageImport193 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_functionDefinition_in_definition218 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_javaudf_in_definition234 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constantDefinition_in_definition240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_functionDefinition252 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_functionDefinition254 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_inlineFunction_in_functionDefinition258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_constantDefinition277 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_constantDefinition279 = new BitSet(new long[]{0x5A0024CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_ternaryExpression_in_constantDefinition283 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FN_in_inlineFunction309 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_inlineFunction311 = new BitSet(new long[]{0x0000010000008000L});
    public static final BitSet FOLLOW_ID_in_inlineFunction320 = new BitSet(new long[]{0x0000090000000000L});
    public static final BitSet FOLLOW_43_in_inlineFunction327 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_inlineFunction331 = new BitSet(new long[]{0x0000090000000000L});
    public static final BitSet FOLLOW_40_in_inlineFunction342 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_inlineFunction352 = new BitSet(new long[]{0x7A0024CA6004A080L,0x0000000000000013L});
    public static final BitSet FOLLOW_expression_in_inlineFunction356 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_inlineFunction358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_javaudf378 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_javaudf380 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_JAVAUDF_in_javaudf382 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_javaudf384 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_STRING_in_javaudf388 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_javaudf390 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ternaryExpression_in_contextAwareExpression418 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operatorExpression_in_expression441 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ternaryExpression_in_expression447 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression465 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_ternaryExpression467 = new BitSet(new long[]{0x5A00A4CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression471 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_ternaryExpression474 = new BitSet(new long[]{0x5A0024CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression478 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression507 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_IF_in_ternaryExpression509 = new BitSet(new long[]{0x5A0024CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression513 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression536 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andExpression_in_orExpression549 = new BitSet(new long[]{0x0000000001000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_OR_in_orExpression553 = new BitSet(new long[]{0x5A0024CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_66_in_orExpression557 = new BitSet(new long[]{0x5A0024CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_andExpression_in_orExpression562 = new BitSet(new long[]{0x0000000001000002L,0x0000000000000004L});
    public static final BitSet FOLLOW_elementExpression_in_andExpression591 = new BitSet(new long[]{0x0000002000000012L});
    public static final BitSet FOLLOW_AND_in_andExpression595 = new BitSet(new long[]{0x5A0024CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_37_in_andExpression599 = new BitSet(new long[]{0x5A0024CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_elementExpression_in_andExpression604 = new BitSet(new long[]{0x0000002000000012L});
    public static final BitSet FOLLOW_comparisonExpression_in_elementExpression633 = new BitSet(new long[]{0x0000000000220002L});
    public static final BitSet FOLLOW_NOT_in_elementExpression638 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_IN_in_elementExpression641 = new BitSet(new long[]{0x5A0024CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_comparisonExpression_in_elementExpression645 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arithmeticExpression_in_comparisonExpression686 = new BitSet(new long[]{0x0076001000000002L});
    public static final BitSet FOLLOW_50_in_comparisonExpression692 = new BitSet(new long[]{0x5A0024CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_54_in_comparisonExpression698 = new BitSet(new long[]{0x5A0024CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_49_in_comparisonExpression704 = new BitSet(new long[]{0x5A0024CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_53_in_comparisonExpression710 = new BitSet(new long[]{0x5A0024CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_52_in_comparisonExpression716 = new BitSet(new long[]{0x5A0024CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_36_in_comparisonExpression722 = new BitSet(new long[]{0x5A0024CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_arithmeticExpression_in_comparisonExpression727 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicationExpression_in_arithmeticExpression807 = new BitSet(new long[]{0x0000120000000002L});
    public static final BitSet FOLLOW_41_in_arithmeticExpression813 = new BitSet(new long[]{0x5A0024CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_44_in_arithmeticExpression819 = new BitSet(new long[]{0x5A0024CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_multiplicationExpression_in_arithmeticExpression824 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_preincrementExpression_in_multiplicationExpression867 = new BitSet(new long[]{0x0000000018000002L});
    public static final BitSet FOLLOW_STAR_in_multiplicationExpression873 = new BitSet(new long[]{0x5A0024CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_SLASH_in_multiplicationExpression879 = new BitSet(new long[]{0x5A0024CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_preincrementExpression_in_multiplicationExpression884 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_42_in_preincrementExpression925 = new BitSet(new long[]{0x5A0024CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_preincrementExpression_in_preincrementExpression927 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_preincrementExpression932 = new BitSet(new long[]{0x5A0024CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_preincrementExpression_in_preincrementExpression934 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unaryExpression_in_preincrementExpression939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_castExpression_in_unaryExpression958 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_castExpression978 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_castExpression982 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_castExpression984 = new BitSet(new long[]{0x5A0000C26004A080L,0x0000000000000002L});
    public static final BitSet FOLLOW_generalPathExpression_in_castExpression988 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_generalPathExpression_in_castExpression1001 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_ID_in_castExpression1006 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_castExpression1010 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_valueExpression_in_generalPathExpression1029 = new BitSet(new long[]{0x0300400000000002L});
    public static final BitSet FOLLOW_pathExpression_in_generalPathExpression1044 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathExpression_in_contextAwarePathExpression1073 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_pathExpression1101 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_methodCall_in_pathExpression1105 = new BitSet(new long[]{0x0300400000000002L});
    public static final BitSet FOLLOW_pathExpression_in_pathExpression1122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_pathExpression1172 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_methodCall_in_pathExpression1176 = new BitSet(new long[]{0x0300400000000002L});
    public static final BitSet FOLLOW_pathExpression_in_pathExpression1193 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathSegment_in_pathExpression1219 = new BitSet(new long[]{0x0300400000000002L});
    public static final BitSet FOLLOW_pathExpression_in_pathExpression1235 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_pathSegment1287 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_pathSegment1291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_pathSegment1319 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_pathSegment1323 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayAccess_in_pathSegment1348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_arrayAccess1358 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_STAR_in_arrayAccess1360 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_arrayAccess1362 = new BitSet(new long[]{0x0300400000000000L});
    public static final BitSet FOLLOW_46_in_arrayAccess1373 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_methodCall_in_arrayAccess1377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathSegment_in_arrayAccess1400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_arrayAccess1421 = new BitSet(new long[]{0x0000000040040000L});
    public static final BitSet FOLLOW_INTEGER_in_arrayAccess1426 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_UINT_in_arrayAccess1432 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_arrayAccess1435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_arrayAccess1453 = new BitSet(new long[]{0x0000000040040000L});
    public static final BitSet FOLLOW_INTEGER_in_arrayAccess1458 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_UINT_in_arrayAccess1464 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_arrayAccess1467 = new BitSet(new long[]{0x0000000040040000L});
    public static final BitSet FOLLOW_INTEGER_in_arrayAccess1472 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_UINT_in_arrayAccess1478 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_arrayAccess1481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functionCall_in_valueExpression1513 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functionReference_in_valueExpression1518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inlineFunction_in_valueExpression1531 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parenthesesExpression_in_valueExpression1545 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_valueExpression1551 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_valueExpression1557 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_valueExpression1577 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_valueExpression1579 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_valueExpression1585 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayCreation_in_valueExpression1605 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objectCreation_in_valueExpression1611 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operator_in_operatorExpression1623 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_parenthesesExpression1642 = new BitSet(new long[]{0x7A0024CA6004A080L,0x0000000000000013L});
    public static final BitSet FOLLOW_expression_in_parenthesesExpression1644 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_parenthesesExpression1646 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_methodCall1676 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_methodCall1678 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_methodCall1684 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_methodCall1686 = new BitSet(new long[]{0x7A0025CA6004A080L,0x0000000000000013L});
    public static final BitSet FOLLOW_expression_in_methodCall1695 = new BitSet(new long[]{0x0000090000000000L});
    public static final BitSet FOLLOW_43_in_methodCall1704 = new BitSet(new long[]{0x7A0024CA6004A080L,0x0000000000000013L});
    public static final BitSet FOLLOW_expression_in_methodCall1709 = new BitSet(new long[]{0x0000090000000000L});
    public static final BitSet FOLLOW_40_in_methodCall1721 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_methodCall_in_functionCall1736 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_functionReference1747 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_functionReference1759 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_functionReference1761 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_functionReference1767 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_fieldAssignment1803 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_fieldAssignment1805 = new BitSet(new long[]{0x7A0024CA6004A080L,0x0000000000000013L});
    public static final BitSet FOLLOW_expression_in_fieldAssignment1807 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_fieldAssignment1824 = new BitSet(new long[]{0x0300400000000000L});
    public static final BitSet FOLLOW_46_in_fieldAssignment1833 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_STAR_in_fieldAssignment1835 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_contextAwarePathExpression_in_fieldAssignment1852 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_47_in_fieldAssignment1863 = new BitSet(new long[]{0x7A0024CA6004A080L,0x0000000000000013L});
    public static final BitSet FOLLOW_expression_in_fieldAssignment1867 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_objectCreation1932 = new BitSet(new long[]{0x0000000200008000L,0x0000000000000008L});
    public static final BitSet FOLLOW_fieldAssignment_in_objectCreation1935 = new BitSet(new long[]{0x0000080000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_43_in_objectCreation1938 = new BitSet(new long[]{0x0000000200008000L});
    public static final BitSet FOLLOW_fieldAssignment_in_objectCreation1940 = new BitSet(new long[]{0x0000080000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_43_in_objectCreation1944 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_objectCreation1949 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_literal1987 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_literal2003 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DECIMAL_in_literal2019 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal2035 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_UINT_in_literal2053 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_literal2059 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_literal2075 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_arrayCreation2099 = new BitSet(new long[]{0x7E0024CA6004A080L,0x0000000000000013L});
    public static final BitSet FOLLOW_expression_in_arrayCreation2104 = new BitSet(new long[]{0x0400080000000000L});
    public static final BitSet FOLLOW_43_in_arrayCreation2107 = new BitSet(new long[]{0x7A0024CA6004A080L,0x0000000000000013L});
    public static final BitSet FOLLOW_expression_in_arrayCreation2111 = new BitSet(new long[]{0x0400080000000000L});
    public static final BitSet FOLLOW_43_in_arrayCreation2115 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_arrayCreation2120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_readOperator_in_operator2146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_writeOperator_in_operator2154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_genericOperator_in_operator2162 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_adhocSource2176 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_adhocSource2178 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_arrayCreation_in_adhocSource2182 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_readOperator2210 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_readOperator2212 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_readOperator2220 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_readOperator2226 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_readOperator2228 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_readOperator2233 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_readOperator2242 = new BitSet(new long[]{0x5A0024CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_ternaryExpression_in_readOperator2246 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_confOption_in_readOperator2251 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_64_in_writeOperator2275 = new BitSet(new long[]{0x0000000200008000L});
    public static final BitSet FOLLOW_ID_in_writeOperator2286 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_writeOperator2288 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_writeOperator2293 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_VAR_in_writeOperator2299 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_writeOperator2307 = new BitSet(new long[]{0x5A0024CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_ternaryExpression_in_writeOperator2311 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_confOption_in_writeOperator2315 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_VAR_in_genericOperator2349 = new BitSet(new long[]{0x0008080000000000L});
    public static final BitSet FOLLOW_43_in_genericOperator2352 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_VAR_in_genericOperator2356 = new BitSet(new long[]{0x0008080000000000L});
    public static final BitSet FOLLOW_51_in_genericOperator2360 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_genericOperator2368 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_genericOperator2370 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_genericOperator2376 = new BitSet(new long[]{0x0000000200008002L});
    public static final BitSet FOLLOW_input_in_genericOperator2392 = new BitSet(new long[]{0x0000080000008002L});
    public static final BitSet FOLLOW_43_in_genericOperator2401 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_input_in_genericOperator2403 = new BitSet(new long[]{0x0000080000008002L});
    public static final BitSet FOLLOW_confOption_in_genericOperator2413 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_ID_in_confOption2440 = new BitSet(new long[]{0x5A0024CA6004A080L,0x0000000000000012L});
    public static final BitSet FOLLOW_ternaryExpression_in_confOption2450 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_input2472 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_IN_in_input2474 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_VAR_in_input2480 = new BitSet(new long[]{0x5A0024CA6004A082L,0x0000000000000012L});
    public static final BitSet FOLLOW_ternaryExpression_in_input2497 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred1_Meteor210 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_synpred1_Meteor212 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_FN_in_synpred1_Meteor214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred2_Meteor226 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_synpred2_Meteor228 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_JAVAUDF_in_synpred2_Meteor230 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred3_Meteor429 = new BitSet(new long[]{0x0000000200008000L});
    public static final BitSet FOLLOW_set_in_synpred3_Meteor431 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpression_in_synpred4_Meteor457 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_synpred4_Meteor459 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpression_in_synpred5_Meteor499 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_IF_in_synpred5_Meteor501 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_synpred6_Meteor970 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_synpred6_Meteor972 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_synpred6_Meteor974 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathExpression_in_synpred7_Meteor1037 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_synpred8_Meteor1093 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_synpred8_Meteor1095 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_synpred8_Meteor1097 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathSegment_in_synpred9_Meteor1116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_synpred10_Meteor1164 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ID_in_synpred10_Meteor1166 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_synpred10_Meteor1168 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathSegment_in_synpred11_Meteor1187 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathSegment_in_synpred12_Meteor1229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_synpred13_Meteor1283 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_synpred14_Meteor1314 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_synpred15_Meteor1343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_synpred16_Meteor1366 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_methodCall_in_synpred16_Meteor1368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred17_Meteor1507 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_39_in_synpred17_Meteor1509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FN_in_synpred18_Meteor1525 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred19_Meteor1569 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_synpred19_Meteor1571 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred20_Meteor1751 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_synpred20_Meteor1753 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred21_Meteor1797 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_synpred21_Meteor1799 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred22_Meteor2388 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_synpred23_Meteor2397 = new BitSet(new long[]{0x0000000000000002L});

}