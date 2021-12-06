all: clean

clean:
	gradle clean

compile:
	gradle build

test:
	gradle test

jar: 
	gradle build -x test
	gradle bootJar

run: jar
	echo Starting Spring at:  http://localhost:8082
	java -jar build/libs/jants-1.0.jar

# Docker

docker-build: jar
	docker build -t starbucks .
	docker images

docker-run: docker-build
	docker run --name starbucks -td -p 80:8080 starbucks	
	docker ps

docker-clean:
	docker stop starbucks
	docker rm starbucks
	docker rmi starbucks

docker-shell:
	docker exec -it starbucks bash 

docker-push:
	docker login
	docker build -t $(account)/starbucks -t $(account)/starbucks .
	docker push $(account)/starbucks 

# Compose

network-ls:
	docker network ls 

network-create:
	docker network create --driver bridge starbucks_network

network-prune:
	docker network prune

compose-up:
	docker-compose up --scale starbucks=2 -d

compose-down:
	docker-compose down 	

lb-stats:
	echo "user = admin | password = admin"
	open http://localhost:1936

lb-test:
	open http://localhost


# Pod

pod-run:
	kubectl apply -f pod.yaml

pod-list:
	kubectl get pods

pod-desc:
	kubectl describe pods starbucks

pod-delete:
	kubectl delete -f pod.yaml

pod-shell:
	kubectl exec -it starbucks -- /bin/bash

pod-logs:
	kubectl logs -f starbucks

# Deployment

deployment-create:
	kubectl create -f deployment.yaml --save-config 

deployment-get:
	kubectl get deployments

deployment-get-pods:
	kubectl get pods -l name=starbucks

deployment-pod-shell:
	kubectl exec -it $(pod) -- /bin/bash

deployment-upgrade:
	kubectl apply  -f deployment.yaml

deployment-delete:
	kubectl delete deployment starbucks-deployment

# Service

service-create:
	kubectl create -f service.yaml

service-get:
	kubectl get services

service-get-ip:
	kubectl get service starbucks -o wide

service-delete:
	kubectl delete service starbucks

# Ingress

ingress-apply:
	kubectl apply -f ingress.yaml

ingress-ip:
	kubectl get ingress starbucks-ingress




