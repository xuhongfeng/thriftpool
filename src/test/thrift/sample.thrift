namespace java me.xuhongfeng.thriftpool.sample

service SampleThrift {

    i32 incr(
        1: i32 value
    ),

    void save (
        1: i32 value
    ),

    string ping(),
}
