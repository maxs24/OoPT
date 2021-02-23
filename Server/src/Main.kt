import com.kfu.imim.networking.Server

class ArgsParser(args: Array<String>){
    val serverPort: Int = args[0].toInt()
    val dbPort: Int = args[1].toInt()
    val dbAddress: String = args[2]
    val dbName: String = args[3]
    val dbLogin: String = args[4]
    val dbPassword: String = args[5]
}
fun main(argv: Array<String>) {
    val argsParser = ArgsParser(argv)
    val s = Server.getInstance(argsParser)
}