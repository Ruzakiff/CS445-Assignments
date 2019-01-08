package cs445.a2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

/**
 * This class uses two stacks to evaluate an infix arithmetic expression from an
 * InputStream. It should not create a full postfix expression along the way; it
 * should convert and evaluate in a pipelined fashion, in a single pass.
 */
public class InfixExpressionEvaluator {
    // Tokenizer to break up our input into tokens
    StreamTokenizer tokenizer;

    // Stacks for operators (for converting to postfix) and operands (for
    // evaluating)
    StackInterface<Character> operatorStack;
    StackInterface<Double> operandStack;
    //what if - and parenthesis
    char[] mdas={'^','*','/','+','-','(','{',')','}'};
    boolean adjacent=false;

    /**
     * Initializes the evaluator to read an infix expression from an input
     * stream.
     * @param input the input stream from which to read the expression
     */
    public InfixExpressionEvaluator(InputStream input) {
        // Initialize the tokenizer to read from the given InputStream
        tokenizer = new StreamTokenizer(new BufferedReader(
                        new InputStreamReader(input)));

        // StreamTokenizer likes to consider - and / to have special meaning.
        // Tell it that these are regular characters, so that they can be parsed
        // as operators
        tokenizer.ordinaryChar('-');
        tokenizer.ordinaryChar('/');

        // Allow the tokenizer to recognize end-of-line, which marks the end of
        // the expression
        tokenizer.eolIsSignificant(true);

        // Initialize the stacks
        operatorStack = new ArrayStack<Character>();
        operandStack = new ArrayStack<Double>();
    }

