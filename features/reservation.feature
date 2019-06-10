Feature: Reservation

  Background:
    Given a capacity of 10 seats

  Scenario: Make Reservation
    When Pete makes a reservation of 5 seats on 21-06-2019
    Then the reservation is accepted

