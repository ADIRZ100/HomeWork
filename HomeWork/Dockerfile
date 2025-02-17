# שלב 1: השתמש בתמונה של JDK 8
FROM openjdk:8-jdk-slim

# הגדרת תיקיית עבודה בתוך הקונטיינר
WORKDIR /app

# העתק את קובץ ה-JAR לאפליקציה לתוך הקונטיינר
COPY target/demo-PROJECT.jar app.jar

# חשוף את הפורט של האפליקציה (במקרה זה 8080)
EXPOSE 8080

# הפעל את האפליקציה
ENTRYPOINT ["java", "-jar", "app.jar"]
