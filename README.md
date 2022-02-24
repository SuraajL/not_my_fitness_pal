
# NotMyFitnessPal

Counting your Calories without judgement.

By Aaron, Marcy, Nasir, Sarina and Suraaj.










## Project Description

A back-end food tracking API project using Java, Spring and PostgreSQL database.

Our database consits of two tables; people and food entries
## Setup Instructions

    1. Clone the Repo: git clone git@github.com:Nasir-6/not_my_fitness_pal.git
    2. Open IntelliJ
    3. Refer to Database setup queries.md
## Usage


#### JSON Object Formats

```http
Person:
  { "id": 1, "name": "Bob", "age": 34, "height_in_cm": 177.0, 
  "weight_in_kg": 78.0, "calorie_target": 3000 }

Food_Entry:
  { "id": 20, "person_id": 5, "name": "avocado on toast", 
    "mealType": "BREAKFAST", "notes": "sliced avocado on brown bread", 
    "calories": 200, "week": 1, "day": "TUESDAY" }
```
#### Table of HTTP Request Paths

localhost:8080/ ...

| HTTP Request Path                                                        | Request Type | Description                                      |
|:-------------------------------------------------------------------------|:-------------|:-------------------------------------------------|
| `.../getAllFood `                                                        | `GET`        | Get All Food Entries                             |
| `.../food/{id} `                                                         | `GET`        | Get Food Entries by Food Id                      |
| `.../food/person/{id} `                                                  | `GET`        | Get Food Entries By Person's Id                  |
| `.../food/person/{id}/week/{week} `                                      | `GET`        | Get Food Entries By Person's Id and Week         |
| `.../food/person/{id}/week/{week}/day/{day} `                            | `GET`        | Get Food Entries By Person's Id and Week and Day |
| `...food/person/mealtype/{mealtype} `                                    | `GET`        | Get Food Entries By Meal Type                    |
| `.../food `                                                              | `POST`       | Add Food Entry                                   |
| `.../food /{id}`                                                         | `PUT`        | Update Food Entry By Food Id                     |
| `.../food/{id} `                                                         | `DELETE`     | Delete Food Entry                                |
| `.../people `                                                            | `GET`        | Get All People                                   |
| `.../people/{id} `                                                       | `GET`        | Get Person By Person's Id                        |
| `.../person `                                                            | `POST`       | Add Person                                       |
| `.../person/{id} `                                                       | `DELETE`     | Delete Person By Person's Id                     |
| `.../person/{id} `                                                       | `PUT`        | Update Person By Person's Id                     |
| `.../food/calorie_goals/week/{week}/day/{day} `                          | `GET`        | Get Daily Calories Goal By Week and Day          | **Stretch Goal**


**Note: Last HTTP Request is a Stretch Goal**



## Acknowledgements

- [Aaron](https://github.com/Aaron-Nazareth)
- [Marcy](https://github.com/mycp98)
- [Nasir](https://github.com/Nasir-6)
- [Sarina](https://github.com/sarinajsal)
- [Suraaj](https://github.com/SuraajL)



