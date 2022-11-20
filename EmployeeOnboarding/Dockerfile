FROM openjdk:17
RUN mkdir -p /opt/wm/employeeOb/lib && mkdir -p /var/log/wmeob
ADD target/employeeonboarding-0.0.1-SNAPSHOT.jar /opt/wm/employeeOb/lib/employeeonboarding-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/opt/wm/employeeOb/lib/employeeonboarding-0.0.1-SNAPSHOT.jar"]