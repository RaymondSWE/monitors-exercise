# 1. Monitors-exercise
Monitors are components that facilitate the regulation of shared data access. It consists of shared data structures, operations, and synchronization between concurrent procedure calls. Monitors are therefore also known as synchronization tools.

# 2. Messages-exercises
Message passing refers to the sending of a message to a process such as an object, parallel process, subroutine, function, or thread. The message can be used to invoke another process, either directly or indirectly. In object-oriented programming and parallel programming, message passing is particularly useful when a single message (such as a signal, data packet, or function) must be sent to many recipients at the same time

As an example, a distributed environment may be characterized by the presence of communicating processes on multiple computers that are connected by a network.


There are two operations. Send (message) and receive (message)Messages sent by processes can either be fixed size or variabels size. Fixed size implementations are easier to implement, but programming is more challenging. The implementation is not a problem, but programming with fixed sizes is a nuisance. Due to the fact that you will always need to keep in mind the message's size.
Implementing variable sizes at the system level is more complex, but programming is not as difficult as implementing fixed sizes. Programming becomes easier as a result of this.


In order for processes to communicate, there must be a link of communication between them. This link can be implemented in a number of ways. In order to implement a logical link, there are a number of methods available. The following are examples of these:

* Direct or indirect communcation
* Synchronous or asynchronous communication
* Automatic or explicit buffering.

**There are serveral issue related with features like naming, synchronization and buffering:**

The naming issue is related to direct and indirect communication. In order for processes to communicate, they need to be able to refer to each other through their names. There are two types of communication: direct and indirect.

**Under direct communication:** Processes that wish to communicate must explicitly identify the recipient or sender of the communication.

There can only be one communication link between each pair of processes. In order to communicate, both sender and receiver need to name each other. This is called symmetry addressing.

**A variant of Direct communication:** Only sender names the recipient. Receiver does not have to name the sender! This is called asymmetry in addressing.

Both of these methods (symmetric and asymmetric) have the disadvantage of limited modularity.

**Indirect communication:** Message are sent to and received from ports. This can be viewed as message that can be placed by processea nd afrom which message can be removed, meaning the port can hold message and it can be deleted aswell from these ports. Port has unique identificationn, and processes must share the port in order to communicate. 

Indirect can be associated with more than two processes. Between each pair of communicating processes, there may be a number of different communication links, with each lik correspoing to one port. The system can implement different method with whihc message will be received first. Either the first message will be received or a algorithm such as Round-Robin can be implemented. So which processes turn for receing message depends intely on the system and it can vary. 

Questions & answers:
Q: what operations does message passing have?
A: send and receive
Q: what is the difference between message passing and shared memory?
A: message passing is slower than shared memory
Q: what is the difference between message passing and RPC?
A: RPC is synchronous, message passing is asynchronous
Q: what is the difference between message passing and remote procedure call?
A: RPC is synchronous, message passing is asynchronous
Q: what is the difference between message passing and remote method invocation?
A: RMI is synchronous, message passing is asynchronous
