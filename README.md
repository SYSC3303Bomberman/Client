Client
======

Communicates with server. Contains GUI. Currently reads inputs from text file.

Expects address of host machine as input argument.

Order of events:
1.  Client sends join byte  (0x02)
2.  Client expects ACK byte (0x00)
3.  Client sends start byte (0x03)
4.  Client expects ACK byte (0x00)
5.  Client starts relaying inputs read from text file* and starts expecting strings**

*   Inputs should be individual characters seperated by white spaces characters. (or '\n')
**  Expected string must be terminated in '\0' and can only contain up to 15 characters in a row without being seperated by the'\n' character.
