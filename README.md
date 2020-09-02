# osce-pair-programming

This Spring Boot application that retrieves a price for a date.

- `GET /price` retrieve price for current date
- `GET /price/{date}` retrieve price for a given date (format yyyy-mm-dd)

The service originally only had `GET /price/{date}` but recently `GET /price` was added.

You have been employed by the client to make some new changes to this service.

### Requirement 1

The client has asked for the following rate change to be added to the service:

- From date: 2020-07-01
- To date: 2099-12-31
- Rate: 0.87

They've asked that appropriate tests are added to ensure the new rate entries are correct. Any existing date entries will need to be adjusted to cater for this new entry.

### Requirement 2

In order to get the JIRA-112 changes live quickly a assertion in `testFindPriceMethod` had to be commented out. Uncomment the line and see why it might be failing. If you can't fix comment out again.

### Requirement 3

There have been complaints that the service occasionally gives the wrong price. No firm examples have been provided yet but this seems to happen most often at the end of the month.
Whilst the code is being looked at to add new prices the client has asked you to see if you can replicate the issue and provide a fix.

### Requirement 4

The client has asked you to provide feedback on the maintainability of the current code as they may be able to secure funding for any refactoring that might be beneficial. 