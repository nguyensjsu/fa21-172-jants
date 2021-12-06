
## Week 4 Changes

![image](https://user-images.githubusercontent.com/60376265/144939634-5ab8f9ae-a741-4db7-ab6f-f05f6ae25b04.png)

### What I've worked on
- Finished rest api to coordinate with back office and map to kong api gateway
- user information authenticated for them to only see their own information on front-office
- code added in to update payments and rewards of users
- Clean up database to check if everything is intact and working
- Starting to configure yaml files for deployment
- Creating MySQL Cloud Instance to communicate with application

### Challenges
- GKE deployment - problems with ingress deployment.yaml due to mysql integration (fixed by setting up connection and private instances)
- auth0 not working in backend GKE because of callback uri not configured (fixed by letting the admin(Tanav) integrate uri in)
- problems with kong files not properly reading in from endpoints - ERROR - "message":"failure to get a peer from the ring-balancer" (fixed by configuring ports/names/paths in kong-ingress-rule.yaml - works fine on GCP now)


### Updated Projects Tab
![image](https://user-images.githubusercontent.com/60376265/144940116-280fb83c-dbf2-4048-9f4d-3c5534d244d1.png)
