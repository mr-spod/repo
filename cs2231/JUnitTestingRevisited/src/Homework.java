
public class Homework {

	/**
	 * Evaluates an expression and returns its value.
	 * 
	 * @param source
	 *            the {@code StringBuilder} that starts with an expr string
	 * @return value of the expression
	 * @updates source
	 * @requires <pre>
	 * [an expr string is a proper prefix of source, and the longest
	 * such, s, concatenated with the character following s, is not a prefix
	 * of any expr string]
	 * </pre>
	 * @ensures <pre>
	 * valueOfExpr =
	 *   [value of longest expr string at start of #source]  and
	 * #source = [longest expr string at start of #source] * source
	 * </pre>
	 */
	public static int valueOfExpr(StringBuilder source) {
		int answer = valueOfTerm(source);
		while (source.charAt(0) == '+' || source.charAt(0) == '-') {
			StringBuilder operator = source.deleteCharAt(0);
			int nextTerm = valueOfTerm(source);
			if (operator.charAt(0) == '-') {
				nextTerm *= -1;
			}
			answer += nextTerm;
		}
		return answer;
	}
	 
	/**
	 * Evaluates a term and returns its value.
	 * 
	 * @param source
	 *            the {@code StringBuilder} that starts with a term string
	 * @return value of the term
	 * @updates source
	 * @requires <pre>
	 * [a term string is a proper prefix of source, and the longest
	 * such, s, concatenated with the character following s, is not a prefix
	 * of any term string]
	 * </pre>
	 * @ensures <pre>
	 * valueOfTerm =
	 *   [value of longest term string at start of #source]  and
	 * #source = [longest term string at start of #source] * source
	 * </pre>
	 */
	private static int valueOfTerm(StringBuilder source) {
		int answer = valueOfFactor(source);
		while (source.charAt(0) == '*' || source.charAt(0) == '/') {
			StringBuilder operator = source.deleteCharAt(0);
			int nextFactor = valueOfFactor(source);
			if (operator.charAt(0) == '*') {
				answer *= nextFactor;
			} else {
				answer /= nextFactor;
			}
		}
		return answer;
	}
	 
	/**
	 * Evaluates a factor and returns its value.
	 * 
	 * @param source
	 *            the {@code StringBuilder} that starts with a factor string
	 * @return value of the factor
	 * @updates source
	 * @requires <pre>
	 * [a factor string is a proper prefix of source, and the longest
	 * such, s, concatenated with the character following s, is not a prefix
	 * of any factor string]
	 * </pre>
	 * @ensures <pre>
	 * valueOfFactor =
	 *   [value of longest factor string at start of #source]  and
	 * #source = [longest factor string at start of #source] * source
	 * </pre>
	 */
	private static int valueOfFactor(StringBuilder source) {
		int answer;
		if (source.charAt(0) == '(') {
			source.deleteCharAt(0);
			answer = valueOfExpr(source);
		} else {
			answer = valueOfDigitSeq(source);
		}
		return answer;
	}
	 
	/**
	 * Evaluates a digit sequence and returns its value.
	 * 
	 * @param source
	 *            the {@code StringBuilder} that starts with a digit-seq string
	 * @return value of the digit sequence
	 * @updates source
	 * @requires <pre>
	 * [a digit-seq string is a proper prefix of source, which
	 * contains a character that is not a digit]
	 * </pre>
	 * @ensures <pre>
	 * valueOfDigitSeq =
	 *   [value of longest digit-seq string at start of #source]  and
	 * #source = [longest digit-seq string at start of #source] * source
	 * </pre>
	 */
	private static int valueOfDigitSeq(StringBuilder source) {
		int answer = valueOfDigit(source);
		while (Character.isDigit(source.charAt(0))) {
			answer *= 10;
			answer += valueOfDigit(source);
		}
		return answer;
	}
	 
	/**
	 * Evaluates a digit and returns its value.
	 * 
	 * @param source
	 *            the {@code StringBuilder} that starts with a digit
	 * @return value of the digit
	 * @updates source
	 * @requires 1 < |source|  and  [the first character of source is a digit]
	 * @ensures <pre>
	 * valueOfDigit = [value of the digit at the start of #source]  and
	 * #source = [digit string at start of #source] * source
	 * </pre>
	 */
	private static int valueOfDigit(StringBuilder source) {
		StringBuilder digit = source.deleteCharAt(0);
		int dig = Character.digit(digit.charAt(0), 10);
		return dig;
	}
}
