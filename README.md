# osce-pair-programming

Spring Boot application that retrieves a price for a date.

- `GET /price` retrieve price for current date
- `GET /price/{date}` retrieve price for a given date (format yyyy-mm-dd)

The service originally only had `GET /price/{date}` but recently `GET /price` was also added.

The service was written by a external consultant who parted on bad terms. 

You have been employed by the client to alter some business logic and to advise on any improvements that could be made to the code.

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

They're particularly keen to know about:
- the quality of the code; the company is disputing the cost of the initial service development and would like to know what code quality issues they can use to aid their case.
- how they could make simple changes to the service in future without development costs