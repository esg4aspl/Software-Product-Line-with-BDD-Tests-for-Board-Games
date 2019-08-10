Feature: New Installation


  Scenario: Manually installing the program (uid:bbc88ad4-371c-46f1-b9e3-8cd3f264b2f2)
    Given user has the necessary files
    When the user clicks the install button
    Then the program is installed in user's device
