package sample;

import java.util.MissingFormatArgumentException;
import java.util.Stack;

public class EvalLong {

	
	

	    public long evaluateExpression(String expression)
	    {
	        char[] chars = expression.toCharArray();

	        Stack<Long> operand = new Stack<Long>();
	        Stack<Character> operator = new Stack<Character>();

	        int count_left = 0, count_right = 0, count_operators = 0, count_operands = 0;

	        for (int i = 0; i < chars.length; i++)
	        {
	            // Current chars is a whitespace, skip it
	            if (chars[i] == ' ')
	                continue;

	            // Current chars is a number, push it to stack for numbers
	            if ((chars[i] >= '0' && chars[i] <= '9') || chars[i]=='.' || (chars[i]=='-' && i==0) || (chars[i]=='-' && chars[i-1]=='(') )
	            {
	                StringBuffer num = new StringBuffer();
	                if((chars[i]=='-' && i==0) || (chars[i]=='-' && chars[i-1]=='(')) 
	                {
	                	num.append('-');
	                	i++;
	                }
	                // There may be more than one digits in number
	                while (i < chars.length && ((chars[i] >= '0' && chars[i] <= '9')|| chars[i]=='.')) {
	                    num.append(chars[i++]);
	                }
	                i--;
	                count_operands++;
	                
	                operand.push(Long.parseLong(num.toString()));
	            }

	            else if (chars[i] == '(') {
	                operator.push(chars[i]);
	                count_left++;
	            }


	            // Closing brace encountered, solve entire brace
	            else if (chars[i] == ')')
	            {
	                count_right++;
	                while (operator.peek() != '(') {
	                    operand.push(applyOp(operator.pop(), operand.pop(), operand.pop()));
	                }
	                operator.pop();
	            }

	            // Current chars is an operator.
	            else if (chars[i] == '+' || chars[i] == '-' || chars[i] == '*' || chars[i] == '/' || chars[i] == '^')
	            {
	                // While top of 'operator' has same or greater precedence to current
	                // chars, which is an operator. Apply operator on top of 'operator'
	                // to top two elements in operand stack
	                count_operators++;
	                while (!operator.empty() && precedence(chars[i]) <= precedence(operator.peek()))
	                    operand.push(applyOp(operator.pop(), operand.pop(), operand.pop()));

	                // Push current chars to 'operator'.
	                operator.push(chars[i]);
	            }
	        }

	        if (count_left != count_right) {
	            throw new
	                    MissingFormatArgumentException("Missing parenthesis");
	        }
	        if (count_operators != count_operands-1) {
	        	System.out.println(count_operands+" "+count_operators);
	            throw new
	                    MissingFormatArgumentException("Syntex error");
	        }

	        // Entire expression has been parsed, apply remaining operators
	        // on remaining operands in stacks.
	        while (!operator.empty()) {
	            System.out.print("here");
	            operand.push(applyOp(operator.pop(), operand.pop(), operand.pop()));
	        }

	        // Top of 'operand' contains result, return it
	        return operand.pop();
	    }
	    

	    // Returns the precedence of each operator,
	    // relative to another.
	    private int precedence(char ch)
	    {
	        switch (ch)
	        {
	            case '+':
	            case '-':
	                return 1;

	            case '*':
	            case '/':
	                return 2;

	            case '^':
	                return 3;
	        }
	        return -1;

	    }

	    // A utility method to apply an operator 'op' on operands 'a'
	    // and 'b'. Return the result.
	    private long applyOp(char op, long b, long a)
	    {
	        System.out.println(a+" "+b );
	        switch (op)
	        {
	            case '+':
	                return a + b;
	            case '-':
	                return a - b;
	            case '*':
	                return a * b;
	            case '/':
	                System.out.println(a+" "+b);
	                if (b == 0) {
	                    System.out.println("here");

	                    throw new
	                            UnsupportedOperationException("Cannot divide by zero");
	                }

	                return a / b;
	            case '^':
	                return (long)Math.pow(a,b);
	        }
	        return 0;
	    }

	    // Driver method to test above methods
	    public static void main(String[] args)
	    {
	        EvalLong p = new EvalLong();
	        System.out.println(p.evaluateExpression("234.556678974411234+678766555667"));
	        //System.out.println(gas.evaluateExpression("100 * 2 + 12"));
//	        System.out.println(gas.evaluateExpression("100 * 2 ^ 12"));
//	        System.out.println(gas.evaluateExpression("100 + 2 ^ 3 - 12"));
//	        System.out.println(gas.evaluateExpression("100 * ( 2 + 12 )"));
//	        System.out.println(gas.evaluateExpression("100 * ( 2 + 12 ) / 14"));
	    }

	}

