Feature: event records

  Scenario Outline: record my event
    Given I input event content with format "<event>"
    When I search the event happen at date "<date>"
    Then I should get the event content "<result>"
    Examples:
      | event | date | result |
      | 20190601;test | 20190601 | 2019-06-01 00:00:00;test |


  Scenario Outline: alert me the event
    When Time arrive at date "<date>"
    Then I should get the event alert message "<alertMessage>"
    Examples:
      | date | alertMessage |
      | 2019-06-01 00:00:00 | 2019-06-01 00:00:00;test |

    # rules
    # history

