rm -fr ../java/me/xuhongfeng/thriftpool/sample/SampleThrift.java
rm -fr ../java/me/xuhongfeng/thriftpool/sample/User.java
rm -fr ../java/me/xuhongfeng/thriftpool/sample/UserNotFoundException.java

thrift --gen java -out ../java sample.thrift
