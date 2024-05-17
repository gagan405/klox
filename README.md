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

**JDK errors**

If you have installed JDK and probably also have another copy of SDK through `sdkman`, you might get into trouble with gradle build.
That's because the Gradle Test executor picks up the installation from sdkman - not sure why.

It ends up with the error:

```
has been compiled by a more recent version of the Java Runtime (class file version 65.0), this version of the Java Runtime only recognizes class file versions up to 61.0
```

To fix this, update the JDK through sdkman: `sdk install java`
