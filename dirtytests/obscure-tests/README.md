# Obscure Tests

## Background information

This code is part of a transport information system, which
tracks goods transported by road,
from factories via distribution centres to warehouses and shops.

This test focuses on the transfer of a shipment of
goods (Transport) delivered at a distribution centre. A carrier
(a transport company that operates a number of trucks) needs to be
assigned to the shipment. A carrier will take care of transporting the
shipment to the next stop.

The system keeps track of the shipment (Transport), the owner of the shipment,
and the current carrier. The current carrier can be the owner or a different organisation;
this can change over time, as the shipment is transported from location to location.

The system also keeps track of the whole Process a shipment goes through, from leaving the
factory, through all involved distribution centres to the final destination.
A shipment's process involves multiple tasks, like assigning a new carrier.
Each task has one or more states.

The test in this exercise (CarrierProcessorTest) focuses on assigning a
(new) carrier to a transport, for example when a shipment is at a distribution
centre and will be transported to a different warehouse.

You will notice that a part of the implementation is missing.
We have provided only a small slice of the system, to focus on the dirty test and
only the related production code.

## Exercise

Refactor these tests so that they become clear, focused, and intention revealing.

- Make a diagram of the classes, interfaces and dependencies.
- Which different concerns do the tests try to cover?
- What is the impact of the way mocks are used?
- How can you improve the common setup code?
- What do the tests tell you about the design of the production code and dependencies?
