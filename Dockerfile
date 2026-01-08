# -----------------------------------------------------------------------------
# AŞAMA 1: Build (Maven ile Projeyi Derle)
# -----------------------------------------------------------------------------
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Bağımlılıkları önbelleğe almak için önce pom.xml'i kopyala
COPY pom.xml .
RUN mvn dependency:go-offline

# Kaynak kodları kopyala
COPY src ./src

# --- EKSİK OLAN KOMUT BURASIYDI ---
# Kodları derle ve JAR dosyasını oluştur
RUN mvn clean package -DskipTests

# -----------------------------------------------------------------------------
# AŞAMA 2: Runtime (Uygulamayı Çalıştır)
# -----------------------------------------------------------------------------
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Derlenen JAR dosyasını kopyala
# Artık bu dosya var olacağı için hata vermeyecek
COPY --from=build /app/target/SpringbootBlogWithQueryDsl-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]