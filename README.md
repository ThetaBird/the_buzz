**Routes, parameters, and returns**
# GET
https://cse216-group4-app.herokuapp.com/api/ideas
^ Returns a list of all ideas in the payload

https://cse216-group4-app.herokuapp.com/api/idea/:id
^ Returns an idea JSON in payload, without reactions
Example:
{
    "mStatus":"ok",
    "mData":{
        "ideaId":1,
        "userId":1,
        "timestamp":1645681889349,
        "subject":"Hello",
        "content":"World",
        "attachment":"test",
        "allowedRoles":[1]
    }
}

https://cse216-group4-app.herokuapp.com/api/idea/:id/reactions
^ Returns the likes/dislikes for an idea in payload
Example:
{
    "mStatus":"ok",
    "mData":{
        "ideaId":1,
        "likes":[1,2,3,4],
        "dislikes":[5,6]
    }
}

https://cse216-group4-app.herokuapp.com/api/user/:id
^ Returns a user JSON in payload
Example:
{
    "mStatus":"ok",
    "mData":{
        "userId":1,
        "avatar":"test.png",
        "name":"John Doe",
        "passwordHash":"fewrufhjerfnif",
        "companyRole":1
    }
}

# POST EXAMPLES
curl -s https://cse216-group4-test.herokuapp.com/api/idea/1/reactions -X POST -d "{'ideaId':1,'userId':3,'type':-1}" (types: -1 = Toggle dislike, 1 = Toggle like, 0 = remove either)



curl -s https://cse216-group4-test.herokuapp.com/api/ideas -X POST -d "{'userId':1, 'subject': 'Hello', 'content': 'World', attachment:'test','allowedRoles':[1]}"

curl -s https://cse216-group4-test.herokuapp.com/api/users -X POST -d "{'avatar':'test.png','name':'John Doe','passwordHash':'feefgbvgrvf','companyRole':1}"