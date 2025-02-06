Feature: Bokningssystem
  Scenario: Hämta alla bokningar
    Given det finns bokningar i systemet
    When användaren hämtar alla bokningar via API
    Then ska systemet returnera en lista av bokningar
