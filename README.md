# OpportunityOdyssey

A comprehensive job portal built using Spring Boot 3.

**Technologies Used:** Java, Spring Boot 3, Spring MVC, Spring Security, Hibernate, MySQL, Maven

**Key Features:**

* **Role-Based Access Control:**  Separate access for job seekers and recruiters.
* **Advanced Job Search:**  Filtering by keywords, location, role, and other criteria.
* **Resume Upload:**  Easy resume submission for job applications.
* **Job Posting:**  Recruiters can create and manage job postings.
* **Application Tracking:**  Recruiters can view and manage applicant details.
* **User Profile Management:**  Job seekers and recruiters can manage their profiles.

**Installation:**

1. Clone the repository: `git clone https://github.com/rohan282/OpportunityOdyssey.git`
2. Navigate to the project directory: `cd OpportunityOdyssey`
3. Build the application using Maven: `mvn clean install` (This will download dependencies and create a JAR file)
4. Deploy the JAR file to Apache Tomcat.  The easiest way is usually to copy the generated JAR file (found in the `target` directory) to the `webapps` directory of your Tomcat installation.  For example (paths may vary): `cp target/OpportunityOdyssey-*.jar /path/to/tomcat/webapps/`  (Replace `OpportunityOdyssey-*.jar` with the actual name of your JAR file.)
5. Start Tomcat.

**Usage:**

1. Once Tomcat is started, the application should be accessible at `http://localhost:8080/OpportunityOdyssey-*` (Replace `OpportunityOdyssey-*` with the context path of your application.  This is usually the same as the JAR file name without the `.jar` extension). If the JAR file is renamed to `OpportunityOdyssey.jar`, then it will be accessible at `http://localhost:8080/OpportunityOdyssey`.
2. **User Registration:**  New users can register as either job seekers or recruiters.
3. **Job Seeker Features:**
    * Browse and search for jobs.
    * Upload resumes.
    * Apply for jobs.
    * Manage profile.
4. **Recruiter Features:**
    * Post new job openings.
    * View and manage applications.
    * Manage profile.

