package com.zemoso.githubtask.assignment.services;

import java.util.MissingFormatArgumentException;
import java.util.Stack;

public class EvalInteger {

    public int calculateInt(String expression) throws Exception {
        char[] chars = expression.toCharArray();

        Stack<Integer> operand = new Stack<Integer>();
        Stack<Character> operator = new Stack<Character>();

        int count_left = 0, count_right = 0, count_operators = 0, count_operands = 0;

        for (int i = 0; i < chars.length; i++)
        {
            // Current chars is a whitespace, skip it
            if (chars[i] == ' ')
                continue;

            // Current chars is a number, push it to stack for numbers
            if ((chars[i] >= '0' && chars[i] <= '9') || chars[i]=='.'|| (chars[i]=='-' && i==0) || (chars[i]=='-' && chars[i-1]=='('))
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
                try
                {
                operand.push(Integer.parseInt(num.toString()));
                }
                catch(NumberFormatException e)
                {
                	throw new Exception("Invalid Data types for Integer");
                }
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
                {
                	if (operand.size()<=1) {
                        throw new
                                MissingFormatArgumentException("Syntax error");
                    }
                
                    operand.push(applyOp(operator.pop(), operand.pop(), operand.pop()));
                }

                // Push current chars to 'operator'.
                operator.push(chars[i]);
            }
        }

        if (count_left != count_right) {
            throw new
                    MissingFormatArgumentException("Missing parenthesis");
        }
        if (count_operators != count_operands-1) {
            throw new
                    MissingFormatArgumentException("Syntax error");
        }

        // Entire expression has been parsed, apply remaining operators
        // on remaining operands in stacks.
        while (!operator.empty()) {
            operand.push(applyOp(operator.pop(), operand.pop(), operand.pop()));
        }

