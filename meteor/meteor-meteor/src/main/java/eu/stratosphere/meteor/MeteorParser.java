// $ANTLR 3.4 /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g 2013-07-03 15:16:37
 
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:78:1: inlineFunction returns [ExpressionFunction func] : FN '(' (param= ID ( ',' param= ID )* )? ')' '{' def= contextAwareExpression[null] '}' ->;
    public final MeteorParser.inlineFunction_return inlineFunction() throws RecognitionException {
        MeteorParser.inlineFunction_return retval = new MeteorParser.inlineFunction_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token param=null;
        Token FN12=null;
        Token char_literal13=null;
        Token char_literal14=null;
        Token char_literal15=null;
        Token char_literal16=null;
        Token char_literal17=null;
        MeteorParser.contextAwareExpression_return def =null;


        EvaluationExpression param_tree=null;
        EvaluationExpression FN12_tree=null;
        EvaluationExpression char_literal13_tree=null;
        EvaluationExpression char_literal14_tree=null;
        EvaluationExpression char_literal15_tree=null;
        EvaluationExpression char_literal16_tree=null;
        EvaluationExpression char_literal17_tree=null;
        RewriteRuleTokenStream stream_68=new RewriteRuleTokenStream(adaptor,"token 68");
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
        RewriteRuleTokenStream stream_FN=new RewriteRuleTokenStream(adaptor,"token FN");
        RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_70=new RewriteRuleTokenStream(adaptor,"token 70");
        RewriteRuleSubtreeStream stream_contextAwareExpression=new RewriteRuleSubtreeStream(adaptor,"rule contextAwareExpression");
         List<Token> params = new ArrayList(); 
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:80:3: ( FN '(' (param= ID ( ',' param= ID )* )? ')' '{' def= contextAwareExpression[null] '}' ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:80:5: FN '(' (param= ID ( ',' param= ID )* )? ')' '{' def= contextAwareExpression[null] '}'
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

            char_literal16=(Token)match(input,68,FOLLOW_68_in_inlineFunction312); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_68.add(char_literal16);


            pushFollow(FOLLOW_contextAwareExpression_in_inlineFunction316);
            def=contextAwareExpression(null);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_contextAwareExpression.add(def.getTree());

            char_literal17=(Token)match(input,70,FOLLOW_70_in_inlineFunction319); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_70.add(char_literal17);


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
        Token char_literal18=null;
        Token JAVAUDF19=null;
        Token char_literal20=null;
        Token char_literal21=null;

        EvaluationExpression name_tree=null;
        EvaluationExpression path_tree=null;
        EvaluationExpression char_literal18_tree=null;
        EvaluationExpression JAVAUDF19_tree=null;
        EvaluationExpression char_literal20_tree=null;
        EvaluationExpression char_literal21_tree=null;
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
            name=(Token)match(input,ID,FOLLOW_ID_in_javaudf339); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(name);


            char_literal18=(Token)match(input,52,FOLLOW_52_in_javaudf341); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_52.add(char_literal18);


            JAVAUDF19=(Token)match(input,JAVAUDF,FOLLOW_JAVAUDF_in_javaudf343); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_JAVAUDF.add(JAVAUDF19);


            char_literal20=(Token)match(input,40,FOLLOW_40_in_javaudf345); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_40.add(char_literal20);


            path=(Token)match(input,STRING,FOLLOW_STRING_in_javaudf349); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_STRING.add(path);


            char_literal21=(Token)match(input,41,FOLLOW_41_in_javaudf351); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_41.add(char_literal21);


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

        MeteorParser.ternaryExpression_return ternaryExpression22 =null;



         ((contextAwareExpression_scope)contextAwareExpression_stack.peek()).context = contextExpression; 
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:102:3: ( ternaryExpression )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:102:5: ternaryExpression
            {
            root_0 = (EvaluationExpression)adaptor.nil();


            pushFollow(FOLLOW_ternaryExpression_in_contextAwareExpression379);
            ternaryExpression22=ternaryExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, ternaryExpression22.getTree());

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

        MeteorParser.operatorExpression_return operatorExpression23 =null;

        MeteorParser.ternaryExpression_return ternaryExpression24 =null;



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
            else if ( (LA6_0==DECIMAL||LA6_0==FN||LA6_0==INTEGER||(LA6_0 >= STRING && LA6_0 <= UINT)||LA6_0==VAR||LA6_0==36||(LA6_0 >= 39 && LA6_0 <= 40)||LA6_0==43||LA6_0==46||LA6_0==58||LA6_0==60||LA6_0==62||LA6_0==65||LA6_0==68||LA6_0==71) ) {
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


                    pushFollow(FOLLOW_operatorExpression_in_expression394);
                    operatorExpression23=operatorExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, operatorExpression23.getTree());

                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:106:5: ternaryExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_ternaryExpression_in_expression400);
                    ternaryExpression24=ternaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ternaryExpression24.getTree());

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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:108:1: ternaryExpression : ( ( orExpression '?' )=>ifClause= orExpression '?' (ifExpr= orExpression )? ':' elseExpr= orExpression -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause) | ( orExpression IF )=>ifExpr2= orExpression IF ifClause2= orExpression -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause2 $ifExpr2) | orExpression );
    public final MeteorParser.ternaryExpression_return ternaryExpression() throws RecognitionException {
        MeteorParser.ternaryExpression_return retval = new MeteorParser.ternaryExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token char_literal25=null;
        Token char_literal26=null;
        Token IF27=null;
        MeteorParser.orExpression_return ifClause =null;

        MeteorParser.orExpression_return ifExpr =null;

        MeteorParser.orExpression_return elseExpr =null;

        MeteorParser.orExpression_return ifExpr2 =null;

        MeteorParser.orExpression_return ifClause2 =null;

        MeteorParser.orExpression_return orExpression28 =null;


        EvaluationExpression char_literal25_tree=null;
        EvaluationExpression char_literal26_tree=null;
        EvaluationExpression IF27_tree=null;
        RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
        RewriteRuleTokenStream stream_56=new RewriteRuleTokenStream(adaptor,"token 56");
        RewriteRuleTokenStream stream_IF=new RewriteRuleTokenStream(adaptor,"token IF");
        RewriteRuleSubtreeStream stream_orExpression=new RewriteRuleSubtreeStream(adaptor,"rule orExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:109:2: ( ( orExpression '?' )=>ifClause= orExpression '?' (ifExpr= orExpression )? ':' elseExpr= orExpression -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause) | ( orExpression IF )=>ifExpr2= orExpression IF ifClause2= orExpression -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause2 $ifExpr2) | orExpression )
            int alt8=3;
            switch ( input.LA(1) ) {
            case 43:
                {
                int LA8_1 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt8=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt8=2;
                }
                else if ( (true) ) {
                    alt8=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 1, input);

                    throw nvae;

                }
                }
                break;
            case 46:
                {
                int LA8_2 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt8=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt8=2;
                }
                else if ( (true) ) {
                    alt8=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 2, input);

                    throw nvae;

                }
                }
                break;
            case 36:
            case 71:
                {
                int LA8_3 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt8=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt8=2;
                }
                else if ( (true) ) {
                    alt8=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 3, input);

                    throw nvae;

                }
                }
                break;
            case 40:
                {
                int LA8_4 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt8=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt8=2;
                }
                else if ( (true) ) {
                    alt8=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 4, input);

                    throw nvae;

                }
                }
                break;
            case ID:
                {
                int LA8_5 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt8=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt8=2;
                }
                else if ( (true) ) {
                    alt8=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 5, input);

                    throw nvae;

                }
                }
                break;
            case 39:
                {
                int LA8_6 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt8=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt8=2;
                }
                else if ( (true) ) {
                    alt8=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 6, input);

                    throw nvae;

                }
                }
                break;
            case FN:
                {
                int LA8_7 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt8=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt8=2;
                }
                else if ( (true) ) {
                    alt8=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 7, input);

                    throw nvae;

                }
                }
                break;
            case 65:
                {
                int LA8_8 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt8=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt8=2;
                }
                else if ( (true) ) {
                    alt8=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 8, input);

                    throw nvae;

                }
                }
                break;
            case 60:
                {
                int LA8_9 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt8=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt8=2;
                }
                else if ( (true) ) {
                    alt8=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 9, input);

                    throw nvae;

                }
                }
                break;
            case DECIMAL:
                {
                int LA8_10 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt8=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt8=2;
                }
                else if ( (true) ) {
                    alt8=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 10, input);

                    throw nvae;

                }
                }
                break;
            case STRING:
                {
                int LA8_11 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt8=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt8=2;
                }
                else if ( (true) ) {
                    alt8=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 11, input);

                    throw nvae;

                }
                }
                break;
            case UINT:
                {
                int LA8_12 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt8=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt8=2;
                }
                else if ( (true) ) {
                    alt8=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 12, input);

                    throw nvae;

                }
                }
                break;
            case INTEGER:
                {
                int LA8_13 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt8=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt8=2;
                }
                else if ( (true) ) {
                    alt8=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 13, input);

                    throw nvae;

                }
                }
                break;
            case 62:
                {
                int LA8_14 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt8=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt8=2;
                }
                else if ( (true) ) {
                    alt8=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 14, input);

                    throw nvae;

                }
                }
                break;
            case VAR:
                {
                int LA8_15 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt8=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt8=2;
                }
                else if ( (true) ) {
                    alt8=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 15, input);

                    throw nvae;

                }
                }
                break;
            case 58:
                {
                int LA8_16 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt8=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt8=2;
                }
                else if ( (true) ) {
                    alt8=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 16, input);

                    throw nvae;

                }
                }
                break;
            case 68:
                {
                int LA8_17 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt8=1;
                }
                else if ( (synpred3_Meteor()) ) {
                    alt8=2;
                }
                else if ( (true) ) {
                    alt8=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 17, input);

                    throw nvae;

                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;

            }

            switch (alt8) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:109:4: ( orExpression '?' )=>ifClause= orExpression '?' (ifExpr= orExpression )? ':' elseExpr= orExpression
                    {
                    pushFollow(FOLLOW_orExpression_in_ternaryExpression418);
                    ifClause=orExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_orExpression.add(ifClause.getTree());

                    char_literal25=(Token)match(input,56,FOLLOW_56_in_ternaryExpression420); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_56.add(char_literal25);


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:109:57: (ifExpr= orExpression )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0==DECIMAL||LA7_0==FN||LA7_0==ID||LA7_0==INTEGER||(LA7_0 >= STRING && LA7_0 <= UINT)||LA7_0==VAR||LA7_0==36||(LA7_0 >= 39 && LA7_0 <= 40)||LA7_0==43||LA7_0==46||LA7_0==58||LA7_0==60||LA7_0==62||LA7_0==65||LA7_0==68||LA7_0==71) ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:109:57: ifExpr= orExpression
                            {
                            pushFollow(FOLLOW_orExpression_in_ternaryExpression424);
                            ifExpr=orExpression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_orExpression.add(ifExpr.getTree());

                            }
                            break;

                    }


                    char_literal26=(Token)match(input,48,FOLLOW_48_in_ternaryExpression427); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_48.add(char_literal26);


                    pushFollow(FOLLOW_orExpression_in_ternaryExpression431);
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:111:4: ( orExpression IF )=>ifExpr2= orExpression IF ifClause2= orExpression
                    {
                    pushFollow(FOLLOW_orExpression_in_ternaryExpression460);
                    ifExpr2=orExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_orExpression.add(ifExpr2.getTree());

                    IF27=(Token)match(input,IF,FOLLOW_IF_in_ternaryExpression462); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_IF.add(IF27);


                    pushFollow(FOLLOW_orExpression_in_ternaryExpression466);
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


                    pushFollow(FOLLOW_orExpression_in_ternaryExpression489);
                    orExpression28=orExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, orExpression28.getTree());

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

        Token OR29=null;
        Token string_literal30=null;
        List list_exprs=null;
        RuleReturnScope exprs = null;
        EvaluationExpression OR29_tree=null;
        EvaluationExpression string_literal30_tree=null;
        RewriteRuleTokenStream stream_69=new RewriteRuleTokenStream(adaptor,"token 69");
        RewriteRuleTokenStream stream_OR=new RewriteRuleTokenStream(adaptor,"token OR");
        RewriteRuleSubtreeStream stream_andExpression=new RewriteRuleSubtreeStream(adaptor,"rule andExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:116:3: (exprs+= andExpression ( ( OR | '||' ) exprs+= andExpression )* -> { $exprs.size() == 1 }? ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:116:5: exprs+= andExpression ( ( OR | '||' ) exprs+= andExpression )*
            {
            pushFollow(FOLLOW_andExpression_in_orExpression502);
            exprs=andExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_andExpression.add(exprs.getTree());
            if (list_exprs==null) list_exprs=new ArrayList();
            list_exprs.add(exprs.getTree());


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:116:26: ( ( OR | '||' ) exprs+= andExpression )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==OR||LA10_0==69) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:116:27: ( OR | '||' ) exprs+= andExpression
            	    {
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:116:27: ( OR | '||' )
            	    int alt9=2;
            	    int LA9_0 = input.LA(1);

            	    if ( (LA9_0==OR) ) {
            	        alt9=1;
            	    }
            	    else if ( (LA9_0==69) ) {
            	        alt9=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 9, 0, input);

            	        throw nvae;

            	    }
            	    switch (alt9) {
            	        case 1 :
            	            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:116:28: OR
            	            {
            	            OR29=(Token)match(input,OR,FOLLOW_OR_in_orExpression506); if (state.failed) return retval; 
            	            if ( state.backtracking==0 ) stream_OR.add(OR29);


            	            }
            	            break;
            	        case 2 :
            	            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:116:33: '||'
            	            {
            	            string_literal30=(Token)match(input,69,FOLLOW_69_in_orExpression510); if (state.failed) return retval; 
            	            if ( state.backtracking==0 ) stream_69.add(string_literal30);


            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_andExpression_in_orExpression515);
            	    exprs=andExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_andExpression.add(exprs.getTree());
            	    if (list_exprs==null) list_exprs=new ArrayList();
            	    list_exprs.add(exprs.getTree());


            	    }
            	    break;

            	default :
            	    break loop10;
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

        Token AND31=null;
        Token string_literal32=null;
        List list_exprs=null;
        RuleReturnScope exprs = null;
        EvaluationExpression AND31_tree=null;
        EvaluationExpression string_literal32_tree=null;
        RewriteRuleTokenStream stream_AND=new RewriteRuleTokenStream(adaptor,"token AND");
        RewriteRuleTokenStream stream_38=new RewriteRuleTokenStream(adaptor,"token 38");
        RewriteRuleSubtreeStream stream_elementExpression=new RewriteRuleSubtreeStream(adaptor,"rule elementExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:121:3: (exprs+= elementExpression ( ( AND | '&&' ) exprs+= elementExpression )* -> { $exprs.size() == 1 }? ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:121:5: exprs+= elementExpression ( ( AND | '&&' ) exprs+= elementExpression )*
            {
            pushFollow(FOLLOW_elementExpression_in_andExpression544);
            exprs=elementExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_elementExpression.add(exprs.getTree());
            if (list_exprs==null) list_exprs=new ArrayList();
            list_exprs.add(exprs.getTree());


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:121:30: ( ( AND | '&&' ) exprs+= elementExpression )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==AND||LA12_0==38) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:121:31: ( AND | '&&' ) exprs+= elementExpression
            	    {
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:121:31: ( AND | '&&' )
            	    int alt11=2;
            	    int LA11_0 = input.LA(1);

            	    if ( (LA11_0==AND) ) {
            	        alt11=1;
            	    }
            	    else if ( (LA11_0==38) ) {
            	        alt11=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 11, 0, input);

            	        throw nvae;

            	    }
            	    switch (alt11) {
            	        case 1 :
            	            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:121:32: AND
            	            {
            	            AND31=(Token)match(input,AND,FOLLOW_AND_in_andExpression548); if (state.failed) return retval; 
            	            if ( state.backtracking==0 ) stream_AND.add(AND31);


            	            }
            	            break;
            	        case 2 :
            	            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:121:38: '&&'
            	            {
            	            string_literal32=(Token)match(input,38,FOLLOW_38_in_andExpression552); if (state.failed) return retval; 
            	            if ( state.backtracking==0 ) stream_38.add(string_literal32);


            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_elementExpression_in_andExpression557);
            	    exprs=elementExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_elementExpression.add(exprs.getTree());
            	    if (list_exprs==null) list_exprs=new ArrayList();
            	    list_exprs.add(exprs.getTree());


            	    }
            	    break;

            	default :
            	    break loop12;
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
        Token IN33=null;
        MeteorParser.comparisonExpression_return elem =null;

        MeteorParser.comparisonExpression_return set =null;


        EvaluationExpression not_tree=null;
        EvaluationExpression IN33_tree=null;
        RewriteRuleTokenStream stream_IN=new RewriteRuleTokenStream(adaptor,"token IN");
        RewriteRuleTokenStream stream_NOT=new RewriteRuleTokenStream(adaptor,"token NOT");
        RewriteRuleSubtreeStream stream_comparisonExpression=new RewriteRuleSubtreeStream(adaptor,"rule comparisonExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:126:2: (elem= comparisonExpression ( (not= NOT )? IN set= comparisonExpression )? -> { set == null }? $elem -> ^( EXPRESSION[\"ElementInSetExpression\"] $elem $set) )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:126:4: elem= comparisonExpression ( (not= NOT )? IN set= comparisonExpression )?
            {
            pushFollow(FOLLOW_comparisonExpression_in_elementExpression586);
            elem=comparisonExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_comparisonExpression.add(elem.getTree());

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:126:30: ( (not= NOT )? IN set= comparisonExpression )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==IN||LA14_0==NOT) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:126:31: (not= NOT )? IN set= comparisonExpression
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:126:34: (not= NOT )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==NOT) ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:126:34: not= NOT
                            {
                            not=(Token)match(input,NOT,FOLLOW_NOT_in_elementExpression591); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_NOT.add(not);


                            }
                            break;

                    }


                    IN33=(Token)match(input,IN,FOLLOW_IN_in_elementExpression594); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_IN.add(IN33);


                    pushFollow(FOLLOW_comparisonExpression_in_elementExpression598);
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
            pushFollow(FOLLOW_arithmeticExpression_in_comparisonExpression639);
            e1=arithmeticExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_arithmeticExpression.add(e1.getTree());

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:132:28: ( (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' ) e2= arithmeticExpression )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==37||(LA16_0 >= 50 && LA16_0 <= 51)||(LA16_0 >= 53 && LA16_0 <= 55)) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:132:29: (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' ) e2= arithmeticExpression
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:132:29: (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' )
                    int alt15=6;
                    switch ( input.LA(1) ) {
                    case 51:
                        {
                        alt15=1;
                        }
                        break;
                    case 55:
                        {
                        alt15=2;
                        }
                        break;
                    case 50:
                        {
                        alt15=3;
                        }
                        break;
                    case 54:
                        {
                        alt15=4;
                        }
                        break;
                    case 53:
                        {
                        alt15=5;
                        }
                        break;
                    case 37:
                        {
                        alt15=6;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 15, 0, input);

                        throw nvae;

                    }

                    switch (alt15) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:132:30: s= '<='
                            {
                            s=(Token)match(input,51,FOLLOW_51_in_comparisonExpression645); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_51.add(s);


                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:132:39: s= '>='
                            {
                            s=(Token)match(input,55,FOLLOW_55_in_comparisonExpression651); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_55.add(s);


                            }
                            break;
                        case 3 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:132:48: s= '<'
                            {
                            s=(Token)match(input,50,FOLLOW_50_in_comparisonExpression657); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_50.add(s);


                            }
                            break;
                        case 4 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:132:56: s= '>'
                            {
                            s=(Token)match(input,54,FOLLOW_54_in_comparisonExpression663); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_54.add(s);


                            }
                            break;
                        case 5 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:132:64: s= '=='
                            {
                            s=(Token)match(input,53,FOLLOW_53_in_comparisonExpression669); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_53.add(s);


                            }
                            break;
                        case 6 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:132:73: s= '!='
                            {
                            s=(Token)match(input,37,FOLLOW_37_in_comparisonExpression675); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_37.add(s);


                            }
                            break;

                    }


                    pushFollow(FOLLOW_arithmeticExpression_in_comparisonExpression680);
                    e2=arithmeticExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_arithmeticExpression.add(e2.getTree());

                    }
                    break;

            }


            // AST REWRITE
            // elements: e2, e2, e1, e1, e1, e2, e1
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
            pushFollow(FOLLOW_multiplicationExpression_in_arithmeticExpression760);
            e1=multiplicationExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_multiplicationExpression.add(e1.getTree());

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:139:32: ( (s= '+' |s= '-' ) e2= multiplicationExpression )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==42||LA18_0==45) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:139:33: (s= '+' |s= '-' ) e2= multiplicationExpression
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:139:33: (s= '+' |s= '-' )
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==42) ) {
                        alt17=1;
                    }
                    else if ( (LA17_0==45) ) {
                        alt17=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 17, 0, input);

                        throw nvae;

                    }
                    switch (alt17) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:139:34: s= '+'
                            {
                            s=(Token)match(input,42,FOLLOW_42_in_arithmeticExpression766); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_42.add(s);


                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:139:42: s= '-'
                            {
                            s=(Token)match(input,45,FOLLOW_45_in_arithmeticExpression772); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_45.add(s);


                            }
                            break;

                    }


                    pushFollow(FOLLOW_multiplicationExpression_in_arithmeticExpression777);
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
            pushFollow(FOLLOW_preincrementExpression_in_multiplicationExpression820);
            e1=preincrementExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_preincrementExpression.add(e1.getTree());

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:145:30: ( (s= '*' |s= SLASH ) e2= preincrementExpression )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( ((LA20_0 >= SLASH && LA20_0 <= STAR)) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:145:31: (s= '*' |s= SLASH ) e2= preincrementExpression
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:145:31: (s= '*' |s= SLASH )
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==STAR) ) {
                        alt19=1;
                    }
                    else if ( (LA19_0==SLASH) ) {
                        alt19=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 19, 0, input);

                        throw nvae;

                    }
                    switch (alt19) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:145:32: s= '*'
                            {
                            s=(Token)match(input,STAR,FOLLOW_STAR_in_multiplicationExpression826); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_STAR.add(s);


                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:145:40: s= SLASH
                            {
                            s=(Token)match(input,SLASH,FOLLOW_SLASH_in_multiplicationExpression832); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_SLASH.add(s);


                            }
                            break;

                    }


                    pushFollow(FOLLOW_preincrementExpression_in_multiplicationExpression837);
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

        Token string_literal34=null;
        Token string_literal36=null;
        MeteorParser.preincrementExpression_return preincrementExpression35 =null;

        MeteorParser.preincrementExpression_return preincrementExpression37 =null;

        MeteorParser.unaryExpression_return unaryExpression38 =null;


        EvaluationExpression string_literal34_tree=null;
        EvaluationExpression string_literal36_tree=null;

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:151:2: ( '++' preincrementExpression | '--' preincrementExpression | unaryExpression )
            int alt21=3;
            switch ( input.LA(1) ) {
            case 43:
                {
                alt21=1;
                }
                break;
            case 46:
                {
                alt21=2;
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
            case 62:
            case 65:
            case 68:
            case 71:
                {
                alt21=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;

            }

            switch (alt21) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:151:4: '++' preincrementExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    string_literal34=(Token)match(input,43,FOLLOW_43_in_preincrementExpression878); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal34_tree = 
                    (EvaluationExpression)adaptor.create(string_literal34)
                    ;
                    adaptor.addChild(root_0, string_literal34_tree);
                    }

                    pushFollow(FOLLOW_preincrementExpression_in_preincrementExpression880);
                    preincrementExpression35=preincrementExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, preincrementExpression35.getTree());

                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:152:4: '--' preincrementExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    string_literal36=(Token)match(input,46,FOLLOW_46_in_preincrementExpression885); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal36_tree = 
                    (EvaluationExpression)adaptor.create(string_literal36)
                    ;
                    adaptor.addChild(root_0, string_literal36_tree);
                    }

                    pushFollow(FOLLOW_preincrementExpression_in_preincrementExpression887);
                    preincrementExpression37=preincrementExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, preincrementExpression37.getTree());

                    }
                    break;
                case 3 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:153:4: unaryExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_unaryExpression_in_preincrementExpression892);
                    unaryExpression38=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression38.getTree());

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

        Token set39=null;
        MeteorParser.castExpression_return castExpression40 =null;


        EvaluationExpression set39_tree=null;

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:156:2: ( ( '!' | '~' )? castExpression )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:156:4: ( '!' | '~' )? castExpression
            {
            root_0 = (EvaluationExpression)adaptor.nil();


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:156:4: ( '!' | '~' )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==36||LA22_0==71) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:
                    {
                    set39=(Token)input.LT(1);

                    if ( input.LA(1)==36||input.LA(1)==71 ) {
                        input.consume();
                        if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                        (EvaluationExpression)adaptor.create(set39)
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


            pushFollow(FOLLOW_castExpression_in_unaryExpression911);
            castExpression40=castExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, castExpression40.getTree());

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
        Token char_literal41=null;
        Token char_literal42=null;
        Token AS43=null;
        MeteorParser.generalPathExpression_return expr =null;

        MeteorParser.generalPathExpression_return generalPathExpression44 =null;


        EvaluationExpression type_tree=null;
        EvaluationExpression char_literal41_tree=null;
        EvaluationExpression char_literal42_tree=null;
        EvaluationExpression AS43_tree=null;
        RewriteRuleTokenStream stream_AS=new RewriteRuleTokenStream(adaptor,"token AS");
        RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_generalPathExpression=new RewriteRuleSubtreeStream(adaptor,"rule generalPathExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:159:2: ( ( '(' ID ')' )=> '(' type= ID ')' expr= generalPathExpression ->| ( generalPathExpression AS )=>expr= generalPathExpression AS type= ID ->| generalPathExpression )
            int alt23=3;
            switch ( input.LA(1) ) {
            case 40:
                {
                int LA23_1 = input.LA(2);

                if ( (synpred4_Meteor()) ) {
                    alt23=1;
                }
                else if ( (synpred5_Meteor()) ) {
                    alt23=2;
                }
                else if ( (true) ) {
                    alt23=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 23, 1, input);

                    throw nvae;

                }
                }
                break;
            case ID:
                {
                int LA23_2 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt23=2;
                }
                else if ( (true) ) {
                    alt23=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 23, 2, input);

                    throw nvae;

                }
                }
                break;
            case 39:
                {
                int LA23_3 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt23=2;
                }
                else if ( (true) ) {
                    alt23=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 23, 3, input);

                    throw nvae;

                }
                }
                break;
            case FN:
                {
                int LA23_4 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt23=2;
                }
                else if ( (true) ) {
                    alt23=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 23, 4, input);

                    throw nvae;

                }
                }
                break;
            case 65:
                {
                int LA23_5 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt23=2;
                }
                else if ( (true) ) {
                    alt23=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 23, 5, input);

                    throw nvae;

                }
                }
                break;
            case 60:
                {
                int LA23_6 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt23=2;
                }
                else if ( (true) ) {
                    alt23=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 23, 6, input);

                    throw nvae;

                }
                }
                break;
            case DECIMAL:
                {
                int LA23_7 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt23=2;
                }
                else if ( (true) ) {
                    alt23=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 23, 7, input);

                    throw nvae;

                }
                }
                break;
            case STRING:
                {
                int LA23_8 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt23=2;
                }
                else if ( (true) ) {
                    alt23=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 23, 8, input);

                    throw nvae;

                }
                }
                break;
            case UINT:
                {
                int LA23_9 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt23=2;
                }
                else if ( (true) ) {
                    alt23=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 23, 9, input);

                    throw nvae;

                }
                }
                break;
            case INTEGER:
                {
                int LA23_10 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt23=2;
                }
                else if ( (true) ) {
                    alt23=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 23, 10, input);

                    throw nvae;

                }
                }
                break;
            case 62:
                {
                int LA23_11 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt23=2;
                }
                else if ( (true) ) {
                    alt23=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 23, 11, input);

                    throw nvae;

                }
                }
                break;
            case VAR:
                {
                int LA23_12 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt23=2;
                }
                else if ( (true) ) {
                    alt23=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 23, 12, input);

                    throw nvae;

                }
                }
                break;
            case 58:
                {
                int LA23_13 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt23=2;
                }
                else if ( (true) ) {
                    alt23=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 23, 13, input);

                    throw nvae;

                }
                }
                break;
            case 68:
                {
                int LA23_14 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt23=2;
                }
                else if ( (true) ) {
                    alt23=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 23, 14, input);

                    throw nvae;

                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;

            }

            switch (alt23) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:159:4: ( '(' ID ')' )=> '(' type= ID ')' expr= generalPathExpression
                    {
                    char_literal41=(Token)match(input,40,FOLLOW_40_in_castExpression929); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_40.add(char_literal41);


                    type=(Token)match(input,ID,FOLLOW_ID_in_castExpression933); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(type);


                    char_literal42=(Token)match(input,41,FOLLOW_41_in_castExpression935); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_41.add(char_literal42);


                    pushFollow(FOLLOW_generalPathExpression_in_castExpression939);
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
                    pushFollow(FOLLOW_generalPathExpression_in_castExpression959);
                    expr=generalPathExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_generalPathExpression.add(expr.getTree());

                    AS43=(Token)match(input,AS,FOLLOW_AS_in_castExpression961); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_AS.add(AS43);


                    type=(Token)match(input,ID,FOLLOW_ID_in_castExpression965); if (state.failed) return retval; 
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


                    pushFollow(FOLLOW_generalPathExpression_in_castExpression976);
                    generalPathExpression44=generalPathExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, generalPathExpression44.getTree());

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
            pushFollow(FOLLOW_valueExpression_in_generalPathExpression988);
            value=valueExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_valueExpression.add(value.getTree());

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:167:4: ( ( pathExpression[EvaluationExpression.VALUE] )=>path= pathExpression[$value.tree] -> $path| -> $value)
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==57) && (synpred6_Meteor())) {
                alt24=1;
            }
            else if ( (LA24_0==47) && (synpred6_Meteor())) {
                alt24=1;
            }
            else if ( (LA24_0==58) && (synpred6_Meteor())) {
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:167:5: ( pathExpression[EvaluationExpression.VALUE] )=>path= pathExpression[$value.tree]
                    {
                    pushFollow(FOLLOW_pathExpression_in_generalPathExpression1003);
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

        MeteorParser.pathExpression_return pathExpression45 =null;



        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:171:3: ( pathExpression[context] )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:171:5: pathExpression[context]
            {
            root_0 = (EvaluationExpression)adaptor.nil();


            pushFollow(FOLLOW_pathExpression_in_contextAwarePathExpression1032);
            pathExpression45=pathExpression(context);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, pathExpression45.getTree());

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

        Token string_literal46=null;
        Token char_literal47=null;
        MeteorParser.methodCall_return call =null;

        MeteorParser.pathExpression_return path =null;

        MeteorParser.pathSegment_return seg =null;


        EvaluationExpression string_literal46_tree=null;
        EvaluationExpression char_literal47_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleSubtreeStream stream_pathSegment=new RewriteRuleSubtreeStream(adaptor,"rule pathSegment");
        RewriteRuleSubtreeStream stream_methodCall=new RewriteRuleSubtreeStream(adaptor,"rule methodCall");
        RewriteRuleSubtreeStream stream_pathExpression=new RewriteRuleSubtreeStream(adaptor,"rule pathExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:174:3: ( ( '?.' ID '(' )=> '?.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)] -> $path| -> ^( EXPRESSION[\"TernaryExpression\"] $call) ) | ( '.' ID '(' )=> '.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[$call.tree] -> $path| -> $call) |seg= pathSegment ( ( pathSegment )=>path= pathExpression[$seg.tree] -> $path| -> $seg) )
            int alt28=3;
            switch ( input.LA(1) ) {
            case 57:
                {
                int LA28_1 = input.LA(2);

                if ( (synpred7_Meteor()) ) {
                    alt28=1;
                }
                else if ( (true) ) {
                    alt28=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 28, 1, input);

                    throw nvae;

                }
                }
                break;
            case 47:
                {
                int LA28_2 = input.LA(2);

                if ( (synpred9_Meteor()) ) {
                    alt28=2;
                }
                else if ( (true) ) {
                    alt28=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 28, 2, input);

                    throw nvae;

                }
                }
                break;
            case 58:
                {
                alt28=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;

            }

            switch (alt28) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:175:5: ( '?.' ID '(' )=> '?.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)] -> $path| -> ^( EXPRESSION[\"TernaryExpression\"] $call) )
                    {
                    string_literal46=(Token)match(input,57,FOLLOW_57_in_pathExpression1060); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(string_literal46);


                    pushFollow(FOLLOW_methodCall_in_pathExpression1064);
                    call=methodCall(inExp);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_methodCall.add(call.getTree());

                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:176:7: ( ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)] -> $path| -> ^( EXPRESSION[\"TernaryExpression\"] $call) )
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( (LA25_0==57) && (synpred8_Meteor())) {
                        alt25=1;
                    }
                    else if ( (LA25_0==47) && (synpred8_Meteor())) {
                        alt25=1;
                    }
                    else if ( (LA25_0==58) && (synpred8_Meteor())) {
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:176:8: ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)]
                            {
                            pushFollow(FOLLOW_pathExpression_in_pathExpression1081);
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
                    char_literal47=(Token)match(input,47,FOLLOW_47_in_pathExpression1131); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_47.add(char_literal47);


                    pushFollow(FOLLOW_methodCall_in_pathExpression1135);
                    call=methodCall(inExp);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_methodCall.add(call.getTree());

                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:180:7: ( ( pathSegment )=>path= pathExpression[$call.tree] -> $path| -> $call)
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0==57) && (synpred10_Meteor())) {
                        alt26=1;
                    }
                    else if ( (LA26_0==47) && (synpred10_Meteor())) {
                        alt26=1;
                    }
                    else if ( (LA26_0==58) && (synpred10_Meteor())) {
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:180:8: ( pathSegment )=>path= pathExpression[$call.tree]
                            {
                            pushFollow(FOLLOW_pathExpression_in_pathExpression1152);
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
                    pushFollow(FOLLOW_pathSegment_in_pathExpression1178);
                    seg=pathSegment();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_pathSegment.add(seg.getTree());

                    if ( state.backtracking==0 ) { ((PathSegmentExpression) seg.getTree()).setInputExpression(inExp); }

                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:183:5: ( ( pathSegment )=>path= pathExpression[$seg.tree] -> $path| -> $seg)
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( (LA27_0==57) && (synpred11_Meteor())) {
                        alt27=1;
                    }
                    else if ( (LA27_0==47) && (synpred11_Meteor())) {
                        alt27=1;
                    }
                    else if ( (LA27_0==58) && (synpred11_Meteor())) {
                        alt27=1;
                    }
                    else if ( (LA27_0==EOF||LA27_0==AND||LA27_0==AS||(LA27_0 >= ID && LA27_0 <= IN)||LA27_0==NOT||LA27_0==OR||(LA27_0 >= SLASH && LA27_0 <= STAR)||(LA27_0 >= 37 && LA27_0 <= 38)||(LA27_0 >= 41 && LA27_0 <= 42)||(LA27_0 >= 44 && LA27_0 <= 45)||(LA27_0 >= 48 && LA27_0 <= 51)||(LA27_0 >= 53 && LA27_0 <= 56)||LA27_0==59||(LA27_0 >= 69 && LA27_0 <= 70)) ) {
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:183:6: ( pathSegment )=>path= pathExpression[$seg.tree]
                            {
                            pushFollow(FOLLOW_pathExpression_in_pathExpression1194);
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
        Token string_literal48=null;
        Token char_literal49=null;
        MeteorParser.arrayAccess_return arrayAccess50 =null;


        EvaluationExpression field_tree=null;
        EvaluationExpression string_literal48_tree=null;
        EvaluationExpression char_literal49_tree=null;
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");

          paraphrase.push("a path expression"); 
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:189:3: ( ( '?.' )=> '?.' field= ID -> ^( EXPRESSION[\"TernaryExpression\"] ^( EXPRESSION[\"NotNullOrMissingBooleanExpression\"] ) ^( EXPRESSION[\"ObjectAccess\"] ) ) | ( '.' )=> '.' field= ID -> ^( EXPRESSION[\"ObjectAccess\"] ) | ( '[' )=> arrayAccess )
            int alt29=3;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==57) && (synpred12_Meteor())) {
                alt29=1;
            }
            else if ( (LA29_0==47) && (synpred13_Meteor())) {
                alt29=2;
            }
            else if ( (LA29_0==58) && (synpred14_Meteor())) {
                alt29=3;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 29, 0, input);

                throw nvae;

            }
            switch (alt29) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:190:5: ( '?.' )=> '?.' field= ID
                    {
                    string_literal48=(Token)match(input,57,FOLLOW_57_in_pathSegment1246); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(string_literal48);


                    field=(Token)match(input,ID,FOLLOW_ID_in_pathSegment1250); if (state.failed) return retval; 
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
                    char_literal49=(Token)match(input,47,FOLLOW_47_in_pathSegment1285); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_47.add(char_literal49);


                    field=(Token)match(input,ID,FOLLOW_ID_in_pathSegment1289); if (state.failed) return retval; 
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


                    pushFollow(FOLLOW_arrayAccess_in_pathSegment1314);
                    arrayAccess50=arrayAccess();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arrayAccess50.getTree());

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
        Token char_literal51=null;
        Token STAR52=null;
        Token char_literal53=null;
        Token char_literal54=null;
        Token char_literal55=null;
        Token char_literal56=null;
        Token char_literal57=null;
        Token char_literal58=null;
        Token char_literal59=null;
        MeteorParser.methodCall_return call =null;

        MeteorParser.pathSegment_return path =null;


        EvaluationExpression pos_tree=null;
        EvaluationExpression start_tree=null;
        EvaluationExpression end_tree=null;
        EvaluationExpression char_literal51_tree=null;
        EvaluationExpression STAR52_tree=null;
        EvaluationExpression char_literal53_tree=null;
        EvaluationExpression char_literal54_tree=null;
        EvaluationExpression char_literal55_tree=null;
        EvaluationExpression char_literal56_tree=null;
        EvaluationExpression char_literal57_tree=null;
        EvaluationExpression char_literal58_tree=null;
        EvaluationExpression char_literal59_tree=null;
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
            int alt34=3;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==58) ) {
                switch ( input.LA(2) ) {
                case STAR:
                    {
                    alt34=1;
                    }
                    break;
                case INTEGER:
                    {
                    int LA34_3 = input.LA(3);

                    if ( (LA34_3==59) ) {
                        alt34=2;
                    }
                    else if ( (LA34_3==48) ) {
                        alt34=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 34, 3, input);

                        throw nvae;

                    }
                    }
                    break;
                case UINT:
                    {
                    int LA34_4 = input.LA(3);

                    if ( (LA34_4==59) ) {
                        alt34=2;
                    }
                    else if ( (LA34_4==48) ) {
                        alt34=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 34, 4, input);

                        throw nvae;

                    }
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 34, 1, input);

                    throw nvae;

                }

            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 34, 0, input);

                throw nvae;

            }
            switch (alt34) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:195:5: '[' STAR ']' ( ( '.' methodCall[null] )=> '.' call= methodCall[EvaluationExpression.VALUE] -> ^( EXPRESSION[\"ArrayProjection\"] $call) |path= pathSegment -> ^( EXPRESSION[\"ArrayProjection\"] $path) )
                    {
                    char_literal51=(Token)match(input,58,FOLLOW_58_in_arrayAccess1324); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_58.add(char_literal51);


                    STAR52=(Token)match(input,STAR,FOLLOW_STAR_in_arrayAccess1326); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_STAR.add(STAR52);


                    char_literal53=(Token)match(input,59,FOLLOW_59_in_arrayAccess1328); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_59.add(char_literal53);


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:195:18: ( ( '.' methodCall[null] )=> '.' call= methodCall[EvaluationExpression.VALUE] -> ^( EXPRESSION[\"ArrayProjection\"] $call) |path= pathSegment -> ^( EXPRESSION[\"ArrayProjection\"] $path) )
                    int alt30=2;
                    int LA30_0 = input.LA(1);

                    if ( (LA30_0==47) ) {
                        int LA30_1 = input.LA(2);

                        if ( (synpred15_Meteor()) ) {
                            alt30=1;
                        }
                        else if ( (true) ) {
                            alt30=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 30, 1, input);

                            throw nvae;

                        }
                    }
                    else if ( ((LA30_0 >= 57 && LA30_0 <= 58)) ) {
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:195:19: ( '.' methodCall[null] )=> '.' call= methodCall[EvaluationExpression.VALUE]
                            {
                            char_literal54=(Token)match(input,47,FOLLOW_47_in_arrayAccess1339); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_47.add(char_literal54);


                            pushFollow(FOLLOW_methodCall_in_arrayAccess1343);
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
                            pushFollow(FOLLOW_pathSegment_in_arrayAccess1366);
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
                    char_literal55=(Token)match(input,58,FOLLOW_58_in_arrayAccess1387); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_58.add(char_literal55);


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:199:9: (pos= INTEGER |pos= UINT )
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:199:10: pos= INTEGER
                            {
                            pos=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_arrayAccess1392); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_INTEGER.add(pos);


                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:199:24: pos= UINT
                            {
                            pos=(Token)match(input,UINT,FOLLOW_UINT_in_arrayAccess1398); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_UINT.add(pos);


                            }
                            break;

                    }


                    char_literal56=(Token)match(input,59,FOLLOW_59_in_arrayAccess1401); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_59.add(char_literal56);


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
                    char_literal57=(Token)match(input,58,FOLLOW_58_in_arrayAccess1419); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_58.add(char_literal57);


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:201:9: (start= INTEGER |start= UINT )
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:201:10: start= INTEGER
                            {
                            start=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_arrayAccess1424); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_INTEGER.add(start);


                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:201:26: start= UINT
                            {
                            start=(Token)match(input,UINT,FOLLOW_UINT_in_arrayAccess1430); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_UINT.add(start);


                            }
                            break;

                    }


                    char_literal58=(Token)match(input,48,FOLLOW_48_in_arrayAccess1433); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_48.add(char_literal58);


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:201:42: (end= INTEGER |end= UINT )
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:201:43: end= INTEGER
                            {
                            end=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_arrayAccess1438); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_INTEGER.add(end);


                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:201:57: end= UINT
                            {
                            end=(Token)match(input,UINT,FOLLOW_UINT_in_arrayAccess1444); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_UINT.add(end);


                            }
                            break;

                    }


                    char_literal59=(Token)match(input,59,FOLLOW_59_in_arrayAccess1447); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_59.add(char_literal59);


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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:204:1: valueExpression : ( ( ID '(' )=> functionCall | functionReference | ( FN )=>func= inlineFunction -> ^( EXPRESSION[\"ConstantExpression\"] ) | parenthesesExpression | literal | ( VAR '[' VAR )=> streamIndexAccess | VAR ->| ( ( ID ':' )=>packageName= ID ':' )? constant= ID {...}? => ->| arrayCreation | objectCreation );
    public final MeteorParser.valueExpression_return valueExpression() throws RecognitionException {
        MeteorParser.valueExpression_return retval = new MeteorParser.valueExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token packageName=null;
        Token constant=null;
        Token VAR65=null;
        Token char_literal66=null;
        MeteorParser.inlineFunction_return func =null;

        MeteorParser.functionCall_return functionCall60 =null;

        MeteorParser.functionReference_return functionReference61 =null;

        MeteorParser.parenthesesExpression_return parenthesesExpression62 =null;

        MeteorParser.literal_return literal63 =null;

        MeteorParser.streamIndexAccess_return streamIndexAccess64 =null;

        MeteorParser.arrayCreation_return arrayCreation67 =null;

        MeteorParser.objectCreation_return objectCreation68 =null;


        EvaluationExpression packageName_tree=null;
        EvaluationExpression constant_tree=null;
        EvaluationExpression VAR65_tree=null;
        EvaluationExpression char_literal66_tree=null;
        RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_inlineFunction=new RewriteRuleSubtreeStream(adaptor,"rule inlineFunction");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:205:2: ( ( ID '(' )=> functionCall | functionReference | ( FN )=>func= inlineFunction -> ^( EXPRESSION[\"ConstantExpression\"] ) | parenthesesExpression | literal | ( VAR '[' VAR )=> streamIndexAccess | VAR ->| ( ( ID ':' )=>packageName= ID ':' )? constant= ID {...}? => ->| arrayCreation | objectCreation )
            int alt36=10;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==ID) ) {
                int LA36_1 = input.LA(2);

                if ( (LA36_1==48) ) {
                    int LA36_9 = input.LA(3);

                    if ( (synpred16_Meteor()) ) {
                        alt36=1;
                    }
                    else if ( (true) ) {
                        alt36=8;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 36, 9, input);

                        throw nvae;

                    }
                }
                else if ( (LA36_1==40) && (synpred16_Meteor())) {
                    alt36=1;
                }
                else if ( (LA36_1==EOF||LA36_1==AND||LA36_1==AS||(LA36_1 >= ID && LA36_1 <= IN)||LA36_1==NOT||LA36_1==OR||(LA36_1 >= SLASH && LA36_1 <= STAR)||(LA36_1 >= 37 && LA36_1 <= 38)||(LA36_1 >= 41 && LA36_1 <= 42)||(LA36_1 >= 44 && LA36_1 <= 45)||LA36_1==47||(LA36_1 >= 49 && LA36_1 <= 51)||(LA36_1 >= 53 && LA36_1 <= 59)||(LA36_1 >= 69 && LA36_1 <= 70)) ) {
                    alt36=8;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 36, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA36_0==39) ) {
                alt36=2;
            }
            else if ( (LA36_0==FN) && (synpred17_Meteor())) {
                alt36=3;
            }
            else if ( (LA36_0==40) ) {
                alt36=4;
            }
            else if ( (LA36_0==DECIMAL||LA36_0==INTEGER||(LA36_0 >= STRING && LA36_0 <= UINT)||LA36_0==60||LA36_0==62||LA36_0==65) ) {
                alt36=5;
            }
            else if ( (LA36_0==VAR) ) {
                int LA36_6 = input.LA(2);

                if ( (LA36_6==58) ) {
                    int LA36_12 = input.LA(3);

                    if ( (LA36_12==STAR) ) {
                        alt36=7;
                    }
                    else if ( (LA36_12==ID) && (synpred18_Meteor())) {
                        alt36=6;
                    }
                    else if ( (LA36_12==39) && (synpred18_Meteor())) {
                        alt36=6;
                    }
                    else if ( (LA36_12==FN) && (synpred18_Meteor())) {
                        alt36=6;
                    }
                    else if ( (LA36_12==40) && (synpred18_Meteor())) {
                        alt36=6;
                    }
                    else if ( (LA36_12==65) && (synpred18_Meteor())) {
                        alt36=6;
                    }
                    else if ( (LA36_12==60) && (synpred18_Meteor())) {
                        alt36=6;
                    }
                    else if ( (LA36_12==DECIMAL) && (synpred18_Meteor())) {
                        alt36=6;
                    }
                    else if ( (LA36_12==STRING) && (synpred18_Meteor())) {
                        alt36=6;
                    }
                    else if ( (LA36_12==UINT) ) {
                        int LA36_22 = input.LA(4);

                        if ( (LA36_22==57) && (synpred18_Meteor())) {
                            alt36=6;
                        }
                        else if ( (LA36_22==47) && (synpred18_Meteor())) {
                            alt36=6;
                        }
                        else if ( (LA36_22==58) && (synpred18_Meteor())) {
                            alt36=6;
                        }
                        else if ( (LA36_22==59) ) {
                            int LA36_31 = input.LA(5);

                            if ( (synpred18_Meteor()) ) {
                                alt36=6;
                            }
                            else if ( (true) ) {
                                alt36=7;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 36, 31, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA36_22==48) ) {
                            alt36=7;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 36, 22, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA36_12==INTEGER) ) {
                        int LA36_23 = input.LA(4);

                        if ( (LA36_23==57) && (synpred18_Meteor())) {
                            alt36=6;
                        }
                        else if ( (LA36_23==47) && (synpred18_Meteor())) {
                            alt36=6;
                        }
                        else if ( (LA36_23==58) && (synpred18_Meteor())) {
                            alt36=6;
                        }
                        else if ( (LA36_23==59) ) {
                            int LA36_32 = input.LA(5);

                            if ( (synpred18_Meteor()) ) {
                                alt36=6;
                            }
                            else if ( (true) ) {
                                alt36=7;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 36, 32, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA36_23==48) ) {
                            alt36=7;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 36, 23, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA36_12==62) && (synpred18_Meteor())) {
                        alt36=6;
                    }
                    else if ( (LA36_12==VAR) && (synpred18_Meteor())) {
                        alt36=6;
                    }
                    else if ( (LA36_12==58) && (synpred18_Meteor())) {
                        alt36=6;
                    }
                    else if ( (LA36_12==68) && (synpred18_Meteor())) {
                        alt36=6;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 36, 12, input);

                        throw nvae;

                    }
                }
                else if ( (LA36_6==EOF||LA36_6==AND||LA36_6==AS||(LA36_6 >= ID && LA36_6 <= IN)||LA36_6==NOT||LA36_6==OR||(LA36_6 >= SLASH && LA36_6 <= STAR)||(LA36_6 >= 37 && LA36_6 <= 38)||(LA36_6 >= 41 && LA36_6 <= 42)||(LA36_6 >= 44 && LA36_6 <= 45)||(LA36_6 >= 47 && LA36_6 <= 51)||(LA36_6 >= 53 && LA36_6 <= 57)||LA36_6==59||(LA36_6 >= 69 && LA36_6 <= 70)) ) {
                    alt36=7;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 36, 6, input);

                    throw nvae;

                }
            }
            else if ( (LA36_0==58) ) {
                alt36=9;
            }
            else if ( (LA36_0==68) ) {
                alt36=10;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 36, 0, input);

                throw nvae;

            }
            switch (alt36) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:205:4: ( ID '(' )=> functionCall
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_functionCall_in_valueExpression1479);
                    functionCall60=functionCall();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, functionCall60.getTree());

                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:206:4: functionReference
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_functionReference_in_valueExpression1484);
                    functionReference61=functionReference();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, functionReference61.getTree());

                    }
                    break;
                case 3 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:207:5: ( FN )=>func= inlineFunction
                    {
                    pushFollow(FOLLOW_inlineFunction_in_valueExpression1497);
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
                    // 207:32: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:207:35: ^( EXPRESSION[\"ConstantExpression\"] )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:208:4: parenthesesExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_parenthesesExpression_in_valueExpression1511);
                    parenthesesExpression62=parenthesesExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, parenthesesExpression62.getTree());

                    }
                    break;
                case 5 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:209:4: literal
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_literal_in_valueExpression1517);
                    literal63=literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, literal63.getTree());

                    }
                    break;
                case 6 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:210:5: ( VAR '[' VAR )=> streamIndexAccess
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_streamIndexAccess_in_valueExpression1533);
                    streamIndexAccess64=streamIndexAccess();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, streamIndexAccess64.getTree());

                    }
                    break;
                case 7 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:211:4: VAR
                    {
                    VAR65=(Token)match(input,VAR,FOLLOW_VAR_in_valueExpression1538); if (state.failed) return retval; 
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
                    // 211:8: ->
                    {
                        adaptor.addChild(root_0,  getInputSelection(VAR65) );

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 8 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:212:5: ( ( ID ':' )=>packageName= ID ':' )? constant= ID {...}? =>
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:212:5: ( ( ID ':' )=>packageName= ID ':' )?
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==ID) ) {
                        int LA35_1 = input.LA(2);

                        if ( (LA35_1==48) ) {
                            int LA35_2 = input.LA(3);

                            if ( (synpred19_Meteor()) ) {
                                alt35=1;
                            }
                        }
                    }
                    switch (alt35) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:212:6: ( ID ':' )=>packageName= ID ':'
                            {
                            packageName=(Token)match(input,ID,FOLLOW_ID_in_valueExpression1558); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_ID.add(packageName);


                            char_literal66=(Token)match(input,48,FOLLOW_48_in_valueExpression1560); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_48.add(char_literal66);


                            }
                            break;

                    }


                    constant=(Token)match(input,ID,FOLLOW_ID_in_valueExpression1566); if (state.failed) return retval; 
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
                    // 213:5: ->
                    {
                        adaptor.addChild(root_0,  getScope((packageName!=null?packageName.getText():null)).getConstantRegistry().get((constant!=null?constant.getText():null)) );

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 9 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:214:4: arrayCreation
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_arrayCreation_in_valueExpression1586);
                    arrayCreation67=arrayCreation();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arrayCreation67.getTree());

                    }
                    break;
                case 10 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:215:4: objectCreation
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_objectCreation_in_valueExpression1592);
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:217:1: operatorExpression : op= operator -> ^( EXPRESSION[\"NestedOperatorExpression\"] ) ;
    public final MeteorParser.operatorExpression_return operatorExpression() throws RecognitionException {
        MeteorParser.operatorExpression_return retval = new MeteorParser.operatorExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.operator_return op =null;


        RewriteRuleSubtreeStream stream_operator=new RewriteRuleSubtreeStream(adaptor,"rule operator");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:218:2: (op= operator -> ^( EXPRESSION[\"NestedOperatorExpression\"] ) )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:218:4: op= operator
            {
            pushFollow(FOLLOW_operator_in_operatorExpression1604);
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
            // 218:16: -> ^( EXPRESSION[\"NestedOperatorExpression\"] )
            {
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:218:19: ^( EXPRESSION[\"NestedOperatorExpression\"] )
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:220:1: parenthesesExpression : ( '(' expression ')' ) -> expression ;
    public final MeteorParser.parenthesesExpression_return parenthesesExpression() throws RecognitionException {
        MeteorParser.parenthesesExpression_return retval = new MeteorParser.parenthesesExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token char_literal69=null;
        Token char_literal71=null;
        MeteorParser.expression_return expression70 =null;


        EvaluationExpression char_literal69_tree=null;
        EvaluationExpression char_literal71_tree=null;
        RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:221:2: ( ( '(' expression ')' ) -> expression )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:221:4: ( '(' expression ')' )
            {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:221:4: ( '(' expression ')' )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:221:5: '(' expression ')'
            {
            char_literal69=(Token)match(input,40,FOLLOW_40_in_parenthesesExpression1623); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_40.add(char_literal69);


            pushFollow(FOLLOW_expression_in_parenthesesExpression1625);
            expression70=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expression.add(expression70.getTree());

            char_literal71=(Token)match(input,41,FOLLOW_41_in_parenthesesExpression1627); if (state.failed) return retval; 
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
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 221:25: -> expression
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:223:1: methodCall[EvaluationExpression targetExpr] : (packageName= ID ':' )? name= ID '(' ( (param= expression ) ( ',' (param= expression ) )* )? ')' ->;
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
        RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
        RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
         List<EvaluationExpression> params = new ArrayList();
                paraphrase.push("a method call"); 
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:227:3: ( (packageName= ID ':' )? name= ID '(' ( (param= expression ) ( ',' (param= expression ) )* )? ')' ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:227:5: (packageName= ID ':' )? name= ID '(' ( (param= expression ) ( ',' (param= expression ) )* )? ')'
            {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:227:5: (packageName= ID ':' )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==ID) ) {
                int LA37_1 = input.LA(2);

                if ( (LA37_1==48) ) {
                    alt37=1;
                }
            }
            switch (alt37) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:227:6: packageName= ID ':'
                    {
                    packageName=(Token)match(input,ID,FOLLOW_ID_in_methodCall1657); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(packageName);


                    char_literal72=(Token)match(input,48,FOLLOW_48_in_methodCall1659); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_48.add(char_literal72);


                    }
                    break;

            }


            name=(Token)match(input,ID,FOLLOW_ID_in_methodCall1665); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(name);


            char_literal73=(Token)match(input,40,FOLLOW_40_in_methodCall1667); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_40.add(char_literal73);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:228:3: ( (param= expression ) ( ',' (param= expression ) )* )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==DECIMAL||LA39_0==FN||LA39_0==ID||LA39_0==INTEGER||(LA39_0 >= STRING && LA39_0 <= UINT)||LA39_0==VAR||LA39_0==36||(LA39_0 >= 39 && LA39_0 <= 40)||LA39_0==43||LA39_0==46||LA39_0==58||LA39_0==60||(LA39_0 >= 62 && LA39_0 <= 63)||LA39_0==65||(LA39_0 >= 67 && LA39_0 <= 68)||LA39_0==71) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:228:4: (param= expression ) ( ',' (param= expression ) )*
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:228:4: (param= expression )
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:228:5: param= expression
                    {
                    pushFollow(FOLLOW_expression_in_methodCall1676);
                    param=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(param.getTree());

                    if ( state.backtracking==0 ) { params.add((param!=null?((EvaluationExpression)param.tree):null)); }

                    }


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:229:3: ( ',' (param= expression ) )*
                    loop38:
                    do {
                        int alt38=2;
                        int LA38_0 = input.LA(1);

                        if ( (LA38_0==44) ) {
                            alt38=1;
                        }


                        switch (alt38) {
                    	case 1 :
                    	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:229:4: ',' (param= expression )
                    	    {
                    	    char_literal74=(Token)match(input,44,FOLLOW_44_in_methodCall1685); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_44.add(char_literal74);


                    	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:229:8: (param= expression )
                    	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:229:9: param= expression
                    	    {
                    	    pushFollow(FOLLOW_expression_in_methodCall1690);
                    	    param=expression();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_expression.add(param.getTree());

                    	    if ( state.backtracking==0 ) { params.add((param!=null?((EvaluationExpression)param.tree):null)); }

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop38;
                        }
                    } while (true);


                    }
                    break;

            }


            char_literal75=(Token)match(input,41,FOLLOW_41_in_methodCall1702); if (state.failed) return retval; 
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
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EvaluationExpression)adaptor.nil();
            // 230:7: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:232:1: functionCall : methodCall[null] ;
    public final MeteorParser.functionCall_return functionCall() throws RecognitionException {
        MeteorParser.functionCall_return retval = new MeteorParser.functionCall_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.methodCall_return methodCall76 =null;



        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:233:2: ( methodCall[null] )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:233:4: methodCall[null]
            {
            root_0 = (EvaluationExpression)adaptor.nil();


            pushFollow(FOLLOW_methodCall_in_functionCall1717);
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:235:1: functionReference : '&' ( ( ID ':' )=>packageName= ID ':' )? name= ID -> ^( EXPRESSION[\"ConstantExpression\"] ) ;
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
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:236:3: ( '&' ( ( ID ':' )=>packageName= ID ':' )? name= ID -> ^( EXPRESSION[\"ConstantExpression\"] ) )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:236:5: '&' ( ( ID ':' )=>packageName= ID ':' )? name= ID
            {
            char_literal77=(Token)match(input,39,FOLLOW_39_in_functionReference1728); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_39.add(char_literal77);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:236:9: ( ( ID ':' )=>packageName= ID ':' )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==ID) ) {
                int LA40_1 = input.LA(2);

                if ( (LA40_1==48) ) {
                    int LA40_2 = input.LA(3);

                    if ( (LA40_2==ID) ) {
                        int LA40_4 = input.LA(4);

                        if ( (synpred20_Meteor()) ) {
                            alt40=1;
                        }
                    }
                }
            }
            switch (alt40) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:236:10: ( ID ':' )=>packageName= ID ':'
                    {
                    packageName=(Token)match(input,ID,FOLLOW_ID_in_functionReference1740); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(packageName);


                    char_literal78=(Token)match(input,48,FOLLOW_48_in_functionReference1742); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_48.add(char_literal78);


                    }
                    break;

            }


            name=(Token)match(input,ID,FOLLOW_ID_in_functionReference1748); if (state.failed) return retval; 
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
            // 237:9: -> ^( EXPRESSION[\"ConstantExpression\"] )
            {
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:237:12: ^( EXPRESSION[\"ConstantExpression\"] )
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:239:1: fieldAssignment : ( ( ( ID ':' )=> ID ':' expression ->) | VAR ( '.' STAR ->| '=' op= operator {...}? =>|p= contextAwarePathExpression[getVariable($VAR).toInputSelection($operator::result)] ( ':' e2= expression ->| ->) ) );
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
        Token char_literal86=null;
        MeteorParser.operator_return op =null;

        MeteorParser.contextAwarePathExpression_return p =null;

        MeteorParser.expression_return e2 =null;

        MeteorParser.expression_return expression81 =null;


        EvaluationExpression ID79_tree=null;
        EvaluationExpression char_literal80_tree=null;
        EvaluationExpression VAR82_tree=null;
        EvaluationExpression char_literal83_tree=null;
        EvaluationExpression STAR84_tree=null;
        EvaluationExpression char_literal85_tree=null;
        EvaluationExpression char_literal86_tree=null;
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
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:240:2: ( ( ( ID ':' )=> ID ':' expression ->) | VAR ( '.' STAR ->| '=' op= operator {...}? =>|p= contextAwarePathExpression[getVariable($VAR).toInputSelection($operator::result)] ( ':' e2= expression ->| ->) ) )
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==ID) && (synpred21_Meteor())) {
                alt43=1;
            }
            else if ( (LA43_0==VAR) ) {
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:240:4: ( ( ID ':' )=> ID ':' expression ->)
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:240:4: ( ( ID ':' )=> ID ':' expression ->)
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:240:5: ( ID ':' )=> ID ':' expression
                    {
                    ID79=(Token)match(input,ID,FOLLOW_ID_in_fieldAssignment1784); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(ID79);


                    char_literal80=(Token)match(input,48,FOLLOW_48_in_fieldAssignment1786); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_48.add(char_literal80);


                    pushFollow(FOLLOW_expression_in_fieldAssignment1788);
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
                    // 241:104: ->
                    {
                        root_0 = null;
                    }


                    retval.tree = root_0;
                    }

                    }


                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:242:5: VAR ( '.' STAR ->| '=' op= operator {...}? =>|p= contextAwarePathExpression[getVariable($VAR).toInputSelection($operator::result)] ( ':' e2= expression ->| ->) )
                    {
                    VAR82=(Token)match(input,VAR,FOLLOW_VAR_in_fieldAssignment1805); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_VAR.add(VAR82);


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:243:5: ( '.' STAR ->| '=' op= operator {...}? =>|p= contextAwarePathExpression[getVariable($VAR).toInputSelection($operator::result)] ( ':' e2= expression ->| ->) )
                    int alt42=3;
                    switch ( input.LA(1) ) {
                    case 47:
                        {
                        int LA42_1 = input.LA(2);

                        if ( (LA42_1==STAR) ) {
                            alt42=1;
                        }
                        else if ( (LA42_1==ID) ) {
                            alt42=3;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 42, 1, input);

                            throw nvae;

                        }
                        }
                        break;
                    case 52:
                        {
                        alt42=2;
                        }
                        break;
                    case 57:
                    case 58:
                        {
                        alt42=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 42, 0, input);

                        throw nvae;

                    }

                    switch (alt42) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:243:7: '.' STAR
                            {
                            char_literal83=(Token)match(input,47,FOLLOW_47_in_fieldAssignment1814); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_47.add(char_literal83);


                            STAR84=(Token)match(input,STAR,FOLLOW_STAR_in_fieldAssignment1816); if (state.failed) return retval; 
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
                            // 243:107: ->
                            {
                                root_0 = null;
                            }


                            retval.tree = root_0;
                            }

                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:244:9: '=' op= operator {...}? =>
                            {
                            char_literal85=(Token)match(input,52,FOLLOW_52_in_fieldAssignment1830); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_52.add(char_literal85);


                            pushFollow(FOLLOW_operator_in_fieldAssignment1834);
                            op=operator();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_operator.add(op.getTree());

                            if ( !(( setInnerOutput(VAR82, (op!=null?op.op:null)) )) ) {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                throw new FailedPredicateException(input, "fieldAssignment", " setInnerOutput($VAR, $op.op) ");
                            }

                            }
                            break;
                        case 3 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:245:9: p= contextAwarePathExpression[getVariable($VAR).toInputSelection($operator::result)] ( ':' e2= expression ->| ->)
                            {
                            pushFollow(FOLLOW_contextAwarePathExpression_in_fieldAssignment1849);
                            p=contextAwarePathExpression(getVariable(VAR82).toInputSelection(((operator_scope)operator_stack.peek()).result));

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_contextAwarePathExpression.add(p.getTree());

                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:246:7: ( ':' e2= expression ->| ->)
                            int alt41=2;
                            int LA41_0 = input.LA(1);

                            if ( (LA41_0==48) ) {
                                alt41=1;
                            }
                            else if ( (LA41_0==44||LA41_0==70) ) {
                                alt41=2;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 41, 0, input);

                                throw nvae;

                            }
                            switch (alt41) {
                                case 1 :
                                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:246:9: ':' e2= expression
                                    {
                                    char_literal86=(Token)match(input,48,FOLLOW_48_in_fieldAssignment1860); if (state.failed) return retval; 
                                    if ( state.backtracking==0 ) stream_48.add(char_literal86);


                                    pushFollow(FOLLOW_expression_in_fieldAssignment1864);
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
                                    // 246:112: ->
                                    {
                                        root_0 = null;
                                    }


                                    retval.tree = root_0;
                                    }

                                    }
                                    break;
                                case 2 :
                                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:247:23: 
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
                                    // 247:131: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:252:1: objectCreation : '{' ( fieldAssignment ( ',' fieldAssignment )* ( ',' )? )? '}' -> ^( EXPRESSION[\"ObjectCreation\"] ) ;
    public final MeteorParser.objectCreation_return objectCreation() throws RecognitionException {
        objectCreation_stack.push(new objectCreation_scope());
        MeteorParser.objectCreation_return retval = new MeteorParser.objectCreation_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token char_literal87=null;
        Token char_literal89=null;
        Token char_literal91=null;
        Token char_literal92=null;
        MeteorParser.fieldAssignment_return fieldAssignment88 =null;

        MeteorParser.fieldAssignment_return fieldAssignment90 =null;


        EvaluationExpression char_literal87_tree=null;
        EvaluationExpression char_literal89_tree=null;
        EvaluationExpression char_literal91_tree=null;
        EvaluationExpression char_literal92_tree=null;
        RewriteRuleTokenStream stream_68=new RewriteRuleTokenStream(adaptor,"token 68");
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
        RewriteRuleTokenStream stream_70=new RewriteRuleTokenStream(adaptor,"token 70");
        RewriteRuleSubtreeStream stream_fieldAssignment=new RewriteRuleSubtreeStream(adaptor,"rule fieldAssignment");
         ((objectCreation_scope)objectCreation_stack.peek()).mappings = new ArrayList<ObjectCreation.Mapping>(); 
                paraphrase.push("a json object"); 
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:257:2: ( '{' ( fieldAssignment ( ',' fieldAssignment )* ( ',' )? )? '}' -> ^( EXPRESSION[\"ObjectCreation\"] ) )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:257:4: '{' ( fieldAssignment ( ',' fieldAssignment )* ( ',' )? )? '}'
            {
            char_literal87=(Token)match(input,68,FOLLOW_68_in_objectCreation1929); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_68.add(char_literal87);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:257:8: ( fieldAssignment ( ',' fieldAssignment )* ( ',' )? )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==ID||LA46_0==VAR) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:257:9: fieldAssignment ( ',' fieldAssignment )* ( ',' )?
                    {
                    pushFollow(FOLLOW_fieldAssignment_in_objectCreation1932);
                    fieldAssignment88=fieldAssignment();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_fieldAssignment.add(fieldAssignment88.getTree());

                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:257:25: ( ',' fieldAssignment )*
                    loop44:
                    do {
                        int alt44=2;
                        int LA44_0 = input.LA(1);

                        if ( (LA44_0==44) ) {
                            int LA44_1 = input.LA(2);

                            if ( (LA44_1==ID||LA44_1==VAR) ) {
                                alt44=1;
                            }


                        }


                        switch (alt44) {
                    	case 1 :
                    	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:257:26: ',' fieldAssignment
                    	    {
                    	    char_literal89=(Token)match(input,44,FOLLOW_44_in_objectCreation1935); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_44.add(char_literal89);


                    	    pushFollow(FOLLOW_fieldAssignment_in_objectCreation1937);
                    	    fieldAssignment90=fieldAssignment();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_fieldAssignment.add(fieldAssignment90.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop44;
                        }
                    } while (true);


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:257:48: ( ',' )?
                    int alt45=2;
                    int LA45_0 = input.LA(1);

                    if ( (LA45_0==44) ) {
                        alt45=1;
                    }
                    switch (alt45) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:257:48: ','
                            {
                            char_literal91=(Token)match(input,44,FOLLOW_44_in_objectCreation1941); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_44.add(char_literal91);


                            }
                            break;

                    }


                    }
                    break;

            }


            char_literal92=(Token)match(input,70,FOLLOW_70_in_objectCreation1946); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_70.add(char_literal92);


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
            // 257:59: -> ^( EXPRESSION[\"ObjectCreation\"] )
            {
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:257:62: ^( EXPRESSION[\"ObjectCreation\"] )
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:260:1: literal : (val= 'true' -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= 'false' -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= DECIMAL -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= STRING -> ^( EXPRESSION[\"ConstantExpression\"] ) | (val= UINT |val= INTEGER ) -> ^( EXPRESSION[\"ConstantExpression\"] ) | 'null' ->);
    public final MeteorParser.literal_return literal() throws RecognitionException {
        MeteorParser.literal_return retval = new MeteorParser.literal_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token val=null;
        Token string_literal93=null;

        EvaluationExpression val_tree=null;
        EvaluationExpression string_literal93_tree=null;
        RewriteRuleTokenStream stream_INTEGER=new RewriteRuleTokenStream(adaptor,"token INTEGER");
        RewriteRuleTokenStream stream_DECIMAL=new RewriteRuleTokenStream(adaptor,"token DECIMAL");
        RewriteRuleTokenStream stream_65=new RewriteRuleTokenStream(adaptor,"token 65");
        RewriteRuleTokenStream stream_62=new RewriteRuleTokenStream(adaptor,"token 62");
        RewriteRuleTokenStream stream_UINT=new RewriteRuleTokenStream(adaptor,"token UINT");
        RewriteRuleTokenStream stream_60=new RewriteRuleTokenStream(adaptor,"token 60");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

         paraphrase.push("a literal"); 
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:263:2: (val= 'true' -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= 'false' -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= DECIMAL -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= STRING -> ^( EXPRESSION[\"ConstantExpression\"] ) | (val= UINT |val= INTEGER ) -> ^( EXPRESSION[\"ConstantExpression\"] ) | 'null' ->)
            int alt48=6;
            switch ( input.LA(1) ) {
            case 65:
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
            case 62:
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:263:4: val= 'true'
                    {
                    val=(Token)match(input,65,FOLLOW_65_in_literal1984); if (state.failed) return retval; 
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
                    // 263:15: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:263:18: ^( EXPRESSION[\"ConstantExpression\"] )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:264:4: val= 'false'
                    {
                    val=(Token)match(input,60,FOLLOW_60_in_literal2000); if (state.failed) return retval; 
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
                    // 264:16: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:264:19: ^( EXPRESSION[\"ConstantExpression\"] )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:265:4: val= DECIMAL
                    {
                    val=(Token)match(input,DECIMAL,FOLLOW_DECIMAL_in_literal2016); if (state.failed) return retval; 
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
                    // 265:16: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:265:19: ^( EXPRESSION[\"ConstantExpression\"] )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:266:4: val= STRING
                    {
                    val=(Token)match(input,STRING,FOLLOW_STRING_in_literal2032); if (state.failed) return retval; 
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
                    // 266:15: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:266:18: ^( EXPRESSION[\"ConstantExpression\"] )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:267:5: (val= UINT |val= INTEGER )
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:267:5: (val= UINT |val= INTEGER )
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:267:6: val= UINT
                            {
                            val=(Token)match(input,UINT,FOLLOW_UINT_in_literal2050); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_UINT.add(val);


                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:267:17: val= INTEGER
                            {
                            val=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_literal2056); if (state.failed) return retval; 
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
                    // 267:30: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:267:33: ^( EXPRESSION[\"ConstantExpression\"] )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:268:5: 'null'
                    {
                    string_literal93=(Token)match(input,62,FOLLOW_62_in_literal2072); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_62.add(string_literal93);


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
                    // 268:12: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:270:1: streamIndexAccess : op= VAR {...}? => '[' path= generalPathExpression ']' {...}? ->;
    public final MeteorParser.streamIndexAccess_return streamIndexAccess() throws RecognitionException {
        MeteorParser.streamIndexAccess_return retval = new MeteorParser.streamIndexAccess_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token op=null;
        Token char_literal94=null;
        Token char_literal95=null;
        MeteorParser.generalPathExpression_return path =null;


        EvaluationExpression op_tree=null;
        EvaluationExpression char_literal94_tree=null;
        EvaluationExpression char_literal95_tree=null;
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleSubtreeStream stream_generalPathExpression=new RewriteRuleSubtreeStream(adaptor,"rule generalPathExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:271:3: (op= VAR {...}? => '[' path= generalPathExpression ']' {...}? ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:271:5: op= VAR {...}? => '[' path= generalPathExpression ']' {...}?
            {
            op=(Token)match(input,VAR,FOLLOW_VAR_in_streamIndexAccess2088); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_VAR.add(op);


            if ( !(( getVariable(op) != null )) ) {
                if (state.backtracking>0) {state.failed=true; return retval;}
                throw new FailedPredicateException(input, "streamIndexAccess", " getVariable($op) != null ");
            }

            char_literal94=(Token)match(input,58,FOLLOW_58_in_streamIndexAccess2097); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_58.add(char_literal94);


            pushFollow(FOLLOW_generalPathExpression_in_streamIndexAccess2101);
            path=generalPathExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_generalPathExpression.add(path.getTree());

            char_literal95=(Token)match(input,59,FOLLOW_59_in_streamIndexAccess2103); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_59.add(char_literal95);


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
            // 273:3: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:275:1: arrayCreation : '[' elems+= expression ( ',' elems+= expression )* ( ',' )? ']' -> ^( EXPRESSION[\"ArrayCreation\"] ) ;
    public final MeteorParser.arrayCreation_return arrayCreation() throws RecognitionException {
        MeteorParser.arrayCreation_return retval = new MeteorParser.arrayCreation_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token char_literal96=null;
        Token char_literal97=null;
        Token char_literal98=null;
        Token char_literal99=null;
        List list_elems=null;
        RuleReturnScope elems = null;
        EvaluationExpression char_literal96_tree=null;
        EvaluationExpression char_literal97_tree=null;
        EvaluationExpression char_literal98_tree=null;
        EvaluationExpression char_literal99_tree=null;
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
         paraphrase.push("a json array"); 
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:278:2: ( '[' elems+= expression ( ',' elems+= expression )* ( ',' )? ']' -> ^( EXPRESSION[\"ArrayCreation\"] ) )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:278:5: '[' elems+= expression ( ',' elems+= expression )* ( ',' )? ']'
            {
            char_literal96=(Token)match(input,58,FOLLOW_58_in_arrayCreation2132); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_58.add(char_literal96);


            pushFollow(FOLLOW_expression_in_arrayCreation2136);
            elems=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expression.add(elems.getTree());
            if (list_elems==null) list_elems=new ArrayList();
            list_elems.add(elems.getTree());


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:278:27: ( ',' elems+= expression )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==44) ) {
                    int LA49_1 = input.LA(2);

                    if ( (LA49_1==DECIMAL||LA49_1==FN||LA49_1==ID||LA49_1==INTEGER||(LA49_1 >= STRING && LA49_1 <= UINT)||LA49_1==VAR||LA49_1==36||(LA49_1 >= 39 && LA49_1 <= 40)||LA49_1==43||LA49_1==46||LA49_1==58||LA49_1==60||(LA49_1 >= 62 && LA49_1 <= 63)||LA49_1==65||(LA49_1 >= 67 && LA49_1 <= 68)||LA49_1==71) ) {
                        alt49=1;
                    }


                }


                switch (alt49) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:278:28: ',' elems+= expression
            	    {
            	    char_literal97=(Token)match(input,44,FOLLOW_44_in_arrayCreation2139); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_44.add(char_literal97);


            	    pushFollow(FOLLOW_expression_in_arrayCreation2143);
            	    elems=expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_expression.add(elems.getTree());
            	    if (list_elems==null) list_elems=new ArrayList();
            	    list_elems.add(elems.getTree());


            	    }
            	    break;

            	default :
            	    break loop49;
                }
            } while (true);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:278:52: ( ',' )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==44) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:278:52: ','
                    {
                    char_literal98=(Token)match(input,44,FOLLOW_44_in_arrayCreation2147); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_44.add(char_literal98);


                    }
                    break;

            }


            char_literal99=(Token)match(input,59,FOLLOW_59_in_arrayCreation2150); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_59.add(char_literal99);


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
            // 278:61: -> ^( EXPRESSION[\"ArrayCreation\"] )
            {
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:278:64: ^( EXPRESSION[\"ArrayCreation\"] )
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:284:1: operator returns [Operator<?> op=null] : ( readOperator | writeOperator | genericOperator );
    public final MeteorParser.operator_return operator() throws RecognitionException {
        operator_stack.push(new operator_scope());
        MeteorParser.operator_return retval = new MeteorParser.operator_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.readOperator_return readOperator100 =null;

        MeteorParser.writeOperator_return writeOperator101 =null;

        MeteorParser.genericOperator_return genericOperator102 =null;




          if(state.backtracking == 0) 
        	  addScope();

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:295:2: ( readOperator | writeOperator | genericOperator )
            int alt51=3;
            switch ( input.LA(1) ) {
            case 63:
                {
                alt51=1;
                }
                break;
            case 67:
                {
                alt51=2;
                }
                break;
            case ID:
                {
                alt51=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 51, 0, input);

                throw nvae;

            }

            switch (alt51) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:295:4: readOperator
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_readOperator_in_operator2186);
                    readOperator100=readOperator();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, readOperator100.getTree());

                    if ( state.backtracking==0 ) { retval.op = (readOperator100!=null?readOperator100.source:null); }

                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:296:5: writeOperator
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_writeOperator_in_operator2194);
                    writeOperator101=writeOperator();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, writeOperator101.getTree());

                    if ( state.backtracking==0 ) { retval.op = (writeOperator101!=null?writeOperator101.sink:null); }

                    }
                    break;
                case 3 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:297:5: genericOperator
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_genericOperator_in_operator2202);
                    genericOperator102=genericOperator();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, genericOperator102.getTree());

                    if ( state.backtracking==0 ) { retval.op = (genericOperator102!=null?genericOperator102.op:null); }

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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:300:1: readOperator returns [Source source] : 'read' ( (packageName= ID ':' )? format= ID )? 'from' ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' ) ( confOption[fileFormatInfo, fileFormat] )* ->;
    public final MeteorParser.readOperator_return readOperator() throws RecognitionException {
        MeteorParser.readOperator_return retval = new MeteorParser.readOperator_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token packageName=null;
        Token format=null;
        Token protocol=null;
        Token filePath=null;
        Token string_literal103=null;
        Token char_literal104=null;
        Token string_literal105=null;
        Token char_literal106=null;
        Token char_literal107=null;
        MeteorParser.confOption_return confOption108 =null;


        EvaluationExpression packageName_tree=null;
        EvaluationExpression format_tree=null;
        EvaluationExpression protocol_tree=null;
        EvaluationExpression filePath_tree=null;
        EvaluationExpression string_literal103_tree=null;
        EvaluationExpression char_literal104_tree=null;
        EvaluationExpression string_literal105_tree=null;
        EvaluationExpression char_literal106_tree=null;
        EvaluationExpression char_literal107_tree=null;
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
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:306:2: ( 'read' ( (packageName= ID ':' )? format= ID )? 'from' ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' ) ( confOption[fileFormatInfo, fileFormat] )* ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:306:4: 'read' ( (packageName= ID ':' )? format= ID )? 'from' ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' ) ( confOption[fileFormatInfo, fileFormat] )*
            {
            string_literal103=(Token)match(input,63,FOLLOW_63_in_readOperator2224); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_63.add(string_literal103);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:306:11: ( (packageName= ID ':' )? format= ID )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==ID) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:306:12: (packageName= ID ':' )? format= ID
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:306:12: (packageName= ID ':' )?
                    int alt52=2;
                    int LA52_0 = input.LA(1);

                    if ( (LA52_0==ID) ) {
                        int LA52_1 = input.LA(2);

                        if ( (LA52_1==48) ) {
                            alt52=1;
                        }
                    }
                    switch (alt52) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:306:13: packageName= ID ':'
                            {
                            packageName=(Token)match(input,ID,FOLLOW_ID_in_readOperator2230); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_ID.add(packageName);


                            char_literal104=(Token)match(input,48,FOLLOW_48_in_readOperator2232); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_48.add(char_literal104);


                            }
                            break;

                    }


                    format=(Token)match(input,ID,FOLLOW_ID_in_readOperator2237); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(format);


                    }
                    break;

            }


            string_literal105=(Token)match(input,61,FOLLOW_61_in_readOperator2244); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_61.add(string_literal105);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:307:11: ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' )
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==ID) ) {
                int LA55_1 = input.LA(2);

                if ( (LA55_1==40) ) {
                    alt55=2;
                }
                else if ( (LA55_1==STRING) ) {
                    alt55=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 55, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA55_0==STRING) ) {
                alt55=1;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 55, 0, input);

                throw nvae;

            }
            switch (alt55) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:307:12: (protocol= ID )? filePath= STRING
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:307:20: (protocol= ID )?
                    int alt54=2;
                    int LA54_0 = input.LA(1);

                    if ( (LA54_0==ID) ) {
                        alt54=1;
                    }
                    switch (alt54) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:307:20: protocol= ID
                            {
                            protocol=(Token)match(input,ID,FOLLOW_ID_in_readOperator2249); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_ID.add(protocol);


                            }
                            break;

                    }


                    filePath=(Token)match(input,STRING,FOLLOW_STRING_in_readOperator2254); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_STRING.add(filePath);


                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:307:43: protocol= ID '(' filePath= STRING ')'
                    {
                    protocol=(Token)match(input,ID,FOLLOW_ID_in_readOperator2260); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(protocol);


                    char_literal106=(Token)match(input,40,FOLLOW_40_in_readOperator2262); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_40.add(char_literal106);


                    filePath=(Token)match(input,STRING,FOLLOW_STRING_in_readOperator2266); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_STRING.add(filePath);


                    char_literal107=(Token)match(input,41,FOLLOW_41_in_readOperator2268); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_41.add(char_literal107);


                    }
                    break;

            }


            if ( state.backtracking==0 ) { 
              path = makeFilePath(protocol, (filePath!=null?filePath.getText():null));
              fileFormatInfo = findFormat((packageName!=null?packageName.getText():null), format, path);
              fileFormat = fileFormatInfo.newInstance(); 
            }

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:313:4: ( confOption[fileFormatInfo, fileFormat] )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==ID) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:313:4: confOption[fileFormatInfo, fileFormat]
            	    {
            	    pushFollow(FOLLOW_confOption_in_readOperator2277);
            	    confOption108=confOption(fileFormatInfo, fileFormat);

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_confOption.add(confOption108.getTree());

            	    }
            	    break;

            	default :
            	    break loop56;
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
            // 314:45: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:317:1: writeOperator returns [Sink sink] : 'write' ( (packageName= ID ':' )? format= ID )? from= VAR 'to' ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' ) ( confOption[fileFormatInfo, fileFormat] )* ->;
    public final MeteorParser.writeOperator_return writeOperator() throws RecognitionException {
        MeteorParser.writeOperator_return retval = new MeteorParser.writeOperator_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token packageName=null;
        Token format=null;
        Token from=null;
        Token protocol=null;
        Token filePath=null;
        Token string_literal109=null;
        Token char_literal110=null;
        Token string_literal111=null;
        Token char_literal112=null;
        Token char_literal113=null;
        MeteorParser.confOption_return confOption114 =null;


        EvaluationExpression packageName_tree=null;
        EvaluationExpression format_tree=null;
        EvaluationExpression from_tree=null;
        EvaluationExpression protocol_tree=null;
        EvaluationExpression filePath_tree=null;
        EvaluationExpression string_literal109_tree=null;
        EvaluationExpression char_literal110_tree=null;
        EvaluationExpression string_literal111_tree=null;
        EvaluationExpression char_literal112_tree=null;
        EvaluationExpression char_literal113_tree=null;
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
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:323:2: ( 'write' ( (packageName= ID ':' )? format= ID )? from= VAR 'to' ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' ) ( confOption[fileFormatInfo, fileFormat] )* ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:323:4: 'write' ( (packageName= ID ':' )? format= ID )? from= VAR 'to' ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' ) ( confOption[fileFormatInfo, fileFormat] )*
            {
            string_literal109=(Token)match(input,67,FOLLOW_67_in_writeOperator2303); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_67.add(string_literal109);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:323:12: ( (packageName= ID ':' )? format= ID )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==ID) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:323:13: (packageName= ID ':' )? format= ID
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:323:13: (packageName= ID ':' )?
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:323:14: packageName= ID ':'
                            {
                            packageName=(Token)match(input,ID,FOLLOW_ID_in_writeOperator2309); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_ID.add(packageName);


                            char_literal110=(Token)match(input,48,FOLLOW_48_in_writeOperator2311); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_48.add(char_literal110);


                            }
                            break;

                    }


                    format=(Token)match(input,ID,FOLLOW_ID_in_writeOperator2316); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(format);


                    }
                    break;

            }


            from=(Token)match(input,VAR,FOLLOW_VAR_in_writeOperator2322); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_VAR.add(from);


            string_literal111=(Token)match(input,64,FOLLOW_64_in_writeOperator2328); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_64.add(string_literal111);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:324:9: ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' )
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==ID) ) {
                int LA60_1 = input.LA(2);

                if ( (LA60_1==40) ) {
                    alt60=2;
                }
                else if ( (LA60_1==STRING) ) {
                    alt60=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 60, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA60_0==STRING) ) {
                alt60=1;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 60, 0, input);

                throw nvae;

            }
            switch (alt60) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:324:10: (protocol= ID )? filePath= STRING
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:324:18: (protocol= ID )?
                    int alt59=2;
                    int LA59_0 = input.LA(1);

                    if ( (LA59_0==ID) ) {
                        alt59=1;
                    }
                    switch (alt59) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:324:18: protocol= ID
                            {
                            protocol=(Token)match(input,ID,FOLLOW_ID_in_writeOperator2333); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_ID.add(protocol);


                            }
                            break;

                    }


                    filePath=(Token)match(input,STRING,FOLLOW_STRING_in_writeOperator2338); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_STRING.add(filePath);


                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:324:41: protocol= ID '(' filePath= STRING ')'
                    {
                    protocol=(Token)match(input,ID,FOLLOW_ID_in_writeOperator2344); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(protocol);


                    char_literal112=(Token)match(input,40,FOLLOW_40_in_writeOperator2346); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_40.add(char_literal112);


                    filePath=(Token)match(input,STRING,FOLLOW_STRING_in_writeOperator2350); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_STRING.add(filePath);


                    char_literal113=(Token)match(input,41,FOLLOW_41_in_writeOperator2352); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_41.add(char_literal113);


                    }
                    break;

            }


            if ( state.backtracking==0 ) { 
              path = makeFilePath(protocol, (filePath!=null?filePath.getText():null));
              fileFormatInfo = findFormat((packageName!=null?packageName.getText():null), format, path);
              fileFormat = fileFormatInfo.newInstance();
            }

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:330:5: ( confOption[fileFormatInfo, fileFormat] )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==ID) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:330:5: confOption[fileFormatInfo, fileFormat]
            	    {
            	    pushFollow(FOLLOW_confOption_in_writeOperator2361);
            	    confOption114=confOption(fileFormatInfo, fileFormat);

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_confOption.add(confOption114.getTree());

            	    }
            	    break;

            	default :
            	    break loop61;
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
            // 335:3: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:341:1: genericOperator returns [Operator<?> op] : (packageName= ID ':' )? name= ID {...}? => ( operatorFlag[operatorInfo, $op] )* ( ( '[' )=> arrayInput | ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )* ) ( confOption[operatorInfo, $op] )* ->;
    public final MeteorParser.genericOperator_return genericOperator() throws RecognitionException {
        MeteorParser.genericOperator_return retval = new MeteorParser.genericOperator_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token packageName=null;
        Token name=null;
        Token char_literal115=null;
        Token char_literal119=null;
        MeteorParser.operatorFlag_return operatorFlag116 =null;

        MeteorParser.arrayInput_return arrayInput117 =null;

        MeteorParser.input_return input118 =null;

        MeteorParser.input_return input120 =null;

        MeteorParser.confOption_return confOption121 =null;


        EvaluationExpression packageName_tree=null;
        EvaluationExpression name_tree=null;
        EvaluationExpression char_literal115_tree=null;
        EvaluationExpression char_literal119_tree=null;
        RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_input=new RewriteRuleSubtreeStream(adaptor,"rule input");
        RewriteRuleSubtreeStream stream_operatorFlag=new RewriteRuleSubtreeStream(adaptor,"rule operatorFlag");
        RewriteRuleSubtreeStream stream_arrayInput=new RewriteRuleSubtreeStream(adaptor,"rule arrayInput");
        RewriteRuleSubtreeStream stream_confOption=new RewriteRuleSubtreeStream(adaptor,"rule confOption");
         
          ConfObjectInfo<? extends Operator<?>> operatorInfo;

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:344:3: ( (packageName= ID ':' )? name= ID {...}? => ( operatorFlag[operatorInfo, $op] )* ( ( '[' )=> arrayInput | ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )* ) ( confOption[operatorInfo, $op] )* ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:344:5: (packageName= ID ':' )? name= ID {...}? => ( operatorFlag[operatorInfo, $op] )* ( ( '[' )=> arrayInput | ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )* ) ( confOption[operatorInfo, $op] )*
            {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:344:5: (packageName= ID ':' )?
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:344:6: packageName= ID ':'
                    {
                    packageName=(Token)match(input,ID,FOLLOW_ID_in_genericOperator2392); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(packageName);


                    char_literal115=(Token)match(input,48,FOLLOW_48_in_genericOperator2394); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_48.add(char_literal115);


                    }
                    break;

            }


            name=(Token)match(input,ID,FOLLOW_ID_in_genericOperator2400); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(name);


            if ( !(( (operatorInfo = findOperatorGreedily((packageName!=null?packageName.getText():null), name)) != null )) ) {
                if (state.backtracking>0) {state.failed=true; return retval;}
                throw new FailedPredicateException(input, "genericOperator", " (operatorInfo = findOperatorGreedily($packageName.text, $name)) != null ");
            }

            if ( state.backtracking==0 ) { ((operator_scope)operator_stack.peek()).result = retval.op = operatorInfo.newInstance(); }

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:346:13: ( operatorFlag[operatorInfo, $op] )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==ID) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:346:13: operatorFlag[operatorInfo, $op]
            	    {
            	    pushFollow(FOLLOW_operatorFlag_in_genericOperator2408);
            	    operatorFlag116=operatorFlag(operatorInfo, retval.op);

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_operatorFlag.add(operatorFlag116.getTree());

            	    }
            	    break;

            	default :
            	    break loop63;
                }
            } while (true);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:347:2: ( ( '[' )=> arrayInput | ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )* )
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==58) && (synpred22_Meteor())) {
                alt65=1;
            }
            else if ( (LA65_0==VAR) && (synpred23_Meteor())) {
                alt65=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 65, 0, input);

                throw nvae;

            }
            switch (alt65) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:347:2: ( '[' )=> arrayInput
                    {
                    pushFollow(FOLLOW_arrayInput_in_genericOperator2418);
                    arrayInput117=arrayInput();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_arrayInput.add(arrayInput117.getTree());

                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:347:23: ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )*
                    {
                    pushFollow(FOLLOW_input_in_genericOperator2427);
                    input118=input(operatorInfo, retval.op);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_input.add(input118.getTree());

                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:347:56: ( ( ',' )=> ',' input[operatorInfo, $op] )*
                    loop64:
                    do {
                        int alt64=2;
                        int LA64_0 = input.LA(1);

                        if ( (LA64_0==44) ) {
                            int LA64_2 = input.LA(2);

                            if ( (LA64_2==VAR) ) {
                                int LA64_3 = input.LA(3);

                                if ( (synpred24_Meteor()) ) {
                                    alt64=1;
                                }


                            }


                        }


                        switch (alt64) {
                    	case 1 :
                    	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:347:57: ( ',' )=> ',' input[operatorInfo, $op]
                    	    {
                    	    char_literal119=(Token)match(input,44,FOLLOW_44_in_genericOperator2436); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_44.add(char_literal119);


                    	    pushFollow(FOLLOW_input_in_genericOperator2438);
                    	    input120=input(operatorInfo, retval.op);

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_input.add(input120.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop64;
                        }
                    } while (true);


                    }
                    break;

            }


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:348:11: ( confOption[operatorInfo, $op] )*
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( (LA66_0==ID) ) {
                    alt66=1;
                }


                switch (alt66) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:348:11: confOption[operatorInfo, $op]
            	    {
            	    pushFollow(FOLLOW_confOption_in_genericOperator2444);
            	    confOption121=confOption(operatorInfo, retval.op);

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_confOption.add(confOption121.getTree());

            	    }
            	    break;

            	default :
            	    break loop66;
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
            // 349:3: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:351:1: confOption[ConfObjectInfo<?> info, ConfigurableSopremoType object] : name= ID expr= contextAwareExpression[null] ->;
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
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:354:3: (name= ID expr= contextAwareExpression[null] ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:355:3: name= ID expr= contextAwareExpression[null]
            {
            name=(Token)match(input,ID,FOLLOW_ID_in_confOption2471); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(name);


            if ( state.backtracking==0 ) { property = findPropertyRelunctantly(info, name); }

            pushFollow(FOLLOW_contextAwareExpression_in_confOption2480);
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
            // 357:80: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:359:1: operatorFlag[ConfObjectInfo<?> info, ConfigurableSopremoType object] : name= ID {...}? ->;
    public final MeteorParser.operatorFlag_return operatorFlag(ConfObjectInfo<?> info, ConfigurableSopremoType object) throws RecognitionException {
        MeteorParser.operatorFlag_return retval = new MeteorParser.operatorFlag_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token name=null;

        EvaluationExpression name_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");


         ConfObjectInfo.ConfObjectPropertyInfo property = null;

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:363:3: (name= ID {...}? ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:363:5: name= ID {...}?
            {
            name=(Token)match(input,ID,FOLLOW_ID_in_operatorFlag2504); if (state.failed) return retval; 
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
            // 366:38: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:368:1: input[ConfObjectInfo<?> info, Operator<?> object] : (name= VAR IN )? from= VAR ({...}? =>expr= contextAwareExpression[new InputSelection($operator::numInputs - 1)] )? ->;
    public final MeteorParser.input_return input(ConfObjectInfo<?> info, Operator<?> object) throws RecognitionException {
        MeteorParser.input_return retval = new MeteorParser.input_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token name=null;
        Token from=null;
        Token IN122=null;
        MeteorParser.contextAwareExpression_return expr =null;


        EvaluationExpression name_tree=null;
        EvaluationExpression from_tree=null;
        EvaluationExpression IN122_tree=null;
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_IN=new RewriteRuleTokenStream(adaptor,"token IN");
        RewriteRuleSubtreeStream stream_contextAwareExpression=new RewriteRuleSubtreeStream(adaptor,"rule contextAwareExpression");

         ConfObjectInfo.ConfObjectIndexedPropertyInfo inputProperty;

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:371:3: ( (name= VAR IN )? from= VAR ({...}? =>expr= contextAwareExpression[new InputSelection($operator::numInputs - 1)] )? ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:371:5: (name= VAR IN )? from= VAR ({...}? =>expr= contextAwareExpression[new InputSelection($operator::numInputs - 1)] )?
            {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:371:5: (name= VAR IN )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==VAR) ) {
                int LA67_1 = input.LA(2);

                if ( (LA67_1==IN) ) {
                    alt67=1;
                }
            }
            switch (alt67) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:371:6: name= VAR IN
                    {
                    name=(Token)match(input,VAR,FOLLOW_VAR_in_input2529); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_VAR.add(name);


                    IN122=(Token)match(input,IN,FOLLOW_IN_in_input2531); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_IN.add(IN122);


                    }
                    break;

            }


            from=(Token)match(input,VAR,FOLLOW_VAR_in_input2537); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_VAR.add(from);


            if ( state.backtracking==0 ) { 
              int inputIndex = ((operator_scope)operator_stack.peek()).numInputs++;
              JsonStreamExpression input = getVariable(from);
              object.setInput(inputIndex, input.getStream());
              
              JsonStreamExpression inputExpression = new JsonStreamExpression(input.getStream(), inputIndex);
              putVariable(name != null ? name : from, inputExpression);
            }

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:380:2: ({...}? =>expr= contextAwareExpression[new InputSelection($operator::numInputs - 1)] )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==DECIMAL||LA68_0==FN||LA68_0==INTEGER||(LA68_0 >= STRING && LA68_0 <= UINT)||LA68_0==VAR||LA68_0==36||(LA68_0 >= 39 && LA68_0 <= 40)||LA68_0==43||LA68_0==46||LA68_0==58||LA68_0==60||LA68_0==62||LA68_0==65||LA68_0==68||LA68_0==71) && (( (inputProperty = findInputPropertyRelunctantly(info, input.LT(1))) != null ))) {
                alt68=1;
            }
            else if ( (LA68_0==ID) ) {
                int LA68_5 = input.LA(2);

                if ( (( (inputProperty = findInputPropertyRelunctantly(info, input.LT(1))) != null )) ) {
                    alt68=1;
                }
            }
            switch (alt68) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:380:2: {...}? =>expr= contextAwareExpression[new InputSelection($operator::numInputs - 1)]
                    {
                    if ( !(( (inputProperty = findInputPropertyRelunctantly(info, input.LT(1))) != null )) ) {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        throw new FailedPredicateException(input, "input", " (inputProperty = findInputPropertyRelunctantly(info, input.LT(1))) != null ");
                    }

                    if ( state.backtracking==0 ) { this.input.consume(); }

                    pushFollow(FOLLOW_contextAwareExpression_in_input2556);
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
            // 383:4: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:385:1: arrayInput : '[' names+= VAR ( ',' names+= VAR )? ']' 'in' from= VAR ->;
    public final MeteorParser.arrayInput_return arrayInput() throws RecognitionException {
        MeteorParser.arrayInput_return retval = new MeteorParser.arrayInput_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token from=null;
        Token char_literal123=null;
        Token char_literal124=null;
        Token char_literal125=null;
        Token string_literal126=null;
        Token names=null;
        List list_names=null;

        EvaluationExpression from_tree=null;
        EvaluationExpression char_literal123_tree=null;
        EvaluationExpression char_literal124_tree=null;
        EvaluationExpression char_literal125_tree=null;
        EvaluationExpression string_literal126_tree=null;
        EvaluationExpression names_tree=null;
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_IN=new RewriteRuleTokenStream(adaptor,"token IN");
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:386:3: ( '[' names+= VAR ( ',' names+= VAR )? ']' 'in' from= VAR ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:386:5: '[' names+= VAR ( ',' names+= VAR )? ']' 'in' from= VAR
            {
            char_literal123=(Token)match(input,58,FOLLOW_58_in_arrayInput2574); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_58.add(char_literal123);


            names=(Token)match(input,VAR,FOLLOW_VAR_in_arrayInput2578); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_VAR.add(names);

            if (list_names==null) list_names=new ArrayList();
            list_names.add(names);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:386:20: ( ',' names+= VAR )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==44) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:386:21: ',' names+= VAR
                    {
                    char_literal124=(Token)match(input,44,FOLLOW_44_in_arrayInput2581); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_44.add(char_literal124);


                    names=(Token)match(input,VAR,FOLLOW_VAR_in_arrayInput2585); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_VAR.add(names);

                    if (list_names==null) list_names=new ArrayList();
                    list_names.add(names);


                    }
                    break;

            }


            char_literal125=(Token)match(input,59,FOLLOW_59_in_arrayInput2589); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_59.add(char_literal125);


            string_literal126=(Token)match(input,IN,FOLLOW_IN_in_arrayInput2591); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_IN.add(string_literal126);


            from=(Token)match(input,VAR,FOLLOW_VAR_in_arrayInput2595); if (state.failed) return retval; 
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
            // 392:3: ->
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
        pushFollow(FOLLOW_operatorExpression_in_synpred1_Meteor390);
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
        pushFollow(FOLLOW_orExpression_in_synpred2_Meteor410);
        orExpression();

        state._fsp--;
        if (state.failed) return ;

        match(input,56,FOLLOW_56_in_synpred2_Meteor412); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred2_Meteor

    // $ANTLR start synpred3_Meteor
    public final void synpred3_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:111:4: ( orExpression IF )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:111:5: orExpression IF
        {
        pushFollow(FOLLOW_orExpression_in_synpred3_Meteor452);
        orExpression();

        state._fsp--;
        if (state.failed) return ;

        match(input,IF,FOLLOW_IF_in_synpred3_Meteor454); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred3_Meteor

    // $ANTLR start synpred4_Meteor
    public final void synpred4_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:159:4: ( '(' ID ')' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:159:5: '(' ID ')'
        {
        match(input,40,FOLLOW_40_in_synpred4_Meteor921); if (state.failed) return ;

        match(input,ID,FOLLOW_ID_in_synpred4_Meteor923); if (state.failed) return ;

        match(input,41,FOLLOW_41_in_synpred4_Meteor925); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred4_Meteor

    // $ANTLR start synpred5_Meteor
    public final void synpred5_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:161:4: ( generalPathExpression AS )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:161:5: generalPathExpression AS
        {
        pushFollow(FOLLOW_generalPathExpression_in_synpred5_Meteor951);
        generalPathExpression();

        state._fsp--;
        if (state.failed) return ;

        match(input,AS,FOLLOW_AS_in_synpred5_Meteor953); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred5_Meteor

    // $ANTLR start synpred6_Meteor
    public final void synpred6_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:167:5: ( pathExpression[EvaluationExpression.VALUE] )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:167:6: pathExpression[EvaluationExpression.VALUE]
        {
        pushFollow(FOLLOW_pathExpression_in_synpred6_Meteor996);
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
        match(input,57,FOLLOW_57_in_synpred7_Meteor1052); if (state.failed) return ;

        match(input,ID,FOLLOW_ID_in_synpred7_Meteor1054); if (state.failed) return ;

        match(input,40,FOLLOW_40_in_synpred7_Meteor1056); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred7_Meteor

    // $ANTLR start synpred8_Meteor
    public final void synpred8_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:176:8: ( pathSegment )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:176:9: pathSegment
        {
        pushFollow(FOLLOW_pathSegment_in_synpred8_Meteor1075);
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
        match(input,47,FOLLOW_47_in_synpred9_Meteor1123); if (state.failed) return ;

        match(input,ID,FOLLOW_ID_in_synpred9_Meteor1125); if (state.failed) return ;

        match(input,40,FOLLOW_40_in_synpred9_Meteor1127); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred9_Meteor

    // $ANTLR start synpred10_Meteor
    public final void synpred10_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:180:8: ( pathSegment )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:180:9: pathSegment
        {
        pushFollow(FOLLOW_pathSegment_in_synpred10_Meteor1146);
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
        pushFollow(FOLLOW_pathSegment_in_synpred11_Meteor1188);
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
        match(input,57,FOLLOW_57_in_synpred12_Meteor1242); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred12_Meteor

    // $ANTLR start synpred13_Meteor
    public final void synpred13_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:191:5: ( '.' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:191:6: '.'
        {
        match(input,47,FOLLOW_47_in_synpred13_Meteor1280); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred13_Meteor

    // $ANTLR start synpred14_Meteor
    public final void synpred14_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:192:5: ( '[' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:192:6: '['
        {
        match(input,58,FOLLOW_58_in_synpred14_Meteor1309); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred14_Meteor

    // $ANTLR start synpred15_Meteor
    public final void synpred15_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:195:19: ( '.' methodCall[null] )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:195:20: '.' methodCall[null]
        {
        match(input,47,FOLLOW_47_in_synpred15_Meteor1332); if (state.failed) return ;

        pushFollow(FOLLOW_methodCall_in_synpred15_Meteor1334);
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
        match(input,ID,FOLLOW_ID_in_synpred16_Meteor1473); if (state.failed) return ;

        match(input,40,FOLLOW_40_in_synpred16_Meteor1475); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred16_Meteor

    // $ANTLR start synpred17_Meteor
    public final void synpred17_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:207:5: ( FN )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:207:6: FN
        {
        match(input,FN,FOLLOW_FN_in_synpred17_Meteor1491); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred17_Meteor

    // $ANTLR start synpred18_Meteor
    public final void synpred18_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:210:5: ( VAR '[' VAR )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:210:6: VAR '[' VAR
        {
        match(input,VAR,FOLLOW_VAR_in_synpred18_Meteor1525); if (state.failed) return ;

        match(input,58,FOLLOW_58_in_synpred18_Meteor1527); if (state.failed) return ;

        match(input,VAR,FOLLOW_VAR_in_synpred18_Meteor1529); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred18_Meteor

    // $ANTLR start synpred19_Meteor
    public final void synpred19_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:212:6: ( ID ':' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:212:7: ID ':'
        {
        match(input,ID,FOLLOW_ID_in_synpred19_Meteor1550); if (state.failed) return ;

        match(input,48,FOLLOW_48_in_synpred19_Meteor1552); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred19_Meteor

    // $ANTLR start synpred20_Meteor
    public final void synpred20_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:236:10: ( ID ':' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:236:11: ID ':'
        {
        match(input,ID,FOLLOW_ID_in_synpred20_Meteor1732); if (state.failed) return ;

        match(input,48,FOLLOW_48_in_synpred20_Meteor1734); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred20_Meteor

    // $ANTLR start synpred21_Meteor
    public final void synpred21_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:240:5: ( ID ':' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:240:6: ID ':'
        {
        match(input,ID,FOLLOW_ID_in_synpred21_Meteor1778); if (state.failed) return ;

        match(input,48,FOLLOW_48_in_synpred21_Meteor1780); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred21_Meteor

    // $ANTLR start synpred22_Meteor
    public final void synpred22_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:347:2: ( '[' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:347:3: '['
        {
        match(input,58,FOLLOW_58_in_synpred22_Meteor2414); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred22_Meteor

    // $ANTLR start synpred23_Meteor
    public final void synpred23_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:347:23: ( VAR )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:347:24: VAR
        {
        match(input,VAR,FOLLOW_VAR_in_synpred23_Meteor2423); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred23_Meteor

    // $ANTLR start synpred24_Meteor
    public final void synpred24_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:347:57: ( ',' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:347:58: ','
        {
        match(input,44,FOLLOW_44_in_synpred24_Meteor2432); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred24_Meteor

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
    public static final BitSet FOLLOW_41_in_inlineFunction302 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_inlineFunction312 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_contextAwareExpression_in_inlineFunction316 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_inlineFunction319 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_javaudf339 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_javaudf341 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_JAVAUDF_in_javaudf343 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_javaudf345 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_STRING_in_javaudf349 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_javaudf351 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ternaryExpression_in_contextAwareExpression379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operatorExpression_in_expression394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ternaryExpression_in_expression400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression418 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_ternaryExpression420 = new BitSet(new long[]{0x54014994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression424 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_ternaryExpression427 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression431 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression460 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_IF_in_ternaryExpression462 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression466 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andExpression_in_orExpression502 = new BitSet(new long[]{0x0000000002000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_OR_in_orExpression506 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_69_in_orExpression510 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_andExpression_in_orExpression515 = new BitSet(new long[]{0x0000000002000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_elementExpression_in_andExpression544 = new BitSet(new long[]{0x0000004000000012L});
    public static final BitSet FOLLOW_AND_in_andExpression548 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_38_in_andExpression552 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_elementExpression_in_andExpression557 = new BitSet(new long[]{0x0000004000000012L});
    public static final BitSet FOLLOW_comparisonExpression_in_elementExpression586 = new BitSet(new long[]{0x0000000000440002L});
    public static final BitSet FOLLOW_NOT_in_elementExpression591 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_IN_in_elementExpression594 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_comparisonExpression_in_elementExpression598 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arithmeticExpression_in_comparisonExpression639 = new BitSet(new long[]{0x00EC002000000002L});
    public static final BitSet FOLLOW_51_in_comparisonExpression645 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_55_in_comparisonExpression651 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_50_in_comparisonExpression657 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_54_in_comparisonExpression663 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_53_in_comparisonExpression669 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_37_in_comparisonExpression675 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_arithmeticExpression_in_comparisonExpression680 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicationExpression_in_arithmeticExpression760 = new BitSet(new long[]{0x0000240000000002L});
    public static final BitSet FOLLOW_42_in_arithmeticExpression766 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_45_in_arithmeticExpression772 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_multiplicationExpression_in_arithmeticExpression777 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_preincrementExpression_in_multiplicationExpression820 = new BitSet(new long[]{0x0000000030000002L});
    public static final BitSet FOLLOW_STAR_in_multiplicationExpression826 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_SLASH_in_multiplicationExpression832 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_preincrementExpression_in_multiplicationExpression837 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_preincrementExpression878 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_preincrementExpression_in_preincrementExpression880 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_preincrementExpression885 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_preincrementExpression_in_preincrementExpression887 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unaryExpression_in_preincrementExpression892 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_castExpression_in_unaryExpression911 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_castExpression929 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_castExpression933 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_castExpression935 = new BitSet(new long[]{0x54000184C0094100L,0x0000000000000012L});
    public static final BitSet FOLLOW_generalPathExpression_in_castExpression939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_generalPathExpression_in_castExpression959 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_AS_in_castExpression961 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_castExpression965 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_generalPathExpression_in_castExpression976 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_valueExpression_in_generalPathExpression988 = new BitSet(new long[]{0x0600800000000002L});
    public static final BitSet FOLLOW_pathExpression_in_generalPathExpression1003 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathExpression_in_contextAwarePathExpression1032 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_pathExpression1060 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_methodCall_in_pathExpression1064 = new BitSet(new long[]{0x0600800000000002L});
    public static final BitSet FOLLOW_pathExpression_in_pathExpression1081 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_pathExpression1131 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_methodCall_in_pathExpression1135 = new BitSet(new long[]{0x0600800000000002L});
    public static final BitSet FOLLOW_pathExpression_in_pathExpression1152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathSegment_in_pathExpression1178 = new BitSet(new long[]{0x0600800000000002L});
    public static final BitSet FOLLOW_pathExpression_in_pathExpression1194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_pathSegment1246 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_pathSegment1250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_pathSegment1285 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_pathSegment1289 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayAccess_in_pathSegment1314 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_arrayAccess1324 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_STAR_in_arrayAccess1326 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_arrayAccess1328 = new BitSet(new long[]{0x0600800000000000L});
    public static final BitSet FOLLOW_47_in_arrayAccess1339 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_methodCall_in_arrayAccess1343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathSegment_in_arrayAccess1366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_arrayAccess1387 = new BitSet(new long[]{0x0000000080080000L});
    public static final BitSet FOLLOW_INTEGER_in_arrayAccess1392 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_UINT_in_arrayAccess1398 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_arrayAccess1401 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_arrayAccess1419 = new BitSet(new long[]{0x0000000080080000L});
    public static final BitSet FOLLOW_INTEGER_in_arrayAccess1424 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_UINT_in_arrayAccess1430 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_arrayAccess1433 = new BitSet(new long[]{0x0000000080080000L});
    public static final BitSet FOLLOW_INTEGER_in_arrayAccess1438 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_UINT_in_arrayAccess1444 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_arrayAccess1447 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functionCall_in_valueExpression1479 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functionReference_in_valueExpression1484 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inlineFunction_in_valueExpression1497 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parenthesesExpression_in_valueExpression1511 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_valueExpression1517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_streamIndexAccess_in_valueExpression1533 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_valueExpression1538 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_valueExpression1558 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_valueExpression1560 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_valueExpression1566 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayCreation_in_valueExpression1586 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objectCreation_in_valueExpression1592 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operator_in_operatorExpression1604 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_parenthesesExpression1623 = new BitSet(new long[]{0xD4004994C0094100L,0x000000000000009AL});
    public static final BitSet FOLLOW_expression_in_parenthesesExpression1625 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_parenthesesExpression1627 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_methodCall1657 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_methodCall1659 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_methodCall1665 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_methodCall1667 = new BitSet(new long[]{0xD4004B94C0094100L,0x000000000000009AL});
    public static final BitSet FOLLOW_expression_in_methodCall1676 = new BitSet(new long[]{0x0000120000000000L});
    public static final BitSet FOLLOW_44_in_methodCall1685 = new BitSet(new long[]{0xD4004994C0094100L,0x000000000000009AL});
    public static final BitSet FOLLOW_expression_in_methodCall1690 = new BitSet(new long[]{0x0000120000000000L});
    public static final BitSet FOLLOW_41_in_methodCall1702 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_methodCall_in_functionCall1717 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_functionReference1728 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_functionReference1740 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_functionReference1742 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_functionReference1748 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_fieldAssignment1784 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_fieldAssignment1786 = new BitSet(new long[]{0xD4004994C0094100L,0x000000000000009AL});
    public static final BitSet FOLLOW_expression_in_fieldAssignment1788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_fieldAssignment1805 = new BitSet(new long[]{0x0610800000000000L});
    public static final BitSet FOLLOW_47_in_fieldAssignment1814 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_STAR_in_fieldAssignment1816 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_52_in_fieldAssignment1830 = new BitSet(new long[]{0x8000000000010000L,0x0000000000000008L});
    public static final BitSet FOLLOW_operator_in_fieldAssignment1834 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_contextAwarePathExpression_in_fieldAssignment1849 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_48_in_fieldAssignment1860 = new BitSet(new long[]{0xD4004994C0094100L,0x000000000000009AL});
    public static final BitSet FOLLOW_expression_in_fieldAssignment1864 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_objectCreation1929 = new BitSet(new long[]{0x0000000400010000L,0x0000000000000040L});
    public static final BitSet FOLLOW_fieldAssignment_in_objectCreation1932 = new BitSet(new long[]{0x0000100000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_44_in_objectCreation1935 = new BitSet(new long[]{0x0000000400010000L});
    public static final BitSet FOLLOW_fieldAssignment_in_objectCreation1937 = new BitSet(new long[]{0x0000100000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_44_in_objectCreation1941 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_objectCreation1946 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_literal1984 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_literal2000 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DECIMAL_in_literal2016 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal2032 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_UINT_in_literal2050 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_literal2056 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_literal2072 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_streamIndexAccess2088 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_streamIndexAccess2097 = new BitSet(new long[]{0x54000184C0094100L,0x0000000000000012L});
    public static final BitSet FOLLOW_generalPathExpression_in_streamIndexAccess2101 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_streamIndexAccess2103 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_arrayCreation2132 = new BitSet(new long[]{0xD4004994C0094100L,0x000000000000009AL});
    public static final BitSet FOLLOW_expression_in_arrayCreation2136 = new BitSet(new long[]{0x0800100000000000L});
    public static final BitSet FOLLOW_44_in_arrayCreation2139 = new BitSet(new long[]{0xD4004994C0094100L,0x000000000000009AL});
    public static final BitSet FOLLOW_expression_in_arrayCreation2143 = new BitSet(new long[]{0x0800100000000000L});
    public static final BitSet FOLLOW_44_in_arrayCreation2147 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_arrayCreation2150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_readOperator_in_operator2186 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_writeOperator_in_operator2194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_genericOperator_in_operator2202 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_readOperator2224 = new BitSet(new long[]{0x2000000000010000L});
    public static final BitSet FOLLOW_ID_in_readOperator2230 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_readOperator2232 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_readOperator2237 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_readOperator2244 = new BitSet(new long[]{0x0000000040010000L});
    public static final BitSet FOLLOW_ID_in_readOperator2249 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_STRING_in_readOperator2254 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_ID_in_readOperator2260 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_readOperator2262 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_STRING_in_readOperator2266 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_readOperator2268 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_confOption_in_readOperator2277 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_67_in_writeOperator2303 = new BitSet(new long[]{0x0000000400010000L});
    public static final BitSet FOLLOW_ID_in_writeOperator2309 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_writeOperator2311 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_writeOperator2316 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_VAR_in_writeOperator2322 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_writeOperator2328 = new BitSet(new long[]{0x0000000040010000L});
    public static final BitSet FOLLOW_ID_in_writeOperator2333 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_STRING_in_writeOperator2338 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_ID_in_writeOperator2344 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_writeOperator2346 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_STRING_in_writeOperator2350 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_writeOperator2352 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_confOption_in_writeOperator2361 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_ID_in_genericOperator2392 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_genericOperator2394 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_genericOperator2400 = new BitSet(new long[]{0x0400000400010000L});
    public static final BitSet FOLLOW_operatorFlag_in_genericOperator2408 = new BitSet(new long[]{0x0400000400010000L});
    public static final BitSet FOLLOW_arrayInput_in_genericOperator2418 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_input_in_genericOperator2427 = new BitSet(new long[]{0x0000100000010002L});
    public static final BitSet FOLLOW_44_in_genericOperator2436 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_input_in_genericOperator2438 = new BitSet(new long[]{0x0000100000010002L});
    public static final BitSet FOLLOW_confOption_in_genericOperator2444 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_ID_in_confOption2471 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_contextAwareExpression_in_confOption2480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_operatorFlag2504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_input2529 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_IN_in_input2531 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_VAR_in_input2537 = new BitSet(new long[]{0x54004994C0094102L,0x0000000000000092L});
    public static final BitSet FOLLOW_contextAwareExpression_in_input2556 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_arrayInput2574 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_VAR_in_arrayInput2578 = new BitSet(new long[]{0x0800100000000000L});
    public static final BitSet FOLLOW_44_in_arrayInput2581 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_VAR_in_arrayInput2585 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_arrayInput2589 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_IN_in_arrayInput2591 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_VAR_in_arrayInput2595 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operatorExpression_in_synpred1_Meteor390 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpression_in_synpred2_Meteor410 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_synpred2_Meteor412 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpression_in_synpred3_Meteor452 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_IF_in_synpred3_Meteor454 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_synpred4_Meteor921 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_synpred4_Meteor923 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_synpred4_Meteor925 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_generalPathExpression_in_synpred5_Meteor951 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_AS_in_synpred5_Meteor953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathExpression_in_synpred6_Meteor996 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_synpred7_Meteor1052 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_synpred7_Meteor1054 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_synpred7_Meteor1056 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathSegment_in_synpred8_Meteor1075 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_synpred9_Meteor1123 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_synpred9_Meteor1125 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_synpred9_Meteor1127 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathSegment_in_synpred10_Meteor1146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathSegment_in_synpred11_Meteor1188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_synpred12_Meteor1242 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_synpred13_Meteor1280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_synpred14_Meteor1309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_synpred15_Meteor1332 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_methodCall_in_synpred15_Meteor1334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred16_Meteor1473 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_synpred16_Meteor1475 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FN_in_synpred17_Meteor1491 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred18_Meteor1525 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_synpred18_Meteor1527 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_VAR_in_synpred18_Meteor1529 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred19_Meteor1550 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_synpred19_Meteor1552 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred20_Meteor1732 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_synpred20_Meteor1734 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred21_Meteor1778 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_synpred21_Meteor1780 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_synpred22_Meteor2414 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred23_Meteor2423 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_synpred24_Meteor2432 = new BitSet(new long[]{0x0000000000000002L});

}