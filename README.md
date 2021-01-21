# Voting system

Program for deciding where to have lunch.

    2 types of users: admin and regular users
    Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
    Menu changes each day (admins do the updates)
    Users can vote on which restaurant they want to have lunch at
    Only one vote counted per user
    If user votes again the same day:
        If it is before 11:00 we assume that he changed his mind.
        If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

### Business model description

Business model of voting includes 4 classes:
        
        User
        Restaurant
        Dish
        Vote
        
There isn't any special menu-entities, the system use a list of dish for each restaurant by date instead.  
Between Vote and User entities is a one-to-many relationship. 
Between Vote and Restaurant entities is a one-to-many relationship. 
Between Dish and Restaurant entities is a one-to-many relationship.  

### API Documentation

#### AdminRestController
This class contains rest API methods for administrators to manage user's profiles. 
    
    getAll() - get all users
    
    get(int id) - get user by id
    
    getWithVotes(int id) - get user with votes by id
    
    createWithLocation(User user) - create new user
    
    delete(id) - delete user by id
    
    update(id) - update user by id
    
    getByMail(String email) - find user by email

#### ProfileRestController
This class contains rest API methods for users to manage their accounts.

    get() - get current user
    
    delete() - delete current user
    
    update() - udate current user

#### VoteRestController
This class contains API for users to manage their votes.
API doesn't contain any delete methods because users shouldn't delete their votes.

    getAll() - gets all votes of authorized user
    
    getByDate(LocalDate date) - gets authorized user's vote for the date
    
    getTodayVote() - gets authorized user's vote for the current date
    
    create(Vote vote) - create new vote for authorized user 
    
    update(LocalDate date) - update authorized user's vote for date. 

#### DishAdminRestController
This class contains rest API methods for administrators to creating and updating menu for restaurants.
      
     create(Dish dish, int restId)  - method create a new dish for a restaurant with id = restId      
        `curl  -s -X POST -d '{"name": "createdDish", "date": "2021-01-15", "rest": null, "price": 200500}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/restvote/rest/admin/rests/100003/dishes`
       
     delete(int id, int restId) - delete dish with id for restaurant restId
      
     update(Dish dish, int id, int restId) - udate dish with id for restaurant restId
       
#### DishRestController
This class contains rest API methods for users to study restaurant's menus.

    get(int id, int restId) - get dish with id for restaurant restId
    
    getAllByDate(LocalDate date, int restId) - get all dishes for restaurant restId by date

#### RestaurantAdminRestController
This class contains rest API methods for administrators to creating and updating restaurants.

    delete(int id) - delete restaurant by id
    
    create(Restaurant rest) - create new restaurant
    
    update(Restaurant rest, int id) - update restaurant with id

#### RestaurantRestController
This class contains rest API methods for users to study restaurant's menus and to count restaurant's votes.    

     get(int id) - get restaurant by id
     
     getAll() - get all restaurants
     
     getAllWithDishesByDate(LocalDate date) - get all restaurants with dishes by date
     
     getWithDishesByDate(int id, LocalDate date) - get restaurant by id with dishes by date
     
     getAllWithVotesByDate(LocalDate date) - get all restaurants with votes by date 
        `curl -s -X GET http://localhost:8080/restvote/rest/profile/rests/with-votes?date=2020-01-31`