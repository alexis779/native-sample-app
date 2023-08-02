Download GraalVM JDK.

Set JDK

```
export JAVA_HOME=~/Library/Java/JavaVirtualMachines/graalvm-jdk-20.0.1+9.1/Contents/Home
```

Run application with agent enabled in the JVM

```
gradle -Pagent runApp
```

Copy generated reflection configuration

```
cp build/native/agent-output/runApp/reflect-config.json src/main/resources/META-INF/native-image
```

Build native image:

```
gradle nativeCompile
```

Run binary:
```
./build/native/nativeCompile/native-sample-app src/test/resources/input.json
```
