namespace java me.xuhongfeng.thriftpool.sample

exception UserNotFoundException {}

struct User {
    1: i32 id,
    2: string name
}

service SampleThrift {

    User get(
        1: i32 id 
    ) throws (1: UserNotFoundException e),

    void save (
        1: User user
    ),

    string ping(),
}
