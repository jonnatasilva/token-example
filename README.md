# Authorizartion server implementation

This project implements some concepts of OAuth2 protocol.

There are two main projects:

  - Authorization-server: It generates and checks token authorization. By default the token transmitted is using the JWT pattern.
  - Resource-server: It exposes a resource used by some clients. To check the token the resource server sends a request to authorization server using the remote token service approach.
  
Project structure:
  - token-example: project used to hold the sub-projects envolved on this example: 
  
    - authorization-server: application designed to hold authentication and authorization functionalities. 
    - resource-server: application exposes a simple resource if the token sent by client is authorizated.

Requirements:
  - jdk8
  - gradle 5.0
