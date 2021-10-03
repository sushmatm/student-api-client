Feature: Test student profile

  Background:

  Scenario Outline: Add a new student
    When new student with <studentId> , '<firstName>' ,'<className>' '<lastName>' ,'<nationality>'
    Then student should be added and return a success <statuscode>
    Examples:
      | studentId | firstName | lastName | nationality | className | statuscode | message |
      | 100       | sushma1    | tm1       | indian1      | 7A        | 201        |         |

  Scenario Outline: Update the Student info
    When  For given studentID className to be updated is '<className>' , with exisitng values '<firstName>' '<lastName>' '<nationality>'
    Then student data should be updated and return a success status <statuscode>
    Examples:
      | studentId | firstName | lastName | nationality | className | statuscode | message |
      | 9         | sushma    | tm       | indian      | 10B       | 202        |         |

  Scenario Outline: Get Student Info by passing Student ID
    When Student ID  is <studentId>
    Then get all the student info for <studentId>
    Examples:
      | studentId | firstName | lastName | nationality | className | statuscode | message |
      | 1         | sushma    | tm       | indian      | 4 A       | 200        |         |

  Scenario Outline: Get Student Info by passing Student ClassName
    When Student ClassName  is '<className>'
    Then get all the student info for '<className>'
    Examples:
      | studentId | firstName | lastName | nationality | className | statuscode | message |
      | 1         | sushma    | tm       | indian      | 2A        | 200        |         |

  Scenario Outline: Delete Student by passing Student ID
    When  Student to delete is <studentId>
    Then  delete successful and return a success <statuscode>
    Examples:
      | studentId | firstName | lastName | nationality | className | statuscode | message |
      | 1         | sushma    | tm       | indian      | 4 A       | 200        |         |


