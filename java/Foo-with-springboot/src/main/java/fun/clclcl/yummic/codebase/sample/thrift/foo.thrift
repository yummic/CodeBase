namespace java com.mengsanjun.fooboot.thrift

service FooService {
  string getMessage(1:string info, 2:i32 sleep)
}