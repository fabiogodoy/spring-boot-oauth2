# spring-boot-oauth2
OAuth2 Project working with mysql, storing access token and creating new users

It was created using oauth2 password flow

![OAuth2 Password Grant Flow](https://docs.apigee.com/api-platform/images/password-grant-flow-diagram.png) 

Usage example:

A post to the url http://localhost:8181/oauth/token?grant_type=password&username=xxx&password=xxx

Providing a header with the basic authorization like this:
'Authorization' 'Basic d2ViOnNlY3JldA=='

Here we have client credentials in a base64 format (in our example clientId = web and clientPassword = secret)

Or, my favorite way, you provide user and pass in a form, with another header:
A post to the url http://localhost:8181/oauth/token?grant_type=password

Providing a header with the basic authorization like this:
'Content-Type':  'application/x-www-form-urlencoded'
'Authorization': 'Basic d2ViOnNlY3JldA=='

Inside of the form, the username and password information