    /**
     * Parses and evaluates the expression read from the provided input stream,
     * then returns the resulting value
     * @return the value of the infix expression that was parsed
     */
    public Double evaluate() throws InvalidExpressionException {
        // Get the first token. If an IO exception occurs, replace it with a
        // runtime exception, causing an immediate crash.
        try {
            tokenizer.nextToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Continue processing tokens until we find end-of-line
        while (tokenizer.ttype != StreamTokenizer.TT_EOL) {
            // Consider possible token types
            switch (tokenizer.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    // If the token is a number, process it as a double-valued
                    // operand
                	adjacent=false;
                    handleOperand((double)tokenizer.nval);
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                case '^':
                	if(adjacent){
                		throw new InvalidExpressionException("You Cannot Have Adjacent Operators");
                	}
                	adjacent=true;
                    // If the token is any of the above characters, process it
                    // is an operator
                    handleOperator((char)tokenizer.ttype);
                    break;
                case '(':
                case '{':
                    // If the token is open bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly.
                    handleOpenBracket((char)tokenizer.ttype);
                    break;
                case ')':
                case '}':
                    // If the token is close bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly.
                    handleCloseBracket((char)tokenizer.ttype);
                    break;
                case StreamTokenizer.TT_WORD:
                    // If the token is a "word", throw an expression error
                    throw new InvalidExpressionException("Unrecognized symbol: " +
                                    tokenizer.sval);
                default:
                    // If the token is any other type or value, throw an
                    // expression error
                    throw new InvalidExpressionException("Unrecognized symbol: " +
                                    String.valueOf((char)tokenizer.ttype));
            }

            // Read the next token, again converting any potential IO exception
            try {
                tokenizer.nextToken();
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Almost done now, but we may have to process remaining operators in
        // the operators stack
        handleRemainingOperators();

        // Return the result of the evaluation
        // TODO: Fix this return statement
        if(operandStack.isEmpty()){
        	throw new InvalidExpressionException("You Need Atleast One Operand");
        }
        else{
        	return operandStack.pop();
        }
    }

    /**
     * This method is called when the evaluator encounters an operand. It
     * manipulates operatorStack and/or operandStack to process the operand
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     * @param operand the operand token that was encountered
     */
    void handleOperand(double operand) {
        // TODO: Complete this method
    	operandStack.push(operand);
    }

    /**
     * This method is called when the evaluator encounters an operator. It
     * manipulates operatorStack and/or operandStack to process the operator
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     * @param operator the operator token that was encountered
     */
    void handleOperator(char operator) {
    	if(operatorStack.isEmpty()){
    		operatorStack.push(operator);
    	}
    	else{
    		//isGreater(operatorStack.peek(),operator);
    		//if(operatorStack.peek()=='('||operatorStack.peek()=='{'){
    			//continue;
    		//}
    		//else{
    			while(!operatorStack.isEmpty()&&!(isGreater(operatorStack.peek(),operator))){
    				//System.out.println("stuck");
    				Calculate(operatorStack.pop());
        		
    			}
    		//}
    		
    		operatorStack.push(operator);
    	}
    	
        // TODO: Complete this method
    }
    private boolean isGreater(char current,char next){
    	//System.out.println(current);
    	//System.out.println(next);
    	if(getIndex(next)>=5){
			return false;
		}
    	if(getIndex(next)<getIndex(current)){
    		if(getIndex(next)==0){
    		//	System.out.println("^ is greater");
    			return true;
    		}
    		
    		if((getIndex(next)==1||getIndex(next)==3)){
    			if(getIndex(next)<getIndex(current)-1){
    				//System.out.println("*");
        			return true;
    			}
    		}
    		else{//if index 2
    			//System.out.println("/");
    			return true;
    			
    		}
    	}
    	else{
    		//System.out.println("+");
    	}
    	//System.out.println("a");
    	return false;
    }
    
    private int getIndex(char a){
    	for(int i=0;i<mdas.length;i++){
    		if(mdas[i]==a){
    			return i;
    		}
    	}
    	return -1;
    }
    private void Calculate(char operator){

    	if(operator!='('||operator!='{'||operator!=')'||operator!='}'){
    		
    	  	double num1=operandStack.pop();
    	  	double num2;
    	  	if(!operandStack.isEmpty()){
    	  		num2=operandStack.pop();
    		}
    	  	else{
    	  		throw new InvalidExpressionException("You Have Mismatched Operands and Operators");
    	  		//return;
    	  	}
        	
        	double result=1;
        	switch(operator){
        		case '^':
        			result=Math.pow(num2, num1);
        		break;
        		case '*':
        			result=num2*num1;
            	break;
        		case '/':
        			if(num1==0){
        				throw new InvalidExpressionException("You Cannot Divide By 0");
        			}
        			result=num2/num1;
            	break;
        		case '+':
        			result=num2+num1;
            	break;
        		case '-':
        			result=num2-num1;
            	break;
        	}
        	//System.out.println(result);
        	operandStack.push(result);
    	}
    	
    }
    /**
     * This method is called when the evaluator encounters an open bracket. It
     * manipulates operatorStack and/or operandStack to process the open bracket
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     * @param openBracket the open bracket token that was encountered
     */
    void handleOpenBracket(char openBracket) {
    	//System.out.println("asf");
    	operatorStack.push(openBracket);
    	//operatorStack.push(openBracket);
        // TODO: Complete this method
    }

    /**
     * This method is called when the evaluator encounters a close bracket. It
     * manipulates operatorStack and/or operandStack to process the close
     * bracket according to the Infix-to-Postfix and Postfix-evaluation
     * algorithms.
     * @param closeBracket the close bracket token that was encountered
     */
    void handleCloseBracket(char closeBracket) {
    	//System.out.println("handling");
    	boolean open=false;
    	while(!operatorStack.isEmpty()&&!open){
    		//System.out.println("opening");
    		if(operandStack.isEmpty()){
    			throw new InvalidExpressionException("No Expression Within Parenthesis");
    		}
    		
    		if(operatorStack.peek()=='{'&&closeBracket=='}'||operatorStack.peek()=='('&&closeBracket==')'){
    			//System.out.println("opening");
    			open=true;
    			operatorStack.pop();
    			return;
    		}
    		else if(operatorStack.peek()=='{'&&closeBracket==')'||operatorStack.peek()=='('&&closeBracket=='}'){
    			throw new InvalidExpressionException("Mismatched Parenthesis");
    		
    		}
    		Calculate(operatorStack.pop());
    	}
    	if(!open&&operatorStack.isEmpty()){
            //throw error. didn't find any open.
            throw new InvalidExpressionException("Found Close Parenthesis But No Open");
        }
    	//operatorStack.push(closeBracket);
    	//while(operatorStack.peek()!='{'){
        // TODO: Complete this method
    }

    /**
     * This method is called when the evaluator encounters the end of an
     * expression. It manipulates operatorStack and/or operandStack to process
     * the operators that remain on the stack, according to the Infix-to-Postfix
     * and Postfix-evaluation algorithms.
     */
    void handleRemainingOperators() {
    	while(!operatorStack.isEmpty()){
    		Calculate(operatorStack.pop());
    	}
    	//System.out.println(operandStack.peek());
        // TODO: Complete this method
    }


    /**
     * Creates an InfixExpressionEvaluator object to read from System.in, then
     * evaluates its input and prints the result.
     * @param args not used
     */
    public static void main(String[] args) {
        System.out.println("Infix expression:");
        InfixExpressionEvaluator evaluator =
                        new InfixExpressionEvaluator(System.in);
        Double value = null;
        try {
            value = evaluator.evaluate();
        } catch (InvalidExpressionException e) {
            System.out.println("Invalid expression: " + e.getMessage());
        }
        if (value != null) {
            System.out.println(value);
        }
    }
    //do method that pops operator and 2 operands off stacks and does calculation
    //what if no closing parenthesis?
    
    
}