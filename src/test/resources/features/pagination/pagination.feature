Feature: Pagination functionality
  As a user of ozone site
  I am able to see any search result page

  Background:
    Given the user navigated to the site

  @pagination @all
  Scenario: Pagination is present on search page
    When the user search for 'палатка' from home page
    Then pagination is shown on results page

  @pagination @wip
  Scenario: Pagination allows to change result page
    When the user search for 'горелка' from home page
    And the user click to '4'-th page
    Then current page number is '4'