# NKNChecker

### Description
NKNChecker is used to track a list of NKN blockchain nodes on Google Clouds, Digital Oceans, your 
own server or any hosting services. Given a list of nodes' IP address, NKNChecker tells you: 
* Node's online status
* Number of NKN it has earned
* Node running time
* Protocol version
* Number of relayed messages
* Error messages

### Screenshots
![NKN Nodes Listing](/res-readme/screenshot_2019-07-12_11-50-23.jpg?raw=true "NKN Nodes Listing")

### Instructions
Currently, list of nodes is maintain in `QueryUtils.java` as a static JSON string constant.
You can update the list to query the status of your nodes.

### Note
NKNChecker was created almost 2 years ago as an effort to help me track my NKN nodes. Now it is no 
longer maintained. You can use [nknx.org](https://nknx.org/) instead.