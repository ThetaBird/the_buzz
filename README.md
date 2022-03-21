# The Buzz
The Buzz is our company's social media platform where employees of the company can post ideas and receive/give feedback on those ideas.

# Phase 1
The Buzz currently is a web system and mobile app currently on [Heroku](https://cse216-group4-app.herokuapp.com/) that lets users post short messages, known as ideas, and like other users' ideas.
The backend also currently supports a dislike system, although that has yet to be integrated into the rest of the project.
There is also a separate java program under admin-cli, which can create and drop tables from a database as needed.

# Routes, parameters, and returns
## GET
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

## POST EXAMPLES
curl -s https://cse216-group4-test.herokuapp.com/api/idea/1/reactions -X POST -d "{'ideaId':1,'userId':3,'type':-1}" (types: -1 = Toggle dislike, 1 = Toggle like, 0 = remove either)



curl -s https://cse216-group4-test.herokuapp.com/api/ideas -X POST -d "{'userId':1, 'subject': 'Hello', 'content': 'World', attachment:'test','allowedRoles':[1]}"

curl -s https://cse216-group4-test.herokuapp.com/api/users -X POST -d "{'avatar':'test.png','name':'John Doe','passwordHash':'feefgbvgrvf','companyRole':1}"


#Phase2

OAuth 2.0 Client ID: "527976125196-fnsdi7e3ml8n8ukjo02o8nec88rgnhfe.apps.googleusercontent.com"

#03/21
OAuth 2.0 Client ID: "WebID" 841253943983-23js8dkv8houcvggnt3trl09v83270am.apps.googleusercontent.com
https://betterprogramming.pub/create-a-simple-login-application-using-google-oauth-2-0-javascript-and-heroku-b1e56ad4604
https://auth0.com/docs/authenticate/protocols/oauth