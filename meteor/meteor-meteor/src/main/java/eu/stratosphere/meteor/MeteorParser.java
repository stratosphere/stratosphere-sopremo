// $ANTLR 3.4 /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g 2013-08-16 14:26:14
 
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
                        else if ( (LA2_8==ID||LA2_8==VAR) ) {
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:72:1: assignment :targets+= VAR ( ',' targets+= VAR )* '=' source= operator ->;
    public final MeteorParser.assignment_return assignment() throws RecognitionException {
        MeteorParser.assignment_return retval = new MeteorParser.assignment_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token char_literal10=null;
        Token char_literal11=null;
        Token targets=null;
        List list_targets=null;
        MeteorParser.operator_return source =null;


        EvaluationExpression char_literal10_tree=null;
        EvaluationExpression char_literal11_tree=null;
        EvaluationExpression targets_tree=null;
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
        RewriteRuleTokenStream stream_52=new RewriteRuleTokenStream(adaptor,"token 52");
        RewriteRuleSubtreeStream stream_operator=new RewriteRuleSubtreeStream(adaptor,"rule operator");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:73:2: (targets+= VAR ( ',' targets+= VAR )* '=' source= operator ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:73:4: targets+= VAR ( ',' targets+= VAR )* '=' source= operator
            {
            targets=(Token)match(input,VAR,FOLLOW_VAR_in_assignment214); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_VAR.add(targets);

            if (list_targets==null) list_targets=new ArrayList();
            list_targets.add(targets);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:73:17: ( ',' targets+= VAR )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==44) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:73:18: ',' targets+= VAR
            	    {
            	    char_literal10=(Token)match(input,44,FOLLOW_44_in_assignment217); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_44.add(char_literal10);


            	    targets=(Token)match(input,VAR,FOLLOW_VAR_in_assignment221); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_VAR.add(targets);

            	    if (list_targets==null) list_targets=new ArrayList();
            	    list_targets.add(targets);


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            char_literal11=(Token)match(input,52,FOLLOW_52_in_assignment225); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_52.add(char_literal11);


            pushFollow(FOLLOW_operator_in_assignment229);
            source=operator();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_operator.add(source.getTree());

            if ( state.backtracking==0 ) { for(int index = 0; index < list_targets.size(); index++)
            	    putVariable((Token) list_targets.get(index), new JsonStreamExpression((source!=null?source.op:null).getOutput(index))); 
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
            // 76:4: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:78:1: functionDefinition : name= ID '=' func= inlineFunction ->;
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
        RewriteRuleTokenStream stream_52=new RewriteRuleTokenStream(adaptor,"token 52");
        RewriteRuleSubtreeStream stream_inlineFunction=new RewriteRuleSubtreeStream(adaptor,"rule inlineFunction");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:79:3: (name= ID '=' func= inlineFunction ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:79:5: name= ID '=' func= inlineFunction
            {
            name=(Token)match(input,ID,FOLLOW_ID_in_functionDefinition248); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(name);


            char_literal12=(Token)match(input,52,FOLLOW_52_in_functionDefinition250); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_52.add(char_literal12);


            pushFollow(FOLLOW_inlineFunction_in_functionDefinition254);
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
            // 79:78: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:81:1: inlineFunction returns [ExpressionFunction func] : FN '(' (param= ID ( ',' param= ID )* )? ')' '{' def= ternaryExpression '}' ->;
    public final MeteorParser.inlineFunction_return inlineFunction() throws RecognitionException {
        MeteorParser.inlineFunction_return retval = new MeteorParser.inlineFunction_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token param=null;
        Token FN13=null;
        Token char_literal14=null;
        Token char_literal15=null;
        Token char_literal16=null;
        Token char_literal17=null;
        Token char_literal18=null;
        MeteorParser.ternaryExpression_return def =null;


        EvaluationExpression param_tree=null;
        EvaluationExpression FN13_tree=null;
        EvaluationExpression char_literal14_tree=null;
        EvaluationExpression char_literal15_tree=null;
        EvaluationExpression char_literal16_tree=null;
        EvaluationExpression char_literal17_tree=null;
        EvaluationExpression char_literal18_tree=null;
        RewriteRuleTokenStream stream_68=new RewriteRuleTokenStream(adaptor,"token 68");
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
        RewriteRuleTokenStream stream_FN=new RewriteRuleTokenStream(adaptor,"token FN");
        RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_70=new RewriteRuleTokenStream(adaptor,"token 70");
        RewriteRuleSubtreeStream stream_ternaryExpression=new RewriteRuleSubtreeStream(adaptor,"rule ternaryExpression");
         List<Token> params = new ArrayList(); 
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:83:3: ( FN '(' (param= ID ( ',' param= ID )* )? ')' '{' def= ternaryExpression '}' ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:83:5: FN '(' (param= ID ( ',' param= ID )* )? ')' '{' def= ternaryExpression '}'
            {
            FN13=(Token)match(input,FN,FOLLOW_FN_in_inlineFunction280); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_FN.add(FN13);


            char_literal14=(Token)match(input,40,FOLLOW_40_in_inlineFunction282); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_40.add(char_literal14);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:84:3: (param= ID ( ',' param= ID )* )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==ID) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:84:4: param= ID ( ',' param= ID )*
                    {
                    param=(Token)match(input,ID,FOLLOW_ID_in_inlineFunction291); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(param);


                    if ( state.backtracking==0 ) { params.add(param); }

                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:85:3: ( ',' param= ID )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==44) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:85:4: ',' param= ID
                    	    {
                    	    char_literal15=(Token)match(input,44,FOLLOW_44_in_inlineFunction298); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_44.add(char_literal15);


                    	    param=(Token)match(input,ID,FOLLOW_ID_in_inlineFunction302); if (state.failed) return retval; 
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


            char_literal16=(Token)match(input,41,FOLLOW_41_in_inlineFunction313); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_41.add(char_literal16);


            if ( state.backtracking==0 ) { 
                addConstantScope();
                for(int index = 0; index < params.size(); index++) 
                  this.getConstantRegistry().put(params.get(index).getText(), new InputSelection(index)); 
              }

            char_literal17=(Token)match(input,68,FOLLOW_68_in_inlineFunction323); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_68.add(char_literal17);


            pushFollow(FOLLOW_ternaryExpression_in_inlineFunction327);
            def=ternaryExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_ternaryExpression.add(def.getTree());

            char_literal18=(Token)match(input,70,FOLLOW_70_in_inlineFunction329); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_70.add(char_literal18);


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
            // 96:5: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:98:1: javaudf : name= ID '=' JAVAUDF '(' path= STRING ')' ->;
    public final MeteorParser.javaudf_return javaudf() throws RecognitionException {
        MeteorParser.javaudf_return retval = new MeteorParser.javaudf_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token name=null;
        Token path=null;
        Token char_literal19=null;
        Token JAVAUDF20=null;
        Token char_literal21=null;
        Token char_literal22=null;

        EvaluationExpression name_tree=null;
        EvaluationExpression path_tree=null;
        EvaluationExpression char_literal19_tree=null;
        EvaluationExpression JAVAUDF20_tree=null;
        EvaluationExpression char_literal21_tree=null;
        EvaluationExpression char_literal22_tree=null;
        RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_52=new RewriteRuleTokenStream(adaptor,"token 52");
        RewriteRuleTokenStream stream_JAVAUDF=new RewriteRuleTokenStream(adaptor,"token JAVAUDF");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:99:3: (name= ID '=' JAVAUDF '(' path= STRING ')' ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:99:5: name= ID '=' JAVAUDF '(' path= STRING ')'
            {
            name=(Token)match(input,ID,FOLLOW_ID_in_javaudf349); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(name);


            char_literal19=(Token)match(input,52,FOLLOW_52_in_javaudf351); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_52.add(char_literal19);


            JAVAUDF20=(Token)match(input,JAVAUDF,FOLLOW_JAVAUDF_in_javaudf353); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_JAVAUDF.add(JAVAUDF20);


            char_literal21=(Token)match(input,40,FOLLOW_40_in_javaudf355); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_40.add(char_literal21);


            path=(Token)match(input,STRING,FOLLOW_STRING_in_javaudf359); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_STRING.add(path);


            char_literal22=(Token)match(input,41,FOLLOW_41_in_javaudf361); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_41.add(char_literal22);


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
            // 100:53: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:102:1: contextAwareExpression[EvaluationExpression contextExpression] : ternaryExpression ;
    public final MeteorParser.contextAwareExpression_return contextAwareExpression(EvaluationExpression contextExpression) throws RecognitionException {
        contextAwareExpression_stack.push(new contextAwareExpression_scope());
        MeteorParser.contextAwareExpression_return retval = new MeteorParser.contextAwareExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.ternaryExpression_return ternaryExpression23 =null;



         ((contextAwareExpression_scope)contextAwareExpression_stack.peek()).context = contextExpression; 
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:105:3: ( ternaryExpression )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:105:5: ternaryExpression
            {
            root_0 = (EvaluationExpression)adaptor.nil();


            pushFollow(FOLLOW_ternaryExpression_in_contextAwareExpression389);
            ternaryExpression23=ternaryExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, ternaryExpression23.getTree());

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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:107:1: expression : ( ( 'read' | 'write' | genericOperatorName )=> operatorExpression | ternaryExpression );
    public final MeteorParser.expression_return expression() throws RecognitionException {
        MeteorParser.expression_return retval = new MeteorParser.expression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.operatorExpression_return operatorExpression24 =null;

        MeteorParser.ternaryExpression_return ternaryExpression25 =null;



        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:108:3: ( ( 'read' | 'write' | genericOperatorName )=> operatorExpression | ternaryExpression )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==63) && (synpred1_Meteor())) {
                alt7=1;
            }
            else if ( (LA7_0==67) && (synpred1_Meteor())) {
                alt7=1;
            }
            else if ( (LA7_0==ID) ) {
                int LA7_3 = input.LA(2);

                if ( (LA7_3==48) ) {
                    int LA7_5 = input.LA(3);

                    if ( (LA7_5==ID) ) {
                        int LA7_8 = input.LA(4);

                        if ( (LA7_8==AND||LA7_8==AS||(LA7_8 >= IF && LA7_8 <= IN)||LA7_8==NOT||LA7_8==OR||(LA7_8 >= SLASH && LA7_8 <= STAR)||(LA7_8 >= 37 && LA7_8 <= 38)||(LA7_8 >= 40 && LA7_8 <= 42)||(LA7_8 >= 44 && LA7_8 <= 45)||LA7_8==47||(LA7_8 >= 50 && LA7_8 <= 51)||(LA7_8 >= 53 && LA7_8 <= 59)||(LA7_8 >= 69 && LA7_8 <= 70)) ) {
                            alt7=2;
                        }
                        else if ( (LA7_8==ID) && (synpred1_Meteor())) {
                            alt7=1;
                        }
                        else if ( (LA7_8==VAR) && (synpred1_Meteor())) {
                            alt7=1;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 7, 8, input);

                            throw nvae;

                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 7, 5, input);

                        throw nvae;

                    }
                }
                else if ( (LA7_3==AND||LA7_3==AS||(LA7_3 >= IF && LA7_3 <= IN)||LA7_3==NOT||LA7_3==OR||(LA7_3 >= SLASH && LA7_3 <= STAR)||(LA7_3 >= 37 && LA7_3 <= 38)||(LA7_3 >= 40 && LA7_3 <= 42)||(LA7_3 >= 44 && LA7_3 <= 45)||LA7_3==47||(LA7_3 >= 50 && LA7_3 <= 51)||(LA7_3 >= 53 && LA7_3 <= 59)||(LA7_3 >= 69 && LA7_3 <= 70)) ) {
                    alt7=2;
                }
                else if ( (LA7_3==ID) && (synpred1_Meteor())) {
                    alt7=1;
                }
                else if ( (LA7_3==VAR) && (synpred1_Meteor())) {
                    alt7=1;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 3, input);

                    throw nvae;

                }
            }
            else if ( (LA7_0==DECIMAL||LA7_0==FN||LA7_0==INTEGER||(LA7_0 >= STRING && LA7_0 <= UINT)||LA7_0==VAR||LA7_0==36||(LA7_0 >= 39 && LA7_0 <= 40)||LA7_0==43||LA7_0==46||LA7_0==58||LA7_0==60||LA7_0==62||LA7_0==65||LA7_0==68||LA7_0==71) ) {
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:108:5: ( 'read' | 'write' | genericOperatorName )=> operatorExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_operatorExpression_in_expression412);
                    operatorExpression24=operatorExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, operatorExpression24.getTree());

                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:109:5: ternaryExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_ternaryExpression_in_expression418);
                    ternaryExpression25=ternaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ternaryExpression25.getTree());

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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:111:1: ternaryExpression : ( ( orExpression '?' )=>ifClause= orExpression '?' (ifExpr= orExpression )? ':' elseExpr= orExpression -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause) | ( orExpression IF )=>ifExpr2= orExpression IF ifClause2= orExpression -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause2 $ifExpr2) | orExpression );
    public final MeteorParser.ternaryExpression_return ternaryExpression() throws RecognitionException {
        MeteorParser.ternaryExpression_return retval = new MeteorParser.ternaryExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token char_literal26=null;
        Token char_literal27=null;
        Token IF28=null;
        MeteorParser.orExpression_return ifClause =null;

        MeteorParser.orExpression_return ifExpr =null;

        MeteorParser.orExpression_return elseExpr =null;

        MeteorParser.orExpression_return ifExpr2 =null;

        MeteorParser.orExpression_return ifClause2 =null;

        MeteorParser.orExpression_return orExpression29 =null;


        EvaluationExpression char_literal26_tree=null;
        EvaluationExpression char_literal27_tree=null;
        EvaluationExpression IF28_tree=null;
        RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
        RewriteRuleTokenStream stream_56=new RewriteRuleTokenStream(adaptor,"token 56");
        RewriteRuleTokenStream stream_IF=new RewriteRuleTokenStream(adaptor,"token IF");
        RewriteRuleSubtreeStream stream_orExpression=new RewriteRuleSubtreeStream(adaptor,"rule orExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:112:2: ( ( orExpression '?' )=>ifClause= orExpression '?' (ifExpr= orExpression )? ':' elseExpr= orExpression -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause) | ( orExpression IF )=>ifExpr2= orExpression IF ifClause2= orExpression -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause2 $ifExpr2) | orExpression )
            int alt9=3;
            switch ( input.LA(1) ) {
            case 43:
                {
                int LA9_1 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt9=1;
                }
                else if ( (synpred3_Meteor()) ) {
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
            case 46:
                {
                int LA9_2 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt9=1;
                }
                else if ( (synpred3_Meteor()) ) {
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
            case 36:
            case 71:
                {
                int LA9_3 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt9=1;
                }
                else if ( (synpred3_Meteor()) ) {
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
            case 40:
                {
                int LA9_4 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt9=1;
                }
                else if ( (synpred3_Meteor()) ) {
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

                if ( (synpred2_Meteor()) ) {
                    alt9=1;
                }
                else if ( (synpred3_Meteor()) ) {
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
            case 39:
                {
                int LA9_6 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt9=1;
                }
                else if ( (synpred3_Meteor()) ) {
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

                if ( (synpred2_Meteor()) ) {
                    alt9=1;
                }
                else if ( (synpred3_Meteor()) ) {
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
            case 65:
                {
                int LA9_8 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt9=1;
                }
                else if ( (synpred3_Meteor()) ) {
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
            case 60:
                {
                int LA9_9 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt9=1;
                }
                else if ( (synpred3_Meteor()) ) {
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

                if ( (synpred2_Meteor()) ) {
                    alt9=1;
                }
                else if ( (synpred3_Meteor()) ) {
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

                if ( (synpred2_Meteor()) ) {
                    alt9=1;
                }
                else if ( (synpred3_Meteor()) ) {
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

                if ( (synpred2_Meteor()) ) {
                    alt9=1;
                }
                else if ( (synpred3_Meteor()) ) {
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

                if ( (synpred2_Meteor()) ) {
                    alt9=1;
                }
                else if ( (synpred3_Meteor()) ) {
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
            case 62:
                {
                int LA9_14 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt9=1;
                }
                else if ( (synpred3_Meteor()) ) {
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

                if ( (synpred2_Meteor()) ) {
                    alt9=1;
                }
                else if ( (synpred3_Meteor()) ) {
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
            case 58:
                {
                int LA9_16 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt9=1;
                }
                else if ( (synpred3_Meteor()) ) {
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
            case 68:
                {
                int LA9_17 = input.LA(2);

                if ( (synpred2_Meteor()) ) {
                    alt9=1;
                }
                else if ( (synpred3_Meteor()) ) {
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:112:4: ( orExpression '?' )=>ifClause= orExpression '?' (ifExpr= orExpression )? ':' elseExpr= orExpression
                    {
                    pushFollow(FOLLOW_orExpression_in_ternaryExpression436);
                    ifClause=orExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_orExpression.add(ifClause.getTree());

                    char_literal26=(Token)match(input,56,FOLLOW_56_in_ternaryExpression438); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_56.add(char_literal26);


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:112:57: (ifExpr= orExpression )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==DECIMAL||LA8_0==FN||LA8_0==ID||LA8_0==INTEGER||(LA8_0 >= STRING && LA8_0 <= UINT)||LA8_0==VAR||LA8_0==36||(LA8_0 >= 39 && LA8_0 <= 40)||LA8_0==43||LA8_0==46||LA8_0==58||LA8_0==60||LA8_0==62||LA8_0==65||LA8_0==68||LA8_0==71) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:112:57: ifExpr= orExpression
                            {
                            pushFollow(FOLLOW_orExpression_in_ternaryExpression442);
                            ifExpr=orExpression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_orExpression.add(ifExpr.getTree());

                            }
                            break;

                    }


                    char_literal27=(Token)match(input,48,FOLLOW_48_in_ternaryExpression445); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_48.add(char_literal27);


                    pushFollow(FOLLOW_orExpression_in_ternaryExpression449);
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
                    // 113:2: -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause)
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:113:5: ^( EXPRESSION[\"TernaryExpression\"] $ifClause)
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:114:4: ( orExpression IF )=>ifExpr2= orExpression IF ifClause2= orExpression
                    {
                    pushFollow(FOLLOW_orExpression_in_ternaryExpression478);
                    ifExpr2=orExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_orExpression.add(ifExpr2.getTree());

                    IF28=(Token)match(input,IF,FOLLOW_IF_in_ternaryExpression480); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_IF.add(IF28);


                    pushFollow(FOLLOW_orExpression_in_ternaryExpression484);
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
                    // 115:3: -> ^( EXPRESSION[\"TernaryExpression\"] $ifClause2 $ifExpr2)
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:115:6: ^( EXPRESSION[\"TernaryExpression\"] $ifClause2 $ifExpr2)
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:116:5: orExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_orExpression_in_ternaryExpression507);
                    orExpression29=orExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, orExpression29.getTree());

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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:118:1: orExpression :exprs+= andExpression ( ( OR | '||' ) exprs+= andExpression )* -> { $exprs.size() == 1 }? ->;
    public final MeteorParser.orExpression_return orExpression() throws RecognitionException {
        MeteorParser.orExpression_return retval = new MeteorParser.orExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token OR30=null;
        Token string_literal31=null;
        List list_exprs=null;
        RuleReturnScope exprs = null;
        EvaluationExpression OR30_tree=null;
        EvaluationExpression string_literal31_tree=null;
        RewriteRuleTokenStream stream_69=new RewriteRuleTokenStream(adaptor,"token 69");
        RewriteRuleTokenStream stream_OR=new RewriteRuleTokenStream(adaptor,"token OR");
        RewriteRuleSubtreeStream stream_andExpression=new RewriteRuleSubtreeStream(adaptor,"rule andExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:119:3: (exprs+= andExpression ( ( OR | '||' ) exprs+= andExpression )* -> { $exprs.size() == 1 }? ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:119:5: exprs+= andExpression ( ( OR | '||' ) exprs+= andExpression )*
            {
            pushFollow(FOLLOW_andExpression_in_orExpression520);
            exprs=andExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_andExpression.add(exprs.getTree());
            if (list_exprs==null) list_exprs=new ArrayList();
            list_exprs.add(exprs.getTree());


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:119:26: ( ( OR | '||' ) exprs+= andExpression )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==OR||LA11_0==69) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:119:27: ( OR | '||' ) exprs+= andExpression
            	    {
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:119:27: ( OR | '||' )
            	    int alt10=2;
            	    int LA10_0 = input.LA(1);

            	    if ( (LA10_0==OR) ) {
            	        alt10=1;
            	    }
            	    else if ( (LA10_0==69) ) {
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
            	            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:119:28: OR
            	            {
            	            OR30=(Token)match(input,OR,FOLLOW_OR_in_orExpression524); if (state.failed) return retval; 
            	            if ( state.backtracking==0 ) stream_OR.add(OR30);


            	            }
            	            break;
            	        case 2 :
            	            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:119:33: '||'
            	            {
            	            string_literal31=(Token)match(input,69,FOLLOW_69_in_orExpression528); if (state.failed) return retval; 
            	            if ( state.backtracking==0 ) stream_69.add(string_literal31);


            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_andExpression_in_orExpression533);
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
            // 120:3: -> { $exprs.size() == 1 }?
            if ( list_exprs.size() == 1 ) {
                adaptor.addChild(root_0,  list_exprs.get(0) );

            }

            else // 121:3: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:123:1: andExpression :exprs+= elementExpression ( ( AND | '&&' ) exprs+= elementExpression )* -> { $exprs.size() == 1 }? ->;
    public final MeteorParser.andExpression_return andExpression() throws RecognitionException {
        MeteorParser.andExpression_return retval = new MeteorParser.andExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token AND32=null;
        Token string_literal33=null;
        List list_exprs=null;
        RuleReturnScope exprs = null;
        EvaluationExpression AND32_tree=null;
        EvaluationExpression string_literal33_tree=null;
        RewriteRuleTokenStream stream_AND=new RewriteRuleTokenStream(adaptor,"token AND");
        RewriteRuleTokenStream stream_38=new RewriteRuleTokenStream(adaptor,"token 38");
        RewriteRuleSubtreeStream stream_elementExpression=new RewriteRuleSubtreeStream(adaptor,"rule elementExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:124:3: (exprs+= elementExpression ( ( AND | '&&' ) exprs+= elementExpression )* -> { $exprs.size() == 1 }? ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:124:5: exprs+= elementExpression ( ( AND | '&&' ) exprs+= elementExpression )*
            {
            pushFollow(FOLLOW_elementExpression_in_andExpression562);
            exprs=elementExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_elementExpression.add(exprs.getTree());
            if (list_exprs==null) list_exprs=new ArrayList();
            list_exprs.add(exprs.getTree());


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:124:30: ( ( AND | '&&' ) exprs+= elementExpression )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==AND||LA13_0==38) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:124:31: ( AND | '&&' ) exprs+= elementExpression
            	    {
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:124:31: ( AND | '&&' )
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
            	            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:124:32: AND
            	            {
            	            AND32=(Token)match(input,AND,FOLLOW_AND_in_andExpression566); if (state.failed) return retval; 
            	            if ( state.backtracking==0 ) stream_AND.add(AND32);


            	            }
            	            break;
            	        case 2 :
            	            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:124:38: '&&'
            	            {
            	            string_literal33=(Token)match(input,38,FOLLOW_38_in_andExpression570); if (state.failed) return retval; 
            	            if ( state.backtracking==0 ) stream_38.add(string_literal33);


            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_elementExpression_in_andExpression575);
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
            // 125:3: -> { $exprs.size() == 1 }?
            if ( list_exprs.size() == 1 ) {
                adaptor.addChild(root_0,  list_exprs.get(0) );

            }

            else // 126:3: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:128:1: elementExpression : elem= comparisonExpression ( (not= NOT )? IN set= comparisonExpression )? -> { set == null }? $elem -> ^( EXPRESSION[\"ElementInSetExpression\"] $elem $set) ;
    public final MeteorParser.elementExpression_return elementExpression() throws RecognitionException {
        MeteorParser.elementExpression_return retval = new MeteorParser.elementExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token not=null;
        Token IN34=null;
        MeteorParser.comparisonExpression_return elem =null;

        MeteorParser.comparisonExpression_return set =null;


        EvaluationExpression not_tree=null;
        EvaluationExpression IN34_tree=null;
        RewriteRuleTokenStream stream_IN=new RewriteRuleTokenStream(adaptor,"token IN");
        RewriteRuleTokenStream stream_NOT=new RewriteRuleTokenStream(adaptor,"token NOT");
        RewriteRuleSubtreeStream stream_comparisonExpression=new RewriteRuleSubtreeStream(adaptor,"rule comparisonExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:129:2: (elem= comparisonExpression ( (not= NOT )? IN set= comparisonExpression )? -> { set == null }? $elem -> ^( EXPRESSION[\"ElementInSetExpression\"] $elem $set) )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:129:4: elem= comparisonExpression ( (not= NOT )? IN set= comparisonExpression )?
            {
            pushFollow(FOLLOW_comparisonExpression_in_elementExpression604);
            elem=comparisonExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_comparisonExpression.add(elem.getTree());

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:129:30: ( (not= NOT )? IN set= comparisonExpression )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==IN||LA15_0==NOT) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:129:31: (not= NOT )? IN set= comparisonExpression
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:129:34: (not= NOT )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==NOT) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:129:34: not= NOT
                            {
                            not=(Token)match(input,NOT,FOLLOW_NOT_in_elementExpression609); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_NOT.add(not);


                            }
                            break;

                    }


                    IN34=(Token)match(input,IN,FOLLOW_IN_in_elementExpression612); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_IN.add(IN34);


                    pushFollow(FOLLOW_comparisonExpression_in_elementExpression616);
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
            // 130:2: -> { set == null }? $elem
            if ( set == null ) {
                adaptor.addChild(root_0, stream_elem.nextTree());

            }

            else // 131:2: -> ^( EXPRESSION[\"ElementInSetExpression\"] $elem $set)
            {
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:131:5: ^( EXPRESSION[\"ElementInSetExpression\"] $elem $set)
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:134:1: comparisonExpression : e1= arithmeticExpression ( (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' ) e2= arithmeticExpression )? -> { $s == null }? $e1 -> { $s.getText().equals(\"!=\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) -> { $s.getText().equals(\"==\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) -> ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) ;
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
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:135:2: (e1= arithmeticExpression ( (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' ) e2= arithmeticExpression )? -> { $s == null }? $e1 -> { $s.getText().equals(\"!=\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) -> { $s.getText().equals(\"==\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) -> ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2) )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:135:4: e1= arithmeticExpression ( (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' ) e2= arithmeticExpression )?
            {
            pushFollow(FOLLOW_arithmeticExpression_in_comparisonExpression657);
            e1=arithmeticExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_arithmeticExpression.add(e1.getTree());

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:135:28: ( (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' ) e2= arithmeticExpression )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==37||(LA17_0 >= 50 && LA17_0 <= 51)||(LA17_0 >= 53 && LA17_0 <= 55)) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:135:29: (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' ) e2= arithmeticExpression
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:135:29: (s= '<=' |s= '>=' |s= '<' |s= '>' |s= '==' |s= '!=' )
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:135:30: s= '<='
                            {
                            s=(Token)match(input,51,FOLLOW_51_in_comparisonExpression663); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_51.add(s);


                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:135:39: s= '>='
                            {
                            s=(Token)match(input,55,FOLLOW_55_in_comparisonExpression669); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_55.add(s);


                            }
                            break;
                        case 3 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:135:48: s= '<'
                            {
                            s=(Token)match(input,50,FOLLOW_50_in_comparisonExpression675); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_50.add(s);


                            }
                            break;
                        case 4 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:135:56: s= '>'
                            {
                            s=(Token)match(input,54,FOLLOW_54_in_comparisonExpression681); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_54.add(s);


                            }
                            break;
                        case 5 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:135:64: s= '=='
                            {
                            s=(Token)match(input,53,FOLLOW_53_in_comparisonExpression687); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_53.add(s);


                            }
                            break;
                        case 6 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:135:73: s= '!='
                            {
                            s=(Token)match(input,37,FOLLOW_37_in_comparisonExpression693); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_37.add(s);


                            }
                            break;

                    }


                    pushFollow(FOLLOW_arithmeticExpression_in_comparisonExpression698);
                    e2=arithmeticExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_arithmeticExpression.add(e2.getTree());

                    }
                    break;

            }


            // AST REWRITE
            // elements: e1, e2, e2, e1, e1, e2, e1
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
            // 136:2: -> { $s == null }? $e1
            if ( s == null ) {
                adaptor.addChild(root_0, stream_e1.nextTree());

            }

            else // 137:3: -> { $s.getText().equals(\"!=\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
            if ( s.getText().equals("!=") ) {
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:137:38: ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
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

            else // 138:3: -> { $s.getText().equals(\"==\") }? ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
            if ( s.getText().equals("==") ) {
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:138:38: ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
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

            else // 139:2: -> ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
            {
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:139:6: ^( EXPRESSION[\"ComparativeExpression\"] $e1 $e2)
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:141:1: arithmeticExpression : e1= multiplicationExpression ( (s= '+' |s= '-' ) e2= multiplicationExpression )? -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2) -> $e1;
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
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:142:2: (e1= multiplicationExpression ( (s= '+' |s= '-' ) e2= multiplicationExpression )? -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2) -> $e1)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:142:4: e1= multiplicationExpression ( (s= '+' |s= '-' ) e2= multiplicationExpression )?
            {
            pushFollow(FOLLOW_multiplicationExpression_in_arithmeticExpression778);
            e1=multiplicationExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_multiplicationExpression.add(e1.getTree());

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:142:32: ( (s= '+' |s= '-' ) e2= multiplicationExpression )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==42||LA19_0==45) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:142:33: (s= '+' |s= '-' ) e2= multiplicationExpression
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:142:33: (s= '+' |s= '-' )
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:142:34: s= '+'
                            {
                            s=(Token)match(input,42,FOLLOW_42_in_arithmeticExpression784); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_42.add(s);


                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:142:42: s= '-'
                            {
                            s=(Token)match(input,45,FOLLOW_45_in_arithmeticExpression790); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_45.add(s);


                            }
                            break;

                    }


                    pushFollow(FOLLOW_multiplicationExpression_in_arithmeticExpression795);
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
            // 143:2: -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2)
            if ( s != null ) {
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:143:21: ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2)
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

            else // 145:2: -> $e1
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:147:1: multiplicationExpression : e1= preincrementExpression ( (s= '*' |s= SLASH ) e2= preincrementExpression )? -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2) -> $e1;
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
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:148:2: (e1= preincrementExpression ( (s= '*' |s= SLASH ) e2= preincrementExpression )? -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2) -> $e1)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:148:4: e1= preincrementExpression ( (s= '*' |s= SLASH ) e2= preincrementExpression )?
            {
            pushFollow(FOLLOW_preincrementExpression_in_multiplicationExpression838);
            e1=preincrementExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_preincrementExpression.add(e1.getTree());

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:148:30: ( (s= '*' |s= SLASH ) e2= preincrementExpression )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( ((LA21_0 >= SLASH && LA21_0 <= STAR)) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:148:31: (s= '*' |s= SLASH ) e2= preincrementExpression
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:148:31: (s= '*' |s= SLASH )
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:148:32: s= '*'
                            {
                            s=(Token)match(input,STAR,FOLLOW_STAR_in_multiplicationExpression844); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_STAR.add(s);


                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:148:40: s= SLASH
                            {
                            s=(Token)match(input,SLASH,FOLLOW_SLASH_in_multiplicationExpression850); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_SLASH.add(s);


                            }
                            break;

                    }


                    pushFollow(FOLLOW_preincrementExpression_in_multiplicationExpression855);
                    e2=preincrementExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_preincrementExpression.add(e2.getTree());

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
            // 149:2: -> { s != null }? ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2)
            if ( s != null ) {
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:149:21: ^( EXPRESSION[\"ArithmeticExpression\"] $e1 $e2)
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

            else // 151:2: -> $e1
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:153:1: preincrementExpression : ( '++' preincrementExpression | '--' preincrementExpression | unaryExpression );
    public final MeteorParser.preincrementExpression_return preincrementExpression() throws RecognitionException {
        MeteorParser.preincrementExpression_return retval = new MeteorParser.preincrementExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token string_literal35=null;
        Token string_literal37=null;
        MeteorParser.preincrementExpression_return preincrementExpression36 =null;

        MeteorParser.preincrementExpression_return preincrementExpression38 =null;

        MeteorParser.unaryExpression_return unaryExpression39 =null;


        EvaluationExpression string_literal35_tree=null;
        EvaluationExpression string_literal37_tree=null;

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:154:2: ( '++' preincrementExpression | '--' preincrementExpression | unaryExpression )
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
            case 62:
            case 65:
            case 68:
            case 71:
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:154:4: '++' preincrementExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    string_literal35=(Token)match(input,43,FOLLOW_43_in_preincrementExpression896); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal35_tree = 
                    (EvaluationExpression)adaptor.create(string_literal35)
                    ;
                    adaptor.addChild(root_0, string_literal35_tree);
                    }

                    pushFollow(FOLLOW_preincrementExpression_in_preincrementExpression898);
                    preincrementExpression36=preincrementExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, preincrementExpression36.getTree());

                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:155:4: '--' preincrementExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    string_literal37=(Token)match(input,46,FOLLOW_46_in_preincrementExpression903); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal37_tree = 
                    (EvaluationExpression)adaptor.create(string_literal37)
                    ;
                    adaptor.addChild(root_0, string_literal37_tree);
                    }

                    pushFollow(FOLLOW_preincrementExpression_in_preincrementExpression905);
                    preincrementExpression38=preincrementExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, preincrementExpression38.getTree());

                    }
                    break;
                case 3 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:156:4: unaryExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_unaryExpression_in_preincrementExpression910);
                    unaryExpression39=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression39.getTree());

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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:158:1: unaryExpression : ( '!' | '~' )? castExpression ;
    public final MeteorParser.unaryExpression_return unaryExpression() throws RecognitionException {
        MeteorParser.unaryExpression_return retval = new MeteorParser.unaryExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token set40=null;
        MeteorParser.castExpression_return castExpression41 =null;


        EvaluationExpression set40_tree=null;

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:159:2: ( ( '!' | '~' )? castExpression )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:159:4: ( '!' | '~' )? castExpression
            {
            root_0 = (EvaluationExpression)adaptor.nil();


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:159:4: ( '!' | '~' )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==36||LA23_0==71) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:
                    {
                    set40=(Token)input.LT(1);

                    if ( input.LA(1)==36||input.LA(1)==71 ) {
                        input.consume();
                        if ( state.backtracking==0 ) adaptor.addChild(root_0, 
                        (EvaluationExpression)adaptor.create(set40)
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


            pushFollow(FOLLOW_castExpression_in_unaryExpression929);
            castExpression41=castExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, castExpression41.getTree());

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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:161:1: castExpression : ( ( '(' ID ')' )=> '(' type= ID ')' expr= generalPathExpression ->| ( generalPathExpression AS )=>expr= generalPathExpression AS type= ID ->| generalPathExpression );
    public final MeteorParser.castExpression_return castExpression() throws RecognitionException {
        MeteorParser.castExpression_return retval = new MeteorParser.castExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token type=null;
        Token char_literal42=null;
        Token char_literal43=null;
        Token AS44=null;
        MeteorParser.generalPathExpression_return expr =null;

        MeteorParser.generalPathExpression_return generalPathExpression45 =null;


        EvaluationExpression type_tree=null;
        EvaluationExpression char_literal42_tree=null;
        EvaluationExpression char_literal43_tree=null;
        EvaluationExpression AS44_tree=null;
        RewriteRuleTokenStream stream_AS=new RewriteRuleTokenStream(adaptor,"token AS");
        RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_generalPathExpression=new RewriteRuleSubtreeStream(adaptor,"rule generalPathExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:162:2: ( ( '(' ID ')' )=> '(' type= ID ')' expr= generalPathExpression ->| ( generalPathExpression AS )=>expr= generalPathExpression AS type= ID ->| generalPathExpression )
            int alt24=3;
            switch ( input.LA(1) ) {
            case 40:
                {
                int LA24_1 = input.LA(2);

                if ( (synpred4_Meteor()) ) {
                    alt24=1;
                }
                else if ( (synpred5_Meteor()) ) {
                    alt24=2;
                }
                else if ( (true) ) {
                    alt24=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 24, 1, input);

                    throw nvae;

                }
                }
                break;
            case ID:
                {
                int LA24_2 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt24=2;
                }
                else if ( (true) ) {
                    alt24=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 24, 2, input);

                    throw nvae;

                }
                }
                break;
            case 39:
                {
                int LA24_3 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt24=2;
                }
                else if ( (true) ) {
                    alt24=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 24, 3, input);

                    throw nvae;

                }
                }
                break;
            case FN:
                {
                int LA24_4 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt24=2;
                }
                else if ( (true) ) {
                    alt24=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 24, 4, input);

                    throw nvae;

                }
                }
                break;
            case 65:
                {
                int LA24_5 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt24=2;
                }
                else if ( (true) ) {
                    alt24=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 24, 5, input);

                    throw nvae;

                }
                }
                break;
            case 60:
                {
                int LA24_6 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt24=2;
                }
                else if ( (true) ) {
                    alt24=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 24, 6, input);

                    throw nvae;

                }
                }
                break;
            case DECIMAL:
                {
                int LA24_7 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt24=2;
                }
                else if ( (true) ) {
                    alt24=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 24, 7, input);

                    throw nvae;

                }
                }
                break;
            case STRING:
                {
                int LA24_8 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt24=2;
                }
                else if ( (true) ) {
                    alt24=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 24, 8, input);

                    throw nvae;

                }
                }
                break;
            case UINT:
                {
                int LA24_9 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt24=2;
                }
                else if ( (true) ) {
                    alt24=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 24, 9, input);

                    throw nvae;

                }
                }
                break;
            case INTEGER:
                {
                int LA24_10 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt24=2;
                }
                else if ( (true) ) {
                    alt24=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 24, 10, input);

                    throw nvae;

                }
                }
                break;
            case 62:
                {
                int LA24_11 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt24=2;
                }
                else if ( (true) ) {
                    alt24=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 24, 11, input);

                    throw nvae;

                }
                }
                break;
            case VAR:
                {
                int LA24_12 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt24=2;
                }
                else if ( (true) ) {
                    alt24=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 24, 12, input);

                    throw nvae;

                }
                }
                break;
            case 58:
                {
                int LA24_13 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt24=2;
                }
                else if ( (true) ) {
                    alt24=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 24, 13, input);

                    throw nvae;

                }
                }
                break;
            case 68:
                {
                int LA24_14 = input.LA(2);

                if ( (synpred5_Meteor()) ) {
                    alt24=2;
                }
                else if ( (true) ) {
                    alt24=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 24, 14, input);

                    throw nvae;

                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;

            }

            switch (alt24) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:162:4: ( '(' ID ')' )=> '(' type= ID ')' expr= generalPathExpression
                    {
                    char_literal42=(Token)match(input,40,FOLLOW_40_in_castExpression947); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_40.add(char_literal42);


                    type=(Token)match(input,ID,FOLLOW_ID_in_castExpression951); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(type);


                    char_literal43=(Token)match(input,41,FOLLOW_41_in_castExpression953); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_41.add(char_literal43);


                    pushFollow(FOLLOW_generalPathExpression_in_castExpression957);
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
                    // 163:3: ->
                    {
                        adaptor.addChild(root_0,  coerce((type!=null?type.getText():null), (expr!=null?((EvaluationExpression)expr.tree):null)) );

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:164:4: ( generalPathExpression AS )=>expr= generalPathExpression AS type= ID
                    {
                    pushFollow(FOLLOW_generalPathExpression_in_castExpression977);
                    expr=generalPathExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_generalPathExpression.add(expr.getTree());

                    AS44=(Token)match(input,AS,FOLLOW_AS_in_castExpression979); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_AS.add(AS44);


                    type=(Token)match(input,ID,FOLLOW_ID_in_castExpression983); if (state.failed) return retval; 
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
                    // 165:3: ->
                    {
                        adaptor.addChild(root_0,  coerce((type!=null?type.getText():null), (expr!=null?((EvaluationExpression)expr.tree):null)) );

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 3 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:166:4: generalPathExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_generalPathExpression_in_castExpression994);
                    generalPathExpression45=generalPathExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, generalPathExpression45.getTree());

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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:168:1: generalPathExpression : value= valueExpression ( ( pathExpression[EvaluationExpression.VALUE] )=>path= pathExpression[$value.tree] -> $path| -> $value) ;
    public final MeteorParser.generalPathExpression_return generalPathExpression() throws RecognitionException {
        MeteorParser.generalPathExpression_return retval = new MeteorParser.generalPathExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.valueExpression_return value =null;

        MeteorParser.pathExpression_return path =null;


        RewriteRuleSubtreeStream stream_valueExpression=new RewriteRuleSubtreeStream(adaptor,"rule valueExpression");
        RewriteRuleSubtreeStream stream_pathExpression=new RewriteRuleSubtreeStream(adaptor,"rule pathExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:169:2: (value= valueExpression ( ( pathExpression[EvaluationExpression.VALUE] )=>path= pathExpression[$value.tree] -> $path| -> $value) )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:169:4: value= valueExpression ( ( pathExpression[EvaluationExpression.VALUE] )=>path= pathExpression[$value.tree] -> $path| -> $value)
            {
            pushFollow(FOLLOW_valueExpression_in_generalPathExpression1006);
            value=valueExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_valueExpression.add(value.getTree());

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:170:4: ( ( pathExpression[EvaluationExpression.VALUE] )=>path= pathExpression[$value.tree] -> $path| -> $value)
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==57) && (synpred6_Meteor())) {
                alt25=1;
            }
            else if ( (LA25_0==47) && (synpred6_Meteor())) {
                alt25=1;
            }
            else if ( (LA25_0==58) && (synpred6_Meteor())) {
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:170:5: ( pathExpression[EvaluationExpression.VALUE] )=>path= pathExpression[$value.tree]
                    {
                    pushFollow(FOLLOW_pathExpression_in_generalPathExpression1021);
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
                    // 170:85: -> $path
                    {
                        adaptor.addChild(root_0, stream_path.nextTree());

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:171:7: 
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
                    // 171:7: -> $value
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:173:1: contextAwarePathExpression[EvaluationExpression context] : pathExpression[context] ;
    public final MeteorParser.contextAwarePathExpression_return contextAwarePathExpression(EvaluationExpression context) throws RecognitionException {
        MeteorParser.contextAwarePathExpression_return retval = new MeteorParser.contextAwarePathExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.pathExpression_return pathExpression46 =null;



        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:174:3: ( pathExpression[context] )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:174:5: pathExpression[context]
            {
            root_0 = (EvaluationExpression)adaptor.nil();


            pushFollow(FOLLOW_pathExpression_in_contextAwarePathExpression1050);
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:176:1: pathExpression[EvaluationExpression inExp] : ( ( '?.' ID '(' )=> '?.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)] -> $path| -> ^( EXPRESSION[\"TernaryExpression\"] $call) ) | ( '.' ID '(' )=> '.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[$call.tree] -> $path| -> $call) |seg= pathSegment ( ( pathSegment )=>path= pathExpression[$seg.tree] -> $path| -> $seg) );
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
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleSubtreeStream stream_pathSegment=new RewriteRuleSubtreeStream(adaptor,"rule pathSegment");
        RewriteRuleSubtreeStream stream_methodCall=new RewriteRuleSubtreeStream(adaptor,"rule methodCall");
        RewriteRuleSubtreeStream stream_pathExpression=new RewriteRuleSubtreeStream(adaptor,"rule pathExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:177:3: ( ( '?.' ID '(' )=> '?.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)] -> $path| -> ^( EXPRESSION[\"TernaryExpression\"] $call) ) | ( '.' ID '(' )=> '.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[$call.tree] -> $path| -> $call) |seg= pathSegment ( ( pathSegment )=>path= pathExpression[$seg.tree] -> $path| -> $seg) )
            int alt29=3;
            switch ( input.LA(1) ) {
            case 57:
                {
                int LA29_1 = input.LA(2);

                if ( (synpred7_Meteor()) ) {
                    alt29=1;
                }
                else if ( (true) ) {
                    alt29=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 29, 1, input);

                    throw nvae;

                }
                }
                break;
            case 47:
                {
                int LA29_2 = input.LA(2);

                if ( (synpred9_Meteor()) ) {
                    alt29=2;
                }
                else if ( (true) ) {
                    alt29=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 29, 2, input);

                    throw nvae;

                }
                }
                break;
            case 58:
                {
                alt29=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 29, 0, input);

                throw nvae;

            }

            switch (alt29) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:178:5: ( '?.' ID '(' )=> '?.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)] -> $path| -> ^( EXPRESSION[\"TernaryExpression\"] $call) )
                    {
                    string_literal47=(Token)match(input,57,FOLLOW_57_in_pathExpression1078); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(string_literal47);


                    pushFollow(FOLLOW_methodCall_in_pathExpression1082);
                    call=methodCall(inExp);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_methodCall.add(call.getTree());

                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:179:7: ( ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)] -> $path| -> ^( EXPRESSION[\"TernaryExpression\"] $call) )
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0==57) && (synpred8_Meteor())) {
                        alt26=1;
                    }
                    else if ( (LA26_0==47) && (synpred8_Meteor())) {
                        alt26=1;
                    }
                    else if ( (LA26_0==58) && (synpred8_Meteor())) {
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:179:8: ( pathSegment )=>path= pathExpression[new TernaryExpression(new NotNullOrMissingBooleanExpression().withInputExpression(inExp), $call.tree)]
                            {
                            pushFollow(FOLLOW_pathExpression_in_pathExpression1099);
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
                            // 179:146: -> $path
                            {
                                adaptor.addChild(root_0, stream_path.nextTree());

                            }


                            retval.tree = root_0;
                            }

                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:180:8: 
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
                            // 180:8: -> ^( EXPRESSION[\"TernaryExpression\"] $call)
                            {
                                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:180:11: ^( EXPRESSION[\"TernaryExpression\"] $call)
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:182:5: ( '.' ID '(' )=> '.' call= methodCall[inExp] ( ( pathSegment )=>path= pathExpression[$call.tree] -> $path| -> $call)
                    {
                    char_literal48=(Token)match(input,47,FOLLOW_47_in_pathExpression1149); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_47.add(char_literal48);


                    pushFollow(FOLLOW_methodCall_in_pathExpression1153);
                    call=methodCall(inExp);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_methodCall.add(call.getTree());

                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:183:7: ( ( pathSegment )=>path= pathExpression[$call.tree] -> $path| -> $call)
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( (LA27_0==57) && (synpred10_Meteor())) {
                        alt27=1;
                    }
                    else if ( (LA27_0==47) && (synpred10_Meteor())) {
                        alt27=1;
                    }
                    else if ( (LA27_0==58) && (synpred10_Meteor())) {
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:183:8: ( pathSegment )=>path= pathExpression[$call.tree]
                            {
                            pushFollow(FOLLOW_pathExpression_in_pathExpression1170);
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
                            // 183:55: -> $path
                            {
                                adaptor.addChild(root_0, stream_path.nextTree());

                            }


                            retval.tree = root_0;
                            }

                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:183:66: 
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
                            // 183:66: -> $call
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:185:5: seg= pathSegment ( ( pathSegment )=>path= pathExpression[$seg.tree] -> $path| -> $seg)
                    {
                    pushFollow(FOLLOW_pathSegment_in_pathExpression1196);
                    seg=pathSegment();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_pathSegment.add(seg.getTree());

                    if ( state.backtracking==0 ) { ((PathSegmentExpression) seg.getTree()).setInputExpression(inExp); }

                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:186:5: ( ( pathSegment )=>path= pathExpression[$seg.tree] -> $path| -> $seg)
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
                    else if ( (LA28_0==EOF||LA28_0==AND||LA28_0==AS||(LA28_0 >= ID && LA28_0 <= IN)||LA28_0==NOT||LA28_0==OR||(LA28_0 >= SLASH && LA28_0 <= STAR)||(LA28_0 >= 37 && LA28_0 <= 38)||(LA28_0 >= 41 && LA28_0 <= 42)||(LA28_0 >= 44 && LA28_0 <= 45)||(LA28_0 >= 48 && LA28_0 <= 51)||(LA28_0 >= 53 && LA28_0 <= 56)||LA28_0==59||(LA28_0 >= 69 && LA28_0 <= 70)) ) {
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:186:6: ( pathSegment )=>path= pathExpression[$seg.tree]
                            {
                            pushFollow(FOLLOW_pathExpression_in_pathExpression1212);
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
                            // 186:53: -> $path
                            {
                                adaptor.addChild(root_0, stream_path.nextTree());

                            }


                            retval.tree = root_0;
                            }

                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:186:64: 
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
                            // 186:64: -> $seg
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:189:1: pathSegment : ( ( '?.' )=> '?.' field= ID -> ^( EXPRESSION[\"TernaryExpression\"] ^( EXPRESSION[\"NotNullOrMissingBooleanExpression\"] ) ^( EXPRESSION[\"ObjectAccess\"] ) ) | ( '.' )=> '.' field= ID -> ^( EXPRESSION[\"ObjectAccess\"] ) | ( '[' )=> arrayAccess );
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
        RewriteRuleTokenStream stream_57=new RewriteRuleTokenStream(adaptor,"token 57");
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");

          paraphrase.push("a path expression"); 
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:192:3: ( ( '?.' )=> '?.' field= ID -> ^( EXPRESSION[\"TernaryExpression\"] ^( EXPRESSION[\"NotNullOrMissingBooleanExpression\"] ) ^( EXPRESSION[\"ObjectAccess\"] ) ) | ( '.' )=> '.' field= ID -> ^( EXPRESSION[\"ObjectAccess\"] ) | ( '[' )=> arrayAccess )
            int alt30=3;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==57) && (synpred12_Meteor())) {
                alt30=1;
            }
            else if ( (LA30_0==47) && (synpred13_Meteor())) {
                alt30=2;
            }
            else if ( (LA30_0==58) && (synpred14_Meteor())) {
                alt30=3;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;

            }
            switch (alt30) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:193:5: ( '?.' )=> '?.' field= ID
                    {
                    string_literal49=(Token)match(input,57,FOLLOW_57_in_pathSegment1264); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_57.add(string_literal49);


                    field=(Token)match(input,ID,FOLLOW_ID_in_pathSegment1268); if (state.failed) return retval; 
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
                    // 193:28: -> ^( EXPRESSION[\"TernaryExpression\"] ^( EXPRESSION[\"NotNullOrMissingBooleanExpression\"] ) ^( EXPRESSION[\"ObjectAccess\"] ) )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:193:31: ^( EXPRESSION[\"TernaryExpression\"] ^( EXPRESSION[\"NotNullOrMissingBooleanExpression\"] ) ^( EXPRESSION[\"ObjectAccess\"] ) )
                        {
                        EvaluationExpression root_1 = (EvaluationExpression)adaptor.nil();
                        root_1 = (EvaluationExpression)adaptor.becomeRoot(
                        (EvaluationExpression)adaptor.create(EXPRESSION, "TernaryExpression")
                        , root_1);

                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:193:65: ^( EXPRESSION[\"NotNullOrMissingBooleanExpression\"] )
                        {
                        EvaluationExpression root_2 = (EvaluationExpression)adaptor.nil();
                        root_2 = (EvaluationExpression)adaptor.becomeRoot(
                        (EvaluationExpression)adaptor.create(EXPRESSION, "NotNullOrMissingBooleanExpression")
                        , root_2);

                        adaptor.addChild(root_1, root_2);
                        }

                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:193:116: ^( EXPRESSION[\"ObjectAccess\"] )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:194:5: ( '.' )=> '.' field= ID
                    {
                    char_literal50=(Token)match(input,47,FOLLOW_47_in_pathSegment1303); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_47.add(char_literal50);


                    field=(Token)match(input,ID,FOLLOW_ID_in_pathSegment1307); if (state.failed) return retval; 
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
                    // 194:27: -> ^( EXPRESSION[\"ObjectAccess\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:194:30: ^( EXPRESSION[\"ObjectAccess\"] )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:195:5: ( '[' )=> arrayAccess
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_arrayAccess_in_pathSegment1332);
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:197:1: arrayAccess : ( '[' STAR ']' ( ( '.' methodCall[null] )=> '.' call= methodCall[EvaluationExpression.VALUE] -> ^( EXPRESSION[\"ArrayProjection\"] $call) |path= pathSegment -> ^( EXPRESSION[\"ArrayProjection\"] $path) ) | '[' (pos= INTEGER |pos= UINT ) ']' -> ^( EXPRESSION[\"ArrayAccess\"] ) | '[' (start= INTEGER |start= UINT ) ':' (end= INTEGER |end= UINT ) ']' -> ^( EXPRESSION[\"ArrayAccess\"] ) );
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
        RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
        RewriteRuleTokenStream stream_59=new RewriteRuleTokenStream(adaptor,"token 59");
        RewriteRuleTokenStream stream_58=new RewriteRuleTokenStream(adaptor,"token 58");
        RewriteRuleTokenStream stream_STAR=new RewriteRuleTokenStream(adaptor,"token STAR");
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleTokenStream stream_UINT=new RewriteRuleTokenStream(adaptor,"token UINT");
        RewriteRuleSubtreeStream stream_methodCall=new RewriteRuleSubtreeStream(adaptor,"rule methodCall");
        RewriteRuleSubtreeStream stream_pathSegment=new RewriteRuleSubtreeStream(adaptor,"rule pathSegment");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:198:3: ( '[' STAR ']' ( ( '.' methodCall[null] )=> '.' call= methodCall[EvaluationExpression.VALUE] -> ^( EXPRESSION[\"ArrayProjection\"] $call) |path= pathSegment -> ^( EXPRESSION[\"ArrayProjection\"] $path) ) | '[' (pos= INTEGER |pos= UINT ) ']' -> ^( EXPRESSION[\"ArrayAccess\"] ) | '[' (start= INTEGER |start= UINT ) ':' (end= INTEGER |end= UINT ) ']' -> ^( EXPRESSION[\"ArrayAccess\"] ) )
            int alt35=3;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==58) ) {
                switch ( input.LA(2) ) {
                case STAR:
                    {
                    alt35=1;
                    }
                    break;
                case INTEGER:
                    {
                    int LA35_3 = input.LA(3);

                    if ( (LA35_3==59) ) {
                        alt35=2;
                    }
                    else if ( (LA35_3==48) ) {
                        alt35=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 35, 3, input);

                        throw nvae;

                    }
                    }
                    break;
                case UINT:
                    {
                    int LA35_4 = input.LA(3);

                    if ( (LA35_4==59) ) {
                        alt35=2;
                    }
                    else if ( (LA35_4==48) ) {
                        alt35=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 35, 4, input);

                        throw nvae;

                    }
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 35, 1, input);

                    throw nvae;

                }

            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 35, 0, input);

                throw nvae;

            }
            switch (alt35) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:198:5: '[' STAR ']' ( ( '.' methodCall[null] )=> '.' call= methodCall[EvaluationExpression.VALUE] -> ^( EXPRESSION[\"ArrayProjection\"] $call) |path= pathSegment -> ^( EXPRESSION[\"ArrayProjection\"] $path) )
                    {
                    char_literal52=(Token)match(input,58,FOLLOW_58_in_arrayAccess1342); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_58.add(char_literal52);


                    STAR53=(Token)match(input,STAR,FOLLOW_STAR_in_arrayAccess1344); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_STAR.add(STAR53);


                    char_literal54=(Token)match(input,59,FOLLOW_59_in_arrayAccess1346); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_59.add(char_literal54);


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:198:18: ( ( '.' methodCall[null] )=> '.' call= methodCall[EvaluationExpression.VALUE] -> ^( EXPRESSION[\"ArrayProjection\"] $call) |path= pathSegment -> ^( EXPRESSION[\"ArrayProjection\"] $path) )
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( (LA31_0==47) ) {
                        int LA31_1 = input.LA(2);

                        if ( (synpred15_Meteor()) ) {
                            alt31=1;
                        }
                        else if ( (true) ) {
                            alt31=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 31, 1, input);

                            throw nvae;

                        }
                    }
                    else if ( ((LA31_0 >= 57 && LA31_0 <= 58)) ) {
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:198:19: ( '.' methodCall[null] )=> '.' call= methodCall[EvaluationExpression.VALUE]
                            {
                            char_literal55=(Token)match(input,47,FOLLOW_47_in_arrayAccess1357); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_47.add(char_literal55);


                            pushFollow(FOLLOW_methodCall_in_arrayAccess1361);
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
                            // 199:4: -> ^( EXPRESSION[\"ArrayProjection\"] $call)
                            {
                                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:199:7: ^( EXPRESSION[\"ArrayProjection\"] $call)
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:200:6: path= pathSegment
                            {
                            pushFollow(FOLLOW_pathSegment_in_arrayAccess1384);
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
                            // 201:4: -> ^( EXPRESSION[\"ArrayProjection\"] $path)
                            {
                                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:201:7: ^( EXPRESSION[\"ArrayProjection\"] $path)
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:202:5: '[' (pos= INTEGER |pos= UINT ) ']'
                    {
                    char_literal56=(Token)match(input,58,FOLLOW_58_in_arrayAccess1405); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_58.add(char_literal56);


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:202:9: (pos= INTEGER |pos= UINT )
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:202:10: pos= INTEGER
                            {
                            pos=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_arrayAccess1410); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_INTEGER.add(pos);


                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:202:24: pos= UINT
                            {
                            pos=(Token)match(input,UINT,FOLLOW_UINT_in_arrayAccess1416); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_UINT.add(pos);


                            }
                            break;

                    }


                    char_literal57=(Token)match(input,59,FOLLOW_59_in_arrayAccess1419); if (state.failed) return retval; 
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
                    // 203:3: -> ^( EXPRESSION[\"ArrayAccess\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:203:6: ^( EXPRESSION[\"ArrayAccess\"] )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:204:5: '[' (start= INTEGER |start= UINT ) ':' (end= INTEGER |end= UINT ) ']'
                    {
                    char_literal58=(Token)match(input,58,FOLLOW_58_in_arrayAccess1437); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_58.add(char_literal58);


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:204:9: (start= INTEGER |start= UINT )
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:204:10: start= INTEGER
                            {
                            start=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_arrayAccess1442); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_INTEGER.add(start);


                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:204:26: start= UINT
                            {
                            start=(Token)match(input,UINT,FOLLOW_UINT_in_arrayAccess1448); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_UINT.add(start);


                            }
                            break;

                    }


                    char_literal59=(Token)match(input,48,FOLLOW_48_in_arrayAccess1451); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_48.add(char_literal59);


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:204:42: (end= INTEGER |end= UINT )
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:204:43: end= INTEGER
                            {
                            end=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_arrayAccess1456); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_INTEGER.add(end);


                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:204:57: end= UINT
                            {
                            end=(Token)match(input,UINT,FOLLOW_UINT_in_arrayAccess1462); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_UINT.add(end);


                            }
                            break;

                    }


                    char_literal60=(Token)match(input,59,FOLLOW_59_in_arrayAccess1465); if (state.failed) return retval; 
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
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (EvaluationExpression)adaptor.nil();
                    // 205:3: -> ^( EXPRESSION[\"ArrayAccess\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:205:6: ^( EXPRESSION[\"ArrayAccess\"] )
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:207:1: valueExpression : ( ( ID '(' )=> functionCall | functionReference | ( FN )=>func= inlineFunction -> ^( EXPRESSION[\"ConstantExpression\"] ) | parenthesesExpression | literal | ( VAR '[' VAR )=> streamIndexAccess | VAR ->| ( ( ID ':' )=>packageName= ID ':' )? constant= ID {...}? => ->| arrayCreation | objectCreation );
    public final MeteorParser.valueExpression_return valueExpression() throws RecognitionException {
        MeteorParser.valueExpression_return retval = new MeteorParser.valueExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token packageName=null;
        Token constant=null;
        Token VAR66=null;
        Token char_literal67=null;
        MeteorParser.inlineFunction_return func =null;

        MeteorParser.functionCall_return functionCall61 =null;

        MeteorParser.functionReference_return functionReference62 =null;

        MeteorParser.parenthesesExpression_return parenthesesExpression63 =null;

        MeteorParser.literal_return literal64 =null;

        MeteorParser.streamIndexAccess_return streamIndexAccess65 =null;

        MeteorParser.arrayCreation_return arrayCreation68 =null;

        MeteorParser.objectCreation_return objectCreation69 =null;


        EvaluationExpression packageName_tree=null;
        EvaluationExpression constant_tree=null;
        EvaluationExpression VAR66_tree=null;
        EvaluationExpression char_literal67_tree=null;
        RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_inlineFunction=new RewriteRuleSubtreeStream(adaptor,"rule inlineFunction");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:208:2: ( ( ID '(' )=> functionCall | functionReference | ( FN )=>func= inlineFunction -> ^( EXPRESSION[\"ConstantExpression\"] ) | parenthesesExpression | literal | ( VAR '[' VAR )=> streamIndexAccess | VAR ->| ( ( ID ':' )=>packageName= ID ':' )? constant= ID {...}? => ->| arrayCreation | objectCreation )
            int alt37=10;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==ID) ) {
                int LA37_1 = input.LA(2);

                if ( (LA37_1==48) ) {
                    int LA37_9 = input.LA(3);

                    if ( (synpred16_Meteor()) ) {
                        alt37=1;
                    }
                    else if ( (true) ) {
                        alt37=8;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 37, 9, input);

                        throw nvae;

                    }
                }
                else if ( (LA37_1==40) && (synpred16_Meteor())) {
                    alt37=1;
                }
                else if ( (LA37_1==EOF||LA37_1==AND||LA37_1==AS||(LA37_1 >= ID && LA37_1 <= IN)||LA37_1==NOT||LA37_1==OR||(LA37_1 >= SLASH && LA37_1 <= STAR)||(LA37_1 >= 37 && LA37_1 <= 38)||(LA37_1 >= 41 && LA37_1 <= 42)||(LA37_1 >= 44 && LA37_1 <= 45)||LA37_1==47||(LA37_1 >= 49 && LA37_1 <= 51)||(LA37_1 >= 53 && LA37_1 <= 59)||(LA37_1 >= 69 && LA37_1 <= 70)) ) {
                    alt37=8;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 37, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA37_0==39) ) {
                alt37=2;
            }
            else if ( (LA37_0==FN) && (synpred17_Meteor())) {
                alt37=3;
            }
            else if ( (LA37_0==40) ) {
                alt37=4;
            }
            else if ( (LA37_0==DECIMAL||LA37_0==INTEGER||(LA37_0 >= STRING && LA37_0 <= UINT)||LA37_0==60||LA37_0==62||LA37_0==65) ) {
                alt37=5;
            }
            else if ( (LA37_0==VAR) ) {
                int LA37_6 = input.LA(2);

                if ( (LA37_6==58) ) {
                    int LA37_12 = input.LA(3);

                    if ( (LA37_12==STAR) ) {
                        alt37=7;
                    }
                    else if ( (LA37_12==ID) && (synpred18_Meteor())) {
                        alt37=6;
                    }
                    else if ( (LA37_12==39) && (synpred18_Meteor())) {
                        alt37=6;
                    }
                    else if ( (LA37_12==FN) && (synpred18_Meteor())) {
                        alt37=6;
                    }
                    else if ( (LA37_12==40) && (synpred18_Meteor())) {
                        alt37=6;
                    }
                    else if ( (LA37_12==65) && (synpred18_Meteor())) {
                        alt37=6;
                    }
                    else if ( (LA37_12==60) && (synpred18_Meteor())) {
                        alt37=6;
                    }
                    else if ( (LA37_12==DECIMAL) && (synpred18_Meteor())) {
                        alt37=6;
                    }
                    else if ( (LA37_12==STRING) && (synpred18_Meteor())) {
                        alt37=6;
                    }
                    else if ( (LA37_12==UINT) ) {
                        int LA37_22 = input.LA(4);

                        if ( (LA37_22==57) && (synpred18_Meteor())) {
                            alt37=6;
                        }
                        else if ( (LA37_22==47) && (synpred18_Meteor())) {
                            alt37=6;
                        }
                        else if ( (LA37_22==58) && (synpred18_Meteor())) {
                            alt37=6;
                        }
                        else if ( (LA37_22==59) ) {
                            int LA37_31 = input.LA(5);

                            if ( (synpred18_Meteor()) ) {
                                alt37=6;
                            }
                            else if ( (true) ) {
                                alt37=7;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 37, 31, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA37_22==48) ) {
                            alt37=7;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 37, 22, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA37_12==INTEGER) ) {
                        int LA37_23 = input.LA(4);

                        if ( (LA37_23==57) && (synpred18_Meteor())) {
                            alt37=6;
                        }
                        else if ( (LA37_23==47) && (synpred18_Meteor())) {
                            alt37=6;
                        }
                        else if ( (LA37_23==58) && (synpred18_Meteor())) {
                            alt37=6;
                        }
                        else if ( (LA37_23==59) ) {
                            int LA37_32 = input.LA(5);

                            if ( (synpred18_Meteor()) ) {
                                alt37=6;
                            }
                            else if ( (true) ) {
                                alt37=7;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 37, 32, input);

                                throw nvae;

                            }
                        }
                        else if ( (LA37_23==48) ) {
                            alt37=7;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 37, 23, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA37_12==62) && (synpred18_Meteor())) {
                        alt37=6;
                    }
                    else if ( (LA37_12==VAR) && (synpred18_Meteor())) {
                        alt37=6;
                    }
                    else if ( (LA37_12==58) && (synpred18_Meteor())) {
                        alt37=6;
                    }
                    else if ( (LA37_12==68) && (synpred18_Meteor())) {
                        alt37=6;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 37, 12, input);

                        throw nvae;

                    }
                }
                else if ( (LA37_6==EOF||LA37_6==AND||LA37_6==AS||(LA37_6 >= ID && LA37_6 <= IN)||LA37_6==NOT||LA37_6==OR||(LA37_6 >= SLASH && LA37_6 <= STAR)||(LA37_6 >= 37 && LA37_6 <= 38)||(LA37_6 >= 41 && LA37_6 <= 42)||(LA37_6 >= 44 && LA37_6 <= 45)||(LA37_6 >= 47 && LA37_6 <= 51)||(LA37_6 >= 53 && LA37_6 <= 57)||LA37_6==59||(LA37_6 >= 69 && LA37_6 <= 70)) ) {
                    alt37=7;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 37, 6, input);

                    throw nvae;

                }
            }
            else if ( (LA37_0==58) ) {
                alt37=9;
            }
            else if ( (LA37_0==68) ) {
                alt37=10;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 37, 0, input);

                throw nvae;

            }
            switch (alt37) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:208:4: ( ID '(' )=> functionCall
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_functionCall_in_valueExpression1497);
                    functionCall61=functionCall();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, functionCall61.getTree());

                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:209:4: functionReference
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_functionReference_in_valueExpression1502);
                    functionReference62=functionReference();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, functionReference62.getTree());

                    }
                    break;
                case 3 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:210:5: ( FN )=>func= inlineFunction
                    {
                    pushFollow(FOLLOW_inlineFunction_in_valueExpression1515);
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
                    // 210:32: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:210:35: ^( EXPRESSION[\"ConstantExpression\"] )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:211:4: parenthesesExpression
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_parenthesesExpression_in_valueExpression1529);
                    parenthesesExpression63=parenthesesExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, parenthesesExpression63.getTree());

                    }
                    break;
                case 5 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:212:4: literal
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_literal_in_valueExpression1535);
                    literal64=literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, literal64.getTree());

                    }
                    break;
                case 6 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:213:5: ( VAR '[' VAR )=> streamIndexAccess
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_streamIndexAccess_in_valueExpression1551);
                    streamIndexAccess65=streamIndexAccess();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, streamIndexAccess65.getTree());

                    }
                    break;
                case 7 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:214:4: VAR
                    {
                    VAR66=(Token)match(input,VAR,FOLLOW_VAR_in_valueExpression1556); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_VAR.add(VAR66);


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
                    // 214:8: ->
                    {
                        adaptor.addChild(root_0,  getInputSelection(VAR66) );

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 8 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:215:5: ( ( ID ':' )=>packageName= ID ':' )? constant= ID {...}? =>
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:215:5: ( ( ID ':' )=>packageName= ID ':' )?
                    int alt36=2;
                    int LA36_0 = input.LA(1);

                    if ( (LA36_0==ID) ) {
                        int LA36_1 = input.LA(2);

                        if ( (LA36_1==48) ) {
                            int LA36_2 = input.LA(3);

                            if ( (synpred19_Meteor()) ) {
                                alt36=1;
                            }
                        }
                    }
                    switch (alt36) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:215:6: ( ID ':' )=>packageName= ID ':'
                            {
                            packageName=(Token)match(input,ID,FOLLOW_ID_in_valueExpression1576); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_ID.add(packageName);


                            char_literal67=(Token)match(input,48,FOLLOW_48_in_valueExpression1578); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_48.add(char_literal67);


                            }
                            break;

                    }


                    constant=(Token)match(input,ID,FOLLOW_ID_in_valueExpression1584); if (state.failed) return retval; 
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
                    // 216:5: ->
                    {
                        adaptor.addChild(root_0,  getScope((packageName!=null?packageName.getText():null)).getConstantRegistry().get((constant!=null?constant.getText():null)) );

                    }


                    retval.tree = root_0;
                    }

                    }
                    break;
                case 9 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:217:4: arrayCreation
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_arrayCreation_in_valueExpression1604);
                    arrayCreation68=arrayCreation();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arrayCreation68.getTree());

                    }
                    break;
                case 10 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:218:4: objectCreation
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_objectCreation_in_valueExpression1610);
                    objectCreation69=objectCreation();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, objectCreation69.getTree());

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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:220:1: operatorExpression : op= operator -> ^( EXPRESSION[\"NestedOperatorExpression\"] ) ;
    public final MeteorParser.operatorExpression_return operatorExpression() throws RecognitionException {
        MeteorParser.operatorExpression_return retval = new MeteorParser.operatorExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.operator_return op =null;


        RewriteRuleSubtreeStream stream_operator=new RewriteRuleSubtreeStream(adaptor,"rule operator");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:221:2: (op= operator -> ^( EXPRESSION[\"NestedOperatorExpression\"] ) )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:221:4: op= operator
            {
            pushFollow(FOLLOW_operator_in_operatorExpression1622);
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
            // 221:16: -> ^( EXPRESSION[\"NestedOperatorExpression\"] )
            {
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:221:19: ^( EXPRESSION[\"NestedOperatorExpression\"] )
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:223:1: parenthesesExpression : ( '(' expression ')' ) -> expression ;
    public final MeteorParser.parenthesesExpression_return parenthesesExpression() throws RecognitionException {
        MeteorParser.parenthesesExpression_return retval = new MeteorParser.parenthesesExpression_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token char_literal70=null;
        Token char_literal72=null;
        MeteorParser.expression_return expression71 =null;


        EvaluationExpression char_literal70_tree=null;
        EvaluationExpression char_literal72_tree=null;
        RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:224:2: ( ( '(' expression ')' ) -> expression )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:224:4: ( '(' expression ')' )
            {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:224:4: ( '(' expression ')' )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:224:5: '(' expression ')'
            {
            char_literal70=(Token)match(input,40,FOLLOW_40_in_parenthesesExpression1641); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_40.add(char_literal70);


            pushFollow(FOLLOW_expression_in_parenthesesExpression1643);
            expression71=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expression.add(expression71.getTree());

            char_literal72=(Token)match(input,41,FOLLOW_41_in_parenthesesExpression1645); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_41.add(char_literal72);


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
            // 224:25: -> expression
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:226:1: methodCall[EvaluationExpression targetExpr] : (packageName= ID ':' )? name= ID '(' ( (param= expression ) ( ',' (param= expression ) )* )? ')' ->;
    public final MeteorParser.methodCall_return methodCall(EvaluationExpression targetExpr) throws RecognitionException {
        MeteorParser.methodCall_return retval = new MeteorParser.methodCall_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token packageName=null;
        Token name=null;
        Token char_literal73=null;
        Token char_literal74=null;
        Token char_literal75=null;
        Token char_literal76=null;
        MeteorParser.expression_return param =null;


        EvaluationExpression packageName_tree=null;
        EvaluationExpression name_tree=null;
        EvaluationExpression char_literal73_tree=null;
        EvaluationExpression char_literal74_tree=null;
        EvaluationExpression char_literal75_tree=null;
        EvaluationExpression char_literal76_tree=null;
        RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
        RewriteRuleTokenStream stream_41=new RewriteRuleTokenStream(adaptor,"token 41");
        RewriteRuleTokenStream stream_40=new RewriteRuleTokenStream(adaptor,"token 40");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
         List<EvaluationExpression> params = new ArrayList();
                paraphrase.push("a method call"); 
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:230:3: ( (packageName= ID ':' )? name= ID '(' ( (param= expression ) ( ',' (param= expression ) )* )? ')' ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:230:5: (packageName= ID ':' )? name= ID '(' ( (param= expression ) ( ',' (param= expression ) )* )? ')'
            {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:230:5: (packageName= ID ':' )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==ID) ) {
                int LA38_1 = input.LA(2);

                if ( (LA38_1==48) ) {
                    alt38=1;
                }
            }
            switch (alt38) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:230:6: packageName= ID ':'
                    {
                    packageName=(Token)match(input,ID,FOLLOW_ID_in_methodCall1675); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(packageName);


                    char_literal73=(Token)match(input,48,FOLLOW_48_in_methodCall1677); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_48.add(char_literal73);


                    }
                    break;

            }


            name=(Token)match(input,ID,FOLLOW_ID_in_methodCall1683); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(name);


            char_literal74=(Token)match(input,40,FOLLOW_40_in_methodCall1685); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_40.add(char_literal74);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:231:3: ( (param= expression ) ( ',' (param= expression ) )* )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==DECIMAL||LA40_0==FN||LA40_0==ID||LA40_0==INTEGER||(LA40_0 >= STRING && LA40_0 <= UINT)||LA40_0==VAR||LA40_0==36||(LA40_0 >= 39 && LA40_0 <= 40)||LA40_0==43||LA40_0==46||LA40_0==58||LA40_0==60||(LA40_0 >= 62 && LA40_0 <= 63)||LA40_0==65||(LA40_0 >= 67 && LA40_0 <= 68)||LA40_0==71) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:231:4: (param= expression ) ( ',' (param= expression ) )*
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:231:4: (param= expression )
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:231:5: param= expression
                    {
                    pushFollow(FOLLOW_expression_in_methodCall1694);
                    param=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(param.getTree());

                    if ( state.backtracking==0 ) { params.add((param!=null?((EvaluationExpression)param.tree):null)); }

                    }


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:232:3: ( ',' (param= expression ) )*
                    loop39:
                    do {
                        int alt39=2;
                        int LA39_0 = input.LA(1);

                        if ( (LA39_0==44) ) {
                            alt39=1;
                        }


                        switch (alt39) {
                    	case 1 :
                    	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:232:4: ',' (param= expression )
                    	    {
                    	    char_literal75=(Token)match(input,44,FOLLOW_44_in_methodCall1703); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_44.add(char_literal75);


                    	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:232:8: (param= expression )
                    	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:232:9: param= expression
                    	    {
                    	    pushFollow(FOLLOW_expression_in_methodCall1708);
                    	    param=expression();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_expression.add(param.getTree());

                    	    if ( state.backtracking==0 ) { params.add((param!=null?((EvaluationExpression)param.tree):null)); }

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


            char_literal76=(Token)match(input,41,FOLLOW_41_in_methodCall1720); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_41.add(char_literal76);


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
            // 233:7: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:235:1: functionCall : methodCall[null] ;
    public final MeteorParser.functionCall_return functionCall() throws RecognitionException {
        MeteorParser.functionCall_return retval = new MeteorParser.functionCall_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        MeteorParser.methodCall_return methodCall77 =null;



        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:236:2: ( methodCall[null] )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:236:4: methodCall[null]
            {
            root_0 = (EvaluationExpression)adaptor.nil();


            pushFollow(FOLLOW_methodCall_in_functionCall1735);
            methodCall77=methodCall(null);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, methodCall77.getTree());

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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:238:1: functionReference : '&' ( ( ID ':' )=>packageName= ID ':' )? name= ID -> ^( EXPRESSION[\"ConstantExpression\"] ) ;
    public final MeteorParser.functionReference_return functionReference() throws RecognitionException {
        MeteorParser.functionReference_return retval = new MeteorParser.functionReference_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token packageName=null;
        Token name=null;
        Token char_literal78=null;
        Token char_literal79=null;

        EvaluationExpression packageName_tree=null;
        EvaluationExpression name_tree=null;
        EvaluationExpression char_literal78_tree=null;
        EvaluationExpression char_literal79_tree=null;
        RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_39=new RewriteRuleTokenStream(adaptor,"token 39");

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:239:3: ( '&' ( ( ID ':' )=>packageName= ID ':' )? name= ID -> ^( EXPRESSION[\"ConstantExpression\"] ) )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:239:5: '&' ( ( ID ':' )=>packageName= ID ':' )? name= ID
            {
            char_literal78=(Token)match(input,39,FOLLOW_39_in_functionReference1746); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_39.add(char_literal78);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:239:9: ( ( ID ':' )=>packageName= ID ':' )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==ID) ) {
                int LA41_1 = input.LA(2);

                if ( (LA41_1==48) ) {
                    int LA41_2 = input.LA(3);

                    if ( (LA41_2==ID) ) {
                        int LA41_4 = input.LA(4);

                        if ( (synpred20_Meteor()) ) {
                            alt41=1;
                        }
                    }
                }
            }
            switch (alt41) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:239:10: ( ID ':' )=>packageName= ID ':'
                    {
                    packageName=(Token)match(input,ID,FOLLOW_ID_in_functionReference1758); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(packageName);


                    char_literal79=(Token)match(input,48,FOLLOW_48_in_functionReference1760); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_48.add(char_literal79);


                    }
                    break;

            }


            name=(Token)match(input,ID,FOLLOW_ID_in_functionReference1766); if (state.failed) return retval; 
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
            // 240:9: -> ^( EXPRESSION[\"ConstantExpression\"] )
            {
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:240:12: ^( EXPRESSION[\"ConstantExpression\"] )
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:242:1: fieldAssignment : ( ( ( ID ':' )=> ID ':' expression ->) | VAR ( '.' STAR ->|p= contextAwarePathExpression[getVariable($VAR).toInputSelection($operator::result)] ( ':' e2= expression ->| ->) ) );
    public final MeteorParser.fieldAssignment_return fieldAssignment() throws RecognitionException {
        MeteorParser.fieldAssignment_return retval = new MeteorParser.fieldAssignment_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token ID80=null;
        Token char_literal81=null;
        Token VAR83=null;
        Token char_literal84=null;
        Token STAR85=null;
        Token char_literal86=null;
        MeteorParser.contextAwarePathExpression_return p =null;

        MeteorParser.expression_return e2 =null;

        MeteorParser.expression_return expression82 =null;


        EvaluationExpression ID80_tree=null;
        EvaluationExpression char_literal81_tree=null;
        EvaluationExpression VAR83_tree=null;
        EvaluationExpression char_literal84_tree=null;
        EvaluationExpression STAR85_tree=null;
        EvaluationExpression char_literal86_tree=null;
        RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_STAR=new RewriteRuleTokenStream(adaptor,"token STAR");
        RewriteRuleTokenStream stream_47=new RewriteRuleTokenStream(adaptor,"token 47");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_contextAwarePathExpression=new RewriteRuleSubtreeStream(adaptor,"rule contextAwarePathExpression");
        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:243:2: ( ( ( ID ':' )=> ID ':' expression ->) | VAR ( '.' STAR ->|p= contextAwarePathExpression[getVariable($VAR).toInputSelection($operator::result)] ( ':' e2= expression ->| ->) ) )
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==ID) && (synpred21_Meteor())) {
                alt44=1;
            }
            else if ( (LA44_0==VAR) ) {
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:243:4: ( ( ID ':' )=> ID ':' expression ->)
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:243:4: ( ( ID ':' )=> ID ':' expression ->)
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:243:5: ( ID ':' )=> ID ':' expression
                    {
                    ID80=(Token)match(input,ID,FOLLOW_ID_in_fieldAssignment1802); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(ID80);


                    char_literal81=(Token)match(input,48,FOLLOW_48_in_fieldAssignment1804); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_48.add(char_literal81);


                    pushFollow(FOLLOW_expression_in_fieldAssignment1806);
                    expression82=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(expression82.getTree());

                    if ( state.backtracking==0 ) { ((objectCreation_scope)objectCreation_stack.peek()).mappings.add(new ObjectCreation.FieldAssignment((ID80!=null?ID80.getText():null), (expression82!=null?((EvaluationExpression)expression82.tree):null))); }

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
                    // 244:104: ->
                    {
                        root_0 = null;
                    }


                    retval.tree = root_0;
                    }

                    }


                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:245:5: VAR ( '.' STAR ->|p= contextAwarePathExpression[getVariable($VAR).toInputSelection($operator::result)] ( ':' e2= expression ->| ->) )
                    {
                    VAR83=(Token)match(input,VAR,FOLLOW_VAR_in_fieldAssignment1823); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_VAR.add(VAR83);


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:246:5: ( '.' STAR ->|p= contextAwarePathExpression[getVariable($VAR).toInputSelection($operator::result)] ( ':' e2= expression ->| ->) )
                    int alt43=2;
                    int LA43_0 = input.LA(1);

                    if ( (LA43_0==47) ) {
                        int LA43_1 = input.LA(2);

                        if ( (LA43_1==STAR) ) {
                            alt43=1;
                        }
                        else if ( (LA43_1==ID) ) {
                            alt43=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 43, 1, input);

                            throw nvae;

                        }
                    }
                    else if ( ((LA43_0 >= 57 && LA43_0 <= 58)) ) {
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:246:7: '.' STAR
                            {
                            char_literal84=(Token)match(input,47,FOLLOW_47_in_fieldAssignment1832); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_47.add(char_literal84);


                            STAR85=(Token)match(input,STAR,FOLLOW_STAR_in_fieldAssignment1834); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_STAR.add(STAR85);


                            if ( state.backtracking==0 ) { ((objectCreation_scope)objectCreation_stack.peek()).mappings.add(new ObjectCreation.CopyFields(getInputSelection(VAR83))); }

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
                            // 246:107: ->
                            {
                                root_0 = null;
                            }


                            retval.tree = root_0;
                            }

                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:248:9: p= contextAwarePathExpression[getVariable($VAR).toInputSelection($operator::result)] ( ':' e2= expression ->| ->)
                            {
                            pushFollow(FOLLOW_contextAwarePathExpression_in_fieldAssignment1851);
                            p=contextAwarePathExpression(getVariable(VAR83).toInputSelection(((operator_scope)operator_stack.peek()).result));

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_contextAwarePathExpression.add(p.getTree());

                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:249:7: ( ':' e2= expression ->| ->)
                            int alt42=2;
                            int LA42_0 = input.LA(1);

                            if ( (LA42_0==48) ) {
                                alt42=1;
                            }
                            else if ( (LA42_0==44||LA42_0==70) ) {
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
                                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:249:9: ':' e2= expression
                                    {
                                    char_literal86=(Token)match(input,48,FOLLOW_48_in_fieldAssignment1862); if (state.failed) return retval; 
                                    if ( state.backtracking==0 ) stream_48.add(char_literal86);


                                    pushFollow(FOLLOW_expression_in_fieldAssignment1866);
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
                                    // 249:112: ->
                                    {
                                        root_0 = null;
                                    }


                                    retval.tree = root_0;
                                    }

                                    }
                                    break;
                                case 2 :
                                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:250:23: 
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
                                    // 250:131: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:255:1: objectCreation : '{' ( fieldAssignment ( ',' fieldAssignment )* ( ',' )? )? '}' -> ^( EXPRESSION[\"ObjectCreation\"] ) ;
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
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:260:2: ( '{' ( fieldAssignment ( ',' fieldAssignment )* ( ',' )? )? '}' -> ^( EXPRESSION[\"ObjectCreation\"] ) )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:260:4: '{' ( fieldAssignment ( ',' fieldAssignment )* ( ',' )? )? '}'
            {
            char_literal87=(Token)match(input,68,FOLLOW_68_in_objectCreation1931); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_68.add(char_literal87);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:260:8: ( fieldAssignment ( ',' fieldAssignment )* ( ',' )? )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==ID||LA47_0==VAR) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:260:9: fieldAssignment ( ',' fieldAssignment )* ( ',' )?
                    {
                    pushFollow(FOLLOW_fieldAssignment_in_objectCreation1934);
                    fieldAssignment88=fieldAssignment();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_fieldAssignment.add(fieldAssignment88.getTree());

                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:260:25: ( ',' fieldAssignment )*
                    loop45:
                    do {
                        int alt45=2;
                        int LA45_0 = input.LA(1);

                        if ( (LA45_0==44) ) {
                            int LA45_1 = input.LA(2);

                            if ( (LA45_1==ID||LA45_1==VAR) ) {
                                alt45=1;
                            }


                        }


                        switch (alt45) {
                    	case 1 :
                    	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:260:26: ',' fieldAssignment
                    	    {
                    	    char_literal89=(Token)match(input,44,FOLLOW_44_in_objectCreation1937); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_44.add(char_literal89);


                    	    pushFollow(FOLLOW_fieldAssignment_in_objectCreation1939);
                    	    fieldAssignment90=fieldAssignment();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_fieldAssignment.add(fieldAssignment90.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop45;
                        }
                    } while (true);


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:260:48: ( ',' )?
                    int alt46=2;
                    int LA46_0 = input.LA(1);

                    if ( (LA46_0==44) ) {
                        alt46=1;
                    }
                    switch (alt46) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:260:48: ','
                            {
                            char_literal91=(Token)match(input,44,FOLLOW_44_in_objectCreation1943); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_44.add(char_literal91);


                            }
                            break;

                    }


                    }
                    break;

            }


            char_literal92=(Token)match(input,70,FOLLOW_70_in_objectCreation1948); if (state.failed) return retval; 
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
            // 260:59: -> ^( EXPRESSION[\"ObjectCreation\"] )
            {
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:260:62: ^( EXPRESSION[\"ObjectCreation\"] )
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:263:1: literal : (val= 'true' -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= 'false' -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= DECIMAL -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= STRING -> ^( EXPRESSION[\"ConstantExpression\"] ) | (val= UINT |val= INTEGER ) -> ^( EXPRESSION[\"ConstantExpression\"] ) | 'null' ->);
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
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:266:2: (val= 'true' -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= 'false' -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= DECIMAL -> ^( EXPRESSION[\"ConstantExpression\"] ) |val= STRING -> ^( EXPRESSION[\"ConstantExpression\"] ) | (val= UINT |val= INTEGER ) -> ^( EXPRESSION[\"ConstantExpression\"] ) | 'null' ->)
            int alt49=6;
            switch ( input.LA(1) ) {
            case 65:
                {
                alt49=1;
                }
                break;
            case 60:
                {
                alt49=2;
                }
                break;
            case DECIMAL:
                {
                alt49=3;
                }
                break;
            case STRING:
                {
                alt49=4;
                }
                break;
            case INTEGER:
            case UINT:
                {
                alt49=5;
                }
                break;
            case 62:
                {
                alt49=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 49, 0, input);

                throw nvae;

            }

            switch (alt49) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:266:4: val= 'true'
                    {
                    val=(Token)match(input,65,FOLLOW_65_in_literal1986); if (state.failed) return retval; 
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
                    // 266:15: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:266:18: ^( EXPRESSION[\"ConstantExpression\"] )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:267:4: val= 'false'
                    {
                    val=(Token)match(input,60,FOLLOW_60_in_literal2002); if (state.failed) return retval; 
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
                    // 267:16: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:267:19: ^( EXPRESSION[\"ConstantExpression\"] )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:268:4: val= DECIMAL
                    {
                    val=(Token)match(input,DECIMAL,FOLLOW_DECIMAL_in_literal2018); if (state.failed) return retval; 
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
                    // 268:16: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:268:19: ^( EXPRESSION[\"ConstantExpression\"] )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:269:4: val= STRING
                    {
                    val=(Token)match(input,STRING,FOLLOW_STRING_in_literal2034); if (state.failed) return retval; 
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
                    // 269:15: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:269:18: ^( EXPRESSION[\"ConstantExpression\"] )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:270:5: (val= UINT |val= INTEGER )
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:270:5: (val= UINT |val= INTEGER )
                    int alt48=2;
                    int LA48_0 = input.LA(1);

                    if ( (LA48_0==UINT) ) {
                        alt48=1;
                    }
                    else if ( (LA48_0==INTEGER) ) {
                        alt48=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 48, 0, input);

                        throw nvae;

                    }
                    switch (alt48) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:270:6: val= UINT
                            {
                            val=(Token)match(input,UINT,FOLLOW_UINT_in_literal2052); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_UINT.add(val);


                            }
                            break;
                        case 2 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:270:17: val= INTEGER
                            {
                            val=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_literal2058); if (state.failed) return retval; 
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
                    // 270:30: -> ^( EXPRESSION[\"ConstantExpression\"] )
                    {
                        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:270:33: ^( EXPRESSION[\"ConstantExpression\"] )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:271:5: 'null'
                    {
                    string_literal93=(Token)match(input,62,FOLLOW_62_in_literal2074); if (state.failed) return retval; 
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
                    // 271:12: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:273:1: streamIndexAccess : op= VAR {...}? => '[' path= generalPathExpression ']' {...}? ->;
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
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:274:3: (op= VAR {...}? => '[' path= generalPathExpression ']' {...}? ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:274:5: op= VAR {...}? => '[' path= generalPathExpression ']' {...}?
            {
            op=(Token)match(input,VAR,FOLLOW_VAR_in_streamIndexAccess2090); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_VAR.add(op);


            if ( !(( getVariable(op) != null )) ) {
                if (state.backtracking>0) {state.failed=true; return retval;}
                throw new FailedPredicateException(input, "streamIndexAccess", " getVariable($op) != null ");
            }

            char_literal94=(Token)match(input,58,FOLLOW_58_in_streamIndexAccess2099); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_58.add(char_literal94);


            pushFollow(FOLLOW_generalPathExpression_in_streamIndexAccess2103);
            path=generalPathExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_generalPathExpression.add(path.getTree());

            char_literal95=(Token)match(input,59,FOLLOW_59_in_streamIndexAccess2105); if (state.failed) return retval; 
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
            // 276:3: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:278:1: arrayCreation : '[' (elems+= expression ( ',' elems+= expression )* ( ',' )? )? ']' -> ^( EXPRESSION[\"ArrayCreation\"] ) ;
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
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:281:2: ( '[' (elems+= expression ( ',' elems+= expression )* ( ',' )? )? ']' -> ^( EXPRESSION[\"ArrayCreation\"] ) )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:281:5: '[' (elems+= expression ( ',' elems+= expression )* ( ',' )? )? ']'
            {
            char_literal96=(Token)match(input,58,FOLLOW_58_in_arrayCreation2134); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_58.add(char_literal96);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:281:9: (elems+= expression ( ',' elems+= expression )* ( ',' )? )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==DECIMAL||LA52_0==FN||LA52_0==ID||LA52_0==INTEGER||(LA52_0 >= STRING && LA52_0 <= UINT)||LA52_0==VAR||LA52_0==36||(LA52_0 >= 39 && LA52_0 <= 40)||LA52_0==43||LA52_0==46||LA52_0==58||LA52_0==60||(LA52_0 >= 62 && LA52_0 <= 63)||LA52_0==65||(LA52_0 >= 67 && LA52_0 <= 68)||LA52_0==71) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:281:10: elems+= expression ( ',' elems+= expression )* ( ',' )?
                    {
                    pushFollow(FOLLOW_expression_in_arrayCreation2139);
                    elems=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(elems.getTree());
                    if (list_elems==null) list_elems=new ArrayList();
                    list_elems.add(elems.getTree());


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:281:28: ( ',' elems+= expression )*
                    loop50:
                    do {
                        int alt50=2;
                        int LA50_0 = input.LA(1);

                        if ( (LA50_0==44) ) {
                            int LA50_1 = input.LA(2);

                            if ( (LA50_1==DECIMAL||LA50_1==FN||LA50_1==ID||LA50_1==INTEGER||(LA50_1 >= STRING && LA50_1 <= UINT)||LA50_1==VAR||LA50_1==36||(LA50_1 >= 39 && LA50_1 <= 40)||LA50_1==43||LA50_1==46||LA50_1==58||LA50_1==60||(LA50_1 >= 62 && LA50_1 <= 63)||LA50_1==65||(LA50_1 >= 67 && LA50_1 <= 68)||LA50_1==71) ) {
                                alt50=1;
                            }


                        }


                        switch (alt50) {
                    	case 1 :
                    	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:281:29: ',' elems+= expression
                    	    {
                    	    char_literal97=(Token)match(input,44,FOLLOW_44_in_arrayCreation2142); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_44.add(char_literal97);


                    	    pushFollow(FOLLOW_expression_in_arrayCreation2146);
                    	    elems=expression();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_expression.add(elems.getTree());
                    	    if (list_elems==null) list_elems=new ArrayList();
                    	    list_elems.add(elems.getTree());


                    	    }
                    	    break;

                    	default :
                    	    break loop50;
                        }
                    } while (true);


                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:281:53: ( ',' )?
                    int alt51=2;
                    int LA51_0 = input.LA(1);

                    if ( (LA51_0==44) ) {
                        alt51=1;
                    }
                    switch (alt51) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:281:53: ','
                            {
                            char_literal98=(Token)match(input,44,FOLLOW_44_in_arrayCreation2150); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_44.add(char_literal98);


                            }
                            break;

                    }


                    }
                    break;

            }


            char_literal99=(Token)match(input,59,FOLLOW_59_in_arrayCreation2155); if (state.failed) return retval; 
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
            // 281:64: -> ^( EXPRESSION[\"ArrayCreation\"] )
            {
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:281:67: ^( EXPRESSION[\"ArrayCreation\"] )
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:287:1: operator returns [Operator<?> op=null] : ( readOperator | writeOperator | genericOperator );
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
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:298:2: ( readOperator | writeOperator | genericOperator )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:298:4: readOperator
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_readOperator_in_operator2191);
                    readOperator100=readOperator();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, readOperator100.getTree());

                    if ( state.backtracking==0 ) { retval.op = (readOperator100!=null?readOperator100.source:null); }

                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:299:5: writeOperator
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_writeOperator_in_operator2199);
                    writeOperator101=writeOperator();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, writeOperator101.getTree());

                    if ( state.backtracking==0 ) { retval.op = (writeOperator101!=null?writeOperator101.sink:null); }

                    }
                    break;
                case 3 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:300:5: genericOperator
                    {
                    root_0 = (EvaluationExpression)adaptor.nil();


                    pushFollow(FOLLOW_genericOperator_in_operator2207);
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:303:1: readOperator returns [Source source] : 'read' ( (packageName= ID ':' )? format= ID )? 'from' ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' ) ( confOption[formatInfo, fileFormat] )* ->;
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
         
          ConfObjectInfo<? extends SopremoFormat> formatInfo = null;
          SopremoFormat fileFormat = null;
          String path = null;

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:309:2: ( 'read' ( (packageName= ID ':' )? format= ID )? 'from' ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' ) ( confOption[formatInfo, fileFormat] )* ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:309:4: 'read' ( (packageName= ID ':' )? format= ID )? 'from' ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' ) ( confOption[formatInfo, fileFormat] )*
            {
            string_literal103=(Token)match(input,63,FOLLOW_63_in_readOperator2229); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_63.add(string_literal103);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:309:11: ( (packageName= ID ':' )? format= ID )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==ID) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:309:12: (packageName= ID ':' )? format= ID
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:309:12: (packageName= ID ':' )?
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:309:13: packageName= ID ':'
                            {
                            packageName=(Token)match(input,ID,FOLLOW_ID_in_readOperator2235); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_ID.add(packageName);


                            char_literal104=(Token)match(input,48,FOLLOW_48_in_readOperator2237); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_48.add(char_literal104);


                            }
                            break;

                    }


                    format=(Token)match(input,ID,FOLLOW_ID_in_readOperator2242); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(format);


                    }
                    break;

            }


            string_literal105=(Token)match(input,61,FOLLOW_61_in_readOperator2249); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_61.add(string_literal105);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:310:11: ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:310:12: (protocol= ID )? filePath= STRING
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:310:20: (protocol= ID )?
                    int alt56=2;
                    int LA56_0 = input.LA(1);

                    if ( (LA56_0==ID) ) {
                        alt56=1;
                    }
                    switch (alt56) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:310:20: protocol= ID
                            {
                            protocol=(Token)match(input,ID,FOLLOW_ID_in_readOperator2254); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_ID.add(protocol);


                            }
                            break;

                    }


                    filePath=(Token)match(input,STRING,FOLLOW_STRING_in_readOperator2259); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_STRING.add(filePath);


                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:310:43: protocol= ID '(' filePath= STRING ')'
                    {
                    protocol=(Token)match(input,ID,FOLLOW_ID_in_readOperator2265); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(protocol);


                    char_literal106=(Token)match(input,40,FOLLOW_40_in_readOperator2267); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_40.add(char_literal106);


                    filePath=(Token)match(input,STRING,FOLLOW_STRING_in_readOperator2271); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_STRING.add(filePath);


                    char_literal107=(Token)match(input,41,FOLLOW_41_in_readOperator2273); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_41.add(char_literal107);


                    }
                    break;

            }


            if ( state.backtracking==0 ) { 
              path = makeFilePath(protocol, (filePath!=null?filePath.getText():null));
              formatInfo = findFormat((packageName!=null?packageName.getText():null), format, path);
              fileFormat = formatInfo.newInstance(); 
            }

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:316:4: ( confOption[formatInfo, fileFormat] )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==ID) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:316:4: confOption[formatInfo, fileFormat]
            	    {
            	    pushFollow(FOLLOW_confOption_in_readOperator2282);
            	    confOption108=confOption(formatInfo, fileFormat);

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_confOption.add(confOption108.getTree());

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
            // 317:45: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:320:1: writeOperator returns [Sink sink] : 'write' ( (packageName= ID ':' )? format= ID )? from= VAR 'to' ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' ) ( confOption[formatInfo, fileFormat] )* ->;
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
         
          ConfObjectInfo<? extends SopremoFormat> formatInfo = null;
          SopremoFormat fileFormat = null;
          String path = null;

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:326:2: ( 'write' ( (packageName= ID ':' )? format= ID )? from= VAR 'to' ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' ) ( confOption[formatInfo, fileFormat] )* ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:326:4: 'write' ( (packageName= ID ':' )? format= ID )? from= VAR 'to' ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' ) ( confOption[formatInfo, fileFormat] )*
            {
            string_literal109=(Token)match(input,67,FOLLOW_67_in_writeOperator2308); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_67.add(string_literal109);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:326:12: ( (packageName= ID ':' )? format= ID )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==ID) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:326:13: (packageName= ID ':' )? format= ID
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:326:13: (packageName= ID ':' )?
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
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:326:14: packageName= ID ':'
                            {
                            packageName=(Token)match(input,ID,FOLLOW_ID_in_writeOperator2314); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_ID.add(packageName);


                            char_literal110=(Token)match(input,48,FOLLOW_48_in_writeOperator2316); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_48.add(char_literal110);


                            }
                            break;

                    }


                    format=(Token)match(input,ID,FOLLOW_ID_in_writeOperator2321); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(format);


                    }
                    break;

            }


            from=(Token)match(input,VAR,FOLLOW_VAR_in_writeOperator2327); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_VAR.add(from);


            string_literal111=(Token)match(input,64,FOLLOW_64_in_writeOperator2333); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_64.add(string_literal111);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:327:9: ( (protocol= ID )? filePath= STRING |protocol= ID '(' filePath= STRING ')' )
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:327:10: (protocol= ID )? filePath= STRING
                    {
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:327:18: (protocol= ID )?
                    int alt61=2;
                    int LA61_0 = input.LA(1);

                    if ( (LA61_0==ID) ) {
                        alt61=1;
                    }
                    switch (alt61) {
                        case 1 :
                            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:327:18: protocol= ID
                            {
                            protocol=(Token)match(input,ID,FOLLOW_ID_in_writeOperator2338); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_ID.add(protocol);


                            }
                            break;

                    }


                    filePath=(Token)match(input,STRING,FOLLOW_STRING_in_writeOperator2343); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_STRING.add(filePath);


                    }
                    break;
                case 2 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:327:41: protocol= ID '(' filePath= STRING ')'
                    {
                    protocol=(Token)match(input,ID,FOLLOW_ID_in_writeOperator2349); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(protocol);


                    char_literal112=(Token)match(input,40,FOLLOW_40_in_writeOperator2351); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_40.add(char_literal112);


                    filePath=(Token)match(input,STRING,FOLLOW_STRING_in_writeOperator2355); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_STRING.add(filePath);


                    char_literal113=(Token)match(input,41,FOLLOW_41_in_writeOperator2357); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_41.add(char_literal113);


                    }
                    break;

            }


            if ( state.backtracking==0 ) { 
              path = makeFilePath(protocol, (filePath!=null?filePath.getText():null));
              formatInfo = findFormat((packageName!=null?packageName.getText():null), format, path);
              fileFormat = formatInfo.newInstance();
            }

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:333:5: ( confOption[formatInfo, fileFormat] )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==ID) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:333:5: confOption[formatInfo, fileFormat]
            	    {
            	    pushFollow(FOLLOW_confOption_in_writeOperator2366);
            	    confOption114=confOption(formatInfo, fileFormat);

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_confOption.add(confOption114.getTree());

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
            // 338:3: ->
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


    public static class genericOperatorName_return extends ParserRuleReturnScope {
        public ConfObjectInfo<? extends Operator<?>> info;
        EvaluationExpression tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "genericOperatorName"
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:340:1: genericOperatorName returns [ConfObjectInfo<? extends Operator<?>> info] : (packageName= ID ':' )? name= ID {...}? => ->;
    public final MeteorParser.genericOperatorName_return genericOperatorName() throws RecognitionException {
        MeteorParser.genericOperatorName_return retval = new MeteorParser.genericOperatorName_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token packageName=null;
        Token name=null;
        Token char_literal115=null;

        EvaluationExpression packageName_tree=null;
        EvaluationExpression name_tree=null;
        EvaluationExpression char_literal115_tree=null;
        RewriteRuleTokenStream stream_48=new RewriteRuleTokenStream(adaptor,"token 48");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:340:73: ( (packageName= ID ':' )? name= ID {...}? => ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:341:2: (packageName= ID ':' )? name= ID {...}? =>
            {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:341:2: (packageName= ID ':' )?
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:341:2: packageName= ID ':'
                    {
                    packageName=(Token)match(input,ID,FOLLOW_ID_in_genericOperatorName2387); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ID.add(packageName);


                    char_literal115=(Token)match(input,48,FOLLOW_48_in_genericOperatorName2389); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_48.add(char_literal115);


                    }
                    break;

            }


            name=(Token)match(input,ID,FOLLOW_ID_in_genericOperatorName2395); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(name);


            if ( !(( (retval.info = findOperatorGreedily((packageName!=null?packageName.getText():null), name)) != null  )) ) {
                if (state.backtracking>0) {state.failed=true; return retval;}
                throw new FailedPredicateException(input, "genericOperatorName", " ($info = findOperatorGreedily($packageName.text, $name)) != null  ");
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
            // 341:104: ->
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
    // $ANTLR end "genericOperatorName"


    public static class genericOperator_return extends ParserRuleReturnScope {
        public Operator<?> op;
        EvaluationExpression tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "genericOperator"
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:347:1: genericOperator returns [Operator<?> op] : name= genericOperatorName {...}? => ( operatorFlag[operatorInfo, $op] )* ( ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )* ) ( confOption[operatorInfo, $op] )* ->;
    public final MeteorParser.genericOperator_return genericOperator() throws RecognitionException {
        MeteorParser.genericOperator_return retval = new MeteorParser.genericOperator_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token char_literal118=null;
        MeteorParser.genericOperatorName_return name =null;

        MeteorParser.operatorFlag_return operatorFlag116 =null;

        MeteorParser.input_return input117 =null;

        MeteorParser.input_return input119 =null;

        MeteorParser.confOption_return confOption120 =null;


        EvaluationExpression char_literal118_tree=null;
        RewriteRuleTokenStream stream_44=new RewriteRuleTokenStream(adaptor,"token 44");
        RewriteRuleSubtreeStream stream_input=new RewriteRuleSubtreeStream(adaptor,"rule input");
        RewriteRuleSubtreeStream stream_genericOperatorName=new RewriteRuleSubtreeStream(adaptor,"rule genericOperatorName");
        RewriteRuleSubtreeStream stream_operatorFlag=new RewriteRuleSubtreeStream(adaptor,"rule operatorFlag");
        RewriteRuleSubtreeStream stream_confOption=new RewriteRuleSubtreeStream(adaptor,"rule confOption");
         
          ConfObjectInfo<? extends Operator<?>> operatorInfo;

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:350:3: (name= genericOperatorName {...}? => ( operatorFlag[operatorInfo, $op] )* ( ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )* ) ( confOption[operatorInfo, $op] )* ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:350:5: name= genericOperatorName {...}? => ( operatorFlag[operatorInfo, $op] )* ( ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )* ) ( confOption[operatorInfo, $op] )*
            {
            pushFollow(FOLLOW_genericOperatorName_in_genericOperator2423);
            name=genericOperatorName();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_genericOperatorName.add(name.getTree());

            if ( !(( (operatorInfo = (name!=null?name.info:null)) != null )) ) {
                if (state.backtracking>0) {state.failed=true; return retval;}
                throw new FailedPredicateException(input, "genericOperator", " (operatorInfo = $name.info) != null ");
            }

            if ( state.backtracking==0 ) { ((operator_scope)operator_stack.peek()).result = retval.op = operatorInfo.newInstance(); }

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:352:13: ( operatorFlag[operatorInfo, $op] )*
            loop65:
            do {
                int alt65=2;
                int LA65_0 = input.LA(1);

                if ( (LA65_0==ID) ) {
                    alt65=1;
                }


                switch (alt65) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:352:13: operatorFlag[operatorInfo, $op]
            	    {
            	    pushFollow(FOLLOW_operatorFlag_in_genericOperator2431);
            	    operatorFlag116=operatorFlag(operatorInfo, retval.op);

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_operatorFlag.add(operatorFlag116.getTree());

            	    }
            	    break;

            	default :
            	    break loop65;
                }
            } while (true);


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:353:2: ( ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )* )
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:353:2: ( VAR )=> input[operatorInfo, $op] ( ( ',' )=> ',' input[operatorInfo, $op] )*
            {
            pushFollow(FOLLOW_input_in_genericOperator2441);
            input117=input(operatorInfo, retval.op);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_input.add(input117.getTree());

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:353:35: ( ( ',' )=> ',' input[operatorInfo, $op] )*
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( (LA66_0==44) ) {
                    int LA66_2 = input.LA(2);

                    if ( (LA66_2==VAR) ) {
                        int LA66_3 = input.LA(3);

                        if ( (synpred23_Meteor()) ) {
                            alt66=1;
                        }


                    }


                }


                switch (alt66) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:353:36: ( ',' )=> ',' input[operatorInfo, $op]
            	    {
            	    char_literal118=(Token)match(input,44,FOLLOW_44_in_genericOperator2450); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_44.add(char_literal118);


            	    pushFollow(FOLLOW_input_in_genericOperator2452);
            	    input119=input(operatorInfo, retval.op);

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_input.add(input119.getTree());

            	    }
            	    break;

            	default :
            	    break loop66;
                }
            } while (true);


            }


            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:354:11: ( confOption[operatorInfo, $op] )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==ID) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:354:11: confOption[operatorInfo, $op]
            	    {
            	    pushFollow(FOLLOW_confOption_in_genericOperator2458);
            	    confOption120=confOption(operatorInfo, retval.op);

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_confOption.add(confOption120.getTree());

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
            // 355:3: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:357:1: confOption[ConfObjectInfo<?> info, ConfigurableSopremoType object] : name= ID expr= ternaryExpression ->;
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
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:360:3: (name= ID expr= ternaryExpression ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:361:3: name= ID expr= ternaryExpression
            {
            name=(Token)match(input,ID,FOLLOW_ID_in_confOption2485); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ID.add(name);


            if ( state.backtracking==0 ) { property = findPropertyRelunctantly(info, name); }

            pushFollow(FOLLOW_ternaryExpression_in_confOption2494);
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
            // 363:69: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:365:1: operatorFlag[ConfObjectInfo<?> info, ConfigurableSopremoType object] : name= ID {...}? ->;
    public final MeteorParser.operatorFlag_return operatorFlag(ConfObjectInfo<?> info, ConfigurableSopremoType object) throws RecognitionException {
        MeteorParser.operatorFlag_return retval = new MeteorParser.operatorFlag_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token name=null;

        EvaluationExpression name_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");


         ConfObjectInfo.ConfObjectPropertyInfo property = null;

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:369:3: (name= ID {...}? ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:369:5: name= ID {...}?
            {
            name=(Token)match(input,ID,FOLLOW_ID_in_operatorFlag2517); if (state.failed) return retval; 
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
            // 372:38: ->
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
    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:374:1: input[ConfObjectInfo<?> info, Operator<?> object] : (name= VAR IN )? from= VAR ({...}? =>expr= ternaryExpression )? ->;
    public final MeteorParser.input_return input(ConfObjectInfo<?> info, Operator<?> object) throws RecognitionException {
        MeteorParser.input_return retval = new MeteorParser.input_return();
        retval.start = input.LT(1);


        EvaluationExpression root_0 = null;

        Token name=null;
        Token from=null;
        Token IN121=null;
        MeteorParser.ternaryExpression_return expr =null;


        EvaluationExpression name_tree=null;
        EvaluationExpression from_tree=null;
        EvaluationExpression IN121_tree=null;
        RewriteRuleTokenStream stream_VAR=new RewriteRuleTokenStream(adaptor,"token VAR");
        RewriteRuleTokenStream stream_IN=new RewriteRuleTokenStream(adaptor,"token IN");
        RewriteRuleSubtreeStream stream_ternaryExpression=new RewriteRuleSubtreeStream(adaptor,"rule ternaryExpression");

         ConfObjectInfo.ConfObjectIndexedPropertyInfo inputProperty = null;

        try {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:377:3: ( (name= VAR IN )? from= VAR ({...}? =>expr= ternaryExpression )? ->)
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:377:5: (name= VAR IN )? from= VAR ({...}? =>expr= ternaryExpression )?
            {
            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:377:5: (name= VAR IN )?
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
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:377:6: name= VAR IN
                    {
                    name=(Token)match(input,VAR,FOLLOW_VAR_in_input2542); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_VAR.add(name);


                    IN121=(Token)match(input,IN,FOLLOW_IN_in_input2544); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_IN.add(IN121);


                    }
                    break;

            }


            from=(Token)match(input,VAR,FOLLOW_VAR_in_input2550); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_VAR.add(from);


            if ( state.backtracking==0 ) { 
              int inputIndex = ((operator_scope)operator_stack.peek()).numInputs++;
              JsonStreamExpression input = getVariable(from);
              object.setInput(inputIndex, input.getStream());
              
              JsonStreamExpression inputExpression = new JsonStreamExpression(input.getStream(), inputIndex);
              putVariable(name != null ? name : from, inputExpression);
            }

            // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:386:2: ({...}? =>expr= ternaryExpression )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==DECIMAL||LA69_0==FN||LA69_0==INTEGER||(LA69_0 >= STRING && LA69_0 <= UINT)||LA69_0==VAR||LA69_0==36||(LA69_0 >= 39 && LA69_0 <= 40)||LA69_0==43||LA69_0==46||LA69_0==58||LA69_0==60||LA69_0==62||LA69_0==65||LA69_0==68||LA69_0==71) && (( (findInputPropertyRelunctantly(info, input.LT(1), false) != null) ))) {
                alt69=1;
            }
            else if ( (LA69_0==ID) ) {
                int LA69_5 = input.LA(2);

                if ( (( (findInputPropertyRelunctantly(info, input.LT(1), false) != null) )) ) {
                    alt69=1;
                }
            }
            switch (alt69) {
                case 1 :
                    // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:386:2: {...}? =>expr= ternaryExpression
                    {
                    if ( !(( (findInputPropertyRelunctantly(info, input.LT(1), false) != null) )) ) {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        throw new FailedPredicateException(input, "input", " (findInputPropertyRelunctantly(info, input.LT(1), false) != null) ");
                    }

                    if ( state.backtracking==0 ) { inputProperty = findInputPropertyRelunctantly(info, input.LT(1), true); }

                    pushFollow(FOLLOW_ternaryExpression_in_input2567);
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
            // 389:4: ->
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
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:108:5: ( 'read' | 'write' | genericOperatorName )
        int alt70=3;
        switch ( input.LA(1) ) {
        case 63:
            {
            alt70=1;
            }
            break;
        case 67:
            {
            alt70=2;
            }
            break;
        case ID:
            {
            alt70=3;
            }
            break;
        default:
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 70, 0, input);

            throw nvae;

        }

        switch (alt70) {
            case 1 :
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:108:6: 'read'
                {
                match(input,63,FOLLOW_63_in_synpred1_Meteor400); if (state.failed) return ;

                }
                break;
            case 2 :
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:108:15: 'write'
                {
                match(input,67,FOLLOW_67_in_synpred1_Meteor404); if (state.failed) return ;

                }
                break;
            case 3 :
                // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:108:25: genericOperatorName
                {
                pushFollow(FOLLOW_genericOperatorName_in_synpred1_Meteor408);
                genericOperatorName();

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }
    }
    // $ANTLR end synpred1_Meteor

    // $ANTLR start synpred2_Meteor
    public final void synpred2_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:112:4: ( orExpression '?' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:112:5: orExpression '?'
        {
        pushFollow(FOLLOW_orExpression_in_synpred2_Meteor428);
        orExpression();

        state._fsp--;
        if (state.failed) return ;

        match(input,56,FOLLOW_56_in_synpred2_Meteor430); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred2_Meteor

    // $ANTLR start synpred3_Meteor
    public final void synpred3_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:114:4: ( orExpression IF )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:114:5: orExpression IF
        {
        pushFollow(FOLLOW_orExpression_in_synpred3_Meteor470);
        orExpression();

        state._fsp--;
        if (state.failed) return ;

        match(input,IF,FOLLOW_IF_in_synpred3_Meteor472); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred3_Meteor

    // $ANTLR start synpred4_Meteor
    public final void synpred4_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:162:4: ( '(' ID ')' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:162:5: '(' ID ')'
        {
        match(input,40,FOLLOW_40_in_synpred4_Meteor939); if (state.failed) return ;

        match(input,ID,FOLLOW_ID_in_synpred4_Meteor941); if (state.failed) return ;

        match(input,41,FOLLOW_41_in_synpred4_Meteor943); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred4_Meteor

    // $ANTLR start synpred5_Meteor
    public final void synpred5_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:164:4: ( generalPathExpression AS )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:164:5: generalPathExpression AS
        {
        pushFollow(FOLLOW_generalPathExpression_in_synpred5_Meteor969);
        generalPathExpression();

        state._fsp--;
        if (state.failed) return ;

        match(input,AS,FOLLOW_AS_in_synpred5_Meteor971); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred5_Meteor

    // $ANTLR start synpred6_Meteor
    public final void synpred6_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:170:5: ( pathExpression[EvaluationExpression.VALUE] )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:170:6: pathExpression[EvaluationExpression.VALUE]
        {
        pushFollow(FOLLOW_pathExpression_in_synpred6_Meteor1014);
        pathExpression(EvaluationExpression.VALUE);

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred6_Meteor

    // $ANTLR start synpred7_Meteor
    public final void synpred7_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:178:5: ( '?.' ID '(' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:178:6: '?.' ID '('
        {
        match(input,57,FOLLOW_57_in_synpred7_Meteor1070); if (state.failed) return ;

        match(input,ID,FOLLOW_ID_in_synpred7_Meteor1072); if (state.failed) return ;

        match(input,40,FOLLOW_40_in_synpred7_Meteor1074); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred7_Meteor

    // $ANTLR start synpred8_Meteor
    public final void synpred8_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:179:8: ( pathSegment )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:179:9: pathSegment
        {
        pushFollow(FOLLOW_pathSegment_in_synpred8_Meteor1093);
        pathSegment();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred8_Meteor

    // $ANTLR start synpred9_Meteor
    public final void synpred9_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:182:5: ( '.' ID '(' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:182:6: '.' ID '('
        {
        match(input,47,FOLLOW_47_in_synpred9_Meteor1141); if (state.failed) return ;

        match(input,ID,FOLLOW_ID_in_synpred9_Meteor1143); if (state.failed) return ;

        match(input,40,FOLLOW_40_in_synpred9_Meteor1145); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred9_Meteor

    // $ANTLR start synpred10_Meteor
    public final void synpred10_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:183:8: ( pathSegment )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:183:9: pathSegment
        {
        pushFollow(FOLLOW_pathSegment_in_synpred10_Meteor1164);
        pathSegment();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred10_Meteor

    // $ANTLR start synpred11_Meteor
    public final void synpred11_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:186:6: ( pathSegment )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:186:7: pathSegment
        {
        pushFollow(FOLLOW_pathSegment_in_synpred11_Meteor1206);
        pathSegment();

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred11_Meteor

    // $ANTLR start synpred12_Meteor
    public final void synpred12_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:193:5: ( '?.' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:193:6: '?.'
        {
        match(input,57,FOLLOW_57_in_synpred12_Meteor1260); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred12_Meteor

    // $ANTLR start synpred13_Meteor
    public final void synpred13_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:194:5: ( '.' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:194:6: '.'
        {
        match(input,47,FOLLOW_47_in_synpred13_Meteor1298); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred13_Meteor

    // $ANTLR start synpred14_Meteor
    public final void synpred14_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:195:5: ( '[' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:195:6: '['
        {
        match(input,58,FOLLOW_58_in_synpred14_Meteor1327); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred14_Meteor

    // $ANTLR start synpred15_Meteor
    public final void synpred15_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:198:19: ( '.' methodCall[null] )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:198:20: '.' methodCall[null]
        {
        match(input,47,FOLLOW_47_in_synpred15_Meteor1350); if (state.failed) return ;

        pushFollow(FOLLOW_methodCall_in_synpred15_Meteor1352);
        methodCall(null);

        state._fsp--;
        if (state.failed) return ;

        }

    }
    // $ANTLR end synpred15_Meteor

    // $ANTLR start synpred16_Meteor
    public final void synpred16_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:208:4: ( ID '(' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:208:5: ID '('
        {
        match(input,ID,FOLLOW_ID_in_synpred16_Meteor1491); if (state.failed) return ;

        match(input,40,FOLLOW_40_in_synpred16_Meteor1493); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred16_Meteor

    // $ANTLR start synpred17_Meteor
    public final void synpred17_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:210:5: ( FN )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:210:6: FN
        {
        match(input,FN,FOLLOW_FN_in_synpred17_Meteor1509); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred17_Meteor

    // $ANTLR start synpred18_Meteor
    public final void synpred18_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:213:5: ( VAR '[' VAR )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:213:6: VAR '[' VAR
        {
        match(input,VAR,FOLLOW_VAR_in_synpred18_Meteor1543); if (state.failed) return ;

        match(input,58,FOLLOW_58_in_synpred18_Meteor1545); if (state.failed) return ;

        match(input,VAR,FOLLOW_VAR_in_synpred18_Meteor1547); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred18_Meteor

    // $ANTLR start synpred19_Meteor
    public final void synpred19_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:215:6: ( ID ':' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:215:7: ID ':'
        {
        match(input,ID,FOLLOW_ID_in_synpred19_Meteor1568); if (state.failed) return ;

        match(input,48,FOLLOW_48_in_synpred19_Meteor1570); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred19_Meteor

    // $ANTLR start synpred20_Meteor
    public final void synpred20_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:239:10: ( ID ':' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:239:11: ID ':'
        {
        match(input,ID,FOLLOW_ID_in_synpred20_Meteor1750); if (state.failed) return ;

        match(input,48,FOLLOW_48_in_synpred20_Meteor1752); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred20_Meteor

    // $ANTLR start synpred21_Meteor
    public final void synpred21_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:243:5: ( ID ':' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:243:6: ID ':'
        {
        match(input,ID,FOLLOW_ID_in_synpred21_Meteor1796); if (state.failed) return ;

        match(input,48,FOLLOW_48_in_synpred21_Meteor1798); if (state.failed) return ;

        }

    }
    // $ANTLR end synpred21_Meteor

    // $ANTLR start synpred23_Meteor
    public final void synpred23_Meteor_fragment() throws RecognitionException {
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:353:36: ( ',' )
        // /home/arv/workspace/ozone-meteor/meteor/meteor-meteor/src/main/java/eu/stratosphere/meteor/Meteor.g:353:37: ','
        {
        match(input,44,FOLLOW_44_in_synpred23_Meteor2446); if (state.failed) return ;

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
    public static final BitSet FOLLOW_VAR_in_assignment214 = new BitSet(new long[]{0x0010100000000000L});
    public static final BitSet FOLLOW_44_in_assignment217 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_VAR_in_assignment221 = new BitSet(new long[]{0x0010100000000000L});
    public static final BitSet FOLLOW_52_in_assignment225 = new BitSet(new long[]{0x8000000000010000L,0x0000000000000008L});
    public static final BitSet FOLLOW_operator_in_assignment229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_functionDefinition248 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_functionDefinition250 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_inlineFunction_in_functionDefinition254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FN_in_inlineFunction280 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_inlineFunction282 = new BitSet(new long[]{0x0000020000010000L});
    public static final BitSet FOLLOW_ID_in_inlineFunction291 = new BitSet(new long[]{0x0000120000000000L});
    public static final BitSet FOLLOW_44_in_inlineFunction298 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_inlineFunction302 = new BitSet(new long[]{0x0000120000000000L});
    public static final BitSet FOLLOW_41_in_inlineFunction313 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_inlineFunction323 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_ternaryExpression_in_inlineFunction327 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_inlineFunction329 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_javaudf349 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_javaudf351 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_JAVAUDF_in_javaudf353 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_javaudf355 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_STRING_in_javaudf359 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_javaudf361 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ternaryExpression_in_contextAwareExpression389 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operatorExpression_in_expression412 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ternaryExpression_in_expression418 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression436 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_ternaryExpression438 = new BitSet(new long[]{0x54014994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression442 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_ternaryExpression445 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression449 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression478 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_IF_in_ternaryExpression480 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression484 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpression_in_ternaryExpression507 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andExpression_in_orExpression520 = new BitSet(new long[]{0x0000000002000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_OR_in_orExpression524 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_69_in_orExpression528 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_andExpression_in_orExpression533 = new BitSet(new long[]{0x0000000002000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_elementExpression_in_andExpression562 = new BitSet(new long[]{0x0000004000000012L});
    public static final BitSet FOLLOW_AND_in_andExpression566 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_38_in_andExpression570 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_elementExpression_in_andExpression575 = new BitSet(new long[]{0x0000004000000012L});
    public static final BitSet FOLLOW_comparisonExpression_in_elementExpression604 = new BitSet(new long[]{0x0000000000440002L});
    public static final BitSet FOLLOW_NOT_in_elementExpression609 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_IN_in_elementExpression612 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_comparisonExpression_in_elementExpression616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arithmeticExpression_in_comparisonExpression657 = new BitSet(new long[]{0x00EC002000000002L});
    public static final BitSet FOLLOW_51_in_comparisonExpression663 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_55_in_comparisonExpression669 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_50_in_comparisonExpression675 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_54_in_comparisonExpression681 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_53_in_comparisonExpression687 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_37_in_comparisonExpression693 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_arithmeticExpression_in_comparisonExpression698 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicationExpression_in_arithmeticExpression778 = new BitSet(new long[]{0x0000240000000002L});
    public static final BitSet FOLLOW_42_in_arithmeticExpression784 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_45_in_arithmeticExpression790 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_multiplicationExpression_in_arithmeticExpression795 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_preincrementExpression_in_multiplicationExpression838 = new BitSet(new long[]{0x0000000030000002L});
    public static final BitSet FOLLOW_STAR_in_multiplicationExpression844 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_SLASH_in_multiplicationExpression850 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_preincrementExpression_in_multiplicationExpression855 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_preincrementExpression896 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_preincrementExpression_in_preincrementExpression898 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_preincrementExpression903 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_preincrementExpression_in_preincrementExpression905 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unaryExpression_in_preincrementExpression910 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_castExpression_in_unaryExpression929 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_castExpression947 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_castExpression951 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_castExpression953 = new BitSet(new long[]{0x54000184C0094100L,0x0000000000000012L});
    public static final BitSet FOLLOW_generalPathExpression_in_castExpression957 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_generalPathExpression_in_castExpression977 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_AS_in_castExpression979 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_castExpression983 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_generalPathExpression_in_castExpression994 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_valueExpression_in_generalPathExpression1006 = new BitSet(new long[]{0x0600800000000002L});
    public static final BitSet FOLLOW_pathExpression_in_generalPathExpression1021 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathExpression_in_contextAwarePathExpression1050 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_pathExpression1078 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_methodCall_in_pathExpression1082 = new BitSet(new long[]{0x0600800000000002L});
    public static final BitSet FOLLOW_pathExpression_in_pathExpression1099 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_pathExpression1149 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_methodCall_in_pathExpression1153 = new BitSet(new long[]{0x0600800000000002L});
    public static final BitSet FOLLOW_pathExpression_in_pathExpression1170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathSegment_in_pathExpression1196 = new BitSet(new long[]{0x0600800000000002L});
    public static final BitSet FOLLOW_pathExpression_in_pathExpression1212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_pathSegment1264 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_pathSegment1268 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_pathSegment1303 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_pathSegment1307 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayAccess_in_pathSegment1332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_arrayAccess1342 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_STAR_in_arrayAccess1344 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_arrayAccess1346 = new BitSet(new long[]{0x0600800000000000L});
    public static final BitSet FOLLOW_47_in_arrayAccess1357 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_methodCall_in_arrayAccess1361 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathSegment_in_arrayAccess1384 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_arrayAccess1405 = new BitSet(new long[]{0x0000000080080000L});
    public static final BitSet FOLLOW_INTEGER_in_arrayAccess1410 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_UINT_in_arrayAccess1416 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_arrayAccess1419 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_arrayAccess1437 = new BitSet(new long[]{0x0000000080080000L});
    public static final BitSet FOLLOW_INTEGER_in_arrayAccess1442 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_UINT_in_arrayAccess1448 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_arrayAccess1451 = new BitSet(new long[]{0x0000000080080000L});
    public static final BitSet FOLLOW_INTEGER_in_arrayAccess1456 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_UINT_in_arrayAccess1462 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_arrayAccess1465 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functionCall_in_valueExpression1497 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functionReference_in_valueExpression1502 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inlineFunction_in_valueExpression1515 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parenthesesExpression_in_valueExpression1529 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_valueExpression1535 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_streamIndexAccess_in_valueExpression1551 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_valueExpression1556 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_valueExpression1576 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_valueExpression1578 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_valueExpression1584 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayCreation_in_valueExpression1604 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objectCreation_in_valueExpression1610 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operator_in_operatorExpression1622 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_parenthesesExpression1641 = new BitSet(new long[]{0xD4004994C0094100L,0x000000000000009AL});
    public static final BitSet FOLLOW_expression_in_parenthesesExpression1643 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_parenthesesExpression1645 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_methodCall1675 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_methodCall1677 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_methodCall1683 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_methodCall1685 = new BitSet(new long[]{0xD4004B94C0094100L,0x000000000000009AL});
    public static final BitSet FOLLOW_expression_in_methodCall1694 = new BitSet(new long[]{0x0000120000000000L});
    public static final BitSet FOLLOW_44_in_methodCall1703 = new BitSet(new long[]{0xD4004994C0094100L,0x000000000000009AL});
    public static final BitSet FOLLOW_expression_in_methodCall1708 = new BitSet(new long[]{0x0000120000000000L});
    public static final BitSet FOLLOW_41_in_methodCall1720 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_methodCall_in_functionCall1735 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_functionReference1746 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_functionReference1758 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_functionReference1760 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_functionReference1766 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_fieldAssignment1802 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_fieldAssignment1804 = new BitSet(new long[]{0xD4004994C0094100L,0x000000000000009AL});
    public static final BitSet FOLLOW_expression_in_fieldAssignment1806 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_fieldAssignment1823 = new BitSet(new long[]{0x0600800000000000L});
    public static final BitSet FOLLOW_47_in_fieldAssignment1832 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_STAR_in_fieldAssignment1834 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_contextAwarePathExpression_in_fieldAssignment1851 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_48_in_fieldAssignment1862 = new BitSet(new long[]{0xD4004994C0094100L,0x000000000000009AL});
    public static final BitSet FOLLOW_expression_in_fieldAssignment1866 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_objectCreation1931 = new BitSet(new long[]{0x0000000400010000L,0x0000000000000040L});
    public static final BitSet FOLLOW_fieldAssignment_in_objectCreation1934 = new BitSet(new long[]{0x0000100000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_44_in_objectCreation1937 = new BitSet(new long[]{0x0000000400010000L});
    public static final BitSet FOLLOW_fieldAssignment_in_objectCreation1939 = new BitSet(new long[]{0x0000100000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_44_in_objectCreation1943 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_objectCreation1948 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_literal1986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_literal2002 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DECIMAL_in_literal2018 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal2034 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_UINT_in_literal2052 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_literal2058 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_literal2074 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_streamIndexAccess2090 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_streamIndexAccess2099 = new BitSet(new long[]{0x54000184C0094100L,0x0000000000000012L});
    public static final BitSet FOLLOW_generalPathExpression_in_streamIndexAccess2103 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_streamIndexAccess2105 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_arrayCreation2134 = new BitSet(new long[]{0xDC004994C0094100L,0x000000000000009AL});
    public static final BitSet FOLLOW_expression_in_arrayCreation2139 = new BitSet(new long[]{0x0800100000000000L});
    public static final BitSet FOLLOW_44_in_arrayCreation2142 = new BitSet(new long[]{0xD4004994C0094100L,0x000000000000009AL});
    public static final BitSet FOLLOW_expression_in_arrayCreation2146 = new BitSet(new long[]{0x0800100000000000L});
    public static final BitSet FOLLOW_44_in_arrayCreation2150 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_arrayCreation2155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_readOperator_in_operator2191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_writeOperator_in_operator2199 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_genericOperator_in_operator2207 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_readOperator2229 = new BitSet(new long[]{0x2000000000010000L});
    public static final BitSet FOLLOW_ID_in_readOperator2235 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_readOperator2237 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_readOperator2242 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_readOperator2249 = new BitSet(new long[]{0x0000000040010000L});
    public static final BitSet FOLLOW_ID_in_readOperator2254 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_STRING_in_readOperator2259 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_ID_in_readOperator2265 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_readOperator2267 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_STRING_in_readOperator2271 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_readOperator2273 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_confOption_in_readOperator2282 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_67_in_writeOperator2308 = new BitSet(new long[]{0x0000000400010000L});
    public static final BitSet FOLLOW_ID_in_writeOperator2314 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_writeOperator2316 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_writeOperator2321 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_VAR_in_writeOperator2327 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_writeOperator2333 = new BitSet(new long[]{0x0000000040010000L});
    public static final BitSet FOLLOW_ID_in_writeOperator2338 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_STRING_in_writeOperator2343 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_ID_in_writeOperator2349 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_writeOperator2351 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_STRING_in_writeOperator2355 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_writeOperator2357 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_confOption_in_writeOperator2366 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_ID_in_genericOperatorName2387 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_genericOperatorName2389 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_genericOperatorName2395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_genericOperatorName_in_genericOperator2423 = new BitSet(new long[]{0x0000000400010000L});
    public static final BitSet FOLLOW_operatorFlag_in_genericOperator2431 = new BitSet(new long[]{0x0000000400010000L});
    public static final BitSet FOLLOW_input_in_genericOperator2441 = new BitSet(new long[]{0x0000100000010002L});
    public static final BitSet FOLLOW_44_in_genericOperator2450 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_input_in_genericOperator2452 = new BitSet(new long[]{0x0000100000010002L});
    public static final BitSet FOLLOW_confOption_in_genericOperator2458 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_ID_in_confOption2485 = new BitSet(new long[]{0x54004994C0094100L,0x0000000000000092L});
    public static final BitSet FOLLOW_ternaryExpression_in_confOption2494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_operatorFlag2517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_input2542 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_IN_in_input2544 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_VAR_in_input2550 = new BitSet(new long[]{0x54004994C0094102L,0x0000000000000092L});
    public static final BitSet FOLLOW_ternaryExpression_in_input2567 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_synpred1_Meteor400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_67_in_synpred1_Meteor404 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_genericOperatorName_in_synpred1_Meteor408 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpression_in_synpred2_Meteor428 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_synpred2_Meteor430 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpression_in_synpred3_Meteor470 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_IF_in_synpred3_Meteor472 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_synpred4_Meteor939 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_synpred4_Meteor941 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_41_in_synpred4_Meteor943 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_generalPathExpression_in_synpred5_Meteor969 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_AS_in_synpred5_Meteor971 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathExpression_in_synpred6_Meteor1014 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_synpred7_Meteor1070 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_synpred7_Meteor1072 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_synpred7_Meteor1074 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathSegment_in_synpred8_Meteor1093 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_synpred9_Meteor1141 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_ID_in_synpred9_Meteor1143 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_synpred9_Meteor1145 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathSegment_in_synpred10_Meteor1164 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathSegment_in_synpred11_Meteor1206 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_synpred12_Meteor1260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_synpred13_Meteor1298 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_synpred14_Meteor1327 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_synpred15_Meteor1350 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_methodCall_in_synpred15_Meteor1352 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred16_Meteor1491 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_40_in_synpred16_Meteor1493 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FN_in_synpred17_Meteor1509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_synpred18_Meteor1543 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_synpred18_Meteor1545 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_VAR_in_synpred18_Meteor1547 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred19_Meteor1568 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_synpred19_Meteor1570 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred20_Meteor1750 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_synpred20_Meteor1752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_synpred21_Meteor1796 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_synpred21_Meteor1798 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_synpred23_Meteor2446 = new BitSet(new long[]{0x0000000000000002L});

}