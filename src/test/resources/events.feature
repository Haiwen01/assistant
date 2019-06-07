Feature: event records

  Scenario Outline: record my event
    Given I input event content with format "<event>"
    When I search the event happen at date "<date>"
    Then I should get the event content "<result>"
    Examples:
      | event | date | result |
      | 20190601;test | 20190601 | 2019-06-01 00:00:00;test |



    # alert
    # rules
    # history

