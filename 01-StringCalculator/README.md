StringCalculator kata
=====================

Kata Steps:
-----------

1. Create a String calculator with a method int Add(string numbers) 
---
<ol type="a">
  <li>The method can take 0, 1, or 2 numbers and will return their sum.</li>
  <li>Example inputs: "", "1", or "1,2"</li>
  <li>Start with the simplest test case of an empty string. Then 1 number. Then 2 numbers.</li>
  <li>Remember to refactor after each passing test.</li>
</ol>

2. Allow the Add method to handle an unknown number of arguments/numbers.
---
3. Allow the Add method to handle new lines between numbers (instead of commas). 
---
<ol type="a">

  <li>Example: “1\n2,3” should return 6.</li> 
  <li>Example: “1,\n” is invalid, but you don’t need a test for this case. 
  <li>Only test correct inputs – there is no need to deal with invalid inputs for this kata.</li> 
</ol>

4. Allow the Add method to handle a different delimiter: 
---
<ol type="a">
 <li>To change the delimiter, the beginning of the string will contain a separate line that looks like this: "//[delimiter]\n[numbers]"</li> 
 <li>Example: "//;\n1;2" should return 3 (the delimiter is ;)<li> 
 <li>This first line is optional; all existing scenarios (using , or \n) should work as before.</li>
</ol> 

5. Calling Add with a negative number will throw an exception "Negatives not allowed: "listing all negative numbers that were in the list of numbers.
---
<ol type="a"> 
 <li>Example “-1,2” throws "Negatives not allowed: -1"</li> 
 <li>Example “2,-4,3,-5” throws "Negatives not allowed: -4,-5"</li>
</ol> 

6. Numbers bigger than 1000 should be ignored.
---
<ol type="a">
 <li>Example: “1001,2” returns 2</li>
</ol> 
