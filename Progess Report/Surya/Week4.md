
## Week 4 Changes

![image](https://user-images.githubusercontent.com/60376265/144939634-5ab8f9ae-a741-4db7-ab6f-f05f6ae25b04.png)

### What I've worked on
- Finished rest api to coordinate with back office and map to kong api gateway
- user information authenticated for them to only see their own information on front-office
- code added in to update payments and rewards of users
- Clean up database to check if everything is intact and working
- Starting to configure yaml files for deployment
- Creating MySQL Cloud Instance to communicate with application

### List of Commits
* [rewards mapped to user sign on](https://github.com/nguyensjsu/fa21-172-jants/commit/62f1e2ddce5c2c534fa607d15061532f461fe6fc)
* [fixed error with payments routing](https://github.com/nguyensjsu/fa21-172-jants/commit/9408dccf0d7053377a848c8816b7546a06648b33)
* [back office function reward points editing - worked with Tanav](https://github.com/nguyensjsu/fa21-172-jants/commit/c50fc4341e57bffc8426a9b8856dd0a0547bfe99)
* [fixed unique registration error](https://github.com/nguyensjsu/fa21-172-jants/commit/a8f8ff96eb7fa122c32ba09507225d43c0a639af)
* [update rest APIs for back office](https://github.com/nguyensjsu/fa21-172-jants/commit/ce3d151eeb5658865308a67ab7159c6f8ab0a4f1)
* [reward points added to sign up rather than using a seperate button after sign up](https://github.com/nguyensjsu/fa21-172-jants/commit/d03ed63758267d3628fd8b38f6c22cd3a875ddb6)
* [gke deployment files](https://github.com/nguyensjsu/fa21-172-jants/commit/22cb40d4afb7ae2459658e9623f5b7c76f7e38ff)

### Challenges
- GKE deployment - problems with ingress deployment.yaml due to mysql integration (fixed by setting up connection and private instances)
- auth0 not working in backend GKE because of callback uri not configured (fixed by letting the admin(Tanav) integrate uri in)
- problems with kong files not properly reading in from endpoints - ERROR - "message":"failure to get a peer from the ring-balancer" (fixed by configuring ports/names/paths in kong-ingress-rule.yaml - works fine on GCP now)


### Updated Projects Tab
![image](https://user-images.githubusercontent.com/60376265/144940116-280fb83c-dbf2-4048-9f4d-3c5534d244d1.png)
