# Argument Captors

This code is part of a system that generates invoices with discounts. If the total amount on the invoice is more
than € 20000, a 10% discount is applied to the invoice.

The tests are capturing arguments of mock invocations.

## Think about the tests

- Why does this test need to capture arguments?
- What effect does this have on the tests?
- What does this tell about the production code?
- What alternative approach(es) can you think of?
- What else is bothering you in these tests?

## Refactor the tests to improve readability

Start by improving the test in small steps. Tackling the low hanging fruit like good names will make
next smells and possible refactorings more visible.

## One step further

What improvements can you make to the production code so that the test can become more simple?

Think back about the reasons for the argument capture. How can we change the production code to remove these reasons.