        // Top of 'operand' contains result, return it
        return operand.pop();
    }

    public float calculateFloat(String expression) {
        char[] chars = expression.toCharArray();

        Stack<Float> operand = new Stack<Float>();
        Stack<Character> operator = new Stack<Character>();

        int count_left = 0, count_right = 0, count_operators = 0, count_operands = 0;

        for (int i = 0; i < chars.length; i++)
        {
            // Current chars is a whitespace, skip it
            if (chars[i] == ' ')
                continue;

            // Current chars is a positive number, or a negative number, or even a decimal then push it to stack for numbers
            if ((chars[i] >= '0' && chars[i] <= '9') || chars[i]=='.' || (chars[i]=='-' && i==0) || (chars[i]=='-' && chars[i-1]=='('))
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
                operand.push(Float.parseFloat(num.toString()));
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
                {
                	if (operand.size()<=1) {
                        throw new
                                MissingFormatArgumentException("Syntax error");
                    }
                
                    operand.push(applyOp(operator.pop(), operand.pop(), operand.pop()));
                }

                // Push current chars to 'operator'.
                operator.push(chars[i]);
            }
        }

        if (count_left != count_right) {
            throw new
                    MissingFormatArgumentException("Missing parenthesis");
        }

        if (count_operators != count_operands-1) {
            throw new
                    MissingFormatArgumentException("Syntax error");
        }

        // Entire expression has been parsed, apply remaining operators
        // on remaining operands in stacks.
        while (!operator.empty()) {
            operand.push(applyOp(operator.pop(), operand.pop(), operand.pop()));
        }

        // Top of 'operand' contains result, return it
        return operand.pop();
    }

    public long calculateLong(String expression) throws Exception {
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
            if ((chars[i] >= '0' && chars[i] <= '9') || chars[i]=='.'|| (chars[i]=='-' && i==0) ||(chars[i]=='-' && chars[i-1]=='('))
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
		try {
                	operand.push(Long.parseLong(num.toString()));
		} catch (NumberFormatException e) {
	                throw new Exception("Invalid Data types for Long type");
		}
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
                {
                	
                	if (operand.size()<=1) {
                        throw new
                                MissingFormatArgumentException("Syntax error");
                    }
                    operand.push(applyOp(operator.pop(), operand.pop(), operand.pop()));
                }

                // Push current chars to 'operator'.
                operator.push(chars[i]);
            }
        }

        if (count_left != count_right) {
            throw new
                    MissingFormatArgumentException("Missing parenthesis");
        }
        if (count_operators != count_operands-1) {
            throw new
                    MissingFormatArgumentException("Syntax error");
        }

        // Entire expression has been parsed, apply remaining operators
        // on remaining operands in stacks.
        while (!operator.empty()) {
            operand.push(applyOp(operator.pop(), operand.pop(), operand.pop()));
        }

        // Top of 'operand' contains result, return it
        return operand.pop();
    }

    public double calculateDouble(String expression) {
        char[] chars = expression.toCharArray();

        Stack<Double> operand = new Stack<Double>();
        Stack<Character> operator = new Stack<Character>();

        int count_left = 0, count_right = 0, count_operators = 0, count_operands = 0;

        for (int i = 0; i < chars.length; i++)
        {
            // Current chars is a whitespace, skip it
            if (chars[i] == ' ')
                continue;

            // Current chars is a positive number, or a negative number, or even a decimal then push it to stack for numbers
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
                operand.push(Double.parseDouble(num.toString()));
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
                while (!operator.empty() && precedence(chars[i]) <= precedence(operator.peek())) {
                	if (operand.size()<=1) {
                        throw new
                                MissingFormatArgumentException("Syntax error");
                    }
                    operand.push(applyOp(operator.pop(), operand.pop(), operand.pop()));
                }

                // Push current chars to 'operator'.
                operator.push(chars[i]);
            }
        }

        
        if (count_left != count_right) {
            throw new
                    MissingFormatArgumentException("Missing parenthesis");
        }
        if (count_operators != count_operands-1) {
            throw new
                    MissingFormatArgumentException("Syntax error");
        }

        // Entire expression has been parsed, apply remaining operators
        // on remaining operands in stacks.
        while (!operator.empty()) {
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
            	return 1;
            case '-':
                return 2;
            case '*':
            	return 3;
            case '/':
                return 4;
            case '^':
                return 5;
        }
        return -1;

    }

    // A utility method to apply an operator 'op' on operands 'a'
    // and 'b'. Return the result.
    private int applyOp(char op, int b, int a)
    {
        
    	
        switch (op)
        {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new
                            UnsupportedOperationException("Cannot divide by zero");
                }
                return a / b;
            case '^':
                return (int)Math.pow(a,b);
        }
        return 0;
    }
    private long applyOp(char op, long b, long a)
    {
       
        switch (op)
        {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new
                            UnsupportedOperationException("Cannot divide by zero");
                }
                return a / b;
            case '^':
                return (long)Math.pow(a,b);
        }
        return 0;
    }
    private float applyOp(char op, float b, float a)
    {
       
        switch (op)
        {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new
                            UnsupportedOperationException("Cannot divide by zero");
                }
                return a / b;
            case '^':
                return (float)Math.pow(a,b);
        }
        return 0;
    }
    private double applyOp(char op, double b, double a)
    {
        
        switch (op)
        {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new
                            UnsupportedOperationException("Cannot divide by zero");
                }
                return a / b;
            case '^':
                return (double)Math.pow(a,b);
        }
        return 0;
    }
    // Driver method to test above methods
    public static void main(String[] args)throws Exception
    {
        EvalInteger p = new EvalInteger();
        System.out.println(p.calculateDouble("2.3457756564533+1.22"));
        //System.out.println(gas.evaluateExpression("100 * 2 + 12"));
//	        System.out.println(gas.evaluateExpression("100 * 2 ^ 12"));
//	        System.out.println(gas.evaluateExpression("100 + 2 ^ 3 - 12"));
//	        System.out.println(gas.evaluateExpression("100 * ( 2 + 12 )"));
//	        System.out.println(gas.evaluateExpression("100 * ( 2 + 12 ) / 14"));
    }

}

