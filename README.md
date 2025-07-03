KLOX
-

Implements the interpreter as done in [Crafting Interpreters](https://craftinginterpreters.com/) book.

**Choice of language**

Kotlin is used here, instead of Java, just to get some more hands on practice and 
learning with the beautiful language.

**Build and run**
Uses `Java 21`. Make sure to have it [setup](https://stackoverflow.com/a/70296798/564503) through `jenv` if you are using
multiple versions.

Use gradle to build the package:
~~~
./gradlew clean build
~~~

### JDK Version Errors & Gradle Build Issues

If you have multiple JDK installations (for example, a system JDK and another managed by [SDKMAN!](https://sdkman.io/)), you might encounter errors like:

has been compiled by a more recent version of the Java Runtime (class file version 65.0),
this version of the Java Runtime only recognizes class file versions up to 61.0

This happens because Gradle’s test executor or build process is running on an older JVM than the one used to compile your code.
Common Causes

* Your JAVA_HOME environment variable points to an older JDK.
* SDKMAN or other version managers override JAVA_HOME or the java command in your shell.
* Gradle daemon is running with an older JVM cached.

#### How to Fix
1. Use Gradle Toolchains (Recommended)

Gradle supports toolchains which allow it to automatically find and use the correct JDK version for compiling and running tests — regardless of your system default JVM.

Add this to your build.gradle.kts (or Groovy equivalent):

```java
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21)) // Or your target Java version
    }
}
```

```shell
export JAVA_HOME="$HOME/.sdkman/candidates/java/current"
export PATH="$JAVA_HOME/bin:$PATH"
```

Or explicitly to a version:

`sdk use java 21.0.2-tem`

2. Then restart your terminal and run:

```shell
./gradlew --stop  # Stop any running Gradle daemons
./gradlew clean test
```

3. Verify Your Setup

Check which Java version Gradle runs under:

`./gradlew -version`

Look for the Launcher JVM line — it should show your target JDK version.
