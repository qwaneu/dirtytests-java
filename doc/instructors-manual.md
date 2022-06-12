# Instructors manual

## Argument Captors

Smells & dirtyness:

- captors add noise to the test, reduce glanceabiltiy a lot; they also
  come with duplication, because for each test case you have to repeat
  some of the captor overhead
- captors are used when it is inconvenient or not possible to verify an
  argument; in this case there are two things getting in the way:
    - the argument object is large and nested, so it is a pain in the
      *** to construct
    - the object under test generates timestamps and unique ids, which 
      we cannot match on

Approach:

- Introduce a custom Hamcrest matcher; customer matchers are quite easy
  to write; this pays off a lot in readability of the test; sometimes
  you can reuse the matcher in multiple test cases
- Encapsulate the timestamp and id concerns in separate classes, and
  refactor the object under test so that these can be injected. This 
  allows the test to make timestamp and id generations predictable.

## Obscure tests

Smells & dirtyness:

- Production code is an unclear mix of data objects and 'services'; it
  is unclear which responsibility lies where, and the tests reflect
  this.
- Tests are not clear in their intent
- The test share a complicated piece of setup code (initMocks)

Approach:

- remove the boolean stateChangeAllowed from initMocks (move stubbing to
  the callers)
- move mock creation to the field declaration so that all mocks are
  created in one place, in a consistent way
- move fields: make distinction clear between object-under-test,
  collaborators and objects/data being passed
- introduce builders (or constructors) to build the data objects
- assignmentCarrierTask, transportOrganisation can be actual objects instead of 
  mocks?
- move the wiring of carrierProcessor to a new @Before method
- building the assignCarrierRequest can be moved (duplicated) to the
  different tests; after this, the carrierOrgId argument of initMocks
  can be removed
- rename the test cases to a When/Then phrase


## Lucene test

Smells & dirtyness:

- Long, complicated tests

Approach:

- Use builders to reduce/hide the complicated setup code
- Extract methods and matchers to make the tests shorter & more explicit
- ...


## Misplaced mocks

Smells & dirtyness:

- A test with an intimidating amount of 'mocks'; it actually is a mix of
  real mocking (expectations about interactions) and stubbing (returning
  canned answers). This obfuscates the intent of the test; furthermore,
  it is not clear which asserts/verifies are relevant.

Approach:

- pull out/group constructor of object-under-test and the collaborators
- replace User by actual object
- replace Email by actual object? If you use the real EmailFactory, the 
  test does not need to know the Email object
- error behaviour cannot be stubbed on Email any more; stub this on 
  Mailer
- Note: testNotificationFails needs Mailer to fail the first time (failing
  email to user) and succeed the second time (email to service desk); use
  specific argument matching or something like:
  doThrow(new SendEmailFailed...).doNothing().when(mailer).send(any(), any(), any(), any())
