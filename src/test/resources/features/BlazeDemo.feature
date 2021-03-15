#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
@TravelAgency
Feature: Blaze Travel Agency

  @Primary
  Scenario: Validate if user able to book flight ticket successfully
    Given I launch the BlazeTravelAgency url
    And I verify welcome page components
    Then I select "Boston" , "Rome" and click on findFlights button
    Then I wait for "ChooseFlights" page to load
    Then I validate flights table header and values are not null
    And I select "Virgin America" by clicking on ChooseThisFlight button
    Then I wait for "passengerDetails" page to load
    And I click on PurchaseFlight button with the default passenger details
    Then I validate whether ticket booking is successful
    
    #Examples:
    #|departureCity|destinationCity|airline|
    #|Boston|Rome|Virgin America|

  #@Secondary
  #Scenario Outline: Validate if flights available for all possible source and destination
    #Given I launch the BlazeTravelAgency url
    #And I verify welcome page components
    #Then I select "<departureCity>" and "<destinationCity>"
    #And I click on findFlights button
    #Then I wait for ChooseFlights page to load
    #Then I validate flights table header and values are not null
    #
    #Examples:
    #|departureCity|destinationCity|
    #|Paris|Rome|
#
  #@Secondary
  #Scenario Outline: Validate if user able to enter values in Passenger details page
    #Given I launch the BlazeTravelAgency url
    #And I verify welcome page components
    #Then I select "<departureCity>" and "<destinationCity>"
    #And I click on findFlights button
    #Then I wait for ChooseFlights page to load
    #Then I validate flights table header and values are not null
    #And I select "<airlines> by clicking on ChooseThisFlight button
    #Then I wait for passenger details page to load
    #And I click on PurchaseFlight button with the default passenger details
    #Then I validate whether ticket booking is successful
    #And I validate reserved ticket details table
    #
    #Examples:
    #|departureCity|destinationCity|airlines|
    #|Paris|Rome|United Airlines|
