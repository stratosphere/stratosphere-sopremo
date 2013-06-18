// $ANTLR 3.4 /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g 2013-06-17 11:58:36
 
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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "AND", "APOSTROPHE", "AS", "COMMENT", "DECIMAL", "DIGIT", "ELSE", "ESC_SEQ", "EXPONENT", "EXPRESSION", "FN", "HEX_DIGIT", "ID", "IF", "IN", "INTEGER", "JAVAUDF", "LOWER_LETTER", "NOT", "OCTAL_ESC", "OPERATOR", "OR", "QUOTATION", "SIGN", "SLASH", "STAR", "STRING", "UINT", "UNICODE_ESC", "UPPER_LETTER", "VAR", "WS", "'!'", "'!='", "'&&'", "'&'", "'('", "')'", "'+'", "'++'", "','", "'-'", "'--'", "'.'", "':'", "';'", "'<'", "'<='", "'='", "'=='", "'>'", "'>='", "'?'", "'?.'", "'['", "']'", "'false'", "'from'", "'null'", "'read'", "'to'", "'true'", "'using'", "'write'", "'{'", "'||'", "'}'", "'~'"
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
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int AND=4;
    public static final int APOSTROPHE=5;
    public static final int AS=6;
    public static final int COMMENT=7;
    public static final int DECIMAL=8;
    public static final int DIGIT=9;
    public static final int ELSE=10;
    public static final int ESC_SEQ=11;
    public static final int EXPONENT=12;
    public static final int EXPRESSION=13;
    public static final int FN=14;
    public static final int HEX_DIGIT=15;
    public static final int ID=16;
    public static final int IF=17;
    public static final int IN=18;
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
    public String[] getTokenNames() { return MeteorParser.tokenNames; }
    public String getGrammarFileName() { return "/home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g"; }


      private Stack<String> paraphrase = new Stack<String>();

      private boolean setInnerOutput(Token VAR, Operator<?> op) {
    	  JsonStreamExpression output = new JsonStreamExpression(op.getOutput(((objectCreation_scope)objectCreation_stack.peek()).mappings.size()));
    	  ((objectCreation_scope)objectCreation_stack.peek()).mappings.add(new ObjectCreation.TagMapping(output, new JsonStreamExpression(op)));
    	  getVariableRegistry().getRegistry(1).put(VAR.getText(), output);
    	  return true;
    	}
      
      protected EvaluationExpression getInputSelection(Token inputVar) {
          return getVariable(inputVar).toInputSelection(((operator_scope)operator_stack.peek()).result);
      }

      public void parseSinks() throws RecognitionException {
        script();
      }


    public static class script_return extends ParserRuleReturnScope {
        EvaluationExpression tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "script"
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:60:1: script : ( statement ';' )+ ->;
    public final MeteorParser.script_return script() throws RecognitionException {
        MeteorParser.script_return retval = new MeteorParser.script_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token char_literal2=null;
        MeteorParser.statement_return statement1 =null;


        EvaluationExpression char_literal2_tree=null;
        RewriteRuleTokenStream stream_49=new RewriteRuleTokenStream(adaptor,"token 49");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:61:2: ( ( statement ';' )+ ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:61:5: ( statement ';' )+
            {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:61:5: ( statement ';' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==ID||LA1_0==VAR||LA1_0==63||(LA1_0 >= 66 && LA1_0 <= 67)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:61:6: statement ';'
            	    {
            	    pushFollow(FOLLOW_statement_in_script121);
            	    statement1=statement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_statement.add(statement1.getTree());

            	    char_literal2=(Token)match(input,49,FOLLOW_49_in_script123); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_49.add(char_literal2);


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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:63:1: statement : ( assignment | operator | packageImport | functionDefinition | javaudf |m= functionCall ) ->;
    public final MeteorParser.statement_return statement() throws RecognitionException {
        MeteorParser.statement_return retval = new MeteorParser.statement_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.functionCall_return m =null;

        MeteorParser.assignment_return assignment3 =null;

        MeteorParser.operator_return operator4 =null;

        MeteorParser.packageImport_return packageImport5 =null;

        MeteorParser.functionDefinition_return functionDefinition6 =null;

        MeteorParser.javaudf_return javaudf7 =null;


        RewriteRuleSubtreeStream stream_assignment=new RewriteRuleSubtreeStream(adaptor,"rule assignment");
        RewriteRuleSubtreeStream stream_functionCall=new RewriteRuleSubtreeStream(adaptor,"rule functionCall");
        RewriteRuleSubtreeStream stream_functionDefinition=new RewriteRuleSubtreeStream(adaptor,"rule functionDefinition");
        RewriteRuleSubtreeStream stream_javaudf=new RewriteRuleSubtreeStream(adaptor,"rule javaudf");
        RewriteRuleSubtreeStream stream_operator=new RewriteRuleSubtreeStream(adaptor,"rule operator");
        RewriteRuleSubtreeStream stream_packageImport=new RewriteRuleSubtreeStream(adaptor,"rule packageImport");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:64:2: ( ( assignment | operator | packageImport | functionDefinition | javaudf |m= functionCall ) ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:64:4: ( assignment | operator | packageImport | functionDefinition | javaudf |m= functionCall )
            {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:64:4: ( assignment | operator | packageImport | functionDefinition | javaudf |m= functionCall )
            int alt2=6;
            switch ( input.LA(1) ) {
            case VAR:
                {
                alt2=1;
                }
                break;
            case 63:
            case 67:
                {
                alt2=2;
                }
                break;
            case ID:
                {
                switch ( input.LA(2) ) {
                case 48:
                    {
                    int LA2_5 = input.LA(3);

                    if ( (LA2_5==ID) ) {
                        int LA2_8 = input.LA(4);

                        if ( (LA2_8==40) ) {
                            alt2=6;
                        }
                        else if ( (LA2_8==ID||LA2_8==VAR||LA2_8==58) ) {
                            alt2=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 2, 8, input);

                            throw nvae;

                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 2, 5, input);

                        throw nvae;

                    }
                    }
                    break;
                case 52:
                    {
                    int LA2_6 = input.LA(3);

                    if ( (LA2_6==JAVAUDF) ) {
                        alt2=5;
                    }
                    else if ( (LA2_6==FN) ) {
                        alt2=4;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 2, 6, input);

                        throw nvae;

                    }
                    }
                    break;
                case 40:
                    {
                    alt2=6;
                    }
                    break;
                case ID:
                case VAR:
                case 58:
                    {
                    alt2=2;
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
            case 66:
                {
                alt2=3;
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:64:5: assignment
                    {
                    pushFollow(FOLLOW_assignment_in_statement137);
                    assignment3=assignment();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_assignment.add(assignment3.getTree());

                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:64:18: operator
                    {
                    pushFollow(FOLLOW_operator_in_statement141);
                    operator4=operator();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_operator.add(operator4.getTree());

                    }
                    break;
                case 3 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:64:29: packageImport
                    {
                    pushFollow(FOLLOW_packageImport_in_statement145);
                    packageImport5=packageImport();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_packageImport.add(packageImport5.getTree());

                    }
                    break;
                case 4 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:64:45: functionDefinition
                    {
                    pushFollow(FOLLOW_functionDefinition_in_statement149);
                    functionDefinition6=functionDefinition();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_functionDefinition.add(functionDefinition6.getTree());

                    }
                    break;
                case 5 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:64:66: javaudf
                    {
                    pushFollow(FOLLOW_javaudf_in_statement153);
                    javaudf7=javaudf();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_javaudf.add(javaudf7.getTree());

                    }
                    break;
                case 6 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:66:4: m= functionCall
                    {
                    pushFollow(FOLLOW_functionCall_in_statement161);
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:68:1: packageImport : 'using' packageName= ID ( ',' additionalPackage= ID )* ->;
    public final MeteorParser.packageImport_return packageImport() throws RecognitionException {
        MeteorParser.packageImport_return retval = new MeteorParser.packageImport_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token packageName=null;
        Token additionalPackage=null;
        Token string_literal8=null;
        Token char_literal9=null;

        EvaluationExpression packageName_tree=null;
        EvaluationExpression additionalPackage_tree=null;
        EvaluationExpression string_literal8_tree=null;
        EvaluationExpression char_literal9_tree=null;
        RewriteRuleTokenStream stream_66=new RewriteRuleTokenStream(adaptor,"token 66");
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:69:3: ( 'using' packageName= ID ( ',' additionalPackage= ID )* ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:69:6: 'using' packageName= ID ( ',' additionalPackage= ID )*
            {
            string_literal8=(Token)match(input,66,FOLLOW_66_in_packageImport178); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_66.add(string_literal8);


            packageName=(Token)match(input,ID,FOLLOW_ID_in_packageImport182); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(packageName);


            if ( state.backtracking==0 ) { getPackageManager().importPackage((packageName!=null?packageName.getText():null)); }

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:70:6: ( ',' additionalPackage= ID )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==44) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:70:7: ',' additionalPackage= ID
            	    {
            	    char_literal9=(Token)match(input,44,FOLLOW_44_in_packageImport193); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_44.add(char_literal9);


            	    additionalPackage=(Token)match(input,ID,FOLLOW_ID_in_packageImport197); if (state.failed) return retval; 
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


    public static class assignment_return extends ParserRuleReturnScope {
        EvaluationExpression tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "assignment"
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:72:1: assignment : target= VAR '=' source= operator ->;
    public final MeteorParser.assignment_return assignment() throws RecognitionException {
        MeteorParser.assignment_return retval = new MeteorParser.assignment_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token target=null;
        Token char_literal10=null;
        MeteorParser.operator_return source =null;


        EvaluationExpression target_tree=null;
        EvaluationExpression char_literal10_tree=null;
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_52=new RewriteRuleTokenStream(adaptor,"token 52");
        RewriteRuleSubtreeStream stream_operator=new RewriteRuleSubtreeStream(adaptor,"rule operator");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:73:2: (target= VAR '=' source= operator ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:73:4: target= VAR '=' source= operator
            {
            target=(Token)match(input,VAR,FOLLOW_VAR_in_assignment214); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_VAR.add(target);


            char_literal10=(Token)match(input,52,FOLLOW_52_in_assignment216); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_52.add(char_literal10);


            pushFollow(FOLLOW_operator_in_assignment220);
            source=operator();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_operator.add(source.getTree());

            if ( state.backtracking==0 ) { putVariable(target, new JsonStreamExpression((source!=null?source.op:null))); }

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
            // 73:99: ->
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
    // $ANTLR end "assignment"


    public static class functionDefinition_return extends ParserRuleReturnScope {
        EvaluationExpression tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "functionDefinition"
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:75:1: functionDefinition : name= ID '=' func= inlineFunction ->;
    public final MeteorParser.functionDefinition_return functionDefinition() throws RecognitionException {
        MeteorParser.functionDefinition_return retval = new MeteorParser.functionDefinition_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token name=null;
        Token char_literal11=null;
        MeteorParser.inlineFunction_return func =null;


        EvaluationExpression name_tree=null;
        EvaluationExpression char_literal11_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_52=new RewriteRuleTokenStream(adaptor,"token 52");
        RewriteRuleSubtreeStream stream_inlineFunction=new RewriteRuleSubtreeStream(adaptor,"rule inlineFunction");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:76:3: (name= ID '=' func= inlineFunction ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:76:5: name= ID '=' func= inlineFunction
            {
            name=(Token)match(input,ID,FOLLOW_ID_in_functionDefinition237); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(name);


            char_literal11=(Token)match(input,52,FOLLOW_52_in_functionDefinition239); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_52.add(char_literal11);


            pushFollow(FOLLOW_inlineFunction_in_functionDefinition243);
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
            // 76:78: ->
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


    public static class inlineFunction_return extends ParserRuleReturnScope {
        public ExpressionFunction func;
        EvaluationExpression tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "inlineFunction"
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:78:1: inlineFunction returns [ExpressionFunction func] : FN '(' (param= ID ( ',' param= ID )* )? ')' def= contextAwareExpression[null] ->;
    public final MeteorParser.inlineFunction_return inlineFunction() throws RecognitionException {
        MeteorParser.inlineFunction_return retval = new MeteorParser.inlineFunction_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token param=null;
        Token FN12=null;
        Token char_literal13=null;
        Token char_literal14=null;
        Token char_literal15=null;
        MeteorParser.contextAwareExpression_return def =null;


        EvaluationExpression param_tree=null;
        EvaluationExpression FN12_tree=null;
        EvaluationExpression char_literal13_tree=null;
        EvaluationExpression char_literal14_tree=null;
        EvaluationExpression char_literal15_tree=null;
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
        RewriteRuleTokenStream stream_FN=new RewriteRuleTokenStream(adaptor,"token FN");
        RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_contextAwareExpression=new RewriteRuleSubtreeStream(adaptor,"rule contextAwareExpression");
         List<Token> params = new ArrayList(); 
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:80:3: ( FN '(' (param= ID ( ',' param= ID )* )? ')' def= contextAwareExpression[null] ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:80:5: FN '(' (param= ID ( ',' param= ID )* )? ')' def= contextAwareExpression[null]
            {
            FN12=(Token)match(input,FN,FOLLOW_FN_in_inlineFunction269); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_FN.add(FN12);


            char_literal13=(Token)match(input,40,FOLLOW_40_in_inlineFunction271); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_40.add(char_literal13);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:81:3: (param= ID ( ',' param= ID )* )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==ID) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:81:4: param= ID ( ',' param= ID )*
                    {
                    param=(Token)match(input,ID,FOLLOW_ID_in_inlineFunction280); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(param);


                    if ( state.backtracking==0 ) { params.add(param); }

                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:82:3: ( ',' param= ID )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0==44) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:82:4: ',' param= ID
                    	    {
                    	    char_literal14=(Token)match(input,44,FOLLOW_44_in_inlineFunction287); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_44.add(char_literal14);


                    	    param=(Token)match(input,ID,FOLLOW_ID_in_inlineFunction291); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_ID.add(param);


                    	    if ( state.backtracking==0 ) { params.add(param); }

                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);


                    }
                    break;

            }


            char_literal15=(Token)match(input,41,FOLLOW_41_in_inlineFunction302); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_41.add(char_literal15);


            if ( state.backtracking==0 ) { 
                addConstantScope();
                for(int index = 0; index < params.size(); index++) 
                  this.getConstantRegistry().put(params.get(index).getText(), new InputSelection(index)); 
              }

            pushFollow(FOLLOW_contextAwareExpression_in_inlineFunction314);
            def=contextAwareExpression(null);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_contextAwareExpression.add(def.getTree());

            if ( state.backtracking==0 ) { 
                retval.func = new ExpressionFunction(params.size(), def.tree);
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
            // 93:5: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:95:1: javaudf : name= ID '=' JAVAUDF '(' path= STRING ')' ->;
    public final MeteorParser.javaudf_return javaudf() throws RecognitionException {
        MeteorParser.javaudf_return retval = new MeteorParser.javaudf_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token name=null;
        Token path=null;
        Token char_literal16=null;
        Token JAVAUDF17=null;
        Token char_literal18=null;
        Token char_literal19=null;

        EvaluationExpression name_tree=null;
        EvaluationExpression path_tree=null;
        EvaluationExpression char_literal16_tree=null;
        EvaluationExpression JAVAUDF17_tree=null;
        EvaluationExpression char_literal18_tree=null;
        EvaluationExpression char_literal19_tree=null;
        RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_52=new RewriteRuleTokenStream(adaptor,"token 52");
        RewriteRuleTokenStream stream_JAVAUDF=new RewriteRuleTokenStream(adaptor,"token JAVAUDF");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:96:3: (name= ID '=' JAVAUDF '(' path= STRING ')' ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:96:5: name= ID '=' JAVAUDF '(' path= STRING ')'
            {
            name=(Token)match(input,ID,FOLLOW_ID_in_javaudf336); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(name);


            char_literal16=(Token)match(input,52,FOLLOW_52_in_javaudf338); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_52.add(char_literal16);


            JAVAUDF17=(Token)match(input,JAVAUDF,FOLLOW_JAVAUDF_in_javaudf340); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_JAVAUDF.add(JAVAUDF17);


            char_literal18=(Token)match(input,40,FOLLOW_40_in_javaudf342); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_40.add(char_literal18);


            path=(Token)match(input,STRING,FOLLOW_STRING_in_javaudf346); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_STRING.add(path);


            char_literal19=(Token)match(input,41,FOLLOW_41_in_javaudf348); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_41.add(char_literal19);


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
            // 97:53: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:99:1: contextAwareExpression[EvaluationExpression contextExpression] : ternaryExpression ;
    public final MeteorParser.contextAwareExpression_return contextAwareExpression(EvaluationExpression contextExpression) throws RecognitionException {
        contextAwareExpression_stack.push(new contextAwareExpression_scope());
        MeteorParser.contextAwareExpression_return retval = new MeteorParser.contextAwareExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.ternaryExpression_return ternaryExpression20 =null;



         ((contextAwareExpression_scope)contextAwareExpression_stack.peek()).context = contextExpression; 
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:102:3: ( ternaryExpression )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:102:5: ternaryExpression
            {
            root_0 = (EvaluationExpression)adaptor.nil();


            pushFollow(FOLLOW_ternaryExpression_in_contextAwareExpression376);
            ternaryExpression20=ternaryExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, ternaryExpression20.getTree());

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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:104:1: expression : ( ( operatorExpression )=> operatorExpression | ternaryExpression );
    public final MeteorParser.expression_return expression() throws RecognitionException {
        MeteorParser.expression_return retval = new MeteorParser.expression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.operatorExpression_return operatorExpression21 =null;

        MeteorParser.ternaryExpression_return ternaryExpression22 =null;



        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:105:3: ( ( operatorExpression )=> operatorExpression | ternaryExpression )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==63) && (synpred1_Meteor())) {
                alt6=1;
            }
            else if ( (LA6_0==67) && (synpred1_Meteor())) {
                alt6=1;
            }
            else if ( (LA6_0==ID) ) {
                int LA6_3 = input.LA(2);

                if ( (LA6_3==48) ) {
                    int LA6_5 = input.LA(3);

                    if ( (LA6_5==ID) ) {
                        int LA6_9 = input.LA(4);

                        if ( (LA6_9==AND||LA6_9==AS||(LA6_9 >= IF && LA6_9 <= IN)||LA6_9==NOT||LA6_9==OR||(LA6_9 >= SLASH && LA6_9 <= STAR)||(LA6_9 >= 37 && LA6_9 <= 38)||(LA6_9 >= 40 && LA6_9 <= 42)||(LA6_9 >= 44 && LA6_9 <= 45)||LA6_9==47||(LA6_9 >= 50 && LA6_9 <= 51)||(LA6_9 >= 53 && LA6_9 <= 57)||LA6_9==59||(LA6_9 >= 69 && LA6_9 <= 70)) ) {
                            alt6=2;
                        }
                        else if ( (LA6_9==ID) && (synpred1_Meteor())) {
                            alt6=1;
                        }
                        else if ( (LA6_9==58) ) {
                            int LA6_7 = input.LA(5);

                            if ( (LA6_7==VAR) && (synpred1_Meteor())) {
                                alt6=1;
                            }
                            else if ( (LA6_7==INTEGER||LA6_7==STAR||LA6_7==UINT) ) {
                                alt6=2;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 6, 7, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA6_9==VAR) && (synpred1_Meteor())) {
                            alt6=1;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 6, 9, input);

                            throw nvae;

                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 6, 5, input);

                        throw nvae;

                    }
                }
                else if ( (LA6_3==AND||LA6_3==AS||(LA6_3 >= IF && LA6_3 <= IN)||LA6_3==NOT||LA6_3==OR||(LA6_3 >= SLASH && LA6_3 <= STAR)||(LA6_3 >= 37 && LA6_3 <= 38)||(LA6_3 >= 40 && LA6_3 <= 42)||(LA6_3 >= 44 && LA6_3 <= 45)||LA6_3==47||(LA6_3 >= 50 && LA6_3 <= 51)||(LA6_3 >= 53 && LA6_3 <= 57)||LA6_3==59||(LA6_3 >= 69 && LA6_3 <= 70)) ) {
                    alt6=2;
                }
                else if ( (LA6_3==ID) && (synpred1_Meteor())) {
                    alt6=1;
                }
                else if ( (LA6_3==58) ) {
                    int LA6_7 = input.LA(3);

                    if ( (LA6_7==VAR) && (synpred1_Meteor())) {
                        alt6=1;
                    }
                    else if ( (LA6_7==INTEGER||LA6_7==STAR||LA6_7==UINT) ) {
                        alt6=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 6, 7, input);

                        throw nvae;

                    }
                }
                else if ( (LA6_3==VAR) && (synpred1_Meteor())) {
                    alt6=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 3, input);

                    throw nvae;

                }
            }
            else if ( (LA6_0==DECIMAL||LA6_0==INTEGER||(LA6_0 >= STRING && LA6_0 <= UINT)||LA6_0==VAR||LA6_0==36||LA6_0==40||LA6_0==43||LA6_0==46||LA6_0==58||LA6_0==60||LA6_0==62||LA6_0==65||LA6_0==68||LA6_0==71) ) {
                alt6=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;

            }
            switch (alt6) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:105:5: ( operatorExpression )=> operatorExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_operatorExpression_in_expression391);
                    operatorExpression21=operatorExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, operatorExpression21.getTree());

                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:106:5: ternaryExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_ternaryExpression_in_expression397);
                    ternaryExpression22=ternaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ternaryExpression22.getTree());

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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:108:1: ternaryExpression : ( ( orExpression '?' )=>ifClause= orExpression '?' ifExpr= orExpression ':' elseExpr= orExpression -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause) | ( orExpression IF )=>ifExpr2= orExpression IF ifClause2= orExpression -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause2 $ifExpr2) | orExpression );
    public final MeteorParser.ternaryExpression_return ternaryExpression() throws RecognitionException {
        MeteorParser.ternaryExpression_return retval = new MeteorParser.ternaryExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token char_literal23=null;
        Token char_literal24=null;
        Token IF25=null;
        MeteorParser.orExpression_return ifClause =null;

        MeteorParser.orExpression_return ifExpr =null;

        MeteorParser.orExpression_return elseExpr =null;

        MeteorParser.orExpression_return ifExpr2 =null;

        MeteorParser.orExpression_return ifClause2 =null;

        MeteorParser.orExpression_return orExpression26 =null;


        EvaluationExpression char_literal23_tree=null;
        EvaluationExpression char_literal24_tree=null;
        EvaluationExpression IF25_tree=null;
        RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
        RewriteRuleTokenStream stream_56=new RewriteRuleTokenStream(adaptor,"token 56");
        RewriteRuleTokenStream stream_IF=new RewriteRuleTokenStream(adaptor,"token IF");
        RewriteRuleSubtreeStream stream_orExpression=new RewriteRuleSubtreeStream(adaptor,"rule orExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:109:2: ( ( orExpression '?' )=>ifClause= orExpression '?' ifExpr= orExpression ':' elseExpr= orExpression -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause) | ( orExpression IF )=>ifExpr2= orExpression IF ifClause2= orExpression -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause2 $ifExpr2) | orExpression )
            int alt7=3;
            switch ( input.LA(1) ) {
            case 43:
                {
                int LA7_1 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt7=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt7=2;
                }
                else if ( (true) ) {
                    alt7=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 1, input);

                    throw nvae;

                }
                }
                break;
            case 46:
                {
                int LA7_2 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt7=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt7=2;
                }
                else if ( (true) ) {
                    alt7=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 2, input);

                    throw nvae;

                }
                }
                break;
            case 36:
            case 71:
                {
                int LA7_3 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt7=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt7=2;
                }
                else if ( (true) ) {
                    alt7=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 3, input);

                    throw nvae;

                }
                }
                break;
            case 40:
                {
                int LA7_4 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt7=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt7=2;
                }
                else if ( (true) ) {
                    alt7=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 4, input);

                    throw nvae;

                }
                }
                break;
            case ID:
                {
                int LA7_5 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt7=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt7=2;
                }
                else if ( (true) ) {
                    alt7=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 5, input);

                    throw nvae;

                }
                }
                break;
            case 65:
                {
                int LA7_6 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt7=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt7=2;
                }
                else if ( (true) ) {
                    alt7=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 6, input);

                    throw nvae;

                }
                }
                break;
            case 60:
                {
                int LA7_7 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt7=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt7=2;
                }
                else if ( (true) ) {
                    alt7=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 7, input);

                    throw nvae;

                }
                }
                break;
            case DECIMAL:
                {
                int LA7_8 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt7=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt7=2;
                }
                else if ( (true) ) {
                    alt7=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 8, input);

                    throw nvae;

                }
                }
                break;
            case STRING:
                {
                int LA7_9 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt7=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt7=2;
                }
                else if ( (true) ) {
                    alt7=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 9, input);

                    throw nvae;

                }
                }
                break;
            case UINT:
                {
                int LA7_10 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt7=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt7=2;
                }
                else if ( (true) ) {
                    alt7=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 10, input);

                    throw nvae;

                }
                }
                break;
            case INTEGER:
                {
                int LA7_11 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt7=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt7=2;
                }
                else if ( (true) ) {
                    alt7=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 11, input);

                    throw nvae;

                }
                }
                break;
            case 62:
                {
                int LA7_12 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt7=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt7=2;
                }
                else if ( (true) ) {
                    alt7=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 12, input);

                    throw nvae;

                }
                }
                break;
            case VAR:
                {
                int LA7_13 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt7=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt7=2;
                }
                else if ( (true) ) {
                    alt7=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 13, input);

                    throw nvae;

                }
                }
                break;
            case 58:
                {
                int LA7_14 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt7=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt7=2;
                }
                else if ( (true) ) {
                    alt7=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 14, input);

                    throw nvae;

                }
                }
                break;
            case 68:
                {
                int LA7_15 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt7=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt7=2;
                }
                else if ( (true) ) {
                    alt7=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 15, input);

                    throw nvae;

                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;

            }

            switch (alt7) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:109:4: ( orExpression '?' )=>ifClause= orExpression '?' ifExpr= orExpression ':' elseExpr= orExpression
                    {
                    pushFollow(FOLLOW_orExpression_in_ternaryExpression415);
                    ifClause=orExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_orExpression.add(ifClause.getTree());

                    char_literal23=(Token)match(input,56,FOLLOW_56_in_ternaryExpression417); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_56.add(char_literal23);


                    pushFollow(FOLLOW_orExpression_in_ternaryExpression421);
                    ifExpr=orExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_orExpression.add(ifExpr.getTree());

                    char_literal24=(Token)match(input,48,FOLLOW_48_in_ternaryExpression423); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_48.add(char_literal24);


                    pushFollow(FOLLOW_orExpression_in_ternaryExpression427);
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
                    // 110:2: -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause)
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:110:5: ^( EXPRESSION[\"TernaryExpression\"] $ifClause)
                        {
                        EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                        root_1 = (EvaluationExpression)adaptor.becomeRoot(
                        (EvaluationExpression)adaptor.create(EXPRESSION, "TernaryExpression")
                        , root_1);

                        adaptor.addChild(root_1, stream_ifClause.nextTree());

                        adaptor.addChild(root_1,  ifExpr == null ? EvaluationExpression.VALUE : (ifExpr!=null?((EvaluationExpression)ifExpr.tree):null) );

                        adaptor.addChild(root_1,  (elseExpr!=null?((EvaluationExpression)elseExpr.tree):null) );

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:111:4: ( orExpression IF )=>ifExpr2= orExpression IF ifClause2= orExpression
                    {
                    pushFollow(FOLLOW_orExpression_in_ternaryExpression456);
                    ifExpr2=orExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_orExpression.add(ifExpr2.getTree());

                    IF25=(Token)match(input,IF,FOLLOW_IF_in_ternaryExpression458); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_IF.add(IF25);


                    pushFollow(FOLLOW_orExpression_in_ternaryExpression462);
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
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_ifExpr2=new RewriteRuleSubtreeStream(adaptor,"rule ifExpr2",ifExpr2!=null?ifExpr2.tree:null);
                    RewriteRuleSubtreeStream stream_ifClause2=new RewriteRuleSubtreeStream(adaptor,"rule ifClause2",ifClause2!=null?ifClause2.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 112:3: -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause2 $ifExpr2)
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:112:6: ^( EXPRESSION[\"TernaryExpression\"] $ifClause2 $ifExpr2)
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:113:5: orExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_orExpression_in_ternaryExpression485);
                    orExpression26=orExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, orExpression26.getTree());

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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:115:1: orExpression :exprs+= andExpression ( ( OR | '||' ) exprs+= andExpression )* -> { $exprs.size() == 1 }? ->;
    public final MeteorParser.orExpression_return orExpression() throws RecognitionException {
        MeteorParser.orExpression_return retval = new MeteorParser.orExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token OR27=null;
        Token string_literal28=null;
        List list_exprs=null;
        RuleReturnScope exprs = null;
        EvaluationExpression OR27_tree=null;
        EvaluationExpression string_literal28_tree=null;
        RewriteRuleTokenStream stream_69=new RewriteRuleTokenStream(adaptor,"token 69");
        RewriteRuleTokenStream stream_OR=new RewriteRuleTokenStream(adaptor,"token OR");
        RewriteRuleSubtreeStream stream_andExpression=new RewriteRuleSubtreeStream(adaptor,"rule andExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:116:3: (exprs+= andExpression ( ( OR | '||' ) exprs+= andExpression )* -> { $exprs.size() == 1 }? ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:116:5: exprs+= andExpression ( ( OR | '||' ) exprs+= andExpression )*
            {
            pushFollow(FOLLOW_andExpression_in_orExpression498);
            exprs=andExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_andExpression.add(exprs.getTree());
            if (list_exprs==null) list_exprs=new ArrayList();
            list_exprs.add(exprs.getTree());


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:116:26: ( ( OR | '||' ) exprs+= andExpression )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==OR||LA9_0==69) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:116:27: ( OR | '||' ) exprs+= andExpression
            	    {
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:116:27: ( OR | '||' )
            	    int alt8=2;
            	    int LA8_0 = input.LA(1);

            	    if ( (LA8_0==OR) ) {
            	        alt8=1;
            	    }
            	    else if ( (LA8_0==69) ) {
            	        alt8=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 8, 0, input);

            	        throw nvae;

            	    }
            	    switch (alt8) {
            	        case 1 :
            	            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:116:28: OR
            	            {
            	            OR27=(Token)match(input,OR,FOLLOW_OR_in_orExpression502); if (state.failed) return retval; 
            	            if ( state.backtracking==0 ) stream_OR.add(OR27);


            	            }
            	            break;
            	        case 2 :
            	            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:116:33: '||'
            	            {
            	            string_literal28=(Token)match(input,69,FOLLOW_69_in_orExpression506); if (state.failed) return retval; 
            	            if ( state.backtracking==0 ) stream_69.add(string_literal28);


            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_andExpression_in_orExpression511);
            	    exprs=andExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_andExpression.add(exprs.getTree());
            	    if (list_exprs==null) list_exprs=new ArrayList();
            	    list_exprs.add(exprs.getTree());


            	    }
            	    break;

            	default :
            	    break loop9;
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
            // 117:3: -> { $exprs.size() == 1 }?
            if ( list_exprs.size() == 1 ) {
                adaptor.addChild(root_0,  list_exprs.get(0) );

            }

            else // 118:3: ->
            {
                adaptor.addChild(root_0,  OrExpression.valueOf(list_exprs) );

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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:120:1: andExpression :exprs+= elementExpression ( ( AND | '&&' ) exprs+= elementExpression )* -> { $exprs.size() == 1 }? ->;
    public final MeteorParser.andExpression_return andExpression() throws RecognitionException {
        MeteorParser.andExpression_return retval = new MeteorParser.andExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token AND29=null;
        Token string_literal30=null;
        List list_exprs=null;
        RuleReturnScope exprs = null;
        EvaluationExpression AND29_tree=null;
        EvaluationExpression string_literal30_tree=null;
        RewriteRuleTokenStream stream_AND=new RewriteRuleTokenStream(adaptor,"token AND");
        RewriteRuleTokenStream stream_38=new RewriteRuleTokenStream(adaptor,"token 38");
        RewriteRuleSubtreeStream stream_elementExpression=new RewriteRuleSubtreeStream(adaptor,"rule elementExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:121:3: (exprs+= elementExpression ( ( AND | '&&' ) exprs+= elementExpression )* -> { $exprs.size() == 1 }? ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:121:5: exprs+= elementExpression ( ( AND | '&&' ) exprs+= elementExpression )*
            {
            pushFollow(FOLLOW_elementExpression_in_andExpression540);
            exprs=elementExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_elementExpression.add(exprs.getTree());
            if (list_exprs==null) list_exprs=new ArrayList();
            list_exprs.add(exprs.getTree());


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:121:30: ( ( AND | '&&' ) exprs+= elementExpression )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==AND||LA11_0==38) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:121:31: ( AND | '&&' ) exprs+= elementExpression
            	    {
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:121:31: ( AND | '&&' )
            	    int alt10=2;
            	    int LA10_0 = input.LA(1);

            	    if ( (LA10_0==AND) ) {
            	        alt10=1;
            	    }
            	    else if ( (LA10_0==38) ) {
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
            	            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:121:32: AND
            	            {
            	            AND29=(Token)match(input,AND,FOLLOW_AND_in_andExpression544); if (state.failed) return retval; 
            	            if ( state.backtracking==0 ) stream_AND.add(AND29);


            	            }
            	            break;
            	        case 2 :
            	            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:121:38: '&&'
            	            {
            	            string_literal30=(Token)match(input,38,FOLLOW_38_in_andExpression548); if (state.failed) return retval; 
            	            if ( state.backtracking==0 ) stream_38.add(string_literal30);


            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_elementExpression_in_andExpression553);
            	    exprs=elementExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_elementExpression.add(exprs.getTree());
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
                adaptor.addChild(root_0,  AndExpression.valueOf(list_exprs) );

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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:125:1: elementExpression : elem= comparisonExpression ( (not= NOT )? IN set= comparisonExpression )? -> { set == null }? $elem -> ^( EXPRESSION[\"ElementInSetExpression\"] $elem $set) ;
    public final MeteorParser.elementExpression_return elementExpression() throws RecognitionException {
        MeteorParser.elementExpression_return retval = new MeteorParser.elementExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token not=null;
        Token IN31=null;
        MeteorParser.comparisonExpression_return elem =null;

        MeteorParser.comparisonExpression_return set =null;


        EvaluationExpression not_tree=null;
        EvaluationExpression IN31_tree=null;
        RewriteRuleTokenStream stream_IN=new RewriteRuleTokenStream(adaptor,"token IN");
        RewriteRuleTokenStream stream_NOT=new RewriteRuleTokenStream(adaptor,"token NOT");
        RewriteRuleSubtreeStream stream_comparisonExpression=new RewriteRuleSubtreeStream(adaptor,"rule comparisonExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:126:2: (elem= comparisonExpression ( (not= NOT )? IN set= comparisonExpression )? -> { set == null }? $elem -> ^( EXPRESSION[\"ElementInSetExpression\"] $elem $set) )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:126:4: elem= comparisonExpression ( (not= NOT )? IN set= comparisonExpression )?
            {
            pushFollow(FOLLOW_comparisonExpression_in_elementExpression582);
            elem=comparisonExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_comparisonExpression.add(elem.getTree());

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:126:30: ( (not= NOT )? IN set= comparisonExpression )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==IN||LA13_0==NOT) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:126:31: (not= NOT )? IN set= comparisonExpression
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:126:34: (not= NOT )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==NOT) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:126:34: not= NOT
                            {
                            not=(Token)match(input,NOT,FOLLOW_NOT_in_elementExpression587); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_NOT.add(not);


                            }
                            break;

                    }


                    IN31=(Token)match(input,IN,FOLLOW_IN_in_elementExpression590); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_IN.add(IN31);


                    pushFollow(FOLLOW_comparisonExpression_in_elementExpression594);
                    set=comparisonExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_comparisonExpression.add(set.getTree());

                    }
                    break;

            }


            // AST REWRITE
            // elements: elem, set, elem
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
            // 127:2: -> { set == null }? $elem
            if ( set == null ) {
                adaptor.addChild(root_0, stream_elem.nextTree());

            }

            else // 128:2: -> ^( EXPRESSION[\"ElementInSetExpression\"] $elem $set)
            {
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:128:5: ^( EXPRESSION[\"ElementInSetExpression\"] $elem $set)
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:131:1: comparisonExpression : e1= arithmeticExpression ( (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' ) e2= arithmeticExpression )? -> { $s == null }? $e1 -> { $s.getText().equals(\"!=\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) -> { $s.getText().equals(\"==\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) -> ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) ;
    public final MeteorParser.comparisonExpression_return comparisonExpression() throws RecognitionException {
        MeteorParser.comparisonExpression_return retval = new MeteorParser.comparisonExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token s=null;
        MeteorParser.arithmeticExpression_return e1 =null;

        MeteorParser.arithmeticExpression_return e2 =null;


        EvaluationExpression s_tree=null;
        RewriteRuleTokenStream stream_55=new RewriteRuleTokenStream(adaptor,"token 55");
        RewriteRuleTokenStream stream_51=new RewriteRuleTokenStream(adaptor,"token 51");
        RewriteRuleTokenStream stream_53=new RewriteRuleTokenStream(adaptor,"token 53");
        RewriteRuleTokenStream stream_54=new RewriteRuleTokenStream(adaptor,"token 54");
        RewriteRuleTokenStream stream_37=new RewriteRuleTokenStream(adaptor,"token 37");
        RewriteRuleTokenStream stream_50=new RewriteRuleTokenStream(adaptor,"token 50");
        RewriteRuleSubtreeStream stream_arithmeticExpression=new RewriteRuleSubtreeStream(adaptor,"rule arithmeticExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:132:2: (e1= arithmeticExpression ( (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' ) e2= arithmeticExpression )? -> { $s == null }? $e1 -> { $s.getText().equals(\"!=\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) -> { $s.getText().equals(\"==\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) -> ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:132:4: e1= arithmeticExpression ( (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' ) e2= arithmeticExpression )?
            {
            pushFollow(FOLLOW_arithmeticExpression_in_comparisonExpression635);
            e1=arithmeticExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_arithmeticExpression.add(e1.getTree());

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:132:28: ( (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' ) e2= arithmeticExpression )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==37||(LA15_0 >= 50 && LA15_0 <= 51)||(LA15_0 >= 53 && LA15_0 <= 55)) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:132:29: (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' ) e2= arithmeticExpression
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:132:29: (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' )
                    int alt14=6;
                    switch ( input.LA(1) ) {
                    case 51:
                        {
                        alt14=1;
                        }
                        break;
                    case 55:
                        {
                        alt14=2;
                        }
                        break;
                    case 50:
                        {
                        alt14=3;
                        }
                        break;
                    case 54:
                        {
                        alt14=4;
                        }
                        break;
                    case 53:
                        {
                        alt14=5;
                        }
                        break;
                    case 37:
                        {
                        alt14=6;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 14, 0, input);

                        throw nvae;

                    }

                    switch (alt14) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:132:30: s= '<='
                            {
                            s=(Token)match(input,51,FOLLOW_51_in_comparisonExpression641); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_51.add(s);


                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:132:39: s= '>='
                            {
                            s=(Token)match(input,55,FOLLOW_55_in_comparisonExpression647); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_55.add(s);


                            }
                            break;
                        case 3 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:132:48: s= '<'
                            {
                            s=(Token)match(input,50,FOLLOW_50_in_comparisonExpression653); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_50.add(s);


                            }
                            break;
                        case 4 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:132:56: s= '>'
                            {
                            s=(Token)match(input,54,FOLLOW_54_in_comparisonExpression659); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_54.add(s);


                            }
                            break;
                        case 5 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:132:64: s= '=='
                            {
                            s=(Token)match(input,53,FOLLOW_53_in_comparisonExpression665); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_53.add(s);


                            }
                            break;
                        case 6 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:132:73: s= '!='
                            {
                            s=(Token)match(input,37,FOLLOW_37_in_comparisonExpression671); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_37.add(s);


                            }
                            break;

                    }


                    pushFollow(FOLLOW_arithmeticExpression_in_comparisonExpression676);
                    e2=arithmeticExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_arithmeticExpression.add(e2.getTree());

                    }
                    break;

            }


            // AST REWRITE
            // elements: e1, e1, e2, e2, e2, e1, e1
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
            // 133:2: -> { $s == null }? $e1
            if ( s == null ) {
                adaptor.addChild(root_0, stream_e1.nextTree());

            }

            else // 134:3: -> { $s.getText().equals(\"!=\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
            if ( s.getText().equals("!=") ) {
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:134:38: ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
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

            else // 135:3: -> { $s.getText().equals(\"==\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
            if ( s.getText().equals("==") ) {
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:135:38: ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
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

            else // 136:2: -> ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
            {
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:136:6: ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:138:1: arithmeticExpression : e1= multiplicationExpression ( (s= '+' |s= '-' ) e2= multiplicationExpression )? -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2) -> $e1;
    public final MeteorParser.arithmeticExpression_return arithmeticExpression() throws RecognitionException {
        MeteorParser.arithmeticExpression_return retval = new MeteorParser.arithmeticExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token s=null;
        MeteorParser.multiplicationExpression_return e1 =null;

        MeteorParser.multiplicationExpression_return e2 =null;


        EvaluationExpression s_tree=null;
        RewriteRuleTokenStream stream_45=new RewriteRuleTokenStream(adaptor,"token 45");
        RewriteRuleTokenStream stream_42=new RewriteRuleTokenStream(adaptor,"token 42");
        RewriteRuleSubtreeStream stream_multiplicationExpression=new RewriteRuleSubtreeStream(adaptor,"rule multiplicationExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:139:2: (e1= multiplicationExpression ( (s= '+' |s= '-' ) e2= multiplicationExpression )? -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2) -> $e1)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:139:4: e1= multiplicationExpression ( (s= '+' |s= '-' ) e2= multiplicationExpression )?
            {
            pushFollow(FOLLOW_multiplicationExpression_in_arithmeticExpression756);
            e1=multiplicationExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_multiplicationExpression.add(e1.getTree());

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:139:32: ( (s= '+' |s= '-' ) e2= multiplicationExpression )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==42||LA17_0==45) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:139:33: (s= '+' |s= '-' ) e2= multiplicationExpression
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:139:33: (s= '+' |s= '-' )
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0==42) ) {
                        alt16=1;
                    }
                    else if ( (LA16_0==45) ) {
                        alt16=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 16, 0, input);

                        throw nvae;

                    }
                    switch (alt16) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:139:34: s= '+'
                            {
                            s=(Token)match(input,42,FOLLOW_42_in_arithmeticExpression762); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_42.add(s);


                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:139:42: s= '-'
                            {
                            s=(Token)match(input,45,FOLLOW_45_in_arithmeticExpression768); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_45.add(s);


                            }
                            break;

                    }


                    pushFollow(FOLLOW_multiplicationExpression_in_arithmeticExpression773);
                    e2=multiplicationExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_multiplicationExpression.add(e2.getTree());

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
            // 140:2: -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2)
            if ( s != null ) {
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:140:21: ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2)
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

            else // 142:2: -> $e1
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:144:1: multiplicationExpression : e1= preincrementExpression ( (s= '*' |s= SLASH ) e2= preincrementExpression )? -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2) -> $e1;
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
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:145:2: (e1= preincrementExpression ( (s= '*' |s= SLASH ) e2= preincrementExpression )? -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2) -> $e1)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:145:4: e1= preincrementExpression ( (s= '*' |s= SLASH ) e2= preincrementExpression )?
            {
            pushFollow(FOLLOW_preincrementExpression_in_multiplicationExpression816);
            e1=preincrementExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_preincrementExpression.add(e1.getTree());

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:145:30: ( (s= '*' |s= SLASH ) e2= preincrementExpression )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( ((LA19_0 >= SLASH && LA19_0 <= STAR)) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:145:31: (s= '*' |s= SLASH ) e2= preincrementExpression
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:145:31: (s= '*' |s= SLASH )
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0==STAR) ) {
                        alt18=1;
                    }
                    else if ( (LA18_0==SLASH) ) {
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:145:32: s= '*'
                            {
                            s=(Token)match(input,STAR,FOLLOW_STAR_in_multiplicationExpression822); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_STAR.add(s);


                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:145:40: s= SLASH
                            {
                            s=(Token)match(input,SLASH,FOLLOW_SLASH_in_multiplicationExpression828); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_SLASH.add(s);


                            }
                            break;

                    }


                    pushFollow(FOLLOW_preincrementExpression_in_multiplicationExpression833);
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
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);
            RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 146:2: -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2)
            if ( s != null ) {
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:146:21: ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2)
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

            else // 148:2: -> $e1
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:150:1: preincrementExpression : ( '++' preincrementExpression | '--' preincrementExpression | unaryExpression );
    public final MeteorParser.preincrementExpression_return preincrementExpression() throws RecognitionException {
        MeteorParser.preincrementExpression_return retval = new MeteorParser.preincrementExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token string_literal32=null;
        Token string_literal34=null;
        MeteorParser.preincrementExpression_return preincrementExpression33 =null;

        MeteorParser.preincrementExpression_return preincrementExpression35 =null;

        MeteorParser.unaryExpression_return unaryExpression36 =null;


        EvaluationExpression string_literal32_tree=null;
        EvaluationExpression string_literal34_tree=null;

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:151:2: ( '++' preincrementExpression | '--' preincrementExpression | unaryExpression )
            int alt20=3;
            switch ( input.LA(1) ) {
            case 43:
                {
                alt20=1;
                }
                break;
            case 46:
                {
                alt20=2;
                }
                break;
            case DECIMAL:
            case ID:
            case INTEGER:
            case STRING:
            case UINT:
            case VAR:
            case 36:
            case 40:
            case 58:
            case 60:
            case 62:
            case 65:
            case 68:
            case 71:
                {
                alt20=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;

            }

            switch (alt20) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:151:4: '++' preincrementExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    string_literal32=(Token)match(input,43,FOLLOW_43_in_preincrementExpression874); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal32_tree = 
                    (EvaluationExpression)adaptor.create(string_literal32)
                    ;
                    adaptor.addChild(root_0, string_literal32_tree);
                    }

                    pushFollow(FOLLOW_preincrementExpression_in_preincrementExpression876);
                    preincrementExpression33=preincrementExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, preincrementExpression33.getTree());

                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:152:4: '--' preincrementExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    string_literal34=(Token)match(input,46,FOLLOW_46_in_preincrementExpression881); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal34_tree = 
                    (EvaluationExpression)adaptor.create(string_literal34)
                    ;
                    adaptor.addChild(root_0, string_literal34_tree);
                    }

                    pushFollow(FOLLOW_preincrementExpression_in_preincrementExpression883);
                    preincrementExpression35=preincrementExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, preincrementExpression35.getTree());

                    }
                    break;
                case 3 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:153:4: unaryExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_unaryExpression_in_preincrementExpression888);
                    unaryExpression36=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression36.getTree());

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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:155:1: unaryExpression : ( '!' | '~' )? castExpression ;
    public final MeteorParser.unaryExpression_return unaryExpression() throws RecognitionException {
        MeteorParser.unaryExpression_return retval = new MeteorParser.unaryExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token set37=null;
        MeteorParser.castExpression_return castExpression38 =null;


        EvaluationExpression set37_tree=null;

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:156:2: ( ( '!' | '~' )? castExpression )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:156:4: ( '!' | '~' )? castExpression
            {
            root_0 = (EvaluationExpression)adaptor.nil();


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:156:4: ( '!' | '~' )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==36||LA21_0==71) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:
                    {
                    set37=(Token)input.LT(1);

                    if ( input.LA(1)==36||input.LA(1)==71 ) {
                        input.consume();
                        if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                        (EvaluationExpression)adaptor.create(set37)
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


            pushFollow(FOLLOW_castExpression_in_unaryExpression907);
            castExpression38=castExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, castExpression38.getTree());

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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:158:1: castExpression : ( ( '(' ID ')' )=> '(' type= ID ')' expr= generalPathExpression ->| ( generalPathExpression AS )=>expr= generalPathExpression AS type= ID ->| generalPathExpression );
    public final MeteorParser.castExpression_return castExpression() throws RecognitionException {
        MeteorParser.castExpression_return retval = new MeteorParser.castExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token type=null;
        Token char_literal39=null;
        Token char_literal40=null;
        Token AS41=null;
        MeteorParser.generalPathExpression_return expr =null;

        MeteorParser.generalPathExpression_return generalPathExpression42 =null;


        EvaluationExpression type_tree=null;
        EvaluationExpression char_literal39_tree=null;
        EvaluationExpression char_literal40_tree=null;
        EvaluationExpression AS41_tree=null;
        RewriteRuleTokenStream stream_AS=new RewriteRuleTokenStream(adaptor,"token AS");
        RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_generalPathExpression=new RewriteRuleSubtreeStream(adaptor,"rule generalPathExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:159:2: ( ( '(' ID ')' )=> '(' type= ID ')' expr= generalPathExpression ->| ( generalPathExpression AS )=>expr= generalPathExpression AS type= ID ->| generalPathExpression )
            int alt22=3;
            switch ( input.LA(1) ) {
            case 40:
                {
                int LA22_1 = input.LA(2);

                if ( (synpred4_Meteor()) ) {
                    alt22=1;
                }
                else if ( (synpred5_Meteor()) ) {
                    alt22=2;
                }
                else if ( (true) ) {
                    alt22=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 22, 1, input);

                    throw nvae;

                }
                }
                break;
            case ID:
                {
                int LA22_2 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt22=2;
                }
                else if ( (true) ) {
                    alt22=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 22, 2, input);

                    throw nvae;

                }
                }
                break;
            case 65:
                {
                int LA22_3 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt22=2;
                }
                else if ( (true) ) {
                    alt22=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 22, 3, input);

                    throw nvae;

                }
                }
                break;
            case 60:
                {
                int LA22_4 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt22=2;
                }
                else if ( (true) ) {
                    alt22=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 22, 4, input);

                    throw nvae;

                }
                }
                break;
            case DECIMAL:
                {
                int LA22_5 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt22=2;
                }
                else if ( (true) ) {
                    alt22=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 22, 5, input);

                    throw nvae;

                }
                }
                break;
            case STRING:
                {
                int LA22_6 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt22=2;
                }
                else if ( (true) ) {
                    alt22=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 22, 6, input);

                    throw nvae;

                }
                }
                break;
            case UINT:
                {
                int LA22_7 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt22=2;
                }
                else if ( (true) ) {
                    alt22=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 22, 7, input);

                    throw nvae;

                }
                }
                break;
            case INTEGER:
                {
                int LA22_8 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt22=2;
                }
                else if ( (true) ) {
                    alt22=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 22, 8, input);

                    throw nvae;

                }
                }
                break;
            case 62:
                {
                int LA22_9 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt22=2;
                }
                else if ( (true) ) {
                    alt22=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 22, 9, input);

                    throw nvae;

                }
                }
                break;
            case VAR:
                {
                int LA22_10 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt22=2;
                }
                else if ( (true) ) {
                    alt22=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 22, 10, input);

                    throw nvae;

                }
                }
                break;
            case 58:
                {
                int LA22_11 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt22=2;
                }
                else if ( (true) ) {
                    alt22=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 22, 11, input);

                    throw nvae;

                }
                }
                break;
            case 68:
                {
                int LA22_12 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt22=2;
                }
                else if ( (true) ) {
                    alt22=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 22, 12, input);

                    throw nvae;

                }
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:159:4: ( '(' ID ')' )=> '(' type= ID ')' expr= generalPathExpression
                    {
                    char_literal39=(Token)match(input,40,FOLLOW_40_in_castExpression925); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_40.add(char_literal39);


                    type=(Token)match(input,ID,FOLLOW_ID_in_castExpression929); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(type);


                    char_literal40=(Token)match(input,41,FOLLOW_41_in_castExpression931); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_41.add(char_literal40);


                    pushFollow(FOLLOW_generalPathExpression_in_castExpression935);
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
                    // 160:3: ->
                    {
                        adaptor.addChild(root_0,  coerce((type!=null?type.getText():null), (expr!=null?((EvaluationExpression)expr.tree):null)) );

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:161:4: ( generalPathExpression AS )=>expr= generalPathExpression AS type= ID
                    {
                    pushFollow(FOLLOW_generalPathExpression_in_castExpression955);
                    expr=generalPathExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_generalPathExpression.add(expr.getTree());

                    AS41=(Token)match(input,AS,FOLLOW_AS_in_castExpression957); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_AS.add(AS41);


                    type=(Token)match(input,ID,FOLLOW_ID_in_castExpression961); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(type);


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
                    // 162:3: ->
                    {
                        adaptor.addChild(root_0,  coerce((type!=null?type.getText():null), (expr!=null?((EvaluationExpression)expr.tree):null)) );

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 3 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:163:4: generalPathExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_generalPathExpression_in_castExpression972);
                    generalPathExpression42=generalPathExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, generalPathExpression42.getTree());

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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:165:1: generalPathExpression : value= valueExpression ( ( pathExpression[EvaluationExpression.VALUE] )=>path= pathExpression[$value.tree] -> $path| -> $value) ;
    public final MeteorParser.generalPathExpression_return generalPathExpression() throws RecognitionException {
        MeteorParser.generalPathExpression_return retval = new MeteorParser.generalPathExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.valueExpression_return value =null;

        MeteorParser.pathExpression_return path =null;


        RewriteRuleSubtreeStream stream_valueExpression=new RewriteRuleSubtreeStream(adaptor,"rule valueExpression");
        RewriteRuleSubtreeStream stream_pathExpression=new RewriteRuleSubtreeStream(adaptor,"rule pathExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:166:2: (value= valueExpression ( ( pathExpression[EvaluationExpression.VALUE] )=>path= pathExpression[$value.tree] -> $path| -> $value) )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:166:4: value= valueExpression ( ( pathExpression[EvaluationExpression.VALUE] )=>path= pathExpression[$value.tree] -> $path| -> $value)
            {
            pushFollow(FOLLOW_valueExpression_in_generalPathExpression984);
            value=valueExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_valueExpression.add(value.getTree());

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:167:4: ( ( pathExpression[EvaluationExpression.VALUE] )=>path= pathExpression[$value.tree] -> $path| -> $value)
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==57) && (synpred6_Meteor())) {
                alt23=1;
            }
            else if ( (LA23_0==47) && (synpred6_Meteor())) {
                alt23=1;
            }
            else if ( (LA23_0==58) && (synpred6_Meteor())) {
                alt23=1;
            }
            else if ( (LA23_0==EOF||LA23_0==AND||LA23_0==AS||(LA23_0 >= ID && LA23_0 <= IN)||LA23_0==NOT||LA23_0==OR||(LA23_0 >= SLASH && LA23_0 <= STAR)||(LA23_0 >= 37 && LA23_0 <= 38)||(LA23_0 >= 41 && LA23_0 <= 42)||(LA23_0 >= 44 && LA23_0 <= 45)||(LA23_0 >= 48 && LA23_0 <= 51)||(LA23_0 >= 53 && LA23_0 <= 56)||LA23_0==59||(LA23_0 >= 69 && LA23_0 <= 70)) ) {
                alt23=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;

            }
            switch (alt23) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:167:5: ( pathExpression[EvaluationExpression.VALUE] )=>path= pathExpression[$value.tree]
                    {
                    pushFollow(FOLLOW_pathExpression_in_generalPathExpression999);
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
                    // 167:85: -> $path
                    {
                        adaptor.addChild(root_0, stream_path.nextTree());

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:168:7: 
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
                    // 168:7: -> $value
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:170:1: contextAwarePathExpression[EvaluationExpression context] : pathExpression[context] ;
    public final MeteorParser.contextAwarePathExpression_return contextAwarePathExpression(EvaluationExpression context) throws RecognitionException {
        MeteorParser.contextAwarePathExpression_return retval = new MeteorParser.contextAwarePathExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.pathExpression_return pathExpression43 =null;



        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:171:3: ( pathExpression[context] )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:171:5: pathExpression[context]
            {
            root_0 = (EvaluationExpression)adaptor.nil();


            pushFollow(FOLLOW_pathExpression_in_contextAwarePathExpression1028);
            pathExpression43=pathExpression(context);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, pathExpression43.getTree());

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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:173:1: pathExpression[EvaluationExpression inExp] : ( ( '?.' ID '(' )=> '?.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)] -> $path| -> ^( EXPRESSION[\"TernaryExpression\"] $call) ) | ( '.' ID '(' )=> '.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[$call.tree] -> $path| -> $call) |seg= pathSegment ( ( pathSegment )=>path= pathExpression[$seg.tree] -> $path| -> $seg) );
    public final MeteorParser.pathExpression_return pathExpression(EvaluationExpression inExp) throws RecognitionException {
        MeteorParser.pathExpression_return retval = new MeteorParser.pathExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token string_literal44=null;
        Token char_literal45=null;
        MeteorParser.methodCall_return call =null;

        MeteorParser.pathExpression_return path =null;

        MeteorParser.pathSegment_return seg =null;


        EvaluationExpression string_literal44_tree=null;
        EvaluationExpression char_literal45_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleSubtreeStream stream_pathSegment=new RewriteRuleSubtreeStream(adaptor,"rule pathSegment");
        RewriteRuleSubtreeStream stream_methodCall=new RewriteRuleSubtreeStream(adaptor,"rule methodCall");
        RewriteRuleSubtreeStream stream_pathExpression=new RewriteRuleSubtreeStream(adaptor,"rule pathExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:174:3: ( ( '?.' ID '(' )=> '?.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)] -> $path| -> ^( EXPRESSION[\"TernaryExpression\"] $call) ) | ( '.' ID '(' )=> '.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[$call.tree] -> $path| -> $call) |seg= pathSegment ( ( pathSegment )=>path= pathExpression[$seg.tree] -> $path| -> $seg) )
            int alt27=3;
            switch ( input.LA(1) ) {
            case 57:
                {
                int LA27_1 = input.LA(2);

                if ( (synpred7_Meteor()) ) {
                    alt27=1;
                }
                else if ( (true) ) {
                    alt27=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 27, 1, input);

                    throw nvae;

                }
                }
                break;
            case 47:
                {
                int LA27_2 = input.LA(2);

                if ( (synpred9_Meteor()) ) {
                    alt27=2;
                }
                else if ( (true) ) {
                    alt27=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 27, 2, input);

                    throw nvae;

                }
                }
                break;
            case 58:
                {
                alt27=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;

            }

            switch (alt27) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:175:5: ( '?.' ID '(' )=> '?.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)] -> $path| -> ^( EXPRESSION[\"TernaryExpression\"] $call) )
                    {
                    string_literal44=(Token)match(input,57,FOLLOW_57_in_pathExpression1056); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(string_literal44);


                    pushFollow(FOLLOW_methodCall_in_pathExpression1060);
                    call=methodCall(inExp);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_methodCall.add(call.getTree());

                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:176:7: ( ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)] -> $path| -> ^( EXPRESSION[\"TernaryExpression\"] $call) )
                    int alt24=2;
                    int LA24_0 = input.LA(1);

                    if ( (LA24_0==57) && (synpred8_Meteor())) {
                        alt24=1;
                    }
                    else if ( (LA24_0==47) && (synpred8_Meteor())) {
                        alt24=1;
                    }
                    else if ( (LA24_0==58) && (synpred8_Meteor())) {
                        alt24=1;
                    }
                    else if ( (LA24_0==EOF||LA24_0==AND||LA24_0==AS||(LA24_0 >= ID && LA24_0 <= IN)||LA24_0==NOT||LA24_0==OR||(LA24_0 >= SLASH && LA24_0 <= STAR)||(LA24_0 >= 37 && LA24_0 <= 38)||(LA24_0 >= 41 && LA24_0 <= 42)||(LA24_0 >= 44 && LA24_0 <= 45)||(LA24_0 >= 48 && LA24_0 <= 51)||(LA24_0 >= 53 && LA24_0 <= 56)||LA24_0==59||(LA24_0 >= 69 && LA24_0 <= 70)) ) {
                        alt24=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 24, 0, input);

                        throw nvae;

                    }
                    switch (alt24) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:176:8: ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)]
                            {
                            pushFollow(FOLLOW_pathExpression_in_pathExpression1077);
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
                            // 176:146: -> $path
                            {
                                adaptor.addChild(root_0, stream_path.nextTree());

                            }


                            retval.tree = root_0;
                            }

                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:177:8: 
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
                            // 177:8: -> ^( EXPRESSION[\"TernaryExpression\"] $call)
                            {
                                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:177:11: ^( EXPRESSION[\"TernaryExpression\"] $call)
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:179:5: ( '.' ID '(' )=> '.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[$call.tree] -> $path| -> $call)
                    {
                    char_literal45=(Token)match(input,47,FOLLOW_47_in_pathExpression1127); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_47.add(char_literal45);


                    pushFollow(FOLLOW_methodCall_in_pathExpression1131);
                    call=methodCall(inExp);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_methodCall.add(call.getTree());

                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:180:7: ( ( pathSegment )=>path= pathExpression[$call.tree] -> $path| -> $call)
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( (LA25_0==57) && (synpred10_Meteor())) {
                        alt25=1;
                    }
                    else if ( (LA25_0==47) && (synpred10_Meteor())) {
                        alt25=1;
                    }
                    else if ( (LA25_0==58) && (synpred10_Meteor())) {
                        alt25=1;
                    }
                    else if ( (LA25_0==EOF||LA25_0==AND||LA25_0==AS||(LA25_0 >= ID && LA25_0 <= IN)||LA25_0==NOT||LA25_0==OR||(LA25_0 >= SLASH && LA25_0 <= STAR)||(LA25_0 >= 37 && LA25_0 <= 38)||(LA25_0 >= 41 && LA25_0 <= 42)||(LA25_0 >= 44 && LA25_0 <= 45)||(LA25_0 >= 48 && LA25_0 <= 51)||(LA25_0 >= 53 && LA25_0 <= 56)||LA25_0==59||(LA25_0 >= 69 && LA25_0 <= 70)) ) {
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:180:8: ( pathSegment )=>path= pathExpression[$call.tree]
                            {
                            pushFollow(FOLLOW_pathExpression_in_pathExpression1148);
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
                            // 180:55: -> $path
                            {
                                adaptor.addChild(root_0, stream_path.nextTree());

                            }


                            retval.tree = root_0;
                            }

                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:180:66: 
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
                            // 180:66: -> $call
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:182:5: seg= pathSegment ( ( pathSegment )=>path= pathExpression[$seg.tree] -> $path| -> $seg)
                    {
                    pushFollow(FOLLOW_pathSegment_in_pathExpression1174);
                    seg=pathSegment();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_pathSegment.add(seg.getTree());

                    if ( state.backtracking==0 ) { ((PathSegmentExpression) seg.getTree()).setInputExpression(inExp); }

                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:183:5: ( ( pathSegment )=>path= pathExpression[$seg.tree] -> $path| -> $seg)
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0==57) && (synpred11_Meteor())) {
                        alt26=1;
                    }
                    else if ( (LA26_0==47) && (synpred11_Meteor())) {
                        alt26=1;
                    }
                    else if ( (LA26_0==58) && (synpred11_Meteor())) {
                        alt26=1;
                    }
                    else if ( (LA26_0==EOF||LA26_0==AND||LA26_0==AS||(LA26_0 >= ID && LA26_0 <= IN)||LA26_0==NOT||LA26_0==OR||(LA26_0 >= SLASH && LA26_0 <= STAR)||(LA26_0 >= 37 && LA26_0 <= 38)||(LA26_0 >= 41 && LA26_0 <= 42)||(LA26_0 >= 44 && LA26_0 <= 45)||(LA26_0 >= 48 && LA26_0 <= 51)||(LA26_0 >= 53 && LA26_0 <= 56)||LA26_0==59||(LA26_0 >= 69 && LA26_0 <= 70)) ) {
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:183:6: ( pathSegment )=>path= pathExpression[$seg.tree]
                            {
                            pushFollow(FOLLOW_pathExpression_in_pathExpression1190);
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
                            // 183:53: -> $path
                            {
                                adaptor.addChild(root_0, stream_path.nextTree());

                            }


                            retval.tree = root_0;
                            }

                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:183:64: 
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
                            // 183:64: -> $seg
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:186:1: pathSegment : ( ( '?.' )=> '?.' field= ID -> ^( EXPRESSION[\"TernaryExpression\"] ^( EXPRESSION[\"NotNullOrMissingBooleanExpression\"] ) ^( EXPRESSION[\"ObjectAccess\"] ) ) | ( '.' )=> '.' field= ID -> ^( EXPRESSION[\"ObjectAccess\"] ) | ( '[' )=> arrayAccess );
    public final MeteorParser.pathSegment_return pathSegment() throws RecognitionException {
        MeteorParser.pathSegment_return retval = new MeteorParser.pathSegment_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token field=null;
        Token string_literal46=null;
        Token char_literal47=null;
        MeteorParser.arrayAccess_return arrayAccess48 =null;


        EvaluationExpression field_tree=null;
        EvaluationExpression string_literal46_tree=null;
        EvaluationExpression char_literal47_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");

          paraphrase.push("a path expression"); 
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:189:3: ( ( '?.' )=> '?.' field= ID -> ^( EXPRESSION[\"TernaryExpression\"] ^( EXPRESSION[\"NotNullOrMissingBooleanExpression\"] ) ^( EXPRESSION[\"ObjectAccess\"] ) ) | ( '.' )=> '.' field= ID -> ^( EXPRESSION[\"ObjectAccess\"] ) | ( '[' )=> arrayAccess )
            int alt28=3;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==57) && (synpred12_Meteor())) {
                alt28=1;
            }
            else if ( (LA28_0==47) && (synpred13_Meteor())) {
                alt28=2;
            }
            else if ( (LA28_0==58) && (synpred14_Meteor())) {
                alt28=3;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;

            }
            switch (alt28) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:190:5: ( '?.' )=> '?.' field= ID
                    {
                    string_literal46=(Token)match(input,57,FOLLOW_57_in_pathSegment1242); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(string_literal46);


                    field=(Token)match(input,ID,FOLLOW_ID_in_pathSegment1246); if (state.failed) return retval; 
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
                    // 190:28: -> ^( EXPRESSION[\"TernaryExpression\"] ^( EXPRESSION[\"NotNullOrMissingBooleanExpression\"] ) ^( EXPRESSION[\"ObjectAccess\"] ) )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:190:31: ^( EXPRESSION[\"TernaryExpression\"] ^( EXPRESSION[\"NotNullOrMissingBooleanExpression\"] ) ^( EXPRESSION[\"ObjectAccess\"] ) )
                        {
                        EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                        root_1 = (EvaluationExpression)adaptor.becomeRoot(
                        (EvaluationExpression)adaptor.create(EXPRESSION, "TernaryExpression")
                        , root_1);

                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:190:65: ^( EXPRESSION[\"NotNullOrMissingBooleanExpression\"] )
                        {
                        EvaluationExpression root_2 = (EvaluationExpression)adaptor.nil();
                        root_2 = (EvaluationExpression)adaptor.becomeRoot(
                        (EvaluationExpression)adaptor.create(EXPRESSION, "NotNullOrMissingBooleanExpression")
                        , root_2);

                        adaptor.addChild(root_1, root_2);
                        }

                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:190:116: ^( EXPRESSION[\"ObjectAccess\"] )
                        {
                        EvaluationExpression root_2 = (EvaluationExpression)adaptor.nil();
                        root_2 = (EvaluationExpression)adaptor.becomeRoot(
                        (EvaluationExpression)adaptor.create(EXPRESSION, "ObjectAccess")
                        , root_2);

                        adaptor.addChild(root_2, (field!=null?field.getText():null));

                        adaptor.addChild(root_2, EvaluationExpression.VALUE);

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:191:5: ( '.' )=> '.' field= ID
                    {
                    char_literal47=(Token)match(input,47,FOLLOW_47_in_pathSegment1281); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_47.add(char_literal47);


                    field=(Token)match(input,ID,FOLLOW_ID_in_pathSegment1285); if (state.failed) return retval; 
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
                    // 191:27: -> ^( EXPRESSION[\"ObjectAccess\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:191:30: ^( EXPRESSION[\"ObjectAccess\"] )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:192:5: ( '[' )=> arrayAccess
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_arrayAccess_in_pathSegment1310);
                    arrayAccess48=arrayAccess();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arrayAccess48.getTree());

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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:194:1: arrayAccess : ( '[' STAR ']' ( ( '.' methodCall[null] )=> '.' call= methodCall[EvaluationExpression.VALUE] -> ^( EXPRESSION[\"ArrayProjection\"] $call) |path= pathSegment -> ^( EXPRESSION[\"ArrayProjection\"] $path) ) | '[' (pos= INTEGER |pos= UINT ) ']' -> ^( EXPRESSION[\"ArrayAccess\"] ) | '[' (start= INTEGER |start= UINT ) ':' (end= INTEGER |end= UINT ) ']' -> ^( EXPRESSION[\"ArrayAccess\"] ) );
    public final MeteorParser.arrayAccess_return arrayAccess() throws RecognitionException {
        MeteorParser.arrayAccess_return retval = new MeteorParser.arrayAccess_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token pos=null;
        Token start=null;
        Token end=null;
        Token char_literal49=null;
        Token STAR50=null;
        Token char_literal51=null;
        Token char_literal52=null;
        Token char_literal53=null;
        Token char_literal54=null;
        Token char_literal55=null;
        Token char_literal56=null;
        Token char_literal57=null;
        MeteorParser.methodCall_return call =null;

        MeteorParser.pathSegment_return path =null;


        EvaluationExpression pos_tree=null;
        EvaluationExpression start_tree=null;
        EvaluationExpression end_tree=null;
        EvaluationExpression char_literal49_tree=null;
        EvaluationExpression STAR50_tree=null;
        EvaluationExpression char_literal51_tree=null;
        EvaluationExpression char_literal52_tree=null;
        EvaluationExpression char_literal53_tree=null;
        EvaluationExpression char_literal54_tree=null;
        EvaluationExpression char_literal55_tree=null;
        EvaluationExpression char_literal56_tree=null;
        EvaluationExpression char_literal57_tree=null;
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
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:195:3: ( '[' STAR ']' ( ( '.' methodCall[null] )=> '.' call= methodCall[EvaluationExpression.VALUE] -> ^( EXPRESSION[\"ArrayProjection\"] $call) |path= pathSegment -> ^( EXPRESSION[\"ArrayProjection\"] $path) ) | '[' (pos= INTEGER |pos= UINT ) ']' -> ^( EXPRESSION[\"ArrayAccess\"] ) | '[' (start= INTEGER |start= UINT ) ':' (end= INTEGER |end= UINT ) ']' -> ^( EXPRESSION[\"ArrayAccess\"] ) )
            int alt33=3;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==58) ) {
                switch ( input.LA(2) ) {
                case STAR:
                    {
                    alt33=1;
                    }
                    break;
                case INTEGER:
                    {
                    int LA33_3 = input.LA(3);

                    if ( (LA33_3==59) ) {
                        alt33=2;
                    }
                    else if ( (LA33_3==48) ) {
                        alt33=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 33, 3, input);

                        throw nvae;

                    }
                    }
                    break;
                case UINT:
                    {
                    int LA33_4 = input.LA(3);

                    if ( (LA33_4==59) ) {
                        alt33=2;
                    }
                    else if ( (LA33_4==48) ) {
                        alt33=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 33, 4, input);

                        throw nvae;

                    }
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 33, 1, input);

                    throw nvae;

                }

            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 33, 0, input);

                throw nvae;

            }
            switch (alt33) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:195:5: '[' STAR ']' ( ( '.' methodCall[null] )=> '.' call= methodCall[EvaluationExpression.VALUE] -> ^( EXPRESSION[\"ArrayProjection\"] $call) |path= pathSegment -> ^( EXPRESSION[\"ArrayProjection\"] $path) )
                    {
                    char_literal49=(Token)match(input,58,FOLLOW_58_in_arrayAccess1320); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_58.add(char_literal49);


                    STAR50=(Token)match(input,STAR,FOLLOW_STAR_in_arrayAccess1322); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_STAR.add(STAR50);


                    char_literal51=(Token)match(input,59,FOLLOW_59_in_arrayAccess1324); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_59.add(char_literal51);


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:195:18: ( ( '.' methodCall[null] )=> '.' call= methodCall[EvaluationExpression.VALUE] -> ^( EXPRESSION[\"ArrayProjection\"] $call) |path= pathSegment -> ^( EXPRESSION[\"ArrayProjection\"] $path) )
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==47) ) {
                        int LA29_1 = input.LA(2);

                        if ( (synpred15_Meteor()) ) {
                            alt29=1;
                        }
                        else if ( (true) ) {
                            alt29=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 29, 1, input);

                            throw nvae;

                        }
                    }
                    else if ( ((LA29_0 >= 57 && LA29_0 <= 58)) ) {
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:195:19: ( '.' methodCall[null] )=> '.' call= methodCall[EvaluationExpression.VALUE]
                            {
                            char_literal52=(Token)match(input,47,FOLLOW_47_in_arrayAccess1335); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_47.add(char_literal52);


                            pushFollow(FOLLOW_methodCall_in_arrayAccess1339);
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
                            // 196:4: -> ^( EXPRESSION[\"ArrayProjection\"] $call)
                            {
                                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:196:7: ^( EXPRESSION[\"ArrayProjection\"] $call)
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:197:6: path= pathSegment
                            {
                            pushFollow(FOLLOW_pathSegment_in_arrayAccess1362);
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
                            // 198:4: -> ^( EXPRESSION[\"ArrayProjection\"] $path)
                            {
                                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:198:7: ^( EXPRESSION[\"ArrayProjection\"] $path)
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:199:5: '[' (pos= INTEGER |pos= UINT ) ']'
                    {
                    char_literal53=(Token)match(input,58,FOLLOW_58_in_arrayAccess1383); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_58.add(char_literal53);


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:199:9: (pos= INTEGER |pos= UINT )
                    int alt30=2;
                    int LA30_0 = input.LA(1);

                    if ( (LA30_0==INTEGER) ) {
                        alt30=1;
                    }
                    else if ( (LA30_0==UINT) ) {
                        alt30=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 30, 0, input);

                        throw nvae;

                    }
                    switch (alt30) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:199:10: pos= INTEGER
                            {
                            pos=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_arrayAccess1388); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_INTEGER.add(pos);


                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:199:24: pos= UINT
                            {
                            pos=(Token)match(input,UINT,FOLLOW_UINT_in_arrayAccess1394); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_UINT.add(pos);


                            }
                            break;

                    }


                    char_literal54=(Token)match(input,59,FOLLOW_59_in_arrayAccess1397); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_59.add(char_literal54);


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
                    // 200:3: -> ^( EXPRESSION[\"ArrayAccess\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:200:6: ^( EXPRESSION[\"ArrayAccess\"] )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:201:5: '[' (start= INTEGER |start= UINT ) ':' (end= INTEGER |end= UINT ) ']'
                    {
                    char_literal55=(Token)match(input,58,FOLLOW_58_in_arrayAccess1415); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_58.add(char_literal55);


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:201:9: (start= INTEGER |start= UINT )
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( (LA31_0==INTEGER) ) {
                        alt31=1;
                    }
                    else if ( (LA31_0==UINT) ) {
                        alt31=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 31, 0, input);

                        throw nvae;

                    }
                    switch (alt31) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:201:10: start= INTEGER
                            {
                            start=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_arrayAccess1420); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_INTEGER.add(start);


                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:201:26: start= UINT
                            {
                            start=(Token)match(input,UINT,FOLLOW_UINT_in_arrayAccess1426); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_UINT.add(start);


                            }
                            break;

                    }


                    char_literal56=(Token)match(input,48,FOLLOW_48_in_arrayAccess1429); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_48.add(char_literal56);


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:201:42: (end= INTEGER |end= UINT )
                    int alt32=2;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==INTEGER) ) {
                        alt32=1;
                    }
                    else if ( (LA32_0==UINT) ) {
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:201:43: end= INTEGER
                            {
                            end=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_arrayAccess1434); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_INTEGER.add(end);


                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:201:57: end= UINT
                            {
                            end=(Token)match(input,UINT,FOLLOW_UINT_in_arrayAccess1440); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_UINT.add(end);


                            }
                            break;

                    }


                    char_literal57=(Token)match(input,59,FOLLOW_59_in_arrayAccess1443); if (state.failed) return retval; 
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
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 202:3: -> ^( EXPRESSION[\"ArrayAccess\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:202:6: ^( EXPRESSION[\"ArrayAccess\"] )
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:204:1: valueExpression : ( ( ID '(' )=> functionCall | parenthesesExpression | literal | ( VAR '[' VAR )=> streamIndexAccess | VAR ->| ( ( ID ':' )=>packageName= ID ':' )? constant= ID {...}? => ->| arrayCreation | objectCreation );
    public final MeteorParser.valueExpression_return valueExpression() throws RecognitionException {
        MeteorParser.valueExpression_return retval = new MeteorParser.valueExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token packageName=null;
        Token constant=null;
        Token VAR62=null;
        Token char_literal63=null;
        MeteorParser.functionCall_return functionCall58 =null;

        MeteorParser.parenthesesExpression_return parenthesesExpression59 =null;

        MeteorParser.literal_return literal60 =null;

        MeteorParser.streamIndexAccess_return streamIndexAccess61 =null;

        MeteorParser.arrayCreation_return arrayCreation64 =null;

        MeteorParser.objectCreation_return objectCreation65 =null;


        EvaluationExpression packageName_tree=null;
        EvaluationExpression constant_tree=null;
        EvaluationExpression VAR62_tree=null;
        EvaluationExpression char_literal63_tree=null;
        RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:205:2: ( ( ID '(' )=> functionCall | parenthesesExpression | literal | ( VAR '[' VAR )=> streamIndexAccess | VAR ->| ( ( ID ':' )=>packageName= ID ':' )? constant= ID {...}? => ->| arrayCreation | objectCreation )
            int alt35=8;
            switch ( input.LA(1) ) {
            case ID:
                {
                int LA35_1 = input.LA(2);

                if ( (LA35_1==48) ) {
                    int LA35_7 = input.LA(3);

                    if ( (synpred16_Meteor()) ) {
                        alt35=1;
                    }
                    else if ( (true) ) {
                        alt35=6;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 35, 7, input);

                        throw nvae;

                    }
                }
                else if ( (LA35_1==40) && (synpred16_Meteor())) {
                    alt35=1;
                }
                else if ( (LA35_1==EOF||LA35_1==AND||LA35_1==AS||(LA35_1 >= ID && LA35_1 <= IN)||LA35_1==NOT||LA35_1==OR||(LA35_1 >= SLASH && LA35_1 <= STAR)||(LA35_1 >= 37 && LA35_1 <= 38)||(LA35_1 >= 41 && LA35_1 <= 42)||(LA35_1 >= 44 && LA35_1 <= 45)||LA35_1==47||(LA35_1 >= 49 && LA35_1 <= 51)||(LA35_1 >= 53 && LA35_1 <= 59)||(LA35_1 >= 69 && LA35_1 <= 70)) ) {
                    alt35=6;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 35, 1, input);

                    throw nvae;

                }
                }
                break;
            case 40:
                {
                alt35=2;
                }
                break;
            case DECIMAL:
            case INTEGER:
            case STRING:
            case UINT:
            case 60:
            case 62:
            case 65:
                {
                alt35=3;
                }
                break;
            case VAR:
                {
                int LA35_4 = input.LA(2);

                if ( (LA35_4==58) ) {
                    int LA35_10 = input.LA(3);

                    if ( (LA35_10==STAR) ) {
                        alt35=5;
                    }
                    else if ( (LA35_10==ID) && (synpred17_Meteor())) {
                        alt35=4;
                    }
                    else if ( (LA35_10==40) && (synpred17_Meteor())) {
                        alt35=4;
                    }
                    else if ( (LA35_10==65) && (synpred17_Meteor())) {
                        alt35=4;
                    }
                    else if ( (LA35_10==60) && (synpred17_Meteor())) {
                        alt35=4;
                    }
                    else if ( (LA35_10==DECIMAL) && (synpred17_Meteor())) {
                        alt35=4;
                    }
                    else if ( (LA35_10==STRING) && (synpred17_Meteor())) {
                        alt35=4;
                    }
                    else if ( (LA35_10==UINT) ) {
                        int LA35_18 = input.LA(4);

                        if ( (LA35_18==57) && (synpred17_Meteor())) {
                            alt35=4;
                        }
                        else if ( (LA35_18==47) && (synpred17_Meteor())) {
                            alt35=4;
                        }
                        else if ( (LA35_18==58) && (synpred17_Meteor())) {
                            alt35=4;
                        }
                        else if ( (LA35_18==59) ) {
                            int LA35_27 = input.LA(5);

                            if ( (synpred17_Meteor()) ) {
                                alt35=4;
                            }
                            else if ( (true) ) {
                                alt35=5;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 35, 27, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA35_18==48) ) {
                            alt35=5;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 35, 18, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA35_10==INTEGER) ) {
                        int LA35_19 = input.LA(4);

                        if ( (LA35_19==57) && (synpred17_Meteor())) {
                            alt35=4;
                        }
                        else if ( (LA35_19==47) && (synpred17_Meteor())) {
                            alt35=4;
                        }
                        else if ( (LA35_19==58) && (synpred17_Meteor())) {
                            alt35=4;
                        }
                        else if ( (LA35_19==59) ) {
                            int LA35_28 = input.LA(5);

                            if ( (synpred17_Meteor()) ) {
                                alt35=4;
                            }
                            else if ( (true) ) {
                                alt35=5;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 35, 28, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA35_19==48) ) {
                            alt35=5;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 35, 19, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA35_10==62) && (synpred17_Meteor())) {
                        alt35=4;
                    }
                    else if ( (LA35_10==VAR) && (synpred17_Meteor())) {
                        alt35=4;
                    }
                    else if ( (LA35_10==58) && (synpred17_Meteor())) {
                        alt35=4;
                    }
                    else if ( (LA35_10==68) && (synpred17_Meteor())) {
                        alt35=4;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 35, 10, input);

                        throw nvae;

                    }
                }
                else if ( (LA35_4==EOF||LA35_4==AND||LA35_4==AS||(LA35_4 >= ID && LA35_4 <= IN)||LA35_4==NOT||LA35_4==OR||(LA35_4 >= SLASH && LA35_4 <= STAR)||(LA35_4 >= 37 && LA35_4 <= 38)||(LA35_4 >= 41 && LA35_4 <= 42)||(LA35_4 >= 44 && LA35_4 <= 45)||(LA35_4 >= 47 && LA35_4 <= 51)||(LA35_4 >= 53 && LA35_4 <= 57)||LA35_4==59||(LA35_4 >= 69 && LA35_4 <= 70)) ) {
                    alt35=5;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 35, 4, input);

                    throw nvae;

                }
                }
                break;
            case 58:
                {
                alt35=7;
                }
                break;
            case 68:
                {
                alt35=8;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 35, 0, input);

                throw nvae;

            }

            switch (alt35) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:205:4: ( ID '(' )=> functionCall
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_functionCall_in_valueExpression1475);
                    functionCall58=functionCall();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, functionCall58.getTree());

                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:206:4: parenthesesExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_parenthesesExpression_in_valueExpression1480);
                    parenthesesExpression59=parenthesesExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, parenthesesExpression59.getTree());

                    }
                    break;
                case 3 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:207:4: literal
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_literal_in_valueExpression1486);
                    literal60=literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, literal60.getTree());

                    }
                    break;
                case 4 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:208:5: ( VAR '[' VAR )=> streamIndexAccess
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_streamIndexAccess_in_valueExpression1502);
                    streamIndexAccess61=streamIndexAccess();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, streamIndexAccess61.getTree());

                    }
                    break;
                case 5 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:209:4: VAR
                    {
                    VAR62=(Token)match(input,VAR,FOLLOW_VAR_in_valueExpression1507); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_VAR.add(VAR62);


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
                    // 209:8: ->
                    {
                        adaptor.addChild(root_0,  getInputSelection(VAR62) );

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 6 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:210:5: ( ( ID ':' )=>packageName= ID ':' )? constant= ID {...}? =>
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:210:5: ( ( ID ':' )=>packageName= ID ':' )?
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==ID) ) {
                        int LA34_1 = input.LA(2);

                        if ( (LA34_1==48) ) {
                            int LA34_2 = input.LA(3);

                            if ( (synpred18_Meteor()) ) {
                                alt34=1;
                            }
                        }
                    }
                    switch (alt34) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:210:6: ( ID ':' )=>packageName= ID ':'
                            {
                            packageName=(Token)match(input,ID,FOLLOW_ID_in_valueExpression1527); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_ID.add(packageName);


                            char_literal63=(Token)match(input,48,FOLLOW_48_in_valueExpression1529); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_48.add(char_literal63);


                            }
                            break;

                    }


                    constant=(Token)match(input,ID,FOLLOW_ID_in_valueExpression1535); if (state.failed) return retval; 
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
                    // 211:5: ->
                    {
                        adaptor.addChild(root_0,  getScope((packageName!=null?packageName.getText():null)).getConstantRegistry().get((constant!=null?constant.getText():null)) );

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 7 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:212:4: arrayCreation
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_arrayCreation_in_valueExpression1555);
                    arrayCreation64=arrayCreation();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arrayCreation64.getTree());

                    }
                    break;
                case 8 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:213:4: objectCreation
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_objectCreation_in_valueExpression1561);
                    objectCreation65=objectCreation();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, objectCreation65.getTree());

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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:215:1: operatorExpression : op= operator -> ^( EXPRESSION[\"NestedOperatorExpression\"] ) ;
    public final MeteorParser.operatorExpression_return operatorExpression() throws RecognitionException {
        MeteorParser.operatorExpression_return retval = new MeteorParser.operatorExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.operator_return op =null;


        RewriteRuleSubtreeStream stream_operator=new RewriteRuleSubtreeStream(adaptor,"rule operator");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:216:2: (op= operator -> ^( EXPRESSION[\"NestedOperatorExpression\"] ) )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:216:4: op= operator
            {
            pushFollow(FOLLOW_operator_in_operatorExpression1574);
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
            // 216:16: -> ^( EXPRESSION[\"NestedOperatorExpression\"] )
            {
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:216:19: ^( EXPRESSION[\"NestedOperatorExpression\"] )
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:218:1: parenthesesExpression : ( '(' expression ')' ) -> expression ;
    public final MeteorParser.parenthesesExpression_return parenthesesExpression() throws RecognitionException {
        MeteorParser.parenthesesExpression_return retval = new MeteorParser.parenthesesExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token char_literal66=null;
        Token char_literal68=null;
        MeteorParser.expression_return expression67 =null;


        EvaluationExpression char_literal66_tree=null;
        EvaluationExpression char_literal68_tree=null;
        RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:219:2: ( ( '(' expression ')' ) -> expression )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:219:4: ( '(' expression ')' )
            {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:219:4: ( '(' expression ')' )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:219:5: '(' expression ')'
            {
            char_literal66=(Token)match(input,40,FOLLOW_40_in_parenthesesExpression1593); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_40.add(char_literal66);


            pushFollow(FOLLOW_expression_in_parenthesesExpression1595);
            expression67=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expression.add(expression67.getTree());

            char_literal68=(Token)match(input,41,FOLLOW_41_in_parenthesesExpression1597); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_41.add(char_literal68);


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
            // 219:25: -> expression
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:221:1: methodCall[EvaluationExpression targetExpr] : (packageName= ID ':' )? name= ID '(' ( (param= expression |func= lowerOrderFunction ) ( ',' (param= expression |func= lowerOrderFunction ) )* )? ')' ->;
    public final MeteorParser.methodCall_return methodCall(EvaluationExpression targetExpr) throws RecognitionException {
        MeteorParser.methodCall_return retval = new MeteorParser.methodCall_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token packageName=null;
        Token name=null;
        Token char_literal69=null;
        Token char_literal70=null;
        Token char_literal71=null;
        Token char_literal72=null;
        MeteorParser.expression_return param =null;

        MeteorParser.lowerOrderFunction_return func =null;


        EvaluationExpression packageName_tree=null;
        EvaluationExpression name_tree=null;
        EvaluationExpression char_literal69_tree=null;
        EvaluationExpression char_literal70_tree=null;
        EvaluationExpression char_literal71_tree=null;
        EvaluationExpression char_literal72_tree=null;
        RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
        RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_lowerOrderFunction=new RewriteRuleSubtreeStream(adaptor,"rule lowerOrderFunction");
         List<EvaluationExpression> params = new ArrayList();
                paraphrase.push("a method call"); 
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:225:3: ( (packageName= ID ':' )? name= ID '(' ( (param= expression |func= lowerOrderFunction ) ( ',' (param= expression |func= lowerOrderFunction ) )* )? ')' ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:225:5: (packageName= ID ':' )? name= ID '(' ( (param= expression |func= lowerOrderFunction ) ( ',' (param= expression |func= lowerOrderFunction ) )* )? ')'
            {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:225:5: (packageName= ID ':' )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==ID) ) {
                int LA36_1 = input.LA(2);

                if ( (LA36_1==48) ) {
                    alt36=1;
                }
            }
            switch (alt36) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:225:6: packageName= ID ':'
                    {
                    packageName=(Token)match(input,ID,FOLLOW_ID_in_methodCall1627); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(packageName);


                    char_literal69=(Token)match(input,48,FOLLOW_48_in_methodCall1629); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_48.add(char_literal69);


                    }
                    break;

            }


            name=(Token)match(input,ID,FOLLOW_ID_in_methodCall1635); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(name);


            char_literal70=(Token)match(input,40,FOLLOW_40_in_methodCall1637); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_40.add(char_literal70);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:226:3: ( (param= expression |func= lowerOrderFunction ) ( ',' (param= expression |func= lowerOrderFunction ) )* )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==DECIMAL||LA40_0==FN||LA40_0==ID||LA40_0==INTEGER||(LA40_0 >= STRING && LA40_0 <= UINT)||LA40_0==VAR||LA40_0==36||(LA40_0 >= 39 && LA40_0 <= 40)||LA40_0==43||LA40_0==46||LA40_0==58||LA40_0==60||(LA40_0 >= 62 && LA40_0 <= 63)||LA40_0==65||(LA40_0 >= 67 && LA40_0 <= 68)||LA40_0==71) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:226:4: (param= expression |func= lowerOrderFunction ) ( ',' (param= expression |func= lowerOrderFunction ) )*
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:226:4: (param= expression |func= lowerOrderFunction )
                    int alt37=2;
                    int LA37_0 = input.LA(1);

                    if ( (LA37_0==DECIMAL||LA37_0==ID||LA37_0==INTEGER||(LA37_0 >= STRING && LA37_0 <= UINT)||LA37_0==VAR||LA37_0==36||LA37_0==40||LA37_0==43||LA37_0==46||LA37_0==58||LA37_0==60||(LA37_0 >= 62 && LA37_0 <= 63)||LA37_0==65||(LA37_0 >= 67 && LA37_0 <= 68)||LA37_0==71) ) {
                        alt37=1;
                    }
                    else if ( (LA37_0==FN||LA37_0==39) ) {
                        alt37=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 37, 0, input);

                        throw nvae;

                    }
                    switch (alt37) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:226:5: param= expression
                            {
                            pushFollow(FOLLOW_expression_in_methodCall1646);
                            param=expression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_expression.add(param.getTree());

                            if ( state.backtracking==0 ) { params.add((param!=null?((EvaluationExpression)param.tree):null)); }

                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:226:53: func= lowerOrderFunction
                            {
                            pushFollow(FOLLOW_lowerOrderFunction_in_methodCall1654);
                            func=lowerOrderFunction();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_lowerOrderFunction.add(func.getTree());

                            if ( state.backtracking==0 ) { params.add((func!=null?((EvaluationExpression)func.tree):null)); }

                            }
                            break;

                    }


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:227:3: ( ',' (param= expression |func= lowerOrderFunction ) )*
                    loop39:
                    do {
                        int alt39=2;
                        int LA39_0 = input.LA(1);

                        if ( (LA39_0==44) ) {
                            alt39=1;
                        }


                        switch (alt39) {
                    	case 1 :
                    	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:227:4: ',' (param= expression |func= lowerOrderFunction )
                    	    {
                    	    char_literal71=(Token)match(input,44,FOLLOW_44_in_methodCall1663); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_44.add(char_literal71);


                    	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:227:8: (param= expression |func= lowerOrderFunction )
                    	    int alt38=2;
                    	    int LA38_0 = input.LA(1);

                    	    if ( (LA38_0==DECIMAL||LA38_0==ID||LA38_0==INTEGER||(LA38_0 >= STRING && LA38_0 <= UINT)||LA38_0==VAR||LA38_0==36||LA38_0==40||LA38_0==43||LA38_0==46||LA38_0==58||LA38_0==60||(LA38_0 >= 62 && LA38_0 <= 63)||LA38_0==65||(LA38_0 >= 67 && LA38_0 <= 68)||LA38_0==71) ) {
                    	        alt38=1;
                    	    }
                    	    else if ( (LA38_0==FN||LA38_0==39) ) {
                    	        alt38=2;
                    	    }
                    	    else {
                    	        if (state.backtracking>0) {state.failed=true; return retval;}
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 38, 0, input);

                    	        throw nvae;

                    	    }
                    	    switch (alt38) {
                    	        case 1 :
                    	            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:227:9: param= expression
                    	            {
                    	            pushFollow(FOLLOW_expression_in_methodCall1668);
                    	            param=expression();

                    	            state._fsp--;
                    	            if (state.failed) return retval;
                    	            if ( state.backtracking==0 ) stream_expression.add(param.getTree());

                    	            if ( state.backtracking==0 ) { params.add((param!=null?((EvaluationExpression)param.tree):null)); }

                    	            }
                    	            break;
                    	        case 2 :
                    	            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:227:57: func= lowerOrderFunction
                    	            {
                    	            pushFollow(FOLLOW_lowerOrderFunction_in_methodCall1676);
                    	            func=lowerOrderFunction();

                    	            state._fsp--;
                    	            if (state.failed) return retval;
                    	            if ( state.backtracking==0 ) stream_lowerOrderFunction.add(func.getTree());

                    	            if ( state.backtracking==0 ) { params.add((func!=null?((EvaluationExpression)func.tree):null)); }

                    	            }
                    	            break;

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop39;
                        }
                    } while (true);


                    }
                    break;

            }


            char_literal72=(Token)match(input,41,FOLLOW_41_in_methodCall1688); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_41.add(char_literal72);


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
            // 228:7: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:230:1: functionCall : methodCall[null] ;
    public final MeteorParser.functionCall_return functionCall() throws RecognitionException {
        MeteorParser.functionCall_return retval = new MeteorParser.functionCall_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.methodCall_return methodCall73 =null;



        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:231:2: ( methodCall[null] )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:231:4: methodCall[null]
            {
            root_0 = (EvaluationExpression)adaptor.nil();


            pushFollow(FOLLOW_methodCall_in_functionCall1703);
            methodCall73=methodCall(null);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, methodCall73.getTree());

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


    public static class lowerOrderFunction_return extends ParserRuleReturnScope {
        EvaluationExpression tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "lowerOrderFunction"
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:233:1: lowerOrderFunction : ( '&' (packageName= ID ':' )? name= ID -> ^( EXPRESSION[\"ConstantExpression\"] ) |func= inlineFunction -> ^( EXPRESSION[\"ConstantExpression\"] ) );
    public final MeteorParser.lowerOrderFunction_return lowerOrderFunction() throws RecognitionException {
        MeteorParser.lowerOrderFunction_return retval = new MeteorParser.lowerOrderFunction_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token packageName=null;
        Token name=null;
        Token char_literal74=null;
        Token char_literal75=null;
        MeteorParser.inlineFunction_return func =null;


        EvaluationExpression packageName_tree=null;
        EvaluationExpression name_tree=null;
        EvaluationExpression char_literal74_tree=null;
        EvaluationExpression char_literal75_tree=null;
        RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");
        RewriteRuleSubtreeStream stream_inlineFunction=new RewriteRuleSubtreeStream(adaptor,"rule inlineFunction");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:234:3: ( '&' (packageName= ID ':' )? name= ID -> ^( EXPRESSION[\"ConstantExpression\"] ) |func= inlineFunction -> ^( EXPRESSION[\"ConstantExpression\"] ) )
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==39) ) {
                alt42=1;
            }
            else if ( (LA42_0==FN) ) {
                alt42=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 42, 0, input);

                throw nvae;

            }
            switch (alt42) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:234:5: '&' (packageName= ID ':' )? name= ID
                    {
                    char_literal74=(Token)match(input,39,FOLLOW_39_in_lowerOrderFunction1715); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_39.add(char_literal74);


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:234:9: (packageName= ID ':' )?
                    int alt41=2;
                    int LA41_0 = input.LA(1);

                    if ( (LA41_0==ID) ) {
                        int LA41_1 = input.LA(2);

                        if ( (LA41_1==48) ) {
                            alt41=1;
                        }
                    }
                    switch (alt41) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:234:10: packageName= ID ':'
                            {
                            packageName=(Token)match(input,ID,FOLLOW_ID_in_lowerOrderFunction1720); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_ID.add(packageName);


                            char_literal75=(Token)match(input,48,FOLLOW_48_in_lowerOrderFunction1722); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_48.add(char_literal75);


                            }
                            break;

                    }


                    name=(Token)match(input,ID,FOLLOW_ID_in_lowerOrderFunction1728); if (state.failed) return retval; 
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
                    // 235:9: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:235:12: ^( EXPRESSION[\"ConstantExpression\"] )
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
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:236:5: func= inlineFunction
                    {
                    pushFollow(FOLLOW_inlineFunction_in_lowerOrderFunction1754);
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
                    // 236:25: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:236:28: ^( EXPRESSION[\"ConstantExpression\"] )
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
    // $ANTLR end "lowerOrderFunction"


    public static class fieldAssignment_return extends ParserRuleReturnScope {
        EvaluationExpression tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "fieldAssignment"
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:238:1: fieldAssignment : ( ( ( ID ':' )=> ID ':' expression ->) | VAR ( '.' STAR ->| '=' op= operator {...}? =>|p= contextAwarePathExpression[getVariable($VAR).toInputSelection($operator::result)] ( ':' e2= expression ->| ->) ) );
    public final MeteorParser.fieldAssignment_return fieldAssignment() throws RecognitionException {
        MeteorParser.fieldAssignment_return retval = new MeteorParser.fieldAssignment_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token ID76=null;
        Token char_literal77=null;
        Token VAR79=null;
        Token char_literal80=null;
        Token STAR81=null;
        Token char_literal82=null;
        Token char_literal83=null;
        MeteorParser.operator_return op =null;

        MeteorParser.contextAwarePathExpression_return p =null;

        MeteorParser.expression_return e2 =null;

        MeteorParser.expression_return expression78 =null;


        EvaluationExpression ID76_tree=null;
        EvaluationExpression char_literal77_tree=null;
        EvaluationExpression VAR79_tree=null;
        EvaluationExpression char_literal80_tree=null;
        EvaluationExpression STAR81_tree=null;
        EvaluationExpression char_literal82_tree=null;
        EvaluationExpression char_literal83_tree=null;
        RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_STAR=new RewriteRuleTokenStream(adaptor,"token STAR");
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_52=new RewriteRuleTokenStream(adaptor,"token 52");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_contextAwarePathExpression=new RewriteRuleSubtreeStream(adaptor,"rule contextAwarePathExpression");
        RewriteRuleSubtreeStream stream_operator=new RewriteRuleSubtreeStream(adaptor,"rule operator");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:239:2: ( ( ( ID ':' )=> ID ':' expression ->) | VAR ( '.' STAR ->| '=' op= operator {...}? =>|p= contextAwarePathExpression[getVariable($VAR).toInputSelection($operator::result)] ( ':' e2= expression ->| ->) ) )
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==ID) && (synpred19_Meteor())) {
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:239:4: ( ( ID ':' )=> ID ':' expression ->)
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:239:4: ( ( ID ':' )=> ID ':' expression ->)
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:239:5: ( ID ':' )=> ID ':' expression
                    {
                    ID76=(Token)match(input,ID,FOLLOW_ID_in_fieldAssignment1780); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(ID76);


                    char_literal77=(Token)match(input,48,FOLLOW_48_in_fieldAssignment1782); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_48.add(char_literal77);


                    pushFollow(FOLLOW_expression_in_fieldAssignment1784);
                    expression78=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(expression78.getTree());

                    if ( state.backtracking==0 ) { ((objectCreation_scope)objectCreation_stack.peek()).mappings.add(new ObjectCreation.FieldAssignment((ID76!=null?ID76.getText():null), (expression78!=null?((EvaluationExpression)expression78.tree):null))); }

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
                    // 240:104: ->
                    {
                        root_0 = null;
                    }


                    retval.tree = root_0;
                    }

                    }


                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:241:5: VAR ( '.' STAR ->| '=' op= operator {...}? =>|p= contextAwarePathExpression[getVariable($VAR).toInputSelection($operator::result)] ( ':' e2= expression ->| ->) )
                    {
                    VAR79=(Token)match(input,VAR,FOLLOW_VAR_in_fieldAssignment1801); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_VAR.add(VAR79);


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:242:5: ( '.' STAR ->| '=' op= operator {...}? =>|p= contextAwarePathExpression[getVariable($VAR).toInputSelection($operator::result)] ( ':' e2= expression ->| ->) )
                    int alt44=3;
                    switch ( input.LA(1) ) {
                    case 47:
                        {
                        int LA44_1 = input.LA(2);

                        if ( (LA44_1==STAR) ) {
                            alt44=1;
                        }
                        else if ( (LA44_1==ID) ) {
                            alt44=3;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 44, 1, input);

                            throw nvae;

                        }
                        }
                        break;
                    case 52:
                        {
                        alt44=2;
                        }
                        break;
                    case 57:
                    case 58:
                        {
                        alt44=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 44, 0, input);

                        throw nvae;

                    }

                    switch (alt44) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:242:7: '.' STAR
                            {
                            char_literal80=(Token)match(input,47,FOLLOW_47_in_fieldAssignment1810); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_47.add(char_literal80);


                            STAR81=(Token)match(input,STAR,FOLLOW_STAR_in_fieldAssignment1812); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_STAR.add(STAR81);


                            if ( state.backtracking==0 ) { ((objectCreation_scope)objectCreation_stack.peek()).mappings.add(new ObjectCreation.CopyFields(getInputSelection(VAR79))); }

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
                            // 242:107: ->
                            {
                                root_0 = null;
                            }


                            retval.tree = root_0;
                            }

                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:243:9: '=' op= operator {...}? =>
                            {
                            char_literal82=(Token)match(input,52,FOLLOW_52_in_fieldAssignment1826); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_52.add(char_literal82);


                            pushFollow(FOLLOW_operator_in_fieldAssignment1830);
                            op=operator();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_operator.add(op.getTree());

                            if ( !(( setInnerOutput(VAR79, (op!=null?op.op:null)) )) ) {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                throw new FailedPredicateException(input, "fieldAssignment", " setInnerOutput($VAR, $op.op) ");
                            }

                            }
                            break;
                        case 3 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:244:9: p= contextAwarePathExpression[getVariable($VAR).toInputSelection($operator::result)] ( ':' e2= expression ->| ->)
                            {
                            pushFollow(FOLLOW_contextAwarePathExpression_in_fieldAssignment1845);
                            p=contextAwarePathExpression(getVariable(VAR79).toInputSelection(((operator_scope)operator_stack.peek()).result));

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_contextAwarePathExpression.add(p.getTree());

                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:245:7: ( ':' e2= expression ->| ->)
                            int alt43=2;
                            int LA43_0 = input.LA(1);

                            if ( (LA43_0==48) ) {
                                alt43=1;
                            }
                            else if ( (LA43_0==44||LA43_0==70) ) {
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
                                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:245:9: ':' e2= expression
                                    {
                                    char_literal83=(Token)match(input,48,FOLLOW_48_in_fieldAssignment1856); if (state.failed) return retval; 
                                    if ( state.backtracking==0 ) stream_48.add(char_literal83);


                                    pushFollow(FOLLOW_expression_in_fieldAssignment1860);
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
                                    // 245:112: ->
                                    {
                                        root_0 = null;
                                    }


                                    retval.tree = root_0;
                                    }

                                    }
                                    break;
                                case 2 :
                                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:246:23: 
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
                                    // 246:131: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:251:1: objectCreation : '{' ( fieldAssignment ( ',' fieldAssignment )* ( ',' )? )? '}' -> ^( EXPRESSION[\"ObjectCreation\"] ) ;
    public final MeteorParser.objectCreation_return objectCreation() throws RecognitionException {
        objectCreation_stack.push(new objectCreation_scope());
        MeteorParser.objectCreation_return retval = new MeteorParser.objectCreation_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token char_literal84=null;
        Token char_literal86=null;
        Token char_literal88=null;
        Token char_literal89=null;
        MeteorParser.fieldAssignment_return fieldAssignment85 =null;

        MeteorParser.fieldAssignment_return fieldAssignment87 =null;


        EvaluationExpression char_literal84_tree=null;
        EvaluationExpression char_literal86_tree=null;
        EvaluationExpression char_literal88_tree=null;
        EvaluationExpression char_literal89_tree=null;
        RewriteRuleTokenStream stream_68=new RewriteRuleTokenStream(adaptor,"token 68");
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
        RewriteRuleTokenStream stream_70=new RewriteRuleTokenStream(adaptor,"token 70");
        RewriteRuleSubtreeStream stream_fieldAssignment=new RewriteRuleSubtreeStream(adaptor,"rule fieldAssignment");
         ((objectCreation_scope)objectCreation_stack.peek()).mappings = new ArrayList<ObjectCreation.Mapping>(); 
                paraphrase.push("a json object"); 
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:256:2: ( '{' ( fieldAssignment ( ',' fieldAssignment )* ( ',' )? )? '}' -> ^( EXPRESSION[\"ObjectCreation\"] ) )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:256:4: '{' ( fieldAssignment ( ',' fieldAssignment )* ( ',' )? )? '}'
            {
            char_literal84=(Token)match(input,68,FOLLOW_68_in_objectCreation1925); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_68.add(char_literal84);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:256:8: ( fieldAssignment ( ',' fieldAssignment )* ( ',' )? )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==ID||LA48_0==VAR) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:256:9: fieldAssignment ( ',' fieldAssignment )* ( ',' )?
                    {
                    pushFollow(FOLLOW_fieldAssignment_in_objectCreation1928);
                    fieldAssignment85=fieldAssignment();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_fieldAssignment.add(fieldAssignment85.getTree());

                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:256:25: ( ',' fieldAssignment )*
                    loop46:
                    do {
                        int alt46=2;
                        int LA46_0 = input.LA(1);

                        if ( (LA46_0==44) ) {
                            int LA46_1 = input.LA(2);

                            if ( (LA46_1==ID||LA46_1==VAR) ) {
                                alt46=1;
                            }


                        }


                        switch (alt46) {
                    	case 1 :
                    	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:256:26: ',' fieldAssignment
                    	    {
                    	    char_literal86=(Token)match(input,44,FOLLOW_44_in_objectCreation1931); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_44.add(char_literal86);


                    	    pushFollow(FOLLOW_fieldAssignment_in_objectCreation1933);
                    	    fieldAssignment87=fieldAssignment();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_fieldAssignment.add(fieldAssignment87.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop46;
                        }
                    } while (true);


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:256:48: ( ',' )?
                    int alt47=2;
                    int LA47_0 = input.LA(1);

                    if ( (LA47_0==44) ) {
                        alt47=1;
                    }
                    switch (alt47) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:256:48: ','
                            {
                            char_literal88=(Token)match(input,44,FOLLOW_44_in_objectCreation1937); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_44.add(char_literal88);


                            }
                            break;

                    }


                    }
                    break;

            }


            char_literal89=(Token)match(input,70,FOLLOW_70_in_objectCreation1942); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_70.add(char_literal89);


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
            // 256:59: -> ^( EXPRESSION[\"ObjectCreation\"] )
            {
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:256:62: ^( EXPRESSION[\"ObjectCreation\"] )
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:259:1: literal : (val= 'true' -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= 'false' -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= DECIMAL -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= STRING -> ^( EXPRESSION[\"ConstantExpression\"] ) | (val= UINT |val= INTEGER ) -> ^( EXPRESSION[\"ConstantExpression\"] ) | 'null' ->);
    public final MeteorParser.literal_return literal() throws RecognitionException {
        MeteorParser.literal_return retval = new MeteorParser.literal_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token val=null;
        Token string_literal90=null;

        EvaluationExpression val_tree=null;
        EvaluationExpression string_literal90_tree=null;
        RewriteRuleTokenStream stream_INTEGER=new RewriteRuleTokenStream(adaptor,"token INTEGER");
        RewriteRuleTokenStream stream_DECIMAL=new RewriteRuleTokenStream(adaptor,"token DECIMAL");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleTokenStream stream_62=new RewriteRuleTokenStream(adaptor,"token 62");
        RewriteRuleTokenStream stream_UINT=new RewriteRuleTokenStream(adaptor,"token UINT");
        RewriteRuleTokenStream stream_60=new RewriteRuleTokenStream(adaptor,"token 60");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

         paraphrase.push("a literal"); 
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:262:2: (val= 'true' -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= 'false' -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= DECIMAL -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= STRING -> ^( EXPRESSION[\"ConstantExpression\"] ) | (val= UINT |val= INTEGER ) -> ^( EXPRESSION[\"ConstantExpression\"] ) | 'null' ->)
            int alt50=6;
            switch ( input.LA(1) ) {
            case 65:
                {
                alt50=1;
                }
                break;
            case 60:
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
            case 62:
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:262:4: val= 'true'
                    {
                    val=(Token)match(input,65,FOLLOW_65_in_literal1980); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_65.add(val);


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
                    // 262:15: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:262:18: ^( EXPRESSION[\"ConstantExpression\"] )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:263:4: val= 'false'
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
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 263:16: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:263:19: ^( EXPRESSION[\"ConstantExpression\"] )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:264:4: val= DECIMAL
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
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 264:16: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:264:19: ^( EXPRESSION[\"ConstantExpression\"] )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:265:4: val= STRING
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
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 265:15: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:265:18: ^( EXPRESSION[\"ConstantExpression\"] )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:266:5: (val= UINT |val= INTEGER )
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:266:5: (val= UINT |val= INTEGER )
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:266:6: val= UINT
                            {
                            val=(Token)match(input,UINT,FOLLOW_UINT_in_literal2046); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_UINT.add(val);


                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:266:17: val= INTEGER
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
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 266:30: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:266:33: ^( EXPRESSION[\"ConstantExpression\"] )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:267:5: 'null'
                    {
                    string_literal90=(Token)match(input,62,FOLLOW_62_in_literal2068); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_62.add(string_literal90);


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
                    // 267:12: ->
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


    public static class streamIndexAccess_return extends ParserRuleReturnScope {
        EvaluationExpression tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "streamIndexAccess"
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:269:1: streamIndexAccess : op= VAR {...}? => '[' path= generalPathExpression ']' {...}? ->;
    public final MeteorParser.streamIndexAccess_return streamIndexAccess() throws RecognitionException {
        MeteorParser.streamIndexAccess_return retval = new MeteorParser.streamIndexAccess_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token op=null;
        Token char_literal91=null;
        Token char_literal92=null;
        MeteorParser.generalPathExpression_return path =null;


        EvaluationExpression op_tree=null;
        EvaluationExpression char_literal91_tree=null;
        EvaluationExpression char_literal92_tree=null;
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleSubtreeStream stream_generalPathExpression=new RewriteRuleSubtreeStream(adaptor,"rule generalPathExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:270:3: (op= VAR {...}? => '[' path= generalPathExpression ']' {...}? ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:270:5: op= VAR {...}? => '[' path= generalPathExpression ']' {...}?
            {
            op=(Token)match(input,VAR,FOLLOW_VAR_in_streamIndexAccess2084); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_VAR.add(op);


            if ( !(( getVariable(op) != null )) ) {
                if (state.backtracking>0) {state.failed=true; return retval;}
                throw new FailedPredicateException(input, "streamIndexAccess", " getVariable($op) != null ");
            }

            char_literal91=(Token)match(input,58,FOLLOW_58_in_streamIndexAccess2093); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_58.add(char_literal91);


            pushFollow(FOLLOW_generalPathExpression_in_streamIndexAccess2097);
            path=generalPathExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_generalPathExpression.add(path.getTree());

            char_literal92=(Token)match(input,59,FOLLOW_59_in_streamIndexAccess2099); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_59.add(char_literal92);


            if ( !(( !((path!=null?((EvaluationExpression)path.tree):null) instanceof ConstantExpression) )) ) {
                if (state.backtracking>0) {state.failed=true; return retval;}
                throw new FailedPredicateException(input, "streamIndexAccess", " !($path.tree instanceof ConstantExpression) ");
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
            // 272:3: ->
            {
                adaptor.addChild(root_0,  new StreamIndexExpression(getVariable(op).getStream(), (path!=null?((EvaluationExpression)path.tree):null)) );

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
    // $ANTLR end "streamIndexAccess"


    public static class arrayCreation_return extends ParserRuleReturnScope {
        EvaluationExpression tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "arrayCreation"
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:274:1: arrayCreation : '[' elems+= expression ( ',' elems+= expression )* ( ',' )? ']' -> ^( EXPRESSION[\"ArrayCreation\"] ) ;
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
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
         paraphrase.push("a json array"); 
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:277:2: ( '[' elems+= expression ( ',' elems+= expression )* ( ',' )? ']' -> ^( EXPRESSION[\"ArrayCreation\"] ) )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:277:5: '[' elems+= expression ( ',' elems+= expression )* ( ',' )? ']'
            {
            char_literal93=(Token)match(input,58,FOLLOW_58_in_arrayCreation2128); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_58.add(char_literal93);


            pushFollow(FOLLOW_expression_in_arrayCreation2132);
            elems=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expression.add(elems.getTree());
            if (list_elems==null) list_elems=new ArrayList();
            list_elems.add(elems.getTree());


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:277:27: ( ',' elems+= expression )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==44) ) {
                    int LA51_1 = input.LA(2);

                    if ( (LA51_1==DECIMAL||LA51_1==ID||LA51_1==INTEGER||(LA51_1 >= STRING && LA51_1 <= UINT)||LA51_1==VAR||LA51_1==36||LA51_1==40||LA51_1==43||LA51_1==46||LA51_1==58||LA51_1==60||(LA51_1 >= 62 && LA51_1 <= 63)||LA51_1==65||(LA51_1 >= 67 && LA51_1 <= 68)||LA51_1==71) ) {
                        alt51=1;
                    }


                }


                switch (alt51) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:277:28: ',' elems+= expression
            	    {
            	    char_literal94=(Token)match(input,44,FOLLOW_44_in_arrayCreation2135); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_44.add(char_literal94);


            	    pushFollow(FOLLOW_expression_in_arrayCreation2139);
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


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:277:52: ( ',' )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==44) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:277:52: ','
                    {
                    char_literal95=(Token)match(input,44,FOLLOW_44_in_arrayCreation2143); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_44.add(char_literal95);


                    }
                    break;

            }


            char_literal96=(Token)match(input,59,FOLLOW_59_in_arrayCreation2146); if (state.failed) return retval; 
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
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 277:61: -> ^( EXPRESSION[\"ArrayCreation\"] )
            {
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:277:64: ^( EXPRESSION[\"ArrayCreation\"] )
                {
                EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                root_1 = (EvaluationExpression)adaptor.becomeRoot(
                (EvaluationExpression)adaptor.create(EXPRESSION, "ArrayCreation")
                , root_1);

                adaptor.addChild(root_1,  list_elems.toArray(new EvaluationExpression[list_elems.size()]) );

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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:283:1: operator returns [Operator<?> op=null] : ( readOperator | writeOperator | genericOperator );
    public final MeteorParser.operator_return operator() throws RecognitionException {
        operator_stack.push(new operator_scope());
        MeteorParser.operator_return retval = new MeteorParser.operator_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.readOperator_return readOperator97 =null;

        MeteorParser.writeOperator_return writeOperator98 =null;

        MeteorParser.genericOperator_return genericOperator99 =null;




          if(state.backtracking == 0) 
        	  addScope();

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:294:2: ( readOperator | writeOperator | genericOperator )
            int alt53=3;
            switch ( input.LA(1) ) {
            case 63:
                {
                alt53=1;
                }
                break;
            case 67:
                {
                alt53=2;
                }
                break;
            case ID:
                {
                alt53=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 53, 0, input);

                throw nvae;

            }

            switch (alt53) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:294:4: readOperator
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_readOperator_in_operator2182);
                    readOperator97=readOperator();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, readOperator97.getTree());

                    if ( state.backtracking==0 ) { retval.op = (readOperator97!=null?readOperator97.source:null); }

                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:295:5: writeOperator
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_writeOperator_in_operator2190);
                    writeOperator98=writeOperator();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, writeOperator98.getTree());

                    if ( state.backtracking==0 ) { retval.op = (writeOperator98!=null?writeOperator98.sink:null); }

                    }
                    break;
                case 3 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:296:5: genericOperator
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_genericOperator_in_operator2198);
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
            if ( state.backtracking==0 ) {
              removeScope();
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


    public static class readOperator_return extends ParserRuleReturnScope {
        public Source source;
        EvaluationExpression tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "readOperator"
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:299:1: readOperator returns [Source source] : 'read' ( (packageName= ID ':' )? format= ID )? 'from' ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' ) ( confOption[fileFormatInfo, fileFormat] )* ->;
    public final MeteorParser.readOperator_return readOperator() throws RecognitionException {
        MeteorParser.readOperator_return retval = new MeteorParser.readOperator_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token packageName=null;
        Token format=null;
        Token protocol=null;
        Token filePath=null;
        Token string_literal100=null;
        Token char_literal101=null;
        Token string_literal102=null;
        Token char_literal103=null;
        Token char_literal104=null;
        MeteorParser.confOption_return confOption105 =null;


        EvaluationExpression packageName_tree=null;
        EvaluationExpression format_tree=null;
        EvaluationExpression protocol_tree=null;
        EvaluationExpression filePath_tree=null;
        EvaluationExpression string_literal100_tree=null;
        EvaluationExpression char_literal101_tree=null;
        EvaluationExpression string_literal102_tree=null;
        EvaluationExpression char_literal103_tree=null;
        EvaluationExpression char_literal104_tree=null;
        RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
        RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_63=new RewriteRuleTokenStream(adaptor,"token 63");
        RewriteRuleTokenStream stream_61=new RewriteRuleTokenStream(adaptor,"token 61");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_confOption=new RewriteRuleSubtreeStream(adaptor,"rule confOption");
         
          ConfObjectInfo<? extends SopremoFileFormat> fileFormatInfo = null;
          SopremoFileFormat fileFormat = null;
          String path = null;

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:305:2: ( 'read' ( (packageName= ID ':' )? format= ID )? 'from' ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' ) ( confOption[fileFormatInfo, fileFormat] )* ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:305:4: 'read' ( (packageName= ID ':' )? format= ID )? 'from' ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' ) ( confOption[fileFormatInfo, fileFormat] )*
            {
            string_literal100=(Token)match(input,63,FOLLOW_63_in_readOperator2220); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_63.add(string_literal100);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:305:11: ( (packageName= ID ':' )? format= ID )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==ID) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:305:12: (packageName= ID ':' )? format= ID
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:305:12: (packageName= ID ':' )?
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:305:13: packageName= ID ':'
                            {
                            packageName=(Token)match(input,ID,FOLLOW_ID_in_readOperator2226); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_ID.add(packageName);


                            char_literal101=(Token)match(input,48,FOLLOW_48_in_readOperator2228); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_48.add(char_literal101);


                            }
                            break;

                    }


                    format=(Token)match(input,ID,FOLLOW_ID_in_readOperator2233); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(format);


                    }
                    break;

            }


            string_literal102=(Token)match(input,61,FOLLOW_61_in_readOperator2240); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_61.add(string_literal102);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:306:11: ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' )
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==ID) ) {
                int LA57_1 = input.LA(2);

                if ( (LA57_1==40) ) {
                    alt57=2;
                }
                else if ( (LA57_1==STRING) ) {
                    alt57=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 57, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA57_0==STRING) ) {
                alt57=1;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 57, 0, input);

                throw nvae;

            }
            switch (alt57) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:306:12: (protocol= ID )? filePath= STRING
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:306:20: (protocol= ID )?
                    int alt56=2;
                    int LA56_0 = input.LA(1);

                    if ( (LA56_0==ID) ) {
                        alt56=1;
                    }
                    switch (alt56) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:306:20: protocol= ID
                            {
                            protocol=(Token)match(input,ID,FOLLOW_ID_in_readOperator2245); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_ID.add(protocol);


                            }
                            break;

                    }


                    filePath=(Token)match(input,STRING,FOLLOW_STRING_in_readOperator2250); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_STRING.add(filePath);


                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:306:43: protocol= ID '(' filePath= STRING ')'
                    {
                    protocol=(Token)match(input,ID,FOLLOW_ID_in_readOperator2256); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(protocol);


                    char_literal103=(Token)match(input,40,FOLLOW_40_in_readOperator2258); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_40.add(char_literal103);


                    filePath=(Token)match(input,STRING,FOLLOW_STRING_in_readOperator2262); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_STRING.add(filePath);


                    char_literal104=(Token)match(input,41,FOLLOW_41_in_readOperator2264); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_41.add(char_literal104);


                    }
                    break;

            }


            if ( state.backtracking==0 ) { 
              path = makeFilePath(protocol, (filePath!=null?filePath.getText():null));
              fileFormatInfo = findFormat((packageName!=null?packageName.getText():null), format, path);
              fileFormat = fileFormatInfo.newInstance(); 
            }

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:312:4: ( confOption[fileFormatInfo, fileFormat] )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==ID) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:312:4: confOption[fileFormatInfo, fileFormat]
            	    {
            	    pushFollow(FOLLOW_confOption_in_readOperator2273);
            	    confOption105=confOption(fileFormatInfo, fileFormat);

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_confOption.add(confOption105.getTree());

            	    }
            	    break;

            	default :
            	    break loop58;
                }
            } while (true);


            if ( state.backtracking==0 ) { retval.source = new Source(fileFormat, path); }

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
            // 313:45: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:316:1: writeOperator returns [Sink sink] : 'write' ( (packageName= ID ':' )? format= ID )? from= VAR 'to' ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' ) ( confOption[fileFormatInfo, fileFormat] )* ->;
    public final MeteorParser.writeOperator_return writeOperator() throws RecognitionException {
        MeteorParser.writeOperator_return retval = new MeteorParser.writeOperator_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token packageName=null;
        Token format=null;
        Token from=null;
        Token protocol=null;
        Token filePath=null;
        Token string_literal106=null;
        Token char_literal107=null;
        Token string_literal108=null;
        Token char_literal109=null;
        Token char_literal110=null;
        MeteorParser.confOption_return confOption111 =null;


        EvaluationExpression packageName_tree=null;
        EvaluationExpression format_tree=null;
        EvaluationExpression from_tree=null;
        EvaluationExpression protocol_tree=null;
        EvaluationExpression filePath_tree=null;
        EvaluationExpression string_literal106_tree=null;
        EvaluationExpression char_literal107_tree=null;
        EvaluationExpression string_literal108_tree=null;
        EvaluationExpression char_literal109_tree=null;
        EvaluationExpression char_literal110_tree=null;
        RewriteRuleTokenStream stream_67=new RewriteRuleTokenStream(adaptor,"token 67");
        RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleTokenStream stream_64=new RewriteRuleTokenStream(adaptor,"token 64");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleSubtreeStream stream_confOption=new RewriteRuleSubtreeStream(adaptor,"rule confOption");
         
          ConfObjectInfo<? extends SopremoFileFormat> fileFormatInfo = null;
          SopremoFileFormat fileFormat = null;
          String path = null;

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:322:2: ( 'write' ( (packageName= ID ':' )? format= ID )? from= VAR 'to' ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' ) ( confOption[fileFormatInfo, fileFormat] )* ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:322:4: 'write' ( (packageName= ID ':' )? format= ID )? from= VAR 'to' ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' ) ( confOption[fileFormatInfo, fileFormat] )*
            {
            string_literal106=(Token)match(input,67,FOLLOW_67_in_writeOperator2299); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_67.add(string_literal106);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:322:12: ( (packageName= ID ':' )? format= ID )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==ID) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:322:13: (packageName= ID ':' )? format= ID
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:322:13: (packageName= ID ':' )?
                    int alt59=2;
                    int LA59_0 = input.LA(1);

                    if ( (LA59_0==ID) ) {
                        int LA59_1 = input.LA(2);

                        if ( (LA59_1==48) ) {
                            alt59=1;
                        }
                    }
                    switch (alt59) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:322:14: packageName= ID ':'
                            {
                            packageName=(Token)match(input,ID,FOLLOW_ID_in_writeOperator2305); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_ID.add(packageName);


                            char_literal107=(Token)match(input,48,FOLLOW_48_in_writeOperator2307); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_48.add(char_literal107);


                            }
                            break;

                    }


                    format=(Token)match(input,ID,FOLLOW_ID_in_writeOperator2312); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(format);


                    }
                    break;

            }


            from=(Token)match(input,VAR,FOLLOW_VAR_in_writeOperator2318); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_VAR.add(from);


            string_literal108=(Token)match(input,64,FOLLOW_64_in_writeOperator2324); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_64.add(string_literal108);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:323:9: ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' )
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==ID) ) {
                int LA62_1 = input.LA(2);

                if ( (LA62_1==40) ) {
                    alt62=2;
                }
                else if ( (LA62_1==STRING) ) {
                    alt62=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 62, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA62_0==STRING) ) {
                alt62=1;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 62, 0, input);

                throw nvae;

            }
            switch (alt62) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:323:10: (protocol= ID )? filePath= STRING
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:323:18: (protocol= ID )?
                    int alt61=2;
                    int LA61_0 = input.LA(1);

                    if ( (LA61_0==ID) ) {
                        alt61=1;
                    }
                    switch (alt61) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:323:18: protocol= ID
                            {
                            protocol=(Token)match(input,ID,FOLLOW_ID_in_writeOperator2329); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_ID.add(protocol);


                            }
                            break;

                    }


                    filePath=(Token)match(input,STRING,FOLLOW_STRING_in_writeOperator2334); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_STRING.add(filePath);


                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:323:41: protocol= ID '(' filePath= STRING ')'
                    {
                    protocol=(Token)match(input,ID,FOLLOW_ID_in_writeOperator2340); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(protocol);


                    char_literal109=(Token)match(input,40,FOLLOW_40_in_writeOperator2342); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_40.add(char_literal109);


                    filePath=(Token)match(input,STRING,FOLLOW_STRING_in_writeOperator2346); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_STRING.add(filePath);


                    char_literal110=(Token)match(input,41,FOLLOW_41_in_writeOperator2348); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_41.add(char_literal110);


                    }
                    break;

            }


            if ( state.backtracking==0 ) { 
              path = makeFilePath(protocol, (filePath!=null?filePath.getText():null));
              fileFormatInfo = findFormat((packageName!=null?packageName.getText():null), format, path);
              fileFormat = fileFormatInfo.newInstance();
            }

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:329:5: ( confOption[fileFormatInfo, fileFormat] )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==ID) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:329:5: confOption[fileFormatInfo, fileFormat]
            	    {
            	    pushFollow(FOLLOW_confOption_in_writeOperator2357);
            	    confOption111=confOption(fileFormatInfo, fileFormat);

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_confOption.add(confOption111.getTree());

            	    }
            	    break;

            	default :
            	    break loop63;
                }
            } while (true);


            if ( state.backtracking==0 ) { 
            	retval.sink = new Sink(fileFormat, makeFilePath(protocol, path));
              retval.sink.setInputs(getVariable(from).getStream());
              this.sinks.add(retval.sink);
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
            // 334:3: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:340:1: genericOperator returns [Operator<?> op] : (packageName= ID ':' )? name= ID {...}? => ( operatorFlag[operatorInfo, $op] )* ( ( '[' )=> arrayInput | ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )* ) ( confOption[operatorInfo, $op] )* ->;
    public final MeteorParser.genericOperator_return genericOperator() throws RecognitionException {
        MeteorParser.genericOperator_return retval = new MeteorParser.genericOperator_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token packageName=null;
        Token name=null;
        Token char_literal112=null;
        Token char_literal116=null;
        MeteorParser.operatorFlag_return operatorFlag113 =null;

        MeteorParser.arrayInput_return arrayInput114 =null;

        MeteorParser.input_return input115 =null;

        MeteorParser.input_return input117 =null;

        MeteorParser.confOption_return confOption118 =null;


        EvaluationExpression packageName_tree=null;
        EvaluationExpression name_tree=null;
        EvaluationExpression char_literal112_tree=null;
        EvaluationExpression char_literal116_tree=null;
        RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_input=new RewriteRuleSubtreeStream(adaptor,"rule input");
        RewriteRuleSubtreeStream stream_operatorFlag=new RewriteRuleSubtreeStream(adaptor,"rule operatorFlag");
        RewriteRuleSubtreeStream stream_arrayInput=new RewriteRuleSubtreeStream(adaptor,"rule arrayInput");
        RewriteRuleSubtreeStream stream_confOption=new RewriteRuleSubtreeStream(adaptor,"rule confOption");
         
          ConfObjectInfo<? extends Operator<?>> operatorInfo;

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:343:3: ( (packageName= ID ':' )? name= ID {...}? => ( operatorFlag[operatorInfo, $op] )* ( ( '[' )=> arrayInput | ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )* ) ( confOption[operatorInfo, $op] )* ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:343:5: (packageName= ID ':' )? name= ID {...}? => ( operatorFlag[operatorInfo, $op] )* ( ( '[' )=> arrayInput | ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )* ) ( confOption[operatorInfo, $op] )*
            {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:343:5: (packageName= ID ':' )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==ID) ) {
                int LA64_1 = input.LA(2);

                if ( (LA64_1==48) ) {
                    alt64=1;
                }
            }
            switch (alt64) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:343:6: packageName= ID ':'
                    {
                    packageName=(Token)match(input,ID,FOLLOW_ID_in_genericOperator2388); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(packageName);


                    char_literal112=(Token)match(input,48,FOLLOW_48_in_genericOperator2390); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_48.add(char_literal112);


                    }
                    break;

            }


            name=(Token)match(input,ID,FOLLOW_ID_in_genericOperator2396); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(name);


            if ( !(( (operatorInfo = findOperatorGreedily((packageName!=null?packageName.getText():null), name)) != null )) ) {
                if (state.backtracking>0) {state.failed=true; return retval;}
                throw new FailedPredicateException(input, "genericOperator", " (operatorInfo = findOperatorGreedily($packageName.text, $name)) != null ");
            }

            if ( state.backtracking==0 ) { ((operator_scope)operator_stack.peek()).result = retval.op = operatorInfo.newInstance(); }

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:345:13: ( operatorFlag[operatorInfo, $op] )*
            loop65:
            do {
                int alt65=2;
                int LA65_0 = input.LA(1);

                if ( (LA65_0==ID) ) {
                    alt65=1;
                }


                switch (alt65) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:345:13: operatorFlag[operatorInfo, $op]
            	    {
            	    pushFollow(FOLLOW_operatorFlag_in_genericOperator2404);
            	    operatorFlag113=operatorFlag(operatorInfo, retval.op);

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_operatorFlag.add(operatorFlag113.getTree());

            	    }
            	    break;

            	default :
            	    break loop65;
                }
            } while (true);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:346:2: ( ( '[' )=> arrayInput | ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )* )
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==58) && (synpred20_Meteor())) {
                alt67=1;
            }
            else if ( (LA67_0==VAR) && (synpred21_Meteor())) {
                alt67=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 67, 0, input);

                throw nvae;

            }
            switch (alt67) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:346:2: ( '[' )=> arrayInput
                    {
                    pushFollow(FOLLOW_arrayInput_in_genericOperator2414);
                    arrayInput114=arrayInput();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_arrayInput.add(arrayInput114.getTree());

                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:346:23: ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )*
                    {
                    pushFollow(FOLLOW_input_in_genericOperator2423);
                    input115=input(operatorInfo, retval.op);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_input.add(input115.getTree());

                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:346:56: ( ( ',' )=> ',' input[operatorInfo, $op] )*
                    loop66:
                    do {
                        int alt66=2;
                        int LA66_0 = input.LA(1);

                        if ( (LA66_0==44) ) {
                            int LA66_2 = input.LA(2);

                            if ( (LA66_2==VAR) ) {
                                int LA66_3 = input.LA(3);

                                if ( (synpred22_Meteor()) ) {
                                    alt66=1;
                                }


                            }


                        }


                        switch (alt66) {
                    	case 1 :
                    	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:346:57: ( ',' )=> ',' input[operatorInfo, $op]
                    	    {
                    	    char_literal116=(Token)match(input,44,FOLLOW_44_in_genericOperator2432); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_44.add(char_literal116);


                    	    pushFollow(FOLLOW_input_in_genericOperator2434);
                    	    input117=input(operatorInfo, retval.op);

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_input.add(input117.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop66;
                        }
                    } while (true);


                    }
                    break;

            }


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:347:11: ( confOption[operatorInfo, $op] )*
            loop68:
            do {
                int alt68=2;
                int LA68_0 = input.LA(1);

                if ( (LA68_0==ID) ) {
                    alt68=1;
                }


                switch (alt68) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:347:11: confOption[operatorInfo, $op]
            	    {
            	    pushFollow(FOLLOW_confOption_in_genericOperator2440);
            	    confOption118=confOption(operatorInfo, retval.op);

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_confOption.add(confOption118.getTree());

            	    }
            	    break;

            	default :
            	    break loop68;
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
            // 348:3: ->
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
    // $ANTLR end "genericOperator"


    public static class confOption_return extends ParserRuleReturnScope {
        EvaluationExpression tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "confOption"
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:350:1: confOption[ConfObjectInfo<?> info, ConfigurableSopremoType object] : name= ID expr= contextAwareExpression[null] ->;
    public final MeteorParser.confOption_return confOption(ConfObjectInfo<?> info, ConfigurableSopremoType object) throws RecognitionException {
        MeteorParser.confOption_return retval = new MeteorParser.confOption_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token name=null;
        MeteorParser.contextAwareExpression_return expr =null;


        EvaluationExpression name_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_contextAwareExpression=new RewriteRuleSubtreeStream(adaptor,"rule contextAwareExpression");

         ConfObjectInfo.ConfObjectPropertyInfo property = null;

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:353:3: (name= ID expr= contextAwareExpression[null] ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:354:3: name= ID expr= contextAwareExpression[null]
            {
            name=(Token)match(input,ID,FOLLOW_ID_in_confOption2467); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(name);


            if ( state.backtracking==0 ) { property = findPropertyRelunctantly(info, name); }

            pushFollow(FOLLOW_contextAwareExpression_in_confOption2476);
            expr=contextAwareExpression(null);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_contextAwareExpression.add(expr.getTree());

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
            // 356:80: ->
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


    public static class operatorFlag_return extends ParserRuleReturnScope {
        EvaluationExpression tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "operatorFlag"
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:358:1: operatorFlag[ConfObjectInfo<?> info, ConfigurableSopremoType object] : name= ID {...}? ->;
    public final MeteorParser.operatorFlag_return operatorFlag(ConfObjectInfo<?> info, ConfigurableSopremoType object) throws RecognitionException {
        MeteorParser.operatorFlag_return retval = new MeteorParser.operatorFlag_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token name=null;

        EvaluationExpression name_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");


         ConfObjectInfo.ConfObjectPropertyInfo property = null;

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:362:3: (name= ID {...}? ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:362:5: name= ID {...}?
            {
            name=(Token)match(input,ID,FOLLOW_ID_in_operatorFlag2500); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(name);


            if ( !(( (property = findPropertyRelunctantly(info, name)) != null )) ) {
                if (state.backtracking>0) {state.failed=true; return retval;}
                throw new FailedPredicateException(input, "operatorFlag", " (property = findPropertyRelunctantly(info, $name)) != null ");
            }

            if ( state.backtracking==0 ) { if(!property.isFlag())
                throw new QueryParserException(String.format("Property %s is not a flag", (name!=null?name.getText():null)), name);
              property.setValue(object, true); }

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
            // 365:38: ->
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
    // $ANTLR end "operatorFlag"


    public static class input_return extends ParserRuleReturnScope {
        EvaluationExpression tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "input"
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:367:1: input[ConfObjectInfo<?> info, Operator<?> object] : (name= VAR IN )? from= VAR ({...}? =>expr= contextAwareExpression[new InputSelection($operator::numInputs - 1)] )? ->;
    public final MeteorParser.input_return input(ConfObjectInfo<?> info, Operator<?> object) throws RecognitionException {
        MeteorParser.input_return retval = new MeteorParser.input_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token name=null;
        Token from=null;
        Token IN119=null;
        MeteorParser.contextAwareExpression_return expr =null;


        EvaluationExpression name_tree=null;
        EvaluationExpression from_tree=null;
        EvaluationExpression IN119_tree=null;
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_IN=new RewriteRuleTokenStream(adaptor,"token IN");
        RewriteRuleSubtreeStream stream_contextAwareExpression=new RewriteRuleSubtreeStream(adaptor,"rule contextAwareExpression");

         ConfObjectInfo.ConfObjectIndexedPropertyInfo inputProperty;

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:370:3: ( (name= VAR IN )? from= VAR ({...}? =>expr= contextAwareExpression[new InputSelection($operator::numInputs - 1)] )? ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:370:5: (name= VAR IN )? from= VAR ({...}? =>expr= contextAwareExpression[new InputSelection($operator::numInputs - 1)] )?
            {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:370:5: (name= VAR IN )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==VAR) ) {
                int LA69_1 = input.LA(2);

                if ( (LA69_1==IN) ) {
                    alt69=1;
                }
            }
            switch (alt69) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:370:6: name= VAR IN
                    {
                    name=(Token)match(input,VAR,FOLLOW_VAR_in_input2525); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_VAR.add(name);


                    IN119=(Token)match(input,IN,FOLLOW_IN_in_input2527); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_IN.add(IN119);


                    }
                    break;

            }


            from=(Token)match(input,VAR,FOLLOW_VAR_in_input2533); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_VAR.add(from);


            if ( state.backtracking==0 ) { 
              int inputIndex = ((operator_scope)operator_stack.peek()).numInputs++;
              JsonStreamExpression input = getVariable(from);
              object.setInput(inputIndex, input.getStream());
              
              JsonStreamExpression inputExpression = new JsonStreamExpression(input.getStream(), inputIndex);
              putVariable(name != null ? name : from, inputExpression);
            }

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:379:2: ({...}? =>expr= contextAwareExpression[new InputSelection($operator::numInputs - 1)] )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==DECIMAL||LA70_0==INTEGER||(LA70_0 >= STRING && LA70_0 <= UINT)||LA70_0==VAR||LA70_0==36||LA70_0==40||LA70_0==43||LA70_0==46||LA70_0==58||LA70_0==60||LA70_0==62||LA70_0==65||LA70_0==68||LA70_0==71) && (( (inputProperty = findInputPropertyRelunctantly(info, input.LT(1))) != null ))) {
                alt70=1;
            }
            else if ( (LA70_0==ID) ) {
                int LA70_5 = input.LA(2);

                if ( (( (inputProperty = findInputPropertyRelunctantly(info, input.LT(1))) != null )) ) {
                    alt70=1;
                }
            }
            switch (alt70) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:379:2: {...}? =>expr= contextAwareExpression[new InputSelection($operator::numInputs - 1)]
                    {
                    if ( !(( (inputProperty = findInputPropertyRelunctantly(info, input.LT(1))) != null )) ) {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        throw new FailedPredicateException(input, "input", " (inputProperty = findInputPropertyRelunctantly(info, input.LT(1))) != null ");
                    }

                    if ( state.backtracking==0 ) { this.input.consume(); }

                    pushFollow(FOLLOW_contextAwareExpression_in_input2552);
                    expr=contextAwareExpression(new InputSelection(((operator_scope)operator_stack.peek()).numInputs - 1));

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_contextAwareExpression.add(expr.getTree());

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
            // 382:4: ->
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


    public static class arrayInput_return extends ParserRuleReturnScope {
        EvaluationExpression tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "arrayInput"
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:384:1: arrayInput : '[' names+= VAR ( ',' names+= VAR )? ']' 'in' from= VAR ->;
    public final MeteorParser.arrayInput_return arrayInput() throws RecognitionException {
        MeteorParser.arrayInput_return retval = new MeteorParser.arrayInput_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token from=null;
        Token char_literal120=null;
        Token char_literal121=null;
        Token char_literal122=null;
        Token string_literal123=null;
        Token names=null;
        List list_names=null;

        EvaluationExpression from_tree=null;
        EvaluationExpression char_literal120_tree=null;
        EvaluationExpression char_literal121_tree=null;
        EvaluationExpression char_literal122_tree=null;
        EvaluationExpression string_literal123_tree=null;
        EvaluationExpression names_tree=null;
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_IN=new RewriteRuleTokenStream(adaptor,"token IN");
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:385:3: ( '[' names+= VAR ( ',' names+= VAR )? ']' 'in' from= VAR ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:385:5: '[' names+= VAR ( ',' names+= VAR )? ']' 'in' from= VAR
            {
            char_literal120=(Token)match(input,58,FOLLOW_58_in_arrayInput2570); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_58.add(char_literal120);


            names=(Token)match(input,VAR,FOLLOW_VAR_in_arrayInput2574); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_VAR.add(names);

            if (list_names==null) list_names=new ArrayList();
            list_names.add(names);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:385:20: ( ',' names+= VAR )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==44) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:385:21: ',' names+= VAR
                    {
                    char_literal121=(Token)match(input,44,FOLLOW_44_in_arrayInput2577); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_44.add(char_literal121);


                    names=(Token)match(input,VAR,FOLLOW_VAR_in_arrayInput2581); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_VAR.add(names);

                    if (list_names==null) list_names=new ArrayList();
                    list_names.add(names);


                    }
                    break;

            }


            char_literal122=(Token)match(input,59,FOLLOW_59_in_arrayInput2585); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_59.add(char_literal122);


            string_literal123=(Token)match(input,IN,FOLLOW_IN_in_arrayInput2587); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_IN.add(string_literal123);


            from=(Token)match(input,VAR,FOLLOW_VAR_in_arrayInput2591); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_VAR.add(from);


            if ( state.backtracking==0 ) { 
              ((operator_scope)operator_stack.peek()).result.setInput(0, getVariable(from).getStream());
              for(int index = 0; index < list_names.size(); index++) {
            	  putVariable((Token) list_names.get(index), new JsonStreamExpression(null, index)); 
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
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 391:3: ->
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
    // $ANTLR end "arrayInput"

    // $ANTLR start synpred1_Meteor
    public final void synpred1_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:105:5: ( operatorExpression )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:105:6: operatorExpression
        {
        pushFollow(FOLLOW_operatorExpression_in_synpred1_Meteor387);
        operatorExpression();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred1_Meteor

    // $ANTLR start synpred2_Meteor
    public final void synpred2_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:109:4: ( orExpression '?' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:109:5: orExpression '?'
        {
        pushFollow(FOLLOW_orExpression_in_synpred2_Meteor407);
        orExpression();

        state._fsp--;
        if (state.failed) return ;

        match(input,56,FOLLOW_56_in_synpred2_Meteor409); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred2_Meteor

    // $ANTLR start synpred3_Meteor
    public final void synpred3_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:111:4: ( orExpression IF )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:111:5: orExpression IF
        {
        pushFollow(FOLLOW_orExpression_in_synpred3_Meteor448);
        orExpression();

        state._fsp--;
        if (state.failed) return ;

        match(input,IF,FOLLOW_IF_in_synpred3_Meteor450); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred3_Meteor

    // $ANTLR start synpred4_Meteor
    public final void synpred4_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:159:4: ( '(' ID ')' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:159:5: '(' ID ')'
        {
        match(input,40,FOLLOW_40_in_synpred4_Meteor917); if (state.failed) return ;

        match(input,ID,FOLLOW_ID_in_synpred4_Meteor919); if (state.failed) return ;

        match(input,41,FOLLOW_41_in_synpred4_Meteor921); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred4_Meteor

    // $ANTLR start synpred5_Meteor
    public final void synpred5_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:161:4: ( generalPathExpression AS )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:161:5: generalPathExpression AS
        {
        pushFollow(FOLLOW_generalPathExpression_in_synpred5_Meteor947);
        generalPathExpression();

        state._fsp--;
        if (state.failed) return ;

        match(input,AS,FOLLOW_AS_in_synpred5_Meteor949); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred5_Meteor

    // $ANTLR start synpred6_Meteor
    public final void synpred6_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:167:5: ( pathExpression[EvaluationExpression.VALUE] )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:167:6: pathExpression[EvaluationExpression.VALUE]
        {
        pushFollow(FOLLOW_pathExpression_in_synpred6_Meteor992);
        pathExpression(EvaluationExpression.VALUE);

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred6_Meteor

    // $ANTLR start synpred7_Meteor
    public final void synpred7_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:175:5: ( '?.' ID '(' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:175:6: '?.' ID '('
        {
        match(input,57,FOLLOW_57_in_synpred7_Meteor1048); if (state.failed) return ;

        match(input,ID,FOLLOW_ID_in_synpred7_Meteor1050); if (state.failed) return ;

        match(input,40,FOLLOW_40_in_synpred7_Meteor1052); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred7_Meteor

    // $ANTLR start synpred8_Meteor
    public final void synpred8_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:176:8: ( pathSegment )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:176:9: pathSegment
        {
        pushFollow(FOLLOW_pathSegment_in_synpred8_Meteor1071);
        pathSegment();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred8_Meteor

    // $ANTLR start synpred9_Meteor
    public final void synpred9_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:179:5: ( '.' ID '(' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:179:6: '.' ID '('
        {
        match(input,47,FOLLOW_47_in_synpred9_Meteor1119); if (state.failed) return ;

        match(input,ID,FOLLOW_ID_in_synpred9_Meteor1121); if (state.failed) return ;

        match(input,40,FOLLOW_40_in_synpred9_Meteor1123); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred9_Meteor

    // $ANTLR start synpred10_Meteor
    public final void synpred10_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:180:8: ( pathSegment )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:180:9: pathSegment
        {
        pushFollow(FOLLOW_pathSegment_in_synpred10_Meteor1142);
        pathSegment();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred10_Meteor

    // $ANTLR start synpred11_Meteor
    public final void synpred11_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:183:6: ( pathSegment )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:183:7: pathSegment
        {
        pushFollow(FOLLOW_pathSegment_in_synpred11_Meteor1184);
        pathSegment();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred11_Meteor

    // $ANTLR start synpred12_Meteor
    public final void synpred12_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:190:5: ( '?.' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:190:6: '?.'
        {
        match(input,57,FOLLOW_57_in_synpred12_Meteor1238); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred12_Meteor

    // $ANTLR start synpred13_Meteor
    public final void synpred13_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:191:5: ( '.' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:191:6: '.'
        {
        match(input,47,FOLLOW_47_in_synpred13_Meteor1276); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred13_Meteor

    // $ANTLR start synpred14_Meteor
    public final void synpred14_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:192:5: ( '[' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:192:6: '['
        {
        match(input,58,FOLLOW_58_in_synpred14_Meteor1305); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred14_Meteor

    // $ANTLR start synpred15_Meteor
    public final void synpred15_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:195:19: ( '.' methodCall[null] )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:195:20: '.' methodCall[null]
        {
        match(input,47,FOLLOW_47_in_synpred15_Meteor1328); if (state.failed) return ;

        pushFollow(FOLLOW_methodCall_in_synpred15_Meteor1330);
        methodCall(null);

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred15_Meteor

    // $ANTLR start synpred16_Meteor
    public final void synpred16_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:205:4: ( ID '(' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:205:5: ID '('
        {
        match(input,ID,FOLLOW_ID_in_synpred16_Meteor1469); if (state.failed) return ;

        match(input,40,FOLLOW_40_in_synpred16_Meteor1471); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred16_Meteor

    // $ANTLR start synpred17_Meteor
    public final void synpred17_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:208:5: ( VAR '[' VAR )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:208:6: VAR '[' VAR
        {
        match(input,VAR,FOLLOW_VAR_in_synpred17_Meteor1494); if (state.failed) return ;

        match(input,58,FOLLOW_58_in_synpred17_Meteor1496); if (state.failed) return ;

        match(input,VAR,FOLLOW_VAR_in_synpred17_Meteor1498); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred17_Meteor

    // $ANTLR start synpred18_Meteor
    public final void synpred18_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:210:6: ( ID ':' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:210:7: ID ':'
        {
        match(input,ID,FOLLOW_ID_in_synpred18_Meteor1519); if (state.failed) return ;

        match(input,48,FOLLOW_48_in_synpred18_Meteor1521); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred18_Meteor

    // $ANTLR start synpred19_Meteor
    public final void synpred19_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:239:5: ( ID ':' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:239:6: ID ':'
        {
        match(input,ID,FOLLOW_ID_in_synpred19_Meteor1774); if (state.failed) return ;

        match(input,48,FOLLOW_48_in_synpred19_Meteor1776); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred19_Meteor

    // $ANTLR start synpred20_Meteor
    public final void synpred20_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:346:2: ( '[' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:346:3: '['
        {
        match(input,58,FOLLOW_58_in_synpred20_Meteor2410); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred20_Meteor

    // $ANTLR start synpred21_Meteor
    public final void synpred21_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:346:23: ( VAR )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:346:24: VAR
        {
        match(input,VAR,FOLLOW_VAR_in_synpred21_Meteor2419); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred21_Meteor

    // $ANTLR start synpred22_Meteor
    public final void synpred22_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:346:57: ( ',' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:346:58: ','
        {
        match(input,44,FOLLOW_44_in_synpred22_Meteor2428); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred22_Meteor

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


 

    public static final BitSet FOLLOW_statement_in_script121 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_script123 = new BitSet(new long[]{0x8000000400010002L,0x000000000000000CL});
    public static final BitSet FOLLOW_assignment_in_statement137 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operator_in_statement141 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_packageImport_in_statement145 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functionDefinition_in_statement149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_javaudf_in_statement153 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functionCall_in_statement161 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_packageImport178 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_packageImport182 = new BitSet(new long[]{0x0000100000000002L});
    public static final BitSet FOLLOW_44_in_packageImport193 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_packageImport197 = new BitSet(new long[]{0x0000100000000002L});
    public static final BitSet FOLLOW_VAR_in_assignment214 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_assignment216 = new BitSet(new long[]{0x8000000000010000L,0x0000000000000008L});
    public static final BitSet FOLLOW_operator_in_assignment220 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_functionDefinition237 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_functionDefinition239 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_inlineFunction_in_functionDefinition243 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FN_in_inlineFunction269 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_inlineFunction271 = new BitSet(new long[]{0x0000020000010000L});
    public static final BitSet FOLLOW_ID_in_inlineFunction280 = new BitSet(new long[]{0x0000120000000000L});
    public static final BitSet FOLLOW_44_in_inlineFunction287 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_inlineFunction291 = new BitSet(new long[]{0x0000120000000000L});
    public static final BitSet FOLLOW_41_in_inlineFunction302 = new BitSet(new long[]{0x54004914C0090100L,0x0000000000000092L});
    public static final BitSet FOLLOW_contextAwareExpression_in_inlineFunction314 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_javaudf336 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_javaudf338 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_JAVAUDF_in_javaudf340 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_javaudf342 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_STRING_in_javaudf346 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_javaudf348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ternaryExpression_in_contextAwareExpression376 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operatorExpression_in_expression391 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ternaryExpression_in_expression397 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression415 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_ternaryExpression417 = new BitSet(new long[]{0x54004914C0090100L,0x0000000000000092L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression421 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_ternaryExpression423 = new BitSet(new long[]{0x54004914C0090100L,0x0000000000000092L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression427 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression456 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_IF_in_ternaryExpression458 = new BitSet(new long[]{0x54004914C0090100L,0x0000000000000092L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression462 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression485 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andExpression_in_orExpression498 = new BitSet(new long[]{0x0000000002000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_OR_in_orExpression502 = new BitSet(new long[]{0x54004914C0090100L,0x0000000000000092L});
    public static final BitSet FOLLOW_69_in_orExpression506 = new BitSet(new long[]{0x54004914C0090100L,0x0000000000000092L});
    public static final BitSet FOLLOW_andExpression_in_orExpression511 = new BitSet(new long[]{0x0000000002000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_elementExpression_in_andExpression540 = new BitSet(new long[]{0x0000004000000012L});
    public static final BitSet FOLLOW_AND_in_andExpression544 = new BitSet(new long[]{0x54004914C0090100L,0x0000000000000092L});
    public static final BitSet FOLLOW_38_in_andExpression548 = new BitSet(new long[]{0x54004914C0090100L,0x0000000000000092L});
    public static final BitSet FOLLOW_elementExpression_in_andExpression553 = new BitSet(new long[]{0x0000004000000012L});
    public static final BitSet FOLLOW_comparisonExpression_in_elementExpression582 = new BitSet(new long[]{0x0000000000440002L});
    public static final BitSet FOLLOW_NOT_in_elementExpression587 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_IN_in_elementExpression590 = new BitSet(new long[]{0x54004914C0090100L,0x0000000000000092L});
    public static final BitSet FOLLOW_comparisonExpression_in_elementExpression594 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arithmeticExpression_in_comparisonExpression635 = new BitSet(new long[]{0x00EC002000000002L});
    public static final BitSet FOLLOW_51_in_comparisonExpression641 = new BitSet(new long[]{0x54004914C0090100L,0x0000000000000092L});
    public static final BitSet FOLLOW_55_in_comparisonExpression647 = new BitSet(new long[]{0x54004914C0090100L,0x0000000000000092L});
    public static final BitSet FOLLOW_50_in_comparisonExpression653 = new BitSet(new long[]{0x54004914C0090100L,0x0000000000000092L});
    public static final BitSet FOLLOW_54_in_comparisonExpression659 = new BitSet(new long[]{0x54004914C0090100L,0x0000000000000092L});
    public static final BitSet FOLLOW_53_in_comparisonExpression665 = new BitSet(new long[]{0x54004914C0090100L,0x0000000000000092L});
    public static final BitSet FOLLOW_37_in_comparisonExpression671 = new BitSet(new long[]{0x54004914C0090100L,0x0000000000000092L});
    public static final BitSet FOLLOW_arithmeticExpression_in_comparisonExpression676 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicationExpression_in_arithmeticExpression756 = new BitSet(new long[]{0x0000240000000002L});
    public static final BitSet FOLLOW_42_in_arithmeticExpression762 = new BitSet(new long[]{0x54004914C0090100L,0x0000000000000092L});
    public static final BitSet FOLLOW_45_in_arithmeticExpression768 = new BitSet(new long[]{0x54004914C0090100L,0x0000000000000092L});
    public static final BitSet FOLLOW_multiplicationExpression_in_arithmeticExpression773 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_preincrementExpression_in_multiplicationExpression816 = new BitSet(new long[]{0x0000000030000002L});
    public static final BitSet FOLLOW_STAR_in_multiplicationExpression822 = new BitSet(new long[]{0x54004914C0090100L,0x0000000000000092L});
    public static final BitSet FOLLOW_SLASH_in_multiplicationExpression828 = new BitSet(new long[]{0x54004914C0090100L,0x0000000000000092L});
    public static final BitSet FOLLOW_preincrementExpression_in_multiplicationExpression833 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_preincrementExpression874 = new BitSet(new long[]{0x54004914C0090100L,0x0000000000000092L});
    public static final BitSet FOLLOW_preincrementExpression_in_preincrementExpression876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_preincrementExpression881 = new BitSet(new long[]{0x54004914C0090100L,0x0000000000000092L});
    public static final BitSet FOLLOW_preincrementExpression_in_preincrementExpression883 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unaryExpression_in_preincrementExpression888 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_castExpression_in_unaryExpression907 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_castExpression925 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_castExpression929 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_castExpression931 = new BitSet(new long[]{0x54000104C0090100L,0x0000000000000012L});
    public static final BitSet FOLLOW_generalPathExpression_in_castExpression935 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_generalPathExpression_in_castExpression955 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_AS_in_castExpression957 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_castExpression961 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_generalPathExpression_in_castExpression972 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_valueExpression_in_generalPathExpression984 = new BitSet(new long[]{0x0600800000000002L});
    public static final BitSet FOLLOW_pathExpression_in_generalPathExpression999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathExpression_in_contextAwarePathExpression1028 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_pathExpression1056 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_methodCall_in_pathExpression1060 = new BitSet(new long[]{0x0600800000000002L});
    public static final BitSet FOLLOW_pathExpression_in_pathExpression1077 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_pathExpression1127 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_methodCall_in_pathExpression1131 = new BitSet(new long[]{0x0600800000000002L});
    public static final BitSet FOLLOW_pathExpression_in_pathExpression1148 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathSegment_in_pathExpression1174 = new BitSet(new long[]{0x0600800000000002L});
    public static final BitSet FOLLOW_pathExpression_in_pathExpression1190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_pathSegment1242 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_pathSegment1246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_pathSegment1281 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_pathSegment1285 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayAccess_in_pathSegment1310 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_arrayAccess1320 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_STAR_in_arrayAccess1322 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_arrayAccess1324 = new BitSet(new long[]{0x0600800000000000L});
    public static final BitSet FOLLOW_47_in_arrayAccess1335 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_methodCall_in_arrayAccess1339 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathSegment_in_arrayAccess1362 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_arrayAccess1383 = new BitSet(new long[]{0x0000000080080000L});
    public static final BitSet FOLLOW_INTEGER_in_arrayAccess1388 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_UINT_in_arrayAccess1394 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_arrayAccess1397 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_arrayAccess1415 = new BitSet(new long[]{0x0000000080080000L});
    public static final BitSet FOLLOW_INTEGER_in_arrayAccess1420 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_UINT_in_arrayAccess1426 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_arrayAccess1429 = new BitSet(new long[]{0x0000000080080000L});
    public static final BitSet FOLLOW_INTEGER_in_arrayAccess1434 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_UINT_in_arrayAccess1440 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_arrayAccess1443 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functionCall_in_valueExpression1475 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parenthesesExpression_in_valueExpression1480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_valueExpression1486 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_streamIndexAccess_in_valueExpression1502 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_valueExpression1507 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_valueExpression1527 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_valueExpression1529 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_valueExpression1535 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayCreation_in_valueExpression1555 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objectCreation_in_valueExpression1561 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operator_in_operatorExpression1574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_parenthesesExpression1593 = new BitSet(new long[]{0xD4004914C0090100L,0x000000000000009AL});
    public static final BitSet FOLLOW_expression_in_parenthesesExpression1595 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_parenthesesExpression1597 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_methodCall1627 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_methodCall1629 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_methodCall1635 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_methodCall1637 = new BitSet(new long[]{0xD4004B94C0094100L,0x000000000000009AL});
    public static final BitSet FOLLOW_expression_in_methodCall1646 = new BitSet(new long[]{0x0000120000000000L});
    public static final BitSet FOLLOW_lowerOrderFunction_in_methodCall1654 = new BitSet(new long[]{0x0000120000000000L});
    public static final BitSet FOLLOW_44_in_methodCall1663 = new BitSet(new long[]{0xD4004994C0094100L,0x000000000000009AL});
    public static final BitSet FOLLOW_expression_in_methodCall1668 = new BitSet(new long[]{0x0000120000000000L});
    public static final BitSet FOLLOW_lowerOrderFunction_in_methodCall1676 = new BitSet(new long[]{0x0000120000000000L});
    public static final BitSet FOLLOW_41_in_methodCall1688 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_methodCall_in_functionCall1703 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_lowerOrderFunction1715 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_lowerOrderFunction1720 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_lowerOrderFunction1722 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_lowerOrderFunction1728 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inlineFunction_in_lowerOrderFunction1754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_fieldAssignment1780 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_fieldAssignment1782 = new BitSet(new long[]{0xD4004914C0090100L,0x000000000000009AL});
    public static final BitSet FOLLOW_expression_in_fieldAssignment1784 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_fieldAssignment1801 = new BitSet(new long[]{0x0610800000000000L});
    public static final BitSet FOLLOW_47_in_fieldAssignment1810 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_STAR_in_fieldAssignment1812 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_52_in_fieldAssignment1826 = new BitSet(new long[]{0x8000000000010000L,0x0000000000000008L});
    public static final BitSet FOLLOW_operator_in_fieldAssignment1830 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_contextAwarePathExpression_in_fieldAssignment1845 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_48_in_fieldAssignment1856 = new BitSet(new long[]{0xD4004914C0090100L,0x000000000000009AL});
    public static final BitSet FOLLOW_expression_in_fieldAssignment1860 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_objectCreation1925 = new BitSet(new long[]{0x0000000400010000L,0x0000000000000040L});
    public static final BitSet FOLLOW_fieldAssignment_in_objectCreation1928 = new BitSet(new long[]{0x0000100000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_44_in_objectCreation1931 = new BitSet(new long[]{0x0000000400010000L});
    public static final BitSet FOLLOW_fieldAssignment_in_objectCreation1933 = new BitSet(new long[]{0x0000100000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_44_in_objectCreation1937 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_objectCreation1942 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_literal1980 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_literal1996 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DECIMAL_in_literal2012 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal2028 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_UINT_in_literal2046 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_literal2052 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_literal2068 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_streamIndexAccess2084 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_streamIndexAccess2093 = new BitSet(new long[]{0x54000104C0090100L,0x0000000000000012L});
    public static final BitSet FOLLOW_generalPathExpression_in_streamIndexAccess2097 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_streamIndexAccess2099 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_arrayCreation2128 = new BitSet(new long[]{0xD4004914C0090100L,0x000000000000009AL});
    public static final BitSet FOLLOW_expression_in_arrayCreation2132 = new BitSet(new long[]{0x0800100000000000L});
    public static final BitSet FOLLOW_44_in_arrayCreation2135 = new BitSet(new long[]{0xD4004914C0090100L,0x000000000000009AL});
    public static final BitSet FOLLOW_expression_in_arrayCreation2139 = new BitSet(new long[]{0x0800100000000000L});
    public static final BitSet FOLLOW_44_in_arrayCreation2143 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_arrayCreation2146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_readOperator_in_operator2182 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_writeOperator_in_operator2190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_genericOperator_in_operator2198 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_readOperator2220 = new BitSet(new long[]{0x2000000000010000L});
    public static final BitSet FOLLOW_ID_in_readOperator2226 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_readOperator2228 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_readOperator2233 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_readOperator2240 = new BitSet(new long[]{0x0000000040010000L});
    public static final BitSet FOLLOW_ID_in_readOperator2245 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_STRING_in_readOperator2250 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_ID_in_readOperator2256 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_readOperator2258 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_STRING_in_readOperator2262 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_readOperator2264 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_confOption_in_readOperator2273 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_67_in_writeOperator2299 = new BitSet(new long[]{0x0000000400010000L});
    public static final BitSet FOLLOW_ID_in_writeOperator2305 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_writeOperator2307 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_writeOperator2312 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_VAR_in_writeOperator2318 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_writeOperator2324 = new BitSet(new long[]{0x0000000040010000L});
    public static final BitSet FOLLOW_ID_in_writeOperator2329 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_STRING_in_writeOperator2334 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_ID_in_writeOperator2340 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_writeOperator2342 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_STRING_in_writeOperator2346 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_writeOperator2348 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_confOption_in_writeOperator2357 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_ID_in_genericOperator2388 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_genericOperator2390 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_genericOperator2396 = new BitSet(new long[]{0x0400000400010000L});
    public static final BitSet FOLLOW_operatorFlag_in_genericOperator2404 = new BitSet(new long[]{0x0400000400010000L});
    public static final BitSet FOLLOW_arrayInput_in_genericOperator2414 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_input_in_genericOperator2423 = new BitSet(new long[]{0x0000100000010002L});
    public static final BitSet FOLLOW_44_in_genericOperator2432 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_input_in_genericOperator2434 = new BitSet(new long[]{0x0000100000010002L});
    public static final BitSet FOLLOW_confOption_in_genericOperator2440 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_ID_in_confOption2467 = new BitSet(new long[]{0x54004914C0090100L,0x0000000000000092L});
    public static final BitSet FOLLOW_contextAwareExpression_in_confOption2476 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_operatorFlag2500 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_input2525 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_IN_in_input2527 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_VAR_in_input2533 = new BitSet(new long[]{0x54004914C0090102L,0x0000000000000092L});
    public static final BitSet FOLLOW_contextAwareExpression_in_input2552 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_arrayInput2570 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_VAR_in_arrayInput2574 = new BitSet(new long[]{0x0800100000000000L});
    public static final BitSet FOLLOW_44_in_arrayInput2577 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_VAR_in_arrayInput2581 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_arrayInput2585 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_IN_in_arrayInput2587 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_VAR_in_arrayInput2591 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operatorExpression_in_synpred1_Meteor387 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpression_in_synpred2_Meteor407 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_synpred2_Meteor409 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpression_in_synpred3_Meteor448 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_IF_in_synpred3_Meteor450 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_synpred4_Meteor917 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_synpred4_Meteor919 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_synpred4_Meteor921 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_generalPathExpression_in_synpred5_Meteor947 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_AS_in_synpred5_Meteor949 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathExpression_in_synpred6_Meteor992 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_synpred7_Meteor1048 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_synpred7_Meteor1050 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_synpred7_Meteor1052 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathSegment_in_synpred8_Meteor1071 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_synpred9_Meteor1119 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_synpred9_Meteor1121 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_synpred9_Meteor1123 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathSegment_in_synpred10_Meteor1142 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathSegment_in_synpred11_Meteor1184 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_synpred12_Meteor1238 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_synpred13_Meteor1276 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_synpred14_Meteor1305 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_synpred15_Meteor1328 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_methodCall_in_synpred15_Meteor1330 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred16_Meteor1469 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_synpred16_Meteor1471 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred17_Meteor1494 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_synpred17_Meteor1496 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_VAR_in_synpred17_Meteor1498 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred18_Meteor1519 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_synpred18_Meteor1521 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred19_Meteor1774 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_synpred19_Meteor1776 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_synpred20_Meteor2410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred21_Meteor2419 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_synpred22_Meteor2428 = new BitSet(new long[]{0x0000000000000002L});

}