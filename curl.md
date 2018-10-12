### CURL COMMAND TO GET DATA FOR VOITING AND VOTE

#### Get votes
`curl -s http://localhost:8080/rest/votes --user admin@gmail.com:admin`

#### Get votes by date
`curl -s http://localhost:8080/rest/votes/getVotesByDate?date=2018-05-05 --user admin@gmail.com:admin`

#### Create vote for restaurant 100004
`curl -s -X POST http://localhost:8080/rest/votes?restaurantId=100004 --user user1@yandex.ru:first_user`

#### Update vote for restaurant 100004
`curl -s -X PUT http://localhost:8080/rest/votes/restaurantId/100004 --user admin@gmail.com:admin`
***

### OPTIONAL
#### FOR RESTAURANTS
#### Get all restaurants
`curl -s http://localhost:8080/rest/admin/restaurants --user admin@gmail.com:admin`

#### Get restaurant 100003
`curl -s http://localhost:8080/rest/admin/restaurants/100003 --user admin@gmail.com:admin`

#### Create restaurant
`curl -s -X POST -d '{"restaurantName":"NewRestaurant","address":"NewAddress"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/restaurants --user admin@gmail.com:admin`

#### Update restaurant 100003
`curl -s -X PUT -d '{"restaurantName":"UpdatedRestaurant","address":"UpdatedAddress"}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/restaurants/100003 --user admin@gmail.com:admin`

#### Delete restaurant 100003
`curl -s -X DELETE http://localhost:8080/rest/admin/restaurants/100003 --user admin@gmail.com:admin`

#### Validate restautants with Error
`curl -s -X POST -d '{}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/restaurants --user admin@gmail.com:admin`

`curl -s -X PUT -d '{"restaurantName":"UpdatedRestaurant"}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/restaurants/100003 --user admin@gmail.com:admin`
***

#### FOR ADMIN
#### Get all users
`curl -s http://localhost:8080/rest/admin/users --user admin@gmail.com:admin`

#### Get user 100001
`curl -s http://localhost:8080/rest/admin/users/100001 --user admin@gmail.com:admin`

#### Create user
`curl -s -X POST -d '{"name":"NewUser","email":"new@email","password":"NewPassword"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/users --user admin@gmail.com:admin`

#### Update user 100001
`curl -s -X PUT -d '{"name":"UpdatedUser","email":"updated@email","password":"UpdatedPassword"}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/users/100001 --user admin@gmail.com:admin`

#### Delete user 100001
`curl -s -X DELETE http://localhost:8080/rest/admin/users/100001 --user admin@gmail.com:admin`

#### Validate users with Error
`curl -s -X POST -d '{}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/users --user admin@gmail.com:admin`

`curl -s -X PUT -d '{"name":"","email":"","password":""}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/users/100001 --user admin@gmail.com:admin`
***

#### FOR DISHES
#### Get dishes (menu) for restaurant 100004
`curl -s http://localhost:8080/rest/dishes?restaurantId=100004 --user user1@yandex.ru:first_user`

#### Get dishes by date for restaurant 100004
`curl -s http://localhost:8080/rest/dishes/getDishesByDate?date=2018-05-05&restaurantId=100004 --user admin@gmail.com:admin`

#### Get dish 100015
`curl -s http://localhost:8080/rest/dishes/100015 --user admin@gmail.com:admin`

#### Create & Update dish
- _**Update the restaurant to eliminate the problem with UTF-8**_

`curl -s -X PUT -d '{"restaurantName":"Canteen","address":"Kolosa, 17"}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/restaurants/100004 --user admin@gmail.com:admin`
- _**Update the dish**_

`curl -s -X PUT -d '{"description":"UpdatedDish","cost":1.5,"restaurant":{"id":100004,"restaurantName":"Canteen","address":"Kolosa, 17"},"menuDate":"2018-05-05"}' -H 'Content-Type: application/json' http://localhost:8080/rest/dishes/100015 --user admin@gmail.com:admin`
- _**Create new dish**_

`curl -s -X POST -d '{"description":"NewDish","cost":2.5,"restaurant":{"id":100004,"restaurantName":"Canteen","address":"Kolosa, 17"},"menuDate":"2018-05-05"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/dishes --user admin@gmail.com:admin`

#### Delete dish 100015
`curl -s -X DELETE http://localhost:8080/rest/dishes/100015 --user admin@gmail.com:admin`

#### Validate dish with Error
`curl -s -X PUT -d '{}' -H 'Content-Type: application/json' http://localhost:8080/rest/dishes/100015 --user admin@gmail.com:admin`

`curl -s -X POST -d '{"description":"","cost":-2.5,"restaurant":{"id":100004,"restaurantName":"Canteen","address":"Kolosa, 17"},"menuDate":"2018-05-05"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/dishes --user admin@gmail.com:admin`
