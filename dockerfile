FROM openjdk:17.0.2
COPY ./target/#{f} /home/app/
WORKDIR /home/app
ENTRYPOINT ["java", "-Dfile.encoding=utf-8", "-jar", "#{f}"]