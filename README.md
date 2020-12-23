# Streams-Playground


This is a simple java command line app to experiment and learn about Java Streams!
Java 11 is used here even though Streams were introduced in Java 8.

Key concepts:
- Streams are really good for plumbing and dealing with lots of data! Super convenient to have in the toolbox
- Pipelines consist of a stream souce, then zero or more intermediate ops, then a terminal op
- Short Circuit ops let us do infinite streams in finite time! (It sounds worse than it is)
- Specialized streams for primitive types, such as IntStream, DoubleStream. They include extra methods(sum, average, etc.)
- There are lots of ways to get data out of the stream. The Collectors interface has lots of good methods to use.... only a few are shown here!
- Infinite streams can be created with "generate" and "iterate", just need to make sure there is a bouund on the stream! These are great when we aren't sure how
many we will need or if we haven't gotten all the data for the stream yet
- Parallel streams can be very helpful and quick but we need to tread carefully 
- Java 9 introduced lots of good stuff.. such as takeWhile, iterate 3rd param, and nullable. Among others...

Some examples taken from [stackify](https://stackify.com/streams-guide-java-8/)