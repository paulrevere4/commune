# based on https://registry.hub.docker.com/u/samtstern/android-sdk/dockerfile/ with openjdk-8
# also based on https://hub.docker.com/r/gfx2015/android/~/dockerfile/
FROM openjdk:8

ENV DEBIAN_FRONTEND noninteractive

# Install dependencies
RUN dpkg --add-architecture i386 && \
    apt-get update && \
    apt-get install -yq libc6:i386 libstdc++6:i386 zlib1g:i386 libncurses5:i386 --no-install-recommends && \
    apt-get clean

# Download and untar SDK
ENV ANDROID_SDK_URL http://dl.google.com/android/android-sdk_r24.4.1-linux.tgz
RUN curl -L "${ANDROID_SDK_URL}" | tar --no-same-owner -xz -C /usr/local
ENV ANDROID_HOME /usr/local/android-sdk-linux
ENV ANDROID_SDK /usr/local/android-sdk-linux
ENV PATH ${ANDROID_HOME}/tools:$ANDROID_HOME/platform-tools:$PATH

# Install Android SDK components

# License Id: android-sdk-license-ed0d0a5b
ENV ANDROID_COMPONENTS platform-tools,build-tools-23.0.3,build-tools-24.0.0,build-tools-24.0.2,android-23,android-24,extra-android-m2repository,21
# License Id: android-sdk-license-5be876d5
ENV GOOGLE_COMPONENTS extra-android-m2repository,extra-google-m2repository

# update the sdk and accept the license agreements
RUN echo y | android update sdk --no-ui --all --filter "${ANDROID_COMPONENTS}" ; \
    echo y | android update sdk --no-ui --all --filter "${GOOGLE_COMPONENTS}"

# Support Gradle
ENV TERM dumb

# now set the working directory for the build
WORKDIR /android

# copy over the files needed for the build
COPY app /android/app
COPY build.gradle /android/build.gradle
COPY gradle /android/gradle
COPY gradle.properties /android/gradle.properties
COPY gradlew /android/gradlew
COPY gradlew.bat /android/gradlew.bat
COPY settings.gradle /android/settings.gradle

# finally, build the app
RUN ./gradlew build
