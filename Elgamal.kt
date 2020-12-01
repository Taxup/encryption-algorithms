import java.math.BigInteger

fun main(args: Array<String>){

    print("Enter q - "); val q = BigInteger(readLine())
//    val q = BigInteger("71")
    print("Enter a - "); var a = BigInteger(readLine())
    while (true){
        val arr: Array<Int> = Array(q.toInt()-1,{0})
        for (i in 0 until q.toInt()-1){
            arr[i] = a.pow(i).mod(q).toInt()
        }
        arr.sort()
        var check = 0
        for (i in 0 until q.toInt()-1){
            if (!arr[i].equals(i+1)){
                break
            }else{
                check++
            }
        }
        if (check==q.toInt()-1)break
        print("a is not primitive root of q\nEnter a - "); a = BigInteger(readLine())
    }
//    val a = BigInteger("7")

    print("Enter xa - "); val xa = readLine()!!.toInt()
    val ya = a.pow(xa).mod(q)
//    val ya = BigInteger("3")
////    var xa = 0
//    for (i in 0..69){
//        if (a.pow(i).mod(q).toInt()==3) {
//            xa = i
//            break
//        }
//    }

    print("Enter m - "); val m = readLine()!!.toInt()
    print("Enter k - "); val k = readLine()!!.toInt()
//    val m = 30
//    for (k in 0..70) {
//        val K = ya.pow(k).mod(q)
//
//        val C1 = a.pow(k).mod(q)
//        val C2 = K.multiply(BigInteger(m.toString())).mod(q)
//        if (C1.toInt()==59){
//            println("C2 = $C2")
//            return
//        }
//    }

    var K = ya.pow(k).mod(q)

    val C1 = a.pow(k).mod(q)
    val C2 = K.multiply(BigInteger(m.toString())).mod(q)


    K = C1.pow(xa).mod(q)
    K = BigInteger(igf(q.toInt(), K.toInt()).toString()).add(q)
    val message = C2.multiply(K).mod(q)

    println("Ciphertext ($C1, $C2)")
    print(message)
}


internal fun igf(m: Int, n: Int): Int {
    val q = IntArray(10)
    val a1 = IntArray(10)
    val a2 = IntArray(10)
    val a3 = IntArray(10)
    val b1 = IntArray(10)
    val b2 = IntArray(10)
    val b3 = IntArray(10)
    q[0] = 0
    a1[0] = 1;a2[0] = 0
    b1[0] = 0;b2[0] = 1
    a3[0] = m;b3[0] = n
    var i = 1
    var j: Int
    do {
        j = i - 1
        q[i] = a3[j] / b3[j]
        a1[i] = b1[j]
        a2[i] = b2[j]
        a3[i] = b3[j]
        b1[i] = a1[j] - b1[j] * q[i]
        b2[i] = a2[j] - b2[j] * q[i]
        b3[i] = a3[j] % b3[j]
        i++
        if (b3[j] == 1)
            return b2[j]
    } while (b3[j + 1] != 0)
    return 0
}
//2.1 Ciphertext (49, 57)
//2.2 C2 = 29