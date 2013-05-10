/* 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS 
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR 
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN 
 * AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION 
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package crashburn.reg;

public enum GradeLevel {
	K("K"),
	L1("1"),
	L2("2"),
	L3("3"),
	L4("4"),
	L5("5"),
	L6("6"),
	L7("7"),
	L8("8"),
	L9("9"),
	L10("10"),
	L11("11"),
	L12("12");

	private String displayValue;

	GradeLevel(String aDisplayValue) {
		displayValue = aDisplayValue;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public boolean isInRange(GradeLevel min, GradeLevel max) {
		if(min == null || max == null ) {
			return false;
		}
		return ( (min.compareTo(this) <= 0) && (max.compareTo(this) >= 0) );
	}

}