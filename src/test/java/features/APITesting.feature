@SpacXAPITesting
Feature: SpaceX basic api testing

  @Prim
  Scenario: Validate spaceX api GET call
    #Given will validate URL, and URL return type is valid json
    Given I validate the status of GET call
    And I validate the api response time
    And I validated spaceX mission is success

  @Secondary
  Scenario Outline: Validate flight and cores in the api GET call
    Then I validate api keys and its dataType in the response
    And I validate the array "<entity>" values from the api response

    Examples: 
      | entity   |
      | crew     |
      | ships    |
      | capsules |
      | payloads |
      | cores    |

  @NegativeValidation
  Scenario: Validate api GET call for 404 response
    Given I validate the status for invalid entity
    And I validate the response for invalid http method call
