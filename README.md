# CMPE 172: - 
# Project Report:
Prepared by JANTS Group

## System Architecture Diagram

![image](https://user-images.githubusercontent.com/60376265/144949969-c8970925-2167-4832-8d39-8a6251252489.png)


### List of Team Journals
Individual contributions can be found below

* Anvay Bhanap - [Anvay's Journal](https://github.com/nguyensjsu/fa21-172-jants/tree/main/Progess%20Report/Anvay)

* Tanav Kudupudi - [Tanav's Journal](https://github.com/nguyensjsu/fa21-172-jants/tree/main/Progess%20Report/Tanav)

* Nimay Patel - [Nimay's Journal](https://github.com/nguyensjsu/fa21-172-jants/tree/main/Progess%20Report/Nimay)

* Jay Solanki - [Jay's Journal](https://github.com/nguyensjsu/fa21-172-jants/tree/main/Progess%20Report/Jay)

* Surya Ram - [Surya's Journal](https://github.com/nguyensjsu/fa21-172-jants/tree/main/Progess%20Report/Surya)


### Functional Requirements and Screenshots

Our project has implemented the following functional requirements:

* Front Office: A "Front Office" Web Application that allows users to do the following:

	* Register for an account
	* Log in to their account 
	* Redeem rewards
	* Place an order for their drink
	* Pay for their drink
	* See list of their own previous orders

* Back Office: A "Back Office" Web Application that allows the admin to access the following help desk functions:

	* See the list of users
	* Edit the list of users
	* See the list of rewards
	* Edit the list of rewards
	* See the list of orders

* REST APIs: Back Office and Front Office Applications are integrated with "Backends" using REST APIs.


### Technical Requirements

Our project has implemented the following technical requirements:

* Integration with CyberSource Payments
* Cloud Deployment on GCP/Kubernetes
	* MySQL Database 8.0
	* RabbitMQ
* Kong API Gateway
* Back Office Portals SSO Integration (Auth0 Extra Credit)

### Screenshots

### Front Office Application with MYSQL GCP
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/register%20page.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/register%20success.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/login%20page.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/login_success.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/drinks%20page.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/payments%20page.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/payments%20processed.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/previous%20orders.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/redeem%20rewards%20page.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/redeem%20rewards%202.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/cybersource%201.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/cybersource%20transaction%20data%202.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/cybersource%20transaction%20data.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/cloudsql%20gcp.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/user%20register%20mysql.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/rewards%20mysql%20table.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/payments%20mysql%20table.PNG)

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
### Back Office Application with MYSQL GCP
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/admin%20home%20page.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/auth0.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/admin%20log%20in.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/edit%20user%20admin.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/user%20details%20changed%20page.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/edit%20rewards%20admin.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/updated%20rewards%20page.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/rewards%20mysql%20after%20back%20office%20changes.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/admin%20logout%20reroute%20page.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/logout%20reroute%20page.PNG)

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
### REST API + KONG with MYSQL GCP
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/rewards%20api%20admin.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/rest%20api%20users%20admin.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/rest%20api%20orders%20admin.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/kong%20admin-users.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/kong%20admin-users%202.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/kong%20admin-rewards.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/kong%20admin-rewards-2.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/kong%20admin-orders.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/kong%20admin-orders%202.PNG)

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
### Local host container and docker hub container
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/mysql%20local%20host%20container.PNG)
![image](https://github.com/nguyensjsu/fa21-172-jants/blob/main/images/docker%20hub%20containers%20stored.PNG)
