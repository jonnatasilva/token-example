# Authorizartion and resource server implementations example

This project is a simple application that implement token authorization.

Project structure:
  - token-example - project used to hold the sub-projects envolved on this example: 
  
    - authorization-server - application designed to hold authentication and authorization functionalities using OAuth2 protocol.
  
      The authorization-server enables an in memory user with the following credentials configuration:
        - username: jhon
        - password: 123
        - role: USER
      
      The authorization token used by the client to acesss the resource server is transmitted using the JWT pattern, but is possible to use JDBC token changing the <spring.profiles.active property> from 'jwtTokenStore' to 'jbdcTokenStore'.
      
    - resource-server: resource server that exposes a simple resource if the token sent by client is authorizated by authorization-server. The token checking is requested to authorization-server using the remote  token service approach.

Requiremts:
  - jdk8
  - gradle 5.0
